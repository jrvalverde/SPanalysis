/*     */ package traJ;
/*     */ 
/*     */ import features.AbstractTrajectoryFeatureModified;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import vecmath.Point3dModified;
/*     */ import vecmath.Tuple3dModified;
/*     */ 
/*     */ public class TrajectoryModified
/*     */   extends ArrayList<Point3dModified> {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private int dimension;
/*     */   private int relativeStartTimepoint;
/*     */   private long id;
/*  15 */   private static long idCounter = 1L;
/*  16 */   private String type = "";
/*     */   private ArrayList<AbstractTrajectoryFeatureModified> features;
/*     */   
/*     */   public TrajectoryModified(int dimension) {
/*  20 */     this.dimension = dimension;
/*  21 */     this.relativeStartTimepoint = 0;
/*  22 */     this.id = idCounter++;
/*  23 */     this.features = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public TrajectoryModified(int dimension, int relativeStartTimepoint) {
/*  28 */     this.dimension = dimension;
/*  29 */     this.relativeStartTimepoint = relativeStartTimepoint;
/*  30 */     this.id = idCounter++;
/*  31 */     this.features = new ArrayList<>();
/*     */   }
/*     */   
/*     */   public TrajectoryModified() {
/*  35 */     this.relativeStartTimepoint = 0;
/*  36 */     this.features = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public TrajectoryModified subList(int fromIndex, int toIndex) {
/*  41 */     TrajectoryModified t = new TrajectoryModified(this.dimension);
/*     */     
/*  43 */     for (int i = fromIndex; i < toIndex; i++) {
/*  44 */       t.add(get(i));
/*     */     }
/*  46 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<AbstractTrajectoryFeatureModified> getFeatures() {
/*  51 */     return this.features;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFeature(AbstractTrajectoryFeatureModified feature) {
/*  57 */     this.features.add(feature);
/*     */   }
/*     */   
/*     */   public TrajectoryModified getCopy() {
/*  61 */     TrajectoryModified t = new TrajectoryModified(this.dimension);
/*  62 */     for (int i = 0; i < size(); i++) {
/*  63 */       t.add(t.get(i));
/*     */     }
/*  65 */     return t;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[][] getPositionsAsArray() {
/*  70 */     double[][] posAsArr = new double[size()][3];
/*  71 */     for (int i = 0; i < size(); i++) {
/*  72 */       if (get(i) != null) {
/*  73 */         posAsArr[i][0] = (get(i)).x;
/*  74 */         posAsArr[i][1] = (get(i)).y;
/*  75 */         posAsArr[i][2] = (get(i)).z;
/*     */       } else {
/*  77 */         posAsArr[i] = null;
/*     */       } 
/*     */     } 
/*  80 */     return posAsArr;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  85 */     String result = "";
/*  86 */     for (int i = 0; i < size(); i++) {
/*  87 */       result = String.valueOf(result) + " x: " + (get(i)).x + " y: " + (get(i)).y + " z: " + (get(i)).z + "\n";
/*     */     }
/*  89 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(Point3dModified e) {
/*  94 */     return super.add(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public void scale(double v) {
/*  99 */     for (int i = 0; i < size(); i++) {
/* 100 */       get(i).scale(v);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void offset(double x, double y, double z) {
/* 107 */     for (int i = 0; i < size(); i++) {
/* 108 */       get(i).add((Tuple3dModified)new Point3dModified(x, y, z));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(double x, double y, double z) {
/* 114 */     return super.add(new Point3dModified(x, y, z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 120 */     return this.dimension;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDimension(int dimension) {
/* 125 */     this.dimension = dimension;
/*     */   }
/*     */   
/*     */   public long getID() {
/* 129 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setID(int id) {
/* 134 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRelativeStartTimepoint() {
/* 139 */     return this.relativeStartTimepoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRelativStartTimepoint(int timepoint) {
/* 144 */     this.relativeStartTimepoint = timepoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setType(String type) {
/* 149 */     this.type = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 154 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void restIDCounter() {
/* 159 */     idCounter = 1L;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/traJ/TrajectoryModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */