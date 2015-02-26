package com.vranec.minimax;

public interface Board {
    boolean isGameOver();

    int getBoardValue(Color color);

    Iterable<Move> getNextBoards(Color color);

    Board apply(Move move);

    void undo(Move move);
}
