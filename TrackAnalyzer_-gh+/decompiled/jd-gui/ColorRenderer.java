/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.border.Border;
/*    */ import javax.swing.table.TableCellRenderer;
/*    */ 
/*    */ 
/*    */ public class ColorRenderer
/*    */   extends JLabel
/*    */   implements TableCellRenderer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 15 */   Border unselectedBorder = null;
/* 16 */   Border selectedBorder = null;
/*    */   boolean isBordered = true;
/*    */   
/*    */   public ColorRenderer(boolean isBordered) {
/* 20 */     this.isBordered = isBordered;
/* 21 */     setOpaque(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
/* 26 */     Color newColor = (Color)color;
/* 27 */     setBackground(newColor);
/* 28 */     if (this.isBordered) {
/* 29 */       if (isSelected) {
/* 30 */         if (this.selectedBorder == null) {
/* 31 */           this.selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
/*    */         }
/* 33 */         setBorder(this.selectedBorder);
/*    */       } else {
/* 35 */         if (this.unselectedBorder == null) {
/* 36 */           this.unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
/*    */         }
/* 38 */         setBorder(this.unselectedBorder);
/*    */       } 
/*    */     }
/*    */     
/* 42 */     setToolTipText("RGB value: " + newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue());
/* 43 */     return this;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ColorRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */