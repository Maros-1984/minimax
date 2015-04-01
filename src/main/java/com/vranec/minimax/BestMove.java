package com.vranec.minimax;

public class BestMove implements Comparable<BestMove> {
    private Move move;
    private int value;

    public BestMove(int boardValue) {
        this.value = boardValue;
    }

    public BestMove(Move move2) {
        this.move = move2;
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

    public int compareTo(BestMove o) {
        if (o.value == value) {
            return 0;
        }
        return o.value > value ? 1 : -1;
    }

    @Override
    public String toString() {
        return "" + move + ", val=" + value;
    }
}
