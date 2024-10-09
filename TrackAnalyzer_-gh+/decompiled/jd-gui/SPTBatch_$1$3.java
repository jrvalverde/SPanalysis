/*      */ import fiji.plugin.trackmate.visualization.table.TablePanel;
/*      */ import ij.measure.ResultsTable;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import javax.swing.JTable;
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
/*      */ class null
/*      */   implements Runnable
/*      */ {
/*      */   public void run() {
/* 1744 */     if (SPTBatch_.checkboxSubBg.isSelected()) {
/* 1745 */       TablePanel<Integer> trackTable = SPTBatch_.access$124(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 1746 */       SPTBatch_.trackJTable = trackTable.getTable();
/*      */       
/* 1748 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1749 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1750 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1751 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1752 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1753 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1754 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1755 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1756 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1757 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1758 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1759 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1760 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1761 */             "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1762 */             ")", 
/* 1763 */             "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 1764 */             "Movement", "sMSS", "sMSS Movement" }; 
/* 1765 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1766 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1767 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1768 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1769 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1770 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1771 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1772 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1773 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1774 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1775 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1776 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1777 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1778 */             "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1779 */             ")", 
/* 1780 */             "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 1781 */             "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */       }
/* 1783 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1784 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1785 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1786 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1787 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1788 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1789 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1790 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1791 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1792 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1793 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1794 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1795 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1796 */             "D1-N", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1797 */             "sMSS Movement" }; 
/* 1798 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1799 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1800 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1801 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1802 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1803 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1804 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1805 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1806 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1807 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1808 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1809 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1810 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1811 */             "D1-N", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1812 */             "sMSS Movement", "Track within Cell" };
/*      */       }
/* 1814 */       String[][] rowDataTrack = new String[SPTBatch_.trackJTable
/* 1815 */           .getRowCount()][SPTBatch_.columnNamesTrack.length];
/* 1816 */       for (int j = 0; j < SPTBatch_.trackJTable.getRowCount(); j++) {
/* 1817 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "";
/* 1818 */         for (int c = 0; c < SPTBatch_.trackJTable.getColumnCount(); c++) {
/* 1819 */           rowDataTrack[j][c] = String.valueOf(SPTBatch_.trackJTable.getValueAt(j, c));
/*      */         }
/*      */       } 
/* 1822 */       List<Integer> nOfTracks = new ArrayList<>();
/* 1823 */       for (int t = 0; t < SPTBatch_.trackJTable.getRowCount(); t++) {
/* 1824 */         nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(t, 2).toString()));
/*      */       }
/*      */       
/* 1827 */       List<Double> allTracks = new ArrayList<>();
/* 1828 */       List<Double> allTracksDef = new ArrayList<>();
/* 1829 */       for (int k = 0; k < nOfTracks.size(); k++) {
/* 1830 */         int counter = 0;
/* 1831 */         List<Double> perTrack = new ArrayList<>();
/* 1832 */         List<Double> perTrackDef = new ArrayList<>();
/* 1833 */         for (int m = 0; m < SPTBatch_.tableSpot.getRowCount(); m++) {
/* 1834 */           if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(m, 2).toString())
/* 1835 */             .equals(nOfTracks.get(k)) == Boolean.TRUE.booleanValue())
/*      */           {
/* 1837 */             perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel()
/* 1838 */                   .getValueAt(m, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1843 */         if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue())
/*      */         {
/* 1845 */           for (int n = SPTBatch_.minTracksJTF; n < SPTBatch_.maxTracksJTF; n++)
/*      */           {
/* 1847 */             perTrackDef.add(perTrack.get(n));
/*      */           }
/*      */         }
/*      */         
/* 1851 */         if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1852 */           perTrackDef.size() != 0) {
/* 1853 */           allTracksDef.add(Double.valueOf(perTrackDef.stream()
/* 1854 */                 .mapToDouble(a -> a.doubleValue()).average().getAsDouble()));
/*      */         }
/* 1856 */         if (perTrack.size() != 0)
/* 1857 */           allTracks.add(Double.valueOf(
/* 1858 */                 perTrack.stream().mapToDouble(a -> a.doubleValue()).average().getAsDouble())); 
/*      */       } 
/* 1860 */       ComputeMSD values = new ComputeMSD(Integer.valueOf(SPTBatch_.pointsMSD.getText()).intValue());
/* 1861 */       values.Compute(nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 1862 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1863 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/*      */       {
/* 1865 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1866 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1867 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1868 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1869 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1870 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1871 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1872 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1873 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1874 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1875 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 1876 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1877 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1878 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1879 */             String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/* 1880 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1881 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1882 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1883 */               String.valueOf("Long");
/*      */           } else {
/* 1885 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1886 */               String.valueOf("Short");
/*      */           } 
/* 1888 */           if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1889 */             if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1890 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1891 */                 String.valueOf("Immobile");
/*      */             }
/* 1893 */             if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1894 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1895 */                 String.valueOf("Mobile"); 
/*      */           } 
/* 1897 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1898 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */           
/* 1900 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1901 */             Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.0D)
/* 1902 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1903 */               String.valueOf("Confined"); 
/* 1904 */           if (Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() < 0.9D && 
/* 1905 */             Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.6D)
/* 1906 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1907 */               String.valueOf("Anomalous"); 
/* 1908 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1909 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1910 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1911 */               String.valueOf("Free"); 
/* 1912 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1913 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1914 */               String.valueOf("Directed");
/*      */           }
/*      */           
/* 1917 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 1918 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 1919 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1920 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1921 */                 1] = "Unidirectional Ballistic"; 
/* 1922 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 1923 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 1924 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 1925 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 1926 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 1927 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 1928 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 1929 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1940 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1941 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1942 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1943 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 1944 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1945 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1946 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1947 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1948 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1949 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1950 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1951 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1952 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 1953 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1954 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1955 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1956 */             String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/*      */           
/* 1958 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1959 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1960 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1961 */               String.valueOf("Long");
/*      */           } else {
/* 1963 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1964 */               String.valueOf("Short");
/*      */           } 
/* 1966 */           if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1967 */             if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1968 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1969 */                 String.valueOf("Immobile");
/*      */             }
/* 1971 */             if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1972 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1973 */                 String.valueOf("Mobile"); 
/*      */           } 
/* 1975 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1976 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */           
/* 1978 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1979 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 1980 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1981 */               String.valueOf("Confined"); 
/* 1982 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 1983 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 1984 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1985 */               String.valueOf("Anomalous"); 
/* 1986 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1987 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1988 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1989 */               String.valueOf("Free"); 
/* 1990 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1991 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1992 */               String.valueOf("Directed");
/*      */           }
/*      */           
/* 1995 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1996 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 1997 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1998 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1999 */                 2] = "Unidirectional Ballistic"; 
/* 2000 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2001 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2002 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2003 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2004 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2005 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2006 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2007 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2015 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2016 */             .toString();
/*      */         } 
/*      */       }
/*      */       
/* 2020 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2021 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2022 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2023 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2024 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2025 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2026 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2027 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2028 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2029 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2030 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2031 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2032 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2033 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2034 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 2035 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2036 */             String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2037 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2038 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2039 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2040 */             String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/* 2041 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2042 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2043 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2044 */               String.valueOf("Long");
/*      */           } else {
/* 2046 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2047 */               String.valueOf("Short");
/*      */           } 
/* 2049 */           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2050 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2051 */               String.valueOf("Immobile");
/*      */           }
/* 2053 */           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2054 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2055 */               String.valueOf("Mobile"); 
/* 2056 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2057 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2058 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2059 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2060 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2061 */               String.valueOf("Confined"); 
/* 2062 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2063 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2064 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2065 */               String.valueOf("Anomalous"); 
/* 2066 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2067 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2068 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2069 */               String.valueOf("Free"); 
/* 2070 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2071 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2072 */               String.valueOf("Directed");
/*      */           }
/*      */ 
/*      */           
/* 2076 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2077 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 2078 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2079 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2080 */                 1] = "Unidirectional Ballistic"; 
/* 2081 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2082 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2083 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2084 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2085 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2086 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2087 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2088 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
/*      */           }
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2099 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2100 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2101 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2102 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2103 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2104 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2105 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2106 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2107 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2108 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 2109 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 2110 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 2111 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 2112 */             "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 2113 */             ")", 
/* 2114 */             "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 2115 */             "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */         
/* 2117 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2118 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 15] = 
/* 2119 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2120 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2121 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2122 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2123 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2124 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2125 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2126 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2127 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 2128 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2129 */             String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2130 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2131 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2132 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2133 */             String.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString());
/* 2134 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2135 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2136 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2137 */               String.valueOf("Long");
/*      */           } else {
/* 2139 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2140 */               String.valueOf("Short");
/*      */           } 
/* 2142 */           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2143 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2144 */               String.valueOf("Immobile");
/*      */           }
/* 2146 */           if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(m)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2147 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2148 */               String.valueOf("Mobile"); 
/* 2149 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2150 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2151 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2152 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2153 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2154 */               String.valueOf("Confined"); 
/* 2155 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2156 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2157 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2158 */               String.valueOf("Anomalous"); 
/* 2159 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2160 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2161 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2162 */               String.valueOf("Free"); 
/* 2163 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2164 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2165 */               String.valueOf("Directed");
/*      */           }
/*      */ 
/*      */           
/* 2169 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2170 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 2171 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2172 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2173 */                 2] = "Unidirectional Ballistic"; 
/* 2174 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2175 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2176 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2177 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2178 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2179 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2180 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2181 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2189 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2190 */             .toString();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2195 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2196 */       for (int x = 0; x < rowDataTrack.length; x++) {
/* 2197 */         for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2198 */           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */       }  try {
/* 2200 */         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2201 */             SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2202 */       } catch (IOException e) {
/*      */         
/* 2204 */         e.printStackTrace();
/*      */       } 
/* 2206 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */     } 
/*      */     
/* 2209 */     if (!SPTBatch_.checkboxSubBg.isSelected()) {
/* 2210 */       TablePanel<Integer> trackTable = SPTBatch_.access$124(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 2211 */       ComputeMSD values = new ComputeMSD(Integer.valueOf(SPTBatch_.pointsMSD.getText()).intValue());
/* 2212 */       values.Compute(SPTBatch_.nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 2213 */       JTable trackJTable = trackTable.getTable();
/* 2214 */       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2215 */           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2216 */           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2217 */           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2218 */           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2219 */           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2220 */           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2221 */           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", 
/* 2222 */           "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", 
/* 2223 */           "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", 
/* 2224 */           "Diffusion Coef.", "D1-N", "Track Length", "Motility", "Alpha", 
/* 2225 */           "Movement", "sMSS", "sMSS Movement" };
/* 2226 */       String[][] rowDataTrack = new String[trackJTable.getModel()
/* 2227 */           .getRowCount()][SPTBatch_.columnNamesTrack.length];
/*      */       int j;
/* 2229 */       for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2230 */         for (int c = 0; c < trackJTable.getModel().getColumnCount(); c++)
/* 2231 */           rowDataTrack[j][c] = 
/* 2232 */             String.valueOf(trackJTable.getModel().getValueAt(j, c)); 
/* 2233 */       }  for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2234 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2235 */           String.valueOf(((Double)ComputeMSD.msd1Values.get(j)).toString());
/* 2236 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2237 */           String.valueOf(((Double)ComputeMSD.msd2Values.get(j)).toString());
/* 2238 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2239 */           String.valueOf(((Double)ComputeMSD.msd3Values.get(j)).toString());
/* 2240 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2241 */           String.valueOf(((Double)ComputeMSD.msdValues.get(j)).toString());
/* 2242 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2243 */           String.valueOf(((Double)ComputeMSD.diffValues.get(j)).toString());
/* 2244 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2245 */           String.valueOf(((Double)ComputeMSD.d1NValues.get(j)).toString());
/* 2246 */         if (Double.valueOf(trackJTable.getModel().getValueAt(j, 3)
/* 2247 */             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2248 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Long");
/*      */         } else {
/* 2250 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Short");
/*      */         } 
/*      */         
/* 2253 */         if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(j)).toString()).doubleValue() <= SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2254 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2255 */             String.valueOf("Immobile");
/*      */         }
/* 2257 */         if (Double.valueOf(((Double)ComputeMSD.d1NValues.get(j)).toString()).doubleValue() > SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2258 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Mobile"); 
/* 2259 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2260 */           String.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString());
/* 2261 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.6D && 
/* 2262 */           Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.0D)
/* 2263 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2264 */             String.valueOf("Confined"); 
/* 2265 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.9D && 
/* 2266 */           Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.6D)
/* 2267 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2268 */             String.valueOf("Anomalous"); 
/* 2269 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 1.1D && 
/* 2270 */           Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.9D)
/* 2271 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Free"); 
/* 2272 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 1.1D) {
/* 2273 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2274 */             String.valueOf("Directed");
/*      */         }
/*      */ 
/*      */         
/* 2278 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2279 */           String.valueOf(ComputeMSD.mssValues.get(j));
/* 2280 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 1.0D)
/* 2281 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 
/* 2282 */               1] = "Unidirectional Ballistic"; 
/* 2283 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 0.0D)
/* 2284 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2285 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() <= 0.55D)
/* 2286 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2287 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 0.45D)
/* 2288 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2289 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 1.0D) {
/* 2290 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2301 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2302 */       for (int x = 0; x < rowDataTrack.length; x++) {
/* 2303 */         for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2304 */           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */       }  try {
/* 2306 */         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2307 */             SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2308 */       } catch (IOException e) {
/*      */         
/* 2310 */         e.printStackTrace();
/*      */       } 
/* 2312 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */     } 
/*      */ 
/*      */     
/* 2316 */     String[][] rowData = new String[4][(SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements.length];
/* 2317 */     int totalTracks = 0;
/* 2318 */     int longTracks = 0;
/* 2319 */     int longConfined = 0;
/* 2320 */     int longUniBal = 0;
/* 2321 */     int longFree = 0;
/* 2322 */     int longDirect = 0;
/* 2323 */     int immob = 0;
/* 2324 */     int shortTracks = 0;
/* 2325 */     int shortConfined = 0;
/* 2326 */     int shortAnom = 0;
/* 2327 */     int shortFree = 0;
/* 2328 */     int shortDirect = 0;
/*      */     
/* 2330 */     for (int r = 0; r < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size(); r++) {
/* 2331 */       if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2332 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2333 */             r) != null)
/* 2334 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2335 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2336 */             .equals("Long"))
/* 2337 */             longTracks++;  
/* 2338 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2339 */             r) != null)
/* 2340 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2341 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2342 */             .equals("Short"))
/* 2343 */             shortTracks++;  
/* 2344 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2345 */             r) != null)
/* 2346 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2347 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2348 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2349 */               .equals("Confined") && 
/* 2350 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2351 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2352 */               .equals("Long"))
/* 2353 */               longConfined++;   
/* 2354 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2355 */             r) != null)
/* 2356 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2357 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2358 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2359 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2360 */               .equals("Confined") && 
/* 2361 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2362 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2363 */               .equals("Short")) {
/* 2364 */               shortConfined++;
/*      */             }  
/* 2366 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2367 */             r) != null)
/* 2368 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2369 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2370 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2371 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2372 */               .equals("Unidirectional Ballistic") && 
/* 2373 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2374 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2375 */               .equals("Long"))
/* 2376 */               longUniBal++;   
/* 2377 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2378 */             r) != null)
/* 2379 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2380 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2381 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2382 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2383 */               .equals("Anomalous") && 
/* 2384 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2385 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2386 */               .equals("Short"))
/* 2387 */               shortAnom++;   
/* 2388 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2389 */             r) != null)
/* 2390 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2391 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2392 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2393 */               .equals("Free") && 
/* 2394 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2395 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2396 */               .equals("Long"))
/* 2397 */               longFree++;   
/* 2398 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2399 */             r) != null)
/* 2400 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2401 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2402 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2403 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2404 */               .equals("Free") && 
/* 2405 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2406 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2407 */               .equals("Short"))
/* 2408 */               shortFree++;   
/* 2409 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2410 */             r) != null)
/* 2411 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2412 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2413 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2414 */               .equals("Directed") && 
/* 2415 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2416 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2417 */               .equals("Long"))
/* 2418 */               longDirect++;   
/* 2419 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2420 */             r) != null)
/* 2421 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2422 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2423 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2424 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2425 */               .equals("Directed") && 
/* 2426 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2427 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2428 */               .equals("Short"))
/* 2429 */               shortDirect++;   
/* 2430 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, 
/* 2431 */             r) != null)
/* 2432 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2433 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, r)
/* 2434 */             .equals("Immobile"))
/* 2435 */             immob++;  
/*      */       } 
/* 2437 */       if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2438 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2439 */             r) != null)
/* 2440 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2441 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2442 */             .equals("Long"))
/* 2443 */             longTracks++;  
/* 2444 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2445 */             r) != null)
/* 2446 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2447 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2448 */             .equals("Short"))
/* 2449 */             shortTracks++;  
/* 2450 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2451 */             r) != null)
/* 2452 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2453 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2454 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2455 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2456 */               .equals("Confined") && 
/* 2457 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2458 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2459 */               .equals("Long"))
/* 2460 */               longConfined++;   
/* 2461 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2462 */             r) != null)
/* 2463 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2464 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2465 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2466 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2467 */               .equals("Confined") && 
/* 2468 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2469 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2470 */               .equals("Short")) {
/* 2471 */               shortConfined++;
/*      */             }  
/* 2473 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2474 */             r) != null)
/* 2475 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2476 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2477 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2478 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2479 */               .equals("Unidirectional Ballistic") && 
/* 2480 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2481 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2482 */               .equals("Long"))
/* 2483 */               longUniBal++;   
/* 2484 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2485 */             r) != null)
/* 2486 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2487 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2488 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2489 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2490 */               .equals("Anomalous") && 
/* 2491 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2492 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2493 */               .equals("Short"))
/* 2494 */               shortAnom++;   
/* 2495 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2496 */             r) != null)
/* 2497 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2498 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2499 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2500 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2501 */               .equals("Free") && 
/* 2502 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2503 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2504 */               .equals("Long"))
/* 2505 */               longFree++;   
/* 2506 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2507 */             r) != null)
/* 2508 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2509 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2510 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2511 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2512 */               .equals("Free") && 
/* 2513 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2514 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2515 */               .equals("Short"))
/* 2516 */               shortFree++;   
/* 2517 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2518 */             r) != null)
/* 2519 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2520 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2521 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2522 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2523 */               .equals("Directed") && 
/* 2524 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2525 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2526 */               .equals("Long"))
/* 2527 */               longDirect++;   
/* 2528 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2529 */             r) != null)
/* 2530 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2531 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2532 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2533 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2534 */               .equals("Directed") && 
/* 2535 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2536 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2537 */               .equals("Short"))
/* 2538 */               shortDirect++;   
/* 2539 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2540 */             r) != null) {
/* 2541 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2542 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2543 */             .equals("Immobile")) {
/* 2544 */             immob++;
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/* 2549 */     SPTBatch_.access$130(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$129(SPTBatch_.null.access$8(SPTBatch_.null.this)) + (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2550 */     SPTBatch_.access$132(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$131(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longTracks);
/* 2551 */     SPTBatch_.access$134(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$133(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longConfined);
/* 2552 */     SPTBatch_.access$136(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$135(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longUniBal);
/* 2553 */     SPTBatch_.access$138(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$137(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longFree);
/* 2554 */     SPTBatch_.access$140(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$139(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longDirect);
/* 2555 */     SPTBatch_.access$142(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$141(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortTracks);
/* 2556 */     SPTBatch_.access$144(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$143(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortConfined);
/* 2557 */     SPTBatch_.access$146(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$145(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortAnom);
/* 2558 */     SPTBatch_.access$148(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$147(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortFree);
/* 2559 */     SPTBatch_.access$150(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$149(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortDirect);
/* 2560 */     SPTBatch_.access$152(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$151(SPTBatch_.null.access$8(SPTBatch_.null.this)) + immob);
/*      */     
/* 2562 */     rowData[0][0] = String.valueOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2563 */     rowData[1][0] = String.valueOf("");
/* 2564 */     rowData[2][0] = String.valueOf("");
/* 2565 */     rowData[3][0] = String.valueOf("");
/* 2566 */     rowData[0][1] = String.valueOf(longTracks);
/* 2567 */     rowData[1][1] = String.valueOf(" ");
/* 2568 */     rowData[2][1] = String.valueOf("Short Tracks");
/* 2569 */     rowData[3][1] = String.valueOf(shortTracks);
/* 2570 */     rowData[0][2] = String.valueOf(longConfined);
/* 2571 */     rowData[1][2] = String.valueOf(" ");
/* 2572 */     rowData[2][2] = String.valueOf("Short Confined");
/* 2573 */     rowData[3][2] = String.valueOf(shortConfined);
/* 2574 */     rowData[0][3] = String.valueOf(longUniBal);
/* 2575 */     rowData[1][3] = String.valueOf(" ");
/* 2576 */     rowData[2][3] = String.valueOf("Short Anomalous");
/* 2577 */     rowData[3][3] = String.valueOf(shortAnom);
/* 2578 */     rowData[0][4] = String.valueOf(longFree);
/* 2579 */     rowData[1][4] = String.valueOf(" ");
/* 2580 */     rowData[2][4] = String.valueOf("Short Free");
/* 2581 */     rowData[3][4] = String.valueOf(shortFree);
/* 2582 */     rowData[0][5] = String.valueOf(longDirect);
/* 2583 */     rowData[1][5] = String.valueOf(" ");
/* 2584 */     rowData[2][5] = String.valueOf("Short Direct");
/* 2585 */     rowData[3][5] = String.valueOf(shortDirect);
/* 2586 */     rowData[0][6] = String.valueOf(immob);
/* 2587 */     rowData[1][6] = String.valueOf("");
/* 2588 */     rowData[2][6] = String.valueOf("");
/* 2589 */     rowData[3][6] = String.valueOf("");
/*      */     
/* 2591 */     ResultsTable rtTrackSum = new ResultsTable();
/* 2592 */     for (int i = 0; i < rowData.length; i++) {
/* 2593 */       for (int c = 0; c < (rowData[i]).length; c++)
/* 2594 */         rtTrackSum.setValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements[c], i, rowData[i][c]); 
/*      */     } 
/*      */     try {
/* 2597 */       rtTrackSum.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2598 */           SPTBatch_.imps.getShortTitle() + "_" + "SummaryTracks" + ".csv");
/* 2599 */     } catch (IOException e) {
/*      */       
/* 2601 */       e.printStackTrace();
/*      */     } 
/*      */ 
/*      */     
/* 2605 */     if (SPTBatch_.checkPBS.isSelected())
/* 2606 */       (new PhotobleachingSpotPlot()).Compute(); 
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/SPTBatch_$1$3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */