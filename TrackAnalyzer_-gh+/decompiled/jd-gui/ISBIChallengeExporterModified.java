/*     */ import fiji.plugin.trackmate.Logger;
/*     */ import fiji.plugin.trackmate.Model;
/*     */ import fiji.plugin.trackmate.SelectionModel;
/*     */ import fiji.plugin.trackmate.Settings;
/*     */ import fiji.plugin.trackmate.Spot;
/*     */ import fiji.plugin.trackmate.TrackMate;
/*     */ import fiji.plugin.trackmate.action.AbstractTMAction;
/*     */ import fiji.plugin.trackmate.gui.displaysettings.DisplaySettings;
/*     */ import fiji.plugin.trackmate.io.IOUtils;
/*     */ import java.awt.Frame;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jdom2.Content;
/*     */ import org.jdom2.Document;
/*     */ import org.jdom2.Element;
/*     */ import org.jdom2.output.Format;
/*     */ import org.jdom2.output.XMLOutputter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ISBIChallengeExporterModified
/*     */   extends AbstractTMAction
/*     */ {
/*     */   public static final String NAME = "Export to ISBI challenge format";
/*     */   public static final String KEY = "EXPORT_TO_ISBI_CHALLENGE_FORMAT";
/*     */   public static final String INFO_TEXT = "<html>Export the current model content to a XML file following the ISBI 2012 particle tracking challenge format, as specified on <a href='http://bioimageanalysis.org/track/'></a>. <p> Only tracks are exported. If there is no track, this action does nothing. </html>";
/*     */   private static final String CONTENT_KEY = "TrackContestISBI2012";
/*     */   private static final String DATE_ATT = "generationDateTime";
/*     */   private static final String SNR_ATT = "snr";
/*     */   private static final String DENSITY_ATT = "density";
/*     */   private static final String SCENARIO_ATT = "scenario";
/*     */   private static final String TRACK_KEY = "particle";
/*     */   private static final String SPOT_KEY = "detection";
/*     */   private static final String X_ATT = "x";
/*     */   private static final String Y_ATT = "y";
/*     */   private static final String Z_ATT = "z";
/*     */   private static final String T_ATT = "t";
/*     */   
/*     */   public void execute(TrackMate trackmate, SelectionModel selectionModel, DisplaySettings displaySettings, Frame parent) {
/*  48 */     Model model = trackmate.getModel();
/*     */     
/*  50 */     File folder = (new File(System.getProperty("user.dir"))).getParentFile().getParentFile();
/*     */     try {
/*  52 */       String filename = (trackmate.getSettings()).imageFileName;
/*  53 */       filename = filename.substring(0, filename.indexOf("."));
/*  54 */       file = new File(String.valueOf(folder.getPath()) + File.separator + filename + "_ISBI.xml");
/*  55 */     } catch (NullPointerException npe) {
/*  56 */       file = new File(String.valueOf(folder.getPath()) + File.separator + "ISBIChallenge2012Result.xml");
/*     */     } 
/*  58 */     File file = IOUtils.askForFileForSaving(file, parent, this.logger);
/*     */     
/*  60 */     exportToFile(model, trackmate.getSettings(), file, this.logger);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void exportToFile(Model model, Settings settings, File file) {
/*  65 */     exportToFile(model, settings, file, model.getLogger());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void exportToFile(Model model, Settings settings, File file, Logger logger) {
/*  70 */     SPTBatch_.taskOutput.append("Exporting to ISBI 2012 particle tracking challenge format.\n");
/*  71 */     int ntracks = model.getTrackModel().nTracks(true);
/*  72 */     if (ntracks == 0) {
/*  73 */       SPTBatch_.taskOutput.append("No visible track found. Aborting.\n");
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     SPTBatch_.taskOutput.append("  Preparing XML data.\n");
/*  78 */     Element root = marshall(model, settings);
/*     */     
/*  80 */     SPTBatch_.taskOutput.append("  Writing to file.\n");
/*  81 */     Document document = new Document(root);
/*  82 */     XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
/*     */     try {
/*  84 */       outputter.output(document, new FileOutputStream(file));
/*  85 */     } catch (FileNotFoundException e) {
/*  86 */       SPTBatch_.taskOutput.append("Trouble writing to " + file + ":\n" + e.getMessage());
/*  87 */     } catch (IOException e) {
/*  88 */       SPTBatch_.taskOutput.append("Trouble writing to " + file + ":\n" + e.getMessage());
/*     */     } 
/*  90 */     SPTBatch_.taskOutput.append("Done.\n");
/*     */   }
/*     */   private static final Element marshall(Model model, Settings settings) {
/*     */     String snr_val, density_val, scenario_val;
/*  94 */     Logger logger = model.getLogger();
/*     */     
/*  96 */     Element root = new Element("root");
/*  97 */     Element content = new Element("TrackContestISBI2012");
/*     */ 
/*     */     
/* 100 */     String filename = settings.imageFileName;
/* 101 */     String pattern = "^(\\w+) snr (\\d+) density (\\w+)\\.";
/* 102 */     Pattern r = Pattern.compile("^(\\w+) snr (\\d+) density (\\w+)\\.");
/* 103 */     Matcher m = r.matcher(filename);
/*     */ 
/*     */ 
/*     */     
/* 107 */     if (m.find()) {
/* 108 */       scenario_val = m.group(1);
/* 109 */       snr_val = m.group(2);
/* 110 */       density_val = m.group(3);
/*     */     } else {
/* 112 */       scenario_val = filename;
/* 113 */       snr_val = "?";
/* 114 */       density_val = "?";
/*     */     } 
/* 116 */     content.setAttribute("snr", snr_val);
/* 117 */     content.setAttribute("density", density_val);
/* 118 */     content.setAttribute("scenario", scenario_val);
/* 119 */     content.setAttribute("generationDateTime", (new Date()).toString());
/*     */     
/* 121 */     SPTBatch_.taskOutput.append("Marshalling...");
/* 122 */     Integer[] visibleTracks = (Integer[])model.getTrackModel().trackIDs(true).toArray((Object[])new Integer[0]);
/* 123 */     for (int i = 0; i < model.getTrackModel().nTracks(true); i++) {
/*     */       
/* 125 */       Element trackElement = new Element("particle");
/* 126 */       int trackindex = visibleTracks[i].intValue();
/* 127 */       Set<Spot> track = model.getTrackModel().trackSpots(Integer.valueOf(trackindex));
/*     */       
/* 129 */       TreeSet<Spot> sortedTrack = new TreeSet<>(Spot.timeComparator);
/* 130 */       sortedTrack.addAll(track);
/*     */       
/* 132 */       for (Spot spot : sortedTrack) {
/* 133 */         int t = spot.getFeature("FRAME").intValue();
/* 134 */         double x = spot.getFeature("POSITION_X").doubleValue();
/* 135 */         double y = spot.getFeature("POSITION_Y").doubleValue();
/* 136 */         double z = spot.getFeature("POSITION_Z").doubleValue();
/*     */         
/* 138 */         Element spotElement = new Element("detection");
/* 139 */         spotElement.setAttribute("t", t);
/* 140 */         spotElement.setAttribute("x", x);
/* 141 */         spotElement.setAttribute("y", y);
/* 142 */         spotElement.setAttribute("z", z);
/* 143 */         trackElement.addContent((Content)spotElement);
/*     */       } 
/* 145 */       content.addContent((Content)trackElement);
/* 146 */       SPTBatch_.taskOutput.append((new StringBuilder(String.valueOf(i / (0.0D + model.getTrackModel().nTracks(true))))).toString());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 151 */     root.addContent((Content)content);
/* 152 */     return root;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ISBIChallengeExporterModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */