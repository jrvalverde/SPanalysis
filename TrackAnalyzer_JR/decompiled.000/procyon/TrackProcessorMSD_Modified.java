import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Point;
import fiji.plugin.trackmate.Spot;
import java.util.Iterator;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import java.io.IOException;
import org.jfree.chart.ChartUtils;
import java.io.File;
import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
import fiji.plugin.trackmate.gui.displaysettings.Colormap;
import java.awt.Paint;
import java.awt.Color;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import java.util.ArrayList;
import org.jfree.data.xy.XYSeriesCollection;
import ij.measure.ResultsTable;
import java.util.List;
import java.awt.Component;
import org.jfree.chart.ChartPanel;
import javax.swing.JLabel;
import java.awt.geom.Rectangle2D;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import org.jfree.chart.JFreeChart;

// 
// Decompiled by Procyon v0.5.36
// 

public class TrackProcessorMSD_Modified
{
    JFreeChart chart;
    JCheckBox displayLegendCheckBox;
    JCheckBox displayGraphInSequenceCheckBox;
    JButton useRoiAsBoundsForChartButton;
    JCheckBox forceAllSequenceGraphWidthCheckBox;
    JCheckBox useRealScalesBox;
    JPanel chartpanel;
    JTextField scaleTextField;
    JButton exportButton;
    JButton exportExcelButton;
    Rectangle2D chartRectangleInSequence;
    JLabel outLabel;
    
    public TrackProcessorMSD_Modified() {
        this.displayLegendCheckBox = new JCheckBox("Display legend.", false);
        this.displayGraphInSequenceCheckBox = new JCheckBox("Display graph on sequence.", false);
        this.useRoiAsBoundsForChartButton = new JButton("place graph in ROI #1");
        this.forceAllSequenceGraphWidthCheckBox = new JCheckBox("Force graph width.", false);
        this.useRealScalesBox = new JCheckBox("use real scales", false);
        this.chartpanel = new JPanel();
        this.scaleTextField = new JTextField("1.0");
        this.exportButton = new JButton("export to console");
        this.exportExcelButton = new JButton("export to excel");
        this.chartRectangleInSequence = new Rectangle2D.Float(250.0f, 20.0f, 490.0f, 240.0f);
        this.outLabel = new JLabel();
        this.chartpanel.add((Component)new ChartPanel(this.chart, 500, 300, 500, 300, 500, 300, false, false, true, true, true, true));
    }
    
    public void Compute(final List<Integer> nOfTracks, final ResultsTable rtSpots) {
        final XYSeriesCollection xyDataset = new XYSeriesCollection();
        XYSeries series = null;
        final List<List<Double>> imported2SpotX = new ArrayList<List<Double>>();
        final List<List<Double>> imported2SpotY = new ArrayList<List<Double>>();
        final List<List<Double>> imported2SpotT = new ArrayList<List<Double>>();
        final List<Integer> trackName = new ArrayList<Integer>();
        for (int id = 0; id < nOfTracks.size(); ++id) {
            trackName.add(nOfTracks.get(id));
            final List<Double> imported1SpotX = new ArrayList<Double>();
            final List<Double> imported1SpotY = new ArrayList<Double>();
            final List<Double> imported1SpotT = new ArrayList<Double>();
            for (int i = 0; i < rtSpots.size(); ++i) {
                if (rtSpots.getStringValue(2, i).equals(String.valueOf((int)nOfTracks.get(id))) == Boolean.TRUE) {
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
        for (int j = 0; j < imported2SpotX.size(); ++j) {
            series = new XYSeries((Comparable)("Track " + j));
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
            final double frameInterval = imported2SpotT.get(j).get(2) - imported2SpotT.get(j).get(1);
            final int nMSD = imported2SpotX.get(j).size();
            final int[] tau = new int[nMSD - 1];
            final double[] msdArray = new double[tau.length];
            for (int z = 0; z < nMSD - 1 - 1 + 1; ++z) {
                tau[z] = 1 + z;
            }
            double msd = -1.0;
            for (int dt = 0; dt < tau.length; ++dt) {
                double N = 0.0;
                msd = 0.0;
                for (int k = tau[dt]; k < imported2SpotX.get(j).size(); ++k) {
                    msd = msd + Math.pow(imported2SpotX.get(j).get(k - tau[dt]) - imported2SpotX.get(j).get(k), 2.0) + Math.pow(imported2SpotY.get(j).get(k - tau[dt]) - imported2SpotY.get(j).get(k), 2.0);
                    ++N;
                }
                msd /= N;
                msdArray[dt] = msd;
                series.add(tau[dt] * frameInterval, msd);
            }
            xyDataset.addSeries(series);
        }
        this.chart = ChartFactory.createXYLineChart(TitleString, TitleString2, TitleString3, (XYDataset)xyDataset, PlotOrientation.VERTICAL, this.displayLegendCheckBox.isSelected(), true, false);
        final XYPlot plot = (XYPlot)this.chart.getPlot();
        plot.setBackgroundPaint((Paint)Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        plot.setDomainMinorGridlinePaint((Paint)Color.GRAY);
        plot.setDomainGridlinePaint((Paint)Color.DARK_GRAY);
        plot.setRangeMinorGridlinePaint((Paint)Color.GRAY);
        plot.setRangeGridlinePaint((Paint)Color.DARK_GRAY);
        this.chartpanel.removeAll();
        if (this.chart != null) {
            final XYItemRenderer renderer = ((XYPlot)this.chart.getPlot()).getRenderer();
            for (final Integer id2 : nOfTracks) {
                final PerTrackFeatureColorGenerator tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, "TRACK_INDEX", (Color)null, (Color)null, Colormap.Turbo, 0.0, 1.0);
                renderer.setSeriesPaint((int)id2, (Paint)tcg.colorOf(id2));
            }
        }
        this.chartpanel.add((Component)new ChartPanel(this.chart, 400, 300, 400, 300, 400, 300, false, false, true, true, true, true));
        this.chartpanel.updateUI();
        if (SPTBatch_.checkboxDiff.isSelected() == Boolean.TRUE) {
            try {
                ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directDiff.getAbsolutePath()) + File.separator + "MSD_curve_" + SPTBatch_.imps.getShortTitle() + ".png"), this.chart, 500, 400);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (SPTBatch_.checkboxDiff.isSelected() == Boolean.FALSE) {
            try {
                ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directImages.getAbsolutePath()) + File.separator + "MSD_curve_" + SPTBatch_.imps.getShortTitle() + ".png"), this.chart, 500, 400);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static double scaledSquaredDistance(final Spot spotSource, final Spot spotTarget, final double sx, final double sy, final double sz) {
        return Math.pow((spotSource.getFeature("POSITION_X") - spotTarget.getFeature("POSITION_X")) * sx, 2.0) + Math.pow((spotSource.getFeature("POSITION_Y") - spotTarget.getFeature("POSITION_Y")) * sy, 2.0);
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
