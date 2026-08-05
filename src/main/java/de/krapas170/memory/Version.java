package de.krapas170.memory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Die eigene Programmversion und der Vergleich zweier Versionsangaben.
 *
 * <p>Die Version kommt aus {@code version.properties}, in das Maven beim Bauen
 * die Projektversion einsetzt. Frueher wurde dafuer die Datei
 * {@code version.json} relativ zum Arbeitsverzeichnis gelesen, was ausserhalb
 * der IDE nie funktioniert hat.</p>
 */
public final class Version {

    private static final String UNBEKANNT = "0.0.0";

    private Version() {
    }

    /** Die Version dieses Programms, zum Beispiel {@code 1.5.0}. */
    public static String lokal() {
        try (InputStream eingang = Version.class.getResourceAsStream("/version.properties")) {
            if (eingang == null) {
                return UNBEKANNT;
            }
            Properties eigenschaften = new Properties();
            eigenschaften.load(eingang);
            String wert = eigenschaften.getProperty("version", "").trim();
            return wert.isEmpty() || wert.startsWith("${") ? UNBEKANNT : wert;
        } catch (IOException e) {
            return UNBEKANNT;
        }
    }

    /**
     * Vergleicht zwei Versionen abschnittsweise als Zahlen.
     *
     * <p>Damit gilt {@code 1.10 > 1.9} und {@code 1.5.0 == 1.5}. Der frueher
     * benutzte {@code !equals}-Vergleich meldete auch dann ein Update, wenn
     * die lokale Version die neuere war.</p>
     *
     * @return negativ wenn {@code a < b}, 0 bei Gleichstand, positiv wenn {@code a > b}
     */
    public static int vergleiche(String a, String b) {
        String[] teileA = zerlege(a);
        String[] teileB = zerlege(b);
        int laenge = Math.max(teileA.length, teileB.length);
        for (int i = 0; i < laenge; i++) {
            int zahlA = i < teileA.length ? alsZahl(teileA[i]) : 0;
            int zahlB = i < teileB.length ? alsZahl(teileB[i]) : 0;
            if (zahlA != zahlB) {
                return Integer.compare(zahlA, zahlB);
            }
        }
        return 0;
    }

    private static String[] zerlege(String version) {
        if (version == null || version.isBlank()) {
            return new String[0];
        }
        return version.trim().split("[.\\-+_]");
    }

    private static int alsZahl(String abschnitt) {
        StringBuilder ziffern = new StringBuilder();
        for (int i = 0; i < abschnitt.length(); i++) {
            char zeichen = abschnitt.charAt(i);
            if (!Character.isDigit(zeichen)) {
                break;
            }
            ziffern.append(zeichen);
        }
        if (ziffern.length() == 0) {
            return 0;
        }
        try {
            return Integer.parseInt(ziffern.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
