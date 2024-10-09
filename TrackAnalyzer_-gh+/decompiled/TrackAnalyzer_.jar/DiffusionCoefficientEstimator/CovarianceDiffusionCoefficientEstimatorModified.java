package DiffusionCoefficientEstimator;

import features.AbstractTrajectoryFeatureModified;
import traJ.TrajectoryModified;
import traJ.TrajectoryValidIndexTimelagIteratorModified;
import vecmath.Point3dModified;

public class CovarianceDiffusionCoefficientEstimatorModified extends AbstractTrajectoryFeatureModified implements AbstractDiffusionCoefficientEstimatorModified {
   private TrajectoryModified t;
   private double fps;

   public CovarianceDiffusionCoefficientEstimatorModified() {
   }

   public CovarianceDiffusionCoefficientEstimatorModified(TrajectoryModified t, double fps) {
      this.t = t;
      this.fps = fps;
   }

   private double getDistanceProductX(TrajectoryModified t, int n, int m) {
      double xn = ((Point3dModified)t.get(n + 1)).x - ((Point3dModified)t.get(n)).x;
      double xm = ((Point3dModified)t.get(m + 1)).x - ((Point3dModified)t.get(m)).x;
      return xn * xm;
   }

   private double getDistanceProductY(TrajectoryModified t, int n, int m) {
      double xn = ((Point3dModified)t.get(n + 1)).y - ((Point3dModified)t.get(n)).y;
      double xm = ((Point3dModified)t.get(m + 1)).y - ((Point3dModified)t.get(m)).y;
      return xn * xm;
   }

   private double getDistanceProductZ(TrajectoryModified t, int n, int m) {
      double xn = ((Point3dModified)t.get(n + 1)).z - ((Point3dModified)t.get(n)).z;
      double xm = ((Point3dModified)t.get(m + 1)).z - ((Point3dModified)t.get(m)).z;
      return xn * xm;
   }

   public double[] getDiffusionCoefficient(TrajectoryModified t, double fps) {
      double[] cov = this.getCovData(t, fps, 0.0D);
      return cov;
   }

   private double[] getCovData(TrajectoryModified track, double fps, double R) {
      double sumX = 0.0D;
      double sumX2 = 0.0D;
      double sumY = 0.0D;
      double sumY2 = 0.0D;
      double sumZ = 0.0D;
      double sumZ2 = 0.0D;
      int N = 0;
      int M = 0;
      TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(track, 1);

      while(it.hasNext()) {
         int i = it.next();
         sumX += this.getDistanceProductX(track, i, i);
         sumY += this.getDistanceProductY(track, i, i);
         sumZ += this.getDistanceProductZ(track, i, i);
         ++N;
         if (i + 2 < track.size() && track.get(i + 2) != null) {
            sumX2 += this.getDistanceProductX(track, i, i + 1);
            sumY2 += this.getDistanceProductY(track, i, i + 1);
            sumZ2 += this.getDistanceProductZ(track, i, i + 1);
            ++M;
         }
      }

      double msdX = sumX / (double)N;
      double msdY = sumY / (double)N;
      double msdZ = sumZ / (double)N;
      double covX = sumX2 / (double)M;
      double covY = sumY2 / (double)M;
      double covZ = sumZ2 / (double)M;
      double termXA = msdX / 2.0D * fps;
      double termXB = covX * fps;
      double termYA = msdY / 2.0D * fps;
      double termYB = covY * fps;
      double termZA = msdZ / 2.0D * fps;
      double termZB = covZ * fps;
      double DX = termXA + termXB;
      double DY = termYA + termYB;
      double DZ = termZA + termZB;
      double D = (DX + DY + DZ) / (double)track.getDimension();
      double[] data = new double[]{D, Math.sqrt(Math.abs(covX)), Math.sqrt(Math.abs(covY)), Math.sqrt(Math.abs(covZ))};
      return data;
   }

   public double[] evaluate() {
      this.result = this.getDiffusionCoefficient(this.t, this.fps);
      return this.result;
   }

   public String getName() {
      return "Diffusion coefficient (Covariance)";
   }

   public String getShortName() {
      return "DC-COV";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
   }
}
