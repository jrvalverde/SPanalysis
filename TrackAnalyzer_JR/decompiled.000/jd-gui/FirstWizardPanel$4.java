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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 431 */     FirstWizardPanel.this.pngThread = new Thread(new Runnable() {
/*     */           public void run() {
/* 433 */             if (IJ.getImage() == null)
/* 434 */               IJ.error("You must have an image window active."); 
/* 435 */             if (IJ.getImage() != null) {
/* 436 */               JFrame pngFrame = new JFrame();
/* 437 */               JFileChooser fileChooser = new JFileChooser();
/* 438 */               fileChooser.setFileSelectionMode(1);
/* 439 */               fileChooser.setDialogTitle("Specify a directory to save");
/* 440 */               int userSelection = fileChooser.showSaveDialog(pngFrame);
/*     */               
/* 442 */               if (userSelection == 0) {
/* 443 */                 File fileToSave = fileChooser.getSelectedFile();
/*     */                 
/* 445 */                 int firstFrame = 0, lastFrame = 0;
/* 446 */                 if (ProcessTrackMateXml.displayer.getImp().getNFrames() > 1) {
/* 447 */                   firstFrame = Math.max(1, Math.min(IJ.getImage().getNFrames(), 1));
/* 448 */                   lastFrame = Math.min(IJ.getImage().getNFrames(), 
/* 449 */                       Math.max(IJ.getImage().getNFrames(), 1));
/*     */                 } 
/* 451 */                 if (ProcessTrackMateXml.displayer.getImp().getNSlices() > 1) {
/* 452 */                   firstFrame = Math.max(1, Math.min(IJ.getImage().getNSlices(), 1));
/* 453 */                   lastFrame = Math.min(IJ.getImage().getNSlices(), 
/* 454 */                       Math.max(IJ.getImage().getNSlices(), 1));
/*     */                 } 
/*     */                 
/* 457 */                 Rectangle bounds = ProcessTrackMateXml.displayer.getImp().getCanvas().getBounds();
/* 458 */                 int width = bounds.width;
/* 459 */                 int height = bounds.height;
/* 460 */                 int nCaptures = lastFrame - firstFrame + 1;
/* 461 */                 ImageStack stack = new ImageStack(width, height);
/* 462 */                 int channel = ProcessTrackMateXml.displayer.getImp().getChannel();
/* 463 */                 int slice = ProcessTrackMateXml.displayer.getImp().getSlice();
/* 464 */                 ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(true);
/* 465 */                 for (int frame = firstFrame; frame <= lastFrame; frame++) {
/*     */                   
/* 467 */                   ProcessTrackMateXml.displayer.getImp().setPositionWithoutUpdate(channel, slice, 
/* 468 */                       frame);
/* 469 */                   BufferedImage bi = new BufferedImage(width, height, 2);
/* 470 */                   ProcessTrackMateXml.displayer.getImp().getCanvas().paint(bi.getGraphics());
/* 471 */                   ColorProcessor cp = new ColorProcessor(bi);
/* 472 */                   int index = ProcessTrackMateXml.displayer.getImp().getStackIndex(channel, slice, 
/* 473 */                       frame);
/* 474 */                   stack.addSlice(
/* 475 */                       ProcessTrackMateXml.displayer.getImp().getImageStack().getSliceLabel(index), 
/* 476 */                       (ImageProcessor)cp);
/*     */                 } 
/* 478 */                 ProcessTrackMateXml.displayer.getImp().getCanvas().hideZoomIndicator(false);
/* 479 */                 ImagePlus capture = new ImagePlus("TrackMate capture of " + 
/* 480 */                     ProcessTrackMateXml.displayer.getImp().getShortTitle(), stack);
/* 481 */                 FirstWizardPanel.access$0(ProcessTrackMateXml.displayer.getImp(), capture);
/* 482 */                 IJ.saveAs(capture, "Tiff", String.valueOf(fileToSave.getAbsolutePath()) + File.separator + 
/* 483 */                     "Capture Overlay for " + IJ.getImage().getShortTitle());
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/* 488 */     FirstWizardPanel.this.pngThread.start();
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/FirstWizardPanel$4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */