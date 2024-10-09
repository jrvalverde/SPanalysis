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
/*      */ 
/*      */ class null
/*      */   implements Runnable
/*      */ {
/*      */   public void run() {
/*  490 */     SPTBatch_.access$45(SPTBatch_.this, SPTBatch_.access$35(SPTBatch_.this).getSelectedItem().toString());
/*  491 */     SPTBatch_.access$46(SPTBatch_.this, SPTBatch_.access$36(SPTBatch_.this).getSelectedItem().toString());
/*      */ 
/*      */     
/*  494 */     SPTBatch_.access$47(SPTBatch_.this, SPTBatch_.access$39(SPTBatch_.this).getSelectedItem().toString());
/*  495 */     SPTBatch_.access$48(SPTBatch_.this, SPTBatch_.access$40(SPTBatch_.this).getSelectedItem().toString());
/*      */ 
/*      */     
/*  498 */     SPTBatch_.access$49(SPTBatch_.this, SPTBatch_.access$43(SPTBatch_.this).getSelectedItem().toString());
/*  499 */     SPTBatch_.access$50(SPTBatch_.this, SPTBatch_.access$44(SPTBatch_.this).getSelectedItem().toString());
/*      */     
/*  501 */     SPTBatch_.fileXmlInitial = new File(SPTBatch_.access$51(SPTBatch_.this));
/*  502 */     File imageFolder = new File(SPTBatch_.access$52(SPTBatch_.this));
/*  503 */     final File[] listOfFiles = imageFolder.listFiles();
/*  504 */     SPTBatch_.access$53(SPTBatch_.this, new String[listOfFiles.length]);
/*  505 */     File[] filesXML = new File[listOfFiles.length];
/*  506 */     for (int u = 0; u < filesXML.length; u++)
/*  507 */       filesXML[u] = new File(SPTBatch_.access$51(SPTBatch_.this)); 
/*  508 */     Object[] columHeadersFinalSpot = { "IMAGE_TITLE", "QUALITY", "POSITION_X", "POSITION_Y", 
/*  509 */         "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MEAN_INTENSITY", 
/*  510 */         "MEDIAN_INTENSITY", "MIN_INTENSITY", "MAX_INTENSITY", "TOTAL_INTENSITY", "STANDARD_DEVIATION", 
/*  511 */         "CONTRAST", "SNR", "ESTIMATED_DIAMETER", "MORPHOLOGY", "ELLIPSOIDFIT_SEMIAXISLENGTH_C", 
/*  512 */         "ELLIPSOIDFIT_SEMIAXISLENGTH_B", "ELLIPSOIDFIT_SEMIAXISLENGTH_A", "ELLIPSOIDFIT_AXISPHI_C", 
/*  513 */         "ELLIPSOIDFIT_AXISPHI_B", "ELLIPSOIDFIT_AXISPHI_A", "ELLIPSOIDFIT_AXISTHETA_C", 
/*  514 */         "ELLIPSOIDFIT_AXISTHETA_B", "ELLIPSOIDFIT_AXISTHETA_A" };
/*  515 */     Object[] columHeadersFinalLink = { "IMAGE_TITLE", "LINK_COST", "EDGE_TIME", 
/*  516 */         "EDGE_X_LOCATION", "EDGE_Y_LOCATION", "EDGE_Z_LOCATION", "VELOCITY", "DISPLACEMENT" };
/*  517 */     Object[] columHeadersFinalTrack = { "IMAGE_TITLE", "TRACK_DURATION", "TRACK_START", 
/*  518 */         "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", 
/*  519 */         "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_INDEX", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", 
/*  520 */         "TRACK_Z_LOCATION", "NUMBER_SPOTS", "NUMBER_GAPS", "LONGEST_GAP", "NUMBER_SPLITS", 
/*  521 */         "NUMBER_MERGES", "NUMBER_COMPLEX", "TRACK_MEAN_QUALITY", "TRACK_MAX_QUALITY", 
/*  522 */         "TRACK_MIN_QUALITY", "TRACK_MEDIAN_QUALITY", "TRACK_STD_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/*  523 */         "MAX_DISTANCE_TRAVELED", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/*  524 */         "TOTAL_ABSOLUTE_ANGLE_XY", "TOTAL_ABSOLUTE_ANGLE_YZ", "TOTAL_ABSOLUTE_ANGLE_ZX", 
/*  525 */         "CONFINMENT_RATIO" };
/*  526 */     SPTBatch_.access$55(SPTBatch_.this, new ResultsTable(Integer.valueOf((SPTBatch_.access$54(SPTBatch_.this)).length)));
/*  527 */     SPTBatch_.access$56(SPTBatch_.this, new ResultsTable(Integer.valueOf((SPTBatch_.access$54(SPTBatch_.this)).length)));
/*  528 */     SPTBatch_.access$57(SPTBatch_.this, new ResultsTable(Integer.valueOf((SPTBatch_.access$54(SPTBatch_.this)).length)));
/*  529 */     SPTBatch_.this.rtSpots = new ResultsTable[listOfFiles.length];
/*  530 */     SPTBatch_.this.rtLinks = new ResultsTable[listOfFiles.length];
/*  531 */     SPTBatch_.this.rtTracks = new ResultsTable[listOfFiles.length]; int y;
/*  532 */     for (y = 0; y < columHeadersFinalSpot.length; y++)
/*  533 */       SPTBatch_.access$58(SPTBatch_.this).setHeading(y, (String)columHeadersFinalSpot[y]); 
/*  534 */     for (y = 0; y < columHeadersFinalLink.length; y++)
/*  535 */       SPTBatch_.access$59(SPTBatch_.this).setHeading(y, (String)columHeadersFinalLink[y]); 
/*  536 */     for (y = 0; y < columHeadersFinalTrack.length; y++) {
/*  537 */       SPTBatch_.access$60(SPTBatch_.this).setHeading(y, (String)columHeadersFinalTrack[y]);
/*      */     }
/*  539 */     int MAX = listOfFiles.length;
/*  540 */     JFrame frameAnalyzer = new JFrame("Analyzing...");
/*  541 */     final JProgressBar pb = new JProgressBar();
/*  542 */     pb.setMinimum(0);
/*  543 */     pb.setMaximum(MAX);
/*  544 */     pb.setStringPainted(true);
/*  545 */     SPTBatch_.taskOutput = new JTextArea(5, 20);
/*  546 */     SPTBatch_.taskOutput.setMargin(new Insets(5, 5, 5, 5));
/*  547 */     SPTBatch_.taskOutput.setEditable(false);
/*  548 */     DefaultCaret caret = (DefaultCaret)SPTBatch_.taskOutput.getCaret();
/*  549 */     caret.setUpdatePolicy(2);
/*  550 */     JPanel panel = new JPanel();
/*  551 */     panel.setLayout(new BoxLayout(panel, 1));
/*  552 */     panel.add(pb);
/*  553 */     panel.add(Box.createVerticalStrut(5));
/*  554 */     panel.add(new JScrollPane(SPTBatch_.taskOutput), "Center");
/*  555 */     panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
/*  556 */     frameAnalyzer.getContentPane().add(panel);
/*  557 */     frameAnalyzer.setDefaultCloseOperation(2);
/*  558 */     frameAnalyzer.setSize(550, 500);
/*  559 */     frameAnalyzer.setVisible(true);
/*  560 */     SPTBatch_.this.selectedItems = new ArrayList<>();
/*      */     
/*  562 */     if (SPTBatch_.checkSummary.isSelected()) {
/*  563 */       if (summaryColsWindow.combo.getSelectedIndex() == 0)
/*  564 */         for (int i = 0; i < summaryColsWindow.itemsSpots.length; i++) {
/*  565 */           if (summaryColsWindow.itemsSpots[i].isSelected())
/*  566 */             SPTBatch_.this.selectedItems.add((summaryColsWindow.itemsSpots[i]).text); 
/*      */         }  
/*  568 */       if (summaryColsWindow.combo.getSelectedIndex() == 1)
/*  569 */         for (int i = 0; i < summaryColsWindow.itemsLinks.length; i++) {
/*  570 */           if (summaryColsWindow.itemsLinks[i].isSelected())
/*  571 */             SPTBatch_.this.selectedItems.add((summaryColsWindow.itemsLinks[i]).text); 
/*      */         }  
/*  573 */       if (summaryColsWindow.combo.getSelectedIndex() == 2)
/*  574 */         for (int i = 0; i < summaryColsWindow.itemsTracks.length; i++) {
/*  575 */           if (summaryColsWindow.itemsTracks[i].isSelected())
/*  576 */             SPTBatch_.this.selectedItems.add((summaryColsWindow.itemsTracks[i]).text); 
/*      */         }  
/*      */     } 
/*  579 */     for (SPTBatch_.i = 0; SPTBatch_.i < listOfFiles.length; SPTBatch_.i++) {
/*  580 */       if (SPTBatch_.imps != null) {
/*  581 */         SPTBatch_.imps.hide();
/*      */       }
/*  583 */       if (listOfFiles[SPTBatch_.i].isFile()) {
/*  584 */         SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i] = listOfFiles[SPTBatch_.i].getName();
/*  585 */         SPTBatch_.imgTitle = SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i];
/*      */       } 
/*  587 */       final int currentValue = SPTBatch_.i + 1;
/*      */       
/*      */       try {
/*  590 */         SwingUtilities.invokeLater(new Runnable() {
/*      */               public void run() {
/*  592 */                 pb.setValue(currentValue);
/*  593 */                 SPTBatch_.taskOutput.append(String.format("Completed %f%% of task.\n", new Object[] {
/*  594 */                         Double.valueOf(this.val$currentValue * 100.0D / this.val$listOfFiles.length) }));
/*      */               }
/*      */             });
/*  597 */         Thread.sleep(100L);
/*  598 */       } catch (InterruptedException var69) {
/*  599 */         JOptionPane.showMessageDialog(frameAnalyzer, var69.getMessage());
/*      */       } 
/*  601 */       if (listOfFiles[SPTBatch_.i].getName().contains(".lif")) {
/*  602 */         SPTBatch_.this.lifs = SPTBatch_.openBF(String.valueOf(SPTBatch_.access$52(SPTBatch_.this)) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i], false, false, false, false, false, 
/*  603 */             true);
/*  604 */         for (int x = 0; x < SPTBatch_.this.lifs.length; x++) {
/*  605 */           SPTBatch_.imps = new ImagePlus(String.valueOf(SPTBatch_.access$52(SPTBatch_.this)) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i]);
/*      */         }
/*      */       } 
/*  608 */       if (!listOfFiles[SPTBatch_.i].getName().contains(".lif"))
/*  609 */         SPTBatch_.imps = new ImagePlus(String.valueOf(SPTBatch_.access$52(SPTBatch_.this)) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i]); 
/*  610 */       IJ.resetMinAndMax(SPTBatch_.imps);
/*  611 */       SPTBatch_.access$61(SPTBatch_.this, SPTBatch_.imps.getDimensions());
/*  612 */       SPTBatch_.this.calibration = SPTBatch_.imps.getCalibration();
/*  613 */       SPTBatch_.fps = (SPTBatch_.imps.getFileInfo()).frameInterval;
/*  614 */       if (SPTBatch_.access$62(SPTBatch_.this)[4] == 1 && SPTBatch_.access$62(SPTBatch_.this)[3] > 1) {
/*      */         
/*  616 */         SPTBatch_.imps.setDimensions(SPTBatch_.access$62(SPTBatch_.this)[2], SPTBatch_.access$62(SPTBatch_.this)[4], SPTBatch_.access$62(SPTBatch_.this)[3]);
/*  617 */         SPTBatch_.this.calibration.frameInterval = SPTBatch_.this.calibration.frameInterval;
/*  618 */         SPTBatch_.access$63(SPTBatch_.this, Logger.IJ_LOGGER);
/*      */       } 
/*      */       
/*  621 */       SPTBatch_.impsSubBg = SPTBatch_.imps.duplicate();
/*  622 */       SPTBatch_.impsSubBg.setCalibration(SPTBatch_.this.calibration);
/*  623 */       SPTBatch_.directImages = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", ""));
/*      */       
/*  625 */       if (!SPTBatch_.directImages.exists()) {
/*  626 */         SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directImages.getName());
/*  627 */         boolean result = false;
/*      */         
/*      */         try {
/*  630 */           SPTBatch_.directImages.mkdir();
/*  631 */           result = true;
/*  632 */         } catch (SecurityException securityException) {}
/*      */ 
/*      */         
/*  635 */         if (result) {
/*  636 */           SPTBatch_.taskOutput.append("DIR created");
/*      */         }
/*      */       } 
/*      */       
/*  640 */       SPTBatch_.directSummary = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + "Summary_Analysis");
/*      */       
/*  642 */       if (!SPTBatch_.directSummary.exists()) {
/*  643 */         boolean result = false;
/*      */         
/*      */         try {
/*  646 */           SPTBatch_.directSummary.mkdir();
/*  647 */           result = true;
/*  648 */         } catch (SecurityException securityException) {}
/*      */ 
/*      */         
/*  651 */         if (result) {
/*  652 */           SPTBatch_.taskOutput.append("DIR created-Summary_Analysis");
/*      */         }
/*      */       } 
/*  655 */       SPTBatch_.directSPT = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", "") + 
/*  656 */           File.separator + "SPT_Analysis");
/*      */       
/*  658 */       if (!SPTBatch_.directSPT.exists()) {
/*  659 */         boolean result = false;
/*      */         
/*      */         try {
/*  662 */           SPTBatch_.directSPT.mkdir();
/*  663 */           result = true;
/*  664 */         } catch (SecurityException securityException) {}
/*      */ 
/*      */         
/*  667 */         if (result) {
/*  668 */           SPTBatch_.taskOutput.append("DIR created-SPT_Analysis");
/*      */         }
/*      */       } 
/*      */       
/*  672 */       SPTBatch_.directPBS = new File(String.valueOf(SPTBatch_.csvPath) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", "") + 
/*  673 */           File.separator + "Photobleaching_Analysis");
/*  674 */       if (SPTBatch_.checkPBS.isSelected())
/*      */       {
/*  676 */         if (!SPTBatch_.directPBS.exists()) {
/*  677 */           boolean result = false;
/*      */           
/*      */           try {
/*  680 */             SPTBatch_.directPBS.mkdir();
/*  681 */             result = true;
/*  682 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/*  685 */           if (result) {
/*  686 */             SPTBatch_.taskOutput.append("DIR created-Photobleching_Analysis");
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/*  691 */       if (SPTBatch_.checkboxMSD.isSelected() == Boolean.TRUE.booleanValue()) {
/*  692 */         SPTBatch_.directMSS = new File(String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "MSS_Analysis");
/*      */         
/*  694 */         if (!SPTBatch_.directMSS.exists()) {
/*  695 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directMSS.getName());
/*  696 */           boolean result = false;
/*      */           
/*      */           try {
/*  699 */             SPTBatch_.directMSS.mkdir();
/*  700 */             result = true;
/*  701 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/*  704 */           if (result) {
/*  705 */             SPTBatch_.taskOutput.append(String.valueOf(SPTBatch_.directMSS.getName()) + "  DIR created");
/*      */           }
/*      */         } 
/*      */       } 
/*  709 */       if (SPTBatch_.checkCluster.isSelected() == Boolean.TRUE.booleanValue()) {
/*  710 */         SPTBatch_.directCluster = new File(
/*  711 */             String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "Cluster_Size_Analysis");
/*      */         
/*  713 */         if (!SPTBatch_.directCluster.exists()) {
/*  714 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directCluster.getName());
/*  715 */           boolean result = false;
/*      */           
/*      */           try {
/*  718 */             SPTBatch_.directCluster.mkdir();
/*  719 */             result = true;
/*  720 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/*  723 */           if (result) {
/*  724 */             SPTBatch_.taskOutput.append(String.valueOf(SPTBatch_.directCluster.getName()) + "  DIR created");
/*      */           }
/*      */         } 
/*      */       } 
/*  728 */       TmXmlReader reader = new TmXmlReader(SPTBatch_.fileXmlInitial);
/*  729 */       DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
/*  730 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*  731 */       DocumentBuilder builder = null;
/*      */       try {
/*  733 */         builder = factory.newDocumentBuilder();
/*  734 */       } catch (ParserConfigurationException e) {
/*      */         
/*  736 */         e.printStackTrace();
/*      */       } 
/*      */       
/*  739 */       Document doc = null;
/*      */       
/*      */       try {
/*  742 */         doc = builder.parse(SPTBatch_.fileXmlInitial);
/*  743 */       } catch (SAXException e) {
/*      */         
/*  745 */         e.printStackTrace();
/*  746 */       } catch (IOException e) {
/*      */         
/*  748 */         e.printStackTrace();
/*      */       } 
/*  750 */       XPathFactory xPathfactory = XPathFactory.newInstance();
/*  751 */       XPath xpath = xPathfactory.newXPath();
/*      */       
/*  753 */       XPathExpression exprBasicSettings = null;
/*  754 */       XPathExpression exprDetectorSettings = null;
/*  755 */       XPathExpression exprInitialSpotFilter = null;
/*  756 */       XPathExpression exprFilter = null;
/*  757 */       XPathExpression exprTrackerSettings = null;
/*  758 */       XPathExpression exprLinking = null;
/*  759 */       XPathExpression exprGapClosing = null;
/*  760 */       XPathExpression exprSplitting = null;
/*  761 */       XPathExpression exprMerging = null;
/*  762 */       XPathExpression exprTrackFilter = null;
/*  763 */       XPathExpression exprLinkingP = null;
/*      */ 
/*      */       
/*      */       try {
/*  767 */         exprBasicSettings = xpath.compile("//Settings/BasicSettings[@zstart]");
/*  768 */       } catch (XPathExpressionException e) {
/*      */         
/*  770 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  774 */         exprLinkingP = xpath.compile("//Linking/FeaturePenalties[@MEAN_INTENSITY]");
/*  775 */       } catch (XPathExpressionException e) {
/*      */         
/*  777 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  781 */         exprDetectorSettings = xpath.compile("//Settings/DetectorSettings[@RADIUS]");
/*  782 */       } catch (XPathExpressionException e) {
/*      */         
/*  784 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  788 */         exprInitialSpotFilter = xpath
/*  789 */           .compile("//Settings/InitialSpotFilter[@feature]");
/*  790 */       } catch (XPathExpressionException e) {
/*      */         
/*  792 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  796 */         exprFilter = xpath.compile("//SpotFilterCollection/Filter[@feature]");
/*  797 */       } catch (XPathExpressionException e) {
/*      */         
/*  799 */         e.printStackTrace();
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  804 */         exprTrackerSettings = xpath
/*  805 */           .compile("//Settings/TrackerSettings[@TRACKER_NAME]");
/*  806 */       } catch (XPathExpressionException e) {
/*      */         
/*  808 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  812 */         exprLinking = xpath
/*  813 */           .compile("//TrackerSettings/Linking[@LINKING_MAX_DISTANCE]");
/*  814 */       } catch (XPathExpressionException e) {
/*      */         
/*  816 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  820 */         exprGapClosing = xpath
/*  821 */           .compile("//TrackerSettings/GapClosing[@MAX_FRAME_GAP]");
/*  822 */       } catch (XPathExpressionException e) {
/*      */         
/*  824 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  828 */         exprSplitting = xpath
/*  829 */           .compile("//TrackerSettings/TrackSplitting[@SPLITTING_MAX_DISTANCE]");
/*  830 */       } catch (XPathExpressionException e) {
/*      */         
/*  832 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  836 */         exprMerging = xpath
/*  837 */           .compile("//TrackerSettings/TrackMerging[@MERGING_MAX_DISTANCE]");
/*  838 */       } catch (XPathExpressionException e) {
/*      */         
/*  840 */         e.printStackTrace();
/*      */       } 
/*      */       
/*      */       try {
/*  844 */         exprTrackFilter = xpath.compile("//TrackFilterCollection/Filter[@feature]");
/*  845 */       } catch (XPathExpressionException e) {
/*      */         
/*  847 */         e.printStackTrace();
/*      */       } 
/*      */       
/*  850 */       NodeList nlBasicSettings = null;
/*  851 */       NodeList nlDetectorSettings = null;
/*  852 */       NodeList nlInitialSpotFilter = null;
/*  853 */       NodeList nlFilter = null;
/*  854 */       NodeList nlTrackerSettings = null;
/*  855 */       NodeList nlLinking = null;
/*  856 */       NodeList nlGapClosing = null;
/*  857 */       NodeList nlSplitting = null;
/*  858 */       NodeList nlMerging = null;
/*  859 */       NodeList nlTrackFilter = null;
/*  860 */       NodeList nlLinkingP = null;
/*      */ 
/*      */       
/*      */       try {
/*  864 */         nlBasicSettings = (NodeList)exprBasicSettings.evaluate(doc, XPathConstants.NODESET);
/*  865 */         nlDetectorSettings = (NodeList)exprDetectorSettings.evaluate(doc, XPathConstants.NODESET);
/*  866 */         nlInitialSpotFilter = (NodeList)exprInitialSpotFilter.evaluate(doc, XPathConstants.NODESET);
/*  867 */         nlFilter = (NodeList)exprFilter.evaluate(doc, XPathConstants.NODESET);
/*  868 */         nlTrackerSettings = (NodeList)exprTrackerSettings.evaluate(doc, XPathConstants.NODESET);
/*  869 */         nlLinking = (NodeList)exprLinking.evaluate(doc, XPathConstants.NODESET);
/*  870 */         nlGapClosing = (NodeList)exprGapClosing.evaluate(doc, XPathConstants.NODESET);
/*  871 */         nlSplitting = (NodeList)exprSplitting.evaluate(doc, XPathConstants.NODESET);
/*  872 */         nlMerging = (NodeList)exprMerging.evaluate(doc, XPathConstants.NODESET);
/*  873 */         nlTrackFilter = (NodeList)exprTrackFilter.evaluate(doc, XPathConstants.NODESET);
/*  874 */         nlLinkingP = (NodeList)exprLinkingP.evaluate(doc, XPathConstants.NODESET);
/*      */       }
/*  876 */       catch (XPathExpressionException e) {
/*      */         
/*  878 */         e.printStackTrace();
/*      */       } 
/*      */       int j;
/*  881 */       for (j = 0; j < nlBasicSettings.getLength(); j++) {
/*  882 */         Node currentItem = nlBasicSettings.item(j);
/*  883 */         SPTBatch_.access$64(SPTBatch_.this, currentItem.getAttributes().getNamedItem("zstart").getNodeValue());
/*  884 */         SPTBatch_.access$65(SPTBatch_.this, currentItem.getAttributes().getNamedItem("zend").getNodeValue());
/*  885 */         SPTBatch_.access$66(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ystart").getNodeValue());
/*  886 */         SPTBatch_.access$67(SPTBatch_.this, currentItem.getAttributes().getNamedItem("yend").getNodeValue());
/*  887 */         SPTBatch_.access$68(SPTBatch_.this, currentItem.getAttributes().getNamedItem("xstart").getNodeValue());
/*  888 */         SPTBatch_.access$69(SPTBatch_.this, currentItem.getAttributes().getNamedItem("xend").getNodeValue());
/*  889 */         SPTBatch_.access$70(SPTBatch_.this, currentItem.getAttributes().getNamedItem("tstart").getNodeValue());
/*  890 */         SPTBatch_.access$71(SPTBatch_.this, currentItem.getAttributes().getNamedItem("tend").getNodeValue());
/*      */       } 
/*  892 */       for (j = 0; j < nlDetectorSettings.getLength(); j++) {
/*  893 */         Node currentItem = nlDetectorSettings.item(j);
/*  894 */         SPTBatch_.RADIUS = currentItem.getAttributes().getNamedItem("RADIUS").getNodeValue();
/*  895 */         SPTBatch_.access$72(SPTBatch_.this, currentItem.getAttributes().getNamedItem("THRESHOLD").getNodeValue());
/*  896 */         SPTBatch_.access$73(SPTBatch_.this, currentItem.getAttributes().getNamedItem("TARGET_CHANNEL").getNodeValue());
/*  897 */         SPTBatch_.access$74(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DO_SUBPIXEL_LOCALIZATION")
/*  898 */             .getNodeValue());
/*  899 */         SPTBatch_.access$75(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DO_MEDIAN_FILTERING")
/*  900 */             .getNodeValue());
/*  901 */         SPTBatch_.access$76(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DETECTOR_NAME").getNodeValue());
/*  902 */         if (SPTBatch_.access$77(SPTBatch_.this).equals("BLOCK_LOG_DETECTOR"))
/*  903 */           SPTBatch_.access$78(SPTBatch_.this, currentItem.getAttributes().getNamedItem("NSPLIT").getNodeValue()); 
/*  904 */         if (SPTBatch_.access$77(SPTBatch_.this).equals("DOWNSAMLE_LOG_DETECTOR")) {
/*  905 */           SPTBatch_.access$79(SPTBatch_.this, currentItem.getAttributes().getNamedItem("DOWNSAMPLE_FACTOR")
/*  906 */               .getNodeValue());
/*      */         }
/*      */       } 
/*      */       
/*  910 */       for (j = 0; j < nlLinkingP.getLength(); j++) {
/*  911 */         SPTBatch_.access$80(SPTBatch_.this, nlLinkingP.item(j).getAttributes().item(j).getNodeName());
/*  912 */         SPTBatch_.access$81(SPTBatch_.this, nlLinkingP.item(j).getAttributes().item(j).getNodeValue());
/*      */       } 
/*  914 */       for (j = 0; j < nlInitialSpotFilter.getLength(); j++) {
/*  915 */         Node currentItem = nlInitialSpotFilter.item(j);
/*  916 */         SPTBatch_.access$82(SPTBatch_.this, currentItem.getAttributes().getNamedItem("value").getNodeValue());
/*      */       } 
/*      */       
/*  919 */       List<String> spotFilterFeature = new ArrayList<>();
/*  920 */       List<String> spotFilterValue = new ArrayList<>();
/*  921 */       List<String> spotFilterAbove = new ArrayList<>();
/*  922 */       for (int i = 0; i < nlFilter.getLength(); i++) {
/*  923 */         Node currentItem = nlFilter.item(i);
/*  924 */         spotFilterFeature.add(currentItem.getAttributes().getNamedItem("feature").getNodeValue());
/*  925 */         spotFilterValue.add(currentItem.getAttributes().getNamedItem("value").getNodeValue());
/*  926 */         spotFilterAbove.add(currentItem.getAttributes().getNamedItem("isabove").getNodeValue());
/*      */       } 
/*      */ 
/*      */       
/*  930 */       List<String> trackFilterFeature = new ArrayList<>();
/*  931 */       List<String> trackFilterValue = new ArrayList<>();
/*  932 */       List<String> trackFilterAbove = new ArrayList<>(); int k;
/*  933 */       for (k = 0; k < nlTrackFilter.getLength(); k++) {
/*  934 */         Node currentItem = nlTrackFilter.item(k);
/*  935 */         trackFilterFeature.add(currentItem.getAttributes().getNamedItem("feature").getNodeValue());
/*  936 */         trackFilterValue.add(currentItem.getAttributes().getNamedItem("value").getNodeValue());
/*  937 */         trackFilterAbove.add(currentItem.getAttributes().getNamedItem("isabove").getNodeValue());
/*      */       } 
/*  939 */       for (k = 0; k < nlTrackerSettings.getLength(); k++) {
/*  940 */         Node currentItem = nlTrackerSettings.item(k);
/*  941 */         SPTBatch_.access$83(SPTBatch_.this, currentItem.getAttributes().getNamedItem("TRACKER_NAME").getNodeValue());
/*  942 */         SPTBatch_.access$84(SPTBatch_.this, currentItem.getAttributes().getNamedItem("CUTOFF_PERCENTILE")
/*  943 */             .getNodeValue());
/*  944 */         SPTBatch_.access$85(SPTBatch_.this, currentItem.getAttributes().getNamedItem("BLOCKING_VALUE").getNodeValue());
/*  945 */         SPTBatch_.access$86(SPTBatch_.this, currentItem.getAttributes()
/*  946 */             .getNamedItem("ALTERNATIVE_LINKING_COST_FACTOR").getNodeValue());
/*      */       } 
/*  948 */       for (k = 0; k < nlLinking.getLength(); k++) {
/*  949 */         Node currentItem = nlLinking.item(k);
/*  950 */         SPTBatch_.access$87(SPTBatch_.this, currentItem.getAttributes().getNamedItem("LINKING_MAX_DISTANCE")
/*  951 */             .getNodeValue());
/*      */       } 
/*  953 */       for (k = 0; k < nlGapClosing.getLength(); k++) {
/*  954 */         Node currentItem = nlGapClosing.item(k);
/*  955 */         SPTBatch_.access$88(SPTBatch_.this, currentItem.getAttributes().getNamedItem("MAX_FRAME_GAP").getNodeValue());
/*  956 */         SPTBatch_.access$89(SPTBatch_.this, currentItem.getAttributes().getNamedItem("GAP_CLOSING_MAX_DISTANCE")
/*  957 */             .getNodeValue());
/*  958 */         SPTBatch_.access$90(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ALLOW_GAP_CLOSING")
/*  959 */             .getNodeValue());
/*      */       } 
/*      */       
/*  962 */       for (k = 0; k < nlSplitting.getLength(); k++) {
/*  963 */         Node currentItem = nlSplitting.item(k);
/*  964 */         SPTBatch_.access$91(SPTBatch_.this, currentItem.getAttributes().getNamedItem("SPLITTING_MAX_DISTANCE")
/*  965 */             .getNodeValue());
/*  966 */         SPTBatch_.access$92(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ALLOW_TRACK_SPLITTING")
/*  967 */             .getNodeValue());
/*      */       } 
/*  969 */       for (k = 0; k < nlMerging.getLength(); k++) {
/*  970 */         Node currentItem = nlMerging.item(k);
/*  971 */         SPTBatch_.access$93(SPTBatch_.this, currentItem.getAttributes().getNamedItem("MERGING_MAX_DISTANCE")
/*  972 */             .getNodeValue());
/*  973 */         SPTBatch_.access$94(SPTBatch_.this, currentItem.getAttributes().getNamedItem("ALLOW_TRACK_MERGING")
/*  974 */             .getNodeValue());
/*      */       } 
/*      */       
/*  977 */       SPTBatch_.access$95(SPTBatch_.this, new Settings(SPTBatch_.imps));
/*  978 */       SPTBatch_.taskOutput.append(SPTBatch_.access$96(SPTBatch_.this).toStringImageInfo());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  996 */       if (SPTBatch_.access$77(SPTBatch_.this).equals("LOG_DETECTOR")) {
/*  997 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorFactory = (SpotDetectorFactoryBase)new LogDetectorFactory();
/*  998 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings = (SPTBatch_.access$96(SPTBatch_.this)).detectorFactory.getDefaultSettings();
/*  999 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", 
/* 1000 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$97(SPTBatch_.this))));
/* 1001 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("RADIUS", Double.valueOf(Double.parseDouble(SPTBatch_.RADIUS)));
/* 1002 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("TARGET_CHANNEL", 
/* 1003 */             Integer.valueOf(Integer.parseInt(SPTBatch_.access$98(SPTBatch_.this))));
/* 1004 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("THRESHOLD", Double.valueOf(Double.parseDouble(SPTBatch_.access$99(SPTBatch_.this))));
/* 1005 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("DO_MEDIAN_FILTERING", 
/* 1006 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$100(SPTBatch_.this))));
/* 1007 */         if (SPTBatch_.access$101(SPTBatch_.this) != null) {
/* 1008 */           (SPTBatch_.access$96(SPTBatch_.this)).initialSpotFilterValue = Double.valueOf(Double.parseDouble(SPTBatch_.access$101(SPTBatch_.this)));
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
/* 1033 */       if (SPTBatch_.access$77(SPTBatch_.this).equals("DOG_DETECTOR")) {
/*      */         
/* 1035 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorFactory = (SpotDetectorFactoryBase)new DogDetectorFactory();
/* 1036 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("DO_SUBPIXEL_LOCALIZATION", 
/* 1037 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$97(SPTBatch_.this))));
/* 1038 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("RADIUS", Double.valueOf(Double.parseDouble(SPTBatch_.RADIUS)));
/* 1039 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("TARGET_CHANNEL", 
/* 1040 */             Integer.valueOf(Integer.parseInt(SPTBatch_.access$98(SPTBatch_.this))));
/* 1041 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("THRESHOLD", Double.valueOf(Double.parseDouble(SPTBatch_.access$99(SPTBatch_.this))));
/* 1042 */         (SPTBatch_.access$96(SPTBatch_.this)).detectorSettings.put("DO_MEDIAN_FILTERING", 
/* 1043 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$100(SPTBatch_.this))));
/* 1044 */         if (SPTBatch_.access$101(SPTBatch_.this) != null) {
/* 1045 */           (SPTBatch_.access$96(SPTBatch_.this)).initialSpotFilterValue = Double.valueOf(Double.parseDouble(SPTBatch_.access$101(SPTBatch_.this)));
/*      */         }
/*      */       } 
/* 1048 */       List<FeatureFilter> spotFilters = new ArrayList<>(); int m;
/* 1049 */       for (m = 0; m < spotFilterFeature.size(); m++)
/* 1050 */         spotFilters.add(new FeatureFilter(spotFilterFeature.get(m), 
/* 1051 */               Double.valueOf(spotFilterValue.get(m)).doubleValue(), 
/* 1052 */               Boolean.valueOf(spotFilterAbove.get(m)).booleanValue())); 
/* 1053 */       for (m = 0; m < spotFilters.size(); m++) {
/* 1054 */         SPTBatch_.access$96(SPTBatch_.this).addSpotFilter(spotFilters.get(m));
/*      */       }
/* 1056 */       if (SPTBatch_.access$102(SPTBatch_.this).equals("MANUAL_TRACKER")) {
/*      */         
/* 1058 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new ManualTrackerFactory();
/* 1059 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*      */       } 
/*      */       
/* 1062 */       if (SPTBatch_.access$102(SPTBatch_.this).equals("MANUAL_TRACKER")) {
/*      */         
/* 1064 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new ManualTrackerFactory();
/* 1065 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/*      */       } 
/*      */ 
/*      */       
/* 1069 */       if (SPTBatch_.access$102(SPTBatch_.this).equals("KALMAN_TRACKER")) {
/*      */         
/* 1071 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new KalmanTrackerFactory();
/* 1072 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/* 1073 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("KALMAN_SEARCH_RADIUS", 
/* 1074 */             Double.valueOf(Double.parseDouble(SPTBatch_.RADIUS)));
/*      */       } 
/*      */ 
/*      */       
/* 1078 */       if (SPTBatch_.access$102(SPTBatch_.this).equals("SIMPLE_SPARSE_LAP_TRACKER")) {
/*      */         
/* 1080 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new SimpleSparseLAPTrackerFactory();
/* 1081 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/* 1082 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("LINKING_MAX_DISTANCE", 
/* 1083 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$103(SPTBatch_.this))));
/* 1084 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", 
/* 1085 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$104(SPTBatch_.this))));
/* 1086 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("MAX_FRAME_GAP", 
/* 1087 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$105(SPTBatch_.this))));
/*      */       } 
/*      */ 
/*      */       
/* 1091 */       if (SPTBatch_.access$102(SPTBatch_.this).equals("SPARSE_LAP_TRACKER")) {
/* 1092 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerFactory = (SpotTrackerFactory)new SparseLAPTrackerFactory();
/* 1093 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings = LAPUtils.getDefaultLAPSettingsMap();
/* 1094 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("LINKING_MAX_DISTANCE", 
/* 1095 */             Double.valueOf(Double.parseDouble(SPTBatch_.access$103(SPTBatch_.this))));
/* 1096 */         Map<String, Double> linkingPenalty = 
/* 1097 */           (Map<String, Double>)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/*      */               }
/* 1099 */             }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/* 1100 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("ALLOW_GAP_CLOSING", 
/* 1101 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$106(SPTBatch_.this))));
/* 1102 */         if (Boolean.parseBoolean(SPTBatch_.access$106(SPTBatch_.this))) {
/* 1103 */           (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("MAX_FRAME_GAP", 
/* 1104 */               Integer.valueOf(Integer.parseInt(SPTBatch_.access$105(SPTBatch_.this))));
/* 1105 */           (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("GAP_CLOSING_MAX_DISTANCE", 
/* 1106 */               Double.valueOf(Double.parseDouble(SPTBatch_.access$104(SPTBatch_.this))));
/*      */           
/* 1108 */           Map map = 
/* 1109 */             (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/* 1110 */                 } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */         } 
/*      */ 
/*      */         
/* 1114 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("ALLOW_TRACK_SPLITTING", 
/* 1115 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$107(SPTBatch_.this))));
/* 1116 */         if (Boolean.parseBoolean(SPTBatch_.access$107(SPTBatch_.this))) {
/* 1117 */           (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("SPLITTING_MAX_DISTANCE", 
/* 1118 */               Double.valueOf(Double.parseDouble(SPTBatch_.access$108(SPTBatch_.this))));
/* 1119 */           Map map = 
/* 1120 */             (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/* 1121 */                 } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */         } 
/*      */ 
/*      */         
/* 1125 */         (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("ALLOW_TRACK_MERGING", 
/* 1126 */             Boolean.valueOf(Boolean.parseBoolean(SPTBatch_.access$109(SPTBatch_.this))));
/* 1127 */         if (Boolean.parseBoolean(SPTBatch_.access$109(SPTBatch_.this))) {
/* 1128 */           (SPTBatch_.access$96(SPTBatch_.this)).trackerSettings.put("MERGING_MAX_DISTANCE", 
/* 1129 */               Double.valueOf(Double.parseDouble(SPTBatch_.access$110(SPTBatch_.this))));
/* 1130 */           Map map = 
/* 1131 */             (Map)Stream.<Object[]>of(new Object[][] { { "MEAN_INTENSITY", Double.valueOf(1.0D) }, { "QUALITY", Double.valueOf(1.0D)
/* 1132 */                 } }).collect(Collectors.toMap(data -> (String)data[0], data -> (Double)data[1]));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1137 */       List<FeatureFilter> trackFilters = new ArrayList<>(); int n;
/* 1138 */       for (n = 0; n < trackFilterFeature.size(); n++)
/* 1139 */         trackFilters.add(new FeatureFilter(trackFilterFeature.get(n), 
/* 1140 */               Double.valueOf(trackFilterValue.get(n)).doubleValue(), 
/* 1141 */               Boolean.valueOf(trackFilterAbove.get(n)).booleanValue())); 
/* 1142 */       for (n = 0; n < trackFilters.size(); n++) {
/* 1143 */         SPTBatch_.access$96(SPTBatch_.this).addTrackFilter(trackFilters.get(n));
/*      */       }
/* 1145 */       if (SPTBatch_.checkboxSubBg.isSelected()) {
/* 1146 */         SPTBatch_.slices = SPTBatch_.stack2images(SPTBatch_.impsSubBg);
/* 1147 */         SPTBatch_.slicesIntensityBg = new double[SPTBatch_.slices.length];
/* 1148 */         SPTBatch_.slicesIntensitySpot = new double[SPTBatch_.slices.length];
/* 1149 */         if (SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 0) {
/* 1150 */           SPTBatch_.impMaxProject = ZProjector.run(SPTBatch_.impsSubBg.duplicate(), "max");
/* 1151 */           SPTBatch_.impMaxProject.show();
/* 1152 */           SPTBatch_.roiManager = RoiManager.getInstance();
/* 1153 */           if (SPTBatch_.roiManager == null) {
/* 1154 */             SPTBatch_.roiManager = new RoiManager();
/*      */           }
/* 1156 */           SPTBatch_.roiManager.reset();
/* 1157 */           SPTBatch_.impMaxProject.getCanvas().addMouseListener(new MouseAdapter() {
/*      */                 public void mouseClicked(MouseEvent e) {
/* 1159 */                   if (e.getClickCount() == 2) {
/*      */                     
/* 1161 */                     Roi roi = new Roi(SPTBatch_.impMaxProject.getCanvas().offScreenX(e.getX()), 
/* 1162 */                         SPTBatch_.impMaxProject.getCanvas().offScreenY(e.getY()), 5, 5);
/* 1163 */                     SPTBatch_.impMaxProject.setRoi(roi);
/* 1164 */                     SPTBatch_.roiManager.runCommand(SPTBatch_.impMaxProject, "Show All with labels");
/* 1165 */                     SPTBatch_.roiManager.addRoi(roi);
/*      */                   } 
/*      */                 }
/*      */               });
/*      */ 
/*      */           
/* 1171 */           Dialog4BgSub0 userDialog = new Dialog4BgSub0("Action Required", 
/* 1172 */               "Please select manually areas to measure background.");
/* 1173 */           userDialog.show();
/*      */         } 
/*      */         
/* 1176 */         if (SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 2)
/*      */         {
/*      */           
/* 1179 */           for (n = 0; n < SPTBatch_.slices.length; n++) {
/* 1180 */             ImagePlus slicesDup = SPTBatch_.slices[n].duplicate();
/* 1181 */             IJ.run(slicesDup, "Auto Threshold", "method=Otsu ignore_black white");
/* 1182 */             slicesDup = new ImagePlus(slicesDup.getTitle(), 
/* 1183 */                 Morphology.dilation(slicesDup.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1184 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1185 */             IJ.run(slicesDup, "Fill Holes", "");
/* 1186 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1187 */             IJ.run(slicesDup, "Create Selection", "");
/* 1188 */             Roi roiToMeasure = slicesDup.getRoi();
/* 1189 */             IJ.run(slicesDup, "Make Inverse", "");
/* 1190 */             Roi roiToMeasureInv = slicesDup.getRoi();
/* 1191 */             SPTBatch_.slices[n].setRoi(roiToMeasure);
/* 1192 */             double meanDirect = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1193 */             SPTBatch_.slices[n].setRoi(roiToMeasureInv);
/* 1194 */             double meanInv = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1195 */             if (meanDirect > meanInv) {
/* 1196 */               SPTBatch_.slicesIntensitySpot[n] = meanDirect;
/*      */             } else {
/* 1198 */               SPTBatch_.slicesIntensitySpot[n] = meanInv;
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 1203 */         if (SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 3) {
/* 1204 */           for (n = 0; n < SPTBatch_.slices.length; n++) {
/* 1205 */             ImagePlus slicesCell = SPTBatch_.slices[n].duplicate();
/* 1206 */             IJ.run(slicesCell, "Auto Threshold", "method=Otsu ignore_black white");
/* 1207 */             slicesCell = new ImagePlus(slicesCell.getTitle(), 
/* 1208 */                 Morphology.dilation(slicesCell.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1209 */             IJ.run(slicesCell, "Invert LUT", "");
/* 1210 */             IJ.run(slicesCell, "Fill Holes", "");
/* 1211 */             IJ.run(slicesCell, "Invert LUT", "");
/* 1212 */             IJ.run(slicesCell, "Create Selection", "");
/* 1213 */             Roi roiCell = slicesCell.getRoi();
/* 1214 */             SPTBatch_.slices[n].setRoi(roiCell);
/* 1215 */             double meanDirect = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1216 */             IJ.run(slicesCell, "Make Inverse", "");
/* 1217 */             Roi roiCellInv = slicesCell.getRoi();
/* 1218 */             SPTBatch_.slices[n].setRoi(roiCellInv);
/* 1219 */             double meanInv = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1220 */             if (meanDirect > meanInv) {
/* 1221 */               roiCell = roiCell;
/*      */             } else {
/* 1223 */               roiCell = roiCellInv;
/*      */             } 
/*      */             
/* 1226 */             ImagePlus slicesSpot = SPTBatch_.slices[n].duplicate();
/* 1227 */             String value = String.valueOf(Double.valueOf(SPTBatch_.RADIUS).doubleValue() / 
/* 1228 */                 (SPTBatch_.imps.getCalibration()).pixelWidth);
/* 1229 */             if (value.contains(","))
/* 1230 */               value = value.replaceAll(",", "."); 
/* 1231 */             IJ.run(slicesSpot, "Subtract Background...", String.format("rolling=%s", new Object[] { value }));
/* 1232 */             IJ.run(slicesSpot, "Auto Threshold", "method=Otsu ignore_black white");
/* 1233 */             IJ.run(slicesSpot, "Create Selection", "");
/* 1234 */             Roi roiSpots = slicesSpot.getRoi();
/*      */             
/* 1236 */             SPTBatch_.slices[n].setRoi(roiSpots);
/* 1237 */             double meanDirectSpots = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1238 */             IJ.run(slicesSpot, "Make Inverse", "");
/* 1239 */             Roi roiSpotInv = slicesCell.getRoi();
/* 1240 */             SPTBatch_.slices[n].setRoi(roiCellInv);
/* 1241 */             double meanInvSpots = (SPTBatch_.slices[n].getStatistics()).mean;
/* 1242 */             if (meanDirect > meanInv) {
/* 1243 */               roiSpots = roiSpots;
/*      */             } else {
/* 1245 */               roiSpots = roiSpotInv;
/*      */             } 
/*      */             
/* 1248 */             ShapeRoi shapeRoi = (new ShapeRoi(roiCell)).xor(new ShapeRoi(roiSpots));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1259 */             SPTBatch_.slices[n].setRoi((Roi)shapeRoi);
/* 1260 */             SPTBatch_.slicesIntensitySpot[n] = (SPTBatch_.slices[n].getStatistics()).mean - 
/* 1261 */               (SPTBatch_.slices[n].getStatistics()).stdDev;
/*      */           } 
/*      */         }
/* 1264 */         if (SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 4)
/*      */         {
/*      */           
/* 1267 */           for (n = 0; n < SPTBatch_.slices.length; n++) {
/* 1268 */             ImagePlus slicesDup = SPTBatch_.slices[n].duplicate();
/* 1269 */             IJ.run(slicesDup, "Auto Threshold", "method=Otsu ignore_black white");
/* 1270 */             slicesDup = new ImagePlus(slicesDup.getTitle(), 
/* 1271 */                 Morphology.dilation(slicesDup.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1272 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1273 */             IJ.run(slicesDup, "Fill Holes", "");
/* 1274 */             IJ.run(slicesDup, "Invert LUT", "");
/* 1275 */             IJ.run(slicesDup, "Create Selection", "");
/*      */             
/* 1277 */             Roi roiToMeasure = slicesDup.getRoi();
/* 1278 */             ImagePlus sliceDup2 = SPTBatch_.slices[n].duplicate();
/* 1279 */             sliceDup2.setRoi(roiToMeasure);
/* 1280 */             IJ.run(sliceDup2, "Clear Outside", "");
/* 1281 */             BackgroundSubtracter bgSubt = new BackgroundSubtracter();
/* 1282 */             ImageProcessor ip = sliceDup2.getProcessor();
/* 1283 */             sliceDup2.getProcessor().resetMinAndMax();
/* 1284 */             boolean isRGB = sliceDup2.getProcessor() instanceof ColorProcessor;
/* 1285 */             boolean calledAsPlugin = true;
/* 1286 */             double radius = Double.valueOf(SPTBatch_.RADIUS).doubleValue() / 
/* 1287 */               (SPTBatch_.imps.getCalibration()).pixelWidth;
/* 1288 */             boolean lightBackground = false;
/* 1289 */             boolean separateColors = false;
/* 1290 */             boolean createBackground = false;
/* 1291 */             boolean useParaboloid = false;
/* 1292 */             boolean doPresmooth = false;
/* 1293 */             boolean previewing = false;
/* 1294 */             boolean correctCorners = true;
/* 1295 */             SPTBatch_.slicesIntensitySpot[n] = bgSubt.rollingBallBackground(ip, radius, createBackground, 
/* 1296 */                 lightBackground, useParaboloid, doPresmooth, correctCorners);
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1303 */       SPTBatch_.access$96(SPTBatch_.this).addAllAnalyzers();
/* 1304 */       SPTBatch_.model = new Model();
/* 1305 */       SPTBatch_.access$111(SPTBatch_.this, new TrackMate(SPTBatch_.model, SPTBatch_.access$96(SPTBatch_.this)));
/* 1306 */       Boolean ok = Boolean.valueOf(SPTBatch_.access$112(SPTBatch_.this).checkInput());
/* 1307 */       if (ok != Boolean.TRUE) {
/* 1308 */         SPTBatch_.taskOutput.append(SPTBatch_.access$112(SPTBatch_.this).getErrorMessage());
/*      */       }
/* 1310 */       ok = Boolean.valueOf(SPTBatch_.access$112(SPTBatch_.this).process());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1329 */       SPTBatch_.selectionModel = new SelectionModel(SPTBatch_.model);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1352 */       SPTBatch_.model.setLogger(Logger.IJ_LOGGER);
/* 1353 */       SpotCollection spots = SPTBatch_.model.getSpots();
/* 1354 */       SPTBatch_.taskOutput.append(spots.toString());
/* 1355 */       FeatureModel fm = SPTBatch_.model.getFeatureModel();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1360 */       SPTBatch_.this.tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, "TRACK_INDEX", null, null, 
/* 1361 */           Colormap.Turbo, 0.0D, 1.0D);
/* 1362 */       SPTBatch_.taskOutput.append("\n\nSETTINGS:");
/* 1363 */       SPTBatch_.taskOutput.append(SPTBatch_.access$96(SPTBatch_.this).toString());
/*      */       
/* 1365 */       SPTBatch_.taskOutput.append(SPTBatch_.model.toString());
/* 1366 */       SPTBatch_.taskOutput.append("Found" + SPTBatch_.model.getTrackModel().nTracks(true) + " tracks.");
/* 1367 */       SPTBatch_.taskOutput.append(SPTBatch_.access$96(SPTBatch_.this).toStringFeatureAnalyzersInfo());
/*      */       
/* 1369 */       Integer firstFrame = null;
/* 1370 */       Integer lastFrame = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1381 */       SPTBatch_.this.ds = DisplaySettingsIO.readUserDefault();
/* 1382 */       SPTBatch_.this.ds.setSpotShowName(SPTBatch_.checkDispSpotName.isSelected());
/* 1383 */       SPTBatch_.this.ds.setSpotVisible(SPTBatch_.checkDispSpots.isSelected());
/* 1384 */       SPTBatch_.this.ds.setSpotColorBy(DisplaySettings.TrackMateObject.TRACKS, "TRACK_INDEX");
/* 1385 */       SPTBatch_.this.ds.setTrackVisible(SPTBatch_.checkDispTracks.isSelected());
/* 1386 */       SPTBatch_.this.ds.setTrackColorBy(DisplaySettings.TrackMateObject.TRACKS, "TRACK_INDEX");
/* 1387 */       if (SPTBatch_.access$28(SPTBatch_.this).getSelectedIndex() == 0)
/* 1388 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.FULL); 
/* 1389 */       if (SPTBatch_.access$28(SPTBatch_.this).getSelectedIndex() == 1)
/* 1390 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.LOCAL); 
/* 1391 */       if (SPTBatch_.access$28(SPTBatch_.this).getSelectedIndex() == 2)
/* 1392 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.LOCAL_BACKWARD); 
/* 1393 */       if (SPTBatch_.access$28(SPTBatch_.this).getSelectedIndex() == 3)
/* 1394 */         SPTBatch_.this.ds.setTrackDisplayMode(DisplaySettings.TrackDisplayMode.LOCAL_FORWARD); 
/* 1395 */       SPTBatch_.access$113(SPTBatch_.this, new HyperStackDisplayer(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.imps, SPTBatch_.this.ds));
/* 1396 */       SPTBatch_.access$114(SPTBatch_.this).render();
/* 1397 */       SPTBatch_.access$114(SPTBatch_.this).refresh();
/*      */       
/* 1399 */       if (SPTBatch_.imps.getNFrames() > 1) {
/* 1400 */         firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNFrames(), 1)));
/* 1401 */         lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNFrames(), Math.max(SPTBatch_.imps.getNFrames(), 1)));
/*      */       } 
/* 1403 */       if (SPTBatch_.imps.getNSlices() > 1) {
/* 1404 */         firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNSlices(), 1)));
/* 1405 */         lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNSlices(), Math.max(SPTBatch_.imps.getNSlices(), 1)));
/*      */       } 
/*      */       
/* 1408 */       SPTBatch_.taskOutput.append(
/* 1409 */           "Capturing TrackMate overlay from frame " + firstFrame + " to " + lastFrame + ".\n");
/* 1410 */       Rectangle bounds = SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().getBounds();
/* 1411 */       Integer width = Integer.valueOf(bounds.width);
/* 1412 */       Integer height = Integer.valueOf(bounds.height);
/* 1413 */       Integer nCaptures = Integer.valueOf(lastFrame.intValue() - firstFrame.intValue() + 1);
/* 1414 */       ImageStack stack = new ImageStack(width.intValue(), height.intValue());
/* 1415 */       Integer channel = Integer.valueOf(SPTBatch_.access$114(SPTBatch_.this).getImp().getChannel());
/* 1416 */       Integer slice = Integer.valueOf(SPTBatch_.access$114(SPTBatch_.this).getImp().getSlice());
/* 1417 */       SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(true);
/* 1418 */       for (int frame = firstFrame.intValue(); frame <= lastFrame.intValue(); frame++) {
/*      */         
/* 1420 */         SPTBatch_.access$114(SPTBatch_.this).getImp().setPositionWithoutUpdate(channel.intValue(), slice.intValue(), frame);
/* 1421 */         BufferedImage bi = new BufferedImage(width.intValue(), height.intValue(), 2);
/* 1422 */         SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().paint(bi.getGraphics());
/* 1423 */         ColorProcessor cp = new ColorProcessor(bi);
/* 1424 */         Integer index = Integer.valueOf(SPTBatch_.access$114(SPTBatch_.this).getImp().getStackIndex(channel.intValue(), slice.intValue(), frame));
/* 1425 */         stack.addSlice(SPTBatch_.access$114(SPTBatch_.this).getImp().getImageStack().getSliceLabel(index.intValue()), (ImageProcessor)cp);
/*      */       } 
/* 1427 */       SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(false);
/* 1428 */       SPTBatch_.access$115(SPTBatch_.this, new ImagePlus("TrackMate capture of " + SPTBatch_.access$114(SPTBatch_.this).getImp().getShortTitle(), stack));
/*      */       
/* 1430 */       SPTBatch_.access$117(SPTBatch_.access$114(SPTBatch_.this).getImp(), SPTBatch_.access$116(SPTBatch_.this));
/* 1431 */       SPTBatch_.taskOutput.append(" done.\n");
/*      */ 
/*      */       
/* 1434 */       if (SPTBatch_.checkboxRoi.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         
/* 1436 */         double dx = (SPTBatch_.imps.getCalibration()).pixelWidth;
/* 1437 */         double dy = (SPTBatch_.imps.getCalibration()).pixelHeight;
/* 1438 */         double dz = (SPTBatch_.imps.getCalibration()).pixelDepth;
/* 1439 */         RoiManager roiManager = RoiManager.getInstance();
/* 1440 */         if (roiManager == null) {
/* 1441 */           roiManager = new RoiManager();
/*      */         }
/* 1443 */         roiManager.reset();
/* 1444 */         List<Spot> spotsRoi = new ArrayList<>(SPTBatch_.access$112(SPTBatch_.this).getModel().getSpots().getNSpots(true));
/* 1445 */         for (Integer trackID : SPTBatch_.access$112(SPTBatch_.this).getModel().getTrackModel().trackIDs(true))
/* 1446 */           spotsRoi.addAll(SPTBatch_.access$112(SPTBatch_.this).getModel().getTrackModel().trackSpots(trackID)); 
/* 1447 */         for (int s = 0; s < spotsRoi.size(); s++) {
/* 1448 */           OvalRoi ovalRoi; SpotRoi sroi = ((Spot)spotsRoi.get(s)).getRoi();
/*      */           
/* 1450 */           if (sroi != null) {
/* 1451 */             double[] xs = sroi.toPolygonX(dx, 0.0D, ((Spot)spotsRoi.get(s)).getDoublePosition(0), 1.0D);
/* 1452 */             double[] ys = sroi.toPolygonY(dy, 0.0D, ((Spot)spotsRoi.get(s)).getDoublePosition(1), 1.0D);
/* 1453 */             float[] xp = SPTBatch_.access$118(xs);
/* 1454 */             float[] yp = SPTBatch_.access$118(ys);
/* 1455 */             PolygonRoi polygonRoi = new PolygonRoi(xp, yp, 2);
/*      */           } else {
/* 1457 */             double diameter = 2.0D * ((Spot)spotsRoi.get(s)).getFeature("RADIUS").doubleValue() / dx;
/* 1458 */             double xs = ((Spot)spotsRoi.get(s)).getDoublePosition(0) / dx - diameter / 2.0D + 0.5D;
/* 1459 */             double ys = ((Spot)spotsRoi.get(s)).getDoublePosition(1) / dy - diameter / 2.0D + 0.5D;
/* 1460 */             ovalRoi = new OvalRoi(xs, ys, diameter, diameter);
/*      */           } 
/*      */           
/* 1463 */           int z = 1 + (int)Math.round(((Spot)spotsRoi.get(s)).getDoublePosition(2) / dz);
/* 1464 */           int i1 = 1 + ((Spot)spotsRoi.get(s)).getFeature("FRAME").intValue();
/* 1465 */           ovalRoi.setPosition(0, z, i1);
/* 1466 */           ovalRoi.setName(((Spot)spotsRoi.get(s)).getName());
/* 1467 */           roiManager.addRoi((Roi)ovalRoi);
/*      */         } 
/*      */         
/* 1470 */         if ((roiManager.getRoisAsArray()).length != 0) {
/* 1471 */           roiManager.runCommand("Save", 
/* 1472 */               SPTBatch_.directSPT + File.separator + SPTBatch_.imps.getShortTitle() + "_" + "RoiSet.zip");
/*      */         }
/* 1474 */         roiManager.close();
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
/* 1497 */       if (SPTBatch_.checkbox2.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         
/* 1499 */         SPTBatch_.taskOutput.append(SPTBatch_.model.toString());
/* 1500 */         ISBIChallengeExporterModified.exportToFile(SPTBatch_.model, SPTBatch_.access$96(SPTBatch_.this), new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + 
/* 1501 */               File.separator + "TrackMate_" + SPTBatch_.imps.getShortTitle() + ".xml"));
/*      */ 
/*      */ 
/*      */         
/* 1505 */         SPTBatch_.taskOutput.append("\nDone.");
/*      */       } 
/*      */       
/* 1508 */       if (SPTBatch_.access$119(SPTBatch_.this) == "ST") {
/* 1509 */         Model model = SPTBatch_.access$112(SPTBatch_.this).getModel();
/* 1510 */         Settings settings = SPTBatch_.access$112(SPTBatch_.this).getSettings();
/* 1511 */         SpotCollection spots1 = model.getSpots();
/* 1512 */         int nFrames = spots1.keySet().size();
/* 1513 */         double[][] data = new double[2][nFrames];
/* 1514 */         int indexx = 0;
/* 1515 */         for (Iterator<Integer> iterator = spots1.keySet().iterator(); iterator.hasNext(); ) { int i1 = ((Integer)iterator.next()).intValue();
/* 1516 */           data[1][indexx] = spots1.getNSpots(i1, true);
/* 1517 */           if (data[1][indexx] > 0.0D) {
/* 1518 */             data[0][indexx] = ((Spot)spots1.iterable(i1, false).iterator().next())
/* 1519 */               .getFeature("POSITION_T").doubleValue();
/*      */           } else {
/* 1521 */             data[0][indexx] = i1 * settings.dt;
/*      */           } 
/* 1523 */           indexx++; }
/*      */ 
/*      */         
/* 1526 */         String xAxisLabel = "Time (" + SPTBatch_.access$112(SPTBatch_.this).getModel().getTimeUnits() + ")";
/* 1527 */         String yAxisLabel = "N spots";
/* 1528 */         String title = "Nspots vs Time for " + (SPTBatch_.access$112(SPTBatch_.this).getSettings()).imp.getShortTitle();
/* 1529 */         DefaultXYDataset dataset = new DefaultXYDataset();
/* 1530 */         dataset.addSeries("Nspots", data);
/*      */         
/* 1532 */         SPTBatch_.access$120(SPTBatch_.this, ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, (XYDataset)dataset, 
/* 1533 */               PlotOrientation.VERTICAL, true, true, false));
/* 1534 */         SPTBatch_.access$121(SPTBatch_.this).getTitle().setFont(Fonts.FONT);
/* 1535 */         SPTBatch_.access$121(SPTBatch_.this).getLegend().setItemFont(Fonts.SMALL_FONT);
/* 1536 */         ExportableChartPanel exportableChartPanel = new ExportableChartPanel(SPTBatch_.access$121(SPTBatch_.this));
/*      */       } 
/*      */ 
/*      */       
/* 1540 */       if (SPTBatch_.checkbox1.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1547 */         if (SPTBatch_.access$122(SPTBatch_.this).equals("spotTable")) {
/*      */ 
/*      */ 
/*      */           
/* 1551 */           TablePanel<Spot> spotTable = SPTBatch_.access$123(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 1552 */           JTable spotJTable = spotTable.getTable();
/* 1553 */           TablePanel<Integer> trackTable = SPTBatch_.access$124(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 1554 */           SPTBatch_.trackJTable = trackTable.getTable();
/* 1555 */           SPTBatch_.nOfTracks = new ArrayList<>();
/* 1556 */           for (int t = 0; t < SPTBatch_.trackJTable.getModel().getRowCount(); t++)
/* 1557 */             SPTBatch_.nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(t, 2).toString())); 
/* 1558 */           SPTBatch_.this.indexes = new ArrayList<>();
/* 1559 */           Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
/* 1560 */           Set<Spot> track = null;
/* 1561 */           int counter = 0;
/*      */           
/* 1563 */           for (int i1 = 0; i1 < SPTBatch_.nOfTracks.size(); i1++) {
/* 1564 */             ArrayList<Float> framesByTrack = new ArrayList<>();
/* 1565 */             ArrayList<Float> framesByTrackSort = new ArrayList<>();
/*      */             
/* 1567 */             for (int r = 0; r < spotJTable.getRowCount(); r++) {
/*      */               
/* 1569 */               if (spotJTable.getModel().getValueAt(r, 2).toString()
/* 1570 */                 .equals(String.valueOf(((Integer)SPTBatch_.nOfTracks.get(i1)).intValue()))) {
/* 1571 */                 framesByTrack
/* 1572 */                   .add(Float.valueOf(spotJTable.getModel().getValueAt(r, 8).toString()));
/* 1573 */                 framesByTrackSort
/* 1574 */                   .add(Float.valueOf(spotJTable.getModel().getValueAt(r, 8).toString()));
/*      */               } 
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1580 */             Collections.sort(framesByTrackSort);
/*      */             
/* 1582 */             for (int z = 0; z < framesByTrackSort.size(); z++) {
/* 1583 */               counter++;
/* 1584 */               if (i1 == 0)
/* 1585 */                 SPTBatch_.this.indexes.add(Integer.valueOf(framesByTrack.indexOf(framesByTrackSort.get(z)))); 
/* 1586 */               if (i1 != 0) {
/* 1587 */                 SPTBatch_.this.indexes.add(
/* 1588 */                     Integer.valueOf(counter - 1 + framesByTrack.indexOf(framesByTrackSort.get(z)) - z));
/*      */               }
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1594 */           if (SPTBatch_.checkboxSubBg.isSelected()) {
/*      */             
/* 1596 */             SPTBatch_.columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", 
/* 1597 */                 "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", 
/* 1598 */                 "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", 
/* 1599 */                 "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", 
/* 1600 */                 "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1", "Intensity-Bg Subtract" };
/*      */             
/* 1602 */             String[][] rowDataSpot = new String[SPTBatch_.this.indexes.size()][SPTBatch_.columnNamesSpot.length];
/*      */             int r;
/* 1604 */             for (r = 0; r < SPTBatch_.this.indexes.size(); r++) {
/* 1605 */               rowDataSpot[r][SPTBatch_.columnNamesSpot.length - 1] = "";
/* 1606 */               for (int c = 0; c < spotJTable.getModel().getColumnCount(); c++) {
/* 1607 */                 rowDataSpot[r][c] = String.valueOf(
/* 1608 */                     spotJTable.getModel().getValueAt(((Integer)SPTBatch_.this.indexes.get(r)).intValue(), c));
/*      */               }
/*      */             } 
/*      */             
/* 1612 */             if (SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 1) {
/* 1613 */               for (r = 0; r < rowDataSpot.length; r++) {
/* 1614 */                 rowDataSpot[r][SPTBatch_.columnNamesSpot.length - 1] = 
/* 1615 */                   String.valueOf(Double.valueOf(rowDataSpot[r][12].toString()).doubleValue() - 
/* 1616 */                     Double.valueOf(rowDataSpot[r][19].toString()).doubleValue() * 
/* 1617 */                     Double.valueOf(rowDataSpot[r][17].toString()).doubleValue());
/*      */               }
/* 1619 */               DefaultTableModel tableModel = new DefaultTableModel((Object[][])rowDataSpot, (Object[])SPTBatch_.columnNamesSpot);
/* 1620 */               SPTBatch_.tableSpot = new JTable(tableModel);
/*      */             } 
/*      */             
/* 1623 */             if (SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 0 || SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 2 || 
/* 1624 */               SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 3 || SPTBatch_.access$17(SPTBatch_.this).getSelectedIndex() == 4) {
/*      */               
/* 1626 */               for (r = 0; r < spotJTable.getModel().getRowCount(); r++) {
/* 1627 */                 for (int i2 = 0; i2 < SPTBatch_.slicesIntensitySpot.length; i2++) {
/* 1628 */                   if (Integer.valueOf(rowDataSpot[r][8].toString())
/* 1629 */                     .equals(Integer.valueOf(i2)) == Boolean.TRUE.booleanValue())
/* 1630 */                     rowDataSpot[r][SPTBatch_.columnNamesSpot.length - 1] = 
/* 1631 */                       String.valueOf(Double.valueOf(rowDataSpot[r][12].toString()).doubleValue() - 
/* 1632 */                         Double.valueOf(SPTBatch_.slicesIntensitySpot[i2]).doubleValue()); 
/*      */                 } 
/*      */               } 
/* 1635 */               SPTBatch_.tableSpot = new JTable((Object[][])rowDataSpot, (Object[])SPTBatch_.columnNamesSpot);
/*      */             } 
/*      */ 
/*      */             
/* 1639 */             SPTBatch_.this.exportToCSV(rowDataSpot, SPTBatch_.columnNamesSpot, 
/* 1640 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 1641 */                   "_" + "Spots in tracks statistics" + ".csv"));
/* 1642 */             ResultsTable rtSpotPerImage = new ResultsTable();
/* 1643 */             for (int x = 0; x < rowDataSpot.length; x++) {
/* 1644 */               for (int i2 = 0; i2 < (rowDataSpot[x]).length; i2++)
/* 1645 */                 rtSpotPerImage.setValue(SPTBatch_.columnNamesSpot[i2], x, rowDataSpot[x][i2]); 
/* 1646 */             }  SPTBatch_.this.rtSpots[SPTBatch_.i] = rtSpotPerImage;
/*      */           } 
/* 1648 */           if (!SPTBatch_.checkboxSubBg.isSelected()) {
/*      */             
/* 1650 */             SPTBatch_.columnNamesSpot = new String[] { "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", 
/* 1651 */                 "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", 
/* 1652 */                 "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", 
/* 1653 */                 "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", 
/* 1654 */                 "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1" };
/*      */             
/* 1656 */             String[][] rowDataSpot = new String[SPTBatch_.this.indexes.size()][SPTBatch_.columnNamesSpot.length];
/*      */             
/* 1658 */             for (int r = 0; r < SPTBatch_.this.indexes.size(); r++) {
/* 1659 */               for (int c = 0; c < spotJTable.getModel().getColumnCount(); c++)
/* 1660 */                 rowDataSpot[r][c] = String.valueOf(
/* 1661 */                     spotJTable.getModel().getValueAt(((Integer)SPTBatch_.this.indexes.get(r)).intValue(), c)); 
/* 1662 */             }  SPTBatch_.tableSpot = new JTable((Object[][])rowDataSpot, (Object[])SPTBatch_.columnNamesSpot);
/* 1663 */             SPTBatch_.this.exportToCSV(rowDataSpot, SPTBatch_.columnNamesSpot, 
/* 1664 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 1665 */                   "_" + "Spots in tracks statistics" + ".csv"));
/* 1666 */             ResultsTable rtSpotPerImage = new ResultsTable();
/* 1667 */             for (int x = 0; x < rowDataSpot.length; x++) {
/* 1668 */               for (int i2 = 0; i2 < (rowDataSpot[x]).length; i2++)
/* 1669 */                 rtSpotPerImage.setValue(SPTBatch_.columnNamesSpot[i2], x, rowDataSpot[x][i2]); 
/* 1670 */             }  SPTBatch_.this.rtSpots[SPTBatch_.i] = rtSpotPerImage;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1675 */         if (SPTBatch_.access$125(SPTBatch_.this).equals("linkTable")) {
/*      */           
/* 1677 */           TablePanel<DefaultWeightedEdge> edgeTable = SPTBatch_.access$126(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 1678 */           SPTBatch_.linkJTable = edgeTable.getTable();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 1686 */             edgeTable.exportToCsv(new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 1687 */                   SPTBatch_.imps.getShortTitle() + "_" + "Links in tracks statistics" + ".csv"));
/* 1688 */           } catch (IOException e) {
/*      */             
/* 1690 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */         
/* 1694 */         if (SPTBatch_.checkExcludeTracks.isSelected()) {
/* 1695 */           Roi mainRoi; SPTBatch_.this.excludeTrack = new ArrayList<>();
/* 1696 */           SPTBatch_.impMainRoi = ZProjector.run(SPTBatch_.impsSubBg.duplicate(), "max");
/* 1697 */           ImagePlus impToMeasure = SPTBatch_.impMainRoi.duplicate();
/* 1698 */           IJ.run(SPTBatch_.impMainRoi, "Auto Threshold", "method=Otsu ignore_black white");
/* 1699 */           SPTBatch_.impMainRoi = new ImagePlus(SPTBatch_.impMainRoi.getTitle(), 
/* 1700 */               Morphology.dilation(SPTBatch_.impMainRoi.getProcessor(), Strel.Shape.DISK.fromRadius(2)));
/* 1701 */           IJ.run(SPTBatch_.impMainRoi, "Invert LUT", "");
/* 1702 */           IJ.run(SPTBatch_.impMainRoi, "Fill Holes", "");
/* 1703 */           IJ.run(SPTBatch_.impMainRoi, "Invert LUT", "");
/* 1704 */           IJ.run(SPTBatch_.impMainRoi, "Create Selection", "");
/* 1705 */           Roi roiToMeasure = SPTBatch_.impMainRoi.getRoi();
/* 1706 */           IJ.run(SPTBatch_.impMainRoi, "Make Inverse", "");
/* 1707 */           Roi roiToMeasureInv = SPTBatch_.impMainRoi.getRoi();
/* 1708 */           impToMeasure.setRoi(roiToMeasure);
/* 1709 */           double meanDirect = (SPTBatch_.impMainRoi.getStatistics()).mean;
/* 1710 */           impToMeasure.setRoi(roiToMeasureInv);
/* 1711 */           double meanInv = (SPTBatch_.impMainRoi.getStatistics()).mean;
/*      */           
/* 1713 */           if (meanDirect > meanInv) {
/* 1714 */             mainRoi = roiToMeasure;
/*      */           } else {
/* 1716 */             mainRoi = roiToMeasureInv;
/*      */           } 
/* 1718 */           for (int i1 = 0; i1 < SPTBatch_.trackJTable.getRowCount(); i1++) {
/* 1719 */             if (mainRoi
/* 1720 */               .contains(
/*      */                 
/* 1722 */                 (int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(SPTBatch_.trackJTable.getModel()
/* 1723 */                     .getValueAt(i1, 
/* 1724 */                       SPTBatch_.trackJTable.convertColumnIndexToModel(13))
/* 1725 */                     .toString())), 
/*      */                 
/* 1727 */                 (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(SPTBatch_.trackJTable.getModel()
/* 1728 */                     .getValueAt(i1, 
/* 1729 */                       SPTBatch_.trackJTable.convertColumnIndexToModel(14))
/* 1730 */                     .toString()))) == Boolean.TRUE.booleanValue()) {
/* 1731 */               SPTBatch_.this.excludeTrack.add(Boolean.valueOf(true));
/*      */             } else {
/*      */               
/* 1734 */               SPTBatch_.this.excludeTrack.add(Boolean.valueOf(false));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1739 */         if (SPTBatch_.access$127(SPTBatch_.this).equals("trackTable")) {
/*      */ 
/*      */           
/* 1742 */           Thread tracksThread = new Thread(new Runnable() {
/*      */                 public void run() {
/* 1744 */                   if (SPTBatch_.checkboxSubBg.isSelected()) {
/* 1745 */                     TablePanel<Integer> trackTable = SPTBatch_.access$124(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 1746 */                     SPTBatch_.trackJTable = trackTable.getTable();
/*      */                     
/* 1748 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1749 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1750 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1751 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1752 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1753 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1754 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1755 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1756 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1757 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1758 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1759 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1760 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1761 */                           "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1762 */                           ")", 
/* 1763 */                           "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 1764 */                           "Movement", "sMSS", "sMSS Movement" }; 
/* 1765 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1766 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1767 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1768 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1769 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1770 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1771 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1772 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1773 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1774 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1775 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1776 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1777 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1778 */                           "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1779 */                           ")", 
/* 1780 */                           "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 1781 */                           "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */                     }
/* 1783 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1784 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1785 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1786 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1787 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1788 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1789 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1790 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1791 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1792 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1793 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1794 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1795 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1796 */                           "D1-N", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1797 */                           "sMSS Movement" }; 
/* 1798 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1799 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1800 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1801 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1802 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1803 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1804 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1805 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1806 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1807 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1808 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1809 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1810 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1811 */                           "D1-N", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1812 */                           "sMSS Movement", "Track within Cell" };
/*      */                     }
/* 1814 */                     String[][] rowDataTrack = new String[SPTBatch_.trackJTable
/* 1815 */                         .getRowCount()][SPTBatch_.columnNamesTrack.length];
/* 1816 */                     for (int j = 0; j < SPTBatch_.trackJTable.getRowCount(); j++) {
/* 1817 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "";
/* 1818 */                       for (int c = 0; c < SPTBatch_.trackJTable.getColumnCount(); c++) {
/* 1819 */                         rowDataTrack[j][c] = String.valueOf(SPTBatch_.trackJTable.getValueAt(j, c));
/*      */                       }
/*      */                     } 
/* 1822 */                     List<Integer> nOfTracks = new ArrayList<>();
/* 1823 */                     for (int t = 0; t < SPTBatch_.trackJTable.getRowCount(); t++) {
/* 1824 */                       nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(t, 2).toString()));
/*      */                     }
/*      */                     
/* 1827 */                     List<Double> allTracks = new ArrayList<>();
/* 1828 */                     List<Double> allTracksDef = new ArrayList<>();
/* 1829 */                     for (int k = 0; k < nOfTracks.size(); k++) {
/* 1830 */                       int counter = 0;
/* 1831 */                       List<Double> perTrack = new ArrayList<>();
/* 1832 */                       List<Double> perTrackDef = new ArrayList<>();
/* 1833 */                       for (int m = 0; m < SPTBatch_.tableSpot.getRowCount(); m++) {
/* 1834 */                         if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(m, 2).toString())
/* 1835 */                           .equals(nOfTracks.get(k)) == Boolean.TRUE.booleanValue())
/*      */                         {
/* 1837 */                           perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel()
/* 1838 */                                 .getValueAt(m, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
/*      */                         }
/*      */                       } 
/*      */ 
/*      */                       
/* 1843 */                       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue())
/*      */                       {
/* 1845 */                         for (int n = SPTBatch_.minTracksJTF; n < SPTBatch_.maxTracksJTF; n++)
/*      */                         {
/* 1847 */                           perTrackDef.add(perTrack.get(n));
/*      */                         }
/*      */                       }
/*      */                       
/* 1851 */                       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1852 */                         perTrackDef.size() != 0) {
/* 1853 */                         allTracksDef.add(Double.valueOf(perTrackDef.stream()
/* 1854 */                               .mapToDouble(a -> a.doubleValue()).average().getAsDouble()));
/*      */                       }
/* 1856 */                       if (perTrack.size() != 0)
/* 1857 */                         allTracks.add(Double.valueOf(
/* 1858 */                               perTrack.stream().mapToDouble(a -> a.doubleValue()).average().getAsDouble())); 
/*      */                     } 
/* 1860 */                     ComputeMSD values = new ComputeMSD(Integer.valueOf(SPTBatch_.pointsMSD.getText()).intValue());
/* 1861 */                     values.Compute(nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 1862 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1863 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/*      */                     {
/* 1865 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1866 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1867 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1868 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1869 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1870 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1871 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1872 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1873 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1874 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1875 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 1876 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1877 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1878 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1879 */                           String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/* 1880 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1881 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1882 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1883 */                             String.valueOf("Long");
/*      */                         } else {
/* 1885 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1886 */                             String.valueOf("Short");
/*      */                         } 
/* 1888 */                         if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1889 */                           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1890 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1891 */                               String.valueOf("Immobile");
/*      */                           }
/* 1893 */                           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1894 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1895 */                               String.valueOf("Mobile"); 
/*      */                         } 
/* 1897 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1898 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */                         
/* 1900 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1901 */                           Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.0D)
/* 1902 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1903 */                             String.valueOf("Confined"); 
/* 1904 */                         if (Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() < 0.9D && 
/* 1905 */                           Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.6D)
/* 1906 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1907 */                             String.valueOf("Anomalous"); 
/* 1908 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1909 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1910 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1911 */                             String.valueOf("Free"); 
/* 1912 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1913 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1914 */                             String.valueOf("Directed");
/*      */                         }
/*      */                         
/* 1917 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 1918 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 1919 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1920 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1921 */                               1] = "Unidirectional Ballistic"; 
/* 1922 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 1923 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 1924 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 1925 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 1926 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 1927 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 1928 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 1929 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
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
/* 1940 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1941 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1942 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1943 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 1944 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1945 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1946 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1947 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1948 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1949 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1950 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1951 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1952 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 1953 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1954 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1955 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1956 */                           String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/*      */                         
/* 1958 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1959 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1960 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1961 */                             String.valueOf("Long");
/*      */                         } else {
/* 1963 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1964 */                             String.valueOf("Short");
/*      */                         } 
/* 1966 */                         if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1967 */                           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1968 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1969 */                               String.valueOf("Immobile");
/*      */                           }
/* 1971 */                           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1972 */                             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1973 */                               String.valueOf("Mobile"); 
/*      */                         } 
/* 1975 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1976 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */                         
/* 1978 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1979 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 1980 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1981 */                             String.valueOf("Confined"); 
/* 1982 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 1983 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 1984 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1985 */                             String.valueOf("Anomalous"); 
/* 1986 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1987 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1988 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1989 */                             String.valueOf("Free"); 
/* 1990 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1991 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1992 */                             String.valueOf("Directed");
/*      */                         }
/*      */                         
/* 1995 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1996 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 1997 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1998 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1999 */                               2] = "Unidirectional Ballistic"; 
/* 2000 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2001 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2002 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2003 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2004 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2005 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2006 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2007 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */                         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2015 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2016 */                           .toString();
/*      */                       } 
/*      */                     }
/*      */                     
/* 2020 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2021 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2022 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2023 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2024 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2025 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2026 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2027 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2028 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2029 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2030 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2031 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2032 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2033 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2034 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 2035 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2036 */                           String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2037 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2038 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2039 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2040 */                           String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/* 2041 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2042 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2043 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2044 */                             String.valueOf("Long");
/*      */                         } else {
/* 2046 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2047 */                             String.valueOf("Short");
/*      */                         } 
/* 2049 */                         if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2050 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2051 */                             String.valueOf("Immobile");
/*      */                         }
/* 2053 */                         if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2054 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2055 */                             String.valueOf("Mobile"); 
/* 2056 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2057 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2058 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2059 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2060 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2061 */                             String.valueOf("Confined"); 
/* 2062 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2063 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2064 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2065 */                             String.valueOf("Anomalous"); 
/* 2066 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2067 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2068 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2069 */                             String.valueOf("Free"); 
/* 2070 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2071 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2072 */                             String.valueOf("Directed");
/*      */                         }
/*      */ 
/*      */                         
/* 2076 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2077 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 2078 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2079 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2080 */                               1] = "Unidirectional Ballistic"; 
/* 2081 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2082 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2083 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2084 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2085 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2086 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2087 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2088 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
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
/* 2099 */                     if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2100 */                       SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2101 */                       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2102 */                           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2103 */                           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2104 */                           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2105 */                           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2106 */                           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2107 */                           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2108 */                           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 2109 */                           "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 2110 */                           "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 2111 */                           "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 2112 */                           "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 2113 */                           ")", 
/* 2114 */                           "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 2115 */                           "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */                       
/* 2117 */                       for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2118 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 15] = 
/* 2119 */                           String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2120 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2121 */                           String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2122 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2123 */                           String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2124 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2125 */                           String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2126 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2127 */                           String.valueOf(((Double)allTracks.get(m)).toString());
/* 2128 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2129 */                           String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2130 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2131 */                           String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2132 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2133 */                           String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/* 2134 */                         if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2135 */                             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2136 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2137 */                             String.valueOf("Long");
/*      */                         } else {
/* 2139 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2140 */                             String.valueOf("Short");
/*      */                         } 
/* 2142 */                         if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2143 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2144 */                             String.valueOf("Immobile");
/*      */                         }
/* 2146 */                         if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2147 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2148 */                             String.valueOf("Mobile"); 
/* 2149 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2150 */                           String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2151 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2152 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2153 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2154 */                             String.valueOf("Confined"); 
/* 2155 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2156 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2157 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2158 */                             String.valueOf("Anomalous"); 
/* 2159 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2160 */                           Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2161 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2162 */                             String.valueOf("Free"); 
/* 2163 */                         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2164 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2165 */                             String.valueOf("Directed");
/*      */                         }
/*      */ 
/*      */                         
/* 2169 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2170 */                           String.valueOf(ComputeMSD.mssValues.get(m));
/* 2171 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2172 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2173 */                               2] = "Unidirectional Ballistic"; 
/* 2174 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2175 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2176 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2177 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2178 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2179 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2180 */                         if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2181 */                           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */                         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                         
/* 2189 */                         rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2190 */                           .toString();
/*      */                       } 
/*      */                     } 
/*      */ 
/*      */                     
/* 2195 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2196 */                     for (int x = 0; x < rowDataTrack.length; x++) {
/* 2197 */                       for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2198 */                         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */                     }  try {
/* 2200 */                       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2201 */                           SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2202 */                     } catch (IOException e) {
/*      */                       
/* 2204 */                       e.printStackTrace();
/*      */                     } 
/* 2206 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */                   } 
/*      */                   
/* 2209 */                   if (!SPTBatch_.checkboxSubBg.isSelected()) {
/* 2210 */                     TablePanel<Integer> trackTable = SPTBatch_.access$124(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 2211 */                     ComputeMSD values = new ComputeMSD(Integer.valueOf(SPTBatch_.pointsMSD.getText()).intValue());
/* 2212 */                     values.Compute(SPTBatch_.nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 2213 */                     JTable trackJTable = trackTable.getTable();
/* 2214 */                     SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2215 */                         "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2216 */                         "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2217 */                         "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2218 */                         "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2219 */                         "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2220 */                         "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2221 */                         "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", 
/* 2222 */                         "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", 
/* 2223 */                         "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", 
/* 2224 */                         "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 2225 */                         "Movement", "sMSS", "sMSS Movement" };
/* 2226 */                     String[][] rowDataTrack = new String[trackJTable.getModel()
/* 2227 */                         .getRowCount()][SPTBatch_.columnNamesTrack.length];
/*      */                     int j;
/* 2229 */                     for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2230 */                       for (int c = 0; c < trackJTable.getModel().getColumnCount(); c++)
/* 2231 */                         rowDataTrack[j][c] = 
/* 2232 */                           String.valueOf(trackJTable.getModel().getValueAt(j, c)); 
/* 2233 */                     }  for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2234 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2235 */                         String.valueOf(((Double)ComputeMSD.msd1Values.get(j)).toString());
/* 2236 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2237 */                         String.valueOf(((Double)ComputeMSD.msd2Values.get(j)).toString());
/* 2238 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2239 */                         String.valueOf(((Double)ComputeMSD.msd3Values.get(j)).toString());
/* 2240 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2241 */                         String.valueOf(((Double)ComputeMSD.msdValues.get(j)).toString());
/* 2242 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2243 */                         String.valueOf(((Double)ComputeMSD.diffValues.get(j)).toString());
/* 2244 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2245 */                         String.valueOf(((Double)ComputeMSD.d1NValues.get(j)).toString());
/* 2246 */                       if (Double.valueOf(trackJTable.getModel().getValueAt(j, 3)
/* 2247 */                           .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2248 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Long");
/*      */                       } else {
/* 2250 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Short");
/*      */                       } 
/*      */                       
/* 2253 */                       if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(j)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2254 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2255 */                           String.valueOf("Immobile");
/*      */                       }
/* 2257 */                       if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(j)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2258 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Mobile"); 
/* 2259 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2260 */                         String.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString());
/* 2261 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.6D && 
/* 2262 */                         Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.0D)
/* 2263 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2264 */                           String.valueOf("Confined"); 
/* 2265 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.9D && 
/* 2266 */                         Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.6D)
/* 2267 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2268 */                           String.valueOf("Anomalous"); 
/* 2269 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 1.1D && 
/* 2270 */                         Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.9D)
/* 2271 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Free"); 
/* 2272 */                       if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 1.1D) {
/* 2273 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2274 */                           String.valueOf("Directed");
/*      */                       }
/*      */ 
/*      */                       
/* 2278 */                       rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2279 */                         String.valueOf(ComputeMSD.mssValues.get(j));
/* 2280 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 1.0D)
/* 2281 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 
/* 2282 */                             1] = "Unidirectional Ballistic"; 
/* 2283 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 0.0D)
/* 2284 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2285 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() <= 0.55D)
/* 2286 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2287 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 0.45D)
/* 2288 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2289 */                       if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 1.0D) {
/* 2290 */                         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
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
/* 2301 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2302 */                     for (int x = 0; x < rowDataTrack.length; x++) {
/* 2303 */                       for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2304 */                         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */                     }  try {
/* 2306 */                       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2307 */                           SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2308 */                     } catch (IOException e) {
/*      */                       
/* 2310 */                       e.printStackTrace();
/*      */                     } 
/* 2312 */                     (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */                   } 
/*      */ 
/*      */                   
/* 2316 */                   String[][] rowData = new String[4][(SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements.length];
/* 2317 */                   int totalTracks = 0;
/* 2318 */                   int longTracks = 0;
/* 2319 */                   int longConfined = 0;
/* 2320 */                   int longUniBal = 0;
/* 2321 */                   int longFree = 0;
/* 2322 */                   int longDirect = 0;
/* 2323 */                   int immob = 0;
/* 2324 */                   int shortTracks = 0;
/* 2325 */                   int shortConfined = 0;
/* 2326 */                   int shortAnom = 0;
/* 2327 */                   int shortFree = 0;
/* 2328 */                   int shortDirect = 0;
/*      */                   
/* 2330 */                   for (int r = 0; r < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size(); r++) {
/* 2331 */                     if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2332 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2333 */                           r) != null)
/* 2334 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2335 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2336 */                           .equals("Long"))
/* 2337 */                           longTracks++;  
/* 2338 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2339 */                           r) != null)
/* 2340 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2341 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2342 */                           .equals("Short"))
/* 2343 */                           shortTracks++;  
/* 2344 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2345 */                           r) != null)
/* 2346 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2347 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2348 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2349 */                             .equals("Confined") && 
/* 2350 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2351 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2352 */                             .equals("Long"))
/* 2353 */                             longConfined++;   
/* 2354 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2355 */                           r) != null)
/* 2356 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2357 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2358 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2359 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2360 */                             .equals("Confined") && 
/* 2361 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2362 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2363 */                             .equals("Short")) {
/* 2364 */                             shortConfined++;
/*      */                           }  
/* 2366 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2367 */                           r) != null)
/* 2368 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2369 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2370 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2371 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2372 */                             .equals("Unidirectional Ballistic") && 
/* 2373 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2374 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2375 */                             .equals("Long"))
/* 2376 */                             longUniBal++;   
/* 2377 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2378 */                           r) != null)
/* 2379 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2380 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2381 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2382 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2383 */                             .equals("Anomalous") && 
/* 2384 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2385 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2386 */                             .equals("Short"))
/* 2387 */                             shortAnom++;   
/* 2388 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2389 */                           r) != null)
/* 2390 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2391 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2392 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2393 */                             .equals("Free") && 
/* 2394 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2395 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2396 */                             .equals("Long"))
/* 2397 */                             longFree++;   
/* 2398 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2399 */                           r) != null)
/* 2400 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2401 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2402 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2403 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2404 */                             .equals("Free") && 
/* 2405 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2406 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2407 */                             .equals("Short"))
/* 2408 */                             shortFree++;   
/* 2409 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2410 */                           r) != null)
/* 2411 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2412 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2413 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2414 */                             .equals("Directed") && 
/* 2415 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2416 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2417 */                             .equals("Long"))
/* 2418 */                             longDirect++;   
/* 2419 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2420 */                           r) != null)
/* 2421 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2422 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2423 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2424 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2425 */                             .equals("Directed") && 
/* 2426 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2427 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2428 */                             .equals("Short"))
/* 2429 */                             shortDirect++;   
/* 2430 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, 
/* 2431 */                           r) != null)
/* 2432 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2433 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, r)
/* 2434 */                           .equals("Immobile"))
/* 2435 */                           immob++;  
/*      */                     } 
/* 2437 */                     if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2438 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2439 */                           r) != null)
/* 2440 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2441 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2442 */                           .equals("Long"))
/* 2443 */                           longTracks++;  
/* 2444 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2445 */                           r) != null)
/* 2446 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2447 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2448 */                           .equals("Short"))
/* 2449 */                           shortTracks++;  
/* 2450 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2451 */                           r) != null)
/* 2452 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2453 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2454 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2455 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2456 */                             .equals("Confined") && 
/* 2457 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2458 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2459 */                             .equals("Long"))
/* 2460 */                             longConfined++;   
/* 2461 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2462 */                           r) != null)
/* 2463 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2464 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2465 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2466 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2467 */                             .equals("Confined") && 
/* 2468 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2469 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2470 */                             .equals("Short")) {
/* 2471 */                             shortConfined++;
/*      */                           }  
/* 2473 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2474 */                           r) != null)
/* 2475 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2476 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2477 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2478 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2479 */                             .equals("Unidirectional Ballistic") && 
/* 2480 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2481 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2482 */                             .equals("Long"))
/* 2483 */                             longUniBal++;   
/* 2484 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2485 */                           r) != null)
/* 2486 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2487 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2488 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2489 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2490 */                             .equals("Anomalous") && 
/* 2491 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2492 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2493 */                             .equals("Short"))
/* 2494 */                             shortAnom++;   
/* 2495 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2496 */                           r) != null)
/* 2497 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2498 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2499 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2500 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2501 */                             .equals("Free") && 
/* 2502 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2503 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2504 */                             .equals("Long"))
/* 2505 */                             longFree++;   
/* 2506 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2507 */                           r) != null)
/* 2508 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2509 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2510 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2511 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2512 */                             .equals("Free") && 
/* 2513 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2514 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2515 */                             .equals("Short"))
/* 2516 */                             shortFree++;   
/* 2517 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2518 */                           r) != null)
/* 2519 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2520 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2521 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2522 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2523 */                             .equals("Directed") && 
/* 2524 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2525 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2526 */                             .equals("Long"))
/* 2527 */                             longDirect++;   
/* 2528 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2529 */                           r) != null)
/* 2530 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2531 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2532 */                           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2533 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2534 */                             .equals("Directed") && 
/* 2535 */                             (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2536 */                             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2537 */                             .equals("Short"))
/* 2538 */                             shortDirect++;   
/* 2539 */                       if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2540 */                           r) != null) {
/* 2541 */                         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2542 */                           .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2543 */                           .equals("Immobile")) {
/* 2544 */                           immob++;
/*      */                         }
/*      */                       }
/*      */                     } 
/*      */                   } 
/* 2549 */                   SPTBatch_.access$130(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$129(SPTBatch_.null.access$8(SPTBatch_.null.this)) + (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2550 */                   SPTBatch_.access$132(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$131(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longTracks);
/* 2551 */                   SPTBatch_.access$134(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$133(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longConfined);
/* 2552 */                   SPTBatch_.access$136(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$135(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longUniBal);
/* 2553 */                   SPTBatch_.access$138(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$137(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longFree);
/* 2554 */                   SPTBatch_.access$140(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$139(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longDirect);
/* 2555 */                   SPTBatch_.access$142(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$141(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortTracks);
/* 2556 */                   SPTBatch_.access$144(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$143(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortConfined);
/* 2557 */                   SPTBatch_.access$146(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$145(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortAnom);
/* 2558 */                   SPTBatch_.access$148(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$147(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortFree);
/* 2559 */                   SPTBatch_.access$150(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$149(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortDirect);
/* 2560 */                   SPTBatch_.access$152(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$151(SPTBatch_.null.access$8(SPTBatch_.null.this)) + immob);
/*      */                   
/* 2562 */                   rowData[0][0] = String.valueOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2563 */                   rowData[1][0] = String.valueOf("");
/* 2564 */                   rowData[2][0] = String.valueOf("");
/* 2565 */                   rowData[3][0] = String.valueOf("");
/* 2566 */                   rowData[0][1] = String.valueOf(longTracks);
/* 2567 */                   rowData[1][1] = String.valueOf(" ");
/* 2568 */                   rowData[2][1] = String.valueOf("Short Tracks");
/* 2569 */                   rowData[3][1] = String.valueOf(shortTracks);
/* 2570 */                   rowData[0][2] = String.valueOf(longConfined);
/* 2571 */                   rowData[1][2] = String.valueOf(" ");
/* 2572 */                   rowData[2][2] = String.valueOf("Short Confined");
/* 2573 */                   rowData[3][2] = String.valueOf(shortConfined);
/* 2574 */                   rowData[0][3] = String.valueOf(longUniBal);
/* 2575 */                   rowData[1][3] = String.valueOf(" ");
/* 2576 */                   rowData[2][3] = String.valueOf("Short Anomalous");
/* 2577 */                   rowData[3][3] = String.valueOf(shortAnom);
/* 2578 */                   rowData[0][4] = String.valueOf(longFree);
/* 2579 */                   rowData[1][4] = String.valueOf(" ");
/* 2580 */                   rowData[2][4] = String.valueOf("Short Free");
/* 2581 */                   rowData[3][4] = String.valueOf(shortFree);
/* 2582 */                   rowData[0][5] = String.valueOf(longDirect);
/* 2583 */                   rowData[1][5] = String.valueOf(" ");
/* 2584 */                   rowData[2][5] = String.valueOf("Short Direct");
/* 2585 */                   rowData[3][5] = String.valueOf(shortDirect);
/* 2586 */                   rowData[0][6] = String.valueOf(immob);
/* 2587 */                   rowData[1][6] = String.valueOf("");
/* 2588 */                   rowData[2][6] = String.valueOf("");
/* 2589 */                   rowData[3][6] = String.valueOf("");
/*      */                   
/* 2591 */                   ResultsTable rtTrackSum = new ResultsTable();
/* 2592 */                   for (int i = 0; i < rowData.length; i++) {
/* 2593 */                     for (int c = 0; c < (rowData[i]).length; c++)
/* 2594 */                       rtTrackSum.setValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements[c], i, rowData[i][c]); 
/*      */                   } 
/*      */                   try {
/* 2597 */                     rtTrackSum.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2598 */                         SPTBatch_.imps.getShortTitle() + "_" + "SummaryTracks" + ".csv");
/* 2599 */                   } catch (IOException e) {
/*      */                     
/* 2601 */                     e.printStackTrace();
/*      */                   } 
/*      */ 
/*      */                   
/* 2605 */                   if (SPTBatch_.checkPBS.isSelected())
/* 2606 */                     (new PhotobleachingSpotPlot()).Compute(); 
/*      */                 }
/*      */               });
/* 2609 */           tracksThread.start();
/*      */         } 
/* 2611 */         if (SPTBatch_.access$153(SPTBatch_.this).equals("branchTable")) {
/* 2612 */           TablePanel<SPTBatch_.Branch> branchTable = SPTBatch_.createBranchTable(SPTBatch_.model, SPTBatch_.selectionModel);
/*      */           
/*      */           try {
/* 2615 */             branchTable.exportToCsv(new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2616 */                   SPTBatch_.imps.getShortTitle() + "_" + "Branch analysis" + ".csv"));
/* 2617 */           } catch (IOException e) {
/*      */             
/* 2619 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2625 */       if (SPTBatch_.checkboxSP.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2626 */         SPTBatch_.directChemo = new File(String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "Chemotaxis_Analysis");
/*      */         
/* 2628 */         if (!SPTBatch_.directChemo.exists()) {
/* 2629 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directChemo.getName());
/* 2630 */           boolean result = false;
/*      */           
/*      */           try {
/* 2633 */             SPTBatch_.directChemo.mkdir();
/* 2634 */             result = true;
/* 2635 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/* 2638 */           if (result) {
/* 2639 */             SPTBatch_.taskOutput.append("DIR created");
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2644 */         TablePanel<Spot> spotTable = SPTBatch_.access$123(SPTBatch_.this, SPTBatch_.model, SPTBatch_.this.ds);
/* 2645 */         JTable spotJTable = spotTable.getTable();
/* 2646 */         ArrayList<Float> importedData = new ArrayList<>();
/*      */         
/* 2648 */         Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
/* 2649 */         Set<Spot> track = null;
/* 2650 */         for (Integer id : trackIDs) {
/*      */           
/* 2652 */           track = SPTBatch_.model.getTrackModel().trackSpots(id);
/* 2653 */           ArrayList<Float> framesByTrack = new ArrayList<>();
/* 2654 */           ArrayList<Float> xByTrack = new ArrayList<>();
/* 2655 */           ArrayList<Float> yByTrack = new ArrayList<>();
/* 2656 */           ArrayList<Float> framesByTrackSort = new ArrayList<>();
/* 2657 */           ArrayList<Float> xByTrackSort = new ArrayList<>();
/* 2658 */           ArrayList<Float> yByTrackSort = new ArrayList<>();
/* 2659 */           ArrayList<Float> trackID = new ArrayList<>();
/* 2660 */           ArrayList<Integer> indexes = new ArrayList<>();
/* 2661 */           for (Spot spot : track) {
/* 2662 */             trackID.add(Float.valueOf(Float.valueOf(id.intValue()).floatValue() + Float.valueOf("1.0").floatValue()));
/* 2663 */             framesByTrack.add(Float.valueOf(spot.getFeature("FRAME").toString()));
/* 2664 */             xByTrack.add(Float.valueOf(spot.getFeature("POSITION_X").toString()));
/* 2665 */             yByTrack.add(Float.valueOf(spot.getFeature("POSITION_Y").toString()));
/* 2666 */             framesByTrackSort.add(Float.valueOf(spot.getFeature("FRAME").toString()));
/*      */           } 
/*      */           
/* 2669 */           Collections.sort(framesByTrackSort);
/* 2670 */           for (int z = 0; z < framesByTrackSort.size(); z++)
/* 2671 */             indexes.add(Integer.valueOf(framesByTrack.indexOf(framesByTrackSort.get(z)))); 
/* 2672 */           for (int i1 = 0; i1 < indexes.size(); i1++) {
/* 2673 */             importedData.add(trackID.get(i1));
/* 2674 */             importedData.add(Float.valueOf(((Float)framesByTrackSort.get(i1)).floatValue() + Float.valueOf("1.0").floatValue()));
/* 2675 */             importedData.add(xByTrack.get(((Integer)indexes.get(i1)).intValue()));
/* 2676 */             importedData.add(yByTrack.get(((Integer)indexes.get(i1)).intValue()));
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2682 */         Chemotaxis_ToolModified chemotool = new Chemotaxis_ToolModified(importedData);
/* 2683 */         chemotool.run("");
/*      */       } 
/*      */       
/* 2686 */       if (SPTBatch_.checkboxDiff.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2687 */         SPTBatch_.directDiff = new File(
/* 2688 */             String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "Trajectory_Classifier");
/*      */         
/* 2690 */         if (!SPTBatch_.directDiff.exists()) {
/* 2691 */           SPTBatch_.taskOutput.append("creating directory: " + SPTBatch_.directDiff.getName());
/* 2692 */           boolean result = false;
/*      */           
/*      */           try {
/* 2695 */             SPTBatch_.directDiff.mkdir();
/* 2696 */             result = true;
/* 2697 */           } catch (SecurityException securityException) {}
/*      */ 
/*      */           
/* 2700 */           if (result) {
/* 2701 */             SPTBatch_.taskOutput.append("DIR created");
/*      */           }
/*      */         } 
/*      */         
/* 2705 */         TraJClassifierTest_ tc = new TraJClassifierTest_();
/* 2706 */         tc.run("");
/*      */       } 
/*      */ 
/*      */       
/* 2710 */       if (SPTBatch_.checkboxMSD.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2711 */         TrackProcessorMSD_Modified msdPlot = new TrackProcessorMSD_Modified();
/* 2712 */         msdPlot.Compute(SPTBatch_.nOfTracks, SPTBatch_.this.rtSpots[SPTBatch_.i]);
/*      */       } 
/*      */       
/* 2715 */       if (SPTBatch_.checkCluster.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2716 */         ClusterSizeAnalysis clusterAnal = new ClusterSizeAnalysis();
/*      */         
/* 2718 */         DefaultCategoryDataset barDatasetCount = new DefaultCategoryDataset();
/* 2719 */         DefaultCategoryDataset barDatasetPercet = new DefaultCategoryDataset();
/* 2720 */         int counter1 = 0, counter2 = 0, counter3 = 0, counter4 = 0, counter5 = 0, counter6 = 0;
/* 2721 */         int counter7 = 0, counter8 = 0, counter9 = 0, counter10 = 0;
/* 2722 */         for (int r = 0; r < SPTBatch_.nOfTracks.size(); r++) {
/* 2723 */           int counter = 0;
/* 2724 */           List<Double> perTrack = new ArrayList<>();
/* 2725 */           for (int t = 0; t < SPTBatch_.tableSpot.getRowCount(); t++) {
/* 2726 */             if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t, 2).toString())
/* 2727 */               .equals(SPTBatch_.nOfTracks.get(r)) == Boolean.TRUE.booleanValue())
/*      */             {
/* 2729 */               perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel()
/* 2730 */                     .getValueAt(t, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/* 2735 */           clusterAnal.Compute(perTrack, SPTBatch_.nOfTracks.get(r));
/*      */ 
/*      */           
/* 2738 */           double[] values = new double[perTrack.size()];
/* 2739 */           for (int i1 = 0; i1 < perTrack.size(); i1++)
/* 2740 */             values[i1] = ((Double)perTrack.get(i1)).doubleValue(); 
/* 2741 */           GaussianMixtureModified gm2 = GaussianMixtureModified.fit(values);
/*      */           
/* 2743 */           if (gm2.components.length == 1) {
/* 2744 */             counter1++;
/*      */           }
/* 2746 */           if (gm2.components.length == 2) {
/* 2747 */             counter2++;
/*      */           }
/* 2749 */           if (gm2.components.length == 3) {
/* 2750 */             counter3++;
/*      */           }
/* 2752 */           if (gm2.components.length == 4) {
/* 2753 */             counter4++;
/*      */           }
/* 2755 */           if (gm2.components.length == 5) {
/* 2756 */             counter5++;
/*      */           }
/* 2758 */           if (gm2.components.length == 6) {
/* 2759 */             counter6++;
/*      */           }
/* 2761 */           if (gm2.components.length == 7) {
/* 2762 */             counter7++;
/*      */           }
/* 2764 */           if (gm2.components.length == 8) {
/* 2765 */             counter8++;
/*      */           }
/* 2767 */           if (gm2.components.length == 9) {
/* 2768 */             counter9++;
/*      */           }
/* 2770 */           if (gm2.components.length == 10) {
/* 2771 */             counter10++;
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 2776 */         barDatasetCount.setValue(counter1, "receptors/particle", "1");
/* 2777 */         barDatasetCount.setValue(counter2, "receptors/particle", "2");
/* 2778 */         barDatasetCount.setValue(counter3, "receptors/particle", "3");
/* 2779 */         barDatasetCount.setValue(counter4, "receptors/particle", "4");
/* 2780 */         barDatasetCount.setValue(counter5, "receptors/particle", "5");
/* 2781 */         barDatasetCount.setValue(counter6, "receptors/particle", "6");
/* 2782 */         barDatasetCount.setValue(counter7, "receptors/particle", "7");
/* 2783 */         barDatasetCount.setValue(counter8, "receptors/particle", "8");
/* 2784 */         barDatasetCount.setValue(counter9, "receptors/particle", "9");
/* 2785 */         barDatasetCount.setValue(counter10, "receptors/particle", "10");
/* 2786 */         JFreeChart chartCount = ChartFactory.createBarChart("Count of receptors/particle", 
/* 2787 */             "receptors/particle", "Count", (CategoryDataset)barDatasetCount, PlotOrientation.VERTICAL, false, true, 
/* 2788 */             false);
/*      */         
/* 2790 */         if (SPTBatch_.nOfTracks.size() != 0) {
/* 2791 */           barDatasetPercet.setValue((counter1 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "1");
/* 2792 */           barDatasetPercet.setValue((counter2 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "2");
/* 2793 */           barDatasetPercet.setValue((counter3 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "3");
/* 2794 */           barDatasetPercet.setValue((counter4 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "4");
/* 2795 */           barDatasetPercet.setValue((counter5 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "5");
/* 2796 */           barDatasetPercet.setValue((counter6 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "6");
/* 2797 */           barDatasetPercet.setValue((counter7 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "7");
/* 2798 */           barDatasetPercet.setValue((counter8 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "8");
/* 2799 */           barDatasetPercet.setValue((counter9 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "9");
/* 2800 */           barDatasetPercet.setValue((counter10 * 100 / SPTBatch_.nOfTracks.size()), "receptors/particle", "10");
/*      */         } 
/* 2802 */         if (SPTBatch_.nOfTracks.size() != 0) {
/*      */           
/* 2804 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "1");
/* 2805 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "2");
/* 2806 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "3");
/* 2807 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "4");
/* 2808 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "5");
/* 2809 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "6");
/* 2810 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "7");
/* 2811 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "8");
/* 2812 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "9");
/* 2813 */           barDatasetPercet.setValue(0.0D, "receptors/particle", "10");
/*      */         } 
/*      */         
/* 2816 */         JFreeChart chartPercet = ChartFactory.createBarChart("Percentage of receptors/particle", 
/* 2817 */             "receptors/particle", "Percentage-(%)", (CategoryDataset)barDatasetPercet, PlotOrientation.VERTICAL, 
/* 2818 */             false, true, false);
/* 2819 */         DecimalFormat pctFormat = new DecimalFormat("##.00%");
/* 2820 */         pctFormat.setMultiplier(1);
/* 2821 */         NumberFormat percent = NumberFormat.getPercentInstance();
/* 2822 */         percent.setMaximumFractionDigits(2);
/* 2823 */         StandardCategoryItemLabelGenerator standardCategoryItemLabelGenerator1 = new StandardCategoryItemLabelGenerator("{2}", 
/* 2824 */             NumberFormat.getInstance(), percent);
/* 2825 */         StandardCategoryItemLabelGenerator standardCategoryItemLabelGenerator2 = new StandardCategoryItemLabelGenerator("{2}", 
/* 2826 */             pctFormat);
/*      */         
/* 2828 */         CategoryPlot plotCount = chartCount.getCategoryPlot();
/* 2829 */         CategoryPlot plotPercent = chartPercet.getCategoryPlot();
/* 2830 */         CategoryItemRenderer rendererCount = plotCount.getRenderer();
/* 2831 */         CategoryItemRenderer rendererPercent = plotPercent.getRenderer();
/*      */         
/* 2833 */         NumberAxis rangeAxisCount = (NumberAxis)plotCount.getRangeAxis();
/* 2834 */         NumberAxis rangeAxisPercent = (NumberAxis)plotPercent.getRangeAxis();
/* 2835 */         rangeAxisPercent.setNumberFormatOverride(pctFormat);
/* 2836 */         rangeAxisCount.setAutoRangeIncludesZero(true);
/* 2837 */         rangeAxisPercent.setAutoRangeIncludesZero(true);
/* 2838 */         rendererCount.setDefaultItemLabelGenerator((CategoryItemLabelGenerator)standardCategoryItemLabelGenerator1);
/* 2839 */         rendererCount.setDefaultItemLabelFont(new Font("SansSerif", 0, 12));
/*      */         
/* 2841 */         rendererCount.setDefaultItemLabelsVisible(true);
/* 2842 */         rendererPercent.setDefaultItemLabelGenerator((CategoryItemLabelGenerator)standardCategoryItemLabelGenerator2);
/* 2843 */         rendererPercent.setDefaultItemLabelFont(new Font("SansSerif", 0, 12));
/*      */         
/* 2845 */         rendererPercent.setDefaultItemLabelsVisible(true);
/*      */         
/*      */         try {
/* 2848 */           ChartUtils.saveChartAsPNG(
/* 2849 */               new File(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + 
/* 2850 */                 SPTBatch_.imps.getShortTitle() + "_ReceptorsPerParticle_Count" + ".png"), 
/* 2851 */               chartCount, 500, 400);
/* 2852 */         } catch (IOException e) {
/*      */           
/* 2854 */           e.printStackTrace();
/*      */         } 
/*      */         
/*      */         try {
/* 2858 */           ChartUtils.saveChartAsPNG(
/* 2859 */               new File(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + 
/* 2860 */                 SPTBatch_.imps.getShortTitle() + 
/* 2861 */                 "_ReceptorsPerParticle_Percentage" + ".png"), 
/* 2862 */               chartPercet, 500, 400);
/* 2863 */         } catch (IOException e) {
/*      */           
/* 2865 */           e.printStackTrace();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2870 */       if (SPTBatch_.checkboxPlot.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */ 
/*      */ 
/*      */         
/* 2874 */         List<Spot> spots1 = new ArrayList<>(SPTBatch_.access$112(SPTBatch_.this).getModel().getSpots().getNSpots(true));
/* 2875 */         Set<String> ySelectedSpotSet = new HashSet<>();
/* 2876 */         ySelectedSpotSet.add(SPTBatch_.access$154(SPTBatch_.this));
/* 2877 */         for (Integer trackID : SPTBatch_.access$112(SPTBatch_.this).getModel().getTrackModel().trackIDs(true)) {
/* 2878 */           spots1.addAll(SPTBatch_.access$112(SPTBatch_.this).getModel().getTrackModel().trackSpots(trackID));
/*      */         }
/* 2880 */         if (SPTBatch_.ESP.isSelected() && SPTBatch_.access$155(SPTBatch_.this) != null && ySelectedSpotSet != null) {
/* 2881 */           JFreeChart chart = null;
/* 2882 */           XYPlot xYPlot = null;
/* 2883 */           Dimension xDimension = (Dimension)SPTBatch_.model.getFeatureModel()
/* 2884 */             .getSpotFeatureDimensions().get(SPTBatch_.access$155(SPTBatch_.this));
/* 2885 */           Map<String, Dimension> yDimensions = SPTBatch_.model.getFeatureModel()
/* 2886 */             .getSpotFeatureDimensions();
/* 2887 */           Map<String, String> featureNames = SPTBatch_.model.getFeatureModel().getSpotFeatureNames();
/*      */           
/* 2889 */           String str = String.valueOf(featureNames.get(SPTBatch_.access$155(SPTBatch_.this))) + " (" + 
/* 2890 */             TMUtils.getUnitsFor(xDimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + 
/* 2891 */             ")";
/*      */ 
/*      */           
/* 2894 */           Set<Dimension> set = SPTBatch_.access$156(SPTBatch_.this, ySelectedSpotSet, 
/* 2895 */               yDimensions);
/*      */ 
/*      */           
/* 2898 */           ArrayList<ExportableChartPanel> arrayList = new ArrayList<>(set.size());
/* 2899 */           for (Dimension dimension : set) {
/*      */ 
/*      */             
/* 2902 */             String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 2903 */                 SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */             
/* 2906 */             List<String> featuresThisDimension = SPTBatch_.access$157(dimension, ySelectedSpotSet, 
/* 2907 */                 yDimensions);
/*      */ 
/*      */             
/* 2910 */             String title = SPTBatch_.access$158(SPTBatch_.this, featuresThisDimension, featureNames, SPTBatch_.access$155(SPTBatch_.this));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2915 */             SpotCollectionDataset spotCollectionDataset = new SpotCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, 
/* 2916 */                 SPTBatch_.access$155(SPTBatch_.this), featuresThisDimension, spots1, true);
/*      */             
/* 2918 */             XYItemRenderer renderer = spotCollectionDataset.getRenderer();
/*      */ 
/*      */             
/* 2921 */             chart = ChartFactory.createXYLineChart(title, str, yAxisLabel, (XYDataset)spotCollectionDataset, 
/* 2922 */                 PlotOrientation.VERTICAL, true, true, false);
/* 2923 */             chart.getTitle().setFont(Fonts.FONT);
/* 2924 */             chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 2925 */             chart.setBackgroundPaint(new Color(220, 220, 220));
/* 2926 */             chart.setBorderVisible(false);
/* 2927 */             chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */             
/* 2930 */             xYPlot = chart.getXYPlot();
/* 2931 */             xYPlot.setRenderer(renderer);
/* 2932 */             xYPlot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 2933 */             xYPlot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 2934 */             xYPlot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 2935 */             xYPlot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 2936 */             xYPlot.setOutlineVisible(false);
/* 2937 */             xYPlot.setDomainCrosshairVisible(false);
/* 2938 */             xYPlot.setDomainGridlinesVisible(false);
/* 2939 */             xYPlot.setRangeCrosshairVisible(false);
/* 2940 */             xYPlot.setRangeGridlinesVisible(false);
/* 2941 */             xYPlot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */             
/* 2944 */             ((NumberAxis)xYPlot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */             
/* 2947 */             xYPlot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 2948 */             xYPlot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */           } 
/*      */ 
/*      */           
/*      */           try {
/* 2953 */             ChartUtils.saveChartAsPNG(
/* 2954 */                 new File(SPTBatch_.directSPT + File.separator + SPTBatch_.imps.getShortTitle() + "_" + 
/* 2955 */                   SPTBatch_.access$155(SPTBatch_.this) + "-" + SPTBatch_.access$154(SPTBatch_.this) + ".png"), 
/* 2956 */                 xYPlot.getChart(), 500, 270);
/* 2957 */           } catch (IOException e) {
/*      */             
/* 2959 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 2965 */         List<DefaultWeightedEdge> edges = new ArrayList<>();
/* 2966 */         Set<String> ySelectedLinkSet = new HashSet<>();
/* 2967 */         ySelectedLinkSet.add(SPTBatch_.access$159(SPTBatch_.this));
/* 2968 */         for (Integer trackID : SPTBatch_.access$112(SPTBatch_.this).getModel().getTrackModel().trackIDs(true)) {
/* 2969 */           edges.addAll(SPTBatch_.access$112(SPTBatch_.this).getModel().getTrackModel().trackEdges(trackID));
/*      */         }
/*      */         
/* 2972 */         if (SPTBatch_.ELP.isSelected() && SPTBatch_.access$160(SPTBatch_.this) != null && ySelectedLinkSet != null) {
/*      */           
/* 2974 */           JFreeChart chart = null;
/* 2975 */           XYPlot xYPlot = null;
/* 2976 */           Dimension xDimension = (Dimension)SPTBatch_.model.getFeatureModel()
/* 2977 */             .getEdgeFeatureDimensions().get(SPTBatch_.access$160(SPTBatch_.this));
/* 2978 */           Map<String, Dimension> yDimensions = SPTBatch_.model.getFeatureModel()
/* 2979 */             .getEdgeFeatureDimensions();
/* 2980 */           Map<String, String> featureNames = SPTBatch_.model.getFeatureModel().getEdgeFeatureNames();
/*      */           
/* 2982 */           String str = String.valueOf(featureNames.get(SPTBatch_.access$160(SPTBatch_.this))) + " (" + 
/* 2983 */             TMUtils.getUnitsFor(xDimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + 
/* 2984 */             ")";
/*      */ 
/*      */           
/* 2987 */           Set<Dimension> set = SPTBatch_.access$156(SPTBatch_.this, ySelectedLinkSet, 
/* 2988 */               yDimensions);
/*      */ 
/*      */           
/* 2991 */           ArrayList<ExportableChartPanel> arrayList = new ArrayList<>(set.size());
/* 2992 */           for (Dimension dimension : set) {
/*      */ 
/*      */             
/* 2995 */             String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 2996 */                 SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */             
/* 2999 */             List<String> featuresThisDimension = SPTBatch_.access$157(dimension, ySelectedLinkSet, 
/* 3000 */                 yDimensions);
/*      */ 
/*      */             
/* 3003 */             String title = SPTBatch_.access$158(SPTBatch_.this, featuresThisDimension, featureNames, SPTBatch_.access$160(SPTBatch_.this));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3008 */             EdgeCollectionDataset edgeCollectionDataset = new EdgeCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, 
/* 3009 */                 SPTBatch_.access$160(SPTBatch_.this), featuresThisDimension, edges, true);
/* 3010 */             XYItemRenderer renderer = edgeCollectionDataset.getRenderer();
/*      */ 
/*      */             
/* 3013 */             chart = ChartFactory.createXYLineChart(title, str, yAxisLabel, (XYDataset)edgeCollectionDataset, 
/* 3014 */                 PlotOrientation.VERTICAL, true, true, false);
/* 3015 */             chart.getTitle().setFont(Fonts.FONT);
/* 3016 */             chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 3017 */             chart.setBackgroundPaint(new Color(220, 220, 220));
/* 3018 */             chart.setBorderVisible(false);
/* 3019 */             chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */             
/* 3022 */             xYPlot = chart.getXYPlot();
/* 3023 */             xYPlot.setRenderer(renderer);
/* 3024 */             xYPlot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 3025 */             xYPlot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3026 */             xYPlot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 3027 */             xYPlot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3028 */             xYPlot.setOutlineVisible(false);
/* 3029 */             xYPlot.setDomainCrosshairVisible(false);
/* 3030 */             xYPlot.setDomainGridlinesVisible(false);
/* 3031 */             xYPlot.setRangeCrosshairVisible(false);
/* 3032 */             xYPlot.setRangeGridlinesVisible(false);
/* 3033 */             xYPlot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */             
/* 3036 */             ((NumberAxis)xYPlot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */             
/* 3039 */             xYPlot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 3040 */             xYPlot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */           } 
/*      */           
/*      */           try {
/* 3044 */             ChartUtils.saveChartAsPNG(
/* 3045 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 3046 */                   "_" + SPTBatch_.access$160(SPTBatch_.this) + "-" + SPTBatch_.access$159(SPTBatch_.this) + ".png"), 
/* 3047 */                 xYPlot.getChart(), 500, 270);
/* 3048 */           } catch (IOException e) {
/*      */             
/* 3050 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3056 */         Set<String> ySelectedTrackSet = new HashSet<>();
/* 3057 */         ySelectedTrackSet.add(SPTBatch_.access$161(SPTBatch_.this));
/*      */         
/* 3059 */         if (SPTBatch_.ETP.isSelected() && SPTBatch_.access$162(SPTBatch_.this) != null && ySelectedTrackSet != null) {
/* 3060 */           SPTBatch_.this.tracksID = new ArrayList<>(SPTBatch_.access$112(SPTBatch_.this).getModel().getTrackModel().unsortedTrackIDs(true));
/* 3061 */           JFreeChart chart = null;
/* 3062 */           XYPlot xYPlot = null;
/* 3063 */           Dimension xDimension = (Dimension)SPTBatch_.model.getFeatureModel()
/* 3064 */             .getTrackFeatureDimensions().get(SPTBatch_.access$162(SPTBatch_.this));
/* 3065 */           Map<String, Dimension> yDimensions = SPTBatch_.model.getFeatureModel()
/* 3066 */             .getTrackFeatureDimensions();
/* 3067 */           Map<String, String> featureNames = SPTBatch_.model.getFeatureModel().getTrackFeatureNames();
/*      */           
/* 3069 */           String str = String.valueOf(featureNames.get(SPTBatch_.access$162(SPTBatch_.this))) + " (" + 
/* 3070 */             TMUtils.getUnitsFor(xDimension, SPTBatch_.model.getSpaceUnits(), SPTBatch_.model.getTimeUnits()) + 
/* 3071 */             ")";
/*      */ 
/*      */           
/* 3074 */           Set<Dimension> set = SPTBatch_.access$156(SPTBatch_.this, ySelectedTrackSet, 
/* 3075 */               yDimensions);
/*      */ 
/*      */           
/* 3078 */           ArrayList<ExportableChartPanel> arrayList = new ArrayList<>(set.size());
/* 3079 */           for (Dimension dimension : set) {
/*      */ 
/*      */             
/* 3082 */             String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 3083 */                 SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */             
/* 3086 */             List<String> featuresThisDimension = SPTBatch_.access$157(dimension, ySelectedTrackSet, 
/* 3087 */                 yDimensions);
/*      */ 
/*      */             
/* 3090 */             String title = SPTBatch_.access$158(SPTBatch_.this, featuresThisDimension, featureNames, 
/* 3091 */                 SPTBatch_.access$162(SPTBatch_.this));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3096 */             TrackCollectionDataset trackCollectionDataset = new TrackCollectionDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, 
/* 3097 */                 SPTBatch_.access$162(SPTBatch_.this), featuresThisDimension, SPTBatch_.this.tracksID);
/* 3098 */             XYItemRenderer renderer = trackCollectionDataset.getRenderer();
/*      */ 
/*      */             
/* 3101 */             chart = ChartFactory.createXYLineChart(title, str, yAxisLabel, (XYDataset)trackCollectionDataset, 
/* 3102 */                 PlotOrientation.VERTICAL, true, true, false);
/* 3103 */             chart.getTitle().setFont(Fonts.FONT);
/* 3104 */             chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 3105 */             chart.setBackgroundPaint(new Color(220, 220, 220));
/* 3106 */             chart.setBorderVisible(false);
/* 3107 */             chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */             
/* 3110 */             xYPlot = chart.getXYPlot();
/* 3111 */             xYPlot.setRenderer(renderer);
/* 3112 */             xYPlot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 3113 */             xYPlot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3114 */             xYPlot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 3115 */             xYPlot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3116 */             xYPlot.setOutlineVisible(false);
/* 3117 */             xYPlot.setDomainCrosshairVisible(false);
/* 3118 */             xYPlot.setDomainGridlinesVisible(false);
/* 3119 */             xYPlot.setRangeCrosshairVisible(false);
/* 3120 */             xYPlot.setRangeGridlinesVisible(false);
/* 3121 */             xYPlot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */             
/* 3124 */             ((NumberAxis)xYPlot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */             
/* 3127 */             xYPlot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 3128 */             xYPlot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/* 3136 */             ChartUtils.saveChartAsPNG(
/* 3137 */                 new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 3138 */                   "_" + SPTBatch_.access$162(SPTBatch_.this) + "-" + SPTBatch_.access$161(SPTBatch_.this) + ".png"), 
/* 3139 */                 xYPlot.getChart(), 500, 270);
/* 3140 */           } catch (IOException e) {
/*      */             
/* 3142 */             e.printStackTrace();
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 3147 */         int maxFrame = spots.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();
/* 3148 */         int[] nSpots = new int[maxFrame + 1];
/* 3149 */         double[] time = new double[maxFrame + 1];
/* 3150 */         XYPlot plot = null;
/* 3151 */         for (int i1 = 0; i1 <= maxFrame; i1++) {
/* 3152 */           nSpots[i1] = spots.getNSpots(i1, true);
/* 3153 */           time[i1] = i1 * (SPTBatch_.access$96(SPTBatch_.this)).dt;
/*      */         } 
/* 3155 */         SPTBatch_.NSpotPerFrameDataset dataset = new SPTBatch_.NSpotPerFrameDataset(SPTBatch_.model, SPTBatch_.selectionModel, SPTBatch_.this.ds, time, 
/* 3156 */             nSpots);
/* 3157 */         String yFeature = "N spots";
/* 3158 */         Map<String, Dimension> dimMap = new HashMap<>(2);
/* 3159 */         dimMap.put("N spots", Dimension.NONE);
/* 3160 */         dimMap.put("POSITION_T", Dimension.TIME);
/* 3161 */         Map<String, String> nameMap = new HashMap<>(2);
/* 3162 */         nameMap.put("N spots", "N spots");
/* 3163 */         nameMap.put("POSITION_T", "T");
/*      */ 
/*      */         
/* 3166 */         String xAxisLabel = String.valueOf(nameMap.get("POSITION_T")) + " (" + 
/* 3167 */           TMUtils.getUnitsFor(Dimension.TIME, SPTBatch_.model.getSpaceUnits(), 
/* 3168 */             SPTBatch_.model.getTimeUnits()) + 
/* 3169 */           ")";
/*      */ 
/*      */         
/* 3172 */         Set<Dimension> dimensions = SPTBatch_.access$156(SPTBatch_.this, 
/* 3173 */             Collections.singletonList("N spots"), dimMap);
/*      */ 
/*      */         
/* 3176 */         ArrayList<ExportableChartPanel> chartPanels = new ArrayList<>(dimensions.size());
/* 3177 */         for (Dimension dimension : dimensions) {
/*      */ 
/*      */           
/* 3180 */           String yAxisLabel = TMUtils.getUnitsFor(dimension, SPTBatch_.model.getSpaceUnits(), 
/* 3181 */               SPTBatch_.model.getTimeUnits());
/*      */ 
/*      */           
/* 3184 */           List<String> featuresThisDimension = SPTBatch_.access$157(dimension, 
/* 3185 */               Collections.singletonList("N spots"), dimMap);
/*      */ 
/*      */           
/* 3188 */           String title = SPTBatch_.access$158(SPTBatch_.this, featuresThisDimension, nameMap, "POSITION_T");
/*      */ 
/*      */           
/* 3191 */           XYItemRenderer renderer = dataset.getRenderer();
/*      */ 
/*      */           
/* 3194 */           JFreeChart chart = ChartFactory.createXYLineChart(title, xAxisLabel, yAxisLabel, 
/* 3195 */               (XYDataset)dataset, PlotOrientation.VERTICAL, true, true, false);
/* 3196 */           chart.getTitle().setFont(Fonts.FONT);
/* 3197 */           chart.getLegend().setItemFont(Fonts.SMALL_FONT);
/* 3198 */           chart.setBackgroundPaint(new Color(220, 220, 220));
/* 3199 */           chart.setBorderVisible(false);
/* 3200 */           chart.getLegend().setBackgroundPaint(new Color(220, 220, 220));
/*      */ 
/*      */           
/* 3203 */           plot = chart.getXYPlot();
/* 3204 */           plot.setRenderer(renderer);
/* 3205 */           plot.getRangeAxis().setLabelFont(Fonts.FONT);
/* 3206 */           plot.getRangeAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3207 */           plot.getDomainAxis().setLabelFont(Fonts.FONT);
/* 3208 */           plot.getDomainAxis().setTickLabelFont(Fonts.SMALL_FONT);
/* 3209 */           plot.setOutlineVisible(false);
/* 3210 */           plot.setDomainCrosshairVisible(false);
/* 3211 */           plot.setDomainGridlinesVisible(false);
/* 3212 */           plot.setRangeCrosshairVisible(false);
/* 3213 */           plot.setRangeGridlinesVisible(false);
/* 3214 */           plot.setBackgroundAlpha(0.0F);
/*      */ 
/*      */           
/* 3217 */           ((NumberAxis)plot.getRangeAxis()).setAutoRangeIncludesZero(false);
/*      */ 
/*      */           
/* 3220 */           plot.getRangeAxis().setTickLabelInsets(new RectangleInsets(20.0D, 10.0D, 20.0D, 10.0D));
/* 3221 */           plot.getDomainAxis().setTickLabelInsets(new RectangleInsets(10.0D, 20.0D, 10.0D, 20.0D));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 3231 */           ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 3232 */                 SPTBatch_.imps.getShortTitle() + "_" + "Nspotsvs.Time" + ".png"), plot.getChart(), 500, 
/* 3233 */               270);
/* 3234 */         } catch (IOException e) {
/*      */           
/* 3236 */           e.printStackTrace();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 3241 */       if (SPTBatch_.checkbox4.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         
/* 3243 */         if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 3244 */           IJ.saveAs(SPTBatch_.access$116(SPTBatch_.this), "Tiff", String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i]);
/*      */         }
/* 3246 */         if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 3247 */           for (int r = 0; r < SPTBatch_.this.excludeTrack.size(); r++) {
/* 3248 */             int trackID = Integer.parseInt(SPTBatch_.trackJTable.getValueAt(r, 2).toString());
/* 3249 */             SPTBatch_.access$112(SPTBatch_.this).getModel().beginUpdate();
/*      */             try {
/* 3251 */               SPTBatch_.access$112(SPTBatch_.this).getModel().setTrackVisibility(Integer.valueOf(trackID), ((Boolean)SPTBatch_.this.excludeTrack.get(r)).booleanValue());
/*      */             } finally {
/* 3253 */               SPTBatch_.access$112(SPTBatch_.this).getModel().endUpdate();
/*      */             } 
/*      */             
/* 3256 */             SPTBatch_.access$114(SPTBatch_.this).render();
/* 3257 */             SPTBatch_.access$114(SPTBatch_.this).refresh();
/*      */           } 
/*      */           
/* 3260 */           if (SPTBatch_.imps.getNFrames() > 1) {
/* 3261 */             firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNFrames(), 1)));
/* 3262 */             lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNFrames(), Math.max(SPTBatch_.imps.getNFrames(), 1)));
/*      */           } 
/* 3264 */           if (SPTBatch_.imps.getNSlices() > 1) {
/* 3265 */             firstFrame = Integer.valueOf(Math.max(1, Math.min(SPTBatch_.imps.getNSlices(), 1)));
/* 3266 */             lastFrame = Integer.valueOf(Math.min(SPTBatch_.imps.getNSlices(), Math.max(SPTBatch_.imps.getNSlices(), 1)));
/*      */           } 
/*      */           
/* 3269 */           SPTBatch_.taskOutput.append("Capturing TrackMate overlay from frame " + firstFrame + " to " + 
/* 3270 */               lastFrame + ".\n");
/* 3271 */           bounds = SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().getBounds();
/* 3272 */           width = Integer.valueOf(bounds.width);
/* 3273 */           height = Integer.valueOf(bounds.height);
/* 3274 */           nCaptures = Integer.valueOf(lastFrame.intValue() - firstFrame.intValue() + 1);
/* 3275 */           stack = new ImageStack(width.intValue(), height.intValue());
/* 3276 */           channel = Integer.valueOf(SPTBatch_.access$114(SPTBatch_.this).getImp().getChannel());
/* 3277 */           slice = Integer.valueOf(SPTBatch_.access$114(SPTBatch_.this).getImp().getSlice());
/* 3278 */           SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(true);
/* 3279 */           for (int i1 = firstFrame.intValue(); i1 <= lastFrame.intValue(); i1++) {
/*      */             
/* 3281 */             SPTBatch_.access$114(SPTBatch_.this).getImp().setPositionWithoutUpdate(channel.intValue(), slice.intValue(), i1);
/* 3282 */             BufferedImage bi = new BufferedImage(width.intValue(), height.intValue(), 2);
/* 3283 */             SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().paint(bi.getGraphics());
/* 3284 */             ColorProcessor cp = new ColorProcessor(bi);
/* 3285 */             Integer index = Integer.valueOf(SPTBatch_.access$114(SPTBatch_.this).getImp().getStackIndex(channel.intValue(), slice.intValue(), i1));
/* 3286 */             stack.addSlice(SPTBatch_.access$114(SPTBatch_.this).getImp().getImageStack().getSliceLabel(index.intValue()), (ImageProcessor)cp);
/*      */           } 
/* 3288 */           SPTBatch_.access$114(SPTBatch_.this).getImp().getCanvas().hideZoomIndicator(false);
/* 3289 */           SPTBatch_.access$115(SPTBatch_.this, new ImagePlus("TrackMate capture of " + SPTBatch_.access$114(SPTBatch_.this).getImp().getShortTitle(), 
/* 3290 */                 stack));
/*      */           
/* 3292 */           SPTBatch_.access$117(SPTBatch_.access$114(SPTBatch_.this).getImp(), SPTBatch_.access$116(SPTBatch_.this));
/* 3293 */           IJ.saveAs(SPTBatch_.access$116(SPTBatch_.this), "Tiff", String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i]);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3301 */       if (SPTBatch_.checkbox3.isSelected() == Boolean.TRUE.booleanValue()) {
/*      */         try {
/* 3303 */           FileWriter writer = new FileWriter(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + "Log" + 
/* 3304 */               "_" + SPTBatch_.access$54(SPTBatch_.this)[SPTBatch_.i].replaceAll("\\.tif+$", "") + ".txt");
/* 3305 */           SPTBatch_.taskOutput.write(writer);
/* 3306 */           writer.close();
/* 3307 */         } catch (IOException iOException) {}
/*      */       }
/*      */       
/* 3310 */       SPTBatch_.imps.hide();
/*      */     } 
/* 3312 */     if (SPTBatch_.access$127(SPTBatch_.this).equals("trackTable")) {
/* 3313 */       Thread trackSummary = new Thread(new Runnable() {
/*      */             public void run() {
/* 3315 */               String[][] rowDataDef = new String[4][(SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements.length];
/* 3316 */               rowDataDef[0][0] = String.valueOf(SPTBatch_.access$129(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3317 */               rowDataDef[1][0] = String.valueOf("");
/* 3318 */               rowDataDef[2][0] = String.valueOf("");
/* 3319 */               rowDataDef[3][0] = String.valueOf("");
/* 3320 */               rowDataDef[0][1] = String.valueOf(SPTBatch_.access$131(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3321 */               rowDataDef[1][1] = String.valueOf(" ");
/* 3322 */               rowDataDef[2][1] = String.valueOf("Short Tracks");
/* 3323 */               rowDataDef[3][1] = String.valueOf(SPTBatch_.access$141(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3324 */               rowDataDef[0][2] = String.valueOf(SPTBatch_.access$133(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3325 */               rowDataDef[1][2] = String.valueOf(" ");
/* 3326 */               rowDataDef[2][2] = String.valueOf("Short Confined");
/* 3327 */               rowDataDef[3][2] = String.valueOf(SPTBatch_.access$143(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3328 */               rowDataDef[0][3] = String.valueOf(SPTBatch_.access$135(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3329 */               rowDataDef[1][3] = String.valueOf(" ");
/* 3330 */               rowDataDef[2][3] = String.valueOf("Short Anomalous");
/* 3331 */               rowDataDef[3][3] = String.valueOf(SPTBatch_.access$145(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3332 */               rowDataDef[0][4] = String.valueOf(SPTBatch_.access$137(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3333 */               rowDataDef[1][4] = String.valueOf(" ");
/* 3334 */               rowDataDef[2][4] = String.valueOf("Short Free");
/* 3335 */               rowDataDef[3][4] = String.valueOf(SPTBatch_.access$147(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3336 */               rowDataDef[0][5] = String.valueOf(SPTBatch_.access$139(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3337 */               rowDataDef[1][5] = String.valueOf(" ");
/* 3338 */               rowDataDef[2][5] = String.valueOf("Short Direct");
/* 3339 */               rowDataDef[3][5] = String.valueOf(SPTBatch_.access$149(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3340 */               rowDataDef[0][6] = String.valueOf(SPTBatch_.access$151(SPTBatch_.null.access$8(SPTBatch_.null.this)));
/* 3341 */               rowDataDef[1][6] = String.valueOf("");
/* 3342 */               rowDataDef[2][6] = String.valueOf("");
/* 3343 */               rowDataDef[3][6] = String.valueOf("");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 3355 */               ResultsTable rtTrackSummary = new ResultsTable();
/* 3356 */               for (int r = 0; r < rowDataDef.length; r++) {
/* 3357 */                 for (int c = 0; c < (rowDataDef[r]).length; c++)
/* 3358 */                   rtTrackSummary.setValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements[c], r, rowDataDef[r][c]); 
/*      */               } 
/*      */               try {
/* 3361 */                 rtTrackSummary.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3362 */                     "SummaryTracks_Condition" + ".csv");
/* 3363 */               } catch (IOException e) {
/*      */                 
/* 3365 */                 e.printStackTrace();
/*      */               } 
/*      */             }
/*      */           });
/* 3369 */       trackSummary.start();
/*      */     } 
/* 3371 */     if (SPTBatch_.checkSummary.isSelected()) {
/* 3372 */       Thread trackSummaryCols = new Thread(new Runnable() {
/*      */             public void run() {
/* 3374 */               for (int x = 0; x < (SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.size(); x++) {
/* 3375 */                 if (summaryColsWindow.combo.getSelectedIndex() == 0) {
/* 3376 */                   ResultsTable rtSpotSum = new ResultsTable();
/* 3377 */                   for (int y = 0; y < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots.length; y++) {
/* 3378 */                     for (int z = 0; z < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[y].size(); z++)
/* 3379 */                       rtSpotSum.setValue(listOfFiles[y].getName(), z, 
/* 3380 */                           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[y].getStringValue(
/* 3381 */                             Arrays.<String>asList(summaryColsWindow.columnNamesSpot)
/* 3382 */                             .indexOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x)), 
/* 3383 */                             z)); 
/*      */                   }  try {
/* 3385 */                     rtSpotSum.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3386 */                         (String)(SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x) + "_" + "SummaryCols_Spots" + ".csv");
/* 3387 */                   } catch (IOException e1) {
/*      */                     
/* 3389 */                     e1.printStackTrace();
/*      */                   } 
/*      */                 } 
/* 3392 */                 if (summaryColsWindow.combo.getSelectedIndex() == 1) {
/* 3393 */                   ResultsTable rtLinkSum = new ResultsTable();
/* 3394 */                   for (int y = 0; y < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtLinks.length; y++) {
/* 3395 */                     for (int z = 0; z < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtLinks[y].size(); z++)
/* 3396 */                       rtLinkSum.setValue(listOfFiles[y].getName(), z, 
/* 3397 */                           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtLinks[y].getStringValue(
/* 3398 */                             Arrays.<String>asList(summaryColsWindow.columnNamesLinks)
/* 3399 */                             .indexOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x)), 
/* 3400 */                             z)); 
/*      */                   }  try {
/* 3402 */                     rtLinkSum.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3403 */                         (String)(SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x) + "_" + "SummaryCols_Links" + ".csv");
/* 3404 */                   } catch (IOException e1) {
/*      */                     
/* 3406 */                     e1.printStackTrace();
/*      */                   } 
/*      */                 } 
/* 3409 */                 if (summaryColsWindow.combo.getSelectedIndex() == 2) {
/* 3410 */                   ResultsTable rtTrackSum = new ResultsTable();
/* 3411 */                   for (int y = 0; y < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks.length; y++) {
/* 3412 */                     for (int z = 0; z < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[y].size(); z++)
/* 3413 */                       rtTrackSum.setValue(listOfFiles[y].getName(), z, 
/* 3414 */                           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[y].getStringValue(
/* 3415 */                             Arrays.<String>asList(summaryColsWindow.columnNamesTracks)
/* 3416 */                             .indexOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x)), 
/* 3417 */                             z)); 
/*      */                   }  try {
/* 3419 */                     rtTrackSum.saveAs(String.valueOf(SPTBatch_.directSummary.getAbsolutePath()) + File.separator + 
/* 3420 */                         (String)(SPTBatch_.null.access$8(SPTBatch_.null.this)).selectedItems.get(x) + "_" + "SummaryCols_Tracks" + ".csv");
/* 3421 */                   } catch (IOException e1) {
/*      */                     
/* 3423 */                     e1.printStackTrace();
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           });
/*      */ 
/*      */       
/* 3431 */       trackSummaryCols.start();
/*      */     } 
/*      */     
/* 3434 */     SPTBatch_.access$122(SPTBatch_.this).equals("spotTable");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3445 */     SPTBatch_.access$125(SPTBatch_.this).equals("linkTable");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3456 */     SPTBatch_.access$127(SPTBatch_.this).equals("trackTable");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3469 */     SPTBatch_.taskOutput.append("              FINISHED!!!");
/* 3470 */     frameAnalyzer.setVisible(false);
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/SPTBatch_$1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */