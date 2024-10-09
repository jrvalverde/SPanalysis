import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import org.jdom2.Content;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;
import fiji.plugin.trackmate.Spot;
import java.util.Date;
import java.util.regex.Pattern;
import org.jdom2.Element;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;
import org.jdom2.Document;
import fiji.plugin.trackmate.Logger;
import fiji.plugin.trackmate.Settings;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.io.IOUtils;
import java.io.File;
import java.awt.Frame;
import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.action.AbstractTMAction;

// 
// Decompiled by Procyon v0.5.36
// 

public class ISBIChallengeExporterModified extends AbstractTMAction
{
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
    
    public void execute(final TrackMate trackmate, final SelectionModel selectionModel, final DisplaySettings displaySettings, final Frame parent) {
        final Model model = trackmate.getModel();
        final File folder = new File(System.getProperty("user.dir")).getParentFile().getParentFile();
        File file;
        try {
            String filename = trackmate.getSettings().imageFileName;
            filename = filename.substring(0, filename.indexOf("."));
            file = new File(String.valueOf(folder.getPath()) + File.separator + filename + "_ISBI.xml");
        }
        catch (NullPointerException npe) {
            file = new File(String.valueOf(folder.getPath()) + File.separator + "ISBIChallenge2012Result.xml");
        }
        file = IOUtils.askForFileForSaving(file, parent, this.logger);
        exportToFile(model, trackmate.getSettings(), file, this.logger);
    }
    
    public static void exportToFile(final Model model, final Settings settings, final File file) {
        exportToFile(model, settings, file, model.getLogger());
    }
    
    public static void exportToFile(final Model model, final Settings settings, final File file, final Logger logger) {
        SPTBatch_.taskOutput.append("Exporting to ISBI 2012 particle tracking challenge format.\n");
        final int ntracks = model.getTrackModel().nTracks(true);
        if (ntracks == 0) {
            SPTBatch_.taskOutput.append("No visible track found. Aborting.\n");
            return;
        }
        SPTBatch_.taskOutput.append("  Preparing XML data.\n");
        final Element root = marshall(model, settings);
        SPTBatch_.taskOutput.append("  Writing to file.\n");
        final Document document = new Document(root);
        final XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try {
            outputter.output(document, (OutputStream)new FileOutputStream(file));
        }
        catch (FileNotFoundException e) {
            SPTBatch_.taskOutput.append("Trouble writing to " + file + ":\n" + e.getMessage());
        }
        catch (IOException e2) {
            SPTBatch_.taskOutput.append("Trouble writing to " + file + ":\n" + e2.getMessage());
        }
        SPTBatch_.taskOutput.append("Done.\n");
    }
    
    private static final Element marshall(final Model model, final Settings settings) {
        final Logger logger = model.getLogger();
        final Element root = new Element("root");
        final Element content = new Element("TrackContestISBI2012");
        final String filename = settings.imageFileName;
        final String pattern = "^(\\w+) snr (\\d+) density (\\w+)\\.";
        final Pattern r = Pattern.compile("^(\\w+) snr (\\d+) density (\\w+)\\.");
        final Matcher m = r.matcher(filename);
        String scenario_val;
        String snr_val;
        String density_val;
        if (m.find()) {
            scenario_val = m.group(1);
            snr_val = m.group(2);
            density_val = m.group(3);
        }
        else {
            scenario_val = filename;
            snr_val = "?";
            density_val = "?";
        }
        content.setAttribute("snr", snr_val);
        content.setAttribute("density", density_val);
        content.setAttribute("scenario", scenario_val);
        content.setAttribute("generationDateTime", new Date().toString());
        SPTBatch_.taskOutput.append("Marshalling...");
        final Integer[] visibleTracks = model.getTrackModel().trackIDs(true).<Integer>toArray(new Integer[0]);
        for (int i = 0; i < model.getTrackModel().nTracks(true); ++i) {
            final Element trackElement = new Element("particle");
            final int trackindex = visibleTracks[i];
            final Set<Spot> track = (Set<Spot>)model.getTrackModel().trackSpots(Integer.valueOf(trackindex));
            final TreeSet<Spot> sortedTrack = new TreeSet<Spot>(Spot.timeComparator);
            sortedTrack.addAll(track);
            for (final Spot spot : sortedTrack) {
                final int t = spot.getFeature("FRAME").intValue();
                final double x = spot.getFeature("POSITION_X");
                final double y = spot.getFeature("POSITION_Y");
                final double z = spot.getFeature("POSITION_Z");
                final Element spotElement = new Element("detection");
                spotElement.setAttribute("t", new StringBuilder().append(t).toString());
                spotElement.setAttribute("x", new StringBuilder().append(x).toString());
                spotElement.setAttribute("y", new StringBuilder().append(y).toString());
                spotElement.setAttribute("z", new StringBuilder().append(z).toString());
                trackElement.addContent((Content)spotElement);
            }
            content.addContent((Content)trackElement);
            SPTBatch_.taskOutput.append(new StringBuilder(String.valueOf(i / (0.0 + model.getTrackModel().nTracks(true)))).toString());
        }
        root.addContent((Content)content);
        return root;
    }
}
