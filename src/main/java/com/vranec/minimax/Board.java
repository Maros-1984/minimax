package com.vranec.minimax;

public interface Board {
    boolean isGameOver();

    public int getBoardValue(Color color);

    public Iterable<Move> getNextMoves(Color color);

    void perform(Move move);

    void undo(Move move);
}
