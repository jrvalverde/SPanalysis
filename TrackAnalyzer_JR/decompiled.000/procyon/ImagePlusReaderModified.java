import loci.formats.FilePattern;
import loci.common.Location;
import loci.common.services.ServiceException;
import loci.common.services.DependencyException;
import loci.formats.meta.MetadataRetrieve;
import loci.formats.services.OMEXMLService;
import loci.common.services.ServiceFactory;
import java.io.File;
import loci.plugins.Slicer;
import loci.plugins.in.Colorizer;
import loci.plugins.in.Concatenator;
import loci.plugins.util.LuraWave;
import loci.common.Region;
import loci.formats.meta.IMetadata;
import java.io.IOException;
import loci.formats.FormatException;
import loci.formats.cache.CacheException;
import loci.plugins.util.BFVirtualStack;
import ij.io.FileInfo;
import loci.formats.IFormatReader;
import loci.plugins.in.Calibrator;
import loci.plugins.util.VirtualImagePlus;
import loci.plugins.util.ImageProcessorReader;
import loci.plugins.in.ImporterOptions;
import java.awt.image.ColorModel;
import ij.ImageStack;
import ij.process.LUT;
import java.util.ArrayList;
import ij.process.ImageProcessor;
import java.util.Iterator;
import loci.common.StatusEvent;
import ij.ImagePlus;
import java.util.Vector;
import loci.common.StatusListener;
import java.util.List;
import loci.plugins.in.ImportProcess;
import loci.common.StatusReporter;

// 
// Decompiled by Procyon v0.5.36
// 

public class ImagePlusReaderModified implements StatusReporter
{
    public static final String PROP_SERIES = "Series";
    public static final String PROP_LUT = "LUT-";
    public ImportProcess process;
    public List<StatusListener> listeners;
    public long startTime;
    public long time;
    
    public ImagePlusReaderModified(final ImportProcess process) {
        this.listeners = new Vector<StatusListener>();
        this.process = process;
    }
    
    public ImagePlus[] openImagePlus() {
        final List<ImagePlus> imps = this.readImages();
        return imps.<ImagePlus>toArray(new ImagePlus[0]);
    }
    
    public ImagePlus[] openThumbImagePlus() {
        final List<ImagePlus> imps = this.readThumbImages();
        return imps.<ImagePlus>toArray(new ImagePlus[imps.size()]);
    }
    
    public void addStatusListener(final StatusListener l) {
        this.listeners.add(l);
    }
    
    public void removeStatusListener(final StatusListener l) {
        this.listeners.remove(l);
    }
    
    public void notifyListeners(final StatusEvent e) {
        for (final StatusListener l : this.listeners) {
            l.statusUpdated(e);
        }
    }
    
    public static ImagePlus createImage(final String title, final List<ImageProcessor> procs) {
        final List<LUT> luts = new ArrayList<LUT>();
        final ImageStack stack = createStack(procs, null, luts);
        return createImage(title, stack, luts);
    }
    
    public static ImagePlus createImage(final String title, final ImageStack stack, final List<LUT> luts) {
        final ImagePlus imp = new ImagePlus(title, stack);
        saveLUTs(imp, luts);
        return imp;
    }
    
    public static ImageStack createStack(final List<ImageProcessor> procs, final List<String> labels, final List<LUT> luts) {
        if (procs == null || procs.size() == 0) {
            return null;
        }
        final ImageProcessor ip0 = procs.get(0);
        final ImageStack stack = new ImageStack(ip0.getWidth(), ip0.getHeight());
        for (int i = 0; i < procs.size(); ++i) {
            final ImageProcessor ip2 = procs.get(i);
            final String label = (labels == null) ? null : labels.get(i);
            if (luts != null) {
                final ColorModel cm = ip2.getColorModel();
                if (cm instanceof LUT) {
                    final LUT lut = (LUT)cm;
                    luts.add(lut);
                    ip2.setColorModel((ColorModel)ip2.getDefaultColorModel());
                }
                else {
                    luts.add(null);
                }
            }
            stack.addSlice(label, ip2);
        }
        return stack;
    }
    
    public List<ImagePlus> readImages() {
        return this.readImages(false);
    }
    
    public List<ImagePlus> readThumbImages() {
        return this.readImages(true);
    }
    
    public List<ImagePlus> readImages(final boolean thumbnail) {
        final ImporterOptions options = this.process.getOptions();
        final ImageProcessorReader reader = this.process.getReader();
        List<ImagePlus> imps = new ArrayList<ImagePlus>();
        this.startTiming();
        for (int s = 0; s < reader.getSeriesCount(); ++s) {
            if (options.isSeriesOn(s)) {
                final ImagePlus imp = this.readImage(s, thumbnail);
                imps.add(imp);
            }
        }
        imps = this.concatenate(imps);
        imps = this.applyColors(imps);
        imps = this.splitDims(imps);
        if (options.isVirtual()) {
            this.process.getVirtualReader().setRefCount(imps.size());
        }
        this.finishTiming();
        return imps;
    }
    
    public ImagePlus readImage(final int s, final boolean thumbnail) {
        final ImporterOptions options = this.process.getOptions();
        final int zCount = this.process.getZCount(s);
        final int cCount = this.process.getCCount(s);
        final int tCount = this.process.getTCount(s);
        final List<LUT> luts = new ArrayList<LUT>();
        ImageStack stack;
        if (options.isVirtual()) {
            stack = this.createVirtualStack(this.process, s, luts);
        }
        else {
            stack = this.readPlanes(this.process, s, luts, thumbnail);
        }
        this.notifyListeners(new StatusEvent(1, 1, "Creating image"));
        final String seriesName = this.process.getOMEMetadata().getImageName(s);
        final String file = this.process.getCurrentFile();
        final IFormatReader reader = (IFormatReader)this.process.getReader();
        final String title = this.constructImageTitle(reader, file, seriesName, options.isGroupFiles());
        ImagePlus imp;
        if (stack.isVirtual()) {
            final VirtualImagePlus vip = new VirtualImagePlus(title, stack);
            vip.setReader(reader);
            imp = (ImagePlus)vip;
            saveLUTs(imp, luts);
        }
        else {
            imp = createImage(title, stack, luts);
        }
        final String metadata = this.process.getOriginalMetadata().toString();
        imp.setProperty("Info", (Object)metadata);
        imp.setProperty("Series", (Object)s);
        final FileInfo fi = this.createFileInfo();
        new Calibrator(this.process).applyCalibration(imp);
        imp.setFileInfo(fi);
        imp.setDimensions(cCount, zCount, tCount);
        final boolean hyper = !options.isViewStandard();
        imp.setOpenAsHyperStack(hyper);
        return imp;
    }
    
    public ImageStack createVirtualStack(final ImportProcess process, final int s, final List<LUT> luts) {
        final ImporterOptions options = process.getOptions();
        final ImageProcessorReader reader = process.getReader();
        reader.setSeries(s);
        final int zCount = process.getZCount(s);
        final int cCount = process.getCCount(s);
        final int tCount = process.getTCount(s);
        final IMetadata meta = process.getOMEMetadata();
        final int imageCount = reader.getImageCount();
        BFVirtualStack virtualStack = null;
        try {
            virtualStack = new BFVirtualStack(options.getId(), (IFormatReader)reader, false, false, false);
        }
        catch (CacheException e) {
            e.printStackTrace();
        }
        catch (FormatException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
        for (int i = 0; i < imageCount; ++i) {
            final String label = this.constructSliceLabel(i, reader, meta, s, zCount, cCount, tCount);
            virtualStack.addSlice(label);
        }
        if (luts != null) {
            for (int c = 0; c < cCount; ++c) {
                final int index = reader.getIndex(0, c, 0);
                ImageProcessor ip = null;
                try {
                    ip = reader.openProcessors(index)[0];
                }
                catch (FormatException | IOException ex2) {
                    final Exception ex;
                    final Exception e4 = ex;
                    e4.printStackTrace();
                }
                final ColorModel cm = ip.getColorModel();
                final LUT lut = (cm instanceof LUT) ? cm : null;
                luts.add(lut);
            }
        }
        return (ImageStack)virtualStack;
    }
    
    public ImageStack readPlanes(final ImportProcess process, final int s, final List<LUT> luts, final boolean thumbnail) {
        final ImageProcessorReader reader = process.getReader();
        reader.setSeries(s);
        final int zCount = process.getZCount(s);
        final int cCount = process.getCCount(s);
        final int tCount = process.getTCount(s);
        final IMetadata meta = process.getOMEMetadata();
        final boolean[] load = this.getPlanesToLoad(s);
        int current = 0;
        int total = 0;
        for (int j = 0; j < load.length; ++j) {
            if (load[j]) {
                ++total;
            }
        }
        final List<ImageProcessor> procs = new ArrayList<ImageProcessor>();
        final List<String> labels = new ArrayList<String>();
        final Region region = process.getCropRegion(s);
        for (int i = 0; i < load.length; ++i) {
            if (load[i]) {
                this.updateTiming(s, current, current++, total);
                final ImageProcessor[] p = this.readProcessors(process, i, region, thumbnail);
                final String label = this.constructSliceLabel(i, reader, meta, s, zCount, cCount, tCount);
                ImageProcessor[] array;
                for (int length = (array = p).length, k = 0; k < length; ++k) {
                    final ImageProcessor ip = array[k];
                    procs.add(ip);
                    labels.add(label);
                }
            }
        }
        return createStack(procs, labels, luts);
    }
    
    public ImageProcessor[] readProcessors(final ImportProcess process, final int no, final Region r, final boolean thumbnail) {
        final ImageProcessorReader reader = process.getReader();
        final ImporterOptions options = process.getOptions();
        boolean first = true;
        for (int i = 0; i < 5; ++i) {
            String code = LuraWave.initLicenseCode();
            try {
                if (thumbnail) {
                    try {
                        return reader.openThumbProcessors(no);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    return reader.openProcessors(no, r.x, r.y, r.width, r.height);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch (FormatException exc) {
                code = LuraWave.promptLicenseCode(code, first);
                if (first) {
                    first = false;
                }
            }
        }
        return null;
    }
    
    public List<ImagePlus> concatenate(List<ImagePlus> imps) {
        final ImporterOptions options = this.process.getOptions();
        if (options.isConcatenate()) {
            imps = (List<ImagePlus>)new Concatenator().concatenate((List)imps);
        }
        return imps;
    }
    
    public List<ImagePlus> applyColors(final List<ImagePlus> imps) {
        return (List<ImagePlus>)new Colorizer(this.process).applyColors((List)imps);
    }
    
    public List<ImagePlus> splitDims(List<ImagePlus> imps) {
        final ImporterOptions options = this.process.getOptions();
        final boolean sliceC = options.isSplitChannels();
        final boolean sliceZ = options.isSplitFocalPlanes();
        final boolean sliceT = options.isSplitTimepoints();
        if (sliceC || sliceZ || sliceT) {
            final String stackOrder = this.process.getStackOrder();
            final List<ImagePlus> slicedImps = new ArrayList<ImagePlus>();
            final Slicer slicer = new Slicer();
            for (final ImagePlus imp : imps) {
                final ImagePlus[] results = slicer.reslice(imp, sliceC, sliceZ, sliceT, stackOrder);
                ImagePlus[] array;
                for (int length = (array = results).length, i = 0; i < length; ++i) {
                    final ImagePlus result = array[i];
                    slicedImps.add(result);
                }
            }
            imps = slicedImps;
        }
        return imps;
    }
    
    public void startTiming() {
        final long currentTimeMillis = System.currentTimeMillis();
        this.time = currentTimeMillis;
        this.startTime = currentTimeMillis;
    }
    
    public void updateTiming(final int s, final int i, final int current, final int total) {
        final ImageProcessorReader reader = this.process.getReader();
        final long clock = System.currentTimeMillis();
        if (clock - this.time >= 100L) {
            final String sLabel = (reader.getSeriesCount() > 1) ? ("series " + (s + 1) + ", ") : "";
            final String pLabel = "plane " + (i + 1) + "/" + total;
            this.notifyListeners(new StatusEvent("Reading " + sLabel + pLabel));
            this.time = clock;
        }
        this.notifyListeners(new StatusEvent(current, total, (String)null));
    }
    
    public void finishTiming() {
        final ImageProcessorReader reader = this.process.getReader();
        final long endTime = System.currentTimeMillis();
        final double elapsed = (endTime - this.startTime) / 1000.0;
        if (reader.getImageCount() == 1) {
            this.notifyListeners(new StatusEvent("Bio-Formats: " + elapsed + " seconds"));
        }
        else {
            final long average = (endTime - this.startTime) / reader.getImageCount();
            this.notifyListeners(new StatusEvent("Bio-Formats: " + elapsed + " seconds (" + average + " ms per plane)"));
        }
    }
    
    public FileInfo createFileInfo() {
        final FileInfo fi = new FileInfo();
        String idDir = (this.process.getIdLocation() == null) ? null : this.process.getIdLocation().getParent();
        if (idDir != null && !idDir.endsWith(File.separator)) {
            idDir = String.valueOf(idDir) + File.separator;
        }
        fi.fileName = this.process.getIdName();
        fi.directory = idDir;
        try {
            final ServiceFactory factory = new ServiceFactory();
            final OMEXMLService service = (OMEXMLService)factory.getInstance((Class)OMEXMLService.class);
            fi.description = service.getOMEXML((MetadataRetrieve)this.process.getOMEMetadata());
        }
        catch (DependencyException ex) {}
        catch (ServiceException ex2) {}
        return fi;
    }
    
    public boolean[] getPlanesToLoad(final int s) {
        final ImageProcessorReader reader = this.process.getReader();
        final boolean[] load = new boolean[reader.getImageCount()];
        final int cBegin = this.process.getCBegin(s);
        final int cEnd = this.process.getCEnd(s);
        final int cStep = this.process.getCStep(s);
        final int zBegin = this.process.getZBegin(s);
        final int zEnd = this.process.getZEnd(s);
        final int zStep = this.process.getZStep(s);
        final int tBegin = this.process.getTBegin(s);
        final int tEnd = this.process.getTEnd(s);
        final int tStep = this.process.getTStep(s);
        for (int c = cBegin; c <= cEnd; c += cStep) {
            for (int z = zBegin; z <= zEnd; z += zStep) {
                for (int t = tBegin; t <= tEnd; t += tStep) {
                    final int index = reader.getIndex(z, c, t);
                    load[index] = true;
                }
            }
        }
        return load;
    }
    
    public String constructImageTitle(final IFormatReader r, final String file, final String seriesName, final boolean groupFiles) {
        final String[] used = r.getUsedFiles();
        String title = file.substring(file.lastIndexOf(File.separator) + 1);
        if (used.length > 1 && groupFiles) {
            final FilePattern fp = new FilePattern(new Location(file));
            title = fp.getPattern();
            if (title == null) {
                title = file;
                if (title.indexOf(".") != -1) {
                    title = title.substring(0, title.lastIndexOf("."));
                }
            }
            title = title.substring(title.lastIndexOf(File.separator) + 1);
        }
        if (seriesName != null && !file.endsWith(seriesName) && r.getSeriesCount() > 1) {
            title = String.valueOf(title) + " - " + seriesName;
        }
        if (title.length() > 128) {
            final String a = title.substring(0, 62);
            final String b = title.substring(title.length() - 62);
            title = String.valueOf(a) + "..." + b;
        }
        return title;
    }
    
    public String constructSliceLabel(final int ndx, final ImageProcessorReader r, final IMetadata meta, final int series, final int zCount, final int cCount, final int tCount) {
        r.setSeries(series);
        final int[] zct = r.getZCTCoords(ndx);
        final StringBuffer sb = new StringBuffer();
        boolean first = true;
        if (zCount > 1) {
            if (first) {
                first = false;
            }
            else {
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
            }
            else {
                sb.append("; ");
            }
            sb.append("t:");
            sb.append(zct[2] + 1);
            sb.append("/");
            sb.append(r.getSizeT());
        }
        final String imageName = meta.getImageName(series);
        if (imageName != null && !imageName.trim().equals("")) {
            sb.append(" - ");
            sb.append(imageName);
        }
        return sb.toString();
    }
    
    public static void saveLUTs(final ImagePlus imp, final List<LUT> luts) {
        for (int i = 0; i < luts.size(); ++i) {
            final LUT lut = luts.get(i);
            if (lut != null) {
                imp.setProperty("LUT-" + i, (Object)lut);
            }
        }
    }
}
