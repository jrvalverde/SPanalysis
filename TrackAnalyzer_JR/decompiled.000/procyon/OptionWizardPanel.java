import java.util.Collections;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import javax.swing.Icon;
import ij.measure.ResultsTable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import ij.IJ;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.FlowLayout;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Stroke;
import java.awt.Paint;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import jwizardcomponent.JWizardComponents;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import javax.swing.JButton;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import org.jfree.chart.plot.IntervalMarker;

// 
// Decompiled by Procyon v0.5.36
// 

public class OptionWizardPanel extends LabelWizardPanel
{
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
    
    public OptionWizardPanel(final JWizardComponents wizardComponents, final String option) {
        super(wizardComponents, "");
        this.setPanelTitle("");
        this.setLayout((LayoutManager)new BoxLayout((Container)this, 1));
        final JPanel chartPanel2Spot = new JPanel();
        this.markerRangeSpot = new IntervalMarker(0.0, 0.0, (Paint)new Color(229, 255, 204), (Stroke)new BasicStroke(), (Paint)new Color(0, 102, 0), (Stroke)new BasicStroke(1.5f), 0.6f);
        this.markerDomainSpot = new IntervalMarker(0.0, 0.0, (Paint)new Color(229, 255, 204), (Stroke)new BasicStroke(), (Paint)new Color(0, 102, 0), (Stroke)new BasicStroke(1.5f), 0.5f);
        this.markerRangeTrack = new IntervalMarker(0.0, 0.0, (Paint)new Color(229, 255, 204), (Stroke)new BasicStroke(), (Paint)new Color(0, 102, 0), (Stroke)new BasicStroke(1.5f), 0.6f);
        this.markerDomainTrack = new IntervalMarker(0.0, 0.0, (Paint)new Color(229, 255, 204), (Stroke)new BasicStroke(), (Paint)new Color(0, 102, 0), (Stroke)new BasicStroke(1.5f), 0.5f);
        this.sp2Spot = new STScatterPlot("");
        this.scatterPlotSpot = this.sp2Spot.createScatterChartPanelInitial("", "", new ArrayList<Double>(Arrays.<Double>asList(0.0, 0.0, 0.0)), new ArrayList<Double>(Arrays.<Double>asList(0.0, 0.0, 0.0)), this.markerRangeSpot, this.markerDomainSpot, new Double[][] { { 0.0 }, { 0.0 } }, new Double[][] { { 0.0 }, { 0.0 } });
        (this.refreshButtonSpot = new JButton("")).setIcon(FirstWizardPanel.refreshCell);
        this.refreshButtonSpot.setToolTipText("Click this button to refresh scatter-plot.");
        this.zoomInSpot = new JButton("");
        final ImageIcon iconZoomIn = FirstWizardPanel.createImageIcon("images/zoomin.png");
        final Icon zoomInCell = new ImageIcon(iconZoomIn.getImage().getScaledInstance(18, 20, 4));
        this.zoomInSpot.setIcon(zoomInCell);
        this.zoomInSpot.setToolTipText("Click this button to zoom in Chart");
        this.zoomOutSpot = new JButton("");
        final ImageIcon iconZoomOut = FirstWizardPanel.createImageIcon("images/zoomout.png");
        final Icon zoomOutCell = new ImageIcon(iconZoomOut.getImage().getScaledInstance(18, 20, 4));
        this.zoomOutSpot.setIcon(zoomOutCell);
        this.zoomOutSpot.setToolTipText("Click this button to zoom out Chart");
        this.itemFiltersSpot = new ArrayList<String>();
        this.itemFiltersTrack = new ArrayList<String>();
        for (int i = 2; i < FirstWizardPanel.columnNamesSpot.length; ++i) {
            this.itemFiltersSpot.add(FirstWizardPanel.columnNamesSpot[i].toString());
        }
        for (int i = 0; i < ChooserWizardPanel.columnNamesTrack.length; ++i) {
            this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[i].toString());
        }
        (this.comboFeatureDomainSpot = new JComboBox<String>()).setPreferredSize(new Dimension(110, 20));
        for (int i = 0; i < this.itemFiltersSpot.size(); ++i) {
            this.comboFeatureDomainSpot.addItem(this.itemFiltersSpot.get(i));
        }
        this.comboFeatureDomainSpot.setOpaque(true);
        this.scatterPlot = new STScatterPlot("holaa");
        chartPanel2Spot.add((Component)this.scatterPlotSpot);
        (this.filterOrderSpot = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1))).setPreferredSize(new Dimension(60, 20));
        this.filterOrderSpot.setEnabled(false);
        final JPanel filtersMaxSpot = new JPanel(new FlowLayout(1));
        filtersMaxSpot.add(Box.createHorizontalStrut(2));
        (this.comboFeatureRangeSpot = new JComboBox<String>()).setPreferredSize(new Dimension(110, 20));
        for (int j = 0; j < this.itemFiltersSpot.size(); ++j) {
            this.comboFeatureRangeSpot.addItem(this.itemFiltersSpot.get(j));
        }
        this.comboFeatureRangeSpot.setOpaque(true);
        filtersMaxSpot.add(new JLabel("X :  "));
        filtersMaxSpot.add(this.comboFeatureDomainSpot);
        filtersMaxSpot.add(new JLabel("   Y :  "));
        filtersMaxSpot.add(this.comboFeatureRangeSpot);
        final JPanel rangePanelFSpot = new JPanel(new FlowLayout(0));
        final JPanel chartDomainPanelBoxSpot = new JPanel();
        chartDomainPanelBoxSpot.setLayout(new BoxLayout(chartDomainPanelBoxSpot, 1));
        chartDomainPanelBoxSpot.add(Box.createVerticalStrut(10));
        chartDomainPanelBoxSpot.add(chartPanel2Spot);
        chartDomainPanelBoxSpot.add(filtersMaxSpot);
        rangePanelFSpot.add(chartDomainPanelBoxSpot);
        final JPanel buttonBox = new JPanel();
        buttonBox.setLayout(new BoxLayout(buttonBox, 1));
        final JPanel refreshButtonPanelSpot = new JPanel(new FlowLayout(0));
        refreshButtonPanelSpot.add(this.refreshButtonSpot);
        final JPanel zoomOutButtonPanelSpot = new JPanel(new FlowLayout(0));
        zoomOutButtonPanelSpot.add(this.zoomOutSpot);
        final JPanel zoomInButtonPanelSpot = new JPanel(new FlowLayout(0));
        zoomInButtonPanelSpot.add(this.zoomInSpot);
        buttonBox.add(zoomInButtonPanelSpot);
        buttonBox.add(zoomOutButtonPanelSpot);
        buttonBox.add(refreshButtonPanelSpot);
        (this.comboRegressionSpot = new JComboBox<String>()).setPreferredSize(new Dimension(90, 20));
        this.comboRegressionSpot.addItem("Linear");
        this.comboRegressionSpot.addItem("Polynomial");
        this.comboRegressionSpot.addItem("Power");
        this.comboRegressionSpot.addItem("Logarithmic");
        this.comboRegressionSpot.addItem("Exponential");
        this.comboRegressionSpot.setSelectedIndex(0);
        this.comboRegressionSpot.setOpaque(true);
        final JPanel regreOrderPanel = new JPanel(new FlowLayout(0));
        regreOrderPanel.add(this.comboRegressionSpot);
        final JPanel filterOrderPanel = new JPanel(new FlowLayout(0));
        filterOrderPanel.add(this.filterOrderSpot);
        buttonBox.add(regreOrderPanel);
        buttonBox.add(filterOrderPanel);
        (OptionWizardPanel.regressionPanelSpot = new JPanel()).setBorder(BorderFactory.createTitledBorder("Reg.Params"));
        OptionWizardPanel.regressionPanelSpot.setPreferredSize(new Dimension(this.comboRegressionSpot.getWidth() + 10, 35));
        rangePanelFSpot.add(buttonBox);
        final JPanel spotPanel = new JPanel();
        spotPanel.add(rangePanelFSpot);
        spotPanel.setBorder(BorderFactory.createTitledBorder(""));
        final JPanel spotStatistics = new JPanel();
        spotStatistics.setLayout(new BoxLayout(spotStatistics, 1));
        final JPanel spotStatisticsFlow = new JPanel(new FlowLayout(0));
        final JPanel classParamSpot = new JPanel(new FlowLayout(0));
        (this.comboClassSpot = new JComboBox<String>()).setPreferredSize(new Dimension(120, 20));
        for (int k = 2; k < FirstWizardPanel.columnNamesSpot.length; ++k) {
            this.comboClassSpot.addItem((String)FirstWizardPanel.columnNamesSpot[k]);
        }
        this.comboClassSpot.setOpaque(true);
        this.comboClassSpot.setToolTipText("Choose selected items for descriptive statistics.");
        (this.comboParamSpot = new JComboBox<String>()).setPreferredSize(new Dimension(120, 20));
        for (int k = 2; k < FirstWizardPanel.columnNamesSpot.length; ++k) {
            this.comboParamSpot.addItem((String)FirstWizardPanel.columnNamesSpot[k]);
        }
        this.comboParamSpot.setOpaque(true);
        this.comboParamSpot.setToolTipText("Choose a spot parameter for descriptive statistics.");
        classParamSpot.add(this.comboClassSpot);
        classParamSpot.add(this.comboParamSpot);
        (OptionWizardPanel.labelScoresSpot = new JLabel("SCORES")).setFont(new Font("Verdana", 1, 20));
        spotStatisticsFlow.add(OptionWizardPanel.labelScoresSpot);
        spotStatistics.add(Box.createVerticalStrut(60));
        spotStatistics.add(spotStatisticsFlow);
        spotStatistics.add(new JSeparator(0));
        spotStatistics.add(Box.createVerticalStrut(20));
        spotStatistics.add(classParamSpot);
        spotStatistics.add(Box.createVerticalStrut(20));
        spotStatistics.add(new JSeparator(0));
        final String[][] data = { { "", "", "", "", "", "", "", "", "", "", "" } };
        final String[] column = { "Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", "Min ", "Max ", "Sum ", "Count " };
        final JTable table = new JTable();
        this.modelSpot = new DefaultTableModel(data, column) {
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
            
            @Override
            public boolean isCellEditable(final int row, final int col) {
                return false;
            }
        };
        table.setAutoResizeMode(0);
        table.setModel(this.modelSpot);
        table.setRowHeight(60);
        final JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(500, 100));
        sp.setVerticalScrollBarPolicy(22);
        sp.setHorizontalScrollBarPolicy(32);
        for (int u = 0; u < table.getColumnCount(); ++u) {
            table.getColumnModel().getColumn(u).setMinWidth(100);
            table.getColumnModel().getColumn(u).setMaxWidth(100);
            table.getColumnModel().getColumn(u).setPreferredWidth(100);
        }
        spotStatistics.add(Box.createVerticalStrut(20));
        spotStatistics.add(sp);
        spotStatistics.add(Box.createVerticalStrut(20));
        spotStatistics.add(new JSeparator(0));
        final JButton plotButton = new JButton();
        final ImageIcon iconPlot = FirstWizardPanel.createImageIcon("images/plot.jpg");
        final Icon plotCell = new ImageIcon(iconPlot.getImage().getScaledInstance(18, 20, 4));
        plotButton.setIcon(plotCell);
        plotButton.setToolTipText("Click to export scatter plot.");
        final JPanel panelPlot = new JPanel(new FlowLayout(0));
        panelPlot.add(plotButton);
        final JButton csvButton = new JButton();
        final ImageIcon iconCsv = FirstWizardPanel.createImageIcon("images/csv.png");
        final Icon csvCell = new ImageIcon(iconCsv.getImage().getScaledInstance(18, 20, 4));
        csvButton.setIcon(csvCell);
        csvButton.setToolTipText("Click to export your spots statistics table.");
        final JPanel panelCsv = new JPanel(new FlowLayout(0));
        panelCsv.add(csvButton);
        final JPanel panelPngCsv = new JPanel(new FlowLayout(0));
        panelPngCsv.add(panelPlot);
        panelPngCsv.add(panelCsv);
        spotStatistics.add(panelPngCsv);
        spotPanel.add(spotStatistics);
        final JPanel chartPanel2Track = new JPanel();
        this.sp2Track = new STScatterPlot("");
        this.scatterPlotTrack = this.sp2Track.createScatterChartPanelInitial("", "", new ArrayList<Double>(Arrays.<Double>asList(0.0, 0.0, 0.0)), new ArrayList<Double>(Arrays.<Double>asList(0.0, 0.0, 0.0)), this.markerRangeTrack, this.markerDomainTrack, new Double[][] { { 0.0 }, { 0.0 } }, new Double[][] { { 0.0 }, { 0.0 } });
        (this.refreshButtonTrack = new JButton("")).setIcon(FirstWizardPanel.refreshCell);
        this.refreshButtonTrack.setToolTipText("Click this button to refresh scatter-plot.");
        (this.zoomInTrack = new JButton("")).setIcon(zoomInCell);
        this.zoomInTrack.setToolTipText("Click this button to zoom in Chart");
        (this.zoomOutTrack = new JButton("")).setIcon(zoomOutCell);
        this.zoomOutTrack.setToolTipText("Click this button to zoom out Chart");
        for (int l = 0; l < ChooserWizardPanel.columnNamesTrack.length; ++l) {
            this.itemFiltersTrack.add(ChooserWizardPanel.columnNamesTrack[l].toString());
        }
        (this.comboFeatureDomainTrack = new JComboBox<String>()).setPreferredSize(new Dimension(110, 20));
        for (int l = 3; l < this.itemFiltersTrack.size(); ++l) {
            this.comboFeatureDomainTrack.addItem(this.itemFiltersTrack.get(l));
        }
        this.comboFeatureDomainTrack.setOpaque(true);
        chartPanel2Track.add((Component)this.scatterPlotTrack);
        (this.filterOrderTrack = new JSpinner(new SpinnerNumberModel(1, 0, 1000, 1))).setPreferredSize(new Dimension(60, 20));
        this.filterOrderTrack.setEnabled(false);
        final JPanel filtersMaxTrack = new JPanel(new FlowLayout(1));
        filtersMaxTrack.add(Box.createHorizontalStrut(2));
        (this.comboFeatureRangeTrack = new JComboBox<String>()).setPreferredSize(new Dimension(110, 20));
        for (int m = 3; m < this.itemFiltersTrack.size(); ++m) {
            this.comboFeatureRangeTrack.addItem(this.itemFiltersTrack.get(m));
        }
        this.comboFeatureRangeTrack.setOpaque(true);
        filtersMaxTrack.add(new JLabel("X :  "));
        filtersMaxTrack.add(this.comboFeatureDomainTrack);
        filtersMaxTrack.add(new JLabel("   Y :  "));
        filtersMaxTrack.add(this.comboFeatureRangeTrack);
        final JPanel rangePanelFTrack = new JPanel(new FlowLayout(0));
        final JPanel chartDomainPanelBoxTrack = new JPanel();
        chartDomainPanelBoxTrack.setLayout(new BoxLayout(chartDomainPanelBoxTrack, 1));
        chartDomainPanelBoxTrack.add(Box.createVerticalStrut(10));
        chartDomainPanelBoxTrack.add(chartPanel2Track);
        chartDomainPanelBoxTrack.add(filtersMaxTrack);
        rangePanelFTrack.add(chartDomainPanelBoxTrack);
        final JPanel buttonBoxTrack = new JPanel();
        buttonBoxTrack.setLayout(new BoxLayout(buttonBoxTrack, 1));
        final JPanel refreshButtonPanelTrack = new JPanel(new FlowLayout(0));
        refreshButtonPanelTrack.add(this.refreshButtonTrack);
        final JPanel zoomOutButtonPanelTrack = new JPanel(new FlowLayout(0));
        zoomOutButtonPanelTrack.add(this.zoomOutTrack);
        final JPanel zoomInButtonPanelTrack = new JPanel(new FlowLayout(0));
        zoomInButtonPanelTrack.add(this.zoomInTrack);
        buttonBoxTrack.add(zoomInButtonPanelTrack);
        buttonBoxTrack.add(zoomOutButtonPanelTrack);
        buttonBoxTrack.add(refreshButtonPanelTrack);
        (this.comboRegressionTrack = new JComboBox<String>()).setPreferredSize(new Dimension(90, 20));
        this.comboRegressionTrack.addItem("Linear");
        this.comboRegressionTrack.addItem("Polynomial");
        this.comboRegressionTrack.addItem("Power");
        this.comboRegressionTrack.addItem("Logarithmic");
        this.comboRegressionTrack.addItem("Exponential");
        this.comboRegressionTrack.setSelectedIndex(0);
        this.comboRegressionTrack.setOpaque(true);
        final JPanel regreOrderPanelTrack = new JPanel(new FlowLayout(0));
        regreOrderPanelTrack.add(this.comboRegressionTrack);
        final JPanel filterOrderPanelTrack = new JPanel(new FlowLayout(0));
        filterOrderPanelTrack.add(this.filterOrderTrack);
        buttonBoxTrack.add(regreOrderPanelTrack);
        buttonBoxTrack.add(filterOrderPanelTrack);
        (OptionWizardPanel.regressionPanelTrack = new JPanel()).setBorder(BorderFactory.createTitledBorder("Reg.Params"));
        OptionWizardPanel.regressionPanelTrack.setPreferredSize(new Dimension(this.comboRegressionTrack.getWidth() + 10, 35));
        rangePanelFTrack.add(buttonBoxTrack);
        final JPanel trackPanel = new JPanel();
        trackPanel.add(rangePanelFTrack);
        trackPanel.setBorder(BorderFactory.createTitledBorder(""));
        final JPanel trackStatistics = new JPanel();
        trackStatistics.setLayout(new BoxLayout(trackStatistics, 1));
        final JPanel trackStatisticsFlow = new JPanel(new FlowLayout(0));
        final JPanel classParamTrack = new JPanel(new FlowLayout(0));
        (this.comboClassTrack = new JComboBox<String>()).setPreferredSize(new Dimension(120, 20));
        for (int i2 = 3; i2 < ChooserWizardPanel.columnNamesTrack.length; ++i2) {
            this.comboClassTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i2]);
        }
        this.comboClassTrack.setOpaque(true);
        this.comboClassTrack.setToolTipText("Choose selected items for descriptive statistics.");
        (this.comboParamTrack = new JComboBox<String>()).setPreferredSize(new Dimension(120, 20));
        for (int i2 = 3; i2 < ChooserWizardPanel.columnNamesTrack.length; ++i2) {
            this.comboParamTrack.addItem((String)ChooserWizardPanel.columnNamesTrack[i2]);
        }
        this.comboParamTrack.setOpaque(true);
        this.comboParamTrack.setToolTipText("Choose a track parameter for descriptive statistics.");
        classParamTrack.add(this.comboClassTrack);
        classParamTrack.add(this.comboParamTrack);
        (OptionWizardPanel.labelScoresTrack = new JLabel("SCORES")).setFont(new Font("Verdana", 1, 20));
        trackStatisticsFlow.add(OptionWizardPanel.labelScoresTrack);
        trackStatistics.add(Box.createVerticalStrut(60));
        trackStatistics.add(trackStatisticsFlow);
        trackStatistics.add(new JSeparator(0));
        trackStatistics.add(Box.createVerticalStrut(20));
        trackStatistics.add(classParamTrack);
        trackStatistics.add(Box.createVerticalStrut(20));
        trackStatistics.add(new JSeparator(0));
        final String[][] dataTrack = { { "", "", "", "", "", "", "", "", "", "", "" } };
        final String[] columnTrack = { "Mean ", "Std.Error ", "Median ", "Std.Dev ", "Variance ", "Kurtosis ", "Skewness ", "Min ", "Max ", "Sum ", "Count " };
        final JTable tableTrack = new JTable();
        this.modelTrack = new DefaultTableModel(dataTrack, columnTrack) {
            @Override
            public Class<?> getColumnClass(final int columnTrack) {
                if (this.getRowCount() > 0) {
                    final Object value = this.getValueAt(0, columnTrack);
                    if (value != null) {
                        return this.getValueAt(0, columnTrack).getClass();
                    }
                }
                return super.getColumnClass(columnTrack);
            }
            
            @Override
            public boolean isCellEditable(final int row, final int col) {
                return false;
            }
        };
        tableTrack.setAutoResizeMode(0);
        tableTrack.setModel(this.modelTrack);
        tableTrack.setRowHeight(60);
        final JScrollPane spTrack = new JScrollPane(tableTrack);
        spTrack.setPreferredSize(new Dimension(500, 100));
        spTrack.setVerticalScrollBarPolicy(22);
        spTrack.setHorizontalScrollBarPolicy(32);
        for (int u2 = 0; u2 < tableTrack.getColumnCount(); ++u2) {
            tableTrack.getColumnModel().getColumn(u2).setMinWidth(100);
            tableTrack.getColumnModel().getColumn(u2).setMaxWidth(100);
            tableTrack.getColumnModel().getColumn(u2).setPreferredWidth(100);
        }
        trackStatistics.add(Box.createVerticalStrut(20));
        trackStatistics.add(spTrack);
        trackStatistics.add(Box.createVerticalStrut(20));
        trackStatistics.add(new JSeparator(0));
        final JButton plotButtonTrack = new JButton();
        plotButtonTrack.setIcon(plotCell);
        plotButtonTrack.setToolTipText("Click to export scatter plot.");
        final JPanel panelPlotTrack = new JPanel(new FlowLayout(0));
        panelPlotTrack.add(plotButtonTrack);
        final JButton csvButtonTrack = new JButton();
        csvButtonTrack.setIcon(csvCell);
        csvButtonTrack.setToolTipText("Click to export your tracks statistics table.");
        final JPanel panelCsvTrack = new JPanel(new FlowLayout(0));
        panelCsvTrack.add(csvButtonTrack);
        final JPanel panelPngCsvTrack = new JPanel(new FlowLayout(0));
        panelPngCsvTrack.add(panelPlotTrack);
        panelPngCsvTrack.add(panelCsvTrack);
        trackStatistics.add(panelPngCsvTrack);
        trackPanel.add(trackStatistics);
        final JTabbedPane maintabbedPane = new JTabbedPane(1);
        maintabbedPane.addTab("SPOTS ", FirstWizardPanel.iconSpotCell, spotPanel, "Scatter-Plot for spots");
        maintabbedPane.addTab("TRACKS ", ChooserWizardPanel.iconTrackCell, trackPanel, "Scatter-Plot for tracks");
        maintabbedPane.setTabLayoutPolicy(1);
        this.add((Component)maintabbedPane, (Object)"Center");
        plotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(1);
                fileChooser.setDialogTitle("Specify a directory to save plot as .png file");
                final int userSelection = fileChooser.showSaveDialog(new JFrame());
                if (userSelection == 0) {
                    final File fileToSave = fileChooser.getSelectedFile();
                    final BufferedImage chartImage = STScatterPlot.plot.getChart().createBufferedImage(1024, 768);
                    try {
                        ImageIO.write(chartImage, "png", new File(String.valueOf(fileToSave.getAbsolutePath()) + File.separator + "SpotPlot for " + IJ.getImage().getShortTitle() + ".png"));
                    }
                    catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
        csvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final List<String> columnSpotHead = new ArrayList<String>();
                for (int j = 0; j < OptionWizardPanel.this.modelSpot.getColumnCount(); ++j) {
                    columnSpotHead.add(OptionWizardPanel.this.modelSpot.getColumnName(j));
                }
                final ResultsTable rt = new ResultsTable(Integer.valueOf(OptionWizardPanel.this.modelSpot.getRowCount()));
                if (rt != null) {
                    rt.reset();
                }
                for (int i = 0; i < OptionWizardPanel.this.modelSpot.getRowCount(); ++i) {
                    for (int k = 0; k < OptionWizardPanel.this.modelSpot.getColumnCount(); ++k) {
                        rt.setValue((String)columnSpotHead.get(k), i, OptionWizardPanel.this.modelSpot.getValueAt(i, k).toString());
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
                    catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
        this.refreshButtonSpot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.refreshSpotThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OptionWizardPanel.this.refreshActionSpot();
                    }
                })).start();
            }
        });
        this.refreshButtonTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.refreshTrackThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OptionWizardPanel.this.refreshActionTrack();
                    }
                })).start();
            }
        });
        this.zoomInSpot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.zoomInSpotThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OptionWizardPanel.this.zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
                        OptionWizardPanel.this.zoomInSpot.addActionListener((ActionListener)OptionWizardPanel.this.scatterPlotSpot);
                    }
                })).start();
            }
        });
        this.zoomOutSpot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.zoomOutSpotThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OptionWizardPanel.this.zoomOutSpot.setActionCommand("ZOOM_OUT_BOTH");
                        OptionWizardPanel.this.zoomOutSpot.addActionListener((ActionListener)OptionWizardPanel.this.scatterPlotSpot);
                    }
                })).start();
            }
        });
        this.zoomInTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.zoomInTrackThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OptionWizardPanel.this.zoomInSpot.setActionCommand("ZOOM_IN_BOTH");
                        OptionWizardPanel.this.zoomInSpot.addActionListener((ActionListener)OptionWizardPanel.this.scatterPlotTrack);
                    }
                })).start();
            }
        });
        this.zoomOutTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.zoomOutTrackThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OptionWizardPanel.this.zoomOutTrack.setActionCommand("ZOOM_OUT_BOTH");
                        OptionWizardPanel.this.zoomOutTrack.addActionListener((ActionListener)OptionWizardPanel.this.scatterPlotTrack);
                    }
                })).start();
            }
        });
        this.comboRegressionSpot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.comboRegSpotThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (OptionWizardPanel.this.comboRegressionSpot.getSelectedIndex() == 1) {
                            OptionWizardPanel.this.filterOrderSpot.setEnabled(true);
                        }
                        if (OptionWizardPanel.this.comboRegressionSpot.getSelectedIndex() != 1) {
                            OptionWizardPanel.this.filterOrderSpot.setEnabled(false);
                        }
                    }
                })).start();
            }
        });
        this.comboRegressionTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.comboRegTrackThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (OptionWizardPanel.this.comboRegressionTrack.getSelectedIndex() == 1) {
                            OptionWizardPanel.this.filterOrderTrack.setEnabled(true);
                        }
                        if (OptionWizardPanel.this.comboRegressionTrack.getSelectedIndex() != 1) {
                            OptionWizardPanel.this.filterOrderTrack.setEnabled(false);
                        }
                    }
                })).start();
            }
        });
        this.comboClassSpot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.comboClassSpotThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (OptionWizardPanel.this.comboClassSpot.getSelectedIndex() == 0) {
                            OptionWizardPanel.this.dataToStatisticsSpot = new ArrayList<Double>();
                            for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                                if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE) {
                                    OptionWizardPanel.this.dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot.getValueAt(i, OptionWizardPanel.this.comboParamSpot.getSelectedIndex() + 3).toString()));
                                }
                            }
                            OptionWizardPanel.this.descriptiveStatisticsActionSpot();
                        }
                        if (OptionWizardPanel.this.comboClassSpot.getSelectedIndex() == 1) {
                            OptionWizardPanel.this.dataToStatisticsSpot = new ArrayList<Double>();
                            for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                                if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0)).getText() == "") {
                                    OptionWizardPanel.this.dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot.getValueAt(i, OptionWizardPanel.this.comboParamSpot.getSelectedIndex() + 3).toString()));
                                }
                            }
                            OptionWizardPanel.this.descriptiveStatisticsActionSpot();
                        }
                        if (OptionWizardPanel.this.comboClassSpot.getSelectedIndex() == 2) {
                            OptionWizardPanel.this.dataToStatisticsSpot = new ArrayList<Double>();
                            for (int i = 0; i < FirstWizardPanel.modelSpot.getRowCount(); ++i) {
                                if (FirstWizardPanel.modelSpot.getValueAt(i, FirstWizardPanel.modelSpot.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)FirstWizardPanel.modelSpot.getValueAt(i, 0)).getText() == OptionWizardPanel.this.comboClassSpot.getSelectedItem().toString()) {
                                    OptionWizardPanel.this.dataToStatisticsSpot.add(Double.valueOf(FirstWizardPanel.modelSpot.getValueAt(i, OptionWizardPanel.this.comboParamSpot.getSelectedIndex() + 3).toString()));
                                }
                            }
                            OptionWizardPanel.this.descriptiveStatisticsActionSpot();
                        }
                    }
                })).start();
            }
        });
        this.comboClassTrack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                (OptionWizardPanel.this.comboClassTrackThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (OptionWizardPanel.this.comboClassTrack.getSelectedIndex() == 0) {
                            OptionWizardPanel.this.dataToStatisticsTrack = new ArrayList<Double>();
                            for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                                if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE) {
                                    OptionWizardPanel.this.dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack.getValueAt(i, OptionWizardPanel.this.comboParamTrack.getSelectedIndex() + 3).toString()));
                                }
                            }
                            OptionWizardPanel.this.descriptiveStatisticsActionTrack();
                        }
                        if (OptionWizardPanel.this.comboClassTrack.getSelectedIndex() == 1) {
                            OptionWizardPanel.this.dataToStatisticsTrack = new ArrayList<Double>();
                            for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                                if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0)).getText() == "") {
                                    OptionWizardPanel.this.dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack.getValueAt(i, OptionWizardPanel.this.comboParamTrack.getSelectedIndex() + 3).toString()));
                                }
                            }
                            OptionWizardPanel.this.descriptiveStatisticsActionTrack();
                        }
                        if (OptionWizardPanel.this.comboClassTrack.getSelectedIndex() == 2) {
                            OptionWizardPanel.this.dataToStatisticsTrack = new ArrayList<Double>();
                            for (int i = 0; i < ChooserWizardPanel.modelTrack.getRowCount(); ++i) {
                                if (ChooserWizardPanel.modelTrack.getValueAt(i, ChooserWizardPanel.modelTrack.getColumnCount() - 1) == Boolean.TRUE && ((JLabel)ChooserWizardPanel.modelTrack.getValueAt(i, 0)).getText() == OptionWizardPanel.this.comboClassTrack.getSelectedItem().toString()) {
                                    OptionWizardPanel.this.dataToStatisticsTrack.add(Double.valueOf(ChooserWizardPanel.modelTrack.getValueAt(i, OptionWizardPanel.this.comboParamTrack.getSelectedIndex() + 3).toString()));
                                }
                            }
                            OptionWizardPanel.this.descriptiveStatisticsActionTrack();
                        }
                    }
                })).start();
            }
        });
    }
    
    public void descriptiveStatisticsActionSpot() {
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getMean() * 1000.0) / 1000.0), 0, 0);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getStandardDeviation() / new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getN() * 1000.0) / 1000.0), 0, 1);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getPercentile(50.0) * 1000.0) / 1000.0), 0, 2);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getStandardDeviation() * 1000.0) / 1000.0), 0, 3);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getVariance() * 1000.0) / 1000.0), 0, 4);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getKurtosis() * 1000.0) / 1000.0), 0, 5);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getSkewness() * 1000.0) / 1000.0), 0, 6);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getMin() * 1000.0) / 1000.0), 0, 7);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getMax() * 1000.0) / 1000.0), 0, 8);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getSum() * 1000.0) / 1000.0), 0, 9);
        this.modelSpot.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsSpot.stream().mapToDouble(Double::doubleValue).toArray()).getN() * 1000.0) / 1000.0), 0, 10);
    }
    
    public void descriptiveStatisticsActionTrack() {
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getMean() * 1000.0) / 1000.0), 0, 0);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getStandardDeviation() / new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getN() * 1000.0) / 1000.0), 0, 1);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getPercentile(50.0) * 1000.0) / 1000.0), 0, 2);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getStandardDeviation() * 1000.0) / 1000.0), 0, 3);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getVariance() * 1000.0) / 1000.0), 0, 4);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getKurtosis() * 1000.0) / 1000.0), 0, 5);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getSkewness() * 1000.0) / 1000.0), 0, 6);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getMin() * 1000.0) / 1000.0), 0, 7);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getMax() * 1000.0) / 1000.0), 0, 8);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getSum() * 1000.0) / 1000.0), 0, 9);
        this.modelTrack.setValueAt(String.valueOf(Math.round(new DescriptiveStatistics(this.dataToStatisticsTrack.stream().mapToDouble(Double::doubleValue).toArray()).getN() * 1000.0) / 1000.0), 0, 10);
    }
    
    public void refreshActionSpot() {
        this.comboClassSpot.removeAllItems();
        this.comboClassSpot.addItem("All items");
        this.comboClassSpot.addItem("No Identified");
        if (ColorEditorSpot.modelC.getRowCount() > 0) {
            for (int i = 0; i < ColorEditorSpot.modelC.getRowCount(); ++i) {
                this.comboClassSpot.addItem(((JLabel)ColorEditorSpot.modelC.getValueAt(i, 0)).getText());
            }
        }
        final int rowCount = FirstWizardPanel.tableSpot.getRowCount();
        final int columnCount = FirstWizardPanel.tableSpot.getColumnCount();
        OptionWizardPanel.selectedIndexDomainSpot = this.comboFeatureDomainSpot.getSelectedIndex();
        OptionWizardPanel.selectedIndexRangeSpot = this.comboFeatureRangeSpot.getSelectedIndex();
        final List<Double> valuesDomainSpot = new ArrayList<Double>();
        final List<Double> valuesRangeSpot = new ArrayList<Double>();
        final Object[][] dataSpot = new Object[rowCount][columnCount];
        for (int j = 0; j < rowCount; ++j) {
            for (int k = 0; k < columnCount; ++k) {
                dataSpot[j][k] = FirstWizardPanel.tableSpot.getValueAt(j, k);
            }
            valuesDomainSpot.add(Double.parseDouble(dataSpot[j][OptionWizardPanel.selectedIndexDomainSpot + 4].toString()));
            valuesRangeSpot.add(Double.parseDouble(dataSpot[j][OptionWizardPanel.selectedIndexRangeSpot + 4].toString()));
        }
        if (valuesDomainSpot.isEmpty() == Boolean.TRUE) {
            IJ.error("You should have your spot analysis done. Please go backwards.");
        }
        else {
            this.maxDomainSpot = Collections.<Double>max((Collection<? extends Double>)valuesDomainSpot);
            this.maxRangeSpot = Collections.<Double>max((Collection<? extends Double>)valuesRangeSpot);
            final List<Color> listColorSpot = new ArrayList<Color>();
            for (int l = 0; l < FirstWizardPanel.modelSpot.getRowCount(); ++l) {
                listColorSpot.add(((JLabel)FirstWizardPanel.modelSpot.getValueAt(l, FirstWizardPanel.tableSpot.convertColumnIndexToModel(1))).getBackground());
            }
            final Color[] classColorSpot = new Color[listColorSpot.size()];
            listColorSpot.<Color>toArray(classColorSpot);
            if (this.comboRegressionSpot.getSelectedIndex() == 0) {
                this.sp2Spot.addScatterPlotSeriesLinear(this.comboFeatureDomainSpot.getSelectedItem().toString(), this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot);
            }
            if (this.comboRegressionSpot.getSelectedIndex() == 1) {
                this.sp2Spot.addScatterPlotSeriesPolynomial(this.comboFeatureDomainSpot.getSelectedItem().toString(), this.comboFeatureRangeSpot.getSelectedItem().toString(), valuesDomainSpot, valuesRangeSpot, this.markerRangeSpot, this.markerDomainSpot, dataSpot, classColorSpot, (int)this.filterOrderSpot.getValue());
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
        if (ColorEditorTrack.modelC.getRowCount() > 0) {
            for (int i = 0; i < ColorEditorTrack.modelC.getRowCount(); ++i) {
                this.comboClassTrack.addItem(((JLabel)ColorEditorTrack.modelC.getValueAt(i, 0)).getText());
            }
        }
        final int rowCount = ChooserWizardPanel.tableTrack.getRowCount();
        final int columnCount = ChooserWizardPanel.tableTrack.getColumnCount();
        OptionWizardPanel.selectedIndexDomainTrack = this.comboFeatureDomainTrack.getSelectedIndex();
        OptionWizardPanel.selectedIndexRangeTrack = this.comboFeatureRangeTrack.getSelectedIndex();
        final List<Double> valuesDomainTrack = new ArrayList<Double>();
        final List<Double> valuesRangeTrack = new ArrayList<Double>();
        final Object[][] dataTrack = new Object[rowCount][columnCount];
        for (int j = 0; j < rowCount; ++j) {
            for (int k = 0; k < columnCount; ++k) {
                dataTrack[j][k] = ChooserWizardPanel.tableTrack.getValueAt(j, k);
            }
            valuesDomainTrack.add(Double.parseDouble(dataTrack[j][OptionWizardPanel.selectedIndexDomainTrack + 4].toString()));
            valuesRangeTrack.add(Double.parseDouble(dataTrack[j][OptionWizardPanel.selectedIndexRangeTrack + 4].toString()));
        }
        if (valuesDomainTrack.isEmpty() == Boolean.TRUE) {
            IJ.error("You should have your track analysis done. Please go backwards.");
        }
        else {
            this.maxDomainTrack = Collections.<Double>max((Collection<? extends Double>)valuesDomainTrack);
            this.maxRangeTrack = Collections.<Double>max((Collection<? extends Double>)valuesRangeTrack);
            final List<Color> listColorTrack = new ArrayList<Color>();
            for (int l = 0; l < ChooserWizardPanel.modelTrack.getRowCount(); ++l) {
                listColorTrack.add(((JLabel)ChooserWizardPanel.modelTrack.getValueAt(l, ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1))).getBackground());
            }
            final Color[] classColorTrack = new Color[listColorTrack.size()];
            listColorTrack.<Color>toArray(classColorTrack);
            if (this.comboRegressionTrack.getSelectedIndex() == 0) {
                this.sp2Track.addScatterPlotSeriesLinear(this.comboFeatureDomainTrack.getSelectedItem().toString(), this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack);
            }
            if (this.comboRegressionTrack.getSelectedIndex() == 1) {
                this.sp2Track.addScatterPlotSeriesPolynomial(this.comboFeatureDomainTrack.getSelectedItem().toString(), this.comboFeatureRangeTrack.getSelectedItem().toString(), valuesDomainTrack, valuesRangeTrack, this.markerRangeTrack, this.markerDomainTrack, dataTrack, classColorTrack, (int)this.filterOrderTrack.getValue());
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
