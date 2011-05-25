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
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cyril
 */
public class MenuSavev020SavAction extends AbstractAction{

    private MainWindow from;
    private IManager manager;
    
    private File lastDirectory;
    
    public MenuSavev020SavAction(String text, MainWindow from, IManager manager) {
        super(text);
        this.from = from;
        this.manager = manager;
    }
    
    public void actionPerformed(ActionEvent e) {
        if (manager.getSongs()!=null){
            
            JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "GameboyAdvance SAV [FAT formated] v0.2.0", "sav");
        chooser.setFileFilter(filter);
        
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setCurrentDirectory(lastDirectory);
        
        int returnVal = chooser.showSaveDialog(from);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            lastDirectory = chooser.getSelectedFile().getParentFile();
            //from.openv020SavFile(chooser.getSelectedFile().getAbsolutePath());
            manager.save(chooser.getSelectedFile());
        }
            
        } else {
            JOptionPane.showMessageDialog(from, "There are no datas to save.", "No file", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
