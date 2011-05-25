/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furiousadvancetracker;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author cyril
 */
public class Burger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        try {
//            UIManager.setLookAndFeel(
//                    UIManager.getSystemLookAndFeelClassName());
//        } catch (UnsupportedLookAndFeelException e) {
//            // handle exception
//        } catch (ClassNotFoundException e) {
//            // handle exception
//        } catch (InstantiationException e) {
//            // handle exception
//        } catch (IllegalAccessException e) {
//            // handle exception
//        }

        MainWindow app = new MainWindow();
        app.setVisible(true);

    }
}
