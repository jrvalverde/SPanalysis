package features;

import traJ.TrajectoryModified;
import vecmath.Point3dModified;

public class FractalDimensionFeatureModified extends AbstractTrajectoryFeatureModified {
   TrajectoryModified t;

   public FractalDimensionFeatureModified(TrajectoryModified t) {
      this.t = t;
      if (t.getDimension() != 2) {
         throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys");
      }
   }

   public double[] evaluate() {
      double largestDistance = Double.MIN_VALUE;
      double totalLength = 0.0D;

      double d;
      for(int i = 0; i < this.t.size(); ++i) {
         for(int j = i + 1; j < this.t.size(); ++j) {
            d = ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(j));
            if (d > largestDistance) {
               largestDistance = d;
            }
         }

         if (i > 0) {
            totalLength += ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(i - 1));
         }
      }

      double n = (double)(this.t.size() - 1);
      d = Math.log(n) / (Math.log(n) + Math.log(largestDistance / totalLength));
      this.result = new double[]{d};
      return this.result;
   }

   public String getName() {
      return "Fractal Dimension";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
      if (t.getDimension() != 2) {
         throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys");
      }
   }

   public String getShortName() {
      return "FD";
   }
}
