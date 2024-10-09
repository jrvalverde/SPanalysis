// 
// Decompiled by Procyon v0.5.36
// 

package trajectory_classifier;

import features.AbstractTrajectoryFeatureModified;

public class FeatureWorkerModified extends Thread
{
    double[] result;
    AbstractTrajectoryFeatureModified c;
    EVALTYPE ev;
    int resIndex;
    
    public FeatureWorkerModified(final double[] result, final int resIndex, final AbstractTrajectoryFeatureModified c, final EVALTYPE ev) {
        this.result = result;
        this.c = c;
        this.ev = ev;
        this.resIndex = resIndex;
    }
    
    @Override
    public void run() {
        switch (this.ev) {
            case FIRST: {
                final double[] res = this.c.getValue();
                this.result[this.resIndex] = res[0];
                break;
            }
            case SECOND: {
                final double[] res = this.c.getValue();
                this.result[this.resIndex] = res[1];
                break;
            }
            case RATIO_01: {
                final double[] res = this.c.getValue();
                this.result[this.resIndex] = res[0] / res[1];
                break;
            }
            case RATIO_10: {
                final double[] res = this.c.getValue();
                this.result[this.resIndex] = res[1] / res[0];
                break;
            }
            case RATIO_12: {
                final double[] res = this.c.getValue();
                this.result[this.resIndex] = res[1] / res[2];
                break;
            }
        }
    }
    
    enum EVALTYPE
    {
        FIRST("FIRST", 0), 
        SECOND("SECOND", 1), 
        RATIO_01("RATIO_01", 2), 
        RATIO_10("RATIO_10", 3), 
        RATIO_12("RATIO_12", 4);
        
        private EVALTYPE(final String name, final int ordinal) {
        }
    }
}
