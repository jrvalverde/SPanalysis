/*     */ import fiji.plugin.trackmate.gui.displaysettings.Colormap;
/*     */ import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
/*     */ import ij.ImagePlus;
/*     */ import ij.gui.OvalRoi;
/*     */ import ij.gui.Roi;
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
/*     */ import java.util.Set;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PhotobleachingSpotPlot
/*     */ {
/*     */   JFreeChart chart;
/*  54 */   JPanel chartpanel = new JPanel();
/*     */ 
/*     */   
/*     */   ResultsTable rt;
/*     */ 
/*     */   
/*     */   Rectangle2D chartRectangleInSequence;
/*     */   
/*     */   JLabel outLabel;
/*     */ 
/*     */   
/*     */   public void Compute() {
/*  66 */     Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
/*  67 */     List<Integer> nOfTracks = new ArrayList<>();
/*  68 */     if (SPTBatch_.checkboxSubBg.isSelected()) {
/*     */       
/*  70 */       for (int t = 0; t < SPTBatch_.trackJTable.getRowCount(); t++)
/*  71 */         nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(t, 2).toString())); 
/*  72 */       for (int r = 0; r < nOfTracks.size(); r++) {
/*  73 */         XYSeriesCollection xyDataset = new XYSeriesCollection();
/*  74 */         XYSeries seriesTrack = null;
/*  75 */         XYSeries seriesBg = null;
/*  76 */         List<Double> perTrack = new ArrayList<>();
/*  77 */         List<Double> perFrame = new ArrayList<>();
/*  78 */         List<Double> perTrackDef = new ArrayList<>();
/*  79 */         List<Double> perXPositionDef = new ArrayList<>();
/*  80 */         List<Double> perYPositionDef = new ArrayList<>();
/*  81 */         List<Double> perFrameDef = new ArrayList<>();
/*     */         
/*  83 */         for (int j = 0; j < SPTBatch_.tableSpot.getRowCount(); j++) {
/*  84 */           if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 2).toString())
/*  85 */             .equals(nOfTracks.get(r)) == Boolean.TRUE.booleanValue()) {
/*  86 */             perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 12).toString()));
/*  87 */             perFrame.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 8).toString()));
/*     */             
/*  89 */             perTrackDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 12).toString()));
/*  90 */             perFrameDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 8).toString()));
/*  91 */             perXPositionDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 4).toString()));
/*  92 */             perYPositionDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(j, 5).toString()));
/*     */           } 
/*     */         } 
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
/* 105 */         this.rt = new ResultsTable();
/*     */ 
/*     */ 
/*     */         
/* 109 */         seriesTrack = new XYSeries("Spot Mean Raw Intensity Track ");
/* 110 */         seriesBg = new XYSeries("Bg Intensity");
/*     */ 
/*     */         
/*     */         int i;
/*     */ 
/*     */         
/* 116 */         for (i = 0; i < perTrackDef.size(); i++) {
/* 117 */           seriesTrack.add(perFrameDef.get(i), perTrackDef.get(i));
/* 118 */           this.rt.setValue("Spot Mean Raw Intensity Track ", i, ((Double)perTrackDef.get(i)).doubleValue());
/*     */         } 
/*     */         
/* 121 */         for (i = 0; i < SPTBatch_.imps.getStack().getSize(); i++) {
/* 122 */           seriesBg.add(i, Double.valueOf(SPTBatch_.slicesIntensitySpot[i]));
/* 123 */           this.rt.setValue("Bg Intensity", i, Double.valueOf(SPTBatch_.slicesIntensitySpot[i]).doubleValue());
/*     */         } 
/*     */         
/* 126 */         if (((Double)perFrameDef.get(perFrameDef.size() - 1)).intValue() != SPTBatch_.imps.getStack().getSize() - 1) {
/* 127 */           ImagePlus[] slices = SPTBatch_.stack2images(SPTBatch_.imps.duplicate());
/* 128 */           OvalRoi ovalRoi = new OvalRoi(((Double)perXPositionDef.get(perXPositionDef.size() - 1)).doubleValue(), (
/* 129 */               (Double)perXPositionDef.get(perXPositionDef.size() - 1)).doubleValue(), 
/* 130 */               Double.valueOf(SPTBatch_.RADIUS).doubleValue() / 
/* 131 */               (SPTBatch_.imps.getCalibration()).pixelWidth, 
/* 132 */               Double.valueOf(SPTBatch_.RADIUS).doubleValue() / 
/* 133 */               (SPTBatch_.imps.getCalibration()).pixelWidth);
/*     */           
/* 135 */           for (int k = ((Double)perFrameDef.get(perFrameDef.size() - 1)).intValue(); k < SPTBatch_.imps.getStack()
/* 136 */             .getSize(); k++) {
/* 137 */             slices[k].setRoi((Roi)ovalRoi);
/* 138 */             seriesTrack.add(k, (slices[k].getStatistics()).mean);
/* 139 */             this.rt.setValue("Spot Mean Raw Intensity Track ", k, (slices[k].getStatistics()).mean);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 144 */         xyDataset.addSeries(seriesTrack);
/* 145 */         xyDataset.addSeries(seriesBg);
/*     */         
/* 147 */         this.chart = ChartFactory.createXYLineChart("PhotoBleaching step for Track " + nOfTracks.get(r), 
/*     */             
/* 149 */             "Frame", 
/* 150 */             "Intensity", 
/* 151 */             (XYDataset)xyDataset, 
/* 152 */             PlotOrientation.VERTICAL, true, 
/* 153 */             true, 
/* 154 */             false);
/*     */ 
/*     */         
/* 157 */         this.chartpanel.removeAll();
/*     */         
/* 159 */         if (this.chart != null) {
/*     */           
/* 161 */           XYItemRenderer renderer = ((XYPlot)this.chart.getPlot()).getRenderer();
/*     */           
/* 163 */           PerTrackFeatureColorGenerator tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, 
/* 164 */               "TRACK_INDEX", null, null, Colormap.Turbo, 0.0D, 1.0D);
/* 165 */           XYPlot xYPlot = this.chart.getXYPlot();
/*     */ 
/*     */           
/* 168 */           xYPlot.setDataset(0, (XYDataset)xyDataset);
/* 169 */           xYPlot.setRenderer(0, renderer);
/* 170 */           xYPlot.getRendererForDataset(xYPlot.getDataset(0)).setSeriesPaint(0, tcg.colorOf(nOfTracks.get(r)));
/*     */         } 
/*     */         
/* 173 */         XYPlot plot = (XYPlot)this.chart.getPlot();
/* 174 */         plot.setBackgroundPaint(Color.white);
/* 175 */         plot.setDomainGridlinesVisible(true);
/* 176 */         plot.setRangeGridlinesVisible(true);
/* 177 */         plot.setDomainMinorGridlinePaint(Color.GRAY);
/* 178 */         plot.setDomainGridlinePaint(Color.DARK_GRAY);
/* 179 */         plot.setRangeMinorGridlinePaint(Color.GRAY);
/* 180 */         plot.setRangeGridlinePaint(Color.DARK_GRAY);
/* 181 */         this.chartpanel.add((Component)new ChartPanel(this.chart));
/*     */         
/* 183 */         this.chartpanel.updateUI();
/*     */         try {
/* 185 */           ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directPBS.getAbsolutePath()) + File.separator + 
/* 186 */                 SPTBatch_.imgTitle + "_" + ((Integer)nOfTracks.get(r)).toString() + ".png"), this.chart, 2000, 400);
/* 187 */         } catch (IOException e) {
/*     */           
/* 189 */           e.printStackTrace();
/*     */         } 
/*     */         try {
/* 192 */           this.rt.saveAs(String.valueOf(SPTBatch_.directPBS.getAbsolutePath()) + File.separator + SPTBatch_.imgTitle + "_" + (
/* 193 */               (Integer)SPTBatch_.nOfTracks.get(r)).toString() + ".csv");
/* 194 */         } catch (IOException e) {
/*     */           
/* 196 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PhotobleachingSpotPlot() {
/* 207 */     this.chartRectangleInSequence = new Rectangle2D.Float(250.0F, 20.0F, 490.0F, 240.0F);
/*     */     
/* 209 */     this.outLabel = new JLabel();
/*     */     this.chartpanel.add((Component)new ChartPanel(this.chart, 500, 300, 500, 300, 500, 300, false, false, true, true, true, true));
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


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/PhotobleachingSpotPlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */