package trajectory_classifier;

import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.gui.TextRoi;
import ij.process.FloatPolygon;
import java.awt.Color;
import java.util.ArrayList;
import vecmath.Point3dModified;

public class VisualizationUtilsModified {
   public static ArrayList<Roi> generateVisualizationRoisFromTrack(SubtrajectoryModified t, Color c, boolean showID) {
      return generateVisualizationRoisFromTrack(t, c, showID, 1.0D);
   }

   public static ArrayList<Roi> generateVisualizationRoisFromTrack(SubtrajectoryModified t, Color c, boolean showID, double pixelsize) {
      ArrayList<Roi> proi = new ArrayList();
      FloatPolygon p = new FloatPolygon();
      double sumx = 0.0D;
      double sumy = 0.0D;
      TextRoi.setFont("TimesRoman", 5, 0);

      for(int i = 0; i < t.getParent().size(); ++i) {
         int to = t.size();
         if (i < t.size()) {
            sumx += ((Point3dModified)t.get(i)).x / pixelsize;
            sumy += ((Point3dModified)t.get(i)).y / pixelsize;
            p.addPoint(((Point3dModified)t.get(i)).x / pixelsize, ((Point3dModified)t.get(i)).y / pixelsize);
            to = i + 1;
         }

         PolygonRoi pr = new PolygonRoi(new FloatPolygon(p.xpoints, p.ypoints, to), 6);
         pr.setStrokeColor(c);
         pr.setPosition(t.getRelativeStartTimepoint() + i + 1);
         proi.add(pr);
         if (showID) {
            long parentID = t.getParent().getID();
            TextRoi troi = new TextRoi(sumx / (double)to, sumy / (double)to, " " + parentID + ":" + t.getID() + " ");
            troi.setPosition(t.getRelativeStartTimepoint() + i + 1);
            troi.setFillColor(Color.BLACK);
            troi.setStrokeColor(c);
            troi.setAntialiased(true);
            proi.add(troi);
         }
      }

      return proi;
   }
}
