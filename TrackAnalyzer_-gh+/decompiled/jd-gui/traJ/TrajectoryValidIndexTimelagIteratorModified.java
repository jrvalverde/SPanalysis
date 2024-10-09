/*    */ package traJ;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrajectoryValidIndexTimelagIteratorModified
/*    */   implements Iterator<Integer>
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   private int timelag;
/*    */   boolean overlap = true;
/*    */   int currentIndex;
/*    */   
/*    */   public TrajectoryValidIndexTimelagIteratorModified(TrajectoryModified t, int timelag) {
/* 22 */     this.t = t;
/* 23 */     this.timelag = timelag;
/* 24 */     this.overlap = true;
/* 25 */     this.currentIndex = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TrajectoryValidIndexTimelagIteratorModified(TrajectoryModified t, int timelag, boolean overlap) {
/* 34 */     this.t = t;
/* 35 */     this.timelag = timelag;
/* 36 */     this.overlap = overlap;
/* 37 */     this.currentIndex = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 42 */     for (int i = this.currentIndex; i < this.t.size(); i++) {
/* 43 */       if (i + this.timelag >= this.t.size()) {
/* 44 */         return false;
/*    */       }
/* 46 */       if (this.t.get(i) != null && this.t.get(i + this.timelag) != null) {
/* 47 */         return true;
/*    */       }
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer next() {
/* 57 */     for (int i = this.currentIndex; i < this.t.size(); i++) {
/* 58 */       if (i + this.timelag >= this.t.size()) {
/* 59 */         return null;
/*    */       }
/* 61 */       if (this.t.get(i) != null && this.t.get(i + this.timelag) != null) {
/* 62 */         if (this.overlap) {
/* 63 */           this.currentIndex = i + 1;
/*    */         } else {
/*    */           
/* 66 */           this.currentIndex = i + this.timelag;
/*    */         } 
/* 68 */         return Integer.valueOf(i);
/*    */       } 
/*    */     } 
/*    */     
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public void remove() {}
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/traJ/TrajectoryValidIndexTimelagIteratorModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */