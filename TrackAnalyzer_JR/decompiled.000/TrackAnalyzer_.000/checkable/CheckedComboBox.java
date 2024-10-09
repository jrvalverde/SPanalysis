package checkable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.accessibility.Accessible;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.ComboPopup;

public class CheckedComboBox<E extends CheckableItem> extends JComboBox<E> {
   private boolean keepOpen;
   private transient ActionListener listener;

   public CheckedComboBox() {
   }

   public CheckedComboBox(ComboBoxModel<E> model) {
      super(model);
   }

   public Dimension getPreferredSize() {
      return new Dimension(200, 20);
   }

   public void updateUI() {
      this.setRenderer((ListCellRenderer)null);
      this.removeActionListener(this.listener);
      super.updateUI();
      this.listener = (e) -> {
         if (((long)e.getModifiers() & 16L) != 0L) {
            this.updateItem(this.getSelectedIndex());
            this.keepOpen = true;
         }

      };
      this.setRenderer(new CheckBoxCellRenderer());
      this.addActionListener(this.listener);
      this.getInputMap(0).put(KeyStroke.getKeyStroke(32, 0), "checkbox-select");
      this.getActionMap().put("checkbox-select", new AbstractAction() {
         public void actionPerformed(ActionEvent e) {
            Accessible a = CheckedComboBox.this.getAccessibleContext().getAccessibleChild(0);
            if (a instanceof ComboPopup) {
               CheckedComboBox.this.updateItem(((ComboPopup)a).getList().getSelectedIndex());
            }

         }
      });
   }

   public void updateItem(int index) {
      if (this.isPopupVisible()) {
         E item = (CheckableItem)this.getItemAt(index);
         item.setSelected(!item.isSelected());
         this.setSelectedIndex(-1);
         this.setSelectedItem(item);
      }

   }

   public void setPopupVisible(boolean v) {
      if (this.keepOpen) {
         this.keepOpen = false;
      } else {
         super.setPopupVisible(v);
      }

   }
}
