/*     */ import fiji.plugin.trackmate.Spot;
/*     */ import fiji.plugin.trackmate.gui.displaysettings.Colormap;
/*     */ import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
/*     */ import ij.measure.ResultsTable;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.ChartUtils;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import org.jfree.data.xy.XYSeriesCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrackProcessorMSD_Modified
/*     */ {
/*     */   JFreeChart chart;
/*  51 */   JCheckBox displayLegendCheckBox = new JCheckBox("Display legend.", false);
/*  52 */   JCheckBox displayGraphInSequenceCheckBox = new JCheckBox("Display graph on sequence.", false);
/*  53 */   JButton useRoiAsBoundsForChartButton = new JButton("place graph in ROI #1");
/*  54 */   JCheckBox forceAllSequenceGraphWidthCheckBox = new JCheckBox("Force graph width.", false);
/*  55 */   JCheckBox useRealScalesBox = new JCheckBox("use real scales", false);
/*  56 */   JPanel chartpanel = new JPanel();
/*  57 */   JTextField scaleTextField = new JTextField("1.0");
/*  58 */   JButton exportButton = new JButton("export to console");
/*  59 */   JButton exportExcelButton = new JButton("export to excel");
/*     */ 
/*     */   
/*     */   Rectangle2D chartRectangleInSequence;
/*     */ 
/*     */   
/*     */   JLabel outLabel;
/*     */ 
/*     */   
/*     */   public void Compute(List<Integer> nOfTracks, ResultsTable rtSpots) {
/*  69 */     XYSeriesCollection xyDataset = new XYSeriesCollection();
/*  70 */     XYSeries series = null;
/*  71 */     List<List<Double>> imported2SpotX = new ArrayList<>();
/*  72 */     List<List<Double>> imported2SpotY = new ArrayList<>();
/*  73 */     List<List<Double>> imported2SpotT = new ArrayList<>();
/*  74 */     List<Integer> trackName = new ArrayList<>();
/*     */     
/*  76 */     for (int id = 0; id < nOfTracks.size(); id++) {
/*  77 */       trackName.add(nOfTracks.get(id));
/*  78 */       List<Double> imported1SpotX = new ArrayList<>();
/*  79 */       List<Double> imported1SpotY = new ArrayList<>();
/*  80 */       List<Double> imported1SpotT = new ArrayList<>();
/*  81 */       for (int j = 0; j < rtSpots.size(); j++) {
/*  82 */         if (rtSpots.getStringValue(2, j).equals(String.valueOf(((Integer)nOfTracks.get(id)).intValue())) == Boolean.TRUE.booleanValue()) {
/*  83 */           imported1SpotX.add(Double.valueOf(rtSpots.getStringValue(4, j)));
/*  84 */           imported1SpotY.add(Double.valueOf(rtSpots.getStringValue(5, j)));
/*  85 */           imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(7, j)));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  90 */       imported2SpotX.add(imported1SpotX);
/*  91 */       imported2SpotY.add(imported1SpotY);
/*  92 */       imported2SpotT.add(imported1SpotT);
/*     */     } 
/*     */ 
/*     */     
/*  96 */     String TitleString = "";
/*  97 */     String TitleString2 = "";
/*  98 */     String TitleString3 = "";
/*     */     
/* 100 */     for (int i = 0; i < imported2SpotX.size(); i++) {
/* 101 */       series = new XYSeries("Track " + i);
/*     */       
/* 103 */       if (SPTBatch_.imps.getCalibration().getXUnit() != "pixel") {
/* 104 */         TitleString = "Mean Square Displacement";
/* 105 */         TitleString2 = "Delta (s)";
/* 106 */         TitleString3 = "MSD (" + SPTBatch_.imps.getCalibration().getXUnit() + "^2)";
/*     */       } 
/*     */       
/* 109 */       if (SPTBatch_.imps.getCalibration().getXUnit() == "pixel") {
/* 110 */         TitleString = "Mean Square Displacement";
/* 111 */         TitleString2 = "Delta (frame)";
/* 112 */         TitleString3 = "MSD (pixel^2)";
/*     */       } 
/*     */ 
/*     */       
/* 116 */       double frameInterval = ((Double)((List<Double>)imported2SpotT.get(i)).get(2)).doubleValue() - ((Double)((List<Double>)imported2SpotT.get(i)).get(1)).doubleValue();
/* 117 */       int nMSD = ((List)imported2SpotX.get(i)).size();
/* 118 */       int[] tau = new int[nMSD - 1];
/* 119 */       double[] msdArray = new double[tau.length];
/* 120 */       for (int z = 0; z < nMSD - 1 - 1 + 1; z++)
/* 121 */         tau[z] = 1 + z; 
/* 122 */       double msd = -1.0D;
/* 123 */       for (int dt = 0; dt < tau.length; dt++) {
/* 124 */         double N = 0.0D;
/* 125 */         msd = 0.0D;
/* 126 */         for (int j = tau[dt]; j < ((List)imported2SpotX.get(i)).size(); j++) {
/* 127 */           msd = msd + 
/* 128 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(j - tau[dt])).doubleValue() - (
/* 129 */               (Double)((List<Double>)imported2SpotX.get(i)).get(j)).doubleValue(), 2.0D) + 
/* 130 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(j - tau[dt])).doubleValue() - (
/* 131 */               (Double)((List<Double>)imported2SpotY.get(i)).get(j)).doubleValue(), 2.0D);
/* 132 */           N++;
/*     */         } 
/*     */         
/* 135 */         msd /= N;
/* 136 */         msdArray[dt] = msd;
/* 137 */         series.add(tau[dt] * frameInterval, msd);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 142 */       xyDataset.addSeries(series);
/*     */     } 
/*     */     
/* 145 */     this.chart = ChartFactory.createXYLineChart(TitleString, 
/* 146 */         TitleString2, 
/* 147 */         TitleString3, 
/* 148 */         (XYDataset)xyDataset, 
/* 149 */         PlotOrientation.VERTICAL, this.displayLegendCheckBox.isSelected(), 
/* 150 */         true, 
/* 151 */         false);
/*     */     
/* 153 */     XYPlot plot = (XYPlot)this.chart.getPlot();
/* 154 */     plot.setBackgroundPaint(Color.white);
/* 155 */     plot.setDomainGridlinesVisible(true);
/* 156 */     plot.setRangeGridlinesVisible(true);
/* 157 */     plot.setDomainMinorGridlinePaint(Color.GRAY);
/* 158 */     plot.setDomainGridlinePaint(Color.DARK_GRAY);
/* 159 */     plot.setRangeMinorGridlinePaint(Color.GRAY);
/* 160 */     plot.setRangeGridlinePaint(Color.DARK_GRAY);
/* 161 */     this.chartpanel.removeAll();
/*     */     
/* 163 */     if (this.chart != null) {
/*     */       
/* 165 */       XYItemRenderer renderer = ((XYPlot)this.chart.getPlot()).getRenderer();
/* 166 */       for (Integer integer : nOfTracks) {
/* 167 */         PerTrackFeatureColorGenerator tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, 
/* 168 */             "TRACK_INDEX", null, null, Colormap.Turbo, 0.0D, 1.0D);
/* 169 */         renderer.setSeriesPaint(integer.intValue(), tcg.colorOf(integer));
/*     */       } 
/*     */     } 
/* 172 */     this.chartpanel.add((Component)new ChartPanel(this.chart, 400, 300, 400, 300, 400, 300, false, false, true, true, true, true));
/*     */     
/* 174 */     this.chartpanel.updateUI();
/* 175 */     if (SPTBatch_.checkboxDiff.isSelected() == Boolean.TRUE.booleanValue()) {
/*     */       try {
/* 177 */         ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directDiff.getAbsolutePath()) + File.separator + 
/* 178 */               "MSD_curve_" + SPTBatch_.imps.getShortTitle() + ".png"), this.chart, 500, 400);
/* 179 */       } catch (IOException e) {
/*     */         
/* 181 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 184 */     if (SPTBatch_.checkboxDiff.isSelected() == Boolean.FALSE.booleanValue())
/*     */       try {
/* 186 */         ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + 
/* 187 */               "MSD_curve_" + SPTBatch_.imps.getShortTitle() + ".png"), this.chart, 500, 400);
/* 188 */       } catch (IOException e) {
/*     */         
/* 190 */         e.printStackTrace();
/*     */       }  
/*     */   }
/*     */   
/*     */   public TrackProcessorMSD_Modified() {
/* 195 */     this.chartRectangleInSequence = new Rectangle2D.Float(250.0F, 20.0F, 490.0F, 240.0F);
/*     */     
/* 197 */     this.outLabel = new JLabel();
/*     */     this.chartpanel.add((Component)new ChartPanel(this.chart, 500, 300, 500, 300, 500, 300, false, false, true, true, true, true));
/*     */   } private static double scaledSquaredDistance(Spot spotSource, Spot spotTarget, double sx, double sy, double sz) {
/* 200 */     return 
/* 201 */       Math.pow((spotSource.getFeature("POSITION_X").doubleValue() - 
/* 202 */         spotTarget.getFeature("POSITION_X").doubleValue()) * sx, 2.0D) + 
/* 203 */       Math.pow((spotSource.getFeature("POSITION_Y").doubleValue() - 
/* 204 */         spotTarget.getFeature("POSITION_Y").doubleValue()) * sy, 2.0D);
/*     */   }
/*     */   
/*     */   public void keyPressed(Point p, KeyEvent e) {}
/*     */   
/*     */   public void mouseClick(Point p, MouseEvent e) {}
/*     */   
/*     */   public void mouseDrag(Point p, MouseEvent e) {}
/*     */   
/*     */   public void mouseMove(Point p, MouseEvent e) {}
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/TrackProcessorMSD_Modified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */