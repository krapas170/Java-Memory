package de.krapas170.memory;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Laedt die Bilder aus dem Classpath statt ueber relative Dateipfade.
 *
 * <p>{@code new ImageIcon("assets/pictures/...")} hat nur funktioniert, wenn
 * das Arbeitsverzeichnis zufaellig der Projektordner war &ndash; also in der
 * IDE, aber nicht im ausgelieferten JAR.</p>
 */
public final class Bilder {

    private Bilder() {
    }

    /**
     * @return das Bild oder {@code null}, wenn es fehlt. Dialoge kommen ohne
     *         Bild aus; ein fehlendes Bild darf das Spiel nicht anhalten.
     */
    public static Icon lade(String dateiname) {
        URL ort = Bilder.class.getResource("/assets/pictures/" + dateiname);
        return ort == null ? null : new ImageIcon(ort);
    }
}
