// 
// Decompiled by Procyon v0.5.36
// 

package checkable;

import javax.swing.Action;
import javax.accessibility.Accessible;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import java.awt.Dimension;
import javax.swing.ComboBoxModel;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public class CheckedComboBox<E extends CheckableItem> extends JComboBox<E>
{
    private boolean keepOpen;
    private transient ActionListener listener;
    
    public CheckedComboBox() {
    }
    
    public CheckedComboBox(final ComboBoxModel<E> model) {
        super(model);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 20);
    }
    
    @Override
    public void updateUI() {
        this.setRenderer(null);
        this.removeActionListener(this.listener);
        super.updateUI();
        this.listener = (e -> {
            if (((long)e.getModifiers() & 0x10L) != 0x0L) {
                this.updateItem(this.getSelectedIndex());
                this.keepOpen = true;
            }
            return;
        });
        this.setRenderer(new CheckBoxCellRenderer<Object>());
        this.addActionListener(this.listener);
        this.getInputMap(0).put(KeyStroke.getKeyStroke(32, 0), "checkbox-select");
        this.getActionMap().put("checkbox-select", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Accessible a = CheckedComboBox.this.getAccessibleContext().getAccessibleChild(0);
                if (a instanceof ComboPopup) {
                    CheckedComboBox.this.updateItem(((ComboPopup)a).getList().getSelectedIndex());
                }
            }
        });
    }
    
    public void updateItem(final int index) {
        if (this.isPopupVisible()) {
            final E item = this.getItemAt(index);
            item.setSelected(!item.isSelected());
            this.setSelectedIndex(-1);
            this.setSelectedItem(item);
        }
    }
    
    @Override
    public void setPopupVisible(final boolean v) {
        if (this.keepOpen) {
            this.keepOpen = false;
        }
        else {
            super.setPopupVisible(v);
        }
    }
}
