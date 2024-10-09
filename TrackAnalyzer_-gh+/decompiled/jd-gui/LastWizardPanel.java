/*    */ import javax.swing.JTabbedPane;
/*    */ import jwizardcomponent.JWizardComponents;
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
/*    */ public class LastWizardPanel
/*    */   extends LabelWizardPanel
/*    */ {
/*    */   public LastWizardPanel(JWizardComponents wizardComponents) {
/* 25 */     super(wizardComponents, "");
/* 26 */     setPanelTitle("⊚  PLOT-OPTIONS:  Spots,Links or Tracks");
/* 27 */     update();
/* 28 */     removeAll();
/*    */     
/* 30 */     JTabbedPane tabbedPane = new JTabbedPane();
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 35 */     setNextButtonEnabled(false);
/*    */ 
/*    */ 
/*    */     
/* 39 */     setFinishButtonEnabled(true);
/* 40 */     setBackButtonEnabled(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void next() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void back() {
/* 52 */     switchPanel(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void finish() {
/* 57 */     switchPanel(2);
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/LastWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */