/*    */ import java.awt.TextField;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.io.File;
/*    */ import javax.swing.JFileChooser;
/*    */ 
/*    */ public class DirectoryListener
/*    */   implements ActionListener {
/*    */   String title;
/*    */   TextField text;
/*    */   int fileSelectionMode;
/*    */   
/*    */   public DirectoryListener(String title, TextField text) {
/* 14 */     this(title, text, 1);
/*    */   }
/*    */   
/*    */   public DirectoryListener(String title, TextField text, int fileSelectionMode) {
/* 18 */     this.title = title;
/* 19 */     this.text = text;
/* 20 */     this.fileSelectionMode = fileSelectionMode;
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent e) {
/* 24 */     File directory = new File(this.text.getText());
/* 25 */     while (directory != null && !directory.exists()) {
/* 26 */       directory = directory.getParentFile();
/*    */     }
/* 28 */     JFileChooser fc = new JFileChooser(directory);
/* 29 */     fc.setFileSelectionMode(this.fileSelectionMode);
/*    */     
/* 31 */     fc.showOpenDialog(null);
/* 32 */     File selFile = fc.getSelectedFile();
/* 33 */     if (selFile != null)
/* 34 */       this.text.setText(selFile.getAbsolutePath()); 
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/DirectoryListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */