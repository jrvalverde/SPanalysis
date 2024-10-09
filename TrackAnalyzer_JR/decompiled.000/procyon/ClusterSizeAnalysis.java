import smileModified.MixtureModified;
import smileModified.GaussianMixtureModified;
import edu.mines.jtk.dsp.Histogram;
import java.util.ArrayList;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public class ClusterSizeAnalysis
{
    List<Double> pdf;
    double[] values;
    int BINS;
    HistogramDataset dataset;
    XYPlot plot;
    ChartPanel panel;
    XYBarRenderer renderer;
    Integer nOfTrack;
    
    public ClusterSizeAnalysis() {
        this.pdf = new ArrayList<Double>();
    }
    
    public void Compute(final List<Double> xData, final Integer nOfTrack) {
        final double[] xDataArray = new double[xData.size()];
        final float[] xDataArrayFloat = new float[xData.size()];
        for (int i = 0; i < xData.size(); ++i) {
            xDataArray[i] = xData.get(i);
            xDataArrayFloat[i] = xData.get(i).floatValue();
        }
        final double meanValue = xData.stream().mapToDouble(a -> a).average().getAsDouble();
        final Histogram histogram = new Histogram(xDataArrayFloat);
        final int BINS = histogram.getBinCount();
        final long[] counts = histogram.getCounts();
        final float[] densities = histogram.getDensities();
        final GaussianMixtureModified gm2 = GaussianMixtureModified.fit(xDataArray);
        final int k = gm2.size();
        final MixtureModified.Component[] components = gm2.components;
        final NormalDistributionMine nd = new NormalDistributionMine(nOfTrack, components, xDataArray.length, meanValue);
        nd.runNormalDistribution();
    }
}
