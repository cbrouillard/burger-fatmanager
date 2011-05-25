/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker.action;

import com.furiousadvancetracker.MainWindow;
import com.furiousadvancetracker.manager.IManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author cyril
 */
public class DeleteAllAction extends AbstractAction {

    private JList list;
    private IManager manager;
    private MainWindow from;

    public DeleteAllAction(String text, JList list, IManager manager, MainWindow from) {
        super(text);
        this.list = list;
        this.manager = manager;
        this.from = from;
    }

    public void actionPerformed(ActionEvent e) {
        int returnVal = JOptionPane.showConfirmDialog(list, "Confirm deletion ? Deletion will not appear in your file until you save it.", "Please confirm your action", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (returnVal == JOptionPane.OK_OPTION) {
            manager.deleteAll();
            from.updateList(manager);
        }
    }
}
