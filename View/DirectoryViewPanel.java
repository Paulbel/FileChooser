package View;

import Model.ChooserConsts;
import Model.Directory;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinelnikov on 26.05.2017.
 */
public class DirectoryViewPanel extends JPanel {
    protected Directory directory;
    protected String extension;

    public DirectoryViewPanel(Directory directory) {
        extension = ChooserConsts.FILTREXCT[0];
        this.directory = directory;
    }

    public void changeDirectory(Directory directory) {
        this.directory = directory;
        repaint();
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public List<File> getFileListSpecificExtension(Directory someDirectory) {
        List<File> specialFiles = new ArrayList<>();
        for (File file : someDirectory.getFiles()) {
            String extension = "";
            String fileName = file.getName();
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            }
            if(extension.equals(this.extension)||this.extension.equals("all")){
                specialFiles.add(file);
            }
        }
        return specialFiles;
    }



/*    @Override
    public Dimension getPreferredSize() {
        return new Dimension(400, height);
    }*/
}

