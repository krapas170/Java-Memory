package de.krapas170.memory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/** Eine Karte auf dem Spielfeld. Kennt ihre Position im Gitter. */
public class Knopf extends JButton {

    private static final long serialVersionUID = 1L;

    private static final Color GRUEN = new Color(0x2E, 0x7D, 0x32);
    private static final Color HELLGRUEN = new Color(0xC8, 0xE6, 0xC9);
    private static final Color ROT = new Color(0xC6, 0x28, 0x28);
    private static final Color HELLROT = new Color(0xFF, 0xCD, 0xD2);

    private static final Border RAND_VERDECKT = BorderFactory.createLineBorder(new Color(0x90, 0x90, 0x90), 1);
    private static final Border RAND_PAAR = BorderFactory.createLineBorder(GRUEN, 3);
    private static final Border RAND_FEHLER = BorderFactory.createLineBorder(ROT, 3);

    private final int x;
    private final int y;

    public Knopf(int px, int py) {
        this.x = px;
        this.y = py;
        setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        setPreferredSize(new Dimension(64, 64));
        setFocusPainted(true);
        // Ohne diese beiden Zeilen ignorieren die meisten Look-and-Feels
        // setBackground() bei Buttons und die Karten bleiben immer grau.
        setContentAreaFilled(false);
        setOpaque(true);
        verdecke();
    }

    public int gibX() {
        return x;
    }

    public int gibY() {
        return y;
    }

    public void zeige(char wert) {
        setText(String.valueOf(wert));
        setBackground(Color.WHITE);
        setBorder(RAND_VERDECKT);
        setToolTipText("Karte " + wert);
    }

    public void verdecke() {
        setText(" ");
        setBackground(Color.WHITE);
        setBorder(RAND_VERDECKT);
        setEnabled(true);
        setToolTipText("Verdeckte Karte");
    }

    /**
     * Dauerhaft gefundenes Paar.
     *
     * <p>Neben der Farbe gibt es zwei weitere Hinweise &ndash; den dicken Rand
     * und den abgeschalteten Zustand &ndash; damit die Rueckmeldung nicht
     * allein von der Unterscheidung Rot/Gruen abhaengt.</p>
     */
    public void markiereAlsPaar() {
        setBackground(HELLGRUEN);
        setBorder(RAND_PAAR);
        setEnabled(false);
        setToolTipText("Gefunden");
    }

    public void markiereAlsFehler() {
        setBackground(HELLROT);
        setBorder(RAND_FEHLER);
        setToolTipText("Passt nicht");
    }
}
