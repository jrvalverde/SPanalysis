package vecmath;

import java.io.Serializable;

public class Point3dModified extends Tuple3dModified implements Serializable {
   static final long serialVersionUID = 5718062286069042927L;

   public Point3dModified(double x, double y, double z) {
      super(x, y, z);
   }

   public Point3dModified(double[] p) {
      super(p);
   }

   public Point3dModified(Point3dModified p1) {
      super((Tuple3dModified)p1);
   }

   public Point3dModified(Point3f p1) {
      super((Tuple3f)p1);
   }

   public Point3dModified(Tuple3f t1) {
      super(t1);
   }

   public Point3dModified(Tuple3dModified t1) {
      super(t1);
   }

   public Point3dModified() {
   }

   public final double distanceSquared(Point3dModified p1) {
      double dx = this.x - p1.x;
      double dy = this.y - p1.y;
      double dz = this.z - p1.z;
      return dx * dx + dy * dy + dz * dz;
   }

   public double distance(Point3dModified p1) {
      double dx = this.x - p1.x;
      double dy = this.y - p1.y;
      double dz = this.z - p1.z;
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
   }

   public final double distanceL1(Point3dModified p1) {
      return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + Math.abs(this.z - p1.z);
   }

   public final double distanceLinf(Point3dModified p1) {
      double tmp = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
      return Math.max(tmp, Math.abs(this.z - p1.z));
   }

   public final void project(Point4d p1) {
      double oneOw = 1.0D / p1.w;
      this.x = p1.x * oneOw;
      this.y = p1.y * oneOw;
      this.z = p1.z * oneOw;
   }
}
