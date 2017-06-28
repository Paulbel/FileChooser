package View;

import Model.ChooserConsts;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

/**
 * Created by Sinelnikov on 03.06.2017.
 */
public class CellRendererDirectory extends JLabel implements ListCellRenderer {
    int type;

    CellRendererDirectory(int type) {
        super();
        this.type = type;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof String) {
            String name = (String) value;
            File file = new File(name);
            if (file.getName().equals("")) {
                name = file.getAbsolutePath();
            } else {
                name = file.getName();
            }
            switch (type) {
                case ChooserConsts.FOLDERVIEWTYPE: {
                    if (file.isFile()) {
                        setIcon(new ImageIcon("image/file.png"));
                    } else {
                        setIcon(new ImageIcon("image/folder.png"));
                    }
                    break;
                }
                case ChooserConsts.LISTVIEWTYPE: {
                    Icon ico = FileSystemView.getFileSystemView().getSystemIcon(file);
                    setIcon(ico);
                }
            }
            setText(name);
            if (isSelected) {
                setBackground(Color.lightGray);
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setOpaque(true);
        }
        return this;
    }
}


