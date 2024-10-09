/*     */ package vecmath;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Point3dModified
/*     */   extends Tuple3dModified
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 5718062286069042927L;
/*     */   
/*     */   public Point3dModified(double x, double y, double z) {
/*  48 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3dModified(double[] p) {
/*  58 */     super(p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3dModified(Point3dModified p1) {
/*  68 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3dModified(Point3f p1) {
/*  78 */     super(p1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3dModified(Tuple3f t1) {
/*  88 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3dModified(Tuple3dModified t1) {
/*  98 */     super(t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3dModified() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceSquared(Point3dModified p1) {
/* 120 */     double dx = this.x - p1.x;
/* 121 */     double dy = this.y - p1.y;
/* 122 */     double dz = this.z - p1.z;
/* 123 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distance(Point3dModified p1) {
/* 136 */     double dx = this.x - p1.x;
/* 137 */     double dy = this.y - p1.y;
/* 138 */     double dz = this.z - p1.z;
/* 139 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceL1(Point3dModified p1) {
/* 151 */     return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + 
/* 152 */       Math.abs(this.z - p1.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double distanceLinf(Point3dModified p1) {
/* 165 */     double tmp = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
/*     */     
/* 167 */     return Math.max(tmp, Math.abs(this.z - p1.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void project(Point4d p1) {
/* 180 */     double oneOw = 1.0D / p1.w;
/* 181 */     this.x = p1.x * oneOw;
/* 182 */     this.y = p1.y * oneOw;
/* 183 */     this.z = p1.z * oneOw;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/vecmath/Point3dModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */