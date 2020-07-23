package com.vranec.minimax;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return 100 - counter;
    }

    public Iterable<TestMove> getNextBoards(Color color) {
        return Stream.of(TestMove.THREE, TestMove.TWO, TestMove.ONE)
                .filter(tm->tm.getValue() <= counter)
                .collect(Collectors.toList());
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
    public Object getTranspositionTableKey() {
        return counter;
    }

    @Override
    public boolean isTranspositionTableUsed() {
        return true;
    }

    @Override
    public boolean isNullHeuristicOn() {
        return false;
    }

    @Override
    public String toString() {
        return "TestBoard " + counter;
    }
}
