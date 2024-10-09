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
/*    */ public enum EVDJobModified
/*    */ {
/* 22 */   NO_VECTORS((byte)
/* 23 */     78),
/* 24 */   VECTORS((byte)
/* 25 */     86);
/*    */ 
/*    */   
/*    */   private final byte lapack;
/*    */ 
/*    */   
/*    */   EVDJobModified(byte lapack) {
/* 32 */     this.lapack = lapack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte lapack() {
/* 39 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/EVDJobModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */