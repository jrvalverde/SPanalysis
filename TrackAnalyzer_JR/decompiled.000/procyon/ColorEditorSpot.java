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

public class ColorEditorSpot extends AbstractCellEditor implements TableCellEditor
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
    
    public ColorEditorSpot(final JList<String> featureList) {
        ColorEditorSpot.featureList = featureList;
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
        (ColorEditorSpot.myFrame = new JFrame("Manage Labels")).setLocation(new Point(100, 100));
        ColorEditorSpot.myFrame.setDefaultCloseOperation(2);
        (this.myPanel = new JPanel()).setLayout(new BoxLayout(this.myPanel, 1));
        final Object[][] rowData2 = new Object[0][];
        final Object[] columnNames = { "Name", "Color", "Feature" };
        ColorEditorSpot.modelC = new DefaultTableModel(rowData2, columnNames) {
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
        ColorEditorSpot.tableC = new JTable();
        ColorEditorSpot.tableC.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
        ColorEditorSpot.tableC.setSelectionBackground(new Color(229, 255, 204));
        ColorEditorSpot.tableC.setSelectionForeground(new Color(0, 102, 0));
        final TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(ColorEditorSpot.modelC);
        final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(0);
        ColorEditorSpot.tableC.setDefaultRenderer(JLabel.class, centerRenderer);
        ColorEditorSpot.tableC.setRowSorter((RowSorter<? extends TableModel>)rowSorter);
        final JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(this.addButton);
        panelButtons.add(this.editButton);
        panelButtons.add(this.deleteButton);
        ColorEditorSpot.tableC.setAutoCreateRowSorter(true);
        ColorEditorSpot.tableC.setEnabled(true);
        ColorEditorSpot.tableC.setCellSelectionEnabled(true);
        ColorEditorSpot.tableC.setRowSelectionAllowed(true);
        ColorEditorSpot.tableC.setColumnSelectionAllowed(false);
        ColorEditorSpot.tableC.setSelectionMode(2);
        ColorEditorSpot.tableC.setDefaultRenderer(JLabel.class, new Renderer());
        ColorEditorSpot.tableC.setDefaultRenderer(Color.class, new ColorRenderer(true));
        ColorEditorSpot.tableC.setModel(ColorEditorSpot.modelC);
        TableColumn column1 = null;
        column1 = ColorEditorSpot.tableC.getColumnModel().getColumn(0);
        column1.setPreferredWidth(7);
        column1.setCellRenderer((TableCellRenderer)new ResultRendererC());
        TableColumn column2 = null;
        column2 = ColorEditorSpot.tableC.getColumnModel().getColumn(1);
        column2.setPreferredWidth(5);
        column2.setCellRenderer((TableCellRenderer)new ResultRendererC());
        TableColumn column3 = null;
        column3 = ColorEditorSpot.tableC.getColumnModel().getColumn(2);
        column3.setPreferredWidth(15);
        column3.setCellRenderer((TableCellRenderer)new ResultRendererC());
        final JScrollPane scrollPane = new JScrollPane(ColorEditorSpot.tableC);
        for (int u = 0; u < ColorEditorSpot.tableC.getColumnCount(); ++u) {
            ColorEditorSpot.tableC.getColumnModel().getColumn(u).setPreferredWidth(90);
        }
        ColorEditorSpot.tableC.setRowHeight(25);
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
        ColorEditorSpot.myFrame.getContentPane().add(this.myPanel);
        ColorEditorSpot.myFrame.pack();
        ColorEditorSpot.myFrame.setLocationByPlatform(true);
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
        ColorEditorSpot.myFrameAdd = new JFrame("Add Label");
        final JPanel mainPanel = new JPanel();
        mainPanel.add(panel1);
        mainPanel.add(this.panelAdd);
        mainPanel.add(panelOkCancelAdd);
        ColorEditorSpot.myFrameAdd.setPreferredSize(new Dimension(250, 250));
        ColorEditorSpot.myFrameAdd.getContentPane().add(mainPanel);
        ColorEditorSpot.myFrameAdd.pack();
        ColorEditorSpot.myFrameAdd.setLocationByPlatform(true);
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
        ColorEditorSpot.myFrameEdit = new JFrame("Edit Label");
        final JPanel mainPanelEdit = new JPanel();
        mainPanelEdit.add(panel3);
        mainPanelEdit.add(panel2);
        mainPanelEdit.add(panelOkCancelEdit);
        ColorEditorSpot.myFrameEdit.setPreferredSize(new Dimension(250, 250));
        ColorEditorSpot.myFrameEdit.getContentPane().add(mainPanelEdit);
        ColorEditorSpot.myFrameEdit.pack();
        ColorEditorSpot.myFrameEdit.setLocationByPlatform(true);
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorSpot.tableC.setRowHeight(featureList.getHeight());
                ColorEditorSpot.myFrameAdd.setVisible(true);
            }
        });
        this.okButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JLabel labelString = new JLabel();
                final JLabel labelColor = new JLabel();
                final JLabel labelFeature = new JLabel();
                labelColor.setText("");
                labelColor.setBackground(ColorEditorSpot.this.currentColorAdd);
                labelString.setText(ColorEditorSpot.this.addTextFAdd.getText());
                labelString.setHorizontalAlignment(0);
                labelString.setBackground(ColorEditorSpot.this.currentColorAdd);
                labelColor.setOpaque(true);
                final StringBuilder filterItems = new StringBuilder();
                for (int x = 0; x < featureList.getModel().getSize(); ++x) {
                    filterItems.append(featureList.getModel().getElementAt(x)).append("<br>");
                }
                labelFeature.setText("<html>" + filterItems.toString() + "</html>");
                ColorEditorSpot.modelC.addRow(new Object[] { labelString, labelColor, labelFeature });
                ColorEditorSpot.modelC.fireTableDataChanged();
                ColorEditorSpot.tableC.repaint();
                ColorEditorSpot.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameAdd, 201));
            }
        });
        this.cancelButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorSpot.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameAdd, 201));
            }
        });
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorSpot.myFrameEdit.setVisible(true);
                ColorEditorSpot.access$2(ColorEditorSpot.this, ColorEditorSpot.tableC.getSelectedRow());
                if (ColorEditorSpot.tableC.getSelectedRowCount() == 0) {
                    return;
                }
                if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
                    ColorEditorSpot.access$3(ColorEditorSpot.this, new Object());
                    ColorEditorSpot.access$4(ColorEditorSpot.this, new Object());
                    ColorEditorSpot.access$3(ColorEditorSpot.this, ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(0)));
                    ColorEditorSpot.access$4(ColorEditorSpot.this, ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(1)));
                    ColorEditorSpot.access$7(ColorEditorSpot.this, ((JLabel)ColorEditorSpot.this.labelC).getText());
                    ColorEditorSpot.access$9(ColorEditorSpot.this, ((JLabel)ColorEditorSpot.this.colorC).getBackground());
                }
                ColorEditorSpot.this.colorButtonEdit.setBackground(((JLabel)ColorEditorSpot.this.colorC).getBackground());
                ColorEditorSpot.access$11(ColorEditorSpot.this, ((JLabel)ColorEditorSpot.this.colorC).getBackground());
                ColorEditorSpot.this.colorButtonEdit.setContentAreaFilled(false);
                ColorEditorSpot.this.colorButtonEdit.setOpaque(true);
                ColorEditorSpot.this.addTextFEdit.setText(((JLabel)ColorEditorSpot.this.labelC).getText());
            }
        });
        this.colorButtonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Locale.setDefault(Locale.ENGLISH);
                JComponent.setDefaultLocale(Locale.ENGLISH);
                JComponent.setDefaultLocale(Locale.getDefault());
                ColorEditorSpot.access$14(ColorEditorSpot.this, JColorChooser.showDialog(null, "Pick a Color: ", ColorEditorSpot.this.colorButtonAdd.getBackground()));
                if (ColorEditorSpot.this.currentColorAdd != null) {
                    ColorEditorSpot.this.colorButtonAdd.setBackground(ColorEditorSpot.this.currentColorAdd);
                }
            }
        });
        this.okButtonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JLabel labelString = new JLabel();
                final JLabel labelColor = new JLabel();
                labelColor.setText("");
                labelColor.setBackground(ColorEditorSpot.this.currentColorEdit);
                labelString.setText(ColorEditorSpot.this.addTextFEdit.getText());
                labelString.setHorizontalAlignment(0);
                labelString.setBackground(ColorEditorSpot.this.currentColorEdit);
                labelColor.setOpaque(true);
                ColorEditorSpot.access$16(ColorEditorSpot.this, labelString.getText());
                ColorEditorSpot.access$17(ColorEditorSpot.this, labelColor.getBackground());
                if (!ColorEditorSpot.this.addTextFinal.equals(ColorEditorSpot.this.addTextInitial)) {
                    ColorEditorSpot.modelC.setValueAt(labelString, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
                }
                if (ColorEditorSpot.this.addTextFinal.equals(ColorEditorSpot.this.addTextInitial)) {
                    ColorEditorSpot.modelC.setValueAt(ColorEditorSpot.this.labelC, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
                }
                if (ColorEditorSpot.this.currentColorEdit != ColorEditorSpot.this.colorCInitial) {
                    ColorEditorSpot.modelC.setValueAt(labelColor, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
                }
                if (ColorEditorSpot.this.currentColorEdit == ColorEditorSpot.this.colorCInitial) {
                    ColorEditorSpot.modelC.setValueAt(ColorEditorSpot.this.colorC, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
                }
                ColorEditorSpot.modelC.fireTableCellUpdated(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
                ColorEditorSpot.modelC.fireTableCellUpdated(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
                ColorEditorSpot.tableC.repaint();
                ColorEditorSpot.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameEdit, 201));
            }
        });
        this.cancelButtonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorSpot.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameEdit, 201));
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
                ColorEditorSpot.access$11(ColorEditorSpot.this, JColorChooser.showDialog(null, "Pick a Color: ", ColorEditorSpot.this.colorButtonEdit.getBackground()));
                if (ColorEditorSpot.this.currentColorEdit != null) {
                    ColorEditorSpot.this.colorButtonEdit.setBackground(ColorEditorSpot.this.currentColorEdit);
                }
            }
        });
        this.okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (ColorEditorSpot.tableC.getSelectedRowCount() <= 0) {
                    ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
                }
                if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
                    final List<String> listClasses = new ArrayList<String>();
                    ColorEditorSpot.classList = FirstWizardPanel.classList;
                    ColorEditorSpot.modelListClass = FirstWizardPanel.modelListClass;
                    final int selectedRow = ColorEditorSpot.tableC.getSelectedRow();
                    if (ColorEditorSpot.modelListClass.getSize() == 0) {
                        ColorEditorSpot.modelListClass.addElement(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                        final JLabel labelsTableC = new JLabel();
                        for (int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); ++i) {
                            labelsTableC.setText(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                            labelsTableC.setHorizontalAlignment(0);
                            labelsTableC.setBackground(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(1))).getBackground());
                            labelsTableC.setOpaque(true);
                        }
                        final String[] filterFeature = ((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "").replace("<html>", "").split("<br>");
                        final List<String> features = new ArrayList<String>();
                        final List<String> featureMin = new ArrayList<String>();
                        final List<String> featureMax = new ArrayList<String>();
                        for (int j = 0; j < filterFeature.length; ++j) {
                            features.add(filterFeature[j].substring(0, filterFeature[j].indexOf(":")));
                            featureMin.add(filterFeature[j].substring(filterFeature[j].indexOf("[") + 1, filterFeature[j].indexOf(",")));
                            featureMax.add(filterFeature[j].substring(filterFeature[j].indexOf(",") + 1, filterFeature[j].indexOf("]")));
                        }
                        for (int x = 0; x < FirstWizardPanel.modelSpot.getRowCount(); ++x) {
                            for (int u = 0; u < features.size(); ++u) {
                                if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) >= Double.parseDouble(featureMin.get(u)) && Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) <= Double.parseDouble(featureMax.get(u))) {
                                    FirstWizardPanel.tableSpot.getModel().setValueAt(labelsTableC, FirstWizardPanel.tableSpot.convertRowIndexToModel(x), FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
                                }
                            }
                        }
                    }
                    if (ColorEditorSpot.modelListClass.getSize() >= 1) {
                        for (int k = 0; k < ColorEditorSpot.modelListClass.getSize(); ++k) {
                            listClasses.add(ColorEditorSpot.modelListClass.getElementAt(k));
                        }
                        if (!listClasses.contains(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText())) {
                            ColorEditorSpot.modelListClass.addElement(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                            final JLabel labelsTableC = new JLabel();
                            for (int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); ++i) {
                                labelsTableC.setText(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                                labelsTableC.setHorizontalAlignment(0);
                                labelsTableC.setBackground(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(1))).getBackground());
                                labelsTableC.setOpaque(true);
                            }
                            final String[] filterFeature = ((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "").replace("<html>", "").split("<br>");
                            final List<String> features = new ArrayList<String>();
                            final List<String> featureMin = new ArrayList<String>();
                            final List<String> featureMax = new ArrayList<String>();
                            for (int j = 0; j < filterFeature.length; ++j) {
                                features.add(filterFeature[j].substring(0, filterFeature[j].indexOf(":")));
                                featureMin.add(filterFeature[j].substring(filterFeature[j].indexOf("[") + 1, filterFeature[j].indexOf(",")));
                                featureMax.add(filterFeature[j].substring(filterFeature[j].indexOf(",") + 1, filterFeature[j].indexOf("]")));
                            }
                            for (int x = 0; x < FirstWizardPanel.modelSpot.getRowCount(); ++x) {
                                for (int u = 0; u < features.size(); ++u) {
                                    if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) >= Double.parseDouble(featureMin.get(u)) && Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) <= Double.parseDouble(featureMax.get(u))) {
                                        FirstWizardPanel.tableSpot.getModel().setValueAt(labelsTableC, FirstWizardPanel.tableSpot.convertRowIndexToModel(x), FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
                                    }
                                }
                            }
                        }
                        if (listClasses.contains(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText())) {
                            ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
                        }
                    }
                }
            }
        });
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
            }
        });
        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Object labelC = null;
                Object colorC = null;
                Object[] labelsC = null;
                Object[] colorsC = null;
                final int[] indexesRowC = ColorEditorSpot.tableC.getSelectedRows();
                final int indexRowC = ColorEditorSpot.tableC.getSelectedRow();
                if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
                    labelC = new Object();
                    colorC = new Object();
                    labelC = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
                    colorC = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
                }
                labelsC = new Object[indexesRowC.length];
                colorsC = new Object[indexesRowC.length];
                if (ColorEditorSpot.tableC.getSelectedRowCount() > 1) {
                    for (int k = 0; k < indexesRowC.length; ++k) {
                        labelsC[k] = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexesRowC[k]), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
                        colorsC[k] = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexesRowC[k]), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
                    }
                }
                Locale.setDefault(Locale.ENGLISH);
                final Locale english = Locale.ENGLISH;
                JComponent.setDefaultLocale(Locale.getDefault());
                if (ColorEditorSpot.tableC.getSelectedRowCount() > 1) {
                    final String[] labelsCtoString = new String[indexesRowC.length];
                    for (int i = 0; i < indexesRowC.length; ++i) {
                        labelsCtoString[i] = ((JLabel)labelsC[i]).getText();
                    }
                    ColorEditorSpot.access$21(ColorEditorSpot.this, JOptionPane.showConfirmDialog(null, "Are you sure to delete the selected labels?", "Delete a label", 1, 0));
                    if (ColorEditorSpot.this.input == 0) {
                        for (int f = 0; f < indexesRowC.length; ++f) {
                            ColorEditorSpot.modelC.removeRow(indexesRowC[f] - f);
                        }
                        ColorEditorSpot.modelC.fireTableDataChanged();
                        ColorEditorSpot.tableC.repaint();
                    }
                    if (ColorEditorSpot.this.input == 1) {
                        return;
                    }
                    if (ColorEditorSpot.this.input == 2) {
                        return;
                    }
                }
                if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
                    final String labelCtoString = ((JLabel)labelC).getText();
                    ColorEditorSpot.access$21(ColorEditorSpot.this, JOptionPane.showConfirmDialog(null, "Are you sure to delete the following label?----- " + labelCtoString, "Delete a label", 1, 0));
                    if (ColorEditorSpot.this.input == 0) {
                        ColorEditorSpot.modelC.removeRow(indexRowC);
                        ColorEditorSpot.modelC.fireTableDataChanged();
                    }
                    if (ColorEditorSpot.this.input == 1) {
                        return;
                    }
                    if (ColorEditorSpot.this.input == 2) {
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
    
    static /* synthetic */ void access$2(final ColorEditorSpot colorEditorSpot, final int indexRowC) {
        colorEditorSpot.indexRowC = indexRowC;
    }
    
    static /* synthetic */ void access$3(final ColorEditorSpot colorEditorSpot, final Object labelC) {
        colorEditorSpot.labelC = labelC;
    }
    
    static /* synthetic */ void access$4(final ColorEditorSpot colorEditorSpot, final Object colorC) {
        colorEditorSpot.colorC = colorC;
    }
    
    static /* synthetic */ void access$7(final ColorEditorSpot colorEditorSpot, final String addTextInitial) {
        colorEditorSpot.addTextInitial = addTextInitial;
    }
    
    static /* synthetic */ void access$9(final ColorEditorSpot colorEditorSpot, final Color colorCInitial) {
        colorEditorSpot.colorCInitial = colorCInitial;
    }
    
    static /* synthetic */ void access$11(final ColorEditorSpot colorEditorSpot, final Color currentColorEdit) {
        colorEditorSpot.currentColorEdit = currentColorEdit;
    }
    
    static /* synthetic */ void access$14(final ColorEditorSpot colorEditorSpot, final Color currentColorAdd) {
        colorEditorSpot.currentColorAdd = currentColorAdd;
    }
    
    static /* synthetic */ void access$16(final ColorEditorSpot colorEditorSpot, final String addTextFinal) {
        colorEditorSpot.addTextFinal = addTextFinal;
    }
    
    static /* synthetic */ void access$17(final ColorEditorSpot colorEditorSpot, final Color colorCFinal) {
        colorEditorSpot.colorCFinal = colorCFinal;
    }
    
    static /* synthetic */ void access$21(final ColorEditorSpot colorEditorSpot, final int input) {
        colorEditorSpot.input = input;
    }
}
