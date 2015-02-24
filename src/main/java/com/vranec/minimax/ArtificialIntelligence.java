package com.vranec.minimax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ArtificialIntelligence {
    private Cache<Board, TranspositionTableEntry> transpositionTable = CacheBuilder.newBuilder().maximumSize(1000000)
            .recordStats().build();

    public BestMove getBestMoveIterativeDeepening(Board board, int depth, Color color) {
        List<BestMove> bestMoves = new ArrayList<BestMove>();
        for (Board nextBoard : board.getNextBoards(color)) {
            BestMove move = new BestMove(0);
            move.setBestBoard(nextBoard);
            bestMoves.add(move);
        }

        for (int currentDepth = 0; currentDepth < depth; currentDepth++) {
            for (BestMove move : bestMoves) {
                BestMove bestMove = getBestMove(move.getBestBoard(), currentDepth, color.getOtherColor());
                move.setValue(-bestMove.getValue());
            }
            Collections.sort(bestMoves);

        }
        System.out.println(transpositionTable.stats());
        return bestMoves.get(0);
    }

    public BestMove getBestMove(Board board, int depth, Color color) {
        BestMove alphaBeta = alphaBeta(board, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE, color);
        return alphaBeta;
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
    public BestMove alphaBeta(Board board, int depth, int alpha, int beta, Color color) {
        int originalAlpha = alpha;

        // Check the transposition table.
        TranspositionTableEntry ttEntry = (TranspositionTableEntry) transpositionTable.getIfPresent(board);
        if (ttEntry != null && ttEntry.getDepth() >= depth) {
            switch (ttEntry.getType()) {
            case EXACT:
                return ttEntry.getBestMove();
            case LOWERBOUND:
                alpha = Math.max(alpha, ttEntry.getBestMove().getValue());
                break;
            case UPPERBOUND:
                beta = Math.min(beta, ttEntry.getBestMove().getValue());
            }
            if (alpha >= beta) {
                return ttEntry.getBestMove();
            }
        }

        // Do the computation the hard way.
        if (depth == 0 || board.isGameOver()) {
            return new BestMove(board.getBoardValue(color));
        }

        BestMove bestMove = new BestMove(-Integer.MAX_VALUE);
        for (Board nextBoard : board.getNextBoards(color)) {
            BestMove nextBestMove = alphaBeta(nextBoard, depth - 1, -beta, -alpha, color.getOtherColor());
            int val = -nextBestMove.getValue();
            if (val > bestMove.getValue()) {
                bestMove.setValue(val);
                bestMove.setBestBoard(nextBoard);
                alpha = Math.max(alpha, val);
            }
            if (alpha >= beta) {
                break;
            }
        }

        // Store the best move into the transposition table.
        transpositionTable.put(board, new TranspositionTableEntry(bestMove, originalAlpha, beta, depth));

        return bestMove;
    }
}
