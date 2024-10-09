// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryValidIndexTimelagIteratorModified;
import traJ.TrajectoryModified;

public class MeanSquaredDisplacmentFeatureModified extends AbstractTrajectoryFeatureModified implements AbstractMeanSquaredDisplacmentEvaluatorModified
{
    private TrajectoryModified t;
    private int timelag;
    private boolean overlap;
    
    public MeanSquaredDisplacmentFeatureModified(final TrajectoryModified t, final int timelag) {
        this.overlap = false;
        this.t = t;
        this.timelag = timelag;
    }
    
    @Override
    public void setTimelag(final int timelag) {
        this.timelag = timelag;
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
    
    private double[] getMeanSquaredDisplacment(final TrajectoryModified t, final int timelag) {
        double msd = 0.0;
        final double[] result = new double[3];
        if (t.size() == 1) {
            result[1] = (result[0] = 0.0);
            result[2] = 1.0;
            return result;
        }
        if (timelag < 1) {
            throw new IllegalArgumentException("Timelag can not be smaller than 1");
        }
        final TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(t, timelag, this.overlap);
        int N = 0;
        while (it.hasNext()) {
            final int i = it.next();
            msd = msd + Math.pow(t.get(i).x - t.get(i + timelag).x, 2.0) + Math.pow(t.get(i).y - t.get(i + timelag).y, 2.0) + Math.pow(t.get(i).z - t.get(i + timelag).z, 2.0);
            ++N;
        }
        msd /= N;
        result[0] = msd;
        result[1] = timelag * (2 * timelag * timelag + 1.0) / (N - timelag + 1.0);
        result[2] = N;
        return result;
    }
    
    @Override
    public double[] evaluate() {
        return this.getMeanSquaredDisplacment(this.t, this.timelag);
    }
    
    public double getRelativeVariance() {
        return this.getMeanSquaredDisplacment(this.t, this.timelag)[1];
    }
    
    @Override
    public String getName() {
        return "Mean squared displacement-dt-" + this.timelag;
    }
    
    @Override
    public String getShortName() {
        return "MSD";
    }
    
    public void setOverlap(final boolean overlap) {
        this.overlap = overlap;
    }
}
