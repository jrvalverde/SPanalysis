/*     */ import ij.IJ;
/*     */ import ij.gui.GUI;
/*     */ import ij.gui.MultiLineLabel;
/*     */ import ij.gui.Roi;
/*     */ import ij.plugin.frame.RoiManager;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.KeyListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ 
/*     */ public class Dialog4BgSub0 extends JDialog implements ActionListener, KeyListener {
/*     */   public JButton button;
/*  20 */   protected static int xloc = -1; public MultiLineLabel label; protected static int yloc = -1;
/*     */   private boolean escPressed;
/*     */   
/*     */   public Dialog4BgSub0(String title, String text) {
/*  24 */     super((Frame)IJ.getInstance(), title, false);
/*  25 */     IJ.protectStatusBar(false);
/*  26 */     if (text != null && text.startsWith("IJ: "))
/*  27 */       text = text.substring(4); 
/*  28 */     this.label = new MultiLineLabel(text, 175);
/*  29 */     if (!IJ.isLinux()) this.label.setFont(new Font("SansSerif", 0, 14)); 
/*  30 */     if (IJ.isMacOSX()) {
/*  31 */       RoiManager rm = RoiManager.getInstance();
/*  32 */       if (rm != null) rm.runCommand("enable interrupts"); 
/*     */     } 
/*  34 */     GridBagLayout gridbag = new GridBagLayout();
/*  35 */     GridBagConstraints c = new GridBagConstraints();
/*  36 */     setLayout(gridbag);
/*  37 */     c.insets = new Insets(6, 6, 0, 6);
/*  38 */     c.gridx = 0; c.gridy = 0; c.anchor = 17;
/*  39 */     add((Component)this.label, c);
/*  40 */     this.button = new JButton("  OK  ");
/*  41 */     this.button.setBounds(50, 100, 95, 30);
/*  42 */     this.button.setToolTipText("Click this button to process next image.");
/*  43 */     this.button.addActionListener(this);
/*  44 */     this.button.addKeyListener(this);
/*  45 */     c.insets = new Insets(2, 6, 6, 6);
/*  46 */     c.gridx = 0; c.gridy = 2; c.anchor = 13;
/*  47 */     add(this.button, c);
/*  48 */     setResizable(false);
/*  49 */     addKeyListener(this);
/*  50 */     GUI.scale(this);
/*  51 */     pack();
/*  52 */     if (xloc == -1) {
/*  53 */       GUI.centerOnImageJScreen(this);
/*     */     } else {
/*  55 */       setLocation(xloc, yloc);
/*  56 */     }  setAlwaysOnTop(true);
/*     */   }
/*     */   
/*     */   public Dialog4BgSub0(String text) {
/*  60 */     this("Action Required", text);
/*     */   }
/*     */   
/*     */   public void show() {
/*  64 */     super.show();
/*  65 */     synchronized (this) { try {
/*  66 */         wait();
/*  67 */       } catch (InterruptedException e) {
/*     */         return;
/*     */       }  }
/*     */   
/*     */   } public void close() {
/*  72 */     synchronized (this) { notify(); }
/*  73 */      xloc = (getLocation()).x;
/*  74 */     yloc = (getLocation()).y;
/*  75 */     dispose();
/*     */   }
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/*  79 */     SPTBatch_.impMaxProject.hide();
/*     */     
/*  81 */     int[] indexes = SPTBatch_.roiManager.getIndexes();
/*  82 */     SPTBatch_.roiManager.setSelectedIndexes(indexes);
/*  83 */     SPTBatch_.roiManager.runCommand(SPTBatch_.impMaxProject, "Combine");
/*  84 */     Roi roiToMeasure = SPTBatch_.impMaxProject.getRoi();
/*  85 */     SPTBatch_.roiManager.close();
/*  86 */     for (int i = 0; i < SPTBatch_.slices.length; i++) {
/*  87 */       SPTBatch_.slices[i].setRoi(roiToMeasure);
/*  88 */       SPTBatch_.slicesIntensitySpot[i] = (SPTBatch_.slices[i].getStatistics()).mean;
/*     */     } 
/*     */     
/*  91 */     close();
/*     */   }
/*     */   
/*     */   public void keyPressed(KeyEvent e) {
/*  95 */     int keyCode = e.getKeyCode();
/*  96 */     IJ.setKeyDown(keyCode);
/*  97 */     if (keyCode == 10 || keyCode == 27) {
/*  98 */       this.escPressed = (keyCode == 27);
/*  99 */       close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean escPressed() {
/* 104 */     return this.escPressed;
/*     */   }
/*     */   
/*     */   public void keyReleased(KeyEvent e) {
/* 108 */     int keyCode = e.getKeyCode();
/* 109 */     IJ.setKeyUp(keyCode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(KeyEvent e) {}
/*     */   
/*     */   public JButton getButton() {
/* 116 */     return this.button;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setNextLocation(int x, int y) {
/* 121 */     xloc = x;
/* 122 */     yloc = y;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/Dialog4BgSub0.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */