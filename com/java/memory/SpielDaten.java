package com.java.memory;

import java.util.ArrayList;
import java.util.Collections;

class SpielDaten {

    private char memoryfeld[][];
    private int zzahl;

    public ArrayList<Integer> vergleich = new ArrayList<>();
    public ArrayList<Integer> arrayeins = new ArrayList<>();
    public ArrayList<Integer> arrayzwei = new ArrayList<>();

    public SpielDaten(int xgroesse, int ygroesse) {

        memoryfeld = new char[xgroesse][ygroesse];
        ArrayList<Integer> BuchstabenListe = new ArrayList<>();


        for (int idx = 0; idx < xgroesse; idx++) {
            for (int idy = 0; idy < ygroesse; idy++) {

                int zzahl = (int) ((Math.random()) * (xgroesse * ygroesse) / 2 + 65);
                int anzahlBuchstabe = Collections.frequency(BuchstabenListe, zzahl);
                while (anzahlBuchstabe >= 2) {
                    zzahl = (int) ((Math.random()) * (xgroesse * ygroesse) / 2 + 65);
                    anzahlBuchstabe = Collections.frequency(BuchstabenListe, zzahl);
                }
                char letter = (char) zzahl;
                BuchstabenListe.add(zzahl);
                memoryfeld[idx][idy] = letter;
            }
        }

    }

    public char gibFeldWert(int px, int py) {
        return memoryfeld[px][py];
    }

}
