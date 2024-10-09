// 
// Decompiled by Procyon v0.5.36
// 

package DiffusionCoefficientEstimator;

import traJ.TrajectoryValidIndexTimelagIteratorModified;
import vecmath.Point3dModified;
import traJ.TrajectoryModified;
import features.AbstractTrajectoryFeatureModified;

public class CovarianceDiffusionCoefficientEstimatorModified extends AbstractTrajectoryFeatureModified implements AbstractDiffusionCoefficientEstimatorModified
{
    private TrajectoryModified t;
    private double fps;
    
    public CovarianceDiffusionCoefficientEstimatorModified() {
    }
    
    public CovarianceDiffusionCoefficientEstimatorModified(final TrajectoryModified t, final double fps) {
        this.t = t;
        this.fps = fps;
    }
    
    private double getDistanceProductX(final TrajectoryModified t, final int n, final int m) {
        final double xn = t.get(n + 1).x - t.get(n).x;
        final double xm = t.get(m + 1).x - t.get(m).x;
        return xn * xm;
    }
    
    private double getDistanceProductY(final TrajectoryModified t, final int n, final int m) {
        final double xn = t.get(n + 1).y - t.get(n).y;
        final double xm = t.get(m + 1).y - t.get(m).y;
        return xn * xm;
    }
    
    private double getDistanceProductZ(final TrajectoryModified t, final int n, final int m) {
        final double xn = t.get(n + 1).z - t.get(n).z;
        final double xm = t.get(m + 1).z - t.get(m).z;
        return xn * xm;
    }
    
    @Override
    public double[] getDiffusionCoefficient(final TrajectoryModified t, final double fps) {
        final double[] cov = this.getCovData(t, fps, 0.0);
        return cov;
    }
    
    private double[] getCovData(final TrajectoryModified track, final double fps, final double R) {
        double sumX = 0.0;
        double sumX2 = 0.0;
        double sumY = 0.0;
        double sumY2 = 0.0;
        double sumZ = 0.0;
        double sumZ2 = 0.0;
        int N = 0;
        int M = 0;
        final TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(track, 1);
        while (it.hasNext()) {
            final int i = it.next();
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
        final double msdX = sumX / N;
        final double msdY = sumY / N;
        final double msdZ = sumZ / N;
        final double covX = sumX2 / M;
        final double covY = sumY2 / M;
        final double covZ = sumZ2 / M;
        final double termXA = msdX / 2.0 * fps;
        final double termXB = covX * fps;
        final double termYA = msdY / 2.0 * fps;
        final double termYB = covY * fps;
        final double termZA = msdZ / 2.0 * fps;
        final double termZB = covZ * fps;
        final double DX = termXA + termXB;
        final double DY = termYA + termYB;
        final double DZ = termZA + termZB;
        final double D = (DX + DY + DZ) / track.getDimension();
        final double[] data = { D, Math.sqrt(Math.abs(covX)), Math.sqrt(Math.abs(covY)), Math.sqrt(Math.abs(covZ)) };
        return data;
    }
    
    @Override
    public double[] evaluate() {
        return this.result = this.getDiffusionCoefficient(this.t, this.fps);
    }
    
    @Override
    public String getName() {
        return "Diffusion coefficient (Covariance)";
    }
    
    @Override
    public String getShortName() {
        return "DC-COV";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
    }
}
