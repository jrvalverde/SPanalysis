import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JButton;

// 
// Decompiled by Procyon v0.5.36
// 

public class traJParametersWindow
{
    static JButton okButton;
    static String minLengthTextS;
    static String windowTextS;
    static String minSegTextS;
    static String keyWord;
    static JFrame frame;
    static JTextField minLengthText;
    static JTextField windowText;
    static JTextField minSegText;
    
    public void run(final String args) {
        (traJParametersWindow.frame = new JFrame("TraJ Classifier Parameters")).setSize(200, 200);
        traJParametersWindow.frame.setDefaultCloseOperation(2);
        final JPanel panel = new JPanel();
        traJParametersWindow.frame.add(panel);
        placeComponents(panel);
        traJParametersWindow.frame.setVisible(true);
    }
    
    private static void placeComponents(final JPanel panel) {
        panel.setLayout(new BoxLayout(panel, 1));
        final JLabel minLengthLabel = new JLabel("Min. Tracklength: ");
        panel.add(minLengthLabel);
        (traJParametersWindow.minLengthText = new JTextField("10", 5)).setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_MIN_TRACK, ""));
        panel.add(traJParametersWindow.minLengthText);
        final JLabel windowLabel = new JLabel("Windowsize (SPOT positions): ");
        panel.add(windowLabel);
        (traJParametersWindow.windowText = new JTextField("5", 5)).setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_WINDOW, ""));
        panel.add(traJParametersWindow.windowText);
        final JLabel minSegLabel = new JLabel("Min.Segment Length: ");
        panel.add(minSegLabel);
        (traJParametersWindow.minSegText = new JTextField("5", 5)).setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_MIN_SEGMENT, ""));
        panel.add(traJParametersWindow.minSegText);
        (traJParametersWindow.okButton = new JButton("OK")).setBounds(10, 80, 80, 25);
        panel.add(traJParametersWindow.okButton);
        traJParametersWindow.okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                traJParametersWindow.keyWord = "keyword";
                traJParametersWindow.minLengthTextS = traJParametersWindow.minLengthText.getText();
                traJParametersWindow.windowTextS = traJParametersWindow.windowText.getText();
                traJParametersWindow.minSegTextS = traJParametersWindow.minSegText.getText();
                traJParametersWindow.frame.setVisible(false);
            }
        });
    }
}
