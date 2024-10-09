package traJ;

import features.AbstractTrajectoryFeatureModified;
import java.util.ArrayList;
import vecmath.Point3dModified;

public class TrajectoryModified extends ArrayList<Point3dModified> {
   private static final long serialVersionUID = 1L;
   private int dimension;
   private int relativeStartTimepoint;
   private long id;
   private static long idCounter = 1L;
   private String type = "";
   private ArrayList<AbstractTrajectoryFeatureModified> features;

   public TrajectoryModified(int dimension) {
      this.dimension = dimension;
      this.relativeStartTimepoint = 0;
      this.id = (long)(idCounter++);
      this.features = new ArrayList();
   }

   public TrajectoryModified(int dimension, int relativeStartTimepoint) {
      this.dimension = dimension;
      this.relativeStartTimepoint = relativeStartTimepoint;
      this.id = (long)(idCounter++);
      this.features = new ArrayList();
   }

   public TrajectoryModified() {
      this.relativeStartTimepoint = 0;
      this.features = new ArrayList();
   }

   public TrajectoryModified subList(int fromIndex, int toIndex) {
      TrajectoryModified t = new TrajectoryModified(this.dimension);

      for(int i = fromIndex; i < toIndex; ++i) {
         t.add((Point3dModified)this.get(i));
      }

      return t;
   }

   public ArrayList<AbstractTrajectoryFeatureModified> getFeatures() {
      return this.features;
   }

   public void addFeature(AbstractTrajectoryFeatureModified feature) {
      this.features.add(feature);
   }

   public TrajectoryModified getCopy() {
      TrajectoryModified t = new TrajectoryModified(this.dimension);

      for(int i = 0; i < this.size(); ++i) {
         t.add((Point3dModified)t.get(i));
      }

      return t;
   }

   public double[][] getPositionsAsArray() {
      double[][] posAsArr = new double[this.size()][3];

      for(int i = 0; i < this.size(); ++i) {
         if (this.get(i) != null) {
            posAsArr[i][0] = ((Point3dModified)this.get(i)).x;
            posAsArr[i][1] = ((Point3dModified)this.get(i)).y;
            posAsArr[i][2] = ((Point3dModified)this.get(i)).z;
         } else {
            posAsArr[i] = null;
         }
      }

      return posAsArr;
   }

   public String toString() {
      String result = "";

      for(int i = 0; i < this.size(); ++i) {
         result = result + " x: " + ((Point3dModified)this.get(i)).x + " y: " + ((Point3dModified)this.get(i)).y + " z: " + ((Point3dModified)this.get(i)).z + "\n";
      }

      return result;
   }

   public boolean add(Point3dModified e) {
      return super.add(e);
   }

   public void scale(double v) {
      for(int i = 0; i < this.size(); ++i) {
         ((Point3dModified)this.get(i)).scale(v);
      }

   }

   public void offset(double x, double y, double z) {
      for(int i = 0; i < this.size(); ++i) {
         ((Point3dModified)this.get(i)).add(new Point3dModified(x, y, z));
      }

   }

   public boolean add(double x, double y, double z) {
      return super.add(new Point3dModified(x, y, z));
   }

   public int getDimension() {
      return this.dimension;
   }

   public void setDimension(int dimension) {
      this.dimension = dimension;
   }

   public long getID() {
      return this.id;
   }

   public void setID(int id) {
      this.id = (long)id;
   }

   public int getRelativeStartTimepoint() {
      return this.relativeStartTimepoint;
   }

   public void setRelativStartTimepoint(int timepoint) {
      this.relativeStartTimepoint = timepoint;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getType() {
      return this.type;
   }

   public static void restIDCounter() {
      idCounter = 1L;
   }
}
