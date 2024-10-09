function analyzeSpotIntensities(varargin)

    % Configure behavior
    spotRadius = 1;
    maxBackground = 0;
    subtractBackground = 1;
    onlyInitialTrajectories = 0;
    Nbleach = 10;
    trackTrajectory = 1;
    extendTrajectory = 0;
    showImages = 0;
    showIntensityProfiles = 0;
    meanLength=-1;
    backgroundMethod = 0; % 0 = manual per cell
                          % 1 = manual per frame
                          % 2 = automatic around spot
			  % 3 = automatic through segmentation
    k0Percentile = 0.5; 	% For method = 0
    k0R = 4  *spotRadius; 	% For method = 3
    excludeTrajectories = [];
    
    % we expect args to be tag value pairs, hence
    if mod(size(varargin),2)~=0
        error('The number of variable arguments must be even')
    end
    % get arguments as tag value pairs
    for i=1:2:length(varargin)
        if strcmp(varargin{i},'maxBackground')==1
            maxBackground=varargin{i+1};
        elseif strcmp(varargin{i},'spotRadius')==1
            spotRadius=varargin{i+1};
        elseif strcmp(varargin{i},'subtractBackground')==1
            subtractBackground=varargin{i+1};
        elseif strcmp(varargin{i},'onlyInitialTrajectories')==1
            onlyInitialTrajectories=varargin{i+1};
        elseif strcmp(varargin{i},'trackTrajectory')==1
            trackTrajectory=varargin{i+1};
        elseif strcmp(varargin{i},'extendTrajectory')==1
            extendTrajectory=varargin{i+1};
        elseif strcmp(varargin{i},'Nbleach')==1
            Nbleach=varargin{i+1};
        elseif strcmp(varargin{i},'showImages')==1
            showImages=varargin{i+1};
        elseif strcmp(varargin{i},'showIntensityProfiles')==1
            showIntensityProfiles=varargin{i+1};
        elseif strcmp(varargin{i},'backgroundMethod')==1
            backgroundMethod=varargin{i+1};
        elseif strcmp(varargin{i},'backgroundPercentile')==1
            k0Percentile=varargin{i+1};
        elseif strcmp(varargin{i},'backgroundRadius')==1
            k0R=varargin{i+1};
        elseif strcmp(varargin{i},'meanLength')==1
            meanLength=varargin{i+1};
        elseif strcmp(varargin{i},'excludeTrajectories')==1
            excludeTrajectories=varargin{i+1};
        else
            error(['Parameter ' varargin{i} ' is unrecognized'])
        end
    end
    disp(['spotRadius =              ' num2str(spotRadius)])
    disp(['maxBackground =           ' num2str(maxBackground)])
    disp(['subtractBackground =      ' num2str(subtractBackground)])
    disp(['onlyInitialTrajectories = ' num2str(onlyInitialTrajectories)])
    disp(['trackTrajectory =         ' num2str(trackTrajectory)])
    disp(['extendTrajectory =        ' num2str(extendTrajectory)])
    disp(['showImages =              ' num2str(showImages)])
    disp(['showIntensityProfiles =   ' num2str(showIntensityProfiles)])
    disp(['backgroundMethod =        ' num2str(backgroundMethod)])
    disp(['k0Percentile =            ' num2str(k0Percentile)])
    disp(['k0R =                     ' num2str(k0R)])
    disp(['excludeTrajectories =     ' num2str(excludeTrajectories)])
    
    warning('off','all')	% suppress all warnings
    % the tracks dir
    fnTracksDir=fullfile('results','TrackingPackage','tracks');
    % the video sequence as TIF files
    fnRootImage=fullfile('videoSeq');
    % a matlab file with the tracking results
    fnTrack=fullfile(fnTracksDir,'Channel_1_tracking_result.mat');
    load(fnTrack);		% with all its known variables

    % a file with the mask as an image
    fnMask=fullfile('mask.tif');
    mask=[];
    numberOfRegionsInMask=1;
    if exist(fnMask,'file')		% if it exists, read it
        mask=imread(fnMask);
        numberOfRegionsInMask=max(mask(:));
    end

    % mean spot intensities
    fhMean=fopen(fullfile(fnTracksDir,'meanSpotIntensities.txt'),'w');
    % intensities by frame
    fhFrame=fopen(fullfile(fnTracksDir,'spotIntensitiesByFrame.txt'),'w');
    % open a log file
    fnDiary=fullfile(fnTracksDir,'log.txt');
    diary(fnDiary);
    
    % Check first and last frames with events
    veryFirstImage = 1e38;
    veryLastImage = -1e38;
    % find first and last images
    for i = 1:length(tracksFinal)		% tracksFinal comes from the matlab file
	% tracksFinal is a data structure containing an Nx1 structure with
	% N being the number of tracks
	% inside these Nx1 structures there are three tables
	%    1) tracksFeatIndxCG is a connectivity matrix of particles
	%	between frames, after gap closing, with one row per 
	%	merging and splitting tracks (i.e. involved in
	%	compound tracks, and one column per frame of track
	%	spans. Zeros are used for missing frames (before or
	%	after track start/end, or temporary disappearance)
	%    2) stracksCoordAmpCG contains info in the positions and
	%	amplitudes (intensities) of the particles after gap
	%	closing, with one row per track merging/splitting with
	%	each other) and columns = 8 x number of frames of the
	%	compound track: for every frame, it contains
	%	x, y, z, amplitude, stdevX, stdevY, stdevZ, stdevA
	% 	with one 8-tuple per time frame (0.05s/frame?)
	%    3) seqOfEvents is a matrix storing the sequence of events 
	%	in a compound track (start, end, splitting and merging):
	%	it has as many rows as events in a compound track (i.e.
	%	2x rge number of tracks within the compound track), and
	%	4 columns:
	%	      - start
	%	      - end
	%	      - index of track that starts or ends (local within
	%		the compound track, corresponding to track row 
	%		number in traclsFeatIndxCG and tracksCoordAmpCG)
	%	      - if a start is a true initiation or a split, or
	%		if an end is a true end or a merge, if NaN then
	%		the start/end is a real one, if a number, then
	%		it is a split/merge, where the track of interest
	%		(column 3) splits from/merges with the track in 
	%		column 4
	% SEE U-TRACK documentation
        idx=tracksFinal(i).seqOfEvents;
        firstImage=idx(1,1)-1;			% change offset
        lastImage=idx(2,1)-1;
        if firstImage < veryFirstImage
            veryFirstImage = firstImage;
        end
        if lastImage > veryLastImage
            veryLastImage = lastImage;
        end
    end
    fnFrames = dir(fullfile('videoseq','video*tif'));
    lastImageInDisk=size(fnFrames,1)-1;
    
    % Construct a mask for this cell
    if backgroundMethod==3
        cellMask=[];
        disp('Constructing cell mask')
        for img=veryFirstImage:veryLastImage  
	    fnImg=fullfile(fnRootImage,sprintf('video%04d.tif',img));
            % read in frame image (first image of the file only)
	    % as an X.Y matrix of pixel values
            I=imread(fnImg);

            if maxBackground>0
	    	% set intensities above maxbg to 1/10 of maxbg (???)
		%%% JR %%% ASK WHY
                I(I>maxBackground) = maxBackground/10;
            end

            % Segment the cell
	    %	get global threshold using Otsu's method (minimizes the
	    % 	intraclass variance of the threshold black and white pixels
            level = graythresh(I);
	    %	conver to B/W (im2bw is no longer recommended, should use
	    %	imbinarize instead)
            BW = im2bw(I,level);
	    %	set mask to BW or to an or with previous cell mask
            if size(cellMask,1)==0
                cellMask=BW;
            else
                cellMask=cellMask | BW;
            end
        end
	
	% open a new image with cell mask and an strel obkect with disk
	$ shape and radius 3
	% A strel object represents a flat morphological structuring element,
	% which is an essential part of morphological dilation and erosion 
	% operations.
	cellMask=imopen(cellMask,strel('disk',3));
	% label connected components in a 2D binary image
        cellMask=bwlabel(cellMask);
        M=max(cellMask(:));
        count=zeros(M,1);
        for m=1:M
            count(m)=sum(cellMask(:)==m);
        end
        [M,im]=max(count);
        cellMask=cellMask==im;
    end

    % compute bgX and bgY
    bgX = k0R*cosd(0:45:315);
    bgY = k0R*sind(0:45:315);
    
    if backgroundMethod == 0 % Manual, per cell
	% read first image
        fnImg = fullfile(fnRootImage,sprintf('video%04d.tif',veryFirstImage));
        I = imread(fnImg);
	% create new default figure
        figure(1)
	% define a 1x1x1 plot area
        subplot(111);
	% display image with scaled colors
        imagesc(I); colormap gray;
        disp('Select 8 background points to track along the series');
        [x,y]=ginput(8);

        allBrightness=[];
	% for each selected background point
        for j=1:length(x)
            xx=round(x(j));
            yy=round(y(j));

	    % for each image
            for img=veryFirstImage:veryLastImage
                fnImg=fullfile(fnRootImage,sprintf('video%04d.tif',img));
                I=imread(fnImg);
		% get a patch sized spotRadius x spotRadius
                patch=double(I((yy-spotRadius:yy+spotRadius),(xx-spotRadius:xx+spotRadius)));
                % set allBrightness to the mean
		allBrightness=[allBrightness; mean(patch(:))];
            end
        end
	% set k0 to the 95 quantile of brightness
        k0 = quantile(allBrightness,0.95);
    end
    
    % Process all spots
    disp('Processing trajectories')
    for i=1:length(tracksFinal)
        % ignore excluded trajectories
        if any(excludeTrajectories == i)
            continue
        end
	% extract x, y, ampl/intensity for trajectory i
        x=tracksFinal(i).tracksCoordAmpCG(1:8:end); % x is in nm
        y=tracksFinal(i).tracksCoordAmpCG(2:8:end); % y is in nm
        I=tracksFinal(i).tracksCoordAmpCG(4:8:end);
        idx=tracksFinal(i).seqOfEvents;
	% shift offset
        firstImage=idx(1,1)-1;
        lastImage=idx(2,1)-1;
        if firstImage ~= veryFirstImage && onlyInitialTrajectories
            continue
        end
        
        jj=1;
        intensitiesAlongTrajectory=[];
        meanIntensityAlongTrajectory=[];
        rawMeanIntensityAlongTrajectory=[];
        k0AlongTrajectory=[];
        validCoordinates=[];
        maskRegionAlongTrajectory=zeros(numberOfRegionsInMask+1,1);
        lastImageForTrack=lastImage;
        if extendTrajectory
            lastImageForTrack=lastImageInDisk;
        end
        for img=firstImage:lastImageForTrack
	    % read image and convert intensities to double
            fnImg=fullfile(fnRootImage,sprintf('video%04d.tif',img));
            I=double(imread(fnImg));
            
	    
            if ~isnan(x(jj)) && ~isnan(y(jj))
                % Estimate background for this frame
                if backgroundMethod==3 % Automatic through segmentation
                    k0=quantile(I(cellMask(:)),k0Percentile);
                elseif backgroundMethod==1 % Manual, per frame
                    figure(1)
                    subplot(111);
                    imagesc(I); colormap gray;
                    disp('Select 8 background points in this frame');
                    [xi,yi]=ginput(8);

                    allBrightness=[];
                    for j=1:length(xi)
                        xx=round(xi(j));
                        yy=round(yi(j));

                        patch=double(I((yy-spotRadius:yy+spotRadius),(xx-spotRadius:xx+spotRadius)));
                        allBrightness=[allBrightness; mean(patch(:))];
                    end
                    k0 = quantile(allBrightness,0.95);
                elseif backgroundMethod==2 % Automatic through radius
                    xi=round(x(jj)+bgX);
                    yi=round(y(jj)+bgY);
                    allBrightness=[];
                    for j=1:length(xi)
                        xx=round(xi(j));
                        yy=round(yi(j));

                        if xx-spotRadius>=1 && xx+spotRadius<=size(I,2) && ...
                           yy-spotRadius>=1 && yy+spotRadius<=size(I,1)
                            patch=double(I((yy-spotRadius:yy+spotRadius),(xx-spotRadius:xx+spotRadius)));
                            allBrightness=[allBrightness; mean(patch(:))];
                        end
                    end
                    k0 = quantile(allBrightness,0.95);
                end
                
                % Measure the intensities around the spot
                xx=round(x(jj));
                yy=round(y(jj));
                if xx-spotRadius>=1 && xx+spotRadius<=size(I,2) && yy-spotRadius>=1 && yy+spotRadius<=size(I,1)
                    patch=double(I((yy-spotRadius:yy+spotRadius),(xx-spotRadius:xx+spotRadius)));
                    rawMeanIntensityAlongTrajectory=[rawMeanIntensityAlongTrajectory; mean(patch(:))];
                    if subtractBackground
                        patch=patch-k0;
                    end
                    k0AlongTrajectory=[k0AlongTrajectory; k0];
                    intensitiesAlongTrajectory=[intensitiesAlongTrajectory; patch(:)];
                    meanIntensityAlongTrajectory=[meanIntensityAlongTrajectory; mean(patch(:))];
                    validCoordinates=[validCoordinates img];
                    maxSpot = max(patch(:));

                    if size(mask,1)>0
                        maskRegion=mask(yy,xx);
                        maskRegionAlongTrajectory(maskRegion+1)=maskRegionAlongTrajectory(maskRegion+1)+1;
                    else
                        maskRegion=0;
                    end
        
                    disp(['    spot=' num2str(i) ' frame=' num2str(img) ...
                          ' x=' num2str(xx) ' y=' num2str(yy) ...
                          ' k0=' num2str(k0) ...
                          ' spotRawIntensity=' num2str(rawMeanIntensityAlongTrajectory(end)) ...
                          ' spotCorrectedIntensity=' num2str(meanIntensityAlongTrajectory(end)) ...
                          ' maxCorrectedSpotIntensity=' num2str(maxSpot) ...
                          ' maskRegion=' num2str(maskRegion)])
                    fprintf(fhFrame,'%d %d %f %f %f %d %f %f %f\n',i,img,...
                        mean(rawMeanIntensityAlongTrajectory(end)),meanIntensityAlongTrajectory(end),...
                        maxSpot,maskRegion,xx,yy,k0);
                    
                    if showImages && backgroundMethod==3
                      figure(1);
                      subplot(131); imagesc(I); colormap gray
                      subplot(132); imagesc(cellMask);
                      hold on
                      plot(x(jj),y(jj),'o');
                      subplot(133);
                      I(I<k0)=max(I(:));
                      imagesc(I);
                      text(round(x(jj)),round(y(jj)),'X','EdgeColor',[.7 .9 .7]);
                      disp('Press any key');
                      pause
                    end
                end
            end
            
            if trackTrajectory && img < lastImage
                jj = jj + 1;
            end
        end
        
        % Check photobleaching by comparing Nbleach images from the beginning
	% of the trajectory to Nbleach images from the end: if their mean
	% intensities differ, then there may have been photobleaching 
	% degradation of the fluorophore during the experiment due to
	% exposition to light)
        if length(meanIntensityAlongTrajectory) > 2 * Nbleach
            x1 = meanIntensityAlongTrajectory(1 : Nbleach);
            x2 = meanIntensityAlongTrajectory(end - Nbleach : end);
	    % compare using t-test (no correction for multiple comparisons!)
            [H,pval] = ttest2(x1,x2,'tail','right');
            if pval<  0.05
                disp('   ******* Possible photobleaching')
            end
        end
        
        % Write summary for this trajectory
        [M,iM]=max(maskRegionAlongTrajectory);
        if meanLength==-1
            meanIntensity = mean(intensitiesAlongTrajectory(:));
        else
            lengthToUse = min(meanLength,length(intensitiesAlongTrajectory(:)));
            meanIntensity = mean(intensitiesAlongTrajectory(1:lengthToUse));
        end
        disp([' spot=' num2str(i) ' meanCorrectedSpotIntensity along frames=' num2str(meanIntensity) ' majoritarian region=' num2str(iM)])
        fprintf(fhMean,'%d %f %d\n',i,meanIntensity,iM);

        if showIntensityProfiles
          figure(1);
          subplot(111);
          if trackTrajectory
            plot(validCoordinates,rawMeanIntensityAlongTrajectory,validCoordinates,k0AlongTrajectory);
            xlabel('Frame number')
          else
            plot(1:length(rawMeanIntensityAlongTrajectory),rawMeanIntensityAlongTrajectory,...
                 1:length(k0AlongTrajectory),k0AlongTrajectory);
          end
          legend('Spot mean raw intensity','Background intensity')
          disp('Press any key');
          pause
        end
    end
    fclose(fhMean);
    fclose(fhFrame);
    diary off


    
