/*     */ import ij.IJ;
/*     */ import ij.ImagePlus;
/*     */ import ij.ImageStack;
/*     */ import ij.process.ColorProcessor;
/*     */ import ij.process.ImageProcessor;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JFrame;
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
/*     */ 
/*     */ class null
/*     */   implements Runnable
/*     */ {
/*     */   public void run() {
/* 434 */     if (IJ.getImage() == null)
/* 435 */       IJ.error("You must have an image window active."); 
/* 436 */     if (IJ.getImage() != null) {
/* 437 */       JFrame pngFrame = new JFrame();
/* 438 */       JFileChooser fileChooser = new JFileChooser();
/* 439 */       fileChooser.setFileSelectionMode(1);
/* 440 */       fileChooser.setDialogTitle("Specify a directory to save");
/* 441 */       int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */       
/* 443 */       if (userSelection == 0) {
/* 444 */         File fileToSave = fileChooser.getSelectedFile();
/*     */         
/* 446 */         int firstFrame = 0, lastFrame = 0;
/* 447 */         if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/* 448 */           firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/* 449 */           lastFrame = Math.min(IJ.getImage().getNFrames(), 
/* 450 */               Math.max(IJ.getImage().getNFrames(), 1));
/*     */         } 
/* 452 */         if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/* 453 */           firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/* 454 */           lastFrame = Math.min(IJ.getImage().getNSlices(), 
/* 455 */               Math.max(IJ.getImage().getNSlices(), 1));
/*     */         } 
/*     */         
/* 458 */         Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/* 459 */         int width = bounds.width;
/* 460 */         int height = bounds.height;
/* 461 */         int nCaptures = lastFrame - firstFrame + 1;
/* 462 */         ImageStack stack = new ImageStack(width, height);
/* 463 */         int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/* 464 */         int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/* 465 */         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/* 466 */         for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*     */           
/* 468 */           ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/* 469 */               frame);
/* 470 */           BufferedImage bi = new BufferedImage(width, height, 2);
/* 471 */           ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/* 472 */           ColorProcessor cp = new ColorProcessor(bi);
/* 473 */           int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/* 474 */               frame);
/* 475 */           stack.addSlice(
/* 476 */               ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/* 477 */               (ImageProcessor)cp);
/*     */         } 
/* 479 */         ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/* 480 */         ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/* 481 */             ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/* 482 */         FirstWizardPanel.access$0(ProcessTrackMateXml.displayer.getImp(), capture);
/* 483 */         IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 484 */             "Capture Overlay for " + IJ.getImage().getShortTitle());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/FirstWizardPanel$4$1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */