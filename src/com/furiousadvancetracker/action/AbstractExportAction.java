/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker.action;

import com.furiousadvancetracker.manager.IManager;
import com.furiousadvancetracker.manager.SavFileException;
import com.furiousadvancetracker.manager.SongEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.AbstractAction;
import javax.swing.JList;

/**
 *
 * @author cyril
 */
public abstract class AbstractExportAction extends AbstractAction {

    protected JList list;
    protected File lastDirectory;
    protected IManager manager;

    public AbstractExportAction(String text) {
        super(text);
    }

    private File checkExtension(File file, String extension) {
        String fileName = file.getAbsolutePath();
        if (!fileName.endsWith(extension)) {
            file = new File(fileName + extension);
        }
        return file;
    }

    private void writeData(SongEntry song, File inFile) throws SavFileException {
        RandomAccessFile writeFile = null;
        try {
            //SongEntry song = manager.getSongs().get(index);
            writeFile = new RandomAccessFile(inFile, "rw");
            writeFile.write(song.getData());

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AbstractExportAction.class.getName()).log(Level.SEVERE, null, ex);
            throw new SavFileException("Le fichier est introuvable", ex);
        } catch (IOException ex) {
            Logger.getLogger(AbstractExportAction.class.getName()).log(Level.SEVERE, null, ex);
            throw new SavFileException("Erreur lors de l'écriture sur le fichier", ex);
        } finally {
            if (writeFile != null) {
                try {
                    writeFile.close();
                } catch (IOException ex) {
                    Logger.getLogger(AbstractExportAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    protected void exportOne(int index, File file) throws SavFileException {

        file = checkExtension(file, ".fat");
        SongEntry song = manager.getSongs().get(index);

        if (song.getData() != null) {
            writeData(song, file);
        }

    }
    
    protected void exportAll (File zipFile) throws SavFileException{
        int[] indices = new int[16];
        for (int i = 0; i < 16; i++){
            indices[i] = i;
        }
        
        exportSeveral(indices, zipFile);
    
    }
    
    protected void exportSeveral(int[] indices, File zipFile) throws SavFileException {

        zipFile = checkExtension(zipFile, ".zip");

        ZipOutputStream out = null;
        try {
            // Create a buffer for reading the files
            byte[] buf = new byte[2048];
            out = new ZipOutputStream(new FileOutputStream(zipFile));

            int n = 0;
            while (n < indices.length) {

                SongEntry song = manager.getSongs().get(n);
                File songFile = new File(song.getName() + ".fat");
                if (song.getData() != null) {
                    writeData(song, songFile);
                    FileInputStream in = new FileInputStream(songFile);
                    out.putNextEntry(
                            new ZipEntry(song.getName() + ".fat"));

                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.closeEntry();
                    in.close();
                }
                songFile.delete();

                n++;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AbstractExportAction.class.getName()).log(Level.SEVERE, null, ex);
            throw new SavFileException("Le fichier est introuvable", ex);
        } catch (IOException e) {
            Logger.getLogger(AbstractExportAction.class.getName()).log(Level.SEVERE, null, e);
            throw new SavFileException("Erreur lors de la lecture du fichier ZIP", e);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(AbstractExportAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
