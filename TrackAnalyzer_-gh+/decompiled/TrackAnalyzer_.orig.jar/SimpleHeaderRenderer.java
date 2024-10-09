import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class SimpleHeaderRenderer extends JLabel implements TableCellRenderer {
   public SimpleHeaderRenderer() {
      this.setFont(new Font("SansSerif", 1, 12));
      this.setForeground(Color.DARK_GRAY);
      this.setBorder(BorderFactory.createEtchedBorder());
      this.setBackground(new Color(230, 250, 240, 50));
      this.setHorizontalAlignment(0);
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      this.setText(value.toString());
      return this;
   }
}
