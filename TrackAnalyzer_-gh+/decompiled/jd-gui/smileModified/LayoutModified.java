/*    */ package smileModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum LayoutModified
/*    */ {
/* 22 */   ROW_MAJOR(
/* 23 */     101),
/* 24 */   COL_MAJOR(
/* 25 */     102);
/*    */ 
/*    */   
/*    */   private final int blas;
/*    */   
/*    */   private final int lapack;
/*    */ 
/*    */   
/*    */   LayoutModified(int value) {
/* 34 */     this.blas = value;
/* 35 */     this.lapack = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int blas() {
/* 42 */     return this.blas;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int lapack() {
/* 48 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/LayoutModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */