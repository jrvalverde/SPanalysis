import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Point;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import ij.ImagePlus;
import java.util.List;
import java.util.Set;
import java.io.IOException;
import org.jfree.chart.ChartUtils;
import java.io.File;
import java.awt.Paint;
import java.awt.Color;
import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
import fiji.plugin.trackmate.gui.displaysettings.Colormap;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import ij.gui.Roi;
import ij.gui.OvalRoi;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;
import java.awt.Component;
import org.jfree.chart.ChartPanel;
import javax.swing.JLabel;
import java.awt.geom.Rectangle2D;
import ij.measure.ResultsTable;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;

// 
// Decompiled by Procyon v0.5.36
// 

public class PhotobleachingSpotPlot
{
    JFreeChart chart;
    JPanel chartpanel;
    ResultsTable rt;
    Rectangle2D chartRectangleInSequence;
    JLabel outLabel;
    
    public PhotobleachingSpotPlot() {
        this.chartpanel = new JPanel();
        this.chartRectangleInSequence = new Rectangle2D.Float(250.0f, 20.0f, 490.0f, 240.0f);
        this.outLabel = new JLabel();
        this.chartpanel.add((Component)new ChartPanel(this.chart, 500, 300, 500, 300, 500, 300, false, false, true, true, true, true));
    }
    
    public void Compute() {
        final Set<Integer> trackIDs = (Set<Integer>)SPTBatch_.model.getTrackModel().trackIDs(true);
        final List<Integer> nOfTracks = new ArrayList<Integer>();
        if (SPTBatch_.checkboxSubBg.isSelected()) {
            for (int t = 0; t < SPTBatch_.trackJTable.getRowCount(); ++t) {
                nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(t, 2).toString()));
            }
            for (int r = 0; r < nOfTracks.size(); ++r) {
                final XYSeriesCollection xyDataset = new XYSeriesCollection();
                XYSeries seriesTrack = null;
                XYSeries seriesBg = null;
                final List<Double> perTrack = new ArrayList<Double>();
                final List<Double> perFrame = new ArrayList<Double>();
                final List<Double> perTrackDef = new ArrayList<Double>();
                final List<Double> perXPositionDef = new ArrayList<Double>();
                final List<Double> perYPositionDef = new ArrayList<Double>();
                final List<Double> perFrameDef = new ArrayList<Double>();
                for (int t2 = 0; t2 < SPTBatch_.tableSpot.getRowCount(); ++t2) {
                    if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t2, 2).toString()).equals(nOfTracks.get(r)) == Boolean.TRUE) {
                        perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t2, 12).toString()));
                        perFrame.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t2, 8).toString()));
                        perTrackDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t2, 12).toString()));
                        perFrameDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t2, 8).toString()));
                        perXPositionDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t2, 4).toString()));
                        perYPositionDef.add(Double.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t2, 5).toString()));
                    }
                }
                this.rt = new ResultsTable();
                seriesTrack = new XYSeries((Comparable)"Spot Mean Raw Intensity Track ");
                seriesBg = new XYSeries((Comparable)"Bg Intensity");
                for (int i = 0; i < perTrackDef.size(); ++i) {
                    seriesTrack.add((Number)perFrameDef.get(i), (Number)perTrackDef.get(i));
                    this.rt.setValue("Spot Mean Raw Intensity Track ", i, (double)perTrackDef.get(i));
                }
                for (int i = 0; i < SPTBatch_.imps.getStack().getSize(); ++i) {
                    seriesBg.add((double)i, (Number)SPTBatch_.slicesIntensitySpot[i]);
                    this.rt.setValue("Bg Intensity", i, (double)SPTBatch_.slicesIntensitySpot[i]);
                }
                if (perFrameDef.get(perFrameDef.size() - 1).intValue() != SPTBatch_.imps.getStack().getSize() - 1) {
                    final ImagePlus[] slices = SPTBatch_.stack2images(SPTBatch_.imps.duplicate());
                    final OvalRoi ovalRoi = new OvalRoi((double)perXPositionDef.get(perXPositionDef.size() - 1), (double)perXPositionDef.get(perXPositionDef.size() - 1), Double.valueOf(SPTBatch_.RADIUS) / SPTBatch_.imps.getCalibration().pixelWidth, Double.valueOf(SPTBatch_.RADIUS) / SPTBatch_.imps.getCalibration().pixelWidth);
                    for (int j = perFrameDef.get(perFrameDef.size() - 1).intValue(); j < SPTBatch_.imps.getStack().getSize(); ++j) {
                        slices[j].setRoi((Roi)ovalRoi);
                        seriesTrack.add((double)j, slices[j].getStatistics().mean);
                        this.rt.setValue("Spot Mean Raw Intensity Track ", j, slices[j].getStatistics().mean);
                    }
                }
                xyDataset.addSeries(seriesTrack);
                xyDataset.addSeries(seriesBg);
                this.chart = ChartFactory.createXYLineChart("PhotoBleaching step for Track " + nOfTracks.get(r), "Frame", "Intensity", (XYDataset)xyDataset, PlotOrientation.VERTICAL, true, true, false);
                this.chartpanel.removeAll();
                if (this.chart != null) {
                    final XYItemRenderer renderer = ((XYPlot)this.chart.getPlot()).getRenderer();
                    final PerTrackFeatureColorGenerator tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, "TRACK_INDEX", (Color)null, (Color)null, Colormap.Turbo, 0.0, 1.0);
                    final XYPlot plot = this.chart.getXYPlot();
                    plot.setDataset(0, (XYDataset)xyDataset);
                    plot.setRenderer(0, renderer);
                    plot.getRendererForDataset(plot.getDataset(0)).setSeriesPaint(0, (Paint)tcg.colorOf(Integer.valueOf(nOfTracks.get(r))));
                }
                final XYPlot plot2 = (XYPlot)this.chart.getPlot();
                plot2.setBackgroundPaint((Paint)Color.white);
                plot2.setDomainGridlinesVisible(true);
                plot2.setRangeGridlinesVisible(true);
                plot2.setDomainMinorGridlinePaint((Paint)Color.GRAY);
                plot2.setDomainGridlinePaint((Paint)Color.DARK_GRAY);
                plot2.setRangeMinorGridlinePaint((Paint)Color.GRAY);
                plot2.setRangeGridlinePaint((Paint)Color.DARK_GRAY);
                this.chartpanel.add((Component)new ChartPanel(this.chart));
                this.chartpanel.updateUI();
                try {
                    ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directPBS.getAbsolutePath()) + File.separator + SPTBatch_.imgTitle + "_" + nOfTracks.get(r).toString() + ".png"), this.chart, 2000, 400);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    this.rt.saveAs(String.valueOf(SPTBatch_.directPBS.getAbsolutePath()) + File.separator + SPTBatch_.imgTitle + "_" + SPTBatch_.nOfTracks.get(r).toString() + ".csv");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void keyPressed(final Point p, final KeyEvent e) {
    }
    
    public void mouseClick(final Point p, final MouseEvent e) {
    }
    
    public void mouseDrag(final Point p, final MouseEvent e) {
    }
    
    public void mouseMove(final Point p, final MouseEvent e) {
    }
}
