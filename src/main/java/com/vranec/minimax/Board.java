package com.vranec.minimax;

/**
 * Interface which must be implemented by board games in order to be used by this library AI.
 */
public interface Board<MoveType extends Move> {
    /**
     * @return Whether the game is now over.
     */
    boolean isGameOver();

    /**
     * Value of the board from the point of view of input player.
     * @param color Input player.
     * @return Value of the board from the point of view of given player.
     */
    int getBoardValue(Color color);

    /**
     * Returns the list of all possible moves.
     *
     * @param color Player on turn.
     * @return Iterable list of all possible moves.
     */
    Iterable<MoveType> getNextBoards(Color color);

    /**
     * Applies a move on the board.
     *
     * @param move Move to be applied.
     * @return New instance of the board.
     */
    Board<MoveType> apply(MoveType move);

    /**
     * Undoes the last move.
     *
     * @param move Move to be undid.
     */
    void undo(MoveType move);

    /**
     * @return Can be the board itself, as long as it is immutable or copied.
     */
    Object getTranspositionTableKey();

    /**
     * @return Whether the transposition table should be used at all. If false, you don't have to really implement the
     * uniqueHashCode.
     */
    boolean isTranspositionTableUsed();

    /**
     * @return Whether the null heuristic should be applied.
     */
    boolean isNullHeuristicOn();
}
