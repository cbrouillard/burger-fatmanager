/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker;

import com.furiousadvancetracker.action.AddSongAction;
import com.furiousadvancetracker.action.DeleteAllAction;
import com.furiousadvancetracker.action.DeleteSelectedAction;
import com.furiousadvancetracker.action.ExportAllAction;
import com.furiousadvancetracker.action.ExportSelectedAction;
import com.furiousadvancetracker.action.MenuExitAction;
import com.furiousadvancetracker.action.MenuOpenv020SavAction;
import com.furiousadvancetracker.action.MenuSavev020SavAction;
import com.furiousadvancetracker.manager.IManager;
import com.furiousadvancetracker.manager.SongEntry;
import com.furiousadvancetracker.manager.V020Manager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 *
 * @author cyril
 */
public class MainWindow extends JFrame {

    private IManager v020Manager;
    private JLabel actualFileTitle;
    private JList songList;
    private JButton addSong;
    private JButton exportSelected;
    private JButton exportAll;
    private JButton deleteSelected;
    private JButton clearAll;

    public MainWindow() {

        v020Manager = new V020Manager();

        setSize(400, 350);
        setTitle("Burger - [FAT] Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setResizable(false);

        setContentPane(buildContentPane());
        addMenu();
    }

    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");

        JMenuItem openSav = new JMenuItem(new MenuOpenv020SavAction(this, "Open v0.2.x .sav"));
        openSav.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        menuFile.add(openSav);

        menuFile.add(new JSeparator());

        JMenuItem saveSav = new JMenuItem(new MenuSavev020SavAction("Save v0.2.x .sav", this, v020Manager));
        saveSav.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        menuFile.add(saveSav);

        menuFile.add(new JSeparator());

        JMenuItem exit = new JMenuItem(new MenuExitAction());
        exit.setText("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke("ctrl W"));
        menuFile.add(exit);

        menuBar.add(menuFile);

        setJMenuBar(menuBar);
    }

    private Container buildContentPane() {
        JPanel panel = new JPanel();

        BorderLayout layout = new BorderLayout();
        panel.setLayout(layout);

        actualFileTitle = new JLabel("File: no file selected");
        panel.add(actualFileTitle, BorderLayout.SOUTH);

        songList = new JList();
        songList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane scrollPaneForList = new JScrollPane(songList);
        panel.add(scrollPaneForList, BorderLayout.CENTER);

        JPanel east = new JPanel();
        east.setLayout(new BorderLayout());
        
        JPanel btnsActions = new JPanel(new GridLayout(0, 1));
        
        exportAll = new JButton("Export all");
        exportAll.setEnabled(false);
        btnsActions.add(exportAll);

        exportSelected = new JButton("Export selected");
        exportSelected.setEnabled(false);
        btnsActions.add(exportSelected);


        deleteSelected = new JButton("Delete selected");
        deleteSelected.setEnabled(false);
        btnsActions.add(deleteSelected);

        clearAll = new JButton("Delete all");
        clearAll.setEnabled(false);
        btnsActions.add(clearAll);

        addSong = new JButton("Add a song");
        addSong.setEnabled(false);
        btnsActions.add(addSong);

        ImageIcon myImage = new ImageIcon(MainWindow.class.getResource("furicat.png"));
        JLabel myLabel = new JLabel();
        myLabel.setHorizontalAlignment(JLabel.CENTER);
        myLabel.setIcon(myImage);
        
        east.add(myLabel, BorderLayout.SOUTH);
        east.add(btnsActions);
        
        panel.add(east, BorderLayout.EAST);

        return panel;
    }

    public void openv020SavFile(String filePath) {
        actualFileTitle.setText("File: " + filePath);
        v020Manager.eatFile(new File(filePath));

        updateList(v020Manager);

        addSong.setEnabled(true);
        addSong.setAction(new AddSongAction("Add a song", songList, v020Manager, this));
        exportSelected.setEnabled(true);
        exportSelected.setAction(new ExportSelectedAction("Export selected", this.songList, this.v020Manager));
        exportAll.setEnabled(true);
        exportAll.setAction(new ExportAllAction("Export all", this.songList, this.v020Manager));
        deleteSelected.setEnabled(true);
        deleteSelected.setAction(new DeleteSelectedAction("Delete selected", this.songList, this.v020Manager, this));
        clearAll.setEnabled(true);
        clearAll.setAction(new DeleteAllAction("Delete all", songList, v020Manager, this));
    }

    public void updateList(IManager songManager) {
        // modif's sur l'interface
        DefaultListModel model = new DefaultListModel();
        for (SongEntry song : songManager.getSongs()) {
            if (song.getName() != null) {
                model.addElement(song.getIndex() + " - " + song.getName() + " (" + song.getSize() + " bytes)");
            } else {
                model.addElement(song.getIndex() + " - EMPTY");
            }
        }
        songList.setModel(model);
    }
}
