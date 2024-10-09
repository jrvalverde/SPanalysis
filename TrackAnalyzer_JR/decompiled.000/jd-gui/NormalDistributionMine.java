/*     */ import ij.measure.ResultsTable;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartUtils;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
/*     */ import org.jfree.data.function.Function2D;
/*     */ import org.jfree.data.function.NormalDistributionFunction2D;
/*     */ import org.jfree.data.general.DatasetUtils;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import org.jfree.data.xy.XYSeriesCollection;
/*     */ import smileModified.MixtureModified;
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
/*     */ public class NormalDistributionMine
/*     */ {
/*     */   Integer nOfTrack;
/*     */   MixtureModified.Component[] components;
/*     */   int samples;
/*     */   double meanValue;
/*     */   
/*     */   public NormalDistributionMine(Integer nOfTrack, MixtureModified.Component[] components, int samples, double meanValue) {
/*  43 */     this.nOfTrack = nOfTrack;
/*  44 */     this.components = components;
/*  45 */     this.samples = samples;
/*  46 */     this.meanValue = meanValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void runNormalDistribution() {
/*  51 */     XYSeriesCollection dataset = new XYSeriesCollection();
/*  52 */     ResultsTable rtMonomer = new ResultsTable();
/*  53 */     String[] columnNames = { "Subpopulation", "μ", "σ", "Entropy", "N of Parameters", 
/*  54 */         "Variance", "N Receptor/Particle", "Monomer Intensity", "Track Mean Intensity" };
/*  55 */     double monomerValue = 0.0D;
/*  56 */     for (int i = 0; i < this.components.length; i++) {
/*  57 */       NormalDistributionFunction2D normalDistributionFunction2D = new NormalDistributionFunction2D((this.components[i]).distribution.mean(), 
/*  58 */           (this.components[i]).distribution.sd());
/*  59 */       XYSeries s1 = DatasetUtils.sampleFunction2DToSeries((Function2D)normalDistributionFunction2D, 
/*  60 */           (this.components[i]).distribution.mean() - 3.0D * (this.components[i]).distribution.sd(), 
/*  61 */           (this.components[i]).distribution.mean() + 3.0D * (this.components[i]).distribution.sd(), this.samples, 
/*  62 */           "subpop-" + i + " : " + "μ = " + String.format("%.3f", new Object[] { Double.valueOf((this.components[i]).distribution.mean()) }) + "," + 
/*  63 */           "σ = " + String.format("%.3f", new Object[] { Double.valueOf((this.components[i]).distribution.sd()) }));
/*  64 */       dataset.addSeries(s1);
/*  65 */       rtMonomer.setValue("Subpopulation", i, String.valueOf(i + 1));
/*  66 */       rtMonomer.setValue("μ", i, String.format("%.3f", new Object[] { Double.valueOf((this.components[i]).distribution.mean()) }));
/*  67 */       rtMonomer.setValue("σ", i, String.format("%.3f", new Object[] { Double.valueOf((this.components[i]).distribution.sd()) }));
/*  68 */       rtMonomer.setValue("Entropy", i, String.format("%.3f", new Object[] { Double.valueOf((this.components[i]).distribution.entropy()) }));
/*  69 */       rtMonomer.setValue("N of Parameters", i, (this.components[i]).distribution.length());
/*  70 */       rtMonomer.setValue("Variance", i, String.format("%.3f", new Object[] { Double.valueOf((this.components[i]).distribution.variance()) }));
/*     */       
/*  72 */       if (SPTBatch_.checkMonomer.isSelected() == Boolean.FALSE.booleanValue() && 
/*  73 */         this.components.length == 2) {
/*  74 */         monomerValue = Math.abs((this.components[0]).distribution.mean() - (this.components[1]).distribution.mean());
/*     */       }
/*     */     } 
/*     */     
/*  78 */     if (SPTBatch_.checkMonomer.isSelected() == Boolean.TRUE.booleanValue())
/*  79 */       rtMonomer.setValue("N Receptor/Particle", 0, 
/*  80 */           String.valueOf(this.meanValue / Double.valueOf(SPTBatch_.monomerField.getText()).doubleValue())); 
/*  81 */     if (SPTBatch_.checkMonomer.isSelected() == Boolean.FALSE.booleanValue()) {
/*  82 */       if (monomerValue == 0.0D) {
/*  83 */         rtMonomer.setValue("N Receptor/Particle", 0, "1");
/*  84 */         monomerValue = (this.components[0]).distribution.mean();
/*     */       } else {
/*  86 */         rtMonomer.setValue("N Receptor/Particle", 0, String.valueOf(this.meanValue / monomerValue));
/*     */       } 
/*     */     }
/*  89 */     if (SPTBatch_.checkMonomer.isSelected() == Boolean.FALSE.booleanValue())
/*  90 */       rtMonomer.setValue("Monomer Intensity", 0, monomerValue); 
/*  91 */     if (SPTBatch_.checkMonomer.isSelected() == Boolean.TRUE.booleanValue())
/*  92 */       rtMonomer.setValue("Monomer Intensity", 0, SPTBatch_.monomerField.getText()); 
/*  93 */     rtMonomer.setValue("Track Mean Intensity", 0, String.valueOf(this.meanValue));
/*  94 */     JFreeChart chart = ChartFactory.createXYLineChart(
/*  95 */         "Distribution of Single Integrated Intensities for Track-" + this.nOfTrack.toString(), 
/*  96 */         "Integrated Intensity", "Probability Density [nmolecule-1]", (XYDataset)dataset, PlotOrientation.VERTICAL, true, 
/*  97 */         true, false);
/*  98 */     XYPlot plot = (XYPlot)chart.getPlot();
/*  99 */     plot.setDomainZeroBaselineVisible(true);
/* 100 */     plot.setRangeZeroBaselineVisible(true);
/* 101 */     plot.setDomainPannable(true);
/* 102 */     plot.setRangePannable(true);
/* 103 */     plot.setBackgroundPaint(Color.white);
/* 104 */     plot.setDomainGridlinesVisible(true);
/* 105 */     plot.setRangeGridlinesVisible(true);
/* 106 */     plot.setDomainMinorGridlinePaint(Color.GRAY);
/* 107 */     plot.setDomainGridlinePaint(Color.DARK_GRAY);
/* 108 */     plot.setRangeMinorGridlinePaint(Color.GRAY);
/* 109 */     plot.setRangeGridlinePaint(Color.DARK_GRAY);
/* 110 */     ValueAxis xAxis = plot.getDomainAxis();
/* 111 */     xAxis.setLowerMargin(0.0D);
/* 112 */     xAxis.setUpperMargin(0.0D);
/* 113 */     XYLineAndShapeRenderer r = (XYLineAndShapeRenderer)plot.getRenderer();
/* 114 */     r.setDrawSeriesLineAsPath(true);
/* 115 */     for (int j = 0; j < dataset.getSeriesCount(); j++) {
/* 116 */       r.setSeriesStroke(j, new BasicStroke(2.0F, 1, 1, 1.0F));
/*     */     }
/*     */     
/*     */     try {
/* 120 */       rtMonomer.saveAs(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + 
/* 121 */           "_Cluster_Analysis_" + this.nOfTrack.toString() + ".csv");
/* 122 */     } catch (IOException e) {
/*     */       
/* 124 */       e.printStackTrace();
/*     */     } 
/*     */     
/*     */     try {
/* 128 */       ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + 
/* 129 */             SPTBatch_.imps.getShortTitle() + "_DistributionDensityFunction_" + this.nOfTrack.toString() + ".png"), 
/* 130 */           chart, 500, 400);
/* 131 */     } catch (IOException e) {
/*     */       
/* 133 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/NormalDistributionMine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */