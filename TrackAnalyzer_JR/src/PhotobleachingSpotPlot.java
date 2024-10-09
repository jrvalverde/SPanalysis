import fiji.plugin.trackmate.gui.displaysettings.Colormap;
import fiji.plugin.trackmate.visualization.PerTrackFeatureColorGenerator;
import ij.ImagePlus;
import ij.gui.OvalRoi;
import ij.measure.ResultsTable;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class PhotobleachingSpotPlot {
   JFreeChart chart;
   JPanel chartpanel = new JPanel();
   ResultsTable rt;
   // Rectangle2D chartRectangleInSequence = new Rectangle2D.Float(250, 20 + 260 * 0, 490, 240);
   Rectangle2D chartRectangleInSequence = new Float(250.0F, 20.0F, 490.0F, 240.0F);
   JLabel outLabel = new JLabel();

   // Create new plot with predefined measures
   public PhotobleachingSpotPlot() {
      this.chartpanel.add(new ChartPanel(this.chart, 
      					500, 300, 500, 300, 500, 300, 
					false, false, true, true, true, true));
   }
   
   
   public void Compute() {
      // for (int j = ((int) minTracksJTF); j < ((int) maxTracksJTF); j++);
      Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
      List<Integer> nOfTracks = new ArrayList<Integer>();
      
      // if checkboc substract background was selected
      // then do the photobleaching plots, otherwise return
      if (SPTBatch_.checkboxSubBg.isSelected()) {
         // for all rows in the track table (which is a JTable)
	 //	A JTable stores Objects for display as a Swing 2D table
	 //	JTable.getValueAt() returns the object stored at (row,col)
	 //	As it is a generic store, in principle, we cannot be
	 //	sure what object we will get, which is why we try to
	 //	ensure it is by first converting it to a string (all
	 //	objects in the table must support it for them to be
	 //	displayable) and then making that string into a number
	 //	A JTable is derived from a Model: the Model stores the
	 // data in its own way, and so the JTable does not impose an
	 // storage structure, as long as it can access the date in the
	 // model (for this the model must implement either DefaultTableModel
	 // (a vector of vectors of object) or the Table Model interface
	 // (which allows directly passing the data to a JTable and is
	 // more efficient), or an AbstractTableModel (which is better
	 // to define a base class for creating subclasses of the model).
	 // 	Elements in a model are accessed with getCoumnCount(),
	 // getRowCount() and getValueAt() (which must be defined in
	 // the model implementation of the chosen interface).
	 //
	 // See: https://docs.oracle.com/javase/6/docs/api/javax/swing/JTable.html
	 //
	 //  It is important to remember that the column and row indexes
	 // returned by various JTable methods are in terms of the JTable (the view) and
	 // are not necessarily the same indexes used by the model it was constructed from:
	 // the JTable may implement its own getValueAt() that returns whichever values
	 // in whichever form it choses.
	 //
	 // trackJTable = chartx.getTable()	// the JTable associated to chartx
	 // chartx is a TrackTable (a TrackMate TablePanel) created from a SPTBatch.model 
	 // and a choice of display settings (ds) 
	 // chartx = SPTBatch_.createTrackTable(model, ds);
	 //  createTrackTable creates a TrackTable mapping  trackIDs from the model (x)
	 // to its features from the model (y) and associates to each track a color 
	 // based on ds
	 //  A TrackMate TablePanel associates the objects supplied with the
	 // feature names allowing to index numerically object properties by
	 // their index in the features list
	 //
	 // for all tracks in the track table
	 int idxTrackIndex = 1;		// XXX JR XXX
	 int idxTrackID = 2;		// XXX JR XXX
	 // xxx DBG
	 System.out.println("SPTBatch_.trackJTable.getRowCount() = " 	// XXX JR XXX
	                   + PTBatch_.trackJTable.getRowCount());	// XXX JR XXX
         for (int r = 0; r < SPTBatch_.trackJTable.getRowCount(); ++r) {
	    // get value at row r, column (feature) 2 as an integer 
	    // in the FeatureModel, getTrackFeature returns the features
	    // in features/track/TrackIndexAnalyzer.java, which are
	    //	   feature	name		shortna	dimens	isInt
	    // 	1: TRACK_INDEX	Track Index	Index	none	true
	    //	2: TRACK_ID	Track ID	ID	none	true
	    // so here we are retrieving TRACK_ID for each row (each track)
	    // and therefore, nOfTracks contains the ID number of each of
	    // the tracks
	    // JR NOTE: wouldn't a different name be more explanatory?
	    
//          nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(r, 2).toString()));
            nOfTracks.add(Integer.valueOf(SPTBatch_.trackJTable.getValueAt(r, idxTrackID).toString()));
         }
	 
	 // for all the trackIDs in nOfTracks, which must be
	 // the same number as in SPTBatch_.trackJTable.getRowCount()
	 // 	JR NOTE: wouldn't a single variable be more readable?
	 // r stands for row in the vector of vectors table
	 //	JR NOTE: wouldn't trackID be a better variable name?
	 // xxx DBG
	 System.out.println("nOfTracks.size = " + nOfTracks.size());	// XXX JR XXX
         for (int r = 0; r < nOfTracks.size(); ++r) {
	    // prepare new series of trajectory spot values
	    // JR NOTE: these names are misleading: they are, for ech track, the...
            List<Double> perTrack =        new ArrayList<Double>();	// mean intensitty
            List<Double> perFrame =        new ArrayList<Double>();	// frame
            List<Double> perTrackDef =     new ArrayList<Double>();	// intensity
            List<Double> perXPositionDef = new ArrayList<Double>();	// Xpos
            List<Double> perYPositionDef = new ArrayList<Double>();	// Ypos
            List<Double> perFrameDef =     new ArrayList<Double>();	// frame
	    // ...perSpot, so a name like trackSpotsXXX would be better
	    // and then these variables would contain that value for all spots in 
	    // the current track
	    
	    
	    // go over the spot table (for all spots)
	    // for each track, we will loop over all spots
	    // and if the spot is in the track, add its intemsity, frame, X and Y
	    // to the corresponding list
	    // tableSpot is a JTable(tableModel) where 
	    //			tableModel is a DefaultTableModel(rowDataSpot, SPTBatch_.columnNamesSpot)
	    //			(a vector of vectors)
	    //           or a JTable(rowDataSpot, SPTBatch_.columnNamesSpot)
	    // most likely in the (lost) original code, it was uniformly defined, but alas
	    // that code is lost and we only have disassembled code to work with
	    // column names in columnNamesSpot are
	    // 0        1     2           3          4             5             6             7             8        9         10            11                   12                    13                      14                   15                   16                     17                   18              19          20
	    // "LABEL", "ID", "TRACK_ID", "QUALITY", "POSITION_X", "POSITION_Y", "POSITION_Z", "POSITION_T", "FRAME", "RADIUS", "VISIBILITY", "MANUAL_SPOT_COLOR", "MEAN_INTENSITY_CH1", "MEDIAN_INTENSITY_CH1", "MIN_INTENSITY_CH1", "MAX_INTENSITY_CH1", "TOTAL_INTENSITY_CH1", "STD_INTENSITY_CH1", "CONTRAST_CH1", "SNR_CH1", "Intensity-Bg Subtract"}
            // JTable allows easy integration in a Swing panel, but all data items
	    // are treated as String independently of how they were defined, thus
	    // complicating access
	    int idxSpotLabel = 0;			// XXX JR XXX
	    int idxSpotID = 1;				// XXX JR XXX
	    int idxSpotTrackID = 2;			// XXX JR XXX
	    int idxSpotQuality = 3;			// XXX JR XXX
	    int idxSpotPosX = 4;			// XXX JR XXX
	    int idxSpotPosY = 5;			// XXX JR XXX
	    int idxSpotPosZ = 6;			// XXX JR XXX
	    int idxSpotFrame = 8;			// XXX JR XXX
	    int idxSpotMeanIntens1 = 12;		// XXX JR XXX
	    
	    // for each spot
	    // xxx DBG
	    System.out.println("  SPTBatch_.tableSpot.getRowCount = " 		// XXX JR XXX
	    			+ SPTBatch_.tableSpot.getRowCount());		// XXX JR XXX
	    for (int i = 0; i < SPTBatch_.tableSpot.getRowCount(); ++i) {
	       // if this spot's trackID matches the tid of the current track
//	       if (Integer.valueOf(SPTBatch_.tableSpot.getModel().getValueAt(t, 2).toString()).equals(nOfTracks.get(r)) == Boolean.TRUE) {
	       Integer s_tid;	// spot track ID
	       s_tid = Integer.valueOf(
	       		SPTBatch_.tableSpot.getModel().getValueAt(i, idxSpotTrackID).toString());
               if (s_tid.equals(nOfTracks.get(r)) == Boolean.TRUE) {
	       	  // get relevant values
	          // mean intensity of channel 1 is at spot.model[i, 12]
	          Double mean_intensity_ch1 = Double.valueOf(
		  	SPTBatch_.tableSpot.getModel().getValueAt(i, idxSpotMeanIntens1).toString());
                  // frame is spot.model[i,8]	FRAME
		  Double frame = Double.valueOf(
		  	SPTBatch_.tableSpot.getModel().getValueAt(i, idxSpotFrame).toString());
                  // Xposdef is spot.model[i, 4]	POSITION_X
		  Double position_x = Double.valueOf(
		  	SPTBatch_.tableSpot.getModel().getValueAt(i, idxSpotPosX).toString());
                  // Yposdef is spot.model[i , 5]	POSITION_Y
		  Double position_y = Double.valueOf(
		  	SPTBatch_.tableSpot.getModel().getValueAt(i, idxSpotPosY).toString());
			
		  // add the values to their corresponding lists
		  // add to perTrack spot.model[i,12]	MEAN_INTENSITY_CH1
		  // why do we have two versions??? Why "Def"
		  perTrack.add(mean_intensity_ch1);
		  perFrame.add(frame);
//                if (SPTBatch_.checkTracks.isSelected() == Boolean.FALSE) {
                  // add to perTrackDef spot.model[i, 12]	MEAN_INTENSITY_CH1 (same as track)
                  perTrackDef.add(mean_intensity_ch1);
		  perFrameDef.add(frame);
                  perXPositionDef.add(position_x);
		  perYPositionDef.add(position_y);
//                }
               }
            }
	    // we have collected all spots
	    
/*
	    if (SPTBatch_.checkTracks.isSelected() == Boolean.TRUE) {

	    for (int j = ((int) SPTBatch_.minTracksJTF); j < ((int) SPTBatch_.maxTracksJTF); j++) {

		perTrackDef.add(perTrack.get(j));
		perFrameDef.add(perFrame.get(j));

	    }
	    }
*/
	    // Now that we have built the perSpot lists for this table,
	    // we can build the ImageJ ResultsTable with help from JFreeChart
	    // define required variables
            this.rt = new ResultsTable();
            XYSeriesCollection xyDataset = new XYSeriesCollection();
            XYSeries seriesTrack = null;
            XYSeries seriesBg = null;
//	    seriesTrackList = new ArrayList<Double>();
//	    seriesBgList = new ArrayList<Double>();
	    // Constructor used is XYSeries(Comparable key)	key = the series key
            seriesTrack = new XYSeries("Spot Mean Raw Intensity Track ");
            seriesBg = new XYSeries("Bg Intensity");
/*
	      if (perFrameDef.get(0).intValue() != 0) {
		      for (int i = 0; i < perFrameDef.get(0); i++)
			      series.add(i, Double.valueOf(SPTBatch_.slicesIntensitySpot[i]));
	      }
*/

	    // for all spot intensities (as many as spots?)
            // xxx DBG
	    System.out.println("  perTrackDef.size = " 				// XXX JR XXX
	                        + perTrackDef.size());				// XXX JR XXX
	    for(int i = 0; i < perTrackDef.size(); ++i) {
	       // XXX JR XXX							XXX JR XXX
	       // XXX JR XXX	There is a potential inconsistency here!!!	XXX JR XXX
	       // XXX JR XXX							XXX JR XXX
	       // add the frame (as x) and intensity (as y) to seriesTrack (why as Number?)
	       // also, what happens if there are any frame jumps?
	       // JR NOTE: DUMP data to check.
	       //	here we get the frame number of element i in perFrameDef
	       //	and associate it to intensity of elemen i
               seriesTrack.add((Number)perFrameDef.get(i), (Number)perTrackDef.get(i));
	       // add the spot intensity to the report table
	       // now, this should nor need to be so verbose, as we have already read 
	       // it as double in a list of doubles (why is this done here and not above???)
	       // and why do we use i and not perFrameDef.get(i)?
	       // 	here we only get intensity of element i and associate it to
	       //	the element number, not to the frame that corresponds to that
	       //	element
	       // Why is not the same data set used for both, chart and report?
	       this.rt.setValue("Spot Mean Raw Intensity Track ", 	    // column
	                        i, 					    // row
				(Double)perTrackDef.get(i).doubleValue());  // value
               // XXX JR XXX try this:
	       // Double spotFrame = perFrameDef(i);
	       // Double spotIntensity = perTrackDef.get(i);
	       // seriesTrack.add(spotFrame, spotIntensity)
	       // rt.setValue("Spot Mean Raw Intensity Track ", i, spotIntensity);
	       // and compare with
	       // rt.setValue("Spot Mean Raw Intensity Track ", spotFrame, spotIntensity);
	       System.out.println("    #" + i +
	                          "Frame#" + i + " = " + perFrameDef.get(i) +
				  "Inten#" + i + " = " + perTrackDef.get(i));
	    }

	    // for all images in the stack of imageplus images
	    // xxx DBG
	    System.out.println("  SPTBatch_.imps.getStack().getSize = "		// XXX JR XXX
	                        + SPTBatch_.imps.getStack().getSize());		// XXX JR XXX
            for(int i = 0; i < SPTBatch_.imps.getStack().getSize(); ++i) {
	       // add the sliceIntensitySpot for this image to seriesBg, why not as Number?
	       seriesBg.add( (double)i, SPTBatch_.slicesIntensitySpot[i] );
	       // and to the report table
               this.rt.setValue("Bg Intensity", i, Double.valueOf(SPTBatch_.slicesIntensitySpot[i]));
            }
	    
	    
	    // now, check if the spot has been detected until the end of the
	    // experiment; if it has disappeared (the track ends) before the
	    // last frame, then get its last position and complete the intensities
	    // with the mean intensities observed in subsequent frames at the 
	    // last-seen XY position
	    //
	    // JR NOTE: This has a potential problem: if another spot enters
	    // that last position later, its intensity will be included here
	    // which might eventually become an issue in overcrowded samples
	    //
	    // I understand the goal is to record the subsequent intensities
	    // at that last-seen point, but does this make sense? wouldn't
	    // it be better to just use the overall frame background? the
	    // spot/particle might have gone out-of-focus, degraded, or just
	    // photobleached and become undetectable but continued moving,
	    // so the last-seen position would not represent any subsequent
	    // assignable intensity
	    //
	    
//	    if (((Double)perFrameDef.get(perFrameDef.size() - 1)).intValue() != SPTBatch_.imps.getStack().getSize() - 1) {
            // get the frame_number of the last frame for this spot
	    int last_frame_no = ((Double)perFrameDef.get(perFrameDef.size())).intValue();
	    // get the number of images (frames) in the stack
	    int img_stack_size = SPTBatch_.imps.getStack().getSize();
	    // if they are not the same, this spot does not reach the end
	    // xxx DBG
	    System.out.println("  perFrameDef.get(perFrameDef.size()) = "		// XXX JR XXX
	                        + perFrameDef.get(perFrameDef.size()));			// XXX JR XXX
	    System.out.println("  perFrameDef.get(perFrameDef.size()).intValue() = "	// XXX JR XXX
	                        + perFrameDef.get(perFrameDef.size()).intValue());	// XXX JR XXX
            if ( last_frame_no != img_stack_size ) {
	       // make a duplicate of the stack images
	       ImagePlus[] slices = SPTBatch_.stack2images(SPTBatch_.imps.duplicate());
	       // define an ovoidal ROI 
               OvalRoi ovalRoi = new OvalRoi((Double)perXPositionDef.get(perXPositionDef.size() - 1), 
	       				     (Double)perYPositionDef.get(perYPositionDef.size() - 1), 
					     Double.valueOf(SPTBatch_.RADIUS) / 
					     	SPTBatch_.imps.getCalibration().pixelWidth, 
					     Double.valueOf(SPTBatch_.RADIUS) / 
					     	SPTBatch_.imps.getCalibration().pixelWidth);
							
               // for all frames from the last one of this spot
               for (int i = ((Double)perFrameDef.get(perFrameDef.size() - 1)).intValue(); 
	            // to the end of the stack images
	            i < SPTBatch_.imps.getStack().getSize(); 
		    ++i) {
                  slices[i].setRoi(ovalRoi);
		  // add to the track the mean intensity at the last spot position
		  // in this frame
                  seriesTrack.add((double)i, slices[i].getStatistics().mean);
		  // and to the report as well
                  this.rt.setValue("Spot Mean Raw Intensity Track ", 
		                   i, 
		  		   slices[i].getStatistics().mean);
               }
            }
	    // at this point we have all per-spot intensities for this track till the end of time

	    // we add the spot intensities and backgrounds series to the dataset
            xyDataset.addSeries(seriesTrack);
            xyDataset.addSeries(seriesBg);
            this.chart = ChartFactory.createXYLineChart("PhotoBleaching step for Track " + nOfTracks.get(r), 
	                                                "Frame", 	// x axis label
							"Intensity", 	// y axis label
							xyDataset, 	// data
							PlotOrientation.VERTICAL, 
							true, 		// include legend
							true, 		// tooltips
							false);		// urls

	    // if the chart was created successfully, 
	    // set the colors and plot xyDataset
            if (this.chart != null) {
	       //  replace default chart colors by detection colors (taken from t=0)
               XYItemRenderer renderer = ((XYPlot)this.chart.getPlot()).getRenderer();

	       PerTrackFeatureColorGenerator tcg = new PerTrackFeatureColorGenerator(SPTBatch_.model, 
	                                                                             TrackIndexAnalyzer.TRACK_INDEX,, 
										     (Color)null, 
										     (Color)null, 
										     Colormap.Turbo, 
										     0.0D, 
										     1.0D);

               XYPlot plot = this.chart.getXYPlot();	// get the plot of the chart as an XY plot
//		final NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
//		xAxis.setTickUnit(new NumberTickUnit(1));
               plot.setDataset(0, xyDataset);
               plot.setRenderer(0, renderer);
               plot.getRendererForDataset(
	       		 plot.getDataset(0)).setSeriesPaint(0, tcg.colorOf((Integer)nOfTracks.get(r)));
            }

	    // make plot be the plot of the chart 
	    // XXX JR XXX (even if chart is null???)
            XYPlot plot = (XYPlot)this.chart.getPlot();
	    plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinesVisible(true);
            plot.setRangeGridlinesVisible(true);
            plot.setDomainMinorGridlinePaint(Color.GRAY);
            plot.setDomainGridlinePaint(Color.DARK_GRAY);
            plot.setRangeMinorGridlinePaint(Color.GRAY);
            plot.setRangeGridlinePaint(Color.DARK_GRAY);
	    
	    // clean the panel, add the chart to the chartpanel and draw it
            this.chartpanel.removeAll(); // clean the panel before we draw a new chart
	    this.chartpanel.add(new ChartPanel(this.chart));
            this.chartpanel.updateUI();

            // and also save the chart as PNG
	    try {
               ChartUtils.saveChartAsPNG(new File(SPTBatch_.directPBS.getAbsolutePath() + 
	                                          File.separator + 
						  SPTBatch_.imgTitle + 
						  "_" + 
						  ((Integer)nOfTracks.get(r)).toString() + 
						  ".png"), 
				         this.chart, 
					 2000, 
					 400);
            } catch (IOException ex) {
               ex.printStackTrace();
            }

            // save report table
	    try {
               this.rt.saveAs(SPTBatch_.directPBS.getAbsolutePath() + 
	                      File.separator + 
			      SPTBatch_.imgTitle + 
			      "_" + 
			      ((Integer)SPTBatch_.nOfTracks.get(r)).toString() + 
			      ".csv");
            } catch (IOException ex) {
               ex.printStackTrace();
            }
	    // IJ.log(seriesTrackList.size() + "------" + seriesBgList.size());
         }	// for each track (nOfTracks.size())
      }		// if (SPTBatch_.checkboxSubBg.isSelected()) {
   }		// Compute()

   public void keyPressed(Point p, KeyEvent e) {
   }

   public void mouseClick(Point p, MouseEvent e) {
   }

   public void mouseDrag(Point p, MouseEvent e) {
   }

   public void mouseMove(Point p, MouseEvent e) {
   }
}
