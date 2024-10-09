// 
// Decompiled by Procyon v0.5.36
// 

package trajectory_classifier;

import java.util.Iterator;
import java.util.ArrayList;
import traJ.TrajectoryModified;

public class SubtrajectoryModified extends TrajectoryModified
{
    private static final long serialVersionUID = 3846588503781023924L;
    private TrajectoryModified parent;
    private double confidence;
    
    public SubtrajectoryModified(final TrajectoryModified parent, final int dimension) {
        super(dimension);
        this.parent = parent;
    }
    
    public SubtrajectoryModified(final TrajectoryModified parent, final int dimension, final int relativeStartPoint) {
        super(dimension, relativeStartPoint);
        this.parent = parent;
    }
    
    public TrajectoryModified getParent() {
        return this.parent;
    }
    
    public static ArrayList<SubtrajectoryModified> getTracksWithSameParant(final ArrayList<SubtrajectoryModified> tracks, final long parentid) {
        final ArrayList<SubtrajectoryModified> res = new ArrayList<SubtrajectoryModified>();
        for (final SubtrajectoryModified sub : tracks) {
            if (sub.getParent().getID() == parentid) {
                res.add(sub);
            }
        }
        return res;
    }
    
    public void setConfidence(final double confidence) {
        this.confidence = confidence;
    }
    
    public double getConfidence() {
        return this.confidence;
    }
}
