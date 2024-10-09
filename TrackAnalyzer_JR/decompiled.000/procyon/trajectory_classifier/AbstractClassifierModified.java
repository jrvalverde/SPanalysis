// 
// Decompiled by Procyon v0.5.36
// 

package trajectory_classifier;

import java.util.ArrayList;
import traJ.TrajectoryModified;

public abstract class AbstractClassifierModified
{
    public abstract String classify(final TrajectoryModified p0);
    
    public abstract void start();
    
    public abstract void stop();
    
    public abstract String[] classify(final ArrayList<TrajectoryModified> p0);
    
    public abstract double[] getConfindence();
}
