package de.krapas170.memory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * Das Spielfenster: Kartengitter, Uhr, Zugzaehler und Bedienleiste.
 *
 * <p>Alles hier laeuft im Event Dispatch Thread. Gewartet wird ausschliesslich
 * mit {@link Timer} (dem Swing-Timer), nie mit {@code Thread.sleep} &ndash;
 * ein schlafender EDT zeichnet nicht neu, weshalb frueher die zweite
 * aufgedeckte Karte praktisch unsichtbar blieb.</p>
 */
public class SpielFeld extends JFrame implements SpielAnzeige {

    private static final long serialVersionUID = 1L;

    /** Taktrate der Uhr. Feiner als eine Sekunde, damit die Anzeige nicht springt. */
    private static final int UHR_TAKT_MS = 200;

    /** Ab hier blinkt die Anzeige und der Schlussklang laeuft. */
    private static final int WARNUNG_AB_SEKUNDEN = 10;

    /** Kurze Pause, damit der Spieler das letzte Paar noch sieht. */
    private static final int SIEG_DIALOG_VERZOEGERUNG_MS = 700;

    private final Einstellungen einstellungen;
    private final transient Consumer<Einstellungen> beimNeustart;
    private final transient Consumer<Einstellungen> beimMenue;
    private final transient Highscore highscore;
    private final transient SpielLogik logik;

    private final Knopf[][] knoepfe;
    private final JPanel gitter;
    private final JLabel zeitAnzeige = new JLabel();
    private final JLabel zugAnzeige = new JLabel();
    private final JLabel rekordAnzeige = new JLabel();
    private final JButton pauseKnopf = new JButton("Pause");

    private final Timer uhr;
    private long endeMillis;
    private long restMillisBeiPause;
    private int verbleibendeSekunden;
    private boolean pausiert;
    private boolean schlussklangGespielt;
    private boolean beendet;

    public SpielFeld(Einstellungen einstellungen,
                     Consumer<Einstellungen> beimNeustart,
                     Consumer<Einstellungen> beimMenue,
                     Highscore highscore,
                     Random zufall) {
        super("Memory - " + einstellungen.breite() + " x " + einstellungen.hoehe());
        this.einstellungen = einstellungen;
        this.beimNeustart = beimNeustart;
        this.beimMenue = beimMenue;
        this.highscore = highscore;
        this.logik = new SpielLogik(einstellungen, this, zufall);
        this.knoepfe = new Knopf[einstellungen.breite()][einstellungen.hoehe()];
        this.gitter = new JPanel(new GridLayout(einstellungen.hoehe(), einstellungen.breite(), 4, 4));
        this.verbleibendeSekunden = einstellungen.sekunden();
        this.uhr = new Timer(UHR_TAKT_MS, e -> uhrTick());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baueOberflaeche();
        pack();
        setLocationRelativeTo(null);

        // Erst wenn das Fenster offen ist, kann eine Karte den Fokus annehmen.
        // Ohne das muesste man sich vor der ersten Pfeiltaste erst per Tab in
        // das Gitter hineinarbeiten.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                fokussiereErsteOffeneKarte();
            }
        });

        endeMillis = System.currentTimeMillis() + einstellungen.sekunden() * 1000L;
        uhr.start();
        Klaenge.instanz().spieleTimer();
    }

    // ------------------------------------------------------------------
    // Aufbau
    // ------------------------------------------------------------------

    private void baueOberflaeche() {
        JPanel inhalt = new JPanel(new BorderLayout(0, 8));
        inhalt.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inhalt.add(baueStatusleiste(), BorderLayout.NORTH);
        inhalt.add(baueGitter(), BorderLayout.CENTER);
        inhalt.add(baueBedienleiste(), BorderLayout.SOUTH);
        setContentPane(inhalt);
        aktualisiereZeitAnzeige();
        zeigeZuege(0);
        aktualisiereRekord();
    }

    private JComponent baueStatusleiste() {
        JPanel leiste = new JPanel(new GridLayout(1, 3, 12, 0));
        zeitAnzeige.setOpaque(true);
        zeitAnzeige.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        leiste.add(zeitAnzeige);
        leiste.add(zugAnzeige);
        leiste.add(rekordAnzeige);
        return leiste;
    }

    private JComponent baueGitter() {
        for (int y = 0; y < einstellungen.hoehe(); y++) {
            for (int x = 0; x < einstellungen.breite(); x++) {
                Knopf knopf = new Knopf(x, y);
                knopf.addActionListener(e -> {
                    if (!pausiert && !beendet) {
                        logik.klick(knopf.gibX(), knopf.gibY());
                    }
                });
                knoepfe[x][y] = knopf;
                gitter.add(knopf);
            }
        }
        richteTastaturEin();
        return gitter;
    }

    private JComponent baueBedienleiste() {
        JButton neustart = new JButton("Neustart");
        neustart.addActionListener(e -> starteNeu());

        pauseKnopf.addActionListener(e -> schaltePause());

        JButton menue = new JButton("Werte ändern");
        menue.addActionListener(e -> zurueckZumMenue());

        JButton beenden = new JButton("Beenden");
        beenden.addActionListener(e -> System.exit(0));

        // Eigene Leiste statt im Kartengitter: frueher sahen diese Knoepfe
        // aus wie zwei zusaetzliche Spielfelder.
        JPanel leiste = new JPanel(new GridLayout(1, 4, 8, 0));
        leiste.add(neustart);
        leiste.add(pauseKnopf);
        leiste.add(menue);
        leiste.add(beenden);
        return leiste;
    }

    /** Pfeiltasten bewegen den Fokus im Gitter; Leertaste deckt auf. */
    private void richteTastaturEin() {
        InputMap tasten = gitter.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap aktionen = gitter.getActionMap();
        tasten.put(KeyStroke.getKeyStroke("LEFT"), "links");
        tasten.put(KeyStroke.getKeyStroke("RIGHT"), "rechts");
        tasten.put(KeyStroke.getKeyStroke("UP"), "hoch");
        tasten.put(KeyStroke.getKeyStroke("DOWN"), "runter");
        aktionen.put("links", bewegeFokus(-1, 0));
        aktionen.put("rechts", bewegeFokus(1, 0));
        aktionen.put("hoch", bewegeFokus(0, -1));
        aktionen.put("runter", bewegeFokus(0, 1));
    }

    private Action bewegeFokus(int dx, int dy) {
        return new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                Component fokussiert = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
                if (!(fokussiert instanceof Knopf aktuell)) {
                    return;
                }
                int x = Math.floorMod(aktuell.gibX() + dx, einstellungen.breite());
                int y = Math.floorMod(aktuell.gibY() + dy, einstellungen.hoehe());
                knoepfe[x][y].requestFocusInWindow();
            }
        };
    }

    // ------------------------------------------------------------------
    // SpielAnzeige
    // ------------------------------------------------------------------

    @Override
    public void zeigeKarte(int x, int y, char wert) {
        knoepfe[x][y].zeige(wert);
    }

    @Override
    public void verdeckeKarte(int x, int y) {
        knoepfe[x][y].verdecke();
    }

    @Override
    public void markierePaar(int x1, int y1, int x2, int y2) {
        knoepfe[x1][y1].markiereAlsPaar();
        knoepfe[x2][y2].markiereAlsPaar();
    }

    @Override
    public void markiereFehlversuch(int x1, int y1, int x2, int y2) {
        knoepfe[x1][y1].markiereAlsFehler();
        knoepfe[x2][y2].markiereAlsFehler();
    }

    @Override
    public void zeigeZuege(int zuege) {
        zugAnzeige.setText("Züge: " + zuege);
    }

    @Override
    public void spielGewonnen() {
        // Die Uhr sofort anhalten. Sonst konnte sie waehrend der Verzoegerung
        // bis zum Dialog noch ablaufen: verloren() setzte dann beendet=true,
        // der wartende Sieg wurde verworfen, und der Spieler bekam trotz
        // gefundener Paare die Niederlage gemeldet.
        uhr.stop();
        verzoegert(SIEG_DIALOG_VERZOEGERUNG_MS, this::gewonnen);
    }

    @Override
    public void verzoegert(int millisekunden, Runnable aktion) {
        Timer einmalig = new Timer(millisekunden, e -> {
            if (!beendet) {
                aktion.run();
            }
        });
        einmalig.setRepeats(false);
        einmalig.start();
    }

    // ------------------------------------------------------------------
    // Uhr
    // ------------------------------------------------------------------

    private void uhrTick() {
        long restMillis = endeMillis - System.currentTimeMillis();
        verbleibendeSekunden = (int) Math.max(0, Math.ceil(restMillis / 1000.0));
        aktualisiereZeitAnzeige();

        if (verbleibendeSekunden <= WARNUNG_AB_SEKUNDEN && verbleibendeSekunden > 0 && !schlussklangGespielt) {
            schlussklangGespielt = true;
            Klaenge.instanz().spieleCountdownEnde();
        }
        if (verbleibendeSekunden <= 0) {
            verloren();
        }
    }

    private void aktualisiereZeitAnzeige() {
        zeitAnzeige.setText("Zeit: " + ZeitFormat.formatiere(verbleibendeSekunden));
        boolean warnung = verbleibendeSekunden <= WARNUNG_AB_SEKUNDEN && verbleibendeSekunden > 0;
        boolean blinkPhase = warnung && verbleibendeSekunden % 2 == 0;
        zeitAnzeige.setBackground(blinkPhase ? new Color(0xFF, 0xCD, 0xD2) : getContentPane().getBackground());
        zeitAnzeige.setForeground(warnung ? new Color(0xC6, 0x28, 0x28) : Color.BLACK);
    }

    /**
     * Zeigt beide Bestwerte getrennt an.
     *
     * <p>Frueher stand hier "Rekord: 66 Zuege in 02:58" &ndash; das las sich wie
     * eine einzelne Runde, war aber die Kombination aus zwei verschiedenen. Die
     * wenigsten Zuege und die schnellste Zeit werden unabhaengig gefuehrt.</p>
     */
    private void aktualisiereRekord() {
        int besteZuege = highscore.wenigsteZuege(einstellungen);
        int besteZeit = highscore.schnellsteZeit(einstellungen);
        if (besteZuege == 0 && besteZeit == 0) {
            rekordAnzeige.setText("Bestwerte: noch keine");
            rekordAnzeige.setToolTipText(null);
            return;
        }
        rekordAnzeige.setText("<html>Wenigste Züge: " + besteZuege + "<br>"
                + "Schnellste Zeit: " + ZeitFormat.formatiere(besteZeit) + "</html>");
        rekordAnzeige.setToolTipText(
                "Beide Bestwerte gelten für " + einstellungen.breite() + " x " + einstellungen.hoehe()
                        + " und können aus verschiedenen Runden stammen.");
    }

    private void schaltePause() {
        if (beendet) {
            return;
        }
        if (pausiert) {
            endeMillis = System.currentTimeMillis() + restMillisBeiPause;
            uhr.start();
            Klaenge.instanz().spieleTimer();
            pausiert = false;
            pauseKnopf.setText("Pause");
        } else {
            restMillisBeiPause = endeMillis - System.currentTimeMillis();
            uhr.stop();
            Klaenge.instanz().stoppe();
            pausiert = true;
            pauseKnopf.setText("Weiter");
        }
        setzeGitterAktiv(!pausiert);
    }

    private void setzeGitterAktiv(boolean aktiv) {
        gitter.setVisible(aktiv);
        if (aktiv) {
            // Waehrend der Pause ist das Gitter unsichtbar und verliert damit
            // den Tastaturfokus. Ohne diese Zeile waeren die Pfeiltasten nach
            // dem Fortsetzen wirkungslos.
            fokussiereErsteOffeneKarte();
        }
    }

    /**
     * Benennt beim Sieg genau den Bestwert, der gefallen ist.
     *
     * <p>Frueher stand dort nur "ein neuer Rekord" &ndash; wer schnell, aber
     * umstaendlich gespielt hatte, konnte daraus nicht ablesen, was er
     * eigentlich verbessert hatte.</p>
     */
    private static String rekordMeldung(Highscore.Verbesserung verbessert) {
        if (verbessert.wenigereZuege() && verbessert.schnellereZeit()) {
            return "\n\nNeuer Bestwert bei Zügen und Zeit!";
        }
        if (verbessert.wenigereZuege()) {
            return "\n\nNeuer Bestwert: so wenige Züge hast du hier noch nie gebraucht.";
        }
        if (verbessert.schnellereZeit()) {
            return "\n\nNeuer Bestwert: so schnell warst du hier noch nie.";
        }
        return "";
    }

    /** "1 Zug" statt "1 Zuege". */
    private static String zuegeText(int anzahl) {
        return anzahl + (anzahl == 1 ? " Zug" : " Züge");
    }

    /** "von 1 Paar" statt "von 1 Paaren". */
    private static String paareDativ(int anzahl) {
        return anzahl + (anzahl == 1 ? " Paar" : " Paaren");
    }

    /** Setzt den Tastaturfokus auf die erste noch nicht gefundene Karte. */
    private void fokussiereErsteOffeneKarte() {
        for (int y = 0; y < einstellungen.hoehe(); y++) {
            for (int x = 0; x < einstellungen.breite(); x++) {
                if (knoepfe[x][y].isEnabled()) {
                    knoepfe[x][y].requestFocusInWindow();
                    return;
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Spielende
    // ------------------------------------------------------------------

    private void gewonnen() {
        if (beendet) {
            return;
        }
        beendeRunde();
        Klaenge.instanz().spieleGewonnen();

        int benoetigt = einstellungen.sekunden() - verbleibendeSekunden;
        Highscore.Verbesserung verbessert = highscore.melde(einstellungen, logik.zuege(), benoetigt);

        String text = "Du hast alle Paare gefunden!\n"
                + "Gebraucht: " + zuegeText(logik.zuege()) + " in " + ZeitFormat.formatiere(benoetigt) + ".\n"
                + "Übrig waren " + ZeitFormat.formatiere(verbleibendeSekunden) + "."
                + rekordMeldung(verbessert);

        frageWieWeiter("Geschafft", text, Bilder.lade("gewonnen.gif"));
    }

    private void verloren() {
        if (beendet) {
            return;
        }
        beendeRunde();
        Klaenge.instanz().spieleVerloren();

        String text = "Die Zeit ist um und du hast es leider nicht geschafft.\n"
                + "Gefunden: " + logik.gefundenePaare() + " von " + paareDativ(einstellungen.paare()) + ".";

        frageWieWeiter("Zeit um", text, Bilder.lade("verloren.gif"));
    }

    private void beendeRunde() {
        beendet = true;
        uhr.stop();
        Klaenge.instanz().stoppe();
    }

    private void frageWieWeiter(String titel, String text, javax.swing.Icon bild) {
        String[] optionen = {"Nochmal", "Werte ändern", "Beenden"};
        // Wird der Dialog weggeklickt, geht es zurueck ins Menue. Das ist die
        // einzige Antwort, die weder Daten verwirft noch das Programm beendet.
        int antwort = Dialoge.frage(this, titel, text, bild, optionen, 1);
        switch (antwort) {
            case 0 -> starteNeu();
            case 2 -> System.exit(0);
            default -> zurueckZumMenue();
        }
    }

    private void starteNeu() {
        schliesse();
        beimNeustart.accept(einstellungen);
    }

    private void zurueckZumMenue() {
        schliesse();
        beimMenue.accept(einstellungen);
    }

    private void schliesse() {
        beendet = true;
        uhr.stop();
        Klaenge.instanz().stoppe();
        dispose();
    }
}
