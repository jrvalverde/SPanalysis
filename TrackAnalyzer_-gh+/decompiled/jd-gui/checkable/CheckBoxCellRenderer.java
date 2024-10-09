/*    */ package checkable;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.IntStream;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.ListCellRenderer;
/*    */ import javax.swing.ListModel;
/*    */ 
/*    */ class CheckBoxCellRenderer<E extends CheckableItem>
/*    */   implements ListCellRenderer<E> {
/* 15 */   private final JLabel label = new JLabel(" ");
/* 16 */   private final JCheckBox check = new JCheckBox(" ");
/*    */ 
/*    */ 
/*    */   
/*    */   public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
/* 21 */     if (index < 0) {
/* 22 */       String txt = getCheckedItemString((ListModel)list.getModel());
/* 23 */       this.label.setText(txt.isEmpty() ? " " : txt);
/* 24 */       return this.label;
/*    */     } 
/* 26 */     this.check.setText(Objects.toString(value, ""));
/* 27 */     this.check.setSelected(value.isSelected());
/* 28 */     if (isSelected) {
/* 29 */       this.check.setBackground(list.getSelectionBackground());
/* 30 */       this.check.setForeground(list.getSelectionForeground());
/*    */     } else {
/* 32 */       this.check.setBackground(list.getBackground());
/* 33 */       this.check.setForeground(list.getForeground());
/*    */     } 
/* 35 */     return this.check;
/*    */   }
/*    */ 
/*    */   
/*    */   private <E extends CheckableItem> String getCheckedItemString(ListModel<E> model) {
/* 40 */     return IntStream.range(0, model.getSize()).mapToObj(model::getElementAt).filter(CheckableItem::isSelected)
/* 41 */       .map(Objects::toString).sorted().collect(Collectors.joining(", "));
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/checkable/CheckBoxCellRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */