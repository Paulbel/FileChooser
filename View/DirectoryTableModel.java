package View;

import Model.ChooserConsts;
import Model.Directory;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Sinelnikov on 26.05.2017.
 */
public class DirectoryTableModel extends AbstractTableModel {
    private Directory directory;
    private List <File> fileList;
    public DirectoryTableModel(Directory directory, List<File> fileList) {
        this.fileList = fileList;
        this.directory = directory;
    }

    @Override
    public int getRowCount() {
        return directory.getSubdirectotiesNumber()+fileList.size();
    }

    @Override
    public int getColumnCount() {
        return ChooserConsts.FILEPARTS + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        File file;
        String extension;
        String size;
        String name;
        if ((rowIndex + 1) > directory.getSubdirectotiesNumber()) {
            int fileIndex = rowIndex - directory.getSubdirectotiesNumber();
            file = fileList.get(fileIndex);
            extension = getFileExtension(file);
            size = String.valueOf(file.length());
            name = file.getName();
        } else {
            Directory subDirectory = directory.getDirectoryByIndex(rowIndex);
            file = new File(subDirectory.getFullPath());
            extension = "Folder";
            size = "<folder>";
            //size = String.valueOf(folderSize(file));
            name = subDirectory.toString();
        }
        switch (columnIndex) {
            case ChooserConsts.NAMEINDEX:
                return name;
            case ChooserConsts.TYPEINDEX:
                return extension;
            case ChooserConsts.DATEINDEX:
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                return sdf.format(file.lastModified());
            case ChooserConsts.SIZEINDEX:
                return size;
        }
        return null;

    }


    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }


    public static long folderSize(File directory) {
        long length = 0;
        try {

            for (File file : directory.listFiles()) {
                if (file.isFile())
                    length += file.length();
                else
                    length += folderSize(file);
            }
        }
        catch (NullPointerException e){
        }
        return length;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case ChooserConsts.NAMEINDEX: {
                return "Name:";
            }
            case ChooserConsts.TYPEINDEX: {
                return "Extension:";
            }
            case ChooserConsts.SIZEINDEX: {
                return "Size:";
            }
            case ChooserConsts.DATEINDEX: {
                return "Date:";
            }
        }
        return null;
    }
}
