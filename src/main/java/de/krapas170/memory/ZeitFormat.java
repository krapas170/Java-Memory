package de.krapas170.memory;

import java.time.Duration;

/**
 * Formatiert Sekunden als {@code mm:ss} beziehungsweise {@code h:mm:ss}.
 *
 * <p>Die frueheren zwei Kopien dieser Rechnung vergassen das {@code % 60} bei
 * den Minuten und zogen die Stunden zweimal ab; 3700 Sekunden wurden als
 * "61:-3560" angezeigt.</p>
 */
public final class ZeitFormat {

    private ZeitFormat() {
    }

    public static String formatiere(long sekunden) {
        long sicher = Math.max(0, sekunden);
        Duration dauer = Duration.ofSeconds(sicher);
        long stunden = dauer.toHours();
        if (stunden > 0) {
            return String.format("%d:%02d:%02d", stunden, dauer.toMinutesPart(), dauer.toSecondsPart());
        }
        return String.format("%02d:%02d", dauer.toMinutesPart(), dauer.toSecondsPart());
    }
}
