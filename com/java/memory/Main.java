package com.java.memory;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

public static void main(String[] args) {
    
    Initialisierung start = new Initialisierung();
    SpielFeld gitter = new SpielFeld();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                start.einstellungensetzen(gitter.getContentPane());
                start.pack();
                start.setVisible(true);

                gitter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gitter.fuegeAllesZurOberflaecheHinzu(gitter.getContentPane());
                gitter.pack();
                gitter.setVisible(true);
            }
        });
    }

    public static void beiFehlerSchließen() {
        JOptionPane.showMessageDialog(null, "Leider ist ein Fehler aufgetaucht. Bitte überprüfe den Code!");
        System.exit(0);
        //ImageIcon icon = new ImageIcon("Fehler.jpg");
        //JOptionPane.showMessageDialog(null, "Eine Meldung", "Meldung", JOptionPane.INFORMATION_MESSAGE, icon);
    }
    
}