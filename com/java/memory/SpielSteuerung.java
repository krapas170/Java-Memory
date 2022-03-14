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

    SpielSteuerung(SpielFeld pFeld) {
        dasSpielFeld = pFeld;
        dieSpielDaten = new SpielDaten(spielfeldx, spielfeldy);

        for (int idx = 0; idx < spielfeldx; idx++) {
            for (int idy = 0; idy < spielfeldy; idy++) {
                dasSpielFeld.knoepfe[idx][idy].setText("          ");
            }
        }

    }

    public void linksKlick(int px, int py) {

        ArrayList<Integer> vergleich = new ArrayList<>();
        ArrayList<Integer> arrayeins = new ArrayList<>();
        ArrayList<Integer> arrayzwei = new ArrayList<>();

        anzahlLinksKlicks++;

        int wertalszahl = 0;

        if (anzahlLinksKlicks == 1) {
            char wert = dieSpielDaten.gibFeldWert(px, py);
            dasSpielFeld.knoepfe[px][py].setText("" + wert);
            dasSpielFeld.knoepfe[px][py].setEnabled(false);
            wertalszahl = wert;
            vergleich.add(0, wertalszahl);
            arrayeins.add(0, px);
            arrayeins.add(1, py);

        } else if (anzahlLinksKlicks == 2) {
            char wert = dieSpielDaten.gibFeldWert(px, py);
            dasSpielFeld.knoepfe[px][py].setText("" + wert);
            dasSpielFeld.knoepfe[px][py].setEnabled(false);
            wertalszahl = wert;
            vergleich.add(1, wertalszahl);
            arrayzwei.add(0, px);
            arrayzwei.add(1, py);
            anzahlLinksKlicks = 0;

            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setText("     ");
                }
            }
            
        } else {
            System.out.println(Farben.ANSI_WHITE + Farben.ANSI_RED_BACKGROUND
                    + "Fehler in der Programmierung. Alle Felder werden verdeckt. Bitte klicke das Feld erneut an!" + Farben.ANSI_RESET);
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setText("     ");
                }
            }
            anzahlLinksKlicks = 0;
        }

        int vergleicheBuchstabe = Collections.frequency(vergleich, wertalszahl);

        if (vergleicheBuchstabe == 2) {
            int parrxeins = arrayeins.get(0);
            int parryeins = arrayeins.get(1);
            int parrxzwei = arrayzwei.get(0);
            int parryzwei = arrayzwei.get(1);
            dasSpielFeld.knoepfe[parrxeins][parryeins].setBackground(Color.GREEN);
            dasSpielFeld.knoepfe[parrxzwei][parryzwei].setBackground(Color.GREEN);
            for (int i = 0; i < vergleich.size(); i++) {
                vergleich.remove(i);
            }
            for (int i = 0; i < arrayeins.size(); i++) {
                arrayeins.remove(i);
            }
            for (int i = 0; i < arrayzwei.size(); i++) {
                arrayzwei.remove(i);
            }
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setText("     ");
                }
            }
        }
        else {
            System.out.println("Noch nichts eingetragen hier in Zeile 102 bis 104");
        }
    }

    public void rechtsKlick(int px, int py) {

        dasSpielFeld.knoepfe[px][py].setText("     ");
        dasSpielFeld.knoepfe[px][py].setEnabled(true);
        anzahlLinksKlicks = 0;
    }

}
