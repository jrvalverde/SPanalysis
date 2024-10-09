import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.TableColumn;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import java.util.Locale;
import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableCellRenderer;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableCellEditor;
import javax.swing.AbstractCellEditor;

// 
// Decompiled by Procyon v0.5.36
// 

public class ColorEditorTrack extends AbstractCellEditor implements TableCellEditor
{
    private JPanel myPanel;
    private JPanel panelAdd;
    private JPanel panelEdit;
    private JLabel labelInitt;
    private int result;
    private int input;
    static DefaultTableModel modelC;
    static JTable tableC;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton colorButtonAdd;
    private JButton colorButtonEdit;
    private JButton okButton;
    private JButton okButtonEdit;
    private JButton cancelButton;
    private JButton cancelButtonEdit;
    private JButton okButtonAdd;
    private JButton cancelButtonAdd;
    static JList<String> featureList;
    static JList<String> classList;
    static DefaultListModel<String> modelListFeature;
    static DefaultListModel<String> modelListClass;
    private JLabel addTextAdd;
    private JLabel addTextEdit;
    private JTextField addTextFAdd;
    private JTextField addTextFEdit;
    private Color currentColorAdd;
    private Color currentColorEdit;
    private Color colorCInitial;
    private Color colorCFinal;
    private Object labelC;
    private Object colorC;
    private Object featureC;
    private String addTextInitial;
    private String addTextFinal;
    private String featureInitial;
    private String featureFinal;
    static JFrame myFrame;
    static JFrame myFrameAdd;
    static JFrame myFrameEdit;
    private Icon iconOKCell;
    private Icon iconCancelCell;
    private int indexRowC;
    
    public ColorEditorTrack(final JList<String> featureList) {
        ColorEditorTrack.featureList = featureList;
        (this.addButton = new JButton("")).setBounds(50, 100, 95, 30);
        final ImageIcon iconAdd = FirstWizardPanel.createImageIcon("images/add.png");
        final Icon iconAddCell = new ImageIcon(iconAdd.getImage().getScaledInstance(17, 15, 4));
        this.addButton.setIcon(iconAddCell);
        this.addButton.setToolTipText("Click this button to add your class-label.");
        (this.editButton = new JButton("")).setBounds(50, 100, 95, 30);
        final ImageIcon iconEdit = FirstWizardPanel.createImageIcon("images/edit.png");
        final Icon iconEditCell = new ImageIcon(iconEdit.getImage().getScaledInstance(17, 15, 4));
        this.editButton.setIcon(iconEditCell);
        this.editButton.setToolTipText("Click this button to edit your class-label.");
        (this.deleteButton = new JButton("")).setBounds(50, 100, 95, 30);
        final ImageIcon iconDelete = FirstWizardPanel.createImageIcon("images/bin.png");
        final Icon iconDeleteCell = new ImageIcon(iconDelete.getImage().getScaledInstance(22, 20, 4));
        this.deleteButton.setIcon(iconDeleteCell);
        this.deleteButton.setToolTipText("Click this button to delete your class-label.");
        (ColorEditorTrack.myFrame = new JFrame("Manage Labels")).setLocation(new Point(100, 100));
        ColorEditorTrack.myFrame.setDefaultCloseOperation(2);
        (this.myPanel = new JPanel()).setLayout(new BoxLayout(this.myPanel, 1));
        final Object[][] rowData2 = new Object[0][];
        final Object[] columnNames = { "Name", "Color", "Feature" };
        ColorEditorTrack.modelC = new DefaultTableModel(rowData2, columnNames) {
            private static final long serialVersionUID = 1L;
            
            @Override
            public boolean isCellEditable(final int row, final int col) {
                return false;
            }
            
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
        ColorEditorTrack.tableC = new JTable();
        ColorEditorTrack.tableC.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
        ColorEditorTrack.tableC.setSelectionBackground(new Color(229, 255, 204));
        ColorEditorTrack.tableC.setSelectionForeground(new Color(0, 102, 0));
        final TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(ColorEditorTrack.modelC);
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(0);
        ColorEditorTrack.tableC.setDefaultRenderer(JLabel.class, centerRenderer);
        ColorEditorTrack.tableC.setRowSorter((RowSorter<? extends TableModel>)rowSorter);
        final JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(this.addButton);
        panelButtons.add(this.editButton);
        panelButtons.add(this.deleteButton);
        ColorEditorTrack.tableC.setAutoCreateRowSorter(true);
        ColorEditorTrack.tableC.setEnabled(true);
        ColorEditorTrack.tableC.setCellSelectionEnabled(true);
        ColorEditorTrack.tableC.setRowSelectionAllowed(true);
        ColorEditorTrack.tableC.setColumnSelectionAllowed(false);
        ColorEditorTrack.tableC.setSelectionMode(2);
        ColorEditorTrack.tableC.setDefaultRenderer(JLabel.class, new Renderer());
        ColorEditorTrack.tableC.setDefaultRenderer(Color.class, new ColorRenderer(true));
        ColorEditorTrack.tableC.setModel(ColorEditorTrack.modelC);
        TableColumn column1 = null;
        column1 = ColorEditorTrack.tableC.getColumnModel().getColumn(0);
        column1.setPreferredWidth(7);
        column1.setCellRenderer((TableCellRenderer)new ResultRendererC());
        TableColumn column2 = null;
        column2 = ColorEditorTrack.tableC.getColumnModel().getColumn(1);
        column2.setPreferredWidth(5);
        column2.setCellRenderer((TableCellRenderer)new ResultRendererC());
        TableColumn column3 = null;
        column3 = ColorEditorTrack.tableC.getColumnModel().getColumn(2);
        column3.setPreferredWidth(15);
        column3.setCellRenderer((TableCellRenderer)new ResultRendererC());
        final JScrollPane scrollPane = new JScrollPane(ColorEditorTrack.tableC);
        for (int u = 0; u < ColorEditorTrack.tableC.getColumnCount(); ++u) {
            ColorEditorTrack.tableC.getColumnModel().getColumn(u).setPreferredWidth(90);
        }
        ColorEditorTrack.tableC.setRowHeight(25);
        this.myPanel.add(Box.createHorizontalStrut(15));
        this.myPanel.add(panelButtons);
        this.myPanel.add(scrollPane, "Center");
        this.myPanel.setSize(300, 150);
        this.myPanel.add(Box.createHorizontalStrut(15));
        (this.okButton = new JButton("")).setBounds(50, 100, 95, 30);
        final ImageIcon iconOk = FirstWizardPanel.createImageIcon("images/add.png");
        this.iconOKCell = new ImageIcon(iconOk.getImage().getScaledInstance(17, 15, 4));
        this.okButton.setIcon(this.iconOKCell);
        this.okButton.setToolTipText("Click this button to edit your color selection.");
        (this.cancelButton = new JButton("")).setBounds(50, 100, 95, 30);
        final ImageIcon iconCancel = FirstWizardPanel.createImageIcon("images/cancel.png");
        this.iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(17, 15, 4));
        this.cancelButton.setIcon(this.iconCancelCell);
        this.cancelButton.setToolTipText("Click this button to cancel your color selection.");
        final JPanel panelOkCancel = new JPanel();
        panelOkCancel.setLayout(new FlowLayout());
        panelOkCancel.add(this.okButton);
        panelOkCancel.add(this.cancelButton);
        this.myPanel.add(panelOkCancel);
        ColorEditorTrack.myFrame.getContentPane().add(this.myPanel);
        ColorEditorTrack.myFrame.pack();
        ColorEditorTrack.myFrame.setLocationByPlatform(true);
        (this.panelAdd = new JPanel()).setPreferredSize(new Dimension(200, 100));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        this.panelAdd.setLayout(new FlowLayout());
        this.addTextAdd = new JLabel("Label Name: ");
        this.addTextFAdd = new JTextField(8);
        panel1.add(this.addTextAdd);
        panel1.add(this.addTextFAdd);
        final JLabel pickC = new JLabel("Pick a color: ");
        this.panelAdd.add(pickC);
        (this.colorButtonAdd = new JButton()).setPreferredSize(new Dimension(200, 75));
        this.panelAdd.add(this.colorButtonAdd);
        (this.okButtonAdd = new JButton("")).setBounds(50, 100, 95, 30);
        this.okButtonAdd.setIcon(this.iconOKCell);
        this.okButtonAdd.setToolTipText("Click this button to edit your color selection.");
        (this.cancelButtonAdd = new JButton("")).setBounds(50, 100, 95, 30);
        this.cancelButtonAdd.setIcon(this.iconCancelCell);
        this.cancelButtonAdd.setToolTipText("Click this button to cancel your color selection.");
        final JPanel panelOkCancelAdd = new JPanel();
        panelOkCancelAdd.setLayout(new FlowLayout());
        panelOkCancelAdd.add(this.okButtonAdd);
        panelOkCancelAdd.add(this.cancelButtonAdd);
        ColorEditorTrack.myFrameAdd = new JFrame("Add Label");
        final JPanel mainPanel = new JPanel();
        mainPanel.add(panel1);
        mainPanel.add(this.panelAdd);
        mainPanel.add(panelOkCancelAdd);
        ColorEditorTrack.myFrameAdd.setPreferredSize(new Dimension(250, 250));
        ColorEditorTrack.myFrameAdd.getContentPane().add(mainPanel);
        ColorEditorTrack.myFrameAdd.pack();
        ColorEditorTrack.myFrameAdd.setLocationByPlatform(true);
        final JPanel panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(200, 100));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        this.addTextEdit = new JLabel("Label Name: ");
        this.addTextFEdit = new JTextField(8);
        panel3.add(this.addTextEdit);
        panel3.add(this.addTextFEdit);
        final JLabel pickEdit = new JLabel("Pick a Color: ");
        panel2.add(pickEdit);
        (this.colorButtonEdit = new JButton()).setPreferredSize(new Dimension(200, 75));
        panel2.add(this.colorButtonEdit);
        (this.okButtonEdit = new JButton("")).setBounds(50, 100, 95, 30);
        this.okButtonEdit.setIcon(this.iconOKCell);
        this.okButtonEdit.setToolTipText("Click this button to edit your color selection.");
        (this.cancelButtonEdit = new JButton("")).setBounds(50, 100, 95, 30);
        this.cancelButtonEdit.setIcon(this.iconCancelCell);
        this.cancelButtonEdit.setToolTipText("Click this button to cancel your color selection.");
        final JPanel panelOkCancelEdit = new JPanel();
        panelOkCancelEdit.setLayout(new FlowLayout());
        panelOkCancelEdit.add(this.okButtonEdit);
        panelOkCancelEdit.add(this.cancelButtonEdit);
        ColorEditorTrack.myFrameEdit = new JFrame("Edit Label");
        final JPanel mainPanelEdit = new JPanel();
        mainPanelEdit.add(panel3);
        mainPanelEdit.add(panel2);
        mainPanelEdit.add(panelOkCancelEdit);
        ColorEditorTrack.myFrameEdit.setPreferredSize(new Dimension(250, 250));
        ColorEditorTrack.myFrameEdit.getContentPane().add(mainPanelEdit);
        ColorEditorTrack.myFrameEdit.pack();
        ColorEditorTrack.myFrameEdit.setLocationByPlatform(true);
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorTrack.tableC.setRowHeight(featureList.getHeight());
                ColorEditorTrack.myFrameAdd.setVisible(true);
            }
        });
        this.okButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JLabel labelString = new JLabel();
                final JLabel labelColor = new JLabel();
                final JLabel labelFeature = new JLabel();
                labelColor.setText("");
                labelColor.setBackground(ColorEditorTrack.this.currentColorAdd);
                labelString.setText(ColorEditorTrack.this.addTextFAdd.getText());
                labelString.setHorizontalAlignment(0);
                labelString.setBackground(ColorEditorTrack.this.currentColorAdd);
                labelColor.setOpaque(true);
                final StringBuilder filterItems = new StringBuilder();
                for (int x = 0; x < featureList.getModel().getSize(); ++x) {
                    filterItems.append(featureList.getModel().getElementAt(x)).append("<br>");
                }
                labelFeature.setText("<html>" + filterItems.toString() + "</html>");
                ColorEditorTrack.modelC.addRow(new Object[] { labelString, labelColor, labelFeature });
                ColorEditorTrack.modelC.fireTableDataChanged();
                ColorEditorTrack.tableC.repaint();
                ColorEditorTrack.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameAdd, 201));
            }
        });
        this.cancelButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorTrack.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameAdd, 201));
            }
        });
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorTrack.myFrameEdit.setVisible(true);
                ColorEditorTrack.access$2(ColorEditorTrack.this, ColorEditorTrack.tableC.getSelectedRow());
                if (ColorEditorTrack.tableC.getSelectedRowCount() == 0) {
                    return;
                }
                if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
                    ColorEditorTrack.access$3(ColorEditorTrack.this, new Object());
                    ColorEditorTrack.access$4(ColorEditorTrack.this, new Object());
                    ColorEditorTrack.access$3(ColorEditorTrack.this, ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(0)));
                    ColorEditorTrack.access$4(ColorEditorTrack.this, ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(1)));
                    ColorEditorTrack.access$7(ColorEditorTrack.this, ((JLabel)ColorEditorTrack.this.labelC).getText());
                    ColorEditorTrack.access$9(ColorEditorTrack.this, ((JLabel)ColorEditorTrack.this.colorC).getBackground());
                }
                ColorEditorTrack.this.colorButtonEdit.setBackground(((JLabel)ColorEditorTrack.this.colorC).getBackground());
                ColorEditorTrack.access$11(ColorEditorTrack.this, ((JLabel)ColorEditorTrack.this.colorC).getBackground());
                ColorEditorTrack.this.colorButtonEdit.setContentAreaFilled(false);
                ColorEditorTrack.this.colorButtonEdit.setOpaque(true);
                ColorEditorTrack.this.addTextFEdit.setText(((JLabel)ColorEditorTrack.this.labelC).getText());
            }
        });
        this.colorButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Locale.setDefault(Locale.ENGLISH);
                JComponent.setDefaultLocale(Locale.ENGLISH);
                JComponent.setDefaultLocale(Locale.getDefault());
                ColorEditorTrack.access$14(ColorEditorTrack.this, JColorChooser.showDialog(null, "Pick a Color: ", ColorEditorTrack.this.colorButtonAdd.getBackground()));
                if (ColorEditorTrack.this.currentColorAdd != null) {
                    ColorEditorTrack.this.colorButtonAdd.setBackground(ColorEditorTrack.this.currentColorAdd);
                }
            }
        });
        this.okButtonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JLabel labelString = new JLabel();
                final JLabel labelColor = new JLabel();
                labelColor.setText("");
                labelColor.setBackground(ColorEditorTrack.this.currentColorEdit);
                labelString.setText(ColorEditorTrack.this.addTextFEdit.getText());
                labelString.setHorizontalAlignment(0);
                labelString.setBackground(ColorEditorTrack.this.currentColorEdit);
                labelColor.setOpaque(true);
                ColorEditorTrack.access$16(ColorEditorTrack.this, labelString.getText());
                ColorEditorTrack.access$17(ColorEditorTrack.this, labelColor.getBackground());
                if (!ColorEditorTrack.this.addTextFinal.equals(ColorEditorTrack.this.addTextInitial)) {
                    ColorEditorTrack.modelC.setValueAt(labelString, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(0));
                }
                if (ColorEditorTrack.this.addTextFinal.equals(ColorEditorTrack.this.addTextInitial)) {
                    ColorEditorTrack.modelC.setValueAt(ColorEditorTrack.this.labelC, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(0));
                }
                if (ColorEditorTrack.this.currentColorEdit != ColorEditorTrack.this.colorCInitial) {
                    ColorEditorTrack.modelC.setValueAt(labelColor, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(1));
                }
                if (ColorEditorTrack.this.currentColorEdit == ColorEditorTrack.this.colorCInitial) {
                    ColorEditorTrack.modelC.setValueAt(ColorEditorTrack.this.colorC, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(1));
                }
                ColorEditorTrack.modelC.fireTableCellUpdated(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(0));
                ColorEditorTrack.modelC.fireTableCellUpdated(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(1));
                ColorEditorTrack.tableC.repaint();
                ColorEditorTrack.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameEdit, 201));
            }
        });
        this.cancelButtonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorTrack.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameEdit, 201));
            }
        });
    }
    
    public void setClassAction() {
        this.colorButtonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Locale.setDefault(Locale.ENGLISH);
                JComponent.setDefaultLocale(Locale.ENGLISH);
                JComponent.setDefaultLocale(Locale.getDefault());
                ColorEditorTrack.access$11(ColorEditorTrack.this, JColorChooser.showDialog(null, "Pick a Color: ", ColorEditorTrack.this.colorButtonEdit.getBackground()));
                if (ColorEditorTrack.this.currentColorEdit != null) {
                    ColorEditorTrack.this.colorButtonEdit.setBackground(ColorEditorTrack.this.currentColorEdit);
                }
            }
        });
        this.okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (ColorEditorTrack.tableC.getSelectedRowCount() <= 0) {
                    ColorEditorTrack.myFrame.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrame, 201));
                }
                if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
                    final List<String> listClasses = new ArrayList<String>();
                    ColorEditorTrack.classList = ChooserWizardPanel.classList;
                    ColorEditorTrack.modelListClass = ChooserWizardPanel.modelListClass;
                    final int selectedRow = ColorEditorTrack.tableC.getSelectedRow();
                    if (ColorEditorTrack.modelListClass.getSize() == 0) {
                        ColorEditorTrack.modelListClass.addElement(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
                        final JLabel labelsTableC = new JLabel();
                        for (int i = 0; i < ColorEditorTrack.tableC.getModel().getRowCount(); ++i) {
                            labelsTableC.setText(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
                            labelsTableC.setHorizontalAlignment(0);
                            labelsTableC.setBackground(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, ColorEditorTrack.tableC.convertColumnIndexToModel(1))).getBackground());
                            labelsTableC.setOpaque(true);
                        }
                        final String[] filterFeature = ((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, ColorEditorTrack.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "").replace("<html>", "").split("<br>");
                        final List<String> features = new ArrayList<String>();
                        final List<String> featureMin = new ArrayList<String>();
                        final List<String> featureMax = new ArrayList<String>();
                        for (int j = 0; j < filterFeature.length; ++j) {
                            features.add(filterFeature[j].substring(0, filterFeature[j].indexOf(":")));
                            featureMin.add(filterFeature[j].substring(filterFeature[j].indexOf("[") + 1, filterFeature[j].indexOf(",")));
                            featureMax.add(filterFeature[j].substring(filterFeature[j].indexOf(",") + 1, filterFeature[j].indexOf("]")));
                        }
                        for (int x = 0; x < ChooserWizardPanel.modelTrack.getRowCount(); ++x) {
                            for (int u = 0; u < features.size(); ++u) {
                                if (Double.parseDouble(ChooserWizardPanel.tableTrack.getModel().getValueAt(x, ChooserWizardPanel.tableTrack.getColumn(features.get(u)).getModelIndex()).toString()) >= Double.parseDouble(featureMin.get(u)) && Double.parseDouble(ChooserWizardPanel.tableTrack.getModel().getValueAt(x, ChooserWizardPanel.tableTrack.getColumn(features.get(u)).getModelIndex()).toString()) <= Double.parseDouble(featureMax.get(u))) {
                                    ChooserWizardPanel.tableTrack.getModel().setValueAt(labelsTableC, ChooserWizardPanel.tableTrack.convertRowIndexToModel(x), ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1));
                                }
                            }
                        }
                    }
                    if (ColorEditorTrack.modelListClass.getSize() >= 1) {
                        for (int k = 0; k < ColorEditorTrack.modelListClass.getSize(); ++k) {
                            listClasses.add(ColorEditorTrack.modelListClass.getElementAt(k));
                        }
                        if (!listClasses.contains(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText())) {
                            ColorEditorTrack.modelListClass.addElement(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
                            final JLabel labelsTableC = new JLabel();
                            for (int i = 0; i < ColorEditorTrack.tableC.getModel().getRowCount(); ++i) {
                                labelsTableC.setText(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
                                labelsTableC.setHorizontalAlignment(0);
                                labelsTableC.setBackground(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, ColorEditorTrack.tableC.convertColumnIndexToModel(1))).getBackground());
                                labelsTableC.setOpaque(true);
                            }
                            final String[] filterFeature = ((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, ColorEditorTrack.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "").replace("<html>", "").split("<br>");
                            final List<String> features = new ArrayList<String>();
                            final List<String> featureMin = new ArrayList<String>();
                            final List<String> featureMax = new ArrayList<String>();
                            for (int j = 0; j < filterFeature.length; ++j) {
                                features.add(filterFeature[j].substring(0, filterFeature[j].indexOf(":")));
                                featureMin.add(filterFeature[j].substring(filterFeature[j].indexOf("[") + 1, filterFeature[j].indexOf(",")));
                                featureMax.add(filterFeature[j].substring(filterFeature[j].indexOf(",") + 1, filterFeature[j].indexOf("]")));
                            }
                            for (int x = 0; x < ChooserWizardPanel.modelTrack.getRowCount(); ++x) {
                                for (int u = 0; u < features.size(); ++u) {
                                    if (Double.parseDouble(ChooserWizardPanel.tableTrack.getModel().getValueAt(x, ChooserWizardPanel.tableTrack.getColumn(features.get(u)).getModelIndex()).toString()) >= Double.parseDouble(featureMin.get(u)) && Double.parseDouble(ChooserWizardPanel.tableTrack.getModel().getValueAt(x, ChooserWizardPanel.tableTrack.getColumn(features.get(u)).getModelIndex()).toString()) <= Double.parseDouble(featureMax.get(u))) {
                                        ChooserWizardPanel.tableTrack.getModel().setValueAt(labelsTableC, ChooserWizardPanel.tableTrack.convertRowIndexToModel(x), ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1));
                                    }
                                }
                            }
                        }
                        if (listClasses.contains(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText())) {
                            ColorEditorTrack.myFrame.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrame, 201));
                        }
                    }
                }
            }
        });
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorTrack.myFrame.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrame, 201));
            }
        });
        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Object labelC = null;
                Object colorC = null;
                Object[] labelsC = null;
                Object[] colorsC = null;
                final int[] indexesRowC = ColorEditorTrack.tableC.getSelectedRows();
                final int indexRowC = ColorEditorTrack.tableC.getSelectedRow();
                if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
                    labelC = new Object();
                    colorC = new Object();
                    labelC = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(0));
                    colorC = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexRowC), ColorEditorTrack.tableC.convertColumnIndexToModel(1));
                }
                labelsC = new Object[indexesRowC.length];
                colorsC = new Object[indexesRowC.length];
                if (ColorEditorTrack.tableC.getSelectedRowCount() > 1) {
                    for (int k = 0; k < indexesRowC.length; ++k) {
                        labelsC[k] = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexesRowC[k]), ColorEditorTrack.tableC.convertColumnIndexToModel(0));
                        colorsC[k] = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexesRowC[k]), ColorEditorTrack.tableC.convertColumnIndexToModel(1));
                    }
                }
                Locale.setDefault(Locale.ENGLISH);
                final Locale english = Locale.ENGLISH;
                JComponent.setDefaultLocale(Locale.getDefault());
                if (ColorEditorTrack.tableC.getSelectedRowCount() > 1) {
                    final String[] labelsCtoString = new String[indexesRowC.length];
                    for (int i = 0; i < indexesRowC.length; ++i) {
                        labelsCtoString[i] = ((JLabel)labelsC[i]).getText();
                    }
                    ColorEditorTrack.access$21(ColorEditorTrack.this, JOptionPane.showConfirmDialog(null, "Are you sure to delete the selected labels?", "Delete a label", 1, 0));
                    if (ColorEditorTrack.this.input == 0) {
                        for (int f = 0; f < indexesRowC.length; ++f) {
                            ColorEditorTrack.modelC.removeRow(indexesRowC[f] - f);
                        }
                        ColorEditorTrack.modelC.fireTableDataChanged();
                        ColorEditorTrack.tableC.repaint();
                    }
                    if (ColorEditorTrack.this.input == 1) {
                        return;
                    }
                    if (ColorEditorTrack.this.input == 2) {
                        return;
                    }
                }
                if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
                    final String labelCtoString = ((JLabel)labelC).getText();
                    ColorEditorTrack.access$21(ColorEditorTrack.this, JOptionPane.showConfirmDialog(null, "Are you sure to delete the following label?----- " + labelCtoString, "Delete a label", 1, 0));
                    if (ColorEditorTrack.this.input == 0) {
                        ColorEditorTrack.modelC.removeRow(indexRowC);
                        ColorEditorTrack.modelC.fireTableDataChanged();
                    }
                    if (ColorEditorTrack.this.input == 1) {
                        return;
                    }
                    if (ColorEditorTrack.this.input == 2) {
                        return;
                    }
                }
            }
        });
    }
    
    public Object getCellEditorValueAdd() {
        return this.currentColorAdd;
    }
    
    public Object getCellEditorValueEdit() {
        return this.currentColorEdit;
    }
    
    public Component getTableCellEditorComponentAdd(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
        this.currentColorAdd = (Color)value;
        return this.colorButtonAdd;
    }
    
    public Component getTableCellEditorComponentEdit(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
        this.currentColorEdit = (Color)value;
        return this.colorButtonEdit;
    }
    
    @Override
    public Component getTableCellEditorComponent(final JTable arg0, final Object arg1, final boolean arg2, final int arg3, final int arg4) {
        return null;
    }
    
    @Override
    public Object getCellEditorValue() {
        return null;
    }
    
    static /* synthetic */ void access$2(final ColorEditorTrack colorEditorTrack, final int indexRowC) {
        colorEditorTrack.indexRowC = indexRowC;
    }
    
    static /* synthetic */ void access$3(final ColorEditorTrack colorEditorTrack, final Object labelC) {
        colorEditorTrack.labelC = labelC;
    }
    
    static /* synthetic */ void access$4(final ColorEditorTrack colorEditorTrack, final Object colorC) {
        colorEditorTrack.colorC = colorC;
    }
    
    static /* synthetic */ void access$7(final ColorEditorTrack colorEditorTrack, final String addTextInitial) {
        colorEditorTrack.addTextInitial = addTextInitial;
    }
    
    static /* synthetic */ void access$9(final ColorEditorTrack colorEditorTrack, final Color colorCInitial) {
        colorEditorTrack.colorCInitial = colorCInitial;
    }
    
    static /* synthetic */ void access$11(final ColorEditorTrack colorEditorTrack, final Color currentColorEdit) {
        colorEditorTrack.currentColorEdit = currentColorEdit;
    }
    
    static /* synthetic */ void access$14(final ColorEditorTrack colorEditorTrack, final Color currentColorAdd) {
        colorEditorTrack.currentColorAdd = currentColorAdd;
    }
    
    static /* synthetic */ void access$16(final ColorEditorTrack colorEditorTrack, final String addTextFinal) {
        colorEditorTrack.addTextFinal = addTextFinal;
    }
    
    static /* synthetic */ void access$17(final ColorEditorTrack colorEditorTrack, final Color colorCFinal) {
        colorEditorTrack.colorCFinal = colorCFinal;
    }
    
    static /* synthetic */ void access$21(final ColorEditorTrack colorEditorTrack, final int input) {
        colorEditorTrack.input = input;
    }
}
