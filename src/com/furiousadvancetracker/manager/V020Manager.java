/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cyril
 */
public class V020Manager implements IManager {

    private static int TABLE_SIZE = 3;
    private List<SongEntry> songs;
    private static int FIRST_SONG_OFFSET = 0x0040;

    public void eatFile(File file) {
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile(file, "rw");

            songs = new ArrayList<SongEntry>();

            readAllocationTable(reader);
            readDatas(reader);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void delete(int index) {
        SongEntry song = songs.get(index);
        song.setData(null);
    }

    public void deleteAll() {
        for (int i = 0; i < songs.size(); i++) {
            delete(i);
        }
    }

    public List<SongEntry> getSongs() {
        return songs;
    }

    public void save(File file) {
        RandomAccessFile writer = null;
        try {
            writer = new RandomAccessFile(file, "rw");

            // reinit de la table
            int cpt = 0;
            int offset = 0;
            while (cpt < TABLE_SIZE) {

                writer.writeChar(offset);
                writer.writeChar(0);
                cpt++;
                offset += 4;
            }

            int offsetTable = 0;
            int offsetSong = FIRST_SONG_OFFSET;

            for (SongEntry song : songs) {

                // ecriture dans la table d'alloc
                writer.seek(offsetTable);
                offsetTable += 4;
                writer.writeChar(offsetSong);
                writer.writeChar(song.getSize());

                // ecriture de la song
                writer.seek(offsetSong);
                offsetSong += song.getSize();
                writer.write(song.getData());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void readAllocationTable(RandomAccessFile file) throws IOException {
        int cpt = 0;

        while (cpt < TABLE_SIZE) {
            char offset = file.readChar();
            char size = file.readChar();

            songs.add(new SongEntry(cpt, (int) offset, (int) size));

            cpt++;
        }
    }

    public void eatSongFile(int index, File file) {
        byte[] data = readOneSongFile(file);
        if (data != null) {
            SongEntry song = this.songs.get(index);
            song.setData(data);
            song.setSize(data.length);
        }
    }

    public void eatSongFile(File file) {
        byte[] data = readOneSongFile(file);
        if (data != null) {
            for (SongEntry song : songs) {
                if (song.getData() == null) {
                    song.setData(data);
                    song.setSize(data.length);
                    return;
                }
            }
        }
    }

    private byte[] readOneSongFile(File file) {
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile(file, "rw");
            int length = (int) reader.length();
            byte[] data = new byte[length];
            reader.readFully(data);

            return data;



        } catch (FileNotFoundException ex) {
            Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();


                } catch (IOException ex) {
                    Logger.getLogger(V020Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    private void readDatas(RandomAccessFile file) throws IOException {

        char initOffset = 0;

        for (SongEntry entry : songs) {
            if (entry.getOffset() != initOffset) {
                file.seek(entry.getOffset());
                byte[] data = new byte[entry.getSize()];
                file.read(data);
                entry.setData(data);


            }
            initOffset += 4;
        }
    }
}
