package de.krapas170.memory;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SpielFeld extends JFrame { // dem Spielfeld werden die Objekte hinzugefügt

  SpielSteuerung dieSpielSteuerung;

  int xgroesse = Menue.gibBreite();
  int ygroesse = Menue.gibHoehe();
  int zeit = Menue.gibZeit();
  int zeit1 = zeit * 60;

  public Knopf knoepfe[][] = new Knopf[xgroesse][ygroesse];
  GridLayout gitterLayout = new GridLayout(0, xgroesse);
  JPanel panel = new JPanel();
  JTextField anzeige = new JTextField();
  JButton neustart = new JButton("Neustart");

  public void reload() {
    this.setVisible(false);
    Main.main(null);
  }

  public void fuegeAllesZurOberflaecheHinzu(final Container pane) { // fügt alles zur Oberfläche hinzu
    panel.setLayout(gitterLayout);

    xgroesse = Menue.gibBreite();
    ygroesse = Menue.gibHoehe();
    zeit = Menue.gibZeit();

    // System.out.println(xgroesse);
    // System.out.println(ygroesse);
    // System.out.println(zeit);

    for (int cx = 0; cx < xgroesse; cx++) {
      for (int cy = 0; cy < ygroesse; cy++) {

        // erzeuge Button
        knoepfe[cx][cy] = new Knopf(cx, cy);

        // fuege Button dem Layout hinzu
        panel.add(knoepfe[cx][cy]);

        knoepfe[cx][cy].addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(java.awt.event.ActionEvent e) {
            // System.out.println("Links Klick!");
            int px = ((Knopf) e.getSource()).gibX();
            int py = ((Knopf) e.getSource()).gibY();
            // System.out.println(px + "/" + py + " wurde gedrueckt");
            dieSpielSteuerung.sperreFeld(px, py);
            dieSpielSteuerung.linksKlick(px, py);
          }
        });

      }
    }

    // fuege die Textanzeige hinzu
    panel.add(neustart);
    neustart.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {

        reload();

      }

    });
    panel.add(anzeige);
    pane.add(panel);
    dieSpielSteuerung = new SpielSteuerung(this);
    Zeitschaltung();
  }

  public void Zeitschaltung() {

    TimerTask task = new TimerTask() {
      public void run() {
        zeit1--;
        if (zeit1 <= 10) {
          if (zeit1 % 2 == 0) {
            System.out.print(Farben.ANSI_RED_BACKGROUND + Farben.ANSI_WHITE);
          } else {
            System.out.print(Farben.ANSI_RESET);
          }
        }
        System.out.println("Verbleibende Sekunden: " + zeit1 + Farben.ANSI_RESET);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if (zeit1 == 0) {
          verloren();
        } else {
        }
      }
    };
    Timer timer = new Timer("Timer");

    long delay = 2500;
    long period = zeit1;
    timer.schedule(task, delay, period);

  }

  private void verloren() {

    ImageIcon icon = new ImageIcon("assets/verloren.gif");
    JOptionPane.showMessageDialog(null, "Die Zeit ist um und du hast es leider nicht geschafft!", "Zeit um",
        JOptionPane.INFORMATION_MESSAGE, icon);
    System.exit(0);
  }

  public void gewonnen() {

    int input = zeit1;

    zeit1 = -1;

    final double scale3600 = 1.0 / 3600;
    final double scale60 = 1.0 / 60;
    int hh = (int) (input * scale3600);
    int mm = (int) (input * scale60);
    int ss = input - mm * 60 - hh * 3600;

    DecimalFormat format = new DecimalFormat("00");

    ImageIcon icon = new ImageIcon("assets/gewonnen.gif");
    JOptionPane.showMessageDialog(null,
        "Du hast alle Felder aufgedeckt und hattest noch " + format.format(hh) + ":" + format.format(mm) + ":"
            + format.format(ss) + " übrig!",
        "Geschafft",
        JOptionPane.INFORMATION_MESSAGE, icon);
    System.exit(0);

  }
}
