package com.vranec.minimax;

public class BestMove {
    private Move move;
    private int value;

    public BestMove(int boardValue) {
        this.value = boardValue;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
