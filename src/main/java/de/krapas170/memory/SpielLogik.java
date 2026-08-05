package de.krapas170.memory;

import java.util.Random;

/**
 * Die Spielregeln. Kennt kein Swing.
 *
 * <p>Der gesamte Zustand steckt in wenigen Feldern statt in drei parallel
 * gefuehrten Listen. Dadurch entfaellt die frueher noetige Selbstdiagnose
 * ("Fehler im Array 'vergleich'"): Ein widerspruechlicher Zustand ist hier
 * nicht mehr herstellbar.</p>
 */
public class SpielLogik {

    /** Wie lange ein falsches Paar sichtbar bleibt, bevor es zugedeckt wird. */
    public static final int VERDECK_VERZOEGERUNG_MS = 900;

    private final Einstellungen einstellungen;
    private final SpielDaten daten;
    private final SpielAnzeige anzeige;
    private final boolean[][] gefunden;

    private int ersteX = -1;
    private int ersteY = -1;
    private int gefundenePaare;
    private int zuege;
    private boolean wartetAufVerdecken;

    public SpielLogik(Einstellungen einstellungen, SpielAnzeige anzeige, Random zufall) {
        this.einstellungen = einstellungen;
        this.anzeige = anzeige;
        this.daten = new SpielDaten(einstellungen, zufall);
        this.gefunden = new boolean[einstellungen.breite()][einstellungen.hoehe()];
    }

    /**
     * Der Spieler hat auf ein Feld geklickt.
     *
     * <p>Klicks, die keinen Sinn ergeben, werden still ignoriert: waehrend
     * ein falsches Paar noch sichtbar ist, auf ein bereits gefundenes Paar
     * und ein zweites Mal auf dieselbe Karte.</p>
     */
    public void klick(int px, int py) {
        if (wartetAufVerdecken || istGewonnen()) {
            return;
        }
        if (gefunden[px][py]) {
            return;
        }
        if (px == ersteX && py == ersteY) {
            return;
        }

        char wert = daten.gibFeldWert(px, py);
        anzeige.zeigeKarte(px, py, wert);

        if (ersteX < 0) {
            ersteX = px;
            ersteY = py;
            return;
        }

        final int ersteKarteX = ersteX;
        final int ersteKarteY = ersteY;
        final int zweiteKarteX = px;
        final int zweiteKarteY = py;
        ersteX = -1;
        ersteY = -1;

        zuege++;
        anzeige.zeigeZuege(zuege);

        if (daten.gibFeldWert(ersteKarteX, ersteKarteY) == wert) {
            gefunden[ersteKarteX][ersteKarteY] = true;
            gefunden[zweiteKarteX][zweiteKarteY] = true;
            gefundenePaare++;
            anzeige.markierePaar(ersteKarteX, ersteKarteY, zweiteKarteX, zweiteKarteY);
            if (istGewonnen()) {
                anzeige.spielGewonnen();
            }
        } else {
            anzeige.markiereFehlversuch(ersteKarteX, ersteKarteY, zweiteKarteX, zweiteKarteY);
            wartetAufVerdecken = true;
            anzeige.verzoegert(VERDECK_VERZOEGERUNG_MS, () -> {
                anzeige.verdeckeKarte(ersteKarteX, ersteKarteY);
                anzeige.verdeckeKarte(zweiteKarteX, zweiteKarteY);
                wartetAufVerdecken = false;
            });
        }
    }

    public boolean istGewonnen() {
        return gefundenePaare == einstellungen.paare();
    }

    public int zuege() {
        return zuege;
    }

    public int gefundenePaare() {
        return gefundenePaare;
    }

    /** Das Symbol eines Feldes &ndash; fuer die Oberflaeche und fuer Tests. */
    public char wertVon(int px, int py) {
        return daten.gibFeldWert(px, py);
    }

    public Einstellungen einstellungen() {
        return einstellungen;
    }
}
