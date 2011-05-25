/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker.action;

import com.furiousadvancetracker.MainWindow;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cyril
 */
public class MenuOpenv020SavAction extends AbstractAction {

    private MainWindow from;

    private File lastDirectory;
    
    public MenuOpenv020SavAction(MainWindow window, String text) {
        super(text);
        this.from = window;
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "GameboyAdvance SAV [FAT formated] v0.2.0", "sav");
        chooser.setFileFilter(filter);
        
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(lastDirectory);
        
        int returnVal = chooser.showOpenDialog(from);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            lastDirectory = chooser.getSelectedFile().getParentFile();
            from.openv020SavFile(chooser.getSelectedFile().getAbsolutePath());
        }

    }
}
