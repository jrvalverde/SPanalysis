/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.swing.AbstractCellEditor;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.DefaultListModel;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableCellEditor;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableModel;
/*     */ import javax.swing.table.TableRowSorter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ColorEditorSpot
/*     */   extends AbstractCellEditor
/*     */   implements TableCellEditor
/*     */ {
/*     */   private JPanel myPanel;
/*     */   private JPanel panelAdd;
/*     */   private JPanel panelEdit;
/*     */   private JLabel labelInitt;
/*     */   private int result;
/*     */   private int input;
/*     */   static DefaultTableModel modelC;
/*     */   static JTable tableC;
/*     */   private JButton addButton;
/*     */   private JButton editButton;
/*     */   private JButton deleteButton;
/*     */   private JButton colorButtonAdd;
/*     */   private JButton colorButtonEdit;
/*     */   private JButton okButton;
/*     */   private JButton okButtonEdit;
/*     */   private JButton cancelButton;
/*     */   private JButton cancelButtonEdit;
/*     */   private JButton okButtonAdd;
/*     */   private JButton cancelButtonAdd;
/*     */   static JList<String> featureList;
/*     */   static JList<String> classList;
/*     */   static DefaultListModel<String> modelListFeature;
/*     */   
/*     */   public ColorEditorSpot(final JList<String> featureList) {
/*  67 */     ColorEditorSpot.featureList = featureList;
/*     */     
/*  69 */     this.addButton = new JButton("");
/*  70 */     this.addButton.setBounds(50, 100, 95, 30);
/*  71 */     ImageIcon iconAdd = FirstWizardPanel.createImageIcon("images/add.png");
/*  72 */     Icon iconAddCell = new ImageIcon(iconAdd.getImage().getScaledInstance(17, 15, 4));
/*  73 */     this.addButton.setIcon(iconAddCell);
/*  74 */     this.addButton.setToolTipText("Click this button to add your class-label.");
/*  75 */     this.editButton = new JButton("");
/*  76 */     this.editButton.setBounds(50, 100, 95, 30);
/*  77 */     ImageIcon iconEdit = FirstWizardPanel.createImageIcon("images/edit.png");
/*  78 */     Icon iconEditCell = new ImageIcon(iconEdit.getImage().getScaledInstance(17, 15, 4));
/*  79 */     this.editButton.setIcon(iconEditCell);
/*  80 */     this.editButton.setToolTipText("Click this button to edit your class-label.");
/*  81 */     this.deleteButton = new JButton("");
/*  82 */     this.deleteButton.setBounds(50, 100, 95, 30);
/*  83 */     ImageIcon iconDelete = FirstWizardPanel.createImageIcon("images/bin.png");
/*  84 */     Icon iconDeleteCell = new ImageIcon(iconDelete.getImage().getScaledInstance(22, 20, 4));
/*  85 */     this.deleteButton.setIcon(iconDeleteCell);
/*  86 */     this.deleteButton.setToolTipText("Click this button to delete your class-label.");
/*  87 */     myFrame = new JFrame("Manage Labels");
/*  88 */     myFrame.setLocation(new Point(100, 100));
/*  89 */     myFrame.setDefaultCloseOperation(2);
/*  90 */     this.myPanel = new JPanel();
/*  91 */     this.myPanel.setLayout(new BoxLayout(this.myPanel, 1));
/*  92 */     Object[][] rowData2 = new Object[0][];
/*  93 */     Object[] columnNames = { "Name", "Color", "Feature" };
/*     */     
/*  95 */     modelC = new DefaultTableModel(rowData2, columnNames)
/*     */       {
/*     */         private static final long serialVersionUID = 1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean isCellEditable(int row, int col) {
/* 106 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public Class<?> getColumnClass(int column) {
/* 112 */           if (getRowCount() > 0) {
/* 113 */             Object value = getValueAt(0, column);
/* 114 */             if (value != null) {
/* 115 */               return getValueAt(0, column).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 119 */           return super.getColumnClass(column);
/*     */         }
/*     */       };
/*     */     
/* 123 */     tableC = new JTable();
/* 124 */     tableC.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
/* 125 */     tableC.setSelectionBackground(new Color(229, 255, 204));
/* 126 */     tableC.setSelectionForeground(new Color(0, 102, 0));
/* 127 */     TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(modelC);
/* 128 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/* 129 */     centerRenderer.setHorizontalAlignment(0);
/* 130 */     tableC.setDefaultRenderer(JLabel.class, centerRenderer);
/* 131 */     tableC.setRowSorter(rowSorter);
/* 132 */     JPanel panelButtons = new JPanel();
/* 133 */     panelButtons.setLayout(new FlowLayout());
/* 134 */     panelButtons.add(this.addButton);
/* 135 */     panelButtons.add(this.editButton);
/* 136 */     panelButtons.add(this.deleteButton);
/* 137 */     tableC.setAutoCreateRowSorter(true);
/* 138 */     tableC.setEnabled(true);
/* 139 */     tableC.setCellSelectionEnabled(true);
/*     */     
/* 141 */     tableC.setRowSelectionAllowed(true);
/* 142 */     tableC.setColumnSelectionAllowed(false);
/* 143 */     tableC.setSelectionMode(2);
/* 144 */     tableC.setDefaultRenderer(JLabel.class, new Renderer());
/* 145 */     tableC.setDefaultRenderer(Color.class, new ColorRenderer(true));
/* 146 */     tableC.setModel(modelC);
/* 147 */     TableColumn column1 = null;
/* 148 */     column1 = tableC.getColumnModel().getColumn(0);
/* 149 */     column1.setPreferredWidth(7);
/* 150 */     column1.setCellRenderer((TableCellRenderer)new ResultRendererC());
/* 151 */     TableColumn column2 = null;
/* 152 */     column2 = tableC.getColumnModel().getColumn(1);
/* 153 */     column2.setPreferredWidth(5);
/* 154 */     column2.setCellRenderer((TableCellRenderer)new ResultRendererC());
/* 155 */     TableColumn column3 = null;
/* 156 */     column3 = tableC.getColumnModel().getColumn(2);
/* 157 */     column3.setPreferredWidth(15);
/* 158 */     column3.setCellRenderer((TableCellRenderer)new ResultRendererC());
/* 159 */     JScrollPane scrollPane = new JScrollPane(tableC);
/* 160 */     for (int u = 0; u < tableC.getColumnCount(); u++)
/* 161 */       tableC.getColumnModel().getColumn(u).setPreferredWidth(90); 
/* 162 */     tableC.setRowHeight(25);
/* 163 */     this.myPanel.add(Box.createHorizontalStrut(15));
/* 164 */     this.myPanel.add(panelButtons);
/* 165 */     this.myPanel.add(scrollPane, "Center");
/* 166 */     this.myPanel.setSize(300, 150);
/*     */     
/* 168 */     this.myPanel.add(Box.createHorizontalStrut(15));
/* 169 */     this.okButton = new JButton("");
/* 170 */     this.okButton.setBounds(50, 100, 95, 30);
/* 171 */     ImageIcon iconOk = FirstWizardPanel.createImageIcon("images/add.png");
/* 172 */     this.iconOKCell = new ImageIcon(iconOk.getImage().getScaledInstance(17, 15, 4));
/* 173 */     this.okButton.setIcon(this.iconOKCell);
/* 174 */     this.okButton.setToolTipText("Click this button to edit your color selection.");
/* 175 */     this.cancelButton = new JButton("");
/* 176 */     this.cancelButton.setBounds(50, 100, 95, 30);
/* 177 */     ImageIcon iconCancel = FirstWizardPanel.createImageIcon("images/cancel.png");
/* 178 */     this.iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(17, 15, 4));
/* 179 */     this.cancelButton.setIcon(this.iconCancelCell);
/* 180 */     this.cancelButton.setToolTipText("Click this button to cancel your color selection.");
/* 181 */     JPanel panelOkCancel = new JPanel();
/* 182 */     panelOkCancel.setLayout(new FlowLayout());
/* 183 */     panelOkCancel.add(this.okButton);
/* 184 */     panelOkCancel.add(this.cancelButton);
/* 185 */     this.myPanel.add(panelOkCancel);
/* 186 */     myFrame.getContentPane().add(this.myPanel);
/* 187 */     myFrame.pack();
/* 188 */     myFrame.setLocationByPlatform(true);
/*     */ 
/*     */     
/* 191 */     this.panelAdd = new JPanel();
/* 192 */     this.panelAdd.setPreferredSize(new Dimension(200, 100));
/* 193 */     JPanel panel1 = new JPanel();
/* 194 */     panel1.setLayout(new FlowLayout());
/* 195 */     this.panelAdd.setLayout(new FlowLayout());
/* 196 */     this.addTextAdd = new JLabel("Label Name: ");
/* 197 */     this.addTextFAdd = new JTextField(8);
/* 198 */     panel1.add(this.addTextAdd);
/* 199 */     panel1.add(this.addTextFAdd);
/* 200 */     JLabel pickC = new JLabel("Pick a color: ");
/* 201 */     this.panelAdd.add(pickC);
/* 202 */     this.colorButtonAdd = new JButton();
/* 203 */     this.colorButtonAdd.setPreferredSize(new Dimension(200, 75));
/*     */     
/* 205 */     this.panelAdd.add(this.colorButtonAdd);
/* 206 */     this.okButtonAdd = new JButton("");
/* 207 */     this.okButtonAdd.setBounds(50, 100, 95, 30);
/* 208 */     this.okButtonAdd.setIcon(this.iconOKCell);
/* 209 */     this.okButtonAdd.setToolTipText("Click this button to edit your color selection.");
/* 210 */     this.cancelButtonAdd = new JButton("");
/* 211 */     this.cancelButtonAdd.setBounds(50, 100, 95, 30);
/* 212 */     this.cancelButtonAdd.setIcon(this.iconCancelCell);
/* 213 */     this.cancelButtonAdd.setToolTipText("Click this button to cancel your color selection.");
/* 214 */     JPanel panelOkCancelAdd = new JPanel();
/* 215 */     panelOkCancelAdd.setLayout(new FlowLayout());
/* 216 */     panelOkCancelAdd.add(this.okButtonAdd);
/* 217 */     panelOkCancelAdd.add(this.cancelButtonAdd);
/* 218 */     myFrameAdd = new JFrame("Add Label");
/* 219 */     JPanel mainPanel = new JPanel();
/* 220 */     mainPanel.add(panel1);
/* 221 */     mainPanel.add(this.panelAdd);
/* 222 */     mainPanel.add(panelOkCancelAdd);
/* 223 */     myFrameAdd.setPreferredSize(new Dimension(250, 250));
/* 224 */     myFrameAdd.getContentPane().add(mainPanel);
/* 225 */     myFrameAdd.pack();
/* 226 */     myFrameAdd.setLocationByPlatform(true);
/*     */ 
/*     */ 
/*     */     
/* 230 */     JPanel panel = new JPanel();
/* 231 */     panel.setPreferredSize(new Dimension(200, 100));
/* 232 */     JPanel panel2 = new JPanel();
/* 233 */     panel2.setLayout(new FlowLayout());
/* 234 */     panel.setLayout(new FlowLayout());
/* 235 */     this.addTextEdit = new JLabel("Label Name: ");
/* 236 */     this.addTextFEdit = new JTextField(8);
/* 237 */     panel2.add(this.addTextEdit);
/* 238 */     panel2.add(this.addTextFEdit);
/* 239 */     JLabel pickEdit = new JLabel("Pick a Color: ");
/* 240 */     panel.add(pickEdit);
/* 241 */     this.colorButtonEdit = new JButton();
/* 242 */     this.colorButtonEdit.setPreferredSize(new Dimension(200, 75));
/* 243 */     panel.add(this.colorButtonEdit);
/* 244 */     this.okButtonEdit = new JButton("");
/* 245 */     this.okButtonEdit.setBounds(50, 100, 95, 30);
/* 246 */     this.okButtonEdit.setIcon(this.iconOKCell);
/* 247 */     this.okButtonEdit.setToolTipText("Click this button to edit your color selection.");
/* 248 */     this.cancelButtonEdit = new JButton("");
/* 249 */     this.cancelButtonEdit.setBounds(50, 100, 95, 30);
/* 250 */     this.cancelButtonEdit.setIcon(this.iconCancelCell);
/* 251 */     this.cancelButtonEdit.setToolTipText("Click this button to cancel your color selection.");
/* 252 */     JPanel panelOkCancelEdit = new JPanel();
/* 253 */     panelOkCancelEdit.setLayout(new FlowLayout());
/* 254 */     panelOkCancelEdit.add(this.okButtonEdit);
/* 255 */     panelOkCancelEdit.add(this.cancelButtonEdit);
/* 256 */     myFrameEdit = new JFrame("Edit Label");
/* 257 */     JPanel mainPanelEdit = new JPanel();
/* 258 */     mainPanelEdit.add(panel2);
/* 259 */     mainPanelEdit.add(panel);
/* 260 */     mainPanelEdit.add(panelOkCancelEdit);
/* 261 */     myFrameEdit.setPreferredSize(new Dimension(250, 250));
/* 262 */     myFrameEdit.getContentPane().add(mainPanelEdit);
/* 263 */     myFrameEdit.pack();
/* 264 */     myFrameEdit.setLocationByPlatform(true);
/*     */ 
/*     */     
/* 267 */     this.addButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 269 */             ColorEditorSpot.tableC.setRowHeight(featureList.getHeight());
/* 270 */             ColorEditorSpot.myFrameAdd.setVisible(true);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 275 */     this.okButtonAdd.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 277 */             JLabel labelString = new JLabel();
/* 278 */             JLabel labelColor = new JLabel();
/* 279 */             JLabel labelFeature = new JLabel();
/* 280 */             labelColor.setText("");
/* 281 */             labelColor.setBackground(ColorEditorSpot.this.currentColorAdd);
/* 282 */             labelString.setText(ColorEditorSpot.this.addTextFAdd.getText());
/* 283 */             labelString.setHorizontalAlignment(0);
/* 284 */             labelString.setBackground(ColorEditorSpot.this.currentColorAdd);
/* 285 */             labelColor.setOpaque(true);
/* 286 */             StringBuilder filterItems = new StringBuilder();
/* 287 */             for (int x = 0; x < featureList.getModel().getSize(); x++)
/* 288 */               filterItems.append(featureList.getModel().getElementAt(x)).append("<br>"); 
/* 289 */             labelFeature.setText("<html>" + filterItems.toString() + "</html>");
/* 290 */             ColorEditorSpot.modelC.addRow(new Object[] { labelString, labelColor, labelFeature });
/* 291 */             ColorEditorSpot.modelC.fireTableDataChanged();
/* 292 */             ColorEditorSpot.tableC.repaint();
/*     */             
/* 294 */             ColorEditorSpot.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameAdd, 201));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 299 */     this.cancelButtonAdd.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 301 */             ColorEditorSpot.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameAdd, 201));
/*     */           }
/*     */         });
/*     */     
/* 305 */     this.editButton.addActionListener(new ActionListener()
/*     */         {
/*     */           
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 310 */             ColorEditorSpot.myFrameEdit.setVisible(true);
/*     */             
/* 312 */             ColorEditorSpot.this.indexRowC = ColorEditorSpot.tableC.getSelectedRow();
/* 313 */             if (ColorEditorSpot.tableC.getSelectedRowCount() == 0)
/*     */               return; 
/* 315 */             if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
/* 316 */               ColorEditorSpot.this.labelC = new Object();
/* 317 */               ColorEditorSpot.this.colorC = new Object();
/* 318 */               ColorEditorSpot.this.labelC = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 319 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(0));
/* 320 */               ColorEditorSpot.this.colorC = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 321 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(1));
/* 322 */               ColorEditorSpot.this.addTextInitial = ((JLabel)ColorEditorSpot.this.labelC).getText();
/* 323 */               ColorEditorSpot.this.colorCInitial = ((JLabel)ColorEditorSpot.this.colorC).getBackground();
/*     */             } 
/*     */             
/* 326 */             ColorEditorSpot.this.colorButtonEdit.setBackground(((JLabel)ColorEditorSpot.this.colorC).getBackground());
/* 327 */             ColorEditorSpot.this.currentColorEdit = ((JLabel)ColorEditorSpot.this.colorC).getBackground();
/* 328 */             ColorEditorSpot.this.colorButtonEdit.setContentAreaFilled(false);
/* 329 */             ColorEditorSpot.this.colorButtonEdit.setOpaque(true);
/*     */             
/* 331 */             ColorEditorSpot.this.addTextFEdit.setText(((JLabel)ColorEditorSpot.this.labelC).getText());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 336 */     this.colorButtonAdd.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 339 */             Locale.setDefault(Locale.ENGLISH);
/* 340 */             JColorChooser.setDefaultLocale(Locale.ENGLISH);
/* 341 */             JColorChooser.setDefaultLocale(Locale.getDefault());
/* 342 */             ColorEditorSpot.this.currentColorAdd = JColorChooser.showDialog((Component)null, "Pick a Color: ", ColorEditorSpot.this.colorButtonAdd.getBackground());
/* 343 */             if (ColorEditorSpot.this.currentColorAdd != null) {
/* 344 */               ColorEditorSpot.this.colorButtonAdd.setBackground(ColorEditorSpot.this.currentColorAdd);
/*     */             }
/*     */           }
/*     */         });
/* 348 */     this.okButtonEdit.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 350 */             JLabel labelString = new JLabel();
/* 351 */             JLabel labelColor = new JLabel();
/* 352 */             labelColor.setText("");
/* 353 */             labelColor.setBackground(ColorEditorSpot.this.currentColorEdit);
/* 354 */             labelString.setText(ColorEditorSpot.this.addTextFEdit.getText());
/* 355 */             labelString.setHorizontalAlignment(0);
/* 356 */             labelString.setBackground(ColorEditorSpot.this.currentColorEdit);
/* 357 */             labelColor.setOpaque(true);
/* 358 */             ColorEditorSpot.this.addTextFinal = labelString.getText();
/* 359 */             ColorEditorSpot.this.colorCFinal = labelColor.getBackground();
/*     */             
/* 361 */             if (!ColorEditorSpot.this.addTextFinal.equals(ColorEditorSpot.this.addTextInitial))
/* 362 */               ColorEditorSpot.modelC.setValueAt(labelString, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 363 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(0)); 
/* 364 */             if (ColorEditorSpot.this.addTextFinal.equals(ColorEditorSpot.this.addTextInitial))
/* 365 */               ColorEditorSpot.modelC.setValueAt(ColorEditorSpot.this.labelC, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 366 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(0)); 
/* 367 */             if (ColorEditorSpot.this.currentColorEdit != ColorEditorSpot.this.colorCInitial)
/* 368 */               ColorEditorSpot.modelC.setValueAt(labelColor, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 369 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(1)); 
/* 370 */             if (ColorEditorSpot.this.currentColorEdit == ColorEditorSpot.this.colorCInitial) {
/* 371 */               ColorEditorSpot.modelC.setValueAt(ColorEditorSpot.this.colorC, ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 372 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(1));
/*     */             }
/* 374 */             ColorEditorSpot.modelC.fireTableCellUpdated(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 375 */                 ColorEditorSpot.tableC.convertColumnIndexToModel(0));
/* 376 */             ColorEditorSpot.modelC.fireTableCellUpdated(ColorEditorSpot.tableC.convertRowIndexToModel(ColorEditorSpot.this.indexRowC), 
/* 377 */                 ColorEditorSpot.tableC.convertColumnIndexToModel(1));
/* 378 */             ColorEditorSpot.tableC.repaint();
/*     */             
/* 380 */             ColorEditorSpot.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameEdit, 201));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 386 */     this.cancelButtonEdit.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 388 */             ColorEditorSpot.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrameEdit, 201));
/*     */           }
/*     */         });
/*     */   } static DefaultListModel<String> modelListClass; private JLabel addTextAdd; private JLabel addTextEdit; private JTextField addTextFAdd; private JTextField addTextFEdit; private Color currentColorAdd; private Color currentColorEdit; private Color colorCInitial; private Color colorCFinal; private Object labelC; private Object colorC; private Object featureC; private String addTextInitial; private String addTextFinal; private String featureInitial; private String featureFinal; static JFrame myFrame; static JFrame myFrameAdd; static JFrame myFrameEdit; private Icon iconOKCell;
/*     */   private Icon iconCancelCell;
/*     */   private int indexRowC;
/*     */   
/*     */   public void setClassAction() {
/* 396 */     this.colorButtonEdit.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 399 */             Locale.setDefault(Locale.ENGLISH);
/* 400 */             JColorChooser.setDefaultLocale(Locale.ENGLISH);
/* 401 */             JColorChooser.setDefaultLocale(Locale.getDefault());
/* 402 */             ColorEditorSpot.this.currentColorEdit = JColorChooser.showDialog((Component)null, "Pick a Color: ", ColorEditorSpot.this.colorButtonEdit.getBackground());
/* 403 */             if (ColorEditorSpot.this.currentColorEdit != null)
/* 404 */               ColorEditorSpot.this.colorButtonEdit.setBackground(ColorEditorSpot.this.currentColorEdit); 
/*     */           }
/*     */         });
/* 407 */     this.okButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 411 */             if (ColorEditorSpot.tableC.getSelectedRowCount() <= 0) {
/* 412 */               ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
/*     */             }
/* 414 */             if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
/*     */               
/* 416 */               List<String> listClasses = new ArrayList<>();
/* 417 */               ColorEditorSpot.classList = FirstWizardPanel.classList;
/* 418 */               ColorEditorSpot.modelListClass = FirstWizardPanel.modelListClass;
/* 419 */               int selectedRow = ColorEditorSpot.tableC.getSelectedRow();
/*     */               
/* 421 */               if (ColorEditorSpot.modelListClass.getSize() == 0) {
/* 422 */                 ColorEditorSpot.modelListClass.addElement((
/* 423 */                     (JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), 
/* 424 */                       ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
/* 425 */                 JLabel labelsTableC = new JLabel();
/* 426 */                 for (int i = 0; i < ColorEditorSpot.tableC.getModel().getRowCount(); i++) {
/* 427 */                   labelsTableC.setText(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, 
/* 428 */                         ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
/* 429 */                   labelsTableC.setHorizontalAlignment(0);
/* 430 */                   labelsTableC.setBackground(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, 
/* 431 */                         ColorEditorSpot.tableC.convertColumnIndexToModel(1))).getBackground());
/* 432 */                   labelsTableC.setOpaque(true);
/*     */                 } 
/*     */                 
/* 435 */                 String[] filterFeature = ((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, 
/* 436 */                     ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "")
/* 437 */                   .replace("<html>", "").split("<br>");
/* 438 */                 List<String> features = new ArrayList<>();
/* 439 */                 List<String> featureMin = new ArrayList<>();
/* 440 */                 List<String> featureMax = new ArrayList<>();
/* 441 */                 for (int j = 0; j < filterFeature.length; j++) {
/* 442 */                   features.add(filterFeature[j].substring(0, filterFeature[j].indexOf(":")));
/* 443 */                   featureMin.add(filterFeature[j].substring(filterFeature[j].indexOf("[") + 1, 
/* 444 */                         filterFeature[j].indexOf(",")));
/* 445 */                   featureMax.add(filterFeature[j].substring(filterFeature[j].indexOf(",") + 1, 
/* 446 */                         filterFeature[j].indexOf("]")));
/*     */                 } 
/*     */                 
/* 449 */                 for (int x = 0; x < FirstWizardPanel.modelSpot.getRowCount(); x++) {
/* 450 */                   for (int u = 0; u < features.size(); u++) {
/* 451 */                     if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel()
/* 452 */                         .getValueAt(x, 
/* 453 */                           FirstWizardPanel.tableSpot.getColumn(features.get(u)).getModelIndex())
/* 454 */                         .toString()) >= Double.parseDouble(featureMin.get(u)))
/* 455 */                       if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel()
/* 456 */                           .getValueAt(x, 
/* 457 */                             FirstWizardPanel.tableSpot.getColumn(features.get(u))
/* 458 */                             .getModelIndex())
/* 459 */                           .toString()) <= Double.parseDouble(featureMax.get(u))) {
/* 460 */                         FirstWizardPanel.tableSpot.getModel().setValueAt(labelsTableC, 
/* 461 */                             FirstWizardPanel.tableSpot.convertRowIndexToModel(x), 
/* 462 */                             FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
/*     */                       } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 467 */               if (ColorEditorSpot.modelListClass.getSize() >= 1) {
/* 468 */                 for (int i = 0; i < ColorEditorSpot.modelListClass.getSize(); i++) {
/* 469 */                   listClasses.add(ColorEditorSpot.modelListClass.getElementAt(i));
/*     */                 }
/* 471 */                 if (!listClasses.contains((
/* 472 */                     (JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), 
/* 473 */                       ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText())) {
/* 474 */                   ColorEditorSpot.modelListClass.addElement((
/* 475 */                       (JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), 
/* 476 */                         ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
/*     */                   
/* 478 */                   JLabel labelsTableC = new JLabel();
/* 479 */                   for (int j = 0; j < ColorEditorSpot.tableC.getModel().getRowCount(); j++) {
/* 480 */                     labelsTableC.setText(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, 
/* 481 */                           ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText());
/* 482 */                     labelsTableC.setHorizontalAlignment(0);
/* 483 */                     labelsTableC.setBackground(((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, 
/* 484 */                           ColorEditorSpot.tableC.convertColumnIndexToModel(1))).getBackground());
/* 485 */                     labelsTableC.setOpaque(true);
/*     */                   } 
/*     */                   
/* 488 */                   String[] filterFeature = ((JLabel)ColorEditorSpot.tableC.getModel().getValueAt(selectedRow, 
/* 489 */                       ColorEditorSpot.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "")
/* 490 */                     .replace("<html>", "").split("<br>");
/* 491 */                   List<String> features = new ArrayList<>();
/* 492 */                   List<String> featureMin = new ArrayList<>();
/* 493 */                   List<String> featureMax = new ArrayList<>();
/* 494 */                   for (int k = 0; k < filterFeature.length; k++) {
/* 495 */                     features.add(filterFeature[k].substring(0, filterFeature[k].indexOf(":")));
/* 496 */                     featureMin.add(filterFeature[k].substring(filterFeature[k].indexOf("[") + 1, 
/* 497 */                           filterFeature[k].indexOf(",")));
/* 498 */                     featureMax.add(filterFeature[k].substring(filterFeature[k].indexOf(",") + 1, 
/* 499 */                           filterFeature[k].indexOf("]")));
/*     */                   } 
/*     */                   
/* 502 */                   for (int x = 0; x < FirstWizardPanel.modelSpot.getRowCount(); x++) {
/* 503 */                     for (int u = 0; u < features.size(); u++) {
/* 504 */                       if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel()
/* 505 */                           .getValueAt(x, 
/* 506 */                             FirstWizardPanel.tableSpot.getColumn(features.get(u))
/* 507 */                             .getModelIndex())
/* 508 */                           .toString()) >= Double.parseDouble(featureMin.get(u)))
/* 509 */                         if (Double.parseDouble(FirstWizardPanel.tableSpot.getModel()
/* 510 */                             .getValueAt(x, 
/* 511 */                               FirstWizardPanel.tableSpot.getColumn(features.get(u))
/* 512 */                               .getModelIndex())
/* 513 */                             .toString()) <= Double.parseDouble(featureMax.get(u))) {
/* 514 */                           FirstWizardPanel.tableSpot.getModel().setValueAt(labelsTableC, 
/* 515 */                               FirstWizardPanel.tableSpot.convertRowIndexToModel(x), 
/* 516 */                               FirstWizardPanel.tableSpot.convertColumnIndexToModel(1));
/*     */                         } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 521 */                 if (listClasses.contains((
/* 522 */                     (JLabel)ColorEditorSpot.tableC.getModel().getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(selectedRow), 
/* 523 */                       ColorEditorSpot.tableC.convertColumnIndexToModel(0))).getText())) {
/* 524 */                   ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 532 */     this.cancelButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 535 */             ColorEditorSpot.myFrame.dispatchEvent(new WindowEvent(ColorEditorSpot.myFrame, 201));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 540 */     this.deleteButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 543 */             Object labelC = null;
/* 544 */             Object colorC = null;
/* 545 */             Object[] labelsC = null;
/* 546 */             Object[] colorsC = null;
/* 547 */             int[] indexesRowC = ColorEditorSpot.tableC.getSelectedRows();
/* 548 */             int indexRowC = ColorEditorSpot.tableC.getSelectedRow();
/* 549 */             if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
/* 550 */               labelC = new Object();
/* 551 */               colorC = new Object();
/* 552 */               labelC = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexRowC), 
/* 553 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(0));
/* 554 */               colorC = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexRowC), 
/* 555 */                   ColorEditorSpot.tableC.convertColumnIndexToModel(1));
/*     */             } 
/* 557 */             labelsC = new Object[indexesRowC.length];
/* 558 */             colorsC = new Object[indexesRowC.length];
/* 559 */             if (ColorEditorSpot.tableC.getSelectedRowCount() > 1)
/*     */             {
/* 561 */               for (int k = 0; k < indexesRowC.length; k++) {
/* 562 */                 labelsC[k] = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexesRowC[k]), 
/* 563 */                     ColorEditorSpot.tableC.convertColumnIndexToModel(0));
/* 564 */                 colorsC[k] = ColorEditorSpot.modelC.getValueAt(ColorEditorSpot.tableC.convertRowIndexToModel(indexesRowC[k]), 
/* 565 */                     ColorEditorSpot.tableC.convertColumnIndexToModel(1));
/*     */               } 
/*     */             }
/*     */             
/* 569 */             Locale.setDefault(Locale.ENGLISH);
/* 570 */             JOptionPane.setDefaultLocale(Locale.getDefault());
/* 571 */             if (ColorEditorSpot.tableC.getSelectedRowCount() > 1) {
/* 572 */               String[] labelsCtoString = new String[indexesRowC.length];
/* 573 */               for (int k = 0; k < indexesRowC.length; k++)
/* 574 */                 labelsCtoString[k] = ((JLabel)labelsC[k]).getText(); 
/* 575 */               ColorEditorSpot.this.input = JOptionPane.showConfirmDialog((Component)null, "Are you sure to delete the selected labels?", 
/* 576 */                   "Delete a label", 1, 0);
/*     */               
/* 578 */               if (ColorEditorSpot.this.input == 0) {
/* 579 */                 for (int f = 0; f < indexesRowC.length; f++)
/* 580 */                   ColorEditorSpot.modelC.removeRow(indexesRowC[f] - f); 
/* 581 */                 ColorEditorSpot.modelC.fireTableDataChanged();
/* 582 */                 ColorEditorSpot.tableC.repaint();
/*     */               } 
/*     */               
/* 585 */               if (ColorEditorSpot.this.input == 1)
/*     */                 return; 
/* 587 */               if (ColorEditorSpot.this.input == 2) {
/*     */                 return;
/*     */               }
/*     */             } 
/* 591 */             if (ColorEditorSpot.tableC.getSelectedRowCount() == 1) {
/* 592 */               String labelCtoString = ((JLabel)labelC).getText();
/* 593 */               ColorEditorSpot.this.input = JOptionPane.showConfirmDialog((Component)null, 
/* 594 */                   "Are you sure to delete the following label?----- " + labelCtoString, "Delete a label", 
/* 595 */                   1, 0);
/*     */               
/* 597 */               if (ColorEditorSpot.this.input == 0) {
/* 598 */                 ColorEditorSpot.modelC.removeRow(indexRowC);
/* 599 */                 ColorEditorSpot.modelC.fireTableDataChanged();
/*     */               } 
/*     */               
/* 602 */               if (ColorEditorSpot.this.input == 1)
/*     */                 return; 
/* 604 */               if (ColorEditorSpot.this.input == 2) {
/*     */                 return;
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getCellEditorValueAdd() {
/* 615 */     return this.currentColorAdd;
/*     */   }
/*     */   
/*     */   public Object getCellEditorValueEdit() {
/* 619 */     return this.currentColorEdit;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTableCellEditorComponentAdd(JTable table, Object value, boolean isSelected, int row, int column) {
/* 624 */     this.currentColorAdd = (Color)value;
/* 625 */     return this.colorButtonAdd;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTableCellEditorComponentEdit(JTable table, Object value, boolean isSelected, int row, int column) {
/* 630 */     this.currentColorEdit = (Color)value;
/* 631 */     return this.colorButtonEdit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
/* 637 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getCellEditorValue() {
/* 643 */     return null;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/ColorEditorSpot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */