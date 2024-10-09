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
/*    */ public enum EigenRangeModified
/*    */ {
/* 22 */   ALL((byte)
/*    */ 
/*    */     
/* 25 */     65),
/* 26 */   VALUE((byte)
/*    */ 
/*    */ 
/*    */     
/* 30 */     86),
/* 31 */   INDEX((byte)
/*    */ 
/*    */     
/* 34 */     73);
/*    */ 
/*    */   
/*    */   private final byte lapack;
/*    */ 
/*    */   
/*    */   EigenRangeModified(byte lapack) {
/* 41 */     this.lapack = lapack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte lapack() {
/* 48 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/EigenRangeModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */