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
/*    */ public class IntPair
/*    */ {
/*    */   public final int i;
/*    */   public final int j;
/*    */   
/*    */   public IntPair(int i, int j) {
/* 17 */     this.i = i;
/* 18 */     this.j = j;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 23 */     return this.i * 31 + this.j;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return String.format("(%d, %d)", new Object[] { Integer.valueOf(this.i), Integer.valueOf(this.j) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 33 */     if (o instanceof IntPair) {
/* 34 */       IntPair p = (IntPair)o;
/* 35 */       return (this.i == p.i && this.j == p.j);
/*    */     } 
/*    */     
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/IntPair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */