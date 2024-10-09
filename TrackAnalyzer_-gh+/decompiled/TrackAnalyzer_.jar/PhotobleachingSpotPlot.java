import fiji.plugin.trackmate.gui.displaysettings.Colormap;
import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
import ij.ImagePlus;
import ij.gui.OvalRoi;
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
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PhotobleachingSpotPlot {
   JFreeChart chart;
   JPanel chartpanel = new JPanel();
   ResultsTable rt;
   Rectangle2D chartRectangleInSequence = new Float(250.0F, 20.0F, 490.0F, 240.0F);
   JLabel outLabel = new JLabel();

   public PhotobleachingSpotPlot() {
      this.chartpanel.add(new ChartPanel(this.chart, 500, 300, 500, 300, 500, 300, false, false, true, true, true, true));
   }

   public void Compute() {
      Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
      List<Integer> nOfTracks = new ArrayList();
      if (SPTBatch_.checkboxSubBg.isSelected()) {
         int r;
         for(r = 0; r < SPTBatch_.trackJTable.getRowCount(); ++r) {
            nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(r, 2).toString()));
         }

         for(r = 0; r < nOfTracks.size(); ++r) {
            XYSeriesCollection xyDataset = new XYSeriesCollection();
            XYSeries seriesTrack = null;
            XYSeries seriesBg = null;
            List<Double> perTrack = new ArrayList();
            List<Double> perFrame = new ArrayList();
            List<Double> perTrackDef = new ArrayList();
            List<Double> perXPositionDef = new ArrayList();
            List<Double> perYPositionDef = new ArrayList();
            List<Double> perFrameDef = new ArrayList();

            int i;
            for(i = 0; i < SPTBatch_.tableSpot.getRowCount(); ++i) {
               if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(i, 2).toString()).equals(nOfTracks.get(r)) == Boolean.TRUE) {
                  perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(i, 12).toString()));
                  perFrame.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(i, 8).toString()));
                  perTrackDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(i, 12).toString()));
                  perFrameDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(i, 8).toString()));
                  perXPositionDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(i, 4).toString()));
                  perYPositionDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(i, 5).toString()));
               }
            }

            this.rt = new ResultsTable();
            seriesTrack = new XYSeries("Spot Mean Raw Intensity Track ");
            seriesBg = new XYSeries("Bg Intensity");

            for(i = 0; i < perTrackDef.size(); ++i) {
               seriesTrack.add((Number)perFrameDef.get(i), (Number)perTrackDef.get(i));
               this.rt.setValue("Spot Mean Raw Intensity Track ", i, (Double)perTrackDef.get(i));
            }

            for(i = 0; i < SPTBatch_.imps.getStack().getSize(); ++i) {
               seriesBg.add((double)i, SPTBatch_.slicesIntensitySpot[i]);
               this.rt.setValue("Bg Intensity", i, Double.valueOf(SPTBatch_.slicesIntensitySpot[i]));
            }

            if (((Double)perFrameDef.get(perFrameDef.size() - 1)).intValue() != SPTBatch_.imps.getStack().getSize() - 1) {
               ImagePlus[] slices = SPTBatch_.stack2images(SPTBatch_.imps.duplicate());
               OvalRoi ovalRoi = new OvalRoi((Double)perXPositionDef.get(perXPositionDef.size() - 1), (Double)perXPositionDef.get(perXPositionDef.size() - 1), Double.valueOf(SPTBatch_.RADIUS) / SPTBatch_.imps.getCalibration().pixelWidth, Double.valueOf(SPTBatch_.RADIUS) / SPTBatch_.imps.getCalibration().pixelWidth);

               for(int i = ((Double)perFrameDef.get(perFrameDef.size() - 1)).intValue(); i < SPTBatch_.imps.getStack().getSize(); ++i) {
                  slices[i].setRoi(ovalRoi);
                  seriesTrack.add((double)i, slices[i].getStatistics().mean);
                  this.rt.setValue("Spot Mean Raw Intensity Track ", i, slices[i].getStatistics().mean);
               }
            }

            xyDataset.addSeries(seriesTrack);
            xyDataset.addSeries(seriesBg);
            this.chart = ChartFactory.createXYLineChart("PhotoBleaching step for Track " + nOfTracks.get(r), "Frame", "Intensity", xyDataset, PlotOrientation.VERTICAL, true, true, false);
            this.chartpanel.removeAll();
            if (this.chart != null) {
               XYItemRenderer renderer = ((XYPlot)this.chart.getPlot()).getRenderer();
               PerTrackFeatureColorGenerator tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, "TRACK_INDEX", (Color)null, (Color)null, Colormap.Turbo, 0.0D, 1.0D);
               XYPlot plot = this.chart.getXYPlot();
               plot.setDataset(0, xyDataset);
               plot.setRenderer(0, renderer);
               plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, tcg.colorOf((Integer)nOfTracks.get(r)));
            }

            XYPlot plot = (XYPlot)this.chart.getPlot();
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinesVisible(true);
            plot.setRangeGridlinesVisible(true);
            plot.setDomainMinorGridlinePaint(Color.GRAY);
            plot.setDomainGridlinePaint(Color.DARK_GRAY);
            plot.setRangeMinorGridlinePaint(Color.GRAY);
            plot.setRangeGridlinePaint(Color.DARK_GRAY);
            this.chartpanel.add(new ChartPanel(this.chart));
            this.chartpanel.updateUI();

            try {
               ChartUtils.saveChartAsPNG(new File(SPTBatch_.directPBS.getAbsolutePath() + File.separator + SPTBatch_.imgTitle + "_" + ((Integer)nOfTracks.get(r)).toString() + ".png"), this.chart, 2000, 400);
            } catch (IOException var17) {
               var17.printStackTrace();
            }

            try {
               this.rt.saveAs(SPTBatch_.directPBS.getAbsolutePath() + File.separator + SPTBatch_.imgTitle + "_" + ((Integer)SPTBatch_.nOfTracks.get(r)).toString() + ".csv");
            } catch (IOException var16) {
               var16.printStackTrace();
            }
         }
      }

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
