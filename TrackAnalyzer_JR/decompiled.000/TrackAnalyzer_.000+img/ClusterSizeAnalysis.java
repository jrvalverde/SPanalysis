import edu.mines.jtk.dsp.Histogram;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import smileModified.GaussianMixtureModified;
import smileModified.MixtureModified;

public class ClusterSizeAnalysis {
   List<Double> pdf = new ArrayList();
   double[] values;
   int BINS;
   HistogramDataset dataset;
   XYPlot plot;
   ChartPanel panel;
   XYBarRenderer renderer;
   Integer nOfTrack;

   public void Compute(List<Double> xData, Integer nOfTrack) {
      double[] xDataArray = new double[xData.size()];
      float[] xDataArrayFloat = new float[xData.size()];

      for(int i = 0; i < xData.size(); ++i) {
         xDataArray[i] = (Double)xData.get(i);
         xDataArrayFloat[i] = ((Double)xData.get(i)).floatValue();
      }

      double meanValue = Double.valueOf(xData.stream().mapToDouble((a) -> {
         return a;
      }).average().getAsDouble());
      Histogram histogram = new Histogram(xDataArrayFloat);
      int BINS = histogram.getBinCount();
      long[] counts = histogram.getCounts();
      float[] densities = histogram.getDensities();
      GaussianMixtureModified gm2 = GaussianMixtureModified.fit(xDataArray);
      int k = gm2.size();
      MixtureModified.Component[] components = gm2.components;
      NormalDistributionMine nd = new NormalDistributionMine(nOfTrack, components, xDataArray.length, meanValue);
      nd.runNormalDistribution();
   }
}
