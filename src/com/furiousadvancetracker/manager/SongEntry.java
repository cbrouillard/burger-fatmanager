/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker.manager;

/**
 *
 * @author cyril
 */
public class SongEntry {

    private String name;
    private int index;
    private int offset;
    private int size;
    private byte[] data;

    public SongEntry(int index, int offset, int size) {
        this.offset = offset;
        this.size = size;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setData(byte[] data) {
        this.data = data;
        if (data!=null) {
            byte[] strName = new byte[10];
            System.arraycopy(data, 0, strName, 0, 9);

            this.name = new String(strName);
        } else {
            this.name = null;
        }
    }

    public byte[] getData() {
        return data;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    
}
