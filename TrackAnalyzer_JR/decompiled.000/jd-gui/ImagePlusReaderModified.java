/*     */ import ij.ImagePlus;
/*     */ import ij.ImageStack;
/*     */ import ij.io.FileInfo;
/*     */ import ij.process.ImageProcessor;
/*     */ import ij.process.LUT;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
/*     */ import loci.common.Location;
/*     */ import loci.common.Region;
/*     */ import loci.common.StatusEvent;
/*     */ import loci.common.StatusListener;
/*     */ import loci.common.StatusReporter;
/*     */ import loci.common.services.DependencyException;
/*     */ import loci.common.services.ServiceException;
/*     */ import loci.common.services.ServiceFactory;
/*     */ import loci.formats.FilePattern;
/*     */ import loci.formats.FormatException;
/*     */ import loci.formats.IFormatReader;
/*     */ import loci.formats.cache.CacheException;
/*     */ import loci.formats.meta.IMetadata;
/*     */ import loci.formats.meta.MetadataRetrieve;
/*     */ import loci.formats.services.OMEXMLService;
/*     */ import loci.plugins.Slicer;
/*     */ import loci.plugins.in.Calibrator;
/*     */ import loci.plugins.in.Colorizer;
/*     */ import loci.plugins.in.Concatenator;
/*     */ import loci.plugins.in.ImportProcess;
/*     */ import loci.plugins.in.ImporterOptions;
/*     */ import loci.plugins.util.BFVirtualStack;
/*     */ import loci.plugins.util.ImageProcessorReader;
/*     */ import loci.plugins.util.LuraWave;
/*     */ import loci.plugins.util.VirtualImagePlus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImagePlusReaderModified
/*     */   implements StatusReporter
/*     */ {
/*     */   public static final String PROP_SERIES = "Series";
/*     */   public static final String PROP_LUT = "LUT-";
/*     */   public ImportProcess process;
/*  65 */   public List<StatusListener> listeners = new Vector<>();
/*     */   
/*     */   public long startTime;
/*     */   
/*     */   public long time;
/*     */ 
/*     */   
/*     */   public ImagePlusReaderModified(ImportProcess process) {
/*  73 */     this.process = process;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImagePlus[] openImagePlus() {
/*  83 */     List<ImagePlus> imps = readImages();
/*  84 */     return imps.<ImagePlus>toArray(new ImagePlus[0]);
/*     */   }
/*     */   
/*     */   public ImagePlus[] openThumbImagePlus() {
/*  88 */     List<ImagePlus> imps = readThumbImages();
/*  89 */     return imps.<ImagePlus>toArray(new ImagePlus[imps.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStatusListener(StatusListener l) {
/*  95 */     this.listeners.add(l);
/*     */   }
/*     */   
/*     */   public void removeStatusListener(StatusListener l) {
/*  99 */     this.listeners.remove(l);
/*     */   }
/*     */   
/*     */   public void notifyListeners(StatusEvent e) {
/* 103 */     for (StatusListener l : this.listeners) l.statusUpdated(e);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImagePlus createImage(String title, List<ImageProcessor> procs) {
/* 115 */     List<LUT> luts = new ArrayList<>();
/* 116 */     ImageStack stack = createStack(procs, null, luts);
/* 117 */     return createImage(title, stack, luts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImagePlus createImage(String title, ImageStack stack, List<LUT> luts) {
/* 131 */     ImagePlus imp = new ImagePlus(title, stack);
/*     */     
/* 133 */     saveLUTs(imp, luts);
/* 134 */     return imp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ImageStack createStack(List<ImageProcessor> procs, List<String> labels, List<LUT> luts) {
/* 147 */     if (procs == null || procs.size() == 0) return null;
/*     */     
/* 149 */     ImageProcessor ip0 = procs.get(0);
/* 150 */     ImageStack stack = new ImageStack(ip0.getWidth(), ip0.getHeight());
/*     */ 
/*     */     
/* 153 */     for (int i = 0; i < procs.size(); i++) {
/* 154 */       ImageProcessor ip = procs.get(i);
/* 155 */       String label = (labels == null) ? null : labels.get(i);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 160 */       if (luts != null) {
/* 161 */         ColorModel cm = ip.getColorModel();
/* 162 */         if (cm instanceof LUT) {
/*     */           
/* 164 */           LUT lut = (LUT)cm;
/* 165 */           luts.add(lut);
/*     */           
/* 167 */           ip.setColorModel(ip.getDefaultColorModel());
/*     */         }
/*     */         else {
/*     */           
/* 171 */           luts.add(null);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 176 */       stack.addSlice(label, ip);
/*     */     } 
/*     */     
/* 179 */     return stack;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ImagePlus> readImages() {
/* 185 */     return readImages(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ImagePlus> readThumbImages() {
/* 190 */     return readImages(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ImagePlus> readImages(boolean thumbnail) {
/* 196 */     ImporterOptions options = this.process.getOptions();
/* 197 */     ImageProcessorReader reader = this.process.getReader();
/*     */     
/* 199 */     List<ImagePlus> imps = new ArrayList<>();
/*     */ 
/*     */     
/* 202 */     startTiming();
/*     */ 
/*     */     
/* 205 */     for (int s = 0; s < reader.getSeriesCount(); s++) {
/* 206 */       if (options.isSeriesOn(s)) {
/* 207 */         ImagePlus imp = readImage(s, thumbnail);
/* 208 */         imps.add(imp);
/*     */       } 
/*     */     } 
/* 211 */     imps = concatenate(imps);
/*     */ 
/*     */     
/* 214 */     imps = applyColors(imps);
/*     */ 
/*     */     
/* 217 */     imps = splitDims(imps);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (options.isVirtual()) {
/* 223 */       this.process.getVirtualReader().setRefCount(imps.size());
/*     */     }
/*     */ 
/*     */     
/* 227 */     finishTiming();
/*     */     
/* 229 */     return imps;
/*     */   }
/*     */   public ImagePlus readImage(int s, boolean thumbnail) {
/*     */     ImageStack stack;
/*     */     ImagePlus imp;
/* 234 */     ImporterOptions options = this.process.getOptions();
/* 235 */     int zCount = this.process.getZCount(s);
/* 236 */     int cCount = this.process.getCCount(s);
/* 237 */     int tCount = this.process.getTCount(s);
/*     */ 
/*     */     
/* 240 */     List<LUT> luts = new ArrayList<>();
/*     */ 
/*     */ 
/*     */     
/* 244 */     if (options.isVirtual()) { stack = createVirtualStack(this.process, s, luts); }
/* 245 */     else { stack = readPlanes(this.process, s, luts, thumbnail); }
/*     */     
/* 247 */     notifyListeners(new StatusEvent(1, 1, "Creating image"));
/*     */ 
/*     */     
/* 250 */     String seriesName = this.process.getOMEMetadata().getImageName(s);
/* 251 */     String file = this.process.getCurrentFile();
/* 252 */     ImageProcessorReader imageProcessorReader = this.process.getReader();
/* 253 */     String title = constructImageTitle((IFormatReader)imageProcessorReader, 
/* 254 */         file, seriesName, options.isGroupFiles());
/*     */ 
/*     */ 
/*     */     
/* 258 */     if (stack.isVirtual()) {
/* 259 */       VirtualImagePlus vip = new VirtualImagePlus(title, stack);
/* 260 */       vip.setReader((IFormatReader)imageProcessorReader);
/* 261 */       VirtualImagePlus virtualImagePlus1 = vip;
/* 262 */       saveLUTs((ImagePlus)virtualImagePlus1, luts);
/*     */     } else {
/* 264 */       imp = createImage(title, stack, luts);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 269 */     String metadata = this.process.getOriginalMetadata().toString();
/* 270 */     imp.setProperty("Info", metadata);
/* 271 */     imp.setProperty("Series", Integer.valueOf(s));
/*     */ 
/*     */     
/* 274 */     FileInfo fi = createFileInfo();
/* 275 */     (new Calibrator(this.process)).applyCalibration(imp);
/* 276 */     imp.setFileInfo(fi);
/* 277 */     imp.setDimensions(cCount, zCount, tCount);
/*     */ 
/*     */     
/* 280 */     boolean hyper = !options.isViewStandard();
/* 281 */     imp.setOpenAsHyperStack(hyper);
/*     */     
/* 283 */     return imp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageStack createVirtualStack(ImportProcess process, int s, List<LUT> luts) {
/* 289 */     ImporterOptions options = process.getOptions();
/* 290 */     ImageProcessorReader reader = process.getReader();
/* 291 */     reader.setSeries(s);
/* 292 */     int zCount = process.getZCount(s);
/* 293 */     int cCount = process.getCCount(s);
/* 294 */     int tCount = process.getTCount(s);
/* 295 */     IMetadata meta = process.getOMEMetadata();
/* 296 */     int imageCount = reader.getImageCount();
/*     */ 
/*     */     
/* 299 */     BFVirtualStack virtualStack = null;
/*     */     try {
/* 301 */       virtualStack = new BFVirtualStack(options.getId(), 
/* 302 */           (IFormatReader)reader, false, false, false);
/* 303 */     } catch (CacheException e) {
/*     */       
/* 305 */       e.printStackTrace();
/* 306 */     } catch (FormatException e) {
/*     */       
/* 308 */       e.printStackTrace();
/* 309 */     } catch (IOException e) {
/*     */       
/* 311 */       e.printStackTrace();
/*     */     } 
/* 313 */     for (int i = 0; i < imageCount; i++) {
/* 314 */       String label = constructSliceLabel(i, 
/* 315 */           reader, meta, s, zCount, cCount, tCount);
/* 316 */       virtualStack.addSlice(label);
/*     */     } 
/*     */     
/* 319 */     if (luts != null) {
/* 320 */       for (int c = 0; c < cCount; c++) {
/* 321 */         int index = reader.getIndex(0, c, 0);
/* 322 */         ImageProcessor ip = null;
/*     */         try {
/* 324 */           ip = reader.openProcessors(index)[0];
/* 325 */         } catch (FormatException|IOException e) {
/*     */           
/* 327 */           e.printStackTrace();
/*     */         } 
/* 329 */         ColorModel cm = ip.getColorModel();
/* 330 */         LUT lut = (cm instanceof LUT) ? (LUT)cm : null;
/* 331 */         luts.add(lut);
/*     */       } 
/*     */     }
/*     */     
/* 335 */     return (ImageStack)virtualStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageStack readPlanes(ImportProcess process, int s, List<LUT> luts, boolean thumbnail) {
/* 342 */     ImageProcessorReader reader = process.getReader();
/* 343 */     reader.setSeries(s);
/* 344 */     int zCount = process.getZCount(s);
/* 345 */     int cCount = process.getCCount(s);
/* 346 */     int tCount = process.getTCount(s);
/* 347 */     IMetadata meta = process.getOMEMetadata();
/*     */ 
/*     */     
/* 350 */     boolean[] load = getPlanesToLoad(s);
/* 351 */     int current = 0, total = 0;
/* 352 */     for (int j = 0; j < load.length; ) { if (load[j]) total++;  j++; }
/*     */     
/* 354 */     List<ImageProcessor> procs = new ArrayList<>();
/* 355 */     List<String> labels = new ArrayList<>();
/*     */ 
/*     */     
/* 358 */     Region region = process.getCropRegion(s);
/* 359 */     for (int i = 0; i < load.length; i++) {
/* 360 */       if (load[i]) {
/*     */ 
/*     */         
/* 363 */         updateTiming(s, current, current++, total);
/*     */ 
/*     */         
/* 366 */         ImageProcessor[] p = readProcessors(process, i, region, thumbnail);
/*     */ 
/*     */         
/* 369 */         String label = constructSliceLabel(i, 
/* 370 */             reader, meta, s, zCount, cCount, tCount); byte b; int k;
/*     */         ImageProcessor[] arrayOfImageProcessor1;
/* 372 */         for (k = (arrayOfImageProcessor1 = p).length, b = 0; b < k; ) { ImageProcessor ip = arrayOfImageProcessor1[b];
/* 373 */           procs.add(ip);
/* 374 */           labels.add(label); b++; }
/*     */       
/*     */       } 
/*     */     } 
/* 378 */     return createStack(procs, labels, luts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageProcessor[] readProcessors(ImportProcess process, int no, Region r, boolean thumbnail) {
/* 390 */     ImageProcessorReader reader = process.getReader();
/* 391 */     ImporterOptions options = process.getOptions();
/*     */     
/* 393 */     boolean first = true;
/* 394 */     for (int i = 0; i < 5; i++) {
/* 395 */       String code = LuraWave.initLicenseCode();
/*     */       try {
/* 397 */         if (thumbnail) {
/*     */           try {
/* 399 */             return reader.openThumbProcessors(no);
/* 400 */           } catch (IOException e) {
/*     */             
/* 402 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */         try {
/* 406 */           return reader.openProcessors(no, r.x, r.y, r.width, r.height);
/* 407 */         } catch (IOException e) {
/*     */           
/* 409 */           e.printStackTrace();
/*     */         }
/*     */       
/* 412 */       } catch (FormatException exc) {
/*     */ 
/*     */         
/* 415 */         code = LuraWave.promptLicenseCode(code, first);
/* 416 */         if (first) first = false; 
/*     */       } 
/*     */     } 
/* 419 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ImagePlus> concatenate(List<ImagePlus> imps) {
/* 426 */     ImporterOptions options = this.process.getOptions();
/* 427 */     if (options.isConcatenate()) imps = (new Concatenator()).concatenate(imps); 
/* 428 */     return imps;
/*     */   }
/*     */   
/*     */   public List<ImagePlus> applyColors(List<ImagePlus> imps) {
/* 432 */     return (new Colorizer(this.process)).applyColors(imps);
/*     */   }
/*     */   
/*     */   public List<ImagePlus> splitDims(List<ImagePlus> imps) {
/* 436 */     ImporterOptions options = this.process.getOptions();
/*     */     
/* 438 */     boolean sliceC = options.isSplitChannels();
/* 439 */     boolean sliceZ = options.isSplitFocalPlanes();
/* 440 */     boolean sliceT = options.isSplitTimepoints();
/* 441 */     if (sliceC || sliceZ || sliceT) {
/* 442 */       String stackOrder = this.process.getStackOrder();
/* 443 */       List<ImagePlus> slicedImps = new ArrayList<>();
/* 444 */       Slicer slicer = new Slicer();
/* 445 */       for (ImagePlus imp : imps) {
/* 446 */         ImagePlus[] results = slicer.reslice(imp, 
/* 447 */             sliceC, sliceZ, sliceT, stackOrder); byte b; int i; ImagePlus[] arrayOfImagePlus1;
/* 448 */         for (i = (arrayOfImagePlus1 = results).length, b = 0; b < i; ) { ImagePlus result = arrayOfImagePlus1[b]; slicedImps.add(result); b++; }
/*     */       
/* 450 */       }  imps = slicedImps;
/*     */     } 
/* 452 */     return imps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTiming() {
/* 460 */     this.startTime = this.time = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public void updateTiming(int s, int i, int current, int total) {
/* 464 */     ImageProcessorReader reader = this.process.getReader();
/*     */     
/* 466 */     long clock = System.currentTimeMillis();
/* 467 */     if (clock - this.time >= 100L) {
/* 468 */       String sLabel = (reader.getSeriesCount() > 1) ? (
/* 469 */         "series " + (s + 1) + ", ") : "";
/* 470 */       String pLabel = "plane " + (i + 1) + "/" + total;
/* 471 */       notifyListeners(new StatusEvent("Reading " + sLabel + pLabel));
/* 472 */       this.time = clock;
/*     */     } 
/* 474 */     notifyListeners(new StatusEvent(current, total, null));
/*     */   }
/*     */   
/*     */   public void finishTiming() {
/* 478 */     ImageProcessorReader reader = this.process.getReader();
/*     */     
/* 480 */     long endTime = System.currentTimeMillis();
/* 481 */     double elapsed = (endTime - this.startTime) / 1000.0D;
/* 482 */     if (reader.getImageCount() == 1) {
/* 483 */       notifyListeners(new StatusEvent("Bio-Formats: " + elapsed + " seconds"));
/*     */     } else {
/*     */       
/* 486 */       long average = (endTime - this.startTime) / reader.getImageCount();
/* 487 */       notifyListeners(new StatusEvent("Bio-Formats: " + 
/* 488 */             elapsed + " seconds (" + average + " ms per plane)"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FileInfo createFileInfo() {
/* 495 */     FileInfo fi = new FileInfo();
/*     */ 
/*     */     
/* 498 */     String idDir = (this.process.getIdLocation() == null) ? 
/* 499 */       null : this.process.getIdLocation().getParent();
/* 500 */     if (idDir != null && !idDir.endsWith(File.separator)) {
/* 501 */       idDir = String.valueOf(idDir) + File.separator;
/*     */     }
/* 503 */     fi.fileName = this.process.getIdName();
/* 504 */     fi.directory = idDir;
/*     */ 
/*     */ 
/*     */     
/* 508 */     try { ServiceFactory factory = new ServiceFactory();
/* 509 */       OMEXMLService service = (OMEXMLService)factory.getInstance(OMEXMLService.class);
/* 510 */       fi.description = service.getOMEXML((MetadataRetrieve)this.process.getOMEMetadata()); }
/*     */     
/* 512 */     catch (DependencyException dependencyException) {  }
/* 513 */     catch (ServiceException serviceException) {}
/*     */     
/* 515 */     return fi;
/*     */   }
/*     */   
/*     */   public boolean[] getPlanesToLoad(int s) {
/* 519 */     ImageProcessorReader reader = this.process.getReader();
/* 520 */     boolean[] load = new boolean[reader.getImageCount()];
/* 521 */     int cBegin = this.process.getCBegin(s);
/* 522 */     int cEnd = this.process.getCEnd(s);
/* 523 */     int cStep = this.process.getCStep(s);
/* 524 */     int zBegin = this.process.getZBegin(s);
/* 525 */     int zEnd = this.process.getZEnd(s);
/* 526 */     int zStep = this.process.getZStep(s);
/* 527 */     int tBegin = this.process.getTBegin(s);
/* 528 */     int tEnd = this.process.getTEnd(s);
/* 529 */     int tStep = this.process.getTStep(s);
/* 530 */     for (int c = cBegin; c <= cEnd; c += cStep) {
/* 531 */       for (int z = zBegin; z <= zEnd; z += zStep) {
/* 532 */         for (int t = tBegin; t <= tEnd; t += tStep) {
/* 533 */           int index = reader.getIndex(z, c, t);
/* 534 */           load[index] = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 538 */     return load;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String constructImageTitle(IFormatReader r, String file, String seriesName, boolean groupFiles) {
/* 544 */     String[] used = r.getUsedFiles();
/* 545 */     String title = file.substring(file.lastIndexOf(File.separator) + 1);
/* 546 */     if (used.length > 1 && groupFiles) {
/* 547 */       FilePattern fp = new FilePattern(new Location(file));
/* 548 */       title = fp.getPattern();
/* 549 */       if (title == null) {
/* 550 */         title = file;
/* 551 */         if (title.indexOf(".") != -1) {
/* 552 */           title = title.substring(0, title.lastIndexOf("."));
/*     */         }
/*     */       } 
/* 555 */       title = title.substring(title.lastIndexOf(File.separator) + 1);
/*     */     } 
/* 557 */     if (seriesName != null && !file.endsWith(seriesName) && 
/* 558 */       r.getSeriesCount() > 1)
/*     */     {
/* 560 */       title = String.valueOf(title) + " - " + seriesName;
/*     */     }
/* 562 */     if (title.length() > 128) {
/* 563 */       String a = title.substring(0, 62);
/* 564 */       String b = title.substring(title.length() - 62);
/* 565 */       title = String.valueOf(a) + "..." + b;
/*     */     } 
/* 567 */     return title;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String constructSliceLabel(int ndx, ImageProcessorReader r, IMetadata meta, int series, int zCount, int cCount, int tCount) {
/* 573 */     r.setSeries(series);
/*     */     
/* 575 */     int[] zct = r.getZCTCoords(ndx);
/*     */ 
/*     */     
/* 578 */     StringBuffer sb = new StringBuffer();
/*     */     
/* 580 */     boolean first = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 589 */     if (zCount > 1) {
/* 590 */       if (first) { first = false; }
/* 591 */       else { sb.append("; "); }
/* 592 */        sb.append("z:");
/* 593 */       sb.append(zct[0] + 1);
/* 594 */       sb.append("/");
/* 595 */       sb.append(r.getSizeZ());
/*     */     } 
/* 597 */     if (tCount > 1) {
/* 598 */       if (first) { first = false; }
/* 599 */       else { sb.append("; "); }
/* 600 */        sb.append("t:");
/* 601 */       sb.append(zct[2] + 1);
/* 602 */       sb.append("/");
/* 603 */       sb.append(r.getSizeT());
/*     */     } 
/*     */     
/* 606 */     String imageName = meta.getImageName(series);
/* 607 */     if (imageName != null && !imageName.trim().equals("")) {
/* 608 */       sb.append(" - ");
/* 609 */       sb.append(imageName);
/*     */     } 
/* 611 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void saveLUTs(ImagePlus imp, List<LUT> luts) {
/* 619 */     for (int i = 0; i < luts.size(); i++) {
/* 620 */       LUT lut = luts.get(i);
/* 621 */       if (lut != null)
/* 622 */         imp.setProperty("LUT-" + i, lut); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/ImagePlusReaderModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */