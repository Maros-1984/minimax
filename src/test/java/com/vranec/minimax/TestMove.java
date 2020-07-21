package com.vranec.minimax;

public class TestMove implements Move {
    public static TestMove ONE = new TestMove(1);
    public static TestMove TWO = new TestMove(2);
    public static TestMove THREE = new TestMove(3);

    private final int value;

    private TestMove(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TestMove " + value;
    }
}
