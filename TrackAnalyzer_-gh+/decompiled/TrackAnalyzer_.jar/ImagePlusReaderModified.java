import ij.ImagePlus;
import ij.ImageStack;
import ij.io.FileInfo;
import ij.process.ImageProcessor;
import ij.process.LUT;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import loci.common.Location;
import loci.common.Region;
import loci.common.StatusEvent;
import loci.common.StatusListener;
import loci.common.StatusReporter;
import loci.common.services.DependencyException;
import loci.common.services.ServiceException;
import loci.common.services.ServiceFactory;
import loci.formats.FilePattern;
import loci.formats.FormatException;
import loci.formats.IFormatReader;
import loci.formats.cache.CacheException;
import loci.formats.meta.IMetadata;
import loci.formats.services.OMEXMLService;
import loci.plugins.Slicer;
import loci.plugins.in.Calibrator;
import loci.plugins.in.Colorizer;
import loci.plugins.in.Concatenator;
import loci.plugins.in.ImportProcess;
import loci.plugins.in.ImporterOptions;
import loci.plugins.util.BFVirtualStack;
import loci.plugins.util.ImageProcessorReader;
import loci.plugins.util.LuraWave;
import loci.plugins.util.VirtualImagePlus;

public class ImagePlusReaderModified implements StatusReporter {
   public static final String PROP_SERIES = "Series";
   public static final String PROP_LUT = "LUT-";
   public ImportProcess process;
   public List<StatusListener> listeners = new Vector();
   public long startTime;
   public long time;

   public ImagePlusReaderModified(ImportProcess process) {
      this.process = process;
   }

   public ImagePlus[] openImagePlus() {
      List<ImagePlus> imps = this.readImages();
      return (ImagePlus[])imps.toArray(new ImagePlus[0]);
   }

   public ImagePlus[] openThumbImagePlus() {
      List<ImagePlus> imps = this.readThumbImages();
      return (ImagePlus[])imps.toArray(new ImagePlus[imps.size()]);
   }

   public void addStatusListener(StatusListener l) {
      this.listeners.add(l);
   }

   public void removeStatusListener(StatusListener l) {
      this.listeners.remove(l);
   }

   public void notifyListeners(StatusEvent e) {
      Iterator var3 = this.listeners.iterator();

      while(var3.hasNext()) {
         StatusListener l = (StatusListener)var3.next();
         l.statusUpdated(e);
      }

   }

   public static ImagePlus createImage(String title, List<ImageProcessor> procs) {
      List<LUT> luts = new ArrayList();
      ImageStack stack = createStack(procs, (List)null, luts);
      return createImage(title, stack, luts);
   }

   public static ImagePlus createImage(String title, ImageStack stack, List<LUT> luts) {
      ImagePlus imp = new ImagePlus(title, stack);
      saveLUTs(imp, luts);
      return imp;
   }

   public static ImageStack createStack(List<ImageProcessor> procs, List<String> labels, List<LUT> luts) {
      if (procs != null && procs.size() != 0) {
         ImageProcessor ip0 = (ImageProcessor)procs.get(0);
         ImageStack stack = new ImageStack(ip0.getWidth(), ip0.getHeight());

         for(int i = 0; i < procs.size(); ++i) {
            ImageProcessor ip = (ImageProcessor)procs.get(i);
            String label = labels == null ? null : (String)labels.get(i);
            if (luts != null) {
               ColorModel cm = ip.getColorModel();
               if (cm instanceof LUT) {
                  LUT lut = (LUT)cm;
                  luts.add(lut);
                  ip.setColorModel(ip.getDefaultColorModel());
               } else {
                  luts.add((Object)null);
               }
            }

            stack.addSlice(label, ip);
         }

         return stack;
      } else {
         return null;
      }
   }

   public List<ImagePlus> readImages() {
      return this.readImages(false);
   }

   public List<ImagePlus> readThumbImages() {
      return this.readImages(true);
   }

   public List<ImagePlus> readImages(boolean thumbnail) {
      ImporterOptions options = this.process.getOptions();
      ImageProcessorReader reader = this.process.getReader();
      List<ImagePlus> imps = new ArrayList();
      this.startTiming();

      for(int s = 0; s < reader.getSeriesCount(); ++s) {
         if (options.isSeriesOn(s)) {
            ImagePlus imp = this.readImage(s, thumbnail);
            imps.add(imp);
         }
      }

      List<ImagePlus> imps = this.concatenate(imps);
      imps = this.applyColors(imps);
      imps = this.splitDims(imps);
      if (options.isVirtual()) {
         this.process.getVirtualReader().setRefCount(imps.size());
      }

      this.finishTiming();
      return imps;
   }

   public ImagePlus readImage(int s, boolean thumbnail) {
      ImporterOptions options = this.process.getOptions();
      int zCount = this.process.getZCount(s);
      int cCount = this.process.getCCount(s);
      int tCount = this.process.getTCount(s);
      List<LUT> luts = new ArrayList();
      ImageStack stack;
      if (options.isVirtual()) {
         stack = this.createVirtualStack(this.process, s, luts);
      } else {
         stack = this.readPlanes(this.process, s, luts, thumbnail);
      }

      this.notifyListeners(new StatusEvent(1, 1, "Creating image"));
      String seriesName = this.process.getOMEMetadata().getImageName(s);
      String file = this.process.getCurrentFile();
      IFormatReader reader = this.process.getReader();
      String title = this.constructImageTitle(reader, file, seriesName, options.isGroupFiles());
      Object imp;
      if (stack.isVirtual()) {
         VirtualImagePlus vip = new VirtualImagePlus(title, stack);
         vip.setReader(reader);
         imp = vip;
         saveLUTs(vip, luts);
      } else {
         imp = createImage(title, stack, luts);
      }

      String metadata = this.process.getOriginalMetadata().toString();
      ((ImagePlus)imp).setProperty("Info", metadata);
      ((ImagePlus)imp).setProperty("Series", s);
      FileInfo fi = this.createFileInfo();
      (new Calibrator(this.process)).applyCalibration((ImagePlus)imp);
      ((ImagePlus)imp).setFileInfo(fi);
      ((ImagePlus)imp).setDimensions(cCount, zCount, tCount);
      boolean hyper = !options.isViewStandard();
      ((ImagePlus)imp).setOpenAsHyperStack(hyper);
      return (ImagePlus)imp;
   }

   public ImageStack createVirtualStack(ImportProcess process, int s, List<LUT> luts) {
      ImporterOptions options = process.getOptions();
      ImageProcessorReader reader = process.getReader();
      reader.setSeries(s);
      int zCount = process.getZCount(s);
      int cCount = process.getCCount(s);
      int tCount = process.getTCount(s);
      IMetadata meta = process.getOMEMetadata();
      int imageCount = reader.getImageCount();
      BFVirtualStack virtualStack = null;

      try {
         virtualStack = new BFVirtualStack(options.getId(), reader, false, false, false);
      } catch (CacheException var18) {
         var18.printStackTrace();
      } catch (FormatException var19) {
         var19.printStackTrace();
      } catch (IOException var20) {
         var20.printStackTrace();
      }

      int c;
      for(c = 0; c < imageCount; ++c) {
         String label = this.constructSliceLabel(c, reader, meta, s, zCount, cCount, tCount);
         virtualStack.addSlice(label);
      }

      if (luts != null) {
         for(c = 0; c < cCount; ++c) {
            int index = reader.getIndex(0, c, 0);
            ImageProcessor ip = null;

            try {
               ip = reader.openProcessors(index)[0];
            } catch (IOException | FormatException var17) {
               var17.printStackTrace();
            }

            ColorModel cm = ip.getColorModel();
            LUT lut = cm instanceof LUT ? (LUT)cm : null;
            luts.add(lut);
         }
      }

      return virtualStack;
   }

   public ImageStack readPlanes(ImportProcess process, int s, List<LUT> luts, boolean thumbnail) {
      ImageProcessorReader reader = process.getReader();
      reader.setSeries(s);
      int zCount = process.getZCount(s);
      int cCount = process.getCCount(s);
      int tCount = process.getTCount(s);
      IMetadata meta = process.getOMEMetadata();
      boolean[] load = this.getPlanesToLoad(s);
      int current = 0;
      int total = 0;

      for(int j = 0; j < load.length; ++j) {
         if (load[j]) {
            ++total;
         }
      }

      List<ImageProcessor> procs = new ArrayList();
      List<String> labels = new ArrayList();
      Region region = process.getCropRegion(s);

      for(int i = 0; i < load.length; ++i) {
         if (load[i]) {
            this.updateTiming(s, current, current++, total);
            ImageProcessor[] p = this.readProcessors(process, i, region, thumbnail);
            String label = this.constructSliceLabel(i, reader, meta, s, zCount, cCount, tCount);
            ImageProcessor[] var22 = p;
            int var21 = p.length;

            for(int var20 = 0; var20 < var21; ++var20) {
               ImageProcessor ip = var22[var20];
               procs.add(ip);
               labels.add(label);
            }
         }
      }

      return createStack(procs, labels, luts);
   }

   public ImageProcessor[] readProcessors(ImportProcess process, int no, Region r, boolean thumbnail) {
      ImageProcessorReader reader = process.getReader();
      ImporterOptions options = process.getOptions();
      boolean first = true;

      for(int i = 0; i < 5; ++i) {
         String code = LuraWave.initLicenseCode();

         try {
            if (thumbnail) {
               try {
                  return reader.openThumbProcessors(no);
               } catch (IOException var12) {
                  var12.printStackTrace();
               }
            }

            try {
               return reader.openProcessors(no, r.x, r.y, r.width, r.height);
            } catch (IOException var11) {
               var11.printStackTrace();
            }
         } catch (FormatException var13) {
            LuraWave.promptLicenseCode(code, first);
            if (first) {
               first = false;
            }
         }
      }

      return null;
   }

   public List<ImagePlus> concatenate(List<ImagePlus> imps) {
      ImporterOptions options = this.process.getOptions();
      if (options.isConcatenate()) {
         imps = (new Concatenator()).concatenate(imps);
      }

      return imps;
   }

   public List<ImagePlus> applyColors(List<ImagePlus> imps) {
      return (new Colorizer(this.process)).applyColors(imps);
   }

   public List<ImagePlus> splitDims(List<ImagePlus> imps) {
      ImporterOptions options = this.process.getOptions();
      boolean sliceC = options.isSplitChannels();
      boolean sliceZ = options.isSplitFocalPlanes();
      boolean sliceT = options.isSplitTimepoints();
      if (sliceC || sliceZ || sliceT) {
         String stackOrder = this.process.getStackOrder();
         List<ImagePlus> slicedImps = new ArrayList();
         Slicer slicer = new Slicer();
         Iterator var10 = ((List)imps).iterator();

         while(var10.hasNext()) {
            ImagePlus imp = (ImagePlus)var10.next();
            ImagePlus[] results = slicer.reslice(imp, sliceC, sliceZ, sliceT, stackOrder);
            ImagePlus[] var15 = results;
            int var14 = results.length;

            for(int var13 = 0; var13 < var14; ++var13) {
               ImagePlus result = var15[var13];
               slicedImps.add(result);
            }
         }

         imps = slicedImps;
      }

      return (List)imps;
   }

   public void startTiming() {
      this.startTime = this.time = System.currentTimeMillis();
   }

   public void updateTiming(int s, int i, int current, int total) {
      ImageProcessorReader reader = this.process.getReader();
      long clock = System.currentTimeMillis();
      if (clock - this.time >= 100L) {
         String sLabel = reader.getSeriesCount() > 1 ? "series " + (s + 1) + ", " : "";
         String pLabel = "plane " + (i + 1) + "/" + total;
         this.notifyListeners(new StatusEvent("Reading " + sLabel + pLabel));
         this.time = clock;
      }

      this.notifyListeners(new StatusEvent(current, total, (String)null));
   }

   public void finishTiming() {
      ImageProcessorReader reader = this.process.getReader();
      long endTime = System.currentTimeMillis();
      double elapsed = (double)(endTime - this.startTime) / 1000.0D;
      if (reader.getImageCount() == 1) {
         this.notifyListeners(new StatusEvent("Bio-Formats: " + elapsed + " seconds"));
      } else {
         long average = (endTime - this.startTime) / (long)reader.getImageCount();
         this.notifyListeners(new StatusEvent("Bio-Formats: " + elapsed + " seconds (" + average + " ms per plane)"));
      }

   }

   public FileInfo createFileInfo() {
      FileInfo fi = new FileInfo();
      String idDir = this.process.getIdLocation() == null ? null : this.process.getIdLocation().getParent();
      if (idDir != null && !idDir.endsWith(File.separator)) {
         idDir = idDir + File.separator;
      }

      fi.fileName = this.process.getIdName();
      fi.directory = idDir;

      try {
         ServiceFactory factory = new ServiceFactory();
         OMEXMLService service = (OMEXMLService)factory.getInstance(OMEXMLService.class);
         fi.description = service.getOMEXML(this.process.getOMEMetadata());
      } catch (DependencyException var5) {
      } catch (ServiceException var6) {
      }

      return fi;
   }

   public boolean[] getPlanesToLoad(int s) {
      ImageProcessorReader reader = this.process.getReader();
      boolean[] load = new boolean[reader.getImageCount()];
      int cBegin = this.process.getCBegin(s);
      int cEnd = this.process.getCEnd(s);
      int cStep = this.process.getCStep(s);
      int zBegin = this.process.getZBegin(s);
      int zEnd = this.process.getZEnd(s);
      int zStep = this.process.getZStep(s);
      int tBegin = this.process.getTBegin(s);
      int tEnd = this.process.getTEnd(s);
      int tStep = this.process.getTStep(s);

      for(int c = cBegin; c <= cEnd; c += cStep) {
         for(int z = zBegin; z <= zEnd; z += zStep) {
            for(int t = tBegin; t <= tEnd; t += tStep) {
               int index = reader.getIndex(z, c, t);
               load[index] = true;
            }
         }
      }

      return load;
   }

   public String constructImageTitle(IFormatReader r, String file, String seriesName, boolean groupFiles) {
      String[] used = r.getUsedFiles();
      String title = file.substring(file.lastIndexOf(File.separator) + 1);
      if (used.length > 1 && groupFiles) {
         FilePattern fp = new FilePattern(new Location(file));
         title = fp.getPattern();
         if (title == null) {
            title = file;
            if (file.indexOf(".") != -1) {
               title = file.substring(0, file.lastIndexOf("."));
            }
         }

         title = title.substring(title.lastIndexOf(File.separator) + 1);
      }

      if (seriesName != null && !file.endsWith(seriesName) && r.getSeriesCount() > 1) {
         title = title + " - " + seriesName;
      }

      if (title.length() > 128) {
         String a = title.substring(0, 62);
         String b = title.substring(title.length() - 62);
         title = a + "..." + b;
      }

      return title;
   }

   public String constructSliceLabel(int ndx, ImageProcessorReader r, IMetadata meta, int series, int zCount, int cCount, int tCount) {
      r.setSeries(series);
      int[] zct = r.getZCTCoords(ndx);
      StringBuffer sb = new StringBuffer();
      boolean first = true;
      if (zCount > 1) {
         if (first) {
            first = false;
         } else {
            sb.append("; ");
         }

         sb.append("z:");
         sb.append(zct[0] + 1);
         sb.append("/");
         sb.append(r.getSizeZ());
      }

      if (tCount > 1) {
         if (first) {
            first = false;
         } else {
            sb.append("; ");
         }

         sb.append("t:");
         sb.append(zct[2] + 1);
         sb.append("/");
         sb.append(r.getSizeT());
      }

      String imageName = meta.getImageName(series);
      if (imageName != null && !imageName.trim().equals("")) {
         sb.append(" - ");
         sb.append(imageName);
      }

      return sb.toString();
   }

   public static void saveLUTs(ImagePlus imp, List<LUT> luts) {
      for(int i = 0; i < luts.size(); ++i) {
         LUT lut = (LUT)luts.get(i);
         if (lut != null) {
            imp.setProperty("LUT-" + i, lut);
         }
      }

   }
}
