package View;

import Controller.MyFileChooser;
import Model.Directory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Sinelnikov on 26.05.2017.
 */
public class TableDirectoryViewPanel extends DirectoryViewPanel {
    private JTable table;
    private MyFileChooser controller;
    public TableDirectoryViewPanel(Directory directory, MyFileChooser controller){
        super(directory);
        this.controller = controller;
        this.setLayout(new BorderLayout());
    }

    @Override
    public void repaint() {
        if(directory!=null) {
            this.removeAll();
            DirectoryTableModel directoryTableModel = new DirectoryTableModel(directory,
                    this.getFileListSpecificExtension(directory));
            table = new JTable(directoryTableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(this.getPreferredSize());
            this.add(scrollPane, BorderLayout.CENTER);
            table.addMouseListener(new TableOpenFolderListener(this, controller));
        }
    }


    public JTable getTable() {
        return table;
    }
}
