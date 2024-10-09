/*     */ import checkable.CheckableItem;
/*     */ import checkable.CheckedComboBox;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class summaryColsWindow
/*     */ {
/*     */   static JButton okButton;
/*     */   static JFrame frame;
/*     */   public static CheckedComboBox comboSpots;
/*     */   public static CheckedComboBox comboLinks;
/*     */   public static CheckedComboBox comboTracks;
/*  25 */   static String[] columnNamesSpot = null; static String[] columnNamesLinks = null; static String[] columnNamesTracks = null;
/*     */   
/*     */   public static CheckableItem[] itemsSpots;
/*     */   
/*     */   public static CheckableItem[] itemsLinks;
/*     */   public static CheckableItem[] itemsTracks;
/*     */   static int indexSLT;
/*     */   static JComboBox combo;
/*     */   
/*     */   public void run(String args) {
/*  35 */     frame = new JFrame("Configure Summary Outputs");
/*  36 */     frame.setSize(200, 150);
/*  37 */     frame.setDefaultCloseOperation(2);
/*     */     
/*  39 */     JPanel panel = new JPanel();
/*  40 */     frame.add(panel);
/*  41 */     placeComponents(panel);
/*     */     
/*  43 */     frame.setVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void placeComponents(final JPanel panel) {
/*  49 */     panel.setLayout(new BoxLayout(panel, 1));
/*     */     
/*  51 */     JLabel paramLabel = new JLabel("Parameters: ");
/*  52 */     panel.add(paramLabel);
/*     */     
/*  54 */     combo = new JComboBox();
/*  55 */     combo.addItem("Spots");
/*  56 */     combo.addItem("Links");
/*  57 */     combo.addItem("Tracks");
/*  58 */     combo.setSelectedIndex(2);
/*  59 */     combo.setBounds(50, 50, 90, 20);
/*  60 */     if (SPTBatch_.checkboxSubBg.isSelected())
/*     */     {
/*  62 */       columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", 
/*  63 */           "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", 
/*  64 */           "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", 
/*  65 */           "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1", "Intensity-Bg Subtract" };
/*     */     }
/*  67 */     if (!SPTBatch_.checkboxSubBg.isSelected())
/*     */     {
/*  69 */       columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", 
/*  70 */           "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", 
/*  71 */           "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", 
/*  72 */           "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1" };
/*     */     }
/*  74 */     itemsSpots = new CheckableItem[columnNamesSpot.length];
/*  75 */     columnNamesLinks = new String[] { "TRACK_ID", "SPOT_SOURCE_ID", "SPOT_TARGET_ID", "LINK_COST", 
/*  76 */         "DIRECTIONAL_CHANGE_RATE", "SPEED", "DISPLACEMENT", "EDGE_TIME", "EDGE_X_LOCATION", "EDGE_Y_LOCATION", 
/*  77 */         "EDGE_Z_LOCATION", "MANUAL_EGE_COLOR" };
/*  78 */     itemsLinks = new CheckableItem[columnNamesLinks.length];
/*  79 */     if (SPTBatch_.checkboxSubBg.isSelected()) {
/*     */       
/*  81 */       if (SPTBatch_.checkTracks.isSelected()) {
/*  82 */         columnNamesTracks = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", 
/*  83 */             "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", 
/*  84 */             "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", 
/*  85 */             "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", 
/*  86 */             "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/*  87 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", 
/*  88 */             "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", 
/*  89 */             "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/*  90 */             "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + ")", 
/*  91 */             "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/*  92 */             "sMSS Movement" };
/*     */       }
/*  94 */       if (!SPTBatch_.checkTracks.isSelected())
/*  95 */         columnNamesTracks = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", 
/*  96 */             "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", 
/*  97 */             "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", 
/*  98 */             "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", 
/*  99 */             "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 100 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", 
/* 101 */             "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", 
/* 102 */             "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 103 */             "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 104 */             "sMSS Movement" }; 
/*     */     } 
/* 106 */     if (!SPTBatch_.checkboxSubBg.isSelected()) {
/* 107 */       columnNamesTracks = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", 
/* 108 */           "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 109 */           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", 
/* 110 */           "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", 
/* 111 */           "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 112 */           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", 
/* 113 */           "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Diffusion Coef.", "D1-4", 
/* 114 */           "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement" };
/*     */     }
/* 116 */     itemsTracks = new CheckableItem[columnNamesTracks.length]; int i;
/* 117 */     for (i = 0; i < columnNamesSpot.length; i++) {
/* 118 */       itemsSpots[i] = new CheckableItem(columnNamesSpot[i], false);
/*     */     }
/* 120 */     for (i = 0; i < columnNamesLinks.length; i++) {
/* 121 */       itemsLinks[i] = new CheckableItem(columnNamesLinks[i], false);
/*     */     }
/* 123 */     for (i = 0; i < columnNamesTracks.length; i++) {
/* 124 */       itemsTracks[i] = new CheckableItem(columnNamesTracks[i], false);
/*     */     }
/* 126 */     comboSpots = new CheckedComboBox(new DefaultComboBoxModel<>(itemsSpots));
/* 127 */     comboSpots.setOpaque(true);
/* 128 */     comboSpots.setToolTipText("Select parameter to build the summary for links.");
/* 129 */     comboSpots.setSelectedItem(itemsSpots[0]);
/*     */     
/* 131 */     comboLinks = new CheckedComboBox(new DefaultComboBoxModel<>(itemsLinks));
/* 132 */     comboLinks.setOpaque(true);
/* 133 */     comboLinks.setToolTipText("Select parameter to build the summary for links.");
/* 134 */     comboLinks.setSelectedItem(itemsLinks[0]);
/*     */     
/* 136 */     comboTracks = new CheckedComboBox(new DefaultComboBoxModel<>(itemsTracks));
/* 137 */     comboTracks.setOpaque(true);
/* 138 */     comboTracks.setToolTipText("Select parameter to build the summary for tracks.");
/* 139 */     comboTracks.setSelectedItem(itemsTracks[0]);
/*     */     
/* 141 */     panel.add(combo);
/*     */     
/* 143 */     JLabel columnParamLabel = new JLabel("Column Parameter: ");
/* 144 */     panel.add(columnParamLabel);
/*     */     
/* 146 */     okButton = new JButton("OK");
/* 147 */     okButton.setBounds(10, 80, 80, 25);
/* 148 */     panel.add(okButton);
/* 149 */     okButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent event) {
/* 151 */             summaryColsWindow.indexSLT = summaryColsWindow.combo.getSelectedIndex();
/* 152 */             summaryColsWindow.frame.setVisible(false);
/*     */           }
/*     */         });
/*     */     
/* 156 */     combo.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 159 */             if ((panel.getComponents()).length == 5)
/* 160 */               panel.remove(3); 
/* 161 */             if (summaryColsWindow.combo.getSelectedIndex() == 0) {
/* 162 */               panel.add((Component)summaryColsWindow.comboSpots, 3);
/*     */             }
/* 164 */             if (summaryColsWindow.combo.getSelectedIndex() == 1) {
/* 165 */               panel.add((Component)summaryColsWindow.comboLinks, 3);
/*     */             }
/* 167 */             if (summaryColsWindow.combo.getSelectedIndex() == 2)
/* 168 */               panel.add((Component)summaryColsWindow.comboTracks, 3); 
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/summaryColsWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */