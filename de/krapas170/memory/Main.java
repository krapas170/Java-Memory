package de.krapas170.memory;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.json.JSONObject;

import jaco.mp3.player.MP3Player;

public class Main {
    static Menue start = new Menue();

    public static void main(String[] args) throws IOException {
        String message = "Überprüfe auf Updates, bitte habe einen Moment Geduld.";
        final JDialog a = new JDialog();
        a.setTitle("Überprüfe Version");
        a.setSize(400, 150);
        JLabel message_label = new JLabel(message, JLabel.CENTER);
        a.add(message_label);
        a.setVisible(true);
        try {
            String versionServer = readServerVersion();
            String versionLocal = readLocalVersion();
            if (!versionServer.equals(versionLocal)) {
                Object meldung = "Anscheinend gibt es eine neuere Version des Spiels.\nSoll sie jetzt heruntergeladen werden?";
                String uberschrift = "Neue Version verfügbar";
                int answer = JOptionPane.showOptionDialog(null, meldung, uberschrift, 1, 3, null,
                        null, null);
                if (answer == 0) {
                    Desktop d = Desktop.getDesktop();
                    try {
                        d.browse(new URI(
                                "https://github.com/krapas170/Java-Memory/releases/latest"));
                        d.browse(new URI(
                                "https://github.com/krapas170/Java-Memory/releases/latest/download/java-memory-installer.exe"));
                        try {
                            Thread.sleep(1000);
                            System.exit(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException | URISyntaxException e2) {
                        e2.printStackTrace();
                    }
                } else if (answer == 1) {
                    System.out.println("Spiel wird mit alter Version fortgesetzt");
                } else if (answer == 2) {
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            System.out.print(Farben.ANSI_RED + "Fehler beim Überprüfen der aktuellen Version" + Farben.ANSI_RESET);
        }
        Thread tr1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000); // <-- Wartezeit in Millisekunden
                    a.dispose();
                } catch (InterruptedException ex) {
                }
            }
        };
        tr1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        boolean MenueIsActive = start.isActive();
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.fuegeAllesZumMenueHinzu(start.getContentPane());
        start.pack();
        start.setVisible(true);
        while (MenueIsActive) {
            try {
                Thread.sleep(100);
                MenueIsActive = start.isActive();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SpielFeld gitter = new SpielFeld();
                gitter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gitter.fuegeAllesZurOberflaecheHinzu(gitter.getContentPane());
                gitter.pack();
                gitter.setVisible(true);
            }
        });
        Main.playTimer();
        int zeit1 = SpielFeld.zeit1();
        while (zeit1 >= 11) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zeit1 = SpielFeld.zeit1();
        }
        if (zeit1 >= 1) {
            Main.playCountdownEnd();
        }
    }

    protected static boolean isMenueActive() {
        boolean MenueIsActive = start.isActive();
        return MenueIsActive;
    }

    protected static void beiFehlerSchließen(String uberschrift, String meldung) {
        ImageIcon icon = new ImageIcon("assets/pictures/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    protected static void beiFehlerFortsetzen(String uberschrift, String meldung) {
        ImageIcon icon = new ImageIcon("assets/pictures/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
    }

    static String url;
    static MP3Player mp3_player = new MP3Player();

    protected static void playTimer() {
        mp3_player.stop();
        mp3_player.getPlayList().clear();
        mp3_player.addToPlayList(new File("assets/sound/timer.mp3"));
        mp3_player.play();
        mp3_player.setRepeat(true);
    }

    protected static void playCountdownEnd() {
        mp3_player.stop();
        mp3_player.setRepeat(false);
        mp3_player.getPlayList().clear();
        mp3_player.addToPlayList(new File("assets/sound/countdown-end.mp3"));
        mp3_player.play();
    }

    protected static void playGewonnen() {
        mp3_player.stop();
        mp3_player.setRepeat(false);
        mp3_player.getPlayList().clear();
        mp3_player.addToPlayList(new File("assets/sound/gewonnen.mp3"));
        mp3_player.play();
    }

    protected static void playVerloren() {
        mp3_player.stop();
        mp3_player.setRepeat(false);
        mp3_player.getPlayList().clear();
        mp3_player.addToPlayList(new File("assets/sound/verloren.mp3"));
        mp3_player.play();
    }

    static int Hoehe;
    static int Breite;
    static int Zeit;

    protected static void setzeEinstellungen(int height, int weight, int time) {
        Hoehe = height;
        Breite = weight;
        Zeit = time;
    }

    protected static int gibHoehe() {
        if (Hoehe >= 0) {
            return Hoehe;
        } else {
            System.out.println("Die Höhe hat keinen oder einen negativen Wert");
            return 0;
        }
    }

    protected static int gibBreite() {
        if (Breite >= 0) {
            return Breite;
        } else {
            System.out.println("Die Breite hat keinen oder einen negativen Wert");
            return 0;
        }
    }

    protected static int gibZeit() {
        if (Zeit >= 0) {
            return Zeit;
        } else {
            System.out.println("Die Zeit hat keinen oder einen negativen Wert");
            return 0;
        }
    }

    private static String readServerVersion() throws IOException {
        String URL = "https://raw.githubusercontent.com/krapas170/Java-Memory/main/version.json";
        URL url = new URL(URL);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        bufferedReader.close();
        String result = stringBuilder.toString().trim();
        JSONObject obj = new JSONObject(result);
        String version = obj.getString("version");
        return version;
    }

    private static String readLocalVersion() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("version.json"));
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        bufferedReader.close();
        String result = stringBuilder.toString().trim();
        JSONObject obj = new JSONObject(result);
        String version = obj.getString("version");
        return version;
    }
}
