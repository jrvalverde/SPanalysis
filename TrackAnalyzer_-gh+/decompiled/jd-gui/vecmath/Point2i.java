/*    */ package vecmath;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class Point2i
/*    */   extends Tuple2i
/*    */   implements Serializable
/*    */ {
/*    */   static final long serialVersionUID = 9208072376494084954L;
/*    */   
/*    */   public Point2i(int x, int y) {
/* 47 */     super(x, y);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point2i(int[] t) {
/* 56 */     super(t);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point2i(Tuple2i t1) {
/* 66 */     super(t1);
/*    */   }
/*    */   
/*    */   public Point2i() {}
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/vecmath/Point2i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */