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
/*    */ public enum UPLOModified
/*    */ {
/* 28 */   UPPER(
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 35 */     121, (byte)85),
/* 36 */   LOWER(
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 43 */     122, (byte)76);
/*    */ 
/*    */   
/*    */   private final int blas;
/*    */   
/*    */   private final byte lapack;
/*    */ 
/*    */   
/*    */   UPLOModified(int blas, byte lapack) {
/* 52 */     this.blas = blas;
/* 53 */     this.lapack = lapack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int blas() {
/* 60 */     return this.blas;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte lapack() {
/* 66 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/UPLOModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */