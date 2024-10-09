function gatherDiffusionAndIntensity(diffusionSuffix)
    % diffusionSuffix is either 'Long' or 'Short'
    % output subdirectory
    fnTrackDir = fullfile('results','TrackingPackage','tracks');
    % file with diffusion Coefficients
    fnDiffusion=fullfile(fnTrackDir,['diffusionCoefficients' diffusionSuffix '.txt']);
    $ mean spot intensities
    fnIntensities=fullfile(fnTrackDir,'meanSpotIntensities.txt');
    % output file
    fnOut=fullfile(fnTrackDir,['diffusionCoefficientsAndIntensities' diffusionSuffix '.txt']);
    % log file
    fnLog=fullfile(fnTrackDir,['log_diffusionCoefficientsAndIntensities' diffusionSuffix '.txt']);
    
    diffusions=textread(fnDiffusion);		% read diffusion ciefs
    intensities=textread(fnIntensities);	% read intensities
    fhOut = fopen(fnOut,'w');			% open output flle
    
    diary(fnLog)
    for i=1:size(diffusions,1)
        spotIdx = diffusions(i,1);		% spot is column 1
        diffusionCoefficient = diffusions(i,2);	% diffusion column 2
        if size(diffusions,2)==3
           maskRegion = diffusions(i,3);	% mask region column 3
        else
            maskRegion = 1;
        end
        
        idx = find(intensities(:,1)==spotIdx);	% get intensities for this spot
        if ~isempty(idx)
            intensity = intensities(idx,2);
	    % display in log file
            disp(['spot= ' num2str(spotIdx) ...
                  ' diffusion= ' num2str(diffusionCoefficient) ...
                  ' spotCorrectedIntensity= ' num2str(intensity) ...
                  ' maskRegion=' num2str(maskRegion)])
            % and save to output file
	    fprintf(fhOut,'%d %f %f %d\n',spotIdx,diffusionCoefficient,...
                intensity,maskRegion);
        end
    end
    diary off
    fclose(fhOut);
end
