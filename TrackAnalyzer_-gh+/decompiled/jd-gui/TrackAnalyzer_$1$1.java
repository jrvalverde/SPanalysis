/*     */ import java.awt.Component;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import jwizardcomponent.Utilities;
/*     */ import jwizardcomponent.frame.JWizardFrame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements Runnable
/*     */ {
/*     */   public void run() {
/* 135 */     TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
/* 136 */     TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
/* 137 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
/* 138 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
/* 139 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame.dispatchEvent(new WindowEvent((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame, 201));
/* 140 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard = new JWizardFrame();
/* 141 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setTitle("SLT Viewer: ANALYSIS");
/*     */     
/* 143 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new FirstWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 144 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(0, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */     
/* 146 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new ChooserWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 147 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(1, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */     
/* 149 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "A");
/* 150 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(2, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */     
/* 152 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "B");
/* 153 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(3, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */     
/* 155 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new LastWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 156 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(4, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/* 157 */     Component[] components = null;
/*     */     try {
/* 159 */       components = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().getComponents();
/* 160 */     } catch (Exception e) {
/*     */       
/* 162 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 165 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().remove(components[2]);
/* 166 */     JButton backButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getBackButton();
/* 167 */     backButton.setText("");
/* 168 */     ImageIcon iconBack = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("/images/next.png");
/* 169 */     Icon backCell = new ImageIcon(
/* 170 */         iconBack.getImage().getScaledInstance(20, 22, 4));
/* 171 */     backButton.setIcon(backCell);
/* 172 */     backButton.setToolTipText("Click this button to back on wizard.");
/* 173 */     JButton nextButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getNextButton();
/* 174 */     nextButton.setText("");
/* 175 */     ImageIcon iconNext = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("/images/back.png");
/* 176 */     Icon nextCell = new ImageIcon(
/* 177 */         iconNext.getImage().getScaledInstance(20, 22, 4));
/* 178 */     nextButton.setIcon(nextCell);
/* 179 */     nextButton.setToolTipText("Click this button to switch wizard.");
/* 180 */     JButton cancelButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getCancelButton();
/* 181 */     cancelButton.setText("");
/* 182 */     ImageIcon iconCancel = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("/images/cancel.png");
/* 183 */     Icon cancelCell = new ImageIcon(
/* 184 */         iconCancel.getImage().getScaledInstance(20, 22, 4));
/* 185 */     cancelButton.setIcon(cancelCell);
/* 186 */     cancelButton.setToolTipText("Click this button to cancel the process.");
/* 187 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setSize(630, 1000);
/* 188 */     Utilities.centerComponentOnScreen((Component)(TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard);
/*     */     
/* 190 */     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setVisible(true);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/TrackAnalyzer_$1$1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */