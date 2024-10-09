/*    */ package trajectory_classifier;public class FeatureWorkerModified extends Thread { double[] result;
/*    */   AbstractTrajectoryFeatureModified c;
/*    */   EVALTYPE ev;
/*    */   int resIndex;
/*    */   
/*  6 */   enum EVALTYPE { FIRST, SECOND, RATIO_01, RATIO_10, RATIO_12; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FeatureWorkerModified(double[] result, int resIndex, AbstractTrajectoryFeatureModified c, EVALTYPE ev) {
/* 14 */     this.result = result;
/* 15 */     this.c = c;
/* 16 */     this.ev = ev;
/* 17 */     this.resIndex = resIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     double[] res;
/* 23 */     switch (this.ev) {
/*    */       case null:
/* 25 */         res = this.c.getValue();
/* 26 */         this.result[this.resIndex] = res[0];
/*    */         break;
/*    */       case SECOND:
/* 29 */         res = this.c.getValue();
/* 30 */         this.result[this.resIndex] = res[1];
/*    */         break;
/*    */       case RATIO_01:
/* 33 */         res = this.c.getValue();
/* 34 */         this.result[this.resIndex] = res[0] / res[1];
/*    */         break;
/*    */       case RATIO_10:
/* 37 */         res = this.c.getValue();
/* 38 */         this.result[this.resIndex] = res[1] / res[0];
/*    */         break;
/*    */       case RATIO_12:
/* 41 */         res = this.c.getValue();
/* 42 */         this.result[this.resIndex] = res[1] / res[2];
/*    */         break;
/*    */     } 
/*    */   } }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/trajectory_classifier/FeatureWorkerModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */