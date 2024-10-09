import java.awt.Component;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;

// 
// Decompiled by Procyon v0.5.36
// 

public class SimpleHeaderRenderer extends JLabel implements TableCellRenderer
{
    public SimpleHeaderRenderer() {
        this.setFont(new Font("SansSerif", 1, 12));
        this.setForeground(Color.DARK_GRAY);
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setBackground(new Color(230, 250, 240, 50));
        this.setHorizontalAlignment(0);
    }
    
    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
        this.setText(value.toString());
        return this;
    }
}
