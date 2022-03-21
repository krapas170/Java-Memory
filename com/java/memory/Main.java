package com.java.memory;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    static Menue start = new Menue();

    public static void main(String[] args) {

        boolean MenueIsActive = start.isActive();

        runMenue();

        while (MenueIsActive == true) {
            try {
                Thread.sleep(100);
                MenueIsActive = start.isActive();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                SpielFeld gitter = new SpielFeld();

                gitter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gitter.fuegeAllesZurOberflaecheHinzu(gitter.getContentPane());
                gitter.pack();
                gitter.setVisible(true);
            }
        });
    }

    public static void runMenue() {
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.fuegeAllesZumMenueHinzu(start.getContentPane());
        start.pack();
        start.setVisible(true);
    }

    public static void beiFehlerSchließen() {
        JOptionPane.showMessageDialog(null, "Leider ist ein Fehler aufgetaucht. Bitte überprüfe den Code!");
        System.exit(0);
        // ImageIcon icon = new ImageIcon("Fehler.jpg");
        // JOptionPane.showMessageDialog(null, "Eine Meldung", "Meldung",
        // JOptionPane.INFORMATION_MESSAGE, icon);
    }

}