package de.krapas170.memory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Fragt beim Programmstart, ob es eine neuere Fassung gibt.
 *
 * <p>Die Abfrage hat feste Zeitlimits: Ist GitHub nicht erreichbar &ndash;
 * Schulnetz, Firewall, kein Internet &ndash; laeuft sie nach wenigen Sekunden
 * ins Leere und das Spiel startet ganz normal. Frueher hing das Programm ohne
 * Timeout am Splash-Dialog fest.</p>
 */
public final class UpdatePruefung {

    public static final String VERSION_URL =
            "https://raw.githubusercontent.com/krapas170/Java-Memory/main/version.json";

    public static final URI RELEASE_SEITE =
            URI.create("https://github.com/krapas170/Java-Memory/releases/latest");

    private static final int TIMEOUT_MS = 4000;

    private UpdatePruefung() {
    }

    /** Liest die auf dem Server veroeffentlichte Version. */
    public static String leseServerVersion() throws IOException {
        HttpURLConnection verbindung = (HttpURLConnection) URI.create(VERSION_URL).toURL().openConnection();
        verbindung.setConnectTimeout(TIMEOUT_MS);
        verbindung.setReadTimeout(TIMEOUT_MS);
        verbindung.setRequestProperty("Accept", "application/json");
        try (InputStream eingang = verbindung.getInputStream()) {
            return leseVersionAus(new String(eingang.readAllBytes(), StandardCharsets.UTF_8));
        } finally {
            verbindung.disconnect();
        }
    }

    /**
     * Zieht die Versionsnummer aus dem Inhalt von {@code version.json}.
     *
     * <p>Bewusst vom Netzwerkzugriff getrennt: So laesst sich das Auswerten
     * pruefen, ohne dass ein Test online gehen muss. Das ist die einzige
     * Stelle im Programm, die org.json benutzt &ndash; ohne diese Trennung
     * wuerde eine neue Fassung der Bibliothek von keinem Test beruehrt.</p>
     *
     * @throws IOException wenn der Inhalt kein brauchbares JSON ist oder das
     *                     Feld {@code version} fehlt
     */
    static String leseVersionAus(String inhalt) throws IOException {
        try {
            String version = new JSONObject(inhalt).getString("version").trim();
            if (version.isEmpty()) {
                throw new IOException("Das Feld 'version' ist leer.");
            }
            return version;
        } catch (JSONException e) {
            throw new IOException("version.json ist nicht auswertbar: " + e.getMessage(), e);
        }
    }

    /** Nur eine echt hoehere Serverversion gilt als Update. */
    public static boolean istUpdateVerfuegbar(String serverVersion, String lokaleVersion) {
        return Version.vergleiche(serverVersion, lokaleVersion) > 0;
    }
}
