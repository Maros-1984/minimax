package com.vranec.minimax;

public class TranspositionTableEntry {
    private final TranspositionTableEntryType type;
    private final BestMove bestMove;
    private final int depth;

    public TranspositionTableEntry(BestMove bestMove2, int originalAlpha, int beta, int depth2) {
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

    public BestMove getBestMove() {
        return bestMove;
    }

    public TranspositionTableEntryType getType() {
        return type;
    }

    public int getDepth() {
        return depth;
    }
}