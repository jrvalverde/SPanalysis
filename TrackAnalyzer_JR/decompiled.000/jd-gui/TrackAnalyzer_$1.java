/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
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
/*     */ class null
/*     */   implements ActionListener
/*     */ {
/*     */   public void actionPerformed(ActionEvent event) {
/* 127 */     TrackAnalyzer_.this.sptViewer = new Thread(new Runnable() {
/*     */           public void run() {
/* 129 */             TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
/* 130 */             TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
/* 131 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
/* 132 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
/* 133 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame.dispatchEvent(new WindowEvent((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame, 201));
/* 134 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard = new JWizardFrame();
/* 135 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setTitle("SLT Viewer: ANALYSIS");
/*     */             
/* 137 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new FirstWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 138 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(0, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */             
/* 140 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new ChooserWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 141 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(1, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */             
/* 143 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "A");
/* 144 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(2, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */             
/* 146 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "B");
/* 147 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(3, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */             
/* 149 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new LastWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 150 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(4, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/* 151 */             Component[] components = null;
/*     */             try {
/* 153 */               components = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().getComponents();
/* 154 */             } catch (Exception e) {
/*     */               
/* 156 */               e.printStackTrace();
/*     */             } 
/*     */             
/* 159 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().remove(components[2]);
/* 160 */             JButton backButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getBackButton();
/* 161 */             backButton.setText("");
/* 162 */             ImageIcon iconBack = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("images/next.png");
/* 163 */             Icon backCell = new ImageIcon(
/* 164 */                 iconBack.getImage().getScaledInstance(20, 22, 4));
/* 165 */             backButton.setIcon(backCell);
/* 166 */             backButton.setToolTipText("Click this button to back on wizard.");
/* 167 */             JButton nextButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getNextButton();
/* 168 */             nextButton.setText("");
/* 169 */             ImageIcon iconNext = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("images/back.png");
/* 170 */             Icon nextCell = new ImageIcon(
/* 171 */                 iconNext.getImage().getScaledInstance(20, 22, 4));
/* 172 */             nextButton.setIcon(nextCell);
/* 173 */             nextButton.setToolTipText("Click this button to switch wizard.");
/* 174 */             JButton cancelButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getCancelButton();
/* 175 */             cancelButton.setText("");
/* 176 */             ImageIcon iconCancel = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("images/cancel.png");
/* 177 */             Icon cancelCell = new ImageIcon(
/* 178 */                 iconCancel.getImage().getScaledInstance(20, 22, 4));
/* 179 */             cancelButton.setIcon(cancelCell);
/* 180 */             cancelButton.setToolTipText("Click this button to cancel the process.");
/* 181 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setSize(630, 1000);
/* 182 */             Utilities.centerComponentOnScreen((Component)(TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard);
/*     */             
/* 184 */             (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setVisible(true);
/*     */           }
/*     */         });
/* 187 */     TrackAnalyzer_.this.sptViewer.start();
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/TrackAnalyzer_$1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */