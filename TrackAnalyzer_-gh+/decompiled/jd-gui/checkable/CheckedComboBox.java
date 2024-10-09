/*    */ package checkable;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.accessibility.Accessible;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.ComboBoxModel;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.KeyStroke;
/*    */ import javax.swing.ListCellRenderer;
/*    */ import javax.swing.plaf.basic.ComboPopup;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CheckedComboBox<E extends CheckableItem>
/*    */   extends JComboBox<E>
/*    */ {
/*    */   private boolean keepOpen;
/*    */   private transient ActionListener listener;
/*    */   
/*    */   public CheckedComboBox() {}
/*    */   
/*    */   public CheckedComboBox(ComboBoxModel<E> model) {
/* 27 */     super(model);
/*    */   }
/*    */ 
/*    */   
/*    */   public Dimension getPreferredSize() {
/* 32 */     return new Dimension(200, 20);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUI() {
/* 37 */     setRenderer((ListCellRenderer<? super E>)null);
/* 38 */     removeActionListener(this.listener);
/* 39 */     super.updateUI();
/* 40 */     this.listener = (e -> {
/*    */         if ((e.getModifiers() & 0x10L) != 0L) {
/*    */           updateItem(getSelectedIndex());
/*    */           this.keepOpen = true;
/*    */         } 
/*    */       });
/* 46 */     setRenderer(new CheckBoxCellRenderer<>());
/* 47 */     addActionListener(this.listener);
/* 48 */     getInputMap(0).put(KeyStroke.getKeyStroke(32, 0), "checkbox-select");
/* 49 */     getActionMap().put("checkbox-select", new AbstractAction()
/*    */         {
/*    */           public void actionPerformed(ActionEvent e) {
/* 52 */             Accessible a = CheckedComboBox.this.getAccessibleContext().getAccessibleChild(0);
/* 53 */             if (a instanceof ComboPopup) {
/* 54 */               CheckedComboBox.this.updateItem(((ComboPopup)a).getList().getSelectedIndex());
/*    */             }
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void updateItem(int index) {
/* 61 */     if (isPopupVisible()) {
/* 62 */       CheckableItem checkableItem = (CheckableItem)getItemAt(index);
/* 63 */       checkableItem.setSelected(!checkableItem.isSelected());
/* 64 */       setSelectedIndex(-1);
/* 65 */       setSelectedItem(checkableItem);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPopupVisible(boolean v) {
/* 71 */     if (this.keepOpen) {
/* 72 */       this.keepOpen = false;
/*    */     } else {
/* 74 */       super.setPopupVisible(v);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/checkable/CheckedComboBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */