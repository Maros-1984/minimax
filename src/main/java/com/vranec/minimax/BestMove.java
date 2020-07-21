package com.vranec.minimax;

public class BestMove<MoveType extends Move> implements Comparable<BestMove<MoveType>> {
    private MoveType move;
    private int value;

    public BestMove(int boardValue) {
        this.value = boardValue;
    }

    public BestMove(MoveType move2) {
        this.move = move2;
    }

    public MoveType getMove() {
        return move;
    }

    public void setMove(MoveType move) {
        this.move = move;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int compareTo(BestMove<MoveType> o) {
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
