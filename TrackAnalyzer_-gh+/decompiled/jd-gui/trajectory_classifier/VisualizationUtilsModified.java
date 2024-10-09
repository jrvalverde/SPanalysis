/*    */ package trajectory_classifier;
/*    */ 
/*    */ import ij.gui.PolygonRoi;
/*    */ import ij.gui.Roi;
/*    */ import ij.gui.TextRoi;
/*    */ import ij.process.FloatPolygon;
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VisualizationUtilsModified
/*    */ {
/*    */   public static ArrayList<Roi> generateVisualizationRoisFromTrack(SubtrajectoryModified t, Color c, boolean showID) {
/* 21 */     return generateVisualizationRoisFromTrack(t, c, showID, 1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ArrayList<Roi> generateVisualizationRoisFromTrack(SubtrajectoryModified t, Color c, boolean showID, double pixelsize) {
/* 27 */     ArrayList<Roi> proi = new ArrayList<>();
/* 28 */     FloatPolygon p = new FloatPolygon();
/* 29 */     double sumx = 0.0D;
/* 30 */     double sumy = 0.0D;
/* 31 */     TextRoi.setFont("TimesRoman", 5, 0);
/* 32 */     for (int i = 0; i < t.getParent().size(); i++) {
/* 33 */       int to = t.size();
/* 34 */       if (i < t.size()) {
/*    */         
/* 36 */         sumx += ((Point3dModified)t.get(i)).x / pixelsize;
/* 37 */         sumy += ((Point3dModified)t.get(i)).y / pixelsize;
/* 38 */         p.addPoint(((Point3dModified)t.get(i)).x / pixelsize, ((Point3dModified)t.get(i)).y / pixelsize);
/*    */         
/* 40 */         to = i + 1;
/*    */       } 
/*    */       
/* 43 */       PolygonRoi pr = new PolygonRoi(new FloatPolygon(p.xpoints, p.ypoints, to), 6);
/* 44 */       pr.setStrokeColor(c);
/* 45 */       pr.setPosition(t.getRelativeStartTimepoint() + i + 1);
/* 46 */       proi.add(pr);
/*    */       
/* 48 */       if (showID) {
/* 49 */         long parentID = t.getParent().getID();
/* 50 */         TextRoi troi = new TextRoi(sumx / to, sumy / to, " " + parentID + ":" + t.getID() + " ");
/* 51 */         troi.setPosition(t.getRelativeStartTimepoint() + i + 1);
/* 52 */         troi.setFillColor(Color.BLACK);
/* 53 */         troi.setStrokeColor(c);
/* 54 */         troi.setAntialiased(true);
/* 55 */         proi.add(troi);
/*    */       } 
/*    */     } 
/* 58 */     return proi;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/trajectory_classifier/VisualizationUtilsModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */