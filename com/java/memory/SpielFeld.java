package com.java.memory;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpielFeld extends JFrame {

    public Knopf knoepfe[][] = new Knopf[6][6];
    GridLayout gitterLayout = new GridLayout(0, 6);
    JPanel panel = new JPanel();
    //JTextField anzeige = new JTextField();
    //JButton neustart = new JButton("Neustart");

    SpielSteuerung dieSpielSteuerung;

    public void fuegeAllesZurOberflaecheHinzu(final Container pane) {
        panel.setLayout(gitterLayout);

        for (int cy = 0; cy < 6; cy++) {
            for (int cx = 0; cx < 6; cx++) {

                //erzeuge Button
                knoepfe[cx][cy] = new Knopf(cx, cy);

                //fuege Button dem Layout hinzu
                panel.add(knoepfe[cx][cy]);

                //füge dem Knopf eine Funktion hinzu, wenn er gedrückt wird
                knoepfe[cx][cy].addActionListener((ActionEvent e) -> {
                    int px = ((Knopf) e.getSource()).gibX();
                    int py = ((Knopf) e.getSource()).gibY();
                    System.out.println(px+"/"+py + " wurde gedrueckt");
                    dieSpielSteuerung.linksKlick(px, py);
                });
            }
        }

        //fuege die Textanzeige hinzu
        //panel.add(neustart);
        //neustart.addActionListener((ActionEvent e) -> {
        //was passiert, wenn neustart gedrueckt wird
        //});
        //panel.add(anzeige);
        pane.add(panel);
        dieSpielSteuerung = new SpielSteuerung(this);
    }
}
