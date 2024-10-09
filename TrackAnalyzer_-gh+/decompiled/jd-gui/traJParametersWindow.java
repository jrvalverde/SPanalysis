/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class traJParametersWindow
/*    */ {
/*    */   static JButton okButton;
/*    */   static String minLengthTextS;
/*    */   static String windowTextS;
/*    */   static String minSegTextS;
/*    */   static String keyWord;
/*    */   static JFrame frame;
/*    */   static JTextField minLengthText;
/*    */   static JTextField windowText;
/*    */   static JTextField minSegText;
/*    */   
/*    */   public void run(String args) {
/* 23 */     frame = new JFrame("TraJ Classifier Parameters");
/* 24 */     frame.setSize(200, 200);
/* 25 */     frame.setDefaultCloseOperation(2);
/*    */     
/* 27 */     JPanel panel = new JPanel();
/* 28 */     frame.add(panel);
/* 29 */     placeComponents(panel);
/*    */     
/* 31 */     frame.setVisible(true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static void placeComponents(JPanel panel) {
/* 37 */     panel.setLayout(new BoxLayout(panel, 1));
/*    */     
/* 39 */     JLabel minLengthLabel = new JLabel("Min. Tracklength: ");
/* 40 */     panel.add(minLengthLabel);
/*    */     
/* 42 */     minLengthText = new JTextField("10", 5);
/* 43 */     minLengthText.setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_MIN_TRACK, ""));
/* 44 */     panel.add(minLengthText);
/*    */     
/* 46 */     JLabel windowLabel = new JLabel("Windowsize (SPOT positions): ");
/* 47 */     panel.add(windowLabel);
/*    */     
/* 49 */     windowText = new JTextField("5", 5);
/* 50 */     windowText.setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_WINDOW, ""));
/* 51 */     panel.add(windowText);
/*    */     
/* 53 */     JLabel minSegLabel = new JLabel("Min.Segment Length: ");
/* 54 */     panel.add(minSegLabel);
/*    */     
/* 56 */     minSegText = new JTextField("5", 5);
/* 57 */     minSegText.setText(SPTBatch_.pref1.get(SPTBatch_.TRACKMATE_MIN_SEGMENT, ""));
/* 58 */     panel.add(minSegText);
/*    */     
/* 60 */     okButton = new JButton("OK");
/* 61 */     okButton.setBounds(10, 80, 80, 25);
/* 62 */     panel.add(okButton);
/* 63 */     okButton.addActionListener(new ActionListener() {
/*    */           public void actionPerformed(ActionEvent event) {
/* 65 */             traJParametersWindow.keyWord = "keyword";
/* 66 */             traJParametersWindow.minLengthTextS = traJParametersWindow.minLengthText.getText();
/* 67 */             traJParametersWindow.windowTextS = traJParametersWindow.windowText.getText();
/* 68 */             traJParametersWindow.minSegTextS = traJParametersWindow.minSegText.getText();
/* 69 */             traJParametersWindow.frame.setVisible(false);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/traJParametersWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */