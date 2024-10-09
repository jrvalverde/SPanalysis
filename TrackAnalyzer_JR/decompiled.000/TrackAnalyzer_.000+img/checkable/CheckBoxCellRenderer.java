package checkable;

import java.awt.Component;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

class CheckBoxCellRenderer<E extends CheckableItem> implements ListCellRenderer<E> {
   private final JLabel label = new JLabel(" ");
   private final JCheckBox check = new JCheckBox(" ");

   public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected, boolean cellHasFocus) {
      if (index < 0) {
         String txt = this.getCheckedItemString(list.getModel());
         this.label.setText(txt.isEmpty() ? " " : txt);
         return this.label;
      } else {
         this.check.setText(Objects.toString(value, ""));
         this.check.setSelected(value.isSelected());
         if (isSelected) {
            this.check.setBackground(list.getSelectionBackground());
            this.check.setForeground(list.getSelectionForeground());
         } else {
            this.check.setBackground(list.getBackground());
            this.check.setForeground(list.getForeground());
         }

         return this.check;
      }
   }

   private <E extends CheckableItem> String getCheckedItemString(ListModel<E> model) {
      IntStream var10000 = IntStream.range(0, model.getSize());
      model.getClass();
      return (String)var10000.mapToObj(model::getElementAt).filter(CheckableItem::isSelected).map(Objects::toString).sorted().collect(Collectors.joining(", "));
   }
}
