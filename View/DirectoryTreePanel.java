package View;

import Controller.MyFileChooser;
import Model.Directory;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;


/**
 * Created by Sinelnikov on 23.05.2017.
 */
public class DirectoryTreePanel extends JPanel{
    private JTree tree;
    private JScrollPane scrollPane;

    MyFileChooser fileChooser;

    public DirectoryTreePanel(MyFileChooser myFileChooser, JTree directoryTree){
        tree = new JTree(directoryTree.getModel());
        this.fileChooser = myFileChooser;
        this.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        this.setLayout(new BorderLayout());
        scrollPane = new JScrollPane(tree);
        this.add(scrollPane, BorderLayout.CENTER);


        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                Directory node = (Directory) e.getPath().getLastPathComponent();
                myFileChooser.changeFolder(node);
                //System.out.println("Folder clicked: "+tree.getPath));
            }
        });




        //tree.add;



    }

/*    @Override
    public void setPreferredSize(Dimension preferredSize) {
        int width = preferredSize.width;
        int height = preferredSize.height-50;
        scrollPane.setPreferredSize(new Dimension(width, height));
    }*/
}
