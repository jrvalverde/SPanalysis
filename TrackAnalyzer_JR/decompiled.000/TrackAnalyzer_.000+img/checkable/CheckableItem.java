package checkable;

public class CheckableItem {
   public final String text;
   private boolean selected;

   public CheckableItem(String text, boolean selected) {
      this.text = text;
      this.selected = selected;
   }

   public boolean isSelected() {
      return this.selected;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   public String toString() {
      return this.text;
   }
}
