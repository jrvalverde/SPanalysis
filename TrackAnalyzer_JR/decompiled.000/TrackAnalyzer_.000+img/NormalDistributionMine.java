import ij.measure.ResultsTable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.NormalDistributionFunction2D;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import smileModified.MixtureModified;

public class NormalDistributionMine {
   Integer nOfTrack;
   MixtureModified.Component[] components;
   int samples;
   double meanValue;

   public NormalDistributionMine(Integer nOfTrack, MixtureModified.Component[] components, int samples, double meanValue) {
      this.nOfTrack = nOfTrack;
      this.components = components;
      this.samples = samples;
      this.meanValue = meanValue;
   }

   public void runNormalDistribution() {
      XYSeriesCollection dataset = new XYSeriesCollection();
      ResultsTable rtMonomer = new ResultsTable();
      String[] var10000 = new String[]{"Subpopulation", "μ", "σ", "Entropy", "N of Parameters", "Variance", "N Receptor/Particle", "Monomer Intensity", "Track Mean Intensity"};
      double monomerValue = 0.0D;

      for(int i = 0; i < this.components.length; ++i) {
         Function2D n1 = new NormalDistributionFunction2D(this.components[i].distribution.mean(), this.components[i].distribution.sd());
         XYSeries s1 = DatasetUtils.sampleFunction2DToSeries(n1, this.components[i].distribution.mean() - 3.0D * this.components[i].distribution.sd(), this.components[i].distribution.mean() + 3.0D * this.components[i].distribution.sd(), this.samples, "subpop-" + i + " : " + "μ = " + String.format("%.3f", this.components[i].distribution.mean()) + "," + "σ = " + String.format("%.3f", this.components[i].distribution.sd()));
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
         if (monomerValue == 0.0D) {
            rtMonomer.setValue("N Receptor/Particle", 0, "1");
            monomerValue = this.components[0].distribution.mean();
         } else {
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
      JFreeChart chart = ChartFactory.createXYLineChart("Distribution of Single Integrated Intensities for Track-" + this.nOfTrack.toString(), "Integrated Intensity", "Probability Density [nmolecule-1]", dataset, PlotOrientation.VERTICAL, true, true, false);
      XYPlot plot = (XYPlot)chart.getPlot();
      plot.setDomainZeroBaselineVisible(true);
      plot.setRangeZeroBaselineVisible(true);
      plot.setDomainPannable(true);
      plot.setRangePannable(true);
      plot.setBackgroundPaint(Color.white);
      plot.setDomainGridlinesVisible(true);
      plot.setRangeGridlinesVisible(true);
      plot.setDomainMinorGridlinePaint(Color.GRAY);
      plot.setDomainGridlinePaint(Color.DARK_GRAY);
      plot.setRangeMinorGridlinePaint(Color.GRAY);
      plot.setRangeGridlinePaint(Color.DARK_GRAY);
      ValueAxis xAxis = plot.getDomainAxis();
      xAxis.setLowerMargin(0.0D);
      xAxis.setUpperMargin(0.0D);
      XYLineAndShapeRenderer r = (XYLineAndShapeRenderer)plot.getRenderer();
      r.setDrawSeriesLineAsPath(true);

      for(int i = 0; i < dataset.getSeriesCount(); ++i) {
         r.setSeriesStroke(i, new BasicStroke(2.0F, 1, 1, 1.0F));
      }

      try {
         rtMonomer.saveAs(SPTBatch_.directCluster.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_Cluster_Analysis_" + this.nOfTrack.toString() + ".csv");
      } catch (IOException var12) {
         var12.printStackTrace();
      }

      try {
         ChartUtils.saveChartAsPNG(new File(SPTBatch_.directCluster.getAbsolutePath() + File.separator + SPTBatch_.imps.getShortTitle() + "_DistributionDensityFunction_" + this.nOfTrack.toString() + ".png"), chart, 500, 400);
      } catch (IOException var11) {
         var11.printStackTrace();
      }

   }
}
