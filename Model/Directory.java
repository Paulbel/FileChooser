package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sinelnikov on 22.05.2017.
 */
public class Directory {
    private String fullPath;
    private List<Directory> subDirectoryList;
    private List<File> files;
    private boolean childListCreated;

    public Directory(String fullPath) {
        this.fullPath = fullPath;
        childListCreated = false;
    }


    public String getFullPath() {
        return fullPath;
    }

    public String toString() {
        File folder = new File(fullPath);
        if(folder.getName().equals("")){
            return fullPath;
        }
        return folder.getName();
    }

    public void createChildList() {
        subDirectoryList = new ArrayList<>();
        files = new ArrayList<>();
        File[] folders;
        if (fullPath.equals(ChooserConsts.NAME_TREE_ROOT)) {
            folders = File.listRoots();
        } else {
            File folder = new File(fullPath);
            folders = folder.listFiles();
        }
        if (folders != null) {
            for (File file : folders) {
                if (file.isDirectory()) {
                    subDirectoryList.add(new Directory(file.toString()));
                } else if(file.isFile()){
                    files.add(file);
                }
            }
        }
        childListCreated = true;
    }

    public Directory getDirectoryByIndex(int index) {
        if(!childListCreated){
            createChildList();
        }
        return subDirectoryList.get(index);
    }

    public int getSubdirectotiesNumber(){
        if(!childListCreated){
            createChildList();
        }
        return subDirectoryList.size();
    }


    public boolean isLeaf() {

        File file = new File(fullPath.toString());
        return file.isFile();
    }

    public int getIndexOfChild(Object node) {
        if(!childListCreated){
            createChildList();
        }
        Directory son = (Directory) node;
        return subDirectoryList.indexOf(son);
    }

    public List<Directory> getSubDirectoryList() {
        if(!childListCreated){
            createChildList();
        }
        return subDirectoryList;
    }


    public List<File> getFiles() {
        if(!childListCreated){
            createChildList();
        }
        return files;
    }
}
