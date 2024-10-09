import ij.IJ;
import ij.WindowManager;
import ij.gui.Plot;
import ij.gui.PlotWindow;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;

public class Chemotaxis_ToolModified implements PlugIn {
   ChemotaxisGUI gui;
   ScalingDialog _dialog;
   float[][] x_values;
   float[][] y_values;
   float _angleBetweenCircle;
   float _angleBetweenDiagram;
   ArrayList _openInfoWindows;
   ArrayList _selectedDatasets;
   ArrayList _currentOpenWindows;
   ArrayList _currentOpenDiagrams;
   Vector _maxVector;
   int _maxPosition;
   float _anglePosition;
   int _currentSelectedDataset;
   ArrayList _variableSliceNumber;
   ArrayList _importedData;
   Hashtable _hashSliceNumber;
   LinkedList _listArrayList;
   Hashtable _hashImportedDataset;
   Hashtable _hashSlicesImported;
   Hashtable _hashTracks;
   Hashtable _hashCurrentDataset;
   Hashtable _hashCurrentPosition;
   Hashtable _hashPlot;
   Hashtable _hashWindow;
   float _coordSize;
   float _calxy;
   double _timeInterval;
   String _unitsPath;
   String _unitsTime;
   int _plotHeight;
   int _plotWidth;
   ArrayList arrayToImport;

   public Chemotaxis_ToolModified(ArrayList arrayToImport) {
      this.arrayToImport = arrayToImport;
      this.gui = null;
      this._dialog = null;
      this._angleBetweenCircle = 66.0F;
      this._angleBetweenDiagram = 66.0F;
      this._maxVector = null;
      this._maxPosition = 0;
      this._anglePosition = 0.0F;
      this._variableSliceNumber = null;
      this._coordSize = 0.0F;
      this._calxy = 1.0F;
      this._timeInterval = 2.0D;
   }

   public void run(String var1) {
      PlotWindow.plotHeight = 500;
      PlotWindow.plotWidth = 500;
      this._plotHeight = 500;
      this._plotWidth = 500;
      (this._dialog = new ScalingDialog(this.gui)).setHeight(this._plotHeight);
      this._dialog.setWidth(this._plotWidth);
      this._openInfoWindows = new ArrayList();
      this._hashImportedDataset = new Hashtable();
      this._hashSlicesImported = new Hashtable();
      this._hashTracks = new Hashtable();
      this._hashCurrentDataset = new Hashtable();
      this._hashCurrentPosition = new Hashtable();
      this._hashPlot = new Hashtable();
      this._hashWindow = new Hashtable();
      this._currentOpenWindows = new ArrayList();
      this._currentOpenDiagrams = new ArrayList();
      this._importedData = new ArrayList();
      this._hashSliceNumber = new Hashtable();
      this._selectedDatasets = new ArrayList();
      this._listArrayList = new LinkedList();
      this._maxVector = new Vector();
      this._selectedDatasets.clear();
      this._hashSlicesImported.clear();
      this._hashImportedDataset.clear();
      this._importedData.clear();
      this._hashSliceNumber.clear();
      this._listArrayList.clear();
      this._hashTracks.clear();
      this._hashCurrentDataset.clear();
      this._hashCurrentPosition.clear();
      this._hashPlot.clear();
      this._hashWindow.clear();

      try {
         this.readData("prueba2.xls");
      } catch (IOException var42) {
         JOptionPane.showMessageDialog(this.gui, "Error reading from file");
      }

      boolean b4 = true;
      String item = "1: prueba2.xls";
      ArrayList list16;
      ArrayList value7;
      if (this._hashSlicesImported.containsKey("1: prueba2.xls")) {
         list16 = (ArrayList)this._hashSlicesImported.get("1: prueba2.xls");
         if (list16.size() == 1) {
            try {
               list16 = new ArrayList();
               this._hashSlicesImported.remove("1: prueba2.xls");
               list16.add(1);
               list16.add(SPTBatch_.imps.getStackSize());
               if (Integer.valueOf(SPTBatch_.imps.getStackSize()) < Integer.valueOf("1")) {
                  JOptionPane.showMessageDialog(this.gui, "Second value can`t be smaller than the first");
               } else {
                  this._hashSlicesImported.put("1: prueba2.xls", list16);
               }
            } catch (NumberFormatException var41) {
               this.gui.firstSlicesField.setText("0");
               this.gui.secondSlicesField.setText("0");
               list16.add(new Integer(0));
               list16.add(new Integer(0));
               this._hashSlicesImported.put("1: prueba2.xls", list16);
               JOptionPane.showMessageDialog(this.gui, "Please enter correct value for number of slices!");
            }
         }

         if (list16.size() == 2) {
            try {
               if (1 != list16.get(0) || SPTBatch_.imps.getStackSize() != list16.get(1)) {
                  this._hashSlicesImported.remove("1: prueba2.xls");
                  value7 = new ArrayList();
                  value7.add(Integer.valueOf("1"));
                  value7.add(SPTBatch_.imps.getStackSize());
                  if (Integer.valueOf(SPTBatch_.imps.getStackSize()) < Integer.valueOf("1.0")) {
                     JOptionPane.showMessageDialog(this.gui, "Second value can`t be smaller than the first");
                  } else {
                     this._hashSlicesImported.put("1: prueba2.xls", value7);
                  }
               }
            } catch (NumberFormatException var43) {
               ArrayList<Integer> list17 = (ArrayList)this._hashSlicesImported.get("1: prueba2.xls");
               JOptionPane.showMessageDialog(this.gui, "Setting old values!");
            }
         }
      } else {
         try {
            list16 = new ArrayList();
            list16.add(Integer.valueOf("1"));
            list16.add(SPTBatch_.imps.getStackSize());
            if (Integer.valueOf(SPTBatch_.imps.getStackSize()) < Integer.valueOf("1")) {
               b4 = false;
               JOptionPane.showMessageDialog(this.gui, "Second value can`t be smaller than the first");
            } else {
               this._hashSlicesImported.put("1: prueba2.xls", list16);
            }
         } catch (NumberFormatException var40) {
            b4 = false;
            JOptionPane.showMessageDialog(this.gui, "Please enter correct value for number of slices!");
         }
      }

      boolean b5 = true;
      this._maxVector.clear();
      value7 = new ArrayList();
      String s20 = "1: prueba2.xls";
      if (!value7.contains("1: prueba2.xls")) {
         value7.add("1: prueba2.xls");
      }

      this._unitsPath = "µm";
      this._unitsTime = "min";
      int intValue31 = false;
      int intValue32 = false;
      int n68 = false;
      double n69 = 0.0D;
      double doubleValue = 0.0D;
      int n70 = false;
      double n71 = 0.0D;
      double doubleValue2 = 0.0D;
      this._calxy = new Float(1.0D);
      this._timeInterval = new Double(1.0D);
      b5 = true;
      int n72;
      String s10;
      ArrayList list20;
      int intValue16;
      ArrayList list14;
      ArrayList list23;
      ArrayList list15;
      int n94;
      int n13;
      int index27;
      int index21;
      int intValue30;
      int index28;
      int intValue44;
      int n93;
      if (b5) {
         this._listArrayList.clear();
         this._hashCurrentDataset.clear();
         this._hashSliceNumber.clear();

         for(n72 = 0; n72 < value7.size(); ++n72) {
            this._variableSliceNumber = new ArrayList();
            s10 = (String)value7.get(n72);
            this._hashCurrentDataset.put(new Integer(n72), s10);
            this._hashCurrentPosition.put(s10, new Integer(n72));
            list20 = (ArrayList)this._hashSlicesImported.get(s10);
            intValue16 = (Integer)this._hashImportedDataset.get(s10);
            list14 = new ArrayList();
            ArrayList<Float> list22 = new ArrayList();
            list23 = new ArrayList();
            list15 = (ArrayList)this._importedData.get(intValue16 - 1);

            for(n94 = 0; n94 < list15.size(); n94 += 4) {
               list14.add(list15.get(n94));
               list22.add((Float)list15.get(n94 + 2));
               list23.add((Float)list15.get(n94 + 3));
            }

            n94 = Math.round((Float)list14.get(list14.size() - 1));
            int[] array70 = new int[3 * n94];
            n13 = 1;

            for(index27 = 0; index27 <= 3 * n94 - 3; index27 += 3) {
               array70[index27] = list14.indexOf(new Float((float)n13));
               array70[index27 + 1] = list14.lastIndexOf(new Float((float)n13));
               array70[index27 + 2] = n13++;
            }

            ArrayList<Float> list25 = new ArrayList();
            ArrayList<Float> list26 = new ArrayList();
            int n75 = 0;
            if (list20.size() == 1) {
               index21 = (Integer)list20.get(0);

               for(intValue30 = 0; intValue30 <= array70.length - 3; intValue30 += 3) {
                  if (array70[intValue30 + 1] != -1 && array70[intValue30] != -1 && array70[intValue30 + 1] - array70[intValue30] == index21 - 1) {
                     ++n75;
                     this._variableSliceNumber.add(new Integer(array70[intValue30 + 1] - array70[intValue30] + 1));

                     for(index28 = array70[intValue30]; index28 <= array70[intValue30 + 1]; ++index28) {
                        list25.add(new Float((Float)list22.get(index28) * this._calxy));
                        list26.add(new Float((Float)list23.get(index28) * this._calxy));
                     }
                  }
               }
            }

            if (list20.size() == 2) {
               index21 = (Integer)list20.get(0);
               intValue30 = (Integer)list20.get(1);

               for(index28 = 0; index28 <= array70.length - 3; index28 += 3) {
                  if (array70[index28 + 1] != -1 && array70[index28] != -1 && array70[index28 + 1] - array70[index28] >= index21 - 1 && array70[index28 + 1] - array70[index28] <= intValue30 - 1) {
                     ++n75;
                     this._variableSliceNumber.add(new Integer(array70[index28 + 1] - array70[index28] + 1));

                     for(intValue44 = array70[index28]; intValue44 <= array70[index28 + 1]; ++intValue44) {
                        list25.add(new Float((Float)list22.get(intValue44) * this._calxy));
                        list26.add(new Float((Float)list23.get(intValue44) * this._calxy));
                     }
                  }
               }
            }

            this.updateNumberofTracks(s10, n75);
            if (this._variableSliceNumber.isEmpty()) {
               if (list20.size() == 1) {
                  JOptionPane.showMessageDialog(this.gui, "No tracks available for current number of slices");
               }

               if (list20.size() == 2) {
                  JOptionPane.showMessageDialog(this.gui, "No tracks available for current range of used number of slices");
               }
            } else {
               index21 = (Integer)Collections.max(this._variableSliceNumber);
               intValue30 = 0;
               this.x_values = new float[n75][index21];
               this.y_values = new float[n75][index21];

               for(index28 = 0; index28 < n75; ++index28) {
                  this.x_values[index28][0] = 0.0F;
                  this.y_values[index28][0] = 0.0F;
                  intValue44 = 1;

                  for(n93 = (Integer)this._variableSliceNumber.get(index28); intValue44 < n93; ++intValue44) {
                     float n90 = (Float)list25.get(intValue44 + intValue30) - (Float)list25.get(intValue30);
                     float n91 = -((Float)list26.get(intValue44 + intValue30) - (Float)list26.get(intValue30));
                     this.x_values[index28][intValue44] = n90;
                     this.y_values[index28][intValue44] = n91;
                  }

                  intValue30 += n93;
               }

               this._listArrayList.add(this.x_values);
               this._listArrayList.add(this.y_values);
               this._hashSliceNumber.put(new Integer(n72), this._variableSliceNumber);
            }
         }
      }

      int n65;
      float[][] array16;
      float[][] array13;
      ResultsTable resultsTable3;
      String[] array11;
      int intValue29;
      for(n72 = 0; n72 < this._listArrayList.size(); n72 += 2) {
         s10 = (String)this._hashCurrentDataset.get(new Integer(n72 / 2));
         list20 = (ArrayList)this._hashSliceNumber.get(new Integer(n72 / 2));
         intValue16 = (Integer)this._hashTracks.get(s10);
         n65 = (Integer)Collections.max(list20);
         array16 = (float[][])this._listArrayList.get(n72);
         array13 = (float[][])this._listArrayList.get(n72 + 1);
         resultsTable3 = new ResultsTable();
         array11 = new String[]{"Slice", "Center of mass x [" + this._unitsPath + "]", "Center of mass y [" + this._unitsPath + "]", "Center of mass length [" + this._unitsPath + "]"};

         for(intValue29 = 0; intValue29 < array11.length; ++intValue29) {
            resultsTable3.setHeading(intValue29, array11[intValue29]);
         }

         for(intValue29 = 1; intValue29 <= n65; ++intValue29) {
            double[] centerofMassSeries = ChemotaxisStatistic.centerofMassSeries(intValue29, array16, array13, intValue16, list20);
            resultsTable3.incrementCounter();
            resultsTable3.addValue(0, (double)intValue29);
            resultsTable3.addValue(1, centerofMassSeries[0]);
            resultsTable3.addValue(2, centerofMassSeries[1]);
            resultsTable3.addValue(3, centerofMassSeries[2]);
         }

         resultsTable3.save(SPTBatch_.directChemo + File.separator + "Center of mass series for " + SPTBatch_.imps.getShortTitle() + ".xls");
      }

      double n9;
      for(n72 = 0; n72 < this._listArrayList.size(); n72 += 2) {
         s10 = (String)this._hashCurrentDataset.get(new Integer(n72 / 2));
         list20 = (ArrayList)this._hashSliceNumber.get(new Integer(n72 / 2));
         intValue16 = (Integer)Collections.max(list20);
         n65 = (Integer)this._hashTracks.get(s10);
         array16 = (float[][])this._listArrayList.get(n72);
         array13 = (float[][])this._listArrayList.get(n72 + 1);
         resultsTable3 = new ResultsTable();
         array11 = new String[]{"Slice", "Directionality"};

         for(intValue29 = 0; intValue29 < array11.length; ++intValue29) {
            resultsTable3.setHeading(intValue29, array11[intValue29]);
         }

         for(intValue29 = 1; intValue29 <= intValue16; ++intValue29) {
            n9 = ChemotaxisStatistic.computeChemotaxisIndex(array16, array13, intValue29, n65, list20);
            resultsTable3.incrementCounter();
            resultsTable3.addValue(0, (double)intValue29);
            resultsTable3.addValue(1, n9);
         }

         resultsTable3.save(SPTBatch_.directChemo + File.separator + "Directionality series for " + SPTBatch_.imps.getShortTitle() + ".xls");
      }

      for(n72 = 0; n72 < this._listArrayList.size(); n72 += 2) {
         s10 = (String)this._hashCurrentDataset.get(new Integer(n72 / 2));
         list20 = (ArrayList)this._hashSliceNumber.get(new Integer(n72 / 2));
         intValue16 = (Integer)this._hashTracks.get(s10);
         n65 = (Integer)Collections.max(list20);
         array16 = (float[][])this._listArrayList.get(n72);
         array13 = (float[][])this._listArrayList.get(n72 + 1);
         resultsTable3 = new ResultsTable();
         array11 = new String[]{"Slice", "x FMI", "y FMI"};

         for(intValue29 = 0; intValue29 < array11.length; ++intValue29) {
            resultsTable3.setHeading(intValue29, array11[intValue29]);
         }

         for(intValue29 = 1; intValue29 <= n65; ++intValue29) {
            n9 = ChemotaxisStatistic.computeFMIIndex(array16, array13, intValue29, intValue16, 1, list20);
            double computeFMIIndex2 = ChemotaxisStatistic.computeFMIIndex(array16, array13, intValue29, intValue16, 2, list20);
            resultsTable3.incrementCounter();
            resultsTable3.addValue(0, (double)intValue29);
            resultsTable3.addValue(1, computeFMIIndex2);
            resultsTable3.addValue(2, n9);
         }

         resultsTable3.save(SPTBatch_.directChemo + File.separator + "FMI index series for " + SPTBatch_.imps.getShortTitle() + ".xls");
      }

      float[][] array15;
      float roundFloatNumbers2;
      float roundFloatNumbers3;
      for(n72 = 0; n72 < this._listArrayList.size(); n72 += 2) {
         s10 = (String)this._hashCurrentDataset.get(new Integer(n72 / 2));
         list20 = (ArrayList)this._hashSliceNumber.get(new Integer(n72 / 2));
         intValue16 = (Integer)this._hashTracks.get(s10);
         array15 = (float[][])this._listArrayList.get(n72);
         array16 = (float[][])this._listArrayList.get(n72 + 1);
         list23 = ChemotaxisStatistic.computeDirectionality(array15, array16, list20, intValue16);
         resultsTable3 = new ResultsTable();
         array11 = new String[]{"Track Number", "Directionality", "Endpoint X Value", "Endpoint Y Value"};

         for(intValue29 = 0; intValue29 < array11.length; ++intValue29) {
            resultsTable3.setHeading(intValue29, array11[intValue29]);
         }

         for(intValue29 = 0; intValue29 < intValue16; ++intValue29) {
            n13 = (Integer)list20.get(intValue29);
            roundFloatNumbers2 = array15[intValue29][n13 - 1];
            roundFloatNumbers3 = array16[intValue29][n13 - 1];
            resultsTable3.incrementCounter();
            resultsTable3.addValue(0, (double)(intValue29 + 1));
            resultsTable3.addValue(1, (Double)list23.get(intValue29));
            resultsTable3.addValue(2, (double)new Float(roundFloatNumbers2));
            resultsTable3.addValue(3, (double)new Float(roundFloatNumbers3));
         }

         resultsTable3.save(SPTBatch_.directChemo + File.separator + "Directionality track series for " + SPTBatch_.imps.getShortTitle() + ".xls");
      }

      int intValue43;
      for(n72 = 0; n72 < this._listArrayList.size(); n72 += 2) {
         s10 = (String)this._hashCurrentDataset.get(new Integer(n72 / 2));
         list20 = (ArrayList)this._hashSliceNumber.get(new Integer(n72 / 2));
         intValue16 = (Integer)this._hashTracks.get(s10);
         array15 = (float[][])this._listArrayList.get(n72);
         array16 = (float[][])this._listArrayList.get(n72 + 1);
         ResultsTable resultsTable5 = new ResultsTable();
         String[] array17 = new String[]{"Track Number", "x FMI", "y FMI", "Endpoint X Value", "Endpoint Y Value"};

         for(n94 = 0; n94 < array17.length; ++n94) {
            resultsTable5.setHeading(n94, array17[n94]);
         }

         for(n94 = 0; n94 < intValue16; ++n94) {
            intValue29 = (Integer)list20.get(n94);
            n9 = 0.0D;

            for(intValue43 = 0; intValue43 < intValue29 - 1; ++intValue43) {
               n9 += Point2D.distance((double)new Float(array15[n94][intValue43]), (double)new Float(array16[n94][intValue43]), (double)new Float(array15[n94][intValue43 + 1]), (double)new Float(array16[n94][intValue43 + 1]));
            }

            roundFloatNumbers3 = array16[n94][intValue29 - 1];
            float value4 = array15[n94][intValue29 - 1];
            double roundDoubleNumbers = this.roundDoubleNumbers((double)value4 / n9);
            double roundDoubleNumbers2 = this.roundDoubleNumbers((double)roundFloatNumbers3 / n9);
            resultsTable5.incrementCounter();
            resultsTable5.addValue(0, (double)(n94 + 1));
            resultsTable5.addValue(1, roundDoubleNumbers);
            resultsTable5.addValue(2, roundDoubleNumbers2);
            resultsTable5.addValue(3, (double)new Float(value4));
            resultsTable5.addValue(4, (double)new Float(roundFloatNumbers3));
         }

         resultsTable5.save(SPTBatch_.directChemo + File.separator + "FMI track series for " + SPTBatch_.imps.getShortTitle() + ".xls");
      }

      float abs;
      for(n72 = 0; n72 < this._listArrayList.size(); n72 += 2) {
         s10 = (String)this._hashCurrentDataset.get(new Integer(n72 / 2));
         list20 = (ArrayList)this._hashSliceNumber.get(new Integer(n72 / 2));
         intValue16 = (Integer)this._hashTracks.get(s10);
         list14 = ChemotaxisStatistic.computeDistandVelocity("velocity", (float[][])this._listArrayList.get(n72), (float[][])this._listArrayList.get(n72 + 1), list20, intValue16, this._timeInterval);
         ResultsTable resultsTable6 = new ResultsTable();
         String[] array18 = new String[]{"Track Number", "Velocity [" + this._unitsPath + "/" + this._unitsTime + "]"};

         int index9;
         for(index9 = 0; index9 < array18.length; ++index9) {
            resultsTable6.setHeading(index9, array18[index9]);
         }

         for(index9 = 0; index9 < intValue16; ++index9) {
            abs = this.roundFloatNumbers(Float.valueOf(list14.get(index9).toString()));
            resultsTable6.incrementCounter();
            resultsTable6.addValue(0, (double)(index9 + 1));
            resultsTable6.addValue(1, (double)abs);
         }

         resultsTable6.save(SPTBatch_.directChemo + File.separator + "Velocity series for " + SPTBatch_.imps.getShortTitle() + ".xls");
      }

      for(n72 = 0; n72 < this._listArrayList.size(); n72 += 2) {
         s10 = (String)this._hashCurrentDataset.get(new Integer(n72 / 2));
         list20 = (ArrayList)this._hashSliceNumber.get(new Integer(n72 / 2));
         intValue16 = (Integer)this._hashTracks.get(s10);
         array15 = (float[][])this._listArrayList.get(n72);
         array16 = (float[][])this._listArrayList.get(n72 + 1);
         list23 = ChemotaxisStatistic.computeDistandVelocity("accumulated distance", array15, array16, list20, intValue16, this._timeInterval);
         list15 = ChemotaxisStatistic.computeDistandVelocity("euclid distance", array15, array16, list20, intValue16, this._timeInterval);
         ResultsTable resultsTable7 = new ResultsTable();
         String[] array21 = new String[]{"Track Number", "Accumulated distance [" + this._unitsPath + "]", "Euclidean distance [" + this._unitsPath + "]"};

         for(n13 = 0; n13 < array21.length; ++n13) {
            resultsTable7.setHeading(n13, array21[n13]);
         }

         for(n13 = 0; n13 < intValue16; ++n13) {
            roundFloatNumbers2 = this.roundFloatNumbers(Float.valueOf(list23.get(n13).toString()));
            roundFloatNumbers3 = this.roundFloatNumbers(Float.valueOf(list15.get(n13).toString()));
            resultsTable7.incrementCounter();
            resultsTable7.addValue(0, (double)(n13 + 1));
            resultsTable7.addValue(1, (double)roundFloatNumbers2);
            resultsTable7.addValue(2, (double)roundFloatNumbers3);
         }

         resultsTable7.save(SPTBatch_.directChemo + File.separator + "Distance series for " + SPTBatch_.imps.getShortTitle() + ".xls");
      }

      String s18 = "1: prueba2.xls";
      int intValue27 = (Integer)this._hashImportedDataset.get("1: prueba2.xls");
      ResultsTable resultsTable13 = new ResultsTable();
      String[] array66 = new String[]{"Track n", "Slice n", "X", "Y"};

      for(n65 = 0; n65 < array66.length; ++n65) {
         resultsTable13.setHeading(n65, array66[n65]);
      }

      list14 = (ArrayList)this._importedData.get(intValue27 - 1);

      for(int index19 = 0; index19 < list14.size(); index19 += 4) {
         resultsTable13.incrementCounter();
         resultsTable13.addValue(0, (double)(Float)list14.get(index19));
         resultsTable13.addValue(1, (double)(Float)list14.get(index19 + 1));
         resultsTable13.addValue(2, (double)(Float)list14.get(index19 + 2));
         resultsTable13.addValue(3, (double)(Float)list14.get(index19 + 3));
      }

      resultsTable13.save(SPTBatch_.directChemo + File.separator + "Original data for " + SPTBatch_.imps.getShortTitle() + ".xls");
      String s19 = "1: prueba2.xls";
      if (this._hashCurrentDataset.contains("1: prueba2.xls")) {
         int intValue28 = (Integer)this._hashCurrentPosition.get("1: prueba2.xls");
         list15 = (ArrayList)this._hashSliceNumber.get(new Integer(intValue28));
         n94 = intValue28 * 2;
         intValue29 = (Integer)this._hashTracks.get("1: prueba2.xls");
         float[][] array67 = (float[][])this._listArrayList.get(n94);
         float[][] array68 = (float[][])this._listArrayList.get(n94 + 1);
         ResultsTable resultsTable14 = new ResultsTable();
         String[] array69 = new String[]{"Track n", "Slice n", "X [" + this._unitsPath + "]", "Y [" + this._unitsPath + "]"};

         for(index21 = 0; index21 < array69.length; ++index21) {
            resultsTable14.setHeading(index21, array69[index21]);
         }

         for(index21 = 0; index21 < intValue29; ++index21) {
            intValue30 = (Integer)list15.get(index21);

            for(index28 = 0; index28 < intValue30; ++index28) {
               resultsTable14.incrementCounter();
               resultsTable14.addValue(0, (double)new Integer(index21 + 1));
               resultsTable14.addValue(1, (double)new Integer(index28 + 1));
               resultsTable14.addValue(2, (double)new Float(array67[index21][index28]));
               resultsTable14.addValue(3, (double)new Float(array68[index21][index28]));
            }
         }

         resultsTable14.save(SPTBatch_.directChemo + File.separator + "Current used data for " + SPTBatch_.imps.getShortTitle() + ".xls");
      } else {
         JOptionPane.showMessageDialog(this.gui, "No current data available!");
      }

      this._hashPlot.clear();
      int n92 = 0;
      String s27 = "Mark up/down";
      if ("Mark up/down".equals("Mark up/down")) {
         n92 = 3;
      }

      if (this._dialog.auto) {
         abs = 0.0F;
         float abs2 = 0.0F;
         if (this._listArrayList.size() > 1) {
            ArrayList coll4 = new ArrayList();

            for(index27 = 0; index27 < this._listArrayList.size(); index27 += 2) {
               intValue43 = (Integer)this._hashTracks.get(this._hashCurrentDataset.get(new Integer(index27 / 2)));
               ArrayList<Integer> list27 = (ArrayList)this._hashSliceNumber.get(new Integer(index27 / 2));
               float[][] array71 = (float[][])this._listArrayList.get(index27);
               float[][] array72 = (float[][])this._listArrayList.get(index27 + 1);

               for(index28 = 0; index28 < intValue43; ++index28) {
                  intValue44 = (Integer)list27.get(index28);

                  for(n93 = 1; n93 < intValue44; ++n93) {
                     if (abs < Math.abs(array71[index28][n93])) {
                        abs = Math.abs(array71[index28][n93]);
                     }

                     if (abs2 < Math.abs(array72[index28][n93])) {
                        abs2 = Math.abs(array72[index28][n93]);
                     }
                  }
               }

               if (abs > abs2) {
                  coll4.add(new Float(abs));
               } else {
                  coll4.add(new Float(abs2));
               }
            }

            this._coordSize = (Float)Collections.max(coll4) + 5.0F;
         }
      }

      for(n94 = 0; n94 < this._listArrayList.size(); n94 += 2) {
         this.plotGraph(n92, n94);
      }

   }

   float angleCorrection(float n) {
      float n2 = n;
      if (n < 0.0F) {
         n2 = n % 360.0F + 360.0F;
      }

      if (n2 > 360.0F) {
         n2 %= 360.0F;
      }

      return n2;
   }

   float[] calculatePoints(float n) {
      float n2 = this._coordSize * 2.0F;
      return new float[]{this.roundNumber(new Float((double)n2 * Math.cos(Math.toRadians((double)n)))), this.roundNumber(new Float((double)n2 * Math.sin(Math.toRadians((double)n))))};
   }

   int countCells(float n, int index) {
      String key = (String)this._hashCurrentDataset.get(new Integer(index / 2));
      ArrayList<Integer> list = (ArrayList)this._hashSliceNumber.get(new Integer(index / 2));
      int intValue = (Integer)this._hashTracks.get(key);
      float[][] array = (float[][])this._listArrayList.get(index);
      float[][] array2 = (float[][])this._listArrayList.get(index + 1);
      int n2 = 0;
      float angleCorrection = this.angleCorrection(this._anglePosition + n / 2.0F);
      float angleCorrection2 = this.angleCorrection(this._anglePosition - n / 2.0F);
      float roundNumber = this.roundNumber(new Float(Math.tan(Math.toRadians((double)angleCorrection))));
      float roundNumber2 = this.roundNumber(new Float(Math.tan(Math.toRadians((double)angleCorrection2))));

      for(int i = 0; i < intValue; ++i) {
         int intValue2 = (Integer)list.get(i);
         float n3 = array[i][intValue2 - 1];
         float n4 = array2[i][intValue2 - 1];
         if ((angleCorrection > 0.0F && angleCorrection < 90.0F && angleCorrection2 > 0.0F && angleCorrection2 < 90.0F || angleCorrection > 270.0F && angleCorrection < 360.0F && angleCorrection2 > 270.0F && angleCorrection2 < 360.0F) && n4 <= roundNumber * n3 && n4 >= roundNumber2 * n3) {
            ++n2;
         }

         if ((angleCorrection > 90.0F && angleCorrection < 180.0F && angleCorrection2 > 90.0F && angleCorrection2 < 180.0F || angleCorrection > 180.0F && angleCorrection < 270.0F && angleCorrection2 > 180.0F && angleCorrection2 < 270.0F) && n4 >= roundNumber * n3 && n4 <= roundNumber2 * n3) {
            ++n2;
         }

         if (angleCorrection > 0.0F && angleCorrection < 90.0F) {
            if (angleCorrection2 > 180.0F && angleCorrection2 < 270.0F && n4 <= roundNumber * n3 && n4 <= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 > 270.0F && angleCorrection2 < 360.0F && n4 <= roundNumber * n3 && n4 >= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 270.0F && n3 >= 0.0F && n4 <= roundNumber * n3) {
               ++n2;
            }

            if (angleCorrection2 == 0.0F && n4 >= 0.0F && n4 <= roundNumber * n3) {
               ++n2;
            }
         }

         if (angleCorrection > 90.0F && angleCorrection < 180.0F) {
            if (angleCorrection2 > 0.0F && angleCorrection2 < 90.0F && n4 >= roundNumber * n3 && n4 >= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 > 270.0F && angleCorrection2 < 360.0F && n4 >= roundNumber * n3 && n4 >= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 90.0F && n3 <= 0.0F && n4 >= roundNumber * n3) {
               ++n2;
            }

            if (angleCorrection2 == 0.0F && n4 >= 0.0F && n4 >= roundNumber * n3) {
               ++n2;
            }
         }

         if (angleCorrection > 180.0F && angleCorrection < 270.0F) {
            if (angleCorrection2 > 90.0F && angleCorrection2 < 180.0F && n4 >= roundNumber * n3 && n4 <= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 > 0.0F && angleCorrection2 < 90.0F && n4 >= roundNumber * n3 && n4 >= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 90.0F && n3 <= 0.0F && n4 >= roundNumber * n3) {
               ++n2;
            }

            if (angleCorrection2 == 180.0F && n4 <= 0.0F && n4 >= roundNumber * n3) {
               ++n2;
            }
         }

         if (angleCorrection > 270.0F && angleCorrection < 360.0F) {
            if (angleCorrection2 > 180.0F && angleCorrection2 < 270.0F && n4 <= roundNumber * n3 && n4 <= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 > 90.0F && angleCorrection2 < 180.0F && n4 <= roundNumber * n3 && n4 <= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 180.0F && n4 <= 0.0F && n4 <= roundNumber * n3) {
               ++n2;
            }

            if (angleCorrection2 == 270.0F && n3 >= 0.0F && n4 <= roundNumber * n3) {
               ++n2;
            }
         }

         if (angleCorrection == 90.0F) {
            if ((angleCorrection2 > 0.0F && angleCorrection2 < 90.0F || angleCorrection2 > 270.0F && angleCorrection2 < 360.0F) && n3 >= 0.0F && n4 >= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 0.0F && n3 >= 0.0F && n4 >= 0.0F) {
               ++n2;
            }

            if (angleCorrection2 == 270.0F && n3 >= 0.0F) {
               ++n2;
            }
         }

         if (angleCorrection == 180.0F) {
            if ((angleCorrection2 > 0.0F && angleCorrection2 < 90.0F || angleCorrection2 > 90.0F && angleCorrection2 < 180.0F) && n4 >= 0.0F && n4 <= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 0.0F && n4 >= 0.0F) {
               ++n2;
            }

            if (angleCorrection2 == 90.0F && n3 <= 0.0F && n4 >= 0.0F) {
               ++n2;
            }
         }

         if (angleCorrection == 270.0F) {
            if ((angleCorrection2 > 90.0F && angleCorrection2 < 180.0F || angleCorrection2 > 180.0F && angleCorrection2 < 270.0F) && n3 <= 0.0F && n4 <= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 90.0F && n3 <= 0.0F) {
               ++n2;
            }

            if (angleCorrection2 == 180.0F && n3 <= 0.0F && n4 <= 0.0F) {
               ++n2;
            }
         }

         if (angleCorrection == 360.0F) {
            if (angleCorrection2 > 180.0F && angleCorrection2 < 270.0F && n4 <= 0.0F && n4 <= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 > 270.0F && angleCorrection2 < 360.0F && n4 <= 0.0F && n4 >= roundNumber2 * n3) {
               ++n2;
            }

            if (angleCorrection2 == 180.0F && n4 <= 0.0F) {
               ++n2;
            }

            if (angleCorrection2 == 270.0F && n3 >= 0.0F && n4 <= 0.0F) {
               ++n2;
            }
         }
      }

      return n2;
   }

   void plotGraph(int value, int n) {
      String s = (String)this._hashCurrentDataset.get(new Integer(n / 2));
      ArrayList<Integer> list = (ArrayList)this._hashSliceNumber.get(new Integer(n / 2));
      int intValue = (Integer)this._hashTracks.get(s);
      float[][] array = (float[][])this._listArrayList.get(n);
      float[][] array2 = (float[][])this._listArrayList.get(n + 1);
      double roundDoubleNumbers = 0.0D;
      double roundDoubleNumbers2 = 0.0D;
      int i = 0;
      int j = 0;
      ArrayList<Double> list2 = null;

      for(int k = 0; k < intValue; ++k) {
         int intValue2 = (Integer)list.get(k);
         roundDoubleNumbers += (double)array[k][intValue2 - 1];
         roundDoubleNumbers2 += (double)array2[k][intValue2 - 1];
      }

      if (intValue != 0) {
         roundDoubleNumbers /= (double)intValue;
         roundDoubleNumbers2 /= (double)intValue;
      }

      roundDoubleNumbers = this.roundDoubleNumbers(roundDoubleNumbers);
      roundDoubleNumbers2 = this.roundDoubleNumbers(roundDoubleNumbers2);
      float[] array3 = new float[]{0.0F};
      Plot plot = new Plot(s, "x axis [" + this._unitsPath + "]", "y axis  [" + this._unitsPath + "]", array3, array3);
      float[] array9;
      float[] array10;
      if (SPTBatch_.chemoScaling.getText().contains("...") == Boolean.TRUE) {
         array9 = new float[]{this._coordSize, -this._coordSize};
         array10 = new float[2];
         plot.setLimits((double)(-this._coordSize), (double)this._coordSize, (double)(-this._coordSize), (double)this._coordSize);
         plot.addPoints(array9, array10, 2);
         plot.addPoints(array10, array9, 2);
      }

      float[] array19;
      if (SPTBatch_.chemoScaling.getText().contains("...") == Boolean.FALSE) {
         array9 = new float[]{(float)(-Integer.valueOf(SPTBatch_.chemoScaling.getText())), (float)Integer.valueOf(SPTBatch_.chemoScaling.getText())};
         array10 = new float[2];
         array19 = new float[]{(float)(-Integer.valueOf(SPTBatch_.chemoScaling.getText())), (float)Integer.valueOf(SPTBatch_.chemoScaling.getText())};
         plot.setLimits(-Double.valueOf(SPTBatch_.chemoScaling.getText()), Double.valueOf(SPTBatch_.chemoScaling.getText()), -Double.valueOf(SPTBatch_.chemoScaling.getText()), Double.valueOf(SPTBatch_.chemoScaling.getText()));
         plot.addPoints(array9, array10, 2);
         plot.addPoints(array10, array19, 2);
      }

      plot.draw();
      plot.setLineWidth(1);
      array9 = new float[]{0.0F};
      array10 = new float[]{0.0F};
      if (value == 3) {
         for(int index = 0; index < intValue; ++index) {
            int intValue4 = (Integer)list.get(index);
            float[] array13 = new float[intValue4];
            float[] array14 = new float[intValue4];
            array9[0] = array[index][intValue4 - 1];
            array10[0] = array2[index][intValue4 - 1];
            if (array10[0] >= 0.0F) {
               plot.setColor(Color.black);
               ++i;
            } else {
               plot.setColor(Color.red);
               ++j;
            }

            for(int n3 = 0; n3 < intValue4; ++n3) {
               array13[n3] = array[index][n3];
               array14[n3] = array2[index][n3];
            }

            plot.setLineWidth(1);
            plot.addPoints(array13, array14, 2);
            plot.setLineWidth(3);
            plot.addPoints(array9, array10, 0);
         }

         plot.setColor(Color.black);
         plot.addLabel(0.0D, 0.0D, "Number of tracks: " + intValue + "  Counts up: " + i + "  Counts down: " + j);
         plot.addLabel(0.0D, 0.04D, "Center of mass [" + this._unitsPath + "]: x=" + roundDoubleNumbers + " y=" + roundDoubleNumbers2);
      }

      array19 = new float[]{new Float(roundDoubleNumbers)};
      float[] array20 = new float[]{new Float(roundDoubleNumbers2)};
      plot.setColor(Color.blue);
      plot.setLineWidth(3);
      plot.addPoints(array19, array20, 5);
      this._currentOpenWindows.size();
      int var10000 = this._listArrayList.size() / 2;
      ArrayList<Integer> value2 = new ArrayList();
      value2.add(new Integer(value));
      value2.add(new Integer(n));
      value2.add(new Integer(intValue));
      this._hashPlot.put(s, value2);
      IJ.save(plot.getImagePlus(), SPTBatch_.directChemo + File.separator + "Chemotaxis Plot for " + SPTBatch_.imps.getShortTitle() + ".png");
   }

   void readData(String string) throws FileNotFoundException, IOException {
      this._importedData.add(this.arrayToImport);
      IJ.showStatus("Dataset imported");
      string = this._importedData.size() + ": " + string;
      this._hashImportedDataset.put(string, new Integer(this._importedData.size()));
   }

   double roundDoubleNumbers(double n) {
      return (double)Math.round(n * 100.0D) / 100.0D;
   }

   float roundFloatNumbers(float n) {
      return (float)Math.round(n * 100.0F) / 100.0F;
   }

   float roundNumber(float n) {
      return (float)Math.round(n * 10000.0F) / 10000.0F;
   }

   public void stateChanged(ChangeEvent changeEvent) {
      int selectedIndex = ((JTabbedPane)changeEvent.getSource()).getSelectedIndex();
      if (selectedIndex == 1) {
         if (this._dialog.auto) {
            PlotWindow.plotHeight = this._plotHeight;
            PlotWindow.plotWidth = this._plotWidth;
         } else {
            PlotWindow.plotHeight = (int)(this._dialog.fraction * (double)(Math.abs(this._dialog.minimumY) + Math.abs(this._dialog.maximumY)));
            PlotWindow.plotWidth = (int)(this._dialog.fraction * (double)(Math.abs(this._dialog.minimumX) + Math.abs(this._dialog.maximumX)));
         }
      }

      if (selectedIndex == 3) {
         PlotWindow.plotHeight = this._plotHeight;
         PlotWindow.plotWidth = this._plotWidth;
      }

   }

   void updateNumberofTracks(String s, int n) {
      if (this._hashTracks.containsKey(s)) {
         this._hashTracks.remove(s);
         this._hashTracks.put(s, new Integer(n));
      } else {
         this._hashTracks.put(s, new Integer(n));
      }

   }

   public void windowActivated(WindowEvent windowEvent) {
   }

   public void windowClosed(WindowEvent windowEvent) {
   }

   public void windowClosing(WindowEvent windowEvent) {
      int k;
      if (windowEvent.getSource() == this.gui) {
         if (this._openInfoWindows != null) {
            for(k = 0; k < this._openInfoWindows.size(); ++k) {
               ((JFrame)this._openInfoWindows.get(k)).dispose();
            }

            this._openInfoWindows.clear();
         }

         this._currentOpenWindows.clear();
         this._currentOpenDiagrams.clear();
         WindowManager.closeAllWindows();
         this.gui.dispose();
      } else {
         if (windowEvent.getSource() instanceof JFrame && this._openInfoWindows.contains(windowEvent.getSource())) {
            this._openInfoWindows.remove(windowEvent.getSource());
         }

         if (windowEvent.getSource() instanceof PlotWindow) {
            if (this._currentOpenWindows.contains(windowEvent.getSource())) {
               for(k = 0; k < this._currentOpenWindows.size(); ++k) {
                  ((Window)this._currentOpenWindows.get(k)).dispose();
               }

               this._currentOpenWindows.clear();
               this.gui.plotClosed();
            }

            if (this._currentOpenDiagrams.contains(windowEvent.getSource())) {
               for(k = 0; k < this._currentOpenDiagrams.size(); ++k) {
                  ((Window)this._currentOpenDiagrams.get(k)).dispose();
               }

               this._currentOpenDiagrams.clear();
            }
         }
      }

   }

   public void windowDeactivated(WindowEvent windowEvent) {
   }

   public void windowDeiconified(WindowEvent windowEvent) {
   }

   public void windowIconified(WindowEvent windowEvent) {
   }

   public void windowOpened(WindowEvent windowEvent) {
   }
}
