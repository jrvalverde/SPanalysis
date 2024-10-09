/*      */ import fiji.plugin.trackmate.Dimension;
/*      */ import fiji.plugin.trackmate.FeatureModel;
/*      */ import fiji.plugin.trackmate.Logger;
/*      */ import fiji.plugin.trackmate.Model;
/*      */ import fiji.plugin.trackmate.SelectionModel;
/*      */ import fiji.plugin.trackmate.Settings;
/*      */ import fiji.plugin.trackmate.Spot;
/*      */ import fiji.plugin.trackmate.SpotCollection;
/*      */ import fiji.plugin.trackmate.SpotRoi;
/*      */ import fiji.plugin.trackmate.TrackMate;
/*      */ import fiji.plugin.trackmate.detection.DogDetectorFactory;
/*      */ import fiji.plugin.trackmate.detection.LogDetectorFactory;
/*      */ import fiji.plugin.trackmate.detection.SpotDetectorFactoryBase;
/*      */ import fiji.plugin.trackmate.features.EdgeCollectionDataset;
/*      */ import fiji.plugin.trackmate.features.FeatureFilter;
/*      */ import fiji.plugin.trackmate.features.SpotCollectionDataset;
/*      */ import fiji.plugin.trackmate.features.TrackCollectionDataset;
/*      */ import fiji.plugin.trackmate.gui.Fonts;
/*      */ import fiji.plugin.trackmate.gui.displaysettings.Colormap;
/*      */ import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings;
/*      */ import fiji.plugin.trackmate.gui.displaysettings.DisplaySettingsIO;
/*      */ import fiji.plugin.trackmate.io.TmXmlReader;
/*      */ import fiji.plugin.trackmate.tracking.LAPUtils;
/*      */ import fiji.plugin.trackmate.tracking.ManualTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.SpotTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.kalman.KalmanTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.sparselap.SimpleSparseLAPTrackerFactory;
/*      */ import fiji.plugin.trackmate.tracking.sparselap.SparseLAPTrackerFactory;
/*      */ import fiji.plugin.trackmate.util.ExportableChartPanel;
/*      */ import fiji.plugin.trackmate.util.TMUtils;
/*      */ import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
/*      */ import fiji.plugin.trackmate.visualization.hyperstack.HyperStackDisplayer;
/*      */ import fiji.plugin.trackmate.visualization.table.TablePanel;
/*      */ import ij.IJ;
/*      */ import ij.ImagePlus;
/*      */ import ij.ImageStack;
/*      */ import ij.gui.OvalRoi;
/*      */ import ij.gui.PolygonRoi;
/*      */ import ij.gui.Roi;
/*      */ import ij.gui.ShapeRoi;
/*      */ import ij.measure.ResultsTable;
/*      */ import ij.plugin.ZProjector;
/*      */ import ij.plugin.frame.RoiManager;
/*      */ import ij.process.ColorProcessor;
/*      */ import ij.process.ImageProcessor;
/*      */ import inra.ijpb.morphology.Morphology;
/*      */ import inra.ijpb.morphology.Strel;
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Insets;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JProgressBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.table.DefaultTableModel;
/*      */ import javax.swing.text.DefaultCaret;
/*      */ import javax.xml.parsers.DocumentBuilder;
/*      */ import javax.xml.parsers.DocumentBuilderFactory;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.xpath.XPath;
/*      */ import javax.xml.xpath.XPathConstants;
/*      */ import javax.xml.xpath.XPathExpression;
/*      */ import javax.xml.xpath.XPathExpressionException;
/*      */ import javax.xml.xpath.XPathFactory;
/*      */ import org.jfree.chart.ChartFactory;
/*      */ import org.jfree.chart.ChartUtils;
/*      */ import org.jfree.chart.JFreeChart;
/*      */ import org.jfree.chart.axis.NumberAxis;
/*      */ import org.jfree.chart.labels.CategoryItemLabelGenerator;
/*      */ import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
/*      */ import org.jfree.chart.plot.CategoryPlot;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.renderer.category.CategoryItemRenderer;
/*      */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*      */ import org.jfree.chart.ui.RectangleInsets;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.category.DefaultCategoryDataset;
/*      */ import org.jfree.data.xy.DefaultXYDataset;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jgrapht.graph.DefaultWeightedEdge;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ import org.xml.sax.SAXException;
/*      */ import smileModified.GaussianMixtureModified;
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
/*      */ class null
/*      */   implements Runnable
/*      */ {
/*      */   public void run() {
/*  489 */     SPTBatch_.access$44(SPTBatch_.this, SPTBatch_.access$34(SPTBatch_.this).getSelectedItem().toString());
/*  490 */     SPTBatch_.access$45(SPTBatch_.this, SPTBatch_.access$35(SPTBatch_.this).getSelectedItem().toString());
/*      */ 
/*      */     
/*  493 */     SPTBatch_.access$46(SPTBatch_.this, SPTBatch_.access$38(SPTBatch_.this).getSelectedItem().toString());
/*  494 */     SPTBatch_.access$47(SPTBatch_.this, SPTBatch_.access$39(SPTBatch_.this).getSelectedItem().toString());
/*      */ 
/*      */     
/*  497 */     SPTBatch_.access$48(SPTBatch_.this, SPTBatch_.access$42(SPTBatch_.this).getSelectedItem().toString());
/*  498 */     SPTBatch_.access$49(SPTBatch_.this, SPTBatch_.access$43(SPTBatch_.this).getSelectedItem().toString());
/*      */     
/*  500 */     SPTBatch_.fileXmlInitial = new File(SPTBatch_.access$50(SPTBatch_.this));
/*  501 */     File imageFolder = new File(SPTBatch_.access$51(SPTBatch_.this));
/*  502 */     final File[] listOfFiles = imageFolder.listFiles();
/*  503 */     SPTBatch_.access$52(SPTBatch_.this, new String[listOfFiles.length]);
/*  504 */     File[] filesXML = new File[listOfFiles.length];
/*  505 */     for (int u = 0; u < filesXML.length; u++)
/*  506 */       filesXML[u] = new File(SPTBatch_.access$50(SPTBatch_.this)); 
/*  507 */     Object[] columHeadersFinalSpot = { "IMAGE_TITLE", "QUALITY", "POSITION_X", "POSITION_Y", 
/*  508 */         "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MEAN_INTENSITY", 
/*  509 */         "MEDIAN_INTENSITY", "MIN_INTENSITY", "MAX_INTENSITY", "TOTAL_INTENSITY", "STANDARD_DEVIATION", 
/*  510 */         "CONTRAST", "SNR", "ESTIMATED_DIAMETER", "MORPHOLOGY", "ELLIPSOIDFIT_SEMIAXISLENGTH_C", 
/*  511 */         "ELLIPSOIDFIT_SEMIAXISLENGTH_B", "ELLIPSOIDFIT_SEMIAXISLENGTH_A", "ELLIPSOIDFIT_AXISPHI_C", 
/*  512 */         "ELLIPSOIDFIT_AXISPHI_B", "ELLIPSOIDFIT_AXISPHI_A", "ELLIPSOIDFIT_AXISTHETA_C", 
/*  513 */         "ELLIPSOIDFIT_AXISTHETA_B", "ELLIPSOIDFIT_AXISTHETA_A" };
/*  514 */     Object[] columHeadersFinalLink = { "IMAGE_TITLE", "LINK_COST", "EDGE_TIME", 
/*  515 */         "EDGE_X_LOCATION", "EDGE_Y_LOCATION", "EDGE_Z_LOCATION", "VELOCITY", "DISPLACEMENT" };
/*  516 */     Object[] columHeadersFinalTrack = { "IMAGE_TITLE", "TRACK_DURATION", "TRACK_START", 
/*  517 */         "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", 
/*  518 */         "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_INDEX", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", 
/*  519 */         "TRACK_Z_LOCATION", "NUMBER_SPOTS", "NUMBER_GAPS", "LONGEST_GAP", "NUMBER_SPLITS", 
/*  520 */         "NUMBER_MERGES", "NUMBER_COMPLEX", "TRACK_MEAN_QUALITY", "TRACK_MAX_QUALITY", 
/*  521 */         "TRACK_MIN_QUALITY", "TRACK_MEDIAN_QUALITY", "TRACK_STD_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/*  522 */         "MAX_DISTANCE_TRAVELED", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/*  523 */         "TOTAL_ABSOLUTE_ANGLE_XY", "TOTAL_ABSOLUTE_ANGLE_YZ", "TOTAL_ABSOLUTE_ANGLE_ZX", 
/*  524 */         "CONFINMENT_RATIO" };
/*  525 */     SPTBatch_.access$54(SPTBatch_.this, new ResultsTable(Integer.valueOf((SPTBatch_.access$53(SPTBatch_.this)).length)));
/*  526 */     SPTBatch_.access$55(SPTBatch_.this, new ResultsTable(Integer.valueOf((SPTBatch_.access$53(SPTBatch_.this)).length)));
/*  527 */     SPTBatch_.access$56(SPTBatch_.this, new ResultsTable(Integer.valueOf((SPTBatch_.access$53(SPTBatch_.this)).length)));
/*  528 */     SPTBatch_.this.rtSpots = new ResultsTable[listOfFiles.length];
/*  529 */     SPTBatch_.this.rtLinks = new ResultsTable[listOfFiles.length];
/*  530 */     SPTBatch_.this.rtTracks = new ResultsTable[listOfFiles.length]; int y;
/*  531 */     for (y = 0; y < columHeadersFinalSpot.length; y++)
/*  532 */       SPTBatch_.access$57(SPTBatch_.this).setHeading(y, (String)columHeadersFinalSpot[y]); 
/*  533 */     for (y = 0; y < columHeadersFinalLink.length; y++)
/*  534 */       SPTBatch_.access$58(SPTBatch_.this).setHeading(y, (String)columHeadersFinalLink[y]); 
/*  535 */     for (y = 0; y < columHeadersFinalTrack.length; y++) {
/*  536 */       SPTBatch_.access$59(SPTBatch_.this).setHeading(y, (String)columHeadersFinalTrack[y]);
/*      */     }
/*  538 */     int MAX = listOfFiles.length;
/*  539 */     JFrame frameAnalyzer = new JFrame("Analyzing...");
/*  540 */     final JProgressBar pb = new JProgressBar();
/*  541 */     pb.setMinimum(0);
/*  542 */     pb.setMaximum(MAX);
/*  543 */     pb.setStringPainted(true);
/*  544 */     SPTBatch_.taskOutput = new JTextArea(5, 20);
/*  545 */     SPTBatch_.taskOutput.setMargin(new Insets(5, 5, 5, 5));
/*  546 */     SPTBatch_.taskOutput.setEditable(false);
/*  547 */     DefaultCaret caret = (DefaultCaret)SPTBatch_.taskOutput.getCaret();
/*  548 */     caret.setUpdatePolicy(2);
/*  549 */     JPanel panel = new JPanel();
/*  550 */     panel.setLayout(new BoxLayout(panel, 1));
/*  551 */     panel.add(pb);
/*  552 */     panel.add(Box.createVerticalStrut(5));
/*  553 */     panel.add(new JScrollPane(SPTBatch_.taskOutput), "Center");
/*  554 */     panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
/*  555 */     frameAnalyzer.getContentPane().add(panel);
/*  556 */     frameAnalyzer.setDefaultCloseOperation(2);
/*  557 */     frameAnalyzer.setSize(550, 500);
/*  558 */     frameAnalyzer.setVisible(true);
/*  559 */     SPTBatch_.this.selectedItems = new ArrayList<>();
/*      */     
/*  561 */     if (SPTBatch_.checkSummary.isSelected()) {
/*  562 */       if (summaryColsWindow.combo.getSelectedIndex() == 0)
/*  563 */         for (int i = 0; i < summaryColsWindow.itemsSpots.length; i++) {
/*  564 */           if (summaryColsWindow.itemsSpots[i].isSelected())
/*  565 */             SPTBatch_.this.selectedItems.add((summaryColsWindow.itemsSpots[i]).text); 
/*      */         }  
/*  567 */       if (summaryColsWindow.combo.getSelectedIndex() == 1)
/*  568 */         for (int i = 0; i < summaryColsWindow.itemsLinks.length; i++) {
/*  569 */           if (summaryColsWindow.itemsLinks[i].isSelected())
/*  570 */             SPTBatch_.this.selectedItems.add((summaryColsWindow.itemsLinks[i]).text); 
/*      */         }  
/*  572 */       if (summaryColsWindow.combo.getSelectedIndex() == 2)
/*  573 */         for (int i = 0; i < summaryColsWindow.itemsTracks.length; i++) {
/*  574 */           if (summaryColsWindow.itemsTracks[i].isSelected())
/*  575 */             SPTBatch_.this.selectedItems.add((summaryColsWindow.itemsTracks[i]).text); 
/*      */         }  
/*      */     } 
/*  578 */     for (SPTBatch_.i = 0; SPTBatch_.i < listOfFiles.length; SPTBatch_.i++) {
/*  579 */       if (SPTBatch_.imps != null) {
/*  580 */         SPTBatch_.imps.hide();
/*      */       }
/*  582 */       if (listOfFiles[SPTBatch_.i].isFile()) {
/*  583 */         SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i] = listOfFiles[SPTBatch_.i].getName();
/*  584 */         SPTBatch_.imgTitle = SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i];
/*      */       } 
/*  586 */       final int currentValue = SPTBatch_.i + 1;
/*      */       
/*      */       try {
/*  589 */         SwingUtilities.invokeLater(new Runnable() {
/*      */               public void run() {
/*  591 */                 pb.setValue(currentValue);
/*  592 */                 SPTBatch_.taskOutput.append(String.format("Completed %f%% of task.\n", new Object[] {
/*  593 */                         Double.valueOf(this.val$currentValue * 100.0D / this.val$listOfFiles.length) }));
/*      */               }
/*      */             });
/*  596 */         Thread.sleep(100L);
/*  597 */       } catch (InterruptedException var69) {
/*  598 */         JOptionPane.showMessageDialog(frameAnalyzer, var69.getMessage());
/*      */       } 
/*  600 */       if (listOfFiles[SPTBatch_.i].getName().contains(".lif")) {
/*  601 */         SPTBatch_.this.lifs = SPTBatch_.openBF(String.valueOf(SPTBatch_.access$51(SPTBatch_.this)) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i], false, false, false, false, false, 
/*  602 */             true);
/*  603 */         for (int x = 0; x < SPTBatch_.this.lifs.length; x++) {
/*  604 */           SPTBatch_.imps = new ImagePlus(String.valueOf(SPTBatch_.access$51(SPTBatch_.this)) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i]);
/*      */         }
/*      */       } 
/*  607 */       if (!listOfFiles[SPTBatch_.i].getName().contains(".lif"))
/*  608 */         SPTBatch_.imps = new ImagePlus(String.valueOf(SPTBatch_.access$51(SPTBatch_.this)) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i]); 
/*  609 */       IJ.resetMinAndMax(SPTBatch_.imps);
/*  610 */       SPTBatch_.access$60(SPTBatch_.this, SPTBatch_.imps.getDimensions());
/*  611 */       SPTBatch_.this.calibration = SPTBatch_.imps.getCalibration();
/*  612 */       SPTBatch_.fps = (SPTBatch_.imps.getFileInfo()).frameInterval;
/*  613 */       if (SPTBatch_.access$61(SPTBatch_.this)[4] == 1 && SPTBatch_.access$61(SPTBatch_.this)[3] > 1) {
/*      */         
/*  615 */         SPTBatch_.imps.setDimensions(SPTBatch_.access$61(SPTBatch_.this)[2], SPTBatch_.access$61(SPTBatch_.this)[4], SPTBatch_.access$61(SPTBatch_.this)[3]);
/*  616 */         SPTBatch_.this.calibration.frameInterval = SPTBatch_.this.calibration.frameInterval;
/*  617 */         SPTBatch_.access$62(SPTBatch_.this, Logger.IJ_LOGGER);
/*      */       } 
/*      */       
/*  620 */       SPTBatch_.impsSubBg = SPTBatch_.imps.duplicate();
/*  621 */       SPTBatch_.impsSubBg.setCalibration(SPTBatch_.this.calibration);
/*  622 */       SPTBatch_.directImages = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", ""));
/*      */       
/*  624 */       if (!SPTBatch_.directImages.exists()) {
/*  625 */         SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directImages.getName());
/*  626 */         boolean result = false;
/*      */         
/*      */         try {
/*  629 */           SPTBatch_.directImages.mkdir();
/*  630 */           result = true;
/*  631 */         } catch (SecurityException securityException) {}
/*      */ 
/*      */         
/*  634 */         if (result) {
/*  635 */           SPTBatch_.taskOutput.append("DIR created");
/*      */         }
/*      */       } 
/*      */       
/*  639 */       SPTBatch_.directSummary = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + "Summary_Analysis");
/*      */       
/*  641 */       if (!SPTBatch_.directSummary.exists()) {
/*  642 */         boolean result = false;
/*      */         
/*      */         try {
/*  645 */           SPTBatch_.directSummary.mkdir();
/*  646 */           result = true;
/*  647 */         } catch (SecurityException securityException) {}
/*      */ 
/*      */         
/*  650 */         if (result) {
/*  651 */           SPTBatch_.taskOutput.append("DIR created-Summary_Analysis");
/*      */         }
/*      */       } 
/*  654 */       SPTBatch_.directSPT = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", "") + 
/*  655 */           File.separator + "SPT_Analysis");
/*      */       
/*  657 */       if (!SPTBatch_.directSPT.exists()) {
/*  658 */         boolean result = false;
/*      */         
/*      */         try {
/*  661 */           SPTBatch_.directSPT.mkdir();
/*  662 */           result = true;
/*  663 */         } catch (SecurityException securityException) {}
/*      */ 
/*      */         
/*  666 */         if (result) {
/*  667 */           SPTBatch_.taskOutput.append("DIR created-SPT_Analysis");
/*      */         }
/*      */       } 
/*      */       
/*  671 */       SPTBatch_.directPBS = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", "") + 
/*  672 */           File.separator + "Photobleaching_Analysis");
/*  673 */       if (SPTBatch_.checkPBS.isSelected())
/*      */       {
/*  675 */         if (!SPTBatch_.directPBS.exists()) {
/*  676 */           boolean result = false;
/*      */           
/*      */           try {
/*  679 */             SPTBatch_.directPBS.mkdir();
/*  680 */             result = true;
/*  681 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/*  684 */           if (result) {
/*  685 */             SPTBatch_.taskOutput.append("DIR created-Photobleching_Analysis");
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  690 */       if (SPTBatch_.checkboxMSD.isSelected() == Boolean.TRUE.booleanValue()) {
/*  691 */         SPTBatch_.directMSS = new File(String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "MSS_Analysis");
/*      */         
/*  693 */         if (!SPTBatch_.directMSS.exists()) {
/*  694 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directMSS.getName());
/*  695 */           boolean result = false;
/*      */           
/*      */           try {
/*  698 */             SPTBatch_.directMSS.mkdir();
/*  699 */             result = true;
/*  700 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/*  703 */           if (result) {
/*  704 */             SPTBatch_.taskOutput.append(String.valueOf(SPTBatch_.directMSS.getName()) + "  DIR created");
/*      */           }
/*      */         } 
/*      */       } 
/*  708 */       if (SPTBatch_.checkCluster.isSelected() == Boolean.TRUE.booleanValue()) {
/*  709 */         SPTBatch_.directCluster = new File(
/*  710 */             String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "Cluster_Size_Analysis");
/*      */         
/*  712 */         if (!SPTBatch_.directCluster.exists()) {
/*  713 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directCluster.getName());
/*  714 */           boolean result = false;
/*      */           
/*      */           try {
/*  717 */             SPTBatch_.directCluster.mkdir();
/*  718 */             result = true;
/*  719 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/*  722 */           if (result) {
/*  723 */             SPTBatch_.taskOutput.append(String.valueOf(SPTBatch_.directCluster.getName()) + "  DIR created");
/*      */           }
/*      */         } 
/*      */       } 
/*  727 */       TmXmlReader reader = new TmXmlReader(SPTBatch_.fileXmlInitial);
/*  728 */       DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
/*  729 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  730 */       DocumentBuilder builder = null;
/*      */       try {
/*  732 */         builder = factory.newDocumentBuilder();
/*  733 */       } catch (ParserConfigurationException e) {
/*      */         
/*  735 */         e.printStackTrace();
/*      */       } 
/*      */       
/*  738 */       Document doc = null;
/*      */       
/*      */       try {
/*  741 */         doc = builder.parse(SPTBatch_.fileXmlInitial);
/*  742 */       } catch (SAXException e) {
/*      */         
/*  744 */         e.printStackTrace();
/*  745 */       } catch (IOException e) {
/*      */         
/*  747 */         e.printStackTrace();
/*      */       } 
/*  749 */       XPathFactory xPathfactory = XPathFactory.newInstance();
/*  750 */       XPath xpath = xPathfactory.newXPath();
/*      */       
/*  752 */       XPathExpression exprBasicSettings = null;
/*  753 */       XPathExpression exprDetectorSettings = null;
/*  754 */       XPathExpression exprInitialSpotFilter = null;
/*  755 */       XPathExpression exprFilter = null;
/*  756 */       XPathExpression exprTrackerSettings = null;
/*  757 */       XPathExpression exprLinking = null;
/*  758 */       XPathExpression exprGapClosing = null;
/*  759 */       XPathExpression exprSplitting = null;
/*  760 */       XPathExpression exprMerging = null;
/*  761 */       XPathExpression exprTrackFilter = null;
/*  762 */       XPathExpression exprLinkingP = null;
/*      */ 
/*      */       
/*      */       try {
/*  766 */         exprBasicSettings = xpath.compile("//Settings/BasicSettings[@zstart]");
/*  767 */       } catch (XPathExpressionException e) {
/*      */         
/*  769 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  773 */         exprLinkingP = xpath.compile("//Linking/FeaturePenalties[@MEAN_INTENSITY]");
/*  774 */       } catch (XPathExpressionException e) {
/*      */         
/*  776 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  780 */         exprDetectorSettings = xpath.compile("//Settings/DetectorSettings[@RADIUS]");
/*  781 */       } catch (XPathExpressionException e) {
/*      */         
/*  783 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  787 */         exprInitialSpotFilter = xpath
/*  788 */           .compile("//Settings/InitialSpotFilter[@feature]");
/*  789 */       } catch (XPathExpressionException e) {
/*      */         
/*  791 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  795 */         exprFilter = xpath.compile("//SpotFilterCollection/Filter[@feature]");
/*  796 */       } catch (XPathExpressionException e) {
/*      */         
/*  798 */         e.printStackTrace();
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  803 */         exprTrackerSettings = xpath
/*  804 */           .compile("//Settings/TrackerSettings[@TRACKER_NAME]");
/*  805 */       } catch (XPathExpressionException e) {
/*      */         
/*  807 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  811 */         exprLinking = xpath
/*  812 */           .compile("//TrackerSettings/Linking[@LINKING_MAX_DISTANCE]");
/*  813 */       } catch (XPathExpressionException e) {
/*      */         
/*  815 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  819 */         exprGapClosing = xpath
/*  820 */           .compile("//TrackerSettings/GapClosing[@MAX_FRAME_GAP]");
/*  821 */       } catch (XPathExpressionException e) {
/*      */         
/*  823 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  827 */         exprSplitting = xpath
/*  828 */           .compile("//TrackerSettings/TrackSplitting[@SPLITTING_MAX_DISTANCE]");
/*  829 */       } catch (XPathExpressionException e) {
/*      */         
/*  831 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  835 */         exprMerging = xpath
/*  836 */           .compile("//TrackerSettings/TrackMerging[@MERGING_MAX_DISTANCE]");
/*  837 */       } catch (XPathExpressionException e) {
/*      */         
/*  839 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  843 */         exprTrackFilter = xpath.compile("//TrackFilterCollection/Filter[@feature]");
/*  844 */       } catch (XPathExpressionException e) {
/*      */         
/*  846 */         e.printStackTrace();
/*      */       } 
/*      */       
/*  849 */       NodeList nlBasicSettings = null;
/*  850 */       NodeList nlDetectorSettings = null;
/*  851 */       NodeList nlInitialSpotFilter = null;
/*  852 */       NodeList nlFilter = null;
/*  853 */       NodeList nlTrackerSettings = null;
/*  854 */       NodeList nlLinking = null;
/*  855 */       NodeList nlGapClosing = null;
/*  856 */       NodeList nlSplitting = null;
/*  857 */       NodeList nlMerging = null;
/*  858 */       NodeList nlTrackFilter = null;
/*  859 */       NodeList nlLinkingP = null;
/*      */ 
/*      */       
/*      */       try {
/*  863 */         nlBasicSettings = (NodeList)exprBasicSettings.evaluate(doc, XPathConstants.NODESET);
/*  864 */         nlDetectorSettings = (NodeList)exprDetectorSettings.evaluate(doc, XPathConstants.NODESET);
/*  865 */         nlInitialSpotFilter = (NodeList)exprInitialSpotFilter.evaluate(doc, XPathConstants.NODESET);
/*  866 */         nlFilter = (NodeList)exprFilter.evaluate(doc, XPathConstants.NODESET);
/*  867 */         nlTrackerSettings = (NodeList)exprTrackerSettings.evaluate(doc, XPathConstants.NODESET);
/*  868 */         nlLinking = (NodeList)exprLinking.evaluate(doc, XPathConstants.NODESET);
/*  869 */         nlGapClosing = (NodeList)exprGapClosing.evaluate(doc, XPathConstants.NODESET);
/*  870 */         nlSplitting = (NodeList)exprSplitting.evaluate(doc, XPathConstants.NODESET);
/*  871 */         nlMerging = (NodeList)exprMerging.evaluate(doc, XPathConstants.NODESET);
/*  872 */         nlTrackFilter = (NodeList)exprTrackFilter.evaluate(doc, XPathConstants.NODESET);
/*  873 */         nlLinkingP = (NodeList)exprLinkingP.evaluate(doc, XPathConstants.NODESET);
/*      */       }
/*  875 */       catch (XPathExpressionException e) {
/*      */         
/*  877 */         e.printStackTrace();
/*      */       } 
/*      */       int j;
/*  880 */       for (j = 0; j < nlBasicSettings.getLength(); j++) {
/*  881 */         Node currentItem = nlBasicSettings.item(j);
/*  882 */         SPTBatch_.access$63(SPTBatch_.this, currentItem.getAttributes().getNamedItem("zstart").getNodeValue());
/*  883 */         SPTBatch_.access$64(SPTBatch_.this, currentItem.getAttributes().getNamedItem("zend").getNodeValue());
/*  884 */         SPTBatch_.access$65(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ystart").getNodeValue());
/*  885 */         SPTBatch_.access$66(SPTBatch_.this, currentItem.getAttributes().getNamedItem("yend").getNodeValue());
/*  886 */         SPTBatch_.access$67(SPTBatch_.this, currentItem.getAttributes().getNamedItem("xstart").getNodeValue());
/*  887 */         SPTBatch_.access$68(SPTBatch_.this, currentItem.getAttributes().getNamedItem("xend").getNodeValue());
/*  888 */         SPTBatch_.access$69(SPTBatch_.this, currentItem.getAttributes().getNamedItem("tstart").getNodeValue());
/*  889 */         SPTBatch_.access$70(SPTBatch_.this, currentItem.getAttributes().getNamedItem("tend").getNodeValue());
/*      */       } 
/*  891 */       for (j = 0; j < nlDetectorSettings.getLength(); j++) {
/*  892 */         Node currentItem = nlDetectorSettings.item(j);
/*  893 */         SPTBatch_.RADIUS = currentItem.getAttributes().getNamedItem("RADIUS").getNodeValue();
/*  894 */         SPTBatch_.access$71(SPTBatch_.this, currentItem.getAttributes().getNamedItem("THRESHOLD").getNodeValue());
/*  895 */         SPTBatch_.access$72(SPTBatch_.this, currentItem.getAttributes().getNamedItem("TARGET_CHANNEL").getNodeValue());
/*  896 */         SPTBatch_.access$73(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DO_SUBPIXEL_LOCALIZATION")
/*  897 */             .getNodeValue());
/*  898 */         SPTBatch_.access$74(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DO_MEDIAN_FILTERING")
/*  899 */             .getNodeValue());
/*  900 */         SPTBatch_.access$75(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DETECTOR_NAME").getNodeValue());
/*  901 */         if (SPTBatch_.access$76(SPTBatch_.this).equals("BLOCK_LOG_DETECTOR"))
/*  902 */           SPTBatch_.access$77(SPTBatch_.this, currentItem.getAttributes().getNamedItem("NSPLIT").getNodeValue()); 
/*  903 */         if (SPTBatch_.access$76(SPTBatch_.this).equals("DOWNSAMLE_LOG_DETECTOR")) {
/*  904 */           SPTBatch_.access$78(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DOWNSAMPLE_FACTOR")
/*  905 */               .getNodeValue());
/*      */         }
/*      */       } 
/*      */       
/*  909 */       for (j = 0; j < nlLinkingP.getLength(); j++) {
/*  910 */         SPTBatch_.access$79(SPTBatch_.this, nlLinkingP.item(j).getAttributes().item(j).getNodeName());
/*  911 */         SPTBatch_.access$80(SPTBatch_.this, nlLinkingP.item(j).getAttributes().item(j).getNodeValue());
/*      */       } 
/*  913 */       for (j = 0; j < nlInitialSpotFilter.getLength(); j++) {
/*  914 */         Node currentItem = nlInitialSpotFilter.item(j);
/*  915 */         SPTBatch_.access$81(SPTBatch_.this, currentItem.getAttributes().getNamedItem("value").getNodeValue());
/*      */       } 
/*      */       
/*  918 */       List<String> spotFilterFeature = new ArrayList<>();
/*  919 */       List<String> spotFilterValue = new ArrayList<>();
/*  920 */       List<String> spotFilterAbove = new ArrayList<>();
/*  921 */       for (int i = 0; i < nlFilter.getLength(); i++) {
/*  922 */         Node currentItem = nlFilter.item(i);
/*  923 */         spotFilterFeature.add(currentItem.getAttributes().getNamedItem("feature").getNodeValue());
/*  924 */         spotFilterValue.add(currentItem.getAttributes().getNamedItem("value").getNodeValue());
/*  925 */         spotFilterAbove.add(currentItem.getAttributes().getNamedItem("isabove").getNodeValue());
/*      */       } 
/*      */ 
/*      */       
/*  929 */       List<String> trackFilterFeature = new ArrayList<>();
/*  930 */       List<String> trackFilterValue = new ArrayList<>();
/*  931 */       List<String> trackFilterAbove = new ArrayList<>(); int k;
/*  932 */       for (k = 0; k < nlTrackFilter.getLength(); k++) {
/*  933 */         Node currentItem = nlTrackFilter.item(k);
/*  934 */         trackFilterFeature.add(currentItem.getAttributes().getNamedItem("feature").getNodeValue());
/*  935 */         trackFilterValue.add(currentItem.getAttributes().getNamedItem("value").getNodeValue());
/*  936 */         trackFilterAbove.add(currentItem.getAttributes().getNamedItem("isabove").getNodeValue());
/*      */       } 
/*  938 */       for (k = 0; k < nlTrackerSettings.getLength(); k++) {
/*  939 */         Node currentItem = nlTrackerSettings.item(k);
/*  940 */         SPTBatch_.access$82(SPTBatch_.this, currentItem.getAttributes().getNamedItem("TRACKER_NAME").getNodeValue());
/*  941 */         SPTBatch_.access$83(SPTBatch_.this, currentItem.getAttributes().getNamedItem("CUTOFF_PERCENTILE")
/*  942 */             .getNodeValue());
/*  943 */         SPTBatch_.access$84(SPTBatch_.this, currentItem.getAttributes().getNamedItem("BLOCKING_VALUE").getNodeValue());
/*  944 */         SPTBatch_.access$85(SPTBatch_.this, currentItem.getAttributes()
/*  945 */             .getNamedItem("ALTERNATIVE_LINKING_COST_FACTOR").getNodeValue());
/*      */       } 
/*  947 */       for (k = 0; k < nlLinking.getLength(); k++) {
/*  948 */         Node currentItem = nlLinking.item(k);
/*  949 */         SPTBatch_.access$86(SPTBatch_.this, currentItem.getAttributes().getNamedItem("LINKING_MAX_DISTANCE")
/*  950 */             .getNodeValue());
/*      */       } 
/*  952 */       for (k = 0; k < nlGapClosing.getLength(); k++) {
/*  953 */         Node currentItem = nlGapClosing.item(k);
/*  954 */         SPTBatch_.access$87(SPTBatch_.this, currentItem.getAttributes().getNamedItem("MAX_FRAME_GAP").getNodeValue());
/*  955 */         SPTBatch_.access$88(SPTBatch_.this, currentItem.getAttributes().getNamedItem("GAP_CLOSING_MAX_DISTANCE")
/*  956 */             .getNodeValue());
/*  957 */         SPTBatch_.access$89(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ALLOW_GAP_CLOSING")
/*  958 */             .getNodeValue());
/*      */       } 
/*      */       
/*  961 */       for (k = 0; k < nlSplitting.getLength(); k++) {
/*  962 */         Node currentItem = nlSplitting.item(k);
/*  963 */         SPTBatch_.access$90(SPTBatch_.this, currentItem.getAttributes().getNamedItem("SPLITTING_MAX_DISTANCE")
/*  964 */             .getNodeValue());
/*  965 */         SPTBatch_.access$91(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ALLOW_TRACK_SPLITTING")
/*  966 */             .getNodeValue());
/*      */       } 
/*  968 */       for (k = 0; k < nlMerging.getLength(); k++) {
/*  969 */         Node currentItem = nlMerging.item(k);
/*  970 */         SPTBatch_.access$92(SPTBatch_.this, currentItem.getAttributes().getNamedItem("MERGING_MAX_DISTANCE")
/*  971 */             .getNodeValue());
/*  972 */         SPTBatch_.access$93(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ALLOW_TRACK_MERGING")
/*  973 */             .getNodeValue());
/*      */       } 
/*      */       
/*  976 */       SPTBatch_.access$94(SPTBatch_.this, new Settings(SPTBatch_.imps));
/*  977 */       SPTBatch_.taskOutput.append(SPTBatch_.access$95(SPTBatch_.this).toStringImageInfo());
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
/*  995 */       if (SPTBatch_.access$76(SPTBatch_.this).equals("LOG_DETECTOR")) {
/*  996 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorFactory = (SpotDetectorFactoryBase)new LogDetectorFactory();
/*  997 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings = (SPTBatch_.access$95(SPTBatch_.this)).detectorFactory.getDefaultSettings();
/*  998 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", 
/*  999 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$96(SPTBatch_.this))));
/* 1000 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("RADIUS", Double.valueOf(Double.parseDouble(SPTBatch_.RADIUS)));
/* 1001 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("TARGET_CHANNEL", 
/* 1002 */             Integer.valueOf(Integer.parseInt(SPTBatch_.access$97(SPTBatch_.this))));
/* 1003 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("THRESHOLD", Double.valueOf(Double.parseDouble(SPTBatch_.access$98(SPTBatch_.this))));
/* 1004 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("DO_MEDIAN_FILTERING", 
/* 1005 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$99(SPTBatch_.this))));
/* 1006 */         if (SPTBatch_.access$100(SPTBatch_.this) != null) {
/* 1007 */           (SPTBatch_.access$95(SPTBatch_.this)).initialSpotFilterValue = Double.valueOf(Double.parseDouble(SPTBatch_.access$100(SPTBatch_.this)));
/*      */         }
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1032 */       if (SPTBatch_.access$76(SPTBatch_.this).equals("DOG_DETECTOR")) {
/*      */         
/* 1034 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorFactory = (SpotDetectorFactoryBase)new DogDetectorFactory();
/* 1035 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", 
/* 1036 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$96(SPTBatch_.this))));
/* 1037 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("RADIUS", Double.valueOf(Double.parseDouble(SPTBatch_.RADIUS)));
/* 1038 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("TARGET_CHANNEL", 
/* 1039 */             Integer.valueOf(Integer.parseInt(SPTBatch_.access$97(SPTBatch_.this))));
/* 1040 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("THRESHOLD", Double.valueOf(Double.parseDouble(SPTBatch_.access$98(SPTBatch_.this))));
/* 1041 */         (SPTBatch_.access$95(SPTBatch_.this)).detectorSettings.put("DO_MEDIAN_FILTERING", 
/* 1042 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$99(SPTBatch_.this))));
/* 1043 */         if (SPTBatch_.access$100(SPTBatch_.this) != null) {
/* 1044 */           (SPTBatch_.access$95(SPTBatch_.this)).initialSpotFilterValue = Double.valueOf(Double.parseDouble(SPTBatch_.access$100(SPTBatch_.this)));
/*      */         }
/*      */       } 
/* 1047 */       List<FeatureFilter> spotFilters = new ArrayList<>(); int m;
/* 1048 */       for (m = 0; m < spotFilterFeature.size(); m++)
/* 1049 */         spotFilters.add(new FeatureFilter(spotFilterFeature.get(m), 
/* 1050 */               Double.valueOf(spotFilterValue.get(m)).doubleValue(), 
/* 1051 */               Boolean.valueOf(spotFilterAbove.get(m)).booleanValue())); 
/* 1052 */       for (m = 0; m < spotFilters.size(); m++) {
/* 1053 */         SPTBatch_.access$95(SPTBatch_.this).addSpotFilter(spotFilters.get(m));
/*      */       }
/* 1055 */       if (SPTBatch_.access$101(SPTBatch_.this).equals("MANUAL_TRACKER")) {
/*      */         
/* 1057 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new ManualTrackerFactory();
/* 1058 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*      */       } 
/*      */       
/* 1061 */       if (SPTBatch_.access$101(SPTBatch_.this).equals("MANUAL_TRACKER")) {
/*      */         
/* 1063 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new ManualTrackerFactory();
/* 1064 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*      */       } 
/*      */ 
/*      */       
/* 1068 */       if (SPTBatch_.access$101(SPTBatch_.this).equals("KALMAN_TRACKER")) {
/*      */         
/* 1070 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new KalmanTrackerFactory();
/* 1071 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/* 1072 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("KALMAN_SEARCH_RADIUS", 
/* 1073 */             Double.valueOf(Double.parseDouble(SPTBatch_.RADIUS)));
/*      */       } 
/*      */ 
/*      */       
/* 1077 */       if (SPTBatch_.access$101(SPTBatch_.this).equals("SIMPLE_SPARSE_LAP_TRACKER")) {
/*      */         
/* 1079 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new SimpleSparseLAPTrackerFactory();
/* 1080 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/* 1081 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("LINKING_MAX_DISTANCE", 
/* 1082 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$102(SPTBatch_.this))));
/* 1083 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", 
/* 1084 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$103(SPTBatch_.this))));
/* 1085 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("MAX_FRAME_GAP", 
/* 1086 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$104(SPTBatch_.this))));
/*      */       } 
/*      */ 
/*      */       
/* 1090 */       if (SPTBatch_.access$101(SPTBatch_.this).equals("SPARSE_LAP_TRACKER")) {
/* 1091 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new SparseLAPTrackerFactory();
/* 1092 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/* 1093 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("LINKING_MAX_DISTANCE", 
/* 1094 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$102(SPTBatch_.this))));
/* 1095 */         Map<String, Double> linkingPenalty = 
/* 1096 */           (Map<String, Double>)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/*      */               }
/* 1098 */             }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/* 1099 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("ALLOW_GAP_CLOSING", 
/* 1100 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$105(SPTBatch_.this))));
/* 1101 */         if (Boolean.parseBoolean(SPTBatch_.access$105(SPTBatch_.this))) {
/* 1102 */           (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("MAX_FRAME_GAP", 
/* 1103 */               Integer.valueOf(Integer.parseInt(SPTBatch_.access$104(SPTBatch_.this))));
/* 1104 */           (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", 
/* 1105 */               Double.valueOf(Double.parseDouble(SPTBatch_.access$103(SPTBatch_.this))));
/*      */           
/* 1107 */           Map map = 
/* 1108 */             (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/* 1109 */                 } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */         } 
/*      */ 
/*      */         
/* 1113 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("ALLOW_TRACK_SPLITTING", 
/* 1114 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$106(SPTBatch_.this))));
/* 1115 */         if (Boolean.parseBoolean(SPTBatch_.access$106(SPTBatch_.this))) {
/* 1116 */           (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("SPLITTING_MAX_DISTANCE", 
/* 1117 */               Double.valueOf(Double.parseDouble(SPTBatch_.access$107(SPTBatch_.this))));
/* 1118 */           Map map = 
/* 1119 */             (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/* 1120 */                 } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */         } 
/*      */ 
/*      */         
/* 1124 */         (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("ALLOW_TRACK_MERGING", 
/* 1125 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$108(SPTBatch_.this))));
/* 1126 */         if (Boolean.parseBoolean(SPTBatch_.access$108(SPTBatch_.this))) {
/* 1127 */           (SPTBatch_.access$95(SPTBatch_.this)).trackerSettings.put("MERGING_MAX_DISTANCE", 
/* 1128 */               Double.valueOf(Double.parseDouble(SPTBatch_.access$109(SPTBatch_.this))));
/* 1129 */           Map map = 
/* 1130 */             (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/* 1131 */                 } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1136 */       List<FeatureFilter> trackFilters = new ArrayList<>(); int n;
/* 1137 */       for (n = 0; n < trackFilterFeature.size(); n++)
/* 1138 */         trackFilters.add(new FeatureFilter(trackFilterFeature.get(n), 
/* 1139 */               Double.valueOf(trackFilterValue.get(n)).doubleValue(), 
/* 1140 */               Boolean.valueOf(trackFilterAbove.get(n)).booleanValue())); 
/* 1141 */       for (n = 0; n < trackFilters.size(); n++) {
/* 1142 */         SPTBatch_.access$95(SPTBatch_.this).addTrackFilter(trackFilters.get(n));
/*      */       }
/* 1144 */       if (SPTBatch_.checkboxSubBg.isSelected()) {
/* 1145 */         SPTBatch_.slices = SPTBatch_.stack2images(SPTBatch_.impsSubBg);
/* 1146 */         SPTBatch_.slicesIntensityBg = new double[SPTBatch_.slices.length];
/* 1147 */         SPTBatch_.slicesIntensitySpot = new double[SPTBatch_.slices.length];
/* 1148 */         if (SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 0) {
/* 1149 */           SPTBatch_.impMaxProject = ZProjector.run(SPTBatch_.impsSubBg.duplicate(), "max");
/* 1150 */           SPTBatch_.impMaxProject.show();
/* 1151 */           SPTBatch_.roiManager = RoiManager.getInstance();
/* 1152 */           if (SPTBatch_.roiManager == null) {
/* 1153 */             SPTBatch_.roiManager = new RoiManager();
/*      */           }
/* 1155 */           SPTBatch_.roiManager.reset();
/* 1156 */           SPTBatch_.impMaxProject.getCanvas().addMouseListener(new MouseAdapter() {
/*      */                 public void mouseClicked(MouseEvent e) {
/* 1158 */                   if (e.getClickCount() == 2) {
/*      */                     
/* 1160 */                     Roi roi = new Roi(SPTBatch_.impMaxProject.getCanvas().offScreenX(e.getX()), 
/* 1161 */                         SPTBatch_.impMaxProject.getCanvas().offScreenY(e.getY()), 5, 5);
/* 1162 */                     SPTBatch_.impMaxProject.setRoi(roi);
/* 1163 */                     SPTBatch_.roiManager.runCommand(SPTBatch_.impMaxProject, "Show All with labels");
/* 1164 */                     SPTBatch_.roiManager.addRoi(roi);
/*      */                   } 
/*      */                 }
/*      */               });
/*      */ 
/*      */           
/* 1170 */           Dialog4BgSub0 userDialog = new Dialog4BgSub0("Action Required", 
/* 1171 */               "Please select manually areas to measure background.");
/* 1172 */           userDialog.show();
/*      */         } 
/*      */         
/* 1175 */         if (SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 2)
/*      */         {
/*      */           
/* 1178 */           for (n = 0; n < SPTBatch_.slices.length; n++) {
/* 1179 */             ImagePlus slicesDup = SPTBatch_.slices[n].duplicate();
/* 1180 */             IJ.run(slicesDup, "Auto Threshold", "method=Otsu ignore_black white");
/* 1181 */             slicesDup = new ImagePlus(slicesDup.getTitle(), 
/* 1182 */                 Morphology.dilation(slicesDup.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1183 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1184 */             IJ.run(slicesDup, "Fill Holes", "");
/* 1185 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1186 */             IJ.run(slicesDup, "Create Selection", "");
/* 1187 */             Roi roiToMeasure = slicesDup.getRoi();
/* 1188 */             IJ.run(slicesDup, "Make Inverse", "");
/* 1189 */             Roi roiToMeasureInv = slicesDup.getRoi();
/* 1190 */             SPTBatch_.slices[n].setRoi(roiToMeasure);
/* 1191 */             double meanDirect = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1192 */             SPTBatch_.slices[n].setRoi(roiToMeasureInv);
/* 1193 */             double meanInv = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1194 */             if (meanDirect > meanInv) {
/* 1195 */               SPTBatch_.slicesIntensitySpot[n] = meanDirect;
/*      */             } else {
/* 1197 */               SPTBatch_.slicesIntensitySpot[n] = meanInv;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 1202 */         if (SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 3) {
/* 1203 */           for (n = 0; n < SPTBatch_.slices.length; n++) {
/* 1204 */             ImagePlus slicesCell = SPTBatch_.slices[n].duplicate();
/* 1205 */             IJ.run(slicesCell, "Auto Threshold", "method=Otsu ignore_black white");
/* 1206 */             slicesCell = new ImagePlus(slicesCell.getTitle(), 
/* 1207 */                 Morphology.dilation(slicesCell.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1208 */             IJ.run(slicesCell, "Invert LUT", "");
/* 1209 */             IJ.run(slicesCell, "Fill Holes", "");
/* 1210 */             IJ.run(slicesCell, "Invert LUT", "");
/* 1211 */             IJ.run(slicesCell, "Create Selection", "");
/* 1212 */             Roi roiCell = slicesCell.getRoi();
/* 1213 */             SPTBatch_.slices[n].setRoi(roiCell);
/* 1214 */             double meanDirect = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1215 */             IJ.run(slicesCell, "Make Inverse", "");
/* 1216 */             Roi roiCellInv = slicesCell.getRoi();
/* 1217 */             SPTBatch_.slices[n].setRoi(roiCellInv);
/* 1218 */             double meanInv = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1219 */             if (meanDirect > meanInv) {
/* 1220 */               roiCell = roiCell;
/*      */             } else {
/* 1222 */               roiCell = roiCellInv;
/*      */             } 
/*      */             
/* 1225 */             ImagePlus slicesSpot = SPTBatch_.slices[n].duplicate();
/* 1226 */             String value = String.valueOf(Double.valueOf(SPTBatch_.RADIUS).doubleValue() / 
/* 1227 */                 (SPTBatch_.imps.getCalibration()).pixelWidth);
/* 1228 */             if (value.contains(","))
/* 1229 */               value = value.replaceAll(",", "."); 
/* 1230 */             IJ.run(slicesSpot, "Subtract Background...", String.format("rolling=%s", new Object[] { value }));
/* 1231 */             IJ.run(slicesSpot, "Auto Threshold", "method=Otsu ignore_black white");
/* 1232 */             IJ.run(slicesSpot, "Create Selection", "");
/* 1233 */             Roi roiSpots = slicesSpot.getRoi();
/*      */             
/* 1235 */             SPTBatch_.slices[n].setRoi(roiSpots);
/* 1236 */             double meanDirectSpots = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1237 */             IJ.run(slicesSpot, "Make Inverse", "");
/* 1238 */             Roi roiSpotInv = slicesCell.getRoi();
/* 1239 */             SPTBatch_.slices[n].setRoi(roiCellInv);
/* 1240 */             double meanInvSpots = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1241 */             if (meanDirect > meanInv) {
/* 1242 */               roiSpots = roiSpots;
/*      */             } else {
/* 1244 */               roiSpots = roiSpotInv;
/*      */             } 
/*      */             
/* 1247 */             ShapeRoi shapeRoi = (new ShapeRoi(roiCell)).xor(new ShapeRoi(roiSpots));
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
/* 1258 */             SPTBatch_.slices[n].setRoi((Roi)shapeRoi);
/* 1259 */             SPTBatch_.slicesIntensitySpot[n] = (SPTBatch_.slices[n].getStatistics()).mean - 
/* 1260 */               (SPTBatch_.slices[n].getStatistics()).stdDev;
/*      */           } 
/*      */         }
/* 1263 */         if (SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 4)
/*      */         {
/*      */           
/* 1266 */           for (n = 0; n < SPTBatch_.slices.length; n++) {
/* 1267 */             ImagePlus slicesDup = SPTBatch_.slices[n].duplicate();
/* 1268 */             IJ.run(slicesDup, "Auto Threshold", "method=Otsu ignore_black white");
/* 1269 */             slicesDup = new ImagePlus(slicesDup.getTitle(), 
/* 1270 */                 Morphology.dilation(slicesDup.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1271 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1272 */             IJ.run(slicesDup, "Fill Holes", "");
/* 1273 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1274 */             IJ.run(slicesDup, "Create Selection", "");
/*      */             
/* 1276 */             Roi roiToMeasure = slicesDup.getRoi();
/* 1277 */             ImagePlus sliceDup2 = SPTBatch_.slices[n].duplicate();
/* 1278 */             sliceDup2.setRoi(roiToMeasure);
/* 1279 */             IJ.run(sliceDup2, "Clear Outside", "");
/* 1280 */             BackgroundSubtracter bgSubt = new BackgroundSubtracter();
/* 1281 */             ImageProcessor ip = sliceDup2.getProcessor();
/* 1282 */             sliceDup2.getProcessor().resetMinAndMax();
/* 1283 */             boolean isRGB = sliceDup2.getProcessor() instanceof ColorProcessor;
/* 1284 */             boolean calledAsPlugin = true;
/* 1285 */             double radius = Double.valueOf(SPTBatch_.RADIUS).doubleValue() / 
/* 1286 */               (SPTBatch_.imps.getCalibration()).pixelWidth;
/* 1287 */             boolean lightBackground = false;
/* 1288 */             boolean separateColors = false;
/* 1289 */             boolean createBackground = false;
/* 1290 */             boolean useParaboloid = false;
/* 1291 */             boolean doPresmooth = false;
/* 1292 */             boolean previewing = false;
/* 1293 */             boolean correctCorners = true;
/* 1294 */             SPTBatch_.slicesIntensitySpot[n] = bgSubt.rollingBallBackground(ip, radius, createBackground, 
/* 1295 */                 lightBackground, useParaboloid, doPresmooth, correctCorners);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1302 */       SPTBatch_.access$95(SPTBatch_.this).addAllAnalyzers();
/* 1303 */       SPTBatch_.model = new Model();
/* 1304 */       SPTBatch_.access$110(SPTBatch_.this, new TrackMate(SPTBatch_.model, SPTBatch_.access$95(SPTBatch_.this)));
/* 1305 */       Boolean ok = Boolean.valueOf(SPTBatch_.access$111(SPTBatch_.this).checkInput());
/* 1306 */       if (ok != Boolean.TRUE) {
/* 1307 */         SPTBatch_.taskOutput.append(SPTBatch_.access$111(SPTBatch_.this).getErrorMessage());
/*      */       }
/* 1309 */       ok = Boolean.valueOf(SPTBatch_.access$111(SPTBatch_.this).process());
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
/*      */       
/* 1328 */       SPTBatch_.selectionModel = new SelectionModel(SPTBatch_.model);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1351 */       SPTBatch_.model.setLogger(Logger.IJ_LOGGER);
/* 1352 */       SpotCollection spots = SPTBatch_.model.getSpots();
/* 1353 */       SPTBatch_.taskOutput.append(spots.toString());
/* 1354 */       FeatureModel fm = SPTBatch_.model.getFeatureModel();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1359 */       SPTBatch_.this.tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, "TRACK_INDEX", null, null, 
/* 1360 */           Colormap.Turbo, 0.0D, 1.0D);
/* 1361 */       SPTBatch_.taskOutput.append("\n\nSETTINGS:");
/* 1362 */       SPTBatch_.taskOutput.append(SPTBatch_.access$95(SPTBatch_.this).toString());
/*      */       
/* 1364 */       SPTBatch_.taskOutput.append(SPTBatch_.model.toString());
/* 1365 */       SPTBatch_.taskOutput.append("Found" + SPTBatch_.model.getTrackModel().nTracks(true) + " tracks.");
/* 1366 */       SPTBatch_.taskOutput.append(SPTBatch_.access$95(SPTBatch_.this).toStringFeatureAnalyzersInfo());
/*      */       
/* 1368 */       Integer firstFrame = null;
/* 1369 */       Integer lastFrame = null;
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
/* 1380 */       SPTBatch_.this.ds = DisplaySettingsIO.readUserDefault();
/* 1381 */       SPTBatch_.this.ds.setSpotShowName(SPTBatch_.checkDispSpotName.isSelected());
/* 1382 */       SPTBatch_.this.ds.setSpotVisible(SPTBatch_.checkDispSpots.isSelected());
/* 1383 */       SPTBatch_.this.ds.setSpotColorBy(DisplaySettings.TrackMateObject.TRACKS, "TRACK_INDEX");
/* 1384 */       SPTBatch_.this.ds.setTrackVisible(SPTBatch_.checkDispTracks.isSelected());
/* 1385 */       SPTBatch_.this.ds.setTrackColorBy(DisplaySettings.TrackMateObject.TRACKS, "TRACK_INDEX");
/* 1386 */       if (SPTBatch_.access$27(SPTBatch_.this).getSelectedIndex() == 0)
/* 1387 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.FULL); 
/* 1388 */       if (SPTBatch_.access$27(SPTBatch_.this).getSelectedIndex() == 1)
/* 1389 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.LOCAL); 
/* 1390 */       if (SPTBatch_.access$27(SPTBatch_.this).getSelectedIndex() == 2)
/* 1391 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.LOCAL_BACKWARD); 
/* 1392 */       if (SPTBatch_.access$27(SPTBatch_.this).getSelectedIndex() == 3)
/* 1393 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.LOCAL_FORWARD); 
/* 1394 */       SPTBatch_.access$112(SPTBatch_.this, new HyperStackDisplayer(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.imps, SPTBatch_.this.ds));
/* 1395 */       SPTBatch_.access$113(SPTBatch_.this).render();
/* 1396 */       SPTBatch_.access$113(SPTBatch_.this).refresh();
/*      */       
/* 1398 */       if (SPTBatch_.imps.getNFrames() > 1) {
/* 1399 */         firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNFrames(), 1)));
/* 1400 */         lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNFrames(), Math.max(SPTBatch_.imps.getNFrames(), 1)));
/*      */       } 
/* 1402 */       if (SPTBatch_.imps.getNSlices() > 1) {
/* 1403 */         firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNSlices(), 1)));
/* 1404 */         lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNSlices(), Math.max(SPTBatch_.imps.getNSlices(), 1)));
/*      */       } 
/*      */       
/* 1407 */       SPTBatch_.taskOutput.append(
/* 1408 */           "Capturing TrackMate overlay from frame " + firstFrame + " to " + lastFrame + ".\n");
/* 1409 */       Rectangle bounds = SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().getBounds();
/* 1410 */       Integer width = Integer.valueOf(bounds.width);
/* 1411 */       Integer height = Integer.valueOf(bounds.height);
/* 1412 */       Integer nCaptures = Integer.valueOf(lastFrame.intValue() - firstFrame.intValue() + 1);
/* 1413 */       ImageStack stack = new ImageStack(width.intValue(), height.intValue());
/* 1414 */       Integer channel = Integer.valueOf(SPTBatch_.access$113(SPTBatch_.this).getImp().getChannel());
/* 1415 */       Integer slice = Integer.valueOf(SPTBatch_.access$113(SPTBatch_.this).getImp().getSlice());
/* 1416 */       SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(true);
/* 1417 */       for (int frame = firstFrame.intValue(); frame <= lastFrame.intValue(); frame++) {
/*      */         
/* 1419 */         SPTBatch_.access$113(SPTBatch_.this).getImp().setPositionWithoutUpdate(channel.intValue(), slice.intValue(), frame);
/* 1420 */         BufferedImage bi = new BufferedImage(width.intValue(), height.intValue(), 2);
/* 1421 */         SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().paint(bi.getGraphics());
/* 1422 */         ColorProcessor cp = new ColorProcessor(bi);
/* 1423 */         Integer index = Integer.valueOf(SPTBatch_.access$113(SPTBatch_.this).getImp().getStackIndex(channel.intValue(), slice.intValue(), frame));
/* 1424 */         stack.addSlice(SPTBatch_.access$113(SPTBatch_.this).getImp().getImageStack().getSliceLabel(index.intValue()), (ImageProcessor)cp);
/*      */       } 
/* 1426 */       SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(false);
/* 1427 */       SPTBatch_.access$114(SPTBatch_.this, new ImagePlus("TrackMate capture of " + SPTBatch_.access$113(SPTBatch_.this).getImp().getShortTitle(), stack));
/*      */       
/* 1429 */       SPTBatch_.access$116(SPTBatch_.access$113(SPTBatch_.this).getImp(), SPTBatch_.access$115(SPTBatch_.this));
/* 1430 */       SPTBatch_.taskOutput.append(" done.\n");
/*      */ 
/*      */       
/* 1433 */       if (SPTBatch_.checkboxRoi.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         
/* 1435 */         double dx = (SPTBatch_.imps.getCalibration()).pixelWidth;
/* 1436 */         double dy = (SPTBatch_.imps.getCalibration()).pixelHeight;
/* 1437 */         double dz = (SPTBatch_.imps.getCalibration()).pixelDepth;
/* 1438 */         RoiManager roiManager = RoiManager.getInstance();
/* 1439 */         if (roiManager == null) {
/* 1440 */           roiManager = new RoiManager();
/*      */         }
/* 1442 */         roiManager.reset();
/* 1443 */         List<Spot> spotsRoi = new ArrayList<>(SPTBatch_.access$111(SPTBatch_.this).getModel().getSpots().getNSpots(true));
/* 1444 */         for (Integer trackID : SPTBatch_.access$111(SPTBatch_.this).getModel().getTrackModel().trackIDs(true))
/* 1445 */           spotsRoi.addAll(SPTBatch_.access$111(SPTBatch_.this).getModel().getTrackModel().trackSpots(trackID)); 
/* 1446 */         for (int s = 0; s < spotsRoi.size(); s++) {
/* 1447 */           OvalRoi ovalRoi; SpotRoi sroi = ((Spot)spotsRoi.get(s)).getRoi();
/*      */           
/* 1449 */           if (sroi != null) {
/* 1450 */             double[] xs = sroi.toPolygonX(dx, 0.0D, ((Spot)spotsRoi.get(s)).getDoublePosition(0), 1.0D);
/* 1451 */             double[] ys = sroi.toPolygonY(dy, 0.0D, ((Spot)spotsRoi.get(s)).getDoublePosition(1), 1.0D);
/* 1452 */             float[] xp = SPTBatch_.access$117(xs);
/* 1453 */             float[] yp = SPTBatch_.access$117(ys);
/* 1454 */             PolygonRoi polygonRoi = new PolygonRoi(xp, yp, 2);
/*      */           } else {
/* 1456 */             double diameter = 2.0D * ((Spot)spotsRoi.get(s)).getFeature("RADIUS").doubleValue() / dx;
/* 1457 */             double xs = ((Spot)spotsRoi.get(s)).getDoublePosition(0) / dx - diameter / 2.0D + 0.5D;
/* 1458 */             double ys = ((Spot)spotsRoi.get(s)).getDoublePosition(1) / dy - diameter / 2.0D + 0.5D;
/* 1459 */             ovalRoi = new OvalRoi(xs, ys, diameter, diameter);
/*      */           } 
/*      */           
/* 1462 */           int z = 1 + (int)Math.round(((Spot)spotsRoi.get(s)).getDoublePosition(2) / dz);
/* 1463 */           int i1 = 1 + ((Spot)spotsRoi.get(s)).getFeature("FRAME").intValue();
/* 1464 */           ovalRoi.setPosition(0, z, i1);
/* 1465 */           ovalRoi.setName(((Spot)spotsRoi.get(s)).getName());
/* 1466 */           roiManager.addRoi((Roi)ovalRoi);
/*      */         } 
/*      */         
/* 1469 */         if ((roiManager.getRoisAsArray()).length != 0) {
/* 1470 */           roiManager.runCommand("Save", 
/* 1471 */               SPTBatch_.directSPT + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "RoiSet.zip");
/*      */         }
/* 1473 */         roiManager.close();
/*      */       } 
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
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1496 */       if (SPTBatch_.checkbox2.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         
/* 1498 */         SPTBatch_.taskOutput.append(SPTBatch_.model.toString());
/* 1499 */         ISBIChallengeExporterModified.exportToFile(SPTBatch_.model, SPTBatch_.access$95(SPTBatch_.this), new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + 
/* 1500 */               File.separator + "TrackMate_" + SPTBatch_.imps.getShortTitle() + ".xml"));
/*      */ 
/*      */ 
/*      */         
/* 1504 */         SPTBatch_.taskOutput.append("\nDone.");
/*      */       } 
/*      */       
/* 1507 */       if (SPTBatch_.access$118(SPTBatch_.this) == "ST") {
/* 1508 */         Model model = SPTBatch_.access$111(SPTBatch_.this).getModel();
/* 1509 */         Settings settings = SPTBatch_.access$111(SPTBatch_.this).getSettings();
/* 1510 */         SpotCollection spots1 = model.getSpots();
/* 1511 */         int nFrames = spots1.keySet().size();
/* 1512 */         double[][] data = new double[2][nFrames];
/* 1513 */         int indexx = 0;
/* 1514 */         for (Iterator<Integer> iterator = spots1.keySet().iterator(); iterator.hasNext(); ) { int i1 = ((Integer)iterator.next()).intValue();
/* 1515 */           data[1][indexx] = spots1.getNSpots(i1, true);
/* 1516 */           if (data[1][indexx] > 0.0D) {
/* 1517 */             data[0][indexx] = ((Spot)spots1.iterable(i1, false).iterator().next())
/* 1518 */               .getFeature("POSITION_T").doubleValue();
/*      */           } else {
/* 1520 */             data[0][indexx] = i1 * settings.dt;
/*      */           } 
/* 1522 */           indexx++; }
/*      */ 
/*      */         
/* 1525 */         String xAxisLabel = "Time (" + SPTBatch_.access$111(SPTBatch_.this).getModel().getTimeUnits() + ")";
/* 1526 */         String yAxisLabel = "N spots";
/* 1527 */         String title = "Nspots vs Time for " + (SPTBatch_.access$111(SPTBatch_.this).getSettings()).imp.getShortTitle();
/* 1528 */         DefaultXYDataset dataset = new DefaultXYDataset();
/* 1529 */         dataset.addSeries("Nspots", data);
/*      */         
/* 1531 */         SPTBatch_.access$119(SPTBatch_.this, ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, (XYDataset)dataset, 
/* 1532 */               PlotOrientation.VERTICAL, true, true, false));
/* 1533 */         SPTBatch_.access$120(SPTBatch_.this).getTitle().setFont(Fonts.FONT);
/* 1534 */         SPTBatch_.access$120(SPTBatch_.this).getLegend().setItemFont(Fonts.SMALL_FONT);
/* 1535 */         ExportableChartPanel exportableChartPanel = new ExportableChartPanel(SPTBatch_.access$120(SPTBatch_.this));
/*      */       } 
/*      */ 
/*      */       
/* 1539 */       if (SPTBatch_.checkbox1.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1546 */         if (SPTBatch_.access$121(SPTBatch_.this).equals("spotTable")) {
/*      */ 
/*      */ 
/*      */           
/* 1550 */           TablePanel<Spot> spotTable = SPTBatch_.access$122(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 1551 */           JTable spotJTable = spotTable.getTable();
/* 1552 */           TablePanel<Integer> trackTable = SPTBatch_.access$123(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 1553 */           SPTBatch_.trackJTable = trackTable.getTable();
/* 1554 */           SPTBatch_.nOfTracks = new ArrayList<>();
/* 1555 */           for (int t = 0; t < SPTBatch_.trackJTable.getModel().getRowCount(); t++)
/* 1556 */             SPTBatch_.nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(t, 2).toString())); 
/* 1557 */           SPTBatch_.this.indexes = new ArrayList<>();
/* 1558 */           Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
/* 1559 */           Set<Spot> track = null;
/* 1560 */           int counter = 0;
/*      */           
/* 1562 */           for (int i1 = 0; i1 < SPTBatch_.nOfTracks.size(); i1++) {
/* 1563 */             ArrayList<Float> framesByTrack = new ArrayList<>();
/* 1564 */             ArrayList<Float> framesByTrackSort = new ArrayList<>();
/*      */             
/* 1566 */             for (int r = 0; r < spotJTable.getRowCount(); r++) {
/*      */               
/* 1568 */               if (spotJTable.getModel().getValueAt(r, 2).toString()
/* 1569 */                 .equals(String.valueOf(((Integer)SPTBatch_.nOfTracks.get(i1)).intValue()))) {
/* 1570 */                 framesByTrack
/* 1571 */                   .add(Float.valueOf(spotJTable.getModel().getValueAt(r, 8).toString()));
/* 1572 */                 framesByTrackSort
/* 1573 */                   .add(Float.valueOf(spotJTable.getModel().getValueAt(r, 8).toString()));
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1579 */             Collections.sort(framesByTrackSort);
/*      */             
/* 1581 */             for (int z = 0; z < framesByTrackSort.size(); z++) {
/* 1582 */               counter++;
/* 1583 */               if (i1 == 0)
/* 1584 */                 SPTBatch_.this.indexes.add(Integer.valueOf(framesByTrack.indexOf(framesByTrackSort.get(z)))); 
/* 1585 */               if (i1 != 0) {
/* 1586 */                 SPTBatch_.this.indexes.add(
/* 1587 */                     Integer.valueOf(counter - 1 + framesByTrack.indexOf(framesByTrackSort.get(z)) - z));
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1593 */           if (SPTBatch_.checkboxSubBg.isSelected()) {
/*      */             
/* 1595 */             SPTBatch_.columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", 
/* 1596 */                 "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", 
/* 1597 */                 "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", 
/* 1598 */                 "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", 
/* 1599 */                 "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1", "Intensity-Bg Subtract" };
/*      */             
/* 1601 */             String[][] rowDataSpot = new String[SPTBatch_.this.indexes.size()][SPTBatch_.columnNamesSpot.length];
/*      */             int r;
/* 1603 */             for (r = 0; r < SPTBatch_.this.indexes.size(); r++) {
/* 1604 */               rowDataSpot[r][SPTBatch_.columnNamesSpot.length - 1] = "";
/* 1605 */               for (int c = 0; c < spotJTable.getModel().getColumnCount(); c++) {
/* 1606 */                 rowDataSpot[r][c] = String.valueOf(
/* 1607 */                     spotJTable.getModel().getValueAt(((Integer)SPTBatch_.this.indexes.get(r)).intValue(), c));
/*      */               }
/*      */             } 
/*      */             
/* 1611 */             if (SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 1) {
/* 1612 */               for (r = 0; r < rowDataSpot.length; r++) {
/* 1613 */                 rowDataSpot[r][SPTBatch_.columnNamesSpot.length - 1] = 
/* 1614 */                   String.valueOf(Double.valueOf(rowDataSpot[r][12].toString()).doubleValue() - 
/* 1615 */                     Double.valueOf(rowDataSpot[r][19].toString()).doubleValue() * 
/* 1616 */                     Double.valueOf(rowDataSpot[r][17].toString()).doubleValue());
/*      */               }
/* 1618 */               DefaultTableModel tableModel = new DefaultTableModel((Object[][])rowDataSpot, (Object[])SPTBatch_.columnNamesSpot);
/* 1619 */               SPTBatch_.tableSpot = new JTable(tableModel);
/*      */             } 
/*      */             
/* 1622 */             if (SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 0 || SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 2 || 
/* 1623 */               SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 3 || SPTBatch_.access$16(SPTBatch_.this).getSelectedIndex() == 4) {
/*      */               
/* 1625 */               for (r = 0; r < spotJTable.getModel().getRowCount(); r++) {
/* 1626 */                 for (int i2 = 0; i2 < SPTBatch_.slicesIntensitySpot.length; i2++) {
/* 1627 */                   if (Integer.valueOf(rowDataSpot[r][8].toString())
/* 1628 */                     .equals(Integer.valueOf(i2)) == Boolean.TRUE.booleanValue())
/* 1629 */                     rowDataSpot[r][SPTBatch_.columnNamesSpot.length - 1] = 
/* 1630 */                       String.valueOf(Double.valueOf(rowDataSpot[r][12].toString()).doubleValue() - 
/* 1631 */                         Double.valueOf(SPTBatch_.slicesIntensitySpot[i2]).doubleValue()); 
/*      */                 } 
/*      */               } 
/* 1634 */               SPTBatch_.tableSpot = new JTable((Object[][])rowDataSpot, (Object[])SPTBatch_.columnNamesSpot);
/*      */             } 
/*      */ 
/*      */             
/* 1638 */             SPTBatch_.this.exportToCSV(rowDataSpot, SPTBatch_.columnNamesSpot, 
/* 1639 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 1640 */                   "_" + "Spots in tracks statistics" + ".csv"));
/* 1641 */             ResultsTable rtSpotPerImage = new ResultsTable();
/* 1642 */             for (int x = 0; x < rowDataSpot.length; x++) {
/* 1643 */               for (int i2 = 0; i2 < (rowDataSpot[x]).length; i2++)
/* 1644 */                 rtSpotPerImage.setValue(SPTBatch_.columnNamesSpot[i2], x, rowDataSpot[x][i2]); 
/* 1645 */             }  SPTBatch_.this.rtSpots[SPTBatch_.i] = rtSpotPerImage;
/*      */           } 
/* 1647 */           if (!SPTBatch_.checkboxSubBg.isSelected()) {
/*      */             
/* 1649 */             SPTBatch_.columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", 
/* 1650 */                 "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", 
/* 1651 */                 "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", 
/* 1652 */                 "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", 
/* 1653 */                 "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1" };
/*      */             
/* 1655 */             String[][] rowDataSpot = new String[SPTBatch_.this.indexes.size()][SPTBatch_.columnNamesSpot.length];
/*      */             
/* 1657 */             for (int r = 0; r < SPTBatch_.this.indexes.size(); r++) {
/* 1658 */               for (int c = 0; c < spotJTable.getModel().getColumnCount(); c++)
/* 1659 */                 rowDataSpot[r][c] = String.valueOf(
/* 1660 */                     spotJTable.getModel().getValueAt(((Integer)SPTBatch_.this.indexes.get(r)).intValue(), c)); 
/* 1661 */             }  SPTBatch_.tableSpot = new JTable((Object[][])rowDataSpot, (Object[])SPTBatch_.columnNamesSpot);
/* 1662 */             SPTBatch_.this.exportToCSV(rowDataSpot, SPTBatch_.columnNamesSpot, 
/* 1663 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 1664 */                   "_" + "Spots in tracks statistics" + ".csv"));
/* 1665 */             ResultsTable rtSpotPerImage = new ResultsTable();
/* 1666 */             for (int x = 0; x < rowDataSpot.length; x++) {
/* 1667 */               for (int i2 = 0; i2 < (rowDataSpot[x]).length; i2++)
/* 1668 */                 rtSpotPerImage.setValue(SPTBatch_.columnNamesSpot[i2], x, rowDataSpot[x][i2]); 
/* 1669 */             }  SPTBatch_.this.rtSpots[SPTBatch_.i] = rtSpotPerImage;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1674 */         if (SPTBatch_.access$124(SPTBatch_.this).equals("linkTable")) {
/*      */           
/* 1676 */           TablePanel<DefaultWeightedEdge> edgeTable = SPTBatch_.access$125(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 1677 */           SPTBatch_.linkJTable = edgeTable.getTable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1685 */             edgeTable.exportToCsv(new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 1686 */                   SPTBatch_.imps.getShortTitle() + "_" + "Links in tracks statistics" + ".csv"));
/* 1687 */           } catch (IOException e) {
/*      */             
/* 1689 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */         
/* 1693 */         if (SPTBatch_.checkExcludeTracks.isSelected()) {
/* 1694 */           Roi mainRoi; SPTBatch_.this.excludeTrack = new ArrayList<>();
/* 1695 */           SPTBatch_.impMainRoi = ZProjector.run(SPTBatch_.impsSubBg.duplicate(), "max");
/* 1696 */           ImagePlus impToMeasure = SPTBatch_.impMainRoi.duplicate();
/* 1697 */           IJ.run(SPTBatch_.impMainRoi, "Auto Threshold", "method=Otsu ignore_black white");
/* 1698 */           SPTBatch_.impMainRoi = new ImagePlus(SPTBatch_.impMainRoi.getTitle(), 
/* 1699 */               Morphology.dilation(SPTBatch_.impMainRoi.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1700 */           IJ.run(SPTBatch_.impMainRoi, "Invert LUT", "");
/* 1701 */           IJ.run(SPTBatch_.impMainRoi, "Fill Holes", "");
/* 1702 */           IJ.run(SPTBatch_.impMainRoi, "Invert LUT", "");
/* 1703 */           IJ.run(SPTBatch_.impMainRoi, "Create Selection", "");
/* 1704 */           Roi roiToMeasure = SPTBatch_.impMainRoi.getRoi();
/* 1705 */           IJ.run(SPTBatch_.impMainRoi, "Make Inverse", "");
/* 1706 */           Roi roiToMeasureInv = SPTBatch_.impMainRoi.getRoi();
/* 1707 */           impToMeasure.setRoi(roiToMeasure);
/* 1708 */           double meanDirect = (SPTBatch_.impMainRoi.getStatistics()).mean;
/* 1709 */           impToMeasure.setRoi(roiToMeasureInv);
/* 1710 */           double meanInv = (SPTBatch_.impMainRoi.getStatistics()).mean;
/*      */           
/* 1712 */           if (meanDirect > meanInv) {
/* 1713 */             mainRoi = roiToMeasure;
/*      */           } else {
/* 1715 */             mainRoi = roiToMeasureInv;
/*      */           } 
/* 1717 */           for (int i1 = 0; i1 < SPTBatch_.trackJTable.getRowCount(); i1++) {
/* 1718 */             if (mainRoi
/* 1719 */               .contains(
/*      */                 
/* 1721 */                 (int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(SPTBatch_.trackJTable.getModel()
/* 1722 */                     .getValueAt(i1, 
/* 1723 */                       SPTBatch_.trackJTable.convertColumnIndexToModel(13))
/* 1724 */                     .toString())), 
/*      */                 
/* 1726 */                 (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(SPTBatch_.trackJTable.getModel()
/* 1727 */                     .getValueAt(i1, 
/* 1728 */                       SPTBatch_.trackJTable.convertColumnIndexToModel(14))
/* 1729 */                     .toString()))) == Boolean.TRUE.booleanValue()) {
/* 1730 */               SPTBatch_.this.excludeTrack.add(Boolean.valueOf(true));
/*      */             } else {
/*      */               
/* 1733 */               SPTBatch_.this.excludeTrack.add(Boolean.valueOf(false));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1738 */         if (SPTBatch_.access$126(SPTBatch_.this).equals("trackTable")) {
/*      */ 
/*      */           
/* 1741 */           Thread tracksThread = new Thread(new Runnable() {
/*      */                 public void run() {
/* 1743 */                   if (SPTBatch_.checkboxSubBg.isSelected()) {
/* 1744 */                     TablePanel<Integer> trackTable = SPTBatch_.access$123(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 1745 */                     SPTBatch_.trackJTable = trackTable.getTable();
/*      */                     
/* 1747 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1748 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1749 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1750 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1751 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1752 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1753 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1754 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1755 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1756 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1757 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1758 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1759 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1760 */                           "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1761 */                           ")", 
/* 1762 */                           "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 1763 */                           "Movement", "sMSS", "sMSS Movement" }; 
/* 1764 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1765 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1766 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1767 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1768 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1769 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1770 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1771 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1772 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1773 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1774 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1775 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1776 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1777 */                           "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1778 */                           ")", 
/* 1779 */                           "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 1780 */                           "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */                     }
/* 1782 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1783 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1784 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1785 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1786 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1787 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1788 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1789 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1790 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1791 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1792 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1793 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1794 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1795 */                           "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1796 */                           "sMSS Movement" }; 
/* 1797 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1798 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1799 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1800 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1801 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1802 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1803 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1804 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1805 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1806 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1807 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1808 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1809 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1810 */                           "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1811 */                           "sMSS Movement", "Track within Cell" };
/*      */                     }
/* 1813 */                     String[][] rowDataTrack = new String[SPTBatch_.trackJTable
/* 1814 */                         .getRowCount()][SPTBatch_.columnNamesTrack.length];
/* 1815 */                     for (int j = 0; j < SPTBatch_.trackJTable.getRowCount(); j++) {
/* 1816 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "";
/* 1817 */                       for (int c = 0; c < SPTBatch_.trackJTable.getColumnCount(); c++) {
/* 1818 */                         rowDataTrack[j][c] = String.valueOf(SPTBatch_.trackJTable.getValueAt(j, c));
/*      */                       }
/*      */                     } 
/* 1821 */                     List<Integer> nOfTracks = new ArrayList<>();
/* 1822 */                     for (int t = 0; t < SPTBatch_.trackJTable.getRowCount(); t++) {
/* 1823 */                       nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(t, 2).toString()));
/*      */                     }
/*      */                     
/* 1826 */                     List<Double> allTracks = new ArrayList<>();
/* 1827 */                     List<Double> allTracksDef = new ArrayList<>();
/* 1828 */                     for (int k = 0; k < nOfTracks.size(); k++) {
/* 1829 */                       int counter = 0;
/* 1830 */                       List<Double> perTrack = new ArrayList<>();
/* 1831 */                       List<Double> perTrackDef = new ArrayList<>();
/* 1832 */                       for (int m = 0; m < SPTBatch_.tableSpot.getRowCount(); m++) {
/* 1833 */                         if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(m, 2).toString())
/* 1834 */                           .equals(nOfTracks.get(k)) == Boolean.TRUE.booleanValue())
/*      */                         {
/* 1836 */                           perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel()
/* 1837 */                                 .getValueAt(m, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
/*      */                         }
/*      */                       } 
/*      */ 
/*      */                       
/* 1842 */                       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue())
/*      */                       {
/* 1844 */                         for (int n = SPTBatch_.minTracksJTF; n < SPTBatch_.maxTracksJTF; n++)
/*      */                         {
/* 1846 */                           perTrackDef.add(perTrack.get(n));
/*      */                         }
/*      */                       }
/*      */                       
/* 1850 */                       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1851 */                         perTrackDef.size() != 0) {
/* 1852 */                         allTracksDef.add(Double.valueOf(perTrackDef.stream()
/* 1853 */                               .mapToDouble(a -> a.doubleValue()).average().getAsDouble()));
/*      */                       }
/* 1855 */                       if (perTrack.size() != 0)
/* 1856 */                         allTracks.add(Double.valueOf(
/* 1857 */                               perTrack.stream().mapToDouble(a -> a.doubleValue()).average().getAsDouble())); 
/*      */                     } 
/* 1859 */                     ComputeMSD values = new ComputeMSD();
/* 1860 */                     values.Compute(nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 1861 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1862 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/*      */                     {
/* 1864 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1865 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1866 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1867 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1868 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1869 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1870 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1871 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1872 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1873 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1874 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 1875 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1876 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1877 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1878 */                           String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/* 1879 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1880 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1881 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1882 */                             String.valueOf("Long");
/*      */                         } else {
/* 1884 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1885 */                             String.valueOf("Short");
/*      */                         } 
/* 1887 */                         if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1888 */                           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1889 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1890 */                               String.valueOf("Immobile");
/*      */                           }
/* 1892 */                           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1893 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1894 */                               String.valueOf("Mobile"); 
/*      */                         } 
/* 1896 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1897 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */                         
/* 1899 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1900 */                           Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.0D)
/* 1901 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1902 */                             String.valueOf("Confined"); 
/* 1903 */                         if (Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() < 0.9D && 
/* 1904 */                           Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.6D)
/* 1905 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1906 */                             String.valueOf("Anomalous"); 
/* 1907 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1908 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1909 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1910 */                             String.valueOf("Free"); 
/* 1911 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1912 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1913 */                             String.valueOf("Directed");
/*      */                         }
/*      */                         
/* 1916 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 1917 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 1918 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1919 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1920 */                               1] = "Unidirectional Ballistic"; 
/* 1921 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 1922 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 1923 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 1924 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 1925 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 1926 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 1927 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 1928 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1939 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1940 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1941 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1942 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 1943 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1944 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1945 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1946 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1947 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1948 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1949 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1950 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1951 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 1952 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1953 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1954 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1955 */                           String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/*      */                         
/* 1957 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1958 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1959 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1960 */                             String.valueOf("Long");
/*      */                         } else {
/* 1962 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1963 */                             String.valueOf("Short");
/*      */                         } 
/* 1965 */                         if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1966 */                           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1967 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1968 */                               String.valueOf("Immobile");
/*      */                           }
/* 1970 */                           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1971 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1972 */                               String.valueOf("Mobile"); 
/*      */                         } 
/* 1974 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1975 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */                         
/* 1977 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1978 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 1979 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1980 */                             String.valueOf("Confined"); 
/* 1981 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 1982 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 1983 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1984 */                             String.valueOf("Anomalous"); 
/* 1985 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1986 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1987 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1988 */                             String.valueOf("Free"); 
/* 1989 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1990 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1991 */                             String.valueOf("Directed");
/*      */                         }
/*      */                         
/* 1994 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1995 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 1996 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1997 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1998 */                               2] = "Unidirectional Ballistic"; 
/* 1999 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2000 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2001 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2002 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2003 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2004 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2005 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2006 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */                         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2014 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2015 */                           .toString();
/*      */                       } 
/*      */                     }
/*      */                     
/* 2019 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2020 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2021 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2022 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2023 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2024 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2025 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2026 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2027 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2028 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2029 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2030 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2031 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2032 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2033 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 2034 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2035 */                           String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2036 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2037 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2038 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2039 */                           String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/* 2040 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2041 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2042 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2043 */                             String.valueOf("Long");
/*      */                         } else {
/* 2045 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2046 */                             String.valueOf("Short");
/*      */                         } 
/* 2048 */                         if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2049 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2050 */                             String.valueOf("Immobile");
/*      */                         }
/* 2052 */                         if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2053 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2054 */                             String.valueOf("Mobile"); 
/* 2055 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2056 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2057 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2058 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2059 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2060 */                             String.valueOf("Confined"); 
/* 2061 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2062 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2063 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2064 */                             String.valueOf("Anomalous"); 
/* 2065 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2066 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2067 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2068 */                             String.valueOf("Free"); 
/* 2069 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2070 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2071 */                             String.valueOf("Directed");
/*      */                         }
/*      */ 
/*      */                         
/* 2075 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2076 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 2077 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2078 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2079 */                               1] = "Unidirectional Ballistic"; 
/* 2080 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2081 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2082 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2083 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2084 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2085 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2086 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2087 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
/*      */                         }
/*      */                       } 
/*      */                     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2098 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2099 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2100 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2101 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2102 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2103 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2104 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2105 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2106 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2107 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 2108 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 2109 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 2110 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 2111 */                           "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 2112 */                           ")", 
/* 2113 */                           "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 2114 */                           "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */                       
/* 2116 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2117 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 15] = 
/* 2118 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2119 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2120 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2121 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2122 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2123 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2124 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2125 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2126 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 2127 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2128 */                           String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2129 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2130 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2131 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2132 */                           String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/* 2133 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2134 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2135 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2136 */                             String.valueOf("Long");
/*      */                         } else {
/* 2138 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2139 */                             String.valueOf("Short");
/*      */                         } 
/* 2141 */                         if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2142 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2143 */                             String.valueOf("Immobile");
/*      */                         }
/* 2145 */                         if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2146 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2147 */                             String.valueOf("Mobile"); 
/* 2148 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2149 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2150 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2151 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2152 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2153 */                             String.valueOf("Confined"); 
/* 2154 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2155 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2156 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2157 */                             String.valueOf("Anomalous"); 
/* 2158 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2159 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2160 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2161 */                             String.valueOf("Free"); 
/* 2162 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2163 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2164 */                             String.valueOf("Directed");
/*      */                         }
/*      */ 
/*      */                         
/* 2168 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2169 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 2170 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2171 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2172 */                               2] = "Unidirectional Ballistic"; 
/* 2173 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2174 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2175 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2176 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2177 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2178 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2179 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2180 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */                         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2188 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2189 */                           .toString();
/*      */                       } 
/*      */                     } 
/*      */ 
/*      */                     
/* 2194 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2195 */                     for (int x = 0; x < rowDataTrack.length; x++) {
/* 2196 */                       for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2197 */                         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */                     }  try {
/* 2199 */                       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2200 */                           SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2201 */                     } catch (IOException e) {
/*      */                       
/* 2203 */                       e.printStackTrace();
/*      */                     } 
/* 2205 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */                   } 
/*      */                   
/* 2208 */                   if (!SPTBatch_.checkboxSubBg.isSelected()) {
/* 2209 */                     TablePanel<Integer> trackTable = SPTBatch_.access$123(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 2210 */                     ComputeMSD values = new ComputeMSD();
/* 2211 */                     values.Compute(SPTBatch_.nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 2212 */                     JTable trackJTable = trackTable.getTable();
/* 2213 */                     SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2214 */                         "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2215 */                         "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2216 */                         "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2217 */                         "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2218 */                         "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2219 */                         "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2220 */                         "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", 
/* 2221 */                         "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", 
/* 2222 */                         "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", 
/* 2223 */                         "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 2224 */                         "Movement", "sMSS", "sMSS Movement" };
/* 2225 */                     String[][] rowDataTrack = new String[trackJTable.getModel()
/* 2226 */                         .getRowCount()][SPTBatch_.columnNamesTrack.length];
/*      */                     int j;
/* 2228 */                     for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2229 */                       for (int c = 0; c < trackJTable.getModel().getColumnCount(); c++)
/* 2230 */                         rowDataTrack[j][c] = 
/* 2231 */                           String.valueOf(trackJTable.getModel().getValueAt(j, c)); 
/* 2232 */                     }  for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2233 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2234 */                         String.valueOf(((Double)ComputeMSD.msd1Values.get(j)).toString());
/* 2235 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2236 */                         String.valueOf(((Double)ComputeMSD.msd2Values.get(j)).toString());
/* 2237 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2238 */                         String.valueOf(((Double)ComputeMSD.msd3Values.get(j)).toString());
/* 2239 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2240 */                         String.valueOf(((Double)ComputeMSD.msdValues.get(j)).toString());
/* 2241 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2242 */                         String.valueOf(((Double)ComputeMSD.diffValues.get(j)).toString());
/* 2243 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2244 */                         String.valueOf(((Double)ComputeMSD.d14Values.get(j)).toString());
/* 2245 */                       if (Double.valueOf(trackJTable.getModel().getValueAt(j, 3)
/* 2246 */                           .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2247 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Long");
/*      */                       } else {
/* 2249 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Short");
/*      */                       } 
/*      */                       
/* 2252 */                       if (Double.valueOf(((Double)ComputeMSD.d14Values.get(j)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2253 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2254 */                           String.valueOf("Immobile");
/*      */                       }
/* 2256 */                       if (Double.valueOf(((Double)ComputeMSD.d14Values.get(j)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2257 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Mobile"); 
/* 2258 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2259 */                         String.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString());
/* 2260 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.6D && 
/* 2261 */                         Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.0D)
/* 2262 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2263 */                           String.valueOf("Confined"); 
/* 2264 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.9D && 
/* 2265 */                         Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.6D)
/* 2266 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2267 */                           String.valueOf("Anomalous"); 
/* 2268 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 1.1D && 
/* 2269 */                         Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.9D)
/* 2270 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Free"); 
/* 2271 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 1.1D) {
/* 2272 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2273 */                           String.valueOf("Directed");
/*      */                       }
/*      */ 
/*      */                       
/* 2277 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2278 */                         String.valueOf(ComputeMSD.mssValues.get(j));
/* 2279 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 1.0D)
/* 2280 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 
/* 2281 */                             1] = "Unidirectional Ballistic"; 
/* 2282 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 0.0D)
/* 2283 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2284 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() <= 0.55D)
/* 2285 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2286 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 0.45D)
/* 2287 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2288 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 1.0D) {
/* 2289 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
/*      */                       }
/*      */                     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2300 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2301 */                     for (int x = 0; x < rowDataTrack.length; x++) {
/* 2302 */                       for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2303 */                         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */                     }  try {
/* 2305 */                       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2306 */                           SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2307 */                     } catch (IOException e) {
/*      */                       
/* 2309 */                       e.printStackTrace();
/*      */                     } 
/* 2311 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */                   } 
/*      */ 
/*      */                   
/* 2315 */                   String[][] rowData = new String[4][(SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements.length];
/* 2316 */                   int totalTracks = 0;
/* 2317 */                   int longTracks = 0;
/* 2318 */                   int longConfined = 0;
/* 2319 */                   int longUniBal = 0;
/* 2320 */                   int longFree = 0;
/* 2321 */                   int longDirect = 0;
/* 2322 */                   int immob = 0;
/* 2323 */                   int shortTracks = 0;
/* 2324 */                   int shortConfined = 0;
/* 2325 */                   int shortAnom = 0;
/* 2326 */                   int shortFree = 0;
/* 2327 */                   int shortDirect = 0;
/*      */                   
/* 2329 */                   for (int r = 0; r < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size(); r++) {
/* 2330 */                     if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2331 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2332 */                           r) != null)
/* 2333 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2334 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2335 */                           .equals("Long"))
/* 2336 */                           longTracks++;  
/* 2337 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2338 */                           r) != null)
/* 2339 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2340 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2341 */                           .equals("Short"))
/* 2342 */                           shortTracks++;  
/* 2343 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2344 */                           r) != null)
/* 2345 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2346 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2347 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2348 */                             .equals("Confined") && 
/* 2349 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2350 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2351 */                             .equals("Long"))
/* 2352 */                             longConfined++;   
/* 2353 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2354 */                           r) != null)
/* 2355 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2356 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2357 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2358 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2359 */                             .equals("Confined") && 
/* 2360 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2361 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2362 */                             .equals("Short")) {
/* 2363 */                             shortConfined++;
/*      */                           }  
/* 2365 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2366 */                           r) != null)
/* 2367 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2368 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2369 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2370 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2371 */                             .equals("Unidirectional Ballistic") && 
/* 2372 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2373 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2374 */                             .equals("Long"))
/* 2375 */                             longUniBal++;   
/* 2376 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2377 */                           r) != null)
/* 2378 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2379 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2380 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2381 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2382 */                             .equals("Anomalous") && 
/* 2383 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2384 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2385 */                             .equals("Short"))
/* 2386 */                             shortAnom++;   
/* 2387 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2388 */                           r) != null)
/* 2389 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2390 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2391 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2392 */                             .equals("Free") && 
/* 2393 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2394 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2395 */                             .equals("Long"))
/* 2396 */                             longFree++;   
/* 2397 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2398 */                           r) != null)
/* 2399 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2400 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2401 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2402 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2403 */                             .equals("Free") && 
/* 2404 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2405 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2406 */                             .equals("Short"))
/* 2407 */                             shortFree++;   
/* 2408 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2409 */                           r) != null)
/* 2410 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2411 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2412 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2413 */                             .equals("Directed") && 
/* 2414 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2415 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2416 */                             .equals("Long"))
/* 2417 */                             longDirect++;   
/* 2418 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2419 */                           r) != null)
/* 2420 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2421 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2422 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2423 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2424 */                             .equals("Directed") && 
/* 2425 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2426 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2427 */                             .equals("Short"))
/* 2428 */                             shortDirect++;   
/* 2429 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, 
/* 2430 */                           r) != null)
/* 2431 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2432 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, r)
/* 2433 */                           .equals("Immobile"))
/* 2434 */                           immob++;  
/*      */                     } 
/* 2436 */                     if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2437 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2438 */                           r) != null)
/* 2439 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2440 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2441 */                           .equals("Long"))
/* 2442 */                           longTracks++;  
/* 2443 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2444 */                           r) != null)
/* 2445 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2446 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2447 */                           .equals("Short"))
/* 2448 */                           shortTracks++;  
/* 2449 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2450 */                           r) != null)
/* 2451 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2452 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2453 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2454 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2455 */                             .equals("Confined") && 
/* 2456 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2457 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2458 */                             .equals("Long"))
/* 2459 */                             longConfined++;   
/* 2460 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2461 */                           r) != null)
/* 2462 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2463 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2464 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2465 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2466 */                             .equals("Confined") && 
/* 2467 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2468 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2469 */                             .equals("Short")) {
/* 2470 */                             shortConfined++;
/*      */                           }  
/* 2472 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2473 */                           r) != null)
/* 2474 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2475 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2476 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2477 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2478 */                             .equals("Unidirectional Ballistic") && 
/* 2479 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2480 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2481 */                             .equals("Long"))
/* 2482 */                             longUniBal++;   
/* 2483 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2484 */                           r) != null)
/* 2485 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2486 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2487 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2488 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2489 */                             .equals("Anomalous") && 
/* 2490 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2491 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2492 */                             .equals("Short"))
/* 2493 */                             shortAnom++;   
/* 2494 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2495 */                           r) != null)
/* 2496 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2497 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2498 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2499 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2500 */                             .equals("Free") && 
/* 2501 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2502 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2503 */                             .equals("Long"))
/* 2504 */                             longFree++;   
/* 2505 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2506 */                           r) != null)
/* 2507 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2508 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2509 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2510 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2511 */                             .equals("Free") && 
/* 2512 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2513 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2514 */                             .equals("Short"))
/* 2515 */                             shortFree++;   
/* 2516 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2517 */                           r) != null)
/* 2518 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2519 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2520 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2521 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2522 */                             .equals("Directed") && 
/* 2523 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2524 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2525 */                             .equals("Long"))
/* 2526 */                             longDirect++;   
/* 2527 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2528 */                           r) != null)
/* 2529 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2530 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2531 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2532 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2533 */                             .equals("Directed") && 
/* 2534 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2535 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2536 */                             .equals("Short"))
/* 2537 */                             shortDirect++;   
/* 2538 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2539 */                           r) != null) {
/* 2540 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2541 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2542 */                           .equals("Immobile")) {
/* 2543 */                           immob++;
/*      */                         }
/*      */                       }
/*      */                     } 
/*      */                   } 
/* 2548 */                   SPTBatch_.access$129(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)) + (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2549 */                   SPTBatch_.access$131(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$130(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longTracks);
/* 2550 */                   SPTBatch_.access$133(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$132(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longConfined);
/* 2551 */                   SPTBatch_.access$135(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$134(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longUniBal);
/* 2552 */                   SPTBatch_.access$137(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$136(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longFree);
/* 2553 */                   SPTBatch_.access$139(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$138(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longDirect);
/* 2554 */                   SPTBatch_.access$141(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$140(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortTracks);
/* 2555 */                   SPTBatch_.access$143(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$142(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortConfined);
/* 2556 */                   SPTBatch_.access$145(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$144(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortAnom);
/* 2557 */                   SPTBatch_.access$147(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$146(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortFree);
/* 2558 */                   SPTBatch_.access$149(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$148(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortDirect);
/* 2559 */                   SPTBatch_.access$151(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$150(SPTBatch_.null.access$8(SPTBatch_.null.this)) + immob);
/*      */                   
/* 2561 */                   rowData[0][0] = String.valueOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2562 */                   rowData[1][0] = String.valueOf("");
/* 2563 */                   rowData[2][0] = String.valueOf("");
/* 2564 */                   rowData[3][0] = String.valueOf("");
/* 2565 */                   rowData[0][1] = String.valueOf(longTracks);
/* 2566 */                   rowData[1][1] = String.valueOf(" ");
/* 2567 */                   rowData[2][1] = String.valueOf("Short Tracks");
/* 2568 */                   rowData[3][1] = String.valueOf(shortTracks);
/* 2569 */                   rowData[0][2] = String.valueOf(longConfined);
/* 2570 */                   rowData[1][2] = String.valueOf(" ");
/* 2571 */                   rowData[2][2] = String.valueOf("Short Confined");
/* 2572 */                   rowData[3][2] = String.valueOf(shortConfined);
/* 2573 */                   rowData[0][3] = String.valueOf(longUniBal);
/* 2574 */                   rowData[1][3] = String.valueOf(" ");
/* 2575 */                   rowData[2][3] = String.valueOf("Short Anomalous");
/* 2576 */                   rowData[3][3] = String.valueOf(shortAnom);
/* 2577 */                   rowData[0][4] = String.valueOf(longFree);
/* 2578 */                   rowData[1][4] = String.valueOf(" ");
/* 2579 */                   rowData[2][4] = String.valueOf("Short Free");
/* 2580 */                   rowData[3][4] = String.valueOf(shortFree);
/* 2581 */                   rowData[0][5] = String.valueOf(longDirect);
/* 2582 */                   rowData[1][5] = String.valueOf(" ");
/* 2583 */                   rowData[2][5] = String.valueOf("Short Direct");
/* 2584 */                   rowData[3][5] = String.valueOf(shortDirect);
/* 2585 */                   rowData[0][6] = String.valueOf(immob);
/* 2586 */                   rowData[1][6] = String.valueOf("");
/* 2587 */                   rowData[2][6] = String.valueOf("");
/* 2588 */                   rowData[3][6] = String.valueOf("");
/*      */                   
/* 2590 */                   ResultsTable rtTrackSum = new ResultsTable();
/* 2591 */                   for (int i = 0; i < rowData.length; i++) {
/* 2592 */                     for (int c = 0; c < (rowData[i]).length; c++)
/* 2593 */                       rtTrackSum.setValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements[c], i, rowData[i][c]); 
/*      */                   } 
/*      */                   try {
/* 2596 */                     rtTrackSum.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2597 */                         SPTBatch_.imps.getShortTitle() + "_" + "SummaryTracks" + ".csv");
/* 2598 */                   } catch (IOException e) {
/*      */                     
/* 2600 */                     e.printStackTrace();
/*      */                   } 
/*      */ 
/*      */                   
/* 2604 */                   if (SPTBatch_.checkPBS.isSelected())
/* 2605 */                     (new PhotobleachingSpotPlot()).Compute(); 
/*      */                 }
/*      */               });
/* 2608 */           tracksThread.start();
/*      */         } 
/* 2610 */         if (SPTBatch_.access$152(SPTBatch_.this).equals("branchTable")) {
/* 2611 */           TablePanel<SPTBatch_.Branch> branchTable = SPTBatch_.createBranchTable(SPTBatch_.model, SPTBatch_.selectionModel);
/*      */           
/*      */           try {
/* 2614 */             branchTable.exportToCsv(new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2615 */                   SPTBatch_.imps.getShortTitle() + "_" + "Branch analysis" + ".csv"));
/* 2616 */           } catch (IOException e) {
/*      */             
/* 2618 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2624 */       if (SPTBatch_.checkboxSP.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2625 */         SPTBatch_.directChemo = new File(String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "Chemotaxis_Analysis");
/*      */         
/* 2627 */         if (!SPTBatch_.directChemo.exists()) {
/* 2628 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directChemo.getName());
/* 2629 */           boolean result = false;
/*      */           
/*      */           try {
/* 2632 */             SPTBatch_.directChemo.mkdir();
/* 2633 */             result = true;
/* 2634 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/* 2637 */           if (result) {
/* 2638 */             SPTBatch_.taskOutput.append("DIR created");
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2643 */         TablePanel<Spot> spotTable = SPTBatch_.access$122(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 2644 */         JTable spotJTable = spotTable.getTable();
/* 2645 */         ArrayList<Float> importedData = new ArrayList<>();
/*      */         
/* 2647 */         Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
/* 2648 */         Set<Spot> track = null;
/* 2649 */         for (Integer id : trackIDs) {
/*      */           
/* 2651 */           track = SPTBatch_.model.getTrackModel().trackSpots(id);
/* 2652 */           ArrayList<Float> framesByTrack = new ArrayList<>();
/* 2653 */           ArrayList<Float> xByTrack = new ArrayList<>();
/* 2654 */           ArrayList<Float> yByTrack = new ArrayList<>();
/* 2655 */           ArrayList<Float> framesByTrackSort = new ArrayList<>();
/* 2656 */           ArrayList<Float> xByTrackSort = new ArrayList<>();
/* 2657 */           ArrayList<Float> yByTrackSort = new ArrayList<>();
/* 2658 */           ArrayList<Float> trackID = new ArrayList<>();
/* 2659 */           ArrayList<Integer> indexes = new ArrayList<>();
/* 2660 */           for (Spot spot : track) {
/* 2661 */             trackID.add(Float.valueOf(Float.valueOf(id.intValue()).floatValue() + Float.valueOf("1.0").floatValue()));
/* 2662 */             framesByTrack.add(Float.valueOf(spot.getFeature("FRAME").toString()));
/* 2663 */             xByTrack.add(Float.valueOf(spot.getFeature("POSITION_X").toString()));
/* 2664 */             yByTrack.add(Float.valueOf(spot.getFeature("POSITION_Y").toString()));
/* 2665 */             framesByTrackSort.add(Float.valueOf(spot.getFeature("FRAME").toString()));
/*      */           } 
/*      */           
/* 2668 */           Collections.sort(framesByTrackSort);
/* 2669 */           for (int z = 0; z < framesByTrackSort.size(); z++)
/* 2670 */             indexes.add(Integer.valueOf(framesByTrack.indexOf(framesByTrackSort.get(z)))); 
/* 2671 */           for (int i1 = 0; i1 < indexes.size(); i1++) {
/* 2672 */             importedData.add(trackID.get(i1));
/* 2673 */             importedData.add(Float.valueOf(((Float)framesByTrackSort.get(i1)).floatValue() + Float.valueOf("1.0").floatValue()));
/* 2674 */             importedData.add(xByTrack.get(((Integer)indexes.get(i1)).intValue()));
/* 2675 */             importedData.add(yByTrack.get(((Integer)indexes.get(i1)).intValue()));
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2681 */         Chemotaxis_ToolModified chemotool = new Chemotaxis_ToolModified(importedData);
/* 2682 */         chemotool.run("");
/*      */       } 
/*      */       
/* 2685 */       if (SPTBatch_.checkboxDiff.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2686 */         SPTBatch_.directDiff = new File(
/* 2687 */             String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "Trajectory_Classifier");
/*      */         
/* 2689 */         if (!SPTBatch_.directDiff.exists()) {
/* 2690 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directDiff.getName());
/* 2691 */           boolean result = false;
/*      */           
/*      */           try {
/* 2694 */             SPTBatch_.directDiff.mkdir();
/* 2695 */             result = true;
/* 2696 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/* 2699 */           if (result) {
/* 2700 */             SPTBatch_.taskOutput.append("DIR created");
/*      */           }
/*      */         } 
/*      */         
/* 2704 */         TraJClassifierTest_ tc = new TraJClassifierTest_();
/* 2705 */         tc.run("");
/*      */       } 
/*      */ 
/*      */       
/* 2709 */       if (SPTBatch_.checkboxMSD.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2710 */         TrackProcessorMSD_Modified msdPlot = new TrackProcessorMSD_Modified();
/* 2711 */         msdPlot.Compute(SPTBatch_.nOfTracks, SPTBatch_.this.rtSpots[SPTBatch_.i]);
/*      */       } 
/*      */       
/* 2714 */       if (SPTBatch_.checkCluster.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2715 */         ClusterSizeAnalysis clusterAnal = new ClusterSizeAnalysis();
/*      */         
/* 2717 */         DefaultCategoryDataset barDatasetCount = new DefaultCategoryDataset();
/* 2718 */         DefaultCategoryDataset barDatasetPercet = new DefaultCategoryDataset();
/* 2719 */         int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0, counter6 = 0;
/* 2720 */         int counter7 = 0, counter8 = 0, counter9 = 0, counter10 = 0;
/* 2721 */         for (int r = 0; r < SPTBatch_.nOfTracks.size(); r++) {
/* 2722 */           int counter = 0;
/* 2723 */           List<Double> perTrack = new ArrayList<>();
/* 2724 */           for (int t = 0; t < SPTBatch_.tableSpot.getRowCount(); t++) {
/* 2725 */             if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t, 2).toString())
/* 2726 */               .equals(SPTBatch_.nOfTracks.get(r)) == Boolean.TRUE.booleanValue())
/*      */             {
/* 2728 */               perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel()
/* 2729 */                     .getValueAt(t, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 2734 */           clusterAnal.Compute(perTrack, SPTBatch_.nOfTracks.get(r));
/*      */ 
/*      */           
/* 2737 */           double[] values = new double[perTrack.size()];
/* 2738 */           for (int i1 = 0; i1 < perTrack.size(); i1++)
/* 2739 */             values[i1] = ((Double)perTrack.get(i1)).doubleValue(); 
/* 2740 */           GaussianMixtureModified gm2 = GaussianMixtureModified.fit(values);
/*      */           
/* 2742 */           if (gm2.components.length == 1) {
/* 2743 */             counter1++;
/*      */           }
/* 2745 */           if (gm2.components.length == 2) {
/* 2746 */             counter2++;
/*      */           }
/* 2748 */           if (gm2.components.length == 3) {
/* 2749 */             counter3++;
/*      */           }
/* 2751 */           if (gm2.components.length == 4) {
/* 2752 */             counter4++;
/*      */           }
/* 2754 */           if (gm2.components.length == 5) {
/* 2755 */             counter5++;
/*      */           }
/* 2757 */           if (gm2.components.length == 6) {
/* 2758 */             counter6++;
/*      */           }
/* 2760 */           if (gm2.components.length == 7) {
/* 2761 */             counter7++;
/*      */           }
/* 2763 */           if (gm2.components.length == 8) {
/* 2764 */             counter8++;
/*      */           }
/* 2766 */           if (gm2.components.length == 9) {
/* 2767 */             counter9++;
/*      */           }
/* 2769 */           if (gm2.components.length == 10) {
/* 2770 */             counter10++;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2775 */         barDatasetCount.setValue(counter1, "receptors/particle", "1");
/* 2776 */         barDatasetCount.setValue(counter2, "receptors/particle", "2");
/* 2777 */         barDatasetCount.setValue(counter3, "receptors/particle", "3");
/* 2778 */         barDatasetCount.setValue(counter4, "receptors/particle", "4");
/* 2779 */         barDatasetCount.setValue(counter5, "receptors/particle", "5");
/* 2780 */         barDatasetCount.setValue(counter6, "receptors/particle", "6");
/* 2781 */         barDatasetCount.setValue(counter7, "receptors/particle", "7");
/* 2782 */         barDatasetCount.setValue(counter8, "receptors/particle", "8");
/* 2783 */         barDatasetCount.setValue(counter9, "receptors/particle", "9");
/* 2784 */         barDatasetCount.setValue(counter10, "receptors/particle", "10");
/* 2785 */         JFreeChart chartCount = ChartFactory.createBarChart("Count of receptors/particle", 
/* 2786 */             "receptors/particle", "Count", (CategoryDataset)barDatasetCount, PlotOrientation.VERTICAL, false, true, 
/* 2787 */             false);
/*      */         
/* 2789 */         if (SPTBatch_.nOfTracks.size() != 0) {
/* 2790 */           barDatasetPercet.setValue((counter1 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "1");
/* 2791 */           barDatasetPercet.setValue((counter2 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "2");
/* 2792 */           barDatasetPercet.setValue((counter3 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "3");
/* 2793 */           barDatasetPercet.setValue((counter4 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "4");
/* 2794 */           barDatasetPercet.setValue((counter5 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "5");
/* 2795 */           barDatasetPercet.setValue((counter6 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "6");
/* 2796 */           barDatasetPercet.setValue((counter7 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "7");
/* 2797 */           barDatasetPercet.setValue((counter8 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "8");
/* 2798 */           barDatasetPercet.setValue((counter9 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "9");
/* 2799 */           barDatasetPercet.setValue((counter10 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "10");
/*      */         } 
/* 2801 */         if (SPTBatch_.nOfTracks.size() != 0) {
/*      */           
/* 2803 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "1");
/* 2804 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "2");
/* 2805 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "3");
/* 2806 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "4");
/* 2807 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "5");
/* 2808 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "6");
/* 2809 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "7");
/* 2810 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "8");
/* 2811 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "9");
/* 2812 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "10");
/*      */         } 
/*      */         
/* 2815 */         JFreeChart chartPercet = ChartFactory.createBarChart("Percentage of receptors/particle", 
/* 2816 */             "receptors/particle", "Percentage-(%)", (CategoryDataset)barDatasetPercet, PlotOrientation.VERTICAL, 
/* 2817 */             false, true, false);
/* 2818 */         DecimalFormat pctFormat = new DecimalFormat("##.00%");
/* 2819 */         pctFormat.setMultiplier(1);
/* 2820 */         NumberFormat percent = NumberFormat.getPercentInstance();
/* 2821 */         percent.setMaximumFractionDigits(2);
/* 2822 */         StandardCategoryItemLabelGenerator standardCategoryItemLabelGenerator1 = new StandardCategoryItemLabelGenerator("{2}", 
/* 2823 */             NumberFormat.getInstance(), percent);
/* 2824 */         StandardCategoryItemLabelGenerator standardCategoryItemLabelGenerator2 = new StandardCategoryItemLabelGenerator("{2}", 
/* 2825 */             pctFormat);
/*      */         
/* 2827 */         CategoryPlot plotCount = chartCount.getCategoryPlot();
/* 2828 */         CategoryPlot plotPercent = chartPercet.getCategoryPlot();
/* 2829 */         CategoryItemRenderer rendererCount = plotCount.getRenderer();
/* 2830 */         CategoryItemRenderer rendererPercent = plotPercent.getRenderer();
/*      */         
/* 2832 */         NumberAxis rangeAxisCount = (NumberAxis)plotCount.getRangeAxis();
/* 2833 */         NumberAxis rangeAxisPercent = (NumberAxis)plotPercent.getRangeAxis();
/* 2834 */         rangeAxisPercent.setNumberFormatOverride(pctFormat);
/* 2835 */         rangeAxisCount.setAutoRangeIncludesZero(true);
/* 2836 */         rangeAxisPercent.setAutoRangeIncludesZero(true);
/* 2837 */         rendererCount.setDefaultItemLabelGenerator((CategoryItemLabelGenerator)standardCategoryItemLabelGenerator1);
/* 2838 */         rendererCount.setDefaultItemLabelFont(new Font("SansSerif", 0, 12));
/*      */         
/* 2840 */         rendererCount.setDefaultItemLabelsVisible(true);
/* 2841 */         rendererPercent.setDefaultItemLabelGenerator((CategoryItemLabelGenerator)standardCategoryItemLabelGenerator2);
/* 2842 */         rendererPercent.setDefaultItemLabelFont(new Font("SansSerif", 0, 12));
/*      */         
/* 2844 */         rendererPercent.setDefaultItemLabelsVisible(true);
/*      */         
/*      */         try {
/* 2847 */           ChartUtils.saveChartAsPNG(
/* 2848 */               new File(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + 
/* 2849 */                 SPTBatch_.imps.getShortTitle() + "_ReceptorsPerParticle_Count" + ".png"), 
/* 2850 */               chartCount, 500, 400);
/* 2851 */         } catch (IOException e) {
/*      */           
/* 2853 */           e.printStackTrace();
/*      */         } 
/*      */         
/*      */         try {
/* 2857 */           ChartUtils.saveChartAsPNG(
/* 2858 */               new File(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + 
/* 2859 */                 SPTBatch_.imps.getShortTitle() + 
/* 2860 */                 "_ReceptorsPerParticle_Percentage" + ".png"), 
/* 2861 */               chartPercet, 500, 400);
/* 2862 */         } catch (IOException e) {
/*      */           
/* 2864 */           e.printStackTrace();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2869 */       if (SPTBatch_.checkboxPlot.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */ 
/*      */ 
/*      */         
/* 2873 */         List<Spot> spots1 = new ArrayList<>(SPTBatch_.access$111(SPTBatch_.this).getModel().getSpots().getNSpots(true));
/* 2874 */         Set<String> ySelectedSpotSet = new HashSet<>();
/* 2875 */         ySelectedSpotSet.add(SPTBatch_.access$153(SPTBatch_.this));
/* 2876 */         for (Integer trackID : SPTBatch_.access$111(SPTBatch_.this).getModel().getTrackModel().trackIDs(true)) {
/* 2877 */           spots1.addAll(SPTBatch_.access$111(SPTBatch_.this).getModel().getTrackModel().trackSpots(trackID));
/*      */         }
/* 2879 */         if (SPTBatch_.ESP.isSelected() && SPTBatch_.access$154(SPTBatch_.this) != null && ySelectedSpotSet != null) {
/* 2880 */           JFreeChart chart = null;
/* 2881 */           XYPlot xYPlot = null;
/* 2882 */           Dimension xDimension = (Dimension)SPTBatch_.model.getFeatureModel()
/* 2883 */             .getSpotFeatureDimensions().get(SPTBatch_.access$154(SPTBatch_.this));
/* 2884 */           Map<String, Dimension> yDimensions = SPTBatch_.model.getFeatureModel()
/* 2885 */             .getSpotFeatureDimensions();
/* 2886 */           Map<String, String> featureNames = SPTBatch_.model.getFeatureModel().getSpotFeatureNames();
/*      */           
/* 2888 */           String str = String.valueOf(featureNames.get(SPTBatch_.access$154(SPTBatch_.this))) + " (" + 
/* 2889 */             TMUtils.getUnitsFor(xDimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + 
/* 2890 */             ")";
/*      */ 
/*      */           
/* 2893 */           Set<Dimension> set = SPTBatch_.access$155(SPTBatch_.this, ySelectedSpotSet, 
/* 2894 */               yDimensions);
/*      */ 
/*      */           
/* 2897 */           ArrayList<ExportableChartPanel> arrayList = new ArrayList<>(set.size());
/* 2898 */           for (Dimension dimension : set) {
/*      */ 
/*      */             
/* 2901 */             String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 2902 */                 SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */             
/* 2905 */             List<String> featuresThisDimension = SPTBatch_.access$156(dimension, ySelectedSpotSet, 
/* 2906 */                 yDimensions);
/*      */ 
/*      */             
/* 2909 */             String title = SPTBatch_.access$157(SPTBatch_.this, featuresThisDimension, featureNames, SPTBatch_.access$154(SPTBatch_.this));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2914 */             SpotCollectionDataset spotCollectionDataset = new SpotCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, 
/* 2915 */                 SPTBatch_.access$154(SPTBatch_.this), featuresThisDimension, spots1, true);
/*      */             
/* 2917 */             XYItemRenderer renderer = spotCollectionDataset.getRenderer();
/*      */ 
/*      */             
/* 2920 */             chart = ChartFactory.createXYLineChart(title, str, yAxisLabel, (XYDataset)spotCollectionDataset, 
/* 2921 */                 PlotOrientation.VERTICAL, true, true, false);
/* 2922 */             chart.getTitle().setFont(Fonts.FONT);
/* 2923 */             chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 2924 */             chart.setBackgroundPaint(new Color(220, 220, 220));
/* 2925 */             chart.setBorderVisible(false);
/* 2926 */             chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */             
/* 2929 */             xYPlot = chart.getXYPlot();
/* 2930 */             xYPlot.setRenderer(renderer);
/* 2931 */             xYPlot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 2932 */             xYPlot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 2933 */             xYPlot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 2934 */             xYPlot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 2935 */             xYPlot.setOutlineVisible(false);
/* 2936 */             xYPlot.setDomainCrosshairVisible(false);
/* 2937 */             xYPlot.setDomainGridlinesVisible(false);
/* 2938 */             xYPlot.setRangeCrosshairVisible(false);
/* 2939 */             xYPlot.setRangeGridlinesVisible(false);
/* 2940 */             xYPlot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */             
/* 2943 */             ((NumberAxis)xYPlot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */             
/* 2946 */             xYPlot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 2947 */             xYPlot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */           } 
/*      */ 
/*      */           
/*      */           try {
/* 2952 */             ChartUtils.saveChartAsPNG(
/* 2953 */                 new File(SPTBatch_.directSPT + File.separator + SPTBatch_.imps.getShortTitle() + "_" + 
/* 2954 */                   SPTBatch_.access$154(SPTBatch_.this) + "-" + SPTBatch_.access$153(SPTBatch_.this) + ".png"), 
/* 2955 */                 xYPlot.getChart(), 500, 270);
/* 2956 */           } catch (IOException e) {
/*      */             
/* 2958 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2964 */         List<DefaultWeightedEdge> edges = new ArrayList<>();
/* 2965 */         Set<String> ySelectedLinkSet = new HashSet<>();
/* 2966 */         ySelectedLinkSet.add(SPTBatch_.access$158(SPTBatch_.this));
/* 2967 */         for (Integer trackID : SPTBatch_.access$111(SPTBatch_.this).getModel().getTrackModel().trackIDs(true)) {
/* 2968 */           edges.addAll(SPTBatch_.access$111(SPTBatch_.this).getModel().getTrackModel().trackEdges(trackID));
/*      */         }
/*      */         
/* 2971 */         if (SPTBatch_.ELP.isSelected() && SPTBatch_.access$159(SPTBatch_.this) != null && ySelectedLinkSet != null) {
/*      */           
/* 2973 */           JFreeChart chart = null;
/* 2974 */           XYPlot xYPlot = null;
/* 2975 */           Dimension xDimension = (Dimension)SPTBatch_.model.getFeatureModel()
/* 2976 */             .getEdgeFeatureDimensions().get(SPTBatch_.access$159(SPTBatch_.this));
/* 2977 */           Map<String, Dimension> yDimensions = SPTBatch_.model.getFeatureModel()
/* 2978 */             .getEdgeFeatureDimensions();
/* 2979 */           Map<String, String> featureNames = SPTBatch_.model.getFeatureModel().getEdgeFeatureNames();
/*      */           
/* 2981 */           String str = String.valueOf(featureNames.get(SPTBatch_.access$159(SPTBatch_.this))) + " (" + 
/* 2982 */             TMUtils.getUnitsFor(xDimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + 
/* 2983 */             ")";
/*      */ 
/*      */           
/* 2986 */           Set<Dimension> set = SPTBatch_.access$155(SPTBatch_.this, ySelectedLinkSet, 
/* 2987 */               yDimensions);
/*      */ 
/*      */           
/* 2990 */           ArrayList<ExportableChartPanel> arrayList = new ArrayList<>(set.size());
/* 2991 */           for (Dimension dimension : set) {
/*      */ 
/*      */             
/* 2994 */             String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 2995 */                 SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */             
/* 2998 */             List<String> featuresThisDimension = SPTBatch_.access$156(dimension, ySelectedLinkSet, 
/* 2999 */                 yDimensions);
/*      */ 
/*      */             
/* 3002 */             String title = SPTBatch_.access$157(SPTBatch_.this, featuresThisDimension, featureNames, SPTBatch_.access$159(SPTBatch_.this));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3007 */             EdgeCollectionDataset edgeCollectionDataset = new EdgeCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, 
/* 3008 */                 SPTBatch_.access$159(SPTBatch_.this), featuresThisDimension, edges, true);
/* 3009 */             XYItemRenderer renderer = edgeCollectionDataset.getRenderer();
/*      */ 
/*      */             
/* 3012 */             chart = ChartFactory.createXYLineChart(title, str, yAxisLabel, (XYDataset)edgeCollectionDataset, 
/* 3013 */                 PlotOrientation.VERTICAL, true, true, false);
/* 3014 */             chart.getTitle().setFont(Fonts.FONT);
/* 3015 */             chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 3016 */             chart.setBackgroundPaint(new Color(220, 220, 220));
/* 3017 */             chart.setBorderVisible(false);
/* 3018 */             chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */             
/* 3021 */             xYPlot = chart.getXYPlot();
/* 3022 */             xYPlot.setRenderer(renderer);
/* 3023 */             xYPlot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 3024 */             xYPlot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3025 */             xYPlot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 3026 */             xYPlot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3027 */             xYPlot.setOutlineVisible(false);
/* 3028 */             xYPlot.setDomainCrosshairVisible(false);
/* 3029 */             xYPlot.setDomainGridlinesVisible(false);
/* 3030 */             xYPlot.setRangeCrosshairVisible(false);
/* 3031 */             xYPlot.setRangeGridlinesVisible(false);
/* 3032 */             xYPlot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */             
/* 3035 */             ((NumberAxis)xYPlot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */             
/* 3038 */             xYPlot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 3039 */             xYPlot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */           } 
/*      */           
/*      */           try {
/* 3043 */             ChartUtils.saveChartAsPNG(
/* 3044 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 3045 */                   "_" + SPTBatch_.access$159(SPTBatch_.this) + "-" + SPTBatch_.access$158(SPTBatch_.this) + ".png"), 
/* 3046 */                 xYPlot.getChart(), 500, 270);
/* 3047 */           } catch (IOException e) {
/*      */             
/* 3049 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3055 */         Set<String> ySelectedTrackSet = new HashSet<>();
/* 3056 */         ySelectedTrackSet.add(SPTBatch_.access$160(SPTBatch_.this));
/*      */         
/* 3058 */         if (SPTBatch_.ETP.isSelected() && SPTBatch_.access$161(SPTBatch_.this) != null && ySelectedTrackSet != null) {
/* 3059 */           SPTBatch_.this.tracksID = new ArrayList<>(SPTBatch_.access$111(SPTBatch_.this).getModel().getTrackModel().unsortedTrackIDs(true));
/* 3060 */           JFreeChart chart = null;
/* 3061 */           XYPlot xYPlot = null;
/* 3062 */           Dimension xDimension = (Dimension)SPTBatch_.model.getFeatureModel()
/* 3063 */             .getTrackFeatureDimensions().get(SPTBatch_.access$161(SPTBatch_.this));
/* 3064 */           Map<String, Dimension> yDimensions = SPTBatch_.model.getFeatureModel()
/* 3065 */             .getTrackFeatureDimensions();
/* 3066 */           Map<String, String> featureNames = SPTBatch_.model.getFeatureModel().getTrackFeatureNames();
/*      */           
/* 3068 */           String str = String.valueOf(featureNames.get(SPTBatch_.access$161(SPTBatch_.this))) + " (" + 
/* 3069 */             TMUtils.getUnitsFor(xDimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + 
/* 3070 */             ")";
/*      */ 
/*      */           
/* 3073 */           Set<Dimension> set = SPTBatch_.access$155(SPTBatch_.this, ySelectedTrackSet, 
/* 3074 */               yDimensions);
/*      */ 
/*      */           
/* 3077 */           ArrayList<ExportableChartPanel> arrayList = new ArrayList<>(set.size());
/* 3078 */           for (Dimension dimension : set) {
/*      */ 
/*      */             
/* 3081 */             String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 3082 */                 SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */             
/* 3085 */             List<String> featuresThisDimension = SPTBatch_.access$156(dimension, ySelectedTrackSet, 
/* 3086 */                 yDimensions);
/*      */ 
/*      */             
/* 3089 */             String title = SPTBatch_.access$157(SPTBatch_.this, featuresThisDimension, featureNames, 
/* 3090 */                 SPTBatch_.access$161(SPTBatch_.this));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3095 */             TrackCollectionDataset trackCollectionDataset = new TrackCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, 
/* 3096 */                 SPTBatch_.access$161(SPTBatch_.this), featuresThisDimension, SPTBatch_.this.tracksID);
/* 3097 */             XYItemRenderer renderer = trackCollectionDataset.getRenderer();
/*      */ 
/*      */             
/* 3100 */             chart = ChartFactory.createXYLineChart(title, str, yAxisLabel, (XYDataset)trackCollectionDataset, 
/* 3101 */                 PlotOrientation.VERTICAL, true, true, false);
/* 3102 */             chart.getTitle().setFont(Fonts.FONT);
/* 3103 */             chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 3104 */             chart.setBackgroundPaint(new Color(220, 220, 220));
/* 3105 */             chart.setBorderVisible(false);
/* 3106 */             chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */             
/* 3109 */             xYPlot = chart.getXYPlot();
/* 3110 */             xYPlot.setRenderer(renderer);
/* 3111 */             xYPlot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 3112 */             xYPlot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3113 */             xYPlot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 3114 */             xYPlot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3115 */             xYPlot.setOutlineVisible(false);
/* 3116 */             xYPlot.setDomainCrosshairVisible(false);
/* 3117 */             xYPlot.setDomainGridlinesVisible(false);
/* 3118 */             xYPlot.setRangeCrosshairVisible(false);
/* 3119 */             xYPlot.setRangeGridlinesVisible(false);
/* 3120 */             xYPlot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */             
/* 3123 */             ((NumberAxis)xYPlot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */             
/* 3126 */             xYPlot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 3127 */             xYPlot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 3135 */             ChartUtils.saveChartAsPNG(
/* 3136 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 3137 */                   "_" + SPTBatch_.access$161(SPTBatch_.this) + "-" + SPTBatch_.access$160(SPTBatch_.this) + ".png"), 
/* 3138 */                 xYPlot.getChart(), 500, 270);
/* 3139 */           } catch (IOException e) {
/*      */             
/* 3141 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 3146 */         int maxFrame = spots.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();
/* 3147 */         int[] nSpots = new int[maxFrame + 1];
/* 3148 */         double[] time = new double[maxFrame + 1];
/* 3149 */         XYPlot plot = null;
/* 3150 */         for (int i1 = 0; i1 <= maxFrame; i1++) {
/* 3151 */           nSpots[i1] = spots.getNSpots(i1, true);
/* 3152 */           time[i1] = i1 * (SPTBatch_.access$95(SPTBatch_.this)).dt;
/*      */         } 
/* 3154 */         SPTBatch_.NSpotPerFrameDataset dataset = new SPTBatch_.NSpotPerFrameDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, time, 
/* 3155 */             nSpots);
/* 3156 */         String yFeature = "N spots";
/* 3157 */         Map<String, Dimension> dimMap = new HashMap<>(2);
/* 3158 */         dimMap.put("N spots", Dimension.NONE);
/* 3159 */         dimMap.put("POSITION_T", Dimension.TIME);
/* 3160 */         Map<String, String> nameMap = new HashMap<>(2);
/* 3161 */         nameMap.put("N spots", "N spots");
/* 3162 */         nameMap.put("POSITION_T", "T");
/*      */ 
/*      */         
/* 3165 */         String xAxisLabel = String.valueOf(nameMap.get("POSITION_T")) + " (" + 
/* 3166 */           TMUtils.getUnitsFor(Dimension.TIME, SPTBatch_.model.getSpaceUnits(), 
/* 3167 */             SPTBatch_.model.getTimeUnits()) + 
/* 3168 */           ")";
/*      */ 
/*      */         
/* 3171 */         Set<Dimension> dimensions = SPTBatch_.access$155(SPTBatch_.this, 
/* 3172 */             Collections.singletonList("N spots"), dimMap);
/*      */ 
/*      */         
/* 3175 */         ArrayList<ExportableChartPanel> chartPanels = new ArrayList<>(dimensions.size());
/* 3176 */         for (Dimension dimension : dimensions) {
/*      */ 
/*      */           
/* 3179 */           String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 3180 */               SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */           
/* 3183 */           List<String> featuresThisDimension = SPTBatch_.access$156(dimension, 
/* 3184 */               Collections.singletonList("N spots"), dimMap);
/*      */ 
/*      */           
/* 3187 */           String title = SPTBatch_.access$157(SPTBatch_.this, featuresThisDimension, nameMap, "POSITION_T");
/*      */ 
/*      */           
/* 3190 */           XYItemRenderer renderer = dataset.getRenderer();
/*      */ 
/*      */           
/* 3193 */           JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, 
/* 3194 */               (XYDataset)dataset, PlotOrientation.VERTICAL, true, true, false);
/* 3195 */           chart.getTitle().setFont(Fonts.FONT);
/* 3196 */           chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 3197 */           chart.setBackgroundPaint(new Color(220, 220, 220));
/* 3198 */           chart.setBorderVisible(false);
/* 3199 */           chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */           
/* 3202 */           plot = chart.getXYPlot();
/* 3203 */           plot.setRenderer(renderer);
/* 3204 */           plot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 3205 */           plot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3206 */           plot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 3207 */           plot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3208 */           plot.setOutlineVisible(false);
/* 3209 */           plot.setDomainCrosshairVisible(false);
/* 3210 */           plot.setDomainGridlinesVisible(false);
/* 3211 */           plot.setRangeCrosshairVisible(false);
/* 3212 */           plot.setRangeGridlinesVisible(false);
/* 3213 */           plot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */           
/* 3216 */           ((NumberAxis)plot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */           
/* 3219 */           plot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 3220 */           plot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 3230 */           ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 3231 */                 SPTBatch_.imps.getShortTitle() + "_" + "Nspotsvs.Time" + ".png"), plot.getChart(), 500, 
/* 3232 */               270);
/* 3233 */         } catch (IOException e) {
/*      */           
/* 3235 */           e.printStackTrace();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3240 */       if (SPTBatch_.checkbox4.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         
/* 3242 */         if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 3243 */           IJ.saveAs(SPTBatch_.access$115(SPTBatch_.this), "Tiff", String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i]);
/*      */         }
/* 3245 */         if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 3246 */           for (int r = 0; r < SPTBatch_.this.excludeTrack.size(); r++) {
/* 3247 */             int trackID = Integer.parseInt(SPTBatch_.trackJTable.getValueAt(r, 2).toString());
/* 3248 */             SPTBatch_.access$111(SPTBatch_.this).getModel().beginUpdate();
/*      */             try {
/* 3250 */               SPTBatch_.access$111(SPTBatch_.this).getModel().setTrackVisibility(Integer.valueOf(trackID), ((Boolean)SPTBatch_.this.excludeTrack.get(r)).booleanValue());
/*      */             } finally {
/* 3252 */               SPTBatch_.access$111(SPTBatch_.this).getModel().endUpdate();
/*      */             } 
/*      */             
/* 3255 */             SPTBatch_.access$113(SPTBatch_.this).render();
/* 3256 */             SPTBatch_.access$113(SPTBatch_.this).refresh();
/*      */           } 
/*      */           
/* 3259 */           if (SPTBatch_.imps.getNFrames() > 1) {
/* 3260 */             firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNFrames(), 1)));
/* 3261 */             lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNFrames(), Math.max(SPTBatch_.imps.getNFrames(), 1)));
/*      */           } 
/* 3263 */           if (SPTBatch_.imps.getNSlices() > 1) {
/* 3264 */             firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNSlices(), 1)));
/* 3265 */             lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNSlices(), Math.max(SPTBatch_.imps.getNSlices(), 1)));
/*      */           } 
/*      */           
/* 3268 */           SPTBatch_.taskOutput.append("Capturing TrackMate overlay from frame " + firstFrame + " to " + 
/* 3269 */               lastFrame + ".\n");
/* 3270 */           bounds = SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().getBounds();
/* 3271 */           width = Integer.valueOf(bounds.width);
/* 3272 */           height = Integer.valueOf(bounds.height);
/* 3273 */           nCaptures = Integer.valueOf(lastFrame.intValue() - firstFrame.intValue() + 1);
/* 3274 */           stack = new ImageStack(width.intValue(), height.intValue());
/* 3275 */           channel = Integer.valueOf(SPTBatch_.access$113(SPTBatch_.this).getImp().getChannel());
/* 3276 */           slice = Integer.valueOf(SPTBatch_.access$113(SPTBatch_.this).getImp().getSlice());
/* 3277 */           SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(true);
/* 3278 */           for (int i1 = firstFrame.intValue(); i1 <= lastFrame.intValue(); i1++) {
/*      */             
/* 3280 */             SPTBatch_.access$113(SPTBatch_.this).getImp().setPositionWithoutUpdate(channel.intValue(), slice.intValue(), i1);
/* 3281 */             BufferedImage bi = new BufferedImage(width.intValue(), height.intValue(), 2);
/* 3282 */             SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().paint(bi.getGraphics());
/* 3283 */             ColorProcessor cp = new ColorProcessor(bi);
/* 3284 */             Integer index = Integer.valueOf(SPTBatch_.access$113(SPTBatch_.this).getImp().getStackIndex(channel.intValue(), slice.intValue(), i1));
/* 3285 */             stack.addSlice(SPTBatch_.access$113(SPTBatch_.this).getImp().getImageStack().getSliceLabel(index.intValue()), (ImageProcessor)cp);
/*      */           } 
/* 3287 */           SPTBatch_.access$113(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(false);
/* 3288 */           SPTBatch_.access$114(SPTBatch_.this, new ImagePlus("TrackMate capture of " + SPTBatch_.access$113(SPTBatch_.this).getImp().getShortTitle(), 
/* 3289 */                 stack));
/*      */           
/* 3291 */           SPTBatch_.access$116(SPTBatch_.access$113(SPTBatch_.this).getImp(), SPTBatch_.access$115(SPTBatch_.this));
/* 3292 */           IJ.saveAs(SPTBatch_.access$115(SPTBatch_.this), "Tiff", String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i]);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3300 */       if (SPTBatch_.checkbox3.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         try {
/* 3302 */           FileWriter writer = new FileWriter(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + "Log" + 
/* 3303 */               "_" + SPTBatch_.access$53(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", "") + ".txt");
/* 3304 */           SPTBatch_.taskOutput.write(writer);
/* 3305 */           writer.close();
/* 3306 */         } catch (IOException iOException) {}
/*      */       }
/*      */       
/* 3309 */       SPTBatch_.imps.hide();
/*      */     } 
/* 3311 */     if (SPTBatch_.access$126(SPTBatch_.this).equals("trackTable")) {
/* 3312 */       Thread trackSummary = new Thread(new Runnable() {
/*      */             public void run() {
/* 3314 */               String[][] rowDataDef = new String[4][(SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements.length];
/* 3315 */               rowDataDef[0][0] = String.valueOf(SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3316 */               rowDataDef[1][0] = String.valueOf("");
/* 3317 */               rowDataDef[2][0] = String.valueOf("");
/* 3318 */               rowDataDef[3][0] = String.valueOf("");
/* 3319 */               rowDataDef[0][1] = String.valueOf(SPTBatch_.access$130(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3320 */               rowDataDef[1][1] = String.valueOf(" ");
/* 3321 */               rowDataDef[2][1] = String.valueOf("Short Tracks");
/* 3322 */               rowDataDef[3][1] = String.valueOf(SPTBatch_.access$140(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3323 */               rowDataDef[0][2] = String.valueOf(SPTBatch_.access$132(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3324 */               rowDataDef[1][2] = String.valueOf(" ");
/* 3325 */               rowDataDef[2][2] = String.valueOf("Short Confined");
/* 3326 */               rowDataDef[3][2] = String.valueOf(SPTBatch_.access$142(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3327 */               rowDataDef[0][3] = String.valueOf(SPTBatch_.access$134(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3328 */               rowDataDef[1][3] = String.valueOf(" ");
/* 3329 */               rowDataDef[2][3] = String.valueOf("Short Anomalous");
/* 3330 */               rowDataDef[3][3] = String.valueOf(SPTBatch_.access$144(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3331 */               rowDataDef[0][4] = String.valueOf(SPTBatch_.access$136(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3332 */               rowDataDef[1][4] = String.valueOf(" ");
/* 3333 */               rowDataDef[2][4] = String.valueOf("Short Free");
/* 3334 */               rowDataDef[3][4] = String.valueOf(SPTBatch_.access$146(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3335 */               rowDataDef[0][5] = String.valueOf(SPTBatch_.access$138(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3336 */               rowDataDef[1][5] = String.valueOf(" ");
/* 3337 */               rowDataDef[2][5] = String.valueOf("Short Direct");
/* 3338 */               rowDataDef[3][5] = String.valueOf(SPTBatch_.access$148(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3339 */               rowDataDef[0][6] = String.valueOf(SPTBatch_.access$150(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3340 */               rowDataDef[1][6] = String.valueOf("");
/* 3341 */               rowDataDef[2][6] = String.valueOf("");
/* 3342 */               rowDataDef[3][6] = String.valueOf("");
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
/* 3354 */               ResultsTable rtTrackSummary = new ResultsTable();
/* 3355 */               for (int r = 0; r < rowDataDef.length; r++) {
/* 3356 */                 for (int c = 0; c < (rowDataDef[r]).length; c++)
/* 3357 */                   rtTrackSummary.setValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements[c], r, rowDataDef[r][c]); 
/*      */               } 
/*      */               try {
/* 3360 */                 rtTrackSummary.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3361 */                     "SummaryTracks_Condition" + ".csv");
/* 3362 */               } catch (IOException e) {
/*      */                 
/* 3364 */                 e.printStackTrace();
/*      */               } 
/*      */             }
/*      */           });
/* 3368 */       trackSummary.start();
/*      */     } 
/* 3370 */     if (SPTBatch_.checkSummary.isSelected()) {
/* 3371 */       Thread trackSummaryCols = new Thread(new Runnable() {
/*      */             public void run() {
/* 3373 */               for (int x = 0; x < (SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.size(); x++) {
/* 3374 */                 if (summaryColsWindow.combo.getSelectedIndex() == 0) {
/* 3375 */                   ResultsTable rtSpotSum = new ResultsTable();
/* 3376 */                   for (int y = 0; y < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots.length; y++) {
/* 3377 */                     for (int z = 0; z < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[y].size(); z++)
/* 3378 */                       rtSpotSum.setValue(listOfFiles[y].getName(), z, 
/* 3379 */                           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[y].getStringValue(
/* 3380 */                             Arrays.<String>asList(summaryColsWindow.columnNamesSpot)
/* 3381 */                             .indexOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x)), 
/* 3382 */                             z)); 
/*      */                   }  try {
/* 3384 */                     rtSpotSum.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3385 */                         (String)(SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x) + "_" + "SummaryCols_Spots" + ".csv");
/* 3386 */                   } catch (IOException e1) {
/*      */                     
/* 3388 */                     e1.printStackTrace();
/*      */                   } 
/*      */                 } 
/* 3391 */                 if (summaryColsWindow.combo.getSelectedIndex() == 1) {
/* 3392 */                   ResultsTable rtLinkSum = new ResultsTable();
/* 3393 */                   for (int y = 0; y < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtLinks.length; y++) {
/* 3394 */                     for (int z = 0; z < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtLinks[y].size(); z++)
/* 3395 */                       rtLinkSum.setValue(listOfFiles[y].getName(), z, 
/* 3396 */                           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtLinks[y].getStringValue(
/* 3397 */                             Arrays.<String>asList(summaryColsWindow.columnNamesLinks)
/* 3398 */                             .indexOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x)), 
/* 3399 */                             z)); 
/*      */                   }  try {
/* 3401 */                     rtLinkSum.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3402 */                         (String)(SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x) + "_" + "SummaryCols_Links" + ".csv");
/* 3403 */                   } catch (IOException e1) {
/*      */                     
/* 3405 */                     e1.printStackTrace();
/*      */                   } 
/*      */                 } 
/* 3408 */                 if (summaryColsWindow.combo.getSelectedIndex() == 2) {
/* 3409 */                   ResultsTable rtTrackSum = new ResultsTable();
/* 3410 */                   for (int y = 0; y < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks.length; y++) {
/* 3411 */                     for (int z = 0; z < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[y].size(); z++)
/* 3412 */                       rtTrackSum.setValue(listOfFiles[y].getName(), z, 
/* 3413 */                           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[y].getStringValue(
/* 3414 */                             Arrays.<String>asList(summaryColsWindow.columnNamesTracks)
/* 3415 */                             .indexOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x)), 
/* 3416 */                             z)); 
/*      */                   }  try {
/* 3418 */                     rtTrackSum.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3419 */                         (String)(SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x) + "_" + "SummaryCols_Tracks" + ".csv");
/* 3420 */                   } catch (IOException e1) {
/*      */                     
/* 3422 */                     e1.printStackTrace();
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           });
/*      */ 
/*      */       
/* 3430 */       trackSummaryCols.start();
/*      */     } 
/*      */     
/* 3433 */     SPTBatch_.access$121(SPTBatch_.this).equals("spotTable");
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
/* 3444 */     SPTBatch_.access$124(SPTBatch_.this).equals("linkTable");
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
/* 3455 */     SPTBatch_.access$126(SPTBatch_.this).equals("trackTable");
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
/* 3468 */     SPTBatch_.taskOutput.append("              FINISHED!!!");
/* 3469 */     frameAnalyzer.setVisible(false);
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/SPTBatch_$1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */