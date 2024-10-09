import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.function.Function2D;
import org.jfree.chart.ChartUtils;
import java.io.IOException;
import java.io.File;
import java.awt.Stroke;
import java.awt.BasicStroke;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.awt.Paint;
import java.awt.Color;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.function.NormalDistributionFunction2D;
import ij.measure.ResultsTable;
import org.jfree.data.xy.XYSeriesCollection;
import smileModified.MixtureModified;

// 
// Decompiled by Procyon v0.5.36
// 

public class NormalDistributionMine
{
    Integer nOfTrack;
    MixtureModified.Component[] components;
    int samples;
    double meanValue;
    
    public NormalDistributionMine(final Integer nOfTrack, final MixtureModified.Component[] components, final int samples, final double meanValue) {
        this.nOfTrack = nOfTrack;
        this.components = components;
        this.samples = samples;
        this.meanValue = meanValue;
    }
    
    public void runNormalDistribution() {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        final ResultsTable rtMonomer = new ResultsTable();
        final String[] columnNames = { "Subpopulation", "μ", "σ", "Entropy", "N of Parameters", "Variance", "N Receptor/Particle", "Monomer Intensity", "Track Mean Intensity" };
        double monomerValue = 0.0;
        for (int i = 0; i < this.components.length; ++i) {
            final Function2D n1 = (Function2D)new NormalDistributionFunction2D(this.components[i].distribution.mean(), this.components[i].distribution.sd());
            final XYSeries s1 = DatasetUtils.sampleFunction2DToSeries(n1, this.components[i].distribution.mean() - 3.0 * this.components[i].distribution.sd(), this.components[i].distribution.mean() + 3.0 * this.components[i].distribution.sd(), this.samples, (Comparable)("subpop-" + i + " : " + "μ = " + String.format("%.3f", this.components[i].distribution.mean()) + "," + "σ = " + String.format("%.3f", this.components[i].distribution.sd())));
            dataset.addSeries(s1);
            rtMonomer.setValue("Subpopulation", i, String.valueOf(i + 1));
            rtMonomer.setValue("μ", i, String.format("%.3f", this.components[i].distribution.mean()));
            rtMonomer.setValue("σ", i, String.format("%.3f", this.components[i].distribution.sd()));
            rtMonomer.setValue("Entropy", i, String.format("%.3f", this.components[i].distribution.entropy()));
            rtMonomer.setValue("N of Parameters", i, (double)this.components[i].distribution.length());
            rtMonomer.setValue("Variance", i, String.format("%.3f", this.components[i].distribution.variance()));
            if (SPTBatch_.checkMonomer.isSelected() == Boolean.FALSE && this.components.length == 2) {
                monomerValue = Math.abs(this.components[0].distribution.mean() - this.components[1].distribution.mean());
            }
        }
        if (SPTBatch_.checkMonomer.isSelected() == Boolean.TRUE) {
            rtMonomer.setValue("N Receptor/Particle", 0, String.valueOf(this.meanValue / Double.valueOf(SPTBatch_.monomerField.getText())));
        }
        if (SPTBatch_.checkMonomer.isSelected() == Boolean.FALSE) {
            if (monomerValue == 0.0) {
                rtMonomer.setValue("N Receptor/Particle", 0, "1");
                monomerValue = this.components[0].distribution.mean();
            }
            else {
                rtMonomer.setValue("N Receptor/Particle", 0, String.valueOf(this.meanValue / monomerValue));
            }
        }
        if (SPTBatch_.checkMonomer.isSelected() == Boolean.FALSE) {
            rtMonomer.setValue("Monomer Intensity", 0, monomerValue);
        }
        if (SPTBatch_.checkMonomer.isSelected() == Boolean.TRUE) {
            rtMonomer.setValue("Monomer Intensity", 0, SPTBatch_.monomerField.getText());
        }
        rtMonomer.setValue("Track Mean Intensity", 0, String.valueOf(this.meanValue));
        final JFreeChart chart = ChartFactory.createXYLineChart("Distribution of Single Integrated Intensities for Track-" + this.nOfTrack.toString(), "Integrated Intensity", "Probability Density [nmolecule-1]", (XYDataset)dataset, PlotOrientation.VERTICAL, true, true, false);
        final XYPlot plot = (XYPlot)chart.getPlot();
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        plot.setBackgroundPaint((Paint)Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainMinorGridlinePaint((Paint)Color.GRAY);
        plot.setDomainGridlinePaint((Paint)Color.DARK_GRAY);
        plot.setRangeMinorGridlinePaint((Paint)Color.GRAY);
        plot.setRangeGridlinePaint((Paint)Color.DARK_GRAY);
        final ValueAxis xAxis = plot.getDomainAxis();
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        final XYLineAndShapeRenderer r = (XYLineAndShapeRenderer)plot.getRenderer();
        r.setDrawSeriesLineAsPath(true);
        for (int j = 0; j < dataset.getSeriesCount(); ++j) {
            r.setSeriesStroke(j, (Stroke)new BasicStroke(2.0f, 1, 1, 1.0f));
        }
        try {
            rtMonomer.saveAs(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + "_Cluster_Analysis_" + this.nOfTrack.toString() + ".csv");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directCluster.getAbsolutePath()) + File.separator + SPTBatch_.imps.getShortTitle() + "_DistributionDensityFunction_" + this.nOfTrack.toString() + ".png"), chart, 500, 400);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
