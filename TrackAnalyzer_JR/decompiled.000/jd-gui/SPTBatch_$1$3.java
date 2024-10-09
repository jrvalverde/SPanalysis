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
/*      */ class null
/*      */   implements Runnable
/*      */ {
/*      */   public void run() {
/* 1743 */     if (SPTBatch_.checkboxSubBg.isSelected()) {
/* 1744 */       TablePanel<Integer> trackTable = SPTBatch_.access$123(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 1745 */       SPTBatch_.trackJTable = trackTable.getTable();
/*      */       
/* 1747 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1748 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1749 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1750 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1751 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1752 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1753 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1754 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1755 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1756 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1757 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1758 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1759 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1760 */             "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1761 */             ")", 
/* 1762 */             "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 1763 */             "Movement", "sMSS", "sMSS Movement" }; 
/* 1764 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1765 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1766 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1767 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1768 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1769 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1770 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1771 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1772 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1773 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1774 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1775 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1776 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 1777 */             "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 1778 */             ")", 
/* 1779 */             "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 1780 */             "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */       }
/* 1782 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1783 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/* 1784 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1785 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1786 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1787 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1788 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1789 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1790 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1791 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1792 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1793 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1794 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1795 */             "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1796 */             "sMSS Movement" }; 
/* 1797 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1798 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1799 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 1800 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 1801 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 1802 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 1803 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 1804 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 1805 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 1806 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 1807 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 1808 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 1809 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", "Diffusion Coef.", 
/* 1810 */             "D1-4", "Track Length", "Motility", "Alpha", "Movement", "sMSS", 
/* 1811 */             "sMSS Movement", "Track within Cell" };
/*      */       }
/* 1813 */       String[][] rowDataTrack = new String[SPTBatch_.trackJTable
/* 1814 */           .getRowCount()][SPTBatch_.columnNamesTrack.length];
/* 1815 */       for (int j = 0; j < SPTBatch_.trackJTable.getRowCount(); j++) {
/* 1816 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "";
/* 1817 */         for (int c = 0; c < SPTBatch_.trackJTable.getColumnCount(); c++) {
/* 1818 */           rowDataTrack[j][c] = String.valueOf(SPTBatch_.trackJTable.getValueAt(j, c));
/*      */         }
/*      */       } 
/* 1821 */       List<Integer> nOfTracks = new ArrayList<>();
/* 1822 */       for (int t = 0; t < SPTBatch_.trackJTable.getRowCount(); t++) {
/* 1823 */         nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(t, 2).toString()));
/*      */       }
/*      */       
/* 1826 */       List<Double> allTracks = new ArrayList<>();
/* 1827 */       List<Double> allTracksDef = new ArrayList<>();
/* 1828 */       for (int k = 0; k < nOfTracks.size(); k++) {
/* 1829 */         int counter = 0;
/* 1830 */         List<Double> perTrack = new ArrayList<>();
/* 1831 */         List<Double> perTrackDef = new ArrayList<>();
/* 1832 */         for (int m = 0; m < SPTBatch_.tableSpot.getRowCount(); m++) {
/* 1833 */           if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(m, 2).toString())
/* 1834 */             .equals(nOfTracks.get(k)) == Boolean.TRUE.booleanValue())
/*      */           {
/* 1836 */             perTrack.add(Double.valueOf(SPTBatch_.tableSpot.getModel()
/* 1837 */                   .getValueAt(m, SPTBatch_.tableSpot.getColumnCount() - 1).toString()));
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/* 1842 */         if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue())
/*      */         {
/* 1844 */           for (int n = SPTBatch_.minTracksJTF; n < SPTBatch_.maxTracksJTF; n++)
/*      */           {
/* 1846 */             perTrackDef.add(perTrack.get(n));
/*      */           }
/*      */         }
/*      */         
/* 1850 */         if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 1851 */           perTrackDef.size() != 0) {
/* 1852 */           allTracksDef.add(Double.valueOf(perTrackDef.stream()
/* 1853 */                 .mapToDouble(a -> a.doubleValue()).average().getAsDouble()));
/*      */         }
/* 1855 */         if (perTrack.size() != 0)
/* 1856 */           allTracks.add(Double.valueOf(
/* 1857 */                 perTrack.stream().mapToDouble(a -> a.doubleValue()).average().getAsDouble())); 
/*      */       } 
/* 1859 */       ComputeMSD values = new ComputeMSD();
/* 1860 */       values.Compute(nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 1861 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1862 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue())
/*      */       {
/* 1864 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1865 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1866 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1867 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1868 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1869 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1870 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1871 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1872 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1873 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1874 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 1875 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1876 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1877 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1878 */             String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/* 1879 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1880 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1881 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1882 */               String.valueOf("Long");
/*      */           } else {
/* 1884 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1885 */               String.valueOf("Short");
/*      */           } 
/* 1887 */           if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1888 */             if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1889 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1890 */                 String.valueOf("Immobile");
/*      */             }
/* 1892 */             if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1893 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1894 */                 String.valueOf("Mobile"); 
/*      */           } 
/* 1896 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1897 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */           
/* 1899 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1900 */             Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.0D)
/* 1901 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1902 */               String.valueOf("Confined"); 
/* 1903 */           if (Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() < 0.9D && 
/* 1904 */             Double.valueOf(ComputeMSD.alphaValues.toString()).doubleValue() >= 0.6D)
/* 1905 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1906 */               String.valueOf("Anomalous"); 
/* 1907 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1908 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1909 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1910 */               String.valueOf("Free"); 
/* 1911 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1912 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1913 */               String.valueOf("Directed");
/*      */           }
/*      */           
/* 1916 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 1917 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 1918 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1919 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1920 */                 1] = "Unidirectional Ballistic"; 
/* 1921 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 1922 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 1923 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 1924 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 1925 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 1926 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 1927 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 1928 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
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
/* 1939 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE.booleanValue() && 
/* 1940 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 1941 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 1942 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 1943 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 1944 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 1945 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 1946 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 1947 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 1948 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 1949 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 1950 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 1951 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 1952 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 1953 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 1954 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 1955 */             String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/*      */           
/* 1957 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 1958 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 1959 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1960 */               String.valueOf("Long");
/*      */           } else {
/* 1962 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 1963 */               String.valueOf("Short");
/*      */           } 
/* 1965 */           if (SPTBatch_.thD14.getText() != null || SPTBatch_.thD14.getText() != "Diff") {
/* 1966 */             if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 1967 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1968 */                 String.valueOf("Immobile");
/*      */             }
/* 1970 */             if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 1971 */               rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 1972 */                 String.valueOf("Mobile"); 
/*      */           } 
/* 1974 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 1975 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/*      */           
/* 1977 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 1978 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 1979 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1980 */               String.valueOf("Confined"); 
/* 1981 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 1982 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 1983 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1984 */               String.valueOf("Anomalous"); 
/* 1985 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 1986 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 1987 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1988 */               String.valueOf("Free"); 
/* 1989 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 1990 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 1991 */               String.valueOf("Directed");
/*      */           }
/*      */           
/* 1994 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 1995 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 1996 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 1997 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 1998 */                 2] = "Unidirectional Ballistic"; 
/* 1999 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2000 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2001 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2002 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2003 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2004 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2005 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2006 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2014 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2015 */             .toString();
/*      */         } 
/*      */       }
/*      */       
/* 2019 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2020 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2021 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2022 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2023 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2024 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2025 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2026 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2027 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2028 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2029 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2030 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2031 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2032 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2033 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 2034 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2035 */             String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2036 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2037 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2038 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2039 */             String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/* 2040 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2041 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2042 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2043 */               String.valueOf("Long");
/*      */           } else {
/* 2045 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2046 */               String.valueOf("Short");
/*      */           } 
/* 2048 */           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2049 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2050 */               String.valueOf("Immobile");
/*      */           }
/* 2052 */           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2053 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2054 */               String.valueOf("Mobile"); 
/* 2055 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2056 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2057 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2058 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2059 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2060 */               String.valueOf("Confined"); 
/* 2061 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2062 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2063 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2064 */               String.valueOf("Anomalous"); 
/* 2065 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2066 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2067 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2068 */               String.valueOf("Free"); 
/* 2069 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2070 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2071 */               String.valueOf("Directed");
/*      */           }
/*      */ 
/*      */           
/* 2075 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2076 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 2077 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2078 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2079 */                 1] = "Unidirectional Ballistic"; 
/* 2080 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2081 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2082 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2083 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2084 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2085 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2086 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2087 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
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
/* 2098 */       if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE.booleanValue() && 
/* 2099 */         SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2100 */         SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2101 */             "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2102 */             "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2103 */             "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2104 */             "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2105 */             "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2106 */             "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2107 */             "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", 
/* 2108 */             "MEAN_STRAIGHT_LINE_SPEED", "LINEARITY_OF_FORWARD_PROGRESSION", 
/* 2109 */             "MEAN_DIRECTIONAL_CHANGE_RATE", "MSD timelag=1", "MSD timelag=2", 
/* 2110 */             "MSD timelag=3", "MSD", "Intensity-Bg Subtract", 
/* 2111 */             "Intensity-Bg Subtract (" + SPTBatch_.minTracksJTF + "-" + SPTBatch_.maxTracksJTF + 
/* 2112 */             ")", 
/* 2113 */             "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 2114 */             "Movement", "sMSS", "sMSS Movement", "Track within Cell" };
/*      */         
/* 2116 */         for (int m = 0; m < SPTBatch_.trackJTable.getRowCount(); m++) {
/* 2117 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 15] = 
/* 2118 */             String.valueOf(((Double)ComputeMSD.msd1Values.get(m)).toString());
/* 2119 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 14] = 
/* 2120 */             String.valueOf(((Double)ComputeMSD.msd2Values.get(m)).toString());
/* 2121 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 13] = 
/* 2122 */             String.valueOf(((Double)ComputeMSD.msd3Values.get(m)).toString());
/* 2123 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2124 */             String.valueOf(((Double)ComputeMSD.msdValues.get(m)).toString());
/* 2125 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2126 */             String.valueOf(((Double)allTracks.get(m)).toString());
/* 2127 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2128 */             String.valueOf(((Double)allTracksDef.get(m)).toString());
/* 2129 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2130 */             String.valueOf(((Double)ComputeMSD.diffValues.get(m)).toString());
/* 2131 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2132 */             String.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString());
/* 2133 */           if (Double.valueOf(SPTBatch_.trackJTable.getModel().getValueAt(m, 3)
/* 2134 */               .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2135 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2136 */               String.valueOf("Long");
/*      */           } else {
/* 2138 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2139 */               String.valueOf("Short");
/*      */           } 
/* 2141 */           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2142 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2143 */               String.valueOf("Immobile");
/*      */           }
/* 2145 */           if (Double.valueOf(((Double)ComputeMSD.d14Values.get(m)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2146 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 6] = 
/* 2147 */               String.valueOf("Mobile"); 
/* 2148 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2149 */             String.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString());
/* 2150 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.6D && 
/* 2151 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.0D)
/* 2152 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2153 */               String.valueOf("Confined"); 
/* 2154 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 0.9D && 
/* 2155 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.6D)
/* 2156 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2157 */               String.valueOf("Anomalous"); 
/* 2158 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() < 1.1D && 
/* 2159 */             Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 0.9D)
/* 2160 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2161 */               String.valueOf("Free"); 
/* 2162 */           if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(m)).toString()).doubleValue() >= 1.1D) {
/* 2163 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2164 */               String.valueOf("Directed");
/*      */           }
/*      */ 
/*      */           
/* 2168 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2169 */             String.valueOf(ComputeMSD.mssValues.get(m));
/* 2170 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 1.0D)
/* 2171 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 
/* 2172 */                 2] = "Unidirectional Ballistic"; 
/* 2173 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() == 0.0D)
/* 2174 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Immobile"; 
/* 2175 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() <= 0.55D)
/* 2176 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Free"; 
/* 2177 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 0.45D)
/* 2178 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Confined"; 
/* 2179 */           if (((Double)ComputeMSD.mssValues.get(m)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(m)).doubleValue() < 1.0D) {
/* 2180 */             rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 2] = "Directed";
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2188 */           rowDataTrack[m][SPTBatch_.columnNamesTrack.length - 1] = ((Boolean)(SPTBatch_.null.access$8(SPTBatch_.null.this)).excludeTrack.get(m))
/* 2189 */             .toString();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2194 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2195 */       for (int x = 0; x < rowDataTrack.length; x++) {
/* 2196 */         for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2197 */           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */       }  try {
/* 2199 */         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2200 */             SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2201 */       } catch (IOException e) {
/*      */         
/* 2203 */         e.printStackTrace();
/*      */       } 
/* 2205 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */     } 
/*      */     
/* 2208 */     if (!SPTBatch_.checkboxSubBg.isSelected()) {
/* 2209 */       TablePanel<Integer> trackTable = SPTBatch_.access$123(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.model, (SPTBatch_.null.access$8(SPTBatch_.null.this)).ds);
/* 2210 */       ComputeMSD values = new ComputeMSD();
/* 2211 */       values.Compute(SPTBatch_.nOfTracks, (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtSpots[SPTBatch_.i]);
/* 2212 */       JTable trackJTable = trackTable.getTable();
/* 2213 */       SPTBatch_.columnNamesTrack = new String[] { "LABEL", "TRACK_INDEX", "TRACK_ID", 
/* 2214 */           "NUMBER_SPOTS", "NUMBER_GAPS", "NUMBER_SPLITS", "NUMBER_MERGES", 
/* 2215 */           "NUMBER_COMPLEX", "LONGEST_GAP", "TRACK_DURATION", "TRACK_START", 
/* 2216 */           "TRACK_STOP", "TRACK_DISPLACEMENT", "TRACK_X_LOCATION", 
/* 2217 */           "TRACK_Y_LOCATION", "TRACK_Z_LOCATION", "TRACK_MEAN_SPEED", 
/* 2218 */           "TRACK_MAX_SPEED", "TRACK_MIN_SPEED", "TRACK_MEDIAN_SPEED", 
/* 2219 */           "TRACK_STD_SPEED", "TRACK_MEAN_QUALITY", "TOTAL_DISTANCE_TRAVELED", 
/* 2220 */           "MAX_DISTANCE_TRAVELED", "CONFINMENT_RATIO", "MEAN_STRAIGHT_LINE_SPEED", 
/* 2221 */           "LINEARITY_OF_FORWARD_PROGRESSION", "MEAN_DIRECTIONAL_CHANGE_RATE", 
/* 2222 */           "MSD timelag=1", "MSD timelag=2", "MSD timelag=3", "MSD", 
/* 2223 */           "Diffusion Coef.", "D1-4", "Track Length", "Motility", "Alpha", 
/* 2224 */           "Movement", "sMSS", "sMSS Movement" };
/* 2225 */       String[][] rowDataTrack = new String[trackJTable.getModel()
/* 2226 */           .getRowCount()][SPTBatch_.columnNamesTrack.length];
/*      */       int j;
/* 2228 */       for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2229 */         for (int c = 0; c < trackJTable.getModel().getColumnCount(); c++)
/* 2230 */           rowDataTrack[j][c] = 
/* 2231 */             String.valueOf(trackJTable.getModel().getValueAt(j, c)); 
/* 2232 */       }  for (j = 0; j < trackJTable.getModel().getRowCount(); j++) {
/* 2233 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 12] = 
/* 2234 */           String.valueOf(((Double)ComputeMSD.msd1Values.get(j)).toString());
/* 2235 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 11] = 
/* 2236 */           String.valueOf(((Double)ComputeMSD.msd2Values.get(j)).toString());
/* 2237 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 10] = 
/* 2238 */           String.valueOf(((Double)ComputeMSD.msd3Values.get(j)).toString());
/* 2239 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 9] = 
/* 2240 */           String.valueOf(((Double)ComputeMSD.msdValues.get(j)).toString());
/* 2241 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 8] = 
/* 2242 */           String.valueOf(((Double)ComputeMSD.diffValues.get(j)).toString());
/* 2243 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 7] = 
/* 2244 */           String.valueOf(((Double)ComputeMSD.d14Values.get(j)).toString());
/* 2245 */         if (Double.valueOf(trackJTable.getModel().getValueAt(j, 3)
/* 2246 */             .toString()).doubleValue() >= Double.valueOf(SPTBatch_.thLengthJTF).doubleValue()) {
/* 2247 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Long");
/*      */         } else {
/* 2249 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 6] = String.valueOf("Short");
/*      */         } 
/*      */         
/* 2252 */         if (Double.valueOf(((Double)ComputeMSD.d14Values.get(j)).toString()).doubleValue() <= SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this))) {
/* 2253 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = 
/* 2254 */             String.valueOf("Immobile");
/*      */         }
/* 2256 */         if (Double.valueOf(((Double)ComputeMSD.d14Values.get(j)).toString()).doubleValue() > SPTBatch_.access$127(SPTBatch_.null.access$8(SPTBatch_.null.this)))
/* 2257 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 5] = String.valueOf("Mobile"); 
/* 2258 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 4] = 
/* 2259 */           String.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString());
/* 2260 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.6D && 
/* 2261 */           Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.0D)
/* 2262 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2263 */             String.valueOf("Confined"); 
/* 2264 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 0.9D && 
/* 2265 */           Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.6D)
/* 2266 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2267 */             String.valueOf("Anomalous"); 
/* 2268 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() < 1.1D && 
/* 2269 */           Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 0.9D)
/* 2270 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = String.valueOf("Free"); 
/* 2271 */         if (Double.valueOf(((Double)ComputeMSD.alphaValues.get(j)).toString()).doubleValue() >= 1.1D) {
/* 2272 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 3] = 
/* 2273 */             String.valueOf("Directed");
/*      */         }
/*      */ 
/*      */         
/* 2277 */         rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 2] = 
/* 2278 */           String.valueOf(ComputeMSD.mssValues.get(j));
/* 2279 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 1.0D)
/* 2280 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 
/* 2281 */               1] = "Unidirectional Ballistic"; 
/* 2282 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() == 0.0D)
/* 2283 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Immobile"; 
/* 2284 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() >= 0.45D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() <= 0.55D)
/* 2285 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Free"; 
/* 2286 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.0D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 0.45D)
/* 2287 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Confined"; 
/* 2288 */         if (((Double)ComputeMSD.mssValues.get(j)).doubleValue() > 0.55D && ((Double)ComputeMSD.mssValues.get(j)).doubleValue() < 1.0D) {
/* 2289 */           rowDataTrack[j][SPTBatch_.columnNamesTrack.length - 1] = "Directed";
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
/* 2300 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage = new ResultsTable();
/* 2301 */       for (int x = 0; x < rowDataTrack.length; x++) {
/* 2302 */         for (int y = 0; y < (rowDataTrack[x]).length; y++)
/* 2303 */           (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.setValue(SPTBatch_.columnNamesTrack[y], x, rowDataTrack[x][y]); 
/*      */       }  try {
/* 2305 */         (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2306 */             SPTBatch_.imps.getShortTitle() + "_" + "Tracks statistics" + ".csv");
/* 2307 */       } catch (IOException e) {
/*      */         
/* 2309 */         e.printStackTrace();
/*      */       } 
/* 2311 */       (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTracks[SPTBatch_.i] = (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage;
/*      */     } 
/*      */ 
/*      */     
/* 2315 */     String[][] rowData = new String[4][(SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements.length];
/* 2316 */     int totalTracks = 0;
/* 2317 */     int longTracks = 0;
/* 2318 */     int longConfined = 0;
/* 2319 */     int longUniBal = 0;
/* 2320 */     int longFree = 0;
/* 2321 */     int longDirect = 0;
/* 2322 */     int immob = 0;
/* 2323 */     int shortTracks = 0;
/* 2324 */     int shortConfined = 0;
/* 2325 */     int shortAnom = 0;
/* 2326 */     int shortFree = 0;
/* 2327 */     int shortDirect = 0;
/*      */     
/* 2329 */     for (int r = 0; r < (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size(); r++) {
/* 2330 */       if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.FALSE.booleanValue()) {
/* 2331 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2332 */             r) != null)
/* 2333 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2334 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2335 */             .equals("Long"))
/* 2336 */             longTracks++;  
/* 2337 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2338 */             r) != null)
/* 2339 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2340 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2341 */             .equals("Short"))
/* 2342 */             shortTracks++;  
/* 2343 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2344 */             r) != null)
/* 2345 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2346 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2347 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2348 */               .equals("Confined") && 
/* 2349 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2350 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2351 */               .equals("Long"))
/* 2352 */               longConfined++;   
/* 2353 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2354 */             r) != null)
/* 2355 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2356 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2357 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2358 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2359 */               .equals("Confined") && 
/* 2360 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2361 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2362 */               .equals("Short")) {
/* 2363 */               shortConfined++;
/*      */             }  
/* 2365 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2366 */             r) != null)
/* 2367 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2368 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2369 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2370 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2371 */               .equals("Unidirectional Ballistic") && 
/* 2372 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2373 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2374 */               .equals("Long"))
/* 2375 */               longUniBal++;   
/* 2376 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2377 */             r) != null)
/* 2378 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2379 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2380 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2381 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2382 */               .equals("Anomalous") && 
/* 2383 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2384 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2385 */               .equals("Short"))
/* 2386 */               shortAnom++;   
/* 2387 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2388 */             r) != null)
/* 2389 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2390 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2391 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2392 */               .equals("Free") && 
/* 2393 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2394 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2395 */               .equals("Long"))
/* 2396 */               longFree++;   
/* 2397 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2398 */             r) != null)
/* 2399 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2400 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2401 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2402 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2403 */               .equals("Free") && 
/* 2404 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2405 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2406 */               .equals("Short"))
/* 2407 */               shortFree++;   
/* 2408 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), 
/* 2409 */             r) != null)
/* 2410 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2411 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2412 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn(), r)
/* 2413 */               .equals("Directed") && 
/* 2414 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2415 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2416 */               .equals("Long"))
/* 2417 */               longDirect++;   
/* 2418 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, 
/* 2419 */             r) != null)
/* 2420 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2421 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r) != null)
/* 2422 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2423 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 2, r)
/* 2424 */               .equals("Directed") && 
/* 2425 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2426 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2427 */               .equals("Short"))
/* 2428 */               shortDirect++;   
/* 2429 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, 
/* 2430 */             r) != null)
/* 2431 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2432 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 4, r)
/* 2433 */             .equals("Immobile"))
/* 2434 */             immob++;  
/*      */       } 
/* 2436 */       if (SPTBatch_.checkExcludeTracks.isSelected() == Boolean.TRUE.booleanValue()) {
/* 2437 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2438 */             r) != null)
/* 2439 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2440 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2441 */             .equals("Long"))
/* 2442 */             longTracks++;  
/* 2443 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, 
/* 2444 */             r) != null)
/* 2445 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2446 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2447 */             .equals("Short"))
/* 2448 */             shortTracks++;  
/* 2449 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2450 */             r) != null)
/* 2451 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2452 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2453 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2454 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2455 */               .equals("Confined") && 
/* 2456 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2457 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2458 */               .equals("Long"))
/* 2459 */               longConfined++;   
/* 2460 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2461 */             r) != null)
/* 2462 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2463 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2464 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2465 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2466 */               .equals("Confined") && 
/* 2467 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2468 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2469 */               .equals("Short")) {
/* 2470 */               shortConfined++;
/*      */             }  
/* 2472 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2473 */             r) != null)
/* 2474 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2475 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2476 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2477 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2478 */               .equals("Unidirectional Ballistic") && 
/* 2479 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2480 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2481 */               .equals("Long"))
/* 2482 */               longUniBal++;   
/* 2483 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2484 */             r) != null)
/* 2485 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2486 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2487 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2488 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2489 */               .equals("Anomalous") && 
/* 2490 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2491 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2492 */               .equals("Short"))
/* 2493 */               shortAnom++;   
/* 2494 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2495 */             r) != null)
/* 2496 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2497 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2498 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2499 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2500 */               .equals("Free") && 
/* 2501 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2502 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2503 */               .equals("Long"))
/* 2504 */               longFree++;   
/* 2505 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2506 */             r) != null)
/* 2507 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2508 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2509 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2510 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2511 */               .equals("Free") && 
/* 2512 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2513 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2514 */               .equals("Short"))
/* 2515 */               shortFree++;   
/* 2516 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, 
/* 2517 */             r) != null)
/* 2518 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2519 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2520 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2521 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 1, r)
/* 2522 */               .equals("Directed") && 
/* 2523 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2524 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2525 */               .equals("Long"))
/* 2526 */               longDirect++;   
/* 2527 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, 
/* 2528 */             r) != null)
/* 2529 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue(
/* 2530 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r) != null)
/* 2531 */             if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2532 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 3, r)
/* 2533 */               .equals("Directed") && 
/* 2534 */               (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2535 */               .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 6, r)
/* 2536 */               .equals("Short"))
/* 2537 */               shortDirect++;   
/* 2538 */         if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, 
/* 2539 */             r) != null) {
/* 2540 */           if ((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage
/* 2541 */             .getStringValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.getLastColumn() - 5, r)
/* 2542 */             .equals("Immobile")) {
/* 2543 */             immob++;
/*      */           }
/*      */         }
/*      */       } 
/*      */     } 
/* 2548 */     SPTBatch_.access$129(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$128(SPTBatch_.null.access$8(SPTBatch_.null.this)) + (SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2549 */     SPTBatch_.access$131(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$130(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longTracks);
/* 2550 */     SPTBatch_.access$133(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$132(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longConfined);
/* 2551 */     SPTBatch_.access$135(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$134(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longUniBal);
/* 2552 */     SPTBatch_.access$137(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$136(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longFree);
/* 2553 */     SPTBatch_.access$139(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$138(SPTBatch_.null.access$8(SPTBatch_.null.this)) + longDirect);
/* 2554 */     SPTBatch_.access$141(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$140(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortTracks);
/* 2555 */     SPTBatch_.access$143(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$142(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortConfined);
/* 2556 */     SPTBatch_.access$145(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$144(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortAnom);
/* 2557 */     SPTBatch_.access$147(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$146(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortFree);
/* 2558 */     SPTBatch_.access$149(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$148(SPTBatch_.null.access$8(SPTBatch_.null.this)) + shortDirect);
/* 2559 */     SPTBatch_.access$151(SPTBatch_.null.access$8(SPTBatch_.null.this), SPTBatch_.access$150(SPTBatch_.null.access$8(SPTBatch_.null.this)) + immob);
/*      */     
/* 2561 */     rowData[0][0] = String.valueOf((SPTBatch_.null.access$8(SPTBatch_.null.this)).rtTrackPerImage.size());
/* 2562 */     rowData[1][0] = String.valueOf("");
/* 2563 */     rowData[2][0] = String.valueOf("");
/* 2564 */     rowData[3][0] = String.valueOf("");
/* 2565 */     rowData[0][1] = String.valueOf(longTracks);
/* 2566 */     rowData[1][1] = String.valueOf(" ");
/* 2567 */     rowData[2][1] = String.valueOf("Short Tracks");
/* 2568 */     rowData[3][1] = String.valueOf(shortTracks);
/* 2569 */     rowData[0][2] = String.valueOf(longConfined);
/* 2570 */     rowData[1][2] = String.valueOf(" ");
/* 2571 */     rowData[2][2] = String.valueOf("Short Confined");
/* 2572 */     rowData[3][2] = String.valueOf(shortConfined);
/* 2573 */     rowData[0][3] = String.valueOf(longUniBal);
/* 2574 */     rowData[1][3] = String.valueOf(" ");
/* 2575 */     rowData[2][3] = String.valueOf("Short Anomalous");
/* 2576 */     rowData[3][3] = String.valueOf(shortAnom);
/* 2577 */     rowData[0][4] = String.valueOf(longFree);
/* 2578 */     rowData[1][4] = String.valueOf(" ");
/* 2579 */     rowData[2][4] = String.valueOf("Short Free");
/* 2580 */     rowData[3][4] = String.valueOf(shortFree);
/* 2581 */     rowData[0][5] = String.valueOf(longDirect);
/* 2582 */     rowData[1][5] = String.valueOf(" ");
/* 2583 */     rowData[2][5] = String.valueOf("Short Direct");
/* 2584 */     rowData[3][5] = String.valueOf(shortDirect);
/* 2585 */     rowData[0][6] = String.valueOf(immob);
/* 2586 */     rowData[1][6] = String.valueOf("");
/* 2587 */     rowData[2][6] = String.valueOf("");
/* 2588 */     rowData[3][6] = String.valueOf("");
/*      */     
/* 2590 */     ResultsTable rtTrackSum = new ResultsTable();
/* 2591 */     for (int i = 0; i < rowData.length; i++) {
/* 2592 */       for (int c = 0; c < (rowData[i]).length; c++)
/* 2593 */         rtTrackSum.setValue((SPTBatch_.null.access$8(SPTBatch_.null.this)).columnsMovements[c], i, rowData[i][c]); 
/*      */     } 
/*      */     try {
/* 2596 */       rtTrackSum.saveAs(String.valueOf(SPTBatch_.directSPT.getAbsolutePath()) + File.separator + 
/* 2597 */           SPTBatch_.imps.getShortTitle() + "_" + "SummaryTracks" + ".csv");
/* 2598 */     } catch (IOException e) {
/*      */       
/* 2600 */       e.printStackTrace();
/*      */     } 
/*      */ 
/*      */     
/* 2604 */     if (SPTBatch_.checkPBS.isSelected())
/* 2605 */       (new PhotobleachingSpotPlot()).Compute(); 
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/SPTBatch_$1$3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */