package de.krapas170.memory;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main {
    static Menue start = new Menue();
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
            inputStream.close();
            isTwoJsonEquals();
            Path server = FileSystems.getDefault().getPath("version-server.json");
            Files.delete(server);
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

    public static void isTwoJsonEquals() throws IOException {
        Path local = FileSystems.getDefault().getPath("version.json");
        Path server = FileSystems.getDefault().getPath("version-server.json");
        List localList = Files.readAllLines(local);
        List serverList = Files.readAllLines(server);
        if (localList.equals(serverList) != true) {
            Object meldung = (String) "Anscheinend gibt es eine neuere Version des Spiels.\nSoll sie jetzt heruntergeladen werden?";
            String uberschrift = "Neue Version verfügbar";
            // ImageIcon icon = new ImageIcon("assets/Fehler.jpg");
            int answer = JOptionPane.showOptionDialog(null, meldung, uberschrift, 1, 3, null,
                    null, null);
            System.out.println(answer);
            if (answer == 0) {
                Desktop d = Desktop.getDesktop();
                try {
                    d.browse(new URI("http://github.com/krapas170/Java-Memory/releases/tags/latest/"));
                    try {
                        Thread.sleep(1000);
                        System.exit(0);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } catch (IOException | URISyntaxException e2) {
                    e2.printStackTrace();
                }
            } else if (answer == 1) {
                Files.delete(server);
                System.out.println("Spiel wird mit alter Version fortgesetzt");
            } else if (answer == 2) {
                Files.delete(server);
                System.exit(0);
            }
        }
        Files.delete(server);
    }
}