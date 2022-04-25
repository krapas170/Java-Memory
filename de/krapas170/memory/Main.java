package de.krapas170.memory;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    static Menue start = new Menue();

    private final Gson gson = new Gson();
    private static HashMap<String, Object> contents;

    public static void main(String[] args) {

        checkVersion();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        boolean GameIsVisable = true;

        boolean MenueIsActive = start.isActive();

        runMenue();

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
    }

    public static void runMenue() {
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.fuegeAllesZumMenueHinzu(start.getContentPane());
        start.pack();
        start.setVisible(true);
    }

    public static void beiFehlerSchließen(String uberschrift, String meldung) {

        ImageIcon icon = new ImageIcon("assets/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    public static void beiFehlerFortsetzen(String uberschrift, String meldung) {

        ImageIcon icon = new ImageIcon("assets/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
    }

    static int Hoehe;
    static int Breite;
    static int Zeit;

    public static void checkVersion() {

        // JOptionPane.showInputDialog(null, "Überprüfe auf Updates, bitte habe einen
        // Moment Geduld.");

        String message = "Überprüfe auf Updates, bitte habe einen Moment Geduld.";

        final JDialog d = new JDialog();
        d.setTitle("Überprüfe Version");
        d.setSize(400, 150);

        JLabel message_label = new JLabel(message.toString(), JLabel.CENTER);
        d.add(message_label);
        d.setVisible(true);

        try (BufferedInputStream inputStream = new BufferedInputStream(
                new URL("https://raw.githubusercontent.com/krapas170/Java-Memory/main/version.json").openStream());
                FileOutputStream fileOS = new FileOutputStream("version-server.json")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
            isTwoJsonEquals();
        } catch (IOException e) {
            System.out.print(Farben.ANSI_RED + "Fehler beim Überprüfen der aktuellen Version" + Farben.ANSI_RESET);
        }
        Thread tr1 = new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000); // <-- Wartezeit in Millisekunden
                    d.dispose();
                } catch (InterruptedException ex) {
                }
            }
        };
        tr1.start();

    }

    public static void isTwoJsonEquals() {
        String local = "version.json";
        String server = "version-server.json";

        String localversion;
        String serverversion;

        try (Reader reader = new FileReader(local)) {

            JsonObject jsonObject = (JsonObject) JsonParser.parseReader(reader);

            localversion = jsonObject.get("version").toString();
            System.out.println(localversion);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader(server)) {


            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            serverversion = jsonObject.get("version").toString();
            System.out.println(serverversion);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object get(String key) {
        return contents.get(key);
    }

    /*
     * Get the config-value as string to the specified key
     * Make sure that the value is a String, else the is an Error
     */
    public static String getString(String key) {
        return (String) get(key);
    }
}