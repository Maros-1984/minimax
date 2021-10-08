package com.vranec.minimax;

public class TranspositionTableEntry<MoveType extends Move> {
    private final TranspositionTableEntryType type;
    private final BestMove<MoveType> bestMove;
    private final int depth;

    public TranspositionTableEntry(BestMove<MoveType> bestMove, int originalAlpha, int beta, int depth2) {
        this.bestMove = bestMove;
        if (this.bestMove.getValue() <= originalAlpha) {
            type = TranspositionTableEntryType.LOWERBOUND;
        } else if (this.bestMove.getValue() >= beta) {
            type = TranspositionTableEntryType.UPPERBOUND;
        } else {
            type = TranspositionTableEntryType.EXACT;
        }
        this.depth = depth2;
    }

    @Override
    public String toString() {
        return "TranspositionTableEntry{" +
                "type=" + type +
                ", bestMove=" + bestMove +
                ", depth=" + depth +
                '}';
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