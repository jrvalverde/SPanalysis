/*     */ import ij.IJ;
/*     */ import ij.ImagePlus;
/*     */ import ij.ImageStack;
/*     */ import ij.process.ColorProcessor;
/*     */ import ij.process.ImageProcessor;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
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
/*     */ class null
/*     */   implements ActionListener
/*     */ {
/*     */   public void actionPerformed(ActionEvent e) {
/* 372 */     ChooserWizardPanel.this.pngThread = new Thread(new Runnable() {
/*     */           public void run() {
/* 374 */             if (IJ.getImage() == null)
/* 375 */               IJ.error("You must have an image window active."); 
/* 376 */             if (IJ.getImage() != null) {
/* 377 */               JFrame pngFrame = new JFrame();
/* 378 */               JFileChooser fileChooser = new JFileChooser();
/* 379 */               fileChooser.setFileSelectionMode(1);
/* 380 */               fileChooser.setDialogTitle("Specify a directory to save");
/* 381 */               int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */               
/* 383 */               if (userSelection == 0) {
/* 384 */                 File fileToSave = fileChooser.getSelectedFile();
/*     */                 
/* 386 */                 int firstFrame = 0, lastFrame = 0;
/* 387 */                 if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/* 388 */                   firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/* 389 */                   lastFrame = Math.min(IJ.getImage().getNFrames(), 
/* 390 */                       Math.max(IJ.getImage().getNFrames(), 1));
/*     */                 } 
/* 392 */                 if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/* 393 */                   firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/* 394 */                   lastFrame = Math.min(IJ.getImage().getNSlices(), 
/* 395 */                       Math.max(IJ.getImage().getNSlices(), 1));
/*     */                 } 
/*     */                 
/* 398 */                 Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/* 399 */                 int width = bounds.width;
/* 400 */                 int height = bounds.height;
/* 401 */                 int nCaptures = lastFrame - firstFrame + 1;
/* 402 */                 ImageStack stack = new ImageStack(width, height);
/* 403 */                 int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/* 404 */                 int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/* 405 */                 ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/* 406 */                 for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*     */                   
/* 408 */                   ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/* 409 */                       frame);
/* 410 */                   BufferedImage bi = new BufferedImage(width, height, 2);
/* 411 */                   ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/* 412 */                   ColorProcessor cp = new ColorProcessor(bi);
/* 413 */                   int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/* 414 */                       frame);
/* 415 */                   stack.addSlice(
/* 416 */                       ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/* 417 */                       (ImageProcessor)cp);
/*     */                 } 
/* 419 */                 ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/* 420 */                 ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/* 421 */                     ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/* 422 */                 ChooserWizardPanel.access$0(ProcessTrackMateXml.displayer.getImp(), capture);
/* 423 */                 IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 424 */                     "Capture Overlay for " + IJ.getImage().getShortTitle());
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/* 429 */     ChooserWizardPanel.this.pngThread.start();
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ChooserWizardPanel$3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */