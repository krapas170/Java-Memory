package de.krapas170.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Die verdeckte Kartenverteilung einer Runde.
 *
 * <p>Jedes Symbol kommt genau zweimal vor. Erzeugt wird das ueber eine
 * vollstaendige Liste, die einmal gemischt wird &ndash; frueher wurden
 * Symbole so lange zufaellig gezogen, bis zufaellig keines mehr dreimal
 * vorkam, was bei den letzten Feldern sehr viele Versuche brauchte.</p>
 */
public class SpielDaten {

    private final char[][] memoryfeld;
    private final int breite;
    private final int hoehe;

    public SpielDaten(Einstellungen einstellungen, Random zufall) {
        this.breite = einstellungen.breite();
        this.hoehe = einstellungen.hoehe();

        List<Character> karten = new ArrayList<>(einstellungen.felder());
        for (int paar = 0; paar < einstellungen.paare(); paar++) {
            char symbol = Kartenblatt.symbolFuer(paar);
            karten.add(symbol);
            karten.add(symbol);
        }
        Collections.shuffle(karten, zufall);

        memoryfeld = new char[breite][hoehe];
        int naechste = 0;
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                memoryfeld[x][y] = karten.get(naechste);
                naechste++;
            }
        }
    }

    public char gibFeldWert(int px, int py) {
        if (px < 0 || px >= breite || py < 0 || py >= hoehe) {
            throw new IndexOutOfBoundsException(
                    "Feld " + px + "/" + py + " liegt ausserhalb von " + breite + "x" + hoehe);
        }
        return memoryfeld[px][py];
    }

    public int breite() {
        return breite;
    }

    public int hoehe() {
        return hoehe;
    }
}
