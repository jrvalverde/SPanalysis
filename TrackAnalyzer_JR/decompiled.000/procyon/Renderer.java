import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// 
// Decompiled by Procyon v0.5.36
// 

public class Renderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(final JTable table, Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        final Component comp = this.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        value = table.getModel().getValueAt(row, 0);
        if (value.equals(true)) {
            comp.setBackground(Color.LIGHT_GRAY);
        }
        else {
            comp.setForeground(Color.white);
        }
        return comp;
    }
}
