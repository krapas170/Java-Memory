package com.java.memory;

import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menue extends JFrame { // dem Spielfeld werden die Objekte hinzugefügt

    JPanel panel = new JPanel();

    Main Main;

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
        jLabel2.setText("Sicherlich kennst du bereits die Regeln von dem Spiel");
        jLabel3.setText("Zum Einstellen des Spiels gib bitte unten die Feldgröße");
        jLabel4.setText("und die Zeit ein. Maximale Anzahl der Felder ist 56.");

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
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(94, 94, 94)
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField3,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 80,
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
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel5)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jTextField1,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(78, 78, 78)
                                                                .addComponent(jLabel6)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jTextField2,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jLabel3))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pane.add(panel);
    }

    public boolean active = true;

    static int height;
    static int weight;
    static int time;

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        height = Integer.parseInt(jTextField1.getText());
        weight = Integer.parseInt(jTextField2.getText());
        time = Integer.parseInt(jTextField3.getText());
        System.out.print("Menü geschlossen!");
        active = false;
        //Main.setzeEinstellungen(height, weight, time);
    }

    public boolean isActive() {
        if (active == true) {
            return true;
        } else {
            return false;
        }
    }

    public void closeMenue() {
        setVisible(false);
        //dispose();
    }

    public static int gibHoehe() {
        if (height > 0) {
            return height;
        } else {
            System.out.println("Die Höhe hat keinen oder einen negativen Wert");
            return 0;
        }
    }
    public static int gibBreite() {
        if (weight > 0) {
            return weight;
        } else {
            System.out.println("Die Breite hat keinen oder einen negativen Wert");
            return 0;
        }
    }
    public static int gibZeit() {
        if (time > 0) {
            return time;
        } else {
            System.out.println("Die Zeit hat keinen oder einen negativen Wert");
            return 0;
        }
    }
}
