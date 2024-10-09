import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.function.PowerFunction2D;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class STScatterPlot extends ApplicationFrame {
   private XYDataset data1;
   List<Double> valuesDomain;
   List<Double> valuesRange;
   IntervalMarker markerRange;
   IntervalMarker markerDomain;
   String domainName;
   String rangeName;
   Object[][] data;
   Color[] classColor;
   String label1;
   String label2;
   XYSeriesCollection dataset;
   static XYPlot plot;
   ChartPanel panel;
   XYSeries series1;
   XYLineAndShapeRenderer renderer;
   double maxDomain;
   int filterOrder;

   public STScatterPlot(String title) {
      super(title);
   }

   public ChartPanel createChartPanelPolynomial() {
      return null;
   }

   public ChartPanel createScatterChartPanelInitial(String label1, String label2, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] dataCh2, Double[][] dataCh3) {
      this.label1 = label1;
      this.label2 = label2;
      this.valuesDomain = valuesDomain;
      this.valuesRange = valuesRange;
      this.markerRange = markerRange;
      this.markerDomain = markerDomain;
      this.dataset = new XYSeriesCollection();
      XYSeries series1 = new XYSeries("");

      for(int i = 0; i < valuesDomain.size(); ++i) {
         series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
      }

      this.dataset.addSeries(series1);
      JFreeChart chart = ChartFactory.createScatterPlot("", "", "", this.dataset);
      plot = (XYPlot)chart.getPlot();
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      this.panel = new ChartPanel(chart, false);
      this.panel.setMaximumDrawWidth(4000);
      this.panel.setPreferredSize(new Dimension(450, 300));
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setVisible(false);
      this.panel.setMouseWheelEnabled(true);
      plot.addRangeMarker(markerRange);
      plot.addDomainMarker(markerDomain);
      Font font3 = new Font("Dialog", 2, 9);
      plot.getDomainAxis().setLabelFont(font3);
      plot.getRangeAxis().setLabelFont(font3);
      plot.getRangeAxis().setTickLabelFont(font3);
      plot.getDomainAxis().setTickLabelFont(font3);
      return this.panel;
   }

   public void addScatterPlotSeriesLinear(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
      this.valuesDomain = valuesDomain;
      this.rangeName = rangeName;
      this.domainName = domainName;
      this.valuesRange = valuesRange;
      this.markerRange = markerRange;
      this.markerDomain = markerDomain;
      this.classColor = classColor;
      this.panel.removeAll();
      this.dataset = new XYSeriesCollection();
      this.series1 = new XYSeries("");

      for(int i = 0; i < valuesDomain.size(); ++i) {
         this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
      }

      this.dataset.addSeries(this.series1);
      double minDomain = (Double)Collections.min(valuesDomain);
      double maxDomain = (Double)Collections.max(valuesDomain);
      double[] coefficients = Regression.getOLSRegression(this.dataset, 0);
      Function2D curve = new LineFunction2D(coefficients[0], coefficients[1]);
      XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), "Fitted Regression Line");
      JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, this.dataset, PlotOrientation.VERTICAL, true, true, false);
      plot = (XYPlot)chart.getPlot();
      XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
      plot.setRenderer(renderer1);
      plot.setBackgroundPaint(new Color(255, 228, 196));
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      plot.setDataset(1, regressionData);
      XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
      renderer2.setSeriesPaint(0, Color.RED);
      plot.setRenderer(1, renderer2);
      Shape cross = new java.awt.geom.Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
      List<String[]> featureLists = new ArrayList<String[]>();

      for(int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); ++i) {
         featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(i, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
      }

      this.renderer = new XYLineAndShapeRenderer() {
         public Paint getItemPaint(int row, int col) {
            Paint cpaint = this.getItemColor(row, col);
            if (cpaint == null) {
               cpaint = super.getItemPaint(row, col);
            }

            return (Paint)cpaint;
         }

         public Color getItemColor(int row, int col) {
            STScatterPlot.this.dataset.getXValue(row, col);
            STScatterPlot.this.dataset.getYValue(row, col);

            try {
               return classColor[col];
            } catch (Exception var8) {
               return null;
            }
         }
      };
      plot.setRenderer(this.renderer);
      this.renderer.setUseOutlinePaint(true);
      this.renderer.setSeriesShape(0, cross);
      this.renderer.setSeriesOutlinePaint(0, Color.black);
      this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
      this.renderer.setSeriesLinesVisible(0, false);
      this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
      plot.getRangeCrosshairValue();
      plot.getDomainCrosshairValue();
      this.panel.setChart(chart);
      this.panel.setMaximumDrawWidth(6000);
      this.panel.setPreferredSize(new Dimension(450, 300));
      this.panel.setMouseWheelEnabled(true);
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      this.panel.setMouseWheelEnabled(true);
      chart.getLegend().setVisible(true);
      plot.addRangeMarker(markerRange);
      plot.addDomainMarker(markerDomain);
      markerRange.setLabel("Low-High");
      markerRange.setLabelFont(new Font("SansSerif", 0, 15));
      markerRange.setLabelPaint(new Color(0, 102, 0));
      Font font3 = new Font("Dialog", 1, 12);
      plot.getDomainAxis().setLabelFont(font3);
      plot.getRangeAxis().setLabelFont(font3);
      plot.getRangeAxis().setTickLabelFont(font3);
      plot.getDomainAxis().setTickLabelFont(font3);
      NumberAxis domain = (NumberAxis)plot.getDomainAxis();
      NumberAxis range = (NumberAxis)plot.getRangeAxis();
      domain.setAutoRange(true);
      range.setAutoRange(true);
      this.computeLinearCoefficients(chart, plot, this.dataset);
   }

   public void addScatterPlotSeriesPower(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
      this.valuesDomain = valuesDomain;
      this.rangeName = rangeName;
      this.domainName = domainName;
      this.valuesRange = valuesRange;
      this.markerRange = markerRange;
      this.markerDomain = markerDomain;
      this.classColor = classColor;
      this.panel.removeAll();
      this.dataset = new XYSeriesCollection();
      this.series1 = new XYSeries("");

      for(int i = 0; i < valuesDomain.size(); ++i) {
         this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
      }

      this.dataset.addSeries(this.series1);
      double minDomain = (Double)Collections.min(valuesDomain);
      double maxDomain = (Double)Collections.max(valuesDomain);
      double[] coefficients = Regression.getPowerRegression(this.dataset, 0);
      Function2D curve = new PowerFunction2D(coefficients[0], coefficients[1]);
      XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), "Fitted Regression Line");
      JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, this.dataset, PlotOrientation.VERTICAL, true, true, false);
      plot = (XYPlot)chart.getPlot();
      XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
      plot.setRenderer(renderer1);
      plot.setBackgroundPaint(new Color(255, 228, 196));
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      plot.setDataset(1, regressionData);
      XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
      renderer2.setSeriesPaint(0, Color.RED);
      plot.setRenderer(1, renderer2);
      Shape cross = new java.awt.geom.Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
      List<String[]> featureLists = new ArrayList<String[]>();

      for(int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); ++i) {
         featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(i, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
      }

      this.renderer = new XYLineAndShapeRenderer() {
         public Paint getItemPaint(int row, int col) {
            Paint cpaint = this.getItemColor(row, col);
            if (cpaint == null) {
               cpaint = super.getItemPaint(row, col);
            }

            return (Paint)cpaint;
         }

         public Color getItemColor(int row, int col) {
            STScatterPlot.this.dataset.getXValue(row, col);
            STScatterPlot.this.dataset.getYValue(row, col);

            try {
               return classColor[col];
            } catch (Exception var8) {
               return null;
            }
         }
      };
      plot.setRenderer(this.renderer);
      this.renderer.setUseOutlinePaint(true);
      this.renderer.setSeriesShape(0, cross);
      this.renderer.setSeriesOutlinePaint(0, Color.black);
      this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
      this.renderer.setSeriesLinesVisible(0, false);
      this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
      plot.getRangeCrosshairValue();
      plot.getDomainCrosshairValue();
      this.panel.setChart(chart);
      this.panel.setMaximumDrawWidth(6000);
      this.panel.setPreferredSize(new Dimension(450, 300));
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      this.panel.setMouseWheelEnabled(true);
      chart.getLegend().setVisible(true);
      plot.addRangeMarker(markerRange);
      plot.addDomainMarker(markerDomain);
      markerRange.setLabel("Low-High");
      markerRange.setLabelFont(new Font("SansSerif", 0, 15));
      markerRange.setLabelPaint(new Color(0, 102, 0));
      Font font3 = new Font("Dialog", 1, 12);
      plot.getDomainAxis().setLabelFont(font3);
      plot.getRangeAxis().setLabelFont(font3);
      plot.getRangeAxis().setTickLabelFont(font3);
      plot.getDomainAxis().setTickLabelFont(font3);
      NumberAxis domain = (NumberAxis)plot.getDomainAxis();
      NumberAxis range = (NumberAxis)plot.getRangeAxis();
      domain.setAutoRange(true);
      range.setAutoRange(true);
      this.computePowerCoefficients(chart, plot, this.dataset);
   }

   public void addScatterPlotSeriesPolynomial(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor, int filterOrder) {
      this.valuesDomain = valuesDomain;
      this.rangeName = rangeName;
      this.domainName = domainName;
      this.valuesRange = valuesRange;
      this.markerRange = markerRange;
      this.markerDomain = markerDomain;
      this.classColor = classColor;
      this.filterOrder = filterOrder;
      this.panel.removeAll();
      this.dataset = new XYSeriesCollection();
      this.series1 = new XYSeries("");

      for(int i = 0; i < valuesDomain.size(); ++i) {
         this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
      }

      this.dataset.addSeries(this.series1);
      double minDomain = (Double)Collections.min(valuesDomain);
      double maxDomain = (Double)Collections.max(valuesDomain);
      double[] coefficients = Regression.getPolynomialRegression(this.dataset, 0, filterOrder);
      Function2D curve = new PowerFunction2D(coefficients[0], coefficients[1]);
      XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), "Fitted Regression Line");
      JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, this.dataset, PlotOrientation.VERTICAL, true, true, false);
      plot = (XYPlot)chart.getPlot();
      XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
      plot.setRenderer(renderer1);
      plot.setBackgroundPaint(new Color(255, 228, 196));
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      plot.setDataset(1, regressionData);
      XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
      renderer2.setSeriesPaint(0, Color.RED);
      plot.setRenderer(1, renderer2);
      Shape cross = new java.awt.geom.Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
      List<String[]> featureLists = new ArrayList<String[]>();

      for(int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); ++i) {
         featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(i, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
      }

      this.renderer = new XYLineAndShapeRenderer() {
         public Paint getItemPaint(int row, int col) {
            Paint cpaint = this.getItemColor(row, col);
            if (cpaint == null) {
               cpaint = super.getItemPaint(row, col);
            }

            return (Paint)cpaint;
         }

         public Color getItemColor(int row, int col) {
            STScatterPlot.this.dataset.getXValue(row, col);
            STScatterPlot.this.dataset.getYValue(row, col);

            try {
               return classColor[col];
            } catch (Exception var8) {
               return null;
            }
         }
      };
      plot.setRenderer(this.renderer);
      this.renderer.setUseOutlinePaint(true);
      this.renderer.setSeriesShape(0, cross);
      this.renderer.setSeriesOutlinePaint(0, Color.black);
      this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
      this.renderer.setSeriesLinesVisible(0, false);
      this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
      plot.getRangeCrosshairValue();
      plot.getDomainCrosshairValue();
      this.panel.setChart(chart);
      this.panel.setMaximumDrawWidth(6000);
      this.panel.setPreferredSize(new Dimension(450, 300));
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      this.panel.setMouseWheelEnabled(true);
      chart.getLegend().setVisible(true);
      plot.addRangeMarker(markerRange);
      plot.addDomainMarker(markerDomain);
      markerRange.setLabel("Low-High");
      markerRange.setLabelFont(new Font("SansSerif", 0, 15));
      markerRange.setLabelPaint(new Color(0, 102, 0));
      Font font3 = new Font("Dialog", 1, 12);
      plot.getDomainAxis().setLabelFont(font3);
      plot.getRangeAxis().setLabelFont(font3);
      plot.getRangeAxis().setTickLabelFont(font3);
      plot.getDomainAxis().setTickLabelFont(font3);
      NumberAxis domain = (NumberAxis)plot.getDomainAxis();
      NumberAxis range = (NumberAxis)plot.getRangeAxis();
      domain.setAutoRange(true);
      range.setAutoRange(true);
      this.computePolynomialCoefficients(chart, plot, this.dataset, filterOrder);
   }

   public void addScatterPlotSeriesLogarithmic(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
      this.valuesDomain = valuesDomain;
      this.rangeName = rangeName;
      this.domainName = domainName;
      this.valuesRange = valuesRange;
      this.markerRange = markerRange;
      this.markerDomain = markerDomain;
      this.classColor = classColor;
      this.filterOrder = this.filterOrder;
      this.panel.removeAll();
      this.dataset = new XYSeriesCollection();
      this.series1 = new XYSeries("");

      for(int i = 0; i < valuesDomain.size(); ++i) {
         this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
      }

      this.dataset.addSeries(this.series1);
      double minDomain = (Double)Collections.min(valuesDomain);
      double maxDomain = (Double)Collections.max(valuesDomain);
      double[] coefficients = RegressionLE_.getLogarithmicRegression(this.dataset, 0);
      Function2D curve = new LogarithmicFunction2D(coefficients[0], coefficients[1]);
      XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), "Fitted Regression Line");
      JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, this.dataset, PlotOrientation.VERTICAL, true, true, false);
      plot = (XYPlot)chart.getPlot();
      XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
      plot.setRenderer(renderer1);
      plot.setBackgroundPaint(new Color(255, 228, 196));
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      plot.setDataset(1, regressionData);
      XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
      renderer2.setSeriesPaint(0, Color.RED);
      plot.setRenderer(1, renderer2);
      Shape cross = new java.awt.geom.Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
      List<String[]> featureLists = new ArrayList<String[]>();

      for(int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); ++i) {
         featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(i, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
      }

      this.renderer = new XYLineAndShapeRenderer() {
         public Paint getItemPaint(int row, int col) {
            Paint cpaint = this.getItemColor(row, col);
            if (cpaint == null) {
               cpaint = super.getItemPaint(row, col);
            }

            return (Paint)cpaint;
         }

         public Color getItemColor(int row, int col) {
            STScatterPlot.this.dataset.getXValue(row, col);
            STScatterPlot.this.dataset.getYValue(row, col);

            try {
               return classColor[col];
            } catch (Exception var8) {
               return null;
            }
         }
      };
      plot.setRenderer(this.renderer);
      this.renderer.setUseOutlinePaint(true);
      this.renderer.setSeriesShape(0, cross);
      this.renderer.setSeriesOutlinePaint(0, Color.black);
      this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
      this.renderer.setSeriesLinesVisible(0, false);
      this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
      plot.getRangeCrosshairValue();
      plot.getDomainCrosshairValue();
      this.panel.setChart(chart);
      this.panel.setMaximumDrawWidth(6000);
      this.panel.setPreferredSize(new Dimension(450, 300));
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      this.panel.setMouseWheelEnabled(true);
      chart.getLegend().setVisible(true);
      plot.addRangeMarker(markerRange);
      plot.addDomainMarker(markerDomain);
      markerRange.setLabel("Low-High");
      markerRange.setLabelFont(new Font("SansSerif", 0, 15));
      markerRange.setLabelPaint(new Color(0, 102, 0));
      Font font3 = new Font("Dialog", 1, 12);
      plot.getDomainAxis().setLabelFont(font3);
      plot.getRangeAxis().setLabelFont(font3);
      plot.getRangeAxis().setTickLabelFont(font3);
      plot.getDomainAxis().setTickLabelFont(font3);
      NumberAxis domain = (NumberAxis)plot.getDomainAxis();
      NumberAxis range = (NumberAxis)plot.getRangeAxis();
      domain.setAutoRange(true);
      range.setAutoRange(true);
      this.computeLogarithmicCoefficients(chart, plot, this.dataset, this.filterOrder);
   }

   public void addScatterPlotSeriesExponential(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
      this.valuesDomain = valuesDomain;
      this.rangeName = rangeName;
      this.domainName = domainName;
      this.valuesRange = valuesRange;
      this.markerRange = markerRange;
      this.markerDomain = markerDomain;
      this.classColor = classColor;
      this.filterOrder = this.filterOrder;
      this.panel.removeAll();
      this.dataset = new XYSeriesCollection();
      this.series1 = new XYSeries("");

      for(int i = 0; i < valuesDomain.size(); ++i) {
         this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
      }

      this.dataset.addSeries(this.series1);
      double minDomain = (Double)Collections.min(valuesDomain);
      double maxDomain = (Double)Collections.max(valuesDomain);
      double[] coefficients = RegressionLE_.getExponentialRegression(this.dataset, 0);
      Function2D curve = new ExponentialFunction2D(coefficients[0], coefficients[1]);
      XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), "Fitted Regression Line");
      JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, this.dataset, PlotOrientation.VERTICAL, true, true, false);
      plot = (XYPlot)chart.getPlot();
      XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
      plot.setRenderer(renderer1);
      plot.setBackgroundPaint(new Color(255, 228, 196));
      Paint[] paintArray = new Paint[]{new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true)};
      plot.setDrawingSupplier(new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
      plot.setDataset(1, regressionData);
      XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
      renderer2.setSeriesPaint(0, Color.RED);
      plot.setRenderer(1, renderer2);
      Shape cross = new java.awt.geom.Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
      List<String[]> featureLists = new ArrayList<String[]>();

      for(int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); ++i) {
         featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(i, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
      }

      this.renderer = new XYLineAndShapeRenderer() {
         public Paint getItemPaint(int row, int col) {
            Paint cpaint = this.getItemColor(row, col);
            if (cpaint == null) {
               cpaint = super.getItemPaint(row, col);
            }

            return (Paint)cpaint;
         }

         public Color getItemColor(int row, int col) {
            STScatterPlot.this.dataset.getXValue(row, col);
            STScatterPlot.this.dataset.getYValue(row, col);

            try {
               return classColor[col];
            } catch (Exception var8) {
               return null;
            }
         }
      };
      plot.setRenderer(this.renderer);
      this.renderer.setUseOutlinePaint(true);
      this.renderer.setSeriesShape(0, cross);
      this.renderer.setSeriesOutlinePaint(0, Color.black);
      this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
      this.renderer.setSeriesLinesVisible(0, false);
      this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
      plot.getRangeCrosshairValue();
      plot.getDomainCrosshairValue();
      this.panel.setChart(chart);
      this.panel.setMaximumDrawWidth(6000);
      this.panel.setPreferredSize(new Dimension(450, 300));
      chart.setBackgroundPaint(new Color(255, 255, 255, 0));
      plot.setBackgroundPaint(new Color(255, 255, 255, 0));
      chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
      this.panel.setMouseWheelEnabled(true);
      chart.getLegend().setVisible(true);
      plot.addRangeMarker(markerRange);
      plot.addDomainMarker(markerDomain);
      markerRange.setLabel("Low-High");
      markerRange.setLabelFont(new Font("SansSerif", 0, 15));
      markerRange.setLabelPaint(new Color(0, 102, 0));
      Font font3 = new Font("Dialog", 1, 12);
      plot.getDomainAxis().setLabelFont(font3);
      plot.getRangeAxis().setLabelFont(font3);
      plot.getRangeAxis().setTickLabelFont(font3);
      plot.getDomainAxis().setTickLabelFont(font3);
      NumberAxis domain = (NumberAxis)plot.getDomainAxis();
      NumberAxis range = (NumberAxis)plot.getRangeAxis();
      domain.setAutoRange(true);
      range.setAutoRange(true);
      this.computeExponentialCoefficients(chart, plot, this.dataset, this.filterOrder);
   }

   private void computeLinearCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset) {
      Function2D retVal = null;
      double r2 = 0.0D;
      double[] coefficients = null;

      try {
         coefficients = RegressionLE_.getOLSRegression(dataset, 0);
         new LineFunction2D(coefficients[0], coefficients[1]);
         r2 = coefficients[2];
      } catch (Exception var15) {
         System.err.println(var15.getMessage());
      }

      double intercept = coefficients[0];
      double slope = coefficients[1];
      String linearEquation;
      if (intercept >= 0.0D) {
         linearEquation = "y = " + String.format("%.2f", slope) + "x + " + String.format("%.2f", intercept);
      } else {
         linearEquation = "y = " + String.format("%.2f", slope) + "x - " + Math.abs(intercept);
      }

      TextTitle tt = new TextTitle(linearEquation + "\nR² = " + String.format("%.2f", r2));
      tt.setTextAlignment(HorizontalAlignment.RIGHT);
      tt.setFont(chart.getLegend().getItemFont());
      tt.setBackgroundPaint(new Color(200, 200, 255, 100));
      tt.setFrame(new BlockBorder(Color.white));
      tt.setPosition(RectangleEdge.BOTTOM);
      XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, tt, RectangleAnchor.BOTTOM_RIGHT);
      r2Annotation.setMaxWidth(0.48D);
      plot.addAnnotation(r2Annotation);
   }

   private void computePowerCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset) {
      Function2D retVal = null;
      double r2 = 0.0D;
      double[] coefficients = null;

      try {
         coefficients = RegressionLE_.getPowerRegression(dataset, 0);
         if (coefficients[2] > r2) {
            new PowerFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
         }
      } catch (Exception var15) {
         System.err.println(var15.getMessage());
      }

      double intercept = coefficients[0];
      double slope = coefficients[1];
      String linearEquation;
      if (intercept >= 0.0D) {
         linearEquation = "y = " + String.format("%.2f", slope) + "x^ " + String.format("%.2f", intercept);
      } else {
         linearEquation = "y = " + String.format("%.2f", slope) + "x^ -" + Math.abs(intercept);
      }

      TextTitle tt = new TextTitle(linearEquation + "\nR² = " + String.format("%.2f", r2));
      tt.setTextAlignment(HorizontalAlignment.RIGHT);
      tt.setFont(chart.getLegend().getItemFont());
      tt.setBackgroundPaint(new Color(200, 200, 255, 100));
      tt.setFrame(new BlockBorder(Color.white));
      tt.setPosition(RectangleEdge.BOTTOM);
      XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, tt, RectangleAnchor.BOTTOM_RIGHT);
      r2Annotation.setMaxWidth(0.48D);
      plot.addAnnotation(r2Annotation);
   }

   private void computePolynomialCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset, int filterOrder) {
      double[] coefficients = Regression.getPolynomialRegression(dataset, 0, filterOrder);
      double r2 = coefficients[coefficients.length - 1];
      String polynomialEquation = "";

      for(int i = coefficients.length - 1; i >= 0; --i) {
         if (i == 0) {
            polynomialEquation = polynomialEquation + String.format("%.2f", coefficients[i]);
         } else if (i == 1) {
            polynomialEquation = polynomialEquation + String.format("%.2f", coefficients[i]) + "*x+";
         } else if (i > 1) {
            polynomialEquation = polynomialEquation + String.format("%.2f", coefficients[i]) + "*x^" + i + "+";
         }
      }

      TextTitle tt = new TextTitle("y = " + polynomialEquation + "\nR² = " + String.format("%.2f", r2));
      tt.setTextAlignment(HorizontalAlignment.RIGHT);
      tt.setFont(chart.getLegend().getItemFont());
      tt.setBackgroundPaint(new Color(200, 200, 255, 100));
      tt.setFrame(new BlockBorder(Color.white));
      tt.setPosition(RectangleEdge.BOTTOM);
      XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, tt, RectangleAnchor.BOTTOM_RIGHT);
      r2Annotation.setMaxWidth(0.48D);
      plot.addAnnotation(r2Annotation);
   }

   private void computeLogarithmicCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset, int filterOrder) {
      Function2D retVal = null;
      double r2 = 0.0D;
      double[] coefficients = null;

      try {
         coefficients = RegressionLE_.getLogarithmicRegression(dataset, 0);
         if (coefficients[2] > r2) {
            new LogarithmicFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
         }
      } catch (Exception var12) {
         System.err.println(var12.getMessage());
      }

      String logarithmicEquation = "y = " + String.format("%.2f", coefficients[0]) + " + " + "( " + String.format("%.2f", coefficients[1]) + " * ln(x) ) ";
      TextTitle tt = new TextTitle("y = " + logarithmicEquation + "\nR² = " + String.format("%.2f", r2));
      tt.setTextAlignment(HorizontalAlignment.RIGHT);
      tt.setFont(chart.getLegend().getItemFont());
      tt.setBackgroundPaint(new Color(200, 200, 255, 100));
      tt.setFrame(new BlockBorder(Color.white));
      tt.setPosition(RectangleEdge.BOTTOM);
      XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, tt, RectangleAnchor.BOTTOM_RIGHT);
      r2Annotation.setMaxWidth(0.48D);
      plot.addAnnotation(r2Annotation);
   }

   private void computeExponentialCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset, int filterOrder) {
      Function2D retVal = null;
      double r2 = 0.0D;
      double[] coefficients = null;

      try {
         coefficients = RegressionLE_.getExponentialRegression(dataset, 0);
         if (coefficients[2] > r2) {
            new LogarithmicFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
         }
      } catch (Exception var12) {
         System.err.println(var12.getMessage());
      }

      String exponentialEquation = "y = " + String.format("%.2f", coefficients[0]) + " * " + "( e^(" + String.format("%.2f", coefficients[1]) + " * x) ) ";
      TextTitle tt = new TextTitle("y = " + exponentialEquation + "\nR² = " + String.format("%.2f", r2));
      tt.setTextAlignment(HorizontalAlignment.RIGHT);
      tt.setFont(chart.getLegend().getItemFont());
      tt.setBackgroundPaint(new Color(200, 200, 255, 100));
      tt.setFrame(new BlockBorder(Color.white));
      tt.setPosition(RectangleEdge.BOTTOM);
      XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, tt, RectangleAnchor.BOTTOM_RIGHT);
      r2Annotation.setMaxWidth(0.48D);
      plot.addAnnotation(r2Annotation);
   }
}
