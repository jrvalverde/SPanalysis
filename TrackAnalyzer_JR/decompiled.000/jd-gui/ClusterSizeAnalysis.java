/*    */ import edu.mines.jtk.dsp.Histogram;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jfree.chart.ChartPanel;
/*    */ import org.jfree.chart.plot.XYPlot;
/*    */ import org.jfree.chart.renderer.xy.XYBarRenderer;
/*    */ import org.jfree.data.statistics.HistogramDataset;
/*    */ import smileModified.GaussianMixtureModified;
/*    */ import smileModified.MixtureModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClusterSizeAnalysis
/*    */ {
/* 34 */   List<Double> pdf = new ArrayList<>();
/*    */   
/*    */   double[] values;
/*    */   
/*    */   int BINS;
/*    */   
/*    */   HistogramDataset dataset;
/*    */   
/*    */   XYPlot plot;
/*    */   ChartPanel panel;
/*    */   XYBarRenderer renderer;
/*    */   Integer nOfTrack;
/*    */   
/*    */   public void Compute(List<Double> xData, Integer nOfTrack) {
/* 48 */     double[] xDataArray = new double[xData.size()];
/* 49 */     float[] xDataArrayFloat = new float[xData.size()];
/* 50 */     for (int i = 0; i < xData.size(); i++) {
/* 51 */       xDataArray[i] = ((Double)xData.get(i)).doubleValue();
/* 52 */       xDataArrayFloat[i] = ((Double)xData.get(i)).floatValue();
/*    */     } 
/* 54 */     double meanValue = Double.valueOf(xData.stream().mapToDouble(a -> a.doubleValue()).average().getAsDouble()).doubleValue();
/* 55 */     Histogram histogram = new Histogram(xDataArrayFloat);
/* 56 */     int BINS = histogram.getBinCount();
/* 57 */     long[] counts = histogram.getCounts();
/* 58 */     float[] densities = histogram.getDensities();
/*    */     
/* 60 */     GaussianMixtureModified gm2 = GaussianMixtureModified.fit(xDataArray);
/* 61 */     int k = gm2.size();
/* 62 */     MixtureModified.Component[] components = gm2.components;
/* 63 */     NormalDistributionMine nd = new NormalDistributionMine(nOfTrack, components, xDataArray.length, meanValue);
/* 64 */     nd.runNormalDistribution();
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/ClusterSizeAnalysis.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */