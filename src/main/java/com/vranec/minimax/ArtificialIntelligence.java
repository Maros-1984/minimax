package com.vranec.minimax;

public class ArtificialIntelligence {
    public BestMove negamax(Board board, int depth, Color color) {
        return negamax(board, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE, color);
    }

    /**
     * 
     * @param board
     *            Current board state.
     * @param depth
     *            Depth to search in.
     * @param color
     *            Who is on the move.
     * @return
     */
    public BestMove negamax(Board board, int depth, int alpha, int beta, Color color) {
        if (depth == 0 || board.isGameOver()) {
            return new BestMove(board.getBoardValue(color));
        }
        BestMove bestMove = new BestMove(-Integer.MAX_VALUE);
        for (Board nextBoard : board.getNextBoards(color)) {
            BestMove nextBestMove = negamax(nextBoard, depth - 1, -beta, -alpha, color.getOtherColor());
            int val = -nextBestMove.getValue();
            if (val > bestMove.getValue()) {
                bestMove = nextBestMove;
                bestMove.setValue(val);
                bestMove.setBestBoard(nextBoard);
                alpha = Math.max(alpha, val);
            }
            if (alpha >= beta) {
                break;
            }
        }
        return bestMove;
    }
}
