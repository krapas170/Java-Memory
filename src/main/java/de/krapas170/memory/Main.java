package de.krapas170.memory;

import java.awt.Desktop;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

/**
 * Einstiegspunkt und Ablaufsteuerung.
 *
 * <p>Der Ablauf ist eine Kette von Rueckrufen: Das Menue meldet die fertigen
 * Einstellungen, das Spielfeld meldet, wie es weitergehen soll. Frueher
 * wartete {@code main} in einer Schleife mit {@code Thread.sleep(100)} darauf,
 * dass ein statisches Flag umspringt.</p>
 */
public final class Main {

    private static final Highscore HIGHSCORE = new Highscore();
    private static final Random ZUFALL = new Random();

    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::starte);
    }

    private static void starte() {
        setzeLookAndFeel();
        pruefeAufUpdate();
        zeigeMenue(Einstellungen.standard());
    }

    private static void zeigeMenue(Einstellungen vorgabe) {
        new Menue(vorgabe, Main::starteSpiel).setVisible(true);
    }

    private static void starteSpiel(Einstellungen einstellungen) {
        new SpielFeld(einstellungen, Main::starteSpiel, Main::zeigeMenue, HIGHSCORE, ZUFALL).setVisible(true);
    }

    // ------------------------------------------------------------------
    // Update-Pruefung
    // ------------------------------------------------------------------

    /**
     * Fragt im Hintergrund nach einer neueren Version.
     *
     * <p>Bewusst nebenlaeufig und ohne Rueckmeldung im Fehlerfall: Wer offline
     * spielt, soll davon nichts merken. Frueher blockierte diese Abfrage den
     * Start und zeigte bei jedem Fehlschlag einen Dialog.</p>
     */
    private static void pruefeAufUpdate() {
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return UpdatePruefung.leseServerVersion();
            }

            @Override
            protected void done() {
                try {
                    String serverVersion = get();
                    if (UpdatePruefung.istUpdateVerfuegbar(serverVersion, Version.lokal())) {
                        zeigeUpdateHinweis(serverVersion);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    // Kein Netz, kein Problem - das Spiel laeuft auch so.
                }
            }
        }.execute();
    }

    private static void zeigeUpdateHinweis(String serverVersion) {
        String[] optionen = {"Zur Download-Seite", "Spaeter"};
        int antwort = Dialoge.frage(
                null,
                "Neue Version verfuegbar",
                "Es gibt eine neuere Fassung des Spiels.\n"
                        + "Installiert ist " + Version.lokal() + ", verfuegbar ist " + serverVersion + ".",
                null,
                optionen,
                1);
        if (antwort == 0) {
            oeffneReleaseSeite();
        }
    }

    /** Oeffnet nur die Release-Seite; der Download startete frueher zusaetzlich ungefragt. */
    private static void oeffneReleaseSeite() {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(UpdatePruefung.RELEASE_SEITE);
                return;
            }
        } catch (Exception e) {
            // faellt unten auf den Hinweis zurueck
        }
        JOptionPane.showMessageDialog(
                null,
                "Bitte oeffne diese Seite im Browser:\n" + UpdatePruefung.RELEASE_SEITE,
                "Download",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void setzeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Das mitgelieferte Aussehen tut es auch.
        }
    }
}
