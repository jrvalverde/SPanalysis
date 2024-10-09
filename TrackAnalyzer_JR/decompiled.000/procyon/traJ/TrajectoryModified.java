// 
// Decompiled by Procyon v0.5.36
// 

package traJ;

import java.util.List;
import vecmath.Tuple3dModified;
import features.AbstractTrajectoryFeatureModified;
import vecmath.Point3dModified;
import java.util.ArrayList;

public class TrajectoryModified extends ArrayList<Point3dModified>
{
    private static final long serialVersionUID = 1L;
    private int dimension;
    private int relativeStartTimepoint;
    private long id;
    private static long idCounter;
    private String type;
    private ArrayList<AbstractTrajectoryFeatureModified> features;
    
    static {
        TrajectoryModified.idCounter = 1L;
    }
    
    public TrajectoryModified(final int dimension) {
        this.type = "";
        this.dimension = dimension;
        this.relativeStartTimepoint = 0;
        this.id = TrajectoryModified.idCounter++;
        this.features = new ArrayList<AbstractTrajectoryFeatureModified>();
    }
    
    public TrajectoryModified(final int dimension, final int relativeStartTimepoint) {
        this.type = "";
        this.dimension = dimension;
        this.relativeStartTimepoint = relativeStartTimepoint;
        this.id = TrajectoryModified.idCounter++;
        this.features = new ArrayList<AbstractTrajectoryFeatureModified>();
    }
    
    public TrajectoryModified() {
        this.type = "";
        this.relativeStartTimepoint = 0;
        this.features = new ArrayList<AbstractTrajectoryFeatureModified>();
    }
    
    @Override
    public TrajectoryModified subList(final int fromIndex, final int toIndex) {
        final TrajectoryModified t = new TrajectoryModified(this.dimension);
        for (int i = fromIndex; i < toIndex; ++i) {
            t.add(this.get(i));
        }
        return t;
    }
    
    public ArrayList<AbstractTrajectoryFeatureModified> getFeatures() {
        return this.features;
    }
    
    public void addFeature(final AbstractTrajectoryFeatureModified feature) {
        this.features.add(feature);
    }
    
    public TrajectoryModified getCopy() {
        final TrajectoryModified t = new TrajectoryModified(this.dimension);
        for (int i = 0; i < this.size(); ++i) {
            t.add(t.get(i));
        }
        return t;
    }
    
    public double[][] getPositionsAsArray() {
        final double[][] posAsArr = new double[this.size()][3];
        for (int i = 0; i < this.size(); ++i) {
            if (this.get(i) != null) {
                posAsArr[i][0] = this.get(i).x;
                posAsArr[i][1] = this.get(i).y;
                posAsArr[i][2] = this.get(i).z;
            }
            else {
                posAsArr[i] = null;
            }
        }
        return posAsArr;
    }
    
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); ++i) {
            result = String.valueOf(result) + " x: " + this.get(i).x + " y: " + this.get(i).y + " z: " + this.get(i).z + "\n";
        }
        return result;
    }
    
    @Override
    public boolean add(final Point3dModified e) {
        return super.add(e);
    }
    
    public void scale(final double v) {
        for (int i = 0; i < this.size(); ++i) {
            this.get(i).scale(v);
        }
    }
    
    public void offset(final double x, final double y, final double z) {
        for (int i = 0; i < this.size(); ++i) {
            this.get(i).add(new Point3dModified(x, y, z));
        }
    }
    
    public boolean add(final double x, final double y, final double z) {
        return super.add(new Point3dModified(x, y, z));
    }
    
    public int getDimension() {
        return this.dimension;
    }
    
    public void setDimension(final int dimension) {
        this.dimension = dimension;
    }
    
    public long getID() {
        return this.id;
    }
    
    public void setID(final int id) {
        this.id = id;
    }
    
    public int getRelativeStartTimepoint() {
        return this.relativeStartTimepoint;
    }
    
    public void setRelativStartTimepoint(final int timepoint) {
        this.relativeStartTimepoint = timepoint;
    }
    
    public void setType(final String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    
    public static void restIDCounter() {
        TrajectoryModified.idCounter = 1L;
    }
}
