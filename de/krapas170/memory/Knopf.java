package de.krapas170.memory;

import javax.swing.JButton;

public class Knopf extends JButton {
    private int x, y;

    public Knopf(int px, int py) {
        x = px;
        y = py;
    }

    public int gibX() {
        return x;
    }

    public int gibY() {
        return y;
    }
}