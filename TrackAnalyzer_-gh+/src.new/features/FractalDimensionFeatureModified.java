package features;
import traJ.TrajectoryModified;

public class FractalDimensionFeatureModified extends AbstractTrajectoryFeatureModified{

	TrajectoryModified t;
	/**
	 * 
	 * @param t Trajectory for which the fractal dimension is to be calculated.
	 */
	public FractalDimensionFeatureModified(TrajectoryModified t) {
		this.t = t;
		if(t.getDimension() != 2){
			throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys"); 
		}
	}
	
	/**
	 * @return Returns an double array with the elements [0] = fractal path dimension
	 */
	@Override
	public double[] evaluate() {
		double largestDistance = Double.MIN_VALUE;
		double totalLength = 0;
		for(int i = 0; i < t.size(); i++){
			for(int j = i+1; j < t.size(); j++){
				double d = t.get(i).distance(t.get(j));
				if(d>largestDistance){
					largestDistance = d;
				}
			}
			if(i>0){
				totalLength += t.get(i).distance(t.get(i-1));
			}
		}
	
		double n = t.size()-1;
		double fractalDImension = Math.log(n)/(Math.log(n)+Math.log(largestDistance/totalLength));
		result = new double[] {fractalDImension};

	
		return result;
	}

	@Override
	public String getName() {
		return "Fractal Dimension";
	}
	
	@Override
	public void setTrajectory(TrajectoryModified t) {
		this.t = t;
		result = null;
		if(t.getDimension() != 2){
			throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys"); 
		}
		
	}

	@Override
	public String getShortName() {
		return "FD";
	}

}