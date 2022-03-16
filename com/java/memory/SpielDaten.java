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
        //Das ist der Konstruktor, hier werden Initialisierungen vorgenommen
        //diese Methode wird zum Erzeugen des Objekts aufgerufen, 
        //danach nicht mehr, es sei denn, es werden mehr Objekte vom 
        //selben Typ benötigt, was bei uns nicht der Fall ist.
        memoryfeld = new char[xgroesse][ygroesse];
        ArrayList<Integer> BuchstabenListe = new ArrayList<>();
        //ArrayList<Integer> vergleich = new ArrayList<>();
        //ArrayList<Integer> arrayeins = new ArrayList<>();
        //ArrayList<Integer> arrayzwei = new ArrayList<>();

        for (int idx = 0; idx < xgroesse; idx++) {
            for (int idy = 0; idy < ygroesse; idy++) {

                int zzahl = (int) ((Math.random()) * (xgroesse*ygroesse)/2 + 65);
                int anzahlBuchstabe = Collections.frequency(BuchstabenListe, zzahl);
                while (anzahlBuchstabe >= 2) {
                    zzahl = (int) ((Math.random()) * (xgroesse*ygroesse)/2 + 65);
                    anzahlBuchstabe = Collections.frequency(BuchstabenListe, zzahl);
                }
                char letter = (char) zzahl;
                BuchstabenListe.add(zzahl);
                memoryfeld[idx][idy] = letter;
            }
        }
        initialisiereMemoryFeld(xgroesse, ygroesse);

    }

    private void initialisiereMemoryFeld(int xgroesse, int ygroesse) {
        //das memoryfeld wird initialisiert, d.h. 
        //zwischen x = 0 und xgroesse, sowie
        //zwischen y = 0 und ygroesse werden 
        //Zeichen jeweils zufällig verteilt, 
        //so dass jedes Zeichen doppelt vorkommt.
        
        //int zzahl = (int) ((Math.random()) * 26 + 64);

        //char letter = (char) zzahl;
        
    }

    public char gibFeldWert(int px, int py) {
        //gibt den Wert des memoryfeld-arrays an der Stelle px/py zurück

        return memoryfeld[px][py];
    }

    
}
