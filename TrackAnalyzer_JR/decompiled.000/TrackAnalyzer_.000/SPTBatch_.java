import checkable.CheckableItem;
import checkable.CheckedComboBox;
import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.FeatureModel;
import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.SpotRoi;
import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.action.PlotNSpotsVsTimeAction;
import fiji.plugin.trackmate.detection.DogDetectorFactory;
import fiji.plugin.trackmate.detection.LogDetectorFactory;
import fiji.plugin.trackmate.features.EdgeCollectionDataset;
import fiji.plugin.trackmate.features.FeatureFilter;
import fiji.plugin.trackmate.features.FeatureUtils;
import fiji.plugin.trackmate.features.ModelDataset;
import fiji.plugin.trackmate.features.SpotCollectionDataset;
import fiji.plugin.trackmate.features.TrackCollectionDataset;
import fiji.plugin.trackmate.graph.ConvexBranchesDecomposition;
import fiji.plugin.trackmate.graph.TimeDirectedNeighborIndex;
import fiji.plugin.trackmate.graph.ConvexBranchesDecomposition.TrackBranchDecomposition;
import fiji.plugin.trackmate.gui.Fonts;
import fiji.plugin.trackmate.gui.displaysettings.Colormap;
import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings;
import fiji.plugin.trackmate.gui.displaysettings.DisplaySettingsIO;
import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings.TrackDisplayMode;
import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings.TrackMateObject;
import fiji.plugin.trackmate.io.TmXmlReader;
import fiji.plugin.trackmate.tracking.LAPUtils;
import fiji.plugin.trackmate.tracking.ManualTrackerFactory;
import fiji.plugin.trackmate.tracking.kalman.KalmanTrackerFactory;
import fiji.plugin.trackmate.tracking.sparselap.SimpleSparseLAPTrackerFactory;
import fiji.plugin.trackmate.tracking.sparselap.SparseLAPTrackerFactory;
import fiji.plugin.trackmate.util.ExportableChartPanel;
import fiji.plugin.trackmate.util.TMUtils;
import fiji.plugin.trackmate.visualization.FeatureColorGenerator;
import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
import fiji.plugin.trackmate.visualization.hyperstack.HyperStackDisplayer;
import fiji.plugin.trackmate.visualization.hyperstack.SpotOverlay;
import fiji.plugin.trackmate.visualization.hyperstack.TrackOverlay;
import fiji.plugin.trackmate.visualization.table.TablePanel;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.OvalRoi;
import ij.gui.Overlay;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.gui.ShapeRoi;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.ZProjector;
import ij.plugin.frame.RoiManager;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel.Shape;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jwizardcomponent.JWizardComponents;
import jwizardcomponent.JWizardPanel;
import jwizardcomponent.Utilities;
import jwizardcomponent.frame.JWizardFrame;
import loci.plugins.in.DisplayHandler;
import loci.plugins.in.ImportProcess;
import loci.plugins.in.ImporterOptions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import smileModified.GaussianMixtureModified;

public class SPTBatch_ {
   static String csvPath;
   static String imgTitle;
   static String RADIUS;
   private String imagesPath;
   private String zstart;
   private String zend;
   private String ystart;
   private String yend;
   private String xstart;
   private String xend;
   private String tstart;
   private String tend;
   private String THRESHOLD;
   private String TARGET_CHANNEL;
   private String DO_SUBPIXEL_LOCALIZATION;
   private String DO_MEDIAN_FILTERING;
   private String DETECTOR_NAME;
   private String NSPLIT;
   private String DOWNSAMPLE_FACTOR;
   private String initialSpotFilter;
   private String TRACKER_NAME;
   private String CUTOFF_PERCENTILE;
   private String ALTERNATIVE_LINKING_COST_FACTOR;
   private String LINKING_MAX_DISTANCE;
   private String MAX_FRAME_GAP;
   private String MAX_DISTANCE;
   private String ALLOW_GAP_CLOSING;
   private String SPLITTING_MAX_DISTANCE;
   private String ALLOW_TRACK_SPLITTING;
   private String MERGING_MAX_DISTANCE;
   private String ALLOW_TRACK_MERGING;
   private String BLOCKING_VALUE;
   private String TransfPath;
   private String enableImages;
   private String enableLog;
   private String enableXML;
   private String enableRois;
   private String xmlPath;
   private String checkEnable;
   private String enableCsv;
   private String TRACKMATE_TRANSF_PATH;
   private String TRACKMATE_IMAGES_PATH;
   private String TRACKMATE_CSV_PATH;
   private String TRACKMATE_XML_PATH;
   private String TRACKMATE_TXT_PATH;
   private String TRACKMATE_OUT_PATH;
   private String xSelectedSpot;
   private String ySelectedSpot;
   private String xSelectedLink;
   private String ySelectedLink;
   private String xSelectedTrack;
   private String ySelectedTrack;
   private String enablePlotF;
   private String enableSql;
   private String enableST;
   private String enableSpotTable;
   private String enableLinkTable;
   private String enableTrackTable;
   private String enableBranchTable;
   private String enablePlot;
   private String titleExportLink;
   private String titleExportTrack;
   private String linkingNames;
   private String linkingValues;
   private String initialFilterFeature;
   private String initialFilterValue;
   private String initialFilterAbove;
   private String selectedOption = "N";
   private String enableCovariance;
   private String enableRegression;
   private String enableKalman;
   private String trackFilterFeature;
   private String trackFilterValue;
   private String trackFilterAbove;
   private String TRACKMATE_MIN_SPOT;
   private String TRACKMATE_MAX_SPOT;
   private String TRACKMATE_LENGTH_TH;
   private String TRACKMATE_DIFF_TH;
   public static String TRACKMATE_MIN_TRACK;
   public static String TRACKMATE_WINDOW;
   public static String TRACKMATE_MIN_SEGMENT;
   public static String TRACKMATE_COLUMN_PARAM;
   String[] columnsMovements = new String[]{"Total Tracks.", "Long Tracks", "Long Confined", "Long Unidirectional Ballistic", "Long Free.", "Long Direct.", "Immob"};
   private int[] dims;
   private JFreeChart chart;
   private JFrame frameChartNS;
   private Map<String, Double> hm;
   public static Thread mainProcess;
   public static final int PANEL_FIRST = 0;
   public static final int PANEL_CHOOSER = 1;
   public static final int PANEL_OPTION_A = 2;
   public static final int PANEL_OPTION_B = 3;
   public static final int PANEL_LAST = 4;
   private ImagePlus imp;
   private ImagePlus capture;
   private JWizardPanel panel;
   private JButton nextButton;
   private JButton backButton;
   private JButton cancelButton;
   private JButton finishButton;
   private JButton buttonCsv;
   private JButton trajButton;
   private JButton summaryButton;
   private CheckedComboBox comboP;
   public static JCheckBox checkbox2;
   public static JCheckBox checkbox3;
   public static JCheckBox checkbox4;
   public static JCheckBox checkboxRoi;
   public static JCheckBox checkboxPlot;
   public static JCheckBox checkPlot;
   public static JCheckBox checkboxAnalysis;
   public static JCheckBox checkboxST;
   public static JCheckBox ESP;
   public static JCheckBox ELP;
   public static JCheckBox ETP;
   public static JCheckBox checkbox1;
   public static JCheckBox checkboxDiff;
   public static JCheckBox checkboxSP;
   public static JCheckBox checkboxMSD;
   public static JCheckBox checkDispSpots;
   public static JCheckBox checkDispSpotName;
   public static JCheckBox checkDispTracks;
   public static JCheckBox checkSummary;
   public static JCheckBox checkPBS;
   public static JCheckBox checkCluster;
   public static JCheckBox checkMonomer;
   public static JCheckBox checkTracks;
   public static JCheckBox checkboxSubBg;
   public static JCheckBox checkExcludeTracks;
   private int itemCheckPlot;
   private Button buttonXMLL;
   private Button buttonImg;
   public static Preferences pref1;
   private Settings settings;
   public static int a;
   public static int minTracksJTF;
   public static int maxTracksJTF;
   public static int thLengthJTF;
   public static int i;
   private double thD14JTF;
   private JFrame f;
   private JWizardFrame wizard;
   private TrackMate trackmate;
   private Logger loggers;
   private ResultsTable spotTable;
   private ResultsTable linkTable;
   private ResultsTable trackTable;
   private ResultsTable rtSpot;
   private ResultsTable rtLink;
   private ResultsTable rtTrack;
   private final String SPOT_TABLE_NAME = "Spots in tracks statistics";
   private final String EDGE_TABLE_NAME = "Links in tracks statistics";
   private final String TRACK_TABLE_NAME = "Track statistics";
   private final String ID_COLUMN = "ID";
   private Spot[] source;
   public static Model model;
   public static SelectionModel selectionModel;
   public static ImagePlus imps;
   public static ImagePlus impsSubBg;
   public ImagePlus impsNano;
   private HyperStackDisplayer displayer;
   private String[] imageTitles;
   private Set<Spot> track;
   private static final String TRACK_ID_COLUMN = "TRACK_ID";
   private PlotNSpotsVsTimeAction plot;
   private JFreeChart chartSpot;
   private JFreeChart chartLink;
   private JFreeChart chartTrack;
   private int itemPlot2;
   private int totalTracksDef;
   private int longTracksDef;
   private int longConfinedDef;
   private int longUniBalDef;
   private int longFreeDef;
   private int longDirectDef;
   private int immobDef;
   private int shortTracksDef;
   private int shortConfinedDef;
   private int shortAnomDef;
   private int shortFreeDef;
   private int shortDirectDef;
   public static File directImages;
   public static File directChemo;
   public static File directDiff;
   public static File directMSS;
   public static File directCluster;
   private JProgressBar progressBar;
   public static File directSummary;
   public static File fileXmlInitial;
   public static File directPBS;
   public static File directSPT;
   static JTextArea taskOutput;
   private JComboBox comboSpotsX;
   private JComboBox comboSpotsY;
   private JComboBox comboLinkX;
   private JComboBox comboLinkY;
   private JComboBox comboTrackX;
   private JComboBox comboTrackY;
   private JComboBox comboSubBg;
   private JComboBox comboDispTracks;
   PerTrackFeatureColorGenerator tcg;
   Calibration calibration;
   static JTable trackJTable;
   static JTable linkJTable;
   public ImagePlus[] lifs;
   ArrayList<Float> xPosition;
   ArrayList<Float> yPosition;
   ArrayList<Integer> tracksID;
   ArrayList<Integer> framePosition;
   static JTextField chemoScaling;
   static JTextField minTracks;
   static JTextField maxTracks;
   static JTextField thLength;
   static JTextField thD14;
   static JTextField monomerField;
   public static RoiManager roiManager;
   public static ImagePlus impMaxProject;
   public static ImagePlus impMainRoi;
   public static ImagePlus[] slices;
   public static double[] slicesIntensityBg;
   public static double[] slicesIntensitySpot;
   static JTable tableSpot = null;
   static String[] columnNamesTrack;
   static String[] columnNamesSpot;
   List<List<List<String>>> dataAllItemsDef = new ArrayList();
   ArrayList<Integer> indexes;
   static List<Integer> nOfTracks;
   List<String> selectedItems = null;
   static double fps;
   ResultsTable[] rtSpots = null;
   ResultsTable[] rtLinks = null;
   ResultsTable[] rtTracks = null;
   List<Boolean> excludeTrack;
   DisplaySettings ds;
   ResultsTable rtTrackPerImage;
   private static final String TRACK_ID = "TRACK_ID";
   private static final String N_PREDECESSORS = "N_PREDECESSORS";
   private static final String N_SUCCESSORS = "N_SUCCESSORS";
   private static final String DELTA_T = "DELTA_T";
   private static final String DISTANCE = "DISTANCE";
   private static final String MEAN_VELOCITY = "MEAN_VELOCITY";
   private static final String FIRST = "FIRST";
   private static final String LAST = "LAST";
   private static final List<String> BRANCH_FEATURES = Arrays.asList("TRACK_ID", "N_PREDECESSORS", "N_SUCCESSORS", "DELTA_T", "DISTANCE", "MEAN_VELOCITY", "FIRST", "LAST");
   private static final Map<String, String> BRANCH_FEATURES_NAMES = new HashMap();
   private static final Map<String, String> BRANCH_FEATURES_SHORTNAMES = new HashMap();
   private static final Map<String, Boolean> BRANCH_FEATURES_ISINTS = new HashMap();
   private static final Map<String, Dimension> BRANCH_FEATURES_DIMENSIONS = new HashMap();

   static {
      BRANCH_FEATURES_NAMES.put("TRACK_ID", "Track ID");
      BRANCH_FEATURES_SHORTNAMES.put("TRACK_ID", "Track ID");
      BRANCH_FEATURES_ISINTS.put("TRACK_ID", Boolean.TRUE);
      BRANCH_FEATURES_DIMENSIONS.put("TRACK_ID", Dimension.NONE);
      BRANCH_FEATURES_NAMES.put("N_PREDECESSORS", "Track ID");
      BRANCH_FEATURES_SHORTNAMES.put("N_PREDECESSORS", "N predecessors");
      BRANCH_FEATURES_ISINTS.put("N_PREDECESSORS", Boolean.TRUE);
      BRANCH_FEATURES_DIMENSIONS.put("N_PREDECESSORS", Dimension.NONE);
      BRANCH_FEATURES_NAMES.put("N_SUCCESSORS", "Track ID");
      BRANCH_FEATURES_SHORTNAMES.put("N_SUCCESSORS", "N successors");
      BRANCH_FEATURES_ISINTS.put("N_SUCCESSORS", Boolean.TRUE);
      BRANCH_FEATURES_DIMENSIONS.put("N_SUCCESSORS", Dimension.NONE);
      BRANCH_FEATURES_NAMES.put("DELTA_T", "Branch duration");
      BRANCH_FEATURES_SHORTNAMES.put("DELTA_T", "Delta T");
      BRANCH_FEATURES_ISINTS.put("DELTA_T", Boolean.FALSE);
      BRANCH_FEATURES_DIMENSIONS.put("DELTA_T", Dimension.TIME);
      BRANCH_FEATURES_NAMES.put("DISTANCE", "Distance traveled");
      BRANCH_FEATURES_SHORTNAMES.put("DISTANCE", "Dist");
      BRANCH_FEATURES_ISINTS.put("DISTANCE", Boolean.FALSE);
      BRANCH_FEATURES_DIMENSIONS.put("DISTANCE", Dimension.LENGTH);
      BRANCH_FEATURES_NAMES.put("MEAN_VELOCITY", "Mean velocity");
      BRANCH_FEATURES_SHORTNAMES.put("MEAN_VELOCITY", "Mean V");
      BRANCH_FEATURES_ISINTS.put("MEAN_VELOCITY", Boolean.FALSE);
      BRANCH_FEATURES_DIMENSIONS.put("MEAN_VELOCITY", Dimension.VELOCITY);
      BRANCH_FEATURES_NAMES.put("FIRST", "First spot ID");
      BRANCH_FEATURES_SHORTNAMES.put("FIRST", "First ID");
      BRANCH_FEATURES_ISINTS.put("FIRST", Boolean.TRUE);
      BRANCH_FEATURES_DIMENSIONS.put("FIRST", Dimension.NONE);
      BRANCH_FEATURES_NAMES.put("LAST", "Last spot ID");
      BRANCH_FEATURES_SHORTNAMES.put("LAST", "Last ID");
      BRANCH_FEATURES_ISINTS.put("LAST", Boolean.TRUE);
      BRANCH_FEATURES_DIMENSIONS.put("LAST", Dimension.NONE);
   }

   public SPTBatch_(String xmlPath, String imagesPath) {
      this.xmlPath = xmlPath;
      this.imagesPath = imagesPath;
   }

   public void run(String arg) {
      this.TRACKMATE_TRANSF_PATH = "transf_path";
      this.TRACKMATE_IMAGES_PATH = "images_path";
      this.TRACKMATE_CSV_PATH = "csv_path";
      this.TRACKMATE_XML_PATH = "xml_path";
      this.TRACKMATE_TXT_PATH = "txt_path";
      this.TRACKMATE_OUT_PATH = "out_path";
      this.TRACKMATE_MIN_SPOT = "min_spot";
      this.TRACKMATE_MAX_SPOT = "max_spot";
      this.TRACKMATE_LENGTH_TH = "length_th";
      this.TRACKMATE_DIFF_TH = "diff_th";
      TRACKMATE_MIN_TRACK = "min_track";
      TRACKMATE_WINDOW = "window";
      TRACKMATE_MIN_SEGMENT = "min_segment";
      TRACKMATE_COLUMN_PARAM = "column_param";
      pref1 = Preferences.userRoot();
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
      } catch (UnsupportedLookAndFeelException var6) {
      } catch (ClassNotFoundException var7) {
      } catch (InstantiationException var8) {
      } catch (IllegalAccessException var9) {
      }

      this.wizard = new JWizardFrame();
      this.wizard.setTitle("SPT-Batch");
      this.panel = new SPTBatch_.FirstWizardPanel(this.wizard.getWizardComponents());
      this.wizard.getWizardComponents().addWizardPanel(0, this.panel);
      this.panel = new SPTBatch_.ChooserWizardPanel(this.wizard.getWizardComponents());
      this.wizard.getWizardComponents().addWizardPanel(1, this.panel);
      this.panel = new SPTBatch_.OptionWizardPanel(this.wizard.getWizardComponents(), "A");
      this.wizard.getWizardComponents().addWizardPanel(2, this.panel);
      this.panel = new SPTBatch_.OptionWizardPanel(this.wizard.getWizardComponents(), "B");
      this.wizard.getWizardComponents().addWizardPanel(3, this.panel);
      this.panel = new SPTBatch_.LastWizardPanel(this.wizard.getWizardComponents());
      this.wizard.getWizardComponents().addWizardPanel(4, this.panel);
      this.wizard.getWizardComponents().removeWizardPanel(0);
      this.wizard.setSize(550, 700);
      Utilities.centerComponentOnScreen(this.wizard);
      this.wizard.setResizable(false);
      this.wizard.setVisible(true);
      mainProcess = new Thread(new Runnable() {
         public void run() {
            SPTBatch_.this.xSelectedSpot = SPTBatch_.this.comboSpotsX.getSelectedItem().toString();
            SPTBatch_.this.ySelectedSpot = SPTBatch_.this.comboSpotsY.getSelectedItem().toString();
            SPTBatch_.this.xSelectedLink = SPTBatch_.this.comboLinkX.getSelectedItem().toString();
            SPTBatch_.this.ySelectedLink = SPTBatch_.this.comboLinkY.getSelectedItem().toString();
            SPTBatch_.this.xSelectedTrack = SPTBatch_.this.comboTrackX.getSelectedItem().toString();
            SPTBatch_.this.ySelectedTrack = SPTBatch_.this.comboTrackY.getSelectedItem().toString();
            SPTBatch_.fileXmlInitial = new File(SPTBatch_.this.xmlPath);
            File imageFolder = new File(SPTBatch_.this.imagesPath);
            final File[] listOfFiles = imageFolder.listFiles();
            SPTBatch_.this.imageTitles = new String[listOfFiles.length];
            File[] filesXML = new File[listOfFiles.length];

            for(int u = 0; u < filesXML.length; ++u) {
               filesXML[u] = new File(SPTBatch_.this.xmlPath);
            }

            Object[] columHeadersFinalSpot = new Object[]{"IMAGE_TITLE", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MEAN_INTENSITY", "MEDIAN_INTENSITY", "MIN_INTENSITY", "MAX_INTENSITY", "TOTAL_INTENSITY", "STANDARD_DEVIATION", "CONTRAST", "SNR", "ESTIMATED_DIAMETER", "MORPHOLOGY", "ELLIPSOIDFIT_SEMIAXISLENGTH_C", "ELLIPSOIDFIT_SEMIAXISLENGTH_B", "ELLIPSOIDFIT_SEMIAXISLENGTH_A", "ELLIPSOIDFIT_AXISPHI_C", "ELLIPSOIDFIT_AXISPHI_B", "ELLIPSOIDFIT_AXISPHI_A", "ELLIPSOIDFIT_AXISTHETA_C", "ELLIPSOIDFIT_AXISTHETA_B", "ELLIPSOIDFIT_AXISTHETA_A"};
            Object[] columHeadersFinalLink = new Object[]{"IMAGE_TITLE", "LINK_COST", "EDGE_TIME", "EDGE_X_LOCATION", "EDGE_Y_LOCATION", "EDGE_Z_LOCATION", "VELOCITY", "DISPLACEMENT"};
            Object[] columHeadersFinalTrack = new Object[]{"IMAGE_TITLE", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_INDEX", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "NUMBER_SPOTS", "NUMBER_GAPS", "LONGEST_GAP", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "TRACK_MEAN_QUALITY", "TRACK_MAX_QUALITY", "TRACK_MIN_QUALITY", "TRACK_MEDIAN_QUALITY", "TRACK_STD_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "TOTAL_ABSOLUTE_ANGLE_XY", "TOTAL_ABSOLUTE_ANGLE_YZ", "TOTAL_ABSOLUTE_ANGLE_ZX", "CONFINMENT_RATIO"};
            SPTBatch_.this.rtSpot = new ResultsTable(SPTBatch_.this.imageTitles.length);
            SPTBatch_.this.rtLink = new ResultsTable(SPTBatch_.this.imageTitles.length);
            SPTBatch_.this.rtTrack = new ResultsTable(SPTBatch_.this.imageTitles.length);
            SPTBatch_.this.rtSpots = new ResultsTable[listOfFiles.length];
            SPTBatch_.this.rtLinks = new ResultsTable[listOfFiles.length];
            SPTBatch_.this.rtTracks = new ResultsTable[listOfFiles.length];

            int MAX;
            for(MAX = 0; MAX < columHeadersFinalSpot.length; ++MAX) {
               SPTBatch_.this.rtSpot.setHeading(MAX, (String)columHeadersFinalSpot[MAX]);
            }

            for(MAX = 0; MAX < columHeadersFinalLink.length; ++MAX) {
               SPTBatch_.this.rtLink.setHeading(MAX, (String)columHeadersFinalLink[MAX]);
            }

            for(MAX = 0; MAX < columHeadersFinalTrack.length; ++MAX) {
               SPTBatch_.this.rtTrack.setHeading(MAX, (String)columHeadersFinalTrack[MAX]);
            }

            MAX = listOfFiles.length;
            JFrame frameAnalyzer = new JFrame("Analyzing...");
            final JProgressBar pb = new JProgressBar();
            pb.setMinimum(0);
            pb.setMaximum(MAX);
            pb.setStringPainted(true);
            SPTBatch_.taskOutput = new JTextArea(5, 20);
            SPTBatch_.taskOutput.setMargin(new Insets(5, 5, 5, 5));
            SPTBatch_.taskOutput.setEditable(false);
            DefaultCaret caret = (DefaultCaret)SPTBatch_.taskOutput.getCaret();
            caret.setUpdatePolicy(2);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, 1));
            panel.add(pb);
            panel.add(Box.createVerticalStrut(5));
            panel.add(new JScrollPane(SPTBatch_.taskOutput), "Center");
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            frameAnalyzer.getContentPane().add(panel);
            frameAnalyzer.setDefaultCloseOperation(2);
            frameAnalyzer.setSize(550, 500);
            frameAnalyzer.setVisible(true);
            SPTBatch_.this.selectedItems = new ArrayList();
            final int i;
            if (SPTBatch_.checkSummary.isSelected()) {
               if (summaryColsWindow.combo.getSelectedIndex() == 0) {
                  for(i = 0; i < summaryColsWindow.itemsSpots.length; ++i) {
                     if (summaryColsWindow.itemsSpots[i].isSelected()) {
                        SPTBatch_.this.selectedItems.add(summaryColsWindow.itemsSpots[i].text);
                     }
                  }
               }

               if (summaryColsWindow.combo.getSelectedIndex() == 1) {
                  for(i = 0; i < summaryColsWindow.itemsLinks.length; ++i) {
                     if (summaryColsWindow.itemsLinks[i].isSelected()) {
                        SPTBatch_.this.selectedItems.add(summaryColsWindow.itemsLinks[i].text);
                     }
                  }
               }

               if (summaryColsWindow.combo.getSelectedIndex() == 2) {
                  for(i = 0; i < summaryColsWindow.itemsTracks.length; ++i) {
                     if (summaryColsWindow.itemsTracks[i].isSelected()) {
                        SPTBatch_.this.selectedItems.add(summaryColsWindow.itemsTracks[i].text);
                     }
                  }
               }
            }

            for(SPTBatch_.i = 0; SPTBatch_.i < listOfFiles.length; ++SPTBatch_.i) {
               if (SPTBatch_.imps != null) {
                  SPTBatch_.imps.hide();
               }

               if (listOfFiles[SPTBatch_.i].isFile()) {
                  SPTBatch_.this.imageTitles[SPTBatch_.i] = listOfFiles[SPTBatch_.i].getName();
                  SPTBatch_.imgTitle = SPTBatch_.this.imageTitles[SPTBatch_.i];
               }

               i = SPTBatch_.i + 1;

               try {
                  SwingUtilities.invokeLater(new Runnable() {
                     public void run() {
                        pb.setValue(i);
                        SPTBatch_.taskOutput.append(String.format("Completed %f%% of task.\n", (double)i * 100.0D / (double)listOfFiles.length));
                     }
                  });
                  Thread.sleep(100L);
               } catch (InterruptedException var158) {
                  JOptionPane.showMessageDialog(frameAnalyzer, var158.getMessage());
               }

               if (listOfFiles[SPTBatch_.i].getName().contains(".lif")) {
                  SPTBatch_.this.lifs = SPTBatch_.openBF(SPTBatch_.this.imagesPath + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i], false, false, false, false, false, true);

                  for(int x = 0; x < SPTBatch_.this.lifs.length; ++x) {
                     SPTBatch_.imps = new ImagePlus(SPTBatch_.this.imagesPath + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i]);
                  }
               }

               if (!listOfFiles[SPTBatch_.i].getName().contains(".lif")) {
                  SPTBatch_.imps = new ImagePlus(SPTBatch_.this.imagesPath + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i]);
               }

               IJ.resetMinAndMax(SPTBatch_.imps);
               SPTBatch_.this.dims = SPTBatch_.imps.getDimensions();
               SPTBatch_.this.calibration = SPTBatch_.imps.getCalibration();
               SPTBatch_.fps = SPTBatch_.imps.getFileInfo().frameInterval;
               if (SPTBatch_.this.dims[4] == 1 && SPTBatch_.this.dims[3] > 1) {
                  SPTBatch_.imps.setDimensions(SPTBatch_.this.dims[2], SPTBatch_.this.dims[4], SPTBatch_.this.dims[3]);
                  SPTBatch_.this.calibration.frameInterval = SPTBatch_.this.calibration.frameInterval;
                  SPTBatch_.this.loggers = Logger.IJ_LOGGER;
               }

               SPTBatch_.impsSubBg = SPTBatch_.imps.duplicate();
               SPTBatch_.impsSubBg.setCalibration(SPTBatch_.this.calibration);
               SPTBatch_.directImages = new File(SPTBatch_.csvPath + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i].replaceAll("\\.tif+$", ""));
               boolean resultx;
               if (!SPTBatch_.directImages.exists()) {
                  SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directImages.getName());
                  resultx = false;

                  try {
                     SPTBatch_.directImages.mkdir();
                     resultx = true;
                  } catch (SecurityException var157) {
                  }

                  if (resultx) {
                     SPTBatch_.taskOutput.append("DIR created");
                  }
               }

               SPTBatch_.directSummary = new File(SPTBatch_.csvPath + File.separator + "Summary_Analysis");
               if (!SPTBatch_.directSummary.exists()) {
                  resultx = false;

                  try {
                     SPTBatch_.directSummary.mkdir();
                     resultx = true;
                  } catch (SecurityException var156) {
                  }

                  if (resultx) {
                     SPTBatch_.taskOutput.append("DIR created-Summary_Analysis");
                  }
               }

               SPTBatch_.directSPT = new File(SPTBatch_.csvPath + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i].replaceAll("\\.tif+$", "") + File.separator + "SPT_Analysis");
               if (!SPTBatch_.directSPT.exists()) {
                  resultx = false;

                  try {
                     SPTBatch_.directSPT.mkdir();
                     resultx = true;
                  } catch (SecurityException var155) {
                  }

                  if (resultx) {
                     SPTBatch_.taskOutput.append("DIR created-SPT_Analysis");
                  }
               }

               SPTBatch_.directPBS = new File(SPTBatch_.csvPath + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i].replaceAll("\\.tif+$", "") + File.separator + "Photobleaching_Analysis");
               if (SPTBatch_.checkPBS.isSelected() && !SPTBatch_.directPBS.exists()) {
                  resultx = false;

                  try {
                     SPTBatch_.directPBS.mkdir();
                     resultx = true;
                  } catch (SecurityException var154) {
                  }

                  if (resultx) {
                     SPTBatch_.taskOutput.append("DIR created-Photobleching_Analysis");
                  }
               }

               if (SPTBatch_.checkboxMSD.isSelected() == Boolean.TRUE) {
                  SPTBatch_.directMSS = new File(SPTBatch_.directImages.getAbsolutePath() + File.separator + "MSS_Analysis");
                  if (!SPTBatch_.directMSS.exists()) {
                     SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directMSS.getName());
                     resultx = false;

                     try {
                        SPTBatch_.directMSS.mkdir();
                        resultx = true;
                     } catch (SecurityException var153) {
                     }

                     if (resultx) {
                        SPTBatch_.taskOutput.append(SPTBatch_.directMSS.getName() + "  DIR created");
                     }
                  }
               }

               if (SPTBatch_.checkCluster.isSelected() == Boolean.TRUE) {
                  SPTBatch_.directCluster = new File(SPTBatch_.directImages.getAbsolutePath() + File.separator + "Cluster_Size_Analysis");
                  if (!SPTBatch_.directCluster.exists()) {
                     SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directCluster.getName());
                     resultx = false;

                     try {
                        SPTBatch_.directCluster.mkdir();
                        resultx = true;
                     } catch (SecurityException var152) {
                     }

                     if (resultx) {
                        SPTBatch_.taskOutput.append(SPTBatch_.directCluster.getName() + "  DIR created");
                     }
                  }
               }

               new TmXmlReader(SPTBatch_.fileXmlInitial);
               DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
               DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
               DocumentBuilder builder = null;

               try {
                  builder = factory.newDocumentBuilder();
               } catch (ParserConfigurationException var151) {
                  var151.printStackTrace();
               }

               Document doc = null;

               try {
                  doc = builder.parse(SPTBatch_.fileXmlInitial);
               } catch (SAXException var149) {
                  var149.printStackTrace();
               } catch (IOException var150) {
                  var150.printStackTrace();
               }

               XPathFactory xPathfactory = XPathFactory.newInstance();
               XPath xpath = xPathfactory.newXPath();
               XPathExpression exprBasicSettings = null;
               XPathExpression exprDetectorSettings = null;
               XPathExpression exprInitialSpotFilter = null;
               XPathExpression exprFilter = null;
               XPathExpression exprTrackerSettings = null;
               XPathExpression exprLinking = null;
               XPathExpression exprGapClosing = null;
               XPathExpression exprSplitting = null;
               XPathExpression exprMerging = null;
               XPathExpression exprTrackFilter = null;
               XPathExpression exprLinkingP = null;

               try {
                  exprBasicSettings = xpath.compile("//Settings/BasicSettings[@zstart]");
               } catch (XPathExpressionException var148) {
                  var148.printStackTrace();
               }

               try {
                  exprLinkingP = xpath.compile("//Linking/FeaturePenalties[@MEAN_INTENSITY]");
               } catch (XPathExpressionException var147) {
                  var147.printStackTrace();
               }

               try {
                  exprDetectorSettings = xpath.compile("//Settings/DetectorSettings[@RADIUS]");
               } catch (XPathExpressionException var146) {
                  var146.printStackTrace();
               }

               try {
                  exprInitialSpotFilter = xpath.compile("//Settings/InitialSpotFilter[@feature]");
               } catch (XPathExpressionException var145) {
                  var145.printStackTrace();
               }

               try {
                  exprFilter = xpath.compile("//SpotFilterCollection/Filter[@feature]");
               } catch (XPathExpressionException var144) {
                  var144.printStackTrace();
               }

               try {
                  exprTrackerSettings = xpath.compile("//Settings/TrackerSettings[@TRACKER_NAME]");
               } catch (XPathExpressionException var143) {
                  var143.printStackTrace();
               }

               try {
                  exprLinking = xpath.compile("//TrackerSettings/Linking[@LINKING_MAX_DISTANCE]");
               } catch (XPathExpressionException var142) {
                  var142.printStackTrace();
               }

               try {
                  exprGapClosing = xpath.compile("//TrackerSettings/GapClosing[@MAX_FRAME_GAP]");
               } catch (XPathExpressionException var141) {
                  var141.printStackTrace();
               }

               try {
                  exprSplitting = xpath.compile("//TrackerSettings/TrackSplitting[@SPLITTING_MAX_DISTANCE]");
               } catch (XPathExpressionException var140) {
                  var140.printStackTrace();
               }

               try {
                  exprMerging = xpath.compile("//TrackerSettings/TrackMerging[@MERGING_MAX_DISTANCE]");
               } catch (XPathExpressionException var139) {
                  var139.printStackTrace();
               }

               try {
                  exprTrackFilter = xpath.compile("//TrackFilterCollection/Filter[@feature]");
               } catch (XPathExpressionException var138) {
                  var138.printStackTrace();
               }

               NodeList nlBasicSettings = null;
               NodeList nlDetectorSettings = null;
               NodeList nlInitialSpotFilter = null;
               NodeList nlFilter = null;
               NodeList nlTrackerSettings = null;
               NodeList nlLinking = null;
               NodeList nlGapClosing = null;
               NodeList nlSplitting = null;
               NodeList nlMerging = null;
               NodeList nlTrackFilter = null;
               NodeList nlLinkingP = null;

               try {
                  nlBasicSettings = (NodeList)exprBasicSettings.evaluate(doc, XPathConstants.NODESET);
                  nlDetectorSettings = (NodeList)exprDetectorSettings.evaluate(doc, XPathConstants.NODESET);
                  nlInitialSpotFilter = (NodeList)exprInitialSpotFilter.evaluate(doc, XPathConstants.NODESET);
                  nlFilter = (NodeList)exprFilter.evaluate(doc, XPathConstants.NODESET);
                  nlTrackerSettings = (NodeList)exprTrackerSettings.evaluate(doc, XPathConstants.NODESET);
                  nlLinking = (NodeList)exprLinking.evaluate(doc, XPathConstants.NODESET);
                  nlGapClosing = (NodeList)exprGapClosing.evaluate(doc, XPathConstants.NODESET);
                  nlSplitting = (NodeList)exprSplitting.evaluate(doc, XPathConstants.NODESET);
                  nlMerging = (NodeList)exprMerging.evaluate(doc, XPathConstants.NODESET);
                  nlTrackFilter = (NodeList)exprTrackFilter.evaluate(doc, XPathConstants.NODESET);
                  nlLinkingP = (NodeList)exprLinkingP.evaluate(doc, XPathConstants.NODESET);
               } catch (XPathExpressionException var137) {
                  var137.printStackTrace();
               }

               int j;
               Node currentItem;
               for(j = 0; j < nlBasicSettings.getLength(); ++j) {
                  currentItem = nlBasicSettings.item(j);
                  SPTBatch_.this.zstart = currentItem.getAttributes().getNamedItem("zstart").getNodeValue();
                  SPTBatch_.this.zend = currentItem.getAttributes().getNamedItem("zend").getNodeValue();
                  SPTBatch_.this.ystart = currentItem.getAttributes().getNamedItem("ystart").getNodeValue();
                  SPTBatch_.this.yend = currentItem.getAttributes().getNamedItem("yend").getNodeValue();
                  SPTBatch_.this.xstart = currentItem.getAttributes().getNamedItem("xstart").getNodeValue();
                  SPTBatch_.this.xend = currentItem.getAttributes().getNamedItem("xend").getNodeValue();
                  SPTBatch_.this.tstart = currentItem.getAttributes().getNamedItem("tstart").getNodeValue();
                  SPTBatch_.this.tend = currentItem.getAttributes().getNamedItem("tend").getNodeValue();
               }

               for(j = 0; j < nlDetectorSettings.getLength(); ++j) {
                  currentItem = nlDetectorSettings.item(j);
                  SPTBatch_.RADIUS = currentItem.getAttributes().getNamedItem("RADIUS").getNodeValue();
                  SPTBatch_.this.THRESHOLD = currentItem.getAttributes().getNamedItem("THRESHOLD").getNodeValue();
                  SPTBatch_.this.TARGET_CHANNEL = currentItem.getAttributes().getNamedItem("TARGET_CHANNEL").getNodeValue();
                  SPTBatch_.this.DO_SUBPIXEL_LOCALIZATION = currentItem.getAttributes().getNamedItem("DO_SUBPIXEL_LOCALIZATION").getNodeValue();
                  SPTBatch_.this.DO_MEDIAN_FILTERING = currentItem.getAttributes().getNamedItem("DO_MEDIAN_FILTERING").getNodeValue();
                  SPTBatch_.this.DETECTOR_NAME = currentItem.getAttributes().getNamedItem("DETECTOR_NAME").getNodeValue();
                  if (SPTBatch_.this.DETECTOR_NAME.equals("BLOCK_LOG_DETECTOR")) {
                     SPTBatch_.this.NSPLIT = currentItem.getAttributes().getNamedItem("NSPLIT").getNodeValue();
                  }

                  if (SPTBatch_.this.DETECTOR_NAME.equals("DOWNSAMLE_LOG_DETECTOR")) {
                     SPTBatch_.this.DOWNSAMPLE_FACTOR = currentItem.getAttributes().getNamedItem("DOWNSAMPLE_FACTOR").getNodeValue();
                  }
               }

               for(j = 0; j < nlLinkingP.getLength(); ++j) {
                  SPTBatch_.this.linkingNames = nlLinkingP.item(j).getAttributes().item(j).getNodeName();
                  SPTBatch_.this.linkingValues = nlLinkingP.item(j).getAttributes().item(j).getNodeValue();
               }

               for(j = 0; j < nlInitialSpotFilter.getLength(); ++j) {
                  currentItem = nlInitialSpotFilter.item(j);
                  SPTBatch_.this.initialSpotFilter = currentItem.getAttributes().getNamedItem("value").getNodeValue();
               }

               List<String> spotFilterFeature = new ArrayList();
               List<String> spotFilterValue = new ArrayList();
               List<String> spotFilterAbove = new ArrayList();

               for(int jx = 0; jx < nlFilter.getLength(); ++jx) {
                  Node currentItemx = nlFilter.item(jx);
                  spotFilterFeature.add(currentItemx.getAttributes().getNamedItem("feature").getNodeValue());
                  spotFilterValue.add(currentItemx.getAttributes().getNamedItem("value").getNodeValue());
                  spotFilterAbove.add(currentItemx.getAttributes().getNamedItem("isabove").getNodeValue());
               }

               List<String> trackFilterFeature = new ArrayList();
               List<String> trackFilterValue = new ArrayList();
               List<String> trackFilterAbove = new ArrayList();

               int jxx;
               Node currentItemxx;
               for(jxx = 0; jxx < nlTrackFilter.getLength(); ++jxx) {
                  currentItemxx = nlTrackFilter.item(jxx);
                  trackFilterFeature.add(currentItemxx.getAttributes().getNamedItem("feature").getNodeValue());
                  trackFilterValue.add(currentItemxx.getAttributes().getNamedItem("value").getNodeValue());
                  trackFilterAbove.add(currentItemxx.getAttributes().getNamedItem("isabove").getNodeValue());
               }

               for(jxx = 0; jxx < nlTrackerSettings.getLength(); ++jxx) {
                  currentItemxx = nlTrackerSettings.item(jxx);
                  SPTBatch_.this.TRACKER_NAME = currentItemxx.getAttributes().getNamedItem("TRACKER_NAME").getNodeValue();
                  SPTBatch_.this.CUTOFF_PERCENTILE = currentItemxx.getAttributes().getNamedItem("CUTOFF_PERCENTILE").getNodeValue();
                  SPTBatch_.this.BLOCKING_VALUE = currentItemxx.getAttributes().getNamedItem("BLOCKING_VALUE").getNodeValue();
                  SPTBatch_.this.ALTERNATIVE_LINKING_COST_FACTOR = currentItemxx.getAttributes().getNamedItem("ALTERNATIVE_LINKING_COST_FACTOR").getNodeValue();
               }

               for(jxx = 0; jxx < nlLinking.getLength(); ++jxx) {
                  currentItemxx = nlLinking.item(jxx);
                  SPTBatch_.this.LINKING_MAX_DISTANCE = currentItemxx.getAttributes().getNamedItem("LINKING_MAX_DISTANCE").getNodeValue();
               }

               for(jxx = 0; jxx < nlGapClosing.getLength(); ++jxx) {
                  currentItemxx = nlGapClosing.item(jxx);
                  SPTBatch_.this.MAX_FRAME_GAP = currentItemxx.getAttributes().getNamedItem("MAX_FRAME_GAP").getNodeValue();
                  SPTBatch_.this.MAX_DISTANCE = currentItemxx.getAttributes().getNamedItem("GAP_CLOSING_MAX_DISTANCE").getNodeValue();
                  SPTBatch_.this.ALLOW_GAP_CLOSING = currentItemxx.getAttributes().getNamedItem("ALLOW_GAP_CLOSING").getNodeValue();
               }

               for(jxx = 0; jxx < nlSplitting.getLength(); ++jxx) {
                  currentItemxx = nlSplitting.item(jxx);
                  SPTBatch_.this.SPLITTING_MAX_DISTANCE = currentItemxx.getAttributes().getNamedItem("SPLITTING_MAX_DISTANCE").getNodeValue();
                  SPTBatch_.this.ALLOW_TRACK_SPLITTING = currentItemxx.getAttributes().getNamedItem("ALLOW_TRACK_SPLITTING").getNodeValue();
               }

               for(jxx = 0; jxx < nlMerging.getLength(); ++jxx) {
                  currentItemxx = nlMerging.item(jxx);
                  SPTBatch_.this.MERGING_MAX_DISTANCE = currentItemxx.getAttributes().getNamedItem("MERGING_MAX_DISTANCE").getNodeValue();
                  SPTBatch_.this.ALLOW_TRACK_MERGING = currentItemxx.getAttributes().getNamedItem("ALLOW_TRACK_MERGING").getNodeValue();
               }

               SPTBatch_.this.settings = new Settings(SPTBatch_.imps);
               SPTBatch_.taskOutput.append(SPTBatch_.this.settings.toStringImageInfo());
               if (SPTBatch_.this.DETECTOR_NAME.equals("LOG_DETECTOR")) {
                  SPTBatch_.this.settings.detectorFactory = new LogDetectorFactory();
                  SPTBatch_.this.settings.detectorSettings = SPTBatch_.this.settings.detectorFactory.getDefaultSettings();
                  SPTBatch_.this.settings.detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", Boolean.parseBoolean(SPTBatch_.this.DO_SUBPIXEL_LOCALIZATION));
                  SPTBatch_.this.settings.detectorSettings.put("RADIUS", Double.parseDouble(SPTBatch_.RADIUS));
                  SPTBatch_.this.settings.detectorSettings.put("TARGET_CHANNEL", Integer.parseInt(SPTBatch_.this.TARGET_CHANNEL));
                  SPTBatch_.this.settings.detectorSettings.put("THRESHOLD", Double.parseDouble(SPTBatch_.this.THRESHOLD));
                  SPTBatch_.this.settings.detectorSettings.put("DO_MEDIAN_FILTERING", Boolean.parseBoolean(SPTBatch_.this.DO_MEDIAN_FILTERING));
                  if (SPTBatch_.this.initialSpotFilter != null) {
                     SPTBatch_.this.settings.initialSpotFilterValue = Double.parseDouble(SPTBatch_.this.initialSpotFilter);
                  }
               }

               if (SPTBatch_.this.DETECTOR_NAME.equals("DOG_DETECTOR")) {
                  SPTBatch_.this.settings.detectorFactory = new DogDetectorFactory();
                  SPTBatch_.this.settings.detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", Boolean.parseBoolean(SPTBatch_.this.DO_SUBPIXEL_LOCALIZATION));
                  SPTBatch_.this.settings.detectorSettings.put("RADIUS", Double.parseDouble(SPTBatch_.RADIUS));
                  SPTBatch_.this.settings.detectorSettings.put("TARGET_CHANNEL", Integer.parseInt(SPTBatch_.this.TARGET_CHANNEL));
                  SPTBatch_.this.settings.detectorSettings.put("THRESHOLD", Double.parseDouble(SPTBatch_.this.THRESHOLD));
                  SPTBatch_.this.settings.detectorSettings.put("DO_MEDIAN_FILTERING", Double.parseDouble(SPTBatch_.this.DO_MEDIAN_FILTERING));
                  if (SPTBatch_.this.initialSpotFilter != null) {
                     SPTBatch_.this.settings.initialSpotFilterValue = Double.parseDouble(SPTBatch_.this.initialSpotFilter);
                  }
               }

               List<FeatureFilter> spotFilters = new ArrayList();

               int jxxxxx;
               for(jxxxxx = 0; jxxxxx < spotFilterFeature.size(); ++jxxxxx) {
                  spotFilters.add(new FeatureFilter((String)spotFilterFeature.get(jxxxxx), Double.valueOf((String)spotFilterValue.get(jxxxxx)), Boolean.valueOf((String)spotFilterAbove.get(jxxxxx))));
               }

               for(jxxxxx = 0; jxxxxx < spotFilters.size(); ++jxxxxx) {
                  SPTBatch_.this.settings.addSpotFilter((FeatureFilter)spotFilters.get(jxxxxx));
               }

               if (SPTBatch_.this.TRACKER_NAME.equals("MANUAL_TRACKER")) {
                  SPTBatch_.this.settings.trackerFactory = new ManualTrackerFactory();
                  SPTBatch_.this.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
               }

               if (SPTBatch_.this.TRACKER_NAME.equals("MANUAL_TRACKER")) {
                  SPTBatch_.this.settings.trackerFactory = new ManualTrackerFactory();
                  SPTBatch_.this.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
               }

               if (SPTBatch_.this.TRACKER_NAME.equals("KALMAN_TRACKER")) {
                  SPTBatch_.this.settings.trackerFactory = new KalmanTrackerFactory();
                  SPTBatch_.this.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
                  SPTBatch_.this.settings.trackerSettings.put("KALMAN_SEARCH_RADIUS", Double.parseDouble(SPTBatch_.RADIUS));
               }

               if (SPTBatch_.this.TRACKER_NAME.equals("SIMPLE_SPARSE_LAP_TRACKER")) {
                  SPTBatch_.this.settings.trackerFactory = new SimpleSparseLAPTrackerFactory();
                  SPTBatch_.this.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
                  SPTBatch_.this.settings.trackerSettings.put("LINKING_MAX_DISTANCE", Double.parseDouble(SPTBatch_.this.LINKING_MAX_DISTANCE));
                  SPTBatch_.this.settings.trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", Double.parseDouble(SPTBatch_.this.MAX_DISTANCE));
                  SPTBatch_.this.settings.trackerSettings.put("MAX_FRAME_GAP", Double.parseDouble(SPTBatch_.this.MAX_FRAME_GAP));
               }

               if (SPTBatch_.this.TRACKER_NAME.equals("SPARSE_LAP_TRACKER")) {
                  SPTBatch_.this.settings.trackerFactory = new SparseLAPTrackerFactory();
                  SPTBatch_.this.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
                  SPTBatch_.this.settings.trackerSettings.put("LINKING_MAX_DISTANCE", Double.parseDouble(SPTBatch_.this.LINKING_MAX_DISTANCE));
                  Map<String, Double> linkingPenalty = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((datax) -> {
                     return (String)datax[0];
                  }, (datax) -> {
                     return (Double)datax[1];
                  }));
                  SPTBatch_.this.settings.trackerSettings.put("ALLOW_GAP_CLOSING", Boolean.parseBoolean(SPTBatch_.this.ALLOW_GAP_CLOSING));
                  Map var50;
                  if (Boolean.parseBoolean(SPTBatch_.this.ALLOW_GAP_CLOSING)) {
                     SPTBatch_.this.settings.trackerSettings.put("MAX_FRAME_GAP", Integer.parseInt(SPTBatch_.this.MAX_FRAME_GAP));
                     SPTBatch_.this.settings.trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", Double.parseDouble(SPTBatch_.this.MAX_DISTANCE));
                     var50 = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((datax) -> {
                        return (String)datax[0];
                     }, (datax) -> {
                        return (Double)datax[1];
                     }));
                  }

                  SPTBatch_.this.settings.trackerSettings.put("ALLOW_TRACK_SPLITTING", Boolean.parseBoolean(SPTBatch_.this.ALLOW_TRACK_SPLITTING));
                  if (Boolean.parseBoolean(SPTBatch_.this.ALLOW_TRACK_SPLITTING)) {
                     SPTBatch_.this.settings.trackerSettings.put("SPLITTING_MAX_DISTANCE", Double.parseDouble(SPTBatch_.this.SPLITTING_MAX_DISTANCE));
                     var50 = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((datax) -> {
                        return (String)datax[0];
                     }, (datax) -> {
                        return (Double)datax[1];
                     }));
                  }

                  SPTBatch_.this.settings.trackerSettings.put("ALLOW_TRACK_MERGING", Boolean.parseBoolean(SPTBatch_.this.ALLOW_TRACK_MERGING));
                  if (Boolean.parseBoolean(SPTBatch_.this.ALLOW_TRACK_MERGING)) {
                     SPTBatch_.this.settings.trackerSettings.put("MERGING_MAX_DISTANCE", Double.parseDouble(SPTBatch_.this.MERGING_MAX_DISTANCE));
                     var50 = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((datax) -> {
                        return (String)datax[0];
                     }, (datax) -> {
                        return (Double)datax[1];
                     }));
                  }
               }

               List<FeatureFilter> trackFilters = new ArrayList();

               int jxxxxxx;
               for(jxxxxxx = 0; jxxxxxx < trackFilterFeature.size(); ++jxxxxxx) {
                  trackFilters.add(new FeatureFilter((String)trackFilterFeature.get(jxxxxxx), Double.valueOf((String)trackFilterValue.get(jxxxxxx)), Boolean.valueOf((String)trackFilterAbove.get(jxxxxxx))));
               }

               for(jxxxxxx = 0; jxxxxxx < trackFilters.size(); ++jxxxxxx) {
                  SPTBatch_.this.settings.addTrackFilter((FeatureFilter)trackFilters.get(jxxxxxx));
               }

               boolean result;
               if (SPTBatch_.checkboxSubBg.isSelected()) {
                  SPTBatch_.slices = SPTBatch_.stack2images(SPTBatch_.impsSubBg);
                  SPTBatch_.slicesIntensityBg = new double[SPTBatch_.slices.length];
                  SPTBatch_.slicesIntensitySpot = new double[SPTBatch_.slices.length];
                  if (SPTBatch_.this.comboSubBg.getSelectedIndex() == 0) {
                     SPTBatch_.impMaxProject = ZProjector.run(SPTBatch_.impsSubBg.duplicate(), "max");
                     SPTBatch_.impMaxProject.show();
                     SPTBatch_.roiManager = RoiManager.getInstance();
                     if (SPTBatch_.roiManager == null) {
                        SPTBatch_.roiManager = new RoiManager();
                     }

                     SPTBatch_.roiManager.reset();
                     SPTBatch_.impMaxProject.getCanvas().addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                           if (e.getClickCount() == 2) {
                              Roi roi = new Roi(SPTBatch_.impMaxProject.getCanvas().offScreenX(e.getX()), SPTBatch_.impMaxProject.getCanvas().offScreenY(e.getY()), 5, 5);
                              SPTBatch_.impMaxProject.setRoi(roi);
                              SPTBatch_.roiManager.runCommand(SPTBatch_.impMaxProject, "Show All with labels");
                              SPTBatch_.roiManager.addRoi(roi);
                           }

                        }
                     });
                     Dialog4BgSub0 userDialog = new Dialog4BgSub0("Action Required", "Please select manually areas to measure background.");
                     userDialog.show();
                  }

                  ImagePlus slicesCell;
                  Roi roiCell;
                  double meanInv;
                  if (SPTBatch_.this.comboSubBg.getSelectedIndex() == 2) {
                     for(jxxxxxx = 0; jxxxxxx < SPTBatch_.slices.length; ++jxxxxxx) {
                        slicesCell = SPTBatch_.slices[jxxxxxx].duplicate();
                        IJ.run(slicesCell, "Auto Threshold", "method=Otsu ignore_black white");
                        slicesCell = new ImagePlus(slicesCell.getTitle(), Morphology.dilation(slicesCell.getProcessor(), Shape.DISK.fromRadius(2)));
                        IJ.run(slicesCell, "Invert LUT", "");
                        IJ.run(slicesCell, "Fill Holes", "");
                        IJ.run(slicesCell, "Invert LUT", "");
                        IJ.run(slicesCell, "Create Selection", "");
                        roiCell = slicesCell.getRoi();
                        IJ.run(slicesCell, "Make Inverse", "");
                        Roi roiToMeasureInvx = slicesCell.getRoi();
                        SPTBatch_.slices[jxxxxxx].setRoi(roiCell);
                        double meanDirectx = SPTBatch_.slices[jxxxxxx].getStatistics().mean;
                        SPTBatch_.slices[jxxxxxx].setRoi(roiToMeasureInvx);
                        meanInv = SPTBatch_.slices[jxxxxxx].getStatistics().mean;
                        if (meanDirectx > meanInv) {
                           SPTBatch_.slicesIntensitySpot[jxxxxxx] = meanDirectx;
                        } else {
                           SPTBatch_.slicesIntensitySpot[jxxxxxx] = meanInv;
                        }
                     }
                  }

                  if (SPTBatch_.this.comboSubBg.getSelectedIndex() == 3) {
                     for(jxxxxxx = 0; jxxxxxx < SPTBatch_.slices.length; ++jxxxxxx) {
                        slicesCell = SPTBatch_.slices[jxxxxxx].duplicate();
                        IJ.run(slicesCell, "Auto Threshold", "method=Otsu ignore_black white");
                        slicesCell = new ImagePlus(slicesCell.getTitle(), Morphology.dilation(slicesCell.getProcessor(), Shape.DISK.fromRadius(2)));
                        IJ.run(slicesCell, "Invert LUT", "");
                        IJ.run(slicesCell, "Fill Holes", "");
                        IJ.run(slicesCell, "Invert LUT", "");
                        IJ.run(slicesCell, "Create Selection", "");
                        roiCell = slicesCell.getRoi();
                        SPTBatch_.slices[jxxxxxx].setRoi(roiCell);
                        double meanDirect = SPTBatch_.slices[jxxxxxx].getStatistics().mean;
                        IJ.run(slicesCell, "Make Inverse", "");
                        Roi roiCellInv = slicesCell.getRoi();
                        SPTBatch_.slices[jxxxxxx].setRoi(roiCellInv);
                        meanInv = SPTBatch_.slices[jxxxxxx].getStatistics().mean;
                        if (meanDirect > meanInv) {
                           roiCell = roiCell;
                        } else {
                           roiCell = roiCellInv;
                        }

                        ImagePlus slicesSpot = SPTBatch_.slices[jxxxxxx].duplicate();
                        String value = String.valueOf(Double.valueOf(SPTBatch_.RADIUS) / SPTBatch_.imps.getCalibration().pixelWidth);
                        if (value.contains(",")) {
                           value = value.replaceAll(",", ".");
                        }

                        IJ.run(slicesSpot, "Subtract Background...", String.format("rolling=%s", value));
                        IJ.run(slicesSpot, "Auto Threshold", "method=Otsu ignore_black white");
                        IJ.run(slicesSpot, "Create Selection", "");
                        Roi roiSpots = slicesSpot.getRoi();
                        SPTBatch_.slices[jxxxxxx].setRoi(roiSpots);
                        double meanDirectSpots = SPTBatch_.slices[jxxxxxx].getStatistics().mean;
                        IJ.run(slicesSpot, "Make Inverse", "");
                        Roi roiSpotInv = slicesCell.getRoi();
                        SPTBatch_.slices[jxxxxxx].setRoi(roiCellInv);
                        double meanInvSpots = SPTBatch_.slices[jxxxxxx].getStatistics().mean;
                        if (meanDirect > meanInv) {
                           roiSpots = roiSpots;
                        } else {
                           roiSpots = roiSpotInv;
                        }

                        Roi roiToMeasurex = (new ShapeRoi(roiCell)).xor(new ShapeRoi(roiSpots));
                        SPTBatch_.slices[jxxxxxx].setRoi(roiToMeasurex);
                        SPTBatch_.slicesIntensitySpot[jxxxxxx] = SPTBatch_.slices[jxxxxxx].getStatistics().mean - SPTBatch_.slices[jxxxxxx].getStatistics().stdDev;
                     }
                  }

                  if (SPTBatch_.this.comboSubBg.getSelectedIndex() == 4) {
                     for(jxxxxxx = 0; jxxxxxx < SPTBatch_.slices.length; ++jxxxxxx) {
                        slicesCell = SPTBatch_.slices[jxxxxxx].duplicate();
                        IJ.run(slicesCell, "Auto Threshold", "method=Otsu ignore_black white");
                        slicesCell = new ImagePlus(slicesCell.getTitle(), Morphology.dilation(slicesCell.getProcessor(), Shape.DISK.fromRadius(2)));
                        IJ.run(slicesCell, "Invert LUT", "");
                        IJ.run(slicesCell, "Fill Holes", "");
                        IJ.run(slicesCell, "Invert LUT", "");
                        IJ.run(slicesCell, "Create Selection", "");
                        roiCell = slicesCell.getRoi();
                        ImagePlus sliceDup2 = SPTBatch_.slices[jxxxxxx].duplicate();
                        sliceDup2.setRoi(roiCell);
                        IJ.run(sliceDup2, "Clear Outside", "");
                        BackgroundSubtracter bgSubt = new BackgroundSubtracter();
                        ImageProcessor ip = sliceDup2.getProcessor();
                        sliceDup2.getProcessor().resetMinAndMax();
                        boolean isRGB = sliceDup2.getProcessor() instanceof ColorProcessor;
                        boolean calledAsPlugin = true;
                        double radius = Double.valueOf(SPTBatch_.RADIUS) / SPTBatch_.imps.getCalibration().pixelWidth;
                        boolean lightBackground = false;
                        boolean separateColors = false;
                        boolean createBackground = false;
                        boolean useParaboloid = false;
                        boolean doPresmooth = false;
                        result = false;
                        boolean correctCorners = true;
                        SPTBatch_.slicesIntensitySpot[jxxxxxx] = bgSubt.rollingBallBackground(ip, radius, createBackground, lightBackground, useParaboloid, doPresmooth, correctCorners);
                     }
                  }
               }

               SPTBatch_.this.settings.addAllAnalyzers();
               SPTBatch_.model = new Model();
               SPTBatch_.this.trackmate = new TrackMate(SPTBatch_.model, SPTBatch_.this.settings);
               Boolean ok = SPTBatch_.this.trackmate.checkInput();
               if (ok != Boolean.TRUE) {
                  SPTBatch_.taskOutput.append(SPTBatch_.this.trackmate.getErrorMessage());
               }

               ok = SPTBatch_.this.trackmate.process();
               SPTBatch_.selectionModel = new SelectionModel(SPTBatch_.model);
               SPTBatch_.model.setLogger(Logger.IJ_LOGGER);
               SpotCollection spots = SPTBatch_.model.getSpots();
               SPTBatch_.taskOutput.append(spots.toString());
               FeatureModel fm = SPTBatch_.model.getFeatureModel();
               SPTBatch_.this.tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, "TRACK_INDEX", (Color)null, (Color)null, Colormap.Turbo, 0.0D, 1.0D);
               SPTBatch_.taskOutput.append("\n\nSETTINGS:");
               SPTBatch_.taskOutput.append(SPTBatch_.this.settings.toString());
               SPTBatch_.taskOutput.append(SPTBatch_.model.toString());
               SPTBatch_.taskOutput.append("Found" + SPTBatch_.model.getTrackModel().nTracks(true) + " tracks.");
               SPTBatch_.taskOutput.append(SPTBatch_.this.settings.toStringFeatureAnalyzersInfo());
               Integer firstFrame = null;
               Integer lastFrame = null;
               SPTBatch_.this.ds = DisplaySettingsIO.readUserDefault();
               SPTBatch_.this.ds.setSpotShowName(SPTBatch_.checkDispSpotName.isSelected());
               SPTBatch_.this.ds.setSpotVisible(SPTBatch_.checkDispSpots.isSelected());
               SPTBatch_.this.ds.setSpotColorBy(TrackMateObject.TRACKS, "TRACK_INDEX");
               SPTBatch_.this.ds.setTrackVisible(SPTBatch_.checkDispTracks.isSelected());
               SPTBatch_.this.ds.setTrackColorBy(TrackMateObject.TRACKS, "TRACK_INDEX");
               if (SPTBatch_.this.comboDispTracks.getSelectedIndex() == 0) {
                  SPTBatch_.this.ds.setTrackDisplayMode(TrackDisplayMode.FULL);
               }

               if (SPTBatch_.this.comboDispTracks.getSelectedIndex() == 1) {
                  SPTBatch_.this.ds.setTrackDisplayMode(TrackDisplayMode.LOCAL);
               }

               if (SPTBatch_.this.comboDispTracks.getSelectedIndex() == 2) {
                  SPTBatch_.this.ds.setTrackDisplayMode(TrackDisplayMode.LOCAL_BACKWARD);
               }

               if (SPTBatch_.this.comboDispTracks.getSelectedIndex() == 3) {
                  SPTBatch_.this.ds.setTrackDisplayMode(TrackDisplayMode.LOCAL_FORWARD);
               }

               SPTBatch_.this.displayer = new HyperStackDisplayer(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.imps, SPTBatch_.this.ds);
               SPTBatch_.this.displayer.render();
               SPTBatch_.this.displayer.refresh();
               if (SPTBatch_.imps.getNFrames() > 1) {
                  firstFrame = Math.max(1, Math.min(SPTBatch_.imps.getNFrames(), 1));
                  lastFrame = Math.min(SPTBatch_.imps.getNFrames(), Math.max(SPTBatch_.imps.getNFrames(), 1));
               }

               if (SPTBatch_.imps.getNSlices() > 1) {
                  firstFrame = Math.max(1, Math.min(SPTBatch_.imps.getNSlices(), 1));
                  lastFrame = Math.min(SPTBatch_.imps.getNSlices(), Math.max(SPTBatch_.imps.getNSlices(), 1));
               }

               SPTBatch_.taskOutput.append("Capturing TrackMate overlay from frame " + firstFrame + " to " + lastFrame + ".\n");
               Rectangle bounds = SPTBatch_.this.displayer.getImp().getCanvas().getBounds();
               Integer width = bounds.width;
               Integer height = bounds.height;
               Integer nCaptures = lastFrame - firstFrame + 1;
               ImageStack stack = new ImageStack(width, height);
               Integer channel = SPTBatch_.this.displayer.getImp().getChannel();
               Integer slice = SPTBatch_.this.displayer.getImp().getSlice();
               SPTBatch_.this.displayer.getImp().getCanvas().hideZoomIndicator(true);

               BufferedImage bi;
               ColorProcessor cp;
               Integer index;
               int frame;
               for(frame = firstFrame; frame <= lastFrame; ++frame) {
                  SPTBatch_.this.displayer.getImp().setPositionWithoutUpdate(channel, slice, frame);
                  bi = new BufferedImage(width, height, 2);
                  SPTBatch_.this.displayer.getImp().getCanvas().paint(bi.getGraphics());
                  cp = new ColorProcessor(bi);
                  index = SPTBatch_.this.displayer.getImp().getStackIndex(channel, slice, frame);
                  stack.addSlice(SPTBatch_.this.displayer.getImp().getImageStack().getSliceLabel(index), cp);
               }

               SPTBatch_.this.displayer.getImp().getCanvas().hideZoomIndicator(false);
               SPTBatch_.this.capture = new ImagePlus("TrackMate capture of " + SPTBatch_.this.displayer.getImp().getShortTitle(), stack);
               SPTBatch_.transferCalibration(SPTBatch_.this.displayer.getImp(), SPTBatch_.this.capture);
               SPTBatch_.taskOutput.append(" done.\n");
               RoiManager trackx;
               ArrayList framesByTrack;
               int n;
               int y;
               int counter10;
               if (SPTBatch_.checkboxRoi.isSelected() == Boolean.TRUE) {
                  double dx = SPTBatch_.imps.getCalibration().pixelWidth;
                  double dy = SPTBatch_.imps.getCalibration().pixelHeight;
                  double dz = SPTBatch_.imps.getCalibration().pixelDepth;
                  trackx = RoiManager.getInstance();
                  if (trackx == null) {
                     trackx = new RoiManager();
                  }

                  trackx.reset();
                  framesByTrack = new ArrayList(SPTBatch_.this.trackmate.getModel().getSpots().getNSpots(true));
                  Iterator var74 = SPTBatch_.this.trackmate.getModel().getTrackModel().trackIDs(true).iterator();

                  while(var74.hasNext()) {
                     Integer trackIDxxx = (Integer)var74.next();
                     framesByTrack.addAll(SPTBatch_.this.trackmate.getModel().getTrackModel().trackSpots(trackIDxxx));
                  }

                  for(n = 0; n < framesByTrack.size(); ++n) {
                     SpotRoi sroi = ((Spot)framesByTrack.get(n)).getRoi();
                     Object roi;
                     if (sroi != null) {
                        double[] xs = sroi.toPolygonX(dx, 0.0D, ((Spot)framesByTrack.get(n)).getDoublePosition(0), 1.0D);
                        double[] ys = sroi.toPolygonY(dy, 0.0D, ((Spot)framesByTrack.get(n)).getDoublePosition(1), 1.0D);
                        float[] xp = SPTBatch_.toFloat(xs);
                        float[] yp = SPTBatch_.toFloat(ys);
                        roi = new PolygonRoi(xp, yp, 2);
                     } else {
                        double diameter = 2.0D * ((Spot)framesByTrack.get(n)).getFeature("RADIUS") / dx;
                        double xsx = ((Spot)framesByTrack.get(n)).getDoublePosition(0) / dx - diameter / 2.0D + 0.5D;
                        double ysx = ((Spot)framesByTrack.get(n)).getDoublePosition(1) / dy - diameter / 2.0D + 0.5D;
                        roi = new OvalRoi(xsx, ysx, diameter, diameter);
                     }

                     y = 1 + (int)Math.round(((Spot)framesByTrack.get(n)).getDoublePosition(2) / dz);
                     counter10 = 1 + ((Spot)framesByTrack.get(n)).getFeature("FRAME").intValue();
                     ((Roi)roi).setPosition(0, y, counter10);
                     ((Roi)roi).setName(((Spot)framesByTrack.get(n)).getName());
                     trackx.addRoi((Roi)roi);
                  }

                  if (trackx.getRoisAsArray().length != 0) {
                     trackx.runCommand("Save", SPTBatch_.directSPT + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "RoiSet.zip");
                  }

                  trackx.close();
               }

               if (SPTBatch_.checkbox2.isSelected() == Boolean.TRUE) {
                  SPTBatch_.taskOutput.append(SPTBatch_.model.toString());
                  ISBIChallengeExporterModified.exportToFile(SPTBatch_.model, SPTBatch_.this.settings, new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + "TrackMate_" + SPTBatch_.imps.getShortTitle() + ".xml"));
                  SPTBatch_.taskOutput.append("\nDone.");
               }

               int counter1;
               int maxFrame;
               SpotCollection chart;
               int counter4;
               String xAxisLabelx;
               if (SPTBatch_.this.enableST == "ST") {
                  Model model = SPTBatch_.this.trackmate.getModel();
                  Settings settings = SPTBatch_.this.trackmate.getSettings();
                  chart = model.getSpots();
                  counter1 = chart.keySet().size();
                  double[][] data = new double[2][counter1];
                  maxFrame = 0;

                  for(Iterator var237 = chart.keySet().iterator(); var237.hasNext(); ++maxFrame) {
                     counter4 = (Integer)var237.next();
                     data[1][maxFrame] = (double)chart.getNSpots(counter4, true);
                     if (data[1][maxFrame] > 0.0D) {
                        data[0][maxFrame] = ((Spot)chart.iterable(counter4, false).iterator().next()).getFeature("POSITION_T");
                     } else {
                        data[0][maxFrame] = (double)counter4 * settings.dt;
                     }
                  }

                  String xAxisLabel = "Time (" + SPTBatch_.this.trackmate.getModel().getTimeUnits() + ")";
                  xAxisLabelx = "N spots";
                  String title = "Nspots vs Time for " + SPTBatch_.this.trackmate.getSettings().imp.getShortTitle();
                  DefaultXYDataset dataset = new DefaultXYDataset();
                  dataset.addSeries("Nspots", data);
                  SPTBatch_.this.chart = ChartFactory.createXYLineChart(title, xAxisLabel, xAxisLabelx, dataset, PlotOrientation.VERTICAL, true, true, false);
                  SPTBatch_.this.chart.getTitle().setFont(Fonts.FONT);
                  SPTBatch_.this.chart.getLegend().setItemFont(Fonts.SMALL_FONT);
                  new ExportableChartPanel(SPTBatch_.this.chart);
               }

               TablePanel chartx;
               Set chartxxx;
               int counter;
               ArrayList framesByTrackx;
               int framex;
               ArrayList framesByTrackSort;
               int jxxxx;
               if (SPTBatch_.checkbox1.isSelected() == Boolean.TRUE) {
                  TablePanel branchTable;
                  if (SPTBatch_.this.enableSpotTable.equals("spotTable")) {
                     branchTable = SPTBatch_.this.createSpotTable(SPTBatch_.model, SPTBatch_.this.ds);
                     JTable spotJTablex = branchTable.getTable();
                     chartx = SPTBatch_.this.createTrackTable(SPTBatch_.model, SPTBatch_.this.ds);
                     SPTBatch_.trackJTable = chartx.getTable();
                     SPTBatch_.nOfTracks = new ArrayList();

                     for(maxFrame = 0; maxFrame < SPTBatch_.trackJTable.getModel().getRowCount(); ++maxFrame) {
                        SPTBatch_.nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(maxFrame, 2).toString()));
                     }

                     SPTBatch_.this.indexes = new ArrayList();
                     chartxxx = SPTBatch_.model.getTrackModel().trackIDs(true);
                     trackx = null;
                     counter = 0;

                     for(n = 0; n < SPTBatch_.nOfTracks.size(); ++n) {
                        framesByTrackx = new ArrayList();
                        framesByTrackSort = new ArrayList();

                        for(y = 0; y < spotJTablex.getRowCount(); ++y) {
                           if (spotJTablex.getModel().getValueAt(y, 2).toString().equals(String.valueOf((Integer)SPTBatch_.nOfTracks.get(n)))) {
                              framesByTrackx.add(Float.valueOf(spotJTablex.getModel().getValueAt(y, 8).toString()));
                              framesByTrackSort.add(Float.valueOf(spotJTablex.getModel().getValueAt(y, 8).toString()));
                           }
                        }

                        Collections.sort(framesByTrackSort);

                        for(y = 0; y < framesByTrackSort.size(); ++y) {
                           ++counter;
                           if (n == 0) {
                              SPTBatch_.this.indexes.add(framesByTrackx.indexOf(framesByTrackSort.get(y)));
                           }

                           if (n != 0) {
                              SPTBatch_.this.indexes.add(counter - 1 + framesByTrackx.indexOf(framesByTrackSort.get(y)) - y);
                           }
                        }
                     }

                     String[][] rowDataSpot;
                     ResultsTable rtSpotPerImage;
                     if (SPTBatch_.checkboxSubBg.isSelected()) {
                        SPTBatch_.columnNamesSpot = new String[]{"LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1", "Intensity-Bg Subtract"};
                        rowDataSpot = new String[SPTBatch_.this.indexes.size()][SPTBatch_.columnNamesSpot.length];
                        framex = 0;

                        while(true) {
                           if (framex >= SPTBatch_.this.indexes.size()) {
                              if (SPTBatch_.this.comboSubBg.getSelectedIndex() == 1) {
                                 for(framex = 0; framex < rowDataSpot.length; ++framex) {
                                    rowDataSpot[framex][SPTBatch_.columnNamesSpot.length - 1] = String.valueOf(Double.valueOf(rowDataSpot[framex][12].toString()) - Double.valueOf(rowDataSpot[framex][19].toString()) * Double.valueOf(rowDataSpot[framex][17].toString()));
                                 }

                                 DefaultTableModel tableModel = new DefaultTableModel(rowDataSpot, SPTBatch_.columnNamesSpot);
                                 SPTBatch_.tableSpot = new JTable(tableModel);
                              }

                              if (SPTBatch_.this.comboSubBg.getSelectedIndex() == 0 || SPTBatch_.this.comboSubBg.getSelectedIndex() == 2 || SPTBatch_.this.comboSubBg.getSelectedIndex() == 3 || SPTBatch_.this.comboSubBg.getSelectedIndex() == 4) {
                                 for(framex = 0; framex < spotJTablex.getModel().getRowCount(); ++framex) {
                                    for(jxxxx = 0; jxxxx < SPTBatch_.slicesIntensitySpot.length; ++jxxxx) {
                                       if (Integer.valueOf(rowDataSpot[framex][8].toString()).equals(jxxxx) == Boolean.TRUE) {
                                          rowDataSpot[framex][SPTBatch_.columnNamesSpot.length - 1] = String.valueOf(Double.valueOf(rowDataSpot[framex][12].toString()) - Double.valueOf(SPTBatch_.slicesIntensitySpot[jxxxx]));
                                       }
                                    }
                                 }

                                 SPTBatch_.tableSpot = new JTable(rowDataSpot, SPTBatch_.columnNamesSpot);
                              }

                              SPTBatch_.this.exportToCSV(rowDataSpot, SPTBatch_.columnNamesSpot, new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "Spots in tracks statistics" + ".csv"));
                              rtSpotPerImage = new ResultsTable();

                              for(jxxxx = 0; jxxxx < rowDataSpot.length; ++jxxxx) {
                                 for(y = 0; y < rowDataSpot[jxxxx].length; ++y) {
                                    rtSpotPerImage.setValue(SPTBatch_.columnNamesSpot[y], jxxxx, rowDataSpot[jxxxx][y]);
                                 }
                              }

                              SPTBatch_.this.rtSpots[SPTBatch_.i] = rtSpotPerImage;
                              break;
                           }

                           rowDataSpot[framex][SPTBatch_.columnNamesSpot.length - 1] = "";

                           for(jxxxx = 0; jxxxx < spotJTablex.getModel().getColumnCount(); ++jxxxx) {
                              rowDataSpot[framex][jxxxx] = String.valueOf(spotJTablex.getModel().getValueAt((Integer)SPTBatch_.this.indexes.get(framex), jxxxx));
                           }

                           ++framex;
                        }
                     }

                     if (!SPTBatch_.checkboxSubBg.isSelected()) {
                        SPTBatch_.columnNamesSpot = new String[]{"LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1"};
                        rowDataSpot = new String[SPTBatch_.this.indexes.size()][SPTBatch_.columnNamesSpot.length];

                        for(framex = 0; framex < SPTBatch_.this.indexes.size(); ++framex) {
                           for(jxxxx = 0; jxxxx < spotJTablex.getModel().getColumnCount(); ++jxxxx) {
                              rowDataSpot[framex][jxxxx] = String.valueOf(spotJTablex.getModel().getValueAt((Integer)SPTBatch_.this.indexes.get(framex), jxxxx));
                           }
                        }

                        SPTBatch_.tableSpot = new JTable(rowDataSpot, SPTBatch_.columnNamesSpot);
                        SPTBatch_.this.exportToCSV(rowDataSpot, SPTBatch_.columnNamesSpot, new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "Spots in tracks statistics" + ".csv"));
                        rtSpotPerImage = new ResultsTable();

                        for(jxxxx = 0; jxxxx < rowDataSpot.length; ++jxxxx) {
                           for(y = 0; y < rowDataSpot[jxxxx].length; ++y) {
                              rtSpotPerImage.setValue(SPTBatch_.columnNamesSpot[y], jxxxx, rowDataSpot[jxxxx][y]);
                           }
                        }

                        SPTBatch_.this.rtSpots[SPTBatch_.i] = rtSpotPerImage;
                     }
                  }

                  if (SPTBatch_.this.enableLinkTable.equals("linkTable")) {
                     branchTable = SPTBatch_.this.createEdgeTable(SPTBatch_.model, SPTBatch_.this.ds);
                     SPTBatch_.linkJTable = branchTable.getTable();

                     try {
                        branchTable.exportToCsv(new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "Links in tracks statistics" + ".csv"));
                     } catch (IOException var136) {
                        var136.printStackTrace();
                     }
                  }

                  if (SPTBatch_.checkExcludeTracks.isSelected()) {
                     SPTBatch_.this.excludeTrack = new ArrayList();
                     SPTBatch_.impMainRoi = ZProjector.run(SPTBatch_.impsSubBg.duplicate(), "max");
                     ImagePlus impToMeasure = SPTBatch_.impMainRoi.duplicate();
                     IJ.run(SPTBatch_.impMainRoi, "Auto Threshold", "method=Otsu ignore_black white");
                     SPTBatch_.impMainRoi = new ImagePlus(SPTBatch_.impMainRoi.getTitle(), Morphology.dilation(SPTBatch_.impMainRoi.getProcessor(), Shape.DISK.fromRadius(2)));
                     IJ.run(SPTBatch_.impMainRoi, "Invert LUT", "");
                     IJ.run(SPTBatch_.impMainRoi, "Fill Holes", "");
                     IJ.run(SPTBatch_.impMainRoi, "Invert LUT", "");
                     IJ.run(SPTBatch_.impMainRoi, "Create Selection", "");
                     Roi roiToMeasure = SPTBatch_.impMainRoi.getRoi();
                     IJ.run(SPTBatch_.impMainRoi, "Make Inverse", "");
                     Roi roiToMeasureInv = SPTBatch_.impMainRoi.getRoi();
                     impToMeasure.setRoi(roiToMeasure);
                     double meanDirectxx = SPTBatch_.impMainRoi.getStatistics().mean;
                     impToMeasure.setRoi(roiToMeasureInv);
                     double meanInvx = SPTBatch_.impMainRoi.getStatistics().mean;
                     Roi mainRoi;
                     if (meanDirectxx > meanInvx) {
                        mainRoi = roiToMeasure;
                     } else {
                        mainRoi = roiToMeasureInv;
                     }

                     for(jxxxx = 0; jxxxx < SPTBatch_.trackJTable.getRowCount(); ++jxxxx) {
                        if (mainRoi.contains((int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(SPTBatch_.trackJTable.getModel().getValueAt(jxxxx, SPTBatch_.trackJTable.convertColumnIndexToModel(13)).toString())), (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(SPTBatch_.trackJTable.getModel().getValueAt(jxxxx, SPTBatch_.trackJTable.convertColumnIndexToModel(14)).toString()))) == Boolean.TRUE) {
                           SPTBatch_.this.excludeTrack.add(true);
                        } else {
                           SPTBatch_.this.excludeTrack.add(false);
                        }
                     }
                  }

                  if (SPTBatch_.this.enableTrackTable.equals("trackTable")) {
                     Thread tracksThread = new Thread(new Runnable() {
                        public void run() {
                           TablePanel trackTable;
                           int r;
                           int t;
                           int rxx;
                           int j;
                           String var10001;
                           int rxxx;
                           int y;
                           if (SPTBatch_.checkboxSubBg.isSelected()) {
                              trackTable = SPTBatch_.this.createTrackTable(SPTBatch_.model, SPTBatch_.this.ds);
                              SPTBatch_.trackJTable = trackTable.getTable();
                              if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE) {
                                 SPTBatch_.columnNamesTrack = new String[]{"LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + ")", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement"};
                              }

                              if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE) {
                                 SPTBatch_.columnNamesTrack = new String[]{"LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + ")", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement", "Track within Cell"};
                              }

                              if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE) {
                                 SPTBatch_.columnNamesTrack = new String[]{"LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement"};
                              }

                              if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE) {
                                 SPTBatch_.columnNamesTrack = new String[]{"LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement", "Track within Cell"};
                              }

                              String[][] rowDataTrack = new String[SPTBatch_.trackJTable.getRowCount()][SPTBatch_.columnNamesTrack.length];

                              for(r = 0; r < SPTBatch_.trackJTable.getRowCount(); ++r) {
                                 rowDataTrack[r][SPTBatch_.columnNamesTrack.length - 1] = "";

                                 for(t = 0; t < SPTBatch_.trackJTable.getColumnCount(); ++t) {
                                    rowDataTrack[r][t] = String.valueOf(SPTBatch_.trackJTable.getValueAt(r, t));
                                 }
                              }

                              List<Integer> nOfTracks = new ArrayList();

                              for(t = 0; t < SPTBatch_.trackJTable.getRowCount(); ++t) {
                                 nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(t, 2).toString()));
                              }

                              List<Double> allTracks = new ArrayList();
                              List<Double> allTracksDef = new ArrayList();

                              for(rxx = 0; rxx < nOfTracks.size(); ++rxx) {
                                 int counter = false;
                                 List<Double> perTrack = new ArrayList();
                                 List<Double> perTrackDef = new ArrayList();

                                 for(j = 0; j < SPTBatch_.tableSpot.getRowCount(); ++j) {
                                    if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 2).toString()).equals(nOfTracks.get(rxx)) == Boolean.TRUE) {
                                       perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
                                    }
                                 }

                                 if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE) {
                                    for(j = SPTBatch_.minTracksJTF; j < SPTBatch_.maxTracksJTF; ++j) {
                                       perTrackDef.add((Double)perTrack.get(j));
                                    }
                                 }

                                 if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE && perTrackDef.size() != 0) {
                                    allTracksDef.add(perTrackDef.stream().mapToDouble((a) -> {
                                       return a;
                                    }).average().getAsDouble());
                                 }

                                 if (perTrack.size() != 0) {
                                    allTracks.add(perTrack.stream().mapToDouble((a) -> {
                                       return a;
                                    }).average().getAsDouble());
                                 }
                              }

                              ComputeMSD values = new ComputeMSD();
                              values.Compute(nOfTracks, SPTBatch_.this.rtSpots[SPTBatch_.i]);
                              if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE) {
                                 for(rxxx = 0; rxxx < SPTBatch_.trackJTable.getRowCount(); ++rxxx) {
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 13] = String.valueOf(((Double)ComputeMSD.msd1Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 12] = String.valueOf(((Double)ComputeMSD.msd2Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 11] = String.valueOf(((Double)ComputeMSD.msd3Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 10] = String.valueOf(((Double)ComputeMSD.msdValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 9] = String.valueOf(((Double)allTracks.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 8] = String.valueOf(((Double)ComputeMSD.diffValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 7] = String.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString());
                                    if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(rxxx, 3).toString()) >= Double.valueOf((double)SPTBatch_.thLengthJTF)) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Long");
                                    } else {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Short");
                                    }

                                    if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
                                       if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) <= SPTBatch_.this.thD14JTF) {
                                          rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Immobile");
                                       }

                                       if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) > SPTBatch_.this.thD14JTF) {
                                          rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Mobile");
                                       }
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString());
                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 0.6D && Double.valueOf(ComputeMSD.alphaValues.toString()) >= 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Confined");
                                    }

                                    if (Double.valueOf(ComputeMSD.alphaValues.toString()) < 0.9D && Double.valueOf(ComputeMSD.alphaValues.toString()) >= 0.6D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Anomalous");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 1.1D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.9D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Free");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 1.1D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Directed");
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = String.valueOf(ComputeMSD.mssValues.get(rxxx));
                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Unidirectional Ballistic";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Immobile";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) >= 0.45D && (Double)ComputeMSD.mssValues.get(rxxx) <= 0.55D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Free";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.0D && (Double)ComputeMSD.mssValues.get(rxxx) < 0.45D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Confined";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.55D && (Double)ComputeMSD.mssValues.get(rxxx) < 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
                                    }
                                 }
                              }

                              if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE) {
                                 for(rxxx = 0; rxxx < SPTBatch_.trackJTable.getRowCount(); ++rxxx) {
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 14] = String.valueOf(((Double)ComputeMSD.msd1Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 13] = String.valueOf(((Double)ComputeMSD.msd2Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 12] = String.valueOf(((Double)ComputeMSD.msd3Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 11] = String.valueOf(((Double)ComputeMSD.msdValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 10] = String.valueOf(((Double)allTracks.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 9] = String.valueOf(((Double)ComputeMSD.diffValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 8] = String.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString());
                                    if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(rxxx, 3).toString()) >= Double.valueOf((double)SPTBatch_.thLengthJTF)) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 7] = String.valueOf("Long");
                                    } else {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 7] = String.valueOf("Short");
                                    }

                                    if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
                                       if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) <= SPTBatch_.this.thD14JTF) {
                                          rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Immobile");
                                       }

                                       if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) > SPTBatch_.this.thD14JTF) {
                                          rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Mobile");
                                       }
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString());
                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 0.6D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Confined");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 0.9D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.6D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Anomalous");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 1.1D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.9D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Free");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 1.1D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Directed");
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf(ComputeMSD.mssValues.get(rxxx));
                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Unidirectional Ballistic";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Immobile";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) >= 0.45D && (Double)ComputeMSD.mssValues.get(rxxx) <= 0.55D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Free";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.0D && (Double)ComputeMSD.mssValues.get(rxxx) < 0.45D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Confined";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.55D && (Double)ComputeMSD.mssValues.get(rxxx) < 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)SPTBatch_.this.excludeTrack.get(rxxx)).toString();
                                 }
                              }

                              if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE) {
                                 for(rxxx = 0; rxxx < SPTBatch_.trackJTable.getRowCount(); ++rxxx) {
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 14] = String.valueOf(((Double)ComputeMSD.msd1Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 13] = String.valueOf(((Double)ComputeMSD.msd2Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 12] = String.valueOf(((Double)ComputeMSD.msd3Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 11] = String.valueOf(((Double)ComputeMSD.msdValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 11] = String.valueOf(((Double)ComputeMSD.msdValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 10] = String.valueOf(((Double)allTracks.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 9] = String.valueOf(((Double)allTracksDef.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 8] = String.valueOf(((Double)ComputeMSD.diffValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 7] = String.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString());
                                    if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(rxxx, 3).toString()) >= Double.valueOf((double)SPTBatch_.thLengthJTF)) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Long");
                                    } else {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Short");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) <= SPTBatch_.this.thD14JTF) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Immobile");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) > SPTBatch_.this.thD14JTF) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Mobile");
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString());
                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 0.6D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Confined");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 0.9D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.6D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Anomalous");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 1.1D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.9D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Free");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 1.1D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Directed");
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = String.valueOf(ComputeMSD.mssValues.get(rxxx));
                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Unidirectional Ballistic";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Immobile";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) >= 0.45D && (Double)ComputeMSD.mssValues.get(rxxx) <= 0.55D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Free";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.0D && (Double)ComputeMSD.mssValues.get(rxxx) < 0.45D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Confined";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.55D && (Double)ComputeMSD.mssValues.get(rxxx) < 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
                                    }
                                 }
                              }

                              if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE && SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE) {
                                 SPTBatch_.columnNamesTrack = new String[]{"LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + ")", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement", "Track within Cell"};

                                 for(rxxx = 0; rxxx < SPTBatch_.trackJTable.getRowCount(); ++rxxx) {
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 15] = String.valueOf(((Double)ComputeMSD.msd1Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 14] = String.valueOf(((Double)ComputeMSD.msd2Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 13] = String.valueOf(((Double)ComputeMSD.msd3Values.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 12] = String.valueOf(((Double)ComputeMSD.msdValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 11] = String.valueOf(((Double)allTracks.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 10] = String.valueOf(((Double)allTracksDef.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 9] = String.valueOf(((Double)ComputeMSD.diffValues.get(rxxx)).toString());
                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 8] = String.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString());
                                    if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(rxxx, 3).toString()) >= Double.valueOf((double)SPTBatch_.thLengthJTF)) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 7] = String.valueOf("Long");
                                    } else {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 7] = String.valueOf("Short");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) <= SPTBatch_.this.thD14JTF) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Immobile");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rxxx)).toString()) > SPTBatch_.this.thD14JTF) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Mobile");
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString());
                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 0.6D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Confined");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 0.9D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.6D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Anomalous");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) < 1.1D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 0.9D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Free");
                                    }

                                    if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rxxx)).toString()) >= 1.1D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf("Directed");
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf(ComputeMSD.mssValues.get(rxxx));
                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Unidirectional Ballistic";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) == 0.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Immobile";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) >= 0.45D && (Double)ComputeMSD.mssValues.get(rxxx) <= 0.55D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Free";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.0D && (Double)ComputeMSD.mssValues.get(rxxx) < 0.45D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Confined";
                                    }

                                    if ((Double)ComputeMSD.mssValues.get(rxxx) > 0.55D && (Double)ComputeMSD.mssValues.get(rxxx) < 1.0D) {
                                       rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
                                    }

                                    rowDataTrack[rxxx][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)SPTBatch_.this.excludeTrack.get(rxxx)).toString();
                                 }
                              }

                              SPTBatch_.this.rtTrackPerImage = new ResultsTable();

                              for(rxxx = 0; rxxx < rowDataTrack.length; ++rxxx) {
                                 for(y = 0; y < rowDataTrack[rxxx].length; ++y) {
                                    var10001 = SPTBatch_.columnNamesTrack[y];
                                    SPTBatch_.this.rtTrackPerImage.setValue(var10001, rxxx, rowDataTrack[rxxx][y]);
                                 }
                              }

                              try {
                                 SPTBatch_.this.rtTrackPerImage.saveAs(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
                              } catch (IOException var19) {
                                 var19.printStackTrace();
                              }

                              SPTBatch_.this.rtTracks[SPTBatch_.i] = SPTBatch_.this.rtTrackPerImage;
                           }

                           int rx;
                           if (!SPTBatch_.checkboxSubBg.isSelected()) {
                              trackTable = SPTBatch_.this.createTrackTable(SPTBatch_.model, SPTBatch_.this.ds);
                              ComputeMSD valuesx = new ComputeMSD();
                              valuesx.Compute(SPTBatch_.nOfTracks, SPTBatch_.this.rtSpots[SPTBatch_.i]);
                              JTable trackJTable = trackTable.getTable();
                              SPTBatch_.columnNamesTrack = new String[]{"LABEL", "TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", "sMSS Movement"};
                              String[][] rowDataTrackx = new String[trackJTable.getModel().getRowCount()][SPTBatch_.columnNamesTrack.length];

                              for(rx = 0; rx < trackJTable.getModel().getRowCount(); ++rx) {
                                 for(rxx = 0; rxx < trackJTable.getModel().getColumnCount(); ++rxx) {
                                    rowDataTrackx[rx][rxx] = String.valueOf(trackJTable.getModel().getValueAt(rx, rxx));
                                 }
                              }

                              for(rx = 0; rx < trackJTable.getModel().getRowCount(); ++rx) {
                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 12] = String.valueOf(((Double)ComputeMSD.msd1Values.get(rx)).toString());
                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 11] = String.valueOf(((Double)ComputeMSD.msd2Values.get(rx)).toString());
                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 10] = String.valueOf(((Double)ComputeMSD.msd3Values.get(rx)).toString());
                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 9] = String.valueOf(((Double)ComputeMSD.msdValues.get(rx)).toString());
                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 8] = String.valueOf(((Double)ComputeMSD.diffValues.get(rx)).toString());
                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 7] = String.valueOf(((Double)ComputeMSD.d14Values.get(rx)).toString());
                                 if (Double.valueOf(trackJTable.getModel().getValueAt(rx, 3).toString()) >= Double.valueOf((double)SPTBatch_.thLengthJTF)) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Long");
                                 } else {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Short");
                                 }

                                 if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rx)).toString()) <= SPTBatch_.this.thD14JTF) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Immobile");
                                 }

                                 if (Double.valueOf(((Double)ComputeMSD.d14Values.get(rx)).toString()) > SPTBatch_.this.thD14JTF) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Mobile");
                                 }

                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 4] = String.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString());
                                 if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString()) < 0.6D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString()) >= 0.0D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Confined");
                                 }

                                 if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString()) < 0.9D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString()) >= 0.6D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Anomalous");
                                 }

                                 if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString()) < 1.1D && Double.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString()) >= 0.9D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Free");
                                 }

                                 if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(rx)).toString()) >= 1.1D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Directed");
                                 }

                                 rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 2] = String.valueOf(ComputeMSD.mssValues.get(rx));
                                 if ((Double)ComputeMSD.mssValues.get(rx) == 1.0D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 1] = "Unidirectional Ballistic";
                                 }

                                 if ((Double)ComputeMSD.mssValues.get(rx) == 0.0D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 1] = "Immobile";
                                 }

                                 if ((Double)ComputeMSD.mssValues.get(rx) >= 0.45D && (Double)ComputeMSD.mssValues.get(rx) <= 0.55D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 1] = "Free";
                                 }

                                 if ((Double)ComputeMSD.mssValues.get(rx) > 0.0D && (Double)ComputeMSD.mssValues.get(rx) < 0.45D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 1] = "Confined";
                                 }

                                 if ((Double)ComputeMSD.mssValues.get(rx) > 0.55D && (Double)ComputeMSD.mssValues.get(rx) < 1.0D) {
                                    rowDataTrackx[rx][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
                                 }
                              }

                              SPTBatch_.this.rtTrackPerImage = new ResultsTable();

                              for(rx = 0; rx < rowDataTrackx.length; ++rx) {
                                 for(rxx = 0; rxx < rowDataTrackx[rx].length; ++rxx) {
                                    var10001 = SPTBatch_.columnNamesTrack[rxx];
                                    SPTBatch_.this.rtTrackPerImage.setValue(var10001, rx, rowDataTrackx[rx][rxx]);
                                 }
                              }

                              try {
                                 SPTBatch_.this.rtTrackPerImage.saveAs(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
                              } catch (IOException var18) {
                                 var18.printStackTrace();
                              }

                              SPTBatch_.this.rtTracks[SPTBatch_.i] = SPTBatch_.this.rtTrackPerImage;
                           }

                           String[][] rowData = new String[4][SPTBatch_.this.columnsMovements.length];
                           int totalTracks = false;
                           r = 0;
                           t = 0;
                           rx = 0;
                           rxx = 0;
                           rxxx = 0;
                           y = 0;
                           int shortTracks = 0;
                           j = 0;
                           int shortAnom = 0;
                           int shortFree = 0;
                           int shortDirect = 0;

                           for(int rxxxx = 0; rxxxx < SPTBatch_.this.rtTrackPerImage.size(); ++rxxxx) {
                              if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE) {
                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Long")) {
                                    ++r;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Short")) {
                                    ++shortTracks;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn(), rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn(), rxxxx).equals("Confined") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Long")) {
                                    ++t;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx).equals("Confined") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Short")) {
                                    ++j;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx).equals("Unidirectional Ballistic") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Long")) {
                                    ++rx;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx).equals("Anomalous") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Short")) {
                                    ++shortAnom;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn(), rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn(), rxxxx).equals("Free") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Long")) {
                                    ++rxx;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx).equals("Free") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Short")) {
                                    ++shortFree;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn(), rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn(), rxxxx).equals("Directed") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Long")) {
                                    ++rxxx;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 2, rxxxx).equals("Directed") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Short")) {
                                    ++shortDirect;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 4, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 4, rxxxx).equals("Immobile")) {
                                    ++y;
                                 }
                              }

                              if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE) {
                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Long")) {
                                    ++r;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Short")) {
                                    ++shortTracks;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 1, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 1, rxxxx).equals("Confined") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Long")) {
                                    ++t;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx).equals("Confined") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Short")) {
                                    ++j;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx).equals("Unidirectional Ballistic") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Long")) {
                                    ++rx;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx).equals("Anomalous") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Short")) {
                                    ++shortAnom;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 1, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 1, rxxxx).equals("Free") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Long")) {
                                    ++rxx;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx).equals("Free") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Short")) {
                                    ++shortFree;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 1, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 1, rxxxx).equals("Directed") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Long")) {
                                    ++rxxx;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 3, rxxxx).equals("Directed") && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 6, rxxxx).equals("Short")) {
                                    ++shortDirect;
                                 }

                                 if (SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx) != null && SPTBatch_.this.rtTrackPerImage.getStringValue(SPTBatch_.this.rtTrackPerImage.getLastColumn() - 5, rxxxx).equals("Immobile")) {
                                    ++y;
                                 }
                              }
                           }

                           SPTBatch_ var10000 = SPTBatch_.this;
                           var10000.totalTracksDef = var10000.totalTracksDef + SPTBatch_.this.rtTrackPerImage.size();
                           var10000 = SPTBatch_.this;
                           var10000.longTracksDef = var10000.longTracksDef + r;
                           var10000 = SPTBatch_.this;
                           var10000.longConfinedDef = var10000.longConfinedDef + t;
                           var10000 = SPTBatch_.this;
                           var10000.longUniBalDef = var10000.longUniBalDef + rx;
                           var10000 = SPTBatch_.this;
                           var10000.longFreeDef = var10000.longFreeDef + rxx;
                           var10000 = SPTBatch_.this;
                           var10000.longDirectDef = var10000.longDirectDef + rxxx;
                           var10000 = SPTBatch_.this;
                           var10000.shortTracksDef = var10000.shortTracksDef + shortTracks;
                           var10000 = SPTBatch_.this;
                           var10000.shortConfinedDef = var10000.shortConfinedDef + j;
                           var10000 = SPTBatch_.this;
                           var10000.shortAnomDef = var10000.shortAnomDef + shortAnom;
                           var10000 = SPTBatch_.this;
                           var10000.shortFreeDef = var10000.shortFreeDef + shortFree;
                           var10000 = SPTBatch_.this;
                           var10000.shortDirectDef = var10000.shortDirectDef + shortDirect;
                           var10000 = SPTBatch_.this;
                           var10000.immobDef = var10000.immobDef + y;
                           rowData[0][0] = String.valueOf(SPTBatch_.this.rtTrackPerImage.size());
                           rowData[1][0] = String.valueOf("");
                           rowData[2][0] = String.valueOf("");
                           rowData[3][0] = String.valueOf("");
                           rowData[0][1] = String.valueOf(r);
                           rowData[1][1] = String.valueOf(" ");
                           rowData[2][1] = String.valueOf("Short Tracks");
                           rowData[3][1] = String.valueOf(shortTracks);
                           rowData[0][2] = String.valueOf(t);
                           rowData[1][2] = String.valueOf(" ");
                           rowData[2][2] = String.valueOf("Short Confined");
                           rowData[3][2] = String.valueOf(j);
                           rowData[0][3] = String.valueOf(rx);
                           rowData[1][3] = String.valueOf(" ");
                           rowData[2][3] = String.valueOf("Short Anomalous");
                           rowData[3][3] = String.valueOf(shortAnom);
                           rowData[0][4] = String.valueOf(rxx);
                           rowData[1][4] = String.valueOf(" ");
                           rowData[2][4] = String.valueOf("Short Free");
                           rowData[3][4] = String.valueOf(shortFree);
                           rowData[0][5] = String.valueOf(rxxx);
                           rowData[1][5] = String.valueOf(" ");
                           rowData[2][5] = String.valueOf("Short Direct");
                           rowData[3][5] = String.valueOf(shortDirect);
                           rowData[0][6] = String.valueOf(y);
                           rowData[1][6] = String.valueOf("");
                           rowData[2][6] = String.valueOf("");
                           rowData[3][6] = String.valueOf("");
                           ResultsTable rtTrackSum = new ResultsTable();

                           for(int rxxxxx = 0; rxxxxx < rowData.length; ++rxxxxx) {
                              for(int c = 0; c < rowData[rxxxxx].length; ++c) {
                                 rtTrackSum.setValue(SPTBatch_.this.columnsMovements[c], rxxxxx, rowData[rxxxxx][c]);
                              }
                           }

                           try {
                              rtTrackSum.saveAs(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "SummaryTracks" + ".csv");
                           } catch (IOException var17) {
                              var17.printStackTrace();
                           }

                           if (SPTBatch_.checkPBS.isSelected()) {
                              (new PhotobleachingSpotPlot()).Compute();
                           }

                        }
                     });
                     tracksThread.start();
                  }

                  if (SPTBatch_.this.enableBranchTable.equals("branchTable")) {
                     branchTable = SPTBatch_.createBranchTable(SPTBatch_.model, SPTBatch_.selectionModel);

                     try {
                        branchTable.exportToCsv(new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "Branch analysis" + ".csv"));
                     } catch (IOException var135) {
                        var135.printStackTrace();
                     }
                  }
               }

               ArrayList edges;
               if (SPTBatch_.checkboxSP.isSelected() == Boolean.TRUE) {
                  SPTBatch_.directChemo = new File(SPTBatch_.directImages.getAbsolutePath() + File.separator + "Chemotaxis_Analysis");
                  if (!SPTBatch_.directChemo.exists()) {
                     SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directChemo.getName());
                     result = false;

                     try {
                        SPTBatch_.directChemo.mkdir();
                        result = true;
                     } catch (SecurityException var134) {
                     }

                     if (result) {
                        SPTBatch_.taskOutput.append("DIR created");
                     }
                  }

                  TablePanel<Spot> spotTable = SPTBatch_.this.createSpotTable(SPTBatch_.model, SPTBatch_.this.ds);
                  JTable spotJTable = spotTable.getTable();
                  edges = new ArrayList();
                  Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
                  chartx = null;
                  Iterator var242 = trackIDs.iterator();

                  while(true) {
                     if (!var242.hasNext()) {
                        Chemotaxis_ToolModified chemotool = new Chemotaxis_ToolModified(edges);
                        chemotool.run("");
                        break;
                     }

                     Integer id = (Integer)var242.next();
                     Set<Spot> track = SPTBatch_.model.getTrackModel().trackSpots(id);
                     framesByTrack = new ArrayList();
                     ArrayList<Float> xByTrack = new ArrayList();
                     framesByTrackx = new ArrayList();
                     framesByTrackSort = new ArrayList();
                     new ArrayList();
                     new ArrayList();
                     ArrayList<Float> trackIDxxxx = new ArrayList();
                     ArrayList<Integer> indexes = new ArrayList();
                     Iterator var81 = track.iterator();

                     while(var81.hasNext()) {
                        Spot spot = (Spot)var81.next();
                        trackIDxxxx.add(Float.valueOf((float)id) + Float.valueOf("1.0"));
                        framesByTrack.add(Float.valueOf(spot.getFeature("FRAME").toString()));
                        xByTrack.add(Float.valueOf(spot.getFeature("POSITION_X").toString()));
                        framesByTrackx.add(Float.valueOf(spot.getFeature("POSITION_Y").toString()));
                        framesByTrackSort.add(Float.valueOf(spot.getFeature("FRAME").toString()));
                     }

                     Collections.sort(framesByTrackSort);

                     int yx;
                     for(yx = 0; yx < framesByTrackSort.size(); ++yx) {
                        indexes.add(framesByTrack.indexOf(framesByTrackSort.get(yx)));
                     }

                     for(yx = 0; yx < indexes.size(); ++yx) {
                        edges.add((Float)trackIDxxxx.get(yx));
                        edges.add((Float)framesByTrackSort.get(yx) + Float.valueOf("1.0"));
                        edges.add((Float)xByTrack.get((Integer)indexes.get(yx)));
                        edges.add((Float)framesByTrackx.get((Integer)indexes.get(yx)));
                     }
                  }
               }

               if (SPTBatch_.checkboxDiff.isSelected() == Boolean.TRUE) {
                  SPTBatch_.directDiff = new File(SPTBatch_.directImages.getAbsolutePath() + File.separator + "Trajectory_Classifier");
                  if (!SPTBatch_.directDiff.exists()) {
                     SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directDiff.getName());
                     result = false;

                     try {
                        SPTBatch_.directDiff.mkdir();
                        result = true;
                     } catch (SecurityException var133) {
                     }

                     if (result) {
                        SPTBatch_.taskOutput.append("DIR created");
                     }
                  }

                  TraJClassifierTest_ tc = new TraJClassifierTest_();
                  tc.run("");
               }

               if (SPTBatch_.checkboxMSD.isSelected() == Boolean.TRUE) {
                  TrackProcessorMSD_Modified msdPlot = new TrackProcessorMSD_Modified();
                  msdPlot.Compute(SPTBatch_.nOfTracks, SPTBatch_.this.rtSpots[SPTBatch_.i]);
               }

               if (SPTBatch_.checkCluster.isSelected() == Boolean.TRUE) {
                  ClusterSizeAnalysis clusterAnal = new ClusterSizeAnalysis();
                  DefaultCategoryDataset barDatasetCount = new DefaultCategoryDataset();
                  DefaultCategoryDataset barDatasetPercet = new DefaultCategoryDataset();
                  counter1 = 0;
                  int counter2 = 0;
                  maxFrame = 0;
                  counter4 = 0;
                  counter = 0;
                  n = 0;
                  framex = 0;
                  jxxxx = 0;
                  y = 0;
                  counter10 = 0;
                  int r = 0;

                  while(true) {
                     if (r >= SPTBatch_.nOfTracks.size()) {
                        barDatasetCount.setValue((double)counter1, "receptors/particle", "1");
                        barDatasetCount.setValue((double)counter2, "receptors/particle", "2");
                        barDatasetCount.setValue((double)maxFrame, "receptors/particle", "3");
                        barDatasetCount.setValue((double)counter4, "receptors/particle", "4");
                        barDatasetCount.setValue((double)counter, "receptors/particle", "5");
                        barDatasetCount.setValue((double)n, "receptors/particle", "6");
                        barDatasetCount.setValue((double)framex, "receptors/particle", "7");
                        barDatasetCount.setValue((double)jxxxx, "receptors/particle", "8");
                        barDatasetCount.setValue((double)y, "receptors/particle", "9");
                        barDatasetCount.setValue((double)counter10, "receptors/particle", "10");
                        JFreeChart chartCount = ChartFactory.createBarChart("Count of receptors/particle", "receptors/particle", "Count", barDatasetCount, PlotOrientation.VERTICAL, false, true, false);
                        if (SPTBatch_.nOfTracks.size() != 0) {
                           barDatasetPercet.setValue((double)(counter1 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "1");
                           barDatasetPercet.setValue((double)(counter2 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "2");
                           barDatasetPercet.setValue((double)(maxFrame * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "3");
                           barDatasetPercet.setValue((double)(counter4 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "4");
                           barDatasetPercet.setValue((double)(counter * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "5");
                           barDatasetPercet.setValue((double)(n * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "6");
                           barDatasetPercet.setValue((double)(framex * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "7");
                           barDatasetPercet.setValue((double)(jxxxx * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "8");
                           barDatasetPercet.setValue((double)(y * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "9");
                           barDatasetPercet.setValue((double)(counter10 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "10");
                        }

                        if (SPTBatch_.nOfTracks.size() != 0) {
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "1");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "2");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "3");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "4");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "5");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "6");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "7");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "8");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "9");
                           barDatasetPercet.setValue(0.0D, "receptors/particle", "10");
                        }

                        JFreeChart chartPercet = ChartFactory.createBarChart("Percentage of receptors/particle", "receptors/particle", "Percentage-(%)", barDatasetPercet, PlotOrientation.VERTICAL, false, true, false);
                        DecimalFormat pctFormat = new DecimalFormat("##.00%");
                        pctFormat.setMultiplier(1);
                        NumberFormat percent = NumberFormat.getPercentInstance();
                        percent.setMaximumFractionDigits(2);
                        CategoryItemLabelGenerator generatorCount = new StandardCategoryItemLabelGenerator("{2}", NumberFormat.getInstance(), percent);
                        CategoryItemLabelGenerator generatorPercent = new StandardCategoryItemLabelGenerator("{2}", pctFormat);
                        CategoryPlot plotCount = chartCount.getCategoryPlot();
                        CategoryPlot plotPercent = chartPercet.getCategoryPlot();
                        CategoryItemRenderer rendererCount = plotCount.getRenderer();
                        CategoryItemRenderer rendererPercent = plotPercent.getRenderer();
                        NumberAxis rangeAxisCount = (NumberAxis)plotCount.getRangeAxis();
                        NumberAxis rangeAxisPercent = (NumberAxis)plotPercent.getRangeAxis();
                        rangeAxisPercent.setNumberFormatOverride(pctFormat);
                        rangeAxisCount.setAutoRangeIncludesZero(true);
                        rangeAxisPercent.setAutoRangeIncludesZero(true);
                        rendererCount.setDefaultItemLabelGenerator(generatorCount);
                        rendererCount.setDefaultItemLabelFont(new Font("SansSerif", 0, 12));
                        rendererCount.setDefaultItemLabelsVisible(true);
                        rendererPercent.setDefaultItemLabelGenerator(generatorPercent);
                        rendererPercent.setDefaultItemLabelFont(new Font("SansSerif", 0, 12));
                        rendererPercent.setDefaultItemLabelsVisible(true);

                        try {
                           ChartUtils.saveChartAsPNG(new File(SPTBatch_.directCluster.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_ReceptorsPerParticle_Count" + ".png"), chartCount, 500, 400);
                        } catch (IOException var132) {
                           var132.printStackTrace();
                        }

                        try {
                           ChartUtils.saveChartAsPNG(new File(SPTBatch_.directCluster.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_ReceptorsPerParticle_Percentage" + ".png"), chartPercet, 500, 400);
                        } catch (IOException var131) {
                           var131.printStackTrace();
                        }
                        break;
                     }

                     int counterx = false;
                     List<Double> perTrack = new ArrayList();

                     for(int t = 0; t < SPTBatch_.tableSpot.getRowCount(); ++t) {
                        if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t, 2).toString()).equals(SPTBatch_.nOfTracks.get(r)) == Boolean.TRUE) {
                           perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
                        }
                     }

                     clusterAnal.Compute(perTrack, (Integer)SPTBatch_.nOfTracks.get(r));
                     double[] values = new double[perTrack.size()];

                     for(int jxxx = 0; jxxx < perTrack.size(); ++jxxx) {
                        values[jxxx] = (Double)perTrack.get(jxxx);
                     }

                     GaussianMixtureModified gm2 = GaussianMixtureModified.fit(values);
                     if (gm2.components.length == 1) {
                        ++counter1;
                     }

                     if (gm2.components.length == 2) {
                        ++counter2;
                     }

                     if (gm2.components.length == 3) {
                        ++maxFrame;
                     }

                     if (gm2.components.length == 4) {
                        ++counter4;
                     }

                     if (gm2.components.length == 5) {
                        ++counter;
                     }

                     if (gm2.components.length == 6) {
                        ++n;
                     }

                     if (gm2.components.length == 7) {
                        ++framex;
                     }

                     if (gm2.components.length == 8) {
                        ++jxxxx;
                     }

                     if (gm2.components.length == 9) {
                        ++y;
                     }

                     if (gm2.components.length == 10) {
                        ++counter10;
                     }

                     ++r;
                  }
               }

               if (SPTBatch_.checkboxPlot.isSelected() == Boolean.TRUE) {
                  List<Spot> spots1 = new ArrayList(SPTBatch_.this.trackmate.getModel().getSpots().getNSpots(true));
                  Set<String> ySelectedSpotSet = new HashSet();
                  ySelectedSpotSet.add(SPTBatch_.this.ySelectedSpot);
                  Iterator var223 = SPTBatch_.this.trackmate.getModel().getTrackModel().trackIDs(true).iterator();

                  while(var223.hasNext()) {
                     Integer trackIDx = (Integer)var223.next();
                     spots1.addAll(SPTBatch_.this.trackmate.getModel().getTrackModel().trackSpots(trackIDx));
                  }

                  String yAxisLabelx;
                  if (SPTBatch_.ESP.isSelected() && SPTBatch_.this.xSelectedSpot != null && ySelectedSpotSet != null) {
                     chart = null;
                     XYPlot plot = null;
                     Dimension xDimension = (Dimension)SPTBatch_.model.getFeatureModel().getSpotFeatureDimensions().get(SPTBatch_.this.xSelectedSpot);
                     Map<String, Dimension> yDimensions = SPTBatch_.model.getFeatureModel().getSpotFeatureDimensions();
                     Map<String, String> featureNames = SPTBatch_.model.getFeatureModel().getSpotFeatureNames();
                     xAxisLabelx = (String)featureNames.get(SPTBatch_.this.xSelectedSpot) + " (" + TMUtils.getUnitsFor(xDimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + ")";
                     Set<Dimension> dimensions = SPTBatch_.this.getUniqueValues(ySelectedSpotSet, yDimensions);
                     new ArrayList(dimensions.size());
                     Iterator var288 = dimensions.iterator();

                     while(var288.hasNext()) {
                        Dimension dimensionxx = (Dimension)var288.next();
                        String yAxisLabel = TMUtils.getUnitsFor(dimensionxx, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits());
                        List<String> featuresThisDimension = SPTBatch_.getCommonKeys(dimensionxx, ySelectedSpotSet, yDimensions);
                        yAxisLabelx = SPTBatch_.this.buildPlotTitle(featuresThisDimension, featureNames, SPTBatch_.this.xSelectedSpot);
                        ModelDataset datasetxx = new SpotCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, SPTBatch_.this.xSelectedSpot, featuresThisDimension, spots1, true);
                        XYItemRenderer renderer = datasetxx.getRenderer();
                        JFreeChart chartxx = ChartFactory.createXYLineChart(yAxisLabelx, xAxisLabelx, yAxisLabel, datasetxx, PlotOrientation.VERTICAL, true, true, false);
                        chartxx.getTitle().setFont(Fonts.FONT);
                        chartxx.getLegend().setItemFont(Fonts.SMALL_FONT);
                        chartxx.setBackgroundPaint(new Color(220, 220, 220));
                        chartxx.setBorderVisible(false);
                        chartxx.getLegend().setBackgroundPaint(new Color(220, 220, 220));
                        plot = chartxx.getXYPlot();
                        plot.setRenderer(renderer);
                        plot.getRangeAxis().setLabelFont(Fonts.FONT);
                        plot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
                        plot.getDomainAxis().setLabelFont(Fonts.FONT);
                        plot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
                        plot.setOutlineVisible(false);
                        plot.setDomainCrosshairVisible(false);
                        plot.setDomainGridlinesVisible(false);
                        plot.setRangeCrosshairVisible(false);
                        plot.setRangeGridlinesVisible(false);
                        plot.setBackgroundAlpha(0.0F);
                        ((NumberAxis)plot.getRangeAxis()).setAutoRangeIncludesZero(false);
                        plot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
                        plot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
                     }

                     try {
                        ChartUtils.saveChartAsPNG(new File(SPTBatch_.directSPT + File.separator + SPTBatch_.imps.getShortTitle() + "_" + SPTBatch_.this.xSelectedSpot + "-" + SPTBatch_.this.ySelectedSpot + ".png"), plot.getChart(), 500, 270);
                     } catch (IOException var130) {
                        var130.printStackTrace();
                     }
                  }

                  edges = new ArrayList();
                  Set<String> ySelectedLinkSet = new HashSet();
                  ySelectedLinkSet.add(SPTBatch_.this.ySelectedLink);
                  Iterator var248 = SPTBatch_.this.trackmate.getModel().getTrackModel().trackIDs(true).iterator();

                  while(var248.hasNext()) {
                     Integer trackIDxx = (Integer)var248.next();
                     edges.addAll(SPTBatch_.this.trackmate.getModel().getTrackModel().trackEdges(trackIDxx));
                  }

                  Map yDimensionsxx;
                  if (SPTBatch_.ELP.isSelected() && SPTBatch_.this.xSelectedLink != null && ySelectedLinkSet != null) {
                     chartx = null;
                     XYPlot plotx = null;
                     Dimension xDimensionx = (Dimension)SPTBatch_.model.getFeatureModel().getEdgeFeatureDimensions().get(SPTBatch_.this.xSelectedLink);
                     Map<String, Dimension> yDimensionsx = SPTBatch_.model.getFeatureModel().getEdgeFeatureDimensions();
                     yDimensionsxx = SPTBatch_.model.getFeatureModel().getEdgeFeatureNames();
                     String xAxisLabelxx = (String)yDimensionsxx.get(SPTBatch_.this.xSelectedLink) + " (" + TMUtils.getUnitsFor(xDimensionx, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + ")";
                     Set<Dimension> dimensionsxxx = SPTBatch_.this.getUniqueValues(ySelectedLinkSet, yDimensionsx);
                     new ArrayList(dimensionsxxx.size());
                     Iterator var290 = dimensionsxxx.iterator();

                     while(var290.hasNext()) {
                        Dimension dimension = (Dimension)var290.next();
                        yAxisLabelx = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits());
                        List<String> featuresThisDimensionx = SPTBatch_.getCommonKeys(dimension, ySelectedLinkSet, yDimensionsx);
                        String titlex = SPTBatch_.this.buildPlotTitle(featuresThisDimensionx, yDimensionsxx, SPTBatch_.this.xSelectedLink);
                        ModelDataset datasetxxx = new EdgeCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, SPTBatch_.this.xSelectedLink, featuresThisDimensionx, edges, true);
                        XYItemRenderer rendererx = datasetxxx.getRenderer();
                        JFreeChart chartxxxx = ChartFactory.createXYLineChart(titlex, xAxisLabelxx, yAxisLabelx, datasetxxx, PlotOrientation.VERTICAL, true, true, false);
                        chartxxxx.getTitle().setFont(Fonts.FONT);
                        chartxxxx.getLegend().setItemFont(Fonts.SMALL_FONT);
                        chartxxxx.setBackgroundPaint(new Color(220, 220, 220));
                        chartxxxx.setBorderVisible(false);
                        chartxxxx.getLegend().setBackgroundPaint(new Color(220, 220, 220));
                        plotx = chartxxxx.getXYPlot();
                        plotx.setRenderer(rendererx);
                        plotx.getRangeAxis().setLabelFont(Fonts.FONT);
                        plotx.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
                        plotx.getDomainAxis().setLabelFont(Fonts.FONT);
                        plotx.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
                        plotx.setOutlineVisible(false);
                        plotx.setDomainCrosshairVisible(false);
                        plotx.setDomainGridlinesVisible(false);
                        plotx.setRangeCrosshairVisible(false);
                        plotx.setRangeGridlinesVisible(false);
                        plotx.setBackgroundAlpha(0.0F);
                        ((NumberAxis)plotx.getRangeAxis()).setAutoRangeIncludesZero(false);
                        plotx.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
                        plotx.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
                     }

                     try {
                        ChartUtils.saveChartAsPNG(new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + SPTBatch_.this.xSelectedLink + "-" + SPTBatch_.this.ySelectedLink + ".png"), plotx.getChart(), 500, 270);
                     } catch (IOException var129) {
                        var129.printStackTrace();
                     }
                  }

                  Set<String> ySelectedTrackSet = new HashSet();
                  ySelectedTrackSet.add(SPTBatch_.this.ySelectedTrack);
                  String yFeature;
                  if (SPTBatch_.ETP.isSelected() && SPTBatch_.this.xSelectedTrack != null && ySelectedTrackSet != null) {
                     SPTBatch_.this.tracksID = new ArrayList(SPTBatch_.this.trackmate.getModel().getTrackModel().unsortedTrackIDs(true));
                     chartxxx = null;
                     XYPlot plotxx = null;
                     Dimension xDimensionxx = (Dimension)SPTBatch_.model.getFeatureModel().getTrackFeatureDimensions().get(SPTBatch_.this.xSelectedTrack);
                     yDimensionsxx = SPTBatch_.model.getFeatureModel().getTrackFeatureDimensions();
                     Map<String, String> featureNamesx = SPTBatch_.model.getFeatureModel().getTrackFeatureNames();
                     yFeature = (String)featureNamesx.get(SPTBatch_.this.xSelectedTrack) + " (" + TMUtils.getUnitsFor(xDimensionxx, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + ")";
                     Set<Dimension> dimensionsx = SPTBatch_.this.getUniqueValues(ySelectedTrackSet, yDimensionsxx);
                     new ArrayList(dimensionsx.size());
                     Iterator var295 = dimensionsx.iterator();

                     while(var295.hasNext()) {
                        Dimension dimensionx = (Dimension)var295.next();
                        String yAxisLabelxx = TMUtils.getUnitsFor(dimensionx, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits());
                        List<String> featuresThisDimensionxx = SPTBatch_.getCommonKeys(dimensionx, ySelectedTrackSet, yDimensionsxx);
                        String titlexx = SPTBatch_.this.buildPlotTitle(featuresThisDimensionxx, featureNamesx, SPTBatch_.this.xSelectedTrack);
                        ModelDataset datasetxxxx = new TrackCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, SPTBatch_.this.xSelectedTrack, featuresThisDimensionxx, SPTBatch_.this.tracksID);
                        XYItemRenderer rendererxx = datasetxxxx.getRenderer();
                        JFreeChart chartxxxxx = ChartFactory.createXYLineChart(titlexx, yFeature, yAxisLabelxx, datasetxxxx, PlotOrientation.VERTICAL, true, true, false);
                        chartxxxxx.getTitle().setFont(Fonts.FONT);
                        chartxxxxx.getLegend().setItemFont(Fonts.SMALL_FONT);
                        chartxxxxx.setBackgroundPaint(new Color(220, 220, 220));
                        chartxxxxx.setBorderVisible(false);
                        chartxxxxx.getLegend().setBackgroundPaint(new Color(220, 220, 220));
                        plotxx = chartxxxxx.getXYPlot();
                        plotxx.setRenderer(rendererxx);
                        plotxx.getRangeAxis().setLabelFont(Fonts.FONT);
                        plotxx.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
                        plotxx.getDomainAxis().setLabelFont(Fonts.FONT);
                        plotxx.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
                        plotxx.setOutlineVisible(false);
                        plotxx.setDomainCrosshairVisible(false);
                        plotxx.setDomainGridlinesVisible(false);
                        plotxx.setRangeCrosshairVisible(false);
                        plotxx.setRangeGridlinesVisible(false);
                        plotxx.setBackgroundAlpha(0.0F);
                        ((NumberAxis)plotxx.getRangeAxis()).setAutoRangeIncludesZero(false);
                        plotxx.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
                        plotxx.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
                     }

                     try {
                        ChartUtils.saveChartAsPNG(new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + SPTBatch_.this.xSelectedTrack + "-" + SPTBatch_.this.ySelectedTrack + ".png"), plotxx.getChart(), 500, 270);
                     } catch (IOException var128) {
                        var128.printStackTrace();
                     }
                  }

                  maxFrame = spots.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();
                  int[] nSpots = new int[maxFrame + 1];
                  double[] time = new double[maxFrame + 1];
                  XYPlot plotxxx = null;

                  for(framex = 0; framex <= maxFrame; ++framex) {
                     nSpots[framex] = spots.getNSpots(framex, true);
                     time[framex] = (double)framex * SPTBatch_.this.settings.dt;
                  }

                  SPTBatch_.NSpotPerFrameDataset datasetx = new SPTBatch_.NSpotPerFrameDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, time, nSpots);
                  yFeature = "N spots";
                  Map<String, Dimension> dimMap = new HashMap(2);
                  dimMap.put("N spots", Dimension.NONE);
                  dimMap.put("POSITION_T", Dimension.TIME);
                  Map<String, String> nameMap = new HashMap(2);
                  nameMap.put("N spots", "N spots");
                  nameMap.put("POSITION_T", "T");
                  String xAxisLabelxxx = (String)nameMap.get("POSITION_T") + " (" + TMUtils.getUnitsFor(Dimension.TIME, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + ")";
                  Set<Dimension> dimensionsxx = SPTBatch_.this.getUniqueValues(Collections.singletonList("N spots"), dimMap);
                  new ArrayList(dimensionsxx.size());
                  Iterator var322 = dimensionsxx.iterator();

                  while(var322.hasNext()) {
                     Dimension dimensionxxx = (Dimension)var322.next();
                     String yAxisLabelxxx = TMUtils.getUnitsFor(dimensionxxx, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits());
                     List<String> featuresThisDimensionxxx = SPTBatch_.getCommonKeys(dimensionxxx, Collections.singletonList("N spots"), dimMap);
                     String titlexxx = SPTBatch_.this.buildPlotTitle(featuresThisDimensionxxx, nameMap, "POSITION_T");
                     XYItemRenderer rendererxxx = datasetx.getRenderer();
                     JFreeChart chartxxxxxx = ChartFactory.createXYLineChart(titlexxx, xAxisLabelxxx, yAxisLabelxxx, datasetx, PlotOrientation.VERTICAL, true, true, false);
                     chartxxxxxx.getTitle().setFont(Fonts.FONT);
                     chartxxxxxx.getLegend().setItemFont(Fonts.SMALL_FONT);
                     chartxxxxxx.setBackgroundPaint(new Color(220, 220, 220));
                     chartxxxxxx.setBorderVisible(false);
                     chartxxxxxx.getLegend().setBackgroundPaint(new Color(220, 220, 220));
                     plotxxx = chartxxxxxx.getXYPlot();
                     plotxxx.setRenderer(rendererxxx);
                     plotxxx.getRangeAxis().setLabelFont(Fonts.FONT);
                     plotxxx.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
                     plotxxx.getDomainAxis().setLabelFont(Fonts.FONT);
                     plotxxx.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
                     plotxxx.setOutlineVisible(false);
                     plotxxx.setDomainCrosshairVisible(false);
                     plotxxx.setDomainGridlinesVisible(false);
                     plotxxx.setRangeCrosshairVisible(false);
                     plotxxx.setRangeGridlinesVisible(false);
                     plotxxx.setBackgroundAlpha(0.0F);
                     ((NumberAxis)plotxxx.getRangeAxis()).setAutoRangeIncludesZero(false);
                     plotxxx.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
                     plotxxx.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
                  }

                  try {
                     ChartUtils.saveChartAsPNG(new File(SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "Nspotsvs.Time" + ".png"), plotxxx.getChart(), 500, 270);
                  } catch (IOException var127) {
                     var127.printStackTrace();
                  }
               }

               if (SPTBatch_.checkbox4.isSelected() == Boolean.TRUE) {
                  if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE) {
                     IJ.saveAs(SPTBatch_.this.capture, "Tiff", SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i]);
                  }

                  if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE) {
                     for(frame = 0; frame < SPTBatch_.this.excludeTrack.size(); ++frame) {
                        int trackID = Integer.parseInt(SPTBatch_.trackJTable.getValueAt(frame, 2).toString());
                        SPTBatch_.this.trackmate.getModel().beginUpdate();

                        try {
                           SPTBatch_.this.trackmate.getModel().setTrackVisibility(trackID, (Boolean)SPTBatch_.this.excludeTrack.get(frame));
                        } finally {
                           SPTBatch_.this.trackmate.getModel().endUpdate();
                        }

                        SPTBatch_.this.displayer.render();
                        SPTBatch_.this.displayer.refresh();
                     }

                     if (SPTBatch_.imps.getNFrames() > 1) {
                        firstFrame = Math.max(1, Math.min(SPTBatch_.imps.getNFrames(), 1));
                        lastFrame = Math.min(SPTBatch_.imps.getNFrames(), Math.max(SPTBatch_.imps.getNFrames(), 1));
                     }

                     if (SPTBatch_.imps.getNSlices() > 1) {
                        firstFrame = Math.max(1, Math.min(SPTBatch_.imps.getNSlices(), 1));
                        lastFrame = Math.min(SPTBatch_.imps.getNSlices(), Math.max(SPTBatch_.imps.getNSlices(), 1));
                     }

                     SPTBatch_.taskOutput.append("Capturing TrackMate overlay from frame " + firstFrame + " to " + lastFrame + ".\n");
                     bounds = SPTBatch_.this.displayer.getImp().getCanvas().getBounds();
                     width = bounds.width;
                     height = bounds.height;
                     nCaptures = lastFrame - firstFrame + 1;
                     stack = new ImageStack(width, height);
                     channel = SPTBatch_.this.displayer.getImp().getChannel();
                     slice = SPTBatch_.this.displayer.getImp().getSlice();
                     SPTBatch_.this.displayer.getImp().getCanvas().hideZoomIndicator(true);

                     for(frame = firstFrame; frame <= lastFrame; ++frame) {
                        SPTBatch_.this.displayer.getImp().setPositionWithoutUpdate(channel, slice, frame);
                        bi = new BufferedImage(width, height, 2);
                        SPTBatch_.this.displayer.getImp().getCanvas().paint(bi.getGraphics());
                        cp = new ColorProcessor(bi);
                        index = SPTBatch_.this.displayer.getImp().getStackIndex(channel, slice, frame);
                        stack.addSlice(SPTBatch_.this.displayer.getImp().getImageStack().getSliceLabel(index), cp);
                     }

                     SPTBatch_.this.displayer.getImp().getCanvas().hideZoomIndicator(false);
                     SPTBatch_.this.capture = new ImagePlus("TrackMate capture of " + SPTBatch_.this.displayer.getImp().getShortTitle(), stack);
                     SPTBatch_.transferCalibration(SPTBatch_.this.displayer.getImp(), SPTBatch_.this.capture);
                     IJ.saveAs(SPTBatch_.this.capture, "Tiff", SPTBatch_.directSPT.getAbsolutePath() + File.separator + SPTBatch_.this.imageTitles[SPTBatch_.i]);
                  }
               }

               if (SPTBatch_.checkbox3.isSelected() == Boolean.TRUE) {
                  try {
                     FileWriter writer = new FileWriter(SPTBatch_.directSPT.getAbsolutePath() + File.separator + "Log" + "_" + SPTBatch_.this.imageTitles[SPTBatch_.i].replaceAll("\\.tif+$", "") + ".txt");
                     SPTBatch_.taskOutput.write(writer);
                     writer.close();
                  } catch (IOException var125) {
                  }
               }

               SPTBatch_.imps.hide();
            }

            Thread trackSummaryCols;
            if (SPTBatch_.this.enableTrackTable.equals("trackTable")) {
               trackSummaryCols = new Thread(new Runnable() {
                  public void run() {
                     String[][] rowDataDef = new String[4][SPTBatch_.this.columnsMovements.length];
                     rowDataDef[0][0] = String.valueOf(SPTBatch_.this.totalTracksDef);
                     rowDataDef[1][0] = String.valueOf("");
                     rowDataDef[2][0] = String.valueOf("");
                     rowDataDef[3][0] = String.valueOf("");
                     rowDataDef[0][1] = String.valueOf(SPTBatch_.this.longTracksDef);
                     rowDataDef[1][1] = String.valueOf(" ");
                     rowDataDef[2][1] = String.valueOf("Short Tracks");
                     rowDataDef[3][1] = String.valueOf(SPTBatch_.this.shortTracksDef);
                     rowDataDef[0][2] = String.valueOf(SPTBatch_.this.longConfinedDef);
                     rowDataDef[1][2] = String.valueOf(" ");
                     rowDataDef[2][2] = String.valueOf("Short Confined");
                     rowDataDef[3][2] = String.valueOf(SPTBatch_.this.shortConfinedDef);
                     rowDataDef[0][3] = String.valueOf(SPTBatch_.this.longUniBalDef);
                     rowDataDef[1][3] = String.valueOf(" ");
                     rowDataDef[2][3] = String.valueOf("Short Anomalous");
                     rowDataDef[3][3] = String.valueOf(SPTBatch_.this.shortAnomDef);
                     rowDataDef[0][4] = String.valueOf(SPTBatch_.this.longFreeDef);
                     rowDataDef[1][4] = String.valueOf(" ");
                     rowDataDef[2][4] = String.valueOf("Short Free");
                     rowDataDef[3][4] = String.valueOf(SPTBatch_.this.shortFreeDef);
                     rowDataDef[0][5] = String.valueOf(SPTBatch_.this.longDirectDef);
                     rowDataDef[1][5] = String.valueOf(" ");
                     rowDataDef[2][5] = String.valueOf("Short Direct");
                     rowDataDef[3][5] = String.valueOf(SPTBatch_.this.shortDirectDef);
                     rowDataDef[0][6] = String.valueOf(SPTBatch_.this.immobDef);
                     rowDataDef[1][6] = String.valueOf("");
                     rowDataDef[2][6] = String.valueOf("");
                     rowDataDef[3][6] = String.valueOf("");
                     ResultsTable rtTrackSummary = new ResultsTable();

                     for(int r = 0; r < rowDataDef.length; ++r) {
                        for(int c = 0; c < rowDataDef[r].length; ++c) {
                           rtTrackSummary.setValue(SPTBatch_.this.columnsMovements[c], r, rowDataDef[r][c]);
                        }
                     }

                     try {
                        rtTrackSummary.saveAs(SPTBatch_.directSummary.getAbsolutePath() + File.separator + "SummaryTracks_Condition" + ".csv");
                     } catch (IOException var5) {
                        var5.printStackTrace();
                     }

                  }
               });
               trackSummaryCols.start();
            }

            if (SPTBatch_.checkSummary.isSelected()) {
               trackSummaryCols = new Thread(new Runnable() {
                  public void run() {
                     for(int x = 0; x < SPTBatch_.this.selectedItems.size(); ++x) {
                        ResultsTable rtTrackSum;
                        int y;
                        int z;
                        if (summaryColsWindow.combo.getSelectedIndex() == 0) {
                           rtTrackSum = new ResultsTable();
                           y = 0;

                           while(true) {
                              if (y >= SPTBatch_.this.rtSpots.length) {
                                 try {
                                    rtTrackSum.saveAs(SPTBatch_.directSummary.getAbsolutePath() + File.separator + (String)SPTBatch_.this.selectedItems.get(x) + "_" + "SummaryCols_Spots" + ".csv");
                                 } catch (IOException var5) {
                                    var5.printStackTrace();
                                 }
                                 break;
                              }

                              for(z = 0; z < SPTBatch_.this.rtSpots[y].size(); ++z) {
                                 rtTrackSum.setValue(listOfFiles[y].getName(), z, SPTBatch_.this.rtSpots[y].getStringValue(Arrays.asList(summaryColsWindow.columnNamesSpot).indexOf(SPTBatch_.this.selectedItems.get(x)), z));
                              }

                              ++y;
                           }
                        }

                        if (summaryColsWindow.combo.getSelectedIndex() == 1) {
                           rtTrackSum = new ResultsTable();
                           y = 0;

                           while(true) {
                              if (y >= SPTBatch_.this.rtLinks.length) {
                                 try {
                                    rtTrackSum.saveAs(SPTBatch_.directSummary.getAbsolutePath() + File.separator + (String)SPTBatch_.this.selectedItems.get(x) + "_" + "SummaryCols_Links" + ".csv");
                                 } catch (IOException var7) {
                                    var7.printStackTrace();
                                 }
                                 break;
                              }

                              for(z = 0; z < SPTBatch_.this.rtLinks[y].size(); ++z) {
                                 rtTrackSum.setValue(listOfFiles[y].getName(), z, SPTBatch_.this.rtLinks[y].getStringValue(Arrays.asList(summaryColsWindow.columnNamesLinks).indexOf(SPTBatch_.this.selectedItems.get(x)), z));
                              }

                              ++y;
                           }
                        }

                        if (summaryColsWindow.combo.getSelectedIndex() == 2) {
                           rtTrackSum = new ResultsTable();

                           for(y = 0; y < SPTBatch_.this.rtTracks.length; ++y) {
                              for(z = 0; z < SPTBatch_.this.rtTracks[y].size(); ++z) {
                                 rtTrackSum.setValue(listOfFiles[y].getName(), z, SPTBatch_.this.rtTracks[y].getStringValue(Arrays.asList(summaryColsWindow.columnNamesTracks).indexOf(SPTBatch_.this.selectedItems.get(x)), z));
                              }
                           }

                           try {
                              rtTrackSum.saveAs(SPTBatch_.directSummary.getAbsolutePath() + File.separator + (String)SPTBatch_.this.selectedItems.get(x) + "_" + "SummaryCols_Tracks" + ".csv");
                           } catch (IOException var6) {
                              var6.printStackTrace();
                           }
                        }
                     }

                  }
               });
               trackSummaryCols.start();
            }

            SPTBatch_.this.enableSpotTable.equals("spotTable");
            SPTBatch_.this.enableLinkTable.equals("linkTable");
            SPTBatch_.this.enableTrackTable.equals("trackTable");
            SPTBatch_.taskOutput.append("              FINISHED!!!");
            frameAnalyzer.setVisible(false);
         }
      });
      this.finishButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE) {
               SPTBatch_.minTracksJTF = Integer.valueOf(SPTBatch_.minTracks.getText());
               SPTBatch_.maxTracksJTF = Integer.valueOf(SPTBatch_.maxTracks.getText());
               SPTBatch_.thLengthJTF = Integer.valueOf(SPTBatch_.thLength.getText());
               if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "DIff") {
                  SPTBatch_.this.thD14JTF = Double.valueOf(SPTBatch_.thD14.getText());
               }
            }

            SPTBatch_.mainProcess.start();
         }
      });
   }

   private static final void transferCalibration(ImagePlus from, ImagePlus to) {
      Calibration fc = from.getCalibration();
      Calibration tc = to.getCalibration();
      tc.setUnit(fc.getUnit());
      tc.setTimeUnit(fc.getTimeUnit());
      tc.frameInterval = fc.frameInterval;
      double mag = from.getCanvas().getMagnification();
      tc.pixelWidth = fc.pixelWidth / mag;
      tc.pixelHeight = fc.pixelHeight / mag;
      tc.pixelDepth = fc.pixelDepth;
   }

   protected ImageIcon createImageIcon(String path) {
      URL imgURL = this.getClass().getResource(path);
      if (imgURL != null) {
         return new ImageIcon(imgURL);
      } else {
         System.err.println("Couldn't find file: " + path);
         return null;
      }
   }

   private Image getScaledImage(Image srcImg, int w, int h) {
      BufferedImage resizedImg = new BufferedImage(w, h, 1);
      Graphics2D g2 = resizedImg.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.drawImage(srcImg, 0, 0, w, h, (ImageObserver)null);
      g2.dispose();
      return resizedImg;
   }

   protected JComponent makeTextPanel(String text) {
      JPanel panel = new JPanel(false);
      JLabel filler = new JLabel(text);
      filler.setHorizontalAlignment(0);
      panel.setLayout(new GridLayout(1, 1));
      panel.add(filler);
      return panel;
   }

   public static ImagePlus[] openBF(String multiSeriesFileName, boolean splitC, boolean splitT, boolean splitZ, boolean autoScale, boolean crop, boolean allSeries) {
      ImagePlus[] imps = null;

      try {
         ImporterOptions options = new ImporterOptions();
         options.setId(multiSeriesFileName);
         options.setSplitChannels(splitC);
         options.setSplitTimepoints(splitT);
         options.setSplitFocalPlanes(splitZ);
         options.setAutoscale(autoScale);
         options.setStackFormat("Hyperstack");
         options.setStackOrder("XYCZT");
         options.setCrop(crop);
         options.setOpenAllSeries(allSeries);
         ImportProcess process = new ImportProcess(options);
         if (!process.execute()) {
            return null;
         } else {
            DisplayHandler displayHandler = new DisplayHandler(process);
            if (options != null && options.isShowOMEXML()) {
               displayHandler.displayOMEXML();
            }

            List<ImagePlus> impsList = (new ImagePlusReaderModified(process)).readImages(false);
            imps = (ImagePlus[])impsList.toArray(new ImagePlus[0]);
            if (options != null && options.showROIs()) {
               displayHandler.displayROIs(imps);
            }

            if (!options.isVirtual()) {
               process.getReader().close();
            }

            return imps;
         }
      } catch (Exception var12) {
         return null;
      }
   }

   private final TablePanel<Spot> createSpotTable(Model model, DisplaySettings ds) {
      List<Spot> objects = new ArrayList();
      Iterator var5 = model.getTrackModel().unsortedTrackIDs(true).iterator();

      while(var5.hasNext()) {
         Integer trackID = (Integer)var5.next();
         objects.addAll(model.getTrackModel().trackSpots(trackID));
      }

      List<String> features = new ArrayList(model.getFeatureModel().getSpotFeatures());
      Map<String, String> featureNames = model.getFeatureModel().getSpotFeatureNames();
      Map<String, String> featureShortNames = model.getFeatureModel().getSpotFeatureShortNames();
      Map<String, String> featureUnits = new HashMap();
      Iterator var9 = features.iterator();

      while(var9.hasNext()) {
         String feature = (String)var9.next();
         Dimension dimension = (Dimension)model.getFeatureModel().getSpotFeatureDimensions().get(feature);
         String units = TMUtils.getUnitsFor(dimension, model.getSpaceUnits(), model.getTimeUnits());
         featureUnits.put(feature, units);
      }

      Map<String, Boolean> isInts = model.getFeatureModel().getSpotFeatureIsInt();
      Map<String, String> infoTexts = new HashMap();
      Function<Spot, String> labelGenerator = (spot) -> {
         return spot.getName();
      };
      BiConsumer<Spot, String> labelSetter = (spot, label) -> {
         spot.setName(label);
      };
      String SPOT_ID = "ID";
      features.add(0, "ID");
      featureNames.put("ID", "Spot ID");
      featureShortNames.put("ID", "Spot ID");
      featureUnits.put("ID", "");
      isInts.put("ID", Boolean.TRUE);
      infoTexts.put("ID", "The id of the spot.");
      String TRACK_ID = "TRACK_ID";
      features.add(1, "TRACK_ID");
      featureNames.put("TRACK_ID", "Track ID");
      featureShortNames.put("TRACK_ID", "Track ID");
      featureUnits.put("TRACK_ID", "");
      isInts.put("TRACK_ID", Boolean.TRUE);
      infoTexts.put("TRACK_ID", "The id of the track this spot belongs to.");
      BiFunction<Spot, String, Double> featureFun = (spot, featurex) -> {
         if (featurex.equals("TRACK_ID")) {
            Integer trackID = model.getTrackModel().trackIDOf(spot);
            return trackID == null ? null : trackID.doubleValue();
         } else {
            return featurex.equals("ID") ? (double)spot.ID() : spot.getFeature(featurex);
         }
      };
      BiConsumer<Spot, Color> colorSetter = (spot, color) -> {
         spot.putFeature("MANUAL_SPOT_COLOR", (double)color.getRGB());
      };
      Supplier<FeatureColorGenerator<Spot>> coloring = () -> {
         return FeatureUtils.createSpotColorGenerator(model, ds);
      };
      TablePanel<Spot> table = new TablePanel(objects, features, featureFun, featureNames, featureShortNames, featureUnits, isInts, infoTexts, coloring, labelGenerator, labelSetter, "MANUAL_SPOT_COLOR", colorSetter);
      return table;
   }

   private final TablePanel<DefaultWeightedEdge> createEdgeTable(Model model, DisplaySettings ds) {
      List<DefaultWeightedEdge> objects = new ArrayList();
      Iterator var5 = model.getTrackModel().unsortedTrackIDs(true).iterator();

      while(var5.hasNext()) {
         Integer trackID = (Integer)var5.next();
         objects.addAll(model.getTrackModel().trackEdges(trackID));
      }

      List<String> features = new ArrayList(model.getFeatureModel().getEdgeFeatures());
      Map<String, String> featureNames = model.getFeatureModel().getEdgeFeatureNames();
      Map<String, String> featureShortNames = model.getFeatureModel().getEdgeFeatureShortNames();
      Map<String, String> featureUnits = new HashMap();
      Iterator var9 = features.iterator();

      String labelSetter;
      while(var9.hasNext()) {
         String feature = (String)var9.next();
         Dimension dimension = (Dimension)model.getFeatureModel().getEdgeFeatureDimensions().get(feature);
         labelSetter = TMUtils.getUnitsFor(dimension, model.getSpaceUnits(), model.getTimeUnits());
         featureUnits.put(feature, labelSetter);
      }

      Map<String, Boolean> isInts = model.getFeatureModel().getEdgeFeatureIsInt();
      Map<String, String> infoTexts = new HashMap();
      Function<DefaultWeightedEdge, String> labelGenerator = (edge) -> {
         return String.format("%s → %s", model.getTrackModel().getEdgeSource(edge).getName(), model.getTrackModel().getEdgeTarget(edge).getName());
      };
      labelSetter = null;
      String TRACK_ID = "TRACK_ID";
      features.add(0, "TRACK_ID");
      featureNames.put("TRACK_ID", "Track ID");
      featureShortNames.put("TRACK_ID", "Track ID");
      featureUnits.put("TRACK_ID", "");
      isInts.put("TRACK_ID", Boolean.TRUE);
      infoTexts.put("TRACK_ID", "The id of the track this spot belongs to.");
      BiFunction<DefaultWeightedEdge, String, Double> featureFun = (edge, featurex) -> {
         if (featurex.equals("TRACK_ID")) {
            Integer trackID = model.getTrackModel().trackIDOf(edge);
            return trackID == null ? null : trackID.doubleValue();
         } else {
            return model.getFeatureModel().getEdgeFeature(edge, featurex);
         }
      };
      BiConsumer<DefaultWeightedEdge, Color> colorSetter = (edge, color) -> {
         model.getFeatureModel().putEdgeFeature(edge, "MANUAL_EGE_COLOR", (double)color.getRGB());
      };
      Supplier<FeatureColorGenerator<DefaultWeightedEdge>> coloring = () -> {
         return FeatureUtils.createTrackColorGenerator(model, ds);
      };
      TablePanel<DefaultWeightedEdge> table = new TablePanel(objects, features, featureFun, featureNames, featureShortNames, featureUnits, isInts, infoTexts, coloring, labelGenerator, labelSetter, "MANUAL_EGE_COLOR", colorSetter);
      return table;
   }

   private final TablePanel<Integer> createTrackTable(Model model, DisplaySettings ds) {
      List<Integer> objects = new ArrayList(model.getTrackModel().trackIDs(true));
      List<String> features = new ArrayList(model.getFeatureModel().getTrackFeatures());
      BiFunction<Integer, String, Double> featureFun = (trackID, featurex) -> {
         return model.getFeatureModel().getTrackFeature(trackID, featurex);
      };
      Map<String, String> featureNames = model.getFeatureModel().getTrackFeatureNames();
      Map<String, String> featureShortNames = model.getFeatureModel().getTrackFeatureShortNames();
      Map<String, String> featureUnits = new HashMap();
      Iterator var10 = features.iterator();

      while(var10.hasNext()) {
         String feature = (String)var10.next();
         Dimension dimension = (Dimension)model.getFeatureModel().getTrackFeatureDimensions().get(feature);
         String units = TMUtils.getUnitsFor(dimension, model.getSpaceUnits(), model.getTimeUnits());
         featureUnits.put(feature, units);
      }

      Map<String, Boolean> isInts = model.getFeatureModel().getTrackFeatureIsInt();
      Map<String, String> infoTexts = new HashMap();
      Function<Integer, String> labelGenerator = (id) -> {
         return model.getTrackModel().name(id);
      };
      BiConsumer<Integer, String> labelSetter = (id, label) -> {
         model.getTrackModel().setName(id, label);
      };
      Supplier<FeatureColorGenerator<Integer>> coloring = () -> {
         return FeatureUtils.createWholeTrackColorGenerator(model, ds);
      };
      TablePanel<Integer> table = new TablePanel(objects, features, featureFun, featureNames, featureShortNames, featureUnits, isInts, infoTexts, coloring, labelGenerator, labelSetter);
      return table;
   }

   public static final TablePanel<SPTBatch_.Branch> createBranchTable(Model model, SelectionModel selectionModel) {
      Logger logger = model.getLogger();
      taskOutput.append("Generating track branches analysis.\n");
      int ntracks = model.getTrackModel().nTracks(true);
      if (ntracks == 0) {
         taskOutput.append("No visible track found. Aborting.\n");
      }

      TimeDirectedNeighborIndex neighborIndex = model.getTrackModel().getDirectedNeighborIndex();
      List<SPTBatch_.Branch> brs = new ArrayList();
      Iterator var7 = model.getTrackModel().unsortedTrackIDs(true).iterator();

      HashMap predecessorMap;
      while(var7.hasNext()) {
         Integer trackID = (Integer)var7.next();
         TrackBranchDecomposition branchDecomposition = ConvexBranchesDecomposition.processTrack(trackID, model.getTrackModel(), neighborIndex, true, false);
         SimpleDirectedGraph<List<Spot>, DefaultEdge> branchGraph = ConvexBranchesDecomposition.buildBranchGraph(branchDecomposition);
         Map<SPTBatch_.Branch, Set<List<Spot>>> successorMap = new HashMap();
         predecessorMap = new HashMap();
         Map<List<Spot>, SPTBatch_.Branch> branchMap = new HashMap();
         Iterator var14 = branchGraph.vertexSet().iterator();

         while(var14.hasNext()) {
            List<Spot> branch = (List)var14.next();
            SPTBatch_.Branch br = new SPTBatch_.Branch();
            branchMap.put(branch, br);
            br.trackName = model.getTrackModel().name(trackID);
            br.putFeature("TRACK_ID", (double)trackID);
            Spot first = (Spot)branch.get(0);
            br.first = first;
            br.putFeature("FIRST", (double)first.ID());
            Spot last = (Spot)branch.get(branch.size() - 1);
            br.last = last;
            br.putFeature("LAST", (double)last.ID());
            br.putFeature("DELTA_T", br.dt());
            double distanceTraveled = Math.sqrt(br.last.squareDistanceTo(br.first));
            br.putFeature("DISTANCE", distanceTraveled);
            double meanV;
            if (branch.size() < 2) {
               meanV = Double.NaN;
            } else {
               Iterator<Spot> it = branch.iterator();
               Spot previous = (Spot)it.next();

               double sum;
               Spot next;
               for(sum = 0.0D; it.hasNext(); previous = next) {
                  next = (Spot)it.next();
                  double dr = Math.sqrt(next.squareDistanceTo(previous));
                  sum += dr;
               }

               meanV = sum / (double)(branch.size() - 1);
            }

            br.putFeature("MEAN_VELOCITY", meanV);
            Set<DefaultEdge> incomingEdges = branchGraph.incomingEdgesOf(branch);
            Set<List<Spot>> predecessors = new HashSet(incomingEdges.size());
            Iterator var25 = incomingEdges.iterator();

            while(var25.hasNext()) {
               DefaultEdge edge = (DefaultEdge)var25.next();
               List<Spot> predecessorBranch = (List)branchGraph.getEdgeSource(edge);
               predecessors.add(predecessorBranch);
            }

            Set<DefaultEdge> outgoingEdges = branchGraph.outgoingEdgesOf(branch);
            Set<List<Spot>> successors = new HashSet(outgoingEdges.size());
            Iterator var51 = outgoingEdges.iterator();

            while(var51.hasNext()) {
               DefaultEdge edge = (DefaultEdge)var51.next();
               List<Spot> successorBranch = (List)branchGraph.getEdgeTarget(edge);
               successors.add(successorBranch);
            }

            successorMap.put(br, successors);
            predecessorMap.put(br, predecessors);
         }

         var14 = successorMap.keySet().iterator();

         while(var14.hasNext()) {
            SPTBatch_.Branch br = (SPTBatch_.Branch)var14.next();
            Set<List<Spot>> succs = (Set)successorMap.get(br);
            Set<SPTBatch_.Branch> succBrs = new HashSet(succs.size());
            Iterator var40 = succs.iterator();

            while(var40.hasNext()) {
               List<Spot> branch = (List)var40.next();
               SPTBatch_.Branch succBr = (SPTBatch_.Branch)branchMap.get(branch);
               succBrs.add(succBr);
            }

            br.successors = succBrs;
            br.putFeature("N_SUCCESSORS", (double)succBrs.size());
            Set<List<Spot>> preds = (Set)predecessorMap.get(br);
            Set<SPTBatch_.Branch> predBrs = new HashSet(preds.size());
            Iterator var43 = preds.iterator();

            while(var43.hasNext()) {
               List<Spot> branch = (List)var43.next();
               SPTBatch_.Branch predBr = (SPTBatch_.Branch)branchMap.get(branch);
               predBrs.add(predBr);
            }

            br.predecessors = predBrs;
            br.putFeature("N_PREDECESSORS", (double)predBrs.size());
         }

         brs.addAll(successorMap.keySet());
      }

      Collections.sort(brs);
      BiFunction<SPTBatch_.Branch, String, Double> featureFun = (brx, feature) -> {
         return brx.getFeature(feature);
      };
      Map<String, String> featureUnits = new HashMap();
      BRANCH_FEATURES_DIMENSIONS.forEach((f, d) -> {
         featureUnits.put(f, TMUtils.getUnitsFor(d, model.getSpaceUnits(), model.getTimeUnits()));
      });
      Map<String, String> infoTexts = new HashMap();
      Function<SPTBatch_.Branch, String> labelGenerator = (b) -> {
         return b.toString();
      };
      predecessorMap = null;
      Supplier<FeatureColorGenerator<SPTBatch_.Branch>> colorSupplier = () -> {
         return (b) -> {
            return Color.WHITE;
         };
      };
      TablePanel<SPTBatch_.Branch> table = new TablePanel(brs, BRANCH_FEATURES, featureFun, BRANCH_FEATURES_NAMES, BRANCH_FEATURES_SHORTNAMES, featureUnits, BRANCH_FEATURES_ISINTS, infoTexts, colorSupplier, labelGenerator, predecessorMap);
      return table;
   }

   private final <K, V> Set<V> getUniqueValues(Iterable<K> keys, Map<K, V> map) {
      Set<V> mapping = new LinkedHashSet();
      Iterator var5 = keys.iterator();

      while(var5.hasNext()) {
         K key = (Object)var5.next();
         mapping.add(map.get(key));
      }

      return mapping;
   }

   private static final <K, V> List<K> getCommonKeys(V targetValue, Iterable<K> keys, Map<K, V> map) {
      ArrayList<K> foundKeys = new ArrayList();
      Iterator var5 = keys.iterator();

      while(var5.hasNext()) {
         K key = (Object)var5.next();
         if (map.get(key).equals(targetValue)) {
            foundKeys.add(key);
         }
      }

      return foundKeys;
   }

   private final String buildPlotTitle(Iterable<String> lYFeatures, Map<String, String> featureNames, String xSelectedSpot) {
      StringBuilder sb = new StringBuilder("Plot of ");
      Iterator<String> it = lYFeatures.iterator();
      sb.append((String)featureNames.get(it.next()));

      while(it.hasNext()) {
         sb.append(", ");
         sb.append((String)featureNames.get(it.next()));
      }

      sb.append(" vs ");
      sb.append((String)featureNames.get(xSelectedSpot));
      sb.append(".");
      return sb.toString();
   }

   private static final float[] toFloat(double[] d) {
      float[] f = new float[d.length];

      for(int i = 0; i < f.length; ++i) {
         f[i] = (float)d[i];
      }

      return f;
   }

   public ImagePlus renderND(HyperStackDisplayer displayer, DisplaySettings ds) {
      Roi initialROI = displayer.getImp().getRoi();
      if (initialROI != null) {
         displayer.getImp().killRoi();
      }

      Overlay overlay = displayer.getImp().getOverlay();
      if (overlay == null) {
         overlay = new Overlay();
         displayer.getImp().setOverlay(overlay);
      }

      overlay.clear();
      if (initialROI != null) {
         displayer.getImp().getOverlay().add(initialROI);
      }

      if (displayer != null) {
         displayer.getImp().updateAndDraw();
      }

      displayer.getImp().setOpenAsHyperStack(true);
      displayer.getImp().getOverlay().add(new SpotOverlay(model, displayer.getImp(), ds));
      displayer.getImp().getOverlay().add(new TrackOverlay(model, displayer.getImp(), ds));
      displayer.getImp().updateAndDraw();
      return displayer.getImp();
   }

   public static ImagePlus[] stack2images(ImagePlus imp) {
      String sLabel = imp.getTitle();
      String sImLabel = "";
      ImageStack stack = imp.getStack();
      int sz = stack.getSize();
      int currentSlice = imp.getCurrentSlice();
      DecimalFormat df = new DecimalFormat("0000");
      ImagePlus[] arrayOfImages = new ImagePlus[imp.getStack().getSize()];

      for(int n = 1; n <= sz; ++n) {
         imp.setSlice(n);
         ImageProcessor ip = imp.getProcessor();
         ImageProcessor newip = ip.createProcessor(ip.getWidth(), ip.getHeight());
         newip.setPixels(ip.getPixelsCopy());
         sImLabel = imp.getStack().getSliceLabel(n);
         if (sImLabel == null || sImLabel.length() < 1) {
            sImLabel = "slice" + df.format((long)n) + "_" + sLabel;
         }

         ImagePlus im = new ImagePlus(sImLabel, newip);
         im.setCalibration(imp.getCalibration());
         arrayOfImages[n - 1] = im;
      }

      imp.setSlice(currentSlice);
      if (imp.isProcessor()) {
         ImageProcessor ip = imp.getProcessor();
         ip.setPixels(ip.getPixels());
      }

      imp.setSlice(currentSlice);
      return arrayOfImages;
   }

   public void exportToCSV(String[][] rowData, String[] titles, File file) {
      try {
         try {
            FileWriter excel = new FileWriter(file);

            int i;
            for(i = 0; i < titles.length; ++i) {
               excel.write(titles[i] + ",");
            }

            excel.write("\n");

            for(i = 0; i < rowData.length; ++i) {
               for(int j = 0; j < rowData[i].length; ++j) {
                  if (j == 11) {
                     excel.write(" ,");
                  }

                  if (j != 11) {
                     if (rowData[i][j].toString() != null || rowData[i][j].toString() != " ") {
                        excel.write(rowData[i][j].toString() + ",");
                     }

                     if (rowData[i][j].toString() == null || rowData[i][j].toString() == " ") {
                        excel.write(" ,");
                     }
                  }
               }

               excel.write("\n");
            }

            excel.close();
         } catch (IOException var7) {
         }
      } catch (NullPointerException var8) {
      }

   }

   public static class Branch implements Comparable<SPTBatch_.Branch> {
      private final Map<String, Double> features = new HashMap();
      private String trackName;
      private Spot first;
      private Spot last;
      private Set<SPTBatch_.Branch> predecessors;
      private Set<SPTBatch_.Branch> successors;

      public String toString() {
         return this.trackName + ": " + this.first + " → " + this.last;
      }

      double dt() {
         return this.last.diffTo(this.first, "POSITION_T");
      }

      public final Double getFeature(String feature) {
         return (Double)this.features.get(feature);
      }

      public final void putFeature(String feature, Double value) {
         this.features.put(feature, value);
      }

      public int compareTo(SPTBatch_.Branch o) {
         if (this.predecessors.size() != o.predecessors.size()) {
            return this.predecessors.size() - o.predecessors.size();
         } else if (this.successors.size() != o.successors.size()) {
            return this.successors.size() - o.successors.size();
         } else {
            return this.first.getName().compareTo(o.first.getName()) != 0 ? this.first.getName().compareTo(o.first.getName()) : this.last.getName().compareTo(o.last.getName());
         }
      }
   }

   class ChooserWizardPanel extends JWizardPanel {
      private ButtonGroup bg;

      public ChooserWizardPanel(JWizardComponents wizardComponents) {
         super(wizardComponents, "");
         this.init();
      }

      private void init() {
         this.setLayout(new GridBagLayout());
         CheckableItem[] items = new CheckableItem[]{new CheckableItem("Spots", true), new CheckableItem("Links", true), new CheckableItem("Tracks", true), new CheckableItem("Branch Analysis", true)};
         SPTBatch_.checkbox1 = new JCheckBox("  Analysis/Statistics Results. ");
         SPTBatch_.checkbox1.setSelected(true);
         SPTBatch_.checkboxDiff = new JCheckBox("  TraJ: Trajectory Classifier. ");
         SPTBatch_.checkboxDiff.setSelected(true);
         SPTBatch_.this.trajButton = new JButton("Tune Parameters");
         SPTBatch_.checkboxSubBg = new JCheckBox(" Subtract Background :  ");
         SPTBatch_.checkboxSubBg.setSelected(false);
         SPTBatch_.checkExcludeTracks = new JCheckBox(" Exclude tracks outside cell  ");
         SPTBatch_.checkExcludeTracks.setSelected(false);
         SPTBatch_.checkPBS = new JCheckBox("Photobleaching step Analysis.");
         SPTBatch_.checkPBS.setSelected(true);
         SPTBatch_.checkSummary = new JCheckBox("");
         SPTBatch_.checkSummary.setSelected(true);
         SPTBatch_.this.summaryButton = new JButton("Summary Outputs");
         JPanel summPanel = new JPanel(new FlowLayout(0));
         summPanel.add(SPTBatch_.checkSummary);
         summPanel.add(SPTBatch_.this.summaryButton);
         JPanel panelPBS = new JPanel(new FlowLayout(0));
         panelPBS.add(SPTBatch_.checkPBS);
         SPTBatch_.this.comboSubBg = new JComboBox();
         SPTBatch_.this.comboSubBg = new JComboBox();
         SPTBatch_.this.comboSubBg.addItem("Subtract Bg 1");
         SPTBatch_.this.comboSubBg.addItem("Subtract Bg 2");
         SPTBatch_.this.comboSubBg.addItem("Subtract Bg 3");
         SPTBatch_.this.comboSubBg.addItem("Subtract Bg 4");
         SPTBatch_.this.comboSubBg.addItem("Subtract Bg 5");
         SPTBatch_.this.comboSubBg.setEnabled(false);
         SPTBatch_.checkboxMSD = new JCheckBox("  MSD and MSS Plots ");
         SPTBatch_.checkboxMSD.setSelected(true);
         SPTBatch_.checkCluster = new JCheckBox(" Cluster Size Analysis ");
         SPTBatch_.checkCluster.setSelected(true);
         SPTBatch_.checkMonomer = new JCheckBox(" Monomeric Protein Intensity ");
         SPTBatch_.checkMonomer.setSelected(true);
         SPTBatch_.checkbox2 = new JCheckBox();
         SPTBatch_.checkbox2.setText(" Tracks to .XML file ");
         SPTBatch_.checkbox2.setSelected(true);
         SPTBatch_.checkbox3 = new JCheckBox();
         SPTBatch_.checkbox3.setText("  Log to .TXT file ");
         SPTBatch_.checkbox3.setSelected(true);
         SPTBatch_.checkbox4 = new JCheckBox();
         SPTBatch_.checkbox4.setText("  Track-Overlays as .TIF images");
         SPTBatch_.checkbox4.setSelected(true);
         SPTBatch_.checkboxRoi = new JCheckBox();
         SPTBatch_.checkboxRoi.setText("  Track-Rois as RoiSet.zip");
         SPTBatch_.checkboxRoi.setSelected(true);
         SPTBatch_.checkboxPlot = new JCheckBox();
         SPTBatch_.checkboxPlot.setText("  Plots as .PNG file");
         SPTBatch_.checkboxPlot.setSelected(true);
         SPTBatch_.checkboxSP = new JCheckBox();
         SPTBatch_.checkboxSP.setText("  Chemotaxis Analysis Data");
         SPTBatch_.checkboxSP.setSelected(true);
         SPTBatch_.chemoScaling = new JTextField("Set Axis Scaling...");
         SPTBatch_.chemoScaling.setEnabled(true);
         SPTBatch_.monomerField = new JTextField("value...");
         SPTBatch_.monomerField.setEnabled(true);
         SPTBatch_.this.comboP = new CheckedComboBox(new DefaultComboBoxModel(items));
         SPTBatch_.this.comboP.setOpaque(true);
         SPTBatch_.this.comboP.setToolTipText("Select an analysis for export csv file");
         SPTBatch_.this.comboP.setSelectedItem(items[0]);
         if (items[0].isSelected()) {
            SPTBatch_.this.enableSpotTable = "spotTable";
         }

         if (items[1].isSelected()) {
            SPTBatch_.this.enableLinkTable = "linkTable";
         }

         if (items[2].isSelected()) {
            SPTBatch_.this.enableTrackTable = "trackTable";
         }

         if (items[3].isSelected()) {
            SPTBatch_.this.enableBranchTable = "branchTable";
         }

         this.removeAll();
         final TextField textCsv = new TextField(20);
         textCsv.setText(SPTBatch_.pref1.get(SPTBatch_.this.TRACKMATE_CSV_PATH, ""));
         SPTBatch_.csvPath = textCsv.getText();
         GridBagLayout layoutCsv = (GridBagLayout)this.getLayout();
         GridBagConstraints constraintsCsv = layoutCsv.getConstraints(textCsv);
         SPTBatch_.this.buttonCsv = new JButton("");
         ImageIcon iconCsv = SPTBatch_.this.createImageIcon("browse.png");
         Icon iconCsvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(15, 15, 4));
         SPTBatch_.this.buttonCsv.setIcon(iconCsvCell);
         DirectoryListener listenerCsv = new DirectoryListener("Browse for ", textCsv, 2);
         SPTBatch_.this.buttonCsv.addActionListener(listenerCsv);
         final Panel panelCsv = new Panel();
         panelCsv.setLayout(new FlowLayout(0, 0, 0));
         layoutCsv.setConstraints(panelCsv, constraintsCsv);
         panelCsv.add(SPTBatch_.checkbox1);
         panelCsv.add(SPTBatch_.this.comboP);
         JPanel panelBox = new JPanel();
         JPanel panelOptions = new JPanel(new FlowLayout(0));
         JPanel panelOptions1 = new JPanel(new FlowLayout(0));
         panelOptions.add(Box.createHorizontalStrut(35));
         panelOptions1.add(Box.createHorizontalStrut(35));
         panelBox.setLayout(new BoxLayout(panelBox, 1));
         SPTBatch_.checkTracks = new JCheckBox("Spot Range in Track: ");
         SPTBatch_.checkTracks.setSelected(false);
         SPTBatch_.minTracks = new JTextField("Min", 3);
         SPTBatch_.minTracks.setText(SPTBatch_.pref1.get(SPTBatch_.this.TRACKMATE_MIN_SPOT, ""));
         SPTBatch_.minTracks.setEnabled(false);
         SPTBatch_.maxTracks = new JTextField("Max", 3);
         SPTBatch_.maxTracks.setText(SPTBatch_.pref1.get(SPTBatch_.this.TRACKMATE_MAX_SPOT, ""));
         SPTBatch_.maxTracks.setEnabled(false);
         panelOptions1.add(SPTBatch_.checkTracks);
         panelOptions1.add(SPTBatch_.minTracks);
         panelOptions1.add(new JLabel("-"));
         panelOptions1.add(SPTBatch_.maxTracks);
         SPTBatch_.checkDispSpots = new JCheckBox("Spots Visible ");
         SPTBatch_.checkDispSpots.setSelected(true);
         SPTBatch_.checkDispSpotName = new JCheckBox("Spots Name Visible ");
         SPTBatch_.checkDispSpotName.setSelected(true);
         SPTBatch_.checkDispTracks = new JCheckBox("Tracks Visible: ");
         SPTBatch_.checkDispTracks.setSelected(true);
         SPTBatch_.this.comboDispTracks = new JComboBox();
         SPTBatch_.this.comboDispTracks.addItem("FULL");
         SPTBatch_.this.comboDispTracks.addItem("LOCAL");
         SPTBatch_.this.comboDispTracks.addItem("LOCAL_BACKWARD");
         SPTBatch_.this.comboDispTracks.addItem("LOCAL_FORWARD");
         SPTBatch_.this.comboDispTracks.setSelectedIndex(0);
         JPanel panelSpotTrackDisp = new JPanel(new FlowLayout(0));
         panelSpotTrackDisp.add(SPTBatch_.checkDispSpots);
         panelSpotTrackDisp.add(SPTBatch_.checkDispSpotName);
         panelSpotTrackDisp.add(SPTBatch_.checkDispTracks);
         panelSpotTrackDisp.add(SPTBatch_.this.comboDispTracks);
         JLabel thLengthLabel = new JLabel("-Length Threshold: ");
         SPTBatch_.thLength = new JTextField("Length", 3);
         SPTBatch_.thLength.setText(SPTBatch_.pref1.get(SPTBatch_.this.TRACKMATE_LENGTH_TH, ""));
         JLabel thD14Label = new JLabel("-Diff.Threshold: ");
         SPTBatch_.thD14 = new JTextField("Diff", 3);
         SPTBatch_.thD14.setText(SPTBatch_.pref1.get(SPTBatch_.this.TRACKMATE_DIFF_TH, ""));
         JPanel panelLengthD14 = new JPanel(new FlowLayout(0));
         panelLengthD14.add(thLengthLabel);
         panelLengthD14.add(SPTBatch_.thLength);
         panelLengthD14.add(thD14Label);
         panelLengthD14.add(SPTBatch_.thD14);
         JPanel panelSubBg = new JPanel(new FlowLayout(0, 0, 0));
         panelSubBg.add(SPTBatch_.checkboxSubBg);
         panelSubBg.add(SPTBatch_.this.comboSubBg);
         panelSubBg.add(SPTBatch_.checkExcludeTracks);
         panelBox.add(panelOptions);
         panelBox.add(Box.createVerticalStrut(3));
         panelBox.add(panelOptions1);
         panelBox.add(Box.createVerticalStrut(3));
         panelBox.add(panelLengthD14);
         panelBox.add(Box.createVerticalStrut(3));
         panelBox.add(panelSpotTrackDisp);
         panelBox.add(Box.createVerticalStrut(3));
         panelBox.add(panelPBS);
         panelBox.add(Box.createVerticalStrut(3));
         panelBox.add(panelSubBg);
         panelBox.add(Box.createVerticalStrut(3));
         panelBox.add(summPanel);
         Panel panelOut = new Panel();
         panelOut.setLayout(new FlowLayout(0, 0, 0));
         panelOut.add(SPTBatch_.checkbox4);
         GridBagLayout layoutXMLL = (GridBagLayout)this.getLayout();
         Panel panelXMLL = new Panel();
         panelXMLL.setLayout(new FlowLayout(0, 0, 0));
         panelXMLL.add(SPTBatch_.checkbox2);
         GridBagLayout layoutTxt = (GridBagLayout)this.getLayout();
         Panel panelTxt = new Panel();
         panelTxt.setLayout(new FlowLayout(0, 0, 0));
         panelTxt.add(SPTBatch_.checkbox3);
         final JPanel panelExport = new JPanel();
         JLabel labelExport = new JLabel();
         labelExport.setText("⤷ Choose a directory to export files:    ");
         labelExport.setFont(new Font("Verdana", 1, 12));
         panelExport.setLayout(new FlowLayout(0, 0, 0));
         panelExport.add(labelExport);
         panelExport.add(textCsv);
         panelExport.add(SPTBatch_.this.buttonCsv);
         JPanel panelDiff = new JPanel(new FlowLayout(0, 0, 0));
         panelDiff.add(SPTBatch_.checkboxDiff);
         panelDiff.add(SPTBatch_.this.trajButton);
         JPanel panelMSD = new JPanel(new FlowLayout(0, 0, 0));
         panelMSD.add(SPTBatch_.checkboxMSD);
         JPanel panelCluster = new JPanel(new FlowLayout(0, 0, 0));
         panelCluster.add(SPTBatch_.checkCluster);
         panelCluster.add(SPTBatch_.checkMonomer);
         panelCluster.add(SPTBatch_.monomerField);
         JPanel panelExport2 = new JPanel(new BorderLayout());
         panelExport2.add(panelExport, "East");
         Panel panelRoi = new Panel();
         panelRoi.setLayout(new FlowLayout(0, 0, 0));
         panelRoi.add(SPTBatch_.checkboxRoi);
         Panel panelPlot = new Panel();
         panelPlot.setLayout(new FlowLayout(0, 0, 0));
         panelPlot.add(SPTBatch_.checkboxPlot);
         Panel panelSP = new Panel();
         panelSP.setLayout(new FlowLayout(0, 0, 0));
         panelSP.add(SPTBatch_.checkboxSP);
         panelSP.add(SPTBatch_.chemoScaling);
         JLabel labelExport1 = new JLabel("⊳   Tuneable Options: ");
         labelExport1.setFont(new Font("Verdana", 1, 12));
         JPanel panelExport1 = new JPanel(new FlowLayout(0));
         panelExport1.add(labelExport1);
         JPanel mainPanel2 = new JPanel();
         mainPanel2.setBorder(BorderFactory.createTitledBorder(""));
         mainPanel2.setLayout(new BoxLayout(mainPanel2, 1));
         mainPanel2.add(Box.createVerticalStrut(3));
         mainPanel2.add(new JSeparator(0));
         mainPanel2.add(panelExport1);
         mainPanel2.add(new JSeparator(0));
         mainPanel2.add(Box.createVerticalStrut(8));
         mainPanel2.add(panelCsv);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(Box.createHorizontalStrut(15));
         mainPanel2.add(panelBox);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelDiff);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelMSD);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelCluster);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelXMLL);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelTxt);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelOut);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelRoi);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelPlot);
         mainPanel2.add(Box.createVerticalStrut(5));
         mainPanel2.add(panelSP);
         mainPanel2.add(Box.createVerticalStrut(15));
         mainPanel2.add(new JSeparator(0));
         mainPanel2.add(Box.createVerticalStrut(3));
         mainPanel2.add(panelExport2);
         mainPanel2.setBorder(BorderFactory.createTitledBorder(""));
         SPTBatch_.this.comboP.setEnabled(true);
         textCsv.setEnabled(true);
         SPTBatch_.this.buttonCsv.setEnabled(true);
         panelExport2.setEnabled(true);
         SPTBatch_.checkbox4.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.checkExcludeTracks.setSelected(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.checkExcludeTracks.setSelected(false);
               }

            }
         });
         SPTBatch_.checkDispSpots.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.checkDispSpotName.setSelected(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.checkDispSpotName.setSelected(false);
               }

            }
         });
         SPTBatch_.checkSummary.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.this.summaryButton.setEnabled(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.this.summaryButton.setEnabled(false);
               }

            }
         });
         SPTBatch_.checkboxDiff.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.this.trajButton.setEnabled(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.this.trajButton.setEnabled(false);
               }

            }
         });
         SPTBatch_.checkDispTracks.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.this.comboDispTracks.setEnabled(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.this.comboDispTracks.setEnabled(false);
               }

            }
         });
         SPTBatch_.checkTracks.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.minTracks.setEnabled(true);
                  SPTBatch_.maxTracks.setEnabled(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.minTracks.setEnabled(false);
                  SPTBatch_.maxTracks.setEnabled(false);
               }

            }
         });
         SPTBatch_.checkMonomer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.monomerField.setEnabled(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.monomerField.setEnabled(false);
               }

            }
         });
         SPTBatch_.checkboxSubBg.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.this.comboSubBg.setEnabled(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.this.comboSubBg.setEnabled(false);
               }

            }
         });
         SPTBatch_.checkbox1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               Component[] com = panelCsv.getComponents();
               Component[] comExport = panelExport.getComponents();
               int a;
               if (e.getStateChange() == 1) {
                  for(a = 1; a < com.length; ++a) {
                     com[a].setEnabled(true);
                  }

                  for(a = 0; a < comExport.length; ++a) {
                     comExport[a].setEnabled(true);
                  }
               }

               if (e.getStateChange() == 2) {
                  for(a = 1; a < com.length; ++a) {
                     com[a].setEnabled(false);
                  }
               }

            }
         });
         SPTBatch_.checkboxSP.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if (e.getStateChange() == 1) {
                  SPTBatch_.chemoScaling.setEnabled(true);
               }

               if (e.getStateChange() == 2) {
                  SPTBatch_.chemoScaling.setEnabled(false);
               }

            }
         });
         SPTBatch_.this.summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               if (SPTBatch_.checkSummary.isSelected()) {
                  if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE) {
                     SPTBatch_.minTracksJTF = Integer.valueOf(SPTBatch_.minTracks.getText());
                     SPTBatch_.maxTracksJTF = Integer.valueOf(SPTBatch_.maxTracks.getText());
                     SPTBatch_.thLengthJTF = Integer.valueOf(SPTBatch_.thLength.getText());
                     if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "DIff") {
                        SPTBatch_.this.thD14JTF = Double.valueOf(SPTBatch_.thD14.getText());
                     }
                  }

                  (new summaryColsWindow()).run("");
               }

            }
         });
         SPTBatch_.this.trajButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               if (SPTBatch_.checkboxDiff.isSelected()) {
                  (new traJParametersWindow()).run("");
               }

            }
         });
         SPTBatch_.this.finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               SPTBatch_.csvPath = textCsv.getText();
               SPTBatch_.pref1.put(SPTBatch_.this.TRACKMATE_CSV_PATH, textCsv.getText());
            }
         });
         this.add(mainPanel2);
      }

      public void update() {
         if (SPTBatch_.checkboxPlot.isSelected() == Boolean.FALSE) {
            this.setNextButtonEnabled(false);
         }

         if (SPTBatch_.checkboxPlot.isSelected() == Boolean.TRUE) {
            this.setNextButtonEnabled(true);
         }

         this.setFinishButtonEnabled(true);
         this.setBackButtonEnabled(false);
      }

      public void next() {
         this.switchPanel(3);
      }

      public void back() {
         this.switchPanel(4);
      }
   }

   private static final class EdgeSourceSpotFrameComparator implements Comparator<DefaultWeightedEdge> {
      private final Model model;

      public EdgeSourceSpotFrameComparator(Model model) {
         this.model = model;
      }

      public int compare(DefaultWeightedEdge e1, DefaultWeightedEdge e2) {
         double t1 = this.model.getTrackModel().getEdgeSource(e1).getFeature("FRAME");
         double t2 = this.model.getTrackModel().getEdgeSource(e2).getFeature("FRAME");
         if (t1 < t2) {
            return -1;
         } else {
            return t1 > t2 ? 1 : 0;
         }
      }
   }

   class FirstWizardPanel extends SPTBatch_.LabelWizardPanel {
      public FirstWizardPanel(JWizardComponents wizardComponents) {
         super(wizardComponents, "");
      }
   }

   class LabelWizardPanel extends JWizardPanel {
      public LabelWizardPanel(JWizardComponents wizardComponents, String label) {
         super(wizardComponents);
         SPTBatch_.this.backButton = wizardComponents.getBackButton();
         SPTBatch_.this.backButton.setText("");
         ImageIcon iconBack = SPTBatch_.this.createImageIcon("img_71224.png");
         Icon iconBackCell = new ImageIcon(iconBack.getImage().getScaledInstance(12, 12, 4));
         SPTBatch_.this.backButton.setIcon(iconBackCell);
         SPTBatch_.this.backButton.setToolTipText("Click chemotool button to back the previous wizard.");
         SPTBatch_.this.nextButton = wizardComponents.getNextButton();
         SPTBatch_.this.nextButton.setText("");
         ImageIcon iconNext = SPTBatch_.this.createImageIcon("img_174455.png");
         Icon iconNextCell = new ImageIcon(iconNext.getImage().getScaledInstance(12, 12, 4));
         SPTBatch_.this.nextButton.setIcon(iconNextCell);
         SPTBatch_.this.nextButton.setToolTipText("Click chemotool button to go to the next wizard.");
         SPTBatch_.this.finishButton = wizardComponents.getFinishButton();
         SPTBatch_.this.finishButton.setText("");
         ImageIcon iconFinish = SPTBatch_.this.createImageIcon("ojala.png");
         Icon iconFinishCell = new ImageIcon(iconFinish.getImage().getScaledInstance(12, 12, 4));
         SPTBatch_.this.finishButton.setIcon(iconFinishCell);
         SPTBatch_.this.finishButton.setToolTipText("Click chemotool button to finish your settings selection.");
         JButton cancelButton = wizardComponents.getCancelButton();
         cancelButton.setText("");
         ImageIcon iconCancel = SPTBatch_.this.createImageIcon("delete.png");
         Icon iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(12, 12, 4));
         cancelButton.setIcon(iconCancelCell);
         cancelButton.setToolTipText("Click chemotool button to cancel chemotool process.");
         this.setLayout(new GridBagLayout());
         this.add(new JLabel(label), new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
         final TextField textXML = new TextField(20);
         textXML.setText(SPTBatch_.pref1.get(SPTBatch_.this.TRACKMATE_TRANSF_PATH, ""));
         GridBagLayout layoutXML = (GridBagLayout)this.getLayout();
         GridBagConstraints constraintsXML = layoutXML.getConstraints(textXML);
         JButton buttonXML = new JButton("");
         ImageIcon iconXML = SPTBatch_.this.createImageIcon("browse.png");
         Icon iconXMLCell = new ImageIcon(iconXML.getImage().getScaledInstance(15, 15, 4));
         buttonXML.setIcon(iconXMLCell);
         ImageIcon iconBrowse = SPTBatch_.this.createImageIcon("browse.png");
         Icon iconBrowseCell = new ImageIcon(iconBrowse.getImage().getScaledInstance(15, 15, 4));
         buttonXML.setIcon(iconBrowseCell);
         DirectoryListener listenerXML = new DirectoryListener("Browse for " + label, textXML, 2);
         buttonXML.addActionListener(listenerXML);
         Panel panelXML = new Panel();
         panelXML.setLayout(new FlowLayout(0));
         JLabel loadLabel = new JLabel("⊳  Load TrackMate .XML file: ");
         loadLabel.setFont(new Font("Verdana", 1, 12));
         panelXML.add(loadLabel);
         panelXML.add(textXML);
         panelXML.add(buttonXML);
         layoutXML.setConstraints(panelXML, constraintsXML);
         final TextField textImg = new TextField(20);
         textImg.setText(SPTBatch_.pref1.get(SPTBatch_.this.TRACKMATE_IMAGES_PATH, ""));
         GridBagLayout layoutImg = (GridBagLayout)this.getLayout();
         GridBagConstraints constraintsImg = layoutImg.getConstraints(textImg);
         JButton buttonImg = new JButton("");
         ImageIcon iconIM = SPTBatch_.this.createImageIcon("browse.png");
         Icon iconIMCell = new ImageIcon(iconIM.getImage().getScaledInstance(15, 15, 4));
         buttonImg.setIcon(iconIMCell);
         ImageIcon iconB = SPTBatch_.this.createImageIcon("browse.png");
         Icon iconBCell = new ImageIcon(iconB.getImage().getScaledInstance(15, 15, 4));
         buttonImg.setIcon(iconBCell);
         DirectoryListener listenerImg = new DirectoryListener("Browse for " + label, textImg, 2);
         buttonImg.addActionListener(listenerImg);
         Panel panelImg = new Panel();
         panelImg.setLayout(new FlowLayout(0));
         JLabel directLabel = new JLabel("⊳  Movie Files Directory:   ");
         directLabel.setFont(new Font("Verdana", 1, 12));
         panelImg.add(directLabel);
         panelImg.add(textImg);
         panelImg.add(buttonImg);
         layoutImg.setConstraints(panelImg, constraintsImg);
         JPanel mainPanel = new JPanel();
         mainPanel.setLayout(new BoxLayout(mainPanel, 1));
         mainPanel.add(Box.createVerticalStrut(15));
         mainPanel.add(panelXML);
         mainPanel.add(Box.createVerticalStrut(20));
         mainPanel.add(panelImg);
         mainPanel.add(Box.createVerticalStrut(15));
         mainPanel.setBorder(BorderFactory.createTitledBorder(""));
         this.add(mainPanel);
         this.setPanelTitle("");
         SPTBatch_.this.nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               SPTBatch_.pref1.put(SPTBatch_.this.TRACKMATE_TRANSF_PATH, textXML.getText());
               SPTBatch_.pref1.put(SPTBatch_.this.TRACKMATE_IMAGES_PATH, textImg.getText());
               SPTBatch_.pref1.put(SPTBatch_.this.TRACKMATE_MIN_SPOT, SPTBatch_.minTracks.getText());
               SPTBatch_.pref1.put(SPTBatch_.this.TRACKMATE_MAX_SPOT, SPTBatch_.maxTracks.getText());
               SPTBatch_.pref1.put(SPTBatch_.this.TRACKMATE_LENGTH_TH, SPTBatch_.thLength.getText());
               SPTBatch_.pref1.put(SPTBatch_.this.TRACKMATE_DIFF_TH, SPTBatch_.thD14.getText());
               if (SPTBatch_.checkboxDiff.isSelected()) {
                  SPTBatch_.pref1.put(SPTBatch_.TRACKMATE_MIN_TRACK, traJParametersWindow.minLengthText.getText());
                  SPTBatch_.pref1.put(SPTBatch_.TRACKMATE_WINDOW, traJParametersWindow.windowText.getText());
                  SPTBatch_.pref1.put(SPTBatch_.TRACKMATE_MIN_SEGMENT, traJParametersWindow.minSegText.getText());
               }

            }
         });
      }
   }

   class LastWizardPanel extends SPTBatch_.LabelWizardPanel {
      public LastWizardPanel(JWizardComponents wizardComponents) {
         super(wizardComponents, "");
         this.setPanelTitle("");
         this.update();
         this.removeAll();
         JTabbedPane tabbedPane = new JTabbedPane();
         ImageIcon iconSpot = SPTBatch_.this.createImageIcon("5441165-point-of-light-png-104-images-in-collection-page-1-point-of-light-png-320_320_preview.png");
         Icon iconSpotCell = new ImageIcon(iconSpot.getImage().getScaledInstance(12, 12, 4));
         ImageIcon iconLink = SPTBatch_.this.createImageIcon("link.png");
         Icon iconLinkCell = new ImageIcon(iconLink.getImage().getScaledInstance(16, 16, 4));
         ImageIcon iconTrack = SPTBatch_.this.createImageIcon("track.jpg");
         Icon iconTrackCell = new ImageIcon(iconTrack.getImage().getScaledInstance(16, 16, 4));
         JComponent panel1 = SPTBatch_.this.makeTextPanel("");
         JComponent panelX = SPTBatch_.this.makeTextPanel("");
         JComponent panelY = SPTBatch_.this.makeTextPanel("");
         panelY.setLayout(new FlowLayout(0));
         JLabel xSpot = new JLabel();
         xSpot.setText("⊳ Spot-Feature for X axis:   ");
         xSpot.setFont(new Font("Verdana", 1, 12));
         JLabel ySpot = new JLabel();
         ySpot.setText("⊳ Spot-Feature for Y axis:   ");
         ySpot.setFont(new Font("Verdana", 1, 12));
         SPTBatch_.this.comboSpotsX = new JComboBox();
         SPTBatch_.this.comboSpotsY = new JComboBox();
         Object[] spotItems = null;
         spotItems = new Object[]{"QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T\tFRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1"};

         for(int ixx = 0; ixx < spotItems.length; ++ixx) {
            SPTBatch_.this.comboSpotsX.addItem(spotItems[ixx]);
            SPTBatch_.this.comboSpotsY.addItem(spotItems[ixx]);
         }

         panelX.setLayout(new FlowLayout(0));
         panelX.add(Box.createVerticalStrut(10));
         panelX.add(xSpot);
         panelX.add(Box.createVerticalStrut(5));
         panelX.add(SPTBatch_.this.comboSpotsX);
         panelY.add(ySpot);
         panelY.add(Box.createVerticalStrut(5));
         panelY.add(SPTBatch_.this.comboSpotsY);
         panel1.setLayout(new BoxLayout(panel1, 1));
         panel1.add(panelX);
         panel1.add(panelY);
         JPanel panelESP = new JPanel(new FlowLayout(1));
         panelESP.setBorder(BorderFactory.createLoweredBevelBorder());
         SPTBatch_.ESP = new JCheckBox("  Export User-Defined Spot Plot.");
         panelESP.add(SPTBatch_.ESP);
         panel1.add(Box.createVerticalStrut(15));
         panel1.add(panelESP);
         panel1.setBorder(BorderFactory.createTitledBorder(""));
         tabbedPane.addTab("Spots", iconSpotCell, panel1, "Select the X-Y Spot features to plot.");
         tabbedPane.setMnemonicAt(0, 49);
         JComponent panel2 = SPTBatch_.this.makeTextPanel("");
         JComponent panelLinkX = SPTBatch_.this.makeTextPanel("");
         JComponent panelLinkY = SPTBatch_.this.makeTextPanel("");
         panelLinkX.setLayout(new FlowLayout(0));
         panelLinkY.setLayout(new FlowLayout(0));
         JLabel xLink = new JLabel();
         xLink.setText("⊳ Link-Feature for X axis:   ");
         xLink.setFont(new Font("Verdana", 1, 12));
         JLabel yLink = new JLabel();
         yLink.setText("⊳ Link-Feature for Y axis:   ");
         yLink.setFont(new Font("Verdana", 1, 12));
         SPTBatch_.this.comboLinkX = new JComboBox();
         SPTBatch_.this.comboLinkY = new JComboBox();
         Object[] edgeItems = new Object[]{"SPOT_SOURCE_ID", "SPOT_TARGET_ID", "LINK_COST", "DIRECTIONAL_CHANGE_RATE", "SPEED", "DISPLACEMENT", "EDGE_TIME", "EDGE_X_LOCATION", "EDGE_Y_LOCATION", "EDGE_Z_LOCATION"};

         for(int i = 0; i < edgeItems.length; ++i) {
            SPTBatch_.this.comboLinkX.addItem(edgeItems[i]);
            SPTBatch_.this.comboLinkY.addItem(edgeItems[i]);
         }

         panelLinkX.setLayout(new FlowLayout(0));
         panelLinkX.add(Box.createVerticalStrut(10));
         panelLinkX.add(xLink);
         panelLinkX.add(Box.createVerticalStrut(5));
         panelLinkX.add(SPTBatch_.this.comboLinkX);
         panelLinkY.add(yLink);
         panelLinkY.add(Box.createVerticalStrut(5));
         panelLinkY.add(SPTBatch_.this.comboLinkY);
         panel2.setLayout(new BoxLayout(panel2, 1));
         panel2.add(panelLinkX);
         panel2.add(panelLinkY);
         JPanel panelELP = new JPanel(new FlowLayout(1));
         panelELP.setBorder(BorderFactory.createLoweredBevelBorder());
         SPTBatch_.ELP = new JCheckBox("  Export User-Defined Links Plot.");
         panelELP.add(SPTBatch_.ELP);
         panel2.add(Box.createVerticalStrut(15));
         panel2.add(panelELP);
         panel2.setBorder(BorderFactory.createTitledBorder(""));
         tabbedPane.addTab("Links", iconLinkCell, panel2, "Select the  X-Y Link features to plot.");
         tabbedPane.setMnemonicAt(1, 50);
         JComponent panel3 = SPTBatch_.this.makeTextPanel("");
         JComponent panelTrackX = SPTBatch_.this.makeTextPanel("");
         JComponent panelTrackY = SPTBatch_.this.makeTextPanel("");
         panelTrackX.setLayout(new FlowLayout(0));
         panelTrackY.setLayout(new FlowLayout(0));
         JLabel xTrack = new JLabel();
         xTrack.setText("⊳ Track-Feature for X axis:   ");
         xTrack.setFont(new Font("Verdana", 1, 12));
         JLabel yTrack = new JLabel();
         yTrack.setText("⊳ Track-Feature for Y axis:   ");
         yTrack.setFont(new Font("Verdana", 1, 12));
         SPTBatch_.this.comboTrackX = new JComboBox();
         SPTBatch_.this.comboTrackY = new JComboBox();
         Object[] trackItems = null;
         trackItems = new Object[]{"TRACK_INDEX", "TRACK_ID", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE"};

         for(int ix = 0; ix < trackItems.length; ++ix) {
            SPTBatch_.this.comboTrackX.addItem(trackItems[ix]);
            SPTBatch_.this.comboTrackY.addItem(trackItems[ix]);
         }

         panelTrackX.setLayout(new FlowLayout(0));
         panelTrackX.add(Box.createVerticalStrut(10));
         panelTrackX.add(xTrack);
         panelTrackX.add(Box.createVerticalStrut(5));
         panelTrackX.add(SPTBatch_.this.comboTrackX);
         panelTrackY.add(yTrack);
         panelTrackY.add(Box.createVerticalStrut(5));
         panelTrackY.add(SPTBatch_.this.comboTrackY);
         panel3.setLayout(new BoxLayout(panel3, 1));
         panel3.add(panelTrackX);
         panel3.add(panelTrackY);
         JPanel panelETP = new JPanel(new FlowLayout(1));
         panelETP.setBorder(BorderFactory.createLoweredBevelBorder());
         SPTBatch_.ETP = new JCheckBox("  Export User-Defined Tracks Plot.");
         panelETP.add(SPTBatch_.ETP);
         panel3.add(Box.createVerticalStrut(15));
         panel3.add(panelETP);
         panel3.setBorder(BorderFactory.createTitledBorder(""));
         tabbedPane.addTab("Tracks", iconTrackCell, panel3, "Select the  X-Y Track features to plot.");
         tabbedPane.setMnemonicAt(2, 51);
         tabbedPane.setPreferredSize(new java.awt.Dimension(450, 250));
         this.add(tabbedPane);
         tabbedPane.setTabLayoutPolicy(1);
      }

      public void update() {
         this.setNextButtonEnabled(false);
         if (SPTBatch_.this.itemCheckPlot == 1) {
            this.setNextButtonEnabled(false);
         }

         this.setFinishButtonEnabled(true);
         this.setBackButtonEnabled(true);
      }

      public void next() {
      }

      public void back() {
         this.switchPanel(0);
      }

      public void finish() {
         this.switchPanel(2);
      }
   }

   private static class NSpotPerFrameDataset extends ModelDataset {
      private static final long serialVersionUID = 1L;
      private final double[] time;
      private final int[] nspots;

      public NSpotPerFrameDataset(Model model, SelectionModel selectionModel, DisplaySettings ds, double[] time, int[] nspots) {
         super(model, selectionModel, ds, "POSITION_T", Collections.singletonList("N spots"));
         this.time = time;
         this.nspots = nspots;
      }

      public int getItemCount(int series) {
         return this.nspots.length;
      }

      public Number getX(int series, int item) {
         return this.time[item];
      }

      public Number getY(int series, int item) {
         return (double)this.nspots[item];
      }

      public String getSeriesKey(int series) {
         return (String)this.yFeatures.get(series);
      }

      public String getItemLabel(int item) {
         return "" + item;
      }

      public void setItemLabel(int item, String label) {
      }

      public XYItemRenderer getRenderer() {
         return new XYLineAndShapeRenderer(true, true);
      }
   }

   class OptionWizardPanel extends SPTBatch_.LabelWizardPanel {
      public OptionWizardPanel(JWizardComponents wizardComponents, String option) {
         super(wizardComponents, "");
         this.setPanelTitle("►  Check options to export Tracking results");
      }

      public void update1() {
         this.setNextButtonEnabled(false);
         this.setFinishButtonEnabled(true);
         this.setBackButtonEnabled(true);
      }

      public void next() {
         this.switchPanel(4);
      }

      public void back() {
         this.switchPanel(1);
         if (SPTBatch_.this.itemCheckPlot == 1) {
            this.switchPanel(4);
         }

         if (SPTBatch_.this.itemPlot2 == 1) {
            this.switchPanel(4);
         }

         if (SPTBatch_.this.itemPlot2 == 1 && SPTBatch_.this.itemCheckPlot == 1) {
            this.switchPanel(4);
         }

      }

      public void finish() {
         this.switchPanel(2);
      }
   }
}
