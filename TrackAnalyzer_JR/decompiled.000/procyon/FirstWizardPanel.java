import ij.measure.Calibration;
import ij.io.FileInfo;
import java.net.URL;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.RenderingHints;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.ListSelectionModel;
import ij.gui.Roi;
import fiji.plugin.trackmate.SpotCollection;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Rectangle;
import ij.process.ImageProcessor;
import java.awt.Image;
import ij.process.ColorProcessor;
import java.awt.image.BufferedImage;
import ij.ImageStack;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import ij.measure.ResultsTable;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ListModel;
import java.awt.BorderLayout;
import java.awt.Stroke;
import java.awt.Paint;
import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Box;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.table.TableModel;
import ij.IJ;
import java.io.File;
import jwizardcomponent.JWizardComponents;
import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JCheckBox;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.ChartPanel;
import javax.swing.JSpinner;
import fiji.plugin.trackmate.Spot;
import java.util.List;
import javax.swing.ImageIcon;
import ij.ImagePlus;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

// 
// Decompiled by Procyon v0.5.36
// 

public class FirstWizardPanel extends LabelWizardPanel
{
    private static final long serialVersionUID = 1L;
    static JTable tableImages;
    static JTable tableSpot;
    static DefaultTableModel modelImages;
    static DefaultTableModel modelSpot;
    static ImagePlus[] imps;
    static ImagePlus[] impsPZ;
    ImageIcon[] icons;
    Thread mainProcess;
    ImagePlus impAnal;
    static String command;
    static String spotEnable;
    List<Spot> removedSpots;
    List<Spot> spots;
    JSpinner filterMin;
    JSpinner filterMax;
    ChartPanel histogram;
    HistogramFilterVersion hs2;
    IntervalMarker intervalMarker;
    JCheckBox checkRPicker;
    static JList<String> classList;
    static JList<String> featureList;
    static DefaultListModel<String> modelListClass;
    static DefaultListModel<String> modelListFeature;
    static JComboBox<String> comboFilters;
    static JLabel labelReset;
    List<Integer> indexesToReset;
    List<Integer> spotID;
    List<Integer> spotIDTI;
    List<Integer> spotIDTO;
    List<Integer> indexesTI;
    List<Integer> indexesTO;
    static JScrollPane jScrollPaneImages;
    static JScrollPane jScrollPaneSpot;
    static Icon iconSpotCell;
    static Icon refreshCell;
    static Object[] columnNamesSpot;
    Thread refreshThread;
    Thread csvThread;
    Thread pngThread;
    Thread paintThread;
    Thread tInsideThread;
    Thread tOutsideThread;
    Thread enableThread;
    Thread disableThread;
    Thread slMinThread;
    Thread filterMinThread;
    Thread slMaxThread;
    Thread filterMaxThread;
    Thread filtersThread;
    Thread pickerThread;
    Thread classThread;
    Thread remClassThread;
    Thread addThread;
    Thread remThread;
    
    static {
        FirstWizardPanel.command = "";
        FirstWizardPanel.spotEnable = "";
    }
    
    public FirstWizardPanel(final JWizardComponents wizardComponents) {
        super(wizardComponents, "");
        this.hs2 = new HistogramFilterVersion();
        final File imageFolder = new File(TrackAnalyzer_.textImages.getText());
        final File[] listOfFiles = imageFolder.listFiles();
        final String[] imageTitles = new String[listOfFiles.length];
        final File[] filesXML = new File[listOfFiles.length];
        for (int u = 0; u < filesXML.length; ++u) {
            filesXML[u] = new File(TrackAnalyzer_.textXml.getText());
        }
        FirstWizardPanel.impsPZ = new ImagePlus[imageTitles.length];
        FirstWizardPanel.imps = new ImagePlus[imageTitles.length];
        this.icons = new ImageIcon[FirstWizardPanel.imps.length];
        for (int i = 0; i < listOfFiles.length; ++i) {
            if (listOfFiles[i].isFile()) {
                imageTitles[i] = listOfFiles[i].getName();
            }
            FirstWizardPanel.imps[i] = IJ.openImage(String.valueOf(TrackAnalyzer_.textImages.getText()) + "/" + imageTitles[i]);
            FirstWizardPanel.impsPZ[i] = this.extractTFrame(FirstWizardPanel.imps[i], 1);
            this.icons[i] = new ImageIcon(getScaledImage(FirstWizardPanel.impsPZ[i].getImage(), 90, 95));
        }
        FirstWizardPanel.tableImages = new JTable();
        FirstWizardPanel.tableSpot = new JTable();
        FirstWizardPanel.modelImages = new DefaultTableModel();
        FirstWizardPanel.modelSpot = new DefaultTableModel();
        final Object[] columnNames = { "Movie", "Title", "Extension" };
        FirstWizardPanel.columnNamesSpot = new Object[] { "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MEAN_INTENSITY", "MEDIAN_INTENSITY", "MIN_INTENSITY", "MAX_INTENSITY", "TOTAL_INTENSITY", "STANDARD_DEVIATION", "CONTRAST", "SNR", "ESTIMATED_DIAMETER", "MORPHOLOGY", "ELLIPSOIDFIT_SEMIAXISLENGTH_C", "ELLIPSOIDFIT_SEMIAXISLENGTH_B", "ELLIPSOIDFIT_SEMIAXISLENGTH_A", "ELLIPSOIDFIT_AXISPHI_C", "ELLIPSOIDFIT_AXISPHI_B", "ELLIPSOIDFIT_AXISPHI_A", "ELLIPSOIDFIT_AXISTHETA_C", "ELLIPSOIDFIT_AXISTHETA_B", "ELLIPSOIDFIT_AXISTHETA_A", "MANUAL_COLOR" };
        final Object[][] data = new Object[FirstWizardPanel.imps.length][columnNames.length];
        for (int j = 0; j < data.length; ++j) {
            for (int k = 0; k < data[j].length; ++k) {
                data[j][k] = "";
            }
        }
        FirstWizardPanel.modelSpot = new DefaultTableModel();
        FirstWizardPanel.modelImages = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(final int column) {
                if (this.getRowCount() > 0) {
                    final Object value = this.getValueAt(0, column);
                    if (value != null) {
                        return this.getValueAt(0, column).getClass();
                    }
                }
                return super.getColumnClass(column);
            }
        };
        FirstWizardPanel.tableImages.setModel(FirstWizardPanel.modelImages);
        FirstWizardPanel.tableSpot.setModel(FirstWizardPanel.modelSpot);
        FirstWizardPanel.tableSpot.setSelectionMode(1);
        FirstWizardPanel.tableImages.getColumnModel().getColumn(0).setPreferredWidth(90);
        FirstWizardPanel.tableImages.getColumnModel().getColumn(1).setPreferredWidth(460);
        FirstWizardPanel.tableImages.getColumnModel().getColumn(2).setPreferredWidth(80);
        FirstWizardPanel.jScrollPaneImages = new JScrollPane(FirstWizardPanel.tableImages);
        FirstWizardPanel.jScrollPaneSpot = new JScrollPane(FirstWizardPanel.tableSpot);
        FirstWizardPanel.jScrollPaneImages.setPreferredSize(new Dimension(590, 240));
        FirstWizardPanel.jScrollPaneSpot.setPreferredSize(new Dimension(590, 240));
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, 1));
        FirstWizardPanel.jScrollPaneImages.setBorder(BorderFactory.createTitledBorder(""));
        FirstWizardPanel.jScrollPaneSpot.setBorder(BorderFactory.createTitledBorder(""));
        final JTabbedPane tabbedPaneSpot = new JTabbedPane(1);
        final ImageIcon iconSpot = createImageIcon("images/spot.png");
        FirstWizardPanel.iconSpotCell = new ImageIcon(iconSpot.getImage().getScaledInstance(18, 20, 4));
        final JButton pngButton = new JButton();
        final ImageIcon iconPng = createImageIcon("images/save.png");
        final Icon pngCell = new ImageIcon(iconPng.getImage().getScaledInstance(18, 20, 4));
        pngButton.setIcon(pngCell);
        pngButton.setToolTipText("Click to capture spots overlay.");
        final JPanel panelPng = new JPanel(new FlowLayout(0));
        panelPng.add(pngButton);
        final JButton csvButton = new JButton();
        final ImageIcon iconCsv = createImageIcon("images/csv.png");
        final Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
        csvButton.setIcon(csvCell);
        csvButton.setToolTipText("Click to export your spots table selection.");
        final JPanel panelCsv = new JPanel(new FlowLayout(0));
        panelCsv.add(csvButton);
        final JPanel panelPngCsv = new JPanel(new FlowLayout(0));
        panelPngCsv.add(panelPng);
        panelPngCsv.add(panelCsv);
        tabbedPaneSpot.addTab("SPOTS ", FirstWizardPanel.iconSpotCell, mainPanel, "Display Spot Analysis");
        tabbedPaneSpot.setTabLayoutPolicy(1);
        final JButton refreshButton = new JButton();
        final ImageIcon iconRefresh = createImageIcon("images/refresh.png");
        refreshButton.setIcon(FirstWizardPanel.refreshCell = new ImageIcon(iconRefresh.getImage().getScaledInstance(18, 20, 4)));
        refreshButton.setToolTipText("Click this button to get spot analysis");
        final JToggleButton paintButton = new JToggleButton();
        final ImageIcon iconPaint = createImageIcon("images/paint.png");
        final Icon paintCell = new ImageIcon(iconPaint.getImage().getScaledInstance(18, 20, 4));
        paintButton.setIcon(paintCell);
        paintButton.setToolTipText("Click this button to display labeled-spots");
        final JToggleButton tInsideButton = new JToggleButton();
        final ImageIcon iconTI = createImageIcon("images/tinside.png");
        final Icon TICell = new ImageIcon(iconTI.getImage().getScaledInstance(18, 20, 4));
        tInsideButton.setIcon(TICell);
        tInsideButton.setToolTipText("Click this button to toggle inside spots.");
        final JToggleButton tOutsideButton = new JToggleButton();
        final ImageIcon iconTO = createImageIcon("images/toutside.png");
        final Icon TOCell = new ImageIcon(iconTO.getImage().getScaledInstance(18, 20, 4));
        tOutsideButton.setIcon(TOCell);
        tOutsideButton.setToolTipText("Click this button to toggle outside spots.");
        final JButton enableButton = new JButton();
        final ImageIcon iconEnable = createImageIcon("images/enable.png");
        final Icon enableCell = new ImageIcon(iconEnable.getImage().getScaledInstance(18, 20, 4));
        enableButton.setIcon(enableCell);
        enableButton.setToolTipText("Click this button to enable your selection");
        final JButton disableButton = new JButton();
        final ImageIcon iconDisable = createImageIcon("images/disable.png");
        final Icon disableCell = new ImageIcon(iconDisable.getImage().getScaledInstance(18, 20, 4));
        disableButton.setIcon(disableCell);
        disableButton.setToolTipText("Click this button to disable your selection");
        final JPanel buttonPanel = new JPanel(new FlowLayout(2));
        final JSeparator separator1 = new JSeparator(1);
        final JSeparator separator2 = new JSeparator(1);
        final Dimension dime = separator1.getPreferredSize();
        dime.height = refreshButton.getPreferredSize().height;
        separator1.setPreferredSize(dime);
        separator2.setPreferredSize(dime);
        this.checkRPicker = new JCheckBox(" Spot Picker");
        final JLabel filterLabel = new JLabel("  ➠ Spot Analysis : ");
        filterLabel.setFont(new Font("Dialog", 1, 13));
        filterLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        final JPanel filterPanel = new JPanel(new FlowLayout(0));
        filterPanel.add(filterLabel);
        filterPanel.add(this.checkRPicker);
        filterPanel.add(Box.createHorizontalStrut(20));
        final JPanel filterMain = new JPanel(new FlowLayout(0));
        filterMain.add(filterPanel);
        buttonPanel.add(refreshButton);
        buttonPanel.add(paintButton);
        buttonPanel.add(separator1);
        buttonPanel.add(enableButton);
        buttonPanel.add(disableButton);
        buttonPanel.add(separator2);
        buttonPanel.add(tInsideButton);
        buttonPanel.add(tOutsideButton);
        filterMain.add(buttonPanel);
        mainPanel.add(FirstWizardPanel.jScrollPaneImages);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(filterMain);
        mainPanel.add(FirstWizardPanel.jScrollPaneSpot);
        final JLabel settingsLabel = new JLabel("  ➠ Settings for Filters/Classes : ");
        settingsLabel.setFont(new Font("Dialog", 1, 13));
        settingsLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        final JPanel settingsPanel = new JPanel(new FlowLayout(0));
        settingsPanel.add(settingsLabel);
        mainPanel.add(settingsPanel);
        final JPanel filtersMin = new JPanel(new FlowLayout(0));
        (this.filterMin = new JSpinner(new SpinnerNumberModel(30, 0, 5000, 1))).setPreferredSize(new Dimension(60, 20));
        final JSlider sliderMin = new JSlider(0, 300, 50);
        sliderMin.setPreferredSize(new Dimension(150, 15));
        final JLabel filterMinLabel = new JLabel("              Min :  ");
        filtersMin.add(filterMinLabel);
        filtersMin.add(sliderMin);
        filtersMin.add(Box.createHorizontalStrut(2));
        filtersMin.add(this.filterMin);
        final JPanel filtersMax = new JPanel(new FlowLayout(0));
        (this.filterMax = new JSpinner(new SpinnerNumberModel(200, 0, 5000, 1))).setPreferredSize(new Dimension(60, 20));
        final JSlider sliderMax = new JSlider(0, 300, 150);
        sliderMax.setPreferredSize(new Dimension(150, 15));
        final JLabel filterMaxLabel = new JLabel("              Max :  ");
        filtersMax.add(filterMaxLabel);
        filtersMax.add(sliderMax);
        filtersMax.add(Box.createHorizontalStrut(2));
        filtersMax.add(this.filterMax);
        final JPanel boxPanel2 = new JPanel();
        boxPanel2.setLayout(new BoxLayout(boxPanel2, 1));
        final IntervalMarker intervalMarker = new IntervalMarker(0.0, 0.0, (Paint)new Color(229, 255, 204), (Stroke)new BasicStroke(), (Paint)new Color(0, 102, 0), (Stroke)new BasicStroke(1.5f), 0.5f);
        this.histogram = this.hs2.createChartPanel("", new double[] { 0.0, 0.0, 0.0 }, 100, intervalMarker);
        final JPanel chartPanel2 = new JPanel(new BorderLayout());
        chartPanel2.setPreferredSize(new Dimension(390, 180));
        chartPanel2.add((Component)this.histogram);
        boxPanel2.add(chartPanel2);
        final JPanel controlPanel2 = this.hs2.createControlPanel();
        boxPanel2.add(controlPanel2);
        final JPanel filtersMain2 = new JPanel();
        filtersMain2.setLayout(new BoxLayout(filtersMain2, 1));
        filtersMain2.add(boxPanel2);
        filtersMain2.add(filtersMin);
        filtersMain2.add(filtersMax);
        final JLabel featureSpot = new JLabel(" » Spot-Features :  ");
        featureSpot.setFont(new Font("Dialog", 1, 13));
        FirstWizardPanel.comboFilters = new JComboBox<String>();
        for (int l = 0; l < FirstWizardPanel.columnNamesSpot.length; ++l) {
            FirstWizardPanel.comboFilters.addItem((String)FirstWizardPanel.columnNamesSpot[l]);
        }
        FirstWizardPanel.comboFilters.setPreferredSize(new Dimension(130, 25));
        FirstWizardPanel.comboFilters.setSelectedIndex(0);
        FirstWizardPanel.comboFilters.setOpaque(true);
        final JPanel panelFilters = new JPanel(new FlowLayout(0));
        final JSeparator separator3 = new JSeparator(1);
        final Dimension dime2 = separator3.getPreferredSize();
        dime2.height = filtersMain2.getPreferredSize().height;
        separator3.setPreferredSize(dime2);
        panelFilters.add(filtersMain2);
        panelFilters.add(separator3);
        FirstWizardPanel.modelListClass = new DefaultListModel<String>();
        FirstWizardPanel.classList = new JList<String>(FirstWizardPanel.modelListClass);
        FirstWizardPanel.modelListFeature = new DefaultListModel<String>();
        FirstWizardPanel.featureList = new JList<String>(FirstWizardPanel.modelListFeature);
        final ColorEditorSpot colorEditor = new ColorEditorSpot(FirstWizardPanel.featureList);
        final JScrollPane scrollListFilter = new JScrollPane(FirstWizardPanel.featureList);
        final JScrollPane scrollListClass = new JScrollPane(FirstWizardPanel.classList);
        final Dimension d = FirstWizardPanel.featureList.getPreferredSize();
        d.width = 150;
        d.height = 90;
        scrollListFilter.setPreferredSize(d);
        scrollListClass.setPreferredSize(d);
        final JPanel filterPanelButtons = new JPanel(new FlowLayout(0));
        final JPanel classPanelButtons = new JPanel();
        classPanelButtons.setLayout(new BoxLayout(classPanelButtons, 1));
        filterPanelButtons.add(scrollListFilter);
        final JPanel fButtonsPanel = new JPanel();
        fButtonsPanel.setLayout(new BoxLayout(fButtonsPanel, 1));
        final JButton addButton = new JButton();
        final ImageIcon iconAdd = createImageIcon("images/add.png");
        final Icon addCell = new ImageIcon(iconAdd.getImage().getScaledInstance(14, 16, 4));
        addButton.setIcon(addCell);
        addButton.setToolTipText("Click this button to add features");
        final JButton remButton = new JButton();
        final ImageIcon iconRem = createImageIcon("images/remove.png");
        final Icon remCell = new ImageIcon(iconRem.getImage().getScaledInstance(14, 16, 4));
        remButton.setIcon(remCell);
        remButton.setToolTipText("Click this button to remove features");
        final JButton classButton = new JButton();
        final ImageIcon iconClass = createImageIcon("images/classes.png");
        final Icon classCell = new ImageIcon(iconClass.getImage().getScaledInstance(14, 16, 4));
        classButton.setIcon(classCell);
        classButton.setToolTipText("Click this button to create a class.");
        final JButton remClassButton = new JButton();
        remClassButton.setIcon(remCell);
        remClassButton.setToolTipText("Click this button to remove a class.");
        fButtonsPanel.add(addButton);
        fButtonsPanel.add(remButton);
        filterPanelButtons.add(fButtonsPanel);
        classPanelButtons.add(classButton);
        classPanelButtons.add(remClassButton);
        final JPanel classPanel = new JPanel(new FlowLayout(0));
        classPanel.add(scrollListClass);
        classPanel.add(classPanelButtons);
        final JPanel boxPanel3 = new JPanel();
        boxPanel3.setLayout(new BoxLayout(boxPanel3, 1));
        boxPanel3.add(FirstWizardPanel.comboFilters);
        boxPanel3.add(Box.createHorizontalStrut(5));
        boxPanel3.add(filterPanelButtons);
        boxPanel3.add(Box.createHorizontalStrut(5));
        boxPanel3.add(classPanel);
        boxPanel3.add(panelPngCsv);
        panelFilters.add(boxPanel3);
        mainPanel.add(panelFilters);
        this.add((Component)tabbedPaneSpot);
        this.createMovieTable();
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.refreshThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FirstWizardPanel.spotEnable = "spotEnable";
                        ProcessTrackMateXml.tracksVisible = false;
                        ProcessTrackMateXml.spotsVisible = true;
                        final ProcessTrackMateXml ptx = new ProcessTrackMateXml();
                        ptx.processTrackMateXml();
                    }
                })).start();
            }
        });
        csvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.csvThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> columnSpotHead = new ArrayList<String>();
                        for (int j = 0; j < FirstWizardPanel.modelSpot.getColumnCount(); ++j) {
                            columnSpotHead.add(FirstWizardPanel.modelSpot.getColumnName(j));
                        }
                        final ResultsTable rt = new ResultsTable(Integer.valueOf(FirstWizardPanel.modelSpot.getRowCount()));
                        if (rt != null) {
                            rt.reset();
                        }
                        for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                            for (int k = 0; k < FirstWizardPanel.modelSpot.getColumnCount(); ++k) {
                                if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE) {
                                    if (columnSpotHead.get(k).equals(columnSpotHead.get(0)) == Boolean.TRUE) {
                                        rt.setValue((String)columnSpotHead.get(k), i, ((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, k)).getText());
                                    }
                                    else {
                                        rt.setValue((String)columnSpotHead.get(k), i, FirstWizardPanel.modelSpot.getValueAt(i, k).toString());
                                    }
                                }
                            }
                        }
                        final JFrame pngFrame = new JFrame();
                        final JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileSelectionMode(1);
                        fileChooser.setDialogTitle("Specify a directory to save csv file");
                        final int userSelection = fileChooser.showSaveDialog(pngFrame);
                        if (userSelection == 0) {
                            final File fileToSave = fileChooser.getSelectedFile();
                            try {
                                rt.saveAs(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + "SpotStatistics for-" + IJ.getImage().getShortTitle() + ".csv");
                            }
                            catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                })).start();
            }
        });
        pngButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.pngThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (IJ.getImage() == null) {
                            IJ.error("You must have an image window active.");
                        }
                        if (IJ.getImage() != null) {
                            final JFrame pngFrame = new JFrame();
                            final JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setFileSelectionMode(1);
                            fileChooser.setDialogTitle("Specify a directory to save");
                            final int userSelection = fileChooser.showSaveDialog(pngFrame);
                            if (userSelection == 0) {
                                final File fileToSave = fileChooser.getSelectedFile();
                                int firstFrame = 0;
                                int lastFrame = 0;
                                if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
                                    firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
                                    lastFrame = Math.min(IJ.getImage().getNFrames(), Math.max(IJ.getImage().getNFrames(), 1));
                                }
                                if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
                                    firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
                                    lastFrame = Math.min(IJ.getImage().getNSlices(), Math.max(IJ.getImage().getNSlices(), 1));
                                }
                                final Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
                                final int width = bounds.width;
                                final int height = bounds.height;
                                final int nCaptures = lastFrame - firstFrame + 1;
                                final ImageStack stack = new ImageStack(width, height);
                                final int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
                                final int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
                                ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
                                for (int frame = firstFrame; frame <= lastFrame; ++frame) {
                                    ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, frame);
                                    final BufferedImage bi = new BufferedImage(width, height, 2);
                                    ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
                                    final ColorProcessor cp = new ColorProcessor((Image)bi);
                                    final int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, frame);
                                    stack.addSlice(ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), (ImageProcessor)cp);
                                }
                                ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
                                final ImagePlus capture = new ImagePlus("TrackMate capture of " + ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
                                transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
                                IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + "Capture Overlay for " + IJ.getImage().getShortTitle());
                            }
                        }
                    }
                })).start();
            }
        });
        paintButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent ev) {
                (FirstWizardPanel.this.paintThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (ev.getStateChange() == 1) {
                            FirstWizardPanel.this.paintAndDisableAction();
                        }
                        else if (ev.getStateChange() == 2) {
                            FirstWizardPanel.this.resetAndEnableAction();
                        }
                    }
                })).start();
            }
        });
        tInsideButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent ev) {
                (FirstWizardPanel.this.tInsideThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (ev.getStateChange() == 1) {
                            FirstWizardPanel.this.toggleInsideAction();
                        }
                        else if (ev.getStateChange() == 2) {
                            FirstWizardPanel.this.resetToggleInsideAction();
                        }
                    }
                })).start();
            }
        });
        tOutsideButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent ev) {
                (FirstWizardPanel.this.tOutsideThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (ev.getStateChange() == 1) {
                            FirstWizardPanel.this.toggleOutsideAction();
                        }
                        else if (ev.getStateChange() == 2) {
                            FirstWizardPanel.this.resetToggleOutsideAction();
                        }
                    }
                })).start();
            }
        });
        enableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.enableThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FirstWizardPanel.this.enableSpots();
                    }
                })).start();
            }
        });
        disableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.disableThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FirstWizardPanel.this.disableSpots();
                    }
                })).start();
            }
        });
        sliderMin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                (FirstWizardPanel.this.slMinThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FirstWizardPanel.this.filterMin.setValue(sliderMin.getValue());
                        intervalMarker.setStartValue((double)sliderMin.getValue());
                    }
                })).start();
            }
        });
        this.filterMin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                (FirstWizardPanel.this.filterMinThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sliderMin.setValue((int)FirstWizardPanel.this.filterMin.getValue());
                        intervalMarker.setStartValue((double)(int)FirstWizardPanel.this.filterMin.getValue());
                    }
                })).start();
            }
        });
        sliderMax.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                (FirstWizardPanel.this.slMaxThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FirstWizardPanel.this.filterMax.setValue(sliderMax.getValue());
                        intervalMarker.setEndValue((double)sliderMax.getValue());
                    }
                })).start();
            }
        });
        this.filterMax.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                (FirstWizardPanel.this.filterMaxThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sliderMax.setValue((int)FirstWizardPanel.this.filterMax.getValue());
                        intervalMarker.setEndValue((double)(int)FirstWizardPanel.this.filterMax.getValue());
                    }
                })).start();
            }
        });
        FirstWizardPanel.comboFilters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.filtersThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String selectedName = (String)FirstWizardPanel.comboFilters.getSelectedItem();
                        final int selectedIndex = FirstWizardPanel.comboFilters.getSelectedIndex();
                        double[] values = null;
                        values = new double[FirstWizardPanel.tableSpot.getRowCount()];
                        for (int r = 0; r < FirstWizardPanel.tableSpot.getRowCount(); ++r) {
                            for (int c = 0; c < FirstWizardPanel.tableSpot.getColumnCount(); ++c) {
                                values[r] = Double.parseDouble((String)FirstWizardPanel.tableSpot.getValueAt(r, selectedIndex + 2));
                            }
                        }
                        double max = values[0];
                        for (int i = 1; i < values.length; ++i) {
                            if (values[i] > max) {
                                max = values[i];
                            }
                        }
                        sliderMin.setMinimum(0);
                        sliderMin.setMaximum((int)max);
                        sliderMax.setMinimum(0);
                        sliderMax.setMaximum((int)max);
                        FirstWizardPanel.this.hs2.addHistogramSeries(selectedName, values, (int)max, intervalMarker);
                    }
                })).start();
            }
        });
        this.checkRPicker.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                (FirstWizardPanel.this.pickerThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (e.getStateChange() == 1) {
                            FirstWizardPanel.command = "enable";
                        }
                        if (e.getStateChange() == 2) {
                            FirstWizardPanel.command = null;
                            ProcessTrackMateXml.selectionModel.clearSpotSelection();
                            ProcessTrackMateXml.selectionModel.clearSelection();
                        }
                    }
                })).start();
            }
        });
        classButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.classThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ColorEditorSpot.myFrame.setVisible(true);
                        colorEditor.setClassAction();
                    }
                })).start();
            }
        });
        remClassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.remClassThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String classSelectedValue = FirstWizardPanel.classList.getSelectedValue();
                        final int[] classSelectedIndex = FirstWizardPanel.classList.getSelectedIndices();
                        for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                            if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getText().equals(classSelectedValue)) {
                                FirstWizardPanel.modelSpot.setValueAt(FirstWizardPanel.labelReset, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
                            }
                        }
                        for (int i = 0; i < classSelectedIndex.length; ++i) {
                            FirstWizardPanel.modelListClass.removeElementAt(classSelectedIndex[i]);
                        }
                    }
                })).start();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.addThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> listFilters = new ArrayList<String>();
                        if (FirstWizardPanel.featureList.getModel().getSize() < 1) {
                            FirstWizardPanel.modelListFeature.addElement(String.valueOf(FirstWizardPanel.comboFilters.getSelectedItem()) + ":  [" + FirstWizardPanel.this.filterMin.getValue() + "," + FirstWizardPanel.this.filterMax.getValue() + "]");
                        }
                        if (FirstWizardPanel.featureList.getModel().getSize() >= 1) {
                            for (int i = 0; i < FirstWizardPanel.featureList.getModel().getSize(); ++i) {
                                listFilters.add(String.valueOf(FirstWizardPanel.featureList.getModel().getElementAt(i).substring(0, FirstWizardPanel.featureList.getModel().getElementAt(i).lastIndexOf(":"))));
                            }
                            if (!listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
                                FirstWizardPanel.modelListFeature.addElement(String.valueOf(FirstWizardPanel.comboFilters.getSelectedItem()) + ":  [" + FirstWizardPanel.this.filterMin.getValue() + "," + FirstWizardPanel.this.filterMax.getValue() + "]");
                            }
                            if (listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
                                return;
                            }
                        }
                    }
                })).start();
            }
        });
        remButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (FirstWizardPanel.this.remThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final int[] indexes = FirstWizardPanel.featureList.getSelectedIndices();
                            for (int i = 0; i < indexes.length; ++i) {
                                FirstWizardPanel.modelListFeature.remove(indexes[i]);
                            }
                        }
                        catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                })).start();
            }
        });
    }
    
    public void toggleOutsideAction() {
        Roi mainRoi = null;
        if (IJ.getImage().getRoi().getType() == 0) {
            mainRoi = IJ.getImage().getRoi();
        }
        this.indexesTO = new ArrayList<Integer>();
        for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
            if (mainRoi.contains((int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(5)).toString())), (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(6)).toString()))) == Boolean.FALSE) {
                this.indexesTO.add(i);
                FirstWizardPanel.modelSpot.setValueAt(false, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(0));
                final int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(i, 2));
                final Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
                if (spot != null) {
                    spot.putFeature("VISIBILITY", SpotCollection.ZERO);
                    ProcessTrackMateXml.model.endUpdate();
                    ProcessTrackMateXml.displayer.refresh();
                }
            }
        }
    }
    
    public void resetToggleOutsideAction() {
        for (int row = 0; row < FirstWizardPanel.modelSpot.getRowCount(); ++row) {
            FirstWizardPanel.modelSpot.setValueAt(true, FirstWizardPanel.tableSpot.convertRowIndexToModel(row), FirstWizardPanel.tableSpot.convertColumnIndexToModel(0));
            final int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(row, 2));
            final Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
            if (spot != null) {
                spot.putFeature("VISIBILITY", SpotCollection.ONE);
                ProcessTrackMateXml.model.endUpdate();
                ProcessTrackMateXml.displayer.refresh();
            }
        }
    }
    
    public void toggleInsideAction() {
        Roi mainRoi = null;
        if (IJ.getImage().getRoi().getType() == 0) {
            mainRoi = IJ.getImage().getRoi();
        }
        this.indexesTI = new ArrayList<Integer>();
        for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
            if (mainRoi.contains((int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(5)).toString())), (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(6)).toString()))) == Boolean.TRUE) {
                this.indexesTI.add(i);
                FirstWizardPanel.modelSpot.setValueAt(false, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(0));
                final int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(i, 2));
                final Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
                if (spot != null) {
                    spot.putFeature("VISIBILITY", SpotCollection.ZERO);
                    ProcessTrackMateXml.model.endUpdate();
                    ProcessTrackMateXml.displayer.refresh();
                }
            }
        }
    }
    
    public void resetToggleInsideAction() {
        for (int row = 0; row < FirstWizardPanel.modelSpot.getRowCount(); ++row) {
            FirstWizardPanel.modelSpot.setValueAt(true, FirstWizardPanel.tableSpot.convertRowIndexToModel(row), FirstWizardPanel.tableSpot.convertColumnIndexToModel(0));
            final int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(row, 2));
            final Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
            if (spot != null) {
                spot.putFeature("VISIBILITY", SpotCollection.ONE);
                ProcessTrackMateXml.model.endUpdate();
                ProcessTrackMateXml.displayer.refresh();
            }
        }
    }
    
    public void paintAndDisableAction() {
        this.indexesToReset = new ArrayList<Integer>();
        this.spotID = new ArrayList<Integer>();
        this.spots = new ArrayList<Spot>();
        for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
            if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getBackground().equals(new Color(214, 217, 223)) == Boolean.TRUE) {
                this.indexesToReset.add(i);
                FirstWizardPanel.modelSpot.setValueAt(false, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(0));
                this.spotID.add(Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(i, 2)));
            }
        }
        for (int row = 0; row < this.indexesToReset.size(); ++row) {
            final int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(this.indexesToReset.get(row), 2));
            final Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
            if (spot != null) {
                spot.putFeature("VISIBILITY", SpotCollection.ZERO);
                ProcessTrackMateXml.model.endUpdate();
                ProcessTrackMateXml.displayer.refresh();
            }
        }
    }
    
    public void resetAndEnableAction() {
        for (int i = 0; i < this.indexesToReset.size(); ++i) {
            FirstWizardPanel.modelSpot.setValueAt(true, FirstWizardPanel.tableSpot.convertRowIndexToModel(this.indexesToReset.get(i)), FirstWizardPanel.tableSpot.convertColumnIndexToModel(0));
        }
        for (int row = 0; row < this.indexesToReset.size(); ++row) {
            final int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(this.indexesToReset.get(row), 2));
            final Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
            if (spot != null) {
                spot.putFeature("VISIBILITY", SpotCollection.ONE);
                ProcessTrackMateXml.model.endUpdate();
                ProcessTrackMateXml.displayer.refresh();
            }
        }
    }
    
    public void enableSpots() {
        final ListSelectionModel lsm = FirstWizardPanel.tableSpot.getSelectionModel();
        final int[] selectedIndices = FirstWizardPanel.tableSpot.getSelectedRows();
        for (int i = 0; i < selectedIndices.length; ++i) {
            FirstWizardPanel.tableSpot.setValueAt(true, selectedIndices[i], 0);
        }
        final int selStart = lsm.getMinSelectionIndex();
        final int selEnd = lsm.getMaxSelectionIndex();
        if (selStart < 0 || selEnd < 0) {
            return;
        }
        final int minLine = Math.min(selStart, selEnd);
        for (int maxLine = Math.max(selStart, selEnd), row = minLine; row <= maxLine; ++row) {
            final int spotIDEnable = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(row, 2));
            final Spot spotEnable = ProcessTrackMateXml.model.getSpots().search(spotIDEnable);
            if (spotEnable != null) {
                spotEnable.putFeature("VISIBILITY", SpotCollection.ONE);
                ProcessTrackMateXml.model.endUpdate();
                ProcessTrackMateXml.displayer.refresh();
            }
        }
    }
    
    public void disableSpots() {
        final ListSelectionModel lsm = FirstWizardPanel.tableSpot.getSelectionModel();
        final int selStart = lsm.getMinSelectionIndex();
        final int selEnd = lsm.getMaxSelectionIndex();
        if (selStart < 0 || selEnd < 0) {
            return;
        }
        final int minLine = Math.min(selStart, selEnd);
        for (int maxLine = Math.max(selStart, selEnd), row = minLine; row <= maxLine; ++row) {
            final int spotID = Integer.parseInt((String)FirstWizardPanel.tableSpot.getValueAt(row, 2));
            final Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
            if (spot != null) {
                spot.putFeature("VISIBILITY", SpotCollection.ZERO);
                ProcessTrackMateXml.model.endUpdate();
                ProcessTrackMateXml.displayer.refresh();
            }
        }
        final int[] selectedIndices = FirstWizardPanel.tableSpot.getSelectedRows();
        for (int i = 0; i < selectedIndices.length; ++i) {
            FirstWizardPanel.tableSpot.setValueAt(false, selectedIndices[i], 0);
        }
    }
    
    public static void createSpotTable() {
        (FirstWizardPanel.modelSpot = new DefaultTableModel(ProcessTrackMateXml.dataSpot, ProcessTrackMateXml.columnHeadersSpot) {
            @Override
            public Class<?> getColumnClass(final int column) {
                if (this.getRowCount() > 0) {
                    final Object value = this.getValueAt(0, column);
                    if (value != null) {
                        return this.getValueAt(0, column).getClass();
                    }
                }
                return super.getColumnClass(column);
            }
        }).addColumn("Enable");
        FirstWizardPanel.tableSpot.setModel(FirstWizardPanel.modelSpot);
        FirstWizardPanel.tableSpot.moveColumn(FirstWizardPanel.tableSpot.getColumnCount() - 1, 0);
        FirstWizardPanel.tableSpot.setSelectionBackground(new Color(229, 255, 204));
        FirstWizardPanel.tableSpot.setSelectionForeground(new Color(0, 102, 0));
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(0);
        FirstWizardPanel.tableSpot.setDefaultRenderer(String.class, centerRenderer);
        FirstWizardPanel.tableSpot.setAutoResizeMode(0);
        FirstWizardPanel.tableSpot.setRowHeight(45);
        FirstWizardPanel.tableSpot.setAutoCreateRowSorter(true);
        FirstWizardPanel.tableSpot.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
        for (int u = 0; u < FirstWizardPanel.tableSpot.getColumnCount(); ++u) {
            FirstWizardPanel.tableSpot.getColumnModel().getColumn(u).setPreferredWidth(90);
        }
        for (int u = 16; u < FirstWizardPanel.tableSpot.getColumnCount(); ++u) {
            FirstWizardPanel.tableSpot.getColumnModel().getColumn(u).setPreferredWidth(150);
        }
        for (int i = 0; i < FirstWizardPanel.tableSpot.getRowCount(); ++i) {
            FirstWizardPanel.tableSpot.setValueAt(true, i, 0);
        }
        FirstWizardPanel.tableSpot.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
        (FirstWizardPanel.labelReset = new JLabel()).setText("");
        FirstWizardPanel.labelReset.setOpaque(true);
        FirstWizardPanel.labelReset.setBackground(new Color(214, 217, 223));
        for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
            FirstWizardPanel.modelSpot.setValueAt(FirstWizardPanel.labelReset, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
        }
    }
    
    public void createMovieTable() {
        FirstWizardPanel.tableImages.setSelectionBackground(new Color(229, 255, 204));
        FirstWizardPanel.tableImages.setSelectionForeground(new Color(0, 102, 0));
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(0);
        FirstWizardPanel.tableImages.setDefaultRenderer(String.class, centerRenderer);
        FirstWizardPanel.tableImages.setAutoResizeMode(0);
        FirstWizardPanel.tableImages.setRowHeight(95);
        FirstWizardPanel.tableImages.setAutoCreateRowSorter(true);
        FirstWizardPanel.tableImages.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
        for (int i = 0; i < FirstWizardPanel.modelImages.getRowCount(); ++i) {
            FirstWizardPanel.modelImages.setValueAt(this.icons[i], i, FirstWizardPanel.tableImages.convertColumnIndexToModel(0));
            FirstWizardPanel.modelImages.setValueAt(FirstWizardPanel.imps[i].getShortTitle(), i, FirstWizardPanel.tableImages.convertColumnIndexToModel(1));
            FirstWizardPanel.modelImages.setValueAt(FirstWizardPanel.imps[i].getTitle().substring(FirstWizardPanel.imps[i].getTitle().lastIndexOf(".")), i, FirstWizardPanel.tableImages.convertColumnIndexToModel(2));
        }
    }
    
    public static Image getScaledImage(final Image srcImg, final int w, final int h) {
        final BufferedImage resizedImg = new BufferedImage(w, h, 1);
        final Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
    
    public static ImageIcon createImageIcon(final String path) {
        final URL imgURL = FirstWizardPanel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }
        System.err.println("Couldn't find file: " + path);
        return null;
    }
    
    public ImagePlus extractTFrame(final ImagePlus imp, final int frame) {
        final int width = imp.getWidth();
        final int height = imp.getHeight();
        final int channels = imp.getNChannels();
        final int zslices = imp.getNSlices();
        final FileInfo fileInfo = imp.getOriginalFileInfo();
        final ImageStack stack2 = new ImageStack(width, height);
        final ImagePlus imp2 = new ImagePlus();
        imp2.setTitle("T" + frame + "-" + imp.getTitle());
        for (int z = 1; z <= zslices; ++z) {
            for (int c = 1; c <= channels; ++c) {
                final int sliceSix = imp.getStackIndex(c, z, frame);
                stack2.addSlice("", imp.getStack().getProcessor(sliceSix));
            }
        }
        imp2.setStack(stack2);
        imp2.setDimensions(channels, zslices, 1);
        if (channels * zslices > 1) {
            imp2.setOpenAsHyperStack(true);
        }
        imp2.setFileInfo(fileInfo);
        return imp2;
    }
    
    private static final void transferCalibration(final ImagePlus from, final ImagePlus to) {
        final Calibration fc = from.getCalibration();
        final Calibration tc = to.getCalibration();
        tc.setUnit(fc.getUnit());
        tc.setTimeUnit(fc.getTimeUnit());
        tc.frameInterval = fc.frameInterval;
        final double mag = from.getCanvas().getMagnification();
        tc.pixelWidth = fc.pixelWidth / mag;
        tc.pixelHeight = fc.pixelHeight / mag;
        tc.pixelDepth = fc.pixelDepth;
    }
}
