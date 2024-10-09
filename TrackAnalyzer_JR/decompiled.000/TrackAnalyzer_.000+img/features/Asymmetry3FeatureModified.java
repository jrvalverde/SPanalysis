package features;

import math.RadiusGyrationTensor2DModified;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import traJ.TrajectoryModified;

public class Asymmetry3FeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;

   public Asymmetry3FeatureModified(TrajectoryModified t) {
      this.t = t;
   }

   public double[] evaluate() {
      Array2DRowRealMatrix gyr = RadiusGyrationTensor2DModified.getRadiusOfGyrationTensor(this.t);
      EigenDecomposition eigdec = new EigenDecomposition(gyr);
      double e1 = eigdec.getRealEigenvalue(0);
      double e2 = eigdec.getRealEigenvalue(1);
      double asym = -1.0D * Math.log(1.0D - Math.pow(e1 - e2, 2.0D) / (2.0D * Math.pow(e1 + e2, 2.0D)));
      this.result = new double[]{asym};
      return this.result;
   }

   public String getName() {
      return "Assymetry3";
   }

   public String getShortName() {
      return "ASYM3";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
