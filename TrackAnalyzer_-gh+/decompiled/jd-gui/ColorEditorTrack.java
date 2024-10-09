/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.net.URL;
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
/*     */ public class ColorEditorTrack
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
/*     */   public ColorEditorTrack(final JList<String> featureList) {
/*  68 */     ColorEditorTrack.featureList = featureList;
/*     */     
/*  70 */     this.addButton = new JButton("");
/*  71 */     this.addButton.setBounds(50, 100, 95, 30);
/*  72 */     ImageIcon iconAdd = createImageIcon("images/add.png");
/*  73 */     Icon iconAddCell = new ImageIcon(iconAdd.getImage().getScaledInstance(17, 15, 4));
/*  74 */     this.addButton.setIcon(iconAddCell);
/*  75 */     this.addButton.setToolTipText("Click this button to add your class-label.");
/*  76 */     this.editButton = new JButton("");
/*  77 */     this.editButton.setBounds(50, 100, 95, 30);
/*  78 */     ImageIcon iconEdit = createImageIcon("images/edit.png");
/*  79 */     Icon iconEditCell = new ImageIcon(iconEdit.getImage().getScaledInstance(17, 15, 4));
/*  80 */     this.editButton.setIcon(iconEditCell);
/*  81 */     this.editButton.setToolTipText("Click this button to edit your class-label.");
/*  82 */     this.deleteButton = new JButton("");
/*  83 */     this.deleteButton.setBounds(50, 100, 95, 30);
/*  84 */     ImageIcon iconDelete = createImageIcon("images/bin.png");
/*  85 */     Icon iconDeleteCell = new ImageIcon(iconDelete.getImage().getScaledInstance(22, 20, 4));
/*  86 */     this.deleteButton.setIcon(iconDeleteCell);
/*  87 */     this.deleteButton.setToolTipText("Click this button to delete your class-label.");
/*  88 */     myFrame = new JFrame("Manage Labels");
/*  89 */     myFrame.setLocation(new Point(100, 100));
/*  90 */     myFrame.setDefaultCloseOperation(2);
/*  91 */     this.myPanel = new JPanel();
/*  92 */     this.myPanel.setLayout(new BoxLayout(this.myPanel, 1));
/*  93 */     Object[][] rowData2 = new Object[0][];
/*  94 */     Object[] columnNames = { "Name", "Color", "Feature" };
/*     */     
/*  96 */     modelC = new DefaultTableModel(rowData2, columnNames)
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
/* 107 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public Class<?> getColumnClass(int column) {
/* 113 */           if (getRowCount() > 0) {
/* 114 */             Object value = getValueAt(0, column);
/* 115 */             if (value != null) {
/* 116 */               return getValueAt(0, column).getClass();
/*     */             }
/*     */           } 
/*     */           
/* 120 */           return super.getColumnClass(column);
/*     */         }
/*     */       };
/*     */     
/* 124 */     tableC = new JTable();
/* 125 */     tableC.getTableHeader().setDefaultRenderer(new SimpleHeaderRenderer());
/* 126 */     tableC.setSelectionBackground(new Color(229, 255, 204));
/* 127 */     tableC.setSelectionForeground(new Color(0, 102, 0));
/* 128 */     TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(modelC);
/* 129 */     DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
/* 130 */     centerRenderer.setHorizontalAlignment(0);
/* 131 */     tableC.setDefaultRenderer(JLabel.class, centerRenderer);
/* 132 */     tableC.setRowSorter(rowSorter);
/* 133 */     JPanel panelButtons = new JPanel();
/* 134 */     panelButtons.setLayout(new FlowLayout());
/* 135 */     panelButtons.add(this.addButton);
/* 136 */     panelButtons.add(this.editButton);
/* 137 */     panelButtons.add(this.deleteButton);
/* 138 */     tableC.setAutoCreateRowSorter(true);
/* 139 */     tableC.setEnabled(true);
/* 140 */     tableC.setCellSelectionEnabled(true);
/*     */     
/* 142 */     tableC.setRowSelectionAllowed(true);
/* 143 */     tableC.setColumnSelectionAllowed(false);
/* 144 */     tableC.setSelectionMode(2);
/* 145 */     tableC.setDefaultRenderer(JLabel.class, new Renderer());
/* 146 */     tableC.setDefaultRenderer(Color.class, new ColorRenderer(true));
/* 147 */     tableC.setModel(modelC);
/* 148 */     TableColumn column1 = null;
/* 149 */     column1 = tableC.getColumnModel().getColumn(0);
/* 150 */     column1.setPreferredWidth(7);
/* 151 */     column1.setCellRenderer((TableCellRenderer)new ResultRendererC());
/* 152 */     TableColumn column2 = null;
/* 153 */     column2 = tableC.getColumnModel().getColumn(1);
/* 154 */     column2.setPreferredWidth(5);
/* 155 */     column2.setCellRenderer((TableCellRenderer)new ResultRendererC());
/* 156 */     TableColumn column3 = null;
/* 157 */     column3 = tableC.getColumnModel().getColumn(2);
/* 158 */     column3.setPreferredWidth(15);
/* 159 */     column3.setCellRenderer((TableCellRenderer)new ResultRendererC());
/* 160 */     JScrollPane scrollPane = new JScrollPane(tableC);
/* 161 */     for (int u = 0; u < tableC.getColumnCount(); u++)
/* 162 */       tableC.getColumnModel().getColumn(u).setPreferredWidth(90); 
/* 163 */     tableC.setRowHeight(25);
/* 164 */     this.myPanel.add(Box.createHorizontalStrut(15));
/* 165 */     this.myPanel.add(panelButtons);
/* 166 */     this.myPanel.add(scrollPane, "Center");
/* 167 */     this.myPanel.setSize(300, 150);
/*     */     
/* 169 */     this.myPanel.add(Box.createHorizontalStrut(15));
/* 170 */     this.okButton = new JButton("");
/* 171 */     this.okButton.setBounds(50, 100, 95, 30);
/* 172 */     ImageIcon iconOk = createImageIcon("images/add.png");
/* 173 */     this.iconOKCell = new ImageIcon(iconOk.getImage().getScaledInstance(17, 15, 4));
/* 174 */     this.okButton.setIcon(this.iconOKCell);
/* 175 */     this.okButton.setToolTipText("Click this button to edit your color selection.");
/* 176 */     this.cancelButton = new JButton("");
/* 177 */     this.cancelButton.setBounds(50, 100, 95, 30);
/* 178 */     ImageIcon iconCancel = createImageIcon("images/cancel.png");
/* 179 */     this.iconCancelCell = new ImageIcon(iconCancel.getImage().getScaledInstance(17, 15, 4));
/* 180 */     this.cancelButton.setIcon(this.iconCancelCell);
/* 181 */     this.cancelButton.setToolTipText("Click this button to cancel your color selection.");
/* 182 */     JPanel panelOkCancel = new JPanel();
/* 183 */     panelOkCancel.setLayout(new FlowLayout());
/* 184 */     panelOkCancel.add(this.okButton);
/* 185 */     panelOkCancel.add(this.cancelButton);
/* 186 */     this.myPanel.add(panelOkCancel);
/* 187 */     myFrame.getContentPane().add(this.myPanel);
/* 188 */     myFrame.pack();
/* 189 */     myFrame.setLocationByPlatform(true);
/*     */ 
/*     */     
/* 192 */     this.panelAdd = new JPanel();
/* 193 */     this.panelAdd.setPreferredSize(new Dimension(200, 100));
/* 194 */     JPanel panel1 = new JPanel();
/* 195 */     panel1.setLayout(new FlowLayout());
/* 196 */     this.panelAdd.setLayout(new FlowLayout());
/* 197 */     this.addTextAdd = new JLabel("Label Name: ");
/* 198 */     this.addTextFAdd = new JTextField(8);
/* 199 */     panel1.add(this.addTextAdd);
/* 200 */     panel1.add(this.addTextFAdd);
/* 201 */     JLabel pickC = new JLabel("Pick a color: ");
/* 202 */     this.panelAdd.add(pickC);
/* 203 */     this.colorButtonAdd = new JButton();
/* 204 */     this.colorButtonAdd.setPreferredSize(new Dimension(200, 75));
/*     */     
/* 206 */     this.panelAdd.add(this.colorButtonAdd);
/* 207 */     this.okButtonAdd = new JButton("");
/* 208 */     this.okButtonAdd.setBounds(50, 100, 95, 30);
/* 209 */     this.okButtonAdd.setIcon(this.iconOKCell);
/* 210 */     this.okButtonAdd.setToolTipText("Click this button to edit your color selection.");
/* 211 */     this.cancelButtonAdd = new JButton("");
/* 212 */     this.cancelButtonAdd.setBounds(50, 100, 95, 30);
/* 213 */     this.cancelButtonAdd.setIcon(this.iconCancelCell);
/* 214 */     this.cancelButtonAdd.setToolTipText("Click this button to cancel your color selection.");
/* 215 */     JPanel panelOkCancelAdd = new JPanel();
/* 216 */     panelOkCancelAdd.setLayout(new FlowLayout());
/* 217 */     panelOkCancelAdd.add(this.okButtonAdd);
/* 218 */     panelOkCancelAdd.add(this.cancelButtonAdd);
/* 219 */     myFrameAdd = new JFrame("Add Label");
/* 220 */     JPanel mainPanel = new JPanel();
/* 221 */     mainPanel.add(panel1);
/* 222 */     mainPanel.add(this.panelAdd);
/* 223 */     mainPanel.add(panelOkCancelAdd);
/* 224 */     myFrameAdd.setPreferredSize(new Dimension(250, 250));
/* 225 */     myFrameAdd.getContentPane().add(mainPanel);
/* 226 */     myFrameAdd.pack();
/* 227 */     myFrameAdd.setLocationByPlatform(true);
/*     */ 
/*     */ 
/*     */     
/* 231 */     JPanel panel = new JPanel();
/* 232 */     panel.setPreferredSize(new Dimension(200, 100));
/* 233 */     JPanel panel2 = new JPanel();
/* 234 */     panel2.setLayout(new FlowLayout());
/* 235 */     panel.setLayout(new FlowLayout());
/* 236 */     this.addTextEdit = new JLabel("Label Name: ");
/* 237 */     this.addTextFEdit = new JTextField(8);
/* 238 */     panel2.add(this.addTextEdit);
/* 239 */     panel2.add(this.addTextFEdit);
/* 240 */     JLabel pickEdit = new JLabel("Pick a Color: ");
/* 241 */     panel.add(pickEdit);
/* 242 */     this.colorButtonEdit = new JButton();
/* 243 */     this.colorButtonEdit.setPreferredSize(new Dimension(200, 75));
/* 244 */     panel.add(this.colorButtonEdit);
/* 245 */     this.okButtonEdit = new JButton("");
/* 246 */     this.okButtonEdit.setBounds(50, 100, 95, 30);
/* 247 */     this.okButtonEdit.setIcon(this.iconOKCell);
/* 248 */     this.okButtonEdit.setToolTipText("Click this button to edit your color selection.");
/* 249 */     this.cancelButtonEdit = new JButton("");
/* 250 */     this.cancelButtonEdit.setBounds(50, 100, 95, 30);
/* 251 */     this.cancelButtonEdit.setIcon(this.iconCancelCell);
/* 252 */     this.cancelButtonEdit.setToolTipText("Click this button to cancel your color selection.");
/* 253 */     JPanel panelOkCancelEdit = new JPanel();
/* 254 */     panelOkCancelEdit.setLayout(new FlowLayout());
/* 255 */     panelOkCancelEdit.add(this.okButtonEdit);
/* 256 */     panelOkCancelEdit.add(this.cancelButtonEdit);
/* 257 */     myFrameEdit = new JFrame("Edit Label");
/* 258 */     JPanel mainPanelEdit = new JPanel();
/* 259 */     mainPanelEdit.add(panel2);
/* 260 */     mainPanelEdit.add(panel);
/* 261 */     mainPanelEdit.add(panelOkCancelEdit);
/* 262 */     myFrameEdit.setPreferredSize(new Dimension(250, 250));
/* 263 */     myFrameEdit.getContentPane().add(mainPanelEdit);
/* 264 */     myFrameEdit.pack();
/* 265 */     myFrameEdit.setLocationByPlatform(true);
/*     */ 
/*     */     
/* 268 */     this.addButton.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 270 */             ColorEditorTrack.tableC.setRowHeight(featureList.getHeight());
/* 271 */             ColorEditorTrack.myFrameAdd.setVisible(true);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 276 */     this.okButtonAdd.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 278 */             JLabel labelString = new JLabel();
/* 279 */             JLabel labelColor = new JLabel();
/* 280 */             JLabel labelFeature = new JLabel();
/* 281 */             labelColor.setText("");
/* 282 */             labelColor.setBackground(ColorEditorTrack.this.currentColorAdd);
/* 283 */             labelString.setText(ColorEditorTrack.this.addTextFAdd.getText());
/* 284 */             labelString.setHorizontalAlignment(0);
/* 285 */             labelString.setBackground(ColorEditorTrack.this.currentColorAdd);
/* 286 */             labelColor.setOpaque(true);
/* 287 */             StringBuilder filterItems = new StringBuilder();
/* 288 */             for (int x = 0; x < featureList.getModel().getSize(); x++)
/* 289 */               filterItems.append(featureList.getModel().getElementAt(x)).append("<br>"); 
/* 290 */             labelFeature.setText("<html>" + filterItems.toString() + "</html>");
/* 291 */             ColorEditorTrack.modelC.addRow(new Object[] { labelString, labelColor, labelFeature });
/* 292 */             ColorEditorTrack.modelC.fireTableDataChanged();
/* 293 */             ColorEditorTrack.tableC.repaint();
/*     */             
/* 295 */             ColorEditorTrack.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameAdd, 201));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 300 */     this.cancelButtonAdd.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 302 */             ColorEditorTrack.myFrameAdd.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameAdd, 201));
/*     */           }
/*     */         });
/*     */     
/* 306 */     this.editButton.addActionListener(new ActionListener()
/*     */         {
/*     */           
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 311 */             ColorEditorTrack.myFrameEdit.setVisible(true);
/*     */             
/* 313 */             ColorEditorTrack.this.indexRowC = ColorEditorTrack.tableC.getSelectedRow();
/* 314 */             if (ColorEditorTrack.tableC.getSelectedRowCount() == 0)
/*     */               return; 
/* 316 */             if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
/* 317 */               ColorEditorTrack.this.labelC = new Object();
/* 318 */               ColorEditorTrack.this.colorC = new Object();
/* 319 */               ColorEditorTrack.this.labelC = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 320 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(0));
/* 321 */               ColorEditorTrack.this.colorC = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 322 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(1));
/* 323 */               ColorEditorTrack.this.addTextInitial = ((JLabel)ColorEditorTrack.this.labelC).getText();
/* 324 */               ColorEditorTrack.this.colorCInitial = ((JLabel)ColorEditorTrack.this.colorC).getBackground();
/*     */             } 
/*     */             
/* 327 */             ColorEditorTrack.this.colorButtonEdit.setBackground(((JLabel)ColorEditorTrack.this.colorC).getBackground());
/* 328 */             ColorEditorTrack.this.currentColorEdit = ((JLabel)ColorEditorTrack.this.colorC).getBackground();
/* 329 */             ColorEditorTrack.this.colorButtonEdit.setContentAreaFilled(false);
/* 330 */             ColorEditorTrack.this.colorButtonEdit.setOpaque(true);
/*     */             
/* 332 */             ColorEditorTrack.this.addTextFEdit.setText(((JLabel)ColorEditorTrack.this.labelC).getText());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 337 */     this.colorButtonAdd.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 340 */             Locale.setDefault(Locale.ENGLISH);
/* 341 */             JColorChooser.setDefaultLocale(Locale.ENGLISH);
/* 342 */             JColorChooser.setDefaultLocale(Locale.getDefault());
/* 343 */             ColorEditorTrack.this.currentColorAdd = JColorChooser.showDialog((Component)null, "Pick a Color: ", ColorEditorTrack.this.colorButtonAdd.getBackground());
/* 344 */             if (ColorEditorTrack.this.currentColorAdd != null) {
/* 345 */               ColorEditorTrack.this.colorButtonAdd.setBackground(ColorEditorTrack.this.currentColorAdd);
/*     */             }
/*     */           }
/*     */         });
/* 349 */     this.okButtonEdit.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 351 */             JLabel labelString = new JLabel();
/* 352 */             JLabel labelColor = new JLabel();
/* 353 */             labelColor.setText("");
/* 354 */             labelColor.setBackground(ColorEditorTrack.this.currentColorEdit);
/* 355 */             labelString.setText(ColorEditorTrack.this.addTextFEdit.getText());
/* 356 */             labelString.setHorizontalAlignment(0);
/* 357 */             labelString.setBackground(ColorEditorTrack.this.currentColorEdit);
/* 358 */             labelColor.setOpaque(true);
/* 359 */             ColorEditorTrack.this.addTextFinal = labelString.getText();
/* 360 */             ColorEditorTrack.this.colorCFinal = labelColor.getBackground();
/*     */             
/* 362 */             if (!ColorEditorTrack.this.addTextFinal.equals(ColorEditorTrack.this.addTextInitial))
/* 363 */               ColorEditorTrack.modelC.setValueAt(labelString, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 364 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(0)); 
/* 365 */             if (ColorEditorTrack.this.addTextFinal.equals(ColorEditorTrack.this.addTextInitial))
/* 366 */               ColorEditorTrack.modelC.setValueAt(ColorEditorTrack.this.labelC, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 367 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(0)); 
/* 368 */             if (ColorEditorTrack.this.currentColorEdit != ColorEditorTrack.this.colorCInitial)
/* 369 */               ColorEditorTrack.modelC.setValueAt(labelColor, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 370 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(1)); 
/* 371 */             if (ColorEditorTrack.this.currentColorEdit == ColorEditorTrack.this.colorCInitial) {
/* 372 */               ColorEditorTrack.modelC.setValueAt(ColorEditorTrack.this.colorC, ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 373 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(1));
/*     */             }
/* 375 */             ColorEditorTrack.modelC.fireTableCellUpdated(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 376 */                 ColorEditorTrack.tableC.convertColumnIndexToModel(0));
/* 377 */             ColorEditorTrack.modelC.fireTableCellUpdated(ColorEditorTrack.tableC.convertRowIndexToModel(ColorEditorTrack.this.indexRowC), 
/* 378 */                 ColorEditorTrack.tableC.convertColumnIndexToModel(1));
/* 379 */             ColorEditorTrack.tableC.repaint();
/*     */             
/* 381 */             ColorEditorTrack.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameEdit, 201));
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 387 */     this.cancelButtonEdit.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 389 */             ColorEditorTrack.myFrameEdit.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrameEdit, 201));
/*     */           }
/*     */         });
/*     */   } static DefaultListModel<String> modelListClass; private JLabel addTextAdd; private JLabel addTextEdit; private JTextField addTextFAdd; private JTextField addTextFEdit; private Color currentColorAdd; private Color currentColorEdit; private Color colorCInitial; private Color colorCFinal; private Object labelC; private Object colorC; private Object featureC; private String addTextInitial; private String addTextFinal; private String featureInitial; private String featureFinal; static JFrame myFrame; static JFrame myFrameAdd; static JFrame myFrameEdit; private Icon iconOKCell;
/*     */   private Icon iconCancelCell;
/*     */   private int indexRowC;
/*     */   
/*     */   public void setClassAction() {
/* 397 */     this.colorButtonEdit.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 400 */             Locale.setDefault(Locale.ENGLISH);
/* 401 */             JColorChooser.setDefaultLocale(Locale.ENGLISH);
/* 402 */             JColorChooser.setDefaultLocale(Locale.getDefault());
/* 403 */             ColorEditorTrack.this.currentColorEdit = JColorChooser.showDialog((Component)null, "Pick a Color: ", ColorEditorTrack.this.colorButtonEdit.getBackground());
/* 404 */             if (ColorEditorTrack.this.currentColorEdit != null)
/* 405 */               ColorEditorTrack.this.colorButtonEdit.setBackground(ColorEditorTrack.this.currentColorEdit); 
/*     */           }
/*     */         });
/* 408 */     this.okButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 412 */             if (ColorEditorTrack.tableC.getSelectedRowCount() <= 0) {
/* 413 */               ColorEditorTrack.myFrame.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrame, 201));
/*     */             }
/* 415 */             if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
/*     */               
/* 417 */               List<String> listClasses = new ArrayList<>();
/* 418 */               ColorEditorTrack.classList = ChooserWizardPanel.classList;
/* 419 */               ColorEditorTrack.modelListClass = ChooserWizardPanel.modelListClass;
/* 420 */               int selectedRow = ColorEditorTrack.tableC.getSelectedRow();
/*     */               
/* 422 */               if (ColorEditorTrack.modelListClass.getSize() == 0) {
/* 423 */                 ColorEditorTrack.modelListClass.addElement((
/* 424 */                     (JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), 
/* 425 */                       ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
/* 426 */                 JLabel labelsTableC = new JLabel();
/* 427 */                 for (int i = 0; i < ColorEditorTrack.tableC.getModel().getRowCount(); i++) {
/* 428 */                   labelsTableC.setText(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, 
/* 429 */                         ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
/* 430 */                   labelsTableC.setHorizontalAlignment(0);
/* 431 */                   labelsTableC.setBackground(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, 
/* 432 */                         ColorEditorTrack.tableC.convertColumnIndexToModel(1))).getBackground());
/* 433 */                   labelsTableC.setOpaque(true);
/*     */                 } 
/*     */                 
/* 436 */                 String[] filterFeature = ((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, 
/* 437 */                     ColorEditorTrack.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "")
/* 438 */                   .replace("<html>", "").split("<br>");
/* 439 */                 List<String> features = new ArrayList<>();
/* 440 */                 List<String> featureMin = new ArrayList<>();
/* 441 */                 List<String> featureMax = new ArrayList<>();
/* 442 */                 for (int j = 0; j < filterFeature.length; j++) {
/* 443 */                   features.add(filterFeature[j].substring(0, filterFeature[j].indexOf(":")));
/* 444 */                   featureMin.add(filterFeature[j].substring(filterFeature[j].indexOf("[") + 1, 
/* 445 */                         filterFeature[j].indexOf(",")));
/* 446 */                   featureMax.add(filterFeature[j].substring(filterFeature[j].indexOf(",") + 1, 
/* 447 */                         filterFeature[j].indexOf("]")));
/*     */                 } 
/*     */                 
/* 450 */                 for (int x = 0; x < ChooserWizardPanel.modelTrack.getRowCount(); x++) {
/* 451 */                   for (int u = 0; u < features.size(); u++) {
/* 452 */                     if (Double.parseDouble(ChooserWizardPanel.tableTrack.getModel()
/* 453 */                         .getValueAt(x, 
/* 454 */                           ChooserWizardPanel.tableTrack.getColumn(features.get(u)).getModelIndex())
/* 455 */                         .toString()) >= Double.parseDouble(featureMin.get(u)))
/* 456 */                       if (Double.parseDouble(ChooserWizardPanel.tableTrack.getModel()
/* 457 */                           .getValueAt(x, 
/* 458 */                             ChooserWizardPanel.tableTrack.getColumn(features.get(u))
/* 459 */                             .getModelIndex())
/* 460 */                           .toString()) <= Double.parseDouble(featureMax.get(u))) {
/* 461 */                         ChooserWizardPanel.tableTrack.getModel().setValueAt(labelsTableC, 
/* 462 */                             ChooserWizardPanel.tableTrack.convertRowIndexToModel(x), 
/* 463 */                             ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1));
/*     */                       } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 468 */               if (ColorEditorTrack.modelListClass.getSize() >= 1) {
/* 469 */                 for (int i = 0; i < ColorEditorTrack.modelListClass.getSize(); i++) {
/* 470 */                   listClasses.add(ColorEditorTrack.modelListClass.getElementAt(i));
/*     */                 }
/* 472 */                 if (!listClasses.contains((
/* 473 */                     (JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), 
/* 474 */                       ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText())) {
/* 475 */                   ColorEditorTrack.modelListClass.addElement((
/* 476 */                       (JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), 
/* 477 */                         ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
/*     */                   
/* 479 */                   JLabel labelsTableC = new JLabel();
/* 480 */                   for (int j = 0; j < ColorEditorTrack.tableC.getModel().getRowCount(); j++) {
/* 481 */                     labelsTableC.setText(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, 
/* 482 */                           ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText());
/* 483 */                     labelsTableC.setHorizontalAlignment(0);
/* 484 */                     labelsTableC.setBackground(((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, 
/* 485 */                           ColorEditorTrack.tableC.convertColumnIndexToModel(1))).getBackground());
/* 486 */                     labelsTableC.setOpaque(true);
/*     */                   } 
/*     */                   
/* 489 */                   String[] filterFeature = ((JLabel)ColorEditorTrack.tableC.getModel().getValueAt(selectedRow, 
/* 490 */                       ColorEditorTrack.tableC.convertColumnIndexToModel(2))).getText().replace("</html>", "")
/* 491 */                     .replace("<html>", "").split("<br>");
/* 492 */                   List<String> features = new ArrayList<>();
/* 493 */                   List<String> featureMin = new ArrayList<>();
/* 494 */                   List<String> featureMax = new ArrayList<>();
/* 495 */                   for (int k = 0; k < filterFeature.length; k++) {
/* 496 */                     features.add(filterFeature[k].substring(0, filterFeature[k].indexOf(":")));
/* 497 */                     featureMin.add(filterFeature[k].substring(filterFeature[k].indexOf("[") + 1, 
/* 498 */                           filterFeature[k].indexOf(",")));
/* 499 */                     featureMax.add(filterFeature[k].substring(filterFeature[k].indexOf(",") + 1, 
/* 500 */                           filterFeature[k].indexOf("]")));
/*     */                   } 
/*     */                   
/* 503 */                   for (int x = 0; x < ChooserWizardPanel.modelTrack.getRowCount(); x++) {
/* 504 */                     for (int u = 0; u < features.size(); u++) {
/* 505 */                       if (Double.parseDouble(ChooserWizardPanel.tableTrack.getModel()
/* 506 */                           .getValueAt(x, 
/* 507 */                             ChooserWizardPanel.tableTrack.getColumn(features.get(u))
/* 508 */                             .getModelIndex())
/* 509 */                           .toString()) >= Double.parseDouble(featureMin.get(u)))
/* 510 */                         if (Double.parseDouble(ChooserWizardPanel.tableTrack.getModel()
/* 511 */                             .getValueAt(x, 
/* 512 */                               ChooserWizardPanel.tableTrack.getColumn(features.get(u))
/* 513 */                               .getModelIndex())
/* 514 */                             .toString()) <= Double.parseDouble(featureMax.get(u))) {
/* 515 */                           ChooserWizardPanel.tableTrack.getModel().setValueAt(labelsTableC, 
/* 516 */                               ChooserWizardPanel.tableTrack.convertRowIndexToModel(x), 
/* 517 */                               ChooserWizardPanel.tableTrack.convertColumnIndexToModel(1));
/*     */                         } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/* 522 */                 if (listClasses.contains((
/* 523 */                     (JLabel)ColorEditorTrack.tableC.getModel().getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(selectedRow), 
/* 524 */                       ColorEditorTrack.tableC.convertColumnIndexToModel(0))).getText())) {
/* 525 */                   ColorEditorTrack.myFrame.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrame, 201));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 533 */     this.cancelButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 536 */             ColorEditorTrack.myFrame.dispatchEvent(new WindowEvent(ColorEditorTrack.myFrame, 201));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 541 */     this.deleteButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 544 */             Object labelC = null;
/* 545 */             Object colorC = null;
/* 546 */             Object[] labelsC = null;
/* 547 */             Object[] colorsC = null;
/* 548 */             int[] indexesRowC = ColorEditorTrack.tableC.getSelectedRows();
/* 549 */             int indexRowC = ColorEditorTrack.tableC.getSelectedRow();
/* 550 */             if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
/* 551 */               labelC = new Object();
/* 552 */               colorC = new Object();
/* 553 */               labelC = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexRowC), 
/* 554 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(0));
/* 555 */               colorC = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexRowC), 
/* 556 */                   ColorEditorTrack.tableC.convertColumnIndexToModel(1));
/*     */             } 
/* 558 */             labelsC = new Object[indexesRowC.length];
/* 559 */             colorsC = new Object[indexesRowC.length];
/* 560 */             if (ColorEditorTrack.tableC.getSelectedRowCount() > 1)
/*     */             {
/* 562 */               for (int k = 0; k < indexesRowC.length; k++) {
/* 563 */                 labelsC[k] = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexesRowC[k]), 
/* 564 */                     ColorEditorTrack.tableC.convertColumnIndexToModel(0));
/* 565 */                 colorsC[k] = ColorEditorTrack.modelC.getValueAt(ColorEditorTrack.tableC.convertRowIndexToModel(indexesRowC[k]), 
/* 566 */                     ColorEditorTrack.tableC.convertColumnIndexToModel(1));
/*     */               } 
/*     */             }
/*     */             
/* 570 */             Locale.setDefault(Locale.ENGLISH);
/* 571 */             JOptionPane.setDefaultLocale(Locale.getDefault());
/* 572 */             if (ColorEditorTrack.tableC.getSelectedRowCount() > 1) {
/* 573 */               String[] labelsCtoString = new String[indexesRowC.length];
/* 574 */               for (int k = 0; k < indexesRowC.length; k++)
/* 575 */                 labelsCtoString[k] = ((JLabel)labelsC[k]).getText(); 
/* 576 */               ColorEditorTrack.this.input = JOptionPane.showConfirmDialog((Component)null, "Are you sure to delete the selected labels?", 
/* 577 */                   "Delete a label", 1, 0);
/*     */               
/* 579 */               if (ColorEditorTrack.this.input == 0) {
/* 580 */                 for (int f = 0; f < indexesRowC.length; f++)
/* 581 */                   ColorEditorTrack.modelC.removeRow(indexesRowC[f] - f); 
/* 582 */                 ColorEditorTrack.modelC.fireTableDataChanged();
/* 583 */                 ColorEditorTrack.tableC.repaint();
/*     */               } 
/*     */               
/* 586 */               if (ColorEditorTrack.this.input == 1)
/*     */                 return; 
/* 588 */               if (ColorEditorTrack.this.input == 2) {
/*     */                 return;
/*     */               }
/*     */             } 
/* 592 */             if (ColorEditorTrack.tableC.getSelectedRowCount() == 1) {
/* 593 */               String labelCtoString = ((JLabel)labelC).getText();
/* 594 */               ColorEditorTrack.this.input = JOptionPane.showConfirmDialog((Component)null, 
/* 595 */                   "Are you sure to delete the following label?----- " + labelCtoString, "Delete a label", 
/* 596 */                   1, 0);
/*     */               
/* 598 */               if (ColorEditorTrack.this.input == 0) {
/* 599 */                 ColorEditorTrack.modelC.removeRow(indexRowC);
/* 600 */                 ColorEditorTrack.modelC.fireTableDataChanged();
/*     */               } 
/*     */               
/* 603 */               if (ColorEditorTrack.this.input == 1)
/*     */                 return; 
/* 605 */               if (ColorEditorTrack.this.input == 2) {
/*     */                 return;
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public ImageIcon createImageIcon(String path) {
/* 615 */     URL img = getClass().getResource(path);
/*     */     
/* 617 */     if (img != null) {
/* 618 */       return new ImageIcon(img);
/*     */     }
/* 620 */     System.err.println("Couldn't find file: " + path);
/* 621 */     return null;
/*     */   }
/*     */   
/*     */   public Object getCellEditorValueAdd() {
/* 625 */     return this.currentColorAdd;
/*     */   }
/*     */   
/*     */   public Object getCellEditorValueEdit() {
/* 629 */     return this.currentColorEdit;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTableCellEditorComponentAdd(JTable table, Object value, boolean isSelected, int row, int column) {
/* 634 */     this.currentColorAdd = (Color)value;
/* 635 */     return this.colorButtonAdd;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTableCellEditorComponentEdit(JTable table, Object value, boolean isSelected, int row, int column) {
/* 640 */     this.currentColorEdit = (Color)value;
/* 641 */     return this.colorButtonEdit;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
/* 647 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getCellEditorValue() {
/* 653 */     return null;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ColorEditorTrack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */