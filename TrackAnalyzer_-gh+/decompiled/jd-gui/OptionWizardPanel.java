/*     */ import ij.IJ;
/*     */ import ij.measure.ResultsTable;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import jwizardcomponent.JWizardComponents;
/*     */ import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.plot.IntervalMarker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptionWizardPanel
/*     */   extends LabelWizardPanel
/*     */ {
/*     */   public double maxDomainSpot;
/*     */   public double maxRangeSpot;
/*     */   public double maxDomainTrack;
/*     */   public double maxRangeTrack;
/*     */   public IntervalMarker markerRangeSpot;
/*     */   public IntervalMarker markerDomainSpot;
/*     */   public IntervalMarker markerRangeTrack;
/*     */   public IntervalMarker markerDomainTrack;
/*     */   private JSpinner filterOrderSpot;
/*     */   private JSpinner filterOrderTrack;
/*     */   static int selectedIndexCh2;
/*     */   static int selectedIndexCh3;
/*     */   static int numCh2Positive;
/*     */   static int numCh3Positive;
/*     */   static int countSenescentNumber;
/*     */   static int lhCountAll;
/*     */   static int hhCountAll;
/*     */   static int llCountAll;
/*     */   static int hlCountAll;
/*     */   static int lhCountNID;
/*     */   static int hhCountNID;
/*     */   static int llCountNID;
/*     */   static int hlCountNID;
/*     */   static int lhCountClass;
/*     */   static int hhCountClass;
/*     */   static int llCountClass;
/*     */   static int hlCountClass;
/*     */   static int selectedIndexDomainSpot;
/*     */   static int selectedIndexRangeSpot;
/*     */   static int selectedIndexDomainTrack;
/*     */   static int selectedIndexRangeTrack;
/*     */   static JLabel scatLabel;
/*     */   static JLabel sumLabel;
/*     */   static JLabel labelScoresSpot;
/*     */   static JLabel labelScoresTrack;
/*     */   JComboBox<String> comboFeatureDomainSpot;
/*     */   JComboBox<String> comboFeatureRangeSpot;
/*     */   
/*     */   public OptionWizardPanel(JWizardComponents wizardComponents, String option) {
/*  94 */     super(wizardComponents, "");
/*  95 */     setPanelTitle("");
/*  96 */     setLayout(new BoxLayout((Container)this, 1));
/*     */     
/*  98 */     JPanel chartPanel2Spot = new JPanel();
/*  99 */     this.markerRangeSpot = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/* 100 */         new BasicStroke(1.5F), 0.6F);
/* 101 */     this.markerDomainSpot = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/* 102 */         new BasicStroke(1.5F), 0.5F);
/* 103 */     this.markerRangeTrack = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/* 104 */         new BasicStroke(1.5F), 0.6F);
/* 105 */     this.markerDomainTrack = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/* 106 */         new BasicStroke(1.5F), 0.5F);
/* 107 */     this.sp2Spot = new STScatterPlot("");
/* 108 */     this.scatterPlotSpot = this.sp2Spot.createScatterChartPanelInitial("", "", new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D)
/* 109 */             }, )), new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D) }, )), this.markerRangeSpot, this.markerDomainSpot, 
/* 110 */         (Object[][])new Double[][] { { Double.valueOf(0.0D) }, , { Double.valueOf(0.0D) },  }, new Double[][] { { Double.valueOf(0.0D) }, { Double.valueOf(0.0D) } });
/* 111 */     this.refreshButtonSpot = new JButton("");
/* 112 */     this.refreshButtonSpot.setIcon(FirstWizardPanel.refreshCell);
/* 113 */     this.refreshButtonSpot.setToolTipText("Click this button to refresh scatter-plot.");
/* 114 */     this.zoomInSpot = new JButton("");
/* 115 */     ImageIcon iconZoomIn = createImageIcon("/images/zoomin.png");
/* 116 */     Icon zoomInCell = new ImageIcon(iconZoomIn.getImage().getScaledInstance(18, 20, 4));
/* 117 */     this.zoomInSpot.setIcon(zoomInCell);
/* 118 */     this.zoomInSpot.setToolTipText("Click this button to zoom in Chart");
/* 119 */     this.zoomOutSpot = new JButton("");
/* 120 */     ImageIcon iconZoomOut = createImageIcon("/images/zoomout.png");
/* 121 */     Icon zoomOutCell = new ImageIcon(iconZoomOut.getImage().getScaledInstance(18, 20, 4));
/* 122 */     this.zoomOutSpot.setIcon(zoomOutCell);
/* 123 */     this.zoomOutSpot.setToolTipText("Click this button to zoom out Chart");
/* 124 */     this.itemFiltersSpot = new ArrayList<>();
/* 125 */     this.itemFiltersTrack = new ArrayList<>(); int i;
/* 126 */     for (i = 2; i < FirstWizardPanel.columnNamesSpot.length; i++)
/* 127 */       this.itemFiltersSpot.add(FirstWizardPanel.columnNamesSpot[i].toString()); 
/* 128 */     for (i = 0; i < ChooserWizardPanel.columnNamesTrack.length; i++)
/* 129 */       this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[i].toString()); 
/* 130 */     this.comboFeatureDomainSpot = new JComboBox<>();
/* 131 */     this.comboFeatureDomainSpot.setPreferredSize(new Dimension(110, 20));
/* 132 */     for (i = 0; i < this.itemFiltersSpot.size(); i++)
/* 133 */       this.comboFeatureDomainSpot.addItem(this.itemFiltersSpot.get(i)); 
/* 134 */     this.comboFeatureDomainSpot.setOpaque(true);
/* 135 */     this.scatterPlot = new STScatterPlot("holaa");
/* 136 */     chartPanel2Spot.add((Component)this.scatterPlotSpot);
/* 137 */     this.filterOrderSpot = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
/* 138 */     this.filterOrderSpot.setPreferredSize(new Dimension(60, 20));
/* 139 */     this.filterOrderSpot.setEnabled(false);
/*     */ 
/*     */ 
/*     */     
/* 143 */     JPanel filtersMaxSpot = new JPanel(new FlowLayout(1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     filtersMaxSpot.add(Box.createHorizontalStrut(2));
/*     */ 
/*     */     
/* 151 */     this.comboFeatureRangeSpot = new JComboBox<>();
/* 152 */     this.comboFeatureRangeSpot.setPreferredSize(new Dimension(110, 20));
/* 153 */     for (int j = 0; j < this.itemFiltersSpot.size(); j++)
/* 154 */       this.comboFeatureRangeSpot.addItem(this.itemFiltersSpot.get(j)); 
/* 155 */     this.comboFeatureRangeSpot.setOpaque(true);
/* 156 */     filtersMaxSpot.add(new JLabel("X :  "));
/* 157 */     filtersMaxSpot.add(this.comboFeatureDomainSpot);
/* 158 */     filtersMaxSpot.add(new JLabel("   Y :  "));
/* 159 */     filtersMaxSpot.add(this.comboFeatureRangeSpot);
/* 160 */     JPanel rangePanelFSpot = new JPanel(new FlowLayout(0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     JPanel chartDomainPanelBoxSpot = new JPanel();
/* 169 */     chartDomainPanelBoxSpot.setLayout(new BoxLayout(chartDomainPanelBoxSpot, 1));
/* 170 */     chartDomainPanelBoxSpot.add(Box.createVerticalStrut(10));
/* 171 */     chartDomainPanelBoxSpot.add(chartPanel2Spot);
/* 172 */     chartDomainPanelBoxSpot.add(filtersMaxSpot);
/*     */ 
/*     */ 
/*     */     
/* 176 */     rangePanelFSpot.add(chartDomainPanelBoxSpot);
/* 177 */     JPanel buttonBox = new JPanel();
/* 178 */     buttonBox.setLayout(new BoxLayout(buttonBox, 1));
/* 179 */     JPanel refreshButtonPanelSpot = new JPanel(new FlowLayout(0));
/* 180 */     refreshButtonPanelSpot.add(this.refreshButtonSpot);
/* 181 */     JPanel zoomOutButtonPanelSpot = new JPanel(new FlowLayout(0));
/* 182 */     zoomOutButtonPanelSpot.add(this.zoomOutSpot);
/* 183 */     JPanel zoomInButtonPanelSpot = new JPanel(new FlowLayout(0));
/* 184 */     zoomInButtonPanelSpot.add(this.zoomInSpot);
/* 185 */     buttonBox.add(zoomInButtonPanelSpot);
/* 186 */     buttonBox.add(zoomOutButtonPanelSpot);
/* 187 */     buttonBox.add(refreshButtonPanelSpot);
/* 188 */     this.comboRegressionSpot = new JComboBox<>();
/* 189 */     this.comboRegressionSpot.setPreferredSize(new Dimension(90, 20));
/* 190 */     this.comboRegressionSpot.addItem("Linear");
/* 191 */     this.comboRegressionSpot.addItem("Polynomial");
/* 192 */     this.comboRegressionSpot.addItem("Power");
/* 193 */     this.comboRegressionSpot.addItem("Logarithmic");
/* 194 */     this.comboRegressionSpot.addItem("Exponential");
/*     */     
/* 196 */     this.comboRegressionSpot.setSelectedIndex(0);
/* 197 */     this.comboRegressionSpot.setOpaque(true);
/* 198 */     JPanel regreOrderPanel = new JPanel(new FlowLayout(0));
/* 199 */     regreOrderPanel.add(this.comboRegressionSpot);
/* 200 */     JPanel filterOrderPanel = new JPanel(new FlowLayout(0));
/* 201 */     filterOrderPanel.add(this.filterOrderSpot);
/* 202 */     buttonBox.add(regreOrderPanel);
/* 203 */     buttonBox.add(filterOrderPanel);
/* 204 */     regressionPanelSpot = new JPanel();
/* 205 */     regressionPanelSpot.setBorder(BorderFactory.createTitledBorder("Reg.Params"));
/* 206 */     regressionPanelSpot.setPreferredSize(new Dimension(this.comboRegressionSpot.getWidth() + 10, 35));
/* 207 */     rangePanelFSpot.add(buttonBox);
/* 208 */     JPanel spotPanel = new JPanel();
/* 209 */     spotPanel.add(rangePanelFSpot);
/* 210 */     spotPanel.setBorder(BorderFactory.createTitledBorder(""));
/*     */     
/* 212 */     JPanel spotStatistics = new JPanel();
/* 213 */     spotStatistics.setLayout(new BoxLayout(spotStatistics, 1));
/* 214 */     JPanel spotStatisticsFlow = new JPanel(new FlowLayout(0));
/* 215 */     JPanel classParamSpot = new JPanel(new FlowLayout(0));
/* 216 */     this.comboClassSpot = new JComboBox<>();
/* 217 */     this.comboClassSpot.setPreferredSize(new Dimension(120, 20)); int k;
/* 218 */     for (k = 2; k < FirstWizardPanel.columnNamesSpot.length; k++)
/* 219 */       this.comboClassSpot.addItem((String)FirstWizardPanel.columnNamesSpot[k]); 
/* 220 */     this.comboClassSpot.setOpaque(true);
/* 221 */     this.comboClassSpot.setToolTipText("Choose selected items for descriptive statistics.");
/* 222 */     this.comboParamSpot = new JComboBox<>();
/* 223 */     this.comboParamSpot.setPreferredSize(new Dimension(120, 20));
/* 224 */     for (k = 2; k < FirstWizardPanel.columnNamesSpot.length; k++)
/* 225 */       this.comboParamSpot.addItem((String)FirstWizardPanel.columnNamesSpot[k]); 
/* 226 */     this.comboParamSpot.setOpaque(true);
/* 227 */     this.comboParamSpot.setToolTipText("Choose a spot parameter for descriptive statistics.");
/* 228 */     classParamSpot.add(this.comboClassSpot);
/* 229 */     classParamSpot.add(this.comboParamSpot);
/* 230 */     labelScoresSpot = new JLabel("SCORES");
/* 231 */     labelScoresSpot.setFont(new Font("Verdana", 1, 20));
/* 232 */     spotStatisticsFlow.add(labelScoresSpot);
/* 233 */     spotStatistics.add(Box.createVerticalStrut(60));
/* 234 */     spotStatistics.add(spotStatisticsFlow);
/* 235 */     spotStatistics.add(new JSeparator(0));
/* 236 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 237 */     spotStatistics.add(classParamSpot);
/* 238 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 239 */     spotStatistics.add(new JSeparator(0));
/* 240 */     String[][] data = { { "", "", "", "", "", "", "", "", "", "", "" } };
/* 241 */     String[] column = { "Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", "Min ", 
/* 242 */         "Max ", "Sum ", "Count " };
/*     */     
/* 244 */     JTable table = new JTable();
/* 245 */     this.modelSpot = new DefaultTableModel((Object[][])data, (Object[])column)
/*     */       {
/*     */         public Class<?> getColumnClass(int column)
/*     */         {
/* 249 */           if (getRowCount() > 0) {
/* 250 */             Object value = getValueAt(0, column);
/* 251 */             if (value != null) {
/* 252 */               return getValueAt(0, column).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 256 */           return super.getColumnClass(column);
/*     */         }
/*     */         
/*     */         public boolean isCellEditable(int row, int col) {
/* 260 */           return false;
/*     */         }
/*     */       };
/* 263 */     table.setAutoResizeMode(0);
/* 264 */     table.setModel(this.modelSpot);
/* 265 */     table.setRowHeight(60);
/* 266 */     JScrollPane sp = new JScrollPane(table);
/* 267 */     sp.setPreferredSize(new Dimension(500, 100));
/* 268 */     sp.setVerticalScrollBarPolicy(22);
/* 269 */     sp.setHorizontalScrollBarPolicy(32);
/* 270 */     for (int u = 0; u < table.getColumnCount(); u++) {
/* 271 */       table.getColumnModel().getColumn(u).setMinWidth(100);
/* 272 */       table.getColumnModel().getColumn(u).setMaxWidth(100);
/* 273 */       table.getColumnModel().getColumn(u).setPreferredWidth(100);
/*     */     } 
/*     */     
/* 276 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 277 */     spotStatistics.add(sp);
/* 278 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 279 */     spotStatistics.add(new JSeparator(0));
/* 280 */     JButton plotButton = new JButton();
/* 281 */     ImageIcon iconPlot = createImageIcon("/images/plot.jpg");
/* 282 */     Icon plotCell = new ImageIcon(iconPlot.getImage().getScaledInstance(18, 20, 4));
/* 283 */     plotButton.setIcon(plotCell);
/* 284 */     plotButton.setToolTipText("Click to export scatter plot.");
/* 285 */     JPanel panelPlot = new JPanel(new FlowLayout(0));
/* 286 */     panelPlot.add(plotButton);
/* 287 */     JButton csvButton = new JButton();
/* 288 */     ImageIcon iconCsv = createImageIcon("/images/csv.png");
/* 289 */     Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
/* 290 */     csvButton.setIcon(csvCell);
/* 291 */     csvButton.setToolTipText("Click to export your spots statistics table.");
/* 292 */     JPanel panelCsv = new JPanel(new FlowLayout(0));
/* 293 */     panelCsv.add(csvButton);
/* 294 */     JPanel panelPngCsv = new JPanel(new FlowLayout(0));
/* 295 */     panelPngCsv.add(panelPlot);
/* 296 */     panelPngCsv.add(panelCsv);
/* 297 */     spotStatistics.add(panelPngCsv);
/* 298 */     spotPanel.add(spotStatistics);
/*     */ 
/*     */ 
/*     */     
/* 302 */     JPanel chartPanel2Track = new JPanel();
/* 303 */     this.sp2Track = new STScatterPlot("");
/* 304 */     this.scatterPlotTrack = this.sp2Track.createScatterChartPanelInitial("", "", 
/* 305 */         new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D) }, )), new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D)
/* 306 */             }, )), this.markerRangeTrack, this.markerDomainTrack, (Object[][])new Double[][] { { Double.valueOf(0.0D) }, , { Double.valueOf(0.0D)
/* 307 */           },  }, new Double[][] { { Double.valueOf(0.0D) }, { Double.valueOf(0.0D) } });
/* 308 */     this.refreshButtonTrack = new JButton("");
/* 309 */     this.refreshButtonTrack.setIcon(FirstWizardPanel.refreshCell);
/* 310 */     this.refreshButtonTrack.setToolTipText("Click this button to refresh scatter-plot.");
/* 311 */     this.zoomInTrack = new JButton("");
/* 312 */     this.zoomInTrack.setIcon(zoomInCell);
/* 313 */     this.zoomInTrack.setToolTipText("Click this button to zoom in Chart");
/* 314 */     this.zoomOutTrack = new JButton("");
/* 315 */     this.zoomOutTrack.setIcon(zoomOutCell);
/* 316 */     this.zoomOutTrack.setToolTipText("Click this button to zoom out Chart"); int m;
/* 317 */     for (m = 0; m < ChooserWizardPanel.columnNamesTrack.length; m++)
/* 318 */       this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[m].toString()); 
/* 319 */     this.comboFeatureDomainTrack = new JComboBox<>();
/* 320 */     this.comboFeatureDomainTrack.setPreferredSize(new Dimension(110, 20));
/* 321 */     for (m = 3; m < this.itemFiltersTrack.size(); m++)
/* 322 */       this.comboFeatureDomainTrack.addItem(this.itemFiltersTrack.get(m)); 
/* 323 */     this.comboFeatureDomainTrack.setOpaque(true);
/*     */     
/* 325 */     chartPanel2Track.add((Component)this.scatterPlotTrack);
/* 326 */     this.filterOrderTrack = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
/* 327 */     this.filterOrderTrack.setPreferredSize(new Dimension(60, 20));
/* 328 */     this.filterOrderTrack.setEnabled(false);
/* 329 */     JPanel filtersMaxTrack = new JPanel(new FlowLayout(1));
/* 330 */     filtersMaxTrack.add(Box.createHorizontalStrut(2));
/*     */     
/* 332 */     this.comboFeatureRangeTrack = new JComboBox<>();
/* 333 */     this.comboFeatureRangeTrack.setPreferredSize(new Dimension(110, 20));
/* 334 */     for (int n = 3; n < this.itemFiltersTrack.size(); n++)
/* 335 */       this.comboFeatureRangeTrack.addItem(this.itemFiltersTrack.get(n)); 
/* 336 */     this.comboFeatureRangeTrack.setOpaque(true);
/* 337 */     filtersMaxTrack.add(new JLabel("X :  "));
/* 338 */     filtersMaxTrack.add(this.comboFeatureDomainTrack);
/* 339 */     filtersMaxTrack.add(new JLabel("   Y :  "));
/* 340 */     filtersMaxTrack.add(this.comboFeatureRangeTrack);
/* 341 */     JPanel rangePanelFTrack = new JPanel(new FlowLayout(0));
/* 342 */     JPanel chartDomainPanelBoxTrack = new JPanel();
/* 343 */     chartDomainPanelBoxTrack.setLayout(new BoxLayout(chartDomainPanelBoxTrack, 1));
/* 344 */     chartDomainPanelBoxTrack.add(Box.createVerticalStrut(10));
/* 345 */     chartDomainPanelBoxTrack.add(chartPanel2Track);
/* 346 */     chartDomainPanelBoxTrack.add(filtersMaxTrack);
/* 347 */     rangePanelFTrack.add(chartDomainPanelBoxTrack);
/* 348 */     JPanel buttonBoxTrack = new JPanel();
/* 349 */     buttonBoxTrack.setLayout(new BoxLayout(buttonBoxTrack, 1));
/* 350 */     JPanel refreshButtonPanelTrack = new JPanel(new FlowLayout(0));
/* 351 */     refreshButtonPanelTrack.add(this.refreshButtonTrack);
/* 352 */     JPanel zoomOutButtonPanelTrack = new JPanel(new FlowLayout(0));
/* 353 */     zoomOutButtonPanelTrack.add(this.zoomOutTrack);
/* 354 */     JPanel zoomInButtonPanelTrack = new JPanel(new FlowLayout(0));
/* 355 */     zoomInButtonPanelTrack.add(this.zoomInTrack);
/* 356 */     buttonBoxTrack.add(zoomInButtonPanelTrack);
/* 357 */     buttonBoxTrack.add(zoomOutButtonPanelTrack);
/* 358 */     buttonBoxTrack.add(refreshButtonPanelTrack);
/* 359 */     this.comboRegressionTrack = new JComboBox<>();
/* 360 */     this.comboRegressionTrack.setPreferredSize(new Dimension(90, 20));
/* 361 */     this.comboRegressionTrack.addItem("Linear");
/* 362 */     this.comboRegressionTrack.addItem("Polynomial");
/* 363 */     this.comboRegressionTrack.addItem("Power");
/* 364 */     this.comboRegressionTrack.addItem("Logarithmic");
/* 365 */     this.comboRegressionTrack.addItem("Exponential");
/*     */     
/* 367 */     this.comboRegressionTrack.setSelectedIndex(0);
/* 368 */     this.comboRegressionTrack.setOpaque(true);
/* 369 */     JPanel regreOrderPanelTrack = new JPanel(new FlowLayout(0));
/* 370 */     regreOrderPanelTrack.add(this.comboRegressionTrack);
/* 371 */     JPanel filterOrderPanelTrack = new JPanel(new FlowLayout(0));
/* 372 */     filterOrderPanelTrack.add(this.filterOrderTrack);
/* 373 */     buttonBoxTrack.add(regreOrderPanelTrack);
/* 374 */     buttonBoxTrack.add(filterOrderPanelTrack);
/* 375 */     regressionPanelTrack = new JPanel();
/* 376 */     regressionPanelTrack.setBorder(BorderFactory.createTitledBorder("Reg.Params"));
/* 377 */     regressionPanelTrack.setPreferredSize(new Dimension(this.comboRegressionTrack.getWidth() + 10, 35));
/* 378 */     rangePanelFTrack.add(buttonBoxTrack);
/*     */     
/* 380 */     JPanel trackPanel = new JPanel();
/* 381 */     trackPanel.add(rangePanelFTrack);
/* 382 */     trackPanel.setBorder(BorderFactory.createTitledBorder(""));
/*     */     
/* 384 */     JPanel trackStatistics = new JPanel();
/* 385 */     trackStatistics.setLayout(new BoxLayout(trackStatistics, 1));
/* 386 */     JPanel trackStatisticsFlow = new JPanel(new FlowLayout(0));
/* 387 */     JPanel classParamTrack = new JPanel(new FlowLayout(0));
/* 388 */     this.comboClassTrack = new JComboBox<>();
/* 389 */     this.comboClassTrack.setPreferredSize(new Dimension(120, 20)); int i1;
/* 390 */     for (i1 = 3; i1 < ChooserWizardPanel.columnNamesTrack.length; i1++)
/* 391 */       this.comboClassTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i1]); 
/* 392 */     this.comboClassTrack.setOpaque(true);
/* 393 */     this.comboClassTrack.setToolTipText("Choose selected items for descriptive statistics.");
/* 394 */     this.comboParamTrack = new JComboBox<>();
/* 395 */     this.comboParamTrack.setPreferredSize(new Dimension(120, 20));
/* 396 */     for (i1 = 3; i1 < ChooserWizardPanel.columnNamesTrack.length; i1++)
/* 397 */       this.comboParamTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i1]); 
/* 398 */     this.comboParamTrack.setOpaque(true);
/* 399 */     this.comboParamTrack.setToolTipText("Choose a track parameter for descriptive statistics.");
/* 400 */     classParamTrack.add(this.comboClassTrack);
/* 401 */     classParamTrack.add(this.comboParamTrack);
/* 402 */     labelScoresTrack = new JLabel("SCORES");
/* 403 */     labelScoresTrack.setFont(new Font("Verdana", 1, 20));
/* 404 */     trackStatisticsFlow.add(labelScoresTrack);
/* 405 */     trackStatistics.add(Box.createVerticalStrut(60));
/* 406 */     trackStatistics.add(trackStatisticsFlow);
/* 407 */     trackStatistics.add(new JSeparator(0));
/* 408 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 409 */     trackStatistics.add(classParamTrack);
/* 410 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 411 */     trackStatistics.add(new JSeparator(0));
/* 412 */     String[][] dataTrack = { { "", "", "", "", "", "", "", "", "", "", "" } };
/* 413 */     String[] columnTrack = { "Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", 
/* 414 */         "Min ", "Max ", "Sum ", "Count " };
/*     */     
/* 416 */     JTable tableTrack = new JTable();
/* 417 */     this.modelTrack = new DefaultTableModel((Object[][])dataTrack, (Object[])columnTrack)
/*     */       {
/*     */         public Class<?> getColumnClass(int columnTrack)
/*     */         {
/* 421 */           if (getRowCount() > 0) {
/* 422 */             Object value = getValueAt(0, columnTrack);
/* 423 */             if (value != null) {
/* 424 */               return getValueAt(0, columnTrack).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 428 */           return super.getColumnClass(columnTrack);
/*     */         }
/*     */         
/*     */         public boolean isCellEditable(int row, int col) {
/* 432 */           return false;
/*     */         }
/*     */       };
/* 435 */     tableTrack.setAutoResizeMode(0);
/* 436 */     tableTrack.setModel(this.modelTrack);
/* 437 */     tableTrack.setRowHeight(60);
/* 438 */     JScrollPane spTrack = new JScrollPane(tableTrack);
/* 439 */     spTrack.setPreferredSize(new Dimension(500, 100));
/* 440 */     spTrack.setVerticalScrollBarPolicy(22);
/* 441 */     spTrack.setHorizontalScrollBarPolicy(32);
/* 442 */     for (int i2 = 0; i2 < tableTrack.getColumnCount(); i2++) {
/* 443 */       tableTrack.getColumnModel().getColumn(i2).setMinWidth(100);
/* 444 */       tableTrack.getColumnModel().getColumn(i2).setMaxWidth(100);
/* 445 */       tableTrack.getColumnModel().getColumn(i2).setPreferredWidth(100);
/*     */     } 
/*     */     
/* 448 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 449 */     trackStatistics.add(spTrack);
/* 450 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 451 */     trackStatistics.add(new JSeparator(0));
/* 452 */     JButton plotButtonTrack = new JButton();
/* 453 */     plotButtonTrack.setIcon(plotCell);
/* 454 */     plotButtonTrack.setToolTipText("Click to export scatter plot.");
/* 455 */     JPanel panelPlotTrack = new JPanel(new FlowLayout(0));
/* 456 */     panelPlotTrack.add(plotButtonTrack);
/* 457 */     JButton csvButtonTrack = new JButton();
/* 458 */     csvButtonTrack.setIcon(csvCell);
/* 459 */     csvButtonTrack.setToolTipText("Click to export your tracks statistics table.");
/* 460 */     JPanel panelCsvTrack = new JPanel(new FlowLayout(0));
/* 461 */     panelCsvTrack.add(csvButtonTrack);
/* 462 */     JPanel panelPngCsvTrack = new JPanel(new FlowLayout(0));
/* 463 */     panelPngCsvTrack.add(panelPlotTrack);
/* 464 */     panelPngCsvTrack.add(panelCsvTrack);
/* 465 */     trackStatistics.add(panelPngCsvTrack);
/* 466 */     trackPanel.add(trackStatistics);
/*     */     
/* 468 */     JTabbedPane maintabbedPane = new JTabbedPane(1);
/* 469 */     maintabbedPane.addTab("SPOTS ", FirstWizardPanel.iconSpotCell, spotPanel, "Scatter-Plot for spots");
/* 470 */     maintabbedPane.addTab("TRACKS ", ChooserWizardPanel.iconTrackCell, trackPanel, "Scatter-Plot for tracks");
/*     */     
/* 472 */     maintabbedPane.setTabLayoutPolicy(1);
/* 473 */     add(maintabbedPane, "Center");
/*     */     
/* 475 */     plotButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 478 */             JFileChooser fileChooser = new JFileChooser();
/* 479 */             fileChooser.setFileSelectionMode(1);
/* 480 */             fileChooser.setDialogTitle("Specify a directory to save plot as .png file");
/* 481 */             int userSelection = fileChooser.showSaveDialog(new JFrame());
/*     */             
/* 483 */             if (userSelection == 0) {
/* 484 */               File fileToSave = fileChooser.getSelectedFile();
/* 485 */               BufferedImage chartImage = STScatterPlot.plot.getChart().createBufferedImage(1024, 768);
/*     */               try {
/* 487 */                 ImageIO.write(chartImage, "png", new File(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 488 */                       "SpotPlot for " + IJ.getImage().getShortTitle() + ".png"));
/* 489 */               } catch (IOException e1) {
/*     */                 
/* 491 */                 e1.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 498 */     csvButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 501 */             List<String> columnSpotHead = new ArrayList<>();
/* 502 */             for (int j = 0; j < OptionWizardPanel.this.modelSpot.getColumnCount(); j++) {
/* 503 */               columnSpotHead.add(OptionWizardPanel.this.modelSpot.getColumnName(j));
/*     */             }
/* 505 */             ResultsTable rt = new ResultsTable(Integer.valueOf(OptionWizardPanel.this.modelSpot.getRowCount()));
/* 506 */             if (rt != null) {
/* 507 */               rt.reset();
/*     */             }
/* 509 */             for (int i = 0; i < OptionWizardPanel.this.modelSpot.getRowCount(); i++) {
/* 510 */               for (int k = 0; k < OptionWizardPanel.this.modelSpot.getColumnCount(); k++)
/* 511 */                 rt.setValue(columnSpotHead.get(k), i, OptionWizardPanel.this.modelSpot.getValueAt(i, k).toString()); 
/* 512 */             }  JFrame pngFrame = new JFrame();
/* 513 */             JFileChooser fileChooser = new JFileChooser();
/* 514 */             fileChooser.setFileSelectionMode(1);
/* 515 */             fileChooser.setDialogTitle("Specify a directory to save csv file");
/* 516 */             int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */             
/* 518 */             if (userSelection == 0) {
/* 519 */               File fileToSave = fileChooser.getSelectedFile();
/*     */               try {
/* 521 */                 rt.saveAs(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + "SpotStatistics for-" + 
/* 522 */                     IJ.getImage().getShortTitle() + ".csv");
/* 523 */               } catch (IOException e1) {
/*     */                 
/* 525 */                 e1.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 533 */     this.refreshButtonSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 537 */             OptionWizardPanel.this.refreshSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 539 */                     OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).refreshActionSpot();
/*     */                   }
/*     */                 });
/* 542 */             OptionWizardPanel.this.refreshSpotThread.start();
/*     */           }
/*     */         });
/* 545 */     this.refreshButtonTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 549 */             OptionWizardPanel.this.refreshTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 551 */                     OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).refreshActionTrack();
/*     */                   }
/*     */                 });
/* 554 */             OptionWizardPanel.this.refreshTrackThread.start();
/*     */           }
/*     */         });
/* 557 */     this.zoomInSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 560 */             OptionWizardPanel.this.zoomInSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 562 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
/* 563 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotSpot);
/*     */                   }
/*     */                 });
/* 566 */             OptionWizardPanel.this.zoomInSpotThread.start();
/*     */           }
/*     */         });
/* 569 */     this.zoomOutSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 572 */             OptionWizardPanel.this.zoomOutSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 574 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutSpot.setActionCommand("ZOOM_OUT_BOTH");
/* 575 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutSpot.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotSpot);
/*     */                   }
/*     */                 });
/* 578 */             OptionWizardPanel.this.zoomOutSpotThread.start();
/*     */           }
/*     */         });
/* 581 */     this.zoomInTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 584 */             OptionWizardPanel.this.zoomInTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 586 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
/* 587 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotTrack);
/*     */                   }
/*     */                 });
/* 590 */             OptionWizardPanel.this.zoomInTrackThread.start();
/*     */           }
/*     */         });
/* 593 */     this.zoomOutTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 596 */             OptionWizardPanel.this.zoomOutTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 598 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutTrack.setActionCommand("ZOOM_OUT_BOTH");
/* 599 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutTrack.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotTrack);
/*     */                   }
/*     */                 });
/* 602 */             OptionWizardPanel.this.zoomOutTrackThread.start();
/*     */           }
/*     */         });
/* 605 */     this.comboRegressionSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 608 */             OptionWizardPanel.this.comboRegSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 610 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionSpot.getSelectedIndex() == 1)
/* 611 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderSpot.setEnabled(true); 
/* 612 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionSpot.getSelectedIndex() != 1)
/* 613 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderSpot.setEnabled(false); 
/*     */                   }
/*     */                 });
/* 616 */             OptionWizardPanel.this.comboRegSpotThread.start();
/*     */           }
/*     */         });
/* 619 */     this.comboRegressionTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 622 */             OptionWizardPanel.this.comboRegTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 624 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionTrack.getSelectedIndex() == 1)
/* 625 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderTrack.setEnabled(true); 
/* 626 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionTrack.getSelectedIndex() != 1)
/* 627 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderTrack.setEnabled(false); 
/*     */                   }
/*     */                 });
/* 630 */             OptionWizardPanel.this.comboRegTrackThread.start();
/*     */           }
/*     */         });
/* 633 */     this.comboClassSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 636 */             OptionWizardPanel.this.comboClassSpotThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 639 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedIndex() == 0) {
/* 640 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot = new ArrayList<>();
/* 641 */                       for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/* 642 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, 
/* 643 */                             FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/* 644 */                           (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot
/* 645 */                                 .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamSpot.getSelectedIndex() + 3).toString())); 
/* 646 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionSpot();
/*     */                     } 
/* 648 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedIndex() == 1) {
/* 649 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot = new ArrayList<>();
/* 650 */                       for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/* 651 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, 
/* 652 */                             FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/* 653 */                           if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0)).getText() == "")
/* 654 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot
/* 655 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamSpot.getSelectedIndex() + 3).toString()));  
/* 656 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionSpot();
/*     */                     } 
/*     */                     
/* 659 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedIndex() == 2) {
/* 660 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot = new ArrayList<>();
/* 661 */                       for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/* 662 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, 
/* 663 */                             FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/* 664 */                           if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0))
/* 665 */                             .getText() == (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedItem().toString())
/* 666 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot
/* 667 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamSpot.getSelectedIndex() + 3).toString()));  
/* 668 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionSpot();
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */             
/* 673 */             OptionWizardPanel.this.comboClassSpotThread.start();
/*     */           }
/*     */         });
/*     */     
/* 677 */     this.comboClassTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 680 */             OptionWizardPanel.this.comboClassTrackThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 683 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedIndex() == 0) {
/* 684 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack = new ArrayList<>();
/* 685 */                       for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 686 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, 
/* 687 */                             ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/* 688 */                           (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack
/* 689 */                                 .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamTrack.getSelectedIndex() + 3).toString())); 
/* 690 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionTrack();
/*     */                     } 
/* 692 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedIndex() == 1) {
/* 693 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack = new ArrayList<>();
/* 694 */                       for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 695 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, 
/* 696 */                             ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/* 697 */                           if (((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0)).getText() == "")
/* 698 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack
/* 699 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamTrack.getSelectedIndex() + 3).toString()));  
/* 700 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionTrack();
/*     */                     } 
/*     */                     
/* 703 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedIndex() == 2) {
/* 704 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack = new ArrayList<>();
/* 705 */                       for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 706 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, 
/* 707 */                             ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/* 708 */                           if (((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0))
/* 709 */                             .getText() == (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedItem().toString())
/* 710 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack
/* 711 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamTrack.getSelectedIndex() + 3).toString()));  
/* 712 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionTrack();
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */ 
/*     */             
/* 718 */             OptionWizardPanel.this.comboClassTrackThread.start();
/*     */           }
/*     */         });
/*     */   }
/*     */   JComboBox<String> comboFeatureDomainTrack; JComboBox<String> comboFeatureRangeTrack; JComboBox<String> comboClassSpot; JComboBox<String> comboParamSpot; JComboBox<String> comboClassTrack; JComboBox<String> comboParamTrack; JComboBox<String> comboRegressionSpot; JComboBox<String> comboRegressionTrack; List<String> itemFiltersSpot; List<String> itemFiltersTrack; STScatterPlot scatterPlot; JButton refreshButtonSpot; JButton zoomInSpot; JButton zoomOutSpot; JButton refreshButtonTrack; JButton zoomInTrack; JButton zoomOutTrack; STScatterPlot sp2Spot; STScatterPlot sp2Track; ChartPanel scatterPlotSpot; ChartPanel scatterPlotTrack; static JPanel regressionPanelSpot; static JPanel regressionPanelTrack; List<Double> dataToStatisticsSpot; List<Double> dataToStatisticsTrack; DefaultTableModel modelSpot; DefaultTableModel modelTrack; Thread refreshSpotThread; Thread refreshTrackThread; Thread zoomInSpotThread; Thread zoomInTrackThread; Thread zoomOutSpotThread; Thread zoomOutTrackThread; Thread comboRegSpotThread; Thread comboRegTrackThread; Thread comboClassSpotThread;
/*     */   Thread comboClassTrackThread;
/*     */   
/*     */   public void descriptiveStatisticsActionSpot() {
/* 726 */     this.modelSpot.setValueAt(String.valueOf(
/* 727 */           Math.round((new DescriptiveStatistics(
/* 728 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMean() * 1000.0D) / 
/* 729 */           1000.0D), 0, 0);
/* 730 */     this.modelSpot.setValueAt(String.valueOf(Math.round((new DescriptiveStatistics(
/* 731 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() / (
/* 732 */             new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()))
/* 733 */             .getN() * 
/* 734 */             1000.0D) / 1000.0D), 0, 1);
/*     */     
/* 736 */     this.modelSpot.setValueAt(String.valueOf(Math.round((
/* 737 */             new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()))
/* 738 */             .getPercentile(50.0D) * 1000.0D) / 
/* 739 */           1000.0D), 0, 2);
/* 740 */     this.modelSpot.setValueAt(String.valueOf(Math.round((
/* 741 */             new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()))
/* 742 */             .getStandardDeviation() * 1000.0D) / 
/* 743 */           1000.0D), 0, 3);
/* 744 */     this.modelSpot.setValueAt(String.valueOf(
/* 745 */           Math.round((new DescriptiveStatistics(
/* 746 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getVariance() * 1000.0D) / 
/* 747 */           1000.0D), 0, 4);
/* 748 */     this.modelSpot.setValueAt(String.valueOf(
/* 749 */           Math.round((new DescriptiveStatistics(
/* 750 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getKurtosis() * 1000.0D) / 
/* 751 */           1000.0D), 0, 5);
/* 752 */     this.modelSpot.setValueAt(String.valueOf(
/* 753 */           Math.round((new DescriptiveStatistics(
/* 754 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getSkewness() * 1000.0D) / 
/* 755 */           1000.0D), 0, 6);
/* 756 */     this.modelSpot.setValueAt(String.valueOf(
/* 757 */           Math.round((new DescriptiveStatistics(
/* 758 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMin() * 1000.0D) / 
/* 759 */           1000.0D), 0, 7);
/* 760 */     this.modelSpot.setValueAt(String.valueOf(
/* 761 */           Math.round((new DescriptiveStatistics(
/* 762 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMax() * 1000.0D) / 
/* 763 */           1000.0D), 0, 8);
/* 764 */     this.modelSpot.setValueAt(String.valueOf(
/* 765 */           Math.round((new DescriptiveStatistics(
/* 766 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getSum() * 1000.0D) / 
/* 767 */           1000.0D), 0, 9);
/*     */     
/* 769 */     this.modelSpot.setValueAt(String.valueOf(
/* 770 */           Math.round((new DescriptiveStatistics(
/* 771 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 
/* 772 */           1000.0D), 0, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void descriptiveStatisticsActionTrack() {
/* 778 */     this.modelTrack.setValueAt(String.valueOf(
/* 779 */           Math.round((new DescriptiveStatistics(
/* 780 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMean() * 1000.0D) / 
/* 781 */           1000.0D), 0, 0);
/* 782 */     this.modelTrack.setValueAt(String.valueOf(Math.round((new DescriptiveStatistics(
/* 783 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() / (
/* 784 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 785 */             .getN() * 
/* 786 */             1000.0D) / 1000.0D), 0, 1);
/*     */     
/* 788 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 789 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 790 */             .getPercentile(50.0D) * 1000.0D) / 
/* 791 */           1000.0D), 0, 2);
/* 792 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 793 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 794 */             .getStandardDeviation() * 1000.0D) / 
/* 795 */           1000.0D), 0, 3);
/* 796 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 797 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 798 */             .getVariance() * 1000.0D) / 
/* 799 */           1000.0D), 0, 4);
/* 800 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 801 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 802 */             .getKurtosis() * 1000.0D) / 
/* 803 */           1000.0D), 0, 5);
/* 804 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 805 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 806 */             .getSkewness() * 1000.0D) / 
/* 807 */           1000.0D), 0, 6);
/* 808 */     this.modelTrack.setValueAt(String.valueOf(
/* 809 */           Math.round((new DescriptiveStatistics(
/* 810 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMin() * 1000.0D) / 
/* 811 */           1000.0D), 0, 7);
/* 812 */     this.modelTrack.setValueAt(String.valueOf(
/* 813 */           Math.round((new DescriptiveStatistics(
/* 814 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMax() * 1000.0D) / 
/* 815 */           1000.0D), 0, 8);
/* 816 */     this.modelTrack.setValueAt(String.valueOf(
/* 817 */           Math.round((new DescriptiveStatistics(
/* 818 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getSum() * 1000.0D) / 
/* 819 */           1000.0D), 0, 9);
/*     */     
/* 821 */     this.modelTrack.setValueAt(String.valueOf(
/* 822 */           Math.round((new DescriptiveStatistics(
/* 823 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 
/* 824 */           1000.0D), 0, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshActionSpot() {
/* 830 */     this.comboClassSpot.removeAllItems();
/* 831 */     this.comboClassSpot.addItem("All items");
/* 832 */     this.comboClassSpot.addItem("No Identified");
/*     */     
/* 834 */     if (ColorEditorSpot.modelC.getRowCount() > 0) {
/* 835 */       for (int j = 0; j < ColorEditorSpot.modelC.getRowCount(); j++) {
/* 836 */         this.comboClassSpot.addItem(((JLabel)ColorEditorSpot.modelC.getValueAt(j, 0)).getText());
/*     */       }
/*     */     }
/* 839 */     int rowCount = FirstWizardPanel.tableSpot.getRowCount();
/* 840 */     int columnCount = FirstWizardPanel.tableSpot.getColumnCount();
/* 841 */     selectedIndexDomainSpot = this.comboFeatureDomainSpot.getSelectedIndex();
/* 842 */     selectedIndexRangeSpot = this.comboFeatureRangeSpot.getSelectedIndex();
/* 843 */     List<Double> valuesDomainSpot = new ArrayList<>();
/* 844 */     List<Double> valuesRangeSpot = new ArrayList<>();
/* 845 */     Object[][] dataSpot = new Object[rowCount][columnCount];
/* 846 */     for (int i = 0; i < rowCount; i++) {
/* 847 */       for (int j = 0; j < columnCount; j++) {
/* 848 */         dataSpot[i][j] = FirstWizardPanel.tableSpot.getValueAt(i, j);
/*     */       }
/*     */       
/* 851 */       valuesDomainSpot.add(Double.valueOf(Double.parseDouble(dataSpot[i][selectedIndexDomainSpot + 4].toString())));
/* 852 */       valuesRangeSpot.add(Double.valueOf(Double.parseDouble(dataSpot[i][selectedIndexRangeSpot + 4].toString())));
/*     */     } 
/* 854 */     if (valuesDomainSpot.isEmpty() == Boolean.TRUE.booleanValue()) {
/* 855 */       IJ.error("You should have your spot analysis done. Please go backwards.");
/*     */     } else {
/*     */       
/* 858 */       this.maxDomainSpot = ((Double)Collections.<Double>max(valuesDomainSpot)).doubleValue();
/* 859 */       this.maxRangeSpot = ((Double)Collections.<Double>max(valuesRangeSpot)).doubleValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 865 */       List<Color> listColorSpot = new ArrayList<>();
/* 866 */       for (int j = 0; j < FirstWizardPanel.modelSpot.getRowCount(); j++)
/* 867 */         listColorSpot.add(((JLabel)FirstWizardPanel.modelSpot.getValueAt(j, 
/* 868 */               FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getBackground()); 
/* 869 */       Color[] classColorSpot = new Color[listColorSpot.size()];
/* 870 */       listColorSpot.toArray(classColorSpot);
/* 871 */       if (this.comboRegressionSpot.getSelectedIndex() == 0)
/* 872 */         this.sp2Spot.addScatterPlotSeriesLinear(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 873 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 874 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot); 
/* 875 */       if (this.comboRegressionSpot.getSelectedIndex() == 1)
/* 876 */         this.sp2Spot.addScatterPlotSeriesPolynomial(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 877 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 878 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot, ((Integer)this.filterOrderSpot.getValue()).intValue()); 
/* 879 */       if (this.comboRegressionSpot.getSelectedIndex() == 2)
/* 880 */         this.sp2Spot.addScatterPlotSeriesPower(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 881 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 882 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot); 
/* 883 */       if (this.comboRegressionSpot.getSelectedIndex() == 3)
/* 884 */         this.sp2Spot.addScatterPlotSeriesLogarithmic(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 885 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 886 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot); 
/* 887 */       if (this.comboRegressionSpot.getSelectedIndex() == 4) {
/* 888 */         this.sp2Spot.addScatterPlotSeriesExponential(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 889 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 890 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshActionTrack() {
/* 896 */     this.comboClassTrack.removeAllItems();
/* 897 */     this.comboClassTrack.addItem("All items");
/* 898 */     this.comboClassTrack.addItem("No Identified");
/*     */     
/* 900 */     if (ColorEditorTrack.modelC.getRowCount() > 0) {
/* 901 */       for (int j = 0; j < ColorEditorTrack.modelC.getRowCount(); j++) {
/* 902 */         this.comboClassTrack.addItem(((JLabel)ColorEditorTrack.modelC.getValueAt(j, 0)).getText());
/*     */       }
/*     */     }
/* 905 */     int rowCount = ChooserWizardPanel.tableTrack.getRowCount();
/* 906 */     int columnCount = ChooserWizardPanel.tableTrack.getColumnCount();
/* 907 */     selectedIndexDomainTrack = this.comboFeatureDomainTrack.getSelectedIndex();
/* 908 */     selectedIndexRangeTrack = this.comboFeatureRangeTrack.getSelectedIndex();
/* 909 */     List<Double> valuesDomainTrack = new ArrayList<>();
/* 910 */     List<Double> valuesRangeTrack = new ArrayList<>();
/* 911 */     Object[][] dataTrack = new Object[rowCount][columnCount];
/* 912 */     for (int i = 0; i < rowCount; i++) {
/* 913 */       for (int j = 0; j < columnCount; j++) {
/* 914 */         dataTrack[i][j] = ChooserWizardPanel.tableTrack.getValueAt(i, j);
/*     */       }
/*     */       
/* 917 */       valuesDomainTrack.add(Double.valueOf(Double.parseDouble(dataTrack[i][selectedIndexDomainTrack + 4].toString())));
/* 918 */       valuesRangeTrack.add(Double.valueOf(Double.parseDouble(dataTrack[i][selectedIndexRangeTrack + 4].toString())));
/*     */     } 
/* 920 */     if (valuesDomainTrack.isEmpty() == Boolean.TRUE.booleanValue()) {
/* 921 */       IJ.error("You should have your track analysis done. Please go backwards.");
/*     */     } else {
/*     */       
/* 924 */       this.maxDomainTrack = ((Double)Collections.<Double>max(valuesDomainTrack)).doubleValue();
/* 925 */       this.maxRangeTrack = ((Double)Collections.<Double>max(valuesRangeTrack)).doubleValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 931 */       List<Color> listColorTrack = new ArrayList<>();
/* 932 */       for (int j = 0; j < ChooserWizardPanel.modelTrack.getRowCount(); j++)
/* 933 */         listColorTrack.add(((JLabel)ChooserWizardPanel.modelTrack.getValueAt(j, 
/* 934 */               ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1))).getBackground()); 
/* 935 */       Color[] classColorTrack = new Color[listColorTrack.size()];
/* 936 */       listColorTrack.toArray(classColorTrack);
/* 937 */       if (this.comboRegressionTrack.getSelectedIndex() == 0)
/* 938 */         this.sp2Track.addScatterPlotSeriesLinear(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 939 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 940 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/* 941 */       if (this.comboRegressionTrack.getSelectedIndex() == 1)
/* 942 */         this.sp2Track.addScatterPlotSeriesPolynomial(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 943 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 944 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack, (
/* 945 */             (Integer)this.filterOrderTrack.getValue()).intValue()); 
/* 946 */       if (this.comboRegressionTrack.getSelectedIndex() == 2)
/* 947 */         this.sp2Track.addScatterPlotSeriesPower(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 948 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 949 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/* 950 */       if (this.comboRegressionTrack.getSelectedIndex() == 3)
/* 951 */         this.sp2Track.addScatterPlotSeriesLogarithmic(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 952 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 953 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/* 954 */       if (this.comboRegressionTrack.getSelectedIndex() == 4)
/* 955 */         this.sp2Track.addScatterPlotSeriesExponential(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 956 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 957 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/*     */     } 
/*     */   }
/*     */   public ImageIcon createImageIcon(String path) {
/* 961 */     URL img = getClass().getResource(path);
/*     */     
/* 963 */     if (img != null) {
/* 964 */       return new ImageIcon(img);
/*     */     }
/* 966 */     System.err.println("Couldn't find file: " + path);
/* 967 */     return null;
/*     */   }
/*     */   
/*     */   public void update1() {
/* 971 */     setNextButtonEnabled(true);
/* 972 */     setFinishButtonEnabled(true);
/* 973 */     setBackButtonEnabled(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void next() {
/* 978 */     setNextButtonEnabled(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void back() {
/* 983 */     switchPanel(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() {
/* 988 */     switchPanel(2);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/OptionWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */