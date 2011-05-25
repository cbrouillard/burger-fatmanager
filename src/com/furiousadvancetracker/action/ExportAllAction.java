/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker.action;

import com.furiousadvancetracker.manager.IManager;
import com.furiousadvancetracker.manager.SavFileException;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author cyril
 */
public class ExportAllAction extends AbstractExportAction {

    public ExportAllAction(String text, JList list, IManager manager) {
        super(text);
        this.list = list;
        this.manager = manager;
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(lastDirectory);

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "FAT songs package (*.zip)", "zip");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnVal = fileChooser.showSaveDialog(list);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // enregistrement du fichier FAT
            lastDirectory = fileChooser.getSelectedFile().getParentFile();
            try {
                super.exportAll(fileChooser.getSelectedFile());
            } catch (SavFileException ex) {
                Logger.getLogger(ExportSelectedAction.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(list, "Error during the file writing process. Sorry -_-", "Error ...", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
