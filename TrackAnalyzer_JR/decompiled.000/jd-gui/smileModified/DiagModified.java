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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DiagModified
/*    */ {
/* 30 */   NON_UNIT(
/* 31 */     131, (byte)78),
/* 32 */   UNIT(
/* 33 */     132, (byte)85);
/*    */ 
/*    */ 
/*    */   
/*    */   private final int blas;
/*    */ 
/*    */ 
/*    */   
/*    */   private final byte lapack;
/*    */ 
/*    */ 
/*    */   
/*    */   DiagModified(int blas, byte lapack) {
/* 46 */     this.blas = blas;
/* 47 */     this.lapack = lapack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int blas() {
/* 54 */     return this.blas;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte lapack() {
/* 60 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/DiagModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */