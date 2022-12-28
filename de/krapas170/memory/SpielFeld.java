package de.krapas170.memory;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SpielFeld extends JFrame { // dem Spielfeld werden die Objekte hinzugefügt
  SpielSteuerung dieSpielSteuerung;
  static int xgroesse = Main.gibBreite();
  static int ygroesse = Main.gibHoehe();
  static int zeit = Main.gibZeit();
  static int zeit1 = zeit * 60;
  protected Knopf knoepfe[][] = new Knopf[xgroesse][ygroesse];
  GridLayout gitterLayout = new GridLayout(0, xgroesse);
  JPanel panel = new JPanel();
  JButton anzeige = new JButton();
  JButton zeitAnzeige = new JButton();
  JButton neustart = new JButton("Neustart");
  JButton beenden = new JButton("Beenden");

  private void reload() {
    timer.cancel();
    timer.purge();
    this.setVisible(false);
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

  private void reloadMenue() {
    timer.cancel();
    timer.purge();
    this.setVisible(false);
    Menue start = new Menue();
    start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    start.fuegeAllesZumMenueHinzu(start.getContentPane());
    start.pack();
    start.setVisible(true);
    while (Menue.active) {
      try {
        Thread.sleep(100);
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
    Main.playTimer();
    int zeit1 = SpielFeld.zeit1();
    while (zeit1 >= 11) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      zeit1 = SpielFeld.zeit1();
    }
    if (zeit1 >= 1) {
      Main.playCountdownEnd();
    }
    /*
     * Main.startMenue();
     * 
     * while (Main.isMenueActive()) {
     * try {
     * Thread.sleep(100);
     * Main.isMenueActive();
     * } catch (InterruptedException e) {
     * e.printStackTrace();
     * }
     * }
     * 
     * Main.startSpielFeld();
     * Main.startTimer();
     */
  }

  private void exit() {
    System.exit(0);
  }

  protected void fuegeAllesZurOberflaecheHinzu(final Container pane) { // fügt alles zur Oberfläche hinzu
    zeit = Main.gibZeit();
    zeit1 = zeit * 60;
    panel.setLayout(gitterLayout);
    for (int cx = 0; cx < xgroesse; cx++) {
      for (int cy = 0; cy < ygroesse; cy++) {
        // erzeuge Button
        knoepfe[cx][cy] = new Knopf(cx, cy);
        // fuege Button dem Layout hinzu
        panel.add(knoepfe[cx][cy]);
        knoepfe[cx][cy].addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(java.awt.event.ActionEvent e) {
            int px = ((Knopf) e.getSource()).gibX();
            int py = ((Knopf) e.getSource()).gibY();
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
    panel.add(beenden);
    beenden.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        exit();
      }
    });
    panel.add(anzeige);
    panel.add(zeitAnzeige);
    pane.add(panel);
    dieSpielSteuerung = new SpielSteuerung(this);
    Zeitschaltung();
  }

  final Timer timer = new Timer("Timer");

  private void Zeitschaltung() {
    TimerTask task = new TimerTask() {
      public void run() {
        zeit1--;
        if (zeit1 <= 10) {
          if (zeit1 >= 1) {
            if (zeit1 % 2 == 0) {
              System.out.print(Farben.ANSI_RED_BACKGROUND + Farben.ANSI_WHITE);
              zeitAnzeige.setBackground(Color.RED);
            } else {
              System.out.print(Farben.ANSI_RESET);
              zeitAnzeige.setBackground(Color.WHITE);
            }
          }
        }
        System.out.println("Verbleibende Sekunden: " + zeit1 + Farben.ANSI_RESET);
        anzeige.setEnabled(false);
        anzeige.setText("Zeit:");
        zeitAnzeige.setEnabled(false);
        ZeitText();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (zeit1 == 0) {
          verloren();
        }
      }

      private void ZeitText() {
        if (zeit1 > 0) {
          int input1 = zeit1;
          final double scale3600 = 1.0 / 3600;
          final double scale60 = 1.0 / 60;
          int hh = (int) (input1 * scale3600);
          int mm = (int) (input1 * scale60);
          int ss = input1 - mm * 60 - hh * 3600;
          DecimalFormat format = new DecimalFormat("00");
          zeitAnzeige.setText(format.format(mm) + ":"
              + format.format(ss));
        }
      }
    };
    long delay = 0;
    long period = zeit1;
    timer.schedule(task, delay, period);
    if (zeit1 <= 0) {
      timer.cancel();
      timer.purge();
    }
  }

  private void verloren() {
    timer.cancel();
    timer.purge();
    Main.playVerloren();
    ImageIcon icon = new ImageIcon("assets/pictures/verloren.gif");
    String[] options = { "Ja", "Nein", "Werte ändern" };
    int answer = JOptionPane.showOptionDialog(null,
        "Die Zeit ist um und du hast es leider nicht geschafft!\nMöchtest du es erneut versuchen?", "Zeit um", 1, 3,
        icon,
        options, null);
    if (answer == 0) {
      reload();
    } else if (answer == 1) {
      System.exit(0);
    } else if (answer == 2) {
      reloadMenue();
    }
  }

  protected void gewonnen() {
    timer.cancel();
    timer.purge();
    int input2 = zeit1;
    zeit1 = -1;
    final double scale3600 = 1.0 / 3600;
    final double scale60 = 1.0 / 60;
    int hh = (int) (input2 * scale3600);
    int mm = (int) (input2 * scale60);
    int ss = input2 - mm * 60 - hh * 3600;
    DecimalFormat format = new DecimalFormat("00");
    ImageIcon icon = new ImageIcon("assets/pictures/gewonnen.gif");
    Main.playGewonnen();
    String[] options = { "Ja", "Nein", "Werte ändern" };
    int answer = JOptionPane.showOptionDialog(null,
        "Du hast alle Felder aufgedeckt und hattest noch " + format.format(mm) + ":"
            + format.format(ss) + " übrig!\nWillst du nochmal spielen?",
        "Geschafft", 1, 3, icon,
        options, null);
    if (answer == 0) {
      reload();
    } else if (answer == 1) {
      System.exit(0);
    } else if (answer == 2) {
      reloadMenue();
    }
  }

  protected static int zeit1() {
    return zeit1;

  }
}