package com.java.memory;

import com.java.memory.Farben;

import java.awt.Color;
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
        

        //Die SpielDaten sind erzeugt, Zeit sie auch auf dem Spielfeld f
        //für uns einmal sichtbar zu machen 
        //um etwaige Fehler erkennen zu können.
        for (int idx = 0; idx < spielfeldx; idx++) {
            for (int idy = 0; idy < spielfeldy; idy++) {
                dasSpielFeld.knoepfe[idx][idy].setText("     ");
            }
        }

    }

    public void linksKlick(int px, int py) {
        
        ArrayList<Integer> vergleich = new ArrayList<>();

        anzahlLinksKlicks++;

        int wertalszahl;

        if(anzahlLinksKlicks == 1) {
            char wert = dieSpielDaten.gibFeldWert(px, py);
            dasSpielFeld.knoepfe[px][py].setText("" + wert);
            dasSpielFeld.knoepfe[px][py].setEnabled(false);
            wertalszahl = wert;
            vergleich.add(0, wertalszahl);
            dasSpielFeld.knoepfe[px][py].setBackground(Color.GREEN);
        }
        else if(anzahlLinksKlicks == 2) {
            char wert = dieSpielDaten.gibFeldWert(px, py);
            dasSpielFeld.knoepfe[px][py].setText("" + wert);
            dasSpielFeld.knoepfe[px][py].setEnabled(false);
            wertalszahl = wert;
            vergleich.add(1, wertalszahl);
            anzahlLinksKlicks = 3;
        }
        else {
            System.out.println(Farben.ANSI_PURPLE + "Fehler in der Programmierung. Alle Felder werden verdeckt. Bitte klicke das Feld erneut an!");
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setText("     ");
                }
            }
            anzahlLinksKlicks = 0;
        }

        int vergleicheBuchstabe = Collections.frequency(vergleich, wertalszahl);

        if(vergleicheBuchstabe == 2) {
            dasSpielFeld.knoepfe[px][py].setBackground(Color.GREEN);
            vergleich.remove(0);
            vergleich.remove(1);
            vergleich.remove(2);
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setText("     ");
                }
            }
        }
        else if (vergleicheBuchstabe == 1) {
            for (int idx = 0; idx < spielfeldx; idx++) {
                for (int idy = 0; idy < spielfeldy; idy++) {
                    dasSpielFeld.knoepfe[idx][idy].setEnabled(true);
                }
            }
        }
            
        else {
            
        }
    }

    public void rechtsKlick(int px, int py) {

        dasSpielFeld.knoepfe[px][py].setText("     ");
        dasSpielFeld.knoepfe[px][py].setEnabled(true);
        anzahlLinksKlicks = 0;
    }

}
