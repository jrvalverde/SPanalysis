import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

public class HistogramFilterVersion {
   private int BINS;
   private HistogramDataset dataset;
   private XYBarRenderer renderer;
   private XYPlot plot;
   private double[] values;
   private String feature;
   private ChartPanel panel;
   public IntervalMarker intervalMarker;

   public ChartPanel createChartPanel(String feature, double[] values, int BINS, IntervalMarker intervalMarker) {
      this.feature = feature;
      this.values = values;
      this.BINS = BINS;
      this.intervalMarker = intervalMarker;
      this.dataset = new HistogramDataset();
      if (BINS != 0) {
         this.dataset.addSeries(feature, values, BINS);
      }

      JFreeChart chart = ChartFactory.createHistogram("", feature, "", this.dataset, PlotOrientation.VERTICAL, true, true, false);
      this.plot = (XYPlot)chart.getPlot();
      this.renderer = (XYBarRenderer)this.plot.getRenderer();
      this.renderer.setBarPainter(new StandardXYBarPainter());
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      this.plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      this.panel = new ChartPanel(chart);
      this.panel.setPreferredSize(new Dimension(390, 180));
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      this.plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      this.panel.setMouseWheelEnabled(true);
      Font font3 = new Font("Dialog", 2, 9);
      this.plot.getDomainAxis().setLabelFont(font3);
      this.plot.getRangeAxis().setLabelFont(font3);
      this.plot.getRangeAxis().setTickLabelFont(font3);
      this.plot.getDomainAxis().setTickLabelFont(font3);
      chart.getLegend().setVisible(false);
      this.plot.addDomainMarker(intervalMarker);
      double x = (double)((float)(0.05D * this.plot.getDomainAxis().getRange().getLength()));
      double y = (double)((float)(0.85D * this.plot.getRangeAxis().getUpperBound()));
      return this.panel;
   }

   public JPanel createControlPanel() {
      JPanel panel = new JPanel();
      return panel;
   }

   public void addHistogramSeries(String feature, double[] values, int BINS, IntervalMarker intervalMarker) {
      this.feature = feature;
      this.values = values;
      this.BINS = BINS;
      this.intervalMarker = intervalMarker;
      this.panel.removeAll();
      this.dataset = new HistogramDataset();
      if (BINS != 0) {
         this.dataset.addSeries(feature, values, BINS);
      }

      JFreeChart chart = ChartFactory.createHistogram("", feature, "COUNTING", this.dataset, PlotOrientation.VERTICAL, true, true, false);
      this.plot = (XYPlot)chart.getPlot();
      this.renderer = (XYBarRenderer)this.plot.getRenderer();
      this.renderer.setBarPainter(new StandardXYBarPainter());
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      this.plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      this.panel.setChart(chart);
      this.panel.setPreferredSize(new Dimension(390, 180));
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      this.plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      this.panel.setMouseWheelEnabled(true);
      chart.getLegend().setVisible(false);
      Font font3 = new Font("Dialog", 2, 10);
      this.plot.getDomainAxis().setLabelFont(font3);
      this.plot.getRangeAxis().setLabelFont(font3);
      this.plot.getRangeAxis().setTickLabelFont(font3);
      this.plot.getDomainAxis().setTickLabelFont(font3);
      this.plot.addDomainMarker(intervalMarker);
   }

   public void display() {
      JFrame f = new JFrame("Histogram");
      f.setDefaultCloseOperation(3);
      f.add(this.createControlPanel(), "South");
      f.pack();
      f.setLocationRelativeTo((Component)null);
      f.setVisible(true);
   }

   public class VisibleAction extends AbstractAction {
      private static final long serialVersionUID = 1L;
      private final int i;

      public VisibleAction(int i) {
         this.i = i;
         this.putValue("Name", (String)HistogramFilterVersion.this.dataset.getSeriesKey(i));
         this.putValue("SwingSelectedKey", true);
         HistogramFilterVersion.this.renderer.setSeriesVisible(i, true);
      }

      public void actionPerformed(ActionEvent e) {
         HistogramFilterVersion.this.renderer.setSeriesVisible(this.i, !HistogramFilterVersion.this.renderer.getSeriesVisible(this.i));
      }
   }
}
