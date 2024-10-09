import javax.swing.JTabbedPane;
import jwizardcomponent.JWizardComponents;

// 
// Decompiled by Procyon v0.5.36
// 

public class LastWizardPanel extends LabelWizardPanel
{
    public LastWizardPanel(final JWizardComponents wizardComponents) {
        super(wizardComponents, "");
        this.setPanelTitle("⊚  PLOT-OPTIONS:  Spots,Links or Tracks");
        this.update();
        this.removeAll();
        final JTabbedPane tabbedPane = new JTabbedPane();
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
