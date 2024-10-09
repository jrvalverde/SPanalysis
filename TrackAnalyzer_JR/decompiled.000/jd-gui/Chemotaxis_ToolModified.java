/*      */ import ij.IJ;
/*      */ import ij.WindowManager;
/*      */ import ij.gui.Plot;
/*      */ import ij.gui.PlotWindow;
/*      */ import ij.measure.ResultsTable;
/*      */ import ij.plugin.PlugIn;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Window;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Hashtable;
/*      */ import java.util.LinkedList;
/*      */ import java.util.Vector;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.event.ChangeEvent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Chemotaxis_ToolModified
/*      */   implements PlugIn
/*      */ {
/*      */   ChemotaxisGUI gui;
/*      */   ScalingDialog _dialog;
/*      */   float[][] x_values;
/*      */   float[][] y_values;
/*      */   float _angleBetweenCircle;
/*      */   float _angleBetweenDiagram;
/*      */   ArrayList _openInfoWindows;
/*      */   ArrayList _selectedDatasets;
/*      */   ArrayList _currentOpenWindows;
/*      */   ArrayList _currentOpenDiagrams;
/*      */   Vector _maxVector;
/*      */   int _maxPosition;
/*      */   float _anglePosition;
/*      */   int _currentSelectedDataset;
/*      */   ArrayList _variableSliceNumber;
/*      */   ArrayList _importedData;
/*      */   Hashtable _hashSliceNumber;
/*      */   LinkedList _listArrayList;
/*      */   Hashtable _hashImportedDataset;
/*      */   Hashtable _hashSlicesImported;
/*      */   Hashtable _hashTracks;
/*      */   Hashtable _hashCurrentDataset;
/*      */   Hashtable _hashCurrentPosition;
/*      */   Hashtable _hashPlot;
/*      */   Hashtable _hashWindow;
/*      */   float _coordSize;
/*      */   float _calxy;
/*      */   double _timeInterval;
/*      */   String _unitsPath;
/*      */   String _unitsTime;
/*      */   int _plotHeight;
/*      */   int _plotWidth;
/*      */   ArrayList arrayToImport;
/*      */   
/*      */   public Chemotaxis_ToolModified(ArrayList arrayToImport) {
/*   90 */     this.arrayToImport = arrayToImport;
/*   91 */     this.gui = null;
/*   92 */     this._dialog = null;
/*   93 */     this._angleBetweenCircle = 66.0F;
/*   94 */     this._angleBetweenDiagram = 66.0F;
/*   95 */     this._maxVector = null;
/*   96 */     this._maxPosition = 0;
/*   97 */     this._anglePosition = 0.0F;
/*   98 */     this._variableSliceNumber = null;
/*   99 */     this._coordSize = 0.0F;
/*  100 */     this._calxy = 1.0F;
/*  101 */     this._timeInterval = 2.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public void run(String var1) {
/*  106 */     PlotWindow.plotHeight = 500;
/*  107 */     PlotWindow.plotWidth = 500;
/*  108 */     this._plotHeight = 500;
/*  109 */     this._plotWidth = 500;
/*  110 */     (this._dialog = new ScalingDialog(this.gui)).setHeight(this._plotHeight);
/*  111 */     this._dialog.setWidth(this._plotWidth);
/*  112 */     this._openInfoWindows = new ArrayList();
/*  113 */     this._hashImportedDataset = new Hashtable<>();
/*  114 */     this._hashSlicesImported = new Hashtable<>();
/*  115 */     this._hashTracks = new Hashtable<>();
/*  116 */     this._hashCurrentDataset = new Hashtable<>();
/*  117 */     this._hashCurrentPosition = new Hashtable<>();
/*  118 */     this._hashPlot = new Hashtable<>();
/*  119 */     this._hashWindow = new Hashtable<>();
/*  120 */     this._currentOpenWindows = new ArrayList();
/*  121 */     this._currentOpenDiagrams = new ArrayList();
/*  122 */     this._importedData = new ArrayList();
/*  123 */     this._hashSliceNumber = new Hashtable<>();
/*  124 */     this._selectedDatasets = new ArrayList();
/*  125 */     this._listArrayList = new LinkedList();
/*  126 */     this._maxVector = new Vector();
/*  127 */     this._selectedDatasets.clear();
/*  128 */     this._hashSlicesImported.clear();
/*  129 */     this._hashImportedDataset.clear();
/*  130 */     this._importedData.clear();
/*  131 */     this._hashSliceNumber.clear();
/*  132 */     this._listArrayList.clear();
/*  133 */     this._hashTracks.clear();
/*  134 */     this._hashCurrentDataset.clear();
/*  135 */     this._hashCurrentPosition.clear();
/*  136 */     this._hashPlot.clear();
/*  137 */     this._hashWindow.clear();
/*      */     
/*      */     try {
/*  140 */       readData("prueba2.xls");
/*  141 */     } catch (IOException ex5) {
/*  142 */       JOptionPane.showMessageDialog((Component)this.gui, "Error reading from file");
/*      */     } 
/*      */     
/*  145 */     boolean b4 = true;
/*  146 */     String item = "1: prueba2.xls";
/*      */     
/*  148 */     if (this._hashSlicesImported.containsKey("1: prueba2.xls")) {
/*      */       
/*  150 */       ArrayList<Integer> list16 = (ArrayList)this._hashSlicesImported.get("1: prueba2.xls");
/*  151 */       if (list16.size() == 1) {
/*      */         
/*      */         try {
/*  154 */           list16 = new ArrayList();
/*  155 */           this._hashSlicesImported.remove("1: prueba2.xls");
/*  156 */           list16.add(Integer.valueOf(1));
/*  157 */           list16.add(Integer.valueOf(SPTBatch_.imps.getStackSize()));
/*  158 */           if (Integer.valueOf(SPTBatch_.imps.getStackSize()).intValue() < Integer.valueOf("1").intValue()) {
/*  159 */             JOptionPane.showMessageDialog((Component)this.gui, "Second value can`t be smaller than the first");
/*      */           } else {
/*  161 */             this._hashSlicesImported.put("1: prueba2.xls", list16);
/*      */           } 
/*  163 */         } catch (NumberFormatException ex12) {
/*  164 */           this.gui.firstSlicesField.setText("0");
/*  165 */           this.gui.secondSlicesField.setText("0");
/*  166 */           list16.add(new Integer(0));
/*  167 */           list16.add(new Integer(0));
/*  168 */           this._hashSlicesImported.put("1: prueba2.xls", list16);
/*  169 */           JOptionPane.showMessageDialog((Component)this.gui, "Please enter correct value for number of slices!");
/*      */         } 
/*      */       }
/*  172 */       if (list16.size() == 2) {
/*      */         
/*      */         try {
/*  175 */           if (Integer.valueOf(1) != list16.get(0) || 
/*  176 */             Integer.valueOf(SPTBatch_.imps.getStackSize()) != list16.get(1)) {
/*  177 */             this._hashSlicesImported.remove("1: prueba2.xls");
/*  178 */             ArrayList<Integer> value7 = new ArrayList<>();
/*  179 */             value7.add(Integer.valueOf("1"));
/*  180 */             value7.add(Integer.valueOf(SPTBatch_.imps.getStackSize()));
/*  181 */             if (Integer.valueOf(SPTBatch_.imps.getStackSize()).intValue() < Integer.valueOf("1.0").intValue()) {
/*  182 */               JOptionPane.showMessageDialog((Component)this.gui, "Second value can`t be smaller than the first");
/*      */             } else {
/*  184 */               this._hashSlicesImported.put("1: prueba2.xls", value7);
/*      */             } 
/*      */           } 
/*  187 */         } catch (NumberFormatException ex13) {
/*  188 */           ArrayList<Integer> list17 = (ArrayList<Integer>)this._hashSlicesImported.get("1: prueba2.xls");
/*  189 */           JOptionPane.showMessageDialog((Component)this.gui, "Setting old values!");
/*      */         } 
/*      */       }
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  197 */         ArrayList<Integer> value9 = new ArrayList<>();
/*  198 */         value9.add(Integer.valueOf("1"));
/*  199 */         value9.add(Integer.valueOf(SPTBatch_.imps.getStackSize()));
/*  200 */         if (Integer.valueOf(SPTBatch_.imps.getStackSize()).intValue() < Integer.valueOf("1").intValue()) {
/*  201 */           b4 = false;
/*  202 */           JOptionPane.showMessageDialog((Component)this.gui, "Second value can`t be smaller than the first");
/*      */         } else {
/*  204 */           this._hashSlicesImported.put("1: prueba2.xls", value9);
/*      */         }
/*      */       
/*  207 */       } catch (NumberFormatException ex14) {
/*  208 */         b4 = false;
/*  209 */         JOptionPane.showMessageDialog((Component)this.gui, "Please enter correct value for number of slices!");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  215 */     boolean b5 = true;
/*  216 */     this._maxVector.clear();
/*  217 */     ArrayList<String> list18 = new ArrayList<>();
/*      */     
/*  219 */     String s20 = "1: prueba2.xls";
/*  220 */     if (!list18.contains("1: prueba2.xls")) {
/*  221 */       list18.add("1: prueba2.xls");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  229 */     this._unitsPath = "µm";
/*  230 */     this._unitsTime = "min";
/*  231 */     int intValue31 = 0;
/*  232 */     int intValue32 = 0;
/*  233 */     int n68 = 0;
/*  234 */     double n69 = 0.0D;
/*  235 */     double doubleValue = 0.0D;
/*      */     
/*  237 */     int n70 = 0;
/*  238 */     double n71 = 0.0D;
/*  239 */     double doubleValue2 = 0.0D;
/*      */     
/*  241 */     this._calxy = (new Float(1.0D)).floatValue();
/*  242 */     this._timeInterval = (new Double(1.0D)).doubleValue();
/*      */     
/*  244 */     b5 = true;
/*  245 */     if (b5) {
/*      */       
/*  247 */       this._listArrayList.clear();
/*  248 */       this._hashCurrentDataset.clear();
/*  249 */       this._hashSliceNumber.clear();
/*      */       
/*  251 */       for (int n72 = 0; n72 < list18.size(); n72++) {
/*  252 */         this._variableSliceNumber = new ArrayList();
/*  253 */         String s26 = list18.get(n72);
/*  254 */         this._hashCurrentDataset.put(new Integer(n72), s26);
/*  255 */         this._hashCurrentPosition.put(s26, new Integer(n72));
/*  256 */         ArrayList<Integer> list20 = (ArrayList<Integer>)this._hashSlicesImported.get(s26);
/*  257 */         int intValue35 = ((Integer)this._hashImportedDataset.get(s26)).intValue();
/*  258 */         ArrayList<Float> list21 = new ArrayList();
/*  259 */         ArrayList<Float> list22 = new ArrayList<>();
/*  260 */         ArrayList<Float> list23 = new ArrayList<>();
/*  261 */         ArrayList<Object> list24 = this._importedData.get(intValue35 - 1);
/*  262 */         for (int index24 = 0; index24 < list24.size(); index24 += 4) {
/*  263 */           list21.add(list24.get(index24));
/*  264 */           list22.add((Float)list24.get(index24 + 2));
/*  265 */           list23.add((Float)list24.get(index24 + 3));
/*      */         } 
/*  267 */         int intValue36 = Math.round(((Float)list21.get(list21.size() - 1)).floatValue());
/*  268 */         int[] array70 = new int[3 * intValue36];
/*  269 */         int n73 = 1;
/*  270 */         for (int n74 = 0; n74 <= 3 * intValue36 - 3; n74 += 3) {
/*  271 */           array70[n74] = list21.indexOf(new Float(n73));
/*  272 */           array70[n74 + 1] = list21.lastIndexOf(new Float(n73));
/*  273 */           array70[n74 + 2] = n73;
/*  274 */           n73++;
/*      */         } 
/*  276 */         ArrayList<Float> list25 = new ArrayList<>();
/*  277 */         ArrayList<Float> list26 = new ArrayList<>();
/*  278 */         int n75 = 0;
/*  279 */         if (list20.size() == 1) {
/*  280 */           int intValue37 = ((Integer)list20.get(0)).intValue();
/*  281 */           for (int n76 = 0; n76 <= array70.length - 3; n76 += 3) {
/*  282 */             if (array70[n76 + 1] != -1 && array70[n76] != -1 && 
/*  283 */               array70[n76 + 1] - array70[n76] == intValue37 - 1) {
/*  284 */               n75++;
/*  285 */               this._variableSliceNumber.add(new Integer(array70[n76 + 1] - array70[n76] + 1));
/*  286 */               for (int n77 = array70[n76]; n77 <= array70[n76 + 1]; n77++) {
/*  287 */                 list25.add(new Float(((Float)list22.get(n77)).floatValue() * this._calxy));
/*  288 */                 list26.add(new Float(((Float)list23.get(n77)).floatValue() * this._calxy));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  293 */         if (list20.size() == 2) {
/*  294 */           int intValue38 = ((Integer)list20.get(0)).intValue();
/*  295 */           int intValue39 = ((Integer)list20.get(1)).intValue();
/*  296 */           for (int n78 = 0; n78 <= array70.length - 3; n78 += 3) {
/*  297 */             if (array70[n78 + 1] != -1 && array70[n78] != -1 && 
/*  298 */               array70[n78 + 1] - array70[n78] >= intValue38 - 1 && 
/*  299 */               array70[n78 + 1] - array70[n78] <= intValue39 - 1) {
/*  300 */               n75++;
/*  301 */               this._variableSliceNumber.add(new Integer(array70[n78 + 1] - array70[n78] + 1));
/*  302 */               for (int n79 = array70[n78]; n79 <= array70[n78 + 1]; n79++) {
/*  303 */                 list25.add(new Float(((Float)list22.get(n79)).floatValue() * this._calxy));
/*  304 */                 list26.add(new Float(((Float)list23.get(n79)).floatValue() * this._calxy));
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  309 */         updateNumberofTracks(s26, n75);
/*  310 */         if (this._variableSliceNumber.isEmpty()) {
/*  311 */           if (list20.size() == 1) {
/*  312 */             JOptionPane.showMessageDialog((Component)this.gui, "No tracks available for current number of slices");
/*      */           }
/*  314 */           if (list20.size() == 2) {
/*  315 */             JOptionPane.showMessageDialog((Component)this.gui, 
/*  316 */                 "No tracks available for current range of used number of slices");
/*      */           }
/*      */         } else {
/*  319 */           int intValue40 = ((Integer)Collections.<Integer>max(this._variableSliceNumber)).intValue();
/*      */           
/*  321 */           int n88 = 0;
/*  322 */           this.x_values = new float[n75][intValue40];
/*  323 */           this.y_values = new float[n75][intValue40];
/*  324 */           for (int index26 = 0; index26 < n75; index26++) {
/*  325 */             this.x_values[index26][0] = 0.0F;
/*  326 */             this.y_values[index26][0] = 0.0F;
/*      */ 
/*      */             
/*  329 */             int n89 = 1;
/*  330 */             int intValue42 = ((Integer)this._variableSliceNumber.get(index26)).intValue(); for (; n89 < intValue42; n89++) {
/*  331 */               float n90 = ((Float)list25.get(n89 + n88)).floatValue() - ((Float)list25.get(n88)).floatValue();
/*  332 */               float n91 = -(((Float)list26.get(n89 + n88)).floatValue() - ((Float)list26.get(n88)).floatValue());
/*  333 */               this.x_values[index26][n89] = n90;
/*  334 */               this.y_values[index26][n89] = n91;
/*      */             } 
/*  336 */             n88 += intValue42;
/*      */           } 
/*      */           
/*  339 */           this._listArrayList.add(this.x_values);
/*  340 */           this._listArrayList.add(this.y_values);
/*  341 */           this._hashSliceNumber.put(new Integer(n72), this._variableSliceNumber);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  349 */     for (int j = 0; j < this._listArrayList.size(); j += 2) {
/*  350 */       String s4 = (String)this._hashCurrentDataset.get(new Integer(j / 2));
/*  351 */       ArrayList<? extends Integer> coll = (ArrayList)this._hashSliceNumber.get(new Integer(j / 2));
/*  352 */       int intValue5 = ((Integer)this._hashTracks.get(s4)).intValue();
/*  353 */       int intValue6 = ((Integer)Collections.<Integer>max(coll)).intValue();
/*  354 */       float[][] array3 = this._listArrayList.get(j);
/*  355 */       float[][] array4 = this._listArrayList.get(j + 1);
/*  356 */       ResultsTable resultsTable = new ResultsTable();
/*  357 */       String[] array5 = { "Slice", "Center of mass x [" + this._unitsPath + "]", 
/*  358 */           "Center of mass y [" + this._unitsPath + "]", "Center of mass length [" + this._unitsPath + "]" };
/*  359 */       for (int k = 0; k < array5.length; k++) {
/*  360 */         resultsTable.setHeading(k, array5[k]);
/*      */       }
/*  362 */       for (int l = 1; l <= intValue6; l++) {
/*  363 */         double[] centerofMassSeries = ChemotaxisStatistic.centerofMassSeries(l, array3, array4, intValue5, 
/*  364 */             coll);
/*  365 */         resultsTable.incrementCounter();
/*  366 */         resultsTable.addValue(0, l);
/*  367 */         resultsTable.addValue(1, centerofMassSeries[0]);
/*  368 */         resultsTable.addValue(2, centerofMassSeries[1]);
/*  369 */         resultsTable.addValue(3, centerofMassSeries[2]);
/*      */       } 
/*      */       
/*  372 */       resultsTable.save(SPTBatch_.directChemo + File.separator + "Center of mass series for " + 
/*  373 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } 
/*      */ 
/*      */     
/*  377 */     for (int index3 = 0; index3 < this._listArrayList.size(); index3 += 2) {
/*  378 */       String s5 = (String)this._hashCurrentDataset.get(new Integer(index3 / 2));
/*  379 */       ArrayList<? extends Integer> coll2 = (ArrayList)this._hashSliceNumber.get(new Integer(index3 / 2));
/*  380 */       int intValue7 = ((Integer)Collections.<Integer>max(coll2)).intValue();
/*  381 */       int intValue8 = ((Integer)this._hashTracks.get(s5)).intValue();
/*  382 */       float[][] array6 = this._listArrayList.get(index3);
/*  383 */       float[][] array7 = this._listArrayList.get(index3 + 1);
/*  384 */       ResultsTable resultsTable2 = new ResultsTable();
/*  385 */       String[] array8 = { "Slice", "Directionality" };
/*  386 */       for (int n2 = 0; n2 < array8.length; n2++) {
/*  387 */         resultsTable2.setHeading(n2, array8[n2]);
/*      */       }
/*  389 */       for (int n3 = 1; n3 <= intValue7; n3++) {
/*  390 */         double computeChemotaxisIndex = ChemotaxisStatistic.computeChemotaxisIndex(array6, array7, n3, 
/*  391 */             intValue8, coll2);
/*  392 */         resultsTable2.incrementCounter();
/*  393 */         resultsTable2.addValue(0, n3);
/*  394 */         resultsTable2.addValue(1, computeChemotaxisIndex);
/*      */       } 
/*      */       
/*  397 */       resultsTable2.save(SPTBatch_.directChemo + File.separator + "Directionality series for " + 
/*  398 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } 
/*      */     
/*  401 */     for (int index5 = 0; index5 < this._listArrayList.size(); index5 += 2) {
/*  402 */       String s7 = (String)this._hashCurrentDataset.get(new Integer(index5 / 2));
/*  403 */       ArrayList<? extends Integer> coll3 = (ArrayList)this._hashSliceNumber.get(new Integer(index5 / 2));
/*  404 */       int intValue11 = ((Integer)this._hashTracks.get(s7)).intValue();
/*  405 */       int intValue12 = ((Integer)Collections.<Integer>max(coll3)).intValue();
/*  406 */       float[][] array12 = this._listArrayList.get(index5);
/*  407 */       float[][] array13 = this._listArrayList.get(index5 + 1);
/*  408 */       ResultsTable resultsTable4 = new ResultsTable();
/*  409 */       String[] array14 = { "Slice", "x FMI", "y FMI" };
/*  410 */       for (int n6 = 0; n6 < array14.length; n6++) {
/*  411 */         resultsTable4.setHeading(n6, array14[n6]);
/*      */       }
/*  413 */       for (int n7 = 1; n7 <= intValue12; n7++) {
/*  414 */         double computeFMIIndex = ChemotaxisStatistic.computeFMIIndex(array12, array13, n7, intValue11, 1, 
/*  415 */             coll3);
/*  416 */         double computeFMIIndex2 = ChemotaxisStatistic.computeFMIIndex(array12, array13, n7, intValue11, 2, 
/*  417 */             coll3);
/*  418 */         resultsTable4.incrementCounter();
/*  419 */         resultsTable4.addValue(0, n7);
/*  420 */         resultsTable4.addValue(1, computeFMIIndex2);
/*  421 */         resultsTable4.addValue(2, computeFMIIndex);
/*      */       } 
/*      */       
/*  424 */       resultsTable4.save(SPTBatch_.directChemo + File.separator + "FMI index series for " + 
/*  425 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  430 */     for (int index4 = 0; index4 < this._listArrayList.size(); index4 += 2) {
/*  431 */       String s6 = (String)this._hashCurrentDataset.get(new Integer(index4 / 2));
/*  432 */       ArrayList<Integer> list3 = (ArrayList<Integer>)this._hashSliceNumber.get(new Integer(index4 / 2));
/*  433 */       int intValue9 = ((Integer)this._hashTracks.get(s6)).intValue();
/*  434 */       float[][] array9 = this._listArrayList.get(index4);
/*  435 */       float[][] array10 = this._listArrayList.get(index4 + 1);
/*  436 */       ArrayList<Double> computeDirectionality = ChemotaxisStatistic.computeDirectionality(array9, array10, list3, 
/*  437 */           intValue9);
/*  438 */       ResultsTable resultsTable3 = new ResultsTable();
/*  439 */       String[] array11 = { "Track Number", "Directionality", "Endpoint X Value", "Endpoint Y Value" };
/*  440 */       for (int n4 = 0; n4 < array11.length; n4++) {
/*  441 */         resultsTable3.setHeading(n4, array11[n4]);
/*      */       }
/*  443 */       for (int n5 = 0; n5 < intValue9; n5++) {
/*  444 */         int intValue10 = ((Integer)list3.get(n5)).intValue();
/*  445 */         float value = array9[n5][intValue10 - 1];
/*  446 */         float value2 = array10[n5][intValue10 - 1];
/*  447 */         resultsTable3.incrementCounter();
/*  448 */         resultsTable3.addValue(0, (n5 + 1));
/*  449 */         resultsTable3.addValue(1, ((Double)computeDirectionality.get(n5)).doubleValue());
/*  450 */         resultsTable3.addValue(2, (new Float(value)).floatValue());
/*  451 */         resultsTable3.addValue(3, (new Float(value2)).floatValue());
/*      */       } 
/*      */       
/*  454 */       resultsTable3.save(SPTBatch_.directChemo + File.separator + "Directionality track series for " + 
/*  455 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } 
/*      */ 
/*      */     
/*  459 */     for (int index6 = 0; index6 < this._listArrayList.size(); index6 += 2) {
/*  460 */       String s8 = (String)this._hashCurrentDataset.get(new Integer(index6 / 2));
/*  461 */       ArrayList<Integer> list4 = (ArrayList<Integer>)this._hashSliceNumber.get(new Integer(index6 / 2));
/*  462 */       int intValue13 = ((Integer)this._hashTracks.get(s8)).intValue();
/*  463 */       float[][] array15 = this._listArrayList.get(index6);
/*  464 */       float[][] array16 = this._listArrayList.get(index6 + 1);
/*  465 */       ResultsTable resultsTable5 = new ResultsTable();
/*  466 */       String[] array17 = { "Track Number", "x FMI", "y FMI", "Endpoint X Value", "Endpoint Y Value" };
/*  467 */       for (int n8 = 0; n8 < array17.length; n8++) {
/*  468 */         resultsTable5.setHeading(n8, array17[n8]);
/*      */       }
/*  470 */       for (int index7 = 0; index7 < intValue13; index7++) {
/*  471 */         int intValue14 = ((Integer)list4.get(index7)).intValue();
/*  472 */         double n9 = 0.0D;
/*  473 */         for (int n10 = 0; n10 < intValue14 - 1; n10++) {
/*  474 */           n9 += Point2D.distance((new Float(array15[index7][n10])).floatValue(), (new Float(array16[index7][n10])).floatValue(), (
/*  475 */               new Float(array15[index7][n10 + 1])).floatValue(), (new Float(array16[index7][n10 + 1])).floatValue());
/*      */         }
/*  477 */         float value3 = array16[index7][intValue14 - 1];
/*  478 */         float value4 = array15[index7][intValue14 - 1];
/*  479 */         double roundDoubleNumbers = roundDoubleNumbers(value4 / n9);
/*  480 */         double roundDoubleNumbers2 = roundDoubleNumbers(value3 / n9);
/*  481 */         resultsTable5.incrementCounter();
/*  482 */         resultsTable5.addValue(0, (index7 + 1));
/*  483 */         resultsTable5.addValue(1, roundDoubleNumbers);
/*  484 */         resultsTable5.addValue(2, roundDoubleNumbers2);
/*  485 */         resultsTable5.addValue(3, (new Float(value4)).floatValue());
/*  486 */         resultsTable5.addValue(4, (new Float(value3)).floatValue());
/*      */       } 
/*      */       
/*  489 */       resultsTable5.save(SPTBatch_.directChemo + File.separator + "FMI track series for " + 
/*  490 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } 
/*      */ 
/*      */     
/*  494 */     for (int index8 = 0; index8 < this._listArrayList.size(); index8 += 2) {
/*  495 */       String s9 = (String)this._hashCurrentDataset.get(new Integer(index8 / 2));
/*  496 */       ArrayList list5 = (ArrayList)this._hashSliceNumber.get(new Integer(index8 / 2));
/*  497 */       int intValue15 = ((Integer)this._hashTracks.get(s9)).intValue();
/*  498 */       ArrayList<E> computeDistandVelocity = ChemotaxisStatistic.computeDistandVelocity("velocity", 
/*  499 */           this._listArrayList.get(index8), this._listArrayList.get(index8 + 1), list5, 
/*  500 */           intValue15, this._timeInterval);
/*  501 */       ResultsTable resultsTable6 = new ResultsTable();
/*  502 */       String[] array18 = { "Track Number", "Velocity [" + this._unitsPath + "/" + this._unitsTime + "]" };
/*  503 */       for (int n11 = 0; n11 < array18.length; n11++) {
/*  504 */         resultsTable6.setHeading(n11, array18[n11]);
/*      */       }
/*  506 */       for (int index9 = 0; index9 < intValue15; index9++) {
/*  507 */         float roundFloatNumbers = 
/*  508 */           roundFloatNumbers(Float.valueOf(computeDistandVelocity.get(index9).toString()).floatValue());
/*  509 */         resultsTable6.incrementCounter();
/*  510 */         resultsTable6.addValue(0, (index9 + 1));
/*  511 */         resultsTable6.addValue(1, roundFloatNumbers);
/*      */       } 
/*      */       
/*  514 */       resultsTable6.save(SPTBatch_.directChemo + File.separator + "Velocity series for " + 
/*  515 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } 
/*      */ 
/*      */     
/*  519 */     for (int index10 = 0; index10 < this._listArrayList.size(); index10 += 2) {
/*  520 */       String s10 = (String)this._hashCurrentDataset.get(new Integer(index10 / 2));
/*  521 */       ArrayList list6 = (ArrayList)this._hashSliceNumber.get(new Integer(index10 / 2));
/*  522 */       int intValue16 = ((Integer)this._hashTracks.get(s10)).intValue();
/*  523 */       float[][] array19 = this._listArrayList.get(index10);
/*  524 */       float[][] array20 = this._listArrayList.get(index10 + 1);
/*  525 */       ArrayList<E> computeDistandVelocity2 = ChemotaxisStatistic.computeDistandVelocity("accumulated distance", 
/*  526 */           array19, array20, list6, intValue16, this._timeInterval);
/*  527 */       ArrayList<E> computeDistandVelocity3 = ChemotaxisStatistic.computeDistandVelocity("euclid distance", 
/*  528 */           array19, array20, list6, intValue16, this._timeInterval);
/*  529 */       ResultsTable resultsTable7 = new ResultsTable();
/*  530 */       String[] array21 = { "Track Number", "Accumulated distance [" + this._unitsPath + "]", 
/*  531 */           "Euclidean distance [" + this._unitsPath + "]" };
/*  532 */       for (int n12 = 0; n12 < array21.length; n12++) {
/*  533 */         resultsTable7.setHeading(n12, array21[n12]);
/*      */       }
/*  535 */       for (int n13 = 0; n13 < intValue16; n13++) {
/*  536 */         float roundFloatNumbers2 = 
/*  537 */           roundFloatNumbers(Float.valueOf(computeDistandVelocity2.get(n13).toString()).floatValue());
/*  538 */         float roundFloatNumbers3 = 
/*  539 */           roundFloatNumbers(Float.valueOf(computeDistandVelocity3.get(n13).toString()).floatValue());
/*  540 */         resultsTable7.incrementCounter();
/*  541 */         resultsTable7.addValue(0, (n13 + 1));
/*  542 */         resultsTable7.addValue(1, roundFloatNumbers2);
/*  543 */         resultsTable7.addValue(2, roundFloatNumbers3);
/*      */       } 
/*      */       
/*  546 */       resultsTable7.save(SPTBatch_.directChemo + File.separator + "Distance series for " + 
/*  547 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } 
/*      */ 
/*      */     
/*  551 */     String s18 = "1: prueba2.xls";
/*  552 */     int intValue27 = ((Integer)this._hashImportedDataset.get("1: prueba2.xls")).intValue();
/*  553 */     ResultsTable resultsTable13 = new ResultsTable();
/*  554 */     String[] array66 = { "Track n", "Slice n", "X", "Y" };
/*  555 */     for (int n65 = 0; n65 < array66.length; n65++) {
/*  556 */       resultsTable13.setHeading(n65, array66[n65]);
/*      */     }
/*  558 */     ArrayList<Float> list14 = this._importedData.get(intValue27 - 1);
/*  559 */     for (int index19 = 0; index19 < list14.size(); index19 += 4) {
/*  560 */       resultsTable13.incrementCounter();
/*  561 */       resultsTable13.addValue(0, ((Float)list14.get(index19)).floatValue());
/*  562 */       resultsTable13.addValue(1, ((Float)list14.get(index19 + 1)).floatValue());
/*  563 */       resultsTable13.addValue(2, ((Float)list14.get(index19 + 2)).floatValue());
/*  564 */       resultsTable13.addValue(3, ((Float)list14.get(index19 + 3)).floatValue());
/*      */     } 
/*      */     
/*  567 */     resultsTable13.save(SPTBatch_.directChemo + File.separator + "Original data for " + 
/*  568 */         SPTBatch_.imps.getShortTitle() + ".xls");
/*      */ 
/*      */     
/*  571 */     String s19 = "1: prueba2.xls";
/*  572 */     if (this._hashCurrentDataset.contains("1: prueba2.xls")) {
/*  573 */       int intValue28 = ((Integer)this._hashCurrentPosition.get("1: prueba2.xls")).intValue();
/*  574 */       ArrayList<Integer> list15 = (ArrayList<Integer>)this._hashSliceNumber.get(new Integer(intValue28));
/*  575 */       int index20 = intValue28 * 2;
/*  576 */       int intValue29 = ((Integer)this._hashTracks.get("1: prueba2.xls")).intValue();
/*  577 */       float[][] array67 = this._listArrayList.get(index20);
/*  578 */       float[][] array68 = this._listArrayList.get(index20 + 1);
/*  579 */       ResultsTable resultsTable14 = new ResultsTable();
/*  580 */       String[] array69 = { "Track n", "Slice n", "X [" + this._unitsPath + "]", 
/*  581 */           "Y [" + this._unitsPath + "]" };
/*  582 */       for (int n66 = 0; n66 < array69.length; n66++) {
/*  583 */         resultsTable14.setHeading(n66, array69[n66]);
/*      */       }
/*  585 */       for (int index21 = 0; index21 < intValue29; index21++) {
/*  586 */         for (int intValue30 = ((Integer)list15.get(index21)).intValue(), n67 = 0; n67 < intValue30; n67++) {
/*  587 */           resultsTable14.incrementCounter();
/*  588 */           resultsTable14.addValue(0, (new Integer(index21 + 1)).intValue());
/*  589 */           resultsTable14.addValue(1, (new Integer(n67 + 1)).intValue());
/*  590 */           resultsTable14.addValue(2, (new Float(array67[index21][n67])).floatValue());
/*  591 */           resultsTable14.addValue(3, (new Float(array68[index21][n67])).floatValue());
/*      */         } 
/*      */       } 
/*      */       
/*  595 */       resultsTable14.save(SPTBatch_.directChemo + File.separator + "Current used data for " + 
/*  596 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*      */     } else {
/*  598 */       JOptionPane.showMessageDialog((Component)this.gui, "No current data available!");
/*      */     } 
/*      */     
/*  601 */     this._hashPlot.clear();
/*  602 */     int n92 = 0;
/*  603 */     String s27 = "Mark up/down";
/*      */     
/*  605 */     if ("Mark up/down".equals("Mark up/down")) {
/*  606 */       n92 = 3;
/*      */     }
/*      */     
/*  609 */     if (this._dialog.auto) {
/*  610 */       float abs = 0.0F;
/*  611 */       float abs2 = 0.0F;
/*  612 */       if (this._listArrayList.size() > 1) {
/*  613 */         ArrayList<Float> coll4 = new ArrayList();
/*  614 */         for (int index27 = 0; index27 < this._listArrayList.size(); index27 += 2) {
/*  615 */           int intValue43 = ((Integer)this._hashTracks
/*  616 */             .get(this._hashCurrentDataset.get(new Integer(index27 / 2)))).intValue();
/*  617 */           ArrayList<Integer> list27 = (ArrayList<Integer>)this._hashSliceNumber
/*  618 */             .get(new Integer(index27 / 2));
/*  619 */           float[][] array71 = this._listArrayList.get(index27);
/*  620 */           float[][] array72 = this._listArrayList.get(index27 + 1);
/*  621 */           for (int index28 = 0; index28 < intValue43; index28++) {
/*  622 */             for (int intValue44 = ((Integer)list27.get(index28)).intValue(), n93 = 1; n93 < intValue44; n93++) {
/*  623 */               if (abs < Math.abs(array71[index28][n93])) {
/*  624 */                 abs = Math.abs(array71[index28][n93]);
/*      */               }
/*  626 */               if (abs2 < Math.abs(array72[index28][n93])) {
/*  627 */                 abs2 = Math.abs(array72[index28][n93]);
/*      */               }
/*      */             } 
/*      */           } 
/*  631 */           if (abs > abs2) {
/*  632 */             coll4.add(new Float(abs));
/*      */           } else {
/*  634 */             coll4.add(new Float(abs2));
/*      */           } 
/*      */         } 
/*  637 */         this._coordSize = ((Float)Collections.<Float>max(coll4)).floatValue() + 5.0F;
/*      */       } 
/*      */     } 
/*      */     
/*  641 */     for (int n94 = 0; n94 < this._listArrayList.size(); n94 += 2) {
/*  642 */       plotGraph(n92, n94);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   float angleCorrection(float n) {
/*  648 */     float n2 = n;
/*  649 */     if (n2 < 0.0F) {
/*  650 */       n2 = n2 % 360.0F + 360.0F;
/*      */     }
/*  652 */     if (n2 > 360.0F) {
/*  653 */       n2 %= 360.0F;
/*      */     }
/*  655 */     return n2;
/*      */   }
/*      */   
/*      */   float[] calculatePoints(float n) {
/*  659 */     float n2 = this._coordSize * 2.0F;
/*  660 */     return new float[] { roundNumber((new Float(n2 * Math.cos(Math.toRadians(n)))).floatValue()), 
/*  661 */         roundNumber((new Float(n2 * Math.sin(Math.toRadians(n)))).floatValue()) };
/*      */   }
/*      */   
/*      */   int countCells(float n, int index) {
/*  665 */     String key = (String)this._hashCurrentDataset.get(new Integer(index / 2));
/*  666 */     ArrayList<Integer> list = (ArrayList<Integer>)this._hashSliceNumber.get(new Integer(index / 2));
/*  667 */     int intValue = ((Integer)this._hashTracks.get(key)).intValue();
/*  668 */     float[][] array = this._listArrayList.get(index);
/*  669 */     float[][] array2 = this._listArrayList.get(index + 1);
/*  670 */     int n2 = 0;
/*  671 */     float angleCorrection = angleCorrection(this._anglePosition + n / 2.0F);
/*  672 */     float angleCorrection2 = angleCorrection(this._anglePosition - n / 2.0F);
/*  673 */     float roundNumber = roundNumber((new Float(Math.tan(Math.toRadians(angleCorrection)))).floatValue());
/*  674 */     float roundNumber2 = roundNumber((new Float(Math.tan(Math.toRadians(angleCorrection2)))).floatValue());
/*  675 */     for (int i = 0; i < intValue; i++) {
/*  676 */       int intValue2 = ((Integer)list.get(i)).intValue();
/*  677 */       float n3 = array[i][intValue2 - 1];
/*  678 */       float n4 = array2[i][intValue2 - 1];
/*  679 */       if (((angleCorrection > 0.0F && angleCorrection < 90.0F && angleCorrection2 > 0.0F && 
/*  680 */         angleCorrection2 < 90.0F) || (
/*  681 */         angleCorrection > 270.0F && angleCorrection < 360.0F && angleCorrection2 > 270.0F && 
/*  682 */         angleCorrection2 < 360.0F)) && 
/*  683 */         n4 <= roundNumber * n3 && n4 >= roundNumber2 * n3) {
/*  684 */         n2++;
/*      */       }
/*  686 */       if (((angleCorrection > 90.0F && angleCorrection < 180.0F && angleCorrection2 > 90.0F && 
/*  687 */         angleCorrection2 < 180.0F) || (
/*  688 */         angleCorrection > 180.0F && angleCorrection < 270.0F && angleCorrection2 > 180.0F && 
/*  689 */         angleCorrection2 < 270.0F)) && 
/*  690 */         n4 >= roundNumber * n3 && n4 <= roundNumber2 * n3) {
/*  691 */         n2++;
/*      */       }
/*  693 */       if (angleCorrection > 0.0F && angleCorrection < 90.0F) {
/*  694 */         if (angleCorrection2 > 180.0F && angleCorrection2 < 270.0F && n4 <= roundNumber * n3 && 
/*  695 */           n4 <= roundNumber2 * n3) {
/*  696 */           n2++;
/*      */         }
/*  698 */         if (angleCorrection2 > 270.0F && angleCorrection2 < 360.0F && n4 <= roundNumber * n3 && 
/*  699 */           n4 >= roundNumber2 * n3) {
/*  700 */           n2++;
/*      */         }
/*  702 */         if (angleCorrection2 == 270.0F && n3 >= 0.0F && n4 <= roundNumber * n3) {
/*  703 */           n2++;
/*      */         }
/*  705 */         if (angleCorrection2 == 0.0F && n4 >= 0.0F && n4 <= roundNumber * n3) {
/*  706 */           n2++;
/*      */         }
/*      */       } 
/*  709 */       if (angleCorrection > 90.0F && angleCorrection < 180.0F) {
/*  710 */         if (angleCorrection2 > 0.0F && angleCorrection2 < 90.0F && n4 >= roundNumber * n3 && 
/*  711 */           n4 >= roundNumber2 * n3) {
/*  712 */           n2++;
/*      */         }
/*  714 */         if (angleCorrection2 > 270.0F && angleCorrection2 < 360.0F && n4 >= roundNumber * n3 && 
/*  715 */           n4 >= roundNumber2 * n3) {
/*  716 */           n2++;
/*      */         }
/*  718 */         if (angleCorrection2 == 90.0F && n3 <= 0.0F && n4 >= roundNumber * n3) {
/*  719 */           n2++;
/*      */         }
/*  721 */         if (angleCorrection2 == 0.0F && n4 >= 0.0F && n4 >= roundNumber * n3) {
/*  722 */           n2++;
/*      */         }
/*      */       } 
/*  725 */       if (angleCorrection > 180.0F && angleCorrection < 270.0F) {
/*  726 */         if (angleCorrection2 > 90.0F && angleCorrection2 < 180.0F && n4 >= roundNumber * n3 && 
/*  727 */           n4 <= roundNumber2 * n3) {
/*  728 */           n2++;
/*      */         }
/*  730 */         if (angleCorrection2 > 0.0F && angleCorrection2 < 90.0F && n4 >= roundNumber * n3 && 
/*  731 */           n4 >= roundNumber2 * n3) {
/*  732 */           n2++;
/*      */         }
/*  734 */         if (angleCorrection2 == 90.0F && n3 <= 0.0F && n4 >= roundNumber * n3) {
/*  735 */           n2++;
/*      */         }
/*  737 */         if (angleCorrection2 == 180.0F && n4 <= 0.0F && n4 >= roundNumber * n3) {
/*  738 */           n2++;
/*      */         }
/*      */       } 
/*  741 */       if (angleCorrection > 270.0F && angleCorrection < 360.0F) {
/*  742 */         if (angleCorrection2 > 180.0F && angleCorrection2 < 270.0F && n4 <= roundNumber * n3 && 
/*  743 */           n4 <= roundNumber2 * n3) {
/*  744 */           n2++;
/*      */         }
/*  746 */         if (angleCorrection2 > 90.0F && angleCorrection2 < 180.0F && n4 <= roundNumber * n3 && 
/*  747 */           n4 <= roundNumber2 * n3) {
/*  748 */           n2++;
/*      */         }
/*  750 */         if (angleCorrection2 == 180.0F && n4 <= 0.0F && n4 <= roundNumber * n3) {
/*  751 */           n2++;
/*      */         }
/*  753 */         if (angleCorrection2 == 270.0F && n3 >= 0.0F && n4 <= roundNumber * n3) {
/*  754 */           n2++;
/*      */         }
/*      */       } 
/*  757 */       if (angleCorrection == 90.0F) {
/*  758 */         if (((angleCorrection2 > 0.0F && angleCorrection2 < 90.0F) || (
/*  759 */           angleCorrection2 > 270.0F && angleCorrection2 < 360.0F)) && n3 >= 0.0F && 
/*  760 */           n4 >= roundNumber2 * n3) {
/*  761 */           n2++;
/*      */         }
/*  763 */         if (angleCorrection2 == 0.0F && n3 >= 0.0F && n4 >= 0.0F) {
/*  764 */           n2++;
/*      */         }
/*  766 */         if (angleCorrection2 == 270.0F && n3 >= 0.0F) {
/*  767 */           n2++;
/*      */         }
/*      */       } 
/*  770 */       if (angleCorrection == 180.0F) {
/*  771 */         if (((angleCorrection2 > 0.0F && angleCorrection2 < 90.0F) || (
/*  772 */           angleCorrection2 > 90.0F && angleCorrection2 < 180.0F)) && n4 >= 0.0F && 
/*  773 */           n4 <= roundNumber2 * n3) {
/*  774 */           n2++;
/*      */         }
/*  776 */         if (angleCorrection2 == 0.0F && n4 >= 0.0F) {
/*  777 */           n2++;
/*      */         }
/*  779 */         if (angleCorrection2 == 90.0F && n3 <= 0.0F && n4 >= 0.0F) {
/*  780 */           n2++;
/*      */         }
/*      */       } 
/*  783 */       if (angleCorrection == 270.0F) {
/*  784 */         if (((angleCorrection2 > 90.0F && angleCorrection2 < 180.0F) || (
/*  785 */           angleCorrection2 > 180.0F && angleCorrection2 < 270.0F)) && n3 <= 0.0F && 
/*  786 */           n4 <= roundNumber2 * n3) {
/*  787 */           n2++;
/*      */         }
/*  789 */         if (angleCorrection2 == 90.0F && n3 <= 0.0F) {
/*  790 */           n2++;
/*      */         }
/*  792 */         if (angleCorrection2 == 180.0F && n3 <= 0.0F && n4 <= 0.0F) {
/*  793 */           n2++;
/*      */         }
/*      */       } 
/*  796 */       if (angleCorrection == 360.0F) {
/*  797 */         if (angleCorrection2 > 180.0F && angleCorrection2 < 270.0F && n4 <= 0.0F && n4 <= roundNumber2 * n3) {
/*  798 */           n2++;
/*      */         }
/*  800 */         if (angleCorrection2 > 270.0F && angleCorrection2 < 360.0F && n4 <= 0.0F && n4 >= roundNumber2 * n3) {
/*  801 */           n2++;
/*      */         }
/*  803 */         if (angleCorrection2 == 180.0F && n4 <= 0.0F) {
/*  804 */           n2++;
/*      */         }
/*  806 */         if (angleCorrection2 == 270.0F && n3 >= 0.0F && n4 <= 0.0F) {
/*  807 */           n2++;
/*      */         }
/*      */       } 
/*      */     } 
/*  811 */     return n2;
/*      */   }
/*      */   
/*      */   void plotGraph(int value, int n) {
/*  815 */     String s = (String)this._hashCurrentDataset.get(new Integer(n / 2));
/*  816 */     ArrayList<Integer> list = (ArrayList<Integer>)this._hashSliceNumber.get(new Integer(n / 2));
/*  817 */     int intValue = ((Integer)this._hashTracks.get(s)).intValue();
/*  818 */     float[][] array = this._listArrayList.get(n);
/*  819 */     float[][] array2 = this._listArrayList.get(n + 1);
/*  820 */     double roundDoubleNumbers = 0.0D;
/*  821 */     double roundDoubleNumbers2 = 0.0D;
/*  822 */     int i = 0;
/*  823 */     int j = 0;
/*  824 */     ArrayList<Double> list2 = null;
/*      */     
/*  826 */     for (int k = 0; k < intValue; k++) {
/*  827 */       int intValue2 = ((Integer)list.get(k)).intValue();
/*  828 */       roundDoubleNumbers += array[k][intValue2 - 1];
/*  829 */       roundDoubleNumbers2 += array2[k][intValue2 - 1];
/*      */     } 
/*  831 */     if (intValue != 0) {
/*  832 */       roundDoubleNumbers /= intValue;
/*  833 */       roundDoubleNumbers2 /= intValue;
/*      */     } 
/*  835 */     roundDoubleNumbers = roundDoubleNumbers(roundDoubleNumbers);
/*  836 */     roundDoubleNumbers2 = roundDoubleNumbers(roundDoubleNumbers2);
/*      */     
/*  838 */     float[] array3 = { 0.0F };
/*  839 */     Plot plot = new Plot(s, "x axis [" + this._unitsPath + "]", "y axis  [" + this._unitsPath + "]", array3, 
/*  840 */         array3);
/*  841 */     if (SPTBatch_.chemoScaling.getText().contains("...") == Boolean.TRUE.booleanValue()) {
/*  842 */       float[] array4 = { this._coordSize, -this._coordSize };
/*  843 */       float[] array5 = new float[2];
/*  844 */       plot.setLimits(-this._coordSize, this._coordSize, -this._coordSize, 
/*  845 */           this._coordSize);
/*  846 */       plot.addPoints(array4, array5, 2);
/*  847 */       plot.addPoints(array5, array4, 2);
/*      */     } 
/*  849 */     if (SPTBatch_.chemoScaling.getText().contains("...") == Boolean.FALSE.booleanValue()) {
/*  850 */       float[] array6 = { -Integer.valueOf(SPTBatch_.chemoScaling.getText()).intValue(), 
/*  851 */           Integer.valueOf(SPTBatch_.chemoScaling.getText()).intValue() };
/*  852 */       float[] array7 = new float[2];
/*  853 */       float[] array8 = { -Integer.valueOf(SPTBatch_.chemoScaling.getText()).intValue(), 
/*  854 */           Integer.valueOf(SPTBatch_.chemoScaling.getText()).intValue() };
/*  855 */       plot.setLimits(-Double.valueOf(SPTBatch_.chemoScaling.getText()).doubleValue(), 
/*  856 */           Double.valueOf(SPTBatch_.chemoScaling.getText()).doubleValue(), -Double.valueOf(SPTBatch_.chemoScaling.getText()).doubleValue(), 
/*  857 */           Double.valueOf(SPTBatch_.chemoScaling.getText()).doubleValue());
/*  858 */       plot.addPoints(array6, array7, 2);
/*  859 */       plot.addPoints(array7, array8, 2);
/*      */     } 
/*  861 */     plot.draw();
/*  862 */     plot.setLineWidth(1);
/*  863 */     float[] array9 = { 0.0F };
/*  864 */     float[] array10 = { 0.0F };
/*      */     
/*  866 */     if (value == 3) {
/*  867 */       for (int index = 0; index < intValue; index++) {
/*  868 */         int intValue4 = ((Integer)list.get(index)).intValue();
/*  869 */         float[] array13 = new float[intValue4];
/*  870 */         float[] array14 = new float[intValue4];
/*  871 */         array9[0] = array[index][intValue4 - 1];
/*  872 */         array10[0] = array2[index][intValue4 - 1];
/*  873 */         if (array10[0] >= 0.0F) {
/*  874 */           plot.setColor(Color.black);
/*  875 */           i++;
/*      */         } else {
/*  877 */           plot.setColor(Color.red);
/*  878 */           j++;
/*      */         } 
/*      */         
/*  881 */         for (int n3 = 0; n3 < intValue4; n3++) {
/*  882 */           array13[n3] = array[index][n3];
/*  883 */           array14[n3] = array2[index][n3];
/*      */         } 
/*  885 */         plot.setLineWidth(1);
/*  886 */         plot.addPoints(array13, array14, 2);
/*      */         
/*  888 */         plot.setLineWidth(3);
/*  889 */         plot.addPoints(array9, array10, 0);
/*      */       } 
/*  891 */       plot.setColor(Color.black);
/*      */       
/*  893 */       plot.addLabel(0.0D, 0.0D, "Number of tracks: " + intValue + "  Counts up: " + i + "  Counts down: " + j);
/*      */       
/*  895 */       plot.addLabel(0.0D, 0.04D, 
/*  896 */           "Center of mass [" + this._unitsPath + "]: x=" + roundDoubleNumbers + " y=" + roundDoubleNumbers2);
/*      */     } 
/*      */ 
/*      */     
/*  900 */     float[] array19 = { (new Float(roundDoubleNumbers)).floatValue() };
/*  901 */     float[] array20 = { (new Float(roundDoubleNumbers2)).floatValue() };
/*  902 */     plot.setColor(Color.blue);
/*  903 */     plot.setLineWidth(3);
/*  904 */     plot.addPoints(array19, array20, 5);
/*      */ 
/*      */     
/*  907 */     this._currentOpenWindows.size(); this._listArrayList.size() / 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  916 */     ArrayList<Integer> value2 = new ArrayList<>();
/*  917 */     value2.add(new Integer(value));
/*  918 */     value2.add(new Integer(n));
/*  919 */     value2.add(new Integer(intValue));
/*  920 */     this._hashPlot.put(s, value2);
/*      */ 
/*      */ 
/*      */     
/*  924 */     IJ.save(plot.getImagePlus(), SPTBatch_.directChemo + File.separator + "Chemotaxis Plot for " + 
/*  925 */         SPTBatch_.imps.getShortTitle() + ".png");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void readData(String string) throws FileNotFoundException, IOException {
/*  932 */     this._importedData.add(this.arrayToImport);
/*  933 */     IJ.showStatus("Dataset imported");
/*  934 */     string = String.valueOf(this._importedData.size()) + ": " + string;
/*  935 */     this._hashImportedDataset.put(string, new Integer(this._importedData.size()));
/*      */   }
/*      */ 
/*      */   
/*      */   double roundDoubleNumbers(double n) {
/*  940 */     return Math.round(n * 100.0D) / 100.0D;
/*      */   }
/*      */   
/*      */   float roundFloatNumbers(float n) {
/*  944 */     return Math.round(n * 100.0F) / 100.0F;
/*      */   }
/*      */   
/*      */   float roundNumber(float n) {
/*  948 */     return Math.round(n * 10000.0F) / 10000.0F;
/*      */   }
/*      */   
/*      */   public void stateChanged(ChangeEvent changeEvent) {
/*  952 */     int selectedIndex = ((JTabbedPane)changeEvent.getSource()).getSelectedIndex();
/*  953 */     if (selectedIndex == 1) {
/*  954 */       if (this._dialog.auto) {
/*  955 */         PlotWindow.plotHeight = this._plotHeight;
/*  956 */         PlotWindow.plotWidth = this._plotWidth;
/*      */       } else {
/*  958 */         PlotWindow.plotHeight = (int)(this._dialog.fraction * (
/*  959 */           Math.abs(this._dialog.minimumY) + Math.abs(this._dialog.maximumY)));
/*  960 */         PlotWindow.plotWidth = (int)(this._dialog.fraction * (
/*  961 */           Math.abs(this._dialog.minimumX) + Math.abs(this._dialog.maximumX)));
/*      */       } 
/*      */     }
/*  964 */     if (selectedIndex == 3) {
/*  965 */       PlotWindow.plotHeight = this._plotHeight;
/*  966 */       PlotWindow.plotWidth = this._plotWidth;
/*      */     } 
/*      */   }
/*      */   
/*      */   void updateNumberofTracks(String s, int n) {
/*  971 */     if (this._hashTracks.containsKey(s)) {
/*  972 */       this._hashTracks.remove(s);
/*  973 */       this._hashTracks.put(s, new Integer(n));
/*      */     } else {
/*  975 */       this._hashTracks.put(s, new Integer(n));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void windowActivated(WindowEvent windowEvent) {}
/*      */ 
/*      */   
/*      */   public void windowClosed(WindowEvent windowEvent) {}
/*      */   
/*      */   public void windowClosing(WindowEvent windowEvent) {
/*  986 */     if (windowEvent.getSource() == this.gui) {
/*  987 */       if (this._openInfoWindows != null) {
/*  988 */         for (int i = 0; i < this._openInfoWindows.size(); i++) {
/*  989 */           ((JFrame)this._openInfoWindows.get(i)).dispose();
/*      */         }
/*  991 */         this._openInfoWindows.clear();
/*      */       } 
/*  993 */       this._currentOpenWindows.clear();
/*  994 */       this._currentOpenDiagrams.clear();
/*  995 */       WindowManager.closeAllWindows();
/*  996 */       this.gui.dispose();
/*      */     } else {
/*  998 */       if (windowEvent.getSource() instanceof JFrame && this._openInfoWindows.contains(windowEvent.getSource())) {
/*  999 */         this._openInfoWindows.remove(windowEvent.getSource());
/*      */       }
/* 1001 */       if (windowEvent.getSource() instanceof PlotWindow) {
/* 1002 */         if (this._currentOpenWindows.contains(windowEvent.getSource())) {
/* 1003 */           for (int j = 0; j < this._currentOpenWindows.size(); j++) {
/* 1004 */             ((Window)this._currentOpenWindows.get(j)).dispose();
/*      */           }
/* 1006 */           this._currentOpenWindows.clear();
/* 1007 */           this.gui.plotClosed();
/*      */         } 
/* 1009 */         if (this._currentOpenDiagrams.contains(windowEvent.getSource())) {
/* 1010 */           for (int k = 0; k < this._currentOpenDiagrams.size(); k++) {
/* 1011 */             ((Window)this._currentOpenDiagrams.get(k)).dispose();
/*      */           }
/* 1013 */           this._currentOpenDiagrams.clear();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void windowDeactivated(WindowEvent windowEvent) {}
/*      */   
/*      */   public void windowDeiconified(WindowEvent windowEvent) {}
/*      */   
/*      */   public void windowIconified(WindowEvent windowEvent) {}
/*      */   
/*      */   public void windowOpened(WindowEvent windowEvent) {}
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/Chemotaxis_ToolModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */