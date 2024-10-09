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
/*    */ public enum TransposeModified
/*    */ {
/* 22 */   NO_TRANSPOSE(
/* 23 */     111, (byte)78),
/* 24 */   TRANSPOSE(
/* 25 */     112, (byte)84),
/* 26 */   CONJUGATE_TRANSPOSE(
/* 27 */     113, (byte)67);
/*    */ 
/*    */   
/*    */   private final int blas;
/*    */   
/*    */   private final byte lapack;
/*    */ 
/*    */   
/*    */   TransposeModified(int blas, byte lapack) {
/* 36 */     this.blas = blas;
/* 37 */     this.lapack = lapack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int blas() {
/* 44 */     return this.blas;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte lapack() {
/* 50 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/TransposeModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */