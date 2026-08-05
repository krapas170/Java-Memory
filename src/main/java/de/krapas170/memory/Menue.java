package de.krapas170.memory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * Der Startbildschirm: Feldgroesse und Zeitlimit einstellen.
 *
 * <p>Die Werte kommen aus {@link JSpinner}n statt aus Textfeldern &ndash;
 * damit sind Buchstaben, leere Eingaben und negative Zahlen von vornherein
 * unmoeglich. Uebrig bleibt eine Regel, die ein Spinner nicht abbilden kann
 * (Hoehe mal Breite muss gerade und darf nicht zu gross sein); die wird beim
 * Klick geprueft und im Fenster erklaert.</p>
 *
 * <p>Das Menue wartet auf niemanden: Es ruft beim Start den uebergebenen
 * Rueckruf auf und schliesst sich. Frueher pollte der Aufrufer in einer
 * Schleife ein statisches Flag, das nach der ersten Runde nie wieder
 * zurueckgesetzt wurde.</p>
 */
public class Menue extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final int MAX_KANTE = 12;

    private final JSpinner hoeheWahl;
    private final JSpinner breiteWahl;
    private final JSpinner zeitWahl;
    private final JLabel hinweis = new JLabel(" ");
    private final transient Consumer<Einstellungen> beimStart;

    public Menue(Einstellungen vorgabe, Consumer<Einstellungen> beimStart) {
        super("Memory - Einstellungen");
        this.beimStart = beimStart;
        this.hoeheWahl = neuerSpinner(vorgabe.hoehe(), 1, MAX_KANTE);
        this.breiteWahl = neuerSpinner(vorgabe.breite(), 1, MAX_KANTE);
        this.zeitWahl = neuerSpinner(Math.max(1, vorgabe.minuten()), 1, 120);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        baueOberflaeche();
        pack();
        setLocationRelativeTo(null);
    }

    private void baueOberflaeche() {
        JPanel inhalt = new JPanel(new BorderLayout(0, 12));
        inhalt.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        inhalt.add(baueKopf(), BorderLayout.NORTH);
        inhalt.add(baueFormular(), BorderLayout.CENTER);
        inhalt.add(baueFuss(), BorderLayout.SOUTH);
        setContentPane(inhalt);
    }

    private JComponent baueKopf() {
        JLabel titel = new JLabel("Herzlich willkommen beim Memory-Spiel");
        titel.setFont(titel.getFont().deriveFont(Font.BOLD, 20f));

        JLabel erklaerung = new JLabel("<html>Stelle die Groesse des Spielfelds und das Zeitlimit ein.<br>"
                + "Hoehe mal Breite muss eine gerade Zahl ergeben, damit jede Karte<br>"
                + "einen Partner hat. Moeglich sind hoechstens " + Einstellungen.MAX_FELDER + " Felder.</html>");

        Box kopf = Box.createVerticalBox();
        titel.setAlignmentX(Component.LEFT_ALIGNMENT);
        erklaerung.setAlignmentX(Component.LEFT_ALIGNMENT);
        kopf.add(titel);
        kopf.add(Box.createVerticalStrut(8));
        kopf.add(erklaerung);
        return kopf;
    }

    private JComponent baueFormular() {
        JPanel formular = new JPanel(new GridLayout(3, 2, 12, 8));
        formular.add(new JLabel("Hoehe:"));
        formular.add(hoeheWahl);
        formular.add(new JLabel("Breite:"));
        formular.add(breiteWahl);
        formular.add(new JLabel("Zeitlimit in Minuten:"));
        formular.add(zeitWahl);

        JPanel vorlagen = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        vorlagen.add(new JLabel("Vorlagen:"));
        vorlagen.add(vorlagenKnopf("Leicht", 4, 4, 3));
        vorlagen.add(vorlagenKnopf("Mittel", 6, 6, 5));
        vorlagen.add(vorlagenKnopf("Schwer", 8, 8, 8));

        Box mitte = Box.createVerticalBox();
        formular.setAlignmentX(Component.LEFT_ALIGNMENT);
        vorlagen.setAlignmentX(Component.LEFT_ALIGNMENT);
        mitte.add(formular);
        mitte.add(Box.createVerticalStrut(10));
        mitte.add(vorlagen);
        return mitte;
    }

    private JComponent baueFuss() {
        hinweis.setForeground(new Color(0xC6, 0x28, 0x28));
        // Platz fuer zwei Zeilen: Bei 360x20 wurde die laengere der beiden
        // Meldungen abgeschnitten - ausgerechnet die, die erklaert, warum es
        // nicht weitergeht. Die feste Groesse verhindert, dass das Fenster
        // springt, sobald die Meldung erscheint oder verschwindet.
        hinweis.setPreferredSize(new Dimension(470, 38));
        hinweis.setVerticalAlignment(SwingConstants.TOP);

        JButton starten = new JButton("Spiel starten!");
        starten.addActionListener(e -> starteSpiel());
        getRootPane().setDefaultButton(starten);

        JPanel knopfleiste = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        knopfleiste.add(starten);

        JPanel fuss = new JPanel(new BorderLayout(0, 8));
        fuss.add(hinweis, BorderLayout.NORTH);
        fuss.add(knopfleiste, BorderLayout.SOUTH);
        return fuss;
    }

    private JButton vorlagenKnopf(String beschriftung, int breite, int hoehe, int minuten) {
        JButton knopf = new JButton(beschriftung);
        knopf.setToolTipText(breite + " x " + hoehe + " Felder, " + minuten + " Minuten");
        knopf.addActionListener(e -> {
            breiteWahl.setValue(breite);
            hoeheWahl.setValue(hoehe);
            zeitWahl.setValue(minuten);
            hinweis.setText(" ");
        });
        return knopf;
    }

    private void starteSpiel() {
        int breite = (Integer) breiteWahl.getValue();
        int hoehe = (Integer) hoeheWahl.getValue();
        int minuten = (Integer) zeitWahl.getValue();

        if ((breite * hoehe) % 2 != 0) {
            hinweis.setText("<html>Hoehe mal Breite muss eine gerade Zahl ergeben,<br>"
                    + "damit jede Karte einen Partner hat. Bitte eine der beiden Zahlen aendern.</html>");
            return;
        }
        if (breite * hoehe > Einstellungen.MAX_FELDER) {
            hinweis.setText("<html>Das waeren " + (breite * hoehe) + " Felder.<br>"
                    + "Erlaubt sind hoechstens " + Einstellungen.MAX_FELDER + ".</html>");
            return;
        }

        Einstellungen einstellungen = Einstellungen.ausMinuten(breite, hoehe, minuten);
        hinweis.setText(" ");
        dispose();
        beimStart.accept(einstellungen);
    }

    private static JSpinner neuerSpinner(int wert, int min, int max) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(Math.min(Math.max(wert, min), max), min, max, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "0");
        editor.getTextField().setColumns(4);
        spinner.setEditor(editor);
        return spinner;
    }
}
