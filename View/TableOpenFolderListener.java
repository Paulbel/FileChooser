package View;

import Controller.MyFileChooser;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Sinelnikov on 06.06.2017.
 */
public class TableOpenFolderListener implements MouseListener {
    TableDirectoryViewPanel tableDirectoryViewPanel;
    MyFileChooser controller;
    TableOpenFolderListener(TableDirectoryViewPanel tableDirectoryViewPanel, MyFileChooser controller){
        this.tableDirectoryViewPanel = tableDirectoryViewPanel;
        this.controller = controller;
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            int rowSelectedNumber  = tableDirectoryViewPanel.getTable().getSelectedRow();
            if(tableDirectoryViewPanel.directory.getSubdirectotiesNumber()<(rowSelectedNumber+1)){
                controller.setSelectedFile(tableDirectoryViewPanel.getFileListSpecificExtension(tableDirectoryViewPanel.directory).get(rowSelectedNumber-tableDirectoryViewPanel.directory.getSubdirectotiesNumber()));
            }
            else {
                controller.changeFolder(tableDirectoryViewPanel.directory.getDirectoryByIndex(rowSelectedNumber));
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
