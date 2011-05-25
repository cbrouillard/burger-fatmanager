/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.furiousadvancetracker.manager;

import java.io.File;
import java.util.List;

/**
 *
 * @author cyril
 */
public interface IManager {

    void eatFile (File file);
    
    List<SongEntry> getSongs();

    public void delete(int index);

    public void deleteAll();
    
    public void eatSongFile (int index, File file);
    
    public void eatSongFile (File file);
    
    public void save (File file);
}
