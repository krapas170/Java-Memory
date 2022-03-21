package com.java.memory;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                Menue start = new Menue();
                SpielFeld gitter = new SpielFeld();

                start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                start.initComponents(start.getContentPane());
                start.setVisible(true);

                /*
                 * while (start.isVisible() == true) {
                 * try {
                 * Thread.sleep(500);
                 * } catch (InterruptedException e) {
                 * // TODO Auto-generated catch block
                 * e.printStackTrace();
                 * }
                 * }
                 */

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
        // ImageIcon icon = new ImageIcon("Fehler.jpg");
        // JOptionPane.showMessageDialog(null, "Eine Meldung", "Meldung",
        // JOptionPane.INFORMATION_MESSAGE, icon);
    }

}