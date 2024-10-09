import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.gui.displaysettings.Colormap;
import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
import ij.measure.ResultsTable;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class TrackProcessorMSD_Modified {
   JFreeChart chart;
   JCheckBox displayLegendCheckBox = new JCheckBox("Display legend.", false);
   JCheckBox displayGraphInSequenceCheckBox = new JCheckBox("Display graph on sequence.", false);
   JButton useRoiAsBoundsForChartButton = new JButton("place graph in ROI #1");
   JCheckBox forceAllSequenceGraphWidthCheckBox = new JCheckBox("Force graph width.", false);
   JCheckBox useRealScalesBox = new JCheckBox("use real scales", false);
   JPanel chartpanel = new JPanel();
   JTextField scaleTextField = new JTextField("1.0");
   JButton exportButton = new JButton("export to console");
   JButton exportExcelButton = new JButton("export to excel");
   Rectangle2D chartRectangleInSequence = new Float(250.0F, 20.0F, 490.0F, 240.0F);
   JLabel outLabel = new JLabel();

   public TrackProcessorMSD_Modified() {
      this.chartpanel.add(new ChartPanel(this.chart, 500, 300, 500, 300, 500, 300, false, false, true, true, true, true));
   }

   public void Compute(List<Integer> nOfTracks, ResultsTable rtSpots) {
      XYSeriesCollection xyDataset = new XYSeriesCollection();
      XYSeries series = null;
      List<List<Double>> imported2SpotX = new ArrayList();
      List<List<Double>> imported2SpotY = new ArrayList();
      List<List<Double>> imported2SpotT = new ArrayList();
      List<Integer> trackName = new ArrayList();

      for(int id = 0; id < nOfTracks.size(); ++id) {
         trackName.add((Integer)nOfTracks.get(id));
         List<Double> imported1SpotX = new ArrayList();
         List<Double> imported1SpotY = new ArrayList();
         List<Double> imported1SpotT = new ArrayList();

         for(int i = 0; i < rtSpots.size(); ++i) {
            if (rtSpots.getStringValue(2, i).equals(String.valueOf((Integer)nOfTracks.get(id))) == Boolean.TRUE) {
               imported1SpotX.add(Double.valueOf(rtSpots.getStringValue(4, i)));
               imported1SpotY.add(Double.valueOf(rtSpots.getStringValue(5, i)));
               imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(7, i)));
            }
         }

         imported2SpotX.add(imported1SpotX);
         imported2SpotY.add(imported1SpotY);
         imported2SpotT.add(imported1SpotT);
      }

      String TitleString = "";
      String TitleString2 = "";
      String TitleString3 = "";

      for(int i = 0; i < imported2SpotX.size(); ++i) {
         series = new XYSeries("Track " + i);
         if (SPTBatch_.imps.getCalibration().getXUnit() != "pixel") {
            TitleString = "Mean Square Displacement";
            TitleString2 = "Delta (s)";
            TitleString3 = "MSD (" + SPTBatch_.imps.getCalibration().getXUnit() + "^2)";
         }

         if (SPTBatch_.imps.getCalibration().getXUnit() == "pixel") {
            TitleString = "Mean Square Displacement";
            TitleString2 = "Delta (frame)";
            TitleString3 = "MSD (pixel^2)";
         }

         double frameInterval = (Double)((List)imported2SpotT.get(i)).get(2) - (Double)((List)imported2SpotT.get(i)).get(1);
         int nMSD = ((List)imported2SpotX.get(i)).size();
         int[] tau = new int[nMSD - 1];
         double[] msdArray = new double[tau.length];

         for(int z = 0; z < nMSD - 1 - 1 + 1; ++z) {
            tau[z] = 1 + z;
         }

         double msd = -1.0D;

         for(int dt = 0; dt < tau.length; ++dt) {
            double N = 0.0D;
            msd = 0.0D;

            for(int j = tau[dt]; j < ((List)imported2SpotX.get(i)).size(); ++j) {
               msd = msd + Math.pow((Double)((List)imported2SpotX.get(i)).get(j - tau[dt]) - (Double)((List)imported2SpotX.get(i)).get(j), 2.0D) + Math.pow((Double)((List)imported2SpotY.get(i)).get(j - tau[dt]) - (Double)((List)imported2SpotY.get(i)).get(j), 2.0D);
               ++N;
            }

            msd /= N;
            msdArray[dt] = msd;
            series.add((double)tau[dt] * frameInterval, msd);
         }

         xyDataset.addSeries(series);
      }

      this.chart = ChartFactory.createXYLineChart(TitleString, TitleString2, TitleString3, xyDataset, PlotOrientation.VERTICAL, this.displayLegendCheckBox.isSelected(), true, false);
      XYPlot plot = (XYPlot)this.chart.getPlot();
      plot.setBackgroundPaint(Color.white);
      plot.setDomainGridlinesVisible(true);
      plot.setRangeGridlinesVisible(true);
      plot.setDomainMinorGridlinePaint(Color.GRAY);
      plot.setDomainGridlinePaint(Color.DARK_GRAY);
      plot.setRangeMinorGridlinePaint(Color.GRAY);
      plot.setRangeGridlinePaint(Color.DARK_GRAY);
      this.chartpanel.removeAll();
      if (this.chart != null) {
         XYItemRenderer renderer = ((XYPlot)this.chart.getPlot()).getRenderer();
         Iterator var33 = nOfTracks.iterator();

         while(var33.hasNext()) {
            Integer id = (Integer)var33.next();
            PerTrackFeatureColorGenerator tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, "TRACK_INDEX", (Color)null, (Color)null, Colormap.Turbo, 0.0D, 1.0D);
            renderer.setSeriesPaint(id, tcg.colorOf(id));
         }
      }

      this.chartpanel.add(new ChartPanel(this.chart, 400, 300, 400, 300, 400, 300, false, false, true, true, true, true));
      this.chartpanel.updateUI();
      if (SPTBatch_.checkboxDiff.isSelected() == Boolean.TRUE) {
         try {
            ChartUtils.saveChartAsPNG(new File(SPTBatch_.directDiff.getAbsolutePath() + File.separator + "MSD_curve_" + SPTBatch_.imps.getShortTitle() + ".png"), this.chart, 500, 400);
         } catch (IOException var25) {
            var25.printStackTrace();
         }
      }

      if (SPTBatch_.checkboxDiff.isSelected() == Boolean.FALSE) {
         try {
            ChartUtils.saveChartAsPNG(new File(SPTBatch_.directImages.getAbsolutePath() + File.separator + "MSD_curve_" + SPTBatch_.imps.getShortTitle() + ".png"), this.chart, 500, 400);
         } catch (IOException var24) {
            var24.printStackTrace();
         }
      }

   }

   private static double scaledSquaredDistance(Spot spotSource, Spot spotTarget, double sx, double sy, double sz) {
      return Math.pow((spotSource.getFeature("POSITION_X") - spotTarget.getFeature("POSITION_X")) * sx, 2.0D) + Math.pow((spotSource.getFeature("POSITION_Y") - spotTarget.getFeature("POSITION_Y")) * sy, 2.0D);
   }

   public void keyPressed(Point p, KeyEvent e) {
   }

   public void mouseClick(Point p, MouseEvent e) {
   }

   public void mouseDrag(Point p, MouseEvent e) {
   }

   public void mouseMove(Point p, MouseEvent e) {
   }
}
