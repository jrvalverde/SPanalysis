import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Roi;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.process.ColorProcessor;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import jwizardcomponent.JWizardComponents;
import jwizardcomponent.JWizardPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.IntervalMarker;

public class ChooserWizardPanel extends JWizardPanel {
   JCheckBox checkRPicker;
   static JTable tableTrack;
   static JTable tableImages;
   static DefaultTableModel modelTrack;
   JScrollPane jScrollPaneTrack;
   JScrollPane jScrollPaneImages;
   JSpinner filterMin;
   JSpinner filterMax;
   HistogramFilterVersion hs2 = new HistogramFilterVersion();
   ChartPanel histogram;
   JComboBox<String> comboFilters;
   static DefaultListModel<String> modelListClass;
   static DefaultListModel<String> modelListFeature;
   static JList<String> classList;
   static JList<String> featureList;
   static JLabel labelReset;
   static String trackEnable = "";
   static String command;
   List<Integer> indexesToReset;
   List<Integer> indexesToReset1;
   List<Integer> tracksID;
   List<Integer> tracksID1;
   List<Integer> indexesTI;
   static Icon iconTrackCell;
   static Object[] columnNamesTrack;
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

   public ChooserWizardPanel(JWizardComponents wizardComponents) {
      super(wizardComponents, "");
      tableTrack = new JTable();
      tableImages = new JTable();
      tableImages.setModel(FirstWizardPanel.modelImages);
      tableImages.getColumnModel().getColumn(0).setPreferredWidth(90);
      tableImages.getColumnModel().getColumn(1).setPreferredWidth(460);
      tableImages.getColumnModel().getColumn(2).setPreferredWidth(80);
      modelTrack = new DefaultTableModel();
      columnNamesTrack = new Object[]{"Label", "TRACK_ID", "TRACK_INDEX", "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", "TOTAL_ABSOLUTE_ANGLE_XY", "TOTAL_ABSOLUTE_ANGLE_YZ", "TOTAL_ABSOLUTE_ANGLE_ZX"};
      tableTrack.setModel(modelTrack);
      tableTrack.setSelectionMode(1);
      this.jScrollPaneTrack = new JScrollPane(tableTrack);
      this.jScrollPaneTrack.setPreferredSize(new Dimension(590, 240));
      this.jScrollPaneTrack.setBorder(BorderFactory.createTitledBorder(""));
      this.jScrollPaneImages = new JScrollPane(tableImages);
      this.jScrollPaneImages.setPreferredSize(new Dimension(590, 240));
      this.jScrollPaneImages.setBorder(BorderFactory.createTitledBorder(""));
      JPanel mainPanel = new JPanel();
      mainPanel.setLayout(new BoxLayout(mainPanel, 1));
      JTabbedPane tabbedPaneTrack = new JTabbedPane(1);
      ImageIcon iconTrack = this.createImageIcon("/images/track.jpg");
      iconTrackCell = new ImageIcon(iconTrack.getImage().getScaledInstance(18, 20, 4));
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
      tabbedPaneTrack.addTab("TRACKS ", iconTrackCell, mainPanel, "Display Track Analysis");
      tabbedPaneTrack.setTabLayoutPolicy(1);
      JButton refreshButton = new JButton();
      ImageIcon iconRefresh = this.createImageIcon("/images/refresh.png");
      Icon refreshCell = new ImageIcon(iconRefresh.getImage().getScaledInstance(18, 20, 4));
      refreshButton.setIcon(refreshCell);
      refreshButton.setToolTipText("Click this button to get Track analysis");
      JToggleButton paintButton = new JToggleButton();
      ImageIcon iconPaint = this.createImageIcon("/images/paint.png");
      Icon paintCell = new ImageIcon(iconPaint.getImage().getScaledInstance(18, 20, 4));
      paintButton.setIcon(paintCell);
      paintButton.setToolTipText("Click this button to display labeled-Tracks");
      JToggleButton tInsideButton = new JToggleButton();
      ImageIcon iconTI = this.createImageIcon("/images/tinside.png");
      Icon TICell = new ImageIcon(iconTI.getImage().getScaledInstance(18, 20, 4));
      tInsideButton.setIcon(TICell);
      tInsideButton.setToolTipText("Click this button to toggle inside Tracks.");
      JToggleButton tOutsideButton = new JToggleButton();
      ImageIcon iconTO = this.createImageIcon("/images/toutside.png");
      Icon TOCell = new ImageIcon(iconTO.getImage().getScaledInstance(18, 20, 4));
      tOutsideButton.setIcon(TOCell);
      tOutsideButton.setToolTipText("Click this button to toggle outside Tracks.");
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
      this.checkRPicker = new JCheckBox(" Track Picker");
      JLabel filterLabel = new JLabel("  ➠ Track Analysis : ");
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
      mainPanel.add(this.jScrollPaneImages);
      mainPanel.add(Box.createVerticalStrut(5));
      mainPanel.add(filterMain);
      mainPanel.add(this.jScrollPaneTrack);
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
      JLabel featureTrack = new JLabel(" » Track-Features :  ");
      featureTrack.setFont(new Font("Dialog", 1, 13));
      this.comboFilters = new JComboBox();

      for(int i = 1; i < columnNamesTrack.length; ++i) {
         this.comboFilters.addItem((String)columnNamesTrack[i]);
      }

      this.comboFilters.setPreferredSize(new Dimension(130, 25));
      this.comboFilters.setSelectedIndex(0);
      this.comboFilters.setOpaque(true);
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
      final ColorEditorTrack colorEditor = new ColorEditorTrack(featureList);
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
      boxPanel.add(this.comboFilters);
      boxPanel.add(Box.createHorizontalStrut(5));
      boxPanel.add(filterPanelButtons);
      boxPanel.add(Box.createHorizontalStrut(5));
      boxPanel.add(classPanel);
      boxPanel.add(panelPngCsv);
      panelFilters.add(boxPanel);
      mainPanel.add(panelFilters);
      this.add(tabbedPaneTrack);
      this.createMovieTable();
      paintButton.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent ev) {
            ChooserWizardPanel.this.paintThread = new Thread(new Runnable() {
               public void run() {
                  if (ev.getStateChange() == 1) {
                     ChooserWizardPanel.this.paintAndDisableAction();
                  } else if (ev.getStateChange() == 2) {
                     ChooserWizardPanel.this.resetAndEnableAction();
                  }

               }
            });
            ChooserWizardPanel.this.paintThread.start();
         }
      });
      csvButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.csvThread = new Thread(new Runnable() {
               public void run() {
                  List<String> columnTrackHead = new ArrayList();

                  for(int j = 0; j < ChooserWizardPanel.modelTrack.getColumnCount(); ++j) {
                     columnTrackHead.add(ChooserWizardPanel.modelTrack.getColumnName(j));
                  }

                  ResultsTable rt = new ResultsTable(ChooserWizardPanel.modelTrack.getRowCount());
                  if (rt != null) {
                     rt.reset();
                  }

                  for(int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                     for(int jx = 0; jx < ChooserWizardPanel.modelTrack.getColumnCount(); ++jx) {
                        if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE) {
                           if (((String)columnTrackHead.get(jx)).equals(columnTrackHead.get(0)) == Boolean.TRUE) {
                              rt.setValue((String)columnTrackHead.get(jx), i, ((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, jx)).getText());
                           } else {
                              rt.setValue((String)columnTrackHead.get(jx), i, ChooserWizardPanel.modelTrack.getValueAt(i, jx).toString());
                           }
                        }
                     }
                  }

                  rt.show("Resutls tracks");
               }
            });
            ChooserWizardPanel.this.csvThread.start();
         }
      });
      pngButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.pngThread = new Thread(new Runnable() {
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
                        ChooserWizardPanel.transferCalibration(ProcessTrackMateXml.displayer.getImp(), capture);
                        IJ.saveAs(capture, "Tiff", fileToSave.getAbsolutePath() + File.separator + "Capture Overlay for " + IJ.getImage().getShortTitle());
                     }
                  }

               }
            });
            ChooserWizardPanel.this.pngThread.start();
         }
      });
      refreshButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.refreshThread = new Thread(new Runnable() {
               public void run() {
                  ChooserWizardPanel.trackEnable = "trackEnable";
                  ProcessTrackMateXml.tracksVisible = true;
                  ProcessTrackMateXml.spotsVisible = false;
                  ProcessTrackMateXml ptx = new ProcessTrackMateXml();
                  ptx.processTrackMateXml();
               }
            });
            ChooserWizardPanel.this.refreshThread.start();
         }
      });
      enableButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.enableThread = new Thread(new Runnable() {
               public void run() {
                  ChooserWizardPanel.this.enableTracks();
               }
            });
            ChooserWizardPanel.this.enableThread.start();
         }
      });
      disableButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.disableThread = new Thread(new Runnable() {
               public void run() {
                  ChooserWizardPanel.this.disableTracks();
               }
            });
            ChooserWizardPanel.this.disableThread.start();
         }
      });
      this.checkRPicker.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent e) {
            ChooserWizardPanel.this.pickerThread = new Thread(new Runnable() {
               public void run() {
                  if (e.getStateChange() == 1) {
                     ChooserWizardPanel.command = "enable";
                  }

                  if (e.getStateChange() == 2) {
                     ChooserWizardPanel.command = null;
                     ProcessTrackMateXml.selectionModel.clearSpotSelection();
                     ProcessTrackMateXml.selectionModel.clearSelection();
                  }
               }
            });
            ChooserWizardPanel.this.pickerThread.start();
         }
      });
      sliderMin.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            ChooserWizardPanel.this.slMinThread = new Thread(new Runnable() {
               public void run() {
                  ChooserWizardPanel.this.filterMin.setValue(sliderMin.getValue());
                  intervalMarker.setStartValue((double)sliderMin.getValue());
               }
            });
            ChooserWizardPanel.this.slMinThread.start();
         }
      });
      this.filterMin.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            ChooserWizardPanel.this.filterMinThread = new Thread(new Runnable() {
               public void run() {
                  sliderMin.setValue((Integer)ChooserWizardPanel.this.filterMin.getValue());
                  intervalMarker.setStartValue((double)(Integer)ChooserWizardPanel.this.filterMin.getValue());
               }
            });
            ChooserWizardPanel.this.filterMinThread.start();
         }
      });
      sliderMax.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            ChooserWizardPanel.this.slMaxThread = new Thread(new Runnable() {
               public void run() {
                  ChooserWizardPanel.this.filterMax.setValue(sliderMax.getValue());
                  intervalMarker.setEndValue((double)sliderMax.getValue());
               }
            });
            ChooserWizardPanel.this.slMaxThread.start();
         }
      });
      this.filterMax.addChangeListener(new ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            ChooserWizardPanel.this.filterMaxThread = new Thread(new Runnable() {
               public void run() {
                  sliderMax.setValue((Integer)ChooserWizardPanel.this.filterMax.getValue());
                  intervalMarker.setEndValue((double)(Integer)ChooserWizardPanel.this.filterMax.getValue());
               }
            });
            ChooserWizardPanel.this.filterMaxThread.start();
         }
      });
      tInsideButton.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent ev) {
            ChooserWizardPanel.this.tInsideThread = new Thread(new Runnable() {
               public void run() {
                  if (ev.getStateChange() == 1) {
                     ChooserWizardPanel.this.toggleInsideAction();
                  } else if (ev.getStateChange() == 2) {
                     ChooserWizardPanel.this.resetToggleInsideAction();
                  }

               }
            });
            ChooserWizardPanel.this.tInsideThread.start();
         }
      });
      this.comboFilters.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.filtersThread = new Thread(new Runnable() {
               public void run() {
                  String selectedName = (String)ChooserWizardPanel.this.comboFilters.getSelectedItem();
                  int selectedIndex = ChooserWizardPanel.this.comboFilters.getSelectedIndex();
                  double[] valuesx = null;
                  double[] values = new double[ChooserWizardPanel.tableTrack.getRowCount()];

                  int i;
                  for(i = 0; i < ChooserWizardPanel.tableTrack.getRowCount(); ++i) {
                     for(int c = 0; c < ChooserWizardPanel.tableTrack.getColumnCount(); ++c) {
                        values[i] = Double.parseDouble((String)ChooserWizardPanel.tableTrack.getValueAt(i, selectedIndex + 2));
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
                  ChooserWizardPanel.this.hs2.addHistogramSeries(selectedName, values, (int)max, intervalMarker);
               }
            });
            ChooserWizardPanel.this.filtersThread.start();
         }
      });
      classButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.classThread = new Thread(new Runnable() {
               public void run() {
                  ColorEditorTrack.myFrame.setVisible(true);
                  colorEditor.setClassAction();
               }
            });
            ChooserWizardPanel.this.classThread.start();
         }
      });
      remClassButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.remClassThread = new Thread(new Runnable() {
               public void run() {
                  String classSelectedValue = (String)ChooserWizardPanel.classList.getSelectedValue();
                  int[] classSelectedIndex = ChooserWizardPanel.classList.getSelectedIndices();

                  int i;
                  for(i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                     if (((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1))).getText().equals(classSelectedValue)) {
                        ChooserWizardPanel.modelTrack.setValueAt(ChooserWizardPanel.labelReset, i, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1));
                     }
                  }

                  for(i = 0; i < classSelectedIndex.length; ++i) {
                     ChooserWizardPanel.modelListClass.removeElementAt(classSelectedIndex[i]);
                  }

               }
            });
            ChooserWizardPanel.this.remClassThread.start();
         }
      });
      addButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.addThread = new Thread(new Runnable() {
               public void run() {
                  List<String> listFilters = new ArrayList();
                  if (ChooserWizardPanel.featureList.getModel().getSize() < 1) {
                     ChooserWizardPanel.modelListFeature.addElement((String)ChooserWizardPanel.this.comboFilters.getSelectedItem() + ":  [" + ChooserWizardPanel.this.filterMin.getValue() + "," + ChooserWizardPanel.this.filterMax.getValue() + "]");
                  }

                  if (ChooserWizardPanel.featureList.getModel().getSize() >= 1) {
                     for(int i = 0; i < ChooserWizardPanel.featureList.getModel().getSize(); ++i) {
                        listFilters.add(String.valueOf(((String)ChooserWizardPanel.featureList.getModel().getElementAt(i)).substring(0, ((String)ChooserWizardPanel.featureList.getModel().getElementAt(i)).lastIndexOf(":"))));
                     }

                     if (!listFilters.contains(ChooserWizardPanel.this.comboFilters.getSelectedItem().toString())) {
                        ChooserWizardPanel.modelListFeature.addElement((String)ChooserWizardPanel.this.comboFilters.getSelectedItem() + ":  [" + ChooserWizardPanel.this.filterMin.getValue() + "," + ChooserWizardPanel.this.filterMax.getValue() + "]");
                     }

                     if (listFilters.contains(ChooserWizardPanel.this.comboFilters.getSelectedItem().toString())) {
                        return;
                     }
                  }

               }
            });
            ChooserWizardPanel.this.addThread.start();
         }
      });
      remButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ChooserWizardPanel.this.remThread = new Thread(new Runnable() {
               public void run() {
                  try {
                     int[] indexes = ChooserWizardPanel.featureList.getSelectedIndices();

                     for(int i = 0; i < indexes.length; ++i) {
                        ChooserWizardPanel.modelListFeature.remove(indexes[i]);
                     }
                  } catch (Exception var3) {
                     var3.printStackTrace();
                  }

               }
            });
            ChooserWizardPanel.this.remThread.start();
         }
      });
      tOutsideButton.addItemListener(new ItemListener() {
         public void itemStateChanged(final ItemEvent ev) {
            ChooserWizardPanel.this.tOutsideThread = new Thread(new Runnable() {
               public void run() {
                  if (ev.getStateChange() == 1) {
                     ChooserWizardPanel.this.toggleOutsideAction();
                  } else if (ev.getStateChange() == 2) {
                     ChooserWizardPanel.this.resetToggleInsideAction();
                  }

               }
            });
            ChooserWizardPanel.this.tOutsideThread.start();
         }
      });
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

   public void toggleOutsideAction() {
      Roi mainRoi = null;
      if (IJ.getImage().getRoi().getType() == 0) {
         mainRoi = IJ.getImage().getRoi();
      }

      this.indexesTI = new ArrayList();

      for(int i = 0; i < modelTrack.getRowCount(); ++i) {
         if (mainRoi.contains((int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(13)).toString())), (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(14)).toString()))) == Boolean.FALSE) {
            this.indexesTI.add(i);
            modelTrack.setValueAt(false, i, tableTrack.convertColumnIndexToModel(0));
            int trackID = Integer.parseInt((String)tableTrack.getValueAt(i, 2));
            ProcessTrackMateXml.model.beginUpdate();

            try {
               ProcessTrackMateXml.model.setTrackVisibility(trackID, false);
            } finally {
               ProcessTrackMateXml.model.endUpdate();
            }

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

      for(int i = 0; i < modelTrack.getRowCount(); ++i) {
         if (mainRoi.contains((int)IJ.getImage().getCalibration().getRawX(Double.parseDouble(modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(13)).toString())), (int)IJ.getImage().getCalibration().getRawY(Double.parseDouble(modelTrack.getValueAt(i, tableTrack.convertColumnIndexToModel(14)).toString()))) == Boolean.TRUE) {
            this.indexesTI.add(i);
            modelTrack.setValueAt(false, i, tableTrack.convertColumnIndexToModel(0));
            int trackID = Integer.parseInt((String)tableTrack.getValueAt(i, 2));
            ProcessTrackMateXml.model.beginUpdate();

            try {
               ProcessTrackMateXml.model.setTrackVisibility(trackID, false);
            } finally {
               ProcessTrackMateXml.model.endUpdate();
            }

            ProcessTrackMateXml.displayer.refresh();
         }
      }

   }

   public void resetToggleInsideAction() {
      for(int row = 0; row < modelTrack.getRowCount(); ++row) {
         modelTrack.setValueAt(true, tableTrack.convertRowIndexToModel(row), tableTrack.convertColumnIndexToModel(0));
         int trackID = Integer.parseInt((String)tableTrack.getValueAt(row, 2));
         ProcessTrackMateXml.model.beginUpdate();

         try {
            ProcessTrackMateXml.model.setTrackVisibility(trackID, true);
         } finally {
            ProcessTrackMateXml.model.endUpdate();
         }

         ProcessTrackMateXml.displayer.refresh();
      }

   }

   public static void createTrackTable() {
      modelTrack = new DefaultTableModel(ProcessTrackMateXml.dataTrack, ProcessTrackMateXml.columnHeadersTrack) {
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
      modelTrack.addColumn("Enable");
      tableTrack.setModel(modelTrack);
      tableTrack.moveColumn(tableTrack.getColumnCount() - 1, 0);
      tableTrack.setSelectionBackground(new Color(229, 255, 204));
      tableTrack.setSelectionForeground(new Color(0, 102, 0));
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(0);
      tableTrack.setDefaultRenderer(String.class, centerRenderer);
      tableTrack.setAutoResizeMode(0);
      tableTrack.setRowHeight(45);
      tableTrack.setAutoCreateRowSorter(true);
      tableTrack.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());

      int i;
      for(i = 0; i < tableTrack.getColumnCount(); ++i) {
         tableTrack.getColumnModel().getColumn(i).setPreferredWidth(90);
      }

      for(i = 3; i < tableTrack.getColumnCount(); ++i) {
         tableTrack.getColumnModel().getColumn(i).setPreferredWidth(130);
      }

      for(i = 0; i < tableTrack.getRowCount(); ++i) {
         tableTrack.setValueAt(true, i, 0);
      }

      tableTrack.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
      labelReset = new JLabel();
      labelReset.setText("");
      labelReset.setOpaque(true);
      labelReset.setBackground(new Color(214, 217, 223));

      for(i = 0; i < modelTrack.getRowCount(); ++i) {
         modelTrack.setValueAt(labelReset, i, tableTrack.convertColumnIndexToModel(1));
      }

   }

   public void enableTracks() {
      this.indexesToReset1 = new ArrayList();
      this.tracksID1 = new ArrayList();
      int[] selectedRows = tableTrack.getSelectedRows();

      int row;
      for(row = 0; row < selectedRows.length; ++row) {
         this.indexesToReset1.add(selectedRows[row]);
         modelTrack.setValueAt(true, selectedRows[row], tableTrack.convertColumnIndexToModel(0));
         this.tracksID1.add(Integer.parseInt((String)tableTrack.getValueAt(selectedRows[row], 2)));
      }

      for(row = 0; row < this.tracksID1.size(); ++row) {
         ProcessTrackMateXml.model.beginUpdate();

         try {
            ProcessTrackMateXml.model.setTrackVisibility((Integer)this.tracksID1.get(row), true);
         } finally {
            ProcessTrackMateXml.model.endUpdate();
         }

         ProcessTrackMateXml.displayer.refresh();
      }

   }

   public void disableTracks() {
      this.indexesToReset1 = new ArrayList();
      this.tracksID1 = new ArrayList();
      int[] selectedRows = tableTrack.getSelectedRows();

      int row;
      for(row = 0; row < selectedRows.length; ++row) {
         this.indexesToReset1.add(selectedRows[row]);
         modelTrack.setValueAt(false, selectedRows[row], tableTrack.convertColumnIndexToModel(0));
         this.tracksID1.add(Integer.parseInt((String)tableTrack.getValueAt(selectedRows[row], 2)));
      }

      for(row = 0; row < this.tracksID1.size(); ++row) {
         ProcessTrackMateXml.model.beginUpdate();

         try {
            ProcessTrackMateXml.model.setTrackVisibility((Integer)this.tracksID1.get(row), false);
         } finally {
            ProcessTrackMateXml.model.endUpdate();
         }

         ProcessTrackMateXml.displayer.refresh();
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
      tableImages.setModel(FirstWizardPanel.modelImages);
   }

   public void paintAndDisableAction() {
      this.indexesToReset = new ArrayList();
      this.tracksID = new ArrayList();

      int row;
      for(row = 0; row < modelTrack.getRowCount(); ++row) {
         if (((JLabel)modelTrack.getValueAt(row, tableTrack.convertColumnIndexToModel(1))).getBackground().equals(new Color(214, 217, 223)) == Boolean.TRUE) {
            this.indexesToReset.add(row);
            modelTrack.setValueAt(false, row, tableTrack.convertColumnIndexToModel(0));
            this.tracksID.add(Integer.parseInt((String)tableTrack.getValueAt(row, 2)));
         }
      }

      for(row = 0; row < this.tracksID.size(); ++row) {
         ProcessTrackMateXml.model.beginUpdate();

         try {
            ProcessTrackMateXml.model.setTrackVisibility((Integer)this.tracksID.get(row), false);
         } finally {
            ProcessTrackMateXml.model.endUpdate();
         }

         ProcessTrackMateXml.displayer.refresh();
      }

   }

   public void resetAndEnableAction() {
      int row;
      for(row = 0; row < this.indexesToReset.size(); ++row) {
         modelTrack.setValueAt(true, tableTrack.convertRowIndexToModel((Integer)this.indexesToReset.get(row)), tableTrack.convertColumnIndexToModel(0));
      }

      for(row = 0; row < this.tracksID.size(); ++row) {
         ProcessTrackMateXml.model.beginUpdate();

         try {
            ProcessTrackMateXml.model.setTrackVisibility((Integer)this.tracksID.get(row), true);
         } finally {
            ProcessTrackMateXml.model.endUpdate();
         }
      }

      ProcessTrackMateXml.displayer.refresh();
   }

   public void update() {
      this.setNextButtonEnabled(true);
      this.setFinishButtonEnabled(true);
      this.setBackButtonEnabled(true);
   }

   public void next() {
      this.switchPanel(2);
   }

   public void back() {
      this.switchPanel(0);
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
