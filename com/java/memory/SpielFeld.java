package com.java.memory;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpielFeld extends JFrame {

  public Knopf knoepfe[][] = new Knopf[6][6];
  GridLayout gitterLayout = new GridLayout(0, 6);
  JPanel panel = new JPanel();
  // JTextField anzeige = new JTextField();
  // JButton neustart = new JButton("Neustart");

  SpielSteuerung dieSpielSteuerung;
  private int xgroesse = 6;
  private int ygroesse = 6;

  public void fuegeAllesZurOberflaecheHinzu(final Container pane) {
    panel.setLayout(gitterLayout);

    for (int cy = 0; cy < xgroesse; cy++) {
      for (int cx = 0; cx < ygroesse; cx++) {

        // erzeuge Button
        knoepfe[cx][cy] = new Knopf(cx, cy);

        // fuege Button dem Layout hinzu
        panel.add(knoepfe[cx][cy]);

        // füge dem Knopf eine Funktion hinzu, wenn er gedrückt wird
        /*
         * knoepfe[cx][cy].addActionListener((ActionEvent e) -> {
         * int px = ((Knopf) e.getSource()).gibX();
         * int py = ((Knopf) e.getSource()).gibY();
         * System.out.println(px+"/"+py + " wurde gedrueckt");
         * dieSpielSteuerung.linksKlick(px, py);
         * });
         */

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
              System.out.println("Left Click!");
              int px = ((Knopf) e.getSource()).gibX();
              int py = ((Knopf) e.getSource()).gibY();
              System.out.println(px + "/" + py + " wurde gedrueckt");
              dieSpielSteuerung.linksKlick(px, py);
            }
            if (e.getButton() == MouseEvent.BUTTON2) {
              System.out.println("Middle Click!");
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
              System.out.println("Right Click!");
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
    // was passiert, wenn neustart gedrueckt wird
    // });
    // panel.add(anzeige);
    pane.add(panel);
    dieSpielSteuerung = new SpielSteuerung(this);
  }
}
