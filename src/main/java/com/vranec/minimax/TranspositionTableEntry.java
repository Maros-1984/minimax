package com.vranec.minimax;

public class TranspositionTableEntry<MoveType extends Move> {
    private final TranspositionTableEntryType type;
    private final BestMove<MoveType> bestMove;
    private final int depth;

    public TranspositionTableEntry(BestMove<MoveType> bestMove2, int originalAlpha, int beta, int depth2) {
        this.bestMove = bestMove2;
        if (bestMove.getValue() <= originalAlpha) {
            type = TranspositionTableEntryType.UPPERBOUND;
        } else if (bestMove.getValue() >= beta) {
            type = TranspositionTableEntryType.LOWERBOUND;
        } else {
            type = TranspositionTableEntryType.EXACT;
        }
        this.depth = depth2;
    }

    public BestMove<MoveType> getBestMove() {
        return bestMove;
    }

    public TranspositionTableEntryType getType() {
        return type;
    }

    public int getDepth() {
        return depth;
    }
}