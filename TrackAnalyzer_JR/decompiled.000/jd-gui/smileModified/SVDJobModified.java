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
/*    */ public enum SVDJobModified
/*    */ {
/* 22 */   ALL((byte)
/* 23 */     65),
/* 24 */   COMPACT((byte)
/* 25 */     83),
/* 26 */   OVERWRITE((byte)
/* 27 */     79),
/* 28 */   NO_VECTORS((byte)
/* 29 */     78);
/*    */ 
/*    */   
/*    */   private final byte lapack;
/*    */ 
/*    */   
/*    */   SVDJobModified(byte lapack) {
/* 36 */     this.lapack = lapack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte lapack() {
/* 43 */     return this.lapack;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/SVDJobModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */