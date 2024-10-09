import ij.IJ;
import ij.measure.ResultsTable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import jwizardcomponent.JWizardComponents;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.IntervalMarker;

public class OptionWizardPanel extends LabelWizardPanel {
   public double maxDomainSpot;
   public double maxRangeSpot;
   public double maxDomainTrack;
   public double maxRangeTrack;
   public IntervalMarker markerRangeSpot;
   public IntervalMarker markerDomainSpot;
   public IntervalMarker markerRangeTrack;
   public IntervalMarker markerDomainTrack;
   private JSpinner filterOrderSpot;
   private JSpinner filterOrderTrack;
   static int selectedIndexCh2;
   static int selectedIndexCh3;
   static int numCh2Positive;
   static int numCh3Positive;
   static int countSenescentNumber;
   static int lhCountAll;
   static int hhCountAll;
   static int llCountAll;
   static int hlCountAll;
   static int lhCountNID;
   static int hhCountNID;
   static int llCountNID;
   static int hlCountNID;
   static int lhCountClass;
   static int hhCountClass;
   static int llCountClass;
   static int hlCountClass;
   static int selectedIndexDomainSpot;
   static int selectedIndexRangeSpot;
   static int selectedIndexDomainTrack;
   static int selectedIndexRangeTrack;
   static JLabel scatLabel;
   static JLabel sumLabel;
   static JLabel labelScoresSpot;
   static JLabel labelScoresTrack;
   JComboBox<String> comboFeatureDomainSpot;
   JComboBox<String> comboFeatureRangeSpot;
   JComboBox<String> comboFeatureDomainTrack;
   JComboBox<String> comboFeatureRangeTrack;
   JComboBox<String> comboClassSpot;
   JComboBox<String> comboParamSpot;
   JComboBox<String> comboClassTrack;
   JComboBox<String> comboParamTrack;
   JComboBox<String> comboRegressionSpot;
   JComboBox<String> comboRegressionTrack;
   List<String> itemFiltersSpot;
   List<String> itemFiltersTrack;
   STScatterPlot scatterPlot;
   JButton refreshButtonSpot;
   JButton zoomInSpot;
   JButton zoomOutSpot;
   JButton refreshButtonTrack;
   JButton zoomInTrack;
   JButton zoomOutTrack;
   STScatterPlot sp2Spot;
   STScatterPlot sp2Track;
   ChartPanel scatterPlotSpot;
   ChartPanel scatterPlotTrack;
   static JPanel regressionPanelSpot;
   static JPanel regressionPanelTrack;
   List<Double> dataToStatisticsSpot;
   List<Double> dataToStatisticsTrack;
   DefaultTableModel modelSpot;
   DefaultTableModel modelTrack;
   Thread refreshSpotThread;
   Thread refreshTrackThread;
   Thread zoomInSpotThread;
   Thread zoomInTrackThread;
   Thread zoomOutSpotThread;
   Thread zoomOutTrackThread;
   Thread comboRegSpotThread;
   Thread comboRegTrackThread;
   Thread comboClassSpotThread;
   Thread comboClassTrackThread;

   public OptionWizardPanel(JWizardComponents wizardComponents, String option) {
      super(wizardComponents, "");
      this.setPanelTitle("");
      this.setLayout(new BoxLayout(this, 1));
      JPanel chartPanel2Spot = new JPanel();
      this.markerRangeSpot = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), new BasicStroke(1.5F), 0.6F);
      this.markerDomainSpot = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), new BasicStroke(1.5F), 0.5F);
      this.markerRangeTrack = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), new BasicStroke(1.5F), 0.6F);
      this.markerDomainTrack = new IntervalMarker(0.0D, 0.0D, new Color(229, 255, 204), new BasicStroke(), new Color(0, 102, 0), new BasicStroke(1.5F), 0.5F);
      this.sp2Spot = new STScatterPlot("");
      this.scatterPlotSpot = this.sp2Spot.createScatterChartPanelInitial("", "", new ArrayList(Arrays.asList(0.0D, 0.0D, 0.0D)), new ArrayList(Arrays.asList(0.0D, 0.0D, 0.0D)), this.markerRangeSpot, this.markerDomainSpot, new Double[][]{{0.0D}, {0.0D}}, new Double[][]{{0.0D}, {0.0D}});
      this.refreshButtonSpot = new JButton("");
      this.refreshButtonSpot.setIcon(FirstWizardPanel.refreshCell);
      this.refreshButtonSpot.setToolTipText("Click this button to refresh scatter-plot.");
      this.zoomInSpot = new JButton("");
      ImageIcon iconZoomIn = FirstWizardPanel.createImageIcon("images/zoomin.png");
      Icon zoomInCell = new ImageIcon(iconZoomIn.getImage().getScaledInstance(18, 20, 4));
      this.zoomInSpot.setIcon(zoomInCell);
      this.zoomInSpot.setToolTipText("Click this button to zoom in Chart");
      this.zoomOutSpot = new JButton("");
      ImageIcon iconZoomOut = FirstWizardPanel.createImageIcon("images/zoomout.png");
      Icon zoomOutCell = new ImageIcon(iconZoomOut.getImage().getScaledInstance(18, 20, 4));
      this.zoomOutSpot.setIcon(zoomOutCell);
      this.zoomOutSpot.setToolTipText("Click this button to zoom out Chart");
      this.itemFiltersSpot = new ArrayList();
      this.itemFiltersTrack = new ArrayList();

      int i;
      for(i = 2; i < FirstWizardPanel.columnNamesSpot.length; ++i) {
         this.itemFiltersSpot.add(FirstWizardPanel.columnNamesSpot[i].toString());
      }

      for(i = 0; i < ChooserWizardPanel.columnNamesTrack.length; ++i) {
         this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[i].toString());
      }

      this.comboFeatureDomainSpot = new JComboBox();
      this.comboFeatureDomainSpot.setPreferredSize(new Dimension(110, 20));

      for(i = 0; i < this.itemFiltersSpot.size(); ++i) {
         this.comboFeatureDomainSpot.addItem((String)this.itemFiltersSpot.get(i));
      }

      this.comboFeatureDomainSpot.setOpaque(true);
      this.scatterPlot = new STScatterPlot("holaa");
      chartPanel2Spot.add(this.scatterPlotSpot);
      this.filterOrderSpot = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
      this.filterOrderSpot.setPreferredSize(new Dimension(60, 20));
      this.filterOrderSpot.setEnabled(false);
      JPanel filtersMaxSpot = new JPanel(new FlowLayout(1));
      filtersMaxSpot.add(Box.createHorizontalStrut(2));
      this.comboFeatureRangeSpot = new JComboBox();
      this.comboFeatureRangeSpot.setPreferredSize(new Dimension(110, 20));

      for(int i = 0; i < this.itemFiltersSpot.size(); ++i) {
         this.comboFeatureRangeSpot.addItem((String)this.itemFiltersSpot.get(i));
      }

      this.comboFeatureRangeSpot.setOpaque(true);
      filtersMaxSpot.add(new JLabel("X :  "));
      filtersMaxSpot.add(this.comboFeatureDomainSpot);
      filtersMaxSpot.add(new JLabel("   Y :  "));
      filtersMaxSpot.add(this.comboFeatureRangeSpot);
      JPanel rangePanelFSpot = new JPanel(new FlowLayout(0));
      JPanel chartDomainPanelBoxSpot = new JPanel();
      chartDomainPanelBoxSpot.setLayout(new BoxLayout(chartDomainPanelBoxSpot, 1));
      chartDomainPanelBoxSpot.add(Box.createVerticalStrut(10));
      chartDomainPanelBoxSpot.add(chartPanel2Spot);
      chartDomainPanelBoxSpot.add(filtersMaxSpot);
      rangePanelFSpot.add(chartDomainPanelBoxSpot);
      JPanel buttonBox = new JPanel();
      buttonBox.setLayout(new BoxLayout(buttonBox, 1));
      JPanel refreshButtonPanelSpot = new JPanel(new FlowLayout(0));
      refreshButtonPanelSpot.add(this.refreshButtonSpot);
      JPanel zoomOutButtonPanelSpot = new JPanel(new FlowLayout(0));
      zoomOutButtonPanelSpot.add(this.zoomOutSpot);
      JPanel zoomInButtonPanelSpot = new JPanel(new FlowLayout(0));
      zoomInButtonPanelSpot.add(this.zoomInSpot);
      buttonBox.add(zoomInButtonPanelSpot);
      buttonBox.add(zoomOutButtonPanelSpot);
      buttonBox.add(refreshButtonPanelSpot);
      this.comboRegressionSpot = new JComboBox();
      this.comboRegressionSpot.setPreferredSize(new Dimension(90, 20));
      this.comboRegressionSpot.addItem("Linear");
      this.comboRegressionSpot.addItem("Polynomial");
      this.comboRegressionSpot.addItem("Power");
      this.comboRegressionSpot.addItem("Logarithmic");
      this.comboRegressionSpot.addItem("Exponential");
      this.comboRegressionSpot.setSelectedIndex(0);
      this.comboRegressionSpot.setOpaque(true);
      JPanel regreOrderPanel = new JPanel(new FlowLayout(0));
      regreOrderPanel.add(this.comboRegressionSpot);
      JPanel filterOrderPanel = new JPanel(new FlowLayout(0));
      filterOrderPanel.add(this.filterOrderSpot);
      buttonBox.add(regreOrderPanel);
      buttonBox.add(filterOrderPanel);
      regressionPanelSpot = new JPanel();
      regressionPanelSpot.setBorder(BorderFactory.createTitledBorder("Reg.Params"));
      regressionPanelSpot.setPreferredSize(new Dimension(this.comboRegressionSpot.getWidth() + 10, 35));
      rangePanelFSpot.add(buttonBox);
      JPanel spotPanel = new JPanel();
      spotPanel.add(rangePanelFSpot);
      spotPanel.setBorder(BorderFactory.createTitledBorder(""));
      JPanel spotStatistics = new JPanel();
      spotStatistics.setLayout(new BoxLayout(spotStatistics, 1));
      JPanel spotStatisticsFlow = new JPanel(new FlowLayout(0));
      JPanel classParamSpot = new JPanel(new FlowLayout(0));
      this.comboClassSpot = new JComboBox();
      this.comboClassSpot.setPreferredSize(new Dimension(120, 20));

      int i;
      for(i = 2; i < FirstWizardPanel.columnNamesSpot.length; ++i) {
         this.comboClassSpot.addItem((String)FirstWizardPanel.columnNamesSpot[i]);
      }

      this.comboClassSpot.setOpaque(true);
      this.comboClassSpot.setToolTipText("Choose selected items for descriptive statistics.");
      this.comboParamSpot = new JComboBox();
      this.comboParamSpot.setPreferredSize(new Dimension(120, 20));

      for(i = 2; i < FirstWizardPanel.columnNamesSpot.length; ++i) {
         this.comboParamSpot.addItem((String)FirstWizardPanel.columnNamesSpot[i]);
      }

      this.comboParamSpot.setOpaque(true);
      this.comboParamSpot.setToolTipText("Choose a spot parameter for descriptive statistics.");
      classParamSpot.add(this.comboClassSpot);
      classParamSpot.add(this.comboParamSpot);
      labelScoresSpot = new JLabel("SCORES");
      labelScoresSpot.setFont(new Font("Verdana", 1, 20));
      spotStatisticsFlow.add(labelScoresSpot);
      spotStatistics.add(Box.createVerticalStrut(60));
      spotStatistics.add(spotStatisticsFlow);
      spotStatistics.add(new JSeparator(0));
      spotStatistics.add(Box.createVerticalStrut(20));
      spotStatistics.add(classParamSpot);
      spotStatistics.add(Box.createVerticalStrut(20));
      spotStatistics.add(new JSeparator(0));
      String[][] data = new String[][]{{"", "", "", "", "", "", "", "", "", "", ""}};
      String[] column = new String[]{"Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", "Min ", "Max ", "Sum ", "Count "};
      JTable table = new JTable();
      this.modelSpot = new DefaultTableModel(data, column) {
         public Class<?> getColumnClass(int column) {
            if (this.getRowCount() > 0) {
               Object value = this.getValueAt(0, column);
               if (value != null) {
                  return this.getValueAt(0, column).getClass();
               }
            }

            return super.getColumnClass(column);
         }

         public boolean isCellEditable(int row, int col) {
            return false;
         }
      };
      table.setAutoResizeMode(0);
      table.setModel(this.modelSpot);
      table.setRowHeight(60);
      JScrollPane sp = new JScrollPane(table);
      sp.setPreferredSize(new Dimension(500, 100));
      sp.setVerticalScrollBarPolicy(22);
      sp.setHorizontalScrollBarPolicy(32);

      for(int u = 0; u < table.getColumnCount(); ++u) {
         table.getColumnModel().getColumn(u).setMinWidth(100);
         table.getColumnModel().getColumn(u).setMaxWidth(100);
         table.getColumnModel().getColumn(u).setPreferredWidth(100);
      }

      spotStatistics.add(Box.createVerticalStrut(20));
      spotStatistics.add(sp);
      spotStatistics.add(Box.createVerticalStrut(20));
      spotStatistics.add(new JSeparator(0));
      JButton plotButton = new JButton();
      ImageIcon iconPlot = FirstWizardPanel.createImageIcon("images/plot.jpg");
      Icon plotCell = new ImageIcon(iconPlot.getImage().getScaledInstance(18, 20, 4));
      plotButton.setIcon(plotCell);
      plotButton.setToolTipText("Click to export scatter plot.");
      JPanel panelPlot = new JPanel(new FlowLayout(0));
      panelPlot.add(plotButton);
      JButton csvButton = new JButton();
      ImageIcon iconCsv = FirstWizardPanel.createImageIcon("images/csv.png");
      Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
      csvButton.setIcon(csvCell);
      csvButton.setToolTipText("Click to export your spots statistics table.");
      JPanel panelCsv = new JPanel(new FlowLayout(0));
      panelCsv.add(csvButton);
      JPanel panelPngCsv = new JPanel(new FlowLayout(0));
      panelPngCsv.add(panelPlot);
      panelPngCsv.add(panelCsv);
      spotStatistics.add(panelPngCsv);
      spotPanel.add(spotStatistics);
      JPanel chartPanel2Track = new JPanel();
      this.sp2Track = new STScatterPlot("");
      this.scatterPlotTrack = this.sp2Track.createScatterChartPanelInitial("", "", new ArrayList(Arrays.asList(0.0D, 0.0D, 0.0D)), new ArrayList(Arrays.asList(0.0D, 0.0D, 0.0D)), this.markerRangeTrack, this.markerDomainTrack, new Double[][]{{0.0D}, {0.0D}}, new Double[][]{{0.0D}, {0.0D}});
      this.refreshButtonTrack = new JButton("");
      this.refreshButtonTrack.setIcon(FirstWizardPanel.refreshCell);
      this.refreshButtonTrack.setToolTipText("Click this button to refresh scatter-plot.");
      this.zoomInTrack = new JButton("");
      this.zoomInTrack.setIcon(zoomInCell);
      this.zoomInTrack.setToolTipText("Click this button to zoom in Chart");
      this.zoomOutTrack = new JButton("");
      this.zoomOutTrack.setIcon(zoomOutCell);
      this.zoomOutTrack.setToolTipText("Click this button to zoom out Chart");

      int i;
      for(i = 0; i < ChooserWizardPanel.columnNamesTrack.length; ++i) {
         this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[i].toString());
      }

      this.comboFeatureDomainTrack = new JComboBox();
      this.comboFeatureDomainTrack.setPreferredSize(new Dimension(110, 20));

      for(i = 3; i < this.itemFiltersTrack.size(); ++i) {
         this.comboFeatureDomainTrack.addItem((String)this.itemFiltersTrack.get(i));
      }

      this.comboFeatureDomainTrack.setOpaque(true);
      chartPanel2Track.add(this.scatterPlotTrack);
      this.filterOrderTrack = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1));
      this.filterOrderTrack.setPreferredSize(new Dimension(60, 20));
      this.filterOrderTrack.setEnabled(false);
      JPanel filtersMaxTrack = new JPanel(new FlowLayout(1));
      filtersMaxTrack.add(Box.createHorizontalStrut(2));
      this.comboFeatureRangeTrack = new JComboBox();
      this.comboFeatureRangeTrack.setPreferredSize(new Dimension(110, 20));

      for(int i = 3; i < this.itemFiltersTrack.size(); ++i) {
         this.comboFeatureRangeTrack.addItem((String)this.itemFiltersTrack.get(i));
      }

      this.comboFeatureRangeTrack.setOpaque(true);
      filtersMaxTrack.add(new JLabel("X :  "));
      filtersMaxTrack.add(this.comboFeatureDomainTrack);
      filtersMaxTrack.add(new JLabel("   Y :  "));
      filtersMaxTrack.add(this.comboFeatureRangeTrack);
      JPanel rangePanelFTrack = new JPanel(new FlowLayout(0));
      JPanel chartDomainPanelBoxTrack = new JPanel();
      chartDomainPanelBoxTrack.setLayout(new BoxLayout(chartDomainPanelBoxTrack, 1));
      chartDomainPanelBoxTrack.add(Box.createVerticalStrut(10));
      chartDomainPanelBoxTrack.add(chartPanel2Track);
      chartDomainPanelBoxTrack.add(filtersMaxTrack);
      rangePanelFTrack.add(chartDomainPanelBoxTrack);
      JPanel buttonBoxTrack = new JPanel();
      buttonBoxTrack.setLayout(new BoxLayout(buttonBoxTrack, 1));
      JPanel refreshButtonPanelTrack = new JPanel(new FlowLayout(0));
      refreshButtonPanelTrack.add(this.refreshButtonTrack);
      JPanel zoomOutButtonPanelTrack = new JPanel(new FlowLayout(0));
      zoomOutButtonPanelTrack.add(this.zoomOutTrack);
      JPanel zoomInButtonPanelTrack = new JPanel(new FlowLayout(0));
      zoomInButtonPanelTrack.add(this.zoomInTrack);
      buttonBoxTrack.add(zoomInButtonPanelTrack);
      buttonBoxTrack.add(zoomOutButtonPanelTrack);
      buttonBoxTrack.add(refreshButtonPanelTrack);
      this.comboRegressionTrack = new JComboBox();
      this.comboRegressionTrack.setPreferredSize(new Dimension(90, 20));
      this.comboRegressionTrack.addItem("Linear");
      this.comboRegressionTrack.addItem("Polynomial");
      this.comboRegressionTrack.addItem("Power");
      this.comboRegressionTrack.addItem("Logarithmic");
      this.comboRegressionTrack.addItem("Exponential");
      this.comboRegressionTrack.setSelectedIndex(0);
      this.comboRegressionTrack.setOpaque(true);
      JPanel regreOrderPanelTrack = new JPanel(new FlowLayout(0));
      regreOrderPanelTrack.add(this.comboRegressionTrack);
      JPanel filterOrderPanelTrack = new JPanel(new FlowLayout(0));
      filterOrderPanelTrack.add(this.filterOrderTrack);
      buttonBoxTrack.add(regreOrderPanelTrack);
      buttonBoxTrack.add(filterOrderPanelTrack);
      regressionPanelTrack = new JPanel();
      regressionPanelTrack.setBorder(BorderFactory.createTitledBorder("Reg.Params"));
      regressionPanelTrack.setPreferredSize(new Dimension(this.comboRegressionTrack.getWidth() + 10, 35));
      rangePanelFTrack.add(buttonBoxTrack);
      JPanel trackPanel = new JPanel();
      trackPanel.add(rangePanelFTrack);
      trackPanel.setBorder(BorderFactory.createTitledBorder(""));
      JPanel trackStatistics = new JPanel();
      trackStatistics.setLayout(new BoxLayout(trackStatistics, 1));
      JPanel trackStatisticsFlow = new JPanel(new FlowLayout(0));
      JPanel classParamTrack = new JPanel(new FlowLayout(0));
      this.comboClassTrack = new JComboBox();
      this.comboClassTrack.setPreferredSize(new Dimension(120, 20));

      int i;
      for(i = 3; i < ChooserWizardPanel.columnNamesTrack.length; ++i) {
         this.comboClassTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i]);
      }

      this.comboClassTrack.setOpaque(true);
      this.comboClassTrack.setToolTipText("Choose selected items for descriptive statistics.");
      this.comboParamTrack = new JComboBox();
      this.comboParamTrack.setPreferredSize(new Dimension(120, 20));

      for(i = 3; i < ChooserWizardPanel.columnNamesTrack.length; ++i) {
         this.comboParamTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i]);
      }

      this.comboParamTrack.setOpaque(true);
      this.comboParamTrack.setToolTipText("Choose a track parameter for descriptive statistics.");
      classParamTrack.add(this.comboClassTrack);
      classParamTrack.add(this.comboParamTrack);
      labelScoresTrack = new JLabel("SCORES");
      labelScoresTrack.setFont(new Font("Verdana", 1, 20));
      trackStatisticsFlow.add(labelScoresTrack);
      trackStatistics.add(Box.createVerticalStrut(60));
      trackStatistics.add(trackStatisticsFlow);
      trackStatistics.add(new JSeparator(0));
      trackStatistics.add(Box.createVerticalStrut(20));
      trackStatistics.add(classParamTrack);
      trackStatistics.add(Box.createVerticalStrut(20));
      trackStatistics.add(new JSeparator(0));
      String[][] dataTrack = new String[][]{{"", "", "", "", "", "", "", "", "", "", ""}};
      String[] columnTrack = new String[]{"Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", "Min ", "Max ", "Sum ", "Count "};
      JTable tableTrack = new JTable();
      this.modelTrack = new DefaultTableModel(dataTrack, columnTrack) {
         public Class<?> getColumnClass(int columnTrack) {
            if (this.getRowCount() > 0) {
               Object value = this.getValueAt(0, columnTrack);
               if (value != null) {
                  return this.getValueAt(0, columnTrack).getClass();
               }
            }

            return super.getColumnClass(columnTrack);
         }

         public boolean isCellEditable(int row, int col) {
            return false;
         }
      };
      tableTrack.setAutoResizeMode(0);
      tableTrack.setModel(this.modelTrack);
      tableTrack.setRowHeight(60);
      JScrollPane spTrack = new JScrollPane(tableTrack);
      spTrack.setPreferredSize(new Dimension(500, 100));
      spTrack.setVerticalScrollBarPolicy(22);
      spTrack.setHorizontalScrollBarPolicy(32);

      for(int u = 0; u < tableTrack.getColumnCount(); ++u) {
         tableTrack.getColumnModel().getColumn(u).setMinWidth(100);
         tableTrack.getColumnModel().getColumn(u).setMaxWidth(100);
         tableTrack.getColumnModel().getColumn(u).setPreferredWidth(100);
      }

      trackStatistics.add(Box.createVerticalStrut(20));
      trackStatistics.add(spTrack);
      trackStatistics.add(Box.createVerticalStrut(20));
      trackStatistics.add(new JSeparator(0));
      JButton plotButtonTrack = new JButton();
      plotButtonTrack.setIcon(plotCell);
      plotButtonTrack.setToolTipText("Click to export scatter plot.");
      JPanel panelPlotTrack = new JPanel(new FlowLayout(0));
      panelPlotTrack.add(plotButtonTrack);
      JButton csvButtonTrack = new JButton();
      csvButtonTrack.setIcon(csvCell);
      csvButtonTrack.setToolTipText("Click to export your tracks statistics table.");
      JPanel panelCsvTrack = new JPanel(new FlowLayout(0));
      panelCsvTrack.add(csvButtonTrack);
      JPanel panelPngCsvTrack = new JPanel(new FlowLayout(0));
      panelPngCsvTrack.add(panelPlotTrack);
      panelPngCsvTrack.add(panelCsvTrack);
      trackStatistics.add(panelPngCsvTrack);
      trackPanel.add(trackStatistics);
      JTabbedPane maintabbedPane = new JTabbedPane(1);
      maintabbedPane.addTab("SPOTS ", FirstWizardPanel.iconSpotCell, spotPanel, "Scatter-Plot for spots");
      maintabbedPane.addTab("TRACKS ", ChooserWizardPanel.iconTrackCell, trackPanel, "Scatter-Plot for tracks");
      maintabbedPane.setTabLayoutPolicy(1);
      this.add(maintabbedPane, "Center");
      plotButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(1);
            fileChooser.setDialogTitle("Specify a directory to save plot as .png file");
            int userSelection = fileChooser.showSaveDialog(new JFrame());
            if (userSelection == 0) {
               File fileToSave = fileChooser.getSelectedFile();
               BufferedImage chartImage = STScatterPlot.plot.getChart().createBufferedImage(1024, 768);

               try {
                  ImageIO.write(chartImage, "png", new File(fileToSave.getAbsolutePath() + File.separator + "SpotPlot for " + IJ.getImage().getShortTitle() + ".png"));
               } catch (IOException var7) {
                  var7.printStackTrace();
               }
            }

         }
      });
      csvButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            List<String> columnSpotHead = new ArrayList();

            for(int j = 0; j < OptionWizardPanel.this.modelSpot.getColumnCount(); ++j) {
               columnSpotHead.add(OptionWizardPanel.this.modelSpot.getColumnName(j));
            }

            ResultsTable rt = new ResultsTable(OptionWizardPanel.this.modelSpot.getRowCount());
            if (rt != null) {
               rt.reset();
            }

            for(int i = 0; i < OptionWizardPanel.this.modelSpot.getRowCount(); ++i) {
               for(int jx = 0; jx < OptionWizardPanel.this.modelSpot.getColumnCount(); ++jx) {
                  rt.setValue((String)columnSpotHead.get(jx), i, OptionWizardPanel.this.modelSpot.getValueAt(i, jx).toString());
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
               } catch (IOException var9) {
                  var9.printStackTrace();
               }
            }

         }
      });
      this.refreshButtonSpot.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.refreshSpotThread = new Thread(new Runnable() {
               public void run() {
                  OptionWizardPanel.this.refreshActionSpot();
               }
            });
            OptionWizardPanel.this.refreshSpotThread.start();
         }
      });
      this.refreshButtonTrack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.refreshTrackThread = new Thread(new Runnable() {
               public void run() {
                  OptionWizardPanel.this.refreshActionTrack();
               }
            });
            OptionWizardPanel.this.refreshTrackThread.start();
         }
      });
      this.zoomInSpot.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.zoomInSpotThread = new Thread(new Runnable() {
               public void run() {
                  OptionWizardPanel.this.zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
                  OptionWizardPanel.this.zoomInSpot.addActionListener(OptionWizardPanel.this.scatterPlotSpot);
               }
            });
            OptionWizardPanel.this.zoomInSpotThread.start();
         }
      });
      this.zoomOutSpot.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.zoomOutSpotThread = new Thread(new Runnable() {
               public void run() {
                  OptionWizardPanel.this.zoomOutSpot.setActionCommand("ZOOM_OUT_BOTH");
                  OptionWizardPanel.this.zoomOutSpot.addActionListener(OptionWizardPanel.this.scatterPlotSpot);
               }
            });
            OptionWizardPanel.this.zoomOutSpotThread.start();
         }
      });
      this.zoomInTrack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.zoomInTrackThread = new Thread(new Runnable() {
               public void run() {
                  OptionWizardPanel.this.zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
                  OptionWizardPanel.this.zoomInSpot.addActionListener(OptionWizardPanel.this.scatterPlotTrack);
               }
            });
            OptionWizardPanel.this.zoomInTrackThread.start();
         }
      });
      this.zoomOutTrack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.zoomOutTrackThread = new Thread(new Runnable() {
               public void run() {
                  OptionWizardPanel.this.zoomOutTrack.setActionCommand("ZOOM_OUT_BOTH");
                  OptionWizardPanel.this.zoomOutTrack.addActionListener(OptionWizardPanel.this.scatterPlotTrack);
               }
            });
            OptionWizardPanel.this.zoomOutTrackThread.start();
         }
      });
      this.comboRegressionSpot.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.comboRegSpotThread = new Thread(new Runnable() {
               public void run() {
                  if (OptionWizardPanel.this.comboRegressionSpot.getSelectedIndex() == 1) {
                     OptionWizardPanel.this.filterOrderSpot.setEnabled(true);
                  }

                  if (OptionWizardPanel.this.comboRegressionSpot.getSelectedIndex() != 1) {
                     OptionWizardPanel.this.filterOrderSpot.setEnabled(false);
                  }

               }
            });
            OptionWizardPanel.this.comboRegSpotThread.start();
         }
      });
      this.comboRegressionTrack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.comboRegTrackThread = new Thread(new Runnable() {
               public void run() {
                  if (OptionWizardPanel.this.comboRegressionTrack.getSelectedIndex() == 1) {
                     OptionWizardPanel.this.filterOrderTrack.setEnabled(true);
                  }

                  if (OptionWizardPanel.this.comboRegressionTrack.getSelectedIndex() != 1) {
                     OptionWizardPanel.this.filterOrderTrack.setEnabled(false);
                  }

               }
            });
            OptionWizardPanel.this.comboRegTrackThread.start();
         }
      });
      this.comboClassSpot.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.comboClassSpotThread = new Thread(new Runnable() {
               public void run() {
                  int i;
                  if (OptionWizardPanel.this.comboClassSpot.getSelectedIndex() == 0) {
                     OptionWizardPanel.this.dataToStatisticsSpot = new ArrayList();

                     for(i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                        if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE) {
                           OptionWizardPanel.this.dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot.getValueAt(i, OptionWizardPanel.this.comboParamSpot.getSelectedIndex() + 3).toString()));
                        }
                     }

                     OptionWizardPanel.this.descriptiveStatisticsActionSpot();
                  }

                  if (OptionWizardPanel.this.comboClassSpot.getSelectedIndex() == 1) {
                     OptionWizardPanel.this.dataToStatisticsSpot = new ArrayList();

                     for(i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                        if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0)).getText() == "") {
                           OptionWizardPanel.this.dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot.getValueAt(i, OptionWizardPanel.this.comboParamSpot.getSelectedIndex() + 3).toString()));
                        }
                     }

                     OptionWizardPanel.this.descriptiveStatisticsActionSpot();
                  }

                  if (OptionWizardPanel.this.comboClassSpot.getSelectedIndex() == 2) {
                     OptionWizardPanel.this.dataToStatisticsSpot = new ArrayList();

                     for(i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                        if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0)).getText() == OptionWizardPanel.this.comboClassSpot.getSelectedItem().toString()) {
                           OptionWizardPanel.this.dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot.getValueAt(i, OptionWizardPanel.this.comboParamSpot.getSelectedIndex() + 3).toString()));
                        }
                     }

                     OptionWizardPanel.this.descriptiveStatisticsActionSpot();
                  }

               }
            });
            OptionWizardPanel.this.comboClassSpotThread.start();
         }
      });
      this.comboClassTrack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            OptionWizardPanel.this.comboClassTrackThread = new Thread(new Runnable() {
               public void run() {
                  int i;
                  if (OptionWizardPanel.this.comboClassTrack.getSelectedIndex() == 0) {
                     OptionWizardPanel.this.dataToStatisticsTrack = new ArrayList();

                     for(i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                        if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE) {
                           OptionWizardPanel.this.dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack.getValueAt(i, OptionWizardPanel.this.comboParamTrack.getSelectedIndex() + 3).toString()));
                        }
                     }

                     OptionWizardPanel.this.descriptiveStatisticsActionTrack();
                  }

                  if (OptionWizardPanel.this.comboClassTrack.getSelectedIndex() == 1) {
                     OptionWizardPanel.this.dataToStatisticsTrack = new ArrayList();

                     for(i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                        if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0)).getText() == "") {
                           OptionWizardPanel.this.dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack.getValueAt(i, OptionWizardPanel.this.comboParamTrack.getSelectedIndex() + 3).toString()));
                        }
                     }

                     OptionWizardPanel.this.descriptiveStatisticsActionTrack();
                  }

                  if (OptionWizardPanel.this.comboClassTrack.getSelectedIndex() == 2) {
                     OptionWizardPanel.this.dataToStatisticsTrack = new ArrayList();

                     for(i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                        if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0)).getText() == OptionWizardPanel.this.comboClassTrack.getSelectedItem().toString()) {
                           OptionWizardPanel.this.dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack.getValueAt(i, OptionWizardPanel.this.comboParamTrack.getSelectedIndex() + 3).toString()));
                        }
                     }

                     OptionWizardPanel.this.descriptiveStatisticsActionTrack();
                  }

               }
            });
            OptionWizardPanel.this.comboClassTrackThread.start();
         }
      });
   }

   public void descriptiveStatisticsActionSpot() {
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMean() * 1000.0D) / 1000.0D), 0, 0);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() / (double)(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 1000.0D), 0, 1);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getPercentile(50.0D) * 1000.0D) / 1000.0D), 0, 2);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() * 1000.0D) / 1000.0D), 0, 3);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getVariance() * 1000.0D) / 1000.0D), 0, 4);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getKurtosis() * 1000.0D) / 1000.0D), 0, 5);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getSkewness() * 1000.0D) / 1000.0D), 0, 6);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMin() * 1000.0D) / 1000.0D), 0, 7);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getMax() * 1000.0D) / 1000.0D), 0, 8);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getSum() * 1000.0D) / 1000.0D), 0, 9);
      this.modelSpot.setValueAt(String.valueOf((double)Math.round((double)(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 1000.0D), 0, 10);
   }

   public void descriptiveStatisticsActionTrack() {
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMean() * 1000.0D) / 1000.0D), 0, 0);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() / (double)(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 1000.0D), 0, 1);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getPercentile(50.0D) * 1000.0D) / 1000.0D), 0, 2);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getStandardDeviation() * 1000.0D) / 1000.0D), 0, 3);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getVariance() * 1000.0D) / 1000.0D), 0, 4);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getKurtosis() * 1000.0D) / 1000.0D), 0, 5);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getSkewness() * 1000.0D) / 1000.0D), 0, 6);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMin() * 1000.0D) / 1000.0D), 0, 7);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getMax() * 1000.0D) / 1000.0D), 0, 8);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getSum() * 1000.0D) / 1000.0D), 0, 9);
      this.modelTrack.setValueAt(String.valueOf((double)Math.round((double)(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray())).getN() * 1000.0D) / 1000.0D), 0, 10);
   }

   public void refreshActionSpot() {
      this.comboClassSpot.removeAllItems();
      this.comboClassSpot.addItem("All items");
      this.comboClassSpot.addItem("No Identified");
      int rowCount;
      if (ColorEditorSpot.modelC.getRowCount() > 0) {
         for(rowCount = 0; rowCount < ColorEditorSpot.modelC.getRowCount(); ++rowCount) {
            this.comboClassSpot.addItem(((JLabel)ColorEditorSpot.modelC.getValueAt(rowCount, 0)).getText());
         }
      }

      rowCount = FirstWizardPanel.tableSpot.getRowCount();
      int columnCount = FirstWizardPanel.tableSpot.getColumnCount();
      selectedIndexDomainSpot = this.comboFeatureDomainSpot.getSelectedIndex();
      selectedIndexRangeSpot = this.comboFeatureRangeSpot.getSelectedIndex();
      List<Double> valuesDomainSpot = new ArrayList();
      List<Double> valuesRangeSpot = new ArrayList();
      Object[][] dataSpot = new Object[rowCount][columnCount];

      int i;
      for(int i = 0; i < rowCount; ++i) {
         for(i = 0; i < columnCount; ++i) {
            dataSpot[i][i] = FirstWizardPanel.tableSpot.getValueAt(i, i);
         }

         valuesDomainSpot.add(Double.parseDouble(dataSpot[i][selectedIndexDomainSpot + 4].toString()));
         valuesRangeSpot.add(Double.parseDouble(dataSpot[i][selectedIndexRangeSpot + 4].toString()));
      }

      if (valuesDomainSpot.isEmpty() == Boolean.TRUE) {
         IJ.error("You should have your spot analysis done. Please go backwards.");
      } else {
         this.maxDomainSpot = (Double)Collections.max(valuesDomainSpot);
         this.maxRangeSpot = (Double)Collections.max(valuesRangeSpot);
         List<Color> listColorSpot = new ArrayList();

         for(i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
            listColorSpot.add(((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getBackground());
         }

         Color[] classColorSpot = new Color[listColorSpot.size()];
         listColorSpot.toArray(classColorSpot);
         if (this.comboRegressionSpot.getSelectedIndex() == 0) {
            this.sp2Spot.addScatterPlotSeriesLinear(this.comboFeatureDomainSpot.getSelectedItem().toString(), this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot);
         }

         if (this.comboRegressionSpot.getSelectedIndex() == 1) {
            this.sp2Spot.addScatterPlotSeriesPolynomial(this.comboFeatureDomainSpot.getSelectedItem().toString(), this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot, (Integer)this.filterOrderSpot.getValue());
         }

         if (this.comboRegressionSpot.getSelectedIndex() == 2) {
            this.sp2Spot.addScatterPlotSeriesPower(this.comboFeatureDomainSpot.getSelectedItem().toString(), this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot);
         }

         if (this.comboRegressionSpot.getSelectedIndex() == 3) {
            this.sp2Spot.addScatterPlotSeriesLogarithmic(this.comboFeatureDomainSpot.getSelectedItem().toString(), this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot);
         }

         if (this.comboRegressionSpot.getSelectedIndex() == 4) {
            this.sp2Spot.addScatterPlotSeriesExponential(this.comboFeatureDomainSpot.getSelectedItem().toString(), this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot);
         }
      }

   }

   public void refreshActionTrack() {
      this.comboClassTrack.removeAllItems();
      this.comboClassTrack.addItem("All items");
      this.comboClassTrack.addItem("No Identified");
      int rowCount;
      if (ColorEditorTrack.modelC.getRowCount() > 0) {
         for(rowCount = 0; rowCount < ColorEditorTrack.modelC.getRowCount(); ++rowCount) {
            this.comboClassTrack.addItem(((JLabel)ColorEditorTrack.modelC.getValueAt(rowCount, 0)).getText());
         }
      }

      rowCount = ChooserWizardPanel.tableTrack.getRowCount();
      int columnCount = ChooserWizardPanel.tableTrack.getColumnCount();
      selectedIndexDomainTrack = this.comboFeatureDomainTrack.getSelectedIndex();
      selectedIndexRangeTrack = this.comboFeatureRangeTrack.getSelectedIndex();
      List<Double> valuesDomainTrack = new ArrayList();
      List<Double> valuesRangeTrack = new ArrayList();
      Object[][] dataTrack = new Object[rowCount][columnCount];

      int i;
      for(int i = 0; i < rowCount; ++i) {
         for(i = 0; i < columnCount; ++i) {
            dataTrack[i][i] = ChooserWizardPanel.tableTrack.getValueAt(i, i);
         }

         valuesDomainTrack.add(Double.parseDouble(dataTrack[i][selectedIndexDomainTrack + 4].toString()));
         valuesRangeTrack.add(Double.parseDouble(dataTrack[i][selectedIndexRangeTrack + 4].toString()));
      }

      if (valuesDomainTrack.isEmpty() == Boolean.TRUE) {
         IJ.error("You should have your track analysis done. Please go backwards.");
      } else {
         this.maxDomainTrack = (Double)Collections.max(valuesDomainTrack);
         this.maxRangeTrack = (Double)Collections.max(valuesRangeTrack);
         List<Color> listColorTrack = new ArrayList();

         for(i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
            listColorTrack.add(((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1))).getBackground());
         }

         Color[] classColorTrack = new Color[listColorTrack.size()];
         listColorTrack.toArray(classColorTrack);
         if (this.comboRegressionTrack.getSelectedIndex() == 0) {
            this.sp2Track.addScatterPlotSeriesLinear(this.comboFeatureDomainTrack.getSelectedItem().toString(), this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack);
         }

         if (this.comboRegressionTrack.getSelectedIndex() == 1) {
            this.sp2Track.addScatterPlotSeriesPolynomial(this.comboFeatureDomainTrack.getSelectedItem().toString(), this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack, (Integer)this.filterOrderTrack.getValue());
         }

         if (this.comboRegressionTrack.getSelectedIndex() == 2) {
            this.sp2Track.addScatterPlotSeriesPower(this.comboFeatureDomainTrack.getSelectedItem().toString(), this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack);
         }

         if (this.comboRegressionTrack.getSelectedIndex() == 3) {
            this.sp2Track.addScatterPlotSeriesLogarithmic(this.comboFeatureDomainTrack.getSelectedItem().toString(), this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack);
         }

         if (this.comboRegressionTrack.getSelectedIndex() == 4) {
            this.sp2Track.addScatterPlotSeriesExponential(this.comboFeatureDomainTrack.getSelectedItem().toString(), this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack);
         }
      }

   }

   public void update1() {
      this.setNextButtonEnabled(true);
      this.setFinishButtonEnabled(true);
      this.setBackButtonEnabled(true);
   }

   public void next() {
      this.setNextButtonEnabled(false);
   }

   public void back() {
      this.switchPanel(1);
   }

   public void finish() {
      this.switchPanel(2);
   }
}
