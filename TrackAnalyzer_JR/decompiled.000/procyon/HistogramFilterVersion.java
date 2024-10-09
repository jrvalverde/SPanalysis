import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Marker;
import java.awt.Font;
import java.awt.Dimension;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.renderer.xy.XYBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

// 
// Decompiled by Procyon v0.5.36
// 

public class HistogramFilterVersion
{
    private int BINS;
    private HistogramDataset dataset;
    private XYBarRenderer renderer;
    private XYPlot plot;
    private double[] values;
    private String feature;
    private ChartPanel panel;
    public IntervalMarker intervalMarker;
    
    public ChartPanel createChartPanel(final String feature, final double[] values, final int BINS, final IntervalMarker intervalMarker) {
        this.feature = feature;
        this.values = values;
        this.BINS = BINS;
        this.intervalMarker = intervalMarker;
        this.dataset = new HistogramDataset();
        if (BINS != 0) {
            this.dataset.addSeries((Comparable)feature, values, BINS);
        }
        final JFreeChart chart = ChartFactory.createHistogram("", feature, "", (IntervalXYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, false);
        this.plot = (XYPlot)chart.getPlot();
        (this.renderer = (XYBarRenderer)this.plot.getRenderer()).setBarPainter((XYBarPainter)new StandardXYBarPainter());
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        this.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        (this.panel = new ChartPanel(chart)).setPreferredSize(new Dimension(390, 180));
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.panel.setMouseWheelEnabled(true);
        final Font font3 = new Font("Dialog", 2, 9);
        this.plot.getDomainAxis().setLabelFont(font3);
        this.plot.getRangeAxis().setLabelFont(font3);
        this.plot.getRangeAxis().setTickLabelFont(font3);
        this.plot.getDomainAxis().setTickLabelFont(font3);
        chart.getLegend().setVisible(false);
        this.plot.addDomainMarker((Marker)intervalMarker);
        final double x = (float)(0.05 * this.plot.getDomainAxis().getRange().getLength());
        final double y = (float)(0.85 * this.plot.getRangeAxis().getUpperBound());
        return this.panel;
    }
    
    public JPanel createControlPanel() {
        final JPanel panel = new JPanel();
        return panel;
    }
    
    public void addHistogramSeries(final String feature, final double[] values, final int BINS, final IntervalMarker intervalMarker) {
        this.feature = feature;
        this.values = values;
        this.BINS = BINS;
        this.intervalMarker = intervalMarker;
        this.panel.removeAll();
        this.dataset = new HistogramDataset();
        if (BINS != 0) {
            this.dataset.addSeries((Comparable)feature, values, BINS);
        }
        final JFreeChart chart = ChartFactory.createHistogram("", feature, "COUNTING", (IntervalXYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, false);
        this.plot = (XYPlot)chart.getPlot();
        (this.renderer = (XYBarRenderer)this.plot.getRenderer()).setBarPainter((XYBarPainter)new StandardXYBarPainter());
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        this.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        this.panel.setChart(chart);
        this.panel.setPreferredSize(new Dimension(390, 180));
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.panel.setMouseWheelEnabled(true);
        chart.getLegend().setVisible(false);
        final Font font3 = new Font("Dialog", 2, 10);
        this.plot.getDomainAxis().setLabelFont(font3);
        this.plot.getRangeAxis().setLabelFont(font3);
        this.plot.getRangeAxis().setTickLabelFont(font3);
        this.plot.getDomainAxis().setTickLabelFont(font3);
        this.plot.addDomainMarker((Marker)intervalMarker);
    }
    
    public void display() {
        final JFrame f = new JFrame("Histogram");
        f.setDefaultCloseOperation(3);
        f.add(this.createControlPanel(), "South");
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public class VisibleAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        private final int i;
        
        public VisibleAction(final int i) {
            this.i = i;
            this.putValue("Name", HistogramFilterVersion.this.dataset.getSeriesKey(i));
            this.putValue("SwingSelectedKey", true);
            HistogramFilterVersion.this.renderer.setSeriesVisible(i, Boolean.valueOf(true));
        }
        
        @Override
        public void actionPerformed(final ActionEvent e) {
            HistogramFilterVersion.this.renderer.setSeriesVisible(this.i, Boolean.valueOf(!HistogramFilterVersion.this.renderer.getSeriesVisible(this.i)));
        }
    }
}
