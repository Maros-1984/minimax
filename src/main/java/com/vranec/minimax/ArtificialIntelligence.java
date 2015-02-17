package com.vranec.minimax;

public class ArtificialIntelligence {
    public BestMove negamax(Board board, int depth, Color color) {
        if (depth == 0 || board.isGameOver()) {
            return new BestMove(board.getBoardValue(color));
        }
        BestMove bestMove = new BestMove(Integer.MIN_VALUE);
        for (Move move : board.getNextMoves(color.getOtherColor())) {
            board.perform(move);
            int val = -negamax(board, depth - 1, color.getOtherColor()).getValue();
            if (val > bestMove.getValue()) {
                bestMove.setValue(val);
                bestMove.setMove(move);
            }
            board.undo(move);
        }
        return bestMove;
    }
}
