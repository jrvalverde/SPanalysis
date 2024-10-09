package math;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import traJ.TrajectoryModified;
import vecmath.Point3dModified;
import vecmath.Vector2d;

public class MomentsCalculatorModified {
   private TrajectoryModified t;

   public MomentsCalculatorModified(TrajectoryModified t) {
      this.t = t;
   }

   public double calculateNthMoment(int n) {
      Array2DRowRealMatrix gyr = RadiusGyrationTensor2DModified.getRadiusOfGyrationTensor(this.t);
      EigenDecomposition eigdec = new EigenDecomposition(gyr);
      Vector2d eigv = new Vector2d(eigdec.getEigenvector(0).getEntry(0), eigdec.getEigenvector(0).getEntry(1));
      double[] projected = new double[this.t.size()];

      double mean;
      for(int i = 0; i < this.t.size(); ++i) {
         Vector2d pos = new Vector2d(((Point3dModified)this.t.get(i)).x, ((Point3dModified)this.t.get(i)).y);
         mean = eigv.dot(pos);
         projected[i] = mean;
      }

      Mean m = new Mean();
      StandardDeviation s = new StandardDeviation();
      mean = m.evaluate(projected);
      double sd = s.evaluate(projected);
      double sumPowN = 0.0D;

      for(int i = 0; i < projected.length; ++i) {
         sumPowN += Math.pow((projected[i] - mean) / sd, (double)n);
      }

      double nThMoment = sumPowN / (double)projected.length;
      return nThMoment;
   }
}
