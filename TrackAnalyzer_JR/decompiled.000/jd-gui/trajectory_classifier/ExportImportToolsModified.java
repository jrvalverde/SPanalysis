/*    */ package trajectory_classifier;
/*    */ 
/*    */ import com.opencsv.CSVReader;
/*    */ import com.opencsv.CSVWriter;
/*    */ import java.io.FileReader;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExportImportToolsModified
/*    */ {
/*    */   public void exportTrajectoryDataAsCSV(ArrayList<? extends TrajectoryModified> tracks, String path) {
/* 18 */     String[] nextLine = null;
/*    */     try {
/* 20 */       CSVWriter writer = new CSVWriter(new FileWriter(path, false));
/* 21 */       nextLine = new String[] { "ID", "X", "Y", "CLASS" };
/* 22 */       writer.writeNext(nextLine);
/*    */       
/* 24 */       for (int i = 0; i < tracks.size(); i++) {
/* 25 */         TrajectoryModified t = tracks.get(i);
/* 26 */         for (int j = 0; j < t.size(); j++) {
/* 27 */           nextLine = new String[] { t.getID(), ((Point3dModified)t.get(j)).x, ((Point3dModified)t.get(j)).y, t.getType() };
/* 28 */           writer.writeNext(nextLine);
/*    */         } 
/*    */       } 
/* 31 */       writer.close();
/*    */     }
/* 33 */     catch (IOException e) {
/*    */       
/* 35 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public ArrayList<TrajectoryModified> importTrajectoryDataFromCSV(String path) {
/* 40 */     ArrayList<TrajectoryModified> tracks = new ArrayList<>();
/*    */     try {
/* 42 */       CSVReader reader = new CSVReader(new FileReader(path));
/*    */       
/* 44 */       reader.readNext();
/* 45 */       TrajectoryModified t = null;
/* 46 */       int lastID = -1; String[] nextLine;
/* 47 */       while ((nextLine = reader.readNext()) != null) {
/* 48 */         int nextID = Integer.parseInt(nextLine[0]);
/* 49 */         double nextX = Double.parseDouble(nextLine[1]);
/* 50 */         double nextY = Double.parseDouble(nextLine[2]);
/* 51 */         String nextClass = nextLine[3];
/* 52 */         if (nextID == lastID) {
/* 53 */           System.out.println();
/* 54 */           t.add(nextX, nextY, 0.0D);
/* 55 */           lastID = nextID; continue;
/*    */         } 
/* 57 */         if (t != null) {
/* 58 */           tracks.add(t);
/*    */         }
/* 60 */         t = new TrajectoryModified(2);
/* 61 */         t.setID(nextID);
/* 62 */         t.setType(nextClass);
/* 63 */         t.add(nextX, nextY, 0.0D);
/* 64 */         lastID = nextID;
/*    */       } 
/*    */       
/* 67 */       tracks.add(t);
/* 68 */       reader.close();
/*    */     
/*    */     }
/* 71 */     catch (IOException e) {
/*    */       
/* 73 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 76 */     return tracks;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/trajectory_classifier/ExportImportToolsModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */