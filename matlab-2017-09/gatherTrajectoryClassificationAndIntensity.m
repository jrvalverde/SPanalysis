function gatherTrajectoryClassificationAndIntensity(trajectorySuffix)
    % we are working at an experiment directory
    % we'll save the results in a subdirectory named
    %	./results/TrackingPackage/tracks/
    fnTrackDir = fullfile('results','TrackingPackage','tracks');
    % classification goes into file
    %	experiment/results/TrackingPackage/tracks/trajectoryClassification'suffix'.txt
    fnTrajectories = fullfile(fnTrackDir,['trajectoryClassification' trajectorySuffix '.txt']);
    % diffusion coefficients
    fnDiffusions = fullfile(fnTrackDir,'diffusionCoefficients.txt');
    % mean Spot Intensities
    fnIntensities = fullfile(fnTrackDir,'meanSpotIntensities.txt');
    % output classification and intensities 
    fnOut = fullfile(fnTrackDir,['trajectoryClassificationAndIntensities' trajectorySuffix '.txt']);
    % output classification and intensities to output file
    fnLog = fullfile(fnTrackDir,['log_trajectoryClassificationAndIntensities' trajectorySuffix '.txt']);
    
    % get list of trajectries from file	trajectoryClassification'suffix'.tx
    trajectories = textread(fnTrajectories);	% (not recommended)
    % same for intensities
    intensities = textread(fnIntensities);	% should use textscan()
    % and diffusion coefficients
    diffusionCoefficients = textread(fnDiffusions);
    
    % open output file
    fhOut = fopen(fnOut,'w');
    
    diary(fnLog)				% annotate log
    for i=1:size(trajectories,1)		% for all trajectories read
        spotIdx = trajectories(i,1);		% spotIdx is in column 1
        movementType = trajectories(i,2);	% movement type un column 2
        firstMoment = trajectories(i,3);	% first movement in column 3
        
        idx = find(intensities(:,1)==spotIdx);	% find the intensities of this spot
        if ~isempty(idx)			% if not empty
            intensity = intensities(idx,2);	%
            maskRegion = intensities(idx,3);
            idxC = find(diffusionCoefficients(:,1)==spotIdx);
            if ~isempty(idxC)
                diffusion=diffusionCoefficients(idxC,2);
            else
                diffusion=-1;
            end
	    # display values of variables (to log file?)
            disp(['spot= ' num2str(spotIdx) ...
                  ' movementType= ' num2str(movementType) ...
                  ' MSSFirstMoment= ' num2str(firstMoment) ...
                  ' spotCorrectedIntensity= ' num2str(intensity) ...
                  ' diffusionCoefficient= ' num2str(diffusion) ...
                  ' maskRegion=' num2str(maskRegion)])
            fprintf(fhOut,'%d %d %f %f %f %d\n',spotIdx,movementType,firstMoment,...
                intensity,diffusion,maskRegion);
        end
    end
    diary off
    fclose(fhOut);
end
