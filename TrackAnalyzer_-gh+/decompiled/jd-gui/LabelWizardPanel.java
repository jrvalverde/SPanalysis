/*    */ import javax.swing.JButton;
/*    */ import jwizardcomponent.JWizardComponents;
/*    */ import jwizardcomponent.JWizardPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LabelWizardPanel
/*    */   extends JWizardPanel
/*    */ {
/*    */   public LabelWizardPanel(JWizardComponents wizardComponents, String label) {
/* 27 */     super(wizardComponents);
/* 28 */     JButton backButton = wizardComponents.getBackButton();
/* 29 */     backButton.setText("");
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/LabelWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */