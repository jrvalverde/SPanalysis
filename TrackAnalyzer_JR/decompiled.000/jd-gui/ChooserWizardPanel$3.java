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
/*     */ class null
/*     */   implements ActionListener
/*     */ {
/*     */   public void actionPerformed(ActionEvent e) {
/* 371 */     ChooserWizardPanel.this.pngThread = new Thread(new Runnable() {
/*     */           public void run() {
/* 373 */             if (IJ.getImage() == null)
/* 374 */               IJ.error("You must have an image window active."); 
/* 375 */             if (IJ.getImage() != null) {
/* 376 */               JFrame pngFrame = new JFrame();
/* 377 */               JFileChooser fileChooser = new JFileChooser();
/* 378 */               fileChooser.setFileSelectionMode(1);
/* 379 */               fileChooser.setDialogTitle("Specify a directory to save");
/* 380 */               int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */               
/* 382 */               if (userSelection == 0) {
/* 383 */                 File fileToSave = fileChooser.getSelectedFile();
/*     */                 
/* 385 */                 int firstFrame = 0, lastFrame = 0;
/* 386 */                 if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/* 387 */                   firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/* 388 */                   lastFrame = Math.min(IJ.getImage().getNFrames(), 
/* 389 */                       Math.max(IJ.getImage().getNFrames(), 1));
/*     */                 } 
/* 391 */                 if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/* 392 */                   firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/* 393 */                   lastFrame = Math.min(IJ.getImage().getNSlices(), 
/* 394 */                       Math.max(IJ.getImage().getNSlices(), 1));
/*     */                 } 
/*     */                 
/* 397 */                 Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/* 398 */                 int width = bounds.width;
/* 399 */                 int height = bounds.height;
/* 400 */                 int nCaptures = lastFrame - firstFrame + 1;
/* 401 */                 ImageStack stack = new ImageStack(width, height);
/* 402 */                 int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/* 403 */                 int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/* 404 */                 ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/* 405 */                 for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*     */                   
/* 407 */                   ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/* 408 */                       frame);
/* 409 */                   BufferedImage bi = new BufferedImage(width, height, 2);
/* 410 */                   ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/* 411 */                   ColorProcessor cp = new ColorProcessor(bi);
/* 412 */                   int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/* 413 */                       frame);
/* 414 */                   stack.addSlice(
/* 415 */                       ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/* 416 */                       (ImageProcessor)cp);
/*     */                 } 
/* 418 */                 ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/* 419 */                 ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/* 420 */                     ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/* 421 */                 ChooserWizardPanel.access$0(ProcessTrackMateXml.displayer.getImp(), capture);
/* 422 */                 IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 423 */                     "Capture Overlay for " + IJ.getImage().getShortTitle());
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/* 428 */     ChooserWizardPanel.this.pngThread.start();
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/ChooserWizardPanel$3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */