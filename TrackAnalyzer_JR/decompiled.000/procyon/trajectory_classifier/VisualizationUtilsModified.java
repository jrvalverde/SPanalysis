// 
// Decompiled by Procyon v0.5.36
// 

package trajectory_classifier;

import ij.gui.PolygonRoi;
import vecmath.Point3dModified;
import ij.gui.TextRoi;
import ij.process.FloatPolygon;
import ij.gui.Roi;
import java.util.ArrayList;
import java.awt.Color;

public class VisualizationUtilsModified
{
    public static ArrayList<Roi> generateVisualizationRoisFromTrack(final SubtrajectoryModified t, final Color c, final boolean showID) {
        return generateVisualizationRoisFromTrack(t, c, showID, 1.0);
    }
    
    public static ArrayList<Roi> generateVisualizationRoisFromTrack(final SubtrajectoryModified t, final Color c, final boolean showID, final double pixelsize) {
        final ArrayList<Roi> proi = new ArrayList<Roi>();
        final FloatPolygon p = new FloatPolygon();
        double sumx = 0.0;
        double sumy = 0.0;
        TextRoi.setFont("TimesRoman", 5, 0);
        for (int i = 0; i < t.getParent().size(); ++i) {
            int to = t.size();
            if (i < t.size()) {
                sumx += t.get(i).x / pixelsize;
                sumy += t.get(i).y / pixelsize;
                p.addPoint(t.get(i).x / pixelsize, t.get(i).y / pixelsize);
                to = i + 1;
            }
            final PolygonRoi pr = new PolygonRoi(new FloatPolygon(p.xpoints, p.ypoints, to), 6);
            pr.setStrokeColor(c);
            pr.setPosition(t.getRelativeStartTimepoint() + i + 1);
            proi.add((Roi)pr);
            if (showID) {
                final long parentID = t.getParent().getID();
                final TextRoi troi = new TextRoi(sumx / to, sumy / to, " " + parentID + ":" + t.getID() + " ");
                troi.setPosition(t.getRelativeStartTimepoint() + i + 1);
                troi.setFillColor(Color.BLACK);
                troi.setStrokeColor(c);
                troi.setAntialiased(true);
                proi.add((Roi)troi);
            }
        }
        return proi;
    }
}
