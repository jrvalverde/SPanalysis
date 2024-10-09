import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.SpotCollection;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Roi;
import ij.io.FileInfo;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.process.ColorProcessor;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import jwizardcomponent.JWizardComponents;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.IntervalMarker;

public class FirstWizardPanel extends LabelWizardPanel {
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
   static String command = "";
   static String spotEnable = "";
   List<Spot> removedSpots;
   List<Spot> spots;
   JSpinner filterMin;
   JSpinner filterMax;
   ChartPanel histogram;
   HistogramFilterVersion hs2 = new HistogramFilterVersion();
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

   public FirstWizardPanel(JWizardComponents wizardComponents) {
      super(wizardComponents, "");
      File imageFolder = new File(TrackAnalyzer_.textImages.getText());
      File[] listOfFiles = imageFolder.listFiles();
      String[] imageTitles = new String[listOfFiles.length];
      File[] filesXML = new File[listOfFiles.length];

      int i;
      for(i = 0; i < filesXML.length; ++i) {
         filesXML[i] = new File(TrackAnalyzer_.textXml.getText());
      }

      impsPZ = new ImagePlus[imageTitles.length];
      imps = new ImagePlus[imageTitles.length];
      this.icons = new ImageIcon[imps.length];

      for(i = 0; i < listOfFiles.length; ++i) {
         if (listOfFiles[i].isFile()) {
            imageTitles[i] = listOfFiles[i].getName();
         }

         imps[i] = IJ.openImage(TrackAnalyzer_.textImages.getText() + "/" + imageTitles[i]);
         impsPZ[i] = this.extractTFrame(imps[i], 1);
         this.icons[i] = new ImageIcon(getScaledImage(impsPZ[i].getImage(), 90, 95));
      }

      tableImages = new JTable();
      tableSpot = new JTable();
      modelImages = new DefaultTableModel();
      modelSpot = new DefaultTableModel();
      Object[] columnNames = new Object[]{"Movie", "Title", "Extension"};
      columnNamesSpot = new Object[]{"ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MEAN_INTENSITY", "MEDIAN_INTENSITY", "MIN_INTENSITY", "MAX_INTENSITY", "TOTAL_INTENSITY", "STANDARD_DEVIATION", "CONTRAST", "SNR", "ESTIMATED_DIAMETER", "MORPHOLOGY", "ELLIPSOIDFIT_SEMIAXISLENGTH_C", "ELLIPSOIDFIT_SEMIAXISLENGTH_B", "ELLIPSOIDFIT_SEMIAXISLENGTH_A", "ELLIPSOIDFIT_AXISPHI_C", "ELLIPSOIDFIT_AXISPHI_B", "ELLIPSOIDFIT_AXISPHI_A", "ELLIPSOIDFIT_AXISTHETA_C", "ELLIPSOIDFIT_AXISTHETA_B", "ELLIPSOIDFIT_AXISTHETA_A", "MANUAL_COLOR"};
      Object[][] data = new Object[imps.length][columnNames.length];

      for(int i = 0; i < data.length; ++i) {
         for(int j = 0; j < data[i].length; ++j) {
            data[i][j] = "";
         }
      }

      modelSpot = new DefaultTableModel();
      modelImages = new DefaultTableModel(data, columnNames) {
         public Class<?> getColumnClass(int column) {
            if (this.getRowCount() > 0) {
               Object value = this.getValueAt(0, column);
               if (value != null) {
                  return this.getValueAt(0, column).getClass();
               }
            }

            return super.getColumnClass(column);
         }
      };
      tableImages.setModel(modelImages);
      tableSpot.setModel(modelSpot);
      tableSpot.setSelectionMode(1);
      tableImages.getColumnModel().getColumn(0).setPreferredWidth(90);
      tableImages.getColumnModel().getColumn(1).setPreferredWidth(460);
      tableImages.getColumnModel().getColumn(2).setPreferredWidth(80);
      jScrollPaneImages = new JScrollPane(tableImages);
      jScrollPaneSpot = new JScrollPane(tableSpot);
      jScrollPaneImages.setPreferredSize(new Dimension(590, 240));
      jScrollPaneSpot.setPreferredSize(new Dimension(590, 240));
      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BoxLayout(mainPanel, 1));
      jScrollPaneImages.setBorder(BorderFactory.createTitledBorder(""));
      jScrollPaneSpot.setBorder(BorderFactory.createTitledBorder(""));
      JTabbedPane tabbedPaneSpot = new JTabbedPane(1);
      ImageIcon iconSpot = this.createImageIcon("/images/spot.png");
      iconSpotCell = new ImageIcon(iconSpot.getImage().getScaledInstance(18, 20, 4));
      JButton pngButton = new JButton();
      ImageIcon iconPng = this.createImageIcon("/images/save.png");
      Icon pngCell = new ImageIcon(iconPng.getImage().getScaledInstance(18, 20, 4));
      pngButton.setIcon(pngCell);
      pngButton.setToolTipText("Click to capture spots overlay.");
      JPanel panelPng = new JPanel(new FlowLayout(0));
      panelPng.add(pngButton);
      JButton csvButton = new JButton();
      ImageIcon iconCsv = this.createImageIcon("/images/csv.png");
      Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
      csvButton.setIcon(csvCell);
      csvButton.setToolTipText("Click to export your spots table selection.");
      JPanel panelCsv = new JPanel(new FlowLayout(0));
      panelCsv.add(csvButton);
      JPanel panelPngCsv = new JPanel(new FlowLayout(0));
      panelPngCsv.add(panelPng);
      panelPngCsv.add(panelCsv);
      tabbedPaneSpot.addTab("SPOTS ", iconSpotCell, mainPanel, "Display Spot Analysis");
      tabbedPaneSpot.setTabLayoutPolicy(1);
      JButton refreshButton = new JButton();
      ImageIcon iconRefresh = this.createImageIcon("/images/refresh.png");
      refreshCell = new ImageIcon(iconRefresh.getImage().getScaledInstance(18, 20, 4));
      refreshButton.setIcon(refreshCell);
      refreshButton.setToolTipText("Click this button to get spot analysis");
      JToggleButton paintButton = new JToggleButton();
      ImageIcon iconPaint = this.createImageIcon("images/paint.png");
      Icon paintCell = new ImageIcon(iconPaint.getImage().getScaledInstance(18, 20, 4));
      paintButton.setIcon(paintCell);
      paintButton.setToolTipText("Click this button to display labeled-spots");
      JToggleButton tInsideButton = new JToggleButton();
      ImageIcon iconTI = this.createImageIcon("/images/tinside.png");
      Icon TICell = new ImageIcon(iconTI.getImage().getScaledInstance(18, 20, 4));
      tInsideButton.setIcon(TICell);
      tInsideButton.setToolTipText("Click this button to toggle inside spots.");
      JToggleButton tOutsideButton = new JToggleButton();
      ImageIcon iconTO = this.createImageIcon("/images/toutside.png");
      Icon TOCell = new ImageIcon(iconTO.getImage().getScaledInstance(18, 20, 4));
      tOutsideButton.setIcon(TOCell);
      tOutsideButton.setToolTipText("Click this button to toggle outside spots.");
      JButton enableButton = new JButton();
      ImageIcon iconEnable = this.createImageIcon("/images/enable.png");
      Icon enableCell = new ImageIcon(iconEnable.getImage().getScaledInstance(18, 20, 4));
      enableButton.setIcon(enableCell);
      enableButton.setToolTipText("Click this button to enable your selection");
      JButton disableButton = new JButton();
      ImageIcon iconDisable = this.createImageIcon("/images/disable.png");
      Icon disableCell = new ImageIcon(iconDisable.getImage().getScaledInstance(18, 20, 4));
      disableButton.setIcon(disableCell);
      disableButton.setToolTipText("Click this button to disable your selection");
      JPanel buttonPanel = new JPanel(new FlowLayout(2));
      JSeparator separator1 = new JSeparator(1);
      JSeparator separator2 = new JSeparator(1);
      Dimension dime = separator1.getPreferredSize();
      dime.height = refreshButton.getPreferredSize().height;
      separator1.setPreferredSize(dime);
      separator2.setPreferredSize(dime);
      this.checkRPicker = new JCheckBox(" Spot Picker");
      JLabel filterLabel = new JLabel("  ➠ Spot Analysis : ");
      filterLabel.setFont(new Font("Dialog", 1, 13));
      filterLabel.setBorder(BorderFactory.createRaisedBevelBorder());
      JPanel filterPanel = new JPanel(new FlowLayout(0));
      filterPanel.add(filterLabel);
      filterPanel.add(this.checkRPicker);
      filterPanel.add(Box.createHorizontalStrut(20));
      JPanel filterMain = new JPanel(new FlowLayout(0));
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
      mainPanel.add(jScrollPaneImages);
      mainPanel.add(Box.createVerticalStrut(5));
      mainPanel.add(filterMain);
      mainPanel.add(jScrollPaneSpot);
      JLabel settingsLabel = new JLabel("  ➠ Settings for Filters/Classes : ");
      settingsLabel.setFont(new Font("Dialog", 1, 13));
      settingsLabel.setBorder(BorderFactory.createRaisedBevelBorder());
      JPanel settingsPanel = new JPanel(new FlowLayout(0));
      settingsPanel.add(settingsLabel);
      mainPanel.add(settingsPanel);
      JPanel filtersMin = new JPanel(new FlowLayout(0));
      this.filterMin = new JSpinner(new SpinnerNumberModel(30, 0, 5000, 1));
      this.filterMin.setPreferredSize(new Dimension(60, 20));
      final JSlider sliderMin = new JSlider(0, 300, 50);
      sliderMin.setPreferredSize(new Dimension(150, 15));
      JLabel filterMinLabel = new JLabel("              Min :  ");
      filtersMin.add(filterMinLabel);
      filtersMin.add(sliderMin);
      filtersMin.add(Box.createHorizontalStrut(2));
      filtersMin.add(this.filterMin);
      JPanel filtersMax = new JPanel(new FlowLayout(0));
      this.filterMax = new JSpinner(new SpinnerNumberModel(200, 0, 5000, 1));
      this.filterMax.setPreferredSize(new Dimension(60, 20));
      final JSlider sliderMax = new JSlider(0, 300, 150);
      sliderMax.setPreferredSize(new Dimension(150, 15));
      JLabel filterMaxLabel = new JLabel("              Max :  ");
      filtersMax.add(filterMaxLabel);
      filtersMax.add(sliderMax);
      filtersMax.add(Box.createHorizontalStrut(2));
      filtersMax.add(this.filterMax);
      JPanel boxPanel2 = new JPanel();
      boxPanel2.setLayout(new BoxLayout(boxPanel2, 1));
      final IntervalMarker intervalMarker = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), new BasicStroke(1.5F), 0.5F);
      this.histogram = this.hs2.createChartPanel("", new double[]{0.0D, 0.0D, 0.0D}, 100, intervalMarker);
      JPanel chartPanel2 = new JPanel(new BorderLayout());
      chartPanel2.setPreferredSize(new Dimension(390, 180));
      chartPanel2.add(this.histogram);
      boxPanel2.add(chartPanel2);
      JPanel controlPanel2 = this.hs2.createControlPanel();
      boxPanel2.add(controlPanel2);
      JPanel filtersMain2 = new JPanel();
      filtersMain2.setLayout(new BoxLayout(filtersMain2, 1));
      filtersMain2.add(boxPanel2);
      filtersMain2.add(filtersMin);
      filtersMain2.add(filtersMax);
      JLabel featureSpot = new JLabel(" » Spot-Features :  ");
      featureSpot.setFont(new Font("Dialog", 1, 13));
      comboFilters = new JComboBox();

      for(int i = 0; i < columnNamesSpot.length; ++i) {
         comboFilters.addItem((String)columnNamesSpot[i]);
      }

      comboFilters.setPreferredSize(new Dimension(130, 25));
      comboFilters.setSelectedIndex(0);
      comboFilters.setOpaque(true);
      JPanel panelFilters = new JPanel(new FlowLayout(0));
      JSeparator separator3 = new JSeparator(1);
      Dimension dime2 = separator3.getPreferredSize();
      dime2.height = filtersMain2.getPreferredSize().height;
      separator3.setPreferredSize(dime2);
      panelFilters.add(filtersMain2);
      panelFilters.add(separator3);
      modelListClass = new DefaultListModel();
      classList = new JList(modelListClass);
      modelListFeature = new DefaultListModel();
      featureList = new JList(modelListFeature);
      final ColorEditorSpot colorEditor = new ColorEditorSpot(featureList);
      JScrollPane scrollListFilter = new JScrollPane(featureList);
      JScrollPane scrollListClass = new JScrollPane(classList);
      Dimension d = featureList.getPreferredSize();
      d.width = 150;
      d.height = 90;
      scrollListFilter.setPreferredSize(d);
      scrollListClass.setPreferredSize(d);
      JPanel filterPanelButtons = new JPanel(new FlowLayout(0));
      JPanel classPanelButtons = new JPanel();
      classPanelButtons.setLayout(new BoxLayout(classPanelButtons, 1));
      filterPanelButtons.add(scrollListFilter);
      JPanel fButtonsPanel = new JPanel();
      fButtonsPanel.setLayout(new BoxLayout(fButtonsPanel, 1));
      JButton addButton = new JButton();
      ImageIcon iconAdd = this.createImageIcon("/images/add.png");
      Icon addCell = new ImageIcon(iconAdd.getImage().getScaledInstance(14, 16, 4));
      addButton.setIcon(addCell);
      addButton.setToolTipText("Click this button to add features");
      JButton remButton = new JButton();
      ImageIcon iconRem = this.createImageIcon("/images/remove.png");
      Icon remCell = new ImageIcon(iconRem.getImage().getScaledInstance(14, 16, 4));
      remButton.setIcon(remCell);
      remButton.setToolTipText("Click this button to remove features");
      JButton classButton = new JButton();
      ImageIcon iconClass = this.createImageIcon("/images/classes.png");
      Icon classCell = new ImageIcon(iconClass.getImage().getScaledInstance(14, 16, 4));
      classButton.setIcon(classCell);
      classButton.setToolTipText("Click this button to create a class.");
      JButton remClassButton = new JButton();
      remClassButton.setIcon(remCell);
      remClassButton.setToolTipText("Click this button to remove a class.");
      fButtonsPanel.add(addButton);
      fButtonsPanel.add(remButton);
      filterPanelButtons.add(fButtonsPanel);
      classPanelButtons.add(classButton);
      classPanelButtons.add(remClassButton);
      JPanel classPanel = new JPanel(new FlowLayout(0));
      classPanel.add(scrollListClass);
      classPanel.add(classPanelButtons);
      JPanel boxPanel = new JPanel();
      boxPanel.setLayout(new BoxLayout(boxPanel, 1));
      boxPanel.add(comboFilters);
      boxPanel.add(Box.createHorizontalStrut(5));
      boxPanel.add(filterPanelButtons);
      boxPanel.add(Box.createHorizontalStrut(5));
      boxPanel.add(classPanel);
      boxPanel.add(panelPngCsv);
      panelFilters.add(boxPanel);
      mainPanel.add(panelFilters);
      this.add(tabbedPaneSpot);
      this.createMovieTable();
      refreshButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.refreshThread = new Thread(new Runnable() {
               public void run() {
                  FirstWizardPanel.spotEnable = "spotEnable";
                  ProcessTrackMateXml.tracksVisible = false;
                  ProcessTrackMateXml.spotsVisible = true;
                  ProcessTrackMateXml ptx = new ProcessTrackMateXml();
                  ptx.processTrackMateXml();
               }
            });
            FirstWizardPanel.this.refreshThread.start();
         }
      });
      csvButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.csvThread = new Thread(new Runnable() {
               public void run() {
                  List<String> columnSpotHead = new ArrayList();

                  for(int j = 0; j < FirstWizardPanel.modelSpot.getColumnCount(); ++j) {
                     columnSpotHead.add(FirstWizardPanel.modelSpot.getColumnName(j));
                  }

                  ResultsTable rt = new ResultsTable(FirstWizardPanel.modelSpot.getRowCount());
                  if (rt != null) {
                     rt.reset();
                  }

                  for(int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                     for(int jx = 0; jx < FirstWizardPanel.modelSpot.getColumnCount(); ++jx) {
                        if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE) {
                           if (((String)columnSpotHead.get(jx)).equals(columnSpotHead.get(0)) == Boolean.TRUE) {
                              rt.setValue((String)columnSpotHead.get(jx), i, ((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, jx)).getText());
                           } else {
                              rt.setValue((String)columnSpotHead.get(jx), i, FirstWizardPanel.modelSpot.getValueAt(i, jx).toString());
                           }
                        }
                     }
                  }

                  JFrame pngFrame = new JFrame();
                  JFileChooser fileChooser = new JFileChooser();
                  fileChooser.setFileSelectionMode(1);
                  fileChooser.setDialogTitle("Specify a directory to save csv file");
                  int userSelection = fileChooser.showSaveDialog(pngFrame);
                  if (userSelection == 0) {
                     File fileToSave = fileChooser.getSelectedFile();

                     try {
                        rt.saveAs(fileToSave.getAbsolutePath() + File.separator + "SpotStatistics for-" + IJ.getImage().getShortTitle() + ".csv");
                     } catch (IOException var8) {
                        var8.printStackTrace();
                     }
                  }

               }
            });
            FirstWizardPanel.this.csvThread.start();
         }
      });
      pngButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.pngThread = new Thread(new Runnable() {
               public void run() {
                  if (IJ.getImage() == null) {
                     IJ.error("You must have an image window active.");
                  }

                  if (IJ.getImage() != null) {
                     JFrame pngFrame = new JFrame();
                     JFileChooser fileChooser = new JFileChooser();
                     fileChooser.setFileSelectionMode(1);
                     fileChooser.setDialogTitle("Specify a directory to save");
                     int userSelection = fileChooser.showSaveDialog(pngFrame);
                     if (userSelection == 0) {
                        File fileToSave = fileChooser.getSelectedFile();
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

                        Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
                        int width = bounds.width;
                        int height = bounds.height;
                        int nCaptures = lastFrame - firstFrame + 1;
                        ImageStack stack = new ImageStack(width, height);
                        int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
                        int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
                        ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);

                        for(int frame = firstFrame; frame <= lastFrame; ++frame) {
                           ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, frame);
                           BufferedImage bi = new BufferedImage(width, height, 2);
                           ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
                           ColorProcessor cp = new ColorProcessor(bi);
                           int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, frame);
                           stack.addSlice(ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), cp);
                        }

                        ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
                        ImagePlus capture = new ImagePlus("TrackMate capture of " + ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
                        FirstWizardPanel.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
                        IJ.saveAs(capture, "Tiff", fileToSave.getAbsolutePath() + File.separator + "Capture Overlay for " + IJ.getImage().getShortTitle());
                     }
                  }

               }
            });
            FirstWizardPanel.this.pngThread.start();
         }
      });
      paintButton.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent ev) {
            FirstWizardPanel.this.paintThread = new Thread(new Runnable() {
               public void run() {
                  if (ev.getStateChange() == 1) {
                     FirstWizardPanel.this.paintAndDisableAction();
                  } else if (ev.getStateChange() == 2) {
                     FirstWizardPanel.this.resetAndEnableAction();
                  }

               }
            });
            FirstWizardPanel.this.paintThread.start();
         }
      });
      tInsideButton.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent ev) {
            FirstWizardPanel.this.tInsideThread = new Thread(new Runnable() {
               public void run() {
                  if (ev.getStateChange() == 1) {
                     FirstWizardPanel.this.toggleInsideAction();
                  } else if (ev.getStateChange() == 2) {
                     FirstWizardPanel.this.resetToggleInsideAction();
                  }

               }
            });
            FirstWizardPanel.this.tInsideThread.start();
         }
      });
      tOutsideButton.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent ev) {
            FirstWizardPanel.this.tOutsideThread = new Thread(new Runnable() {
               public void run() {
                  if (ev.getStateChange() == 1) {
                     FirstWizardPanel.this.toggleOutsideAction();
                  } else if (ev.getStateChange() == 2) {
                     FirstWizardPanel.this.resetToggleOutsideAction();
                  }

               }
            });
            FirstWizardPanel.this.tOutsideThread.start();
         }
      });
      enableButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.enableThread = new Thread(new Runnable() {
               public void run() {
                  FirstWizardPanel.this.enableSpots();
               }
            });
            FirstWizardPanel.this.enableThread.start();
         }
      });
      disableButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.disableThread = new Thread(new Runnable() {
               public void run() {
                  FirstWizardPanel.this.disableSpots();
               }
            });
            FirstWizardPanel.this.disableThread.start();
         }
      });
      sliderMin.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FirstWizardPanel.this.slMinThread = new Thread(new Runnable() {
               public void run() {
                  FirstWizardPanel.this.filterMin.setValue(sliderMin.getValue());
                  intervalMarker.setStartValue((double)sliderMin.getValue());
               }
            });
            FirstWizardPanel.this.slMinThread.start();
         }
      });
      this.filterMin.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FirstWizardPanel.this.filterMinThread = new Thread(new Runnable() {
               public void run() {
                  sliderMin.setValue((Integer)FirstWizardPanel.this.filterMin.getValue());
                  intervalMarker.setStartValue((double)(Integer)FirstWizardPanel.this.filterMin.getValue());
               }
            });
            FirstWizardPanel.this.filterMinThread.start();
         }
      });
      sliderMax.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FirstWizardPanel.this.slMaxThread = new Thread(new Runnable() {
               public void run() {
                  FirstWizardPanel.this.filterMax.setValue(sliderMax.getValue());
                  intervalMarker.setEndValue((double)sliderMax.getValue());
               }
            });
            FirstWizardPanel.this.slMaxThread.start();
         }
      });
      this.filterMax.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            FirstWizardPanel.this.filterMaxThread = new Thread(new Runnable() {
               public void run() {
                  sliderMax.setValue((Integer)FirstWizardPanel.this.filterMax.getValue());
                  intervalMarker.setEndValue((double)(Integer)FirstWizardPanel.this.filterMax.getValue());
               }
            });
            FirstWizardPanel.this.filterMaxThread.start();
         }
      });
      comboFilters.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.filtersThread = new Thread(new Runnable() {
               public void run() {
                  String selectedName = (String)FirstWizardPanel.comboFilters.getSelectedItem();
                  int selectedIndex = FirstWizardPanel.comboFilters.getSelectedIndex();
                  double[] valuesx = null;
                  double[] values = new double[FirstWizardPanel.tableSpot.getRowCount()];

                  int i;
                  for(i = 0; i < FirstWizardPanel.tableSpot.getRowCount(); ++i) {
                     for(int c = 0; c < FirstWizardPanel.tableSpot.getColumnCount(); ++c) {
                        values[i] = Double.parseDouble((String)FirstWizardPanel.tableSpot.getValueAt(i, selectedIndex + 2));
                     }
                  }

                  double max = values[0];

                  for(i = 1; i < values.length; ++i) {
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
            });
            FirstWizardPanel.this.filtersThread.start();
         }
      });
      this.checkRPicker.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent e) {
            FirstWizardPanel.this.pickerThread = new Thread(new Runnable() {
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
            });
            FirstWizardPanel.this.pickerThread.start();
         }
      });
      classButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.classThread = new Thread(new Runnable() {
               public void run() {
                  ColorEditorSpot.myFrame.setVisible(true);
                  colorEditor.setClassAction();
               }
            });
            FirstWizardPanel.this.classThread.start();
         }
      });
      remClassButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.remClassThread = new Thread(new Runnable() {
               public void run() {
                  String classSelectedValue = (String)FirstWizardPanel.classList.getSelectedValue();
                  int[] classSelectedIndex = FirstWizardPanel.classList.getSelectedIndices();

                  int i;
                  for(i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                     if (((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getText().equals(classSelectedValue)) {
                        FirstWizardPanel.modelSpot.setValueAt(FirstWizardPanel.labelReset, i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
                     }
                  }

                  for(i = 0; i < classSelectedIndex.length; ++i) {
                     FirstWizardPanel.modelListClass.removeElementAt(classSelectedIndex[i]);
                  }

               }
            });
            FirstWizardPanel.this.remClassThread.start();
         }
      });
      addButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.addThread = new Thread(new Runnable() {
               public void run() {
                  List<String> listFilters = new ArrayList();
                  if (FirstWizardPanel.featureList.getModel().getSize() < 1) {
                     FirstWizardPanel.modelListFeature.addElement((String)FirstWizardPanel.comboFilters.getSelectedItem() + ":  [" + FirstWizardPanel.this.filterMin.getValue() + "," + FirstWizardPanel.this.filterMax.getValue() + "]");
                  }

                  if (FirstWizardPanel.featureList.getModel().getSize() >= 1) {
                     for(int i = 0; i < FirstWizardPanel.featureList.getModel().getSize(); ++i) {
                        listFilters.add(String.valueOf(((String)FirstWizardPanel.featureList.getModel().getElementAt(i)).substring(0, ((String)FirstWizardPanel.featureList.getModel().getElementAt(i)).lastIndexOf(":"))));
                     }

                     if (!listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
                        FirstWizardPanel.modelListFeature.addElement((String)FirstWizardPanel.comboFilters.getSelectedItem() + ":  [" + FirstWizardPanel.this.filterMin.getValue() + "," + FirstWizardPanel.this.filterMax.getValue() + "]");
                     }

                     if (listFilters.contains(FirstWizardPanel.comboFilters.getSelectedItem().toString())) {
                        return;
                     }
                  }

               }
            });
            FirstWizardPanel.this.addThread.start();
         }
      });
      remButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            FirstWizardPanel.this.remThread = new Thread(new Runnable() {
               public void run() {
                  try {
                     int[] indexes = FirstWizardPanel.featureList.getSelectedIndices();

                     for(int i = 0; i < indexes.length; ++i) {
                        FirstWizardPanel.modelListFeature.remove(indexes[i]);
                     }
                  } catch (Exception var3) {
                     var3.printStackTrace();
                  }

               }
            });
            FirstWizardPanel.this.remThread.start();
         }
      });
   }

   public void toggleOutsideAction() {
      Roi mainRoi = null;
      if (IJ.getImage().getRoi().getType() == 0) {
         mainRoi = IJ.getImage().getRoi();
      }

      this.indexesTO = new ArrayList();

      for(int i = 0; i < modelSpot.getRowCount(); ++i) {
         if (mainRoi.contains((int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(5)).toString())), (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(6)).toString()))) == Boolean.FALSE) {
            this.indexesTO.add(i);
            modelSpot.setValueAt(false, i, tableSpot.convertColumnIndexToModel(0));
            int spotID = Integer.parseInt((String)tableSpot.getValueAt(i, 2));
            Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
            if (spot != null) {
               spot.putFeature("VISIBILITY", SpotCollection.ZERO);
               ProcessTrackMateXml.model.endUpdate();
               ProcessTrackMateXml.displayer.refresh();
            }
         }
      }

   }

   public void resetToggleOutsideAction() {
      for(int row = 0; row < modelSpot.getRowCount(); ++row) {
         modelSpot.setValueAt(true, tableSpot.convertRowIndexToModel(row), tableSpot.convertColumnIndexToModel(0));
         int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
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

      this.indexesTI = new ArrayList();

      for(int i = 0; i < modelSpot.getRowCount(); ++i) {
         if (mainRoi.contains((int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(5)).toString())), (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(modelSpot.getValueAt(i, tableSpot.convertColumnIndexToModel(6)).toString()))) == Boolean.TRUE) {
            this.indexesTI.add(i);
            modelSpot.setValueAt(false, i, tableSpot.convertColumnIndexToModel(0));
            int spotID = Integer.parseInt((String)tableSpot.getValueAt(i, 2));
            Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
            if (spot != null) {
               spot.putFeature("VISIBILITY", SpotCollection.ZERO);
               ProcessTrackMateXml.model.endUpdate();
               ProcessTrackMateXml.displayer.refresh();
            }
         }
      }

   }

   public void resetToggleInsideAction() {
      for(int row = 0; row < modelSpot.getRowCount(); ++row) {
         modelSpot.setValueAt(true, tableSpot.convertRowIndexToModel(row), tableSpot.convertColumnIndexToModel(0));
         int spotID = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
         if (spot != null) {
            spot.putFeature("VISIBILITY", SpotCollection.ONE);
            ProcessTrackMateXml.model.endUpdate();
            ProcessTrackMateXml.displayer.refresh();
         }
      }

   }

   public void paintAndDisableAction() {
      this.indexesToReset = new ArrayList();
      this.spotID = new ArrayList();
      this.spots = new ArrayList();

      int row;
      for(row = 0; row < modelSpot.getRowCount(); ++row) {
         if (((JLabel)modelSpot.getValueAt(row, tableSpot.convertColumnIndexToModel(1))).getBackground().equals(new Color(214, 217, 223)) == Boolean.TRUE) {
            this.indexesToReset.add(row);
            modelSpot.setValueAt(false, row, tableSpot.convertColumnIndexToModel(0));
            this.spotID.add(Integer.parseInt((String)tableSpot.getValueAt(row, 2)));
         }
      }

      for(row = 0; row < this.indexesToReset.size(); ++row) {
         int spotID = Integer.parseInt((String)tableSpot.getValueAt((Integer)this.indexesToReset.get(row), 2));
         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
         if (spot != null) {
            spot.putFeature("VISIBILITY", SpotCollection.ZERO);
            ProcessTrackMateXml.model.endUpdate();
            ProcessTrackMateXml.displayer.refresh();
         }
      }

   }

   public void resetAndEnableAction() {
      int row;
      for(row = 0; row < this.indexesToReset.size(); ++row) {
         modelSpot.setValueAt(true, tableSpot.convertRowIndexToModel((Integer)this.indexesToReset.get(row)), tableSpot.convertColumnIndexToModel(0));
      }

      for(row = 0; row < this.indexesToReset.size(); ++row) {
         int spotID = Integer.parseInt((String)tableSpot.getValueAt((Integer)this.indexesToReset.get(row), 2));
         Spot spot = ProcessTrackMateXml.model.getSpots().search(spotID);
         if (spot != null) {
            spot.putFeature("VISIBILITY", SpotCollection.ONE);
            ProcessTrackMateXml.model.endUpdate();
            ProcessTrackMateXml.displayer.refresh();
         }
      }

   }

   public void enableSpots() {
      ListSelectionModel lsm = tableSpot.getSelectionModel();
      int[] selectedIndices = tableSpot.getSelectedRows();

      int selStart;
      for(selStart = 0; selStart < selectedIndices.length; ++selStart) {
         tableSpot.setValueAt(true, selectedIndices[selStart], 0);
      }

      selStart = lsm.getMinSelectionIndex();
      int selEnd = lsm.getMaxSelectionIndex();
      if (selStart >= 0 && selEnd >= 0) {
         int minLine = Math.min(selStart, selEnd);
         int maxLine = Math.max(selStart, selEnd);

         for(int row = minLine; row <= maxLine; ++row) {
            int spotIDEnable = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
            Spot spotEnable = ProcessTrackMateXml.model.getSpots().search(spotIDEnable);
            if (spotEnable != null) {
               spotEnable.putFeature("VISIBILITY", SpotCollection.ONE);
               ProcessTrackMateXml.model.endUpdate();
               ProcessTrackMateXml.displayer.refresh();
            }
         }

      }
   }

   public void disableSpots() {
      ListSelectionModel lsm = tableSpot.getSelectionModel();
      int selStart = lsm.getMinSelectionIndex();
      int selEnd = lsm.getMaxSelectionIndex();
      if (selStart >= 0 && selEnd >= 0) {
         int minLine = Math.min(selStart, selEnd);
         int maxLine = Math.max(selStart, selEnd);

         int i;
         for(int row = minLine; row <= maxLine; ++row) {
            i = Integer.parseInt((String)tableSpot.getValueAt(row, 2));
            Spot spot = ProcessTrackMateXml.model.getSpots().search(i);
            if (spot != null) {
               spot.putFeature("VISIBILITY", SpotCollection.ZERO);
               ProcessTrackMateXml.model.endUpdate();
               ProcessTrackMateXml.displayer.refresh();
            }
         }

         int[] selectedIndices = tableSpot.getSelectedRows();

         for(i = 0; i < selectedIndices.length; ++i) {
            tableSpot.setValueAt(false, selectedIndices[i], 0);
         }

      }
   }

   public static void createSpotTable() {
      modelSpot = new DefaultTableModel(ProcessTrackMateXml.dataSpot, ProcessTrackMateXml.columnHeadersSpot) {
         public Class<?> getColumnClass(int column) {
            if (this.getRowCount() > 0) {
               Object value = this.getValueAt(0, column);
               if (value != null) {
                  return this.getValueAt(0, column).getClass();
               }
            }

            return super.getColumnClass(column);
         }
      };
      modelSpot.addColumn("Enable");
      tableSpot.setModel(modelSpot);
      tableSpot.moveColumn(tableSpot.getColumnCount() - 1, 0);
      tableSpot.setSelectionBackground(new Color(229, 255, 204));
      tableSpot.setSelectionForeground(new Color(0, 102, 0));
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(0);
      tableSpot.setDefaultRenderer(String.class, centerRenderer);
      tableSpot.setAutoResizeMode(0);
      tableSpot.setRowHeight(45);
      tableSpot.setAutoCreateRowSorter(true);
      tableSpot.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());

      int i;
      for(i = 0; i < tableSpot.getColumnCount(); ++i) {
         tableSpot.getColumnModel().getColumn(i).setPreferredWidth(90);
      }

      for(i = 16; i < tableSpot.getColumnCount(); ++i) {
         tableSpot.getColumnModel().getColumn(i).setPreferredWidth(150);
      }

      for(i = 0; i < tableSpot.getRowCount(); ++i) {
         tableSpot.setValueAt(true, i, 0);
      }

      tableSpot.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
      labelReset = new JLabel();
      labelReset.setText("");
      labelReset.setOpaque(true);
      labelReset.setBackground(new Color(214, 217, 223));

      for(i = 0; i < modelSpot.getRowCount(); ++i) {
         modelSpot.setValueAt(labelReset, i, tableSpot.convertColumnIndexToModel(1));
      }

   }

   public void createMovieTable() {
      tableImages.setSelectionBackground(new Color(229, 255, 204));
      tableImages.setSelectionForeground(new Color(0, 102, 0));
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(0);
      tableImages.setDefaultRenderer(String.class, centerRenderer);
      tableImages.setAutoResizeMode(0);
      tableImages.setRowHeight(95);
      tableImages.setAutoCreateRowSorter(true);
      tableImages.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());

      for(int i = 0; i < modelImages.getRowCount(); ++i) {
         modelImages.setValueAt(this.icons[i], i, tableImages.convertColumnIndexToModel(0));
         modelImages.setValueAt(imps[i].getShortTitle(), i, tableImages.convertColumnIndexToModel(1));
         modelImages.setValueAt(imps[i].getTitle().substring(imps[i].getTitle().lastIndexOf(".")), i, tableImages.convertColumnIndexToModel(2));
      }

   }

   public static Image getScaledImage(Image srcImg, int w, int h) {
      BufferedImage resizedImg = new BufferedImage(w, h, 1);
      Graphics2D g2 = resizedImg.createGraphics();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g2.drawImage(srcImg, 0, 0, w, h, (ImageObserver)null);
      g2.dispose();
      return resizedImg;
   }

   public ImageIcon createImageIcon(String path) {
      URL img = this.getClass().getResource(path);
      if (img != null) {
         return new ImageIcon(img);
      } else {
         System.err.println("Couldn't find file: " + path);
         return null;
      }
   }

   public ImagePlus extractTFrame(ImagePlus imp, int frame) {
      int width = imp.getWidth();
      int height = imp.getHeight();
      int channels = imp.getNChannels();
      int zslices = imp.getNSlices();
      FileInfo fileInfo = imp.getOriginalFileInfo();
      ImageStack stack2 = new ImageStack(width, height);
      ImagePlus imp2 = new ImagePlus();
      imp2.setTitle("T" + frame + "-" + imp.getTitle());

      for(int z = 1; z <= zslices; ++z) {
         for(int c = 1; c <= channels; ++c) {
            int sliceSix = imp.getStackIndex(c, z, frame);
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

   private static final void transferCalibration(ImagePlus from, ImagePlus to) {
      Calibration fc = from.getCalibration();
      Calibration tc = to.getCalibration();
      tc.setUnit(fc.getUnit());
      tc.setTimeUnit(fc.getTimeUnit());
      tc.frameInterval = fc.frameInterval;
      double mag = from.getCanvas().getMagnification();
      tc.pixelWidth = fc.pixelWidth / mag;
      tc.pixelHeight = fc.pixelHeight / mag;
      tc.pixelDepth = fc.pixelDepth;
   }
}
