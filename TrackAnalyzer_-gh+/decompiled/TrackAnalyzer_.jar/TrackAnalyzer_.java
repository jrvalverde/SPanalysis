import ij.measure.Measurements;
import ij.plugin.PlugIn;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.prefs.Preferences;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import jwizardcomponent.JWizardPanel;
import jwizardcomponent.Utilities;
import jwizardcomponent.frame.JWizardFrame;

public class TrackAnalyzer_ implements PlugIn, Measurements {
   static String SLTDISPLAYER_XML_PATH;
   static String SLTDISPLAYER_IMAGES_PATH;
   static String xmlPath;
   static String imagesPath;
   Preferences prefXml;
   Preferences prefImages;
   JPanel panelInitial;
   JFrame frame;
   JWizardFrame wizard;
   JWizardPanel panel;
   public static final int PANEL_FIRST = 0;
   public static final int PANEL_CHOOSER = 1;
   public static final int PANEL_OPTION_A = 2;
   public static final int PANEL_OPTION_B = 3;
   public static final int PANEL_LAST = 4;
   public static TextField textXml;
   public static TextField textImages;
   Thread sptViewer;
   Thread sptBatch;

   public void run(String arg0) {
      JFrame.setDefaultLookAndFeelDecorated(true);

      try {
         LookAndFeelInfo[] var5;
         int var4 = (var5 = UIManager.getInstalledLookAndFeels()).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            LookAndFeelInfo info = var5[var3];
            if ("Nimbus".equals(info.getName())) {
               UIManager.setLookAndFeel(info.getClassName());
               break;
            }
         }
      } catch (UnsupportedLookAndFeelException var19) {
      } catch (ClassNotFoundException var20) {
      } catch (InstantiationException var21) {
      } catch (IllegalAccessException var22) {
      }

      this.prefXml = Preferences.userRoot();
      this.prefImages = Preferences.userRoot();
      SLTDISPLAYER_XML_PATH = "xml_path";
      SLTDISPLAYER_IMAGES_PATH = "images_path";
      JButton buttonBrowse1 = new JButton("");
      JButton buttonBrowse2 = new JButton("");
      ImageIcon iconBrowse = this.createImageIcon("/images/browse.png");
      Icon iconBrowseCell = new ImageIcon(iconBrowse.getImage().getScaledInstance(15, 15, 4));
      buttonBrowse1.setIcon(iconBrowseCell);
      this.panelInitial = new JPanel();
      this.panelInitial.setLayout(new BoxLayout(this.panelInitial, 1));
      textXml = new TextField(20);
      textXml.setText(this.prefXml.get(SLTDISPLAYER_XML_PATH, ""));
      textImages = new TextField(20);
      textImages.setText(this.prefImages.get(SLTDISPLAYER_IMAGES_PATH, ""));
      JLabel labelXml = new JLabel("⊳  Load TrackMate .XML file: ");
      labelXml.setFont(new Font("Verdana", 1, 12));
      JLabel labelImages = new JLabel("⊳  Load movies to be analyzed:   ");
      labelImages.setFont(new Font("Verdana", 1, 12));
      DirectoryListener listenerXml = new DirectoryListener("Browse for TrackMate XML file...  ", textXml, 2);
      DirectoryListener listenerImages = new DirectoryListener("Browse for movies...  ", textImages, 2);
      buttonBrowse2.setIcon(iconBrowseCell);
      buttonBrowse1.addActionListener(listenerXml);
      buttonBrowse2.addActionListener(listenerImages);
      Panel panelXml = new Panel();
      panelXml.setLayout(new FlowLayout(0));
      panelXml.add(labelXml);
      panelXml.add(textXml);
      panelXml.add(buttonBrowse1);
      Panel panelImages = new Panel();
      panelImages.setLayout(new FlowLayout(0));
      panelImages.add(labelImages);
      panelImages.add(textImages);
      panelImages.add(buttonBrowse2);
      JButton okButton = new JButton("SPT-Viewer");
      ImageIcon iconOk = this.createImageIcon("/images/viewer.png");
      Icon iconOkCell = new ImageIcon(iconOk.getImage().getScaledInstance(15, 15, 4));
      okButton.setIcon(iconOkCell);
      JButton cancelButton = new JButton("SPT-Batch");
      ImageIcon iconCancel = this.createImageIcon("/images/batch.png");
      Icon iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(15, 15, 4));
      cancelButton.setIcon(iconCancelCell);
      Panel panelOkCancel = new Panel();
      panelOkCancel.setLayout(new FlowLayout(1));
      panelOkCancel.add(okButton);
      panelOkCancel.add(cancelButton);
      this.panelInitial.add(panelXml);
      this.panelInitial.add(panelImages);
      this.panelInitial.add(panelOkCancel);
      this.createFrame();
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            TrackAnalyzer_.this.sptViewer = new Thread(new Runnable() {
               public void run() {
                  TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
                  TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
                  TrackAnalyzer_.this.prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
                  TrackAnalyzer_.this.prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
                  TrackAnalyzer_.this.frame.dispatchEvent(new WindowEvent(TrackAnalyzer_.this.frame, 201));
                  TrackAnalyzer_.this.wizard = new JWizardFrame();
                  TrackAnalyzer_.this.wizard.setTitle("SLT Viewer: ANALYSIS");
                  TrackAnalyzer_.this.panel = new FirstWizardPanel(TrackAnalyzer_.this.wizard.getWizardComponents());
                  TrackAnalyzer_.this.wizard.getWizardComponents().addWizardPanel(0, TrackAnalyzer_.this.panel);
                  TrackAnalyzer_.this.panel = new ChooserWizardPanel(TrackAnalyzer_.this.wizard.getWizardComponents());
                  TrackAnalyzer_.this.wizard.getWizardComponents().addWizardPanel(1, TrackAnalyzer_.this.panel);
                  TrackAnalyzer_.this.panel = new OptionWizardPanel(TrackAnalyzer_.this.wizard.getWizardComponents(), "A");
                  TrackAnalyzer_.this.wizard.getWizardComponents().addWizardPanel(2, TrackAnalyzer_.this.panel);
                  TrackAnalyzer_.this.panel = new OptionWizardPanel(TrackAnalyzer_.this.wizard.getWizardComponents(), "B");
                  TrackAnalyzer_.this.wizard.getWizardComponents().addWizardPanel(3, TrackAnalyzer_.this.panel);
                  TrackAnalyzer_.this.panel = new LastWizardPanel(TrackAnalyzer_.this.wizard.getWizardComponents());
                  TrackAnalyzer_.this.wizard.getWizardComponents().addWizardPanel(4, TrackAnalyzer_.this.panel);
                  Component[] components = null;

                  try {
                     components = TrackAnalyzer_.this.wizard.getWizardComponents().getFinishButton().getParent().getComponents();
                  } catch (Exception var11) {
                     var11.printStackTrace();
                  }

                  TrackAnalyzer_.this.wizard.getWizardComponents().getFinishButton().getParent().remove(components[2]);
                  JButton backButton = TrackAnalyzer_.this.wizard.getWizardComponents().getBackButton();
                  backButton.setText("");
                  ImageIcon iconBack = TrackAnalyzer_.this.createImageIcon("/images/next.png");
                  Icon backCell = new ImageIcon(iconBack.getImage().getScaledInstance(20, 22, 4));
                  backButton.setIcon(backCell);
                  backButton.setToolTipText("Click this button to back on wizard.");
                  JButton nextButton = TrackAnalyzer_.this.wizard.getWizardComponents().getNextButton();
                  nextButton.setText("");
                  ImageIcon iconNext = TrackAnalyzer_.this.createImageIcon("/images/back.png");
                  Icon nextCell = new ImageIcon(iconNext.getImage().getScaledInstance(20, 22, 4));
                  nextButton.setIcon(nextCell);
                  nextButton.setToolTipText("Click this button to switch wizard.");
                  JButton cancelButton = TrackAnalyzer_.this.wizard.getWizardComponents().getCancelButton();
                  cancelButton.setText("");
                  ImageIcon iconCancel = TrackAnalyzer_.this.createImageIcon("/images/cancel.png");
                  Icon cancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(20, 22, 4));
                  cancelButton.setIcon(cancelCell);
                  cancelButton.setToolTipText("Click this button to cancel the process.");
                  TrackAnalyzer_.this.wizard.setSize(630, 1000);
                  Utilities.centerComponentOnScreen(TrackAnalyzer_.this.wizard);
                  TrackAnalyzer_.this.wizard.setVisible(true);
               }
            });
            TrackAnalyzer_.this.sptViewer.start();
         }
      });
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            TrackAnalyzer_.this.sptBatch = new Thread(new Runnable() {
               public void run() {
                  TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
                  TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
                  TrackAnalyzer_.this.prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
                  TrackAnalyzer_.this.prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
                  SPTBatch_ sptBatch = new SPTBatch_(TrackAnalyzer_.xmlPath, TrackAnalyzer_.imagesPath);
                  sptBatch.run("");
                  TrackAnalyzer_.this.frame.dispatchEvent(new WindowEvent(TrackAnalyzer_.this.frame, 201));
               }
            });
            TrackAnalyzer_.this.sptBatch.start();
         }
      });
   }

   public void createFrame() {
      this.frame = new JFrame("TrackAnalyzer");
      this.frame.setDefaultCloseOperation(2);
      this.frame.setSize(400, 500);
      this.frame.setResizable(false);
      this.frame.getContentPane().add(this.panelInitial);
      this.frame.pack();
      this.frame.setVisible(true);
   }

   protected ImageIcon createImageIcon(String path) {
      URL img = this.getClass().getResource(path);
      if (img != null) {
         return new ImageIcon(img);
      } else {
         System.err.println("Couldn't find file: " + path);
         return null;
      }
   }
}
