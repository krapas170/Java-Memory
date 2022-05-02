package de.krapas170.memory;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.Font;

public class Menue extends JFrame { // dem Spielfeld werden die Objekte hinzugefügt
        JPanel panel = new JPanel();
        JTextField jTextField1 = new javax.swing.JTextField();
        JTextField jTextField2 = new javax.swing.JTextField();
        JTextField jTextField3 = new javax.swing.JTextField();

        public void fuegeAllesZumMenueHinzu(final Container pane) { // fügt alles zur Oberfläche hinzu
                GroupLayout layout = new GroupLayout(panel);
                panel.setLayout(layout);
                JLabel jLabel1 = new javax.swing.JLabel();
                JLabel jLabel2 = new javax.swing.JLabel();
                JLabel jLabel3 = new javax.swing.JLabel();
                JLabel jLabel4 = new javax.swing.JLabel();
                JLabel jLabel5 = new javax.swing.JLabel();
                JLabel jLabel6 = new javax.swing.JLabel();
                JLabel jLabel7 = new javax.swing.JLabel();
                JButton jButton1 = new javax.swing.JButton();
                jLabel1.setText("Herzlich willkommen beim Memory-Game");
                jLabel1.setFont(new Font("Arial", Font.BOLD, 21));
                jLabel2.setText("Sicherlich kennst du bereits die Regeln von dem Spiel");
                jLabel3.setText("Zum Einstellen des Spiels gib bitte unten die Anzahl der Felder");
                jLabel4.setText("und die Zeit ein. Maximale Anzahl der Felder ist 52.");
                jLabel5.setText("Höhe:");
                jLabel6.setText("Breite:");
                jLabel7.setText("Zeitlimit in Minuten:");
                jButton1.setText("Spiel starten!");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(94, 94, 94)
                                                                                                .addComponent(jLabel7)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jTextField3,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                80,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(184, 184, 184)
                                                                                                .addComponent(jButton1))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(jLabel1))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(45, 45, 45)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jLabel2)
                                                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                                                .addComponent(jLabel5)
                                                                                                                                .addGap(18, 18, 18)
                                                                                                                                .addComponent(jTextField1,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                80,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addGap(78, 78, 78)
                                                                                                                                .addComponent(jLabel6)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                .addComponent(jTextField2,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                80,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(jLabel3)
                                                                                                                .addComponent(jLabel4))))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(13, 13, 13)
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel2)
                                                                .addGap(36, 36, 36)
                                                                .addComponent(jLabel3)
                                                                .addComponent(jLabel4)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGap(31, 31, 31)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel5)
                                                                                .addComponent(jTextField1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel6)
                                                                                .addComponent(jTextField2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel7)
                                                                                .addComponent(jTextField3,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton1)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                pane.add(panel);
        }

        private boolean active = true;
        static int height;
        static int weight;
        static int time;
        static boolean feldgroessekorrekt = false;

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
                height = Integer.parseInt(jTextField1.getText());
                weight = Integer.parseInt(jTextField2.getText());
                time = Integer.parseInt(jTextField3.getText());
                if ((height * weight) % 2 == 1) {
                        String uberschrift = "Ungültige Feldgröße";
                        String meldung = "Bitte stelle sicher, dass entweder die Höhe oder die Breite eine gerade Zahl ist!";
                        System.out.println(
                                        Farben.ANSI_RED_BACKGROUND + Farben.ANSI_WHITE
                                                        + "Bitte stelle sicher, dass entweder die Höhe oder die Breite eine gerade Zahl ist!"
                                                        + Farben.ANSI_RESET);
                        Main.beiFehlerFortsetzen(uberschrift, meldung);
                } else if (height * weight >= 53) {
                        String uberschrift = "Zu viele Felder";
                        String meldung = "Bitte stelle sicher, dass es nicht mehr als 52 Felder gibt!";
                        System.out.println(
                                        Farben.ANSI_RED_BACKGROUND + Farben.ANSI_WHITE
                                                        + "Bitte stelle sicher, dass es nicht mehr als 52 Felder gibt!"
                                                        + Farben.ANSI_RESET);
                        Main.beiFehlerFortsetzen(uberschrift, meldung);
                } else if ((height * weight) % 2 == 0) {
                        if (height * weight <= 52) {
                                System.out.print(Farben.ANSI_GREEN + "Menü geschlossen!" + Farben.ANSI_RESET);
                                active = false;
                                setVisible(false);
                                Main.setzeEinstellungen(height, weight, time);
                        }
                }
        }

        public boolean isActive() {
                if (active == true) {
                        return true;
                } else {
                        return false;
                }
        }

        public static int gibHoehe() {
                if (height >= 0) {
                        return height;
                } else {
                        System.out.println("Die Höhe hat keinen oder einen negativen Wert");
                        return 0;
                }
        }

        public static int gibBreite() {
                if (weight >= 0) {
                        return weight;
                } else {
                        System.out.println("Die Breite hat keinen oder einen negativen Wert");
                        return 0;
                }
        }

        public static int gibZeit() {
                if (time >= 0) {
                        return time;
                } else {
                        System.out.println("Die Zeit hat keinen oder einen negativen Wert");
                        return 0;
                }
        }
}