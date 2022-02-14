package com.java.memory;

import java.awt.Color;

public class SpielSteuerung {

    private SpielDaten dieSpielDaten;
    private SpielFeld dasSpielFeld;
    private int spielfeldx = 7;
    private int spielfeldy = 7;
    private int anzahlLinksKlicks = 0;
    private char wertErsterKlick;
    private int erstesX, erstesY;

    SpielSteuerung(SpielFeld pFeld) {
        dasSpielFeld = pFeld;
        dieSpielDaten = new SpielDaten(spielfeldx, spielfeldy);

        //Die SpielDaten sind erzeugt, Zeit sie auch auf dem Spielfeld f
        //für uns einmal sichtbar zu machen 
        //um etwaige Fehler erkennen zu können.
        for (int idx = 0; idx < spielfeldx; idx++) {
            for (int idy = 0; idy < spielfeldy; idy++) {
                char wert = dieSpielDaten.gibFeldWert(idx, idy);
                dasSpielFeld.knoepfe[idx][idy].setText("" + wert);
            }
        }

    }

    public void linksKlick(int x, int y) {
        anzahlLinksKlicks++;
        char wert = dieSpielDaten.gibFeldWert(px, py);

        if(anzahlLinksKlicks == 1) {
            wertErsterKlick = wert;
        }

        if(anzahlLinksKlicks >= 2) {
            anzahlLinksKlicks = 0;
        }
    }

    public void rechtsKlick(int x, int y) {
        
    }

}
