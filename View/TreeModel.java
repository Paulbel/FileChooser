package View;

import Model.ChooserConsts;
import Model.Directory;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

/**
 * Created by Sinelnikov on 22.05.2017.
 */
public class TreeModel implements javax.swing.tree.TreeModel {
    private Directory rootNode;

    public TreeModel() {
        rootNode = new Directory(ChooserConsts.NAME_TREE_ROOT);
    }

    @Override
    public int getChildCount(Object parent) {
        Directory directoryNode = (Directory) parent;
        int childCount = directoryNode.getSubdirectotiesNumber();
        return childCount;
    }


    @Override
    public Object getRoot() {
        return rootNode;
    }


    @Override
    public Object getChild(Object parent, int index) {
        Directory parentDirectory = (Directory) parent;
        return parentDirectory.getDirectoryByIndex(index);
        //asda
    }

    @Override
    public boolean isLeaf(Object node) {
        Directory file = (Directory) node;
        return file.isLeaf();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        Directory parentFolder = (Directory) parent;
        return parentFolder.getIndexOfChild(child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }
}
