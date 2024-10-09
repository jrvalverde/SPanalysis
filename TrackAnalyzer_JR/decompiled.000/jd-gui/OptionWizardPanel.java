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
/*  93 */     super(wizardComponents, "");
/*  94 */     setPanelTitle("");
/*  95 */     setLayout(new BoxLayout((Container)this, 1));
/*     */     
/*  97 */     JPanel chartPanel2Spot = new JPanel();
/*  98 */     this.markerRangeSpot = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/*  99 */         new BasicStroke(1.5F), 0.6F);
/* 100 */     this.markerDomainSpot = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/* 101 */         new BasicStroke(1.5F), 0.5F);
/* 102 */     this.markerRangeTrack = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/* 103 */         new BasicStroke(1.5F), 0.6F);
/* 104 */     this.markerDomainTrack = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), 
/* 105 */         new BasicStroke(1.5F), 0.5F);
/* 106 */     this.sp2Spot = new STScatterPlot("");
/* 107 */     this.scatterPlotSpot = this.sp2Spot.createScatterChartPanelInitial("", "", new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D)
/* 108 */             }, )), new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D) }, )), this.markerRangeSpot, this.markerDomainSpot, 
/* 109 */         (Object[][])new Double[][] { { Double.valueOf(0.0D) }, , { Double.valueOf(0.0D) },  }, new Double[][] { { Double.valueOf(0.0D) }, { Double.valueOf(0.0D) } });
/* 110 */     this.refreshButtonSpot = new JButton("");
/* 111 */     this.refreshButtonSpot.setIcon(FirstWizardPanel.refreshCell);
/* 112 */     this.refreshButtonSpot.setToolTipText("Click this button to refresh scatter-plot.");
/* 113 */     this.zoomInSpot = new JButton("");
/* 114 */     ImageIcon iconZoomIn = FirstWizardPanel.createImageIcon("images/zoomin.png");
/* 115 */     Icon zoomInCell = new ImageIcon(iconZoomIn.getImage().getScaledInstance(18, 20, 4));
/* 116 */     this.zoomInSpot.setIcon(zoomInCell);
/* 117 */     this.zoomInSpot.setToolTipText("Click this button to zoom in Chart");
/* 118 */     this.zoomOutSpot = new JButton("");
/* 119 */     ImageIcon iconZoomOut = FirstWizardPanel.createImageIcon("images/zoomout.png");
/* 120 */     Icon zoomOutCell = new ImageIcon(iconZoomOut.getImage().getScaledInstance(18, 20, 4));
/* 121 */     this.zoomOutSpot.setIcon(zoomOutCell);
/* 122 */     this.zoomOutSpot.setToolTipText("Click this button to zoom out Chart");
/* 123 */     this.itemFiltersSpot = new ArrayList<>();
/* 124 */     this.itemFiltersTrack = new ArrayList<>(); int i;
/* 125 */     for (i = 2; i < FirstWizardPanel.columnNamesSpot.length; i++)
/* 126 */       this.itemFiltersSpot.add(FirstWizardPanel.columnNamesSpot[i].toString()); 
/* 127 */     for (i = 0; i < ChooserWizardPanel.columnNamesTrack.length; i++)
/* 128 */       this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[i].toString()); 
/* 129 */     this.comboFeatureDomainSpot = new JComboBox<>();
/* 130 */     this.comboFeatureDomainSpot.setPreferredSize(new Dimension(110, 20));
/* 131 */     for (i = 0; i < this.itemFiltersSpot.size(); i++)
/* 132 */       this.comboFeatureDomainSpot.addItem(this.itemFiltersSpot.get(i)); 
/* 133 */     this.comboFeatureDomainSpot.setOpaque(true);
/* 134 */     this.scatterPlot = new STScatterPlot("holaa");
/* 135 */     chartPanel2Spot.add((Component)this.scatterPlotSpot);
/* 136 */     this.filterOrderSpot = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
/* 137 */     this.filterOrderSpot.setPreferredSize(new Dimension(60, 20));
/* 138 */     this.filterOrderSpot.setEnabled(false);
/*     */ 
/*     */ 
/*     */     
/* 142 */     JPanel filtersMaxSpot = new JPanel(new FlowLayout(1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     filtersMaxSpot.add(Box.createHorizontalStrut(2));
/*     */ 
/*     */     
/* 150 */     this.comboFeatureRangeSpot = new JComboBox<>();
/* 151 */     this.comboFeatureRangeSpot.setPreferredSize(new Dimension(110, 20));
/* 152 */     for (int j = 0; j < this.itemFiltersSpot.size(); j++)
/* 153 */       this.comboFeatureRangeSpot.addItem(this.itemFiltersSpot.get(j)); 
/* 154 */     this.comboFeatureRangeSpot.setOpaque(true);
/* 155 */     filtersMaxSpot.add(new JLabel("X :  "));
/* 156 */     filtersMaxSpot.add(this.comboFeatureDomainSpot);
/* 157 */     filtersMaxSpot.add(new JLabel("   Y :  "));
/* 158 */     filtersMaxSpot.add(this.comboFeatureRangeSpot);
/* 159 */     JPanel rangePanelFSpot = new JPanel(new FlowLayout(0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     JPanel chartDomainPanelBoxSpot = new JPanel();
/* 168 */     chartDomainPanelBoxSpot.setLayout(new BoxLayout(chartDomainPanelBoxSpot, 1));
/* 169 */     chartDomainPanelBoxSpot.add(Box.createVerticalStrut(10));
/* 170 */     chartDomainPanelBoxSpot.add(chartPanel2Spot);
/* 171 */     chartDomainPanelBoxSpot.add(filtersMaxSpot);
/*     */ 
/*     */ 
/*     */     
/* 175 */     rangePanelFSpot.add(chartDomainPanelBoxSpot);
/* 176 */     JPanel buttonBox = new JPanel();
/* 177 */     buttonBox.setLayout(new BoxLayout(buttonBox, 1));
/* 178 */     JPanel refreshButtonPanelSpot = new JPanel(new FlowLayout(0));
/* 179 */     refreshButtonPanelSpot.add(this.refreshButtonSpot);
/* 180 */     JPanel zoomOutButtonPanelSpot = new JPanel(new FlowLayout(0));
/* 181 */     zoomOutButtonPanelSpot.add(this.zoomOutSpot);
/* 182 */     JPanel zoomInButtonPanelSpot = new JPanel(new FlowLayout(0));
/* 183 */     zoomInButtonPanelSpot.add(this.zoomInSpot);
/* 184 */     buttonBox.add(zoomInButtonPanelSpot);
/* 185 */     buttonBox.add(zoomOutButtonPanelSpot);
/* 186 */     buttonBox.add(refreshButtonPanelSpot);
/* 187 */     this.comboRegressionSpot = new JComboBox<>();
/* 188 */     this.comboRegressionSpot.setPreferredSize(new Dimension(90, 20));
/* 189 */     this.comboRegressionSpot.addItem("Linear");
/* 190 */     this.comboRegressionSpot.addItem("Polynomial");
/* 191 */     this.comboRegressionSpot.addItem("Power");
/* 192 */     this.comboRegressionSpot.addItem("Logarithmic");
/* 193 */     this.comboRegressionSpot.addItem("Exponential");
/*     */     
/* 195 */     this.comboRegressionSpot.setSelectedIndex(0);
/* 196 */     this.comboRegressionSpot.setOpaque(true);
/* 197 */     JPanel regreOrderPanel = new JPanel(new FlowLayout(0));
/* 198 */     regreOrderPanel.add(this.comboRegressionSpot);
/* 199 */     JPanel filterOrderPanel = new JPanel(new FlowLayout(0));
/* 200 */     filterOrderPanel.add(this.filterOrderSpot);
/* 201 */     buttonBox.add(regreOrderPanel);
/* 202 */     buttonBox.add(filterOrderPanel);
/* 203 */     regressionPanelSpot = new JPanel();
/* 204 */     regressionPanelSpot.setBorder(BorderFactory.createTitledBorder("Reg.Params"));
/* 205 */     regressionPanelSpot.setPreferredSize(new Dimension(this.comboRegressionSpot.getWidth() + 10, 35));
/* 206 */     rangePanelFSpot.add(buttonBox);
/* 207 */     JPanel spotPanel = new JPanel();
/* 208 */     spotPanel.add(rangePanelFSpot);
/* 209 */     spotPanel.setBorder(BorderFactory.createTitledBorder(""));
/*     */     
/* 211 */     JPanel spotStatistics = new JPanel();
/* 212 */     spotStatistics.setLayout(new BoxLayout(spotStatistics, 1));
/* 213 */     JPanel spotStatisticsFlow = new JPanel(new FlowLayout(0));
/* 214 */     JPanel classParamSpot = new JPanel(new FlowLayout(0));
/* 215 */     this.comboClassSpot = new JComboBox<>();
/* 216 */     this.comboClassSpot.setPreferredSize(new Dimension(120, 20)); int k;
/* 217 */     for (k = 2; k < FirstWizardPanel.columnNamesSpot.length; k++)
/* 218 */       this.comboClassSpot.addItem((String)FirstWizardPanel.columnNamesSpot[k]); 
/* 219 */     this.comboClassSpot.setOpaque(true);
/* 220 */     this.comboClassSpot.setToolTipText("Choose selected items for descriptive statistics.");
/* 221 */     this.comboParamSpot = new JComboBox<>();
/* 222 */     this.comboParamSpot.setPreferredSize(new Dimension(120, 20));
/* 223 */     for (k = 2; k < FirstWizardPanel.columnNamesSpot.length; k++)
/* 224 */       this.comboParamSpot.addItem((String)FirstWizardPanel.columnNamesSpot[k]); 
/* 225 */     this.comboParamSpot.setOpaque(true);
/* 226 */     this.comboParamSpot.setToolTipText("Choose a spot parameter for descriptive statistics.");
/* 227 */     classParamSpot.add(this.comboClassSpot);
/* 228 */     classParamSpot.add(this.comboParamSpot);
/* 229 */     labelScoresSpot = new JLabel("SCORES");
/* 230 */     labelScoresSpot.setFont(new Font("Verdana", 1, 20));
/* 231 */     spotStatisticsFlow.add(labelScoresSpot);
/* 232 */     spotStatistics.add(Box.createVerticalStrut(60));
/* 233 */     spotStatistics.add(spotStatisticsFlow);
/* 234 */     spotStatistics.add(new JSeparator(0));
/* 235 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 236 */     spotStatistics.add(classParamSpot);
/* 237 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 238 */     spotStatistics.add(new JSeparator(0));
/* 239 */     String[][] data = { { "", "", "", "", "", "", "", "", "", "", "" } };
/* 240 */     String[] column = { "Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", "Min ", 
/* 241 */         "Max ", "Sum ", "Count " };
/*     */     
/* 243 */     JTable table = new JTable();
/* 244 */     this.modelSpot = new DefaultTableModel((Object[][])data, (Object[])column)
/*     */       {
/*     */         public Class<?> getColumnClass(int column)
/*     */         {
/* 248 */           if (getRowCount() > 0) {
/* 249 */             Object value = getValueAt(0, column);
/* 250 */             if (value != null) {
/* 251 */               return getValueAt(0, column).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 255 */           return super.getColumnClass(column);
/*     */         }
/*     */         
/*     */         public boolean isCellEditable(int row, int col) {
/* 259 */           return false;
/*     */         }
/*     */       };
/* 262 */     table.setAutoResizeMode(0);
/* 263 */     table.setModel(this.modelSpot);
/* 264 */     table.setRowHeight(60);
/* 265 */     JScrollPane sp = new JScrollPane(table);
/* 266 */     sp.setPreferredSize(new Dimension(500, 100));
/* 267 */     sp.setVerticalScrollBarPolicy(22);
/* 268 */     sp.setHorizontalScrollBarPolicy(32);
/* 269 */     for (int u = 0; u < table.getColumnCount(); u++) {
/* 270 */       table.getColumnModel().getColumn(u).setMinWidth(100);
/* 271 */       table.getColumnModel().getColumn(u).setMaxWidth(100);
/* 272 */       table.getColumnModel().getColumn(u).setPreferredWidth(100);
/*     */     } 
/*     */     
/* 275 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 276 */     spotStatistics.add(sp);
/* 277 */     spotStatistics.add(Box.createVerticalStrut(20));
/* 278 */     spotStatistics.add(new JSeparator(0));
/* 279 */     JButton plotButton = new JButton();
/* 280 */     ImageIcon iconPlot = FirstWizardPanel.createImageIcon("images/plot.jpg");
/* 281 */     Icon plotCell = new ImageIcon(iconPlot.getImage().getScaledInstance(18, 20, 4));
/* 282 */     plotButton.setIcon(plotCell);
/* 283 */     plotButton.setToolTipText("Click to export scatter plot.");
/* 284 */     JPanel panelPlot = new JPanel(new FlowLayout(0));
/* 285 */     panelPlot.add(plotButton);
/* 286 */     JButton csvButton = new JButton();
/* 287 */     ImageIcon iconCsv = FirstWizardPanel.createImageIcon("images/csv.png");
/* 288 */     Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
/* 289 */     csvButton.setIcon(csvCell);
/* 290 */     csvButton.setToolTipText("Click to export your spots statistics table.");
/* 291 */     JPanel panelCsv = new JPanel(new FlowLayout(0));
/* 292 */     panelCsv.add(csvButton);
/* 293 */     JPanel panelPngCsv = new JPanel(new FlowLayout(0));
/* 294 */     panelPngCsv.add(panelPlot);
/* 295 */     panelPngCsv.add(panelCsv);
/* 296 */     spotStatistics.add(panelPngCsv);
/* 297 */     spotPanel.add(spotStatistics);
/*     */ 
/*     */ 
/*     */     
/* 301 */     JPanel chartPanel2Track = new JPanel();
/* 302 */     this.sp2Track = new STScatterPlot("");
/* 303 */     this.scatterPlotTrack = this.sp2Track.createScatterChartPanelInitial("", "", 
/* 304 */         new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D) }, )), new ArrayList<>(Arrays.asList(new Double[] { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D)
/* 305 */             }, )), this.markerRangeTrack, this.markerDomainTrack, (Object[][])new Double[][] { { Double.valueOf(0.0D) }, , { Double.valueOf(0.0D)
/* 306 */           },  }, new Double[][] { { Double.valueOf(0.0D) }, { Double.valueOf(0.0D) } });
/* 307 */     this.refreshButtonTrack = new JButton("");
/* 308 */     this.refreshButtonTrack.setIcon(FirstWizardPanel.refreshCell);
/* 309 */     this.refreshButtonTrack.setToolTipText("Click this button to refresh scatter-plot.");
/* 310 */     this.zoomInTrack = new JButton("");
/* 311 */     this.zoomInTrack.setIcon(zoomInCell);
/* 312 */     this.zoomInTrack.setToolTipText("Click this button to zoom in Chart");
/* 313 */     this.zoomOutTrack = new JButton("");
/* 314 */     this.zoomOutTrack.setIcon(zoomOutCell);
/* 315 */     this.zoomOutTrack.setToolTipText("Click this button to zoom out Chart"); int m;
/* 316 */     for (m = 0; m < ChooserWizardPanel.columnNamesTrack.length; m++)
/* 317 */       this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[m].toString()); 
/* 318 */     this.comboFeatureDomainTrack = new JComboBox<>();
/* 319 */     this.comboFeatureDomainTrack.setPreferredSize(new Dimension(110, 20));
/* 320 */     for (m = 3; m < this.itemFiltersTrack.size(); m++)
/* 321 */       this.comboFeatureDomainTrack.addItem(this.itemFiltersTrack.get(m)); 
/* 322 */     this.comboFeatureDomainTrack.setOpaque(true);
/*     */     
/* 324 */     chartPanel2Track.add((Component)this.scatterPlotTrack);
/* 325 */     this.filterOrderTrack = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
/* 326 */     this.filterOrderTrack.setPreferredSize(new Dimension(60, 20));
/* 327 */     this.filterOrderTrack.setEnabled(false);
/* 328 */     JPanel filtersMaxTrack = new JPanel(new FlowLayout(1));
/* 329 */     filtersMaxTrack.add(Box.createHorizontalStrut(2));
/*     */     
/* 331 */     this.comboFeatureRangeTrack = new JComboBox<>();
/* 332 */     this.comboFeatureRangeTrack.setPreferredSize(new Dimension(110, 20));
/* 333 */     for (int n = 3; n < this.itemFiltersTrack.size(); n++)
/* 334 */       this.comboFeatureRangeTrack.addItem(this.itemFiltersTrack.get(n)); 
/* 335 */     this.comboFeatureRangeTrack.setOpaque(true);
/* 336 */     filtersMaxTrack.add(new JLabel("X :  "));
/* 337 */     filtersMaxTrack.add(this.comboFeatureDomainTrack);
/* 338 */     filtersMaxTrack.add(new JLabel("   Y :  "));
/* 339 */     filtersMaxTrack.add(this.comboFeatureRangeTrack);
/* 340 */     JPanel rangePanelFTrack = new JPanel(new FlowLayout(0));
/* 341 */     JPanel chartDomainPanelBoxTrack = new JPanel();
/* 342 */     chartDomainPanelBoxTrack.setLayout(new BoxLayout(chartDomainPanelBoxTrack, 1));
/* 343 */     chartDomainPanelBoxTrack.add(Box.createVerticalStrut(10));
/* 344 */     chartDomainPanelBoxTrack.add(chartPanel2Track);
/* 345 */     chartDomainPanelBoxTrack.add(filtersMaxTrack);
/* 346 */     rangePanelFTrack.add(chartDomainPanelBoxTrack);
/* 347 */     JPanel buttonBoxTrack = new JPanel();
/* 348 */     buttonBoxTrack.setLayout(new BoxLayout(buttonBoxTrack, 1));
/* 349 */     JPanel refreshButtonPanelTrack = new JPanel(new FlowLayout(0));
/* 350 */     refreshButtonPanelTrack.add(this.refreshButtonTrack);
/* 351 */     JPanel zoomOutButtonPanelTrack = new JPanel(new FlowLayout(0));
/* 352 */     zoomOutButtonPanelTrack.add(this.zoomOutTrack);
/* 353 */     JPanel zoomInButtonPanelTrack = new JPanel(new FlowLayout(0));
/* 354 */     zoomInButtonPanelTrack.add(this.zoomInTrack);
/* 355 */     buttonBoxTrack.add(zoomInButtonPanelTrack);
/* 356 */     buttonBoxTrack.add(zoomOutButtonPanelTrack);
/* 357 */     buttonBoxTrack.add(refreshButtonPanelTrack);
/* 358 */     this.comboRegressionTrack = new JComboBox<>();
/* 359 */     this.comboRegressionTrack.setPreferredSize(new Dimension(90, 20));
/* 360 */     this.comboRegressionTrack.addItem("Linear");
/* 361 */     this.comboRegressionTrack.addItem("Polynomial");
/* 362 */     this.comboRegressionTrack.addItem("Power");
/* 363 */     this.comboRegressionTrack.addItem("Logarithmic");
/* 364 */     this.comboRegressionTrack.addItem("Exponential");
/*     */     
/* 366 */     this.comboRegressionTrack.setSelectedIndex(0);
/* 367 */     this.comboRegressionTrack.setOpaque(true);
/* 368 */     JPanel regreOrderPanelTrack = new JPanel(new FlowLayout(0));
/* 369 */     regreOrderPanelTrack.add(this.comboRegressionTrack);
/* 370 */     JPanel filterOrderPanelTrack = new JPanel(new FlowLayout(0));
/* 371 */     filterOrderPanelTrack.add(this.filterOrderTrack);
/* 372 */     buttonBoxTrack.add(regreOrderPanelTrack);
/* 373 */     buttonBoxTrack.add(filterOrderPanelTrack);
/* 374 */     regressionPanelTrack = new JPanel();
/* 375 */     regressionPanelTrack.setBorder(BorderFactory.createTitledBorder("Reg.Params"));
/* 376 */     regressionPanelTrack.setPreferredSize(new Dimension(this.comboRegressionTrack.getWidth() + 10, 35));
/* 377 */     rangePanelFTrack.add(buttonBoxTrack);
/*     */     
/* 379 */     JPanel trackPanel = new JPanel();
/* 380 */     trackPanel.add(rangePanelFTrack);
/* 381 */     trackPanel.setBorder(BorderFactory.createTitledBorder(""));
/*     */     
/* 383 */     JPanel trackStatistics = new JPanel();
/* 384 */     trackStatistics.setLayout(new BoxLayout(trackStatistics, 1));
/* 385 */     JPanel trackStatisticsFlow = new JPanel(new FlowLayout(0));
/* 386 */     JPanel classParamTrack = new JPanel(new FlowLayout(0));
/* 387 */     this.comboClassTrack = new JComboBox<>();
/* 388 */     this.comboClassTrack.setPreferredSize(new Dimension(120, 20)); int i1;
/* 389 */     for (i1 = 3; i1 < ChooserWizardPanel.columnNamesTrack.length; i1++)
/* 390 */       this.comboClassTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i1]); 
/* 391 */     this.comboClassTrack.setOpaque(true);
/* 392 */     this.comboClassTrack.setToolTipText("Choose selected items for descriptive statistics.");
/* 393 */     this.comboParamTrack = new JComboBox<>();
/* 394 */     this.comboParamTrack.setPreferredSize(new Dimension(120, 20));
/* 395 */     for (i1 = 3; i1 < ChooserWizardPanel.columnNamesTrack.length; i1++)
/* 396 */       this.comboParamTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i1]); 
/* 397 */     this.comboParamTrack.setOpaque(true);
/* 398 */     this.comboParamTrack.setToolTipText("Choose a track parameter for descriptive statistics.");
/* 399 */     classParamTrack.add(this.comboClassTrack);
/* 400 */     classParamTrack.add(this.comboParamTrack);
/* 401 */     labelScoresTrack = new JLabel("SCORES");
/* 402 */     labelScoresTrack.setFont(new Font("Verdana", 1, 20));
/* 403 */     trackStatisticsFlow.add(labelScoresTrack);
/* 404 */     trackStatistics.add(Box.createVerticalStrut(60));
/* 405 */     trackStatistics.add(trackStatisticsFlow);
/* 406 */     trackStatistics.add(new JSeparator(0));
/* 407 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 408 */     trackStatistics.add(classParamTrack);
/* 409 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 410 */     trackStatistics.add(new JSeparator(0));
/* 411 */     String[][] dataTrack = { { "", "", "", "", "", "", "", "", "", "", "" } };
/* 412 */     String[] columnTrack = { "Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", 
/* 413 */         "Min ", "Max ", "Sum ", "Count " };
/*     */     
/* 415 */     JTable tableTrack = new JTable();
/* 416 */     this.modelTrack = new DefaultTableModel((Object[][])dataTrack, (Object[])columnTrack)
/*     */       {
/*     */         public Class<?> getColumnClass(int columnTrack)
/*     */         {
/* 420 */           if (getRowCount() > 0) {
/* 421 */             Object value = getValueAt(0, columnTrack);
/* 422 */             if (value != null) {
/* 423 */               return getValueAt(0, columnTrack).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 427 */           return super.getColumnClass(columnTrack);
/*     */         }
/*     */         
/*     */         public boolean isCellEditable(int row, int col) {
/* 431 */           return false;
/*     */         }
/*     */       };
/* 434 */     tableTrack.setAutoResizeMode(0);
/* 435 */     tableTrack.setModel(this.modelTrack);
/* 436 */     tableTrack.setRowHeight(60);
/* 437 */     JScrollPane spTrack = new JScrollPane(tableTrack);
/* 438 */     spTrack.setPreferredSize(new Dimension(500, 100));
/* 439 */     spTrack.setVerticalScrollBarPolicy(22);
/* 440 */     spTrack.setHorizontalScrollBarPolicy(32);
/* 441 */     for (int i2 = 0; i2 < tableTrack.getColumnCount(); i2++) {
/* 442 */       tableTrack.getColumnModel().getColumn(i2).setMinWidth(100);
/* 443 */       tableTrack.getColumnModel().getColumn(i2).setMaxWidth(100);
/* 444 */       tableTrack.getColumnModel().getColumn(i2).setPreferredWidth(100);
/*     */     } 
/*     */     
/* 447 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 448 */     trackStatistics.add(spTrack);
/* 449 */     trackStatistics.add(Box.createVerticalStrut(20));
/* 450 */     trackStatistics.add(new JSeparator(0));
/* 451 */     JButton plotButtonTrack = new JButton();
/* 452 */     plotButtonTrack.setIcon(plotCell);
/* 453 */     plotButtonTrack.setToolTipText("Click to export scatter plot.");
/* 454 */     JPanel panelPlotTrack = new JPanel(new FlowLayout(0));
/* 455 */     panelPlotTrack.add(plotButtonTrack);
/* 456 */     JButton csvButtonTrack = new JButton();
/* 457 */     csvButtonTrack.setIcon(csvCell);
/* 458 */     csvButtonTrack.setToolTipText("Click to export your tracks statistics table.");
/* 459 */     JPanel panelCsvTrack = new JPanel(new FlowLayout(0));
/* 460 */     panelCsvTrack.add(csvButtonTrack);
/* 461 */     JPanel panelPngCsvTrack = new JPanel(new FlowLayout(0));
/* 462 */     panelPngCsvTrack.add(panelPlotTrack);
/* 463 */     panelPngCsvTrack.add(panelCsvTrack);
/* 464 */     trackStatistics.add(panelPngCsvTrack);
/* 465 */     trackPanel.add(trackStatistics);
/*     */     
/* 467 */     JTabbedPane maintabbedPane = new JTabbedPane(1);
/* 468 */     maintabbedPane.addTab("SPOTS ", FirstWizardPanel.iconSpotCell, spotPanel, "Scatter-Plot for spots");
/* 469 */     maintabbedPane.addTab("TRACKS ", ChooserWizardPanel.iconTrackCell, trackPanel, "Scatter-Plot for tracks");
/*     */     
/* 471 */     maintabbedPane.setTabLayoutPolicy(1);
/* 472 */     add(maintabbedPane, "Center");
/*     */     
/* 474 */     plotButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 477 */             JFileChooser fileChooser = new JFileChooser();
/* 478 */             fileChooser.setFileSelectionMode(1);
/* 479 */             fileChooser.setDialogTitle("Specify a directory to save plot as .png file");
/* 480 */             int userSelection = fileChooser.showSaveDialog(new JFrame());
/*     */             
/* 482 */             if (userSelection == 0) {
/* 483 */               File fileToSave = fileChooser.getSelectedFile();
/* 484 */               BufferedImage chartImage = STScatterPlot.plot.getChart().createBufferedImage(1024, 768);
/*     */               try {
/* 486 */                 ImageIO.write(chartImage, "png", new File(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 487 */                       "SpotPlot for " + IJ.getImage().getShortTitle() + ".png"));
/* 488 */               } catch (IOException e1) {
/*     */                 
/* 490 */                 e1.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 497 */     csvButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 500 */             List<String> columnSpotHead = new ArrayList<>();
/* 501 */             for (int j = 0; j < OptionWizardPanel.this.modelSpot.getColumnCount(); j++) {
/* 502 */               columnSpotHead.add(OptionWizardPanel.this.modelSpot.getColumnName(j));
/*     */             }
/* 504 */             ResultsTable rt = new ResultsTable(Integer.valueOf(OptionWizardPanel.this.modelSpot.getRowCount()));
/* 505 */             if (rt != null) {
/* 506 */               rt.reset();
/*     */             }
/* 508 */             for (int i = 0; i < OptionWizardPanel.this.modelSpot.getRowCount(); i++) {
/* 509 */               for (int k = 0; k < OptionWizardPanel.this.modelSpot.getColumnCount(); k++)
/* 510 */                 rt.setValue(columnSpotHead.get(k), i, OptionWizardPanel.this.modelSpot.getValueAt(i, k).toString()); 
/* 511 */             }  JFrame pngFrame = new JFrame();
/* 512 */             JFileChooser fileChooser = new JFileChooser();
/* 513 */             fileChooser.setFileSelectionMode(1);
/* 514 */             fileChooser.setDialogTitle("Specify a directory to save csv file");
/* 515 */             int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */             
/* 517 */             if (userSelection == 0) {
/* 518 */               File fileToSave = fileChooser.getSelectedFile();
/*     */               try {
/* 520 */                 rt.saveAs(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + "SpotStatistics for-" + 
/* 521 */                     IJ.getImage().getShortTitle() + ".csv");
/* 522 */               } catch (IOException e1) {
/*     */                 
/* 524 */                 e1.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 532 */     this.refreshButtonSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 536 */             OptionWizardPanel.this.refreshSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 538 */                     OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).refreshActionSpot();
/*     */                   }
/*     */                 });
/* 541 */             OptionWizardPanel.this.refreshSpotThread.start();
/*     */           }
/*     */         });
/* 544 */     this.refreshButtonTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 548 */             OptionWizardPanel.this.refreshTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 550 */                     OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).refreshActionTrack();
/*     */                   }
/*     */                 });
/* 553 */             OptionWizardPanel.this.refreshTrackThread.start();
/*     */           }
/*     */         });
/* 556 */     this.zoomInSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 559 */             OptionWizardPanel.this.zoomInSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 561 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
/* 562 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotSpot);
/*     */                   }
/*     */                 });
/* 565 */             OptionWizardPanel.this.zoomInSpotThread.start();
/*     */           }
/*     */         });
/* 568 */     this.zoomOutSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 571 */             OptionWizardPanel.this.zoomOutSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 573 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutSpot.setActionCommand("ZOOM_OUT_BOTH");
/* 574 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutSpot.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotSpot);
/*     */                   }
/*     */                 });
/* 577 */             OptionWizardPanel.this.zoomOutSpotThread.start();
/*     */           }
/*     */         });
/* 580 */     this.zoomInTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 583 */             OptionWizardPanel.this.zoomInTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 585 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
/* 586 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomInSpot.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotTrack);
/*     */                   }
/*     */                 });
/* 589 */             OptionWizardPanel.this.zoomInTrackThread.start();
/*     */           }
/*     */         });
/* 592 */     this.zoomOutTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 595 */             OptionWizardPanel.this.zoomOutTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 597 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutTrack.setActionCommand("ZOOM_OUT_BOTH");
/* 598 */                     (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).zoomOutTrack.addActionListener((ActionListener)(OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).scatterPlotTrack);
/*     */                   }
/*     */                 });
/* 601 */             OptionWizardPanel.this.zoomOutTrackThread.start();
/*     */           }
/*     */         });
/* 604 */     this.comboRegressionSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 607 */             OptionWizardPanel.this.comboRegSpotThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 609 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionSpot.getSelectedIndex() == 1)
/* 610 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderSpot.setEnabled(true); 
/* 611 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionSpot.getSelectedIndex() != 1)
/* 612 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderSpot.setEnabled(false); 
/*     */                   }
/*     */                 });
/* 615 */             OptionWizardPanel.this.comboRegSpotThread.start();
/*     */           }
/*     */         });
/* 618 */     this.comboRegressionTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 621 */             OptionWizardPanel.this.comboRegTrackThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 623 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionTrack.getSelectedIndex() == 1)
/* 624 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderTrack.setEnabled(true); 
/* 625 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboRegressionTrack.getSelectedIndex() != 1)
/* 626 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).filterOrderTrack.setEnabled(false); 
/*     */                   }
/*     */                 });
/* 629 */             OptionWizardPanel.this.comboRegTrackThread.start();
/*     */           }
/*     */         });
/* 632 */     this.comboClassSpot.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 635 */             OptionWizardPanel.this.comboClassSpotThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 638 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedIndex() == 0) {
/* 639 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot = new ArrayList<>();
/* 640 */                       for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/* 641 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, 
/* 642 */                             FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/* 643 */                           (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot
/* 644 */                                 .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamSpot.getSelectedIndex() + 3).toString())); 
/* 645 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionSpot();
/*     */                     } 
/* 647 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedIndex() == 1) {
/* 648 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot = new ArrayList<>();
/* 649 */                       for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/* 650 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, 
/* 651 */                             FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/* 652 */                           if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0)).getText() == "")
/* 653 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot
/* 654 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamSpot.getSelectedIndex() + 3).toString()));  
/* 655 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionSpot();
/*     */                     } 
/*     */                     
/* 658 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedIndex() == 2) {
/* 659 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot = new ArrayList<>();
/* 660 */                       for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/* 661 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, 
/* 662 */                             FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/* 663 */                           if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0))
/* 664 */                             .getText() == (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassSpot.getSelectedItem().toString())
/* 665 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot
/* 666 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamSpot.getSelectedIndex() + 3).toString()));  
/* 667 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionSpot();
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */             
/* 672 */             OptionWizardPanel.this.comboClassSpotThread.start();
/*     */           }
/*     */         });
/*     */     
/* 676 */     this.comboClassTrack.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 679 */             OptionWizardPanel.this.comboClassTrackThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 682 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedIndex() == 0) {
/* 683 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack = new ArrayList<>();
/* 684 */                       for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 685 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, 
/* 686 */                             ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/* 687 */                           (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack
/* 688 */                                 .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamTrack.getSelectedIndex() + 3).toString())); 
/* 689 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionTrack();
/*     */                     } 
/* 691 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedIndex() == 1) {
/* 692 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack = new ArrayList<>();
/* 693 */                       for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 694 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, 
/* 695 */                             ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/* 696 */                           if (((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0)).getText() == "")
/* 697 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack
/* 698 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamTrack.getSelectedIndex() + 3).toString()));  
/* 699 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionTrack();
/*     */                     } 
/*     */                     
/* 702 */                     if ((OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedIndex() == 2) {
/* 703 */                       (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack = new ArrayList<>();
/* 704 */                       for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 705 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, 
/* 706 */                             ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/* 707 */                           if (((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0))
/* 708 */                             .getText() == (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboClassTrack.getSelectedItem().toString())
/* 709 */                             (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack
/* 710 */                                   .getValueAt(i, (OptionWizardPanel.null.access$0(OptionWizardPanel.null.this)).comboParamTrack.getSelectedIndex() + 3).toString()));  
/* 711 */                       }  OptionWizardPanel.null.access$0(OptionWizardPanel.null.this).descriptiveStatisticsActionTrack();
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */ 
/*     */             
/* 717 */             OptionWizardPanel.this.comboClassTrackThread.start();
/*     */           }
/*     */         });
/*     */   }
/*     */   JComboBox<String> comboFeatureDomainTrack; JComboBox<String> comboFeatureRangeTrack; JComboBox<String> comboClassSpot; JComboBox<String> comboParamSpot; JComboBox<String> comboClassTrack; JComboBox<String> comboParamTrack; JComboBox<String> comboRegressionSpot; JComboBox<String> comboRegressionTrack; List<String> itemFiltersSpot; List<String> itemFiltersTrack; STScatterPlot scatterPlot; JButton refreshButtonSpot; JButton zoomInSpot; JButton zoomOutSpot; JButton refreshButtonTrack; JButton zoomInTrack; JButton zoomOutTrack; STScatterPlot sp2Spot; STScatterPlot sp2Track; ChartPanel scatterPlotSpot; ChartPanel scatterPlotTrack; static JPanel regressionPanelSpot; static JPanel regressionPanelTrack; List<Double> dataToStatisticsSpot; List<Double> dataToStatisticsTrack; DefaultTableModel modelSpot; DefaultTableModel modelTrack; Thread refreshSpotThread; Thread refreshTrackThread; Thread zoomInSpotThread; Thread zoomInTrackThread; Thread zoomOutSpotThread; Thread zoomOutTrackThread; Thread comboRegSpotThread; Thread comboRegTrackThread; Thread comboClassSpotThread;
/*     */   Thread comboClassTrackThread;
/*     */   
/*     */   public void descriptiveStatisticsActionSpot() {
/* 725 */     this.modelSpot.setValueAt(String.valueOf(
/* 726 */           Math.round((new DescriptiveStatistics(
/* 727 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMean() * 1000.0D) / 
/* 728 */           1000.0D), 0, 0);
/* 729 */     this.modelSpot.setValueAt(String.valueOf(Math.round((new DescriptiveStatistics(
/* 730 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() / (
/* 731 */             new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()))
/* 732 */             .getN() * 
/* 733 */             1000.0D) / 1000.0D), 0, 1);
/*     */     
/* 735 */     this.modelSpot.setValueAt(String.valueOf(Math.round((
/* 736 */             new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()))
/* 737 */             .getPercentile(50.0D) * 1000.0D) / 
/* 738 */           1000.0D), 0, 2);
/* 739 */     this.modelSpot.setValueAt(String.valueOf(Math.round((
/* 740 */             new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()))
/* 741 */             .getStandardDeviation() * 1000.0D) / 
/* 742 */           1000.0D), 0, 3);
/* 743 */     this.modelSpot.setValueAt(String.valueOf(
/* 744 */           Math.round((new DescriptiveStatistics(
/* 745 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getVariance() * 1000.0D) / 
/* 746 */           1000.0D), 0, 4);
/* 747 */     this.modelSpot.setValueAt(String.valueOf(
/* 748 */           Math.round((new DescriptiveStatistics(
/* 749 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getKurtosis() * 1000.0D) / 
/* 750 */           1000.0D), 0, 5);
/* 751 */     this.modelSpot.setValueAt(String.valueOf(
/* 752 */           Math.round((new DescriptiveStatistics(
/* 753 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getSkewness() * 1000.0D) / 
/* 754 */           1000.0D), 0, 6);
/* 755 */     this.modelSpot.setValueAt(String.valueOf(
/* 756 */           Math.round((new DescriptiveStatistics(
/* 757 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMin() * 1000.0D) / 
/* 758 */           1000.0D), 0, 7);
/* 759 */     this.modelSpot.setValueAt(String.valueOf(
/* 760 */           Math.round((new DescriptiveStatistics(
/* 761 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMax() * 1000.0D) / 
/* 762 */           1000.0D), 0, 8);
/* 763 */     this.modelSpot.setValueAt(String.valueOf(
/* 764 */           Math.round((new DescriptiveStatistics(
/* 765 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getSum() * 1000.0D) / 
/* 766 */           1000.0D), 0, 9);
/*     */     
/* 768 */     this.modelSpot.setValueAt(String.valueOf(
/* 769 */           Math.round((new DescriptiveStatistics(
/* 770 */               this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 
/* 771 */           1000.0D), 0, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void descriptiveStatisticsActionTrack() {
/* 777 */     this.modelTrack.setValueAt(String.valueOf(
/* 778 */           Math.round((new DescriptiveStatistics(
/* 779 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMean() * 1000.0D) / 
/* 780 */           1000.0D), 0, 0);
/* 781 */     this.modelTrack.setValueAt(String.valueOf(Math.round((new DescriptiveStatistics(
/* 782 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() / (
/* 783 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 784 */             .getN() * 
/* 785 */             1000.0D) / 1000.0D), 0, 1);
/*     */     
/* 787 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 788 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 789 */             .getPercentile(50.0D) * 1000.0D) / 
/* 790 */           1000.0D), 0, 2);
/* 791 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 792 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 793 */             .getStandardDeviation() * 1000.0D) / 
/* 794 */           1000.0D), 0, 3);
/* 795 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 796 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 797 */             .getVariance() * 1000.0D) / 
/* 798 */           1000.0D), 0, 4);
/* 799 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 800 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 801 */             .getKurtosis() * 1000.0D) / 
/* 802 */           1000.0D), 0, 5);
/* 803 */     this.modelTrack.setValueAt(String.valueOf(Math.round((
/* 804 */             new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()))
/* 805 */             .getSkewness() * 1000.0D) / 
/* 806 */           1000.0D), 0, 6);
/* 807 */     this.modelTrack.setValueAt(String.valueOf(
/* 808 */           Math.round((new DescriptiveStatistics(
/* 809 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMin() * 1000.0D) / 
/* 810 */           1000.0D), 0, 7);
/* 811 */     this.modelTrack.setValueAt(String.valueOf(
/* 812 */           Math.round((new DescriptiveStatistics(
/* 813 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMax() * 1000.0D) / 
/* 814 */           1000.0D), 0, 8);
/* 815 */     this.modelTrack.setValueAt(String.valueOf(
/* 816 */           Math.round((new DescriptiveStatistics(
/* 817 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getSum() * 1000.0D) / 
/* 818 */           1000.0D), 0, 9);
/*     */     
/* 820 */     this.modelTrack.setValueAt(String.valueOf(
/* 821 */           Math.round((new DescriptiveStatistics(
/* 822 */               this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 
/* 823 */           1000.0D), 0, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshActionSpot() {
/* 829 */     this.comboClassSpot.removeAllItems();
/* 830 */     this.comboClassSpot.addItem("All items");
/* 831 */     this.comboClassSpot.addItem("No Identified");
/*     */     
/* 833 */     if (ColorEditorSpot.modelC.getRowCount() > 0) {
/* 834 */       for (int j = 0; j < ColorEditorSpot.modelC.getRowCount(); j++) {
/* 835 */         this.comboClassSpot.addItem(((JLabel)ColorEditorSpot.modelC.getValueAt(j, 0)).getText());
/*     */       }
/*     */     }
/* 838 */     int rowCount = FirstWizardPanel.tableSpot.getRowCount();
/* 839 */     int columnCount = FirstWizardPanel.tableSpot.getColumnCount();
/* 840 */     selectedIndexDomainSpot = this.comboFeatureDomainSpot.getSelectedIndex();
/* 841 */     selectedIndexRangeSpot = this.comboFeatureRangeSpot.getSelectedIndex();
/* 842 */     List<Double> valuesDomainSpot = new ArrayList<>();
/* 843 */     List<Double> valuesRangeSpot = new ArrayList<>();
/* 844 */     Object[][] dataSpot = new Object[rowCount][columnCount];
/* 845 */     for (int i = 0; i < rowCount; i++) {
/* 846 */       for (int j = 0; j < columnCount; j++) {
/* 847 */         dataSpot[i][j] = FirstWizardPanel.tableSpot.getValueAt(i, j);
/*     */       }
/*     */       
/* 850 */       valuesDomainSpot.add(Double.valueOf(Double.parseDouble(dataSpot[i][selectedIndexDomainSpot + 4].toString())));
/* 851 */       valuesRangeSpot.add(Double.valueOf(Double.parseDouble(dataSpot[i][selectedIndexRangeSpot + 4].toString())));
/*     */     } 
/* 853 */     if (valuesDomainSpot.isEmpty() == Boolean.TRUE.booleanValue()) {
/* 854 */       IJ.error("You should have your spot analysis done. Please go backwards.");
/*     */     } else {
/*     */       
/* 857 */       this.maxDomainSpot = ((Double)Collections.<Double>max(valuesDomainSpot)).doubleValue();
/* 858 */       this.maxRangeSpot = ((Double)Collections.<Double>max(valuesRangeSpot)).doubleValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 864 */       List<Color> listColorSpot = new ArrayList<>();
/* 865 */       for (int j = 0; j < FirstWizardPanel.modelSpot.getRowCount(); j++)
/* 866 */         listColorSpot.add(((JLabel)FirstWizardPanel.modelSpot.getValueAt(j, 
/* 867 */               FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getBackground()); 
/* 868 */       Color[] classColorSpot = new Color[listColorSpot.size()];
/* 869 */       listColorSpot.toArray(classColorSpot);
/* 870 */       if (this.comboRegressionSpot.getSelectedIndex() == 0)
/* 871 */         this.sp2Spot.addScatterPlotSeriesLinear(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 872 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 873 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot); 
/* 874 */       if (this.comboRegressionSpot.getSelectedIndex() == 1)
/* 875 */         this.sp2Spot.addScatterPlotSeriesPolynomial(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 876 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 877 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot, ((Integer)this.filterOrderSpot.getValue()).intValue()); 
/* 878 */       if (this.comboRegressionSpot.getSelectedIndex() == 2)
/* 879 */         this.sp2Spot.addScatterPlotSeriesPower(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 880 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 881 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot); 
/* 882 */       if (this.comboRegressionSpot.getSelectedIndex() == 3)
/* 883 */         this.sp2Spot.addScatterPlotSeriesLogarithmic(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 884 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 885 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot); 
/* 886 */       if (this.comboRegressionSpot.getSelectedIndex() == 4) {
/* 887 */         this.sp2Spot.addScatterPlotSeriesExponential(this.comboFeatureDomainSpot.getSelectedItem().toString(), 
/* 888 */             this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, 
/* 889 */             this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void refreshActionTrack() {
/* 895 */     this.comboClassTrack.removeAllItems();
/* 896 */     this.comboClassTrack.addItem("All items");
/* 897 */     this.comboClassTrack.addItem("No Identified");
/*     */     
/* 899 */     if (ColorEditorTrack.modelC.getRowCount() > 0) {
/* 900 */       for (int j = 0; j < ColorEditorTrack.modelC.getRowCount(); j++) {
/* 901 */         this.comboClassTrack.addItem(((JLabel)ColorEditorTrack.modelC.getValueAt(j, 0)).getText());
/*     */       }
/*     */     }
/* 904 */     int rowCount = ChooserWizardPanel.tableTrack.getRowCount();
/* 905 */     int columnCount = ChooserWizardPanel.tableTrack.getColumnCount();
/* 906 */     selectedIndexDomainTrack = this.comboFeatureDomainTrack.getSelectedIndex();
/* 907 */     selectedIndexRangeTrack = this.comboFeatureRangeTrack.getSelectedIndex();
/* 908 */     List<Double> valuesDomainTrack = new ArrayList<>();
/* 909 */     List<Double> valuesRangeTrack = new ArrayList<>();
/* 910 */     Object[][] dataTrack = new Object[rowCount][columnCount];
/* 911 */     for (int i = 0; i < rowCount; i++) {
/* 912 */       for (int j = 0; j < columnCount; j++) {
/* 913 */         dataTrack[i][j] = ChooserWizardPanel.tableTrack.getValueAt(i, j);
/*     */       }
/*     */       
/* 916 */       valuesDomainTrack.add(Double.valueOf(Double.parseDouble(dataTrack[i][selectedIndexDomainTrack + 4].toString())));
/* 917 */       valuesRangeTrack.add(Double.valueOf(Double.parseDouble(dataTrack[i][selectedIndexRangeTrack + 4].toString())));
/*     */     } 
/* 919 */     if (valuesDomainTrack.isEmpty() == Boolean.TRUE.booleanValue()) {
/* 920 */       IJ.error("You should have your track analysis done. Please go backwards.");
/*     */     } else {
/*     */       
/* 923 */       this.maxDomainTrack = ((Double)Collections.<Double>max(valuesDomainTrack)).doubleValue();
/* 924 */       this.maxRangeTrack = ((Double)Collections.<Double>max(valuesRangeTrack)).doubleValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 930 */       List<Color> listColorTrack = new ArrayList<>();
/* 931 */       for (int j = 0; j < ChooserWizardPanel.modelTrack.getRowCount(); j++)
/* 932 */         listColorTrack.add(((JLabel)ChooserWizardPanel.modelTrack.getValueAt(j, 
/* 933 */               ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1))).getBackground()); 
/* 934 */       Color[] classColorTrack = new Color[listColorTrack.size()];
/* 935 */       listColorTrack.toArray(classColorTrack);
/* 936 */       if (this.comboRegressionTrack.getSelectedIndex() == 0)
/* 937 */         this.sp2Track.addScatterPlotSeriesLinear(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 938 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 939 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/* 940 */       if (this.comboRegressionTrack.getSelectedIndex() == 1)
/* 941 */         this.sp2Track.addScatterPlotSeriesPolynomial(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 942 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 943 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack, (
/* 944 */             (Integer)this.filterOrderTrack.getValue()).intValue()); 
/* 945 */       if (this.comboRegressionTrack.getSelectedIndex() == 2)
/* 946 */         this.sp2Track.addScatterPlotSeriesPower(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 947 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 948 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/* 949 */       if (this.comboRegressionTrack.getSelectedIndex() == 3)
/* 950 */         this.sp2Track.addScatterPlotSeriesLogarithmic(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 951 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 952 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/* 953 */       if (this.comboRegressionTrack.getSelectedIndex() == 4)
/* 954 */         this.sp2Track.addScatterPlotSeriesExponential(this.comboFeatureDomainTrack.getSelectedItem().toString(), 
/* 955 */             this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, 
/* 956 */             this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update1() {
/* 961 */     setNextButtonEnabled(true);
/* 962 */     setFinishButtonEnabled(true);
/* 963 */     setBackButtonEnabled(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void next() {
/* 968 */     setNextButtonEnabled(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void back() {
/* 973 */     switchPanel(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() {
/* 978 */     switchPanel(2);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/OptionWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */