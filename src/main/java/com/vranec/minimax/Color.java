package com.vranec.minimax;

public enum Color {
    HUMAN("COMPUTER"), COMPUTER("HUMAN");

    private final String otherColorName;
    private Color otherColor;

    Color(String otherColor) {
        this.otherColorName = otherColor;
    }

    public Color getOtherColor() {
        if (otherColor == null) {
            otherColor = Color.valueOf(otherColorName);
        }
        return otherColor;
    }
}
