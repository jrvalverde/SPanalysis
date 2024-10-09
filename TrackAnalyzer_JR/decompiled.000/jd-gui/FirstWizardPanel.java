/*      */ import fiji.plugin.trackmate.Spot;
/*      */ import fiji.plugin.trackmate.SpotCollection;
/*      */ import ij.IJ;
/*      */ import ij.ImagePlus;
/*      */ import ij.ImageStack;
/*      */ import ij.gui.Roi;
/*      */ import ij.io.FileInfo;
/*      */ import ij.measure.Calibration;
/*      */ import ij.measure.ResultsTable;
/*      */ import ij.process.ColorProcessor;
/*      */ import ij.process.ImageProcessor;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.FlowLayout;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ItemEvent;
/*      */ import java.awt.event.ItemListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.Box;
/*      */ import javax.swing.BoxLayout;
/*      */ import javax.swing.DefaultListModel;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JComboBox;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JList;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.JSlider;
/*      */ import javax.swing.JSpinner;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JToggleButton;
/*      */ import javax.swing.ListSelectionModel;
/*      */ import javax.swing.SpinnerNumberModel;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ import javax.swing.event.ChangeListener;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.DefaultTableModel;
/*      */ import jwizardcomponent.JWizardComponents;
/*      */ import org.jfree.chart.ChartPanel;
/*      */ import org.jfree.chart.plot.IntervalMarker;
/*      */ 
/*      */ 
/*      */ public class FirstWizardPanel
/*      */   extends LabelWizardPanel
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   static JTable tableImages;
/*      */   static JTable tableSpot;
/*      */   static DefaultTableModel modelImages;
/*      */   static DefaultTableModel modelSpot;
/*      */   static ImagePlus[] imps;
/*      */   static ImagePlus[] impsPZ;
/*      */   ImageIcon[] icons;
/*      */   Thread mainProcess;
/*      */   ImagePlus impAnal;
/*   78 */   static String command = ""; static String spotEnable = ""; List<Spot> removedSpots; List<Spot> spots;
/*      */   JSpinner filterMin;
/*      */   JSpinner filterMax;
/*      */   ChartPanel histogram;
/*   82 */   HistogramFilterVersion hs2 = new HistogramFilterVersion(); IntervalMarker intervalMarker; JCheckBox checkRPicker; static JList<String> classList; static JList<String> featureList; static DefaultListModel<String> modelListClass;
/*      */   static DefaultListModel<String> modelListFeature;
/*      */   static JComboBox<String> comboFilters;
/*      */   static JLabel labelReset;
/*      */   List<Integer> indexesToReset;
/*      */   List<Integer> spotID;
/*      */   List<Integer> spotIDTI;
/*      */   List<Integer> spotIDTO;
/*      */   List<Integer> indexesTI;
/*      */   List<Integer> indexesTO;
/*      */   static JScrollPane jScrollPaneImages;
/*      */   static JScrollPane jScrollPaneSpot;
/*      */   static Icon iconSpotCell;
/*      */   static Icon refreshCell;
/*      */   
/*      */   public FirstWizardPanel(JWizardComponents wizardComponents) {
/*   98 */     super(wizardComponents, "");
/*      */ 
/*      */     
/*  101 */     File imageFolder = new File(TrackAnalyzer_.textImages.getText());
/*  102 */     File[] listOfFiles = imageFolder.listFiles();
/*  103 */     String[] imageTitles = new String[listOfFiles.length];
/*  104 */     File[] filesXML = new File[listOfFiles.length];
/*      */     
/*  106 */     for (int u = 0; u < filesXML.length; u++) {
/*  107 */       filesXML[u] = new File(TrackAnalyzer_.textXml.getText());
/*      */     }
/*  109 */     impsPZ = new ImagePlus[imageTitles.length];
/*  110 */     imps = new ImagePlus[imageTitles.length];
/*  111 */     this.icons = new ImageIcon[imps.length];
/*  112 */     for (int i = 0; i < listOfFiles.length; i++) {
/*  113 */       if (listOfFiles[i].isFile())
/*  114 */         imageTitles[i] = listOfFiles[i].getName(); 
/*  115 */       imps[i] = IJ.openImage(String.valueOf(TrackAnalyzer_.textImages.getText()) + "/" + imageTitles[i]);
/*  116 */       impsPZ[i] = extractTFrame(imps[i], 1);
/*      */       
/*  118 */       this.icons[i] = new ImageIcon(getScaledImage(impsPZ[i].getImage(), 90, 95));
/*      */     } 
/*      */     
/*  121 */     tableImages = new JTable();
/*      */     
/*  123 */     tableSpot = new JTable();
/*  124 */     modelImages = new DefaultTableModel();
/*  125 */     modelSpot = new DefaultTableModel();
/*  126 */     Object[] columnNames = { "Movie", "Title", "Extension" };
/*  127 */     columnNamesSpot = new Object[] { "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", 
/*  128 */         "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MEAN_INTENSITY", "MEDIAN_INTENSITY", "MIN_INTENSITY", 
/*  129 */         "MAX_INTENSITY", "TOTAL_INTENSITY", "STANDARD_DEVIATION", "CONTRAST", "SNR", "ESTIMATED_DIAMETER", 
/*  130 */         "MORPHOLOGY", "ELLIPSOIDFIT_SEMIAXISLENGTH_C", "ELLIPSOIDFIT_SEMIAXISLENGTH_B", 
/*  131 */         "ELLIPSOIDFIT_SEMIAXISLENGTH_A", "ELLIPSOIDFIT_AXISPHI_C", "ELLIPSOIDFIT_AXISPHI_B", 
/*  132 */         "ELLIPSOIDFIT_AXISPHI_A", "ELLIPSOIDFIT_AXISTHETA_C", "ELLIPSOIDFIT_AXISTHETA_B", 
/*  133 */         "ELLIPSOIDFIT_AXISTHETA_A", "MANUAL_COLOR" };
/*  134 */     Object[][] data = new Object[imps.length][columnNames.length];
/*  135 */     for (int j = 0; j < data.length; j++) {
/*  136 */       for (int m = 0; m < (data[j]).length; m++)
/*  137 */         data[j][m] = ""; 
/*      */     } 
/*  139 */     modelSpot = new DefaultTableModel();
/*  140 */     modelImages = new DefaultTableModel(data, columnNames)
/*      */       {
/*      */         public Class<?> getColumnClass(int column)
/*      */         {
/*  144 */           if (getRowCount() > 0) {
/*  145 */             Object value = getValueAt(0, column);
/*  146 */             if (value != null) {
/*  147 */               return getValueAt(0, column).getClass();
/*      */             }
/*      */           } 
/*      */           
/*  151 */           return super.getColumnClass(column);
/*      */         }
/*      */       };
/*      */     
/*  155 */     tableImages.setModel(modelImages);
/*  156 */     tableSpot.setModel(modelSpot);
/*  157 */     tableSpot.setSelectionMode(1);
/*  158 */     tableImages.getColumnModel().getColumn(0).setPreferredWidth(90);
/*  159 */     tableImages.getColumnModel().getColumn(1).setPreferredWidth(460);
/*  160 */     tableImages.getColumnModel().getColumn(2).setPreferredWidth(80);
/*  161 */     jScrollPaneImages = new JScrollPane(tableImages);
/*  162 */     jScrollPaneSpot = new JScrollPane(tableSpot);
/*  163 */     jScrollPaneImages.setPreferredSize(new Dimension(590, 240));
/*  164 */     jScrollPaneSpot.setPreferredSize(new Dimension(590, 240));
/*  165 */     JPanel mainPanel = new JPanel();
/*  166 */     mainPanel.setLayout(new BoxLayout(mainPanel, 1));
/*  167 */     jScrollPaneImages.setBorder(BorderFactory.createTitledBorder(""));
/*  168 */     jScrollPaneSpot.setBorder(BorderFactory.createTitledBorder(""));
/*  169 */     JTabbedPane tabbedPaneSpot = new JTabbedPane(1);
/*  170 */     ImageIcon iconSpot = createImageIcon("images/spot.png");
/*  171 */     iconSpotCell = new ImageIcon(iconSpot.getImage().getScaledInstance(18, 20, 4));
/*  172 */     JButton pngButton = new JButton();
/*  173 */     ImageIcon iconPng = createImageIcon("images/save.png");
/*  174 */     Icon pngCell = new ImageIcon(iconPng.getImage().getScaledInstance(18, 20, 4));
/*  175 */     pngButton.setIcon(pngCell);
/*  176 */     pngButton.setToolTipText("Click to capture spots overlay.");
/*  177 */     JPanel panelPng = new JPanel(new FlowLayout(0));
/*  178 */     panelPng.add(pngButton);
/*  179 */     JButton csvButton = new JButton();
/*  180 */     ImageIcon iconCsv = createImageIcon("images/csv.png");
/*  181 */     Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
/*  182 */     csvButton.setIcon(csvCell);
/*  183 */     csvButton.setToolTipText("Click to export your spots table selection.");
/*  184 */     JPanel panelCsv = new JPanel(new FlowLayout(0));
/*  185 */     panelCsv.add(csvButton);
/*  186 */     JPanel panelPngCsv = new JPanel(new FlowLayout(0));
/*  187 */     panelPngCsv.add(panelPng);
/*  188 */     panelPngCsv.add(panelCsv);
/*  189 */     tabbedPaneSpot.addTab("SPOTS ", iconSpotCell, mainPanel, "Display Spot Analysis");
/*  190 */     tabbedPaneSpot.setTabLayoutPolicy(1);
/*  191 */     JButton refreshButton = new JButton();
/*  192 */     ImageIcon iconRefresh = createImageIcon("images/refresh.png");
/*  193 */     refreshCell = new ImageIcon(iconRefresh.getImage().getScaledInstance(18, 20, 4));
/*  194 */     refreshButton.setIcon(refreshCell);
/*  195 */     refreshButton.setToolTipText("Click this button to get spot analysis");
/*  196 */     JToggleButton paintButton = new JToggleButton();
/*  197 */     ImageIcon iconPaint = createImageIcon("images/paint.png");
/*  198 */     Icon paintCell = new ImageIcon(iconPaint.getImage().getScaledInstance(18, 20, 4));
/*  199 */     paintButton.setIcon(paintCell);
/*  200 */     paintButton.setToolTipText("Click this button to display labeled-spots");
/*  201 */     JToggleButton tInsideButton = new JToggleButton();
/*  202 */     ImageIcon iconTI = createImageIcon("images/tinside.png");
/*  203 */     Icon TICell = new ImageIcon(iconTI.getImage().getScaledInstance(18, 20, 4));
/*  204 */     tInsideButton.setIcon(TICell);
/*  205 */     tInsideButton.setToolTipText("Click this button to toggle inside spots.");
/*  206 */     JToggleButton tOutsideButton = new JToggleButton();
/*  207 */     ImageIcon iconTO = createImageIcon("images/toutside.png");
/*  208 */     Icon TOCell = new ImageIcon(iconTO.getImage().getScaledInstance(18, 20, 4));
/*  209 */     tOutsideButton.setIcon(TOCell);
/*  210 */     tOutsideButton.setToolTipText("Click this button to toggle outside spots.");
/*  211 */     JButton enableButton = new JButton();
/*  212 */     ImageIcon iconEnable = createImageIcon("images/enable.png");
/*  213 */     Icon enableCell = new ImageIcon(iconEnable.getImage().getScaledInstance(18, 20, 4));
/*  214 */     enableButton.setIcon(enableCell);
/*  215 */     enableButton.setToolTipText("Click this button to enable your selection");
/*  216 */     JButton disableButton = new JButton();
/*  217 */     ImageIcon iconDisable = createImageIcon("images/disable.png");
/*  218 */     Icon disableCell = new ImageIcon(iconDisable.getImage().getScaledInstance(18, 20, 4));
/*  219 */     disableButton.setIcon(disableCell);
/*  220 */     disableButton.setToolTipText("Click this button to disable your selection");
/*  221 */     JPanel buttonPanel = new JPanel(new FlowLayout(2));
/*  222 */     JSeparator separator1 = new JSeparator(1);
/*  223 */     JSeparator separator2 = new JSeparator(1);
/*  224 */     Dimension dime = separator1.getPreferredSize();
/*  225 */     dime.height = (refreshButton.getPreferredSize()).height;
/*  226 */     separator1.setPreferredSize(dime);
/*  227 */     separator2.setPreferredSize(dime);
/*  228 */     this.checkRPicker = new JCheckBox(" Spot Picker");
/*  229 */     JLabel filterLabel = new JLabel("  ➠ Spot Analysis : ");
/*  230 */     filterLabel.setFont(new Font("Dialog", 1, 13));
/*  231 */     filterLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/*  232 */     JPanel filterPanel = new JPanel(new FlowLayout(0));
/*  233 */     filterPanel.add(filterLabel);
/*  234 */     filterPanel.add(this.checkRPicker);
/*  235 */     filterPanel.add(Box.createHorizontalStrut(20));
/*  236 */     JPanel filterMain = new JPanel(new FlowLayout(0));
/*  237 */     filterMain.add(filterPanel);
/*  238 */     buttonPanel.add(refreshButton);
/*  239 */     buttonPanel.add(paintButton);
/*  240 */     buttonPanel.add(separator1);
/*  241 */     buttonPanel.add(enableButton);
/*  242 */     buttonPanel.add(disableButton);
/*  243 */     buttonPanel.add(separator2);
/*  244 */     buttonPanel.add(tInsideButton);
/*  245 */     buttonPanel.add(tOutsideButton);
/*  246 */     filterMain.add(buttonPanel);
/*  247 */     mainPanel.add(jScrollPaneImages);
/*  248 */     mainPanel.add(Box.createVerticalStrut(5));
/*  249 */     mainPanel.add(filterMain);
/*  250 */     mainPanel.add(jScrollPaneSpot);
/*  251 */     JLabel settingsLabel = new JLabel("  ➠ Settings for Filters/Classes : ");
/*  252 */     settingsLabel.setFont(new Font("Dialog", 1, 13));
/*  253 */     settingsLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/*  254 */     JPanel settingsPanel = new JPanel(new FlowLayout(0));
/*  255 */     settingsPanel.add(settingsLabel);
/*  256 */     mainPanel.add(settingsPanel);
/*  257 */     JPanel filtersMin = new JPanel(new FlowLayout(0));
/*  258 */     this.filterMin = new JSpinner(new SpinnerNumberModel(30, 0, 5000, 1));
/*  259 */     this.filterMin.setPreferredSize(new Dimension(60, 20));
/*  260 */     final JSlider sliderMin = new JSlider(0, 300, 50);
/*  261 */     sliderMin.setPreferredSize(new Dimension(150, 15));
/*  262 */     JLabel filterMinLabel = new JLabel("              Min :  ");
/*  263 */     filtersMin.add(filterMinLabel);
/*  264 */     filtersMin.add(sliderMin);
/*  265 */     filtersMin.add(Box.createHorizontalStrut(2));
/*  266 */     filtersMin.add(this.filterMin);
/*  267 */     JPanel filtersMax = new JPanel(new FlowLayout(0));
/*  268 */     this.filterMax = new JSpinner(new SpinnerNumberModel(200, 0, 5000, 1));
/*  269 */     this.filterMax.setPreferredSize(new Dimension(60, 20));
/*  270 */     final JSlider sliderMax = new JSlider(0, 300, 150);
/*  271 */     sliderMax.setPreferredSize(new Dimension(150, 15));
/*  272 */     JLabel filterMaxLabel = new JLabel("              Max :  ");
/*  273 */     filtersMax.add(filterMaxLabel);
/*  274 */     filtersMax.add(sliderMax);
/*  275 */     filtersMax.add(Box.createHorizontalStrut(2));
/*  276 */     filtersMax.add(this.filterMax);
/*  277 */     JPanel boxPanel2 = new JPanel();
/*  278 */     boxPanel2.setLayout(new BoxLayout(boxPanel2, 1));
/*  279 */     final IntervalMarker intervalMarker = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), 
/*  280 */         new Color(0, 102, 0), new BasicStroke(1.5F), 0.5F);
/*  281 */     this.histogram = this.hs2.createChartPanel("", new double[] { 0.0D, 0.0D, 0.0D }, 100, intervalMarker);
/*      */     
/*  283 */     JPanel chartPanel2 = new JPanel(new BorderLayout());
/*  284 */     chartPanel2.setPreferredSize(new Dimension(390, 180));
/*  285 */     chartPanel2.add((Component)this.histogram);
/*  286 */     boxPanel2.add(chartPanel2);
/*  287 */     JPanel controlPanel2 = this.hs2.createControlPanel();
/*  288 */     boxPanel2.add(controlPanel2);
/*  289 */     JPanel filtersMain2 = new JPanel();
/*  290 */     filtersMain2.setLayout(new BoxLayout(filtersMain2, 1));
/*  291 */     filtersMain2.add(boxPanel2);
/*  292 */     filtersMain2.add(filtersMin);
/*  293 */     filtersMain2.add(filtersMax);
/*  294 */     JLabel featureSpot = new JLabel(" » Spot-Features :  ");
/*  295 */     featureSpot.setFont(new Font("Dialog", 1, 13));
/*  296 */     comboFilters = new JComboBox<>();
/*  297 */     for (int k = 0; k < columnNamesSpot.length; k++)
/*  298 */       comboFilters.addItem((String)columnNamesSpot[k]); 
/*  299 */     comboFilters.setPreferredSize(new Dimension(130, 25));
/*  300 */     comboFilters.setSelectedIndex(0);
/*  301 */     comboFilters.setOpaque(true);
/*  302 */     JPanel panelFilters = new JPanel(new FlowLayout(0));
/*  303 */     JSeparator separator3 = new JSeparator(1);
/*  304 */     Dimension dime2 = separator3.getPreferredSize();
/*  305 */     dime2.height = (filtersMain2.getPreferredSize()).height;
/*  306 */     separator3.setPreferredSize(dime2);
/*  307 */     panelFilters.add(filtersMain2);
/*  308 */     panelFilters.add(separator3);
/*  309 */     modelListClass = new DefaultListModel<>();
/*  310 */     classList = new JList<>(modelListClass);
/*  311 */     modelListFeature = new DefaultListModel<>();
/*  312 */     featureList = new JList<>(modelListFeature);
/*  313 */     final ColorEditorSpot colorEditor = new ColorEditorSpot(featureList);
/*  314 */     JScrollPane scrollListFilter = new JScrollPane(featureList);
/*  315 */     JScrollPane scrollListClass = new JScrollPane(classList);
/*  316 */     Dimension d = featureList.getPreferredSize();
/*  317 */     d.width = 150;
/*  318 */     d.height = 90;
/*  319 */     scrollListFilter.setPreferredSize(d);
/*  320 */     scrollListClass.setPreferredSize(d);
/*  321 */     JPanel filterPanelButtons = new JPanel(new FlowLayout(0));
/*  322 */     JPanel classPanelButtons = new JPanel();
/*  323 */     classPanelButtons.setLayout(new BoxLayout(classPanelButtons, 1));
/*  324 */     filterPanelButtons.add(scrollListFilter);
/*  325 */     JPanel fButtonsPanel = new JPanel();
/*  326 */     fButtonsPanel.setLayout(new BoxLayout(fButtonsPanel, 1));
/*  327 */     JButton addButton = new JButton();
/*  328 */     ImageIcon iconAdd = createImageIcon("images/add.png");
/*  329 */     Icon addCell = new ImageIcon(iconAdd.getImage().getScaledInstance(14, 16, 4));
/*  330 */     addButton.setIcon(addCell);
/*  331 */     addButton.setToolTipText("Click this button to add features");
/*  332 */     JButton remButton = new JButton();
/*  333 */     ImageIcon iconRem = createImageIcon("images/remove.png");
/*  334 */     Icon remCell = new ImageIcon(iconRem.getImage().getScaledInstance(14, 16, 4));
/*  335 */     remButton.setIcon(remCell);
/*  336 */     remButton.setToolTipText("Click this button to remove features");
/*  337 */     JButton classButton = new JButton();
/*  338 */     ImageIcon iconClass = createImageIcon("images/classes.png");
/*  339 */     Icon classCell = new ImageIcon(iconClass.getImage().getScaledInstance(14, 16, 4));
/*  340 */     classButton.setIcon(classCell);
/*  341 */     classButton.setToolTipText("Click this button to create a class.");
/*  342 */     JButton remClassButton = new JButton();
/*  343 */     remClassButton.setIcon(remCell);
/*  344 */     remClassButton.setToolTipText("Click this button to remove a class.");
/*  345 */     fButtonsPanel.add(addButton);
/*  346 */     fButtonsPanel.add(remButton);
/*  347 */     filterPanelButtons.add(fButtonsPanel);
/*  348 */     classPanelButtons.add(classButton);
/*  349 */     classPanelButtons.add(remClassButton);
/*  350 */     JPanel classPanel = new JPanel(new FlowLayout(0));
/*  351 */     classPanel.add(scrollListClass);
/*  352 */     classPanel.add(classPanelButtons);
/*  353 */     JPanel boxPanel = new JPanel();
/*  354 */     boxPanel.setLayout(new BoxLayout(boxPanel, 1));
/*  355 */     boxPanel.add(comboFilters);
/*  356 */     boxPanel.add(Box.createHorizontalStrut(5));
/*  357 */     boxPanel.add(filterPanelButtons);
/*  358 */     boxPanel.add(Box.createHorizontalStrut(5));
/*  359 */     boxPanel.add(classPanel);
/*  360 */     boxPanel.add(panelPngCsv);
/*  361 */     panelFilters.add(boxPanel);
/*  362 */     mainPanel.add(panelFilters);
/*  363 */     add(tabbedPaneSpot);
/*  364 */     createMovieTable();
/*      */     
/*  366 */     refreshButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  369 */             FirstWizardPanel.this.refreshThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  371 */                     FirstWizardPanel.spotEnable = "spotEnable";
/*  372 */                     ProcessTrackMateXml.tracksVisible = false;
/*  373 */                     ProcessTrackMateXml.spotsVisible = true;
/*  374 */                     ProcessTrackMateXml ptx = new ProcessTrackMateXml();
/*  375 */                     ptx.processTrackMateXml();
/*      */                   }
/*      */                 });
/*  378 */             FirstWizardPanel.this.refreshThread.start();
/*      */           }
/*      */         });
/*  381 */     csvButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  384 */             FirstWizardPanel.this.csvThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  386 */                     List<String> columnSpotHead = new ArrayList<>();
/*  387 */                     for (int j = 0; j < FirstWizardPanel.modelSpot.getColumnCount(); j++) {
/*  388 */                       columnSpotHead.add(FirstWizardPanel.modelSpot.getColumnName(j));
/*      */                     }
/*  390 */                     ResultsTable rt = new ResultsTable(Integer.valueOf(FirstWizardPanel.modelSpot.getRowCount()));
/*  391 */                     if (rt != null) {
/*  392 */                       rt.reset();
/*      */                     }
/*  394 */                     for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/*  395 */                       for (int k = 0; k < FirstWizardPanel.modelSpot.getColumnCount(); k++) {
/*  396 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/*      */                         {
/*  398 */                           if (((String)columnSpotHead.get(k)).equals(columnSpotHead.get(0)) == Boolean.TRUE.booleanValue()) {
/*      */                             
/*  400 */                             rt.setValue(columnSpotHead.get(k), i, (
/*  401 */                                 (JLabel)FirstWizardPanel.modelSpot.getValueAt(i, k)).getText());
/*      */                           } else {
/*      */                             
/*  404 */                             rt.setValue(columnSpotHead.get(k), i, FirstWizardPanel.modelSpot.getValueAt(i, k).toString());
/*      */                           }  } 
/*      */                       } 
/*  407 */                     }  JFrame pngFrame = new JFrame();
/*  408 */                     JFileChooser fileChooser = new JFileChooser();
/*  409 */                     fileChooser.setFileSelectionMode(1);
/*  410 */                     fileChooser.setDialogTitle("Specify a directory to save csv file");
/*  411 */                     int userSelection = fileChooser.showSaveDialog(pngFrame);
/*      */                     
/*  413 */                     if (userSelection == 0) {
/*  414 */                       File fileToSave = fileChooser.getSelectedFile();
/*      */                       try {
/*  416 */                         rt.saveAs(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + "SpotStatistics for-" + 
/*  417 */                             IJ.getImage().getShortTitle() + ".csv");
/*  418 */                       } catch (IOException e1) {
/*      */                         
/*  420 */                         e1.printStackTrace();
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */                 });
/*  425 */             FirstWizardPanel.this.csvThread.start();
/*      */           }
/*      */         });
/*  428 */     pngButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  431 */             FirstWizardPanel.this.pngThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  433 */                     if (IJ.getImage() == null)
/*  434 */                       IJ.error("You must have an image window active."); 
/*  435 */                     if (IJ.getImage() != null) {
/*  436 */                       JFrame pngFrame = new JFrame();
/*  437 */                       JFileChooser fileChooser = new JFileChooser();
/*  438 */                       fileChooser.setFileSelectionMode(1);
/*  439 */                       fileChooser.setDialogTitle("Specify a directory to save");
/*  440 */                       int userSelection = fileChooser.showSaveDialog(pngFrame);
/*      */                       
/*  442 */                       if (userSelection == 0) {
/*  443 */                         File fileToSave = fileChooser.getSelectedFile();
/*      */                         
/*  445 */                         int firstFrame = 0, lastFrame = 0;
/*  446 */                         if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/*  447 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/*  448 */                           lastFrame = Math.min(IJ.getImage().getNFrames(), 
/*  449 */                               Math.max(IJ.getImage().getNFrames(), 1));
/*      */                         } 
/*  451 */                         if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/*  452 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/*  453 */                           lastFrame = Math.min(IJ.getImage().getNSlices(), 
/*  454 */                               Math.max(IJ.getImage().getNSlices(), 1));
/*      */                         } 
/*      */                         
/*  457 */                         Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/*  458 */                         int width = bounds.width;
/*  459 */                         int height = bounds.height;
/*  460 */                         int nCaptures = lastFrame - firstFrame + 1;
/*  461 */                         ImageStack stack = new ImageStack(width, height);
/*  462 */                         int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/*  463 */                         int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/*  464 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/*  465 */                         for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*      */                           
/*  467 */                           ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/*  468 */                               frame);
/*  469 */                           BufferedImage bi = new BufferedImage(width, height, 2);
/*  470 */                           ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/*  471 */                           ColorProcessor cp = new ColorProcessor(bi);
/*  472 */                           int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/*  473 */                               frame);
/*  474 */                           stack.addSlice(
/*  475 */                               ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/*  476 */                               (ImageProcessor)cp);
/*      */                         } 
/*  478 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/*  479 */                         ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/*  480 */                             ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/*  481 */                         FirstWizardPanel.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
/*  482 */                         IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/*  483 */                             "Capture Overlay for " + IJ.getImage().getShortTitle());
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */                 });
/*  488 */             FirstWizardPanel.this.pngThread.start();
/*      */           }
/*      */         });
/*      */     
/*  492 */     paintButton.addItemListener(new ItemListener() {
/*      */           public void itemStateChanged(final ItemEvent ev) {
/*  494 */             FirstWizardPanel.this.paintThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  496 */                     if (ev.getStateChange() == 1) {
/*  497 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).paintAndDisableAction();
/*  498 */                     } else if (ev.getStateChange() == 2) {
/*  499 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).resetAndEnableAction();
/*      */                     } 
/*      */                   }
/*      */                 });
/*  503 */             FirstWizardPanel.this.paintThread.start();
/*      */           }
/*      */         });
/*  506 */     tInsideButton.addItemListener(new ItemListener() {
/*      */           public void itemStateChanged(final ItemEvent ev) {
/*  508 */             FirstWizardPanel.this.tInsideThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  510 */                     if (ev.getStateChange() == 1) {
/*  511 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).toggleInsideAction();
/*  512 */                     } else if (ev.getStateChange() == 2) {
/*  513 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).resetToggleInsideAction();
/*      */                     } 
/*      */                   }
/*      */                 });
/*  517 */             FirstWizardPanel.this.tInsideThread.start();
/*      */           }
/*      */         });
/*  520 */     tOutsideButton.addItemListener(new ItemListener() {
/*      */           public void itemStateChanged(final ItemEvent ev) {
/*  522 */             FirstWizardPanel.this.tOutsideThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  524 */                     if (ev.getStateChange() == 1) {
/*  525 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).toggleOutsideAction();
/*  526 */                     } else if (ev.getStateChange() == 2) {
/*  527 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).resetToggleOutsideAction();
/*      */                     } 
/*      */                   }
/*      */                 });
/*  531 */             FirstWizardPanel.this.tOutsideThread.start();
/*      */           }
/*      */         });
/*  534 */     enableButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  537 */             FirstWizardPanel.this.enableThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  539 */                     FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).enableSpots();
/*      */                   }
/*      */                 });
/*  542 */             FirstWizardPanel.this.enableThread.start();
/*      */           }
/*      */         });
/*  545 */     disableButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  548 */             FirstWizardPanel.this.disableThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  550 */                     FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).disableSpots();
/*      */                   }
/*      */                 });
/*  553 */             FirstWizardPanel.this.disableThread.start();
/*      */           }
/*      */         });
/*      */     
/*  557 */     sliderMin.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  560 */             FirstWizardPanel.this.slMinThread = new Thread(new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  563 */                     (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.setValue(Integer.valueOf(sliderMin.getValue()));
/*  564 */                     intervalMarker.setStartValue(sliderMin.getValue());
/*      */                   }
/*      */                 });
/*  567 */             FirstWizardPanel.this.slMinThread.start();
/*      */           }
/*      */         });
/*      */     
/*  571 */     this.filterMin.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  574 */             FirstWizardPanel.this.filterMinThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  576 */                     sliderMin.setValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue()).intValue());
/*  577 */                     intervalMarker.setStartValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue()).intValue());
/*      */                   }
/*      */                 });
/*  580 */             FirstWizardPanel.this.filterMinThread.start();
/*      */           }
/*      */         });
/*      */     
/*  584 */     sliderMax.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  587 */             FirstWizardPanel.this.slMaxThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  589 */                     (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.setValue(Integer.valueOf(sliderMax.getValue()));
/*  590 */                     intervalMarker.setEndValue(sliderMax.getValue());
/*      */                   }
/*      */                 });
/*  593 */             FirstWizardPanel.this.slMaxThread.start();
/*      */           }
/*      */         });
/*      */     
/*  597 */     this.filterMax.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  600 */             FirstWizardPanel.this.filterMaxThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  602 */                     sliderMax.setValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue()).intValue());
/*  603 */                     intervalMarker.setEndValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue()).intValue());
/*      */                   }
/*      */                 });
/*  606 */             FirstWizardPanel.this.filterMaxThread.start();
/*      */           }
/*      */         });
/*  609 */     comboFilters.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent e) {
/*  611 */             FirstWizardPanel.this.filtersThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  613 */                     String selectedName = (String)FirstWizardPanel.comboFilters.getSelectedItem();
/*  614 */                     int selectedIndex = FirstWizardPanel.comboFilters.getSelectedIndex();
/*  615 */                     double[] values = null;
/*      */ 
/*      */                     
/*  618 */                     values = new double[FirstWizardPanel.tableSpot.getRowCount()];
/*  619 */                     for (int r = 0; r < FirstWizardPanel.tableSpot.getRowCount(); r++) {
/*  620 */                       for (int c = 0; c < FirstWizardPanel.tableSpot.getColumnCount(); c++)
/*  621 */                         values[r] = Double.parseDouble((String)FirstWizardPanel.tableSpot.getValueAt(r, selectedIndex + 2)); 
/*      */                     } 
/*  623 */                     double max = values[0];
/*  624 */                     for (int i = 1; i < values.length; i++) {
/*  625 */                       if (values[i] > max)
/*  626 */                         max = values[i]; 
/*      */                     } 
/*  628 */                     sliderMin.setMinimum(0);
/*  629 */                     sliderMin.setMaximum((int)max);
/*  630 */                     sliderMax.setMinimum(0);
/*  631 */                     sliderMax.setMaximum((int)max);
/*      */                     
/*  633 */                     (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).hs2.addHistogramSeries(selectedName, values, (int)max, intervalMarker);
/*      */                   }
/*      */                 });
/*  636 */             FirstWizardPanel.this.filtersThread.start();
/*      */           }
/*      */         });
/*      */     
/*  640 */     this.checkRPicker.addItemListener(new ItemListener()
/*      */         {
/*      */           public void itemStateChanged(final ItemEvent e)
/*      */           {
/*  644 */             FirstWizardPanel.this.pickerThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  646 */                     if (e.getStateChange() == 1)
/*  647 */                       FirstWizardPanel.command = "enable"; 
/*  648 */                     if (e.getStateChange() == 2) {
/*  649 */                       FirstWizardPanel.command = null;
/*  650 */                       ProcessTrackMateXml.selectionModel.clearSpotSelection();
/*  651 */                       ProcessTrackMateXml.selectionModel.clearSelection();
/*      */                       return;
/*      */                     } 
/*      */                   }
/*      */                 });
/*  656 */             FirstWizardPanel.this.pickerThread.start();
/*      */           }
/*      */         });
/*      */     
/*  660 */     classButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  663 */             FirstWizardPanel.this.classThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  665 */                     ColorEditorSpot.myFrame.setVisible(true);
/*  666 */                     colorEditor.setClassAction();
/*      */                   }
/*      */                 });
/*  669 */             FirstWizardPanel.this.classThread.start();
/*      */           }
/*      */         });
/*      */     
/*  673 */     remClassButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  676 */             FirstWizardPanel.this.remClassThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  678 */                     String classSelectedValue = FirstWizardPanel.classList.getSelectedValue();
/*  679 */                     int[] classSelectedIndex = FirstWizardPanel.classList.getSelectedIndices(); int i;
/*  680 */                     for (i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/*  681 */                       if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getText()
/*  682 */                         .equals(classSelectedValue))
/*  683 */                         FirstWizardPanel.modelSpot.setValueAt(FirstWizardPanel.labelReset, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1)); 
/*      */                     } 
/*  685 */                     for (i = 0; i < classSelectedIndex.length; i++)
/*  686 */                       FirstWizardPanel.modelListClass.removeElementAt(classSelectedIndex[i]); 
/*      */                   }
/*      */                 });
/*  689 */             FirstWizardPanel.this.remClassThread.start();
/*      */           }
/*      */         });
/*      */     
/*  693 */     addButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  696 */             FirstWizardPanel.this.addThread = new Thread(new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  699 */                     List<String> listFilters = new ArrayList<>();
/*      */                     
/*  701 */                     if (FirstWizardPanel.featureList.getModel().getSize() < 1) {
/*  702 */                       FirstWizardPanel.modelListFeature.addElement(String.valueOf(FirstWizardPanel.comboFilters.getSelectedItem()) + ":  [" + 
/*  703 */                           (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue() + "," + (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue() + "]");
/*      */                     }
/*  705 */                     if (FirstWizardPanel.featureList.getModel().getSize() >= 1) {
/*  706 */                       for (int i = 0; i < FirstWizardPanel.featureList.getModel().getSize(); i++) {
/*  707 */                         listFilters.add(String.valueOf(((String)FirstWizardPanel.featureList.getModel().getElementAt(i)).substring(0, (
/*  708 */                                 (String)FirstWizardPanel.featureList.getModel().getElementAt(i)).lastIndexOf(":"))));
/*      */                       }
/*  710 */                       if (!listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
/*  711 */                         FirstWizardPanel.modelListFeature.addElement(String.valueOf(FirstWizardPanel.comboFilters.getSelectedItem()) + ":  [" + 
/*  712 */                             (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue() + "," + (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue() + "]");
/*      */                       }
/*  714 */                       if (listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
/*      */                         return;
/*      */                       }
/*      */                     } 
/*      */                   }
/*      */                 });
/*  720 */             FirstWizardPanel.this.addThread.start();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  725 */     remButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  728 */             FirstWizardPanel.this.remThread = new Thread(new Runnable()
/*      */                 {
/*      */                   public void run() {
/*      */                     try {
/*  732 */                       int[] indexes = FirstWizardPanel.featureList.getSelectedIndices();
/*  733 */                       for (int i = 0; i < indexes.length; i++)
/*  734 */                         FirstWizardPanel.modelListFeature.remove(indexes[i]); 
/*  735 */                     } catch (Exception e1) {
/*  736 */                       e1.printStackTrace();
/*      */                     } 
/*      */                   }
/*      */                 });
/*      */             
/*  741 */             FirstWizardPanel.this.remThread.start();
/*      */           }
/*      */         });
/*      */   }
/*      */   static Object[] columnNamesSpot; Thread refreshThread; Thread csvThread; Thread pngThread; Thread paintThread; Thread tInsideThread; Thread tOutsideThread; Thread enableThread; Thread disableThread; Thread slMinThread; Thread filterMinThread; Thread slMaxThread; Thread filterMaxThread; Thread filtersThread; Thread pickerThread; Thread classThread; Thread remClassThread; Thread addThread; Thread remThread;
/*      */   
/*      */   public void toggleOutsideAction() {
/*  748 */     Roi mainRoi = null;
/*  749 */     if (IJ.getImage().getRoi().getType() == 0)
/*  750 */       mainRoi = IJ.getImage().getRoi(); 
/*  751 */     this.indexesTO = new ArrayList<>();
/*  752 */     for (int i = 0; i < modelSpot.getRowCount(); i++) {
/*  753 */       if (mainRoi
/*  754 */         .contains(
/*      */           
/*  756 */           (int)IJ.getImage().getCalibration().getRawX(
/*  757 */             Double.parseDouble(
/*  758 */               modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(5))
/*  759 */               .toString())), 
/*  760 */           (int)IJ.getImage().getCalibration().getRawY(
/*  761 */             Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(6))
/*  762 */               .toString()))) == Boolean.FALSE.booleanValue()) {
/*  763 */         this.indexesTO.add(Integer.valueOf(i));
/*  764 */         modelSpot.setValueAt(Boolean.valueOf(false), i, tableSpot.convertColumnIndexToModel(0));
/*  765 */         int spotID = Integer.parseInt((String)tableSpot.getValueAt(i, 2));
/*  766 */         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*      */         
/*  768 */         if (spot != null) {
/*  769 */           spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  770 */           ProcessTrackMateXml.model.endUpdate();
/*  771 */           ProcessTrackMateXml.displayer.refresh();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetToggleOutsideAction() {
/*  780 */     for (int row = 0; row < modelSpot.getRowCount(); row++) {
/*  781 */       modelSpot.setValueAt(Boolean.valueOf(true), tableSpot.convertRowIndexToModel(row), tableSpot.convertColumnIndexToModel(0));
/*  782 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  783 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  784 */       if (spot != null) {
/*  785 */         spot.putFeature("VISIBILITY", SpotCollection.ONE);
/*  786 */         ProcessTrackMateXml.model.endUpdate();
/*  787 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleInsideAction() {
/*  796 */     Roi mainRoi = null;
/*  797 */     if (IJ.getImage().getRoi().getType() == 0)
/*  798 */       mainRoi = IJ.getImage().getRoi(); 
/*  799 */     this.indexesTI = new ArrayList<>();
/*  800 */     for (int i = 0; i < modelSpot.getRowCount(); i++) {
/*  801 */       if (mainRoi
/*  802 */         .contains(
/*      */           
/*  804 */           (int)IJ.getImage().getCalibration().getRawX(
/*  805 */             Double.parseDouble(
/*  806 */               modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(5))
/*  807 */               .toString())), 
/*  808 */           (int)IJ.getImage().getCalibration().getRawY(
/*  809 */             Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(6))
/*  810 */               .toString()))) == Boolean.TRUE.booleanValue()) {
/*  811 */         this.indexesTI.add(Integer.valueOf(i));
/*  812 */         modelSpot.setValueAt(Boolean.valueOf(false), i, tableSpot.convertColumnIndexToModel(0));
/*  813 */         int spotID = Integer.parseInt((String)tableSpot.getValueAt(i, 2));
/*  814 */         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*      */         
/*  816 */         if (spot != null) {
/*  817 */           spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  818 */           ProcessTrackMateXml.model.endUpdate();
/*  819 */           ProcessTrackMateXml.displayer.refresh();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetToggleInsideAction() {
/*  829 */     for (int row = 0; row < modelSpot.getRowCount(); row++) {
/*  830 */       modelSpot.setValueAt(Boolean.valueOf(true), tableSpot.convertRowIndexToModel(row), tableSpot.convertColumnIndexToModel(0));
/*  831 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  832 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  833 */       if (spot != null) {
/*  834 */         spot.putFeature("VISIBILITY", SpotCollection.ONE);
/*  835 */         ProcessTrackMateXml.model.endUpdate();
/*  836 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintAndDisableAction() {
/*  844 */     this.indexesToReset = new ArrayList<>();
/*  845 */     this.spotID = new ArrayList<>();
/*  846 */     this.spots = new ArrayList<>();
/*  847 */     for (int i = 0; i < modelSpot.getRowCount(); i++) {
/*  848 */       if (((JLabel)modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(1))).getBackground()
/*  849 */         .equals(new Color(214, 217, 223)) == Boolean.TRUE.booleanValue()) {
/*  850 */         this.indexesToReset.add(Integer.valueOf(i));
/*  851 */         modelSpot.setValueAt(Boolean.valueOf(false), i, tableSpot.convertColumnIndexToModel(0));
/*  852 */         this.spotID.add(Integer.valueOf(Integer.parseInt((String)tableSpot.getValueAt(i, 2))));
/*      */       } 
/*  854 */     }  for (int row = 0; row < this.indexesToReset.size(); row++) {
/*  855 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(((Integer)this.indexesToReset.get(row)).intValue(), 2));
/*  856 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  857 */       if (spot != null) {
/*  858 */         spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  859 */         ProcessTrackMateXml.model.endUpdate();
/*  860 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetAndEnableAction() {
/*  868 */     for (int i = 0; i < this.indexesToReset.size(); i++)
/*  869 */       modelSpot.setValueAt(Boolean.valueOf(true), tableSpot.convertRowIndexToModel(((Integer)this.indexesToReset.get(i)).intValue()), 
/*  870 */           tableSpot.convertColumnIndexToModel(0)); 
/*  871 */     for (int row = 0; row < this.indexesToReset.size(); row++) {
/*  872 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(((Integer)this.indexesToReset.get(row)).intValue(), 2));
/*  873 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  874 */       if (spot != null) {
/*  875 */         spot.putFeature("VISIBILITY", SpotCollection.ONE);
/*  876 */         ProcessTrackMateXml.model.endUpdate();
/*  877 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableSpots() {
/*  886 */     ListSelectionModel lsm = tableSpot.getSelectionModel();
/*  887 */     int[] selectedIndices = tableSpot.getSelectedRows();
/*  888 */     for (int i = 0; i < selectedIndices.length; i++)
/*  889 */       tableSpot.setValueAt(Boolean.valueOf(true), selectedIndices[i], 0); 
/*  890 */     int selStart = lsm.getMinSelectionIndex();
/*  891 */     int selEnd = lsm.getMaxSelectionIndex();
/*  892 */     if (selStart < 0 || selEnd < 0) {
/*      */       return;
/*      */     }
/*  895 */     int minLine = Math.min(selStart, selEnd);
/*  896 */     int maxLine = Math.max(selStart, selEnd);
/*  897 */     for (int row = minLine; row <= maxLine; row++) {
/*  898 */       int spotIDEnable = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  899 */       Spot spotEnable = ProcessTrackMateXml.model.getSpots().search(spotIDEnable);
/*  900 */       if (spotEnable != null) {
/*  901 */         spotEnable.putFeature("VISIBILITY", SpotCollection.ONE);
/*  902 */         ProcessTrackMateXml.model.endUpdate();
/*  903 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableSpots() {
/*  911 */     ListSelectionModel lsm = tableSpot.getSelectionModel();
/*  912 */     int selStart = lsm.getMinSelectionIndex();
/*  913 */     int selEnd = lsm.getMaxSelectionIndex();
/*  914 */     if (selStart < 0 || selEnd < 0) {
/*      */       return;
/*      */     }
/*  917 */     int minLine = Math.min(selStart, selEnd);
/*  918 */     int maxLine = Math.max(selStart, selEnd);
/*  919 */     for (int row = minLine; row <= maxLine; row++) {
/*  920 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  921 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  922 */       if (spot != null) {
/*  923 */         spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  924 */         ProcessTrackMateXml.model.endUpdate();
/*  925 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  930 */     int[] selectedIndices = tableSpot.getSelectedRows();
/*  931 */     for (int i = 0; i < selectedIndices.length; i++) {
/*  932 */       tableSpot.setValueAt(Boolean.valueOf(false), selectedIndices[i], 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createSpotTable() {
/*  940 */     modelSpot = new DefaultTableModel((Object[][])ProcessTrackMateXml.dataSpot, (Object[])ProcessTrackMateXml.columnHeadersSpot)
/*      */       {
/*      */         public Class<?> getColumnClass(int column)
/*      */         {
/*  944 */           if (getRowCount() > 0) {
/*  945 */             Object value = getValueAt(0, column);
/*  946 */             if (value != null) {
/*  947 */               return getValueAt(0, column).getClass();
/*      */             }
/*      */           } 
/*      */           
/*  951 */           return super.getColumnClass(column);
/*      */         }
/*      */       };
/*      */     
/*  955 */     modelSpot.addColumn("Enable");
/*  956 */     tableSpot.setModel(modelSpot);
/*  957 */     tableSpot.moveColumn(tableSpot.getColumnCount() - 1, 0);
/*  958 */     tableSpot.setSelectionBackground(new Color(229, 255, 204));
/*  959 */     tableSpot.setSelectionForeground(new Color(0, 102, 0));
/*  960 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/*  961 */     centerRenderer.setHorizontalAlignment(0);
/*  962 */     tableSpot.setDefaultRenderer(String.class, centerRenderer);
/*  963 */     tableSpot.setAutoResizeMode(0);
/*  964 */     tableSpot.setRowHeight(45);
/*  965 */     tableSpot.setAutoCreateRowSorter(true);
/*  966 */     tableSpot.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer()); int u;
/*  967 */     for (u = 0; u < tableSpot.getColumnCount(); u++)
/*  968 */       tableSpot.getColumnModel().getColumn(u).setPreferredWidth(90); 
/*  969 */     for (u = 16; u < tableSpot.getColumnCount(); u++)
/*  970 */       tableSpot.getColumnModel().getColumn(u).setPreferredWidth(150); 
/*      */     int i;
/*  972 */     for (i = 0; i < tableSpot.getRowCount(); i++)
/*  973 */       tableSpot.setValueAt(Boolean.valueOf(true), i, 0); 
/*  974 */     tableSpot.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
/*  975 */     labelReset = new JLabel();
/*  976 */     labelReset.setText("");
/*  977 */     labelReset.setOpaque(true);
/*  978 */     labelReset.setBackground(new Color(214, 217, 223));
/*  979 */     for (i = 0; i < modelSpot.getRowCount(); i++) {
/*  980 */       modelSpot.setValueAt(labelReset, i, tableSpot.convertColumnIndexToModel(1));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void createMovieTable() {
/*  986 */     tableImages.setSelectionBackground(new Color(229, 255, 204));
/*  987 */     tableImages.setSelectionForeground(new Color(0, 102, 0));
/*  988 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/*  989 */     centerRenderer.setHorizontalAlignment(0);
/*  990 */     tableImages.setDefaultRenderer(String.class, centerRenderer);
/*  991 */     tableImages.setAutoResizeMode(0);
/*  992 */     tableImages.setRowHeight(95);
/*  993 */     tableImages.setAutoCreateRowSorter(true);
/*  994 */     tableImages.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
/*      */     
/*  996 */     for (int i = 0; i < modelImages.getRowCount(); i++) {
/*  997 */       modelImages.setValueAt(this.icons[i], i, tableImages.convertColumnIndexToModel(0));
/*  998 */       modelImages.setValueAt(imps[i].getShortTitle(), i, tableImages.convertColumnIndexToModel(1));
/*  999 */       modelImages.setValueAt(imps[i].getTitle().substring(imps[i].getTitle().lastIndexOf(".")), i, 
/* 1000 */           tableImages.convertColumnIndexToModel(2));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Image getScaledImage(Image srcImg, int w, int h) {
/* 1006 */     BufferedImage resizedImg = new BufferedImage(w, h, 1);
/* 1007 */     Graphics2D g2 = resizedImg.createGraphics();
/* 1008 */     g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 1009 */     g2.drawImage(srcImg, 0, 0, w, h, null);
/* 1010 */     g2.dispose();
/* 1011 */     return resizedImg;
/*      */   }
/*      */   
/*      */   public static ImageIcon createImageIcon(String path) {
/* 1015 */     URL imgURL = FirstWizardPanel.class.getResource(path);
/* 1016 */     if (imgURL != null) {
/* 1017 */       return new ImageIcon(imgURL);
/*      */     }
/* 1019 */     System.err.println("Couldn't find file: " + path);
/* 1020 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public ImagePlus extractTFrame(ImagePlus imp, int frame) {
/* 1025 */     int width = imp.getWidth();
/* 1026 */     int height = imp.getHeight();
/* 1027 */     int channels = imp.getNChannels();
/* 1028 */     int zslices = imp.getNSlices();
/* 1029 */     FileInfo fileInfo = imp.getOriginalFileInfo();
/* 1030 */     ImageStack stack2 = new ImageStack(width, height);
/* 1031 */     ImagePlus imp2 = new ImagePlus();
/* 1032 */     imp2.setTitle("T" + frame + "-" + imp.getTitle());
/*      */     
/* 1034 */     for (int z = 1; z <= zslices; z++) {
/* 1035 */       for (int c = 1; c <= channels; c++) {
/* 1036 */         int sliceSix = imp.getStackIndex(c, z, frame);
/* 1037 */         stack2.addSlice("", imp.getStack().getProcessor(sliceSix));
/*      */       } 
/* 1039 */     }  imp2.setStack(stack2);
/* 1040 */     imp2.setDimensions(channels, zslices, 1);
/* 1041 */     if (channels * zslices > 1)
/* 1042 */       imp2.setOpenAsHyperStack(true); 
/* 1043 */     imp2.setFileInfo(fileInfo);
/* 1044 */     return imp2;
/*      */   }
/*      */   
/*      */   private static final void transferCalibration(ImagePlus from, ImagePlus to) {
/* 1048 */     Calibration fc = from.getCalibration();
/* 1049 */     Calibration tc = to.getCalibration();
/*      */     
/* 1051 */     tc.setUnit(fc.getUnit());
/* 1052 */     tc.setTimeUnit(fc.getTimeUnit());
/* 1053 */     tc.frameInterval = fc.frameInterval;
/*      */     
/* 1055 */     double mag = from.getCanvas().getMagnification();
/* 1056 */     fc.pixelWidth /= mag;
/* 1057 */     fc.pixelHeight /= mag;
/* 1058 */     tc.pixelDepth = fc.pixelDepth;
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/FirstWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */