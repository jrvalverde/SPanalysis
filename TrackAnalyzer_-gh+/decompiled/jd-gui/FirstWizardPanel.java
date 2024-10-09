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
/*   79 */   static String command = ""; static String spotEnable = ""; List<Spot> removedSpots; List<Spot> spots;
/*      */   JSpinner filterMin;
/*      */   JSpinner filterMax;
/*      */   ChartPanel histogram;
/*   83 */   HistogramFilterVersion hs2 = new HistogramFilterVersion(); IntervalMarker intervalMarker; JCheckBox checkRPicker; static JList<String> classList; static JList<String> featureList; static DefaultListModel<String> modelListClass;
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
/*   99 */     super(wizardComponents, "");
/*      */ 
/*      */     
/*  102 */     File imageFolder = new File(TrackAnalyzer_.textImages.getText());
/*  103 */     File[] listOfFiles = imageFolder.listFiles();
/*  104 */     String[] imageTitles = new String[listOfFiles.length];
/*  105 */     File[] filesXML = new File[listOfFiles.length];
/*      */     
/*  107 */     for (int u = 0; u < filesXML.length; u++) {
/*  108 */       filesXML[u] = new File(TrackAnalyzer_.textXml.getText());
/*      */     }
/*  110 */     impsPZ = new ImagePlus[imageTitles.length];
/*  111 */     imps = new ImagePlus[imageTitles.length];
/*  112 */     this.icons = new ImageIcon[imps.length];
/*  113 */     for (int i = 0; i < listOfFiles.length; i++) {
/*  114 */       if (listOfFiles[i].isFile())
/*  115 */         imageTitles[i] = listOfFiles[i].getName(); 
/*  116 */       imps[i] = IJ.openImage(String.valueOf(TrackAnalyzer_.textImages.getText()) + "/" + imageTitles[i]);
/*  117 */       impsPZ[i] = extractTFrame(imps[i], 1);
/*      */       
/*  119 */       this.icons[i] = new ImageIcon(getScaledImage(impsPZ[i].getImage(), 90, 95));
/*      */     } 
/*      */     
/*  122 */     tableImages = new JTable();
/*      */     
/*  124 */     tableSpot = new JTable();
/*  125 */     modelImages = new DefaultTableModel();
/*  126 */     modelSpot = new DefaultTableModel();
/*  127 */     Object[] columnNames = { "Movie", "Title", "Extension" };
/*  128 */     columnNamesSpot = new Object[] { "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", 
/*  129 */         "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MEAN_INTENSITY", "MEDIAN_INTENSITY", "MIN_INTENSITY", 
/*  130 */         "MAX_INTENSITY", "TOTAL_INTENSITY", "STANDARD_DEVIATION", "CONTRAST", "SNR", "ESTIMATED_DIAMETER", 
/*  131 */         "MORPHOLOGY", "ELLIPSOIDFIT_SEMIAXISLENGTH_C", "ELLIPSOIDFIT_SEMIAXISLENGTH_B", 
/*  132 */         "ELLIPSOIDFIT_SEMIAXISLENGTH_A", "ELLIPSOIDFIT_AXISPHI_C", "ELLIPSOIDFIT_AXISPHI_B", 
/*  133 */         "ELLIPSOIDFIT_AXISPHI_A", "ELLIPSOIDFIT_AXISTHETA_C", "ELLIPSOIDFIT_AXISTHETA_B", 
/*  134 */         "ELLIPSOIDFIT_AXISTHETA_A", "MANUAL_COLOR" };
/*  135 */     Object[][] data = new Object[imps.length][columnNames.length];
/*  136 */     for (int j = 0; j < data.length; j++) {
/*  137 */       for (int m = 0; m < (data[j]).length; m++)
/*  138 */         data[j][m] = ""; 
/*      */     } 
/*  140 */     modelSpot = new DefaultTableModel();
/*  141 */     modelImages = new DefaultTableModel(data, columnNames)
/*      */       {
/*      */         public Class<?> getColumnClass(int column)
/*      */         {
/*  145 */           if (getRowCount() > 0) {
/*  146 */             Object value = getValueAt(0, column);
/*  147 */             if (value != null) {
/*  148 */               return getValueAt(0, column).getClass();
/*      */             }
/*      */           } 
/*      */           
/*  152 */           return super.getColumnClass(column);
/*      */         }
/*      */       };
/*      */     
/*  156 */     tableImages.setModel(modelImages);
/*  157 */     tableSpot.setModel(modelSpot);
/*  158 */     tableSpot.setSelectionMode(1);
/*  159 */     tableImages.getColumnModel().getColumn(0).setPreferredWidth(90);
/*  160 */     tableImages.getColumnModel().getColumn(1).setPreferredWidth(460);
/*  161 */     tableImages.getColumnModel().getColumn(2).setPreferredWidth(80);
/*  162 */     jScrollPaneImages = new JScrollPane(tableImages);
/*  163 */     jScrollPaneSpot = new JScrollPane(tableSpot);
/*  164 */     jScrollPaneImages.setPreferredSize(new Dimension(590, 240));
/*  165 */     jScrollPaneSpot.setPreferredSize(new Dimension(590, 240));
/*  166 */     JPanel mainPanel = new JPanel();
/*  167 */     mainPanel.setLayout(new BoxLayout(mainPanel, 1));
/*  168 */     jScrollPaneImages.setBorder(BorderFactory.createTitledBorder(""));
/*  169 */     jScrollPaneSpot.setBorder(BorderFactory.createTitledBorder(""));
/*  170 */     JTabbedPane tabbedPaneSpot = new JTabbedPane(1);
/*  171 */     ImageIcon iconSpot = createImageIcon("/images/spot.png");
/*  172 */     iconSpotCell = new ImageIcon(iconSpot.getImage().getScaledInstance(18, 20, 4));
/*  173 */     JButton pngButton = new JButton();
/*  174 */     ImageIcon iconPng = createImageIcon("/images/save.png");
/*  175 */     Icon pngCell = new ImageIcon(iconPng.getImage().getScaledInstance(18, 20, 4));
/*  176 */     pngButton.setIcon(pngCell);
/*  177 */     pngButton.setToolTipText("Click to capture spots overlay.");
/*  178 */     JPanel panelPng = new JPanel(new FlowLayout(0));
/*  179 */     panelPng.add(pngButton);
/*  180 */     JButton csvButton = new JButton();
/*  181 */     ImageIcon iconCsv = createImageIcon("/images/csv.png");
/*  182 */     Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
/*  183 */     csvButton.setIcon(csvCell);
/*  184 */     csvButton.setToolTipText("Click to export your spots table selection.");
/*  185 */     JPanel panelCsv = new JPanel(new FlowLayout(0));
/*  186 */     panelCsv.add(csvButton);
/*  187 */     JPanel panelPngCsv = new JPanel(new FlowLayout(0));
/*  188 */     panelPngCsv.add(panelPng);
/*  189 */     panelPngCsv.add(panelCsv);
/*  190 */     tabbedPaneSpot.addTab("SPOTS ", iconSpotCell, mainPanel, "Display Spot Analysis");
/*  191 */     tabbedPaneSpot.setTabLayoutPolicy(1);
/*  192 */     JButton refreshButton = new JButton();
/*  193 */     ImageIcon iconRefresh = createImageIcon("/images/refresh.png");
/*  194 */     refreshCell = new ImageIcon(iconRefresh.getImage().getScaledInstance(18, 20, 4));
/*  195 */     refreshButton.setIcon(refreshCell);
/*  196 */     refreshButton.setToolTipText("Click this button to get spot analysis");
/*  197 */     JToggleButton paintButton = new JToggleButton();
/*  198 */     ImageIcon iconPaint = createImageIcon("images/paint.png");
/*  199 */     Icon paintCell = new ImageIcon(iconPaint.getImage().getScaledInstance(18, 20, 4));
/*  200 */     paintButton.setIcon(paintCell);
/*  201 */     paintButton.setToolTipText("Click this button to display labeled-spots");
/*  202 */     JToggleButton tInsideButton = new JToggleButton();
/*  203 */     ImageIcon iconTI = createImageIcon("/images/tinside.png");
/*  204 */     Icon TICell = new ImageIcon(iconTI.getImage().getScaledInstance(18, 20, 4));
/*  205 */     tInsideButton.setIcon(TICell);
/*  206 */     tInsideButton.setToolTipText("Click this button to toggle inside spots.");
/*  207 */     JToggleButton tOutsideButton = new JToggleButton();
/*  208 */     ImageIcon iconTO = createImageIcon("/images/toutside.png");
/*  209 */     Icon TOCell = new ImageIcon(iconTO.getImage().getScaledInstance(18, 20, 4));
/*  210 */     tOutsideButton.setIcon(TOCell);
/*  211 */     tOutsideButton.setToolTipText("Click this button to toggle outside spots.");
/*  212 */     JButton enableButton = new JButton();
/*  213 */     ImageIcon iconEnable = createImageIcon("/images/enable.png");
/*  214 */     Icon enableCell = new ImageIcon(iconEnable.getImage().getScaledInstance(18, 20, 4));
/*  215 */     enableButton.setIcon(enableCell);
/*  216 */     enableButton.setToolTipText("Click this button to enable your selection");
/*  217 */     JButton disableButton = new JButton();
/*  218 */     ImageIcon iconDisable = createImageIcon("/images/disable.png");
/*  219 */     Icon disableCell = new ImageIcon(iconDisable.getImage().getScaledInstance(18, 20, 4));
/*  220 */     disableButton.setIcon(disableCell);
/*  221 */     disableButton.setToolTipText("Click this button to disable your selection");
/*  222 */     JPanel buttonPanel = new JPanel(new FlowLayout(2));
/*  223 */     JSeparator separator1 = new JSeparator(1);
/*  224 */     JSeparator separator2 = new JSeparator(1);
/*  225 */     Dimension dime = separator1.getPreferredSize();
/*  226 */     dime.height = (refreshButton.getPreferredSize()).height;
/*  227 */     separator1.setPreferredSize(dime);
/*  228 */     separator2.setPreferredSize(dime);
/*  229 */     this.checkRPicker = new JCheckBox(" Spot Picker");
/*  230 */     JLabel filterLabel = new JLabel("  ➠ Spot Analysis : ");
/*  231 */     filterLabel.setFont(new Font("Dialog", 1, 13));
/*  232 */     filterLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/*  233 */     JPanel filterPanel = new JPanel(new FlowLayout(0));
/*  234 */     filterPanel.add(filterLabel);
/*  235 */     filterPanel.add(this.checkRPicker);
/*  236 */     filterPanel.add(Box.createHorizontalStrut(20));
/*  237 */     JPanel filterMain = new JPanel(new FlowLayout(0));
/*  238 */     filterMain.add(filterPanel);
/*  239 */     buttonPanel.add(refreshButton);
/*  240 */     buttonPanel.add(paintButton);
/*  241 */     buttonPanel.add(separator1);
/*  242 */     buttonPanel.add(enableButton);
/*  243 */     buttonPanel.add(disableButton);
/*  244 */     buttonPanel.add(separator2);
/*  245 */     buttonPanel.add(tInsideButton);
/*  246 */     buttonPanel.add(tOutsideButton);
/*  247 */     filterMain.add(buttonPanel);
/*  248 */     mainPanel.add(jScrollPaneImages);
/*  249 */     mainPanel.add(Box.createVerticalStrut(5));
/*  250 */     mainPanel.add(filterMain);
/*  251 */     mainPanel.add(jScrollPaneSpot);
/*  252 */     JLabel settingsLabel = new JLabel("  ➠ Settings for Filters/Classes : ");
/*  253 */     settingsLabel.setFont(new Font("Dialog", 1, 13));
/*  254 */     settingsLabel.setBorder(BorderFactory.createRaisedBevelBorder());
/*  255 */     JPanel settingsPanel = new JPanel(new FlowLayout(0));
/*  256 */     settingsPanel.add(settingsLabel);
/*  257 */     mainPanel.add(settingsPanel);
/*  258 */     JPanel filtersMin = new JPanel(new FlowLayout(0));
/*  259 */     this.filterMin = new JSpinner(new SpinnerNumberModel(30, 0, 5000, 1));
/*  260 */     this.filterMin.setPreferredSize(new Dimension(60, 20));
/*  261 */     final JSlider sliderMin = new JSlider(0, 300, 50);
/*  262 */     sliderMin.setPreferredSize(new Dimension(150, 15));
/*  263 */     JLabel filterMinLabel = new JLabel("              Min :  ");
/*  264 */     filtersMin.add(filterMinLabel);
/*  265 */     filtersMin.add(sliderMin);
/*  266 */     filtersMin.add(Box.createHorizontalStrut(2));
/*  267 */     filtersMin.add(this.filterMin);
/*  268 */     JPanel filtersMax = new JPanel(new FlowLayout(0));
/*  269 */     this.filterMax = new JSpinner(new SpinnerNumberModel(200, 0, 5000, 1));
/*  270 */     this.filterMax.setPreferredSize(new Dimension(60, 20));
/*  271 */     final JSlider sliderMax = new JSlider(0, 300, 150);
/*  272 */     sliderMax.setPreferredSize(new Dimension(150, 15));
/*  273 */     JLabel filterMaxLabel = new JLabel("              Max :  ");
/*  274 */     filtersMax.add(filterMaxLabel);
/*  275 */     filtersMax.add(sliderMax);
/*  276 */     filtersMax.add(Box.createHorizontalStrut(2));
/*  277 */     filtersMax.add(this.filterMax);
/*  278 */     JPanel boxPanel2 = new JPanel();
/*  279 */     boxPanel2.setLayout(new BoxLayout(boxPanel2, 1));
/*  280 */     final IntervalMarker intervalMarker = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), 
/*  281 */         new Color(0, 102, 0), new BasicStroke(1.5F), 0.5F);
/*  282 */     this.histogram = this.hs2.createChartPanel("", new double[] { 0.0D, 0.0D, 0.0D }, 100, intervalMarker);
/*      */     
/*  284 */     JPanel chartPanel2 = new JPanel(new BorderLayout());
/*  285 */     chartPanel2.setPreferredSize(new Dimension(390, 180));
/*  286 */     chartPanel2.add((Component)this.histogram);
/*  287 */     boxPanel2.add(chartPanel2);
/*  288 */     JPanel controlPanel2 = this.hs2.createControlPanel();
/*  289 */     boxPanel2.add(controlPanel2);
/*  290 */     JPanel filtersMain2 = new JPanel();
/*  291 */     filtersMain2.setLayout(new BoxLayout(filtersMain2, 1));
/*  292 */     filtersMain2.add(boxPanel2);
/*  293 */     filtersMain2.add(filtersMin);
/*  294 */     filtersMain2.add(filtersMax);
/*  295 */     JLabel featureSpot = new JLabel(" » Spot-Features :  ");
/*  296 */     featureSpot.setFont(new Font("Dialog", 1, 13));
/*  297 */     comboFilters = new JComboBox<>();
/*  298 */     for (int k = 0; k < columnNamesSpot.length; k++)
/*  299 */       comboFilters.addItem((String)columnNamesSpot[k]); 
/*  300 */     comboFilters.setPreferredSize(new Dimension(130, 25));
/*  301 */     comboFilters.setSelectedIndex(0);
/*  302 */     comboFilters.setOpaque(true);
/*  303 */     JPanel panelFilters = new JPanel(new FlowLayout(0));
/*  304 */     JSeparator separator3 = new JSeparator(1);
/*  305 */     Dimension dime2 = separator3.getPreferredSize();
/*  306 */     dime2.height = (filtersMain2.getPreferredSize()).height;
/*  307 */     separator3.setPreferredSize(dime2);
/*  308 */     panelFilters.add(filtersMain2);
/*  309 */     panelFilters.add(separator3);
/*  310 */     modelListClass = new DefaultListModel<>();
/*  311 */     classList = new JList<>(modelListClass);
/*  312 */     modelListFeature = new DefaultListModel<>();
/*  313 */     featureList = new JList<>(modelListFeature);
/*  314 */     final ColorEditorSpot colorEditor = new ColorEditorSpot(featureList);
/*  315 */     JScrollPane scrollListFilter = new JScrollPane(featureList);
/*  316 */     JScrollPane scrollListClass = new JScrollPane(classList);
/*  317 */     Dimension d = featureList.getPreferredSize();
/*  318 */     d.width = 150;
/*  319 */     d.height = 90;
/*  320 */     scrollListFilter.setPreferredSize(d);
/*  321 */     scrollListClass.setPreferredSize(d);
/*  322 */     JPanel filterPanelButtons = new JPanel(new FlowLayout(0));
/*  323 */     JPanel classPanelButtons = new JPanel();
/*  324 */     classPanelButtons.setLayout(new BoxLayout(classPanelButtons, 1));
/*  325 */     filterPanelButtons.add(scrollListFilter);
/*  326 */     JPanel fButtonsPanel = new JPanel();
/*  327 */     fButtonsPanel.setLayout(new BoxLayout(fButtonsPanel, 1));
/*  328 */     JButton addButton = new JButton();
/*  329 */     ImageIcon iconAdd = createImageIcon("/images/add.png");
/*  330 */     Icon addCell = new ImageIcon(iconAdd.getImage().getScaledInstance(14, 16, 4));
/*  331 */     addButton.setIcon(addCell);
/*  332 */     addButton.setToolTipText("Click this button to add features");
/*  333 */     JButton remButton = new JButton();
/*  334 */     ImageIcon iconRem = createImageIcon("/images/remove.png");
/*  335 */     Icon remCell = new ImageIcon(iconRem.getImage().getScaledInstance(14, 16, 4));
/*  336 */     remButton.setIcon(remCell);
/*  337 */     remButton.setToolTipText("Click this button to remove features");
/*  338 */     JButton classButton = new JButton();
/*  339 */     ImageIcon iconClass = createImageIcon("/images/classes.png");
/*  340 */     Icon classCell = new ImageIcon(iconClass.getImage().getScaledInstance(14, 16, 4));
/*  341 */     classButton.setIcon(classCell);
/*  342 */     classButton.setToolTipText("Click this button to create a class.");
/*  343 */     JButton remClassButton = new JButton();
/*  344 */     remClassButton.setIcon(remCell);
/*  345 */     remClassButton.setToolTipText("Click this button to remove a class.");
/*  346 */     fButtonsPanel.add(addButton);
/*  347 */     fButtonsPanel.add(remButton);
/*  348 */     filterPanelButtons.add(fButtonsPanel);
/*  349 */     classPanelButtons.add(classButton);
/*  350 */     classPanelButtons.add(remClassButton);
/*  351 */     JPanel classPanel = new JPanel(new FlowLayout(0));
/*  352 */     classPanel.add(scrollListClass);
/*  353 */     classPanel.add(classPanelButtons);
/*  354 */     JPanel boxPanel = new JPanel();
/*  355 */     boxPanel.setLayout(new BoxLayout(boxPanel, 1));
/*  356 */     boxPanel.add(comboFilters);
/*  357 */     boxPanel.add(Box.createHorizontalStrut(5));
/*  358 */     boxPanel.add(filterPanelButtons);
/*  359 */     boxPanel.add(Box.createHorizontalStrut(5));
/*  360 */     boxPanel.add(classPanel);
/*  361 */     boxPanel.add(panelPngCsv);
/*  362 */     panelFilters.add(boxPanel);
/*  363 */     mainPanel.add(panelFilters);
/*  364 */     add(tabbedPaneSpot);
/*  365 */     createMovieTable();
/*      */     
/*  367 */     refreshButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  370 */             FirstWizardPanel.this.refreshThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  372 */                     FirstWizardPanel.spotEnable = "spotEnable";
/*  373 */                     ProcessTrackMateXml.tracksVisible = false;
/*  374 */                     ProcessTrackMateXml.spotsVisible = true;
/*  375 */                     ProcessTrackMateXml ptx = new ProcessTrackMateXml();
/*  376 */                     ptx.processTrackMateXml();
/*      */                   }
/*      */                 });
/*  379 */             FirstWizardPanel.this.refreshThread.start();
/*      */           }
/*      */         });
/*  382 */     csvButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  385 */             FirstWizardPanel.this.csvThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  387 */                     List<String> columnSpotHead = new ArrayList<>();
/*  388 */                     for (int j = 0; j < FirstWizardPanel.modelSpot.getColumnCount(); j++) {
/*  389 */                       columnSpotHead.add(FirstWizardPanel.modelSpot.getColumnName(j));
/*      */                     }
/*  391 */                     ResultsTable rt = new ResultsTable(Integer.valueOf(FirstWizardPanel.modelSpot.getRowCount()));
/*  392 */                     if (rt != null) {
/*  393 */                       rt.reset();
/*      */                     }
/*  395 */                     for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/*  396 */                       for (int k = 0; k < FirstWizardPanel.modelSpot.getColumnCount(); k++) {
/*  397 */                         if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE)
/*      */                         {
/*  399 */                           if (((String)columnSpotHead.get(k)).equals(columnSpotHead.get(0)) == Boolean.TRUE.booleanValue()) {
/*      */                             
/*  401 */                             rt.setValue(columnSpotHead.get(k), i, (
/*  402 */                                 (JLabel)FirstWizardPanel.modelSpot.getValueAt(i, k)).getText());
/*      */                           } else {
/*      */                             
/*  405 */                             rt.setValue(columnSpotHead.get(k), i, FirstWizardPanel.modelSpot.getValueAt(i, k).toString());
/*      */                           }  } 
/*      */                       } 
/*  408 */                     }  JFrame pngFrame = new JFrame();
/*  409 */                     JFileChooser fileChooser = new JFileChooser();
/*  410 */                     fileChooser.setFileSelectionMode(1);
/*  411 */                     fileChooser.setDialogTitle("Specify a directory to save csv file");
/*  412 */                     int userSelection = fileChooser.showSaveDialog(pngFrame);
/*      */                     
/*  414 */                     if (userSelection == 0) {
/*  415 */                       File fileToSave = fileChooser.getSelectedFile();
/*      */                       try {
/*  417 */                         rt.saveAs(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + "SpotStatistics for-" + 
/*  418 */                             IJ.getImage().getShortTitle() + ".csv");
/*  419 */                       } catch (IOException e1) {
/*      */                         
/*  421 */                         e1.printStackTrace();
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */                 });
/*  426 */             FirstWizardPanel.this.csvThread.start();
/*      */           }
/*      */         });
/*  429 */     pngButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  432 */             FirstWizardPanel.this.pngThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  434 */                     if (IJ.getImage() == null)
/*  435 */                       IJ.error("You must have an image window active."); 
/*  436 */                     if (IJ.getImage() != null) {
/*  437 */                       JFrame pngFrame = new JFrame();
/*  438 */                       JFileChooser fileChooser = new JFileChooser();
/*  439 */                       fileChooser.setFileSelectionMode(1);
/*  440 */                       fileChooser.setDialogTitle("Specify a directory to save");
/*  441 */                       int userSelection = fileChooser.showSaveDialog(pngFrame);
/*      */                       
/*  443 */                       if (userSelection == 0) {
/*  444 */                         File fileToSave = fileChooser.getSelectedFile();
/*      */                         
/*  446 */                         int firstFrame = 0, lastFrame = 0;
/*  447 */                         if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/*  448 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/*  449 */                           lastFrame = Math.min(IJ.getImage().getNFrames(), 
/*  450 */                               Math.max(IJ.getImage().getNFrames(), 1));
/*      */                         } 
/*  452 */                         if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/*  453 */                           firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/*  454 */                           lastFrame = Math.min(IJ.getImage().getNSlices(), 
/*  455 */                               Math.max(IJ.getImage().getNSlices(), 1));
/*      */                         } 
/*      */                         
/*  458 */                         Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/*  459 */                         int width = bounds.width;
/*  460 */                         int height = bounds.height;
/*  461 */                         int nCaptures = lastFrame - firstFrame + 1;
/*  462 */                         ImageStack stack = new ImageStack(width, height);
/*  463 */                         int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/*  464 */                         int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/*  465 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/*  466 */                         for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*      */                           
/*  468 */                           ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/*  469 */                               frame);
/*  470 */                           BufferedImage bi = new BufferedImage(width, height, 2);
/*  471 */                           ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/*  472 */                           ColorProcessor cp = new ColorProcessor(bi);
/*  473 */                           int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/*  474 */                               frame);
/*  475 */                           stack.addSlice(
/*  476 */                               ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/*  477 */                               (ImageProcessor)cp);
/*      */                         } 
/*  479 */                         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/*  480 */                         ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/*  481 */                             ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/*  482 */                         FirstWizardPanel.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
/*  483 */                         IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/*  484 */                             "Capture Overlay for " + IJ.getImage().getShortTitle());
/*      */                       } 
/*      */                     } 
/*      */                   }
/*      */                 });
/*  489 */             FirstWizardPanel.this.pngThread.start();
/*      */           }
/*      */         });
/*      */     
/*  493 */     paintButton.addItemListener(new ItemListener() {
/*      */           public void itemStateChanged(final ItemEvent ev) {
/*  495 */             FirstWizardPanel.this.paintThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  497 */                     if (ev.getStateChange() == 1) {
/*  498 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).paintAndDisableAction();
/*  499 */                     } else if (ev.getStateChange() == 2) {
/*  500 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).resetAndEnableAction();
/*      */                     } 
/*      */                   }
/*      */                 });
/*  504 */             FirstWizardPanel.this.paintThread.start();
/*      */           }
/*      */         });
/*  507 */     tInsideButton.addItemListener(new ItemListener() {
/*      */           public void itemStateChanged(final ItemEvent ev) {
/*  509 */             FirstWizardPanel.this.tInsideThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  511 */                     if (ev.getStateChange() == 1) {
/*  512 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).toggleInsideAction();
/*  513 */                     } else if (ev.getStateChange() == 2) {
/*  514 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).resetToggleInsideAction();
/*      */                     } 
/*      */                   }
/*      */                 });
/*  518 */             FirstWizardPanel.this.tInsideThread.start();
/*      */           }
/*      */         });
/*  521 */     tOutsideButton.addItemListener(new ItemListener() {
/*      */           public void itemStateChanged(final ItemEvent ev) {
/*  523 */             FirstWizardPanel.this.tOutsideThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  525 */                     if (ev.getStateChange() == 1) {
/*  526 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).toggleOutsideAction();
/*  527 */                     } else if (ev.getStateChange() == 2) {
/*  528 */                       FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).resetToggleOutsideAction();
/*      */                     } 
/*      */                   }
/*      */                 });
/*  532 */             FirstWizardPanel.this.tOutsideThread.start();
/*      */           }
/*      */         });
/*  535 */     enableButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  538 */             FirstWizardPanel.this.enableThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  540 */                     FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).enableSpots();
/*      */                   }
/*      */                 });
/*  543 */             FirstWizardPanel.this.enableThread.start();
/*      */           }
/*      */         });
/*  546 */     disableButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  549 */             FirstWizardPanel.this.disableThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  551 */                     FirstWizardPanel.null.access$0(FirstWizardPanel.null.this).disableSpots();
/*      */                   }
/*      */                 });
/*  554 */             FirstWizardPanel.this.disableThread.start();
/*      */           }
/*      */         });
/*      */     
/*  558 */     sliderMin.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  561 */             FirstWizardPanel.this.slMinThread = new Thread(new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  564 */                     (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.setValue(Integer.valueOf(sliderMin.getValue()));
/*  565 */                     intervalMarker.setStartValue(sliderMin.getValue());
/*      */                   }
/*      */                 });
/*  568 */             FirstWizardPanel.this.slMinThread.start();
/*      */           }
/*      */         });
/*      */     
/*  572 */     this.filterMin.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  575 */             FirstWizardPanel.this.filterMinThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  577 */                     sliderMin.setValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue()).intValue());
/*  578 */                     intervalMarker.setStartValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue()).intValue());
/*      */                   }
/*      */                 });
/*  581 */             FirstWizardPanel.this.filterMinThread.start();
/*      */           }
/*      */         });
/*      */     
/*  585 */     sliderMax.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  588 */             FirstWizardPanel.this.slMaxThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  590 */                     (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.setValue(Integer.valueOf(sliderMax.getValue()));
/*  591 */                     intervalMarker.setEndValue(sliderMax.getValue());
/*      */                   }
/*      */                 });
/*  594 */             FirstWizardPanel.this.slMaxThread.start();
/*      */           }
/*      */         });
/*      */     
/*  598 */     this.filterMax.addChangeListener(new ChangeListener()
/*      */         {
/*      */           public void stateChanged(ChangeEvent e) {
/*  601 */             FirstWizardPanel.this.filterMaxThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  603 */                     sliderMax.setValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue()).intValue());
/*  604 */                     intervalMarker.setEndValue(((Integer)(FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue()).intValue());
/*      */                   }
/*      */                 });
/*  607 */             FirstWizardPanel.this.filterMaxThread.start();
/*      */           }
/*      */         });
/*  610 */     comboFilters.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent e) {
/*  612 */             FirstWizardPanel.this.filtersThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  614 */                     String selectedName = (String)FirstWizardPanel.comboFilters.getSelectedItem();
/*  615 */                     int selectedIndex = FirstWizardPanel.comboFilters.getSelectedIndex();
/*  616 */                     double[] values = null;
/*      */ 
/*      */                     
/*  619 */                     values = new double[FirstWizardPanel.tableSpot.getRowCount()];
/*  620 */                     for (int r = 0; r < FirstWizardPanel.tableSpot.getRowCount(); r++) {
/*  621 */                       for (int c = 0; c < FirstWizardPanel.tableSpot.getColumnCount(); c++)
/*  622 */                         values[r] = Double.parseDouble((String)FirstWizardPanel.tableSpot.getValueAt(r, selectedIndex + 2)); 
/*      */                     } 
/*  624 */                     double max = values[0];
/*  625 */                     for (int i = 1; i < values.length; i++) {
/*  626 */                       if (values[i] > max)
/*  627 */                         max = values[i]; 
/*      */                     } 
/*  629 */                     sliderMin.setMinimum(0);
/*  630 */                     sliderMin.setMaximum((int)max);
/*  631 */                     sliderMax.setMinimum(0);
/*  632 */                     sliderMax.setMaximum((int)max);
/*      */                     
/*  634 */                     (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).hs2.addHistogramSeries(selectedName, values, (int)max, intervalMarker);
/*      */                   }
/*      */                 });
/*  637 */             FirstWizardPanel.this.filtersThread.start();
/*      */           }
/*      */         });
/*      */     
/*  641 */     this.checkRPicker.addItemListener(new ItemListener()
/*      */         {
/*      */           public void itemStateChanged(final ItemEvent e)
/*      */           {
/*  645 */             FirstWizardPanel.this.pickerThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  647 */                     if (e.getStateChange() == 1)
/*  648 */                       FirstWizardPanel.command = "enable"; 
/*  649 */                     if (e.getStateChange() == 2) {
/*  650 */                       FirstWizardPanel.command = null;
/*  651 */                       ProcessTrackMateXml.selectionModel.clearSpotSelection();
/*  652 */                       ProcessTrackMateXml.selectionModel.clearSelection();
/*      */                       return;
/*      */                     } 
/*      */                   }
/*      */                 });
/*  657 */             FirstWizardPanel.this.pickerThread.start();
/*      */           }
/*      */         });
/*      */     
/*  661 */     classButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  664 */             FirstWizardPanel.this.classThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  666 */                     ColorEditorSpot.myFrame.setVisible(true);
/*  667 */                     colorEditor.setClassAction();
/*      */                   }
/*      */                 });
/*  670 */             FirstWizardPanel.this.classThread.start();
/*      */           }
/*      */         });
/*      */     
/*  674 */     remClassButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  677 */             FirstWizardPanel.this.remClassThread = new Thread(new Runnable() {
/*      */                   public void run() {
/*  679 */                     String classSelectedValue = FirstWizardPanel.classList.getSelectedValue();
/*  680 */                     int[] classSelectedIndex = FirstWizardPanel.classList.getSelectedIndices(); int i;
/*  681 */                     for (i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); i++) {
/*  682 */                       if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getText()
/*  683 */                         .equals(classSelectedValue))
/*  684 */                         FirstWizardPanel.modelSpot.setValueAt(FirstWizardPanel.labelReset, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1)); 
/*      */                     } 
/*  686 */                     for (i = 0; i < classSelectedIndex.length; i++)
/*  687 */                       FirstWizardPanel.modelListClass.removeElementAt(classSelectedIndex[i]); 
/*      */                   }
/*      */                 });
/*  690 */             FirstWizardPanel.this.remClassThread.start();
/*      */           }
/*      */         });
/*      */     
/*  694 */     addButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  697 */             FirstWizardPanel.this.addThread = new Thread(new Runnable()
/*      */                 {
/*      */                   public void run() {
/*  700 */                     List<String> listFilters = new ArrayList<>();
/*      */                     
/*  702 */                     if (FirstWizardPanel.featureList.getModel().getSize() < 1) {
/*  703 */                       FirstWizardPanel.modelListFeature.addElement(String.valueOf(FirstWizardPanel.comboFilters.getSelectedItem()) + ":  [" + 
/*  704 */                           (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue() + "," + (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue() + "]");
/*      */                     }
/*  706 */                     if (FirstWizardPanel.featureList.getModel().getSize() >= 1) {
/*  707 */                       for (int i = 0; i < FirstWizardPanel.featureList.getModel().getSize(); i++) {
/*  708 */                         listFilters.add(String.valueOf(((String)FirstWizardPanel.featureList.getModel().getElementAt(i)).substring(0, (
/*  709 */                                 (String)FirstWizardPanel.featureList.getModel().getElementAt(i)).lastIndexOf(":"))));
/*      */                       }
/*  711 */                       if (!listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
/*  712 */                         FirstWizardPanel.modelListFeature.addElement(String.valueOf(FirstWizardPanel.comboFilters.getSelectedItem()) + ":  [" + 
/*  713 */                             (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMin.getValue() + "," + (FirstWizardPanel.null.access$0(FirstWizardPanel.null.this)).filterMax.getValue() + "]");
/*      */                       }
/*  715 */                       if (listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
/*      */                         return;
/*      */                       }
/*      */                     } 
/*      */                   }
/*      */                 });
/*  721 */             FirstWizardPanel.this.addThread.start();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  726 */     remButton.addActionListener(new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  729 */             FirstWizardPanel.this.remThread = new Thread(new Runnable()
/*      */                 {
/*      */                   public void run() {
/*      */                     try {
/*  733 */                       int[] indexes = FirstWizardPanel.featureList.getSelectedIndices();
/*  734 */                       for (int i = 0; i < indexes.length; i++)
/*  735 */                         FirstWizardPanel.modelListFeature.remove(indexes[i]); 
/*  736 */                     } catch (Exception e1) {
/*  737 */                       e1.printStackTrace();
/*      */                     } 
/*      */                   }
/*      */                 });
/*      */             
/*  742 */             FirstWizardPanel.this.remThread.start();
/*      */           }
/*      */         });
/*      */   }
/*      */   static Object[] columnNamesSpot; Thread refreshThread; Thread csvThread; Thread pngThread; Thread paintThread; Thread tInsideThread; Thread tOutsideThread; Thread enableThread; Thread disableThread; Thread slMinThread; Thread filterMinThread; Thread slMaxThread; Thread filterMaxThread; Thread filtersThread; Thread pickerThread; Thread classThread; Thread remClassThread; Thread addThread; Thread remThread;
/*      */   
/*      */   public void toggleOutsideAction() {
/*  749 */     Roi mainRoi = null;
/*  750 */     if (IJ.getImage().getRoi().getType() == 0)
/*  751 */       mainRoi = IJ.getImage().getRoi(); 
/*  752 */     this.indexesTO = new ArrayList<>();
/*  753 */     for (int i = 0; i < modelSpot.getRowCount(); i++) {
/*  754 */       if (mainRoi
/*  755 */         .contains(
/*      */           
/*  757 */           (int)IJ.getImage().getCalibration().getRawX(
/*  758 */             Double.parseDouble(
/*  759 */               modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(5))
/*  760 */               .toString())), 
/*  761 */           (int)IJ.getImage().getCalibration().getRawY(
/*  762 */             Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(6))
/*  763 */               .toString()))) == Boolean.FALSE.booleanValue()) {
/*  764 */         this.indexesTO.add(Integer.valueOf(i));
/*  765 */         modelSpot.setValueAt(Boolean.valueOf(false), i, tableSpot.convertColumnIndexToModel(0));
/*  766 */         int spotID = Integer.parseInt((String)tableSpot.getValueAt(i, 2));
/*  767 */         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*      */         
/*  769 */         if (spot != null) {
/*  770 */           spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  771 */           ProcessTrackMateXml.model.endUpdate();
/*  772 */           ProcessTrackMateXml.displayer.refresh();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetToggleOutsideAction() {
/*  781 */     for (int row = 0; row < modelSpot.getRowCount(); row++) {
/*  782 */       modelSpot.setValueAt(Boolean.valueOf(true), tableSpot.convertRowIndexToModel(row), tableSpot.convertColumnIndexToModel(0));
/*  783 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  784 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  785 */       if (spot != null) {
/*  786 */         spot.putFeature("VISIBILITY", SpotCollection.ONE);
/*  787 */         ProcessTrackMateXml.model.endUpdate();
/*  788 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleInsideAction() {
/*  797 */     Roi mainRoi = null;
/*  798 */     if (IJ.getImage().getRoi().getType() == 0)
/*  799 */       mainRoi = IJ.getImage().getRoi(); 
/*  800 */     this.indexesTI = new ArrayList<>();
/*  801 */     for (int i = 0; i < modelSpot.getRowCount(); i++) {
/*  802 */       if (mainRoi
/*  803 */         .contains(
/*      */           
/*  805 */           (int)IJ.getImage().getCalibration().getRawX(
/*  806 */             Double.parseDouble(
/*  807 */               modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(5))
/*  808 */               .toString())), 
/*  809 */           (int)IJ.getImage().getCalibration().getRawY(
/*  810 */             Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(6))
/*  811 */               .toString()))) == Boolean.TRUE.booleanValue()) {
/*  812 */         this.indexesTI.add(Integer.valueOf(i));
/*  813 */         modelSpot.setValueAt(Boolean.valueOf(false), i, tableSpot.convertColumnIndexToModel(0));
/*  814 */         int spotID = Integer.parseInt((String)tableSpot.getValueAt(i, 2));
/*  815 */         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*      */         
/*  817 */         if (spot != null) {
/*  818 */           spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  819 */           ProcessTrackMateXml.model.endUpdate();
/*  820 */           ProcessTrackMateXml.displayer.refresh();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetToggleInsideAction() {
/*  830 */     for (int row = 0; row < modelSpot.getRowCount(); row++) {
/*  831 */       modelSpot.setValueAt(Boolean.valueOf(true), tableSpot.convertRowIndexToModel(row), tableSpot.convertColumnIndexToModel(0));
/*  832 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  833 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  834 */       if (spot != null) {
/*  835 */         spot.putFeature("VISIBILITY", SpotCollection.ONE);
/*  836 */         ProcessTrackMateXml.model.endUpdate();
/*  837 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void paintAndDisableAction() {
/*  845 */     this.indexesToReset = new ArrayList<>();
/*  846 */     this.spotID = new ArrayList<>();
/*  847 */     this.spots = new ArrayList<>();
/*  848 */     for (int i = 0; i < modelSpot.getRowCount(); i++) {
/*  849 */       if (((JLabel)modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(1))).getBackground()
/*  850 */         .equals(new Color(214, 217, 223)) == Boolean.TRUE.booleanValue()) {
/*  851 */         this.indexesToReset.add(Integer.valueOf(i));
/*  852 */         modelSpot.setValueAt(Boolean.valueOf(false), i, tableSpot.convertColumnIndexToModel(0));
/*  853 */         this.spotID.add(Integer.valueOf(Integer.parseInt((String)tableSpot.getValueAt(i, 2))));
/*      */       } 
/*  855 */     }  for (int row = 0; row < this.indexesToReset.size(); row++) {
/*  856 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(((Integer)this.indexesToReset.get(row)).intValue(), 2));
/*  857 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  858 */       if (spot != null) {
/*  859 */         spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  860 */         ProcessTrackMateXml.model.endUpdate();
/*  861 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetAndEnableAction() {
/*  869 */     for (int i = 0; i < this.indexesToReset.size(); i++)
/*  870 */       modelSpot.setValueAt(Boolean.valueOf(true), tableSpot.convertRowIndexToModel(((Integer)this.indexesToReset.get(i)).intValue()), 
/*  871 */           tableSpot.convertColumnIndexToModel(0)); 
/*  872 */     for (int row = 0; row < this.indexesToReset.size(); row++) {
/*  873 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(((Integer)this.indexesToReset.get(row)).intValue(), 2));
/*  874 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  875 */       if (spot != null) {
/*  876 */         spot.putFeature("VISIBILITY", SpotCollection.ONE);
/*  877 */         ProcessTrackMateXml.model.endUpdate();
/*  878 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enableSpots() {
/*  887 */     ListSelectionModel lsm = tableSpot.getSelectionModel();
/*  888 */     int[] selectedIndices = tableSpot.getSelectedRows();
/*  889 */     for (int i = 0; i < selectedIndices.length; i++)
/*  890 */       tableSpot.setValueAt(Boolean.valueOf(true), selectedIndices[i], 0); 
/*  891 */     int selStart = lsm.getMinSelectionIndex();
/*  892 */     int selEnd = lsm.getMaxSelectionIndex();
/*  893 */     if (selStart < 0 || selEnd < 0) {
/*      */       return;
/*      */     }
/*  896 */     int minLine = Math.min(selStart, selEnd);
/*  897 */     int maxLine = Math.max(selStart, selEnd);
/*  898 */     for (int row = minLine; row <= maxLine; row++) {
/*  899 */       int spotIDEnable = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  900 */       Spot spotEnable = ProcessTrackMateXml.model.getSpots().search(spotIDEnable);
/*  901 */       if (spotEnable != null) {
/*  902 */         spotEnable.putFeature("VISIBILITY", SpotCollection.ONE);
/*  903 */         ProcessTrackMateXml.model.endUpdate();
/*  904 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableSpots() {
/*  912 */     ListSelectionModel lsm = tableSpot.getSelectionModel();
/*  913 */     int selStart = lsm.getMinSelectionIndex();
/*  914 */     int selEnd = lsm.getMaxSelectionIndex();
/*  915 */     if (selStart < 0 || selEnd < 0) {
/*      */       return;
/*      */     }
/*  918 */     int minLine = Math.min(selStart, selEnd);
/*  919 */     int maxLine = Math.max(selStart, selEnd);
/*  920 */     for (int row = minLine; row <= maxLine; row++) {
/*  921 */       int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
/*  922 */       Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
/*  923 */       if (spot != null) {
/*  924 */         spot.putFeature("VISIBILITY", SpotCollection.ZERO);
/*  925 */         ProcessTrackMateXml.model.endUpdate();
/*  926 */         ProcessTrackMateXml.displayer.refresh();
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  931 */     int[] selectedIndices = tableSpot.getSelectedRows();
/*  932 */     for (int i = 0; i < selectedIndices.length; i++) {
/*  933 */       tableSpot.setValueAt(Boolean.valueOf(false), selectedIndices[i], 0);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void createSpotTable() {
/*  941 */     modelSpot = new DefaultTableModel((Object[][])ProcessTrackMateXml.dataSpot, (Object[])ProcessTrackMateXml.columnHeadersSpot)
/*      */       {
/*      */         public Class<?> getColumnClass(int column)
/*      */         {
/*  945 */           if (getRowCount() > 0) {
/*  946 */             Object value = getValueAt(0, column);
/*  947 */             if (value != null) {
/*  948 */               return getValueAt(0, column).getClass();
/*      */             }
/*      */           } 
/*      */           
/*  952 */           return super.getColumnClass(column);
/*      */         }
/*      */       };
/*      */     
/*  956 */     modelSpot.addColumn("Enable");
/*  957 */     tableSpot.setModel(modelSpot);
/*  958 */     tableSpot.moveColumn(tableSpot.getColumnCount() - 1, 0);
/*  959 */     tableSpot.setSelectionBackground(new Color(229, 255, 204));
/*  960 */     tableSpot.setSelectionForeground(new Color(0, 102, 0));
/*  961 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/*  962 */     centerRenderer.setHorizontalAlignment(0);
/*  963 */     tableSpot.setDefaultRenderer(String.class, centerRenderer);
/*  964 */     tableSpot.setAutoResizeMode(0);
/*  965 */     tableSpot.setRowHeight(45);
/*  966 */     tableSpot.setAutoCreateRowSorter(true);
/*  967 */     tableSpot.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer()); int u;
/*  968 */     for (u = 0; u < tableSpot.getColumnCount(); u++)
/*  969 */       tableSpot.getColumnModel().getColumn(u).setPreferredWidth(90); 
/*  970 */     for (u = 16; u < tableSpot.getColumnCount(); u++)
/*  971 */       tableSpot.getColumnModel().getColumn(u).setPreferredWidth(150); 
/*      */     int i;
/*  973 */     for (i = 0; i < tableSpot.getRowCount(); i++)
/*  974 */       tableSpot.setValueAt(Boolean.valueOf(true), i, 0); 
/*  975 */     tableSpot.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
/*  976 */     labelReset = new JLabel();
/*  977 */     labelReset.setText("");
/*  978 */     labelReset.setOpaque(true);
/*  979 */     labelReset.setBackground(new Color(214, 217, 223));
/*  980 */     for (i = 0; i < modelSpot.getRowCount(); i++) {
/*  981 */       modelSpot.setValueAt(labelReset, i, tableSpot.convertColumnIndexToModel(1));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void createMovieTable() {
/*  987 */     tableImages.setSelectionBackground(new Color(229, 255, 204));
/*  988 */     tableImages.setSelectionForeground(new Color(0, 102, 0));
/*  989 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/*  990 */     centerRenderer.setHorizontalAlignment(0);
/*  991 */     tableImages.setDefaultRenderer(String.class, centerRenderer);
/*  992 */     tableImages.setAutoResizeMode(0);
/*  993 */     tableImages.setRowHeight(95);
/*  994 */     tableImages.setAutoCreateRowSorter(true);
/*  995 */     tableImages.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
/*      */     
/*  997 */     for (int i = 0; i < modelImages.getRowCount(); i++) {
/*  998 */       modelImages.setValueAt(this.icons[i], i, tableImages.convertColumnIndexToModel(0));
/*  999 */       modelImages.setValueAt(imps[i].getShortTitle(), i, tableImages.convertColumnIndexToModel(1));
/* 1000 */       modelImages.setValueAt(imps[i].getTitle().substring(imps[i].getTitle().lastIndexOf(".")), i, 
/* 1001 */           tableImages.convertColumnIndexToModel(2));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Image getScaledImage(Image srcImg, int w, int h) {
/* 1007 */     BufferedImage resizedImg = new BufferedImage(w, h, 1);
/* 1008 */     Graphics2D g2 = resizedImg.createGraphics();
/* 1009 */     g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 1010 */     g2.drawImage(srcImg, 0, 0, w, h, null);
/* 1011 */     g2.dispose();
/* 1012 */     return resizedImg;
/*      */   }
/*      */   
/*      */   public ImageIcon createImageIcon(String path) {
/* 1016 */     URL img = getClass().getResource(path);
/*      */     
/* 1018 */     if (img != null) {
/* 1019 */       return new ImageIcon(img);
/*      */     }
/* 1021 */     System.err.println("Couldn't find file: " + path);
/* 1022 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public ImagePlus extractTFrame(ImagePlus imp, int frame) {
/* 1027 */     int width = imp.getWidth();
/* 1028 */     int height = imp.getHeight();
/* 1029 */     int channels = imp.getNChannels();
/* 1030 */     int zslices = imp.getNSlices();
/* 1031 */     FileInfo fileInfo = imp.getOriginalFileInfo();
/* 1032 */     ImageStack stack2 = new ImageStack(width, height);
/* 1033 */     ImagePlus imp2 = new ImagePlus();
/* 1034 */     imp2.setTitle("T" + frame + "-" + imp.getTitle());
/*      */     
/* 1036 */     for (int z = 1; z <= zslices; z++) {
/* 1037 */       for (int c = 1; c <= channels; c++) {
/* 1038 */         int sliceSix = imp.getStackIndex(c, z, frame);
/* 1039 */         stack2.addSlice("", imp.getStack().getProcessor(sliceSix));
/*      */       } 
/* 1041 */     }  imp2.setStack(stack2);
/* 1042 */     imp2.setDimensions(channels, zslices, 1);
/* 1043 */     if (channels * zslices > 1)
/* 1044 */       imp2.setOpenAsHyperStack(true); 
/* 1045 */     imp2.setFileInfo(fileInfo);
/* 1046 */     return imp2;
/*      */   }
/*      */   
/*      */   private static final void transferCalibration(ImagePlus from, ImagePlus to) {
/* 1050 */     Calibration fc = from.getCalibration();
/* 1051 */     Calibration tc = to.getCalibration();
/*      */     
/* 1053 */     tc.setUnit(fc.getUnit());
/* 1054 */     tc.setTimeUnit(fc.getTimeUnit());
/* 1055 */     tc.frameInterval = fc.frameInterval;
/*      */     
/* 1057 */     double mag = from.getCanvas().getMagnification();
/* 1058 */     fc.pixelWidth /= mag;
/* 1059 */     fc.pixelHeight /= mag;
/* 1060 */     tc.pixelDepth = fc.pixelDepth;
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/FirstWizardPanel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */