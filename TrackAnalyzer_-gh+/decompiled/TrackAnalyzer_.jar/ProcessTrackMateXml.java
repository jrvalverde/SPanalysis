import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.FeatureModel;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.detection.DogDetectorFactory;
import fiji.plugin.trackmate.detection.LogDetectorFactory;
import fiji.plugin.trackmate.detection.ManualDetectorFactory;
import fiji.plugin.trackmate.features.FeatureFilter;
import fiji.plugin.trackmate.features.FeatureUtils;
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
import fiji.plugin.trackmate.util.TMUtils;
import fiji.plugin.trackmate.visualization.FeatureColorGenerator;
import fiji.plugin.trackmate.visualization.hyperstack.HyperStackDisplayer;
import fiji.plugin.trackmate.visualization.table.TablePanel;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.process.ColorProcessor;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.ListSelectionModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProcessTrackMateXml {
   Thread mainProcess;
   String zstart;
   String zend;
   String ystart;
   String yend;
   String xstart;
   String xend;
   String tstart;
   String tend;
   String RADIUS;
   String THRESHOLD;
   String TARGET_CHANNEL;
   String DO_SUBPIXEL_LOCALIZATION;
   String DO_MEDIAN_FILTERING;
   String DETECTOR_NAME;
   String NSPLIT;
   String DOWNSAMPLE_FACTOR;
   String initialSpotFilter;
   String TRACKER_NAME;
   String CUTOFF_PERCENTILE;
   String ALTERNATIVE_LINKING_COST_FACTOR;
   String LINKING_MAX_DISTANCE;
   String MAX_FRAME_GAP;
   String MAX_DISTANCE;
   String ALLOW_GAP_CLOSING;
   String SPLITTING_MAX_DISTANCE;
   String ALLOW_TRACK_SPLITTING;
   String MERGING_MAX_DISTANCE;
   String ALLOW_TRACK_MERGING;
   String BLOCKING_VALUE;
   static Model model;
   static Settings settings;
   static SelectionModel selectionModel;
   static TrackMate trackmate;
   SpotCollection totalSpots;
   static HyperStackDisplayer displayer;
   ImagePlus impAnal;
   ImagePlus[] imps;
   static boolean tracksVisible;
   static boolean spotsVisible;
   static String[][] dataSpot;
   static String[][] dataTrack;
   static String[] columnHeadersSpot;
   static String[] columnHeadersTrack;
   static DisplaySettings ds;
   public static final String NAME = "Export statistics to tables";
   public static final String KEY = "EXPORT_STATS_TO_IJ";
   public static final String INFO_TEXT = "<html>Compute and export all statistics to 3 ImageJ results table. Statistisc are separated in features computed for: <ol> \t<li> spots in filtered tracks; \t<li> links between those spots; \t<li> filtered tracks. </ol> For tracks and links, they are recalculated prior to exporting. Note that spots and links that are not in a filtered tracks are not part of this export.</html>";
   private static final String SPOT_TABLE_NAME = "Spots in tracks statistics";
   private static final String EDGE_TABLE_NAME = "Links in tracks statistics";
   private static final String TRACK_TABLE_NAME = "Track statistics";
   private static final String ID_COLUMN = "ID";
   private static final String TRACK_ID_COLUMN = "TRACK_ID";
   private ResultsTable spotTable;
   private ResultsTable edgeTable;
   private ResultsTable trackTable;
   public static final String KEYLINEAR = "Linear track analysis";
   public static final String TRACK_TOTAL_DISTANCE_TRAVELED = "TOTAL_DISTANCE_TRAVELED";
   public static final String TRACK_MAX_DISTANCE_TRAVELED = "MAX_DISTANCE_TRAVELED";
   public static final String TRACK_CONFINMENT_RATIO = "CONFINMENT_RATIO";
   public static final String TRACK_MEAN_STRAIGHT_LINE_SPEED = "MEAN_STRAIGHT_LINE_SPEED";
   public static final String TRACK_LINEARITY_OF_FORWARD_PROGRESSION = "LINEARITY_OF_FORWARD_PROGRESSION";
   public static final String TOTAL_ABSOLUTE_ANGLE_XY = "TOTAL_ABSOLUTE_ANGLE_XY";
   public static final String TOTAL_ABSOLUTE_ANGLE_YZ = "TOTAL_ABSOLUTE_ANGLE_YZ";
   public static final String TOTAL_ABSOLUTE_ANGLE_ZX = "TOTAL_ABSOLUTE_ANGLE_ZX";
   public static final List<String> FEATURES = new ArrayList(9);
   public static final Map<String, String> FEATURE_NAMES = new HashMap(9);
   public static final Map<String, String> FEATURE_SHORT_NAMES = new HashMap(9);
   public static final Map<String, Dimension> FEATURE_DIMENSIONS = new HashMap(9);
   public static final Map<String, Boolean> IS_INT = new HashMap(9);

   public ProcessTrackMateXml() {
      this.imps = FirstWizardPanel.imps;
   }

   public void processTrackMateXml() {
      this.mainProcess = new Thread(new Runnable() {
         public void run() {
            File fileXML = new File(TrackAnalyzer_.xmlPath);
            List<ImagePlus> impAnalClose = new ArrayList();
            int[] IDs = WindowManager.getIDList();
            int i;
            if (IDs != null) {
               for(i = 0; i < IDs.length; ++i) {
                  impAnalClose.add(WindowManager.getImage(IDs[i]));
               }
            }

            if (FirstWizardPanel.spotEnable.equals("spotEnable") == Boolean.TRUE && FirstWizardPanel.tableImages.getSelectedRow() != -1) {
               if (IDs != null) {
                  for(i = 0; i < IDs.length; ++i) {
                     ((ImagePlus)impAnalClose.get(i)).hide();
                  }
               }

               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[FirstWizardPanel.tableImages.getSelectedRow()];
            }

            if (FirstWizardPanel.spotEnable.equals("spotEnable") == Boolean.TRUE && FirstWizardPanel.tableImages.getSelectedRow() == -1) {
               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[ChooserWizardPanel.tableImages.getSelectedRow()];
            }

            if (ChooserWizardPanel.trackEnable.equals("trackEnable") == Boolean.TRUE && ChooserWizardPanel.tableImages.getSelectedRow() != -1) {
               if (IDs != null) {
                  for(i = 0; i < IDs.length; ++i) {
                     ((ImagePlus)impAnalClose.get(i)).hide();
                  }
               }

               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[ChooserWizardPanel.tableImages.getSelectedRow()];
            }

            if (ChooserWizardPanel.trackEnable.equals("trackEnable") == Boolean.TRUE && ChooserWizardPanel.tableImages.getSelectedRow() == -1) {
               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[FirstWizardPanel.tableImages.getSelectedRow()];
            }

            ProcessTrackMateXml.this.impAnal.show();
            int[] dims = ProcessTrackMateXml.this.impAnal.getDimensions();
            if (dims[4] == 1 && dims[3] > 1) {
               ProcessTrackMateXml.this.impAnal.setDimensions(dims[2], dims[4], dims[3]);
               Calibration calibration = ProcessTrackMateXml.this.impAnal.getCalibration();
               calibration.frameInterval = 1.0D;
            }

            new TmXmlReader(fileXML);
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;

            try {
               builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException var116) {
               var116.printStackTrace();
            }

            Document doc = null;

            try {
               doc = builder.parse(TrackAnalyzer_.xmlPath);
            } catch (SAXException var114) {
               var114.printStackTrace();
            } catch (IOException var115) {
               var115.printStackTrace();
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
            } catch (XPathExpressionException var113) {
               var113.printStackTrace();
            }

            try {
               exprLinkingP = xpath.compile("//Linking/FeaturePenalties[@MEAN_INTENSITY]");
            } catch (XPathExpressionException var112) {
               var112.printStackTrace();
            }

            try {
               exprDetectorSettings = xpath.compile("//Settings/DetectorSettings[@RADIUS]");
            } catch (XPathExpressionException var111) {
               var111.printStackTrace();
            }

            try {
               exprInitialSpotFilter = xpath.compile("//Settings/InitialSpotFilter[@feature]");
            } catch (XPathExpressionException var110) {
               var110.printStackTrace();
            }

            try {
               exprFilter = xpath.compile("//SpotFilterCollection/Filter[@feature]");
            } catch (XPathExpressionException var109) {
               var109.printStackTrace();
            }

            try {
               exprTrackerSettings = xpath.compile("//Settings/TrackerSettings[@TRACKER_NAME]");
            } catch (XPathExpressionException var108) {
               var108.printStackTrace();
            }

            try {
               exprLinking = xpath.compile("//TrackerSettings/Linking[@LINKING_MAX_DISTANCE]");
            } catch (XPathExpressionException var107) {
               var107.printStackTrace();
            }

            try {
               exprGapClosing = xpath.compile("//TrackerSettings/GapClosing[@MAX_FRAME_GAP]");
            } catch (XPathExpressionException var106) {
               var106.printStackTrace();
            }

            try {
               exprSplitting = xpath.compile("//TrackerSettings/TrackSplitting[@SPLITTING_MAX_DISTANCE]");
            } catch (XPathExpressionException var105) {
               var105.printStackTrace();
            }

            try {
               exprMerging = xpath.compile("//TrackerSettings/TrackMerging[@MERGING_MAX_DISTANCE]");
            } catch (XPathExpressionException var104) {
               var104.printStackTrace();
            }

            try {
               exprTrackFilter = xpath.compile("//TrackFilterCollection/Filter[@feature]");
            } catch (XPathExpressionException var103) {
               var103.printStackTrace();
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
            } catch (XPathExpressionException var102) {
               var102.printStackTrace();
            }

            Node linkingNames;
            for(int ixx = 0; ixx < nlBasicSettings.getLength(); ++ixx) {
               linkingNames = nlBasicSettings.item(ixx);
               ProcessTrackMateXml.this.zstart = linkingNames.getAttributes().getNamedItem("zstart").getNodeValue();
               ProcessTrackMateXml.this.zend = linkingNames.getAttributes().getNamedItem("zend").getNodeValue();
               ProcessTrackMateXml.this.ystart = linkingNames.getAttributes().getNamedItem("ystart").getNodeValue();
               ProcessTrackMateXml.this.yend = linkingNames.getAttributes().getNamedItem("yend").getNodeValue();
               ProcessTrackMateXml.this.xstart = linkingNames.getAttributes().getNamedItem("xstart").getNodeValue();
               ProcessTrackMateXml.this.xend = linkingNames.getAttributes().getNamedItem("xend").getNodeValue();
               ProcessTrackMateXml.this.tstart = linkingNames.getAttributes().getNamedItem("tstart").getNodeValue();
               ProcessTrackMateXml.this.tend = linkingNames.getAttributes().getNamedItem("tend").getNodeValue();
            }

            Node currentItem = null;

            for(int ix = 0; ix < nlDetectorSettings.getLength(); ++ix) {
               currentItem = nlDetectorSettings.item(ix);
               ProcessTrackMateXml.this.RADIUS = currentItem.getAttributes().getNamedItem("RADIUS").getNodeValue();
               ProcessTrackMateXml.this.THRESHOLD = currentItem.getAttributes().getNamedItem("THRESHOLD").getNodeValue();
               ProcessTrackMateXml.this.TARGET_CHANNEL = currentItem.getAttributes().getNamedItem("TARGET_CHANNEL").getNodeValue();
               ProcessTrackMateXml.this.DO_SUBPIXEL_LOCALIZATION = currentItem.getAttributes().getNamedItem("DO_SUBPIXEL_LOCALIZATION").getNodeValue();
               ProcessTrackMateXml.this.DO_MEDIAN_FILTERING = currentItem.getAttributes().getNamedItem("DO_MEDIAN_FILTERING").getNodeValue();
               ProcessTrackMateXml.this.DETECTOR_NAME = currentItem.getAttributes().getNamedItem("DETECTOR_NAME").getNodeValue();
               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("BLOCK_LOG_DETECTOR")) {
                  ProcessTrackMateXml.this.NSPLIT = currentItem.getAttributes().getNamedItem("NSPLIT").getNodeValue();
               }

               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("DOWNSAMLE_LOG_DETECTOR")) {
                  ProcessTrackMateXml.this.DOWNSAMPLE_FACTOR = currentItem.getAttributes().getNamedItem("DOWNSAMPLE_FACTOR").getNodeValue();
               }
            }

            linkingNames = null;
            String linkingValues = null;

            int ixxx;
            for(ixxx = 0; ixxx < nlLinkingP.getLength(); ++ixxx) {
               nlLinkingP.item(ixxx).getAttributes().item(ixxx).getNodeName();
               nlLinkingP.item(ixxx).getAttributes().item(ixxx).getNodeValue();
            }

            for(ixxx = 0; ixxx < nlInitialSpotFilter.getLength(); ++ixxx) {
               currentItem = nlInitialSpotFilter.item(ixxx);
               ProcessTrackMateXml.this.initialSpotFilter = currentItem.getAttributes().getNamedItem("value").getNodeValue();
            }

            String initialFilterFeature = null;
            String initialFilterValue = null;
            String initialFilterAbove = null;

            for(int ixxxx = 0; ixxxx < nlFilter.getLength(); ++ixxxx) {
               currentItem = nlFilter.item(ixxxx);
               initialFilterFeature = currentItem.getAttributes().getNamedItem("feature").getNodeValue();
               initialFilterValue = currentItem.getAttributes().getNamedItem("value").getNodeValue();
               initialFilterAbove = currentItem.getAttributes().getNamedItem("isabove").getNodeValue();
            }

            String initialTrackFilterFeature = null;
            String initialTrackFilterValue = null;
            String initialTrackFilterAbove = null;

            int ixxxxx;
            for(ixxxxx = 0; ixxxxx < nlTrackerSettings.getLength(); ++ixxxxx) {
               currentItem = nlTrackerSettings.item(ixxxxx);
               ProcessTrackMateXml.this.TRACKER_NAME = currentItem.getAttributes().getNamedItem("TRACKER_NAME").getNodeValue();
               ProcessTrackMateXml.this.CUTOFF_PERCENTILE = currentItem.getAttributes().getNamedItem("CUTOFF_PERCENTILE").getNodeValue();
               ProcessTrackMateXml.this.BLOCKING_VALUE = currentItem.getAttributes().getNamedItem("BLOCKING_VALUE").getNodeValue();
               ProcessTrackMateXml.this.ALTERNATIVE_LINKING_COST_FACTOR = currentItem.getAttributes().getNamedItem("ALTERNATIVE_LINKING_COST_FACTOR").getNodeValue();
            }

            for(ixxxxx = 0; ixxxxx < nlLinking.getLength(); ++ixxxxx) {
               currentItem = nlLinking.item(ixxxxx);
               ProcessTrackMateXml.this.LINKING_MAX_DISTANCE = currentItem.getAttributes().getNamedItem("LINKING_MAX_DISTANCE").getNodeValue();
            }

            for(ixxxxx = 0; ixxxxx < nlGapClosing.getLength(); ++ixxxxx) {
               currentItem = nlGapClosing.item(ixxxxx);
               ProcessTrackMateXml.this.MAX_FRAME_GAP = currentItem.getAttributes().getNamedItem("MAX_FRAME_GAP").getNodeValue();
               ProcessTrackMateXml.this.MAX_DISTANCE = currentItem.getAttributes().getNamedItem("GAP_CLOSING_MAX_DISTANCE").getNodeValue();
               ProcessTrackMateXml.this.ALLOW_GAP_CLOSING = currentItem.getAttributes().getNamedItem("ALLOW_GAP_CLOSING").getNodeValue();
            }

            for(ixxxxx = 0; ixxxxx < nlSplitting.getLength(); ++ixxxxx) {
               currentItem = nlSplitting.item(ixxxxx);
               ProcessTrackMateXml.this.SPLITTING_MAX_DISTANCE = currentItem.getAttributes().getNamedItem("SPLITTING_MAX_DISTANCE").getNodeValue();
               ProcessTrackMateXml.this.ALLOW_TRACK_SPLITTING = currentItem.getAttributes().getNamedItem("ALLOW_TRACK_SPLITTING").getNodeValue();
            }

            for(ixxxxx = 0; ixxxxx < nlMerging.getLength(); ++ixxxxx) {
               currentItem = nlMerging.item(ixxxxx);
               ProcessTrackMateXml.this.MERGING_MAX_DISTANCE = currentItem.getAttributes().getNamedItem("MERGING_MAX_DISTANCE").getNodeValue();
               ProcessTrackMateXml.this.ALLOW_TRACK_MERGING = currentItem.getAttributes().getNamedItem("ALLOW_TRACK_MERGING").getNodeValue();
            }

            ProcessTrackMateXml.settings = new Settings(ProcessTrackMateXml.this.impAnal);
            ProcessTrackMateXml.settings.dt = 0.05D;
            FeatureFilter ok;
            if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("LOG_DETECTOR")) {
               ProcessTrackMateXml.settings.detectorFactory = new LogDetectorFactory();
               ProcessTrackMateXml.settings.detectorSettings = ProcessTrackMateXml.settings.detectorFactory.getDefaultSettings();
               ProcessTrackMateXml.settings.detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", Boolean.parseBoolean(ProcessTrackMateXml.this.DO_SUBPIXEL_LOCALIZATION));
               ProcessTrackMateXml.settings.detectorSettings.put("RADIUS", Double.parseDouble(ProcessTrackMateXml.this.RADIUS));
               ProcessTrackMateXml.settings.detectorSettings.put("TARGET_CHANNEL", Integer.parseInt(ProcessTrackMateXml.this.TARGET_CHANNEL));
               ProcessTrackMateXml.settings.detectorSettings.put("THRESHOLD", Double.parseDouble(ProcessTrackMateXml.this.THRESHOLD));
               ProcessTrackMateXml.settings.detectorSettings.put("DO_MEDIAN_FILTERING", Boolean.parseBoolean(ProcessTrackMateXml.this.DO_MEDIAN_FILTERING));
               if (ProcessTrackMateXml.this.initialSpotFilter != null) {
                  ProcessTrackMateXml.settings.initialSpotFilterValue = Double.parseDouble(ProcessTrackMateXml.this.initialSpotFilter);
               }

               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("MANUAL_DETECTOR")) {
                  ProcessTrackMateXml.settings.detectorFactory = new ManualDetectorFactory();
                  ProcessTrackMateXml.settings.detectorSettings.put("RADIUS", Double.parseDouble(ProcessTrackMateXml.this.RADIUS));
                  if (ProcessTrackMateXml.this.initialSpotFilter != null) {
                     ProcessTrackMateXml.settings.initialSpotFilterValue = Double.parseDouble(ProcessTrackMateXml.this.initialSpotFilter);
                  }
               }

               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("DOG_DETECTOR")) {
                  ProcessTrackMateXml.settings.detectorFactory = new DogDetectorFactory();
                  ProcessTrackMateXml.settings.detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", Boolean.parseBoolean(ProcessTrackMateXml.this.DO_SUBPIXEL_LOCALIZATION));
                  ProcessTrackMateXml.settings.detectorSettings.put("RADIUS", Double.parseDouble(ProcessTrackMateXml.this.RADIUS));
                  ProcessTrackMateXml.settings.detectorSettings.put("TARGET_CHANNEL", Integer.parseInt(ProcessTrackMateXml.this.TARGET_CHANNEL));
                  ProcessTrackMateXml.settings.detectorSettings.put("THRESHOLD", Double.parseDouble(ProcessTrackMateXml.this.THRESHOLD));
                  ProcessTrackMateXml.settings.detectorSettings.put("DO_MEDIAN_FILTERING", Double.parseDouble(ProcessTrackMateXml.this.DO_MEDIAN_FILTERING));
                  if (ProcessTrackMateXml.this.initialSpotFilter != null) {
                     ProcessTrackMateXml.settings.initialSpotFilterValue = Double.parseDouble(ProcessTrackMateXml.this.initialSpotFilter);
                  }
               }

               if (initialFilterFeature != null) {
                  ok = new FeatureFilter(initialFilterFeature, Double.parseDouble(initialFilterValue), Boolean.parseBoolean(initialFilterAbove));
                  ProcessTrackMateXml.settings.addSpotFilter(ok);
               }
            }

            if (ProcessTrackMateXml.this.TRACKER_NAME.equals("MANUAL_TRACKER")) {
               ProcessTrackMateXml.settings.trackerFactory = new ManualTrackerFactory();
               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
            }

            if (ProcessTrackMateXml.this.TRACKER_NAME.equals("MANUAL_TRACKER")) {
               ProcessTrackMateXml.settings.trackerFactory = new ManualTrackerFactory();
               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
            }

            if (ProcessTrackMateXml.this.TRACKER_NAME.equals("KALMAN_TRACKER")) {
               ProcessTrackMateXml.settings.trackerFactory = new KalmanTrackerFactory();
               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
               ProcessTrackMateXml.settings.trackerSettings.put("KALMAN_SEARCH_RADIUS", Double.parseDouble(ProcessTrackMateXml.this.RADIUS));
            }

            if (ProcessTrackMateXml.this.TRACKER_NAME.equals("SIMPLE_SPARSE_LAP_TRACKER")) {
               ProcessTrackMateXml.settings.trackerFactory = new SimpleSparseLAPTrackerFactory();
               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
               ProcessTrackMateXml.settings.trackerSettings.put("LINKING_MAX_DISTANCE", Double.parseDouble(ProcessTrackMateXml.this.LINKING_MAX_DISTANCE));
               ProcessTrackMateXml.settings.trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", Double.parseDouble(ProcessTrackMateXml.this.MAX_DISTANCE));
               ProcessTrackMateXml.settings.trackerSettings.put("MAX_FRAME_GAP", Double.parseDouble(ProcessTrackMateXml.this.MAX_FRAME_GAP));
            }

            if (ProcessTrackMateXml.this.TRACKER_NAME.equals("SPARSE_LAP_TRACKER")) {
               ProcessTrackMateXml.settings.trackerFactory = new SparseLAPTrackerFactory();
               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
               ProcessTrackMateXml.settings.trackerSettings.put("LINKING_MAX_DISTANCE", Double.parseDouble(ProcessTrackMateXml.this.LINKING_MAX_DISTANCE));
               Map<String, Double> linkingPenalty = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((data) -> {
                  return (String)data[0];
               }, (data) -> {
                  return (Double)data[1];
               }));
               ProcessTrackMateXml.settings.trackerSettings.put("ALLOW_GAP_CLOSING", Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_GAP_CLOSING));
               Map var44;
               if (Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_GAP_CLOSING)) {
                  ProcessTrackMateXml.settings.trackerSettings.put("MAX_FRAME_GAP", Integer.parseInt(ProcessTrackMateXml.this.MAX_FRAME_GAP));
                  ProcessTrackMateXml.settings.trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", Double.parseDouble(ProcessTrackMateXml.this.MAX_DISTANCE));
                  var44 = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((data) -> {
                     return (String)data[0];
                  }, (data) -> {
                     return (Double)data[1];
                  }));
               }

               ProcessTrackMateXml.settings.trackerSettings.put("ALLOW_TRACK_SPLITTING", Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_SPLITTING));
               if (Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_SPLITTING)) {
                  ProcessTrackMateXml.settings.trackerSettings.put("SPLITTING_MAX_DISTANCE", Double.parseDouble(ProcessTrackMateXml.this.SPLITTING_MAX_DISTANCE));
                  var44 = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((data) -> {
                     return (String)data[0];
                  }, (data) -> {
                     return (Double)data[1];
                  }));
               }

               ProcessTrackMateXml.settings.trackerSettings.put("ALLOW_TRACK_MERGING", Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_MERGING));
               if (Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_MERGING)) {
                  ProcessTrackMateXml.settings.trackerSettings.put("MERGING_MAX_DISTANCE", Double.parseDouble(ProcessTrackMateXml.this.MERGING_MAX_DISTANCE));
                  var44 = (Map)Stream.of(new Object[]{"MEAN_INTENSITY", 1.0D}, new Object[]{"QUALITY", 1.0D}).collect(Collectors.toMap((data) -> {
                     return (String)data[0];
                  }, (data) -> {
                     return (Double)data[1];
                  }));
               }
            }

            ProcessTrackMateXml.settings.addAllAnalyzers();
            ProcessTrackMateXml.model = new Model();
            ProcessTrackMateXml.trackmate = new TrackMate(ProcessTrackMateXml.model, ProcessTrackMateXml.settings);
            ok = null;
            Boolean okx = ProcessTrackMateXml.trackmate.checkInput();
            okx = ProcessTrackMateXml.trackmate.process();
            FeatureModel fm = ProcessTrackMateXml.model.getFeatureModel();
            Set<Integer> trackIDs = ProcessTrackMateXml.model.getTrackModel().trackIDs(true);
            Set<Spot> track = null;

            for(int id = 0; id < trackIDs.size(); ++id) {
               fm.getTrackFeature(id, "TRACK_MEAN_SPEED");
               track = ProcessTrackMateXml.model.getTrackModel().trackSpots(id);
            }

            Double widthx;
            Double heightx;
            Double nCapturesx;
            Double stackx;
            Double channelx;
            Double slicex;
            Spot spot;
            for(Iterator var48 = track.iterator(); var48.hasNext(); slicex = spot.getFeature("MEAN_INTENSITY")) {
               spot = (Spot)var48.next();
               int sid = spot.ID();
               widthx = spot.getFeature("POSITION_X");
               heightx = spot.getFeature("POSITION_Y");
               nCapturesx = spot.getFeature("FRAME");
               stackx = spot.getFeature("QUALITY");
               channelx = spot.getFeature("SNR");
            }

            ProcessTrackMateXml.this.totalSpots = ProcessTrackMateXml.model.getSpots();
            ProcessTrackMateXml.displayer = null;
            ProcessTrackMateXml.selectionModel = new SelectionModel(ProcessTrackMateXml.model);
            ProcessTrackMateXml.ds = DisplaySettingsIO.readUserDefault();
            ProcessTrackMateXml.ds.setSpotShowName(true);
            ProcessTrackMateXml.ds.setSpotVisible(ProcessTrackMateXml.spotsVisible);
            ProcessTrackMateXml.ds.setSpotColorBy(TrackMateObject.TRACKS, "TRACK_INDEX");
            ProcessTrackMateXml.ds.setTrackVisible(ProcessTrackMateXml.tracksVisible);
            ProcessTrackMateXml.ds.setTrackColorBy(TrackMateObject.TRACKS, "TRACK_INDEX");
            ProcessTrackMateXml.ds.setTrackDisplayMode(TrackDisplayMode.FULL);
            ProcessTrackMateXml.displayer = new HyperStackDisplayer(ProcessTrackMateXml.model, ProcessTrackMateXml.selectionModel, ProcessTrackMateXml.this.impAnal, ProcessTrackMateXml.ds);
            ProcessTrackMateXml.displayer.render();
            ProcessTrackMateXml.displayer.refresh();
            spot = null;
            Integer firstFrame = null;
            Integer lastFrame = null;
            widthx = null;
            heightx = null;
            nCapturesx = null;
            stackx = null;
            channelx = null;
            slicex = null;
            BufferedImage bi = null;
            ColorProcessor cp = null;
            Integer index = null;
            ImagePlus capture = null;
            if (ProcessTrackMateXml.this.impAnal.getNFrames() > 1) {
               firstFrame = Math.max(1, Math.min(ProcessTrackMateXml.this.impAnal.getNFrames(), 1));
               lastFrame = Math.min(ProcessTrackMateXml.this.impAnal.getNFrames(), Math.max(ProcessTrackMateXml.this.impAnal.getNFrames(), 1));
            }

            if (ProcessTrackMateXml.this.impAnal.getNSlices() > 1) {
               firstFrame = Math.max(1, Math.min(ProcessTrackMateXml.this.impAnal.getNSlices(), 1));
               lastFrame = Math.min(ProcessTrackMateXml.this.impAnal.getNSlices(), Math.max(ProcessTrackMateXml.this.impAnal.getNSlices(), 1));
            }

            Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
            Integer width = bounds.width;
            Integer height = bounds.height;
            Integer nCaptures = lastFrame - firstFrame + 1;
            ImageStack stack = new ImageStack(width, height);
            Integer channel = ProcessTrackMateXml.displayer.getImp().getChannel();
            Integer slice = ProcessTrackMateXml.displayer.getImp().getSlice();
            ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);

            for(int frame = firstFrame; frame <= lastFrame; ++frame) {
               ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, frame);
               bi = new BufferedImage(width, height, 2);
               ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
               cp = new ColorProcessor(bi);
               index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, frame);
               stack.addSlice(ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), cp);
            }

            ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
            capture = new ImagePlus("TrackMate capture of " + ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
            ProcessTrackMateXml.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
            FeatureModel fm1;
            Set trackIDs1;
            Set edges;
            final Model model;
            int j;
            ArrayList dataListSpot;
            int jx;
            if (FirstWizardPanel.spotEnable.equals("spotEnable") == Boolean.TRUE) {
               model = ProcessTrackMateXml.trackmate.getModel();
               fm1 = model.getFeatureModel();
               trackIDs1 = model.getTrackModel().trackIDs(true);
               Collection<String> spotFeatures = ProcessTrackMateXml.trackmate.getModel().getFeatureModel().getSpotFeatures();
               ResultsTable spotTable = new ResultsTable();
               Iterator var66 = trackIDs1.iterator();

               ArrayList stringsSpot;
               while(var66.hasNext()) {
                  Integer trackIDx = (Integer)var66.next();
                  edges = model.getTrackModel().trackSpots(trackIDx);
                  stringsSpot = new ArrayList(edges);
                  Collections.sort(stringsSpot, Spot.frameComparator);
                  Iterator var70 = stringsSpot.iterator();

                  while(var70.hasNext()) {
                     Spot spotx = (Spot)var70.next();
                     spotTable.incrementCounter();
                     spotTable.addLabel(spotx.getName());
                     spotTable.addValue("ID", "" + spotx.ID());
                     spotTable.addValue("TRACK_ID", "" + trackIDx);
                     Iterator var72 = spotFeatures.iterator();

                     while(var72.hasNext()) {
                        String featurex = (String)var72.next();
                        Double valx = spotx.getFeature(featurex);
                        if (valx == null) {
                           spotTable.addValue(featurex, "None");
                        } else if ((Boolean)fm1.getSpotFeatureIsInt().get(featurex)) {
                           spotTable.addValue(featurex, "" + valx.intValue());
                        } else {
                           spotTable.addValue(featurex, valx);
                        }
                     }
                  }
               }

               ProcessTrackMateXml.columnHeadersSpot = spotTable.getHeadings();
               j = spotTable.size();
               dataListSpot = new ArrayList();

               for(jx = 0; jx < j; ++jx) {
                  stringsSpot = new ArrayList();

                  for(int c = 0; c < ProcessTrackMateXml.columnHeadersSpot.length; ++c) {
                     String valuesSpot = spotTable.getStringValue(ProcessTrackMateXml.columnHeadersSpot[c], jx);
                     stringsSpot.add(valuesSpot);
                  }

                  dataListSpot.add(stringsSpot);
               }

               ProcessTrackMateXml.dataSpot = new String[dataListSpot.size()][];

               for(jx = 0; jx < ProcessTrackMateXml.dataSpot.length; ++jx) {
                  ProcessTrackMateXml.dataSpot[jx] = new String[((List)dataListSpot.get(jx)).size()];
               }

               for(jx = 0; jx < dataListSpot.size(); ++jx) {
                  for(int ux = 1; ux < ((List)dataListSpot.get(jx)).size(); ++ux) {
                     ProcessTrackMateXml.dataSpot[jx][ux] = (String)((List)dataListSpot.get(jx)).get(ux);
                  }
               }

               FirstWizardPanel.createSpotTable();
               FirstWizardPanel.tableSpot.addMouseListener(new MouseAdapter() {
                  public void mouseReleased(MouseEvent e) {
                     if (ProcessTrackMateXml.selectionModel != null && FirstWizardPanel.command == "enable" && FirstWizardPanel.command != null) {
                        ListSelectionModel lsm = FirstWizardPanel.tableSpot.getSelectionModel();
                        int selStart = lsm.getMinSelectionIndex();
                        int selEnd = lsm.getMaxSelectionIndex();
                        if (selStart < 0 || selEnd < 0) {
                           return;
                        }

                        int minLine = Math.min(selStart, selEnd);
                        int maxLine = Math.max(selStart, selEnd);
                        Set<Spot> spots = new HashSet();

                        for(int row = minLine; row <= maxLine; ++row) {
                           int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(row, 2));
                           Spot spot = ProcessTrackMateXml.this.totalSpots.search(spotID);
                           if (spot != null) {
                              spots.add(spot);
                           }
                        }

                        ProcessTrackMateXml.selectionModel.clearSelection();
                        ProcessTrackMateXml.selectionModel.addSpotToSelection(spots);
                     }

                  }
               });
            }

            if (ChooserWizardPanel.trackEnable.equals("trackEnable") == Boolean.TRUE) {
               ProcessTrackMateXml.FEATURES.add("TOTAL_DISTANCE_TRAVELED");
               ProcessTrackMateXml.FEATURES.add("MAX_DISTANCE_TRAVELED");
               ProcessTrackMateXml.FEATURES.add("CONFINMENT_RATIO");
               ProcessTrackMateXml.FEATURES.add("MEAN_STRAIGHT_LINE_SPEED");
               ProcessTrackMateXml.FEATURES.add("LINEARITY_OF_FORWARD_PROGRESSION");
               ProcessTrackMateXml.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_XY");
               ProcessTrackMateXml.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_YZ");
               ProcessTrackMateXml.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_ZX");
               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total distance traveled");
               ProcessTrackMateXml.FEATURE_NAMES.put("MAX_DISTANCE_TRAVELED", "Max distance traveled");
               ProcessTrackMateXml.FEATURE_NAMES.put("CONFINMENT_RATIO", "Confinment ratio");
               ProcessTrackMateXml.FEATURE_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean straight line speed");
               ProcessTrackMateXml.FEATURE_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Linearity of forward progression");
               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Absolute angle in xy plane");
               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Absolute angle in yz plane");
               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Absolute angle in zx plane");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total dist.");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("MAX_DISTANCE_TRAVELED", "Max dist.");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("CONFINMENT_RATIO", "Cnfnmnt ratio");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean v. line");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Lin. fwd. progr.");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Abs. angle xy");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Abs. angle yz");
               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Abs. angle zx");
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_DISTANCE_TRAVELED", Dimension.LENGTH);
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("MAX_DISTANCE_TRAVELED", Dimension.LENGTH);
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("CONFINMENT_RATIO", Dimension.NONE);
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("MEAN_STRAIGHT_LINE_SPEED", Dimension.VELOCITY);
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("LINEARITY_OF_FORWARD_PROGRESSION", Dimension.NONE);
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_XY", Dimension.ANGLE);
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_YZ", Dimension.ANGLE);
               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_ZX", Dimension.ANGLE);
               ProcessTrackMateXml.IS_INT.put("TOTAL_DISTANCE_TRAVELED", Boolean.FALSE);
               ProcessTrackMateXml.IS_INT.put("MAX_DISTANCE_TRAVELED", Boolean.FALSE);
               ProcessTrackMateXml.IS_INT.put("CONFINMENT_RATIO", Boolean.FALSE);
               ProcessTrackMateXml.IS_INT.put("MEAN_STRAIGHT_LINE_SPEED", Boolean.FALSE);
               ProcessTrackMateXml.IS_INT.put("LINEARITY_OF_FORWARD_PROGRESSION", Boolean.FALSE);
               ProcessTrackMateXml.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_XY", Boolean.FALSE);
               ProcessTrackMateXml.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_YZ", Boolean.FALSE);
               ProcessTrackMateXml.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_ZX", Boolean.FALSE);
               model = ProcessTrackMateXml.trackmate.getModel();
               fm1 = model.getFeatureModel();
               trackIDs1 = model.getTrackModel().trackIDs(true);
               ProcessTrackMateXml.this.trackTable = new ResultsTable();
               Iterator var139 = trackIDs1.iterator();

               while(var139.hasNext()) {
                  Integer trackID = (Integer)var139.next();
                  List<Spot> spots = new ArrayList(model.getTrackModel().trackSpots(trackID));
                  Collections.sort(spots, Spot.frameComparator);
                  Spot first = (Spot)spots.get(0);
                  edges = model.getTrackModel().trackEdges(trackID);
                  double totalDistance = 0.0D;
                  double maxDistanceSq = Double.NEGATIVE_INFINITY;
                  double maxDistance = 0.0D;
                  double dx = 0.0D;
                  double dy = 0.0D;
                  double dz = 0.0D;

                  Spot source;
                  Spot target;
                  double vMean;
                  double confinmentRatio;
                  for(Iterator var81 = edges.iterator(); var81.hasNext(); dz += target.getDoublePosition(2) - source.getDoublePosition(2)) {
                     DefaultWeightedEdge edge = (DefaultWeightedEdge)var81.next();
                     source = model.getTrackModel().getEdgeSource(edge);
                     target = model.getTrackModel().getEdgeTarget(edge);
                     vMean = Math.sqrt(source.squareDistanceTo(target));
                     totalDistance += vMean;
                     confinmentRatio = first.squareDistanceTo(target);
                     if (confinmentRatio > maxDistanceSq) {
                        maxDistanceSq = confinmentRatio;
                        maxDistance = Math.sqrt(confinmentRatio);
                     }

                     dx += target.getDoublePosition(0) - source.getDoublePosition(0);
                     dy += target.getDoublePosition(1) - source.getDoublePosition(1);
                  }

                  double netDistance = fm1.getTrackFeature(trackID, "TRACK_DISPLACEMENT");
                  double tTotal = fm1.getTrackFeature(trackID, "TRACK_DURATION");
                  vMean = fm1.getTrackFeature(trackID, "TRACK_MEAN_SPEED");
                  confinmentRatio = netDistance / totalDistance;
                  double meanStraightLineSpeed = netDistance / tTotal;
                  double linearityForwardProgression = meanStraightLineSpeed / vMean;
                  double angleXY = Math.atan2(dy, dx);
                  double angleYZ = Math.atan2(dz, dy);
                  double angleZX = Math.atan2(dx, dz);
                  Collection<String> trackFeatures = fm1.getTrackFeatures();
                  ProcessTrackMateXml.this.trackTable.incrementCounter();
                  ProcessTrackMateXml.this.trackTable.addLabel(model.getTrackModel().name(trackID));
                  ProcessTrackMateXml.this.trackTable.addValue("TRACK_ID", "" + trackID);
                  Iterator var100 = trackFeatures.iterator();

                  while(var100.hasNext()) {
                     String feature = (String)var100.next();
                     Double val = fm1.getTrackFeature(trackID, feature);
                     if (val == null) {
                        ProcessTrackMateXml.this.trackTable.addValue(feature, "None");
                     } else if ((Boolean)fm1.getTrackFeatureIsInt().get(feature)) {
                        ProcessTrackMateXml.this.trackTable.addValue(feature, "" + val.intValue());
                     } else {
                        ProcessTrackMateXml.this.trackTable.addValue(feature, val);
                     }
                  }

                  ProcessTrackMateXml.this.trackTable.addValue("TOTAL_DISTANCE_TRAVELED", "" + (double)Math.round(totalDistance * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("MAX_DISTANCE_TRAVELED", "" + (double)Math.round(maxDistance * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("MEAN_STRAIGHT_LINE_SPEED", "" + (double)Math.round(meanStraightLineSpeed * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("LINEARITY_OF_FORWARD_PROGRESSION", "" + (double)Math.round(linearityForwardProgression * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_XY", "" + (double)Math.round(angleXY * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_YZ", "" + (double)Math.round(angleYZ * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_ZX", "" + (double)Math.round(angleZX * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("CONFINMENT_RATIO", "" + (double)Math.round(confinmentRatio * 1000.0D) / 1000.0D);
                  ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "");
                  if (confinmentRatio == 0.0D) {
                     ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Total-Confined Track");
                  }

                  if (confinmentRatio == 1.0D) {
                     ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Perfectly Straight Track");
                  }

                  if (confinmentRatio > 0.0D && confinmentRatio <= 0.5D) {
                     ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Strongly Confined Track");
                  }

                  if (confinmentRatio > 0.05D && confinmentRatio <= 0.25D) {
                     ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Purely Random Track");
                  }

                  if (confinmentRatio > 0.25D && confinmentRatio < 1.0D) {
                     ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Fairly Straight Track");
                  }
               }

               ProcessTrackMateXml.columnHeadersTrack = ProcessTrackMateXml.this.trackTable.getHeadings();
               int rowsTrack = ProcessTrackMateXml.this.trackTable.size();
               List<List<String>> dataListTrack = new ArrayList();

               for(j = 0; j < rowsTrack; ++j) {
                  dataListSpot = new ArrayList();

                  for(jx = 0; jx < ProcessTrackMateXml.columnHeadersTrack.length; ++jx) {
                     String valuesTrack = ProcessTrackMateXml.this.trackTable.getStringValue(ProcessTrackMateXml.columnHeadersTrack[jx], j);
                     dataListSpot.add(valuesTrack);
                  }

                  dataListTrack.add(dataListSpot);
               }

               ProcessTrackMateXml.dataTrack = new String[dataListTrack.size()][];

               for(j = 0; j < ProcessTrackMateXml.dataTrack.length; ++j) {
                  ProcessTrackMateXml.dataTrack[j] = new String[((List)dataListTrack.get(j)).size()];
               }

               for(j = 0; j < dataListTrack.size(); ++j) {
                  for(int u = 1; u < ((List)dataListTrack.get(j)).size(); ++u) {
                     ProcessTrackMateXml.dataTrack[j][u] = (String)((List)dataListTrack.get(j)).get(u);
                  }
               }

               ChooserWizardPanel.createTrackTable();
               ChooserWizardPanel.tableTrack.addMouseListener(new MouseAdapter() {
                  public void mouseReleased(MouseEvent e) {
                     if (ProcessTrackMateXml.selectionModel != null && ChooserWizardPanel.command == "enable" && ChooserWizardPanel.command != null) {
                        ListSelectionModel lsm = ChooserWizardPanel.tableTrack.getSelectionModel();
                        int selStart = lsm.getMinSelectionIndex();
                        int selEnd = lsm.getMaxSelectionIndex();
                        if (selStart < 0 || selEnd < 0) {
                           return;
                        }

                        int minLine = Math.min(selStart, selEnd);
                        int maxLine = Math.max(selStart, selEnd);
                        Set<DefaultWeightedEdge> edges = new HashSet();
                        Set<Spot> spots = new HashSet();

                        for(int row = minLine; row <= maxLine; ++row) {
                           int trackID = Integer.parseInt((String)ChooserWizardPanel.tableTrack.getValueAt(row, 2));
                           spots.addAll(model.getTrackModel().trackSpots(trackID));
                           edges.addAll(model.getTrackModel().trackEdges(trackID));
                        }

                        ProcessTrackMateXml.selectionModel.clearSelection();
                        ProcessTrackMateXml.selectionModel.addSpotToSelection(spots);
                        ProcessTrackMateXml.selectionModel.addEdgeToSelection(edges);
                     }

                  }
               });
            }

         }
      });
      this.mainProcess.start();
   }

   private static void transferCalibration(ImagePlus from, ImagePlus to) {
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

   private final TablePanel<Spot> createSpotTableRT(Model model, DisplaySettings ds) {
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
}
