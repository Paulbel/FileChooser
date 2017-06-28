package View; /**
 * Created by Sinelnikov on 05.06.2017.
 */

import Controller.MyFileChooser;
import Model.Directory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;


public class OpenFolderListener implements ListSelectionListener {
    private JList listVisibly;
    private Directory directory;
    MyFileChooser controller;

    public OpenFolderListener(JList listVisibly, Directory directory, MyFileChooser controller){
        this.controller = controller;
        this.directory = directory;
        this.listVisibly = listVisibly;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String name = (String)listVisibly.getSelectedValue();
        File folder =new  File(name);
        if(folder.isDirectory()){
            controller.changeFolder(new Directory(name));
        } else if(folder.isFile()){
            controller.setSelectedFile(folder);
        }
/*        if (name!=null){
            File file = new File(name);
            if (file.isDirectory()){
                directoryDisplayPanel.setCurrentDirectory(name);
            } else {
                directoryDisplayPanel.getChooseFilePanel().changeSelectedFile(file.getName());
            }
        }*/
    }
}