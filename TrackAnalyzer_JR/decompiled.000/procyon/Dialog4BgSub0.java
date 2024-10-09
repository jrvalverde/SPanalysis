import java.awt.event.KeyEvent;
import ij.gui.Roi;
import java.awt.event.ActionEvent;
import java.awt.Window;
import ij.gui.GUI;
import java.awt.Component;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import ij.plugin.frame.RoiManager;
import java.awt.Font;
import java.awt.Frame;
import ij.IJ;
import ij.gui.MultiLineLabel;
import javax.swing.JButton;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

// 
// Decompiled by Procyon v0.5.36
// 

public class Dialog4BgSub0 extends JDialog implements ActionListener, KeyListener
{
    public JButton button;
    public MultiLineLabel label;
    protected static int xloc;
    protected static int yloc;
    private boolean escPressed;
    
    static {
        Dialog4BgSub0.xloc = -1;
        Dialog4BgSub0.yloc = -1;
    }
    
    public Dialog4BgSub0(final String title, String text) {
        super((Frame)IJ.getInstance(), title, false);
        IJ.protectStatusBar(false);
        if (text != null && text.startsWith("IJ: ")) {
            text = text.substring(4);
        }
        this.label = new MultiLineLabel(text, 175);
        if (!IJ.isLinux()) {
            this.label.setFont(new Font("SansSerif", 0, 14));
        }
        if (IJ.isMacOSX()) {
            final RoiManager rm = RoiManager.getInstance();
            if (rm != null) {
                rm.runCommand("enable interrupts");
            }
        }
        final GridBagLayout gridbag = new GridBagLayout();
        final GridBagConstraints c = new GridBagConstraints();
        this.setLayout(gridbag);
        c.insets = new Insets(6, 6, 0, 6);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = 17;
        this.add((Component)this.label, c);
        (this.button = new JButton("  OK  ")).setBounds(50, 100, 95, 30);
        this.button.setToolTipText("Click this button to process next image.");
        this.button.addActionListener(this);
        this.button.addKeyListener(this);
        c.insets = new Insets(2, 6, 6, 6);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = 13;
        this.add(this.button, c);
        this.setResizable(false);
        this.addKeyListener(this);
        GUI.scale((Component)this);
        this.pack();
        if (Dialog4BgSub0.xloc == -1) {
            GUI.centerOnImageJScreen((Window)this);
        }
        else {
            this.setLocation(Dialog4BgSub0.xloc, Dialog4BgSub0.yloc);
        }
        this.setAlwaysOnTop(true);
    }
    
    public Dialog4BgSub0(final String text) {
        this("Action Required", text);
    }
    
    @Override
    public void show() {
        super.show();
        synchronized (this) {
            try {
                this.wait();
            }
            catch (InterruptedException e) {
            }
            // monitorexit(this)
        }
    }
    
    public void close() {
        synchronized (this) {
            this.notify();
        }
        Dialog4BgSub0.xloc = this.getLocation().x;
        Dialog4BgSub0.yloc = this.getLocation().y;
        this.dispose();
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        SPTBatch_.impMaxProject.hide();
        final int[] indexes = SPTBatch_.roiManager.getIndexes();
        SPTBatch_.roiManager.setSelectedIndexes(indexes);
        SPTBatch_.roiManager.runCommand(SPTBatch_.impMaxProject, "Combine");
        final Roi roiToMeasure = SPTBatch_.impMaxProject.getRoi();
        SPTBatch_.roiManager.close();
        for (int i = 0; i < SPTBatch_.slices.length; ++i) {
            SPTBatch_.slices[i].setRoi(roiToMeasure);
            SPTBatch_.slicesIntensitySpot[i] = SPTBatch_.slices[i].getStatistics().mean;
        }
        this.close();
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        final int keyCode = e.getKeyCode();
        IJ.setKeyDown(keyCode);
        if (keyCode == 10 || keyCode == 27) {
            this.escPressed = (keyCode == 27);
            this.close();
        }
    }
    
    public boolean escPressed() {
        return this.escPressed;
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        final int keyCode = e.getKeyCode();
        IJ.setKeyUp(keyCode);
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
    
    public JButton getButton() {
        return this.button;
    }
    
    public static void setNextLocation(final int x, final int y) {
        Dialog4BgSub0.xloc = x;
        Dialog4BgSub0.yloc = y;
    }
}
