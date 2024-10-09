import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ColorEditorSpot extends AbstractCellEditor implements TableCellEditor {
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
      this.addButton = new JButton("");
      this.addButton.setBounds(50, 100, 95, 30);
      ImageIcon iconAdd = this.createImageIcon("images/add.png");
      Icon iconAddCell = new ImageIcon(iconAdd.getImage().getScaledInstance(17, 15, 4));
      this.addButton.setIcon(iconAddCell);
      this.addButton.setToolTipText("Click this button to add your class-label.");
      this.editButton = new JButton("");
      this.editButton.setBounds(50, 100, 95, 30);
      ImageIcon iconEdit = this.createImageIcon("images/edit.png");
      Icon iconEditCell = new ImageIcon(iconEdit.getImage().getScaledInstance(17, 15, 4));
      this.editButton.setIcon(iconEditCell);
      this.editButton.setToolTipText("Click this button to edit your class-label.");
      this.deleteButton = new JButton("");
      this.deleteButton.setBounds(50, 100, 95, 30);
      ImageIcon iconDelete = this.createImageIcon("images/bin.png");
      Icon iconDeleteCell = new ImageIcon(iconDelete.getImage().getScaledInstance(22, 20, 4));
      this.deleteButton.setIcon(iconDeleteCell);
      this.deleteButton.setToolTipText("Click this button to delete your class-label.");
      myFrame = new JFrame("Manage Labels");
      myFrame.setLocation(new Point(100, 100));
      myFrame.setDefaultCloseOperation(2);
      this.myPanel = new JPanel();
      this.myPanel.setLayout(new BoxLayout(this.myPanel, 1));
      Object[][] rowData2 = new Object[0][];
      Object[] columnNames = new Object[]{"Name", "Color", "Feature"};
      modelC = new DefaultTableModel(rowData2, columnNames) {
         private static final long serialVersionUID = 1L;

         public boolean isCellEditable(int row, int col) {
            return false;
         }

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
      tableC = new JTable();
      tableC.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
      tableC.setSelectionBackground(new Color(229, 255, 204));
      tableC.setSelectionForeground(new Color(0, 102, 0));
      TableRowSorter<TableModel> rowSorter = new TableRowSorter(modelC);
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(0);
      tableC.setDefaultRenderer(JLabel.class, centerRenderer);
      tableC.setRowSorter(rowSorter);
      JPanel panelButtons = new JPanel();
      panelButtons.setLayout(new FlowLayout());
      panelButtons.add(this.addButton);
      panelButtons.add(this.editButton);
      panelButtons.add(this.deleteButton);
      tableC.setAutoCreateRowSorter(true);
      tableC.setEnabled(true);
      tableC.setCellSelectionEnabled(true);
      tableC.setRowSelectionAllowed(true);
      tableC.setColumnSelectionAllowed(false);
      tableC.setSelectionMode(2);
      tableC.setDefaultRenderer(JLabel.class, new Renderer());
      tableC.setDefaultRenderer(Color.class, new ColorRenderer(true));
      tableC.setModel(modelC);
      TableColumn column1 = null;
      column1 = tableC.getColumnModel().getColumn(0);
      column1.setPreferredWidth(7);
      column1.setCellRenderer(new ResultRendererC());
      TableColumn column2 = null;
      column2 = tableC.getColumnModel().getColumn(1);
      column2.setPreferredWidth(5);
      column2.setCellRenderer(new ResultRendererC());
      TableColumn column3 = null;
      column3 = tableC.getColumnModel().getColumn(2);
      column3.setPreferredWidth(15);
      column3.setCellRenderer(new ResultRendererC());
      JScrollPane scrollPane = new JScrollPane(tableC);

      for(int u = 0; u < tableC.getColumnCount(); ++u) {
         tableC.getColumnModel().getColumn(u).setPreferredWidth(90);
      }

      tableC.setRowHeight(25);
      this.myPanel.add(Box.createHorizontalStrut(15));
      this.myPanel.add(panelButtons);
      this.myPanel.add(scrollPane, "Center");
      this.myPanel.setSize(300, 150);
      this.myPanel.add(Box.createHorizontalStrut(15));
      this.okButton = new JButton("");
      this.okButton.setBounds(50, 100, 95, 30);
      ImageIcon iconOk = this.createImageIcon("images/add.png");
      this.iconOKCell = new ImageIcon(iconOk.getImage().getScaledInstance(17, 15, 4));
      this.okButton.setIcon(this.iconOKCell);
      this.okButton.setToolTipText("Click this button to edit your color selection.");
      this.cancelButton = new JButton("");
      this.cancelButton.setBounds(50, 100, 95, 30);
      ImageIcon iconCancel = this.createImageIcon("images/cancel.png");
      this.iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(17, 15, 4));
      this.cancelButton.setIcon(this.iconCancelCell);
      this.cancelButton.setToolTipText("Click this button to cancel your color selection.");
      JPanel panelOkCancel = new JPanel();
      panelOkCancel.setLayout(new FlowLayout());
      panelOkCancel.add(this.okButton);
      panelOkCancel.add(this.cancelButton);
      this.myPanel.add(panelOkCancel);
      myFrame.getContentPane().add(this.myPanel);
      myFrame.pack();
      myFrame.setLocationByPlatform(true);
      this.panelAdd = new JPanel();
      this.panelAdd.setPreferredSize(new Dimension(200, 100));
      JPanel panel1 = new JPanel();
      panel1.setLayout(new FlowLayout());
      this.panelAdd.setLayout(new FlowLayout());
      this.addTextAdd = new JLabel("Label Name: ");
      this.addTextFAdd = new JTextField(8);
      panel1.add(this.addTextAdd);
      panel1.add(this.addTextFAdd);
      JLabel pickC = new JLabel("Pick a color: ");
      this.panelAdd.add(pickC);
      this.colorButtonAdd = new JButton();
      this.colorButtonAdd.setPreferredSize(new Dimension(200, 75));
      this.panelAdd.add(this.colorButtonAdd);
      this.okButtonAdd = new JButton("");
      this.okButtonAdd.setBounds(50, 100, 95, 30);
      this.okButtonAdd.setIcon(this.iconOKCell);
      this.okButtonAdd.setToolTipText("Click this button to edit your color selection.");
      this.cancelButtonAdd = new JButton("");
      this.cancelButtonAdd.setBounds(50, 100, 95, 30);
      this.cancelButtonAdd.setIcon(this.iconCancelCell);
      this.cancelButtonAdd.setToolTipText("Click this button to cancel your color selection.");
      JPanel panelOkCancelAdd = new JPanel();
      panelOkCancelAdd.setLayout(new FlowLayout());
      panelOkCancelAdd.add(this.okButtonAdd);
      panelOkCancelAdd.add(this.cancelButtonAdd);
      myFrameAdd = new JFrame("Add Label");
      JPanel mainPanel = new JPanel();
      mainPanel.add(panel1);
      mainPanel.add(this.panelAdd);
      mainPanel.add(panelOkCancelAdd);
      myFrameAdd.setPreferredSize(new Dimension(250, 250));
      myFrameAdd.getContentPane().add(mainPanel);
      myFrameAdd.pack();
      myFrameAdd.setLocationByPlatform(true);
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(200, 100));
      JPanel panel2 = new JPanel();
      panel2.setLayout(new FlowLayout());
      panel.setLayout(new FlowLayout());
      this.addTextEdit = new JLabel("Label Name: ");
      this.addTextFEdit = new JTextField(8);
      panel2.add(this.addTextEdit);
      panel2.add(this.addTextFEdit);
      JLabel pickEdit = new JLabel("Pick a Color: ");
      panel.add(pickEdit);
      this.colorButtonEdit = new JButton();
      this.colorButtonEdit.setPreferredSize(new Dimension(200, 75));
      panel.add(this.colorButtonEdit);
      this.okButtonEdit = new JButton("");
      this.okButtonEdit.setBounds(50, 100, 95, 30);
      this.okButtonEdit.setIcon(this.iconOKCell);
      this.okButtonEdit.setToolTipText("Click this button to edit your color selection.");
      this.cancelButtonEdit = new JButton("");
      this.cancelButtonEdit.setBounds(50, 100, 95, 30);
      this.cancelButtonEdit.setIcon(this.iconCancelCell);
      this.cancelButtonEdit.setToolTipText("Click this button to cancel your color selection.");
      JPanel panelOkCancelEdit = new JPanel();
      panelOkCancelEdit.setLayout(new FlowLayout());
      panelOkCancelEdit.add(this.okButtonEdit);
      panelOkCancelEdit.add(this.cancelButtonEdit);
      myFrameEdit = new JFrame("Edit Label");
      JPanel mainPanelEdit = new JPanel();
      mainPanelEdit.add(panel2);
      mainPanelEdit.add(panel);
      mainPanelEdit.add(panelOkCancelEdit);
      myFrameEdit.setPreferredSize(new Dimension(250, 250));
      myFrameEdit.getContentPane().add(mainPanelEdit);
      myFrameEdit.pack();
      myFrameEdit.setLocationByPlatform(true);
      this.addButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ColorEditorSpot.tableC.setRowHeight(featureList.getHeight());
            ColorEditorSpot.myFrameAdd.setVisible(true);
         }
      });
      this.okButtonAdd.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JLabel labelString = new JLabel();
            JLabel labelColor = new JLabel();
            JLabel labelFeature = new JLabel();
            labelColor.setText("");
            labelColor.setBackground(ColorEditorSpot.this.currentColorAdd);
            labelString.setText(ColorEditorSpot.this.addTextFAdd.getText());
            labelString.setHorizontalAlignment(0);
            labelString.setBackground(ColorEditorSpot.this.currentColorAdd);
            labelColor.setOpaque(true);
            StringBuilder filterItems = new StringBuilder();

            for(int x = 0; x < featureList.getModel().getSize(); ++x) {
               filterItems.append((String)featureList.getModel().getElementAt(x)).append("<br>");
            }

            labelFeature.setText("<html>" + filterItems.toString() + "</html>");
            ColorEditorSpot.modelC.addRow(new Object[]{labelString, labelColor, labelFeature});
            ColorEditorSpot.modelC.fireTableDataChanged();
            ColorEditorSpot.tableC.repaint();
            ColorEditorSpot.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameAdd, 201));
         }
      });
      this.cancelButtonAdd.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ColorEditorSpot.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameAdd, 201));
         }
      });
      this.editButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            ColorEditorSpot.myFrameEdit.setVisible(true);
            ColorEditorSpot.this.indexRowC = ColorEditorSpot.tableC.getSelectedRow();
            if (ColorEditorSpot.tableC.getSelectedRowCount() != 0) {
               if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
                  ColorEditorSpot.this.labelC = new Object();
                  ColorEditorSpot.this.colorC = new Object();
                  ColorEditorSpot.this.labelC = (JLabel)ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
                  ColorEditorSpot.this.colorC = (JLabel)ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
                  ColorEditorSpot.this.addTextInitial = ((JLabel)ColorEditorSpot.this.labelC).getText();
                  ColorEditorSpot.this.colorCInitial = ((JLabel)ColorEditorSpot.this.colorC).getBackground();
               }

               ColorEditorSpot.this.colorButtonEdit.setBackground(((JLabel)ColorEditorSpot.this.colorC).getBackground());
               ColorEditorSpot.this.currentColorEdit = ((JLabel)ColorEditorSpot.this.colorC).getBackground();
               ColorEditorSpot.this.colorButtonEdit.setContentAreaFilled(false);
               ColorEditorSpot.this.colorButtonEdit.setOpaque(true);
               ColorEditorSpot.this.addTextFEdit.setText(((JLabel)ColorEditorSpot.this.labelC).getText());
            }
         }
      });
      this.colorButtonAdd.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Locale.setDefault(Locale.ENGLISH);
            JColorChooser.setDefaultLocale(Locale.ENGLISH);
            JColorChooser.setDefaultLocale(Locale.getDefault());
            ColorEditorSpot.this.currentColorAdd = JColorChooser.showDialog((Component)null, "Pick a Color: ", ColorEditorSpot.this.colorButtonAdd.getBackground());
            if (ColorEditorSpot.this.currentColorAdd != null) {
               ColorEditorSpot.this.colorButtonAdd.setBackground(ColorEditorSpot.this.currentColorAdd);
            }

         }
      });
      this.okButtonEdit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JLabel labelString = new JLabel();
            JLabel labelColor = new JLabel();
            labelColor.setText("");
            labelColor.setBackground(ColorEditorSpot.this.currentColorEdit);
            labelString.setText(ColorEditorSpot.this.addTextFEdit.getText());
            labelString.setHorizontalAlignment(0);
            labelString.setBackground(ColorEditorSpot.this.currentColorEdit);
            labelColor.setOpaque(true);
            ColorEditorSpot.this.addTextFinal = labelString.getText();
            ColorEditorSpot.this.colorCFinal = labelColor.getBackground();
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
         public void actionPerformed(ActionEvent e) {
            ColorEditorSpot.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameEdit, 201));
         }
      });
   }

   public void setClassAction() {
      this.colorButtonEdit.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Locale.setDefault(Locale.ENGLISH);
            JColorChooser.setDefaultLocale(Locale.ENGLISH);
            JColorChooser.setDefaultLocale(Locale.getDefault());
            ColorEditorSpot.this.currentColorEdit = JColorChooser.showDialog((Component)null, "Pick a Color: ", ColorEditorSpot.this.colorButtonEdit.getBackground());
            if (ColorEditorSpot.this.currentColorEdit != null) {
               ColorEditorSpot.this.colorButtonEdit.setBackground(ColorEditorSpot.this.currentColorEdit);
            }

         }
      });
      this.okButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            if (ColorEditorSpot.tableC.getSelectedRowCount() <= 0) {
               ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
            }

            if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
               List<String> listClasses = new ArrayList();
               ColorEditorSpot.classList = FirstWizardPanel.classList;
               ColorEditorSpot.modelListClass = FirstWizardPanel.modelListClass;
               int selectedRow = ColorEditorSpot.tableC.getSelectedRow();
               JLabel labelsTableC;
               int ix;
               ArrayList features;
               ArrayList featureMin;
               ArrayList featureMax;
               int x;
               int u;
               String[] filterFeature;
               if (ColorEditorSpot.modelListClass.getSize() == 0) {
                  ColorEditorSpot.modelListClass.addElement(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                  labelsTableC = new JLabel();

                  for(ix = 0; ix < ColorEditorSpot.tableC.getModel().getRowCount(); ++ix) {
                     labelsTableC.setText(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                     labelsTableC.setHorizontalAlignment(0);
                     labelsTableC.setBackground(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(1))).getBackground());
                     labelsTableC.setOpaque(true);
                  }

                  filterFeature = ((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "").replace("<html>", "").split("<br>");
                  features = new ArrayList();
                  featureMin = new ArrayList();
                  featureMax = new ArrayList();

                  for(x = 0; x < filterFeature.length; ++x) {
                     features.add(filterFeature[x].substring(0, filterFeature[x].indexOf(":")));
                     featureMin.add(filterFeature[x].substring(filterFeature[x].indexOf("[") + 1, filterFeature[x].indexOf(",")));
                     featureMax.add(filterFeature[x].substring(filterFeature[x].indexOf(",") + 1, filterFeature[x].indexOf("]")));
                  }

                  for(x = 0; x < FirstWizardPanel.modelSpot.getRowCount(); ++x) {
                     for(u = 0; u < features.size(); ++u) {
                        if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) >= Double.parseDouble((String)featureMin.get(u)) && Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) <= Double.parseDouble((String)featureMax.get(u))) {
                           FirstWizardPanel.tableSpot.getModel().setValueAt(labelsTableC, FirstWizardPanel.tableSpot.convertRowIndexToModel(x), FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
                        }
                     }
                  }
               }

               if (ColorEditorSpot.modelListClass.getSize() >= 1) {
                  for(int i = 0; i < ColorEditorSpot.modelListClass.getSize(); ++i) {
                     listClasses.add((String)ColorEditorSpot.modelListClass.getElementAt(i));
                  }

                  if (!listClasses.contains(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText())) {
                     ColorEditorSpot.modelListClass.addElement(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                     labelsTableC = new JLabel();

                     for(ix = 0; ix < ColorEditorSpot.tableC.getModel().getRowCount(); ++ix) {
                        labelsTableC.setText(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
                        labelsTableC.setHorizontalAlignment(0);
                        labelsTableC.setBackground(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(1))).getBackground());
                        labelsTableC.setOpaque(true);
                     }

                     filterFeature = ((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "").replace("<html>", "").split("<br>");
                     features = new ArrayList();
                     featureMin = new ArrayList();
                     featureMax = new ArrayList();

                     for(x = 0; x < filterFeature.length; ++x) {
                        features.add(filterFeature[x].substring(0, filterFeature[x].indexOf(":")));
                        featureMin.add(filterFeature[x].substring(filterFeature[x].indexOf("[") + 1, filterFeature[x].indexOf(",")));
                        featureMax.add(filterFeature[x].substring(filterFeature[x].indexOf(",") + 1, filterFeature[x].indexOf("]")));
                     }

                     for(x = 0; x < FirstWizardPanel.modelSpot.getRowCount(); ++x) {
                        for(u = 0; u < features.size(); ++u) {
                           if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) >= Double.parseDouble((String)featureMin.get(u)) && Double.parseDouble(FirstWizardPanel.tableSpot.getModel().getValueAt(x, FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex()).toString()) <= Double.parseDouble((String)featureMax.get(u))) {
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
         public void actionPerformed(ActionEvent e) {
            ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
         }
      });
      this.deleteButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Object labelC = null;
            Object colorC = null;
            Object[] labelsC = null;
            Object[] colorsC = null;
            int[] indexesRowC = ColorEditorSpot.tableC.getSelectedRows();
            int indexRowC = ColorEditorSpot.tableC.getSelectedRow();
            if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
               new Object();
               new Object();
               labelC = (JLabel)ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
               colorC = (JLabel)ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexRowC), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
            }

            labelsC = new Object[indexesRowC.length];
            colorsC = new Object[indexesRowC.length];
            if (ColorEditorSpot.tableC.getSelectedRowCount() > 1) {
               for(int k = 0; k < indexesRowC.length; ++k) {
                  labelsC[k] = (JLabel)ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexesRowC[k]), ColorEditorSpot.tableC.convertColumnIndexToModel(0));
                  colorsC[k] = (JLabel)ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexesRowC[k]), ColorEditorSpot.tableC.convertColumnIndexToModel(1));
               }
            }

            Locale.setDefault(Locale.ENGLISH);
            Locale var10000 = Locale.ENGLISH;
            JOptionPane.setDefaultLocale(Locale.getDefault());
            if (ColorEditorSpot.tableC.getSelectedRowCount() > 1) {
               String[] labelsCtoString = new String[indexesRowC.length];

               int f;
               for(f = 0; f < indexesRowC.length; ++f) {
                  labelsCtoString[f] = ((JLabel)labelsC[f]).getText();
               }

               ColorEditorSpot.this.input = JOptionPane.showConfirmDialog((Component)null, "Are you sure to delete the selected labels?", "Delete a label", 1, 0);
               if (ColorEditorSpot.this.input == 0) {
                  for(f = 0; f < indexesRowC.length; ++f) {
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
               String labelCtoString = ((JLabel)labelC).getText();
               ColorEditorSpot.this.input = JOptionPane.showConfirmDialog((Component)null, "Are you sure to delete the following label?----- " + labelCtoString, "Delete a label", 1, 0);
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

   public ImageIcon createImageIcon(String path) {
      URL img = this.getClass().getResource(path);
      if (img != null) {
         return new ImageIcon(img);
      } else {
         System.err.println("Couldn't find file: " + path);
         return null;
      }
   }

   public Object getCellEditorValueAdd() {
      return this.currentColorAdd;
   }

   public Object getCellEditorValueEdit() {
      return this.currentColorEdit;
   }

   public Component getTableCellEditorComponentAdd(JTable table, Object value, boolean isSelected, int row, int column) {
      this.currentColorAdd = (Color)value;
      return this.colorButtonAdd;
   }

   public Component getTableCellEditorComponentEdit(JTable table, Object value, boolean isSelected, int row, int column) {
      this.currentColorEdit = (Color)value;
      return this.colorButtonEdit;
   }

   public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
      return null;
   }

   public Object getCellEditorValue() {
      return null;
   }
}
