// 
// Decompiled by Procyon v0.5.36
// 

package trajectory_classifier;

import java.io.Reader;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import vecmath.Point3dModified;
import java.io.Writer;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import traJ.TrajectoryModified;
import java.util.ArrayList;

public class ExportImportToolsModified
{
    public void exportTrajectoryDataAsCSV(final ArrayList<? extends TrajectoryModified> tracks, final String path) {
        String[] nextLine = null;
        try {
            final CSVWriter writer = new CSVWriter((Writer)new FileWriter(path, false));
            nextLine = new String[] { "ID", "X", "Y", "CLASS" };
            writer.writeNext(nextLine);
            for (int i = 0; i < tracks.size(); ++i) {
                final TrajectoryModified t = (TrajectoryModified)tracks.get(i);
                for (int j = 0; j < t.size(); ++j) {
                    nextLine = new String[] { new StringBuilder().append(t.getID()).toString(), new StringBuilder().append(t.get(j).x).toString(), new StringBuilder().append(t.get(j).y).toString(), t.getType() };
                    writer.writeNext(nextLine);
                }
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<TrajectoryModified> importTrajectoryDataFromCSV(final String path) {
        final ArrayList<TrajectoryModified> tracks = new ArrayList<TrajectoryModified>();
        try {
            final CSVReader reader = new CSVReader((Reader)new FileReader(path));
            reader.readNext();
            TrajectoryModified t = null;
            int lastID = -1;
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                final int nextID = Integer.parseInt(nextLine[0]);
                final double nextX = Double.parseDouble(nextLine[1]);
                final double nextY = Double.parseDouble(nextLine[2]);
                final String nextClass = nextLine[3];
                if (nextID == lastID) {
                    System.out.println();
                    t.add(nextX, nextY, 0.0);
                    lastID = nextID;
                }
                else {
                    if (t != null) {
                        tracks.add(t);
                    }
                    t = new TrajectoryModified(2);
                    t.setID(nextID);
                    t.setType(nextClass);
                    t.add(nextX, nextY, 0.0);
                    lastID = nextID;
                }
            }
            tracks.add(t);
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
