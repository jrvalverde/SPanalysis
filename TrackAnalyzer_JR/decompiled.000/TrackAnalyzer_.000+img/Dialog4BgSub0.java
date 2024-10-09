import ij.IJ;
import ij.gui.GUI;
import ij.gui.MultiLineLabel;
import ij.gui.Roi;
import ij.plugin.frame.RoiManager;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JDialog;

public class Dialog4BgSub0 extends JDialog implements ActionListener, KeyListener {
   public JButton button;
   public MultiLineLabel label;
   protected static int xloc = -1;
   protected static int yloc = -1;
   private boolean escPressed;

   public Dialog4BgSub0(String title, String text) {
      super(IJ.getInstance(), title, false);
      IJ.protectStatusBar(false);
      if (text != null && text.startsWith("IJ: ")) {
         text = text.substring(4);
      }

      this.label = new MultiLineLabel(text, 175);
      if (!IJ.isLinux()) {
         this.label.setFont(new Font("SansSerif", 0, 14));
      }

      if (IJ.isMacOSX()) {
         RoiManager rm = RoiManager.getInstance();
         if (rm != null) {
            rm.runCommand("enable interrupts");
         }
      }

      GridBagLayout gridbag = new GridBagLayout();
      GridBagConstraints c = new GridBagConstraints();
      this.setLayout(gridbag);
      c.insets = new Insets(6, 6, 0, 6);
      c.gridx = 0;
      c.gridy = 0;
      c.anchor = 17;
      this.add(this.label, c);
      this.button = new JButton("  OK  ");
      this.button.setBounds(50, 100, 95, 30);
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
      GUI.scale(this);
      this.pack();
      if (xloc == -1) {
         GUI.centerOnImageJScreen(this);
      } else {
         this.setLocation(xloc, yloc);
      }

      this.setAlwaysOnTop(true);
   }

   public Dialog4BgSub0(String text) {
      this("Action Required", text);
   }

   public void show() {
      super.show();
      synchronized(this) {
         try {
            this.wait();
         } catch (InterruptedException var3) {
            return;
         }

      }
   }

   public void close() {
      synchronized(this) {
         this.notify();
      }

      xloc = this.getLocation().x;
      yloc = this.getLocation().y;
      this.dispose();
   }

   public void actionPerformed(ActionEvent e) {
      SPTBatch_.impMaxProject.hide();
      int[] indexes = SPTBatch_.roiManager.getIndexes();
      SPTBatch_.roiManager.setSelectedIndexes(indexes);
      SPTBatch_.roiManager.runCommand(SPTBatch_.impMaxProject, "Combine");
      Roi roiToMeasure = SPTBatch_.impMaxProject.getRoi();
      SPTBatch_.roiManager.close();

      for(int i = 0; i < SPTBatch_.slices.length; ++i) {
         SPTBatch_.slices[i].setRoi(roiToMeasure);
         SPTBatch_.slicesIntensitySpot[i] = SPTBatch_.slices[i].getStatistics().mean;
      }

      this.close();
   }

   public void keyPressed(KeyEvent e) {
      int keyCode = e.getKeyCode();
      IJ.setKeyDown(keyCode);
      if (keyCode == 10 || keyCode == 27) {
         this.escPressed = keyCode == 27;
         this.close();
      }

   }

   public boolean escPressed() {
      return this.escPressed;
   }

   public void keyReleased(KeyEvent e) {
      int keyCode = e.getKeyCode();
      IJ.setKeyUp(keyCode);
   }

   public void keyTyped(KeyEvent e) {
   }

   public JButton getButton() {
      return this.button;
   }

   public static void setNextLocation(int x, int y) {
      xloc = x;
      yloc = y;
   }
}
