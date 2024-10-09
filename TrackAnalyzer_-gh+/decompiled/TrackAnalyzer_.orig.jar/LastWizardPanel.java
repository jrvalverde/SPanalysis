import javax.swing.JTabbedPane;
import jwizardcomponent.JWizardComponents;

public class LastWizardPanel extends LabelWizardPanel {
   public LastWizardPanel(JWizardComponents wizardComponents) {
      super(wizardComponents, "");
      this.setPanelTitle("⊚  PLOT-OPTIONS:  Spots,Links or Tracks");
      this.update();
      this.removeAll();
      new JTabbedPane();
   }

   public void update() {
      this.setNextButtonEnabled(false);
      this.setFinishButtonEnabled(true);
      this.setBackButtonEnabled(true);
   }

   public void next() {
   }

   public void back() {
      this.switchPanel(1);
   }

   public void finish() {
      this.switchPanel(2);
   }
}
