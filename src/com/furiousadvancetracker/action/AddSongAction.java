/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker.action;

import com.furiousadvancetracker.MainWindow;
import com.furiousadvancetracker.manager.IManager;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cyril
 */
public class AddSongAction extends AbstractAction {

    private JList list;
    private IManager manager;
    private MainWindow from;
    private File lastDirectory;

    public AddSongAction(String text, JList list, IManager manager, MainWindow from) {
        super(text);
        this.list = list;
        this.manager = manager;
        this.from = from;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(lastDirectory);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "FAT song (*.fat)", "fat");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnVal = fileChooser.showOpenDialog(list);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            lastDirectory = fileChooser.getSelectedFile().getParentFile();
            int[] selected = this.list.getSelectedIndices();
            if (selected.length!=0){
                this.manager.eatSongFile(selected[0], fileChooser.getSelectedFile());
            } else {
                this.manager.eatSongFile(fileChooser.getSelectedFile());
            }
            
            from.updateList(manager);
        }
    }
}
