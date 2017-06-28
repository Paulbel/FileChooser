package View;

import Controller.MyFileChooser;
import Model.ChooserConsts;
import Model.Directory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by Sinelnikov on 04.06.2017.
 */
public class ChooserDialog extends JDialog{
    private JTextField fileNameTextField;
    private DirectoryViewPanel panel;
    private Map<String, DirectoryViewPanel> panels;
    private JDialog dialog;
    private MyFileChooser controller;
    private Directory directory;
    private ReentrantLock lock;
    private Condition condition;



    public ChooserDialog(MyFileChooser controller, ReentrantLock lock, Condition condition) {
        lock.lock();
        this.controller = controller;
        this.setSize(new Dimension(1000, 600));
        this.setResizable(false);
        dialog = this;
        this.lock = lock;
        this.condition = condition;
        directory = new Directory(ChooserConsts.NAME_TREE_ROOT);

        this.setLayout(new BorderLayout());
        Map<String, String> extensions = new HashMap<>();

        for (int index = 0; index < ChooserConsts.FILTRENAME.length && index < ChooserConsts.FILTREXCT.length; index++) {
            extensions.put(ChooserConsts.FILTRENAME[index], ChooserConsts.FILTREXCT[index]);
        }
        fileNameTextField = new JTextField(20);

        JComboBox extensionComboBox = new JComboBox(ChooserConsts.FILTRENAME);

        Vector<String> modes = new Vector<>();
        modes.add(ChooserConsts.DIRECTORYNAME);
        modes.add(ChooserConsts.LISTNAME);
        modes.add(ChooserConsts.TABLENAME);

        ImageIcon homeIcon = new ImageIcon("image/home.png");

        JButton homeButton = new JButton(homeIcon);

        JToolBar southToolBar = new JToolBar(JToolBar.HORIZONTAL);

        southToolBar.setFloatable(false);
        southToolBar.setLayout(new BorderLayout());
        JButton cancelButton = new JButton("Отмена");
        JButton okButton = new JButton("Ок");
        JPanel okCancelPanel = new JPanel();
        okCancelPanel.setLayout(new GridLayout(2, 1));
        okCancelPanel.add(cancelButton);
        okCancelPanel.add(okButton);
        okCancelPanel.setVisible(true);

        JPanel fileSelectionPanel = new JPanel();
        fileSelectionPanel.setLayout(new GridLayout(2, 1));

        fileSelectionPanel.add(fileNameTextField);
        fileSelectionPanel.add(extensionComboBox);
        southToolBar.add(okCancelPanel, BorderLayout.EAST);
        southToolBar.add(fileSelectionPanel, BorderLayout.CENTER);
        JToolBar northToolBar = new JToolBar(JToolBar.HORIZONTAL);


        northToolBar.setFloatable(false);
        northToolBar.setLayout(new BorderLayout());

        JComboBox modeComboBox = new JComboBox(modes);
        modeComboBox.setSelectedItem(ChooserConsts.LISTNAME);


        TreeModel treeModel = new TreeModel();

        JTree directoryTree = new JTree(treeModel);
        DirectoryTreePanel directoryTreePanel = new DirectoryTreePanel(controller, directoryTree);
        directoryTreePanel.setPreferredSize(new Dimension(200, dialog.getPreferredSize().height));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(homeButton);
        buttonPanel.setVisible(true);
        northToolBar.add(buttonPanel, BorderLayout.WEST);
        northToolBar.add(modeComboBox, BorderLayout.EAST);
        this.add(directoryTreePanel, BorderLayout.WEST);
        this.add(southToolBar, BorderLayout.SOUTH);

        panels = new HashMap<>();
        panel = new DirectoryDisplayPanel(directory, ChooserConsts.FOLDERVIEWTYPE, controller);
        panels.put(ChooserConsts.DIRECTORYNAME, panel);
        panel = new DirectoryDisplayPanel(directory, ChooserConsts.LISTVIEWTYPE, controller);
        panels.put(ChooserConsts.LISTNAME, panel);
        panel = new TableDirectoryViewPanel(directory, controller);
        panels.put(ChooserConsts.TABLENAME, panel);

        panel.repaint();
       this.add(panel, BorderLayout.CENTER);
        this.add(northToolBar, BorderLayout.NORTH);
        this.setVisible(true);
        modeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.remove(panel);
                panel = panels.get(modeComboBox.getSelectedItem());
                dialog.add(panel, BorderLayout.CENTER);
                dialog.pack();
                dialog.repaint();
            }
        });



        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changeFolder(new Directory(ChooserConsts.NAME_TREE_ROOT));
            }
        });

        extensionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Map.Entry<String, DirectoryViewPanel> entry : panels.entrySet()) {
                    entry.getValue().setExtension(extensions.get(extensionComboBox.getSelectedItem()));
                    entry.getValue().repaint();
                    revalidate();

                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lock.lock();
                controller.setSelectedFile(new File(directory.getFullPath()+"\\"+fileNameTextField.getText()));
                controller.setOption(ChooserConsts.OKOPTION);
                dialog.dispose();
                dialog = null;
                condition.signalAll();
                lock.unlock();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lock.lock();
                controller.setSelectedFile(null);
                controller.setOption(ChooserConsts.CANCELOPTION);

                dialog.dispose();
                dialog = null;
                condition.signalAll();
                lock.unlock();
            }
        });
        lock.unlock();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 600);
    }

    public JTextField getFileNameTextField() {
        return fileNameTextField;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
        panel.changeDirectory(directory);
    }

    public Directory getDirectory() {
        return directory;
    }
}
