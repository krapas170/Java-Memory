package com.java.memory;

public class SpielSteuerung {

    private char letter;
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
                char wert = dieSpielDaten.gibFeldWert(idx, idy);
                dasSpielFeld.knoepfe[idx][idy].setText("" + wert);
            }
        }

    }

    public void linksKlick(int px, int py) {
        
        if(anzahlLinksKlicks == 1) {
            
            System.out.println(letter);
        }

        anzahlLinksKlicks++;

        if(anzahlLinksKlicks >= 2) {
            anzahlLinksKlicks = 0;
        }
    }

    public void rechtsKlick(int x, int y) {
        
    }

}
