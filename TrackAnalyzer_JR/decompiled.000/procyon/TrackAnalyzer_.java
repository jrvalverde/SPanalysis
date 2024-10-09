import java.net.URL;
import javax.swing.Icon;
import jwizardcomponent.Utilities;
import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import java.awt.TextField;
import jwizardcomponent.JWizardPanel;
import jwizardcomponent.frame.JWizardFrame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.prefs.Preferences;
import ij.measure.Measurements;
import ij.plugin.PlugIn;

// 
// Decompiled by Procyon v0.5.36
// 

public class TrackAnalyzer_ implements PlugIn, Measurements
{
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
    
    public void run(final String arg0) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.LookAndFeelInfo[] installedLookAndFeels;
            for (int length = (installedLookAndFeels = UIManager.getInstalledLookAndFeels()).length, i = 0; i < length; ++i) {
                final UIManager.LookAndFeelInfo info = installedLookAndFeels[i];
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (UnsupportedLookAndFeelException ex) {}
        catch (ClassNotFoundException ex2) {}
        catch (InstantiationException ex3) {}
        catch (IllegalAccessException ex4) {}
        this.prefXml = Preferences.userRoot();
        this.prefImages = Preferences.userRoot();
        TrackAnalyzer_.SLTDISPLAYER_XML_PATH = "xml_path";
        TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH = "images_path";
        final JButton buttonBrowse1 = new JButton("");
        final JButton buttonBrowse2 = new JButton("");
        final ImageIcon iconBrowse = this.createImageIcon("images/browse.png");
        final Icon iconBrowseCell = new ImageIcon(iconBrowse.getImage().getScaledInstance(15, 15, 4));
        buttonBrowse1.setIcon(iconBrowseCell);
        (this.panelInitial = new JPanel()).setLayout(new BoxLayout(this.panelInitial, 1));
        (TrackAnalyzer_.textXml = new TextField(20)).setText(this.prefXml.get(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, ""));
        (TrackAnalyzer_.textImages = new TextField(20)).setText(this.prefImages.get(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, ""));
        final JLabel labelXml = new JLabel("⊳  Load TrackMate .XML file: ");
        labelXml.setFont(new Font("Verdana", 1, 12));
        final JLabel labelImages = new JLabel("⊳  Load movies to be analyzed:   ");
        labelImages.setFont(new Font("Verdana", 1, 12));
        final DirectoryListener listenerXml = new DirectoryListener("Browse for TrackMate XML file...  ", TrackAnalyzer_.textXml, 2);
        final DirectoryListener listenerImages = new DirectoryListener("Browse for movies...  ", TrackAnalyzer_.textImages, 2);
        buttonBrowse2.setIcon(iconBrowseCell);
        buttonBrowse1.addActionListener(listenerXml);
        buttonBrowse2.addActionListener(listenerImages);
        final Panel panelXml = new Panel();
        panelXml.setLayout(new FlowLayout(0));
        panelXml.add(labelXml);
        panelXml.add(TrackAnalyzer_.textXml);
        panelXml.add(buttonBrowse1);
        final Panel panelImages = new Panel();
        panelImages.setLayout(new FlowLayout(0));
        panelImages.add(labelImages);
        panelImages.add(TrackAnalyzer_.textImages);
        panelImages.add(buttonBrowse2);
        final JButton okButton = new JButton("SPT-Viewer");
        final ImageIcon iconOk = this.createImageIcon("images/viewer.png");
        final Icon iconOkCell = new ImageIcon(iconOk.getImage().getScaledInstance(15, 15, 4));
        okButton.setIcon(iconOkCell);
        final JButton cancelButton = new JButton("SPT-Batch");
        final ImageIcon iconCancel = this.createImageIcon("images/batch.png");
        final Icon iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(15, 15, 4));
        cancelButton.setIcon(iconCancelCell);
        final Panel panelOkCancel = new Panel();
        panelOkCancel.setLayout(new FlowLayout(1));
        panelOkCancel.add(okButton);
        panelOkCancel.add(cancelButton);
        this.panelInitial.add(panelXml);
        this.panelInitial.add(panelImages);
        this.panelInitial.add(panelOkCancel);
        this.createFrame();
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                (TrackAnalyzer_.this.sptViewer = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
                        TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
                        TrackAnalyzer_.this.prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
                        TrackAnalyzer_.this.prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
                        TrackAnalyzer_.this.frame.dispatchEvent(new WindowEvent(TrackAnalyzer_.this.frame, 201));
                        (TrackAnalyzer_.this.wizard = new JWizardFrame()).setTitle("SLT Viewer: ANALYSIS");
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
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        TrackAnalyzer_.this.wizard.getWizardComponents().getFinishButton().getParent().remove(components[2]);
                        final JButton backButton = TrackAnalyzer_.this.wizard.getWizardComponents().getBackButton();
                        backButton.setText("");
                        final ImageIcon iconBack = TrackAnalyzer_.this.createImageIcon("images/next.png");
                        final Icon backCell = new ImageIcon(iconBack.getImage().getScaledInstance(20, 22, 4));
                        backButton.setIcon(backCell);
                        backButton.setToolTipText("Click this button to back on wizard.");
                        final JButton nextButton = TrackAnalyzer_.this.wizard.getWizardComponents().getNextButton();
                        nextButton.setText("");
                        final ImageIcon iconNext = TrackAnalyzer_.this.createImageIcon("images/back.png");
                        final Icon nextCell = new ImageIcon(iconNext.getImage().getScaledInstance(20, 22, 4));
                        nextButton.setIcon(nextCell);
                        nextButton.setToolTipText("Click this button to switch wizard.");
                        final JButton cancelButton = TrackAnalyzer_.this.wizard.getWizardComponents().getCancelButton();
                        cancelButton.setText("");
                        final ImageIcon iconCancel = TrackAnalyzer_.this.createImageIcon("images/cancel.png");
                        final Icon cancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(20, 22, 4));
                        cancelButton.setIcon(cancelCell);
                        cancelButton.setToolTipText("Click this button to cancel the process.");
                        TrackAnalyzer_.this.wizard.setSize(630, 1000);
                        Utilities.centerComponentOnScreen((Component)TrackAnalyzer_.this.wizard);
                        TrackAnalyzer_.this.wizard.setVisible(true);
                    }
                })).start();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                (TrackAnalyzer_.this.sptBatch = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TrackAnalyzer_.xmlPath = TrackAnalyzer_.textXml.getText();
                        TrackAnalyzer_.imagesPath = TrackAnalyzer_.textImages.getText();
                        TrackAnalyzer_.this.prefXml.put(TrackAnalyzer_.SLTDISPLAYER_XML_PATH, TrackAnalyzer_.textXml.getText());
                        TrackAnalyzer_.this.prefImages.put(TrackAnalyzer_.SLTDISPLAYER_IMAGES_PATH, TrackAnalyzer_.textImages.getText());
                        final SPTBatch_ sptBatch = new SPTBatch_(TrackAnalyzer_.xmlPath, TrackAnalyzer_.imagesPath);
                        sptBatch.run("");
                        TrackAnalyzer_.this.frame.dispatchEvent(new WindowEvent(TrackAnalyzer_.this.frame, 201));
                    }
                })).start();
            }
        });
    }
    
    public void createFrame() {
        (this.frame = new JFrame("TrackAnalyzer")).setDefaultCloseOperation(2);
        this.frame.setSize(400, 500);
        this.frame.setResizable(false);
        this.frame.getContentPane().add(this.panelInitial);
        this.frame.pack();
        this.frame.setVisible(true);
    }
    
    protected ImageIcon createImageIcon(final String path) {
        final URL img = TrackAnalyzer_.class.getResource(path);
        if (img != null) {
            return new ImageIcon(img);
        }
        System.err.println("Couldn't find file: " + path);
        return null;
    }
}
