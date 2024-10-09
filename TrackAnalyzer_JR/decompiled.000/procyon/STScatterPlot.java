import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.title.Title;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.function.PowerFunction2D;
import java.awt.Shape;
import org.jfree.data.function.Function2D;
import org.jfree.chart.axis.NumberAxis;
import java.awt.Stroke;
import java.awt.BasicStroke;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.statistics.Regression;
import java.util.Collection;
import java.util.Collections;
import org.jfree.chart.JFreeChart;
import java.awt.Font;
import org.jfree.chart.plot.Marker;
import java.awt.Dimension;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import java.awt.Paint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.Color;
import org.jfree.chart.plot.IntervalMarker;
import java.util.List;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class STScatterPlot extends ApplicationFrame
{
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
    
    public STScatterPlot(final String title) {
        super(title);
    }
    
    public ChartPanel createChartPanelPolynomial() {
        return null;
    }
    
    public ChartPanel createScatterChartPanelInitial(final String label1, final String label2, final List<Double> valuesDomain, final List<Double> valuesRange, final IntervalMarker markerRange, final IntervalMarker markerDomain, final Object[][] dataCh2, final Double[][] dataCh3) {
        this.label1 = label1;
        this.label2 = label2;
        this.valuesDomain = valuesDomain;
        this.valuesRange = valuesRange;
        this.markerRange = markerRange;
        this.markerDomain = markerDomain;
        this.dataset = new XYSeriesCollection();
        final XYSeries series1 = new XYSeries((Comparable)"");
        for (int i = 0; i < valuesDomain.size(); ++i) {
            series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
        }
        this.dataset.addSeries(series1);
        final JFreeChart chart = ChartFactory.createScatterPlot("", "", "", (XYDataset)this.dataset);
        STScatterPlot.plot = (XYPlot)chart.getPlot();
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        STScatterPlot.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        (this.panel = new ChartPanel(chart, false)).setMaximumDrawWidth(4000);
        this.panel.setPreferredSize(new Dimension(450, 300));
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setVisible(false);
        this.panel.setMouseWheelEnabled(true);
        STScatterPlot.plot.addRangeMarker((Marker)markerRange);
        STScatterPlot.plot.addDomainMarker((Marker)markerDomain);
        final Font font3 = new Font("Dialog", 2, 9);
        STScatterPlot.plot.getDomainAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setTickLabelFont(font3);
        STScatterPlot.plot.getDomainAxis().setTickLabelFont(font3);
        return this.panel;
    }
    
    public void addScatterPlotSeriesLinear(final String domainName, final String rangeName, final List<Double> valuesDomain, final List<Double> valuesRange, final IntervalMarker markerRange, final IntervalMarker markerDomain, final Object[][] data, final Color[] classColor) {
        this.valuesDomain = valuesDomain;
        this.rangeName = rangeName;
        this.domainName = domainName;
        this.valuesRange = valuesRange;
        this.markerRange = markerRange;
        this.markerDomain = markerDomain;
        this.classColor = classColor;
        this.panel.removeAll();
        this.dataset = new XYSeriesCollection();
        this.series1 = new XYSeries((Comparable)"");
        for (int i = 0; i < valuesDomain.size(); ++i) {
            this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
        }
        this.dataset.addSeries(this.series1);
        final double minDomain = Collections.<Double>min((Collection<? extends Double>)valuesDomain);
        final double maxDomain = Collections.<Double>max((Collection<? extends Double>)valuesDomain);
        final double[] coefficients = Regression.getOLSRegression((XYDataset)this.dataset, 0);
        final Function2D curve = (Function2D)new LineFunction2D(coefficients[0], coefficients[1]);
        final XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), (Comparable)"Fitted Regression Line");
        final JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, false);
        STScatterPlot.plot = (XYPlot)chart.getPlot();
        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
        STScatterPlot.plot.setRenderer((XYItemRenderer)renderer1);
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 228, 196));
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        STScatterPlot.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        STScatterPlot.plot.setDataset(1, regressionData);
        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        renderer2.setSeriesPaint(0, (Paint)Color.RED);
        STScatterPlot.plot.setRenderer(1, (XYItemRenderer)renderer2);
        final Shape cross = new Ellipse2D.Double(0.0, 0.0, 5.0, 5.0);
        final List<String[]> featureLists = new ArrayList<String[]>();
        for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); ++j) {
            featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
        }
        this.renderer = new XYLineAndShapeRenderer() {
            public Paint getItemPaint(final int row, final int col) {
                Paint cpaint = this.getItemColor(row, col);
                if (cpaint == null) {
                    cpaint = super.getItemPaint(row, col);
                }
                return cpaint;
            }
            
            public Color getItemColor(final int row, final int col) {
                final double x = STScatterPlot.this.dataset.getXValue(row, col);
                final double y = STScatterPlot.this.dataset.getYValue(row, col);
                try {
                    return classColor[col];
                }
                catch (Exception ex) {
                    return null;
                }
            }
        };
        STScatterPlot.plot.setRenderer((XYItemRenderer)this.renderer);
        this.renderer.setUseOutlinePaint(true);
        this.renderer.setSeriesShape(0, cross);
        this.renderer.setSeriesOutlinePaint(0, (Paint)Color.black);
        this.renderer.setSeriesOutlineStroke(0, (Stroke)new BasicStroke(1.0f));
        this.renderer.setSeriesLinesVisible(0, false);
        this.renderer.setSeriesPaint(0, (Paint)Color.LIGHT_GRAY);
        STScatterPlot.plot.getRangeCrosshairValue();
        STScatterPlot.plot.getDomainCrosshairValue();
        this.panel.setChart(chart);
        this.panel.setMaximumDrawWidth(6000);
        this.panel.setPreferredSize(new Dimension(450, 300));
        this.panel.setMouseWheelEnabled(true);
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.panel.setMouseWheelEnabled(true);
        chart.getLegend().setVisible(true);
        STScatterPlot.plot.addRangeMarker((Marker)markerRange);
        STScatterPlot.plot.addDomainMarker((Marker)markerDomain);
        markerRange.setLabel("Low-High");
        markerRange.setLabelFont(new Font("SansSerif", 0, 15));
        markerRange.setLabelPaint((Paint)new Color(0, 102, 0));
        final Font font3 = new Font("Dialog", 1, 12);
        STScatterPlot.plot.getDomainAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setTickLabelFont(font3);
        STScatterPlot.plot.getDomainAxis().setTickLabelFont(font3);
        final NumberAxis domain = (NumberAxis)STScatterPlot.plot.getDomainAxis();
        final NumberAxis range = (NumberAxis)STScatterPlot.plot.getRangeAxis();
        domain.setAutoRange(true);
        range.setAutoRange(true);
        this.computeLinearCoefficients(chart, STScatterPlot.plot, this.dataset);
    }
    
    public void addScatterPlotSeriesPower(final String domainName, final String rangeName, final List<Double> valuesDomain, final List<Double> valuesRange, final IntervalMarker markerRange, final IntervalMarker markerDomain, final Object[][] data, final Color[] classColor) {
        this.valuesDomain = valuesDomain;
        this.rangeName = rangeName;
        this.domainName = domainName;
        this.valuesRange = valuesRange;
        this.markerRange = markerRange;
        this.markerDomain = markerDomain;
        this.classColor = classColor;
        this.panel.removeAll();
        this.dataset = new XYSeriesCollection();
        this.series1 = new XYSeries((Comparable)"");
        for (int i = 0; i < valuesDomain.size(); ++i) {
            this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
        }
        this.dataset.addSeries(this.series1);
        final double minDomain = Collections.<Double>min((Collection<? extends Double>)valuesDomain);
        final double maxDomain = Collections.<Double>max((Collection<? extends Double>)valuesDomain);
        final double[] coefficients = Regression.getPowerRegression((XYDataset)this.dataset, 0);
        final Function2D curve = (Function2D)new PowerFunction2D(coefficients[0], coefficients[1]);
        final XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), (Comparable)"Fitted Regression Line");
        final JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, false);
        STScatterPlot.plot = (XYPlot)chart.getPlot();
        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
        STScatterPlot.plot.setRenderer((XYItemRenderer)renderer1);
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 228, 196));
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        STScatterPlot.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        STScatterPlot.plot.setDataset(1, regressionData);
        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        renderer2.setSeriesPaint(0, (Paint)Color.RED);
        STScatterPlot.plot.setRenderer(1, (XYItemRenderer)renderer2);
        final Shape cross = new Ellipse2D.Double(0.0, 0.0, 5.0, 5.0);
        final List<String[]> featureLists = new ArrayList<String[]>();
        for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); ++j) {
            featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
        }
        this.renderer = new XYLineAndShapeRenderer() {
            public Paint getItemPaint(final int row, final int col) {
                Paint cpaint = this.getItemColor(row, col);
                if (cpaint == null) {
                    cpaint = super.getItemPaint(row, col);
                }
                return cpaint;
            }
            
            public Color getItemColor(final int row, final int col) {
                final double x = STScatterPlot.this.dataset.getXValue(row, col);
                final double y = STScatterPlot.this.dataset.getYValue(row, col);
                try {
                    return classColor[col];
                }
                catch (Exception ex) {
                    return null;
                }
            }
        };
        STScatterPlot.plot.setRenderer((XYItemRenderer)this.renderer);
        this.renderer.setUseOutlinePaint(true);
        this.renderer.setSeriesShape(0, cross);
        this.renderer.setSeriesOutlinePaint(0, (Paint)Color.black);
        this.renderer.setSeriesOutlineStroke(0, (Stroke)new BasicStroke(1.0f));
        this.renderer.setSeriesLinesVisible(0, false);
        this.renderer.setSeriesPaint(0, (Paint)Color.LIGHT_GRAY);
        STScatterPlot.plot.getRangeCrosshairValue();
        STScatterPlot.plot.getDomainCrosshairValue();
        this.panel.setChart(chart);
        this.panel.setMaximumDrawWidth(6000);
        this.panel.setPreferredSize(new Dimension(450, 300));
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.panel.setMouseWheelEnabled(true);
        chart.getLegend().setVisible(true);
        STScatterPlot.plot.addRangeMarker((Marker)markerRange);
        STScatterPlot.plot.addDomainMarker((Marker)markerDomain);
        markerRange.setLabel("Low-High");
        markerRange.setLabelFont(new Font("SansSerif", 0, 15));
        markerRange.setLabelPaint((Paint)new Color(0, 102, 0));
        final Font font3 = new Font("Dialog", 1, 12);
        STScatterPlot.plot.getDomainAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setTickLabelFont(font3);
        STScatterPlot.plot.getDomainAxis().setTickLabelFont(font3);
        final NumberAxis domain = (NumberAxis)STScatterPlot.plot.getDomainAxis();
        final NumberAxis range = (NumberAxis)STScatterPlot.plot.getRangeAxis();
        domain.setAutoRange(true);
        range.setAutoRange(true);
        this.computePowerCoefficients(chart, STScatterPlot.plot, this.dataset);
    }
    
    public void addScatterPlotSeriesPolynomial(final String domainName, final String rangeName, final List<Double> valuesDomain, final List<Double> valuesRange, final IntervalMarker markerRange, final IntervalMarker markerDomain, final Object[][] data, final Color[] classColor, final int filterOrder) {
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
        this.series1 = new XYSeries((Comparable)"");
        for (int i = 0; i < valuesDomain.size(); ++i) {
            this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
        }
        this.dataset.addSeries(this.series1);
        final double minDomain = Collections.<Double>min((Collection<? extends Double>)valuesDomain);
        final double maxDomain = Collections.<Double>max((Collection<? extends Double>)valuesDomain);
        final double[] coefficients = Regression.getPolynomialRegression((XYDataset)this.dataset, 0, filterOrder);
        final Function2D curve = (Function2D)new PowerFunction2D(coefficients[0], coefficients[1]);
        final XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), (Comparable)"Fitted Regression Line");
        final JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, false);
        STScatterPlot.plot = (XYPlot)chart.getPlot();
        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
        STScatterPlot.plot.setRenderer((XYItemRenderer)renderer1);
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 228, 196));
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        STScatterPlot.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        STScatterPlot.plot.setDataset(1, regressionData);
        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        renderer2.setSeriesPaint(0, (Paint)Color.RED);
        STScatterPlot.plot.setRenderer(1, (XYItemRenderer)renderer2);
        final Shape cross = new Ellipse2D.Double(0.0, 0.0, 5.0, 5.0);
        final List<String[]> featureLists = new ArrayList<String[]>();
        for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); ++j) {
            featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
        }
        this.renderer = new XYLineAndShapeRenderer() {
            public Paint getItemPaint(final int row, final int col) {
                Paint cpaint = this.getItemColor(row, col);
                if (cpaint == null) {
                    cpaint = super.getItemPaint(row, col);
                }
                return cpaint;
            }
            
            public Color getItemColor(final int row, final int col) {
                final double x = STScatterPlot.this.dataset.getXValue(row, col);
                final double y = STScatterPlot.this.dataset.getYValue(row, col);
                try {
                    return classColor[col];
                }
                catch (Exception ex) {
                    return null;
                }
            }
        };
        STScatterPlot.plot.setRenderer((XYItemRenderer)this.renderer);
        this.renderer.setUseOutlinePaint(true);
        this.renderer.setSeriesShape(0, cross);
        this.renderer.setSeriesOutlinePaint(0, (Paint)Color.black);
        this.renderer.setSeriesOutlineStroke(0, (Stroke)new BasicStroke(1.0f));
        this.renderer.setSeriesLinesVisible(0, false);
        this.renderer.setSeriesPaint(0, (Paint)Color.LIGHT_GRAY);
        STScatterPlot.plot.getRangeCrosshairValue();
        STScatterPlot.plot.getDomainCrosshairValue();
        this.panel.setChart(chart);
        this.panel.setMaximumDrawWidth(6000);
        this.panel.setPreferredSize(new Dimension(450, 300));
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.panel.setMouseWheelEnabled(true);
        chart.getLegend().setVisible(true);
        STScatterPlot.plot.addRangeMarker((Marker)markerRange);
        STScatterPlot.plot.addDomainMarker((Marker)markerDomain);
        markerRange.setLabel("Low-High");
        markerRange.setLabelFont(new Font("SansSerif", 0, 15));
        markerRange.setLabelPaint((Paint)new Color(0, 102, 0));
        final Font font3 = new Font("Dialog", 1, 12);
        STScatterPlot.plot.getDomainAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setTickLabelFont(font3);
        STScatterPlot.plot.getDomainAxis().setTickLabelFont(font3);
        final NumberAxis domain = (NumberAxis)STScatterPlot.plot.getDomainAxis();
        final NumberAxis range = (NumberAxis)STScatterPlot.plot.getRangeAxis();
        domain.setAutoRange(true);
        range.setAutoRange(true);
        this.computePolynomialCoefficients(chart, STScatterPlot.plot, this.dataset, filterOrder);
    }
    
    public void addScatterPlotSeriesLogarithmic(final String domainName, final String rangeName, final List<Double> valuesDomain, final List<Double> valuesRange, final IntervalMarker markerRange, final IntervalMarker markerDomain, final Object[][] data, final Color[] classColor) {
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
        this.series1 = new XYSeries((Comparable)"");
        for (int i = 0; i < valuesDomain.size(); ++i) {
            this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
        }
        this.dataset.addSeries(this.series1);
        final double minDomain = Collections.<Double>min((Collection<? extends Double>)valuesDomain);
        final double maxDomain = Collections.<Double>max((Collection<? extends Double>)valuesDomain);
        final double[] coefficients = RegressionLE_.getLogarithmicRegression((XYDataset)this.dataset, 0);
        final Function2D curve = (Function2D)new LogarithmicFunction2D(coefficients[0], coefficients[1]);
        final XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), (Comparable)"Fitted Regression Line");
        final JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, false);
        STScatterPlot.plot = (XYPlot)chart.getPlot();
        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
        STScatterPlot.plot.setRenderer((XYItemRenderer)renderer1);
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 228, 196));
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        STScatterPlot.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        STScatterPlot.plot.setDataset(1, regressionData);
        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        renderer2.setSeriesPaint(0, (Paint)Color.RED);
        STScatterPlot.plot.setRenderer(1, (XYItemRenderer)renderer2);
        final Shape cross = new Ellipse2D.Double(0.0, 0.0, 5.0, 5.0);
        final List<String[]> featureLists = new ArrayList<String[]>();
        for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); ++j) {
            featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
        }
        this.renderer = new XYLineAndShapeRenderer() {
            public Paint getItemPaint(final int row, final int col) {
                Paint cpaint = this.getItemColor(row, col);
                if (cpaint == null) {
                    cpaint = super.getItemPaint(row, col);
                }
                return cpaint;
            }
            
            public Color getItemColor(final int row, final int col) {
                final double x = STScatterPlot.this.dataset.getXValue(row, col);
                final double y = STScatterPlot.this.dataset.getYValue(row, col);
                try {
                    return classColor[col];
                }
                catch (Exception ex) {
                    return null;
                }
            }
        };
        STScatterPlot.plot.setRenderer((XYItemRenderer)this.renderer);
        this.renderer.setUseOutlinePaint(true);
        this.renderer.setSeriesShape(0, cross);
        this.renderer.setSeriesOutlinePaint(0, (Paint)Color.black);
        this.renderer.setSeriesOutlineStroke(0, (Stroke)new BasicStroke(1.0f));
        this.renderer.setSeriesLinesVisible(0, false);
        this.renderer.setSeriesPaint(0, (Paint)Color.LIGHT_GRAY);
        STScatterPlot.plot.getRangeCrosshairValue();
        STScatterPlot.plot.getDomainCrosshairValue();
        this.panel.setChart(chart);
        this.panel.setMaximumDrawWidth(6000);
        this.panel.setPreferredSize(new Dimension(450, 300));
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.panel.setMouseWheelEnabled(true);
        chart.getLegend().setVisible(true);
        STScatterPlot.plot.addRangeMarker((Marker)markerRange);
        STScatterPlot.plot.addDomainMarker((Marker)markerDomain);
        markerRange.setLabel("Low-High");
        markerRange.setLabelFont(new Font("SansSerif", 0, 15));
        markerRange.setLabelPaint((Paint)new Color(0, 102, 0));
        final Font font3 = new Font("Dialog", 1, 12);
        STScatterPlot.plot.getDomainAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setTickLabelFont(font3);
        STScatterPlot.plot.getDomainAxis().setTickLabelFont(font3);
        final NumberAxis domain = (NumberAxis)STScatterPlot.plot.getDomainAxis();
        final NumberAxis range = (NumberAxis)STScatterPlot.plot.getRangeAxis();
        domain.setAutoRange(true);
        range.setAutoRange(true);
        this.computeLogarithmicCoefficients(chart, STScatterPlot.plot, this.dataset, this.filterOrder);
    }
    
    public void addScatterPlotSeriesExponential(final String domainName, final String rangeName, final List<Double> valuesDomain, final List<Double> valuesRange, final IntervalMarker markerRange, final IntervalMarker markerDomain, final Object[][] data, final Color[] classColor) {
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
        this.series1 = new XYSeries((Comparable)"");
        for (int i = 0; i < valuesDomain.size(); ++i) {
            this.series1.add((Number)valuesDomain.get(i), (Number)valuesRange.get(i));
        }
        this.dataset.addSeries(this.series1);
        final double minDomain = Collections.<Double>min((Collection<? extends Double>)valuesDomain);
        final double maxDomain = Collections.<Double>max((Collection<? extends Double>)valuesDomain);
        final double[] coefficients = RegressionLE_.getExponentialRegression((XYDataset)this.dataset, 0);
        final Function2D curve = (Function2D)new ExponentialFunction2D(coefficients[0], coefficients[1]);
        final XYDataset regressionData = DatasetUtils.sampleFunction2D(curve, minDomain, maxDomain, valuesDomain.size(), (Comparable)"Fitted Regression Line");
        final JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, false);
        STScatterPlot.plot = (XYPlot)chart.getPlot();
        final XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
        STScatterPlot.plot.setRenderer((XYItemRenderer)renderer1);
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 228, 196));
        final Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
        STScatterPlot.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
        STScatterPlot.plot.setDataset(1, regressionData);
        final XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
        renderer2.setSeriesPaint(0, (Paint)Color.RED);
        STScatterPlot.plot.setRenderer(1, (XYItemRenderer)renderer2);
        final Shape cross = new Ellipse2D.Double(0.0, 0.0, 5.0, 5.0);
        final List<String[]> featureLists = new ArrayList<String[]>();
        for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); ++j) {
            featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "").split("<br>"));
        }
        this.renderer = new XYLineAndShapeRenderer() {
            public Paint getItemPaint(final int row, final int col) {
                Paint cpaint = this.getItemColor(row, col);
                if (cpaint == null) {
                    cpaint = super.getItemPaint(row, col);
                }
                return cpaint;
            }
            
            public Color getItemColor(final int row, final int col) {
                final double x = STScatterPlot.this.dataset.getXValue(row, col);
                final double y = STScatterPlot.this.dataset.getYValue(row, col);
                try {
                    return classColor[col];
                }
                catch (Exception ex) {
                    return null;
                }
            }
        };
        STScatterPlot.plot.setRenderer((XYItemRenderer)this.renderer);
        this.renderer.setUseOutlinePaint(true);
        this.renderer.setSeriesShape(0, cross);
        this.renderer.setSeriesOutlinePaint(0, (Paint)Color.black);
        this.renderer.setSeriesOutlineStroke(0, (Stroke)new BasicStroke(1.0f));
        this.renderer.setSeriesLinesVisible(0, false);
        this.renderer.setSeriesPaint(0, (Paint)Color.LIGHT_GRAY);
        STScatterPlot.plot.getRangeCrosshairValue();
        STScatterPlot.plot.getDomainCrosshairValue();
        this.panel.setChart(chart);
        this.panel.setMaximumDrawWidth(6000);
        this.panel.setPreferredSize(new Dimension(450, 300));
        chart.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        STScatterPlot.plot.setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        chart.getLegend().setBackgroundPaint((Paint)new Color(255, 255, 255, 0));
        this.panel.setMouseWheelEnabled(true);
        chart.getLegend().setVisible(true);
        STScatterPlot.plot.addRangeMarker((Marker)markerRange);
        STScatterPlot.plot.addDomainMarker((Marker)markerDomain);
        markerRange.setLabel("Low-High");
        markerRange.setLabelFont(new Font("SansSerif", 0, 15));
        markerRange.setLabelPaint((Paint)new Color(0, 102, 0));
        final Font font3 = new Font("Dialog", 1, 12);
        STScatterPlot.plot.getDomainAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setLabelFont(font3);
        STScatterPlot.plot.getRangeAxis().setTickLabelFont(font3);
        STScatterPlot.plot.getDomainAxis().setTickLabelFont(font3);
        final NumberAxis domain = (NumberAxis)STScatterPlot.plot.getDomainAxis();
        final NumberAxis range = (NumberAxis)STScatterPlot.plot.getRangeAxis();
        domain.setAutoRange(true);
        range.setAutoRange(true);
        this.computeExponentialCoefficients(chart, STScatterPlot.plot, this.dataset, this.filterOrder);
    }
    
    private void computeLinearCoefficients(final JFreeChart chart, final XYPlot plot, final XYSeriesCollection dataset) {
        Function2D retVal = null;
        double r2 = 0.0;
        double[] coefficients = null;
        try {
            coefficients = RegressionLE_.getOLSRegression((XYDataset)dataset, 0);
            retVal = (Function2D)new LineFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        final double intercept = coefficients[0];
        final double slope = coefficients[1];
        String linearEquation;
        if (intercept >= 0.0) {
            linearEquation = "y = " + String.format("%.2f", slope) + "x + " + String.format("%.2f", intercept);
        }
        else {
            linearEquation = "y = " + String.format("%.2f", slope) + "x - " + Math.abs(intercept);
        }
        final TextTitle tt = new TextTitle(String.valueOf(linearEquation) + "\nR² = " + String.format("%.2f", r2));
        tt.setTextAlignment(HorizontalAlignment.RIGHT);
        tt.setFont(chart.getLegend().getItemFont());
        tt.setBackgroundPaint((Paint)new Color(200, 200, 255, 100));
        tt.setFrame((BlockFrame)new BlockBorder((Paint)Color.white));
        tt.setPosition(RectangleEdge.BOTTOM);
        final XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98, 0.02, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
        r2Annotation.setMaxWidth(0.48);
        plot.addAnnotation((XYAnnotation)r2Annotation);
    }
    
    private void computePowerCoefficients(final JFreeChart chart, final XYPlot plot, final XYSeriesCollection dataset) {
        Function2D retVal = null;
        double r2 = 0.0;
        double[] coefficients = null;
        try {
            coefficients = RegressionLE_.getPowerRegression((XYDataset)dataset, 0);
            if (coefficients[2] > r2) {
                retVal = (Function2D)new PowerFunction2D(coefficients[0], coefficients[1]);
                r2 = coefficients[2];
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        final double intercept = coefficients[0];
        final double slope = coefficients[1];
        String linearEquation;
        if (intercept >= 0.0) {
            linearEquation = "y = " + String.format("%.2f", slope) + "x^ " + String.format("%.2f", intercept);
        }
        else {
            linearEquation = "y = " + String.format("%.2f", slope) + "x^ -" + Math.abs(intercept);
        }
        final TextTitle tt = new TextTitle(String.valueOf(linearEquation) + "\nR² = " + String.format("%.2f", r2));
        tt.setTextAlignment(HorizontalAlignment.RIGHT);
        tt.setFont(chart.getLegend().getItemFont());
        tt.setBackgroundPaint((Paint)new Color(200, 200, 255, 100));
        tt.setFrame((BlockFrame)new BlockBorder((Paint)Color.white));
        tt.setPosition(RectangleEdge.BOTTOM);
        final XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98, 0.02, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
        r2Annotation.setMaxWidth(0.48);
        plot.addAnnotation((XYAnnotation)r2Annotation);
    }
    
    private void computePolynomialCoefficients(final JFreeChart chart, final XYPlot plot, final XYSeriesCollection dataset, final int filterOrder) {
        final double[] coefficients = Regression.getPolynomialRegression((XYDataset)dataset, 0, filterOrder);
        final double r2 = coefficients[coefficients.length - 1];
        String polynomialEquation = "";
        for (int i = coefficients.length - 1; i >= 0; --i) {
            if (i == 0) {
                polynomialEquation = String.valueOf(polynomialEquation) + String.format("%.2f", coefficients[i]);
            }
            else if (i == 1) {
                polynomialEquation = String.valueOf(polynomialEquation) + String.format("%.2f", coefficients[i]) + "*x+";
            }
            else if (i > 1) {
                polynomialEquation = String.valueOf(polynomialEquation) + String.format("%.2f", coefficients[i]) + "*x^" + i + "+";
            }
        }
        final TextTitle tt = new TextTitle("y = " + polynomialEquation + "\nR² = " + String.format("%.2f", r2));
        tt.setTextAlignment(HorizontalAlignment.RIGHT);
        tt.setFont(chart.getLegend().getItemFont());
        tt.setBackgroundPaint((Paint)new Color(200, 200, 255, 100));
        tt.setFrame((BlockFrame)new BlockBorder((Paint)Color.white));
        tt.setPosition(RectangleEdge.BOTTOM);
        final XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98, 0.02, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
        r2Annotation.setMaxWidth(0.48);
        plot.addAnnotation((XYAnnotation)r2Annotation);
    }
    
    private void computeLogarithmicCoefficients(final JFreeChart chart, final XYPlot plot, final XYSeriesCollection dataset, final int filterOrder) {
        Function2D retVal = null;
        double r2 = 0.0;
        double[] coefficients = null;
        try {
            coefficients = RegressionLE_.getLogarithmicRegression((XYDataset)dataset, 0);
            if (coefficients[2] > r2) {
                retVal = (Function2D)new LogarithmicFunction2D(coefficients[0], coefficients[1]);
                r2 = coefficients[2];
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        final String logarithmicEquation = "y = " + String.format("%.2f", coefficients[0]) + " + " + "( " + String.format("%.2f", coefficients[1]) + " * ln(x) ) ";
        final TextTitle tt = new TextTitle("y = " + logarithmicEquation + "\nR² = " + String.format("%.2f", r2));
        tt.setTextAlignment(HorizontalAlignment.RIGHT);
        tt.setFont(chart.getLegend().getItemFont());
        tt.setBackgroundPaint((Paint)new Color(200, 200, 255, 100));
        tt.setFrame((BlockFrame)new BlockBorder((Paint)Color.white));
        tt.setPosition(RectangleEdge.BOTTOM);
        final XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98, 0.02, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
        r2Annotation.setMaxWidth(0.48);
        plot.addAnnotation((XYAnnotation)r2Annotation);
    }
    
    private void computeExponentialCoefficients(final JFreeChart chart, final XYPlot plot, final XYSeriesCollection dataset, final int filterOrder) {
        Function2D retVal = null;
        double r2 = 0.0;
        double[] coefficients = null;
        try {
            coefficients = RegressionLE_.getExponentialRegression((XYDataset)dataset, 0);
            if (coefficients[2] > r2) {
                retVal = (Function2D)new LogarithmicFunction2D(coefficients[0], coefficients[1]);
                r2 = coefficients[2];
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        final String exponentialEquation = "y = " + String.format("%.2f", coefficients[0]) + " * " + "( e^(" + String.format("%.2f", coefficients[1]) + " * x) ) ";
        final TextTitle tt = new TextTitle("y = " + exponentialEquation + "\nR² = " + String.format("%.2f", r2));
        tt.setTextAlignment(HorizontalAlignment.RIGHT);
        tt.setFont(chart.getLegend().getItemFont());
        tt.setBackgroundPaint((Paint)new Color(200, 200, 255, 100));
        tt.setFrame((BlockFrame)new BlockBorder((Paint)Color.white));
        tt.setPosition(RectangleEdge.BOTTOM);
        final XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98, 0.02, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
        r2Annotation.setMaxWidth(0.48);
        plot.addAnnotation((XYAnnotation)r2Annotation);
    }
}
