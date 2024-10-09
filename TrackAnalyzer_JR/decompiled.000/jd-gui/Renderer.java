/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.DefaultTableCellRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Renderer
/*    */   extends DefaultTableCellRenderer
/*    */ {
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 18 */     Component comp = getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
/* 19 */     value = table.getModel().getValueAt(row, 0);
/*    */     
/* 21 */     if (value.equals(Boolean.valueOf(true))) {
/* 22 */       comp.setBackground(Color.LIGHT_GRAY);
/*    */     } else {
/*    */       
/* 25 */       comp.setForeground(Color.white);
/*    */     } 
/*    */ 
/*    */     
/* 29 */     return comp;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/Renderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */