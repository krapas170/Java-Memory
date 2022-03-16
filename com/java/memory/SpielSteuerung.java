package com.java.memory;

import com.java.memory.Farben;

import java.awt.Color;
import java.beans.ExceptionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.GrayFilter;

public class SpielSteuerung {

    private SpielDaten dieSpielDaten;
    private SpielFeld dasSpielFeld;
    private int spielfeldx = 6;
    private int spielfeldy = 6;
    private int anzahlLinksKlicks = 0;
    private int anzahlrichtige = 0;

    SpielSteuerung(SpielFeld pFeld) {
        dasSpielFeld = pFeld;
        dieSpielDaten = new SpielDaten(spielfeldx, spielfeldy);

        for (int idx = 0; idx < spielfeldx; idx++) {
            for (int idy = 0; idy < spielfeldy; idy++) {
                dasSpielFeld.knoepfe[idx][idy].setText("     ");
            }
        }

    }

    public void linksKlick(int px, int py) {

        anzahlLinksKlicks++;

        int wertalszahl = 0;

        if (anzahlLinksKlicks == 1) {
            char wert = dieSpielDaten.gibFeldWert(px, py);
            setzeFeldWert(px, py);
            wertalszahl = wert;
            dieSpielDaten.vergleich.add(wertalszahl);
            dieSpielDaten.arrayeins.add(0, px);
            dieSpielDaten.arrayeins.add(1, py);

        } else if (anzahlLinksKlicks == 2) {
            char wert = dieSpielDaten.gibFeldWert(px, py);
            setzeFeldWert(px, py);
            wertalszahl = wert;
            dieSpielDaten.vergleich.add(wertalszahl);
            dieSpielDaten.arrayzwei.add(0, px);
            dieSpielDaten.arrayzwei.add(1, py);
            anzahlLinksKlicks = 0;

        } else {
            System.out.println(Farben.ANSI_WHITE + Farben.ANSI_RED_BACKGROUND
                    + "Fehler in der Programmierung. Alle Felder werden verdeckt. Bitte klicke das Feld erneut an!"
                    + Farben.ANSI_RESET);
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setText("     ");
                }
            }
            anzahlLinksKlicks = 0;
        }

        int vergleicheBuchstabe = 0;

        vergleicheBuchstabe = dieSpielDaten.vergleich.size();

        if (vergleicheBuchstabe == 1) {
        }

        else if (vergleicheBuchstabe == 2) {
            int parrxeins = dieSpielDaten.arrayeins.get(0);
            int parryeins = dieSpielDaten.arrayeins.get(1);
            int parrxzwei = dieSpielDaten.arrayzwei.get(0);
            int parryzwei = dieSpielDaten.arrayzwei.get(1);

            if (dieSpielDaten.vergleich.get(0) == dieSpielDaten.vergleich.get(1)) {
                dasSpielFeld.knoepfe[parrxeins][parryeins].setBackground(Color.GREEN);
                dasSpielFeld.knoepfe[parrxzwei][parryzwei].setBackground(Color.GREEN);

                dasSpielFeld.knoepfe[parrxeins][parryeins].setEnabled(false);
                dasSpielFeld.knoepfe[parrxzwei][parryzwei].setEnabled(false);

                anzahlrichtige++;
                anzahlrichtige++;
            } else {
                dasSpielFeld.knoepfe[parrxeins][parryeins].setBackground(Color.RED);
                dasSpielFeld.knoepfe[parrxzwei][parryzwei].setBackground(Color.RED);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dasSpielFeld.knoepfe[parrxeins][parryeins].setBackground(Color.WHITE);
                dasSpielFeld.knoepfe[parrxzwei][parryzwei].setBackground(Color.WHITE);

                dasSpielFeld.knoepfe[parrxeins][parryeins].setEnabled(true);
                dasSpielFeld.knoepfe[parrxzwei][parryzwei].setEnabled(true);
            }

            for (int i = 0; i < dieSpielDaten.vergleich.size(); i++) {
                dieSpielDaten.vergleich.clear();
            }
            for (int i = 0; i < dieSpielDaten.arrayeins.size(); i++) {
                dieSpielDaten.arrayeins.clear();
            }
            for (int i = 0; i < dieSpielDaten.arrayzwei.size(); i++) {
                dieSpielDaten.arrayzwei.clear();
            }
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setText("     ");
                }
            }
        }

        else {
            System.out.println(Farben.ANSI_RED_BACKGROUND + Farben.ANSI_WHITE
                    + "Fehler im Array 'vergleich'! Beende das Spiel!" + Farben.ANSI_RESET);
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setBackground(Color.RED);
                }
            }
            Main.beiFehlerSchließen();
        }
        if (anzahlrichtige == ) {
            
        }
    }

    public void rechtsKlick(int px, int py) {

        dasSpielFeld.knoepfe[px][py].setText("     ");
        dasSpielFeld.knoepfe[px][py].setEnabled(true);
        anzahlLinksKlicks = 0;
    }

    public void setzeFeldWert(int px, int py) {
        char wert = dieSpielDaten.gibFeldWert(px, py);
        dasSpielFeld.knoepfe[px][py].setText("" + wert);
        dasSpielFeld.knoepfe[px][py].setEnabled(false);
    }

}
