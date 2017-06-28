package View;

import Controller.MyFileChooser;
import Model.ChooserConsts;
import Model.Directory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Created by Sinelnikov on 03.06.2017.
 */
public class DirectoryDisplayPanel extends DirectoryViewPanel {
    DefaultListModel listModel;
    MyFileChooser controller;
    CellRendererDirectory cellRendererDirectory;
    int type;

    DirectoryDisplayPanel(Directory directory, int type, MyFileChooser myFileChooser) {
        super(directory);
        controller =myFileChooser;
        this.type = type;
        this.setLayout(new BorderLayout());
        cellRendererDirectory = new CellRendererDirectory(type);
    }

    @Override
    public void repaint() {

        if (directory != null) {
            this.removeAll();
            listModel = new DefaultListModel();
            List<Directory> directories = directory.getSubDirectoryList();
            for (Directory directory : directories) {
                listModel.addElement(directory.getFullPath());
            }
            for (File file : getFileListSpecificExtension(directory)) {
                listModel.addElement(file.getAbsolutePath());
            }

            JList listVisibly = new JList(listModel);

            switch (type) {
                case ChooserConsts.LISTVIEWTYPE: {
                    listVisibly.setLayoutOrientation(JList.VERTICAL);
                    break;
                }
                case ChooserConsts.FOLDERVIEWTYPE: {
                    listVisibly.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                    listVisibly.setVisibleRowCount(50);
                }
            }
            listVisibly.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listVisibly.setCellRenderer(cellRendererDirectory);

            //listVisibly.addMouseListener(new ListSelectedListener(listVisibly,consistDirectoryPanel));
            //listVisibly.addListSelectionListener(new ListSelectedListener(listVisibly,consistDirectoryPanel));

            JScrollPane scroll = new JScrollPane(listVisibly);

            add(scroll, BorderLayout.CENTER);
            listVisibly.addListSelectionListener(new OpenFolderListener(listVisibly,directory, controller));
        }

    }

}
