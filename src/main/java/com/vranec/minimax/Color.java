package com.vranec.minimax;

public enum Color {
    WHITE("BLACK"), BLACK("WHITE");

    private final Color otherColor;
    
    private Color(String otherColor) {
        this.otherColor = Color.valueOf(otherColor);
    }
    
    public Color getOtherColor() {
        return otherColor;
    }
}
