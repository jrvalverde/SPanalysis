/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.plot.DefaultDrawingSupplier;
/*     */ import org.jfree.chart.plot.DrawingSupplier;
/*     */ import org.jfree.chart.plot.IntervalMarker;
/*     */ import org.jfree.chart.plot.Marker;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.StandardXYBarPainter;
/*     */ import org.jfree.chart.renderer.xy.XYBarPainter;
/*     */ import org.jfree.chart.renderer.xy.XYBarRenderer;
/*     */ import org.jfree.data.statistics.HistogramDataset;
/*     */ import org.jfree.data.xy.IntervalXYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HistogramFilterVersion
/*     */ {
/*     */   private int BINS;
/*     */   private HistogramDataset dataset;
/*     */   private XYBarRenderer renderer;
/*     */   private XYPlot plot;
/*     */   private double[] values;
/*     */   private String feature;
/*     */   private ChartPanel panel;
/*     */   public IntervalMarker intervalMarker;
/*     */   
/*     */   public ChartPanel createChartPanel(String feature, double[] values, int BINS, IntervalMarker intervalMarker) {
/*  62 */     this.feature = feature;
/*  63 */     this.values = values;
/*  64 */     this.BINS = BINS;
/*  65 */     this.intervalMarker = intervalMarker;
/*     */ 
/*     */     
/*  68 */     this.dataset = new HistogramDataset();
/*  69 */     if (BINS != 0) {
/*  70 */       this.dataset.addSeries(feature, values, BINS);
/*     */     }
/*  72 */     JFreeChart chart = ChartFactory.createHistogram("", feature, "", (IntervalXYDataset)this.dataset, PlotOrientation.VERTICAL, true, true, 
/*  73 */         false);
/*     */     
/*  75 */     this.plot = (XYPlot)chart.getPlot();
/*  76 */     this.renderer = (XYBarRenderer)this.plot.getRenderer();
/*  77 */     this.renderer.setBarPainter((XYBarPainter)new StandardXYBarPainter());
/*     */     
/*  79 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/*  80 */     this.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/*  81 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/*  82 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/*  83 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/*  84 */     this.panel = new ChartPanel(chart);
/*     */     
/*  86 */     this.panel.setPreferredSize(new Dimension(390, 180));
/*     */     
/*  88 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/*  89 */     this.plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/*  90 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/*  91 */     this.panel.setMouseWheelEnabled(true);
/*  92 */     Font font3 = new Font("Dialog", 2, 9);
/*  93 */     this.plot.getDomainAxis().setLabelFont(font3);
/*  94 */     this.plot.getRangeAxis().setLabelFont(font3);
/*  95 */     this.plot.getRangeAxis().setTickLabelFont(font3);
/*  96 */     this.plot.getDomainAxis().setTickLabelFont(font3);
/*  97 */     chart.getLegend().setVisible(false);
/*  98 */     this.plot.addDomainMarker((Marker)intervalMarker);
/*  99 */     double x = (float)(0.05D * this.plot.getDomainAxis().getRange().getLength());
/* 100 */     double y = (float)(0.85D * this.plot.getRangeAxis().getUpperBound());
/*     */     
/* 102 */     return this.panel;
/*     */   }
/*     */   
/*     */   public JPanel createControlPanel() {
/* 106 */     JPanel panel = new JPanel();
/*     */     
/* 108 */     return panel;
/*     */   }
/*     */   
/*     */   public void addHistogramSeries(String feature, double[] values, int BINS, IntervalMarker intervalMarker) {
/* 112 */     this.feature = feature;
/* 113 */     this.values = values;
/* 114 */     this.BINS = BINS;
/* 115 */     this.intervalMarker = intervalMarker;
/*     */     
/* 117 */     this.panel.removeAll();
/* 118 */     this.dataset = new HistogramDataset();
/* 119 */     if (BINS != 0) {
/* 120 */       this.dataset.addSeries(feature, values, BINS);
/*     */     }
/*     */     
/* 123 */     JFreeChart chart = ChartFactory.createHistogram("", feature, "COUNTING", (IntervalXYDataset)this.dataset, PlotOrientation.VERTICAL, 
/* 124 */         true, true, false);
/*     */     
/* 126 */     this.plot = (XYPlot)chart.getPlot();
/* 127 */     this.renderer = (XYBarRenderer)this.plot.getRenderer();
/* 128 */     this.renderer.setBarPainter((XYBarPainter)new StandardXYBarPainter());
/*     */     
/* 130 */     Paint[] paintArray = { new Color(-2130771968, true), new Color(-2147418368, true), new Color(-2147483393, true) };
/* 131 */     this.plot.setDrawingSupplier((DrawingSupplier)new DefaultDrawingSupplier(paintArray, 
/* 132 */           DefaultDrawingSupplier.DEFAULT_FILL_PAINT_SEQUENCE, 
/* 133 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, 
/* 134 */           DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));
/* 135 */     this.panel.setChart(chart);
/*     */     
/* 137 */     this.panel.setPreferredSize(new Dimension(390, 180));
/*     */     
/* 139 */     chart.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 140 */     this.plot.setBackgroundPaint(new Color(255, 255, 255, 0));
/* 141 */     chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
/* 142 */     this.panel.setMouseWheelEnabled(true);
/* 143 */     chart.getLegend().setVisible(false);
/* 144 */     Font font3 = new Font("Dialog", 2, 10);
/* 145 */     this.plot.getDomainAxis().setLabelFont(font3);
/* 146 */     this.plot.getRangeAxis().setLabelFont(font3);
/* 147 */     this.plot.getRangeAxis().setTickLabelFont(font3);
/* 148 */     this.plot.getDomainAxis().setTickLabelFont(font3);
/* 149 */     this.plot.addDomainMarker((Marker)intervalMarker);
/*     */   }
/*     */ 
/*     */   
/*     */   public class VisibleAction
/*     */     extends AbstractAction
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private final int i;
/*     */ 
/*     */     
/*     */     public VisibleAction(int i) {
/* 162 */       this.i = i;
/* 163 */       putValue("Name", HistogramFilterVersion.this.dataset.getSeriesKey(i));
/* 164 */       putValue("SwingSelectedKey", Boolean.valueOf(true));
/* 165 */       HistogramFilterVersion.this.renderer.setSeriesVisible(i, Boolean.valueOf(true));
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 170 */       HistogramFilterVersion.this.renderer.setSeriesVisible(this.i, Boolean.valueOf(!HistogramFilterVersion.this.renderer.getSeriesVisible(this.i).booleanValue()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void display() {
/* 175 */     JFrame f = new JFrame("Histogram");
/* 176 */     f.setDefaultCloseOperation(3);
/*     */     
/* 178 */     f.add(createControlPanel(), "South");
/* 179 */     f.pack();
/* 180 */     f.setLocationRelativeTo((Component)null);
/* 181 */     f.setVisible(true);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/HistogramFilterVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */