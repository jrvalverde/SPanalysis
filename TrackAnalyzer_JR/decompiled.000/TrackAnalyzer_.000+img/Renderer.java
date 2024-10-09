import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer {
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      Component comp = this.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      value = table.getModel().getValueAt(row, 0);
      if (value.equals(true)) {
         comp.setBackground(Color.LIGHT_GRAY);
      } else {
         comp.setForeground(Color.white);
      }

      return comp;
   }
}
