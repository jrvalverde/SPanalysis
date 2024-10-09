// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryValidIndexTimelagIteratorModified;
import traJ.TrajectoryModified;

public class QuartricMomentFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    private int timelag;
    private String shortname;
    
    public QuartricMomentFeatureModified(final TrajectoryModified t, final int timelag) {
        this.shortname = "QMOMENT";
        this.t = t;
        this.timelag = timelag;
    }
    
    @Override
    public double[] evaluate() {
        double sum = 0.0;
        final TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(this.t, this.timelag);
        int N = 0;
        while (it.hasNext()) {
            final int i = it.next();
            sum = sum + Math.pow(this.t.get(i).x - this.t.get(i + this.timelag).x, 4.0) + Math.pow(this.t.get(i).y - this.t.get(i + this.timelag).y, 4.0) + Math.pow(this.t.get(i).z - this.t.get(i + this.timelag).z, 4.0);
            ++N;
        }
        return this.result = new double[] { sum / N };
    }
    
    public void setTimelag(final int timelag) {
        this.timelag = timelag;
    }
    
    @Override
    public String getName() {
        return "Quartric Moment";
    }
    
    @Override
    public String getShortName() {
        return this.shortname;
    }
    
    public void setShortName(final String name) {
        this.shortname = name;
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
