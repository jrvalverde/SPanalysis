/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.JLabel;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.annotations.XYAnnotation;
/*     */ import org.jfree.chart.annotations.XYTitleAnnotation;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.block.BlockBorder;
/*     */ import org.jfree.chart.block.BlockFrame;
/*     */ import org.jfree.chart.plot.DefaultDrawingSupplier;
/*     */ import org.jfree.chart.plot.DrawingSupplier;
/*     */ import org.jfree.chart.plot.IntervalMarker;
/*     */ import org.jfree.chart.plot.Marker;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.chart.title.Title;
/*     */ import org.jfree.chart.ui.HorizontalAlignment;
/*     */ import org.jfree.chart.ui.RectangleAnchor;
/*     */ import org.jfree.chart.ui.RectangleEdge;
/*     */ import org.jfree.data.function.Function2D;
/*     */ import org.jfree.data.function.LineFunction2D;
/*     */ import org.jfree.data.function.PowerFunction2D;
/*     */ import org.jfree.data.general.DatasetUtils;
/*     */ import org.jfree.data.statistics.Regression;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import org.jfree.data.xy.XYSeriesCollection;
/*     */ import org.jfree.ui.ApplicationFrame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class STScatterPlot
/*     */   extends ApplicationFrame
/*     */ {
/*     */   private XYDataset data1;
/*     */   List<Double> valuesDomain;
/*     */   List<Double> valuesRange;
/*     */   IntervalMarker markerRange;
/*     */   IntervalMarker markerDomain;
/*     */   String domainName;
/*     */   String rangeName;
/*     */   Object[][] data;
/*     */   Color[] classColor;
/*     */   String label1;
/*     */   String label2;
/*     */   XYSeriesCollection dataset;
/*     */   static XYPlot plot;
/*     */   ChartPanel panel;
/*     */   XYSeries series1;
/*     */   XYLineAndShapeRenderer renderer;
/*     */   double maxDomain;
/*     */   int filterOrder;
/*     */   
/*     */   public STScatterPlot(String title) {
/*  71 */     super(title);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChartPanel createChartPanelPolynomial() {
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChartPanel createScatterChartPanelInitial(String label1, String label2, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] dataCh2, Double[][] dataCh3) {
/*  88 */     this.label1 = label1;
/*  89 */     this.label2 = label2;
/*  90 */     this.valuesDomain = valuesDomain;
/*  91 */     this.valuesRange = valuesRange;
/*  92 */     this.markerRange = markerRange;
/*  93 */     this.markerDomain = markerDomain;
/*     */     
/*  95 */     this.dataset = new XYSeriesCollection();
/*  96 */     XYSeries series1 = new XYSeries("");
/*  97 */     for (int i = 0; i < valuesDomain.size(); i++)
/*  98 */       series1.add(valuesDomain.get(i), valuesRange.get(i)); 
/*  99 */     this.dataset.addSeries(series1);
/*     */ 
/*     */     
/* 102 */     JFreeChart chart = ChartFactory.createScatterPlot("", "", "", (XYDataset)this.dataset);
/*     */ 
/*     */     
/* 105 */     plot = (XYPlot)chart.getPlot();
/* 106 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/* 107 */     plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/* 108 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/* 109 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/* 110 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/* 111 */     this.panel = new ChartPanel(chart, false);
/* 112 */     this.panel.setMaximumDrawWidth(4000);
/* 113 */     this.panel.setPreferredSize(new Dimension(450, 300));
/* 114 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 115 */     plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 116 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/* 117 */     chart.getLegend().setVisible(false);
/* 118 */     this.panel.setMouseWheelEnabled(true);
/* 119 */     plot.addRangeMarker((Marker)markerRange);
/* 120 */     plot.addDomainMarker((Marker)markerDomain);
/*     */     
/* 122 */     Font font3 = new Font("Dialog", 2, 9);
/* 123 */     plot.getDomainAxis().setLabelFont(font3);
/* 124 */     plot.getRangeAxis().setLabelFont(font3);
/* 125 */     plot.getRangeAxis().setTickLabelFont(font3);
/* 126 */     plot.getDomainAxis().setTickLabelFont(font3);
/* 127 */     return this.panel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addScatterPlotSeriesLinear(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
/* 134 */     this.valuesDomain = valuesDomain;
/* 135 */     this.rangeName = rangeName;
/* 136 */     this.domainName = domainName;
/* 137 */     this.valuesRange = valuesRange;
/* 138 */     this.markerRange = markerRange;
/* 139 */     this.markerDomain = markerDomain;
/*     */ 
/*     */     
/* 142 */     this.classColor = classColor;
/*     */     
/* 144 */     this.panel.removeAll();
/* 145 */     this.dataset = new XYSeriesCollection();
/* 146 */     this.series1 = new XYSeries("");
/* 147 */     for (int i = 0; i < valuesDomain.size(); i++)
/* 148 */       this.series1.add(valuesDomain.get(i), valuesRange.get(i)); 
/* 149 */     this.dataset.addSeries(this.series1);
/* 150 */     double minDomain = ((Double)Collections.<Double>min(valuesDomain)).doubleValue();
/* 151 */     double maxDomain = ((Double)Collections.<Double>max(valuesDomain)).doubleValue();
/* 152 */     double[] coefficients = Regression.getOLSRegression((XYDataset)this.dataset, 0);
/* 153 */     LineFunction2D lineFunction2D = new LineFunction2D(coefficients[0], coefficients[1]);
/* 154 */     XYDataset regressionData = DatasetUtils.sampleFunction2D((Function2D)lineFunction2D, minDomain, maxDomain, valuesDomain.size(), 
/* 155 */         "Fitted Regression Line");
/*     */     
/* 157 */     JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, 
/* 158 */         true, true, false);
/*     */     
/* 160 */     plot = (XYPlot)chart.getPlot();
/* 161 */     XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
/* 162 */     plot.setRenderer((XYItemRenderer)renderer1);
/* 163 */     plot.setBackgroundPaint(new Color(255, 228, 196));
/* 164 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/* 165 */     plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/* 166 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/* 167 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/* 168 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/* 169 */     plot.setDataset(1, regressionData);
/* 170 */     XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
/* 171 */     renderer2.setSeriesPaint(0, Color.RED);
/* 172 */     plot.setRenderer(1, (XYItemRenderer)renderer2);
/* 173 */     Shape cross = new Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
/* 174 */     List<String[]> featureLists = (List)new ArrayList<>();
/* 175 */     for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); j++) {
/* 176 */       featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, 
/* 177 */             ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "")
/* 178 */           .split("<br>"));
/*     */     }
/* 180 */     this.renderer = new XYLineAndShapeRenderer()
/*     */       {
/*     */         public Paint getItemPaint(int row, int col) {
/* 183 */           Paint cpaint = getItemColor(row, col);
/* 184 */           if (cpaint == null) {
/* 185 */             cpaint = super.getItemPaint(row, col);
/*     */           }
/* 187 */           return cpaint;
/*     */         }
/*     */         
/*     */         public Color getItemColor(int row, int col) {
/* 191 */           double x = STScatterPlot.this.dataset.getXValue(row, col);
/* 192 */           double y = STScatterPlot.this.dataset.getYValue(row, col);
/*     */           
/*     */           try {
/* 195 */             return classColor[col];
/* 196 */           } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */             
/* 200 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 205 */     plot.setRenderer((XYItemRenderer)this.renderer);
/* 206 */     this.renderer.setUseOutlinePaint(true);
/* 207 */     this.renderer.setSeriesShape(0, cross);
/* 208 */     this.renderer.setSeriesOutlinePaint(0, Color.black);
/* 209 */     this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
/* 210 */     this.renderer.setSeriesLinesVisible(0, false);
/* 211 */     this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
/* 212 */     plot.getRangeCrosshairValue();
/* 213 */     plot.getDomainCrosshairValue();
/*     */ 
/*     */     
/* 216 */     this.panel.setChart(chart);
/* 217 */     this.panel.setMaximumDrawWidth(6000);
/* 218 */     this.panel.setPreferredSize(new Dimension(450, 300));
/* 219 */     this.panel.setMouseWheelEnabled(true);
/* 220 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 221 */     plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 222 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/* 223 */     this.panel.setMouseWheelEnabled(true);
/* 224 */     chart.getLegend().setVisible(true);
/* 225 */     plot.addRangeMarker((Marker)markerRange);
/* 226 */     plot.addDomainMarker((Marker)markerDomain);
/* 227 */     markerRange.setLabel("Low-High");
/* 228 */     markerRange.setLabelFont(new Font("SansSerif", 0, 15));
/* 229 */     markerRange.setLabelPaint(new Color(0, 102, 0));
/*     */     
/* 231 */     Font font3 = new Font("Dialog", 1, 12);
/* 232 */     plot.getDomainAxis().setLabelFont(font3);
/* 233 */     plot.getRangeAxis().setLabelFont(font3);
/* 234 */     plot.getRangeAxis().setTickLabelFont(font3);
/* 235 */     plot.getDomainAxis().setTickLabelFont(font3);
/*     */     
/* 237 */     NumberAxis domain = (NumberAxis)plot.getDomainAxis();
/*     */     
/* 239 */     NumberAxis range = (NumberAxis)plot.getRangeAxis();
/*     */     
/* 241 */     domain.setAutoRange(true);
/* 242 */     range.setAutoRange(true);
/* 243 */     computeLinearCoefficients(chart, plot, this.dataset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addScatterPlotSeriesPower(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
/* 251 */     this.valuesDomain = valuesDomain;
/* 252 */     this.rangeName = rangeName;
/* 253 */     this.domainName = domainName;
/* 254 */     this.valuesRange = valuesRange;
/* 255 */     this.markerRange = markerRange;
/* 256 */     this.markerDomain = markerDomain;
/*     */ 
/*     */     
/* 259 */     this.classColor = classColor;
/* 260 */     this.panel.removeAll();
/* 261 */     this.dataset = new XYSeriesCollection();
/* 262 */     this.series1 = new XYSeries("");
/* 263 */     for (int i = 0; i < valuesDomain.size(); i++)
/* 264 */       this.series1.add(valuesDomain.get(i), valuesRange.get(i)); 
/* 265 */     this.dataset.addSeries(this.series1);
/*     */     
/* 267 */     double minDomain = ((Double)Collections.<Double>min(valuesDomain)).doubleValue();
/* 268 */     double maxDomain = ((Double)Collections.<Double>max(valuesDomain)).doubleValue();
/* 269 */     double[] coefficients = Regression.getPowerRegression((XYDataset)this.dataset, 0);
/* 270 */     PowerFunction2D powerFunction2D = new PowerFunction2D(coefficients[0], coefficients[1]);
/* 271 */     XYDataset regressionData = DatasetUtils.sampleFunction2D((Function2D)powerFunction2D, minDomain, maxDomain, valuesDomain.size(), 
/* 272 */         "Fitted Regression Line");
/*     */     
/* 274 */     JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, 
/* 275 */         true, true, false);
/*     */     
/* 277 */     plot = (XYPlot)chart.getPlot();
/* 278 */     XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
/* 279 */     plot.setRenderer((XYItemRenderer)renderer1);
/* 280 */     plot.setBackgroundPaint(new Color(255, 228, 196));
/* 281 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/* 282 */     plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/* 283 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/* 284 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/* 285 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/* 286 */     plot.setDataset(1, regressionData);
/* 287 */     XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
/* 288 */     renderer2.setSeriesPaint(0, Color.RED);
/* 289 */     plot.setRenderer(1, (XYItemRenderer)renderer2);
/* 290 */     Shape cross = new Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
/* 291 */     List<String[]> featureLists = (List)new ArrayList<>();
/* 292 */     for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); j++) {
/* 293 */       featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, 
/* 294 */             ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "")
/* 295 */           .split("<br>"));
/*     */     }
/* 297 */     this.renderer = new XYLineAndShapeRenderer()
/*     */       {
/*     */         public Paint getItemPaint(int row, int col) {
/* 300 */           Paint cpaint = getItemColor(row, col);
/* 301 */           if (cpaint == null) {
/* 302 */             cpaint = super.getItemPaint(row, col);
/*     */           }
/* 304 */           return cpaint;
/*     */         }
/*     */         
/*     */         public Color getItemColor(int row, int col) {
/* 308 */           double x = STScatterPlot.this.dataset.getXValue(row, col);
/* 309 */           double y = STScatterPlot.this.dataset.getYValue(row, col);
/*     */           
/*     */           try {
/* 312 */             return classColor[col];
/* 313 */           } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */             
/* 317 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 322 */     plot.setRenderer((XYItemRenderer)this.renderer);
/* 323 */     this.renderer.setUseOutlinePaint(true);
/* 324 */     this.renderer.setSeriesShape(0, cross);
/* 325 */     this.renderer.setSeriesOutlinePaint(0, Color.black);
/* 326 */     this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
/* 327 */     this.renderer.setSeriesLinesVisible(0, false);
/* 328 */     this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
/* 329 */     plot.getRangeCrosshairValue();
/* 330 */     plot.getDomainCrosshairValue();
/*     */ 
/*     */     
/* 333 */     this.panel.setChart(chart);
/* 334 */     this.panel.setMaximumDrawWidth(6000);
/* 335 */     this.panel.setPreferredSize(new Dimension(450, 300));
/* 336 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 337 */     plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 338 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/* 339 */     this.panel.setMouseWheelEnabled(true);
/* 340 */     chart.getLegend().setVisible(true);
/* 341 */     plot.addRangeMarker((Marker)markerRange);
/* 342 */     plot.addDomainMarker((Marker)markerDomain);
/* 343 */     markerRange.setLabel("Low-High");
/* 344 */     markerRange.setLabelFont(new Font("SansSerif", 0, 15));
/* 345 */     markerRange.setLabelPaint(new Color(0, 102, 0));
/*     */     
/* 347 */     Font font3 = new Font("Dialog", 1, 12);
/* 348 */     plot.getDomainAxis().setLabelFont(font3);
/* 349 */     plot.getRangeAxis().setLabelFont(font3);
/* 350 */     plot.getRangeAxis().setTickLabelFont(font3);
/* 351 */     plot.getDomainAxis().setTickLabelFont(font3);
/*     */     
/* 353 */     NumberAxis domain = (NumberAxis)plot.getDomainAxis();
/*     */     
/* 355 */     NumberAxis range = (NumberAxis)plot.getRangeAxis();
/*     */     
/* 357 */     domain.setAutoRange(true);
/* 358 */     range.setAutoRange(true);
/* 359 */     computePowerCoefficients(chart, plot, this.dataset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addScatterPlotSeriesPolynomial(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor, int filterOrder) {
/* 367 */     this.valuesDomain = valuesDomain;
/* 368 */     this.rangeName = rangeName;
/* 369 */     this.domainName = domainName;
/* 370 */     this.valuesRange = valuesRange;
/* 371 */     this.markerRange = markerRange;
/* 372 */     this.markerDomain = markerDomain;
/*     */ 
/*     */     
/* 375 */     this.classColor = classColor;
/* 376 */     this.filterOrder = filterOrder;
/*     */     
/* 378 */     this.panel.removeAll();
/* 379 */     this.dataset = new XYSeriesCollection();
/* 380 */     this.series1 = new XYSeries("");
/* 381 */     for (int i = 0; i < valuesDomain.size(); i++)
/* 382 */       this.series1.add(valuesDomain.get(i), valuesRange.get(i)); 
/* 383 */     this.dataset.addSeries(this.series1);
/* 384 */     double minDomain = ((Double)Collections.<Double>min(valuesDomain)).doubleValue();
/* 385 */     double maxDomain = ((Double)Collections.<Double>max(valuesDomain)).doubleValue();
/*     */     
/* 387 */     double[] coefficients = Regression.getPolynomialRegression((XYDataset)this.dataset, 0, filterOrder);
/* 388 */     PowerFunction2D powerFunction2D = new PowerFunction2D(coefficients[0], coefficients[1]);
/* 389 */     XYDataset regressionData = DatasetUtils.sampleFunction2D((Function2D)powerFunction2D, minDomain, maxDomain, valuesDomain.size(), 
/* 390 */         "Fitted Regression Line");
/*     */     
/* 392 */     JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, 
/* 393 */         true, true, false);
/*     */     
/* 395 */     plot = (XYPlot)chart.getPlot();
/* 396 */     XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
/* 397 */     plot.setRenderer((XYItemRenderer)renderer1);
/* 398 */     plot.setBackgroundPaint(new Color(255, 228, 196));
/* 399 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/* 400 */     plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/* 401 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/* 402 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/* 403 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/* 404 */     plot.setDataset(1, regressionData);
/* 405 */     XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
/* 406 */     renderer2.setSeriesPaint(0, Color.RED);
/* 407 */     plot.setRenderer(1, (XYItemRenderer)renderer2);
/* 408 */     Shape cross = new Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
/* 409 */     List<String[]> featureLists = (List)new ArrayList<>();
/* 410 */     for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); j++) {
/* 411 */       featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, 
/* 412 */             ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "")
/* 413 */           .split("<br>"));
/*     */     }
/* 415 */     this.renderer = new XYLineAndShapeRenderer()
/*     */       {
/*     */         public Paint getItemPaint(int row, int col) {
/* 418 */           Paint cpaint = getItemColor(row, col);
/* 419 */           if (cpaint == null) {
/* 420 */             cpaint = super.getItemPaint(row, col);
/*     */           }
/* 422 */           return cpaint;
/*     */         }
/*     */         
/*     */         public Color getItemColor(int row, int col) {
/* 426 */           double x = STScatterPlot.this.dataset.getXValue(row, col);
/* 427 */           double y = STScatterPlot.this.dataset.getYValue(row, col);
/*     */           
/*     */           try {
/* 430 */             return classColor[col];
/* 431 */           } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */             
/* 435 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 440 */     plot.setRenderer((XYItemRenderer)this.renderer);
/* 441 */     this.renderer.setUseOutlinePaint(true);
/* 442 */     this.renderer.setSeriesShape(0, cross);
/* 443 */     this.renderer.setSeriesOutlinePaint(0, Color.black);
/* 444 */     this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
/* 445 */     this.renderer.setSeriesLinesVisible(0, false);
/* 446 */     this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
/* 447 */     plot.getRangeCrosshairValue();
/* 448 */     plot.getDomainCrosshairValue();
/*     */ 
/*     */     
/* 451 */     this.panel.setChart(chart);
/* 452 */     this.panel.setMaximumDrawWidth(6000);
/* 453 */     this.panel.setPreferredSize(new Dimension(450, 300));
/* 454 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 455 */     plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 456 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/* 457 */     this.panel.setMouseWheelEnabled(true);
/* 458 */     chart.getLegend().setVisible(true);
/* 459 */     plot.addRangeMarker((Marker)markerRange);
/* 460 */     plot.addDomainMarker((Marker)markerDomain);
/* 461 */     markerRange.setLabel("Low-High");
/* 462 */     markerRange.setLabelFont(new Font("SansSerif", 0, 15));
/* 463 */     markerRange.setLabelPaint(new Color(0, 102, 0));
/*     */     
/* 465 */     Font font3 = new Font("Dialog", 1, 12);
/* 466 */     plot.getDomainAxis().setLabelFont(font3);
/* 467 */     plot.getRangeAxis().setLabelFont(font3);
/* 468 */     plot.getRangeAxis().setTickLabelFont(font3);
/* 469 */     plot.getDomainAxis().setTickLabelFont(font3);
/*     */     
/* 471 */     NumberAxis domain = (NumberAxis)plot.getDomainAxis();
/*     */     
/* 473 */     NumberAxis range = (NumberAxis)plot.getRangeAxis();
/*     */     
/* 475 */     domain.setAutoRange(true);
/* 476 */     range.setAutoRange(true);
/* 477 */     computePolynomialCoefficients(chart, plot, this.dataset, filterOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addScatterPlotSeriesLogarithmic(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
/* 485 */     this.valuesDomain = valuesDomain;
/* 486 */     this.rangeName = rangeName;
/* 487 */     this.domainName = domainName;
/* 488 */     this.valuesRange = valuesRange;
/* 489 */     this.markerRange = markerRange;
/* 490 */     this.markerDomain = markerDomain;
/*     */ 
/*     */     
/* 493 */     this.classColor = classColor;
/* 494 */     this.filterOrder = this.filterOrder;
/*     */     
/* 496 */     this.panel.removeAll();
/* 497 */     this.dataset = new XYSeriesCollection();
/* 498 */     this.series1 = new XYSeries("");
/* 499 */     for (int i = 0; i < valuesDomain.size(); i++)
/* 500 */       this.series1.add(valuesDomain.get(i), valuesRange.get(i)); 
/* 501 */     this.dataset.addSeries(this.series1);
/* 502 */     double minDomain = ((Double)Collections.<Double>min(valuesDomain)).doubleValue();
/* 503 */     double maxDomain = ((Double)Collections.<Double>max(valuesDomain)).doubleValue();
/* 504 */     double[] coefficients = RegressionLE_.getLogarithmicRegression((XYDataset)this.dataset, 0);
/* 505 */     LogarithmicFunction2D logarithmicFunction2D = new LogarithmicFunction2D(coefficients[0], coefficients[1]);
/* 506 */     XYDataset regressionData = DatasetUtils.sampleFunction2D((Function2D)logarithmicFunction2D, minDomain, maxDomain, valuesDomain.size(), 
/* 507 */         "Fitted Regression Line");
/*     */     
/* 509 */     JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, 
/* 510 */         true, true, false);
/*     */     
/* 512 */     plot = (XYPlot)chart.getPlot();
/* 513 */     XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
/* 514 */     plot.setRenderer((XYItemRenderer)renderer1);
/* 515 */     plot.setBackgroundPaint(new Color(255, 228, 196));
/* 516 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/* 517 */     plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/* 518 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/* 519 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/* 520 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/* 521 */     plot.setDataset(1, regressionData);
/* 522 */     XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
/* 523 */     renderer2.setSeriesPaint(0, Color.RED);
/* 524 */     plot.setRenderer(1, (XYItemRenderer)renderer2);
/* 525 */     Shape cross = new Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
/* 526 */     List<String[]> featureLists = (List)new ArrayList<>();
/* 527 */     for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); j++) {
/* 528 */       featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, 
/* 529 */             ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "")
/* 530 */           .split("<br>"));
/*     */     }
/* 532 */     this.renderer = new XYLineAndShapeRenderer()
/*     */       {
/*     */         public Paint getItemPaint(int row, int col) {
/* 535 */           Paint cpaint = getItemColor(row, col);
/* 536 */           if (cpaint == null) {
/* 537 */             cpaint = super.getItemPaint(row, col);
/*     */           }
/* 539 */           return cpaint;
/*     */         }
/*     */         
/*     */         public Color getItemColor(int row, int col) {
/* 543 */           double x = STScatterPlot.this.dataset.getXValue(row, col);
/* 544 */           double y = STScatterPlot.this.dataset.getYValue(row, col);
/*     */           
/*     */           try {
/* 547 */             return classColor[col];
/* 548 */           } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */             
/* 552 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 557 */     plot.setRenderer((XYItemRenderer)this.renderer);
/* 558 */     this.renderer.setUseOutlinePaint(true);
/* 559 */     this.renderer.setSeriesShape(0, cross);
/* 560 */     this.renderer.setSeriesOutlinePaint(0, Color.black);
/* 561 */     this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
/* 562 */     this.renderer.setSeriesLinesVisible(0, false);
/* 563 */     this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
/* 564 */     plot.getRangeCrosshairValue();
/* 565 */     plot.getDomainCrosshairValue();
/*     */ 
/*     */     
/* 568 */     this.panel.setChart(chart);
/* 569 */     this.panel.setMaximumDrawWidth(6000);
/* 570 */     this.panel.setPreferredSize(new Dimension(450, 300));
/* 571 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 572 */     plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 573 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/* 574 */     this.panel.setMouseWheelEnabled(true);
/* 575 */     chart.getLegend().setVisible(true);
/* 576 */     plot.addRangeMarker((Marker)markerRange);
/* 577 */     plot.addDomainMarker((Marker)markerDomain);
/* 578 */     markerRange.setLabel("Low-High");
/* 579 */     markerRange.setLabelFont(new Font("SansSerif", 0, 15));
/* 580 */     markerRange.setLabelPaint(new Color(0, 102, 0));
/*     */     
/* 582 */     Font font3 = new Font("Dialog", 1, 12);
/* 583 */     plot.getDomainAxis().setLabelFont(font3);
/* 584 */     plot.getRangeAxis().setLabelFont(font3);
/* 585 */     plot.getRangeAxis().setTickLabelFont(font3);
/* 586 */     plot.getDomainAxis().setTickLabelFont(font3);
/*     */     
/* 588 */     NumberAxis domain = (NumberAxis)plot.getDomainAxis();
/*     */     
/* 590 */     NumberAxis range = (NumberAxis)plot.getRangeAxis();
/*     */     
/* 592 */     domain.setAutoRange(true);
/* 593 */     range.setAutoRange(true);
/* 594 */     computeLogarithmicCoefficients(chart, plot, this.dataset, this.filterOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addScatterPlotSeriesExponential(String domainName, String rangeName, List<Double> valuesDomain, List<Double> valuesRange, IntervalMarker markerRange, IntervalMarker markerDomain, Object[][] data, final Color[] classColor) {
/* 602 */     this.valuesDomain = valuesDomain;
/* 603 */     this.rangeName = rangeName;
/* 604 */     this.domainName = domainName;
/* 605 */     this.valuesRange = valuesRange;
/* 606 */     this.markerRange = markerRange;
/* 607 */     this.markerDomain = markerDomain;
/*     */ 
/*     */     
/* 610 */     this.classColor = classColor;
/* 611 */     this.filterOrder = this.filterOrder;
/*     */     
/* 613 */     this.panel.removeAll();
/* 614 */     this.dataset = new XYSeriesCollection();
/* 615 */     this.series1 = new XYSeries("");
/* 616 */     for (int i = 0; i < valuesDomain.size(); i++)
/* 617 */       this.series1.add(valuesDomain.get(i), valuesRange.get(i)); 
/* 618 */     this.dataset.addSeries(this.series1);
/*     */     
/* 620 */     double minDomain = ((Double)Collections.<Double>min(valuesDomain)).doubleValue();
/* 621 */     double maxDomain = ((Double)Collections.<Double>max(valuesDomain)).doubleValue();
/* 622 */     double[] coefficients = RegressionLE_.getExponentialRegression((XYDataset)this.dataset, 0);
/* 623 */     ExponentialFunction2D exponentialFunction2D = new ExponentialFunction2D(coefficients[0], coefficients[1]);
/* 624 */     XYDataset regressionData = DatasetUtils.sampleFunction2D((Function2D)exponentialFunction2D, minDomain, maxDomain, valuesDomain.size(), 
/* 625 */         "Fitted Regression Line");
/*     */     
/* 627 */     JFreeChart chart = ChartFactory.createScatterPlot("", domainName, rangeName, (XYDataset)this.dataset, PlotOrientation.VERTICAL, 
/* 628 */         true, true, false);
/*     */     
/* 630 */     plot = (XYPlot)chart.getPlot();
/* 631 */     XYLineAndShapeRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
/* 632 */     plot.setRenderer((XYItemRenderer)renderer1);
/* 633 */     plot.setBackgroundPaint(new Color(255, 228, 196));
/* 634 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/* 635 */     plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/* 636 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/* 637 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/* 638 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/* 639 */     plot.setDataset(1, regressionData);
/* 640 */     XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer(true, false);
/* 641 */     renderer2.setSeriesPaint(0, Color.RED);
/* 642 */     plot.setRenderer(1, (XYItemRenderer)renderer2);
/* 643 */     Shape cross = new Ellipse2D.Double(0.0D, 0.0D, 5.0D, 5.0D);
/* 644 */     List<String[]> featureLists = (List)new ArrayList<>();
/* 645 */     for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); j++) {
/* 646 */       featureLists.add(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(j, 
/* 647 */             ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("<html>", "")
/* 648 */           .split("<br>"));
/*     */     }
/* 650 */     this.renderer = new XYLineAndShapeRenderer()
/*     */       {
/*     */         public Paint getItemPaint(int row, int col) {
/* 653 */           Paint cpaint = getItemColor(row, col);
/* 654 */           if (cpaint == null) {
/* 655 */             cpaint = super.getItemPaint(row, col);
/*     */           }
/* 657 */           return cpaint;
/*     */         }
/*     */         
/*     */         public Color getItemColor(int row, int col) {
/* 661 */           double x = STScatterPlot.this.dataset.getXValue(row, col);
/* 662 */           double y = STScatterPlot.this.dataset.getYValue(row, col);
/*     */           
/*     */           try {
/* 665 */             return classColor[col];
/* 666 */           } catch (Exception exception) {
/*     */ 
/*     */ 
/*     */             
/* 670 */             return null;
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 675 */     plot.setRenderer((XYItemRenderer)this.renderer);
/* 676 */     this.renderer.setUseOutlinePaint(true);
/* 677 */     this.renderer.setSeriesShape(0, cross);
/* 678 */     this.renderer.setSeriesOutlinePaint(0, Color.black);
/* 679 */     this.renderer.setSeriesOutlineStroke(0, new BasicStroke(1.0F));
/* 680 */     this.renderer.setSeriesLinesVisible(0, false);
/* 681 */     this.renderer.setSeriesPaint(0, Color.LIGHT_GRAY);
/* 682 */     plot.getRangeCrosshairValue();
/* 683 */     plot.getDomainCrosshairValue();
/*     */ 
/*     */     
/* 686 */     this.panel.setChart(chart);
/* 687 */     this.panel.setMaximumDrawWidth(6000);
/* 688 */     this.panel.setPreferredSize(new Dimension(450, 300));
/* 689 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 690 */     plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 691 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/* 692 */     this.panel.setMouseWheelEnabled(true);
/* 693 */     chart.getLegend().setVisible(true);
/* 694 */     plot.addRangeMarker((Marker)markerRange);
/* 695 */     plot.addDomainMarker((Marker)markerDomain);
/* 696 */     markerRange.setLabel("Low-High");
/* 697 */     markerRange.setLabelFont(new Font("SansSerif", 0, 15));
/* 698 */     markerRange.setLabelPaint(new Color(0, 102, 0));
/*     */     
/* 700 */     Font font3 = new Font("Dialog", 1, 12);
/* 701 */     plot.getDomainAxis().setLabelFont(font3);
/* 702 */     plot.getRangeAxis().setLabelFont(font3);
/* 703 */     plot.getRangeAxis().setTickLabelFont(font3);
/* 704 */     plot.getDomainAxis().setTickLabelFont(font3);
/*     */     
/* 706 */     NumberAxis domain = (NumberAxis)plot.getDomainAxis();
/*     */     
/* 708 */     NumberAxis range = (NumberAxis)plot.getRangeAxis();
/*     */     
/* 710 */     domain.setAutoRange(true);
/* 711 */     range.setAutoRange(true);
/* 712 */     computeExponentialCoefficients(chart, plot, this.dataset, this.filterOrder);
/*     */   }
/*     */   
/*     */   private void computeLinearCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset) {
/*     */     String linearEquation;
/* 717 */     Function2D retVal = null;
/* 718 */     double r2 = 0.0D;
/* 719 */     double[] coefficients = null;
/*     */ 
/*     */     
/*     */     try {
/* 723 */       coefficients = RegressionLE_.getOLSRegression((XYDataset)dataset, 0);
/* 724 */       LineFunction2D lineFunction2D = new LineFunction2D(coefficients[0], coefficients[1]);
/* 725 */       r2 = coefficients[2];
/* 726 */     } catch (Exception e) {
/* 727 */       System.err.println(e.getMessage());
/*     */     } 
/* 729 */     double intercept = coefficients[0];
/* 730 */     double slope = coefficients[1];
/*     */     
/* 732 */     if (intercept >= 0.0D) {
/* 733 */       linearEquation = "y = " + String.format("%.2f", new Object[] { Double.valueOf(slope) }) + "x + " + String.format("%.2f", new Object[] { Double.valueOf(intercept) });
/*     */     } else {
/* 735 */       linearEquation = "y = " + String.format("%.2f", new Object[] { Double.valueOf(slope) }) + "x - " + Math.abs(intercept);
/*     */     } 
/*     */     
/* 738 */     TextTitle tt = new TextTitle(String.valueOf(linearEquation) + "\nR² = " + String.format("%.2f", new Object[] { Double.valueOf(r2) }));
/* 739 */     tt.setTextAlignment(HorizontalAlignment.RIGHT);
/* 740 */     tt.setFont(chart.getLegend().getItemFont());
/* 741 */     tt.setBackgroundPaint(new Color(200, 200, 255, 100));
/* 742 */     tt.setFrame((BlockFrame)new BlockBorder(Color.white));
/* 743 */     tt.setPosition(RectangleEdge.BOTTOM);
/*     */     
/* 745 */     XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
/* 746 */     r2Annotation.setMaxWidth(0.48D);
/* 747 */     plot.addAnnotation((XYAnnotation)r2Annotation);
/*     */   }
/*     */   private void computePowerCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset) {
/*     */     String linearEquation;
/* 751 */     Function2D retVal = null;
/* 752 */     double r2 = 0.0D;
/* 753 */     double[] coefficients = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 759 */       coefficients = RegressionLE_.getPowerRegression((XYDataset)dataset, 0);
/* 760 */       if (coefficients[2] > r2) {
/* 761 */         PowerFunction2D powerFunction2D = new PowerFunction2D(coefficients[0], coefficients[1]);
/* 762 */         r2 = coefficients[2];
/*     */       } 
/* 764 */     } catch (Exception e) {
/* 765 */       System.err.println(e.getMessage());
/*     */     } 
/* 767 */     double intercept = coefficients[0];
/* 768 */     double slope = coefficients[1];
/*     */     
/* 770 */     if (intercept >= 0.0D) {
/* 771 */       linearEquation = "y = " + String.format("%.2f", new Object[] { Double.valueOf(slope) }) + "x^ " + String.format("%.2f", new Object[] { Double.valueOf(intercept) });
/*     */     } else {
/* 773 */       linearEquation = "y = " + String.format("%.2f", new Object[] { Double.valueOf(slope) }) + "x^ -" + Math.abs(intercept);
/*     */     } 
/*     */     
/* 776 */     TextTitle tt = new TextTitle(String.valueOf(linearEquation) + "\nR² = " + String.format("%.2f", new Object[] { Double.valueOf(r2) }));
/* 777 */     tt.setTextAlignment(HorizontalAlignment.RIGHT);
/* 778 */     tt.setFont(chart.getLegend().getItemFont());
/* 779 */     tt.setBackgroundPaint(new Color(200, 200, 255, 100));
/* 780 */     tt.setFrame((BlockFrame)new BlockBorder(Color.white));
/* 781 */     tt.setPosition(RectangleEdge.BOTTOM);
/*     */     
/* 783 */     XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
/* 784 */     r2Annotation.setMaxWidth(0.48D);
/* 785 */     plot.addAnnotation((XYAnnotation)r2Annotation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void computePolynomialCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset, int filterOrder) {
/* 791 */     double[] coefficients = Regression.getPolynomialRegression((XYDataset)dataset, 0, filterOrder);
/* 792 */     double r2 = coefficients[coefficients.length - 1];
/* 793 */     String polynomialEquation = "";
/* 794 */     for (int i = coefficients.length - 1; i >= 0; i--) {
/* 795 */       if (i == 0) {
/* 796 */         polynomialEquation = String.valueOf(polynomialEquation) + String.format("%.2f", new Object[] { Double.valueOf(coefficients[i]) });
/*     */       }
/* 798 */       else if (i == 1) {
/* 799 */         polynomialEquation = String.valueOf(polynomialEquation) + String.format("%.2f", new Object[] { Double.valueOf(coefficients[i]) }) + "*x+";
/* 800 */       } else if (i > 1) {
/* 801 */         polynomialEquation = String.valueOf(polynomialEquation) + String.format("%.2f", new Object[] { Double.valueOf(coefficients[i]) }) + "*x^" + i + "+";
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 806 */     TextTitle tt = new TextTitle("y = " + polynomialEquation + "\nR² = " + String.format("%.2f", new Object[] { Double.valueOf(r2) }));
/* 807 */     tt.setTextAlignment(HorizontalAlignment.RIGHT);
/* 808 */     tt.setFont(chart.getLegend().getItemFont());
/* 809 */     tt.setBackgroundPaint(new Color(200, 200, 255, 100));
/* 810 */     tt.setFrame((BlockFrame)new BlockBorder(Color.white));
/* 811 */     tt.setPosition(RectangleEdge.BOTTOM);
/*     */     
/* 813 */     XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
/* 814 */     r2Annotation.setMaxWidth(0.48D);
/* 815 */     plot.addAnnotation((XYAnnotation)r2Annotation);
/*     */   }
/*     */ 
/*     */   
/*     */   private void computeLogarithmicCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset, int filterOrder) {
/* 820 */     Function2D retVal = null;
/* 821 */     double r2 = 0.0D;
/* 822 */     double[] coefficients = null;
/*     */     
/*     */     try {
/* 825 */       coefficients = RegressionLE_.getLogarithmicRegression((XYDataset)dataset, 0);
/* 826 */       if (coefficients[2] > r2) {
/* 827 */         LogarithmicFunction2D logarithmicFunction2D = new LogarithmicFunction2D(coefficients[0], coefficients[1]);
/* 828 */         r2 = coefficients[2];
/*     */       } 
/* 830 */     } catch (Exception e) {
/* 831 */       System.err.println(e.getMessage());
/*     */     } 
/* 833 */     String logarithmicEquation = "y = " + String.format("%.2f", new Object[] { Double.valueOf(coefficients[0]) }) + " + " + "( " + 
/* 834 */       String.format("%.2f", new Object[] { Double.valueOf(coefficients[1]) }) + " * ln(x) ) ";
/*     */     
/* 836 */     TextTitle tt = new TextTitle("y = " + logarithmicEquation + "\nR² = " + String.format("%.2f", new Object[] { Double.valueOf(r2) }));
/* 837 */     tt.setTextAlignment(HorizontalAlignment.RIGHT);
/* 838 */     tt.setFont(chart.getLegend().getItemFont());
/* 839 */     tt.setBackgroundPaint(new Color(200, 200, 255, 100));
/* 840 */     tt.setFrame((BlockFrame)new BlockBorder(Color.white));
/* 841 */     tt.setPosition(RectangleEdge.BOTTOM);
/*     */     
/* 843 */     XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
/* 844 */     r2Annotation.setMaxWidth(0.48D);
/* 845 */     plot.addAnnotation((XYAnnotation)r2Annotation);
/*     */   }
/*     */ 
/*     */   
/*     */   private void computeExponentialCoefficients(JFreeChart chart, XYPlot plot, XYSeriesCollection dataset, int filterOrder) {
/* 850 */     Function2D retVal = null;
/* 851 */     double r2 = 0.0D;
/* 852 */     double[] coefficients = null;
/*     */     
/*     */     try {
/* 855 */       coefficients = RegressionLE_.getExponentialRegression((XYDataset)dataset, 0);
/* 856 */       if (coefficients[2] > r2) {
/* 857 */         LogarithmicFunction2D logarithmicFunction2D = new LogarithmicFunction2D(coefficients[0], coefficients[1]);
/* 858 */         r2 = coefficients[2];
/*     */       } 
/* 860 */     } catch (Exception e) {
/* 861 */       System.err.println(e.getMessage());
/*     */     } 
/* 863 */     String exponentialEquation = "y = " + String.format("%.2f", new Object[] { Double.valueOf(coefficients[0]) }) + " * " + "( e^(" + 
/* 864 */       String.format("%.2f", new Object[] { Double.valueOf(coefficients[1]) }) + " * x) ) ";
/*     */     
/* 866 */     TextTitle tt = new TextTitle("y = " + exponentialEquation + "\nR² = " + String.format("%.2f", new Object[] { Double.valueOf(r2) }));
/* 867 */     tt.setTextAlignment(HorizontalAlignment.RIGHT);
/* 868 */     tt.setFont(chart.getLegend().getItemFont());
/* 869 */     tt.setBackgroundPaint(new Color(200, 200, 255, 100));
/* 870 */     tt.setFrame((BlockFrame)new BlockBorder(Color.white));
/* 871 */     tt.setPosition(RectangleEdge.BOTTOM);
/*     */     
/* 873 */     XYTitleAnnotation r2Annotation = new XYTitleAnnotation(0.98D, 0.02D, (Title)tt, RectangleAnchor.BOTTOM_RIGHT);
/* 874 */     r2Annotation.setMaxWidth(0.48D);
/* 875 */     plot.addAnnotation((XYAnnotation)r2Annotation);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/STScatterPlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */