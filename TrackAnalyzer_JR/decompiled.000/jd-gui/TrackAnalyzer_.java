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
/*  51 */     JFrame.setDefaultLookAndFeelDecorated(true); try {
/*     */       byte b; int i; UIManager.LookAndFeelInfo[] arrayOfLookAndFeelInfo;
/*  53 */       for (i = (arrayOfLookAndFeelInfo = UIManager.getInstalledLookAndFeels()).length, b = 0; b < i; ) { UIManager.LookAndFeelInfo info = arrayOfLookAndFeelInfo[b];
/*  54 */         if ("Nimbus".equals(info.getName())) {
/*  55 */           UIManager.setLookAndFeel(info.getClassName()); break;
/*     */         } 
/*     */         b++; }
/*     */     
/*  59 */     } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
/*     */     
/*  61 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */     
/*  63 */     } catch (InstantiationException instantiationException) {
/*     */     
/*  65 */     } catch (IllegalAccessException illegalAccessException) {}
/*     */ 
/*     */ 
/*     */     
/*  69 */     this.prefXml = Preferences.userRoot();
/*  70 */     this.prefImages = Preferences.userRoot();
/*  71 */     SLTDISPLAYER_XML_PATH = "xml_path";
/*  72 */     SLTDISPLAYER_IMAGES_PATH = "images_path";
/*     */     
/*  74 */     JButton buttonBrowse1 = new JButton("");
/*  75 */     JButton buttonBrowse2 = new JButton("");
/*  76 */     ImageIcon iconBrowse = createImageIcon("images/browse.png");
/*  77 */     Icon iconBrowseCell = new ImageIcon(iconBrowse.getImage().getScaledInstance(15, 15, 4));
/*  78 */     buttonBrowse1.setIcon(iconBrowseCell);
/*  79 */     this.panelInitial = new JPanel();
/*  80 */     this.panelInitial.setLayout(new BoxLayout(this.panelInitial, 1));
/*     */     
/*  82 */     textXml = new TextField(20);
/*  83 */     textXml.setText(this.prefXml.get(SLTDISPLAYER_XML_PATH, ""));
/*  84 */     textImages = new TextField(20);
/*  85 */     textImages.setText(this.prefImages.get(SLTDISPLAYER_IMAGES_PATH, ""));
/*  86 */     JLabel labelXml = new JLabel("⊳  Load TrackMate .XML file: ");
/*  87 */     labelXml.setFont(new Font("Verdana", 1, 12));
/*  88 */     JLabel labelImages = new JLabel("⊳  Load movies to be analyzed:   ");
/*  89 */     labelImages.setFont(new Font("Verdana", 1, 12));
/*  90 */     DirectoryListener listenerXml = new DirectoryListener("Browse for TrackMate XML file...  ", textXml, 
/*  91 */         2);
/*  92 */     DirectoryListener listenerImages = new DirectoryListener("Browse for movies...  ", textImages, 
/*  93 */         2);
/*  94 */     buttonBrowse2.setIcon(iconBrowseCell);
/*  95 */     buttonBrowse1.addActionListener(listenerXml);
/*  96 */     buttonBrowse2.addActionListener(listenerImages);
/*     */     
/*  98 */     Panel panelXml = new Panel();
/*  99 */     panelXml.setLayout(new FlowLayout(0));
/* 100 */     panelXml.add(labelXml);
/* 101 */     panelXml.add(textXml);
/* 102 */     panelXml.add(buttonBrowse1);
/* 103 */     Panel panelImages = new Panel();
/* 104 */     panelImages.setLayout(new FlowLayout(0));
/* 105 */     panelImages.add(labelImages);
/* 106 */     panelImages.add(textImages);
/* 107 */     panelImages.add(buttonBrowse2);
/* 108 */     JButton okButton = new JButton("SPT-Viewer");
/* 109 */     ImageIcon iconOk = createImageIcon("images/viewer.png");
/* 110 */     Icon iconOkCell = new ImageIcon(iconOk.getImage().getScaledInstance(15, 15, 4));
/* 111 */     okButton.setIcon(iconOkCell);
/* 112 */     JButton cancelButton = new JButton("SPT-Batch");
/* 113 */     ImageIcon iconCancel = createImageIcon("images/batch.png");
/* 114 */     Icon iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(15, 15, 4));
/* 115 */     cancelButton.setIcon(iconCancelCell);
/* 116 */     Panel panelOkCancel = new Panel();
/* 117 */     panelOkCancel.setLayout(new FlowLayout(1));
/* 118 */     panelOkCancel.add(okButton);
/* 119 */     panelOkCancel.add(cancelButton);
/* 120 */     this.panelInitial.add(panelXml);
/* 121 */     this.panelInitial.add(panelImages);
/* 122 */     this.panelInitial.add(panelOkCancel);
/* 123 */     createFrame();
/* 124 */     okButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 127 */             TrackAnalyzer_.this.sptViewer = new Thread(new Runnable() {
/*     */                   public void run() {
/* 129 */                     TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
/* 130 */                     TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
/* 131 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
/* 132 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
/* 133 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame.dispatchEvent(new WindowEvent((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame, 201));
/* 134 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard = new JWizardFrame();
/* 135 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setTitle("SLT Viewer: ANALYSIS");
/*     */                     
/* 137 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new FirstWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 138 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(0, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 140 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new ChooserWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 141 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(1, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 143 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "A");
/* 144 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(2, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 146 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new OptionWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents(), "B");
/* 147 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(3, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/*     */                     
/* 149 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel = new LastWizardPanel((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents());
/* 150 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().addWizardPanel(4, (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).panel);
/* 151 */                     Component[] components = null;
/*     */                     try {
/* 153 */                       components = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().getComponents();
/* 154 */                     } catch (Exception e) {
/*     */                       
/* 156 */                       e.printStackTrace();
/*     */                     } 
/*     */                     
/* 159 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getFinishButton().getParent().remove(components[2]);
/* 160 */                     JButton backButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getBackButton();
/* 161 */                     backButton.setText("");
/* 162 */                     ImageIcon iconBack = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("images/next.png");
/* 163 */                     Icon backCell = new ImageIcon(
/* 164 */                         iconBack.getImage().getScaledInstance(20, 22, 4));
/* 165 */                     backButton.setIcon(backCell);
/* 166 */                     backButton.setToolTipText("Click this button to back on wizard.");
/* 167 */                     JButton nextButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getNextButton();
/* 168 */                     nextButton.setText("");
/* 169 */                     ImageIcon iconNext = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("images/back.png");
/* 170 */                     Icon nextCell = new ImageIcon(
/* 171 */                         iconNext.getImage().getScaledInstance(20, 22, 4));
/* 172 */                     nextButton.setIcon(nextCell);
/* 173 */                     nextButton.setToolTipText("Click this button to switch wizard.");
/* 174 */                     JButton cancelButton = (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.getWizardComponents().getCancelButton();
/* 175 */                     cancelButton.setText("");
/* 176 */                     ImageIcon iconCancel = TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this).createImageIcon("images/cancel.png");
/* 177 */                     Icon cancelCell = new ImageIcon(
/* 178 */                         iconCancel.getImage().getScaledInstance(20, 22, 4));
/* 179 */                     cancelButton.setIcon(cancelCell);
/* 180 */                     cancelButton.setToolTipText("Click this button to cancel the process.");
/* 181 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setSize(630, 1000);
/* 182 */                     Utilities.centerComponentOnScreen((Component)(TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard);
/*     */                     
/* 184 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).wizard.setVisible(true);
/*     */                   }
/*     */                 });
/* 187 */             TrackAnalyzer_.this.sptViewer.start();
/*     */           }
/*     */         });
/*     */     
/* 191 */     cancelButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 194 */             TrackAnalyzer_.this.sptBatch = new Thread(new Runnable() {
/*     */                   public void run() {
/* 196 */                     TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
/* 197 */                     TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
/* 198 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
/* 199 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
/* 200 */                     SPTBatch_ sptBatch = new SPTBatch_(TrackAnalyzer_.xmlPath, TrackAnalyzer_.imagesPath);
/* 201 */                     sptBatch.run("");
/* 202 */                     (TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame.dispatchEvent(new WindowEvent((TrackAnalyzer_.null.access$0(TrackAnalyzer_.null.this)).frame, 201));
/*     */                   }
/*     */                 });
/* 205 */             TrackAnalyzer_.this.sptBatch.start();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void createFrame() {
/* 213 */     this.frame = new JFrame("TrackAnalyzer");
/* 214 */     this.frame.setDefaultCloseOperation(2);
/* 215 */     this.frame.setSize(400, 500);
/* 216 */     this.frame.setResizable(false);
/* 217 */     this.frame.getContentPane().add(this.panelInitial);
/* 218 */     this.frame.pack();
/* 219 */     this.frame.setVisible(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ImageIcon createImageIcon(String path) {
/* 224 */     URL img = TrackAnalyzer_.class.getResource(path);
/* 225 */     if (img != null) {
/* 226 */       return new ImageIcon(img);
/*     */     }
/* 228 */     System.err.println("Couldn't find file: " + path);
/* 229 */     return null;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/TrackAnalyzer_.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */