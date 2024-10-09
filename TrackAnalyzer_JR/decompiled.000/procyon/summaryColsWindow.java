import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import checkable.CheckableItem;
import checkable.CheckedComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;

// 
// Decompiled by Procyon v0.5.36
// 

public class summaryColsWindow
{
    static JButton okButton;
    static JFrame frame;
    public static CheckedComboBox comboSpots;
    public static CheckedComboBox comboLinks;
    public static CheckedComboBox comboTracks;
    static String[] columnNamesSpot;
    static String[] columnNamesLinks;
    static String[] columnNamesTracks;
    public static CheckableItem[] itemsSpots;
    public static CheckableItem[] itemsLinks;
    public static CheckableItem[] itemsTracks;
    static int indexSLT;
    static JComboBox combo;
    
    static {
        summaryColsWindow.columnNamesSpot = null;
        summaryColsWindow.columnNamesLinks = null;
        summaryColsWindow.columnNamesTracks = null;
    }
    
    public void run(final String args) {
        (summaryColsWindow.frame = new JFrame("Configure Summary Outputs")).setSize(200, 150);
        summaryColsWindow.frame.setDefaultCloseOperation(2);
        final JPanel panel = new JPanel();
        summaryColsWindow.frame.add(panel);
        placeComponents(panel);
        summaryColsWindow.frame.setVisible(true);
    }
    
    private static void placeComponents(final JPanel panel) {
        panel.setLayout(new BoxLayout(panel, 1));
        final JLabel paramLabel = new JLabel("Parameters: ");
        panel.add(paramLabel);
        (summaryColsWindow.combo = new JComboBox()).addItem("Spots");
        summaryColsWindow.combo.addItem("Links");
        summaryColsWindow.combo.addItem("Tracks");
        summaryColsWindow.combo.setSelectedIndex(2);
        summaryColsWindow.combo.setBounds(50, 50, 90, 20);
        if (SPTBatch_.checkboxSubBg.isSelected()) {
            summaryColsWindow.columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1", "Intensity-Bg Subtract" };
        }
        if (!SPTBatch_.checkboxSubBg.isSelected()) {
            summaryColsWindow.columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1" };
        }
        summaryColsWindow.itemsSpots = new CheckableItem[summaryColsWindow.columnNamesSpot.length];
        summaryColsWindow.columnNamesLinks = new String[] { "TRACK_ID", "SPOT_SOURCE_ID", "SPOT_TARGET_ID", "LINK_COST", "DIRECTIONAL_CHANGE_RATE", "SPEED", "DISPLACEMENT", "EDGE_TIME", "EDGE_X_LOCATION", "EDGE_Y_LOCATION", "EDGE_Z_LOCATION", "MANUAL_EGE_COLOR" };
        summaryColsWindow.itemsLinks = new CheckableItem[summaryColsWindow.columnNamesLinks.length];
        if (SPTBatch_.checkboxSubBg.isSelected()) {
            if (SPTBatch_.checkTracks.isSelected()) {
                summaryColsWindow.columnNamesTracks = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + ")", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement" };
            }
            if (!SPTBatch_.checkTracks.isSelected()) {
                summaryColsWindow.columnNamesTracks = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement" };
            }
        }
        if (!SPTBatch_.checkboxSubBg.isSelected()) {
            summaryColsWindow.columnNamesTracks = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement" };
        }
        summaryColsWindow.itemsTracks = new CheckableItem[summaryColsWindow.columnNamesTracks.length];
        for (int i = 0; i < summaryColsWindow.columnNamesSpot.length; ++i) {
            summaryColsWindow.itemsSpots[i] = new CheckableItem(summaryColsWindow.columnNamesSpot[i], false);
        }
        for (int i = 0; i < summaryColsWindow.columnNamesLinks.length; ++i) {
            summaryColsWindow.itemsLinks[i] = new CheckableItem(summaryColsWindow.columnNamesLinks[i], false);
        }
        for (int i = 0; i < summaryColsWindow.columnNamesTracks.length; ++i) {
            summaryColsWindow.itemsTracks[i] = new CheckableItem(summaryColsWindow.columnNamesTracks[i], false);
        }
        (summaryColsWindow.comboSpots = new CheckedComboBox((ComboBoxModel<E>)new DefaultComboBoxModel<CheckableItem>((E[])summaryColsWindow.itemsSpots))).setOpaque(true);
        summaryColsWindow.comboSpots.setToolTipText("Select parameter to build the summary for links.");
        summaryColsWindow.comboSpots.setSelectedItem(summaryColsWindow.itemsSpots[0]);
        (summaryColsWindow.comboLinks = new CheckedComboBox((ComboBoxModel<E>)new DefaultComboBoxModel<CheckableItem>((E[])summaryColsWindow.itemsLinks))).setOpaque(true);
        summaryColsWindow.comboLinks.setToolTipText("Select parameter to build the summary for links.");
        summaryColsWindow.comboLinks.setSelectedItem(summaryColsWindow.itemsLinks[0]);
        (summaryColsWindow.comboTracks = new CheckedComboBox((ComboBoxModel<E>)new DefaultComboBoxModel<CheckableItem>((E[])summaryColsWindow.itemsTracks))).setOpaque(true);
        summaryColsWindow.comboTracks.setToolTipText("Select parameter to build the summary for tracks.");
        summaryColsWindow.comboTracks.setSelectedItem(summaryColsWindow.itemsTracks[0]);
        panel.add(summaryColsWindow.combo);
        final JLabel columnParamLabel = new JLabel("Column Parameter: ");
        panel.add(columnParamLabel);
        (summaryColsWindow.okButton = new JButton("OK")).setBounds(10, 80, 80, 25);
        panel.add(summaryColsWindow.okButton);
        summaryColsWindow.okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                summaryColsWindow.indexSLT = summaryColsWindow.combo.getSelectedIndex();
                summaryColsWindow.frame.setVisible(false);
            }
        });
        summaryColsWindow.combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (panel.getComponents().length == 5) {
                    panel.remove(3);
                }
                if (summaryColsWindow.combo.getSelectedIndex() == 0) {
                    panel.add(summaryColsWindow.comboSpots, 3);
                }
                if (summaryColsWindow.combo.getSelectedIndex() == 1) {
                    panel.add(summaryColsWindow.comboLinks, 3);
                }
                if (summaryColsWindow.combo.getSelectedIndex() == 2) {
                    panel.add(summaryColsWindow.comboTracks, 3);
                }
            }
        });
    }
}
