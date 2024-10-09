import SPTBatch_.1;
import SPTBatch_.2;
import checkable.CheckedComboBox;
import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.action.PlotNSpotsVsTimeAction;
import fiji.plugin.trackmate.features.FeatureUtils;
import fiji.plugin.trackmate.graph.ConvexBranchesDecomposition;
import fiji.plugin.trackmate.graph.TimeDirectedNeighborIndex;
import fiji.plugin.trackmate.graph.ConvexBranchesDecomposition.TrackBranchDecomposition;
import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings;
import fiji.plugin.trackmate.util.TMUtils;
import fiji.plugin.trackmate.visualization.FeatureColorGenerator;
import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
import fiji.plugin.trackmate.visualization.hyperstack.HyperStackDisplayer;
import fiji.plugin.trackmate.visualization.hyperstack.SpotOverlay;
import fiji.plugin.trackmate.visualization.hyperstack.TrackOverlay;
import fiji.plugin.trackmate.visualization.table.TablePanel;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.frame.RoiManager;
import ij.process.ImageProcessor;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import jwizardcomponent.JWizardPanel;
import jwizardcomponent.Utilities;
import jwizardcomponent.frame.JWizardFrame;
import loci.plugins.in.DisplayHandler;
import loci.plugins.in.ImportProcess;
import loci.plugins.in.ImporterOptions;
import org.jfree.chart.JFreeChart;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

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
      this.panel = new SPTBatch_.FirstWizardPanel(this, this.wizard.getWizardComponents());
      this.wizard.getWizardComponents().addWizardPanel(0, this.panel);
      this.panel = new SPTBatch_.ChooserWizardPanel(this, this.wizard.getWizardComponents());
      this.wizard.getWizardComponents().addWizardPanel(1, this.panel);
      this.panel = new SPTBatch_.OptionWizardPanel(this, this.wizard.getWizardComponents(), "A");
      this.wizard.getWizardComponents().addWizardPanel(2, this.panel);
      this.panel = new SPTBatch_.OptionWizardPanel(this, this.wizard.getWizardComponents(), "B");
      this.wizard.getWizardComponents().addWizardPanel(3, this.panel);
      this.panel = new SPTBatch_.LastWizardPanel(this, this.wizard.getWizardComponents());
      this.wizard.getWizardComponents().addWizardPanel(4, this.panel);
      this.wizard.getWizardComponents().removeWizardPanel(0);
      this.wizard.setSize(550, 700);
      Utilities.centerComponentOnScreen(this.wizard);
      this.wizard.setResizable(false);
      this.wizard.setVisible(true);
      mainProcess = new Thread(new SPTBatch_.1(this));
      this.finishButton.addActionListener(new SPTBatch_.2(this));
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
            SPTBatch_.Branch.access$1(br, model.getTrackModel().name(trackID));
            br.putFeature("TRACK_ID", (double)trackID);
            Spot first = (Spot)branch.get(0);
            SPTBatch_.Branch.access$2(br, first);
            br.putFeature("FIRST", (double)first.ID());
            Spot last = (Spot)branch.get(branch.size() - 1);
            SPTBatch_.Branch.access$3(br, last);
            br.putFeature("LAST", (double)last.ID());
            br.putFeature("DELTA_T", br.dt());
            double distanceTraveled = Math.sqrt(SPTBatch_.Branch.access$4(br).squareDistanceTo(SPTBatch_.Branch.access$5(br)));
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

            SPTBatch_.Branch.access$6(br, succBrs);
            br.putFeature("N_SUCCESSORS", (double)succBrs.size());
            Set<List<Spot>> preds = (Set)predecessorMap.get(br);
            Set<SPTBatch_.Branch> predBrs = new HashSet(preds.size());
            Iterator var43 = preds.iterator();

            while(var43.hasNext()) {
               List<Spot> branch = (List)var43.next();
               SPTBatch_.Branch predBr = (SPTBatch_.Branch)branchMap.get(branch);
               predBrs.add(predBr);
            }

            SPTBatch_.Branch.access$7(br, predBrs);
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

   // $FF: synthetic method
   static void access$0(SPTBatch_ var0, JButton var1) {
      var0.backButton = var1;
   }

   // $FF: synthetic method
   static JButton access$1(SPTBatch_ var0) {
      return var0.backButton;
   }

   // $FF: synthetic method
   static void access$2(SPTBatch_ var0, JButton var1) {
      var0.nextButton = var1;
   }

   // $FF: synthetic method
   static JButton access$3(SPTBatch_ var0) {
      return var0.nextButton;
   }

   // $FF: synthetic method
   static void access$4(SPTBatch_ var0, JButton var1) {
      var0.finishButton = var1;
   }

   // $FF: synthetic method
   static JButton access$5(SPTBatch_ var0) {
      return var0.finishButton;
   }

   // $FF: synthetic method
   static String access$6(SPTBatch_ var0) {
      return var0.TRACKMATE_TRANSF_PATH;
   }

   // $FF: synthetic method
   static String access$7(SPTBatch_ var0) {
      return var0.TRACKMATE_IMAGES_PATH;
   }

   // $FF: synthetic method
   static String access$8(SPTBatch_ var0) {
      return var0.TRACKMATE_MIN_SPOT;
   }

   // $FF: synthetic method
   static String access$9(SPTBatch_ var0) {
      return var0.TRACKMATE_MAX_SPOT;
   }

   // $FF: synthetic method
   static String access$10(SPTBatch_ var0) {
      return var0.TRACKMATE_LENGTH_TH;
   }

   // $FF: synthetic method
   static String access$11(SPTBatch_ var0) {
      return var0.TRACKMATE_DIFF_TH;
   }

   // $FF: synthetic method
   static void access$12(SPTBatch_ var0, JButton var1) {
      var0.trajButton = var1;
   }

   // $FF: synthetic method
   static void access$13(SPTBatch_ var0, JButton var1) {
      var0.summaryButton = var1;
   }

   // $FF: synthetic method
   static JButton access$14(SPTBatch_ var0) {
      return var0.summaryButton;
   }

   // $FF: synthetic method
   static void access$15(SPTBatch_ var0, JComboBox var1) {
      var0.comboSubBg = var1;
   }

   // $FF: synthetic method
   static JComboBox access$16(SPTBatch_ var0) {
      return var0.comboSubBg;
   }

   // $FF: synthetic method
   static void access$17(SPTBatch_ var0, CheckedComboBox var1) {
      var0.comboP = var1;
   }

   // $FF: synthetic method
   static CheckedComboBox access$18(SPTBatch_ var0) {
      return var0.comboP;
   }

   // $FF: synthetic method
   static void access$19(SPTBatch_ var0, String var1) {
      var0.enableSpotTable = var1;
   }

   // $FF: synthetic method
   static void access$20(SPTBatch_ var0, String var1) {
      var0.enableLinkTable = var1;
   }

   // $FF: synthetic method
   static void access$21(SPTBatch_ var0, String var1) {
      var0.enableTrackTable = var1;
   }

   // $FF: synthetic method
   static void access$22(SPTBatch_ var0, String var1) {
      var0.enableBranchTable = var1;
   }

   // $FF: synthetic method
   static String access$23(SPTBatch_ var0) {
      return var0.TRACKMATE_CSV_PATH;
   }

   // $FF: synthetic method
   static void access$24(SPTBatch_ var0, JButton var1) {
      var0.buttonCsv = var1;
   }

   // $FF: synthetic method
   static JButton access$25(SPTBatch_ var0) {
      return var0.buttonCsv;
   }

   // $FF: synthetic method
   static void access$26(SPTBatch_ var0, JComboBox var1) {
      var0.comboDispTracks = var1;
   }

   // $FF: synthetic method
   static JComboBox access$27(SPTBatch_ var0) {
      return var0.comboDispTracks;
   }

   // $FF: synthetic method
   static JButton access$28(SPTBatch_ var0) {
      return var0.trajButton;
   }

   // $FF: synthetic method
   static void access$29(SPTBatch_ var0, double var1) {
      var0.thD14JTF = var1;
   }

   // $FF: synthetic method
   static int access$30(SPTBatch_ var0) {
      return var0.itemCheckPlot;
   }

   // $FF: synthetic method
   static int access$31(SPTBatch_ var0) {
      return var0.itemPlot2;
   }

   // $FF: synthetic method
   static void access$32(SPTBatch_ var0, JComboBox var1) {
      var0.comboSpotsX = var1;
   }

   // $FF: synthetic method
   static void access$33(SPTBatch_ var0, JComboBox var1) {
      var0.comboSpotsY = var1;
   }

   // $FF: synthetic method
   static JComboBox access$34(SPTBatch_ var0) {
      return var0.comboSpotsX;
   }

   // $FF: synthetic method
   static JComboBox access$35(SPTBatch_ var0) {
      return var0.comboSpotsY;
   }

   // $FF: synthetic method
   static void access$36(SPTBatch_ var0, JComboBox var1) {
      var0.comboLinkX = var1;
   }

   // $FF: synthetic method
   static void access$37(SPTBatch_ var0, JComboBox var1) {
      var0.comboLinkY = var1;
   }

   // $FF: synthetic method
   static JComboBox access$38(SPTBatch_ var0) {
      return var0.comboLinkX;
   }

   // $FF: synthetic method
   static JComboBox access$39(SPTBatch_ var0) {
      return var0.comboLinkY;
   }

   // $FF: synthetic method
   static void access$40(SPTBatch_ var0, JComboBox var1) {
      var0.comboTrackX = var1;
   }

   // $FF: synthetic method
   static void access$41(SPTBatch_ var0, JComboBox var1) {
      var0.comboTrackY = var1;
   }

   // $FF: synthetic method
   static JComboBox access$42(SPTBatch_ var0) {
      return var0.comboTrackX;
   }

   // $FF: synthetic method
   static JComboBox access$43(SPTBatch_ var0) {
      return var0.comboTrackY;
   }

   // $FF: synthetic method
   static void access$44(SPTBatch_ var0, String var1) {
      var0.xSelectedSpot = var1;
   }

   // $FF: synthetic method
   static void access$45(SPTBatch_ var0, String var1) {
      var0.ySelectedSpot = var1;
   }

   // $FF: synthetic method
   static void access$46(SPTBatch_ var0, String var1) {
      var0.xSelectedLink = var1;
   }

   // $FF: synthetic method
   static void access$47(SPTBatch_ var0, String var1) {
      var0.ySelectedLink = var1;
   }

   // $FF: synthetic method
   static void access$48(SPTBatch_ var0, String var1) {
      var0.xSelectedTrack = var1;
   }

   // $FF: synthetic method
   static void access$49(SPTBatch_ var0, String var1) {
      var0.ySelectedTrack = var1;
   }

   // $FF: synthetic method
   static String access$50(SPTBatch_ var0) {
      return var0.xmlPath;
   }

   // $FF: synthetic method
   static String access$51(SPTBatch_ var0) {
      return var0.imagesPath;
   }

   // $FF: synthetic method
   static void access$52(SPTBatch_ var0, String[] var1) {
      var0.imageTitles = var1;
   }

   // $FF: synthetic method
   static String[] access$53(SPTBatch_ var0) {
      return var0.imageTitles;
   }

   // $FF: synthetic method
   static void access$54(SPTBatch_ var0, ResultsTable var1) {
      var0.rtSpot = var1;
   }

   // $FF: synthetic method
   static void access$55(SPTBatch_ var0, ResultsTable var1) {
      var0.rtLink = var1;
   }

   // $FF: synthetic method
   static void access$56(SPTBatch_ var0, ResultsTable var1) {
      var0.rtTrack = var1;
   }

   // $FF: synthetic method
   static ResultsTable access$57(SPTBatch_ var0) {
      return var0.rtSpot;
   }

   // $FF: synthetic method
   static ResultsTable access$58(SPTBatch_ var0) {
      return var0.rtLink;
   }

   // $FF: synthetic method
   static ResultsTable access$59(SPTBatch_ var0) {
      return var0.rtTrack;
   }

   // $FF: synthetic method
   static void access$60(SPTBatch_ var0, int[] var1) {
      var0.dims = var1;
   }

   // $FF: synthetic method
   static int[] access$61(SPTBatch_ var0) {
      return var0.dims;
   }

   // $FF: synthetic method
   static void access$62(SPTBatch_ var0, Logger var1) {
      var0.loggers = var1;
   }

   // $FF: synthetic method
   static void access$63(SPTBatch_ var0, String var1) {
      var0.zstart = var1;
   }

   // $FF: synthetic method
   static void access$64(SPTBatch_ var0, String var1) {
      var0.zend = var1;
   }

   // $FF: synthetic method
   static void access$65(SPTBatch_ var0, String var1) {
      var0.ystart = var1;
   }

   // $FF: synthetic method
   static void access$66(SPTBatch_ var0, String var1) {
      var0.yend = var1;
   }

   // $FF: synthetic method
   static void access$67(SPTBatch_ var0, String var1) {
      var0.xstart = var1;
   }

   // $FF: synthetic method
   static void access$68(SPTBatch_ var0, String var1) {
      var0.xend = var1;
   }

   // $FF: synthetic method
   static void access$69(SPTBatch_ var0, String var1) {
      var0.tstart = var1;
   }

   // $FF: synthetic method
   static void access$70(SPTBatch_ var0, String var1) {
      var0.tend = var1;
   }

   // $FF: synthetic method
   static void access$71(SPTBatch_ var0, String var1) {
      var0.THRESHOLD = var1;
   }

   // $FF: synthetic method
   static void access$72(SPTBatch_ var0, String var1) {
      var0.TARGET_CHANNEL = var1;
   }

   // $FF: synthetic method
   static void access$73(SPTBatch_ var0, String var1) {
      var0.DO_SUBPIXEL_LOCALIZATION = var1;
   }

   // $FF: synthetic method
   static void access$74(SPTBatch_ var0, String var1) {
      var0.DO_MEDIAN_FILTERING = var1;
   }

   // $FF: synthetic method
   static void access$75(SPTBatch_ var0, String var1) {
      var0.DETECTOR_NAME = var1;
   }

   // $FF: synthetic method
   static String access$76(SPTBatch_ var0) {
      return var0.DETECTOR_NAME;
   }

   // $FF: synthetic method
   static void access$77(SPTBatch_ var0, String var1) {
      var0.NSPLIT = var1;
   }

   // $FF: synthetic method
   static void access$78(SPTBatch_ var0, String var1) {
      var0.DOWNSAMPLE_FACTOR = var1;
   }

   // $FF: synthetic method
   static void access$79(SPTBatch_ var0, String var1) {
      var0.linkingNames = var1;
   }

   // $FF: synthetic method
   static void access$80(SPTBatch_ var0, String var1) {
      var0.linkingValues = var1;
   }

   // $FF: synthetic method
   static void access$81(SPTBatch_ var0, String var1) {
      var0.initialSpotFilter = var1;
   }

   // $FF: synthetic method
   static void access$82(SPTBatch_ var0, String var1) {
      var0.TRACKER_NAME = var1;
   }

   // $FF: synthetic method
   static void access$83(SPTBatch_ var0, String var1) {
      var0.CUTOFF_PERCENTILE = var1;
   }

   // $FF: synthetic method
   static void access$84(SPTBatch_ var0, String var1) {
      var0.BLOCKING_VALUE = var1;
   }

   // $FF: synthetic method
   static void access$85(SPTBatch_ var0, String var1) {
      var0.ALTERNATIVE_LINKING_COST_FACTOR = var1;
   }

   // $FF: synthetic method
   static void access$86(SPTBatch_ var0, String var1) {
      var0.LINKING_MAX_DISTANCE = var1;
   }

   // $FF: synthetic method
   static void access$87(SPTBatch_ var0, String var1) {
      var0.MAX_FRAME_GAP = var1;
   }

   // $FF: synthetic method
   static void access$88(SPTBatch_ var0, String var1) {
      var0.MAX_DISTANCE = var1;
   }

   // $FF: synthetic method
   static void access$89(SPTBatch_ var0, String var1) {
      var0.ALLOW_GAP_CLOSING = var1;
   }

   // $FF: synthetic method
   static void access$90(SPTBatch_ var0, String var1) {
      var0.SPLITTING_MAX_DISTANCE = var1;
   }

   // $FF: synthetic method
   static void access$91(SPTBatch_ var0, String var1) {
      var0.ALLOW_TRACK_SPLITTING = var1;
   }

   // $FF: synthetic method
   static void access$92(SPTBatch_ var0, String var1) {
      var0.MERGING_MAX_DISTANCE = var1;
   }

   // $FF: synthetic method
   static void access$93(SPTBatch_ var0, String var1) {
      var0.ALLOW_TRACK_MERGING = var1;
   }

   // $FF: synthetic method
   static void access$94(SPTBatch_ var0, Settings var1) {
      var0.settings = var1;
   }

   // $FF: synthetic method
   static Settings access$95(SPTBatch_ var0) {
      return var0.settings;
   }

   // $FF: synthetic method
   static String access$96(SPTBatch_ var0) {
      return var0.DO_SUBPIXEL_LOCALIZATION;
   }

   // $FF: synthetic method
   static String access$97(SPTBatch_ var0) {
      return var0.TARGET_CHANNEL;
   }

   // $FF: synthetic method
   static String access$98(SPTBatch_ var0) {
      return var0.THRESHOLD;
   }

   // $FF: synthetic method
   static String access$99(SPTBatch_ var0) {
      return var0.DO_MEDIAN_FILTERING;
   }

   // $FF: synthetic method
   static String access$100(SPTBatch_ var0) {
      return var0.initialSpotFilter;
   }

   // $FF: synthetic method
   static String access$101(SPTBatch_ var0) {
      return var0.TRACKER_NAME;
   }

   // $FF: synthetic method
   static String access$102(SPTBatch_ var0) {
      return var0.LINKING_MAX_DISTANCE;
   }

   // $FF: synthetic method
   static String access$103(SPTBatch_ var0) {
      return var0.MAX_DISTANCE;
   }

   // $FF: synthetic method
   static String access$104(SPTBatch_ var0) {
      return var0.MAX_FRAME_GAP;
   }

   // $FF: synthetic method
   static String access$105(SPTBatch_ var0) {
      return var0.ALLOW_GAP_CLOSING;
   }

   // $FF: synthetic method
   static String access$106(SPTBatch_ var0) {
      return var0.ALLOW_TRACK_SPLITTING;
   }

   // $FF: synthetic method
   static String access$107(SPTBatch_ var0) {
      return var0.SPLITTING_MAX_DISTANCE;
   }

   // $FF: synthetic method
   static String access$108(SPTBatch_ var0) {
      return var0.ALLOW_TRACK_MERGING;
   }

   // $FF: synthetic method
   static String access$109(SPTBatch_ var0) {
      return var0.MERGING_MAX_DISTANCE;
   }

   // $FF: synthetic method
   static void access$110(SPTBatch_ var0, TrackMate var1) {
      var0.trackmate = var1;
   }

   // $FF: synthetic method
   static TrackMate access$111(SPTBatch_ var0) {
      return var0.trackmate;
   }

   // $FF: synthetic method
   static void access$112(SPTBatch_ var0, HyperStackDisplayer var1) {
      var0.displayer = var1;
   }

   // $FF: synthetic method
   static HyperStackDisplayer access$113(SPTBatch_ var0) {
      return var0.displayer;
   }

   // $FF: synthetic method
   static void access$114(SPTBatch_ var0, ImagePlus var1) {
      var0.capture = var1;
   }

   // $FF: synthetic method
   static ImagePlus access$115(SPTBatch_ var0) {
      return var0.capture;
   }

   // $FF: synthetic method
   static void access$116(ImagePlus var0, ImagePlus var1) {
      transferCalibration(var0, var1);
   }

   // $FF: synthetic method
   static float[] access$117(double[] var0) {
      return toFloat(var0);
   }

   // $FF: synthetic method
   static String access$118(SPTBatch_ var0) {
      return var0.enableST;
   }

   // $FF: synthetic method
   static void access$119(SPTBatch_ var0, JFreeChart var1) {
      var0.chart = var1;
   }

   // $FF: synthetic method
   static JFreeChart access$120(SPTBatch_ var0) {
      return var0.chart;
   }

   // $FF: synthetic method
   static String access$121(SPTBatch_ var0) {
      return var0.enableSpotTable;
   }

   // $FF: synthetic method
   static TablePanel access$122(SPTBatch_ var0, Model var1, DisplaySettings var2) {
      return var0.createSpotTable(var1, var2);
   }

   // $FF: synthetic method
   static TablePanel access$123(SPTBatch_ var0, Model var1, DisplaySettings var2) {
      return var0.createTrackTable(var1, var2);
   }

   // $FF: synthetic method
   static String access$124(SPTBatch_ var0) {
      return var0.enableLinkTable;
   }

   // $FF: synthetic method
   static TablePanel access$125(SPTBatch_ var0, Model var1, DisplaySettings var2) {
      return var0.createEdgeTable(var1, var2);
   }

   // $FF: synthetic method
   static String access$126(SPTBatch_ var0) {
      return var0.enableTrackTable;
   }

   // $FF: synthetic method
   static double access$127(SPTBatch_ var0) {
      return var0.thD14JTF;
   }

   // $FF: synthetic method
   static int access$128(SPTBatch_ var0) {
      return var0.totalTracksDef;
   }

   // $FF: synthetic method
   static void access$129(SPTBatch_ var0, int var1) {
      var0.totalTracksDef = var1;
   }

   // $FF: synthetic method
   static int access$130(SPTBatch_ var0) {
      return var0.longTracksDef;
   }

   // $FF: synthetic method
   static void access$131(SPTBatch_ var0, int var1) {
      var0.longTracksDef = var1;
   }

   // $FF: synthetic method
   static int access$132(SPTBatch_ var0) {
      return var0.longConfinedDef;
   }

   // $FF: synthetic method
   static void access$133(SPTBatch_ var0, int var1) {
      var0.longConfinedDef = var1;
   }

   // $FF: synthetic method
   static int access$134(SPTBatch_ var0) {
      return var0.longUniBalDef;
   }

   // $FF: synthetic method
   static void access$135(SPTBatch_ var0, int var1) {
      var0.longUniBalDef = var1;
   }

   // $FF: synthetic method
   static int access$136(SPTBatch_ var0) {
      return var0.longFreeDef;
   }

   // $FF: synthetic method
   static void access$137(SPTBatch_ var0, int var1) {
      var0.longFreeDef = var1;
   }

   // $FF: synthetic method
   static int access$138(SPTBatch_ var0) {
      return var0.longDirectDef;
   }

   // $FF: synthetic method
   static void access$139(SPTBatch_ var0, int var1) {
      var0.longDirectDef = var1;
   }

   // $FF: synthetic method
   static int access$140(SPTBatch_ var0) {
      return var0.shortTracksDef;
   }

   // $FF: synthetic method
   static void access$141(SPTBatch_ var0, int var1) {
      var0.shortTracksDef = var1;
   }

   // $FF: synthetic method
   static int access$142(SPTBatch_ var0) {
      return var0.shortConfinedDef;
   }

   // $FF: synthetic method
   static void access$143(SPTBatch_ var0, int var1) {
      var0.shortConfinedDef = var1;
   }

   // $FF: synthetic method
   static int access$144(SPTBatch_ var0) {
      return var0.shortAnomDef;
   }

   // $FF: synthetic method
   static void access$145(SPTBatch_ var0, int var1) {
      var0.shortAnomDef = var1;
   }

   // $FF: synthetic method
   static int access$146(SPTBatch_ var0) {
      return var0.shortFreeDef;
   }

   // $FF: synthetic method
   static void access$147(SPTBatch_ var0, int var1) {
      var0.shortFreeDef = var1;
   }

   // $FF: synthetic method
   static int access$148(SPTBatch_ var0) {
      return var0.shortDirectDef;
   }

   // $FF: synthetic method
   static void access$149(SPTBatch_ var0, int var1) {
      var0.shortDirectDef = var1;
   }

   // $FF: synthetic method
   static int access$150(SPTBatch_ var0) {
      return var0.immobDef;
   }

   // $FF: synthetic method
   static void access$151(SPTBatch_ var0, int var1) {
      var0.immobDef = var1;
   }

   // $FF: synthetic method
   static String access$152(SPTBatch_ var0) {
      return var0.enableBranchTable;
   }

   // $FF: synthetic method
   static String access$153(SPTBatch_ var0) {
      return var0.ySelectedSpot;
   }

   // $FF: synthetic method
   static String access$154(SPTBatch_ var0) {
      return var0.xSelectedSpot;
   }

   // $FF: synthetic method
   static Set access$155(SPTBatch_ var0, Iterable var1, Map var2) {
      return var0.getUniqueValues(var1, var2);
   }

   // $FF: synthetic method
   static List access$156(Object var0, Iterable var1, Map var2) {
      return getCommonKeys(var0, var1, var2);
   }

   // $FF: synthetic method
   static String access$157(SPTBatch_ var0, Iterable var1, Map var2, String var3) {
      return var0.buildPlotTitle(var1, var2, var3);
   }

   // $FF: synthetic method
   static String access$158(SPTBatch_ var0) {
      return var0.ySelectedLink;
   }

   // $FF: synthetic method
   static String access$159(SPTBatch_ var0) {
      return var0.xSelectedLink;
   }

   // $FF: synthetic method
   static String access$160(SPTBatch_ var0) {
      return var0.ySelectedTrack;
   }

   // $FF: synthetic method
   static String access$161(SPTBatch_ var0) {
      return var0.xSelectedTrack;
   }
}
