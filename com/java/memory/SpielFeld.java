package com.java.memory;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SpielFeld extends JFrame { // dem Spielfeld werden die Objekte hinzugefügt

  SpielSteuerung dieSpielSteuerung;

  int xgroesse = Menue.gibBreite();
  int ygroesse = Menue.gibHoehe();
  int zeit = Menue.gibZeit();

  public Knopf knoepfe[][] = new Knopf[8][8];
  GridLayout gitterLayout = new GridLayout(8, 8);
  JPanel panel = new JPanel();
  JTextField anzeige = new JTextField();
  JButton neustart = new JButton("Neustart");

  // private int xgroesse = Menue.gibBreite();
  // private int ygroesse = Menue.gibHoehe();
  // private int time = Menue.gibZeit();

  public void fuegeAllesZurOberflaecheHinzu(final Container pane) { // fügt alles zur Oberfläche hinzu
    panel.setLayout(gitterLayout);

    xgroesse = Menue.gibBreite();
    ygroesse = Menue.gibHoehe();
    zeit = Menue.gibZeit();

    System.out.println(xgroesse);
    System.out.println(ygroesse);
    System.out.println(zeit);

    for (int cx = 0; cx < xgroesse; cx++) {
      for (int cy = 0; cy < ygroesse; cy++) {

        // erzeuge Button
        knoepfe[cx][cy] = new Knopf(cx, cy);

        // fuege Button dem Layout hinzu
        panel.add(knoepfe[cx][cy]);

        knoepfe[cx][cy].addMouseListener(new MouseListener() {
          public void mousePressed(MouseEvent e) {
          }

          public void mouseReleased(MouseEvent e) {
          }

          public void mouseEntered(MouseEvent e) {
          }

          public void mouseExited(MouseEvent e) {
          }

          public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
              System.out.println("Links Klick!");
              int px = ((Knopf) e.getSource()).gibX();
              int py = ((Knopf) e.getSource()).gibY();
              System.out.println(px + "/" + py + " wurde gedrueckt");
              dieSpielSteuerung.linksKlick(px, py);
            }
            if (e.getButton() == MouseEvent.BUTTON2) {
              System.out.println("Middle Click!");
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
              System.out.println("Rechts Klick!");
              int px = ((Knopf) e.getSource()).gibX();
              int py = ((Knopf) e.getSource()).gibY();
              System.out.println(px + "/" + py + " wurde gedrueckt");
              dieSpielSteuerung.rechtsKlick(px, py);
            }
          }
        });

      }
    }

    // fuege die Textanzeige hinzu
    // panel.add(neustart);
    // neustart.addActionListener((ActionEvent e) -> {
    // Main.main(null);
    // System.exit(0);
    // });
    // panel.add(anzeige);
    pane.add(panel);
    dieSpielSteuerung = new SpielSteuerung(this);
  }
}
