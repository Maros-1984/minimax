package com.vranec.minimax;

import java.util.Arrays;

public class TestBoard implements Board<TestMove> {
    private int counter;
    private Color playerOnTurn;

    TestBoard(int initialCounter) {
        this.counter = initialCounter;
        this.playerOnTurn = Color.COMPUTER;
    }

    public boolean isGameOver() {
        return counter <= 0;
    }

    public int getBoardValue(Color color) {
        if (counter <= 0 && color == playerOnTurn) {
            return -Integer.MAX_VALUE;
        } else if (counter <= 0) {
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    public Iterable<TestMove> getNextBoards(Color color) {
        return Arrays.asList(TestMove.ONE, TestMove.TWO, TestMove.THREE);
    }

    public Board<TestMove> apply(TestMove move) {
        this.counter -= move.getValue();
        this.playerOnTurn = playerOnTurn.getOtherColor();
        return this;
    }

    public void undo(TestMove move) {
        this.counter += move.getValue();
        this.playerOnTurn = playerOnTurn.getOtherColor();
    }

    @Override
    public long uniqueHashCode() {
        return counter;
    }

    @Override
    public String toString() {
        return "TestBoard " + counter;
    }
}
