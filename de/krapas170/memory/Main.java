package de.krapas170.memory;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    static Menue start = new Menue();

    public static void main(String[] args) {
        boolean GameIsVisable = true;

        boolean MenueIsActive = start.isActive();

        runMenue();

        while (MenueIsActive) {
            try {
                Thread.sleep(100);
                MenueIsActive = start.isActive();
            } catch (InterruptedException e) {
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

    public static void beiFehlerSchließen(String uberschrift, String meldung) {

        ImageIcon icon = new ImageIcon("assets/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    public static void beiFehlerFortsetzen(String uberschrift, String meldung) {

        ImageIcon icon = new ImageIcon("assets/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
    }

    static int Hoehe;
    static int Breite;
    static int Zeit;

    public void setzeEinstellungen(int height, int weight, int time) {
        Hoehe = height;
        Breite = weight;
        Zeit = time;
    }

}