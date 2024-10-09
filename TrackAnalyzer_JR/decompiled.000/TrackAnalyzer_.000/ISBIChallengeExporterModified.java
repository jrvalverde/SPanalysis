import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.action.AbstractTMAction;
import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings;
import fiji.plugin.trackmate.io.IOUtils;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ISBIChallengeExporterModified extends AbstractTMAction {
   public static final String NAME = "Export to ISBI challenge format";
   public static final String KEY = "EXPORT_TO_ISBI_CHALLENGE_FORMAT";
   public static final String INFO_TEXT = "<html>Export the current model content to a XML file following the ISBI 2012 particle tracking challenge format, as specified on <a href='http://bioimageanalysis.org/track/'></a>. <p> Only tracks are exported. If there is no track, this action does nothing. </html>";
   private static final String CONTENT_KEY = "TrackContestISBI2012";
   private static final String DATE_ATT = "generationDateTime";
   private static final String SNR_ATT = "snr";
   private static final String DENSITY_ATT = "density";
   private static final String SCENARIO_ATT = "scenario";
   private static final String TRACK_KEY = "particle";
   private static final String SPOT_KEY = "detection";
   private static final String X_ATT = "x";
   private static final String Y_ATT = "y";
   private static final String Z_ATT = "z";
   private static final String T_ATT = "t";

   public void execute(TrackMate trackmate, SelectionModel selectionModel, DisplaySettings displaySettings, Frame parent) {
      Model model = trackmate.getModel();
      File folder = (new File(System.getProperty("user.dir"))).getParentFile().getParentFile();

      File file;
      try {
         String filename = trackmate.getSettings().imageFileName;
         filename = filename.substring(0, filename.indexOf("."));
         file = new File(folder.getPath() + File.separator + filename + "_ISBI.xml");
      } catch (NullPointerException var9) {
         file = new File(folder.getPath() + File.separator + "ISBIChallenge2012Result.xml");
      }

      file = IOUtils.askForFileForSaving(file, parent, this.logger);
      exportToFile(model, trackmate.getSettings(), file, this.logger);
   }

   public static void exportToFile(Model model, Settings settings, File file) {
      exportToFile(model, settings, file, model.getLogger());
   }

   public static void exportToFile(Model model, Settings settings, File file, Logger logger) {
      SPTBatch_.taskOutput.append("Exporting to ISBI 2012 particle tracking challenge format.\n");
      int ntracks = model.getTrackModel().nTracks(true);
      if (ntracks == 0) {
         SPTBatch_.taskOutput.append("No visible track found. Aborting.\n");
      } else {
         SPTBatch_.taskOutput.append("  Preparing XML data.\n");
         Element root = marshall(model, settings);
         SPTBatch_.taskOutput.append("  Writing to file.\n");
         Document document = new Document(root);
         XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

         try {
            outputter.output(document, new FileOutputStream(file));
         } catch (FileNotFoundException var9) {
            SPTBatch_.taskOutput.append("Trouble writing to " + file + ":\n" + var9.getMessage());
         } catch (IOException var10) {
            SPTBatch_.taskOutput.append("Trouble writing to " + file + ":\n" + var10.getMessage());
         }

         SPTBatch_.taskOutput.append("Done.\n");
      }
   }

   private static final Element marshall(Model model, Settings settings) {
      Logger logger = model.getLogger();
      Element root = new Element("root");
      Element content = new Element("TrackContestISBI2012");
      String filename = settings.imageFileName;
      String pattern = "^(\\w+) snr (\\d+) density (\\w+)\\.";
      Pattern r = Pattern.compile("^(\\w+) snr (\\d+) density (\\w+)\\.");
      Matcher m = r.matcher(filename);
      String snr_val;
      String density_val;
      String scenario_val;
      if (m.find()) {
         scenario_val = m.group(1);
         snr_val = m.group(2);
         density_val = m.group(3);
      } else {
         scenario_val = filename;
         snr_val = "?";
         density_val = "?";
      }

      content.setAttribute("snr", snr_val);
      content.setAttribute("density", density_val);
      content.setAttribute("scenario", scenario_val);
      content.setAttribute("generationDateTime", (new Date()).toString());
      SPTBatch_.taskOutput.append("Marshalling...");
      Integer[] visibleTracks = (Integer[])model.getTrackModel().trackIDs(true).toArray(new Integer[0]);

      for(int i = 0; i < model.getTrackModel().nTracks(true); ++i) {
         Element trackElement = new Element("particle");
         int trackindex = visibleTracks[i];
         Set<Spot> track = model.getTrackModel().trackSpots(trackindex);
         TreeSet<Spot> sortedTrack = new TreeSet(Spot.timeComparator);
         sortedTrack.addAll(track);
         Iterator var19 = sortedTrack.iterator();

         while(var19.hasNext()) {
            Spot spot = (Spot)var19.next();
            int t = spot.getFeature("FRAME").intValue();
            double x = spot.getFeature("POSITION_X");
            double y = spot.getFeature("POSITION_Y");
            double z = spot.getFeature("POSITION_Z");
            Element spotElement = new Element("detection");
            spotElement.setAttribute("t", "" + t);
            spotElement.setAttribute("x", "" + x);
            spotElement.setAttribute("y", "" + y);
            spotElement.setAttribute("z", "" + z);
            trackElement.addContent(spotElement);
         }

         content.addContent(trackElement);
         SPTBatch_.taskOutput.append(String.valueOf((double)i / (0.0D + (double)model.getTrackModel().nTracks(true))));
      }

      root.addContent(content);
      return root;
   }
}
