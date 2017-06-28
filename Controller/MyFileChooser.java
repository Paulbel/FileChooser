package Controller;

import Model.ChooserConsts;
import Model.Directory;
import View.ChooserDialog;

import java.io.File;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Sinelnikov on 03.06.2017.
 */
public class MyFileChooser {
    private ChooserDialog chooserDialog;
    private File selectedFile;
    private int option;
    private ReentrantLock lock;
    private Condition condition;


    public MyFileChooser() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
        option = ChooserConsts.WAITOPTION;
        selectedFile = null;
        //asdad
    }

    public void changeFolder(Directory directory) {
        chooserDialog.setDirectory(directory);
        chooserDialog.revalidate();
    }

    public int showDialog() {
        chooserDialog = new ChooserDialog(this, lock, condition);
        lock.lock();
        try {
            while (option == ChooserConsts.WAITOPTION) {
                condition.await();
            }
        } catch (InterruptedException e) {

        } finally {
            lock.unlock();
        }
        return option;
    }


    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
        if (selectedFile != null) {
            chooserDialog.getFileNameTextField().setText(selectedFile.getName());
        } else {
            chooserDialog.getFileNameTextField().setText("");
        }
    }

    public void setOption(int option) {
        this.option = option;
    }

    public File getSelectedFile() {
        return selectedFile;
    }
}
