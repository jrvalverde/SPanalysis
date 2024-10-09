import javax.swing.JButton;
import jwizardcomponent.JWizardComponents;
import jwizardcomponent.JWizardPanel;

// 
// Decompiled by Procyon v0.5.36
// 

public class LabelWizardPanel extends JWizardPanel
{
    public LabelWizardPanel(final JWizardComponents wizardComponents, final String label) {
        super(wizardComponents);
        final JButton backButton = wizardComponents.getBackButton();
        backButton.setText("");
    }
}
