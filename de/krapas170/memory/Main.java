package de.krapas170.memory;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
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

import jaco.mp3.player.MP3Player;

public class Main {
    static Menue start = new Menue();
    public static void main(String[] args) {
        String message = "Überprüfe auf Updates, bitte habe einen Moment Geduld.";
        final JDialog a = new JDialog();
        a.setTitle("Überprüfe Version");
        a.setSize(400, 150);
        JLabel message_label = new JLabel(message, JLabel.CENTER);
        a.add(message_label);
        a.setVisible(true);
        try (BufferedInputStream inputStream = new BufferedInputStream(
                new URL("https://raw.githubusercontent.com/krapas170/Java-Memory/main/version.json").openStream());
                FileOutputStream fileOS = new FileOutputStream("version-server.json")) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
                fileOS.write(data, 0, byteContent);
            }
            Path local = FileSystems.getDefault().getPath("version.json");
            Path server = FileSystems.getDefault().getPath("version-server.json");
            List localList = Files.readAllLines(local);
            List serverList = Files.readAllLines(server);
            if (!localList.equals(serverList)) {
                Object meldung = "Anscheinend gibt es eine neuere Version des Spiels.\nSoll sie jetzt heruntergeladen werden?";
                String uberschrift = "Neue Version verfügbar";
                int answer = JOptionPane.showOptionDialog(null, meldung, uberschrift, 1, 3, null,
                        null, null);
                if (answer == 0) {
                    Desktop d = Desktop.getDesktop();
                    try {
                        d.browse(new URI("https://github.com/krapas170/Java-Memory/releases/tag/latest"));
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
        } catch (IOException e) {
            System.out.print(Farben.ANSI_RED + "Fehler beim Überprüfen der aktuellen Version" + Farben.ANSI_RESET);
        }
        Thread tr1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000); // <-- Wartezeit in Millisekunden
                    a.dispose();
                } catch (InterruptedException ex) {
                }
            }
        };
        tr1.start();
        try {
            Thread.sleep(2000);
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
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                int zeit1 = SpielFeld.zeit1();
                int zeit2 = (zeit1 / 13);
                for (int index = 0; index < zeit2; index++) {
                    Main.playSound("timer.mp3", "play");
                    try {
                        Thread.sleep(13000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                int zeit1 = SpielFeld.zeit1();
                while (zeit1 >= 11) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    zeit1 = SpielFeld.zeit1();
                }
                Main.playSound(null, "stop");
                Main.playSound("countdown-end.mp3", "play");
            }
        };
        thread1.start();
        thread2.start();
    }

    public static void beiFehlerSchließen(String uberschrift, String meldung) {
        ImageIcon icon = new ImageIcon("assets/pictures/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

    public static void beiFehlerFortsetzen(String uberschrift, String meldung) {
        ImageIcon icon = new ImageIcon("assets/pictures/Fehler.jpg");
        JOptionPane.showMessageDialog(null, meldung, uberschrift,
                JOptionPane.INFORMATION_MESSAGE, icon);
    }

    static MP3Player mp3_player;

    public static void playSound(String url, String method) {
        try {
            mp3_player = new MP3Player(new File("assets/sound/" + url));
        } catch (Exception e) {
            System.err.println(e);
        }
        if ("play".equals(method)) {
            try {
                mp3_player.play();
            } catch (Exception e) {
                System.out.println("Error with playing sound.");
                System.err.println(e);
            }
        } else if ("stop".equals(method)) {
            try {
                mp3_player.stop();
            } catch (Exception e) {
                System.out.println("Error with stopping sound.");
                System.err.println(e);
            }
        } else if ("addToPlaylist".equals(method)) {
            try {
                mp3_player.addToPlayList(new File("assets/sound/" + url));
            } catch (Exception e) {
                System.out.println("Error with adding sound to playlist.");
                System.err.println(e);
            }
        } else {
            System.out.println("Error: No sound method");
        }
    }
}