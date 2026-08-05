package de.krapas170.memory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

import javazoom.jl.player.Player;

/**
 * Spielt die Klaenge ab &ndash; aus dem Classpath, in einem eigenen
 * Hintergrund-Thread und ohne das Spiel aufzuhalten.
 *
 * <p>Ton ist Beiwerk: Faellt die Wiedergabe aus, laeuft das Spiel weiter.
 * Deshalb werden Fehler hier bewusst geschluckt.</p>
 */
public final class Klaenge {

    private static final Klaenge INSTANZ = new Klaenge();

    /** Zaehlt die Wiedergaben, damit ein alter Thread keinen neuen ueberschreibt. */
    private final AtomicLong generation = new AtomicLong();

    private Player player;
    private Thread thread;

    private Klaenge() {
    }

    public static Klaenge instanz() {
        return INSTANZ;
    }

    public void spieleTimer() {
        spiele("timer.mp3", true);
    }

    public void spieleCountdownEnde() {
        spiele("countdown-end.mp3", false);
    }

    public void spieleGewonnen() {
        spiele("gewonnen.mp3", false);
    }

    public void spieleVerloren() {
        spiele("verloren.mp3", false);
    }

    /** Beendet die laufende Wiedergabe. */
    public synchronized void stoppe() {
        generation.incrementAndGet();
        if (player != null) {
            player.close();
            player = null;
        }
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private synchronized void spiele(String dateiname, boolean wiederholen) {
        stoppe();
        final long meineGeneration = generation.get();

        Thread wiedergabe = new Thread(() -> {
            do {
                try (InputStream eingang = new BufferedInputStream(oeffne(dateiname))) {
                    Player neuerPlayer = new Player(eingang);
                    synchronized (Klaenge.this) {
                        if (generation.get() != meineGeneration) {
                            neuerPlayer.close();
                            return;
                        }
                        player = neuerPlayer;
                    }
                    neuerPlayer.play();
                } catch (Exception e) {
                    return; // Ton ist optional
                }
            } while (wiederholen
                    && generation.get() == meineGeneration
                    && !Thread.currentThread().isInterrupted());
        }, "Klang-" + dateiname);

        wiedergabe.setDaemon(true);
        thread = wiedergabe;
        wiedergabe.start();
    }

    private static InputStream oeffne(String dateiname) throws Exception {
        InputStream eingang = Klaenge.class.getResourceAsStream("/assets/sound/" + dateiname);
        if (eingang == null) {
            throw new IllegalStateException("Klangdatei nicht im Classpath: " + dateiname);
        }
        return eingang;
    }
}
