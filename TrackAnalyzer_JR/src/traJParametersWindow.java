import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class traJParametersWindow {
   static JButton okButton;
   static String minLengthTextS;
   static String windowTextS;
   static String minSegTextS;
   static String keyWord;
   static JFrame frame;
   static JTextField minLengthText;
   static JTextField windowText;
   static JTextField minSegText;

   public void run(String args) {
      frame = new JFrame("TraJ Classifier Parameters");
      frame.setSize(200, 200);
      frame.setDefaultCloseOperation(2);
      JPanel panel = new JPanel();
      frame.add(panel);
      placeComponents(panel);
      frame.setVisible(true);
   }

   private static void placeComponents(JPanel panel) {
      panel.setLayout(new BoxLayout(panel, 1));
      JLabel minLengthLabel = new JLabel("Min. Tracklength: ");
      panel.add(minLengthLabel);
      minLengthText = new JTextField("10", 5);
      minLengthText.setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_MIN_TRACK, ""));
      panel.add(minLengthText);
      JLabel windowLabel = new JLabel("Windowsize (SPOT positions): ");
      panel.add(windowLabel);
      windowText = new JTextField("5", 5);
      windowText.setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_WINDOW, ""));
      panel.add(windowText);
      JLabel minSegLabel = new JLabel("Min.Segment Length: ");
      panel.add(minSegLabel);
      minSegText = new JTextField("5", 5);
      minSegText.setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_MIN_SEGMENT, ""));
      panel.add(minSegText);
      okButton = new JButton("OK");
      okButton.setBounds(10, 80, 80, 25);
      panel.add(okButton);
      okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            traJParametersWindow.keyWord = "keyword";
            traJParametersWindow.minLengthTextS = traJParametersWindow.minLengthText.getText();
            traJParametersWindow.windowTextS = traJParametersWindow.windowText.getText();
            traJParametersWindow.minSegTextS = traJParametersWindow.minSegText.getText();
            traJParametersWindow.frame.setVisible(false);
         }
      });
   }
}
