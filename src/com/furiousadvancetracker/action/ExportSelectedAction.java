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
public class ExportSelectedAction extends AbstractExportAction {

    public ExportSelectedAction(String text, JList list, IManager manager) {
        super(text);
        this.list = list;
        this.manager = manager;
    }

    public void actionPerformed(ActionEvent e) {

        int[] indices = list.getSelectedIndices();
        if (indices.length != 0) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(lastDirectory);

            if (indices.length == 1) {
                // une seule track
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "FAT song (*.fat)", "fat");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int returnVal = fileChooser.showSaveDialog(list);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    // enregistrement du fichier FAT
                    lastDirectory = fileChooser.getSelectedFile().getParentFile();
                    try {
                        super.exportOne(indices[0], fileChooser.getSelectedFile());
                    } catch (SavFileException ex) {
                        Logger.getLogger(ExportSelectedAction.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(list, "Error during the file writing process. Sorry -_-", "Error ...", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                // plusieurs dans un seul zip
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "FAT songs package (*.zip)", "zip");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);

                int returnVal = fileChooser.showSaveDialog(list);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    // enregistrement du fichier FAT
                    lastDirectory = fileChooser.getSelectedFile().getParentFile();
                    try {
                        super.exportSeveral(indices, fileChooser.getSelectedFile());
                    } catch (SavFileException ex) {
                        Logger.getLogger(ExportSelectedAction.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(list, "Error during the file writing process. Sorry -_-", "Error ...", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(list, "No songs had been selected. Please select at least one.", "Select a song", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
