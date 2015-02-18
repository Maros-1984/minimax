package com.vranec.minimax;

public class ArtificialIntelligence {
    public BestMove negamax(Board board, int depth, Color color) {
        if (depth == 0 || board.isGameOver()) {
            return new BestMove(board.getBoardValue(color));
        }
        BestMove bestMove = new BestMove(Integer.MIN_VALUE);
        for (Board nextBoard : board.getNextBoards(color.getOtherColor())) {
            int val = -negamax(nextBoard, depth - 1, color.getOtherColor()).getValue();
            if (val > bestMove.getValue()) {
                bestMove.setValue(val);
                bestMove.setBestBoard(nextBoard);
            }
        }
        return bestMove;
    }
}
