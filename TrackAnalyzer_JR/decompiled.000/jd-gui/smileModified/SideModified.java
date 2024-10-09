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
/*    */ public enum SideModified
/*    */ {
/* 25 */   LEFT(
/* 26 */     141, (byte)76),
/* 27 */   RIGHT(
/* 28 */     142, (byte)82);
/*    */ 
/*    */   
/*    */   private final int blas;
/*    */   
/*    */   private final byte lapack;
/*    */ 
/*    */   
/*    */   SideModified(int blas, byte lapack) {
/* 37 */     this.blas = blas;
/* 38 */     this.lapack = lapack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int blas() {
/* 45 */     return this.blas;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public byte lapack() {
/* 51 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/SideModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */