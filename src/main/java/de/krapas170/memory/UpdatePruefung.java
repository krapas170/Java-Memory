package de.krapas170.memory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

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
            String inhalt = new String(eingang.readAllBytes(), StandardCharsets.UTF_8);
            return new JSONObject(inhalt).getString("version");
        } finally {
            verbindung.disconnect();
        }
    }

    /** Nur eine echt hoehere Serverversion gilt als Update. */
    public static boolean istUpdateVerfuegbar(String serverVersion, String lokaleVersion) {
        return Version.vergleiche(serverVersion, lokaleVersion) > 0;
    }
}
