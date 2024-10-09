/*      */ import fiji.plugin.trackmate.Dimension;
/*      */ import fiji.plugin.trackmate.FeatureModel;
/*      */ import fiji.plugin.trackmate.Model;
/*      */ import fiji.plugin.trackmate.SelectionModel;
/*      */ import fiji.plugin.trackmate.Settings;
/*      */ import fiji.plugin.trackmate.Spot;
/*      */ import fiji.plugin.trackmate.SpotCollection;
/*      */ import fiji.plugin.trackmate.TrackMate;
/*      */ import fiji.plugin.trackmate.detection.DogDetectorFactory;
/*      */ import fiji.plugin.trackmate.detection.LogDetectorFactory;
/*      */ import fiji.plugin.trackmate.detection.ManualDetectorFactory;
/*      */ import fiji.plugin.trackmate.detection.SpotDetectorFactoryBase;
/*      */ import fiji.plugin.trackmate.features.FeatureFilter;
/*      */ import fiji.plugin.trackmate.features.FeatureUtils;
/*      */ import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings;
/*      */ import fiji.plugin.trackmate.gui.displaysettings.DisplaySettingsIO;
/*      */ import fiji.plugin.trackmate.io.TmXmlReader;
/*      */ import fiji.plugin.trackmate.tracking.LAPUtils;
/*      */ import fiji.plugin.trackmate.tracking.ManualTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.SpotTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.kalman.KalmanTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.sparselap.SimpleSparseLAPTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.sparselap.SparseLAPTrackerFactory;
/*      */ import fiji.plugin.trackmate.util.TMUtils;
/*      */ import fiji.plugin.trackmate.visualization.FeatureColorGenerator;
/*      */ import fiji.plugin.trackmate.visualization.hyperstack.HyperStackDisplayer;
/*      */ import fiji.plugin.trackmate.visualization.table.TablePanel;
/*      */ import ij.ImagePlus;
/*      */ import ij.ImageStack;
/*      */ import ij.WindowManager;
/*      */ import ij.measure.Calibration;
/*      */ import ij.measure.ResultsTable;
/*      */ import ij.process.ColorProcessor;
/*      */ import ij.process.ImageProcessor;
/*      */ import java.awt.Color;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.function.BiConsumer;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.xpath.XPath;
/*      */ import javax.xml.xpath.XPathConstants;
/*      */ import javax.xml.xpath.XPathExpression;
/*      */ import javax.xml.xpath.XPathExpressionException;
/*      */ import javax.xml.xpath.XPathFactory;
/*      */ import net.imglib2.RealLocalizable;
/*      */ import org.jgrapht.graph.DefaultWeightedEdge;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ProcessTrackMateXml
/*      */ {
/*      */   Thread mainProcess;
/*      */   String zstart;
/*      */   String zend;
/*      */   String ystart;
/*      */   String yend;
/*      */   String xstart;
/*      */   String xend;
/*      */   String tstart;
/*      */   String tend;
/*      */   String RADIUS;
/*      */   String THRESHOLD;
/*      */   String TARGET_CHANNEL;
/*      */   String DO_SUBPIXEL_LOCALIZATION;
/*      */   String DO_MEDIAN_FILTERING;
/*      */   String DETECTOR_NAME;
/*      */   String NSPLIT;
/*      */   String DOWNSAMPLE_FACTOR;
/*      */   String initialSpotFilter;
/*  108 */   ImagePlus[] imps = FirstWizardPanel.imps;
/*      */   
/*      */   String TRACKER_NAME;
/*      */   
/*      */   String CUTOFF_PERCENTILE;
/*      */   
/*      */   String ALTERNATIVE_LINKING_COST_FACTOR;
/*      */   
/*      */   String LINKING_MAX_DISTANCE;
/*      */   
/*      */   String MAX_FRAME_GAP;
/*      */   
/*      */   String MAX_DISTANCE;
/*      */   
/*      */   String ALLOW_GAP_CLOSING;
/*      */   
/*      */   String SPLITTING_MAX_DISTANCE;
/*      */   String ALLOW_TRACK_SPLITTING;
/*      */   String MERGING_MAX_DISTANCE;
/*      */   String ALLOW_TRACK_MERGING;
/*      */   String BLOCKING_VALUE;
/*      */   static Model model;
/*      */   static Settings settings;
/*      */   static SelectionModel selectionModel;
/*      */   static TrackMate trackmate;
/*      */   SpotCollection totalSpots;
/*      */   static HyperStackDisplayer displayer;
/*      */   ImagePlus impAnal;
/*      */   static boolean tracksVisible;
/*      */   static boolean spotsVisible;
/*      */   static String[][] dataSpot;
/*      */   static String[][] dataTrack;
/*      */   static String[] columnHeadersSpot;
/*      */   static String[] columnHeadersTrack;
/*      */   static DisplaySettings ds;
/*      */   public static final String NAME = "Export statistics to tables";
/*      */   public static final String KEY = "EXPORT_STATS_TO_IJ";
/*      */   public static final String INFO_TEXT = "<html>Compute and export all statistics to 3 ImageJ results table. Statistisc are separated in features computed for: <ol> \t<li> spots in filtered tracks; \t<li> links between those spots; \t<li> filtered tracks. </ol> For tracks and links, they are recalculated prior to exporting. Note that spots and links that are not in a filtered tracks are not part of this export.</html>";
/*      */   private static final String SPOT_TABLE_NAME = "Spots in tracks statistics";
/*      */   private static final String EDGE_TABLE_NAME = "Links in tracks statistics";
/*      */   private static final String TRACK_TABLE_NAME = "Track statistics";
/*      */   private static final String ID_COLUMN = "ID";
/*      */   private static final String TRACK_ID_COLUMN = "TRACK_ID";
/*      */   private ResultsTable spotTable;
/*      */   private ResultsTable edgeTable;
/*      */   private ResultsTable trackTable;
/*      */   public static final String KEYLINEAR = "Linear track analysis";
/*      */   public static final String TRACK_TOTAL_DISTANCE_TRAVELED = "TOTAL_DISTANCE_TRAVELED";
/*      */   public static final String TRACK_MAX_DISTANCE_TRAVELED = "MAX_DISTANCE_TRAVELED";
/*      */   public static final String TRACK_CONFINMENT_RATIO = "CONFINMENT_RATIO";
/*      */   public static final String TRACK_MEAN_STRAIGHT_LINE_SPEED = "MEAN_STRAIGHT_LINE_SPEED";
/*      */   public static final String TRACK_LINEARITY_OF_FORWARD_PROGRESSION = "LINEARITY_OF_FORWARD_PROGRESSION";
/*      */   public static final String TOTAL_ABSOLUTE_ANGLE_XY = "TOTAL_ABSOLUTE_ANGLE_XY";
/*      */   public static final String TOTAL_ABSOLUTE_ANGLE_YZ = "TOTAL_ABSOLUTE_ANGLE_YZ";
/*      */   public static final String TOTAL_ABSOLUTE_ANGLE_ZX = "TOTAL_ABSOLUTE_ANGLE_ZX";
/*  163 */   public static final List<String> FEATURES = new ArrayList<>(9);
/*      */   
/*  165 */   public static final Map<String, String> FEATURE_NAMES = new HashMap<>(9);
/*      */   
/*  167 */   public static final Map<String, String> FEATURE_SHORT_NAMES = new HashMap<>(9);
/*      */   
/*  169 */   public static final Map<String, Dimension> FEATURE_DIMENSIONS = new HashMap<>(9);
/*      */   
/*  171 */   public static final Map<String, Boolean> IS_INT = new HashMap<>(9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processTrackMateXml() {
/*  178 */     this.mainProcess = new Thread(new Runnable()
/*      */         {
/*      */ 
/*      */           
/*      */           public void run()
/*      */           {
/*  184 */             File fileXML = new File(TrackAnalyzer_.xmlPath);
/*  185 */             List<ImagePlus> impAnalClose = new ArrayList<>();
/*  186 */             int[] IDs = WindowManager.getIDList();
/*  187 */             if (IDs != null)
/*  188 */               for (int i1 = 0; i1 < IDs.length; i1++) {
/*  189 */                 impAnalClose.add(WindowManager.getImage(IDs[i1]));
/*      */               } 
/*  191 */             if (FirstWizardPanel.spotEnable.equals("spotEnable") == Boolean.TRUE.booleanValue() && 
/*  192 */               FirstWizardPanel.tableImages.getSelectedRow() != -1) {
/*  193 */               if (IDs != null)
/*  194 */                 for (int i1 = 0; i1 < IDs.length; i1++)
/*  195 */                   ((ImagePlus)impAnalClose.get(i1)).hide();  
/*  196 */               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[FirstWizardPanel.tableImages.getSelectedRow()];
/*      */             } 
/*  198 */             if (FirstWizardPanel.spotEnable.equals("spotEnable") == Boolean.TRUE.booleanValue() && 
/*  199 */               FirstWizardPanel.tableImages.getSelectedRow() == -1)
/*  200 */               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[ChooserWizardPanel.tableImages.getSelectedRow()]; 
/*  201 */             if (ChooserWizardPanel.trackEnable.equals("trackEnable") == Boolean.TRUE.booleanValue() && 
/*  202 */               ChooserWizardPanel.tableImages.getSelectedRow() != -1) {
/*  203 */               if (IDs != null)
/*  204 */                 for (int i1 = 0; i1 < IDs.length; i1++) {
/*  205 */                   ((ImagePlus)impAnalClose.get(i1)).hide();
/*      */                 } 
/*  207 */               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[ChooserWizardPanel.tableImages.getSelectedRow()];
/*      */             } 
/*  209 */             if (ChooserWizardPanel.trackEnable.equals("trackEnable") == Boolean.TRUE.booleanValue() && 
/*  210 */               ChooserWizardPanel.tableImages.getSelectedRow() == -1) {
/*  211 */               ProcessTrackMateXml.this.impAnal = ProcessTrackMateXml.this.imps[FirstWizardPanel.tableImages.getSelectedRow()];
/*      */             }
/*  213 */             ProcessTrackMateXml.this.impAnal.show();
/*  214 */             int[] dims = ProcessTrackMateXml.this.impAnal.getDimensions();
/*      */             
/*  216 */             if (dims[4] == 1 && dims[3] > 1) {
/*      */               
/*  218 */               ProcessTrackMateXml.this.impAnal.setDimensions(dims[2], dims[4], dims[3]);
/*  219 */               Calibration calibration = ProcessTrackMateXml.this.impAnal.getCalibration();
/*  220 */               calibration.frameInterval = 1.0D;
/*      */             } 
/*      */ 
/*      */             
/*  224 */             TmXmlReader reader = new TmXmlReader(fileXML);
/*  225 */             DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
/*  226 */             DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  227 */             DocumentBuilder builder = null;
/*      */             try {
/*  229 */               builder = factory.newDocumentBuilder();
/*  230 */             } catch (ParserConfigurationException e) {
/*      */               
/*  232 */               e.printStackTrace();
/*      */             } 
/*  234 */             Document doc = null;
/*      */             try {
/*  236 */               doc = builder.parse(TrackAnalyzer_.xmlPath);
/*  237 */             } catch (SAXException e) {
/*      */               
/*  239 */               e.printStackTrace();
/*  240 */             } catch (IOException e) {
/*      */               
/*  242 */               e.printStackTrace();
/*      */             } 
/*  244 */             XPathFactory xPathfactory = XPathFactory.newInstance();
/*  245 */             XPath xpath = xPathfactory.newXPath();
/*      */             
/*  247 */             XPathExpression exprBasicSettings = null;
/*  248 */             XPathExpression exprDetectorSettings = null;
/*  249 */             XPathExpression exprInitialSpotFilter = null;
/*  250 */             XPathExpression exprFilter = null;
/*  251 */             XPathExpression exprTrackerSettings = null;
/*  252 */             XPathExpression exprLinking = null;
/*  253 */             XPathExpression exprGapClosing = null;
/*  254 */             XPathExpression exprSplitting = null;
/*  255 */             XPathExpression exprMerging = null;
/*  256 */             XPathExpression exprTrackFilter = null;
/*  257 */             XPathExpression exprLinkingP = null;
/*      */ 
/*      */             
/*      */             try {
/*  261 */               exprBasicSettings = xpath.compile("//Settings/BasicSettings[@zstart]");
/*  262 */             } catch (XPathExpressionException e) {
/*      */               
/*  264 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  268 */               exprLinkingP = xpath.compile("//Linking/FeaturePenalties[@MEAN_INTENSITY]");
/*  269 */             } catch (XPathExpressionException e) {
/*      */               
/*  271 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  275 */               exprDetectorSettings = xpath.compile("//Settings/DetectorSettings[@RADIUS]");
/*  276 */             } catch (XPathExpressionException e) {
/*      */               
/*  278 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  282 */               exprInitialSpotFilter = xpath.compile("//Settings/InitialSpotFilter[@feature]");
/*  283 */             } catch (XPathExpressionException e) {
/*      */               
/*  285 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  289 */               exprFilter = xpath.compile("//SpotFilterCollection/Filter[@feature]");
/*  290 */             } catch (XPathExpressionException e) {
/*      */               
/*  292 */               e.printStackTrace();
/*      */             } 
/*      */ 
/*      */             
/*      */             try {
/*  297 */               exprTrackerSettings = xpath.compile("//Settings/TrackerSettings[@TRACKER_NAME]");
/*  298 */             } catch (XPathExpressionException e) {
/*      */               
/*  300 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  304 */               exprLinking = xpath.compile("//TrackerSettings/Linking[@LINKING_MAX_DISTANCE]");
/*  305 */             } catch (XPathExpressionException e) {
/*      */               
/*  307 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  311 */               exprGapClosing = xpath.compile("//TrackerSettings/GapClosing[@MAX_FRAME_GAP]");
/*  312 */             } catch (XPathExpressionException e) {
/*      */               
/*  314 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  318 */               exprSplitting = xpath
/*  319 */                 .compile("//TrackerSettings/TrackSplitting[@SPLITTING_MAX_DISTANCE]");
/*  320 */             } catch (XPathExpressionException e) {
/*      */               
/*  322 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  326 */               exprMerging = xpath
/*  327 */                 .compile("//TrackerSettings/TrackMerging[@MERGING_MAX_DISTANCE]");
/*  328 */             } catch (XPathExpressionException e) {
/*      */               
/*  330 */               e.printStackTrace();
/*      */             } 
/*      */             
/*      */             try {
/*  334 */               exprTrackFilter = xpath.compile("//TrackFilterCollection/Filter[@feature]");
/*  335 */             } catch (XPathExpressionException e) {
/*      */               
/*  337 */               e.printStackTrace();
/*      */             } 
/*      */             
/*  340 */             NodeList nlBasicSettings = null;
/*  341 */             NodeList nlDetectorSettings = null;
/*  342 */             NodeList nlInitialSpotFilter = null;
/*  343 */             NodeList nlFilter = null;
/*  344 */             NodeList nlTrackerSettings = null;
/*  345 */             NodeList nlLinking = null;
/*  346 */             NodeList nlGapClosing = null;
/*  347 */             NodeList nlSplitting = null;
/*  348 */             NodeList nlMerging = null;
/*  349 */             NodeList nlTrackFilter = null;
/*  350 */             NodeList nlLinkingP = null;
/*      */             
/*      */             try {
/*  353 */               nlBasicSettings = (NodeList)exprBasicSettings.evaluate(doc, XPathConstants.NODESET);
/*  354 */               nlDetectorSettings = (NodeList)exprDetectorSettings.evaluate(doc, XPathConstants.NODESET);
/*  355 */               nlInitialSpotFilter = (NodeList)exprInitialSpotFilter.evaluate(doc, XPathConstants.NODESET);
/*  356 */               nlFilter = (NodeList)exprFilter.evaluate(doc, XPathConstants.NODESET);
/*  357 */               nlTrackerSettings = (NodeList)exprTrackerSettings.evaluate(doc, XPathConstants.NODESET);
/*  358 */               nlLinking = (NodeList)exprLinking.evaluate(doc, XPathConstants.NODESET);
/*  359 */               nlGapClosing = (NodeList)exprGapClosing.evaluate(doc, XPathConstants.NODESET);
/*  360 */               nlSplitting = (NodeList)exprSplitting.evaluate(doc, XPathConstants.NODESET);
/*  361 */               nlMerging = (NodeList)exprMerging.evaluate(doc, XPathConstants.NODESET);
/*  362 */               nlTrackFilter = (NodeList)exprTrackFilter.evaluate(doc, XPathConstants.NODESET);
/*  363 */               nlLinkingP = (NodeList)exprLinkingP.evaluate(doc, XPathConstants.NODESET);
/*      */             }
/*  365 */             catch (XPathExpressionException e) {
/*      */               
/*  367 */               e.printStackTrace();
/*      */             } 
/*      */             
/*  370 */             for (int i = 0; i < nlBasicSettings.getLength(); i++) {
/*  371 */               Node node = nlBasicSettings.item(i);
/*  372 */               ProcessTrackMateXml.this.zstart = node.getAttributes().getNamedItem("zstart").getNodeValue();
/*  373 */               ProcessTrackMateXml.this.zend = node.getAttributes().getNamedItem("zend").getNodeValue();
/*  374 */               ProcessTrackMateXml.this.ystart = node.getAttributes().getNamedItem("ystart").getNodeValue();
/*  375 */               ProcessTrackMateXml.this.yend = node.getAttributes().getNamedItem("yend").getNodeValue();
/*  376 */               ProcessTrackMateXml.this.xstart = node.getAttributes().getNamedItem("xstart").getNodeValue();
/*  377 */               ProcessTrackMateXml.this.xend = node.getAttributes().getNamedItem("xend").getNodeValue();
/*  378 */               ProcessTrackMateXml.this.tstart = node.getAttributes().getNamedItem("tstart").getNodeValue();
/*  379 */               ProcessTrackMateXml.this.tend = node.getAttributes().getNamedItem("tend").getNodeValue();
/*      */             } 
/*  381 */             Node currentItem = null;
/*  382 */             for (int j = 0; j < nlDetectorSettings.getLength(); j++) {
/*  383 */               currentItem = nlDetectorSettings.item(j);
/*  384 */               ProcessTrackMateXml.this.RADIUS = currentItem.getAttributes().getNamedItem("RADIUS").getNodeValue();
/*  385 */               ProcessTrackMateXml.this.THRESHOLD = currentItem.getAttributes().getNamedItem("THRESHOLD").getNodeValue();
/*  386 */               ProcessTrackMateXml.this.TARGET_CHANNEL = currentItem.getAttributes().getNamedItem("TARGET_CHANNEL").getNodeValue();
/*  387 */               ProcessTrackMateXml.this.DO_SUBPIXEL_LOCALIZATION = currentItem.getAttributes().getNamedItem("DO_SUBPIXEL_LOCALIZATION")
/*  388 */                 .getNodeValue();
/*  389 */               ProcessTrackMateXml.this.DO_MEDIAN_FILTERING = currentItem.getAttributes().getNamedItem("DO_MEDIAN_FILTERING")
/*  390 */                 .getNodeValue();
/*  391 */               ProcessTrackMateXml.this.DETECTOR_NAME = currentItem.getAttributes().getNamedItem("DETECTOR_NAME").getNodeValue();
/*  392 */               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("BLOCK_LOG_DETECTOR"))
/*  393 */                 ProcessTrackMateXml.this.NSPLIT = currentItem.getAttributes().getNamedItem("NSPLIT").getNodeValue(); 
/*  394 */               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("DOWNSAMLE_LOG_DETECTOR")) {
/*  395 */                 ProcessTrackMateXml.this.DOWNSAMPLE_FACTOR = currentItem.getAttributes().getNamedItem("DOWNSAMPLE_FACTOR")
/*  396 */                   .getNodeValue();
/*      */               }
/*      */             } 
/*      */             
/*  400 */             String linkingNames = null;
/*  401 */             String linkingValues = null; int k;
/*  402 */             for (k = 0; k < nlLinkingP.getLength(); k++) {
/*  403 */               linkingNames = nlLinkingP.item(k).getAttributes().item(k).getNodeName();
/*  404 */               linkingValues = nlLinkingP.item(k).getAttributes().item(k).getNodeValue();
/*      */             } 
/*  406 */             for (k = 0; k < nlInitialSpotFilter.getLength(); k++) {
/*  407 */               currentItem = nlInitialSpotFilter.item(k);
/*  408 */               ProcessTrackMateXml.this.initialSpotFilter = currentItem.getAttributes().getNamedItem("value").getNodeValue();
/*      */             } 
/*      */             
/*  411 */             String initialFilterFeature = null;
/*  412 */             String initialFilterValue = null;
/*  413 */             String initialFilterAbove = null;
/*  414 */             for (int m = 0; m < nlFilter.getLength(); m++) {
/*  415 */               currentItem = nlFilter.item(m);
/*  416 */               initialFilterFeature = currentItem.getAttributes().getNamedItem("feature").getNodeValue();
/*  417 */               initialFilterValue = currentItem.getAttributes().getNamedItem("value").getNodeValue();
/*  418 */               initialFilterAbove = currentItem.getAttributes().getNamedItem("isabove").getNodeValue();
/*      */             } 
/*      */ 
/*      */             
/*  422 */             String initialTrackFilterFeature = null;
/*  423 */             String initialTrackFilterValue = null;
/*  424 */             String initialTrackFilterAbove = null;
/*      */             int n;
/*  426 */             for (n = 0; n < nlTrackerSettings.getLength(); n++) {
/*  427 */               currentItem = nlTrackerSettings.item(n);
/*  428 */               ProcessTrackMateXml.this.TRACKER_NAME = currentItem.getAttributes().getNamedItem("TRACKER_NAME").getNodeValue();
/*  429 */               ProcessTrackMateXml.this.CUTOFF_PERCENTILE = currentItem.getAttributes().getNamedItem("CUTOFF_PERCENTILE").getNodeValue();
/*  430 */               ProcessTrackMateXml.this.BLOCKING_VALUE = currentItem.getAttributes().getNamedItem("BLOCKING_VALUE").getNodeValue();
/*  431 */               ProcessTrackMateXml.this.ALTERNATIVE_LINKING_COST_FACTOR = currentItem.getAttributes()
/*  432 */                 .getNamedItem("ALTERNATIVE_LINKING_COST_FACTOR").getNodeValue();
/*      */             } 
/*  434 */             for (n = 0; n < nlLinking.getLength(); n++) {
/*  435 */               currentItem = nlLinking.item(n);
/*  436 */               ProcessTrackMateXml.this.LINKING_MAX_DISTANCE = currentItem.getAttributes().getNamedItem("LINKING_MAX_DISTANCE")
/*  437 */                 .getNodeValue();
/*      */             } 
/*  439 */             for (n = 0; n < nlGapClosing.getLength(); n++) {
/*  440 */               currentItem = nlGapClosing.item(n);
/*  441 */               ProcessTrackMateXml.this.MAX_FRAME_GAP = currentItem.getAttributes().getNamedItem("MAX_FRAME_GAP").getNodeValue();
/*  442 */               ProcessTrackMateXml.this.MAX_DISTANCE = currentItem.getAttributes().getNamedItem("GAP_CLOSING_MAX_DISTANCE").getNodeValue();
/*  443 */               ProcessTrackMateXml.this.ALLOW_GAP_CLOSING = currentItem.getAttributes().getNamedItem("ALLOW_GAP_CLOSING").getNodeValue();
/*      */             } 
/*      */             
/*  446 */             for (n = 0; n < nlSplitting.getLength(); n++) {
/*  447 */               currentItem = nlSplitting.item(n);
/*  448 */               ProcessTrackMateXml.this.SPLITTING_MAX_DISTANCE = currentItem.getAttributes().getNamedItem("SPLITTING_MAX_DISTANCE")
/*  449 */                 .getNodeValue();
/*  450 */               ProcessTrackMateXml.this.ALLOW_TRACK_SPLITTING = currentItem.getAttributes().getNamedItem("ALLOW_TRACK_SPLITTING")
/*  451 */                 .getNodeValue();
/*      */             } 
/*  453 */             for (n = 0; n < nlMerging.getLength(); n++) {
/*  454 */               currentItem = nlMerging.item(n);
/*  455 */               ProcessTrackMateXml.this.MERGING_MAX_DISTANCE = currentItem.getAttributes().getNamedItem("MERGING_MAX_DISTANCE")
/*  456 */                 .getNodeValue();
/*  457 */               ProcessTrackMateXml.this.ALLOW_TRACK_MERGING = currentItem.getAttributes().getNamedItem("ALLOW_TRACK_MERGING")
/*  458 */                 .getNodeValue();
/*      */             } 
/*      */             
/*  461 */             ProcessTrackMateXml.settings = new Settings(ProcessTrackMateXml.this.impAnal);
/*      */             
/*  463 */             ProcessTrackMateXml.settings.dt = 0.05D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  481 */             if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("LOG_DETECTOR")) {
/*      */               
/*  483 */               ProcessTrackMateXml.settings.detectorFactory = (SpotDetectorFactoryBase)new LogDetectorFactory();
/*  484 */               ProcessTrackMateXml.settings.detectorSettings = ProcessTrackMateXml.settings.detectorFactory.getDefaultSettings();
/*  485 */               ProcessTrackMateXml.settings.detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", 
/*  486 */                   Boolean.valueOf(Boolean.parseBoolean(ProcessTrackMateXml.this.DO_SUBPIXEL_LOCALIZATION)));
/*  487 */               ProcessTrackMateXml.settings.detectorSettings.put("RADIUS", Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.RADIUS)));
/*  488 */               ProcessTrackMateXml.settings.detectorSettings.put("TARGET_CHANNEL", Integer.valueOf(Integer.parseInt(ProcessTrackMateXml.this.TARGET_CHANNEL)));
/*  489 */               ProcessTrackMateXml.settings.detectorSettings.put("THRESHOLD", Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.THRESHOLD)));
/*  490 */               ProcessTrackMateXml.settings.detectorSettings.put("DO_MEDIAN_FILTERING", 
/*  491 */                   Boolean.valueOf(Boolean.parseBoolean(ProcessTrackMateXml.this.DO_MEDIAN_FILTERING)));
/*  492 */               if (ProcessTrackMateXml.this.initialSpotFilter != null) {
/*  493 */                 ProcessTrackMateXml.settings.initialSpotFilterValue = Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.initialSpotFilter));
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  508 */               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("MANUAL_DETECTOR")) {
/*      */                 
/*  510 */                 ProcessTrackMateXml.settings.detectorFactory = (SpotDetectorFactoryBase)new ManualDetectorFactory();
/*  511 */                 ProcessTrackMateXml.settings.detectorSettings.put("RADIUS", Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.RADIUS)));
/*  512 */                 if (ProcessTrackMateXml.this.initialSpotFilter != null) {
/*  513 */                   ProcessTrackMateXml.settings.initialSpotFilterValue = Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.initialSpotFilter));
/*      */                 }
/*      */               } 
/*      */               
/*  517 */               if (ProcessTrackMateXml.this.DETECTOR_NAME.equals("DOG_DETECTOR")) {
/*  518 */                 ProcessTrackMateXml.settings.detectorFactory = (SpotDetectorFactoryBase)new DogDetectorFactory();
/*  519 */                 ProcessTrackMateXml.settings.detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", 
/*  520 */                     Boolean.valueOf(Boolean.parseBoolean(ProcessTrackMateXml.this.DO_SUBPIXEL_LOCALIZATION)));
/*  521 */                 ProcessTrackMateXml.settings.detectorSettings.put("RADIUS", Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.RADIUS)));
/*  522 */                 ProcessTrackMateXml.settings.detectorSettings.put("TARGET_CHANNEL", 
/*  523 */                     Integer.valueOf(Integer.parseInt(ProcessTrackMateXml.this.TARGET_CHANNEL)));
/*  524 */                 ProcessTrackMateXml.settings.detectorSettings.put("THRESHOLD", Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.THRESHOLD)));
/*  525 */                 ProcessTrackMateXml.settings.detectorSettings.put("DO_MEDIAN_FILTERING", 
/*  526 */                     Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.DO_MEDIAN_FILTERING)));
/*  527 */                 if (ProcessTrackMateXml.this.initialSpotFilter != null) {
/*  528 */                   ProcessTrackMateXml.settings.initialSpotFilterValue = Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.initialSpotFilter));
/*      */                 }
/*      */               } 
/*      */               
/*  532 */               if (initialFilterFeature != null) {
/*      */                 
/*  534 */                 FeatureFilter filter1 = new FeatureFilter(initialFilterFeature, 
/*  535 */                     Double.parseDouble(initialFilterValue), Boolean.parseBoolean(initialFilterAbove));
/*      */                 
/*  537 */                 ProcessTrackMateXml.settings.addSpotFilter(filter1);
/*      */               } 
/*      */             } 
/*      */ 
/*      */             
/*  542 */             if (ProcessTrackMateXml.this.TRACKER_NAME.equals("MANUAL_TRACKER")) {
/*      */               
/*  544 */               ProcessTrackMateXml.settings.trackerFactory = (SpotTrackerFactory)new ManualTrackerFactory();
/*  545 */               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*      */             } 
/*      */             
/*  548 */             if (ProcessTrackMateXml.this.TRACKER_NAME.equals("MANUAL_TRACKER")) {
/*  549 */               ProcessTrackMateXml.settings.trackerFactory = (SpotTrackerFactory)new ManualTrackerFactory();
/*  550 */               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*      */             } 
/*      */ 
/*      */             
/*  554 */             if (ProcessTrackMateXml.this.TRACKER_NAME.equals("KALMAN_TRACKER")) {
/*      */               
/*  556 */               ProcessTrackMateXml.settings.trackerFactory = (SpotTrackerFactory)new KalmanTrackerFactory();
/*  557 */               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*  558 */               ProcessTrackMateXml.settings.trackerSettings.put("KALMAN_SEARCH_RADIUS", 
/*  559 */                   Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.RADIUS)));
/*      */             } 
/*      */ 
/*      */             
/*  563 */             if (ProcessTrackMateXml.this.TRACKER_NAME.equals("SIMPLE_SPARSE_LAP_TRACKER")) {
/*      */               
/*  565 */               ProcessTrackMateXml.settings.trackerFactory = (SpotTrackerFactory)new SimpleSparseLAPTrackerFactory();
/*  566 */               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*  567 */               ProcessTrackMateXml.settings.trackerSettings.put("LINKING_MAX_DISTANCE", 
/*  568 */                   Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.LINKING_MAX_DISTANCE)));
/*  569 */               ProcessTrackMateXml.settings.trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", 
/*  570 */                   Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.MAX_DISTANCE)));
/*  571 */               ProcessTrackMateXml.settings.trackerSettings.put("MAX_FRAME_GAP", 
/*  572 */                   Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.MAX_FRAME_GAP)));
/*      */             } 
/*      */ 
/*      */             
/*  576 */             if (ProcessTrackMateXml.this.TRACKER_NAME.equals("SPARSE_LAP_TRACKER")) {
/*      */               
/*  578 */               ProcessTrackMateXml.settings.trackerFactory = (SpotTrackerFactory)new SparseLAPTrackerFactory();
/*  579 */               ProcessTrackMateXml.settings.trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*  580 */               ProcessTrackMateXml.settings.trackerSettings.put("LINKING_MAX_DISTANCE", 
/*  581 */                   Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.LINKING_MAX_DISTANCE)));
/*  582 */               Map<String, Double> linkingPenalty = 
/*  583 */                 (Map<String, Double>)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/*      */                     }
/*  585 */                   }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */               
/*  587 */               ProcessTrackMateXml.settings.trackerSettings.put("ALLOW_GAP_CLOSING", 
/*  588 */                   Boolean.valueOf(Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_GAP_CLOSING)));
/*  589 */               if (Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_GAP_CLOSING)) {
/*  590 */                 ProcessTrackMateXml.settings.trackerSettings.put("MAX_FRAME_GAP", 
/*  591 */                     Integer.valueOf(Integer.parseInt(ProcessTrackMateXml.this.MAX_FRAME_GAP)));
/*  592 */                 ProcessTrackMateXml.settings.trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", 
/*  593 */                     Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.MAX_DISTANCE)));
/*      */                 
/*  595 */                 Map map = 
/*  596 */                   (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/*  597 */                       } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */               } 
/*      */ 
/*      */               
/*  601 */               ProcessTrackMateXml.settings.trackerSettings.put("ALLOW_TRACK_SPLITTING", 
/*  602 */                   Boolean.valueOf(Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_SPLITTING)));
/*  603 */               if (Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_SPLITTING)) {
/*  604 */                 ProcessTrackMateXml.settings.trackerSettings.put("SPLITTING_MAX_DISTANCE", 
/*  605 */                     Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.SPLITTING_MAX_DISTANCE)));
/*  606 */                 Map map = 
/*  607 */                   (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/*  608 */                       } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */               } 
/*      */ 
/*      */               
/*  612 */               ProcessTrackMateXml.settings.trackerSettings.put("ALLOW_TRACK_MERGING", 
/*  613 */                   Boolean.valueOf(Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_MERGING)));
/*  614 */               if (Boolean.parseBoolean(ProcessTrackMateXml.this.ALLOW_TRACK_MERGING)) {
/*  615 */                 ProcessTrackMateXml.settings.trackerSettings.put("MERGING_MAX_DISTANCE", 
/*  616 */                     Double.valueOf(Double.parseDouble(ProcessTrackMateXml.this.MERGING_MAX_DISTANCE)));
/*  617 */                 Map map = 
/*  618 */                   (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/*  619 */                       } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  625 */             ProcessTrackMateXml.settings.addAllAnalyzers();
/*      */             
/*  627 */             ProcessTrackMateXml.model = new Model();
/*  628 */             ProcessTrackMateXml.trackmate = new TrackMate(ProcessTrackMateXml.model, ProcessTrackMateXml.settings);
/*      */             
/*  630 */             Boolean ok = null;
/*      */             
/*  632 */             ok = Boolean.valueOf(ProcessTrackMateXml.trackmate.checkInput());
/*  633 */             ok = Boolean.valueOf(ProcessTrackMateXml.trackmate.process());
/*      */             
/*  635 */             FeatureModel fm = ProcessTrackMateXml.model.getFeatureModel();
/*  636 */             Set<Integer> trackIDs = ProcessTrackMateXml.model.getTrackModel().trackIDs(true);
/*  637 */             Set<Spot> track = null;
/*  638 */             for (int id = 0; id < trackIDs.size(); id++) {
/*  639 */               Double v = fm.getTrackFeature(Integer.valueOf(id), "TRACK_MEAN_SPEED");
/*  640 */               track = ProcessTrackMateXml.model.getTrackModel().trackSpots(Integer.valueOf(id));
/*      */             } 
/*      */             
/*  643 */             for (Spot spot : track) {
/*  644 */               int sid = spot.ID();
/*  645 */               Double x = spot.getFeature("POSITION_X");
/*  646 */               Double y = spot.getFeature("POSITION_Y");
/*  647 */               Double t = spot.getFeature("FRAME");
/*  648 */               Double q = spot.getFeature("QUALITY");
/*  649 */               Double snr = spot.getFeature("SNR");
/*  650 */               Double double_1 = spot.getFeature("MEAN_INTENSITY");
/*      */             } 
/*  652 */             ProcessTrackMateXml.this.totalSpots = ProcessTrackMateXml.model.getSpots();
/*  653 */             ProcessTrackMateXml.displayer = null;
/*  654 */             ProcessTrackMateXml.selectionModel = new SelectionModel(ProcessTrackMateXml.model);
/*  655 */             ProcessTrackMateXml.ds = DisplaySettingsIO.readUserDefault();
/*  656 */             ProcessTrackMateXml.ds.setSpotShowName(true);
/*  657 */             ProcessTrackMateXml.ds.setSpotVisible(ProcessTrackMateXml.spotsVisible);
/*  658 */             ProcessTrackMateXml.ds.setSpotColorBy(DisplaySettings.TrackMateObject.TRACKS, "TRACK_INDEX");
/*  659 */             ProcessTrackMateXml.ds.setTrackVisible(ProcessTrackMateXml.tracksVisible);
/*  660 */             ProcessTrackMateXml.ds.setTrackColorBy(DisplaySettings.TrackMateObject.TRACKS, "TRACK_INDEX");
/*  661 */             ProcessTrackMateXml.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.FULL);
/*  662 */             ProcessTrackMateXml.displayer = new HyperStackDisplayer(ProcessTrackMateXml.model, ProcessTrackMateXml.selectionModel, ProcessTrackMateXml.this.impAnal, ProcessTrackMateXml.ds);
/*  663 */             ProcessTrackMateXml.displayer.render();
/*  664 */             ProcessTrackMateXml.displayer.refresh();
/*      */             
/*  666 */             Rectangle bounds = null;
/*  667 */             Integer firstFrame = null;
/*  668 */             Integer lastFrame = null;
/*  669 */             Integer width = null;
/*  670 */             Integer height = null;
/*  671 */             Integer nCaptures = null;
/*  672 */             ImageStack stack = null;
/*  673 */             Integer channel = null;
/*  674 */             Integer slice = null;
/*  675 */             BufferedImage bi = null;
/*  676 */             ColorProcessor cp = null;
/*  677 */             Integer index = null;
/*  678 */             ImagePlus capture = null;
/*      */             
/*  680 */             if (ProcessTrackMateXml.this.impAnal.getNFrames() > 1) {
/*  681 */               firstFrame = Integer.valueOf(Math.max(1, Math.min(ProcessTrackMateXml.this.impAnal.getNFrames(), 1)));
/*  682 */               lastFrame = Integer.valueOf(Math.min(ProcessTrackMateXml.this.impAnal.getNFrames(), Math.max(ProcessTrackMateXml.this.impAnal.getNFrames(), 1)));
/*      */             } 
/*  684 */             if (ProcessTrackMateXml.this.impAnal.getNSlices() > 1) {
/*  685 */               firstFrame = Integer.valueOf(Math.max(1, Math.min(ProcessTrackMateXml.this.impAnal.getNSlices(), 1)));
/*  686 */               lastFrame = Integer.valueOf(Math.min(ProcessTrackMateXml.this.impAnal.getNSlices(), Math.max(ProcessTrackMateXml.this.impAnal.getNSlices(), 1)));
/*      */             } 
/*  688 */             bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/*  689 */             width = Integer.valueOf(bounds.width);
/*  690 */             height = Integer.valueOf(bounds.height);
/*  691 */             nCaptures = Integer.valueOf(lastFrame.intValue() - firstFrame.intValue() + 1);
/*  692 */             stack = new ImageStack(width.intValue(), height.intValue());
/*      */             
/*  694 */             channel = Integer.valueOf(ProcessTrackMateXml.displayer.getImp().getChannel());
/*  695 */             slice = Integer.valueOf(ProcessTrackMateXml.displayer.getImp().getSlice());
/*  696 */             ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/*  697 */             for (int frame = firstFrame.intValue(); frame <= lastFrame.intValue(); frame++) {
/*  698 */               ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel.intValue(), slice.intValue(), frame);
/*  699 */               bi = new BufferedImage(width.intValue(), height.intValue(), 2);
/*  700 */               ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/*  701 */               cp = new ColorProcessor(bi);
/*  702 */               index = Integer.valueOf(ProcessTrackMateXml.displayer.getImp().getStackIndex(channel.intValue(), slice.intValue(), frame));
/*  703 */               stack.addSlice(ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index.intValue()), (ImageProcessor)cp);
/*      */             } 
/*  705 */             ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/*  706 */             capture = new ImagePlus("TrackMate capture of " + ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/*  707 */             ProcessTrackMateXml.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
/*      */             
/*  709 */             if (FirstWizardPanel.spotEnable.equals("spotEnable") == Boolean.TRUE.booleanValue()) {
/*      */ 
/*      */               
/*  712 */               final Model model = ProcessTrackMateXml.trackmate.getModel();
/*  713 */               FeatureModel fm1 = model.getFeatureModel();
/*      */ 
/*      */ 
/*      */               
/*  717 */               Set<Integer> trackIDs1 = model.getTrackModel().trackIDs(true);
/*  718 */               Collection<String> spotFeatures = ProcessTrackMateXml.trackmate.getModel().getFeatureModel().getSpotFeatures();
/*      */               
/*  720 */               ResultsTable spotTable = new ResultsTable();
/*      */ 
/*      */               
/*  723 */               for (Integer trackID : trackIDs1) {
/*  724 */                 Set<Spot> track1 = model.getTrackModel().trackSpots(trackID);
/*      */                 
/*  726 */                 List<Spot> sortedTrack = new ArrayList<>(track1);
/*  727 */                 Collections.sort(sortedTrack, Spot.frameComparator);
/*      */                 
/*  729 */                 for (Spot spot : sortedTrack) {
/*  730 */                   spotTable.incrementCounter();
/*  731 */                   spotTable.addLabel(spot.getName());
/*  732 */                   spotTable.addValue("ID", spot.ID());
/*  733 */                   spotTable.addValue("TRACK_ID", trackID.intValue());
/*  734 */                   for (String feature : spotFeatures) {
/*  735 */                     Double val = spot.getFeature(feature);
/*  736 */                     if (val == null) {
/*  737 */                       spotTable.addValue(feature, "None"); continue;
/*      */                     } 
/*  739 */                     if (((Boolean)fm1.getSpotFeatureIsInt().get(feature)).booleanValue()) {
/*  740 */                       spotTable.addValue(feature, val.intValue()); continue;
/*      */                     } 
/*  742 */                     spotTable.addValue(feature, val.doubleValue());
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  749 */               ProcessTrackMateXml.columnHeadersSpot = spotTable.getHeadings();
/*  750 */               int rowsSpot = spotTable.size();
/*  751 */               List<List<String>> dataListSpot = new ArrayList<>();
/*  752 */               for (int r = 0; r < rowsSpot; r++) {
/*  753 */                 List<String> stringsSpot = new ArrayList<>();
/*  754 */                 for (int c = 0; c < ProcessTrackMateXml.columnHeadersSpot.length; c++) {
/*  755 */                   String valuesSpot = spotTable.getStringValue(ProcessTrackMateXml.columnHeadersSpot[c], r);
/*  756 */                   stringsSpot.add(valuesSpot);
/*      */                 } 
/*      */                 
/*  759 */                 dataListSpot.add(stringsSpot);
/*      */               } 
/*  761 */               ProcessTrackMateXml.dataSpot = new String[dataListSpot.size()][]; int i1;
/*  762 */               for (i1 = 0; i1 < ProcessTrackMateXml.dataSpot.length; i1++) {
/*  763 */                 ProcessTrackMateXml.dataSpot[i1] = new String[((List)dataListSpot.get(i1)).size()];
/*      */               }
/*  765 */               for (i1 = 0; i1 < dataListSpot.size(); i1++) {
/*  766 */                 for (int u = 1; u < ((List)dataListSpot.get(i1)).size(); u++)
/*  767 */                   ProcessTrackMateXml.dataSpot[i1][u] = ((List<String>)dataListSpot.get(i1)).get(u); 
/*      */               } 
/*  769 */               FirstWizardPanel.createSpotTable();
/*      */               
/*  771 */               FirstWizardPanel.tableSpot.addMouseListener(new MouseAdapter()
/*      */                   {
/*      */                     public void mouseReleased(MouseEvent e)
/*      */                     {
/*  775 */                       if (ProcessTrackMateXml.selectionModel != null && FirstWizardPanel.command == "enable" && 
/*  776 */                         FirstWizardPanel.command != null) {
/*  777 */                         ListSelectionModel lsm = FirstWizardPanel.tableSpot.getSelectionModel();
/*  778 */                         int selStart = lsm.getMinSelectionIndex();
/*  779 */                         int selEnd = lsm.getMaxSelectionIndex();
/*  780 */                         if (selStart < 0 || selEnd < 0) {
/*      */                           return;
/*      */                         }
/*  783 */                         int minLine = Math.min(selStart, selEnd);
/*  784 */                         int maxLine = Math.max(selStart, selEnd);
/*  785 */                         Set<Spot> spots = new HashSet<>();
/*  786 */                         for (int row = minLine; row <= maxLine; row++) {
/*  787 */                           int spotID = 
/*  788 */                             Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(row, 2));
/*  789 */                           Spot spot = (ProcessTrackMateXml.null.access$8(ProcessTrackMateXml.null.this)).totalSpots.search(spotID);
/*  790 */                           if (spot != null)
/*  791 */                             spots.add(spot); 
/*      */                         } 
/*  793 */                         ProcessTrackMateXml.selectionModel.clearSelection();
/*  794 */                         ProcessTrackMateXml.selectionModel.addSpotToSelection(spots);
/*      */                       } 
/*      */                     }
/*      */                   });
/*      */             } 
/*      */ 
/*      */             
/*  801 */             if (ChooserWizardPanel.trackEnable.equals("trackEnable") == Boolean.TRUE.booleanValue()) {
/*      */               
/*  803 */               ProcessTrackMateXml.FEATURES.add("TOTAL_DISTANCE_TRAVELED");
/*  804 */               ProcessTrackMateXml.FEATURES.add("MAX_DISTANCE_TRAVELED");
/*  805 */               ProcessTrackMateXml.FEATURES.add("CONFINMENT_RATIO");
/*  806 */               ProcessTrackMateXml.FEATURES.add("MEAN_STRAIGHT_LINE_SPEED");
/*  807 */               ProcessTrackMateXml.FEATURES.add("LINEARITY_OF_FORWARD_PROGRESSION");
/*      */               
/*  809 */               ProcessTrackMateXml.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_XY");
/*  810 */               ProcessTrackMateXml.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_YZ");
/*  811 */               ProcessTrackMateXml.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_ZX");
/*      */               
/*  813 */               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total distance traveled");
/*  814 */               ProcessTrackMateXml.FEATURE_NAMES.put("MAX_DISTANCE_TRAVELED", "Max distance traveled");
/*  815 */               ProcessTrackMateXml.FEATURE_NAMES.put("CONFINMENT_RATIO", "Confinment ratio");
/*  816 */               ProcessTrackMateXml.FEATURE_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean straight line speed");
/*  817 */               ProcessTrackMateXml.FEATURE_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Linearity of forward progression");
/*      */ 
/*      */               
/*  820 */               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Absolute angle in xy plane");
/*  821 */               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Absolute angle in yz plane");
/*  822 */               ProcessTrackMateXml.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Absolute angle in zx plane");
/*      */               
/*  824 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total dist.");
/*  825 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("MAX_DISTANCE_TRAVELED", "Max dist.");
/*  826 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("CONFINMENT_RATIO", "Cnfnmnt ratio");
/*  827 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean v. line");
/*  828 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Lin. fwd. progr.");
/*      */ 
/*      */               
/*  831 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Abs. angle xy");
/*  832 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Abs. angle yz");
/*  833 */               ProcessTrackMateXml.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Abs. angle zx");
/*      */               
/*  835 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_DISTANCE_TRAVELED", Dimension.LENGTH);
/*  836 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("MAX_DISTANCE_TRAVELED", Dimension.LENGTH);
/*  837 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("CONFINMENT_RATIO", Dimension.NONE);
/*  838 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("MEAN_STRAIGHT_LINE_SPEED", Dimension.VELOCITY);
/*  839 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("LINEARITY_OF_FORWARD_PROGRESSION", Dimension.NONE);
/*      */               
/*  841 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_XY", Dimension.ANGLE);
/*  842 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_YZ", Dimension.ANGLE);
/*  843 */               ProcessTrackMateXml.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_ZX", Dimension.ANGLE);
/*      */               
/*  845 */               ProcessTrackMateXml.IS_INT.put("TOTAL_DISTANCE_TRAVELED", Boolean.FALSE);
/*  846 */               ProcessTrackMateXml.IS_INT.put("MAX_DISTANCE_TRAVELED", Boolean.FALSE);
/*  847 */               ProcessTrackMateXml.IS_INT.put("CONFINMENT_RATIO", Boolean.FALSE);
/*  848 */               ProcessTrackMateXml.IS_INT.put("MEAN_STRAIGHT_LINE_SPEED", Boolean.FALSE);
/*  849 */               ProcessTrackMateXml.IS_INT.put("LINEARITY_OF_FORWARD_PROGRESSION", Boolean.FALSE);
/*      */               
/*  851 */               ProcessTrackMateXml.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_XY", Boolean.FALSE);
/*  852 */               ProcessTrackMateXml.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_YZ", Boolean.FALSE);
/*  853 */               ProcessTrackMateXml.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_ZX", Boolean.FALSE);
/*      */ 
/*      */               
/*  856 */               final Model model = ProcessTrackMateXml.trackmate.getModel();
/*  857 */               FeatureModel fm1 = model.getFeatureModel();
/*      */ 
/*      */               
/*  860 */               Set<Integer> trackIDs1 = model.getTrackModel().trackIDs(true);
/*      */ 
/*      */               
/*  863 */               ProcessTrackMateXml.this.trackTable = new ResultsTable();
/*      */ 
/*      */               
/*  866 */               for (Integer trackID : trackIDs1) {
/*      */                 
/*  868 */                 List<Spot> spots = new ArrayList<>(model.getTrackModel().trackSpots(trackID));
/*  869 */                 Collections.sort(spots, Spot.frameComparator);
/*  870 */                 Spot first = spots.get(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  876 */                 Set<DefaultWeightedEdge> edges = model.getTrackModel().trackEdges(trackID);
/*      */                 
/*  878 */                 double totalDistance = 0.0D;
/*  879 */                 double maxDistanceSq = Double.NEGATIVE_INFINITY;
/*  880 */                 double maxDistance = 0.0D;
/*  881 */                 double dx = 0.0D;
/*  882 */                 double dy = 0.0D;
/*  883 */                 double dz = 0.0D;
/*      */                 
/*  885 */                 for (DefaultWeightedEdge edge : edges) {
/*      */                   
/*  887 */                   Spot source = model.getTrackModel().getEdgeSource(edge);
/*  888 */                   Spot target = model.getTrackModel().getEdgeTarget(edge);
/*  889 */                   double d = Math.sqrt(source.squareDistanceTo((RealLocalizable)target));
/*  890 */                   totalDistance += d;
/*      */ 
/*      */                   
/*  893 */                   double dToFirstSq = first.squareDistanceTo((RealLocalizable)target);
/*  894 */                   if (dToFirstSq > maxDistanceSq) {
/*  895 */                     maxDistanceSq = dToFirstSq;
/*  896 */                     maxDistance = Math.sqrt(maxDistanceSq);
/*      */                   } 
/*      */                   
/*  899 */                   dx += target.getDoublePosition(0) - source.getDoublePosition(0);
/*  900 */                   dy += target.getDoublePosition(1) - source.getDoublePosition(1);
/*  901 */                   dz += target.getDoublePosition(2) - source.getDoublePosition(2);
/*      */                 } 
/*      */ 
/*      */                 
/*  905 */                 double netDistance = fm1.getTrackFeature(trackID, 
/*  906 */                     "TRACK_DISPLACEMENT").doubleValue();
/*  907 */                 double tTotal = fm1.getTrackFeature(trackID, "TRACK_DURATION").doubleValue();
/*  908 */                 double vMean = fm1.getTrackFeature(trackID, 
/*  909 */                     "TRACK_MEAN_SPEED").doubleValue();
/*      */ 
/*      */                 
/*  912 */                 double confinmentRatio = netDistance / totalDistance;
/*  913 */                 double meanStraightLineSpeed = netDistance / tTotal;
/*  914 */                 double linearityForwardProgression = meanStraightLineSpeed / vMean;
/*      */ 
/*      */ 
/*      */                 
/*  918 */                 double angleXY = Math.atan2(dy, dx);
/*  919 */                 double angleYZ = Math.atan2(dz, dy);
/*  920 */                 double angleZX = Math.atan2(dx, dz);
/*  921 */                 Collection<String> trackFeatures = fm1.getTrackFeatures();
/*  922 */                 ProcessTrackMateXml.this.trackTable.incrementCounter();
/*  923 */                 ProcessTrackMateXml.this.trackTable.addLabel(model.getTrackModel().name(trackID));
/*  924 */                 ProcessTrackMateXml.this.trackTable.addValue("TRACK_ID", trackID.intValue());
/*      */                 
/*  926 */                 for (String feature : trackFeatures) {
/*  927 */                   Double val = fm1.getTrackFeature(trackID, feature);
/*  928 */                   if (val == null) {
/*  929 */                     ProcessTrackMateXml.this.trackTable.addValue(feature, "None"); continue;
/*      */                   } 
/*  931 */                   if (((Boolean)fm1.getTrackFeatureIsInt().get(feature)).booleanValue()) {
/*  932 */                     ProcessTrackMateXml.this.trackTable.addValue(feature, val.intValue()); continue;
/*      */                   } 
/*  934 */                   ProcessTrackMateXml.this.trackTable.addValue(feature, val.doubleValue());
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/*  939 */                 ProcessTrackMateXml.this.trackTable.addValue("TOTAL_DISTANCE_TRAVELED", 
/*  940 */                     Math.round(totalDistance * 1000.0D) / 1000.0D);
/*  941 */                 ProcessTrackMateXml.this.trackTable.addValue("MAX_DISTANCE_TRAVELED", 
/*  942 */                     Math.round(maxDistance * 1000.0D) / 1000.0D);
/*  943 */                 ProcessTrackMateXml.this.trackTable.addValue("MEAN_STRAIGHT_LINE_SPEED", 
/*  944 */                     Math.round(meanStraightLineSpeed * 1000.0D) / 1000.0D);
/*  945 */                 ProcessTrackMateXml.this.trackTable.addValue("LINEARITY_OF_FORWARD_PROGRESSION", 
/*  946 */                     Math.round(linearityForwardProgression * 1000.0D) / 1000.0D);
/*  947 */                 ProcessTrackMateXml.this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_XY", Math.round(angleXY * 1000.0D) / 1000.0D);
/*  948 */                 ProcessTrackMateXml.this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_YZ", Math.round(angleYZ * 1000.0D) / 1000.0D);
/*  949 */                 ProcessTrackMateXml.this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_ZX", Math.round(angleZX * 1000.0D) / 1000.0D);
/*  950 */                 ProcessTrackMateXml.this.trackTable.addValue("CONFINMENT_RATIO", 
/*  951 */                     Math.round(confinmentRatio * 1000.0D) / 1000.0D);
/*  952 */                 ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "");
/*      */                 
/*  954 */                 if (confinmentRatio == 0.0D)
/*  955 */                   ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Total-Confined Track"); 
/*  956 */                 if (confinmentRatio == 1.0D)
/*  957 */                   ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Perfectly Straight Track"); 
/*  958 */                 if (confinmentRatio > 0.0D && confinmentRatio <= 0.5D)
/*  959 */                   ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Strongly Confined Track"); 
/*  960 */                 if (confinmentRatio > 0.05D && confinmentRatio <= 0.25D)
/*  961 */                   ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Purely Random Track"); 
/*  962 */                 if (confinmentRatio > 0.25D && confinmentRatio < 1.0D) {
/*  963 */                   ProcessTrackMateXml.this.trackTable.addValue("TRACK_CLASSIFICATION", "Fairly Straight Track");
/*      */                 }
/*      */               } 
/*  966 */               ProcessTrackMateXml.columnHeadersTrack = ProcessTrackMateXml.this.trackTable.getHeadings();
/*  967 */               int rowsTrack = ProcessTrackMateXml.this.trackTable.size();
/*  968 */               List<List<String>> dataListTrack = new ArrayList<>();
/*  969 */               for (int r = 0; r < rowsTrack; r++) {
/*  970 */                 List<String> stringsTrack = new ArrayList<>();
/*  971 */                 for (int c = 0; c < ProcessTrackMateXml.columnHeadersTrack.length; c++) {
/*  972 */                   String valuesTrack = ProcessTrackMateXml.this.trackTable.getStringValue(ProcessTrackMateXml.columnHeadersTrack[c], r);
/*  973 */                   stringsTrack.add(valuesTrack);
/*      */                 } 
/*      */                 
/*  976 */                 dataListTrack.add(stringsTrack);
/*      */               } 
/*  978 */               ProcessTrackMateXml.dataTrack = new String[dataListTrack.size()][]; int i1;
/*  979 */               for (i1 = 0; i1 < ProcessTrackMateXml.dataTrack.length; i1++) {
/*  980 */                 ProcessTrackMateXml.dataTrack[i1] = new String[((List)dataListTrack.get(i1)).size()];
/*      */               }
/*  982 */               for (i1 = 0; i1 < dataListTrack.size(); i1++) {
/*  983 */                 for (int u = 1; u < ((List)dataListTrack.get(i1)).size(); u++)
/*  984 */                   ProcessTrackMateXml.dataTrack[i1][u] = ((List<String>)dataListTrack.get(i1)).get(u); 
/*      */               } 
/*  986 */               ChooserWizardPanel.createTrackTable();
/*  987 */               ChooserWizardPanel.tableTrack.addMouseListener(new MouseAdapter()
/*      */                   {
/*      */                     public void mouseReleased(MouseEvent e)
/*      */                     {
/*  991 */                       if (ProcessTrackMateXml.selectionModel != null && ChooserWizardPanel.command == "enable" && 
/*  992 */                         ChooserWizardPanel.command != null) {
/*  993 */                         ListSelectionModel lsm = ChooserWizardPanel.tableTrack.getSelectionModel();
/*  994 */                         int selStart = lsm.getMinSelectionIndex();
/*  995 */                         int selEnd = lsm.getMaxSelectionIndex();
/*  996 */                         if (selStart < 0 || selEnd < 0) {
/*      */                           return;
/*      */                         }
/*  999 */                         int minLine = Math.min(selStart, selEnd);
/* 1000 */                         int maxLine = Math.max(selStart, selEnd);
/* 1001 */                         Set<DefaultWeightedEdge> edges = new HashSet<>();
/* 1002 */                         Set<Spot> spots = new HashSet<>();
/* 1003 */                         for (int row = minLine; row <= maxLine; row++) {
/* 1004 */                           int trackID = 
/* 1005 */                             Integer.parseInt((String)ChooserWizardPanel.tableTrack.getValueAt(row, 2));
/* 1006 */                           spots.addAll(model.getTrackModel().trackSpots(Integer.valueOf(trackID)));
/* 1007 */                           edges.addAll(model.getTrackModel().trackEdges(Integer.valueOf(trackID)));
/*      */                         } 
/* 1009 */                         ProcessTrackMateXml.selectionModel.clearSelection();
/* 1010 */                         ProcessTrackMateXml.selectionModel.addSpotToSelection(spots);
/* 1011 */                         ProcessTrackMateXml.selectionModel.addEdgeToSelection(edges);
/*      */                       } 
/*      */                     }
/*      */                   });
/*      */             } 
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1023 */     this.mainProcess.start();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void transferCalibration(ImagePlus from, ImagePlus to) {
/* 1028 */     Calibration fc = from.getCalibration();
/* 1029 */     Calibration tc = to.getCalibration();
/*      */     
/* 1031 */     tc.setUnit(fc.getUnit());
/* 1032 */     tc.setTimeUnit(fc.getTimeUnit());
/* 1033 */     tc.frameInterval = fc.frameInterval;
/*      */     
/* 1035 */     double mag = from.getCanvas().getMagnification();
/* 1036 */     fc.pixelWidth /= mag;
/* 1037 */     fc.pixelHeight /= mag;
/* 1038 */     tc.pixelDepth = fc.pixelDepth;
/*      */   }
/*      */   
/*      */   private final TablePanel<Spot> createSpotTableRT(Model model, DisplaySettings ds) {
/* 1042 */     List<Spot> objects = new ArrayList<>();
/* 1043 */     for (Integer trackID : model.getTrackModel().unsortedTrackIDs(true))
/* 1044 */       objects.addAll(model.getTrackModel().trackSpots(trackID)); 
/* 1045 */     List<String> features = new ArrayList<>(model.getFeatureModel().getSpotFeatures());
/* 1046 */     Map<String, String> featureNames = model.getFeatureModel().getSpotFeatureNames();
/* 1047 */     Map<String, String> featureShortNames = model.getFeatureModel().getSpotFeatureShortNames();
/* 1048 */     Map<String, String> featureUnits = new HashMap<>();
/* 1049 */     for (String feature : features) {
/* 1050 */       Dimension dimension = (Dimension)model.getFeatureModel().getSpotFeatureDimensions()
/* 1051 */         .get(feature);
/* 1052 */       String units = TMUtils.getUnitsFor(dimension, model.getSpaceUnits(), model.getTimeUnits());
/* 1053 */       featureUnits.put(feature, units);
/*      */     } 
/* 1055 */     Map<String, Boolean> isInts = model.getFeatureModel().getSpotFeatureIsInt();
/* 1056 */     Map<String, String> infoTexts = new HashMap<>();
/* 1057 */     Function<Spot, String> labelGenerator = spot -> spot.getName();
/* 1058 */     BiConsumer<Spot, String> labelSetter = (spot, label) -> spot.setName(label);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1063 */     String SPOT_ID = "ID";
/* 1064 */     features.add(0, "ID");
/* 1065 */     featureNames.put("ID", "Spot ID");
/* 1066 */     featureShortNames.put("ID", "Spot ID");
/* 1067 */     featureUnits.put("ID", "");
/* 1068 */     isInts.put("ID", Boolean.TRUE);
/* 1069 */     infoTexts.put("ID", "The id of the spot.");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     String TRACK_ID = "TRACK_ID";
/* 1075 */     features.add(1, "TRACK_ID");
/* 1076 */     featureNames.put("TRACK_ID", "Track ID");
/* 1077 */     featureShortNames.put("TRACK_ID", "Track ID");
/* 1078 */     featureUnits.put("TRACK_ID", "");
/* 1079 */     isInts.put("TRACK_ID", Boolean.TRUE);
/* 1080 */     infoTexts.put("TRACK_ID", "The id of the track this spot belongs to.");
/*      */     
/* 1082 */     BiFunction<Spot, String, Double> featureFun = (spot, feature) -> {
/*      */         if (feature.equals("TRACK_ID")) {
/*      */           Integer trackID = paramModel.getTrackModel().trackIDOf(spot);
/*      */           
/*      */           return (trackID == null) ? null : Double.valueOf(trackID.doubleValue());
/*      */         } 
/*      */         
/*      */         return feature.equals("ID") ? Double.valueOf(spot.ID()) : spot.getFeature(feature);
/*      */       };
/*      */     
/* 1092 */     BiConsumer<Spot, Color> colorSetter = (spot, color) -> spot.putFeature("MANUAL_SPOT_COLOR", Double.valueOf(color.getRGB()));
/*      */ 
/*      */     
/* 1095 */     Supplier<FeatureColorGenerator<Spot>> coloring = () -> FeatureUtils.createSpotColorGenerator(paramModel, paramDisplaySettings);
/*      */     
/* 1097 */     TablePanel<Spot> table = new TablePanel(objects, features, featureFun, featureNames, featureShortNames, 
/* 1098 */         featureUnits, isInts, infoTexts, coloring, labelGenerator, labelSetter, 
/* 1099 */         "MANUAL_SPOT_COLOR", colorSetter);
/*      */     
/* 1101 */     return table;
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ProcessTrackMateXml.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */