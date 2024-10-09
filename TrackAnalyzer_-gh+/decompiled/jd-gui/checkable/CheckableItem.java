/*    */ package checkable;
/*    */ 
/*    */ public class CheckableItem {
/*    */   public final String text;
/*    */   private boolean selected;
/*    */   
/*    */   public CheckableItem(String text, boolean selected) {
/*  8 */     this.text = text;
/*  9 */     this.selected = selected;
/*    */   }
/*    */   
/*    */   public boolean isSelected() {
/* 13 */     return this.selected;
/*    */   }
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 17 */     this.selected = selected;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return this.text;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/checkable/CheckableItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */