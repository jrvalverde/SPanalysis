import javax.swing.JButton;
import jwizardcomponent.JWizardComponents;
import jwizardcomponent.JWizardPanel;

public class LabelWizardPanel extends JWizardPanel {
   public LabelWizardPanel(JWizardComponents wizardComponents, String label) {
      super(wizardComponents);
      JButton backButton = wizardComponents.getBackButton();
      backButton.setText("");
   }
}
