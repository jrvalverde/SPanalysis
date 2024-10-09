import java.awt.Component;
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.awt.event.ActionListener;

// 
// Decompiled by Procyon v0.5.36
// 

public class DirectoryListener implements ActionListener
{
    String title;
    TextField text;
    int fileSelectionMode;
    
    public DirectoryListener(final String title, final TextField text) {
        this(title, text, 1);
    }
    
    public DirectoryListener(final String title, final TextField text, final int fileSelectionMode) {
        this.title = title;
        this.text = text;
        this.fileSelectionMode = fileSelectionMode;
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        File directory;
        for (directory = new File(this.text.getText()); directory != null && !directory.exists(); directory = directory.getParentFile()) {}
        final JFileChooser fc = new JFileChooser(directory);
        fc.setFileSelectionMode(this.fileSelectionMode);
        fc.showOpenDialog(null);
        final File selFile = fc.getSelectedFile();
        if (selFile != null) {
            this.text.setText(selFile.getAbsolutePath());
        }
    }
}
