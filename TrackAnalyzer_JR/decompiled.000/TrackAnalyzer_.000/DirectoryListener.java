import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

public class DirectoryListener implements ActionListener {
   String title;
   TextField text;
   int fileSelectionMode;

   public DirectoryListener(String title, TextField text) {
      this(title, text, 1);
   }

   public DirectoryListener(String title, TextField text, int fileSelectionMode) {
      this.title = title;
      this.text = text;
      this.fileSelectionMode = fileSelectionMode;
   }

   public void actionPerformed(ActionEvent e) {
      File directory;
      for(directory = new File(this.text.getText()); directory != null && !directory.exists(); directory = directory.getParentFile()) {
      }

      JFileChooser fc = new JFileChooser(directory);
      fc.setFileSelectionMode(this.fileSelectionMode);
      fc.showOpenDialog((Component)null);
      File selFile = fc.getSelectedFile();
      if (selFile != null) {
         this.text.setText(selFile.getAbsolutePath());
      }

   }
}
