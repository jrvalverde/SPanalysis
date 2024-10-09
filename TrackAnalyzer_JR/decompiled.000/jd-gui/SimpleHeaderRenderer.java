/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Font;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ 
/*    */ public class SimpleHeaderRenderer
/*    */   extends JLabel
/*    */   implements TableCellRenderer
/*    */ {
/*    */   public SimpleHeaderRenderer() {
/* 14 */     setFont(new Font("SansSerif", 1, 12));
/* 15 */     setForeground(Color.DARK_GRAY);
/* 16 */     setBorder(BorderFactory.createEtchedBorder());
/* 17 */     setBackground(new Color(230, 250, 240, 50));
/* 18 */     setHorizontalAlignment(0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/* 24 */     setText(value.toString());
/* 25 */     return this;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/SimpleHeaderRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */