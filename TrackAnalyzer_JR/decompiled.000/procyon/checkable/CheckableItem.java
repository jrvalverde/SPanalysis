// 
// Decompiled by Procyon v0.5.36
// 

package checkable;

public class CheckableItem
{
    public final String text;
    private boolean selected;
    
    public CheckableItem(final String text, final boolean selected) {
        this.text = text;
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
    
    @Override
    public String toString() {
        return this.text;
    }
}
