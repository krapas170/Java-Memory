package de.krapas170.memory;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Kleine Huelle um {@link JOptionPane}.
 *
 * <p>Der Zweck: {@link JOptionPane#CLOSED_OPTION} an einer Stelle behandeln.
 * Frueher wurde nur auf 0, 1 und 2 geprueft &ndash; wer den Dialog ueber das
 * Fenster-X oder mit Esc schloss, landete in einem Spiel, dessen Uhr bereits
 * abgeschaltet war, das aber weder neu startete noch endete.</p>
 */
public final class Dialoge {

    private Dialoge() {
    }

    /**
     * Zeigt eine Auswahl und liefert immer eine gueltige Antwort.
     *
     * @param beimSchliessen Antwort, die gelten soll, wenn der Dialog
     *                       weggeklickt statt beantwortet wird
     * @return der Index der gewaehlten Option
     */
    public static int frage(Component eltern, String titel, String text, Icon bild,
                            String[] optionen, int beimSchliessen) {
        int antwort = JOptionPane.showOptionDialog(
                eltern,
                text,
                titel,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                bild,
                optionen,
                optionen[0]);
        if (antwort == JOptionPane.CLOSED_OPTION || antwort < 0 || antwort >= optionen.length) {
            return beimSchliessen;
        }
        return antwort;
    }

    public static void hinweis(Component eltern, String titel, String text) {
        JOptionPane.showMessageDialog(eltern, text, titel, JOptionPane.WARNING_MESSAGE, Bilder.lade("Fehler.jpg"));
    }
}
