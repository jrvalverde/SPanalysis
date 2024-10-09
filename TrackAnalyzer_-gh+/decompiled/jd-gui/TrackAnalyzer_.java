/*     */ import ij.measure.Measurements;
/*     */ import ij.plugin.PlugIn;
/*     */ import java.awt.Component;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.Panel;
/*     */ import java.awt.TextField;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.net.URL;
/*     */ import java.util.prefs.Preferences;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import jwizardcomponent.JWizardPanel;
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
/*     */ public class TrackAnalyzer_
/*     */   implements PlugIn, Measurements
/*     */ {
/*     */   static String SLTDISPLAYER_XML_PATH;
/*     */   static String SLTDISPLAYER_IMAGES_PATH;
/*     */   static String xmlPath;
/*     */   static String imagesPath;
/*     */   Preferences prefXml;
/*     */   Preferences prefImages;
/*     */   JPanel panelInitial;
/*     */   JFrame frame;
/*     */   JWizardFrame wizard;
/*     */   JWizardPanel panel;
/*     */   public static final int PANEL_FIRST = 0;
/*     */   public static final int PANEL_CHOOSER = 1;
/*     */   public static final int PANEL_OPTION_A = 2;
/*     */   public static final int PANEL_OPTION_B = 3;
/*     */   public static final int PANEL_LAST = 4;
/*     */   public static TextField textXml;
/*     */   public static TextField textImages;
/*     */   Thread sptViewer;
/*     */   Thread sptBatch;
/*     */   
/*     */   public void run(String arg0) {
/*  57 */     JFrame.setDefaultLookAndFeelDecorated(true); try {
/*     */       byte b; int i; UIManager.LookAndFeelInfo[] arrayOfLookAndFeelInfo;
/*  59 */       for (i = (arrayOfLookAndFeelInfo = UIManager.getInstalledLookAndFeels()).length, b = 0; b < i; ) { UIManager.LookAndFeelInfo info = arrayOfLookAndFeelInfo[b];
/*  60 */         if ("Nimbus".equals(info.getName())) {
/*  61 */           UIManager.setLookAndFeel(info.getClassName()); break;
/*     */         } 
/*     */         b++; }
/*     */     
/*  65 */     } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
/*     */     
/*  67 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */     
/*  69 */     } catch (InstantiationException instantiationException) {
/*     */     
/*  71 */     } catch (IllegalAccessException illegalAccessException) {}
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.prefXml = Preferences.userRoot();
/*  76 */     this.prefImages = Preferences.userRoot();
/*  77 */     SLTDISPLAYER_XML_PATH = "xml_path";
/*  78 */     SLTDISPLAYER_IMAGES_PATH = "images_path";
/*     */     
/*  80 */     JButton buttonBrowse1 = new JButton("");
/*  81 */     JButton buttonBrowse2 = new JButton("");
/*  82 */     ImageIcon iconBrowse = createImageIcon("/images/browse.png");
/*  83 */     Icon iconBrowseCell = new ImageIcon(iconBrowse.getImage().getScaledInstance(15, 15, 4));
/*  84 */     buttonBrowse1.setIcon(iconBrowseCell);
/*  85 */     this.panelInitial = new JPanel();
/*  86 */     this.panelInitial.setLayout(new BoxLayout(this.panelInitial, 1));
/*     */     
/*  88 */     textXml = new TextField(20);
/*  89 */     textXml.setText(this.prefXml.get(SLTDISPLAYER_XML_PATH, ""));
/*  90 */     textImages = new TextField(20);
/*  91 */     textImages.setText(this.prefImages.get(SLTDISPLAYER_IMAGES_PATH, ""));
/*  92 */     JLabel labelXml = new JLabel("⊳  Load TrackMate .XML file: ");
/*  93 */     labelXml.setFont(new Font("Verdana", 1, 12));
/*  94 */     JLabel labelImages = new JLabel("⊳  Load movies to be analyzed:   ");
/*  95 */     labelImages.setFont(new Font("Verdana", 1, 12));
/*  96 */     DirectoryListener listenerXml = new DirectoryListener("Browse for TrackMate XML file...  ", textXml, 
/*  97 */         2);
/*  98 */     DirectoryListener listenerImages = new DirectoryListener("Browse for movies...  ", textImages, 
/*  99 */         2);
/* 100 */     buttonBrowse2.setIcon(iconBrowseCell);
/* 101 */     buttonBrowse1.addActionListener(listenerXml);
/* 102 */     buttonBrowse2.addActionListener(listenerImages);
/*     */     
/* 104 */     Panel panelXml = new Panel();
/* 105 */     panelXml.setLayout(new FlowLayout(0));
/* 106 */     panelXml.add(labelXml);
/* 107 */     panelXml.add(textXml);
/* 108 */     panelXml.add(buttonBrowse1);
/* 109 */     Panel panelImages = new Panel();
/* 110 */     panelImages.setLayout(new FlowLayout(0));
/* 111 */     panelImages.add(labelImages);
/* 112 */     panelImages.add(textImages);
/* 113 */     panelImages.add(buttonBrowse2);
/* 114 */     JButton okButton = new JButton("SPT-Viewer");
/* 115 */     ImageIcon iconOk = createImageIcon("/images/viewer.png");
/* 116 */     Icon iconOkCell = new ImageIcon(iconOk.getImage().getScaledInstance(15, 15, 4));
/* 117 */     okButton.setIcon(iconOkCell);
/* 118 */     JButton cancelButton = new JButton("SPT-Batch");
/* 119 */     ImageIcon iconCancel = createImageIcon("/images/batch.png");
/* 120 */     Icon iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(15, 15, 4));
/* 121 */     cancelButton.setIcon(iconCancelCell);
/* 122 */     Panel panelOkCancel = new Panel();
/* 123 */     panelOkCancel.setLayout(new FlowLayout(1));
/* 124 */     panelOkCancel.add(okButton);
/* 125 */     panelOkCancel.add(cancelButton);
/* 126 */     this.panelInitial.add(panelXml);
/* 127 */     this.panelInitial.add(panelImages);
/* 128 */     this.panelInitial.add(panelOkCancel);
/* 129 */     createFrame();
/* 130 */     okButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 133 */             TrackAnalyzer_.this.sptViewer = new Thread(new Runnable() {
/*     */                   public void run() {
/* 135 */                     TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
/* 136 */                     TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
/* 137 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
/* 138 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
/* 139 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame.dispatchEvent(new WindowEvent((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame, 201));
/* 140 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard = new JWizardFrame();
/* 141 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setTitle("SLT Viewer: ANALYSIS");
/*     */                     
/* 143 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new FirstWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 144 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(0, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 146 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new ChooserWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 147 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(1, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 149 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "A");
/* 150 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(2, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 152 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "B");
/* 153 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(3, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 155 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new LastWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 156 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(4, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/* 157 */                     Component[] components = null;
/*     */                     try {
/* 159 */                       components = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().getComponents();
/* 160 */                     } catch (Exception e) {
/*     */                       
/* 162 */                       e.printStackTrace();
/*     */                     } 
/*     */                     
/* 165 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().remove(components[2]);
/* 166 */                     JButton backButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getBackButton();
/* 167 */                     backButton.setText("");
/* 168 */                     ImageIcon iconBack = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("/images/next.png");
/* 169 */                     Icon backCell = new ImageIcon(
/* 170 */                         iconBack.getImage().getScaledInstance(20, 22, 4));
/* 171 */                     backButton.setIcon(backCell);
/* 172 */                     backButton.setToolTipText("Click this button to back on wizard.");
/* 173 */                     JButton nextButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getNextButton();
/* 174 */                     nextButton.setText("");
/* 175 */                     ImageIcon iconNext = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("/images/back.png");
/* 176 */                     Icon nextCell = new ImageIcon(
/* 177 */                         iconNext.getImage().getScaledInstance(20, 22, 4));
/* 178 */                     nextButton.setIcon(nextCell);
/* 179 */                     nextButton.setToolTipText("Click this button to switch wizard.");
/* 180 */                     JButton cancelButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getCancelButton();
/* 181 */                     cancelButton.setText("");
/* 182 */                     ImageIcon iconCancel = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("/images/cancel.png");
/* 183 */                     Icon cancelCell = new ImageIcon(
/* 184 */                         iconCancel.getImage().getScaledInstance(20, 22, 4));
/* 185 */                     cancelButton.setIcon(cancelCell);
/* 186 */                     cancelButton.setToolTipText("Click this button to cancel the process.");
/* 187 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setSize(630, 1000);
/* 188 */                     Utilities.centerComponentOnScreen((Component)(TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard);
/*     */                     
/* 190 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setVisible(true);
/*     */                   }
/*     */                 });
/* 193 */             TrackAnalyzer_.this.sptViewer.start();
/*     */           }
/*     */         });
/*     */     
/* 197 */     cancelButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 200 */             TrackAnalyzer_.this.sptBatch = new Thread(new Runnable() {
/*     */                   public void run() {
/* 202 */                     TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
/* 203 */                     TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
/* 204 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
/* 205 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
/* 206 */                     SPTBatch_ sptBatch = new SPTBatch_(TrackAnalyzer_.xmlPath, TrackAnalyzer_.imagesPath);
/* 207 */                     sptBatch.run("");
/* 208 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame.dispatchEvent(new WindowEvent((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame, 201));
/*     */                   }
/*     */                 });
/* 211 */             TrackAnalyzer_.this.sptBatch.start();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createFrame() {
/* 219 */     this.frame = new JFrame("TrackAnalyzer");
/* 220 */     this.frame.setDefaultCloseOperation(2);
/* 221 */     this.frame.setSize(400, 500);
/* 222 */     this.frame.setResizable(false);
/* 223 */     this.frame.getContentPane().add(this.panelInitial);
/* 224 */     this.frame.pack();
/* 225 */     this.frame.setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ImageIcon createImageIcon(String path) {
/* 230 */     URL img = getClass().getResource(path);
/*     */ 
/*     */     
/* 233 */     if (img != null) {
/* 234 */       return new ImageIcon(img);
/*     */     }
/* 236 */     System.err.println("Couldn't find file: " + path);
/* 237 */     return null;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/TrackAnalyzer_.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */