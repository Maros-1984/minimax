package com.vranec.minimax;

public class BestMove implements Comparable<BestMove> {
    private Board bestBoard;
    private int value;

    public BestMove(int boardValue) {
        this.value = boardValue;
    }

    public Board getBestBoard() {
        return bestBoard;
    }

    public void setBestBoard(Board bestBoard) {
        this.bestBoard = bestBoard;
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
}
