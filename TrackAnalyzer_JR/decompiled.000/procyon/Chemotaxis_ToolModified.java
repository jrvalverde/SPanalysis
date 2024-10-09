import java.awt.Window;
import ij.WindowManager;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import java.io.FileNotFoundException;
import ij.IJ;
import java.awt.Color;
import ij.gui.Plot;
import java.awt.geom.Point2D;
import java.io.File;
import ij.measure.ResultsTable;
import java.util.Collection;
import java.util.Collections;
import java.io.IOException;
import java.awt.Component;
import javax.swing.JOptionPane;
import ij.gui.PlotWindow;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;
import ij.plugin.PlugIn;

// 
// Decompiled by Procyon v0.5.36
// 

public class Chemotaxis_ToolModified implements PlugIn
{
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
    
    public Chemotaxis_ToolModified(final ArrayList arrayToImport) {
        this.arrayToImport = arrayToImport;
        this.gui = null;
        this._dialog = null;
        this._angleBetweenCircle = 66.0f;
        this._angleBetweenDiagram = 66.0f;
        this._maxVector = null;
        this._maxPosition = 0;
        this._anglePosition = 0.0f;
        this._variableSliceNumber = null;
        this._coordSize = 0.0f;
        this._calxy = 1.0f;
        this._timeInterval = 2.0;
    }
    
    public void run(final String var1) {
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
        }
        catch (IOException ex5) {
            JOptionPane.showMessageDialog((Component)this.gui, "Error reading from file");
        }
        boolean b4 = true;
        final String item = "1: prueba2.xls";
        if (this._hashSlicesImported.containsKey("1: prueba2.xls")) {
            ArrayList list16 = this._hashSlicesImported.get("1: prueba2.xls");
            if (list16.size() == 1) {
                try {
                    list16 = new ArrayList();
                    this._hashSlicesImported.remove("1: prueba2.xls");
                    list16.add(1);
                    list16.add(SPTBatch_.imps.getStackSize());
                    if (SPTBatch_.imps.getStackSize() < Integer.valueOf("1")) {
                        JOptionPane.showMessageDialog((Component)this.gui, "Second value can`t be smaller than the first");
                    }
                    else {
                        this._hashSlicesImported.put("1: prueba2.xls", list16);
                    }
                }
                catch (NumberFormatException ex6) {
                    this.gui.firstSlicesField.setText("0");
                    this.gui.secondSlicesField.setText("0");
                    list16.add(new Integer(0));
                    list16.add(new Integer(0));
                    this._hashSlicesImported.put("1: prueba2.xls", list16);
                    JOptionPane.showMessageDialog((Component)this.gui, "Please enter correct value for number of slices!");
                }
            }
            if (list16.size() == 2) {
                try {
                    if (Integer.valueOf(1) != list16.get(0) || Integer.valueOf(SPTBatch_.imps.getStackSize()) != list16.get(1)) {
                        this._hashSlicesImported.remove("1: prueba2.xls");
                        final ArrayList<Integer> value7 = new ArrayList<Integer>();
                        value7.add(Integer.valueOf("1"));
                        value7.add(SPTBatch_.imps.getStackSize());
                        if (SPTBatch_.imps.getStackSize() < Integer.valueOf("1.0")) {
                            JOptionPane.showMessageDialog((Component)this.gui, "Second value can`t be smaller than the first");
                        }
                        else {
                            this._hashSlicesImported.put("1: prueba2.xls", value7);
                        }
                    }
                }
                catch (NumberFormatException ex7) {
                    final ArrayList<Integer> list17 = this._hashSlicesImported.get("1: prueba2.xls");
                    JOptionPane.showMessageDialog((Component)this.gui, "Setting old values!");
                }
            }
        }
        else {
            try {
                final ArrayList<Integer> value8 = new ArrayList<Integer>();
                value8.add(Integer.valueOf("1"));
                value8.add(SPTBatch_.imps.getStackSize());
                if (SPTBatch_.imps.getStackSize() < Integer.valueOf("1")) {
                    b4 = false;
                    JOptionPane.showMessageDialog((Component)this.gui, "Second value can`t be smaller than the first");
                }
                else {
                    this._hashSlicesImported.put("1: prueba2.xls", value8);
                }
            }
            catch (NumberFormatException ex8) {
                b4 = false;
                JOptionPane.showMessageDialog((Component)this.gui, "Please enter correct value for number of slices!");
            }
        }
        boolean b5 = true;
        this._maxVector.clear();
        final ArrayList<String> list18 = new ArrayList<String>();
        final String s20 = "1: prueba2.xls";
        if (!list18.contains("1: prueba2.xls")) {
            list18.add("1: prueba2.xls");
        }
        this._unitsPath = "µm";
        this._unitsTime = "min";
        final int intValue31 = 0;
        final int intValue32 = 0;
        final int n68 = 0;
        final double n69 = 0.0;
        final double doubleValue = 0.0;
        final int n70 = 0;
        final double n71 = 0.0;
        final double doubleValue2 = 0.0;
        this._calxy = new Float(1.0);
        this._timeInterval = new Double(1.0);
        b5 = true;
        if (b5) {
            this._listArrayList.clear();
            this._hashCurrentDataset.clear();
            this._hashSliceNumber.clear();
            for (int n72 = 0; n72 < list18.size(); ++n72) {
                this._variableSliceNumber = new ArrayList();
                final String s21 = list18.get(n72);
                this._hashCurrentDataset.put(new Integer(n72), s21);
                this._hashCurrentPosition.put(s21, new Integer(n72));
                final ArrayList<Integer> list19 = this._hashSlicesImported.get(s21);
                final int intValue33 = this._hashImportedDataset.get(s21);
                final ArrayList list20 = new ArrayList();
                final ArrayList<Float> list21 = new ArrayList<Float>();
                final ArrayList<Float> list22 = new ArrayList<Float>();
                final ArrayList<Object> list23 = this._importedData.get(intValue33 - 1);
                for (int index24 = 0; index24 < list23.size(); index24 += 4) {
                    list20.add(list23.get(index24));
                    list21.add(list23.get(index24 + 2));
                    list22.add(list23.get(index24 + 3));
                }
                final int intValue34 = Math.round(list20.get(list20.size() - 1));
                final int[] array70 = new int[3 * intValue34];
                int n73 = 1;
                for (int n74 = 0; n74 <= 3 * intValue34 - 3; n74 += 3) {
                    array70[n74] = list20.indexOf(new Float((float)n73));
                    array70[n74 + 1] = list20.lastIndexOf(new Float((float)n73));
                    array70[n74 + 2] = n73;
                    ++n73;
                }
                final ArrayList<Float> list24 = new ArrayList<Float>();
                final ArrayList<Float> list25 = new ArrayList<Float>();
                int n75 = 0;
                if (list19.size() == 1) {
                    final int intValue35 = list19.get(0);
                    for (int n76 = 0; n76 <= array70.length - 3; n76 += 3) {
                        if (array70[n76 + 1] != -1 && array70[n76] != -1 && array70[n76 + 1] - array70[n76] == intValue35 - 1) {
                            ++n75;
                            this._variableSliceNumber.add(new Integer(array70[n76 + 1] - array70[n76] + 1));
                            for (int n77 = array70[n76]; n77 <= array70[n76 + 1]; ++n77) {
                                list24.add(new Float(list21.get(n77) * this._calxy));
                                list25.add(new Float(list22.get(n77) * this._calxy));
                            }
                        }
                    }
                }
                if (list19.size() == 2) {
                    final int intValue36 = list19.get(0);
                    final int intValue37 = list19.get(1);
                    for (int n78 = 0; n78 <= array70.length - 3; n78 += 3) {
                        if (array70[n78 + 1] != -1 && array70[n78] != -1 && array70[n78 + 1] - array70[n78] >= intValue36 - 1 && array70[n78 + 1] - array70[n78] <= intValue37 - 1) {
                            ++n75;
                            this._variableSliceNumber.add(new Integer(array70[n78 + 1] - array70[n78] + 1));
                            for (int n79 = array70[n78]; n79 <= array70[n78 + 1]; ++n79) {
                                list24.add(new Float(list21.get(n79) * this._calxy));
                                list25.add(new Float(list22.get(n79) * this._calxy));
                            }
                        }
                    }
                }
                this.updateNumberofTracks(s21, n75);
                if (this._variableSliceNumber.isEmpty()) {
                    if (list19.size() == 1) {
                        JOptionPane.showMessageDialog((Component)this.gui, "No tracks available for current number of slices");
                    }
                    if (list19.size() == 2) {
                        JOptionPane.showMessageDialog((Component)this.gui, "No tracks available for current range of used number of slices");
                    }
                }
                else {
                    final int intValue38 = Collections.<Integer>max((Collection<? extends Integer>)this._variableSliceNumber);
                    int n80 = 0;
                    this.x_values = new float[n75][intValue38];
                    this.y_values = new float[n75][intValue38];
                    for (int index25 = 0; index25 < n75; ++index25) {
                        this.x_values[index25][0] = 0.0f;
                        this.y_values[index25][0] = 0.0f;
                        int n81;
                        int intValue39;
                        for (n81 = 1, intValue39 = this._variableSliceNumber.get(index25); n81 < intValue39; ++n81) {
                            final float n82 = list24.get(n81 + n80) - list24.get(n80);
                            final float n83 = -(list25.get(n81 + n80) - list25.get(n80));
                            this.x_values[index25][n81] = n82;
                            this.y_values[index25][n81] = n83;
                        }
                        n80 += intValue39;
                    }
                    this._listArrayList.add(this.x_values);
                    this._listArrayList.add(this.y_values);
                    this._hashSliceNumber.put(new Integer(n72), this._variableSliceNumber);
                }
            }
        }
        for (int j = 0; j < this._listArrayList.size(); j += 2) {
            final String s22 = this._hashCurrentDataset.get(new Integer(j / 2));
            final ArrayList coll = this._hashSliceNumber.get(new Integer(j / 2));
            final int intValue40 = this._hashTracks.get(s22);
            final int intValue41 = Collections.<Integer>max((Collection<? extends Integer>)coll);
            final float[][] array71 = this._listArrayList.get(j);
            final float[][] array72 = this._listArrayList.get(j + 1);
            final ResultsTable resultsTable = new ResultsTable();
            final String[] array73 = { "Slice", "Center of mass x [" + this._unitsPath + "]", "Center of mass y [" + this._unitsPath + "]", "Center of mass length [" + this._unitsPath + "]" };
            for (int k = 0; k < array73.length; ++k) {
                resultsTable.setHeading(k, array73[k]);
            }
            for (int l = 1; l <= intValue41; ++l) {
                final double[] centerofMassSeries = ChemotaxisStatistic.centerofMassSeries(l, array71, array72, intValue40, coll);
                resultsTable.incrementCounter();
                resultsTable.addValue(0, (double)l);
                resultsTable.addValue(1, centerofMassSeries[0]);
                resultsTable.addValue(2, centerofMassSeries[1]);
                resultsTable.addValue(3, centerofMassSeries[2]);
            }
            resultsTable.save(SPTBatch_.directChemo + File.separator + "Center of mass series for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        for (int index26 = 0; index26 < this._listArrayList.size(); index26 += 2) {
            final String s23 = this._hashCurrentDataset.get(new Integer(index26 / 2));
            final ArrayList coll2 = this._hashSliceNumber.get(new Integer(index26 / 2));
            final int intValue42 = Collections.<Integer>max((Collection<? extends Integer>)coll2);
            final int intValue43 = this._hashTracks.get(s23);
            final float[][] array74 = this._listArrayList.get(index26);
            final float[][] array75 = this._listArrayList.get(index26 + 1);
            final ResultsTable resultsTable2 = new ResultsTable();
            final String[] array76 = { "Slice", "Directionality" };
            for (int n84 = 0; n84 < array76.length; ++n84) {
                resultsTable2.setHeading(n84, array76[n84]);
            }
            for (int n85 = 1; n85 <= intValue42; ++n85) {
                final double computeChemotaxisIndex = ChemotaxisStatistic.computeChemotaxisIndex(array74, array75, n85, intValue43, coll2);
                resultsTable2.incrementCounter();
                resultsTable2.addValue(0, (double)n85);
                resultsTable2.addValue(1, computeChemotaxisIndex);
            }
            resultsTable2.save(SPTBatch_.directChemo + File.separator + "Directionality series for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        for (int index27 = 0; index27 < this._listArrayList.size(); index27 += 2) {
            final String s24 = this._hashCurrentDataset.get(new Integer(index27 / 2));
            final ArrayList coll3 = this._hashSliceNumber.get(new Integer(index27 / 2));
            final int intValue44 = this._hashTracks.get(s24);
            final int intValue45 = Collections.<Integer>max((Collection<? extends Integer>)coll3);
            final float[][] array77 = this._listArrayList.get(index27);
            final float[][] array78 = this._listArrayList.get(index27 + 1);
            final ResultsTable resultsTable3 = new ResultsTable();
            final String[] array79 = { "Slice", "x FMI", "y FMI" };
            for (int n86 = 0; n86 < array79.length; ++n86) {
                resultsTable3.setHeading(n86, array79[n86]);
            }
            for (int n87 = 1; n87 <= intValue45; ++n87) {
                final double computeFMIIndex = ChemotaxisStatistic.computeFMIIndex(array77, array78, n87, intValue44, 1, coll3);
                final double computeFMIIndex2 = ChemotaxisStatistic.computeFMIIndex(array77, array78, n87, intValue44, 2, coll3);
                resultsTable3.incrementCounter();
                resultsTable3.addValue(0, (double)n87);
                resultsTable3.addValue(1, computeFMIIndex2);
                resultsTable3.addValue(2, computeFMIIndex);
            }
            resultsTable3.save(SPTBatch_.directChemo + File.separator + "FMI index series for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        for (int index28 = 0; index28 < this._listArrayList.size(); index28 += 2) {
            final String s25 = this._hashCurrentDataset.get(new Integer(index28 / 2));
            final ArrayList<Integer> list26 = this._hashSliceNumber.get(new Integer(index28 / 2));
            final int intValue46 = this._hashTracks.get(s25);
            final float[][] array80 = this._listArrayList.get(index28);
            final float[][] array81 = this._listArrayList.get(index28 + 1);
            final ArrayList computeDirectionality = ChemotaxisStatistic.computeDirectionality(array80, array81, (ArrayList)list26, intValue46);
            final ResultsTable resultsTable4 = new ResultsTable();
            final String[] array82 = { "Track Number", "Directionality", "Endpoint X Value", "Endpoint Y Value" };
            for (int n88 = 0; n88 < array82.length; ++n88) {
                resultsTable4.setHeading(n88, array82[n88]);
            }
            for (int n89 = 0; n89 < intValue46; ++n89) {
                final int intValue47 = list26.get(n89);
                final float value9 = array80[n89][intValue47 - 1];
                final float value10 = array81[n89][intValue47 - 1];
                resultsTable4.incrementCounter();
                resultsTable4.addValue(0, (double)(n89 + 1));
                resultsTable4.addValue(1, (double)computeDirectionality.get(n89));
                resultsTable4.addValue(2, (double)new Float(value9));
                resultsTable4.addValue(3, (double)new Float(value10));
            }
            resultsTable4.save(SPTBatch_.directChemo + File.separator + "Directionality track series for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        for (int index29 = 0; index29 < this._listArrayList.size(); index29 += 2) {
            final String s26 = this._hashCurrentDataset.get(new Integer(index29 / 2));
            final ArrayList<Integer> list27 = this._hashSliceNumber.get(new Integer(index29 / 2));
            final int intValue48 = this._hashTracks.get(s26);
            final float[][] array83 = this._listArrayList.get(index29);
            final float[][] array84 = this._listArrayList.get(index29 + 1);
            final ResultsTable resultsTable5 = new ResultsTable();
            final String[] array85 = { "Track Number", "x FMI", "y FMI", "Endpoint X Value", "Endpoint Y Value" };
            for (int n90 = 0; n90 < array85.length; ++n90) {
                resultsTable5.setHeading(n90, array85[n90]);
            }
            for (int index30 = 0; index30 < intValue48; ++index30) {
                final int intValue49 = list27.get(index30);
                double n91 = 0.0;
                for (int n92 = 0; n92 < intValue49 - 1; ++n92) {
                    n91 += Point2D.distance(new Float(array83[index30][n92]), new Float(array84[index30][n92]), new Float(array83[index30][n92 + 1]), new Float(array84[index30][n92 + 1]));
                }
                final float value11 = array84[index30][intValue49 - 1];
                final float value12 = array83[index30][intValue49 - 1];
                final double roundDoubleNumbers = this.roundDoubleNumbers(value12 / n91);
                final double roundDoubleNumbers2 = this.roundDoubleNumbers(value11 / n91);
                resultsTable5.incrementCounter();
                resultsTable5.addValue(0, (double)(index30 + 1));
                resultsTable5.addValue(1, roundDoubleNumbers);
                resultsTable5.addValue(2, roundDoubleNumbers2);
                resultsTable5.addValue(3, (double)new Float(value12));
                resultsTable5.addValue(4, (double)new Float(value11));
            }
            resultsTable5.save(SPTBatch_.directChemo + File.separator + "FMI track series for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        for (int index31 = 0; index31 < this._listArrayList.size(); index31 += 2) {
            final String s27 = this._hashCurrentDataset.get(new Integer(index31 / 2));
            final ArrayList list28 = this._hashSliceNumber.get(new Integer(index31 / 2));
            final int intValue50 = this._hashTracks.get(s27);
            final ArrayList computeDistandVelocity = ChemotaxisStatistic.computeDistandVelocity("velocity", (float[][])this._listArrayList.get(index31), (float[][])this._listArrayList.get(index31 + 1), list28, intValue50, this._timeInterval);
            final ResultsTable resultsTable6 = new ResultsTable();
            final String[] array86 = { "Track Number", "Velocity [" + this._unitsPath + "/" + this._unitsTime + "]" };
            for (int n93 = 0; n93 < array86.length; ++n93) {
                resultsTable6.setHeading(n93, array86[n93]);
            }
            for (int index32 = 0; index32 < intValue50; ++index32) {
                final float roundFloatNumbers = this.roundFloatNumbers(Float.valueOf(computeDistandVelocity.get(index32).toString()));
                resultsTable6.incrementCounter();
                resultsTable6.addValue(0, (double)(index32 + 1));
                resultsTable6.addValue(1, (double)roundFloatNumbers);
            }
            resultsTable6.save(SPTBatch_.directChemo + File.separator + "Velocity series for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        for (int index33 = 0; index33 < this._listArrayList.size(); index33 += 2) {
            final String s28 = this._hashCurrentDataset.get(new Integer(index33 / 2));
            final ArrayList list29 = this._hashSliceNumber.get(new Integer(index33 / 2));
            final int intValue51 = this._hashTracks.get(s28);
            final float[][] array87 = this._listArrayList.get(index33);
            final float[][] array88 = this._listArrayList.get(index33 + 1);
            final ArrayList computeDistandVelocity2 = ChemotaxisStatistic.computeDistandVelocity("accumulated distance", array87, array88, list29, intValue51, this._timeInterval);
            final ArrayList computeDistandVelocity3 = ChemotaxisStatistic.computeDistandVelocity("euclid distance", array87, array88, list29, intValue51, this._timeInterval);
            final ResultsTable resultsTable7 = new ResultsTable();
            final String[] array89 = { "Track Number", "Accumulated distance [" + this._unitsPath + "]", "Euclidean distance [" + this._unitsPath + "]" };
            for (int n94 = 0; n94 < array89.length; ++n94) {
                resultsTable7.setHeading(n94, array89[n94]);
            }
            for (int n95 = 0; n95 < intValue51; ++n95) {
                final float roundFloatNumbers2 = this.roundFloatNumbers(Float.valueOf(computeDistandVelocity2.get(n95).toString()));
                final float roundFloatNumbers3 = this.roundFloatNumbers(Float.valueOf(computeDistandVelocity3.get(n95).toString()));
                resultsTable7.incrementCounter();
                resultsTable7.addValue(0, (double)(n95 + 1));
                resultsTable7.addValue(1, (double)roundFloatNumbers2);
                resultsTable7.addValue(2, (double)roundFloatNumbers3);
            }
            resultsTable7.save(SPTBatch_.directChemo + File.separator + "Distance series for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        final String s29 = "1: prueba2.xls";
        final int intValue52 = this._hashImportedDataset.get("1: prueba2.xls");
        final ResultsTable resultsTable8 = new ResultsTable();
        final String[] array90 = { "Track n", "Slice n", "X", "Y" };
        for (int n96 = 0; n96 < array90.length; ++n96) {
            resultsTable8.setHeading(n96, array90[n96]);
        }
        final ArrayList<Float> list30 = this._importedData.get(intValue52 - 1);
        for (int index34 = 0; index34 < list30.size(); index34 += 4) {
            resultsTable8.incrementCounter();
            resultsTable8.addValue(0, (double)list30.get(index34));
            resultsTable8.addValue(1, (double)list30.get(index34 + 1));
            resultsTable8.addValue(2, (double)list30.get(index34 + 2));
            resultsTable8.addValue(3, (double)list30.get(index34 + 3));
        }
        resultsTable8.save(SPTBatch_.directChemo + File.separator + "Original data for " + SPTBatch_.imps.getShortTitle() + ".xls");
        final String s30 = "1: prueba2.xls";
        if (this._hashCurrentDataset.contains("1: prueba2.xls")) {
            final int intValue53 = this._hashCurrentPosition.get("1: prueba2.xls");
            final ArrayList<Integer> list31 = this._hashSliceNumber.get(new Integer(intValue53));
            final int index35 = intValue53 * 2;
            final int intValue54 = this._hashTracks.get("1: prueba2.xls");
            final float[][] array91 = this._listArrayList.get(index35);
            final float[][] array92 = this._listArrayList.get(index35 + 1);
            final ResultsTable resultsTable9 = new ResultsTable();
            final String[] array93 = { "Track n", "Slice n", "X [" + this._unitsPath + "]", "Y [" + this._unitsPath + "]" };
            for (int n97 = 0; n97 < array93.length; ++n97) {
                resultsTable9.setHeading(n97, array93[n97]);
            }
            for (int index36 = 0; index36 < intValue54; ++index36) {
                for (int intValue55 = list31.get(index36), n98 = 0; n98 < intValue55; ++n98) {
                    resultsTable9.incrementCounter();
                    resultsTable9.addValue(0, (double)new Integer(index36 + 1));
                    resultsTable9.addValue(1, (double)new Integer(n98 + 1));
                    resultsTable9.addValue(2, (double)new Float(array91[index36][n98]));
                    resultsTable9.addValue(3, (double)new Float(array92[index36][n98]));
                }
            }
            resultsTable9.save(SPTBatch_.directChemo + File.separator + "Current used data for " + SPTBatch_.imps.getShortTitle() + ".xls");
        }
        else {
            JOptionPane.showMessageDialog((Component)this.gui, "No current data available!");
        }
        this._hashPlot.clear();
        int n99 = 0;
        final String s31 = "Mark up/down";
        if ("Mark up/down".equals("Mark up/down")) {
            n99 = 3;
        }
        if (this._dialog.auto) {
            float abs = 0.0f;
            float abs2 = 0.0f;
            if (this._listArrayList.size() > 1) {
                final ArrayList coll4 = new ArrayList();
                for (int index37 = 0; index37 < this._listArrayList.size(); index37 += 2) {
                    final int intValue56 = this._hashTracks.get(this._hashCurrentDataset.get(new Integer(index37 / 2)));
                    final ArrayList<Integer> list32 = this._hashSliceNumber.get(new Integer(index37 / 2));
                    final float[][] array94 = this._listArrayList.get(index37);
                    final float[][] array95 = this._listArrayList.get(index37 + 1);
                    for (int index38 = 0; index38 < intValue56; ++index38) {
                        for (int intValue57 = list32.get(index38), n100 = 1; n100 < intValue57; ++n100) {
                            if (abs < Math.abs(array94[index38][n100])) {
                                abs = Math.abs(array94[index38][n100]);
                            }
                            if (abs2 < Math.abs(array95[index38][n100])) {
                                abs2 = Math.abs(array95[index38][n100]);
                            }
                        }
                    }
                    if (abs > abs2) {
                        coll4.add(new Float(abs));
                    }
                    else {
                        coll4.add(new Float(abs2));
                    }
                }
                this._coordSize = Collections.<Float>max((Collection<? extends Float>)coll4) + 5.0f;
            }
        }
        for (int n101 = 0; n101 < this._listArrayList.size(); n101 += 2) {
            this.plotGraph(n99, n101);
        }
    }
    
    float angleCorrection(final float n) {
        float n2 = n;
        if (n2 < 0.0f) {
            n2 = n2 % 360.0f + 360.0f;
        }
        if (n2 > 360.0f) {
            n2 %= 360.0f;
        }
        return n2;
    }
    
    float[] calculatePoints(final float n) {
        final float n2 = this._coordSize * 2.0f;
        return new float[] { this.roundNumber(new Float(n2 * Math.cos(Math.toRadians(n)))), this.roundNumber(new Float(n2 * Math.sin(Math.toRadians(n)))) };
    }
    
    int countCells(final float n, final int index) {
        final String key = this._hashCurrentDataset.get(new Integer(index / 2));
        final ArrayList<Integer> list = this._hashSliceNumber.get(new Integer(index / 2));
        final int intValue = this._hashTracks.get(key);
        final float[][] array = this._listArrayList.get(index);
        final float[][] array2 = this._listArrayList.get(index + 1);
        int n2 = 0;
        final float angleCorrection = this.angleCorrection(this._anglePosition + n / 2.0f);
        final float angleCorrection2 = this.angleCorrection(this._anglePosition - n / 2.0f);
        final float roundNumber = this.roundNumber(new Float(Math.tan(Math.toRadians(angleCorrection))));
        final float roundNumber2 = this.roundNumber(new Float(Math.tan(Math.toRadians(angleCorrection2))));
        for (int i = 0; i < intValue; ++i) {
            final int intValue2 = list.get(i);
            final float n3 = array[i][intValue2 - 1];
            final float n4 = array2[i][intValue2 - 1];
            if (((angleCorrection > 0.0f && angleCorrection < 90.0f && angleCorrection2 > 0.0f && angleCorrection2 < 90.0f) || (angleCorrection > 270.0f && angleCorrection < 360.0f && angleCorrection2 > 270.0f && angleCorrection2 < 360.0f)) && n4 <= roundNumber * n3 && n4 >= roundNumber2 * n3) {
                ++n2;
            }
            if (((angleCorrection > 90.0f && angleCorrection < 180.0f && angleCorrection2 > 90.0f && angleCorrection2 < 180.0f) || (angleCorrection > 180.0f && angleCorrection < 270.0f && angleCorrection2 > 180.0f && angleCorrection2 < 270.0f)) && n4 >= roundNumber * n3 && n4 <= roundNumber2 * n3) {
                ++n2;
            }
            if (angleCorrection > 0.0f && angleCorrection < 90.0f) {
                if (angleCorrection2 > 180.0f && angleCorrection2 < 270.0f && n4 <= roundNumber * n3 && n4 <= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 > 270.0f && angleCorrection2 < 360.0f && n4 <= roundNumber * n3 && n4 >= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 270.0f && n3 >= 0.0f && n4 <= roundNumber * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 0.0f && n4 >= 0.0f && n4 <= roundNumber * n3) {
                    ++n2;
                }
            }
            if (angleCorrection > 90.0f && angleCorrection < 180.0f) {
                if (angleCorrection2 > 0.0f && angleCorrection2 < 90.0f && n4 >= roundNumber * n3 && n4 >= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 > 270.0f && angleCorrection2 < 360.0f && n4 >= roundNumber * n3 && n4 >= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 90.0f && n3 <= 0.0f && n4 >= roundNumber * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 0.0f && n4 >= 0.0f && n4 >= roundNumber * n3) {
                    ++n2;
                }
            }
            if (angleCorrection > 180.0f && angleCorrection < 270.0f) {
                if (angleCorrection2 > 90.0f && angleCorrection2 < 180.0f && n4 >= roundNumber * n3 && n4 <= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 > 0.0f && angleCorrection2 < 90.0f && n4 >= roundNumber * n3 && n4 >= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 90.0f && n3 <= 0.0f && n4 >= roundNumber * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 180.0f && n4 <= 0.0f && n4 >= roundNumber * n3) {
                    ++n2;
                }
            }
            if (angleCorrection > 270.0f && angleCorrection < 360.0f) {
                if (angleCorrection2 > 180.0f && angleCorrection2 < 270.0f && n4 <= roundNumber * n3 && n4 <= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 > 90.0f && angleCorrection2 < 180.0f && n4 <= roundNumber * n3 && n4 <= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 180.0f && n4 <= 0.0f && n4 <= roundNumber * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 270.0f && n3 >= 0.0f && n4 <= roundNumber * n3) {
                    ++n2;
                }
            }
            if (angleCorrection == 90.0f) {
                if (((angleCorrection2 > 0.0f && angleCorrection2 < 90.0f) || (angleCorrection2 > 270.0f && angleCorrection2 < 360.0f)) && n3 >= 0.0f && n4 >= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 0.0f && n3 >= 0.0f && n4 >= 0.0f) {
                    ++n2;
                }
                if (angleCorrection2 == 270.0f && n3 >= 0.0f) {
                    ++n2;
                }
            }
            if (angleCorrection == 180.0f) {
                if (((angleCorrection2 > 0.0f && angleCorrection2 < 90.0f) || (angleCorrection2 > 90.0f && angleCorrection2 < 180.0f)) && n4 >= 0.0f && n4 <= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 0.0f && n4 >= 0.0f) {
                    ++n2;
                }
                if (angleCorrection2 == 90.0f && n3 <= 0.0f && n4 >= 0.0f) {
                    ++n2;
                }
            }
            if (angleCorrection == 270.0f) {
                if (((angleCorrection2 > 90.0f && angleCorrection2 < 180.0f) || (angleCorrection2 > 180.0f && angleCorrection2 < 270.0f)) && n3 <= 0.0f && n4 <= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 90.0f && n3 <= 0.0f) {
                    ++n2;
                }
                if (angleCorrection2 == 180.0f && n3 <= 0.0f && n4 <= 0.0f) {
                    ++n2;
                }
            }
            if (angleCorrection == 360.0f) {
                if (angleCorrection2 > 180.0f && angleCorrection2 < 270.0f && n4 <= 0.0f && n4 <= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 > 270.0f && angleCorrection2 < 360.0f && n4 <= 0.0f && n4 >= roundNumber2 * n3) {
                    ++n2;
                }
                if (angleCorrection2 == 180.0f && n4 <= 0.0f) {
                    ++n2;
                }
                if (angleCorrection2 == 270.0f && n3 >= 0.0f && n4 <= 0.0f) {
                    ++n2;
                }
            }
        }
        return n2;
    }
    
    void plotGraph(final int value, final int n) {
        final String s = this._hashCurrentDataset.get(new Integer(n / 2));
        final ArrayList<Integer> list = this._hashSliceNumber.get(new Integer(n / 2));
        final int intValue = this._hashTracks.get(s);
        final float[][] array = this._listArrayList.get(n);
        final float[][] array2 = this._listArrayList.get(n + 1);
        double roundDoubleNumbers = 0.0;
        double roundDoubleNumbers2 = 0.0;
        int i = 0;
        int j = 0;
        final ArrayList<Double> list2 = null;
        for (int k = 0; k < intValue; ++k) {
            final int intValue2 = list.get(k);
            roundDoubleNumbers += array[k][intValue2 - 1];
            roundDoubleNumbers2 += array2[k][intValue2 - 1];
        }
        if (intValue != 0) {
            roundDoubleNumbers /= intValue;
            roundDoubleNumbers2 /= intValue;
        }
        roundDoubleNumbers = this.roundDoubleNumbers(roundDoubleNumbers);
        roundDoubleNumbers2 = this.roundDoubleNumbers(roundDoubleNumbers2);
        final float[] array3 = { 0.0f };
        final Plot plot = new Plot(s, "x axis [" + this._unitsPath + "]", "y axis  [" + this._unitsPath + "]", array3, array3);
        if (SPTBatch_.chemoScaling.getText().contains("...") == Boolean.TRUE) {
            final float[] array4 = { this._coordSize, -this._coordSize };
            final float[] array5 = new float[2];
            plot.setLimits((double)(-this._coordSize), (double)this._coordSize, (double)(-this._coordSize), (double)this._coordSize);
            plot.addPoints(array4, array5, 2);
            plot.addPoints(array5, array4, 2);
        }
        if (SPTBatch_.chemoScaling.getText().contains("...") == Boolean.FALSE) {
            final float[] array6 = { -Integer.valueOf(SPTBatch_.chemoScaling.getText()), Integer.valueOf(SPTBatch_.chemoScaling.getText()) };
            final float[] array7 = new float[2];
            final float[] array8 = { -Integer.valueOf(SPTBatch_.chemoScaling.getText()), Integer.valueOf(SPTBatch_.chemoScaling.getText()) };
            plot.setLimits(-Double.valueOf(SPTBatch_.chemoScaling.getText()), (double)Double.valueOf(SPTBatch_.chemoScaling.getText()), -Double.valueOf(SPTBatch_.chemoScaling.getText()), (double)Double.valueOf(SPTBatch_.chemoScaling.getText()));
            plot.addPoints(array6, array7, 2);
            plot.addPoints(array7, array8, 2);
        }
        plot.draw();
        plot.setLineWidth(1);
        final float[] array9 = { 0.0f };
        final float[] array10 = { 0.0f };
        if (value == 3) {
            for (int index = 0; index < intValue; ++index) {
                final int intValue3 = list.get(index);
                final float[] array11 = new float[intValue3];
                final float[] array12 = new float[intValue3];
                array9[0] = array[index][intValue3 - 1];
                array10[0] = array2[index][intValue3 - 1];
                if (array10[0] >= 0.0f) {
                    plot.setColor(Color.black);
                    ++i;
                }
                else {
                    plot.setColor(Color.red);
                    ++j;
                }
                for (int n2 = 0; n2 < intValue3; ++n2) {
                    array11[n2] = array[index][n2];
                    array12[n2] = array2[index][n2];
                }
                plot.setLineWidth(1);
                plot.addPoints(array11, array12, 2);
                plot.setLineWidth(3);
                plot.addPoints(array9, array10, 0);
            }
            plot.setColor(Color.black);
            plot.addLabel(0.0, 0.0, "Number of tracks: " + intValue + "  Counts up: " + i + "  Counts down: " + j);
            plot.addLabel(0.0, 0.04, "Center of mass [" + this._unitsPath + "]: x=" + roundDoubleNumbers + " y=" + roundDoubleNumbers2);
        }
        final float[] array13 = { new Float(roundDoubleNumbers) };
        final float[] array14 = { new Float(roundDoubleNumbers2) };
        plot.setColor(Color.blue);
        plot.setLineWidth(3);
        plot.addPoints(array13, array14, 5);
        this._currentOpenWindows.size();
        final int n3 = this._listArrayList.size() / 2;
        final ArrayList<Integer> value2 = new ArrayList<Integer>();
        value2.add(new Integer(value));
        value2.add(new Integer(n));
        value2.add(new Integer(intValue));
        this._hashPlot.put(s, value2);
        IJ.save(plot.getImagePlus(), SPTBatch_.directChemo + File.separator + "Chemotaxis Plot for " + SPTBatch_.imps.getShortTitle() + ".png");
    }
    
    void readData(String string) throws FileNotFoundException, IOException {
        this._importedData.add(this.arrayToImport);
        IJ.showStatus("Dataset imported");
        string = String.valueOf(this._importedData.size()) + ": " + string;
        this._hashImportedDataset.put(string, new Integer(this._importedData.size()));
    }
    
    double roundDoubleNumbers(final double n) {
        return Math.round(n * 100.0) / 100.0;
    }
    
    float roundFloatNumbers(final float n) {
        return Math.round(n * 100.0f) / 100.0f;
    }
    
    float roundNumber(final float n) {
        return Math.round(n * 10000.0f) / 10000.0f;
    }
    
    public void stateChanged(final ChangeEvent changeEvent) {
        final int selectedIndex = ((JTabbedPane)changeEvent.getSource()).getSelectedIndex();
        if (selectedIndex == 1) {
            if (this._dialog.auto) {
                PlotWindow.plotHeight = this._plotHeight;
                PlotWindow.plotWidth = this._plotWidth;
            }
            else {
                PlotWindow.plotHeight = (int)(this._dialog.fraction * (Math.abs(this._dialog.minimumY) + Math.abs(this._dialog.maximumY)));
                PlotWindow.plotWidth = (int)(this._dialog.fraction * (Math.abs(this._dialog.minimumX) + Math.abs(this._dialog.maximumX)));
            }
        }
        if (selectedIndex == 3) {
            PlotWindow.plotHeight = this._plotHeight;
            PlotWindow.plotWidth = this._plotWidth;
        }
    }
    
    void updateNumberofTracks(final String s, final int n) {
        if (this._hashTracks.containsKey(s)) {
            this._hashTracks.remove(s);
            this._hashTracks.put(s, new Integer(n));
        }
        else {
            this._hashTracks.put(s, new Integer(n));
        }
    }
    
    public void windowActivated(final WindowEvent windowEvent) {
    }
    
    public void windowClosed(final WindowEvent windowEvent) {
    }
    
    public void windowClosing(final WindowEvent windowEvent) {
        if (windowEvent.getSource() == this.gui) {
            if (this._openInfoWindows != null) {
                for (int i = 0; i < this._openInfoWindows.size(); ++i) {
                    this._openInfoWindows.get(i).dispose();
                }
                this._openInfoWindows.clear();
            }
            this._currentOpenWindows.clear();
            this._currentOpenDiagrams.clear();
            WindowManager.closeAllWindows();
            this.gui.dispose();
        }
        else {
            if (windowEvent.getSource() instanceof JFrame && this._openInfoWindows.contains(windowEvent.getSource())) {
                this._openInfoWindows.remove(windowEvent.getSource());
            }
            if (windowEvent.getSource() instanceof PlotWindow) {
                if (this._currentOpenWindows.contains(windowEvent.getSource())) {
                    for (int j = 0; j < this._currentOpenWindows.size(); ++j) {
                        this._currentOpenWindows.get(j).dispose();
                    }
                    this._currentOpenWindows.clear();
                    this.gui.plotClosed();
                }
                if (this._currentOpenDiagrams.contains(windowEvent.getSource())) {
                    for (int k = 0; k < this._currentOpenDiagrams.size(); ++k) {
                        this._currentOpenDiagrams.get(k).dispose();
                    }
                    this._currentOpenDiagrams.clear();
                }
            }
        }
    }
    
    public void windowDeactivated(final WindowEvent windowEvent) {
    }
    
    public void windowDeiconified(final WindowEvent windowEvent) {
    }
    
    public void windowIconified(final WindowEvent windowEvent) {
    }
    
    public void windowOpened(final WindowEvent windowEvent) {
    }
}
