package com.vranec.minimax;

public interface Board {
    boolean isGameOver();

    public int getBoardValue(Color color);

    public Iterable<Board> getNextBoards(Color color);
}
