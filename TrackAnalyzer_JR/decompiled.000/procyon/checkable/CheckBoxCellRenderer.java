// 
// Decompiled by Procyon v0.5.36
// 

package checkable;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import javax.swing.ListModel;
import java.util.Objects;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;

class CheckBoxCellRenderer<E extends CheckableItem> implements ListCellRenderer<E>
{
    private final JLabel label;
    private final JCheckBox check;
    
    CheckBoxCellRenderer() {
        this.label = new JLabel(" ");
        this.check = new JCheckBox(" ");
    }
    
    @Override
    public Component getListCellRendererComponent(final JList<? extends E> list, final E value, final int index, final boolean isSelected, final boolean cellHasFocus) {
        if (index < 0) {
            final String txt = this.<? extends E>getCheckedItemString(list.getModel());
            this.label.setText(txt.isEmpty() ? " " : txt);
            return this.label;
        }
        this.check.setText(Objects.toString(value, ""));
        this.check.setSelected(value.isSelected());
        if (isSelected) {
            this.check.setBackground(list.getSelectionBackground());
            this.check.setForeground(list.getSelectionForeground());
        }
        else {
            this.check.setBackground(list.getBackground());
            this.check.setForeground(list.getForeground());
        }
        return this.check;
    }
    
    private <E extends CheckableItem> String getCheckedItemString(final ListModel<E> model) {
        return IntStream.range(0, model.getSize()).<Object>mapToObj((IntFunction<?>)model::getElementAt).filter(CheckableItem::isSelected).<Object>map((Function<? super Object, ?>)Objects::toString).sorted().<String, ?>collect((Collector<? super Object, ?, String>)Collectors.joining(", "));
    }
}
