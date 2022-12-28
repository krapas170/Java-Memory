package de.krapas170.memory;

import javax.swing.JButton;

public class Knopf extends JButton {
    private int x, y;

    protected Knopf(int px, int py) {
        x = px;
        y = py;
    }

    protected int gibX() {
        return x;
    }

    protected int gibY() {
        return y;
    }
}