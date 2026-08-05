package de.krapas170.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Attrappe fuer {@link SpielAnzeige}: schreibt die Aufrufe nur mit.
 *
 * <p>Die verzoegerte Aktion wird bewusst <em>nicht</em> automatisch
 * ausgefuehrt. So laesst sich pruefen, was passiert, waehrend ein falsches
 * Paar noch offen liegt.</p>
 */
class TestAnzeige implements SpielAnzeige {

    final List<String> ereignisse = new ArrayList<>();
    private Runnable offeneVerzoegerung;
    boolean gewonnen;
    int letzteZuege;

    @Override
    public void zeigeKarte(int x, int y, char wert) {
        ereignisse.add("zeige " + x + "/" + y + "=" + wert);
    }

    @Override
    public void verdeckeKarte(int x, int y) {
        ereignisse.add("verdecke " + x + "/" + y);
    }

    @Override
    public void markierePaar(int x1, int y1, int x2, int y2) {
        ereignisse.add("paar " + x1 + "/" + y1 + " " + x2 + "/" + y2);
    }

    @Override
    public void markiereFehlversuch(int x1, int y1, int x2, int y2) {
        ereignisse.add("fehler " + x1 + "/" + y1 + " " + x2 + "/" + y2);
    }

    @Override
    public void zeigeZuege(int zuege) {
        letzteZuege = zuege;
    }

    @Override
    public void spielGewonnen() {
        gewonnen = true;
        ereignisse.add("gewonnen");
    }

    @Override
    public void verzoegert(int millisekunden, Runnable aktion) {
        offeneVerzoegerung = aktion;
    }

    boolean hatOffeneVerzoegerung() {
        return offeneVerzoegerung != null;
    }

    /** Laesst die Zeit vergehen: fuehrt die wartende Aktion aus. */
    void fuehreVerzoegerungAus() {
        Runnable aktion = offeneVerzoegerung;
        offeneVerzoegerung = null;
        if (aktion != null) {
            aktion.run();
        }
    }
}
