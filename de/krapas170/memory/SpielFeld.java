package de.krapas170.memory;

import java.awt.Color;
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

public class SpielFeld extends JFrame { // dem Spielfeld werden die Objekte hinzugefügt
  SpielSteuerung dieSpielSteuerung;
  int xgroesse = Menue.gibBreite();
  int ygroesse = Menue.gibHoehe();
  int zeit = Menue.gibZeit();
  int zeit1 = zeit * 60;
  public Knopf knoepfe[][] = new Knopf[xgroesse][ygroesse];
  GridLayout gitterLayout = new GridLayout(0, xgroesse);
  JPanel panel = new JPanel();
  JButton anzeige = new JButton();
  JButton zeitAnzeige = new JButton();
  JButton neustart = new JButton("Neustart");
  JButton beenden = new JButton("Beenden");

  public void reload() {
    this.setVisible(false);
    Main.main(null);
  }

  public void exit() {
    System.exit(0);
  }

  public void fuegeAllesZurOberflaecheHinzu(final Container pane) { // fügt alles zur Oberfläche hinzu
    panel.setLayout(gitterLayout);
    xgroesse = Menue.gibBreite();
    ygroesse = Menue.gibHoehe();
    zeit = Menue.gibZeit();
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

  public void Zeitschaltung() {
    TimerTask task = new TimerTask() {
      public void run() {
        zeit1--;
        if (zeit1 <= 10) {
          if (zeit1 >= 0) {
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
        anzeige.setText("Verbleibend:");
        zeitAnzeige.setEnabled(false);
        ZeitText();
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
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
    Timer timer = new Timer("Timer");
    long delay = 2500;
    long period = zeit1;
    timer.schedule(task, delay, period);
    timer.cancel();
    timer.purge();
  }

  private void verloren() {
    ImageIcon icon = new ImageIcon("assets/pictures/verloren.gif");
    JOptionPane.showMessageDialog(null, "Die Zeit ist um und du hast es leider nicht geschafft!", "Zeit um",
        JOptionPane.INFORMATION_MESSAGE, icon);
    System.exit(0);
  }

  public void gewonnen() {
    int input2 = zeit1;
    zeit1 = -1;
    final double scale3600 = 1.0 / 3600;
    final double scale60 = 1.0 / 60;
    int hh = (int) (input2 * scale3600);
    int mm = (int) (input2 * scale60);
    int ss = input2 - mm * 60 - hh * 3600;
    DecimalFormat format = new DecimalFormat("00");
    ImageIcon icon = new ImageIcon("assets/pictures/gewonnen.gif");
    JOptionPane.showMessageDialog(null,
        "Du hast alle Felder aufgedeckt und hattest noch " + format.format(mm) + ":"
            + format.format(ss) + " übrig!",
        "Geschafft",
        JOptionPane.INFORMATION_MESSAGE, icon);
    System.exit(0);
  }
}