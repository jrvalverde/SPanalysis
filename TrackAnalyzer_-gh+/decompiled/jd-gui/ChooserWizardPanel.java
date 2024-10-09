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
/*     */ import java.net.URL;
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
/*  83 */   HistogramFilterVersion hs2 = new HistogramFilterVersion(); ChartPanel histogram; JComboBox<String> comboFilters;
/*     */   static DefaultListModel<String> modelListClass;
/*     */   static DefaultListModel<String> modelListFeature;
/*     */   static JList<String> classList;
/*     */   static JList<String> featureList;
/*     */   static JLabel labelReset;
/*  89 */   static String trackEnable = ""; static String command; List<Integer> indexesToReset; List<Integer> indexesToReset1; List<Integer> tracksID; List<Integer> tracksID1; List<Integer> indexesTI; static Icon iconTrackCell;
/*     */   static Object[] columnNamesTrack;
/*     */   Thread refreshThread;
/*     */   Thread csvThread;
/*     */   Thread pngThread;
/*     */   Thread paintThread;
/*     */   Thread tInsideThread;
/*     */   
/*     */   public ChooserWizardPanel(JWizardComponents wizardComponents) {
/*  98 */     super(wizardComponents, "");
/*     */     
/* 100 */     tableTrack = new JTable();
/* 101 */     tableImages = new JTable();
/* 102 */     tableImages.setModel(FirstWizardPanel.modelImages);
/* 103 */     tableImages.getColumnModel().getColumn(0).setPreferredWidth(90);
/* 104 */     tableImages.getColumnModel().getColumn(1).setPreferredWidth(460);
/* 105 */     tableImages.getColumnModel().getColumn(2).setPreferredWidth(80);
/* 106 */     modelTrack = new DefaultTableModel();
/* 107 */     columnNamesTrack = new Object[] { "Label", "TRACK_ID", "TRACK_INDEX", "NUMBER_SPOTS", "NUMBER_GAPS", 
/* 108 */         "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 109 */         "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", 
/* 110 */         "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", 
/* 111 */         "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 112 */         "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", 
/* 113 */         "TOTAL_ABSOLUTE_ANGLE_XY", "TOTAL_ABSOLUTE_ANGLE_YZ", "TOTAL_ABSOLUTE_ANGLE_ZX" };
/*     */     
/* 115 */     tableTrack.setModel(modelTrack);
/* 116 */     tableTrack.setSelectionMode(1);
/* 117 */     this.jScrollPaneTrack = new JScrollPane(tableTrack);
/* 118 */     this.jScrollPaneTrack.setPreferredSize(new Dimension(590, 240));
/* 119 */     this.jScrollPaneTrack.setBorder(BorderFactory.createTitledBorder(""));
/* 120 */     this.jScrollPaneImages = new JScrollPane(tableImages);
/* 121 */     this.jScrollPaneImages.setPreferredSize(new Dimension(590, 240));
/* 122 */     this.jScrollPaneImages.setBorder(BorderFactory.createTitledBorder(""));
/* 123 */     JPanel mainPanel = new JPanel();
/* 124 */     mainPanel.setLayout(new BoxLayout(mainPanel, 1));
/* 125 */     JTabbedPane tabbedPaneTrack = new JTabbedPane(1);
/* 126 */     ImageIcon iconTrack = createImageIcon("/images/track.jpg");
/* 127 */     iconTrackCell = new ImageIcon(iconTrack.getImage().getScaledInstance(18, 20, 4));
/* 128 */     JButton pngButton = new JButton();
/* 129 */     ImageIcon iconPng = createImageIcon("/images/save.png");
/* 130 */     Icon pngCell = new ImageIcon(iconPng.getImage().getScaledInstance(18, 20, 4));
/* 131 */     pngButton.setIcon(pngCell);
/* 132 */     pngButton.setToolTipText("Click to capture spots overlay.");
/* 133 */     JPanel panelPng = new JPanel(new FlowLayout(0));
/* 134 */     panelPng.add(pngButton);
/* 135 */     JButton csvButton = new JButton();
/* 136 */     ImageIcon iconCsv = createImageIcon("/images/csv.png");
/* 137 */     Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
/* 138 */     csvButton.setIcon(csvCell);
/* 139 */     csvButton.setToolTipText("Click to export your spots table selection.");
/* 140 */     JPanel panelCsv = new JPanel(new FlowLayout(0));
/* 141 */     panelCsv.add(csvButton);
/* 142 */     JPanel panelPngCsv = new JPanel(new FlowLayout(0));
/* 143 */     panelPngCsv.add(panelPng);
/* 144 */     panelPngCsv.add(panelCsv);
/* 145 */     tabbedPaneTrack.addTab("TRACKS ", iconTrackCell, mainPanel, "Display Track Analysis");
/* 146 */     tabbedPaneTrack.setTabLayoutPolicy(1);
/* 147 */     JButton refreshButton = new JButton();
/* 148 */     ImageIcon iconRefresh = createImageIcon("/images/refresh.png");
/* 149 */     Icon refreshCell = new ImageIcon(iconRefresh.getImage().getScaledInstance(18, 20, 4));
/* 150 */     refreshButton.setIcon(refreshCell);
/* 151 */     refreshButton.setToolTipText("Click this button to get Track analysis");
/* 152 */     JToggleButton paintButton = new JToggleButton();
/* 153 */     ImageIcon iconPaint = createImageIcon("/images/paint.png");
/* 154 */     Icon paintCell = new ImageIcon(iconPaint.getImage().getScaledInstance(18, 20, 4));
/* 155 */     paintButton.setIcon(paintCell);
/* 156 */     paintButton.setToolTipText("Click this button to display labeled-Tracks");
/* 157 */     JToggleButton tInsideButton = new JToggleButton();
/* 158 */     ImageIcon iconTI = createImageIcon("/images/tinside.png");
/* 159 */     Icon TICell = new ImageIcon(iconTI.getImage().getScaledInstance(18, 20, 4));
/* 160 */     tInsideButton.setIcon(TICell);
/* 161 */     tInsideButton.setToolTipText("Click this button to toggle inside Tracks.");
/* 162 */     JToggleButton tOutsideButton = new JToggleButton();
/* 163 */     ImageIcon iconTO = createImageIcon("/images/toutside.png");
/* 164 */     Icon TOCell = new ImageIcon(iconTO.getImage().getScaledInstance(18, 20, 4));
/* 165 */     tOutsideButton.setIcon(TOCell);
/* 166 */     tOutsideButton.setToolTipText("Click this button to toggle outside Tracks.");
/* 167 */     JButton enableButton = new JButton();
/* 168 */     ImageIcon iconEnable = createImageIcon("/images/enable.png");
/* 169 */     Icon enableCell = new ImageIcon(iconEnable.getImage().getScaledInstance(18, 20, 4));
/* 170 */     enableButton.setIcon(enableCell);
/* 171 */     enableButton.setToolTipText("Click this button to enable your selection");
/* 172 */     JButton disableButton = new JButton();
/* 173 */     ImageIcon iconDisable = createImageIcon("/images/disable.png");
/* 174 */     Icon disableCell = new ImageIcon(iconDisable.getImage().getScaledInstance(18, 20, 4));
/* 175 */     disableButton.setIcon(disableCell);
/* 176 */     disableButton.setToolTipText("Click this button to disable your selection");
/* 177 */     JPanel buttonPanel = new JPanel(new FlowLayout(2));
/* 178 */     JSeparator separator1 = new JSeparator(1);
/* 179 */     JSeparator separator2 = new JSeparator(1);
/* 180 */     Dimension dime = separator1.getPreferredSize();
/* 181 */     dime.height = (refreshButton.getPreferredSize()).height;
/* 182 */     separator1.setPreferredSize(dime);
/* 183 */     separator2.setPreferredSize(dime);
/* 184 */     this.checkRPicker = new JCheckBox(" Track Picker");
/* 185 */     JLabel filterLabel = new JLabel("  ➠ Track Analysis : ");
/* 186 */     filterLabel.setFont(new Font("Dialog", 1, 13));
/* 187 */     filterLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/* 188 */     JPanel filterPanel = new JPanel(new FlowLayout(0));
/* 189 */     filterPanel.add(filterLabel);
/* 190 */     filterPanel.add(this.checkRPicker);
/* 191 */     filterPanel.add(Box.createHorizontalStrut(20));
/* 192 */     JPanel filterMain = new JPanel(new FlowLayout(0));
/* 193 */     filterMain.add(filterPanel);
/* 194 */     buttonPanel.add(refreshButton);
/* 195 */     buttonPanel.add(paintButton);
/* 196 */     buttonPanel.add(separator1);
/* 197 */     buttonPanel.add(enableButton);
/* 198 */     buttonPanel.add(disableButton);
/* 199 */     buttonPanel.add(separator2);
/* 200 */     buttonPanel.add(tInsideButton);
/* 201 */     buttonPanel.add(tOutsideButton);
/* 202 */     filterMain.add(buttonPanel);
/* 203 */     mainPanel.add(this.jScrollPaneImages);
/* 204 */     mainPanel.add(Box.createVerticalStrut(5));
/* 205 */     mainPanel.add(filterMain);
/* 206 */     mainPanel.add(this.jScrollPaneTrack);
/* 207 */     JLabel settingsLabel = new JLabel("  ➠ Settings for Filters/Classes : ");
/* 208 */     settingsLabel.setFont(new Font("Dialog", 1, 13));
/* 209 */     settingsLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/* 210 */     JPanel settingsPanel = new JPanel(new FlowLayout(0));
/* 211 */     settingsPanel.add(settingsLabel);
/* 212 */     mainPanel.add(settingsPanel);
/* 213 */     JPanel filtersMin = new JPanel(new FlowLayout(0));
/* 214 */     this.filterMin = new JSpinner(new SpinnerNumberModel(30, 0, 5000, 1));
/* 215 */     this.filterMin.setPreferredSize(new Dimension(60, 20));
/* 216 */     final JSlider sliderMin = new JSlider(0, 300, 50);
/* 217 */     sliderMin.setPreferredSize(new Dimension(150, 15));
/* 218 */     JLabel filterMinLabel = new JLabel("              Min :  ");
/* 219 */     filtersMin.add(filterMinLabel);
/* 220 */     filtersMin.add(sliderMin);
/* 221 */     filtersMin.add(Box.createHorizontalStrut(2));
/* 222 */     filtersMin.add(this.filterMin);
/* 223 */     JPanel filtersMax = new JPanel(new FlowLayout(0));
/* 224 */     this.filterMax = new JSpinner(new SpinnerNumberModel(200, 0, 5000, 1));
/* 225 */     this.filterMax.setPreferredSize(new Dimension(60, 20));
/* 226 */     final JSlider sliderMax = new JSlider(0, 300, 150);
/* 227 */     sliderMax.setPreferredSize(new Dimension(150, 15));
/* 228 */     JLabel filterMaxLabel = new JLabel("              Max :  ");
/* 229 */     filtersMax.add(filterMaxLabel);
/* 230 */     filtersMax.add(sliderMax);
/* 231 */     filtersMax.add(Box.createHorizontalStrut(2));
/* 232 */     filtersMax.add(this.filterMax);
/* 233 */     JPanel boxPanel2 = new JPanel();
/* 234 */     boxPanel2.setLayout(new BoxLayout(boxPanel2, 1));
/* 235 */     final IntervalMarker intervalMarker = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), 
/* 236 */         new Color(0, 102, 0), new BasicStroke(1.5F), 0.5F);
/* 237 */     this.histogram = this.hs2.createChartPanel("", new double[] { 0.0D, 0.0D, 0.0D }, 100, intervalMarker);
/*     */     
/* 239 */     JPanel chartPanel2 = new JPanel(new BorderLayout());
/* 240 */     chartPanel2.setPreferredSize(new Dimension(390, 180));
/* 241 */     chartPanel2.add((Component)this.histogram);
/* 242 */     boxPanel2.add(chartPanel2);
/* 243 */     JPanel controlPanel2 = this.hs2.createControlPanel();
/* 244 */     boxPanel2.add(controlPanel2);
/* 245 */     JPanel filtersMain2 = new JPanel();
/* 246 */     filtersMain2.setLayout(new BoxLayout(filtersMain2, 1));
/* 247 */     filtersMain2.add(boxPanel2);
/* 248 */     filtersMain2.add(filtersMin);
/* 249 */     filtersMain2.add(filtersMax);
/* 250 */     JLabel featureTrack = new JLabel(" » Track-Features :  ");
/* 251 */     featureTrack.setFont(new Font("Dialog", 1, 13));
/* 252 */     this.comboFilters = new JComboBox<>();
/* 253 */     for (int i = 1; i < columnNamesTrack.length; i++)
/* 254 */       this.comboFilters.addItem((String)columnNamesTrack[i]); 
/* 255 */     this.comboFilters.setPreferredSize(new Dimension(130, 25));
/* 256 */     this.comboFilters.setSelectedIndex(0);
/* 257 */     this.comboFilters.setOpaque(true);
/* 258 */     JPanel panelFilters = new JPanel(new FlowLayout(0));
/* 259 */     JSeparator separator3 = new JSeparator(1);
/* 260 */     Dimension dime2 = separator3.getPreferredSize();
/* 261 */     dime2.height = (filtersMain2.getPreferredSize()).height;
/* 262 */     separator3.setPreferredSize(dime2);
/* 263 */     panelFilters.add(filtersMain2);
/* 264 */     panelFilters.add(separator3);
/* 265 */     modelListClass = new DefaultListModel<>();
/* 266 */     classList = new JList<>(modelListClass);
/* 267 */     modelListFeature = new DefaultListModel<>();
/* 268 */     featureList = new JList<>(modelListFeature);
/* 269 */     final ColorEditorTrack colorEditor = new ColorEditorTrack(featureList);
/* 270 */     JScrollPane scrollListFilter = new JScrollPane(featureList);
/* 271 */     JScrollPane scrollListClass = new JScrollPane(classList);
/* 272 */     Dimension d = featureList.getPreferredSize();
/* 273 */     d.width = 150;
/* 274 */     d.height = 90;
/* 275 */     scrollListFilter.setPreferredSize(d);
/* 276 */     scrollListClass.setPreferredSize(d);
/* 277 */     JPanel filterPanelButtons = new JPanel(new FlowLayout(0));
/* 278 */     JPanel classPanelButtons = new JPanel();
/* 279 */     classPanelButtons.setLayout(new BoxLayout(classPanelButtons, 1));
/* 280 */     filterPanelButtons.add(scrollListFilter);
/* 281 */     JPanel fButtonsPanel = new JPanel();
/* 282 */     fButtonsPanel.setLayout(new BoxLayout(fButtonsPanel, 1));
/* 283 */     JButton addButton = new JButton();
/* 284 */     ImageIcon iconAdd = createImageIcon("/images/add.png");
/* 285 */     Icon addCell = new ImageIcon(iconAdd.getImage().getScaledInstance(14, 16, 4));
/* 286 */     addButton.setIcon(addCell);
/* 287 */     addButton.setToolTipText("Click this button to add features");
/* 288 */     JButton remButton = new JButton();
/* 289 */     ImageIcon iconRem = createImageIcon("/images/remove.png");
/* 290 */     Icon remCell = new ImageIcon(iconRem.getImage().getScaledInstance(14, 16, 4));
/* 291 */     remButton.setIcon(remCell);
/* 292 */     remButton.setToolTipText("Click this button to remove features");
/* 293 */     JButton classButton = new JButton();
/* 294 */     ImageIcon iconClass = createImageIcon("/images/classes.png");
/* 295 */     Icon classCell = new ImageIcon(iconClass.getImage().getScaledInstance(14, 16, 4));
/* 296 */     classButton.setIcon(classCell);
/* 297 */     classButton.setToolTipText("Click this button to create a class.");
/* 298 */     JButton remClassButton = new JButton();
/* 299 */     remClassButton.setIcon(remCell);
/* 300 */     remClassButton.setToolTipText("Click this button to remove a class.");
/* 301 */     fButtonsPanel.add(addButton);
/* 302 */     fButtonsPanel.add(remButton);
/* 303 */     filterPanelButtons.add(fButtonsPanel);
/* 304 */     classPanelButtons.add(classButton);
/* 305 */     classPanelButtons.add(remClassButton);
/* 306 */     JPanel classPanel = new JPanel(new FlowLayout(0));
/* 307 */     classPanel.add(scrollListClass);
/* 308 */     classPanel.add(classPanelButtons);
/* 309 */     JPanel boxPanel = new JPanel();
/* 310 */     boxPanel.setLayout(new BoxLayout(boxPanel, 1));
/* 311 */     boxPanel.add(this.comboFilters);
/* 312 */     boxPanel.add(Box.createHorizontalStrut(5));
/* 313 */     boxPanel.add(filterPanelButtons);
/* 314 */     boxPanel.add(Box.createHorizontalStrut(5));
/* 315 */     boxPanel.add(classPanel);
/* 316 */     boxPanel.add(panelPngCsv);
/* 317 */     panelFilters.add(boxPanel);
/* 318 */     mainPanel.add(panelFilters);
/* 319 */     add(tabbedPaneTrack);
/* 320 */     createMovieTable();
/*     */     
/* 322 */     paintButton.addItemListener(new ItemListener() {
/*     */           public void itemStateChanged(final ItemEvent ev) {
/* 324 */             ChooserWizardPanel.this.paintThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 326 */                     if (ev.getStateChange() == 1) {
/* 327 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).paintAndDisableAction();
/* 328 */                     } else if (ev.getStateChange() == 2) {
/* 329 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).resetAndEnableAction();
/*     */                     } 
/*     */                   }
/*     */                 });
/* 333 */             ChooserWizardPanel.this.paintThread.start();
/*     */           }
/*     */         });
/* 336 */     csvButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 339 */             ChooserWizardPanel.this.csvThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 341 */                     List<String> columnTrackHead = new ArrayList<>();
/* 342 */                     for (int j = 0; j < ChooserWizardPanel.modelTrack.getColumnCount(); j++) {
/* 343 */                       columnTrackHead.add(ChooserWizardPanel.modelTrack.getColumnName(j));
/*     */                     }
/* 345 */                     ResultsTable rt = new ResultsTable(Integer.valueOf(ChooserWizardPanel.modelTrack.getRowCount()));
/* 346 */                     if (rt != null) {
/* 347 */                       rt.reset();
/*     */                     }
/* 349 */                     for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 350 */                       for (int k = 0; k < ChooserWizardPanel.modelTrack.getColumnCount(); k++) {
/* 351 */                         if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE)
/*     */                         {
/* 353 */                           if (((String)columnTrackHead.get(k)).equals(columnTrackHead.get(0)) == Boolean.TRUE.booleanValue()) {
/*     */                             
/* 355 */                             rt.setValue(columnTrackHead.get(k), i, (
/* 356 */                                 (JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, k)).getText());
/*     */                           } else {
/*     */                             
/* 359 */                             rt.setValue(columnTrackHead.get(k), i, ChooserWizardPanel.modelTrack.getValueAt(i, k).toString());
/*     */                           }  } 
/*     */                       } 
/* 362 */                     }  rt.show("Resutls tracks");
/*     */                   }
/*     */                 });
/* 365 */             ChooserWizardPanel.this.csvThread.start();
/*     */           }
/*     */         });
/*     */     
/* 369 */     pngButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 372 */             ChooserWizardPanel.this.pngThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 374 */                     if (IJ.getImage() == null)
/* 375 */                       IJ.error("You must have an image window active."); 
/* 376 */                     if (IJ.getImage() != null) {
/* 377 */                       JFrame pngFrame = new JFrame();
/* 378 */                       JFileChooser fileChooser = new JFileChooser();
/* 379 */                       fileChooser.setFileSelectionMode(1);
/* 380 */                       fileChooser.setDialogTitle("Specify a directory to save");
/* 381 */                       int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */                       
/* 383 */                       if (userSelection == 0) {
/* 384 */                         File fileToSave = fileChooser.getSelectedFile();
/*     */                         
/* 386 */                         int firstFrame = 0, lastFrame = 0;
/* 387 */                         if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/* 388 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/* 389 */                           lastFrame = Math.min(IJ.getImage().getNFrames(), 
/* 390 */                               Math.max(IJ.getImage().getNFrames(), 1));
/*     */                         } 
/* 392 */                         if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/* 393 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/* 394 */                           lastFrame = Math.min(IJ.getImage().getNSlices(), 
/* 395 */                               Math.max(IJ.getImage().getNSlices(), 1));
/*     */                         } 
/*     */                         
/* 398 */                         Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/* 399 */                         int width = bounds.width;
/* 400 */                         int height = bounds.height;
/* 401 */                         int nCaptures = lastFrame - firstFrame + 1;
/* 402 */                         ImageStack stack = new ImageStack(width, height);
/* 403 */                         int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/* 404 */                         int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/* 405 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/* 406 */                         for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*     */                           
/* 408 */                           ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/* 409 */                               frame);
/* 410 */                           BufferedImage bi = new BufferedImage(width, height, 2);
/* 411 */                           ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/* 412 */                           ColorProcessor cp = new ColorProcessor(bi);
/* 413 */                           int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/* 414 */                               frame);
/* 415 */                           stack.addSlice(
/* 416 */                               ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/* 417 */                               (ImageProcessor)cp);
/*     */                         } 
/* 419 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/* 420 */                         ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/* 421 */                             ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/* 422 */                         ChooserWizardPanel.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
/* 423 */                         IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 424 */                             "Capture Overlay for " + IJ.getImage().getShortTitle());
/*     */                       } 
/*     */                     } 
/*     */                   }
/*     */                 });
/* 429 */             ChooserWizardPanel.this.pngThread.start();
/*     */           }
/*     */         });
/* 432 */     refreshButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 435 */             ChooserWizardPanel.this.refreshThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 438 */                     ChooserWizardPanel.trackEnable = "trackEnable";
/* 439 */                     ProcessTrackMateXml.tracksVisible = true;
/* 440 */                     ProcessTrackMateXml.spotsVisible = false;
/* 441 */                     ProcessTrackMateXml ptx = new ProcessTrackMateXml();
/* 442 */                     ptx.processTrackMateXml();
/*     */                   }
/*     */                 });
/* 445 */             ChooserWizardPanel.this.refreshThread.start();
/*     */           }
/*     */         });
/*     */     
/* 449 */     enableButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 452 */             ChooserWizardPanel.this.enableThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 454 */                     ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).enableTracks();
/*     */                   }
/*     */                 });
/* 457 */             ChooserWizardPanel.this.enableThread.start();
/*     */           }
/*     */         });
/*     */     
/* 461 */     disableButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 464 */             ChooserWizardPanel.this.disableThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 466 */                     ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).disableTracks();
/*     */                   }
/*     */                 });
/* 469 */             ChooserWizardPanel.this.disableThread.start();
/*     */           }
/*     */         });
/*     */     
/* 473 */     this.checkRPicker.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(final ItemEvent e)
/*     */           {
/* 477 */             ChooserWizardPanel.this.pickerThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 479 */                     if (e.getStateChange() == 1)
/* 480 */                       ChooserWizardPanel.command = "enable"; 
/* 481 */                     if (e.getStateChange() == 2) {
/* 482 */                       ChooserWizardPanel.command = null;
/* 483 */                       ProcessTrackMateXml.selectionModel.clearSpotSelection();
/* 484 */                       ProcessTrackMateXml.selectionModel.clearSelection();
/*     */                       return;
/*     */                     } 
/*     */                   }
/*     */                 });
/* 489 */             ChooserWizardPanel.this.pickerThread.start();
/*     */           }
/*     */         });
/*     */     
/* 493 */     sliderMin.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 496 */             ChooserWizardPanel.this.slMinThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 499 */                     (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.setValue(Integer.valueOf(sliderMin.getValue()));
/* 500 */                     intervalMarker.setStartValue(sliderMin.getValue());
/*     */                   }
/*     */                 });
/* 503 */             ChooserWizardPanel.this.slMinThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 508 */     this.filterMin.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 511 */             ChooserWizardPanel.this.filterMinThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 513 */                     sliderMin.setValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue()).intValue());
/* 514 */                     intervalMarker.setStartValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue()).intValue());
/*     */                   }
/*     */                 });
/* 517 */             ChooserWizardPanel.this.filterMinThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 522 */     sliderMax.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 525 */             ChooserWizardPanel.this.slMaxThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 527 */                     (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.setValue(Integer.valueOf(sliderMax.getValue()));
/* 528 */                     intervalMarker.setEndValue(sliderMax.getValue());
/*     */                   }
/*     */                 });
/* 531 */             ChooserWizardPanel.this.slMaxThread.start();
/*     */           }
/*     */         });
/*     */     
/* 535 */     this.filterMax.addChangeListener(new ChangeListener()
/*     */         {
/*     */           public void stateChanged(ChangeEvent e) {
/* 538 */             ChooserWizardPanel.this.filterMaxThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 540 */                     sliderMax.setValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue()).intValue());
/* 541 */                     intervalMarker.setEndValue(((Integer)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue()).intValue());
/*     */                   }
/*     */                 });
/* 544 */             ChooserWizardPanel.this.filterMaxThread.start();
/*     */           }
/*     */         });
/* 547 */     tInsideButton.addItemListener(new ItemListener() {
/*     */           public void itemStateChanged(final ItemEvent ev) {
/* 549 */             ChooserWizardPanel.this.tInsideThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 551 */                     if (ev.getStateChange() == 1) {
/* 552 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).toggleInsideAction();
/* 553 */                     } else if (ev.getStateChange() == 2) {
/* 554 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).resetToggleInsideAction();
/*     */                     } 
/*     */                   }
/*     */                 });
/* 558 */             ChooserWizardPanel.this.tInsideThread.start();
/*     */           }
/*     */         });
/* 561 */     this.comboFilters.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 563 */             ChooserWizardPanel.this.filtersThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 565 */                     String selectedName = (String)(ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem();
/* 566 */                     int selectedIndex = (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedIndex();
/* 567 */                     double[] values = null;
/*     */ 
/*     */                     
/* 570 */                     values = new double[ChooserWizardPanel.tableTrack.getRowCount()];
/* 571 */                     for (int r = 0; r < ChooserWizardPanel.tableTrack.getRowCount(); r++) {
/* 572 */                       for (int c = 0; c < ChooserWizardPanel.tableTrack.getColumnCount(); c++)
/* 573 */                         values[r] = Double.parseDouble((String)ChooserWizardPanel.tableTrack.getValueAt(r, selectedIndex + 2)); 
/*     */                     } 
/* 575 */                     double max = values[0];
/* 576 */                     for (int i = 1; i < values.length; i++) {
/* 577 */                       if (values[i] > max)
/* 578 */                         max = values[i]; 
/*     */                     } 
/* 580 */                     sliderMin.setMinimum(0);
/* 581 */                     sliderMin.setMaximum((int)max);
/* 582 */                     sliderMax.setMinimum(0);
/* 583 */                     sliderMax.setMaximum((int)max);
/*     */                     
/* 585 */                     (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).hs2.addHistogramSeries(selectedName, values, (int)max, intervalMarker);
/*     */                   }
/*     */                 });
/* 588 */             ChooserWizardPanel.this.filtersThread.start();
/*     */           }
/*     */         });
/*     */     
/* 592 */     classButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 595 */             ChooserWizardPanel.this.classThread = new Thread(new Runnable() {
/*     */                   public void run() {
/* 597 */                     ColorEditorTrack.myFrame.setVisible(true);
/* 598 */                     colorEditor.setClassAction();
/*     */                   }
/*     */                 });
/* 601 */             ChooserWizardPanel.this.classThread.start();
/*     */           }
/*     */         });
/* 604 */     remClassButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 607 */             ChooserWizardPanel.this.remClassThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 610 */                     String classSelectedValue = ChooserWizardPanel.classList.getSelectedValue();
/* 611 */                     int[] classSelectedIndex = ChooserWizardPanel.classList.getSelectedIndices(); int i;
/* 612 */                     for (i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); i++) {
/* 613 */                       if (((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1))).getText()
/* 614 */                         .equals(classSelectedValue))
/* 615 */                         ChooserWizardPanel.modelTrack.setValueAt(ChooserWizardPanel.labelReset, i, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1)); 
/*     */                     } 
/* 617 */                     for (i = 0; i < classSelectedIndex.length; i++)
/* 618 */                       ChooserWizardPanel.modelListClass.removeElementAt(classSelectedIndex[i]); 
/*     */                   }
/*     */                 });
/* 621 */             ChooserWizardPanel.this.remClassThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 626 */     addButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 629 */             ChooserWizardPanel.this.addThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 632 */                     List<String> listFilters = new ArrayList<>();
/*     */                     
/* 634 */                     if (ChooserWizardPanel.featureList.getModel().getSize() < 1) {
/* 635 */                       ChooserWizardPanel.modelListFeature.addElement(String.valueOf((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem()) + ":  [" + 
/* 636 */                           (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue() + "," + (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue() + "]");
/*     */                     }
/* 638 */                     if (ChooserWizardPanel.featureList.getModel().getSize() >= 1) {
/* 639 */                       for (int i = 0; i < ChooserWizardPanel.featureList.getModel().getSize(); i++) {
/* 640 */                         listFilters.add(String.valueOf(((String)ChooserWizardPanel.featureList.getModel().getElementAt(i)).substring(0, (
/* 641 */                                 (String)ChooserWizardPanel.featureList.getModel().getElementAt(i)).lastIndexOf(":"))));
/*     */                       }
/* 643 */                       if (!listFilters.contains((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem().toString())) {
/* 644 */                         ChooserWizardPanel.modelListFeature.addElement(String.valueOf((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem()) + ":  [" + 
/* 645 */                             (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMin.getValue() + "," + (ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).filterMax.getValue() + "]");
/*     */                       }
/* 647 */                       if (listFilters.contains((ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this)).comboFilters.getSelectedItem().toString())) {
/*     */                         return;
/*     */                       }
/*     */                     } 
/*     */                   }
/*     */                 });
/* 653 */             ChooserWizardPanel.this.addThread.start();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 658 */     remButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 661 */             ChooserWizardPanel.this.remThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/*     */                     try {
/* 665 */                       int[] indexes = ChooserWizardPanel.featureList.getSelectedIndices();
/* 666 */                       for (int i = 0; i < indexes.length; i++)
/* 667 */                         ChooserWizardPanel.modelListFeature.remove(indexes[i]); 
/* 668 */                     } catch (Exception e1) {
/* 669 */                       e1.printStackTrace();
/*     */                     } 
/*     */                   }
/*     */                 });
/* 673 */             ChooserWizardPanel.this.remThread.start();
/*     */           }
/*     */         });
/* 676 */     tOutsideButton.addItemListener(new ItemListener() {
/*     */           public void itemStateChanged(final ItemEvent ev) {
/* 678 */             ChooserWizardPanel.this.tOutsideThread = new Thread(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 681 */                     if (ev.getStateChange() == 1) {
/* 682 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).toggleOutsideAction();
/* 683 */                     } else if (ev.getStateChange() == 2) {
/* 684 */                       ChooserWizardPanel.null.access$0(ChooserWizardPanel.null.this).resetToggleInsideAction();
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */             
/* 689 */             ChooserWizardPanel.this.tOutsideThread.start();
/*     */           }
/*     */         });
/*     */   } Thread tOutsideThread; Thread enableThread; Thread disableThread; Thread slMinThread; Thread filterMinThread; Thread slMaxThread; Thread filterMaxThread; Thread filtersThread; Thread pickerThread; Thread classThread; Thread remClassThread; Thread addThread; Thread remThread;
/*     */   public ImageIcon createImageIcon(String path) {
/* 694 */     URL img = getClass().getResource(path);
/*     */     
/* 696 */     if (img != null) {
/* 697 */       return new ImageIcon(img);
/*     */     }
/* 699 */     System.err.println("Couldn't find file: " + path);
/* 700 */     return null;
/*     */   }
/*     */   
/*     */   public void toggleOutsideAction() {
/* 704 */     Roi mainRoi = null;
/* 705 */     if (IJ.getImage().getRoi().getType() == 0)
/* 706 */       mainRoi = IJ.getImage().getRoi(); 
/* 707 */     this.indexesTI = new ArrayList<>();
/*     */     
/* 709 */     for (int i = 0; i < modelTrack.getRowCount(); i++) {
/*     */       
/* 711 */       if (mainRoi
/* 712 */         .contains(
/*     */           
/* 714 */           (int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelTrack
/* 715 */               .getValueAt(i, tableTrack.convertColumnIndexToModel(13)).toString())), 
/*     */           
/* 717 */           (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(
/* 718 */               modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(14))
/* 719 */               .toString()))) == Boolean.FALSE.booleanValue()) {
/* 720 */         this.indexesTI.add(Integer.valueOf(i));
/* 721 */         modelTrack.setValueAt(Boolean.valueOf(false), i, tableTrack.convertColumnIndexToModel(0));
/*     */         
/* 723 */         int trackID = Integer.parseInt((String)tableTrack.getValueAt(i, 2));
/* 724 */         ProcessTrackMateXml.model.beginUpdate();
/*     */         try {
/* 726 */           ProcessTrackMateXml.model.setTrackVisibility(Integer.valueOf(trackID), false);
/*     */         } finally {
/* 728 */           ProcessTrackMateXml.model.endUpdate();
/*     */         } 
/*     */         
/* 731 */         ProcessTrackMateXml.displayer.refresh();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleInsideAction() {
/* 739 */     Roi mainRoi = null;
/* 740 */     if (IJ.getImage().getRoi().getType() == 0)
/* 741 */       mainRoi = IJ.getImage().getRoi(); 
/* 742 */     this.indexesTI = new ArrayList<>();
/*     */     
/* 744 */     for (int i = 0; i < modelTrack.getRowCount(); i++) {
/*     */       
/* 746 */       if (mainRoi
/* 747 */         .contains(
/*     */           
/* 749 */           (int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelTrack
/* 750 */               .getValueAt(i, tableTrack.convertColumnIndexToModel(13)).toString())), 
/*     */           
/* 752 */           (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(
/* 753 */               modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(14))
/* 754 */               .toString()))) == Boolean.TRUE.booleanValue()) {
/* 755 */         this.indexesTI.add(Integer.valueOf(i));
/* 756 */         modelTrack.setValueAt(Boolean.valueOf(false), i, tableTrack.convertColumnIndexToModel(0));
/*     */         
/* 758 */         int trackID = Integer.parseInt((String)tableTrack.getValueAt(i, 2));
/* 759 */         ProcessTrackMateXml.model.beginUpdate();
/*     */         try {
/* 761 */           ProcessTrackMateXml.model.setTrackVisibility(Integer.valueOf(trackID), false);
/*     */         } finally {
/* 763 */           ProcessTrackMateXml.model.endUpdate();
/*     */         } 
/*     */         
/* 766 */         ProcessTrackMateXml.displayer.refresh();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetToggleInsideAction() {
/* 775 */     for (int row = 0; row < modelTrack.getRowCount(); row++) {
/* 776 */       modelTrack.setValueAt(Boolean.valueOf(true), tableTrack.convertRowIndexToModel(row), 
/* 777 */           tableTrack.convertColumnIndexToModel(0));
/* 778 */       int trackID = Integer.parseInt((String)tableTrack.getValueAt(row, 2));
/* 779 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 781 */         ProcessTrackMateXml.model.setTrackVisibility(Integer.valueOf(trackID), true);
/*     */       } finally {
/* 783 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */       
/* 786 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createTrackTable() {
/* 793 */     modelTrack = new DefaultTableModel((Object[][])ProcessTrackMateXml.dataTrack, (Object[])ProcessTrackMateXml.columnHeadersTrack)
/*     */       {
/*     */         public Class<?> getColumnClass(int column)
/*     */         {
/* 797 */           if (getRowCount() > 0) {
/* 798 */             Object value = getValueAt(0, column);
/* 799 */             if (value != null) {
/* 800 */               return getValueAt(0, column).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 804 */           return super.getColumnClass(column);
/*     */         }
/*     */       };
/*     */     
/* 808 */     modelTrack.addColumn("Enable");
/* 809 */     tableTrack.setModel(modelTrack);
/* 810 */     tableTrack.moveColumn(tableTrack.getColumnCount() - 1, 0);
/* 811 */     tableTrack.setSelectionBackground(new Color(229, 255, 204));
/* 812 */     tableTrack.setSelectionForeground(new Color(0, 102, 0));
/* 813 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/* 814 */     centerRenderer.setHorizontalAlignment(0);
/* 815 */     tableTrack.setDefaultRenderer(String.class, centerRenderer);
/* 816 */     tableTrack.setAutoResizeMode(0);
/* 817 */     tableTrack.setRowHeight(45);
/* 818 */     tableTrack.setAutoCreateRowSorter(true);
/* 819 */     tableTrack.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer()); int u;
/* 820 */     for (u = 0; u < tableTrack.getColumnCount(); u++)
/* 821 */       tableTrack.getColumnModel().getColumn(u).setPreferredWidth(90); 
/* 822 */     for (u = 3; u < tableTrack.getColumnCount(); u++)
/* 823 */       tableTrack.getColumnModel().getColumn(u).setPreferredWidth(130); 
/*     */     int i;
/* 825 */     for (i = 0; i < tableTrack.getRowCount(); i++)
/* 826 */       tableTrack.setValueAt(Boolean.valueOf(true), i, 0); 
/* 827 */     tableTrack.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
/* 828 */     labelReset = new JLabel();
/* 829 */     labelReset.setText("");
/* 830 */     labelReset.setOpaque(true);
/* 831 */     labelReset.setBackground(new Color(214, 217, 223));
/* 832 */     for (i = 0; i < modelTrack.getRowCount(); i++) {
/* 833 */       modelTrack.setValueAt(labelReset, i, tableTrack.convertColumnIndexToModel(1));
/*     */     }
/*     */   }
/*     */   
/*     */   public void enableTracks() {
/* 838 */     this.indexesToReset1 = new ArrayList<>();
/* 839 */     this.tracksID1 = new ArrayList<>();
/* 840 */     int[] selectedRows = tableTrack.getSelectedRows();
/* 841 */     for (int i = 0; i < selectedRows.length; i++) {
/* 842 */       this.indexesToReset1.add(Integer.valueOf(selectedRows[i]));
/* 843 */       modelTrack.setValueAt(Boolean.valueOf(true), selectedRows[i], tableTrack.convertColumnIndexToModel(0));
/* 844 */       this.tracksID1.add(Integer.valueOf(Integer.parseInt((String)tableTrack.getValueAt(selectedRows[i], 2))));
/*     */     } 
/*     */     
/* 847 */     for (int row = 0; row < this.tracksID1.size(); row++) {
/*     */       
/* 849 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 851 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID1.get(row), true);
/*     */       } finally {
/* 853 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */ 
/*     */       
/* 857 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableTracks() {
/* 864 */     this.indexesToReset1 = new ArrayList<>();
/* 865 */     this.tracksID1 = new ArrayList<>();
/* 866 */     int[] selectedRows = tableTrack.getSelectedRows();
/* 867 */     for (int i = 0; i < selectedRows.length; i++) {
/* 868 */       this.indexesToReset1.add(Integer.valueOf(selectedRows[i]));
/* 869 */       modelTrack.setValueAt(Boolean.valueOf(false), selectedRows[i], tableTrack.convertColumnIndexToModel(0));
/* 870 */       this.tracksID1.add(Integer.valueOf(Integer.parseInt((String)tableTrack.getValueAt(selectedRows[i], 2))));
/*     */     } 
/*     */     
/* 873 */     for (int row = 0; row < this.tracksID1.size(); row++) {
/*     */       
/* 875 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 877 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID1.get(row), false);
/*     */       } finally {
/* 879 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */ 
/*     */       
/* 883 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void createMovieTable() {
/* 891 */     tableImages.setSelectionBackground(new Color(229, 255, 204));
/* 892 */     tableImages.setSelectionForeground(new Color(0, 102, 0));
/* 893 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/* 894 */     centerRenderer.setHorizontalAlignment(0);
/* 895 */     tableImages.setDefaultRenderer(String.class, centerRenderer);
/* 896 */     tableImages.setAutoResizeMode(0);
/* 897 */     tableImages.setRowHeight(95);
/* 898 */     tableImages.setAutoCreateRowSorter(true);
/* 899 */     tableImages.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
/* 900 */     tableImages.setModel(FirstWizardPanel.modelImages);
/*     */   }
/*     */ 
/*     */   
/*     */   public void paintAndDisableAction() {
/* 905 */     this.indexesToReset = new ArrayList<>();
/* 906 */     this.tracksID = new ArrayList<>();
/*     */     
/* 908 */     for (int i = 0; i < modelTrack.getRowCount(); i++) {
/* 909 */       if (((JLabel)modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(1))).getBackground()
/* 910 */         .equals(new Color(214, 217, 223)) == Boolean.TRUE.booleanValue()) {
/* 911 */         this.indexesToReset.add(Integer.valueOf(i));
/* 912 */         modelTrack.setValueAt(Boolean.valueOf(false), i, tableTrack.convertColumnIndexToModel(0));
/* 913 */         this.tracksID.add(Integer.valueOf(Integer.parseInt((String)tableTrack.getValueAt(i, 2))));
/*     */       } 
/* 915 */     }  for (int row = 0; row < this.tracksID.size(); row++) {
/*     */       
/* 917 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 919 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID.get(row), false);
/*     */       } finally {
/* 921 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */ 
/*     */       
/* 925 */       ProcessTrackMateXml.displayer.refresh();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetAndEnableAction() {
/* 931 */     for (int i = 0; i < this.indexesToReset.size(); i++)
/* 932 */       modelTrack.setValueAt(Boolean.valueOf(true), tableTrack.convertRowIndexToModel(((Integer)this.indexesToReset.get(i)).intValue()), 
/* 933 */           tableTrack.convertColumnIndexToModel(0)); 
/* 934 */     for (int row = 0; row < this.tracksID.size(); row++) {
/* 935 */       ProcessTrackMateXml.model.beginUpdate();
/*     */       try {
/* 937 */         ProcessTrackMateXml.model.setTrackVisibility(this.tracksID.get(row), true);
/*     */       } finally {
/* 939 */         ProcessTrackMateXml.model.endUpdate();
/*     */       } 
/*     */     } 
/* 942 */     ProcessTrackMateXml.displayer.refresh();
/*     */   }
/*     */   
/*     */   public void update() {
/* 946 */     setNextButtonEnabled(true);
/* 947 */     setFinishButtonEnabled(true);
/* 948 */     setBackButtonEnabled(true);
/*     */   }
/*     */   
/*     */   public void next() {
/* 952 */     switchPanel(2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void back() {
/* 958 */     switchPanel(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final void transferCalibration(ImagePlus from, ImagePlus to) {
/* 963 */     Calibration fc = from.getCalibration();
/* 964 */     Calibration tc = to.getCalibration();
/*     */     
/* 966 */     tc.setUnit(fc.getUnit());
/* 967 */     tc.setTimeUnit(fc.getTimeUnit());
/* 968 */     tc.frameInterval = fc.frameInterval;
/*     */     
/* 970 */     double mag = from.getCanvas().getMagnification();
/* 971 */     fc.pixelWidth /= mag;
/* 972 */     fc.pixelHeight /= mag;
/* 973 */     tc.pixelDepth = fc.pixelDepth;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ChooserWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */