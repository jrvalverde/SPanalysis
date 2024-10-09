/*     */ import ij.IJ;
/*     */ import ij.ImagePlus;
/*     */ import ij.ImageStack;
/*     */ import ij.gui.Roi;
/*     */ import ij.measure.Calibration;
/*     */ import ij.measure.ResultsTable;
/*     */ import ij.process.ColorProcessor;
/*     */ import ij.process.ImageProcessor;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JToggleButton;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import jwizardcomponent.JWizardComponents;
/*     */ import jwizardcomponent.JWizardPanel;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChooserWizardPanel
/*     */   extends JWizardPanel
/*     */ {
/*     */   JCheckBox checkRPicker;
/*     */   static JTable tableTrack;
/*     */   static JTable tableImages;
/*     */   static DefaultTableModel modelTrack;
/*     */   JScrollPane jScrollPaneTrack;
/*     */   JScrollPane jScrollPaneImages;
/*     */   JSpinner filterMin;
/*     */   JSpinner filterMax;
/*  82 */   HistogramFilterVersion hs2 = new HistogramFilterVersion(); ChartPanel histogram; JComboBox<String> comboFilters;
/*     */   static DefaultListModel<String> modelListClass;
/*     */   static DefaultListModel<String> modelListFeature;
/*     */   static JList<String> classList;
/*     */   static JList<String> featureList;
/*     */   static JLabel labelReset;
/*  88 */   static String trackEnable = ""; static String command; List<Integer> indexesToReset; List<Integer> indexesToReset1; List<Integer> tracksID; List<Integer> tracksID1; List<Integer> indexesTI; static Icon iconTrackCell;
/*     */   static Object[] columnNamesTrack;
/*     */   Thread refreshThread;
/*     */   Thread csvThread;
/*     */   Thread pngThread;
/*     */   Thread paintThread;
/*     */   Thread tInsideThread;
/*     */   
/*     */   public ChooserWizardPanel(JWizardComponents wizardComponents) {
/*  97 */     super(wizardComponents, "");
/*     */     
/*  99 */     tableTrack = new JTable();
/* 100 */     tableImages = new JTable();
/* 101 */     tableImages.setModel(FirstWizardPanel.modelImages);
/* 102 */     tableImages.getColumnModel().getColumn(0).setPreferredWidth(90);
/* 103 */     tableImages.getColumnModel().getColumn(1).setPreferredWidth(460);
/* 104 */     tableImages.getColumnModel().getColumn(2).setPreferredWidth(80);
/* 105 */     modelTrack = new DefaultTableModel();
/* 106 */     columnNamesTrack = new Object[] { "Label", "TRACK_ID", "TRACK_INDEX", "NUMBER_SPOTS", "NUMBER_GAPS", 
/* 107 */         "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 108 */         "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", 
/* 109 */         "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", 
/* 110 */         "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 111 */         "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", 
/* 112 */         "TOTAL_ABSOLUTE_ANGLE_XY", "TOTAL_ABSOLUTE_ANGLE_YZ", "TOTAL_ABSOLUTE_ANGLE_ZX" };
/*     */     
/* 114 */     tableTrack.setModel(modelTrack);
/* 115 */     tableTrack.setSelectionMode(1);
/* 116 */     this.jScrollPaneTrack = new JScrollPane(tableTrack);
/* 117 */     this.jScrollPaneTrack.setPreferredSize(new Dimension(590, 240));
/* 118 */     this.jScrollPaneTrack.setBorder(BorderFactory.createTitledBorder(""));
/* 119 */     this.jScrollPaneImages = new JScrollPane(tableImages);
/* 120 */     this.jScrollPaneImages.setPreferredSize(new Dimension(590, 240));
/* 121 */     this.jScrollPaneImages.setBorder(BorderFactory.createTitledBorder(""));
/* 122 */     JPanel mainPanel = new JPanel();
/* 123 */     mainPanel.setLayout(new BoxLayout(mainPanel, 1));
/* 124 */     JTabbedPane tabbedPaneTrack = new JTabbedPane(1);
/* 125 */     ImageIcon iconTrack = FirstWizardPanel.createImageIcon("images/track.jpg");
/* 126 */     iconTrackCell = new ImageIcon(iconTrack.getImage().getScaledInstance(18, 20, 4));
/* 127 */     JButton pngButton = new JButton();
/* 128 */     ImageIcon iconPng = FirstWizardPanel.createImageIcon("images/save.png");
/* 129 */     Icon pngCell = new ImageIcon(iconPng.getImage().getScaledInstance(18, 20, 4));
/* 130 */     pngButton.setIcon(pngCell);
/* 131 */     pngButton.setToolTipText("Click to capture spots overlay.");
/* 132 */     JPanel panelPng = new JPanel(new FlowLayout(0));
/* 133 */     panelPng.add(pngButton);
/* 134 */     JButton csvButton = new JButton();
/* 135 */     ImageIcon iconCsv = FirstWizardPanel.createImageIcon("images/csv.png");
/* 136 */     Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
/* 137 */     csvButton.setIcon(csvCell);
/* 138 */     csvButton.setToolTipText("Click to export your spots table selection.");
/* 139 */     JPanel panelCsv = new JPanel(new FlowLayout(0));
/* 140 */     panelCsv.add(csvButton);
/* 141 */     JPanel panelPngCsv = new JPanel(new FlowLayout(0));
/* 142 */     panelPngCsv.add(panelPng);
/* 143 */     panelPngCsv.add(panelCsv);
/* 144 */     tabbedPaneTrack.addTab("TRACKS ", iconTrackCell, mainPanel, "Display Track Analysis");
/* 145 */     tabbedPaneTrack.setTabLayoutPolicy(1);
/* 146 */     JButton refreshButton = new JButton();
/* 147 */     ImageIcon iconRefresh = FirstWizardPanel.createImageIcon("images/refresh.png");
/* 148 */     Icon refreshCell = new ImageIcon(iconRefresh.getImage().getScaledInstance(18, 20, 4));
/* 149 */     refreshButton.setIcon(refreshCell);
/* 150 */     refreshButton.setToolTipText("Click this button to get Track analysis");
/* 151 */     JToggleButton paintButton = new JToggleButton();
/* 152 */     ImageIcon iconPaint = FirstWizardPanel.createImageIcon("images/paint.png");
/* 153 */     Icon paintCell = new ImageIcon(iconPaint.getImage().getScaledInstance(18, 20, 4));
/* 154 */     paintButton.setIcon(paintCell);
/* 155 */     paintButton.setToolTipText("Click this button to display labeled-Tracks");
/* 156 */     JToggleButton tInsideButton = new JToggleButton();
/* 157 */     ImageIcon iconTI = FirstWizardPanel.createImageIcon("images/tinside.png");
/* 158 */     Icon TICell = new ImageIcon(iconTI.getImage().getScaledInstance(18, 20, 4));
/* 159 */     tInsideButton.setIcon(TICell);
/* 160 */     tInsideButton.setToolTipText("Click this button to toggle inside Tracks.");
/* 161 */     JToggleButton tOutsideButton = new JToggleButton();
/* 162 */     ImageIcon iconTO = FirstWizardPanel.createImageIcon("images/toutside.png");
/* 163 */     Icon TOCell = new ImageIcon(iconTO.getImage().getScaledInstance(18, 20, 4));
/* 164 */     tOutsideButton.setIcon(TOCell);
/* 165 */     tOutsideButton.setToolTipText("Click this button to toggle outside Tracks.");
/* 166 */     JButton enableButton = new JButton();
/* 167 */     ImageIcon iconEnable = FirstWizardPanel.createImageIcon("images/enable.png");
/* 168 */     Icon enableCell = new ImageIcon(iconEnable.getImage().getScaledInstance(18, 20, 4));
/* 169 */     enableButton.setIcon(enableCell);
/* 170 */     enableButton.setToolTipText("Click this button to enable your selection");
/* 171 */     JButton disableButton = new JButton();
/* 172 */     ImageIcon iconDisable = FirstWizardPanel.createImageIcon("images/disable.png");
/* 173 */     Icon disableCell = new ImageIcon(iconDisable.getImage().getScaledInstance(18, 20, 4));
/* 174 */     disableButton.setIcon(disableCell);
/* 175 */     disableButton.setToolTipText("Click this button to disable your selection");
/* 176 */     JPanel buttonPanel = new JPanel(new FlowLayout(2));
/* 177 */     JSeparator separator1 = new JSeparator(1);
/* 178 */     JSeparator separator2 = new JSeparator(1);
/* 179 */     Dimension dime = separator1.getPreferredSize();
/* 180 */     dime.height = (refreshButton.getPreferredSize()).height;
/* 181 */     separator1.setPreferredSize(dime);
/* 182 */     separator2.setPreferredSize(dime);
/* 183 */     this.checkRPicker = new JCheckBox(" Track Picker");
/* 184 */     JLabel filterLabel = new JLabel("  ➠ Track Analysis : ");
/* 185 */     filterLabel.setFont(new Font("Dialog", 1, 13));
/* 186 */     filterLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/* 187 */     JPanel filterPanel = new JPanel(new FlowLayout(0));
/* 188 */     filterPanel.add(filterLabel);
/* 189 */     filterPanel.add(this.checkRPicker);
/* 190 */     filterPanel.add(Box.createHorizontalStrut(20));
/* 191 */     JPanel filterMain = new JPanel(new FlowLayout(0));
/* 192 */     filterMain.add(filterPanel);
/* 193 */     buttonPanel.add(refreshButton);
/* 194 */     buttonPanel.add(paintButton);
/* 195 */     buttonPanel.add(separator1);
/* 196 */     buttonPanel.add(enableButton);
/* 197 */     buttonPanel.add(disableButton);
/* 198 */     buttonPanel.add(separator2);
/* 199 */     buttonPanel.add(tInsideButton);
/* 200 */     buttonPanel.add(tOutsideButton);
/* 201 */     filterMain.add(buttonPanel);
/* 202 */     mainPanel.add(this.jScrollPaneImages);
/* 203 */     mainPanel.add(Box.createVerticalStrut(5));
/* 204 */     mainPanel.add(filterMain);
/* 205 */     mainPanel.add(this.jScrollPaneTrack);
/* 206 */     JLabel settingsLabel = new JLabel("  ➠ Settings for Filters/Classes : ");
/* 207 */     settingsLabel.setFont(new Font("Dialog", 1, 13));
/* 208 */     settingsLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/* 209 */     JPanel settingsPanel = new JPanel(new FlowLayout(0));
/* 210 */     settingsPanel.add(settingsLabel);
/* 211 */     mainPanel.add(settingsPanel);
/* 212 */     JPanel filtersMin = new JPanel(new FlowLayout(0));
/* 213 */     this.filterMin = new JSpinner(new SpinnerNumberModel(30, 0, 5000, 1));
/* 214 */     this.filterMin.setPreferredSize(new Dimension(60, 20));
/* 215 */     final JSlider sliderMin = new JSlider(0, 300, 50);
/* 216 */     sliderMin.setPreferredSize(new Dimension(150, 15));
/* 217 */     JLabel filterMinLabel = new JLabel("              Min :  ");
/* 218 */     filtersMin.add(filterMinLabel);
/* 219 */     filtersMin.add(sliderMin);
/* 220 */     filtersMin.add(Box.createHorizontalStrut(2));
/* 221 */     filtersMin.add(this.filterMin);
/* 222 */     JPanel filtersMax = new JPanel(new FlowLayout(0));
/* 223 */     this.filterMax = new JSpinner(new SpinnerNumberModel(200, 0, 5000, 1));
/* 224 */     this.filterMax.setPreferredSize(new Dimension(60, 20));
/* 225 */     final JSlider sliderMax = new JSlider(0, 300, 150);
/* 226 */     sliderMax.setPreferredSize(new Dimension(150, 15));
/* 227 */     JLabel filterMaxLabel = new JLabel("              Max :  ");
/* 228 */     filtersMax.add(filterMaxLabel);
/* 229 */     filtersMax.add(sliderMax);
/* 230 */     filtersMax.add(Box.createHorizontalStrut(2));
/* 231 */     filtersMax.add(this.filterMax);
/* 232 */     JPanel boxPanel2 = new JPanel();
/* 233 */     boxPanel2.setLayout(new BoxLayout(boxPanel2, 1));
/* 234 */     final IntervalMarker intervalMarker = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), 
/* 235 */         new Color(0, 102, 0), new BasicStroke(1.5F), 0.5F);
/* 236 */     this.histogram = this.hs2.createChartPanel("", new double[] { 0.0D, 0.0D, 0.0D }, 100, intervalMarker);
/*     */     
/* 238 */     JPanel chartPanel2 = new JPanel(new BorderLayout());
/* 239 */     chartPanel2.setPreferredSize(new Dimension(390, 180));
/* 240 */     chartPanel2.add((Component)this.histogram);
/* 241 */     boxPanel2.add(chartPanel2);
/* 242 */     JPanel controlPanel2 = this.hs2.createControlPanel();
/* 243 */     boxPanel2.add(controlPanel2);
/* 244 */     JPanel filtersMain2 = new JPanel();
/* 245 */     filtersMain2.setLayout(new BoxLayout(filtersMain2, 1));
/* 246 */     filtersMain2.add(boxPanel2);
/* 247 */     filtersMain2.add(filtersMin);
/* 248 */     filtersMain2.add(filtersMax);
/* 249 */     JLabel featureTrack = new JLabel(" » Track-Features :  ");
/* 250 */     featureTrack.setFont(new Font("Dialog", 1, 13));
/* 251 */     this.comboFilters = new JComboBox<>();
/* 252 */     for (int i = 1; i < columnNamesTrack.length; i++)
/* 253 */       this.comboFilters.addItem((String)columnNamesTrack[i]); 
/* 254 */     this.comboFilters.setPreferredSize(new Dimension(130, 25));
/* 255 */     this.comboFilters.setSelectedIndex(0);
/* 256 */     this.comboFilters.setOpaque(true);
/* 257 */     JPanel panelFilters = new JPanel(new FlowLayout(0));
/* 258 */     JSeparator separator3 = new JSeparator(1);
/* 259 */     Dimension dime2 = separator3.getPreferredSize();
/* 260 */     dime2.height = (filtersMain2.getPreferredSize()).height;
/* 261 */     separator3.setPreferredSize(dime2);
/* 262 */     panelFilters.add(filtersMain2);
/* 263 */     panelFilters.add(separator3);
/* 264 */     modelListClass = new DefaultListModel<>();
/* 265 */     classList = new JList<>(modelListClass);
/* 266 */     modelListFeature = new DefaultListModel<>();
/* 267 */     featureList = new JList<>(modelListFeature);
/* 268 */     final ColorEditorTrack colorEditor = new ColorEditorTrack(featureList);
/* 269 */     JScrollPane scrollListFilter = new JScrollPane(featureList);
/* 270 */     JScrollPane scrollListClass = new JScrollPane(classList);
/* 271 */     Dimension d = featureList.getPreferredSize();
/* 272 */     d.width = 150;
/* 273 */     d.height = 90;
/* 274 */     scrollListFilter.setPreferredSize(d);
/* 275 */     scrollListClass.setPreferredSize(d);
/* 276 */     JPanel filterPanelButtons = new JPanel(new FlowLayout(0));
/* 277 */     JPanel classPanelButtons = new JPanel();
/* 278 */     classPanelButtons.setLayout(new BoxLayout(classPanelButtons, 1));
/* 279 */     filterPanelButtons.add(scrollListFilter);
/* 280 */     JPanel fButtonsPanel = new JPanel();
/* 281 */     fButtonsPanel.setLayout(new BoxLayout(fButtonsPanel, 1));
/* 282 */     JButton addButton = new JButton();
/* 283 */     ImageIcon iconAdd = FirstWizardPanel.createImageIcon("images/add.png");
/* 284 */     Icon addCell = new ImageIcon(iconAdd.getImage().getScaledInstance(14, 16, 4));
/* 285 */     addButton.setIcon(addCell);
/* 286 */     addButton.setToolTipText("Click this button to add features");
/* 287 */     JButton remButton = new JButton();
/* 288 */     ImageIcon iconRem = FirstWizardPanel.createImageIcon("images/remove.png");
/* 289 */     Icon remCell = new ImageIcon(iconRem.getImage().getScaledInstance(14, 16, 4));
/* 290 */     remButton.setIcon(remCell);
/* 291 */     remButton.setToolTipText("Click this button to remove features");
/* 292 */     JButton classButton = new JButton();
/* 293 */     ImageIcon iconClass = FirstWizardPanel.createImageIcon("images/classes.png");
/* 294 */     Icon classCell = new ImageIcon(iconClass.getImage().getScaledInstance(14, 16, 4));
/* 295 */     classButton.setIcon(classCell);
/* 296 */     classButton.setToolTipText("Click this button to create a class.");
/* 297 */     JButton remClassButton = new JButton();
/* 298 */     remClassButton.setIcon(remCell);
/* 299 */     remClassButton.setToolTipText("Click this button to remove a class.");
/* 300 */     fButtonsPanel.add(addButton);
/* 301 */     fButtonsPanel.add(remButton);
/* 302 */     filterPanelButtons.add(fButtonsPanel);
/* 303 */     classPanelButtons.add(classButton);
/* 304 */     classPanelButtons.add(remClassButton);
/* 305 */     JPanel classPanel = new JPanel(new FlowLayout(0));
/* 306 */     classPanel.add(scrollListClass);
/* 307 */     classPanel.add(classPanelButtons);
/* 308 */     JPanel boxPanel = new JPanel();
/* 309 */     boxPanel.setLayout(new BoxLayout(boxPanel, 1));
/* 310 */     boxPanel.add(this.comboFilters);
/* 311 */     boxPanel.add(Box.createHorizontalStrut(5));
/* 312 */     boxPanel.add(filterPanelButtons);
/* 313 */     boxPanel.add(Box.createHorizontalStrut(5));
/* 314 */     boxPanel.add(classPanel);
/* 315 */     boxPanel.add(panelPngCsv);
/* 316 */     panelFilters.add(boxPanel);
/* 317 */     mainPanel.add(panelFilters);
/* 318 */     add(tabbedPaneTrack);
/* 319 */     createMovieTable();
/*     */     
/* 321 */     paintButton.addItemListener(new ItemListener() {
/*     */           public void itemStateChanged(final ItemEvent ev) {
/* 323 */             ChooserWizardPanel.this.paintThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 325 */                     if (ev.getStateChange() == 1) {
/* 326 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).paintAndDisableAction();
/* 327 */                     } else if (ev.getStateChange() == 2) {
/* 328 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).resetAndEnableAction();
/*     */                     } 
/*     */                   }
/*     */                 });
/* 332 */             ChooserWizardPanel.this.paintThread.start();
/*     */           }
/*     */         });
/* 335 */     csvButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 338 */             ChooserWizardPanel.this.csvThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 340 */                     List<String> columnTrackHead = new ArrayList<>();
/* 341 */                     for (int j = 0; j < ChooserWizardPanel.modelTrack.getColumnCount(); j++) {
/* 342 */                       columnTrackHead.add(ChooserWizardPanel.modelTrack.getColumnName(j));
/*     */                     }
/* 344 */                     ResultsTable rt = new ResultsTable(Integer.valueOf(ChooserWizardPanel.modelTrack.getRowCount()));
/* 345 */                     if (rt != null) {
/* 346 */                       rt.reset();
/*     */                     }
/* 348 */                     for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 349 */                       for (int k = 0; k < ChooserWizardPanel.modelTrack.getColumnCount(); k++) {
/* 350 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/*     */                         {
/* 352 */                           if (((String)columnTrackHead.get(k)).equals(columnTrackHead.get(0)) == Boolean.TRUE.booleanValue()) {
/*     */                             
/* 354 */                             rt.setValue(columnTrackHead.get(k), i, (
/* 355 */                                 (JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, k)).getText());
/*     */                           } else {
/*     */                             
/* 358 */                             rt.setValue(columnTrackHead.get(k), i, ChooserWizardPanel.modelTrack.getValueAt(i, k).toString());
/*     */                           }  } 
/*     */                       } 
/* 361 */                     }  rt.show("Resutls tracks");
/*     */                   }
/*     */                 });
/* 364 */             ChooserWizardPanel.this.csvThread.start();
/*     */           }
/*     */         });
/*     */     
/* 368 */     pngButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 371 */             ChooserWizardPanel.this.pngThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 373 */                     if (IJ.getImage() == null)
/* 374 */                       IJ.error("You must have an image window active."); 
/* 375 */                     if (IJ.getImage() != null) {
/* 376 */                       JFrame pngFrame = new JFrame();
/* 377 */                       JFileChooser fileChooser = new JFileChooser();
/* 378 */                       fileChooser.setFileSelectionMode(1);
/* 379 */                       fileChooser.setDialogTitle("Specify a directory to save");
/* 380 */                       int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */                       
/* 382 */                       if (userSelection == 0) {
/* 383 */                         File fileToSave = fileChooser.getSelectedFile();
/*     */                         
/* 385 */                         int firstFrame = 0, lastFrame = 0;
/* 386 */                         if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/* 387 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/* 388 */                           lastFrame = Math.min(IJ.getImage().getNFrames(), 
/* 389 */                               Math.max(IJ.getImage().getNFrames(), 1));
/*     */                         } 
/* 391 */                         if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/* 392 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/* 393 */                           lastFrame = Math.min(IJ.getImage().getNSlices(), 
/* 394 */                               Math.max(IJ.getImage().getNSlices(), 1));
/*     */                         } 
/*     */                         
/* 397 */                         Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/* 398 */                         int width = bounds.width;
/* 399 */                         int height = bounds.height;
/* 400 */                         int nCaptures = lastFrame - firstFrame + 1;
/* 401 */                         ImageStack stack = new ImageStack(width, height);
/* 402 */                         int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/* 403 */                         int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/* 404 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/* 405 */                         for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*     */                           
/* 407 */                           ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/* 408 */                               frame);
/* 409 */                           BufferedImage bi = new BufferedImage(width, height, 2);
/* 410 */                           ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/* 411 */                           ColorProcessor cp = new ColorProcessor(bi);
/* 412 */                           int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/* 413 */                               frame);
/* 414 */                           stack.addSlice(
/* 415 */                               ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/* 416 */                               (ImageProcessor)cp);
/*     */                         } 
/* 418 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/* 419 */                         ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/* 420 */                             ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/* 421 */                         ChooserWizardPanel.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
/* 422 */                         IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 423 */                             "Capture Overlay for " + IJ.getImage().getShortTitle());
/*     */                       } 
/*     */                     } 
/*     */                   }
/*     */                 });
/* 428 */             ChooserWizardPanel.this.pngThread.start();
/*     */           }
/*     */         });
/* 431 */     refreshButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 434 */             ChooserWizardPanel.this.refreshThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 437 */                     ChooserWizardPanel.trackEnable = "trackEnable";
/* 438 */                     ProcessTrackMateXml.tracksVisible = true;
/* 439 */                     ProcessTrackMateXml.spotsVisible = false;
/* 440 */                     ProcessTrackMateXml ptx = new ProcessTrackMateXml();
/* 441 */                     ptx.processTrackMateXml();
/*     */                   }
/*     */                 });
/* 444 */             ChooserWizardPanel.this.refreshThread.start();
/*     */           }
/*     */         });
/*     */     
/* 448 */     enableButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 451 */             ChooserWizardPanel.this.enableThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 453 */                     ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).enableTracks();
/*     */                   }
/*     */                 });
/* 456 */             ChooserWizardPanel.this.enableThread.start();
/*     */           }
/*     */         });
/*     */     
/* 460 */     disableButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 463 */             ChooserWizardPanel.this.disableThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 465 */                     ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).disableTracks();
/*     */                   }
/*     */                 });
/* 468 */             ChooserWizardPanel.this.disableThread.start();
/*     */           }
/*     */         });
/*     */     
/* 472 */     this.checkRPicker.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(final ItemEvent e)
/*     */           {
/* 476 */             ChooserWizardPanel.this.pickerThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 478 */                     if (e.getStateChange() == 1)
/* 479 */                       ChooserWizardPanel.command = "enable"; 
/* 480 */                     if (e.getStateChange() == 2) {
/* 481 */                       ChooserWizardPanel.command = null;
/* 482 */                       ProcessTrackMateXml.selectionModel.clearSpotSelection();
/* 483 */                       ProcessTrackMateXml.selectionModel.clearSelection();
/*     */                       return;
/*     */                     } 
/*     */                   }
/*     */                 });
/* 488 */             ChooserWizardPanel.this.pickerThread.start();
/*     */           }
/*     */         });
/*     */     
/* 492 */     sliderMin.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 495 */             ChooserWizardPanel.this.slMinThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 498 */                     (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.setValue(Integer.valueOf(sliderMin.getValue()));
/* 499 */                     intervalMarker.setStartValue(sliderMin.getValue());
/*     */                   }
/*     */                 });
/* 502 */             ChooserWizardPanel.this.slMinThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 507 */     this.filterMin.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 510 */             ChooserWizardPanel.this.filterMinThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 512 */                     sliderMin.setValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue()).intValue());
/* 513 */                     intervalMarker.setStartValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue()).intValue());
/*     */                   }
/*     */                 });
/* 516 */             ChooserWizardPanel.this.filterMinThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 521 */     sliderMax.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 524 */             ChooserWizardPanel.this.slMaxThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 526 */                     (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.setValue(Integer.valueOf(sliderMax.getValue()));
/* 527 */                     intervalMarker.setEndValue(sliderMax.getValue());
/*     */                   }
/*     */                 });
/* 530 */             ChooserWizardPanel.this.slMaxThread.start();
/*     */           }
/*     */         });
/*     */     
/* 534 */     this.filterMax.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 537 */             ChooserWizardPanel.this.filterMaxThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 539 */                     sliderMax.setValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue()).intValue());
/* 540 */                     intervalMarker.setEndValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue()).intValue());
/*     */                   }
/*     */                 });
/* 543 */             ChooserWizardPanel.this.filterMaxThread.start();
/*     */           }
/*     */         });
/* 546 */     tInsideButton.addItemListener(new ItemListener() {
/*     */           public void itemStateChanged(final ItemEvent ev) {
/* 548 */             ChooserWizardPanel.this.tInsideThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 550 */                     if (ev.getStateChange() == 1) {
/* 551 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).toggleInsideAction();
/* 552 */                     } else if (ev.getStateChange() == 2) {
/* 553 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).resetToggleInsideAction();
/*     */                     } 
/*     */                   }
/*     */                 });
/* 557 */             ChooserWizardPanel.this.tInsideThread.start();
/*     */           }
/*     */         });
/* 560 */     this.comboFilters.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 562 */             ChooserWizardPanel.this.filtersThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 564 */                     String selectedName = (String)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem();
/* 565 */                     int selectedIndex = (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedIndex();
/* 566 */                     double[] values = null;
/*     */ 
/*     */                     
/* 569 */                     values = new double[ChooserWizardPanel.tableTrack.getRowCount()];
/* 570 */                     for (int r = 0; r < ChooserWizardPanel.tableTrack.getRowCount(); r++) {
/* 571 */                       for (int c = 0; c < ChooserWizardPanel.tableTrack.getColumnCount(); c++)
/* 572 */                         values[r] = Double.parseDouble((String)ChooserWizardPanel.tableTrack.getValueAt(r, selectedIndex + 2)); 
/*     */                     } 
/* 574 */                     double max = values[0];
/* 575 */                     for (int i = 1; i < values.length; i++) {
/* 576 */                       if (values[i] > max)
/* 577 */                         max = values[i]; 
/*     */                     } 
/* 579 */                     sliderMin.setMinimum(0);
/* 580 */                     sliderMin.setMaximum((int)max);
/* 581 */                     sliderMax.setMinimum(0);
/* 582 */                     sliderMax.setMaximum((int)max);
/*     */                     
/* 584 */                     (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).hs2.addHistogramSeries(selectedName, values, (int)max, intervalMarker);
/*     */                   }
/*     */                 });
/* 587 */             ChooserWizardPanel.this.filtersThread.start();
/*     */           }
/*     */         });
/*     */     
/* 591 */     classButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 594 */             ChooserWizardPanel.this.classThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 596 */                     ColorEditorTrack.myFrame.setVisible(true);
/* 597 */                     colorEditor.setClassAction();
/*     */                   }
/*     */                 });
/* 600 */             ChooserWizardPanel.this.classThread.start();
/*     */           }
/*     */         });
/* 603 */     remClassButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 606 */             ChooserWizardPanel.this.remClassThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 609 */                     String classSelectedValue = ChooserWizardPanel.classList.getSelectedValue();
/* 610 */                     int[] classSelectedIndex = ChooserWizardPanel.classList.getSelectedIndices(); int i;
/* 611 */                     for (i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 612 */                       if (((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1))).getText()
/* 613 */                         .equals(classSelectedValue))
/* 614 */                         ChooserWizardPanel.modelTrack.setValueAt(ChooserWizardPanel.labelReset, i, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1)); 
/*     */                     } 
/* 616 */                     for (i = 0; i < classSelectedIndex.length; i++)
/* 617 */                       ChooserWizardPanel.modelListClass.removeElementAt(classSelectedIndex[i]); 
/*     */                   }
/*     */                 });
/* 620 */             ChooserWizardPanel.this.remClassThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 625 */     addButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 628 */             ChooserWizardPanel.this.addThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 631 */                     List<String> listFilters = new ArrayList<>();
/*     */                     
/* 633 */                     if (ChooserWizardPanel.featureList.getModel().getSize() < 1) {
/* 634 */                       ChooserWizardPanel.modelListFeature.addElement(String.valueOf((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem()) + ":  [" + 
/* 635 */                           (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue() + "," + (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue() + "]");
/*     */                     }
/* 637 */                     if (ChooserWizardPanel.featureList.getModel().getSize() >= 1) {
/* 638 */                       for (int i = 0; i < ChooserWizardPanel.featureList.getModel().getSize(); i++) {
/* 639 */                         listFilters.add(String.valueOf(((String)ChooserWizardPanel.featureList.getModel().getElementAt(i)).substring(0, (
/* 640 */                                 (String)ChooserWizardPanel.featureList.getModel().getElementAt(i)).lastIndexOf(":"))));
/*     */                       }
/* 642 */                       if (!listFilters.contains((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem().toString())) {
/* 643 */                         ChooserWizardPanel.modelListFeature.addElement(String.valueOf((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem()) + ":  [" + 
/* 644 */                             (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue() + "," + (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue() + "]");
/*     */                       }
/* 646 */                       if (listFilters.contains((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem().toString())) {
/*     */                         return;
/*     */                       }
/*     */                     } 
/*     */                   }
/*     */                 });
/* 652 */             ChooserWizardPanel.this.addThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 657 */     remButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 660 */             ChooserWizardPanel.this.remThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/*     */                     try {
/* 664 */                       int[] indexes = ChooserWizardPanel.featureList.getSelectedIndices();
/* 665 */                       for (int i = 0; i < indexes.length; i++)
/* 666 */                         ChooserWizardPanel.modelListFeature.remove(indexes[i]); 
/* 667 */                     } catch (Exception e1) {
/* 668 */                       e1.printStackTrace();
/*     */                     } 
/*     */                   }
/*     */                 });
/* 672 */             ChooserWizardPanel.this.remThread.start();
/*     */           }
/*     */         });
/* 675 */     tOutsideButton.addItemListener(new ItemListener() {
/*     */           public void itemStateChanged(final ItemEvent ev) {
/* 677 */             ChooserWizardPanel.this.tOutsideThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 680 */                     if (ev.getStateChange() == 1) {
/* 681 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).toggleOutsideAction();
/* 682 */                     } else if (ev.getStateChange() == 2) {
/* 683 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).resetToggleInsideAction();
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */             
/* 688 */             ChooserWizardPanel.this.tOutsideThread.start();
/*     */           }
/*     */         });
/*     */   }
/*     */   Thread tOutsideThread; Thread enableThread; Thread disableThread; Thread slMinThread; Thread filterMinThread; Thread slMaxThread; Thread filterMaxThread; Thread filtersThread; Thread pickerThread; Thread classThread; Thread remClassThread; Thread addThread; Thread remThread;
/*     */   public void toggleOutsideAction() {
/* 694 */     Roi mainRoi = null;
/* 695 */     if (IJ.getImage().getRoi().getType() == 0)
/* 696 */       mainRoi = IJ.getImage().getRoi(); 
/* 697 */     this.indexesTI = new ArrayList<>();
/*     */     
/* 699 */     for (int i = 0; i < modelTrack.getRowCount(); i++) {
/*     */       
/* 701 */       if (mainRoi
/* 702 */         .contains(
/*     */           
/* 704 */           (int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelTrack
/* 705 */               .getValueAt(i, tableTrack.convertColumnIndexToModel(13)).toString())), 
/*     */           
/* 707 */           (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(
/* 708 */               modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(14))
/* 709 */               .toString()))) == Boolean.FALSE.booleanValue()) {
/* 710 */         this.indexesTI.add(Integer.valueOf(i));
/* 711 */         modelTrack.setValueAt(Boolean.valueOf(false), i, tableTrack.convertColumnIndexToModel(0));
/*     */         
/* 713 */         int trackID = Integer.parseInt((String)tableTrack.getValueAt(i, 2));
/* 714 */         ProcessTrackMateXml.model.beginUpdate();
/*     */         try {
/* 716 */           ProcessTrackMateXml.model.setTrackVisibility(Integer.valueOf(trackID), false);
/*     */         } finally {
/* 718 */           ProcessTrackMateXml.model.endUpdate();
/*     */         } 
/*     */         
/* 721 */         ProcessTrackMateXml.displayer.refresh();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleInsideAction() {
/* 729 */     Roi mainRoi = null;
/* 730 */     if (IJ.getImage().getRoi().getType() == 0)
/* 731 */       mainRoi = IJ.getImage().getRoi(); 
/* 732 */     this.indexesTI = new ArrayList<>();
/*     */     
/* 734 */     for (int i = 0; i < modelTrack.getRowCount(); i++) {
/*     */       
/* 736 */       if (mainRoi
/* 737 */         .contains(
/*     */           
/* 739 */           (int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelTrack
/* 740 */               .getValueAt(i, tableTrack.convertColumnIndexToModel(13)).toString())), 
/*     */           
/* 742 */           (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(
/* 743 */               modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(14))
/* 744 */               .toString()))) == Boolean.TRUE.booleanValue()) {
/* 745 */         this.indexesTI.add(Integer.valueOf(i));
/* 746 */         modelTrack.setValueAt(Boolean.valueOf(false), i, tableTrack.convertColumnIndexToModel(0));
/*     */         
/* 748 */         int trackID = Integer.parseInt((String)tableTrack.getValueAt(i, 2));
/* 749 */         ProcessTrackMateXml.model.beginUpdate();
/*     */         try {
/* 751 */           ProcessTrackMateXml.model.setTrackVisibility(Integer.valueOf(trackID), false);
/*     */         } finally {
/* 753 */           ProcessTrackMateXml.model.endUpdate();
/*     */         } 
/*     */         
/* 756 */         ProcessTrackMateXml.displayer.refresh();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetToggleInsideAction() {
/* 765 */     for (int row = 0; row < modelTrack.getRowCount(); row++) {
/* 766 */       modelTrack.setValueAt(Boolean.valueOf(true), tableTrack.convertRowIndexToModel(row), 
/* 767 */           tableTrack.convertColumnIndexToModel(0));
/* 768 */       int trackID = Integer.parseInt((String)tableTrack.getValueAt(row, 2));
/* 769 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 771 */         ProcessTrackMateXml.model.setTrackVisibility(Integer.valueOf(trackID), true);
/*     */       } finally {
/* 773 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */       
/* 776 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTrackTable() {
/* 783 */     modelTrack = new DefaultTableModel((Object[][])ProcessTrackMateXml.dataTrack, (Object[])ProcessTrackMateXml.columnHeadersTrack)
/*     */       {
/*     */         public Class<?> getColumnClass(int column)
/*     */         {
/* 787 */           if (getRowCount() > 0) {
/* 788 */             Object value = getValueAt(0, column);
/* 789 */             if (value != null) {
/* 790 */               return getValueAt(0, column).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 794 */           return super.getColumnClass(column);
/*     */         }
/*     */       };
/*     */     
/* 798 */     modelTrack.addColumn("Enable");
/* 799 */     tableTrack.setModel(modelTrack);
/* 800 */     tableTrack.moveColumn(tableTrack.getColumnCount() - 1, 0);
/* 801 */     tableTrack.setSelectionBackground(new Color(229, 255, 204));
/* 802 */     tableTrack.setSelectionForeground(new Color(0, 102, 0));
/* 803 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/* 804 */     centerRenderer.setHorizontalAlignment(0);
/* 805 */     tableTrack.setDefaultRenderer(String.class, centerRenderer);
/* 806 */     tableTrack.setAutoResizeMode(0);
/* 807 */     tableTrack.setRowHeight(45);
/* 808 */     tableTrack.setAutoCreateRowSorter(true);
/* 809 */     tableTrack.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer()); int u;
/* 810 */     for (u = 0; u < tableTrack.getColumnCount(); u++)
/* 811 */       tableTrack.getColumnModel().getColumn(u).setPreferredWidth(90); 
/* 812 */     for (u = 3; u < tableTrack.getColumnCount(); u++)
/* 813 */       tableTrack.getColumnModel().getColumn(u).setPreferredWidth(130); 
/*     */     int i;
/* 815 */     for (i = 0; i < tableTrack.getRowCount(); i++)
/* 816 */       tableTrack.setValueAt(Boolean.valueOf(true), i, 0); 
/* 817 */     tableTrack.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
/* 818 */     labelReset = new JLabel();
/* 819 */     labelReset.setText("");
/* 820 */     labelReset.setOpaque(true);
/* 821 */     labelReset.setBackground(new Color(214, 217, 223));
/* 822 */     for (i = 0; i < modelTrack.getRowCount(); i++) {
/* 823 */       modelTrack.setValueAt(labelReset, i, tableTrack.convertColumnIndexToModel(1));
/*     */     }
/*     */   }
/*     */   
/*     */   public void enableTracks() {
/* 828 */     this.indexesToReset1 = new ArrayList<>();
/* 829 */     this.tracksID1 = new ArrayList<>();
/* 830 */     int[] selectedRows = tableTrack.getSelectedRows();
/* 831 */     for (int i = 0; i < selectedRows.length; i++) {
/* 832 */       this.indexesToReset1.add(Integer.valueOf(selectedRows[i]));
/* 833 */       modelTrack.setValueAt(Boolean.valueOf(true), selectedRows[i], tableTrack.convertColumnIndexToModel(0));
/* 834 */       this.tracksID1.add(Integer.valueOf(Integer.parseInt((String)tableTrack.getValueAt(selectedRows[i], 2))));
/*     */     } 
/*     */     
/* 837 */     for (int row = 0; row < this.tracksID1.size(); row++) {
/*     */       
/* 839 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 841 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID1.get(row), true);
/*     */       } finally {
/* 843 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */ 
/*     */       
/* 847 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableTracks() {
/* 854 */     this.indexesToReset1 = new ArrayList<>();
/* 855 */     this.tracksID1 = new ArrayList<>();
/* 856 */     int[] selectedRows = tableTrack.getSelectedRows();
/* 857 */     for (int i = 0; i < selectedRows.length; i++) {
/* 858 */       this.indexesToReset1.add(Integer.valueOf(selectedRows[i]));
/* 859 */       modelTrack.setValueAt(Boolean.valueOf(false), selectedRows[i], tableTrack.convertColumnIndexToModel(0));
/* 860 */       this.tracksID1.add(Integer.valueOf(Integer.parseInt((String)tableTrack.getValueAt(selectedRows[i], 2))));
/*     */     } 
/*     */     
/* 863 */     for (int row = 0; row < this.tracksID1.size(); row++) {
/*     */       
/* 865 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 867 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID1.get(row), false);
/*     */       } finally {
/* 869 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */ 
/*     */       
/* 873 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createMovieTable() {
/* 881 */     tableImages.setSelectionBackground(new Color(229, 255, 204));
/* 882 */     tableImages.setSelectionForeground(new Color(0, 102, 0));
/* 883 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/* 884 */     centerRenderer.setHorizontalAlignment(0);
/* 885 */     tableImages.setDefaultRenderer(String.class, centerRenderer);
/* 886 */     tableImages.setAutoResizeMode(0);
/* 887 */     tableImages.setRowHeight(95);
/* 888 */     tableImages.setAutoCreateRowSorter(true);
/* 889 */     tableImages.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
/* 890 */     tableImages.setModel(FirstWizardPanel.modelImages);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintAndDisableAction() {
/* 895 */     this.indexesToReset = new ArrayList<>();
/* 896 */     this.tracksID = new ArrayList<>();
/*     */     
/* 898 */     for (int i = 0; i < modelTrack.getRowCount(); i++) {
/* 899 */       if (((JLabel)modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(1))).getBackground()
/* 900 */         .equals(new Color(214, 217, 223)) == Boolean.TRUE.booleanValue()) {
/* 901 */         this.indexesToReset.add(Integer.valueOf(i));
/* 902 */         modelTrack.setValueAt(Boolean.valueOf(false), i, tableTrack.convertColumnIndexToModel(0));
/* 903 */         this.tracksID.add(Integer.valueOf(Integer.parseInt((String)tableTrack.getValueAt(i, 2))));
/*     */       } 
/* 905 */     }  for (int row = 0; row < this.tracksID.size(); row++) {
/*     */       
/* 907 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 909 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID.get(row), false);
/*     */       } finally {
/* 911 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */ 
/*     */       
/* 915 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetAndEnableAction() {
/* 921 */     for (int i = 0; i < this.indexesToReset.size(); i++)
/* 922 */       modelTrack.setValueAt(Boolean.valueOf(true), tableTrack.convertRowIndexToModel(((Integer)this.indexesToReset.get(i)).intValue()), 
/* 923 */           tableTrack.convertColumnIndexToModel(0)); 
/* 924 */     for (int row = 0; row < this.tracksID.size(); row++) {
/* 925 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 927 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID.get(row), true);
/*     */       } finally {
/* 929 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */     } 
/* 932 */     ProcessTrackMateXml.displayer.refresh();
/*     */   }
/*     */   
/*     */   public void update() {
/* 936 */     setNextButtonEnabled(true);
/* 937 */     setFinishButtonEnabled(true);
/* 938 */     setBackButtonEnabled(true);
/*     */   }
/*     */   
/*     */   public void next() {
/* 942 */     switchPanel(2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void back() {
/* 948 */     switchPanel(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final void transferCalibration(ImagePlus from, ImagePlus to) {
/* 953 */     Calibration fc = from.getCalibration();
/* 954 */     Calibration tc = to.getCalibration();
/*     */     
/* 956 */     tc.setUnit(fc.getUnit());
/* 957 */     tc.setTimeUnit(fc.getTimeUnit());
/* 958 */     tc.frameInterval = fc.frameInterval;
/*     */     
/* 960 */     double mag = from.getCanvas().getMagnification();
/* 961 */     fc.pixelWidth /= mag;
/* 962 */     fc.pixelHeight /= mag;
/* 963 */     tc.pixelDepth = fc.pixelDepth;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/ChooserWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */