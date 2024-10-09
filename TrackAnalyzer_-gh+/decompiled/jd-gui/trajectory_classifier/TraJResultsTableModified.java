/*    */ package trajectory_classifier;
/*    */ 
/*    */ import ij.measure.ResultsTable;
/*    */ 
/*    */ public class TraJResultsTableModified
/*    */   extends ResultsTable {
/*    */   boolean isParentTable;
/*    */   
/*    */   public TraJResultsTableModified() {
/* 10 */     this.isParentTable = false;
/*    */   }
/*    */   
/*    */   public TraJResultsTableModified(boolean isParentTable) {
/* 14 */     this.isParentTable = isParentTable;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/trajectory_classifier/TraJResultsTableModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */