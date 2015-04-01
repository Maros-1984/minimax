package com.vranec.minimax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ArtificialIntelligence {
    private Cache<Integer, TranspositionTableEntry> transpositionTable = CacheBuilder.newBuilder().maximumSize(1000000)
            .recordStats().build();

    public BestMove getBestMoveIterativeDeepening(Board board, int depth, Color color) {
        return getBestMoveTimedIterativeDeepeningTimed(board, depth, color, Long.MAX_VALUE);
    }

    public BestMove getBestMoveTimedIterativeDeepeningTimed(Board board, int depth, Color color, long timeToStop) {
        List<BestMove> bestMoves = new ArrayList<BestMove>();
        BestMove lastKnownBestMove = null;
        for (Move move : board.getNextBoards(color)) {
            bestMoves.add(new BestMove(move));
        }

        int currentDepth = 0;
        for (; currentDepth < depth; currentDepth++) {
            for (BestMove move : bestMoves) {
                Board nextBoard = board.apply(move.getMove());
                int value = -getBestMove(nextBoard, currentDepth, color.getOtherColor(), timeToStop).getValue();
                move.setValue(value);
                nextBoard.undo(move.getMove());
            }
            Collections.sort(bestMoves);
            if (System.currentTimeMillis() > timeToStop) {
                break;
            }
            lastKnownBestMove = bestMoves.get(0);
            if (lastKnownBestMove.getValue() == Integer.MAX_VALUE) {
                return lastKnownBestMove;
            }
        }
        System.out.println(transpositionTable.stats());
        System.out.println("Depth searched: " + currentDepth);

        return lastKnownBestMove;
    }

    public BestMove getBestMove(Board board, int depth, Color color, long timeToStop) {
        BestMove alphaBeta = alphaBeta(board, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE, color, timeToStop);
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
    public BestMove alphaBeta(Board board, int depth, int alpha, int beta, Color color, long timeToStop) {
        int originalAlpha = alpha;

        if (isTranspositionTableUsed()) {
            // Check the transposition table.
            TranspositionTableEntry ttEntry = (TranspositionTableEntry) transpositionTable.getIfPresent(board.hashCode());
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
        }

        // Do the computation the hard way.
        if (depth == 0 || System.currentTimeMillis() > timeToStop || board.isGameOver()) {
            return new BestMove(board.getBoardValue(color));
        }

        if (isNullHeuristicOn() && depth >= 3) {
            int value = -alphaBeta(board, depth - 1 - 2, -beta, -beta + 1, color.getOtherColor(), timeToStop).getValue();
            if (value >= beta) {
                return new BestMove(value);
            }
        }

        BestMove bestMove = new BestMove(-Integer.MAX_VALUE);
        for (Move nextMove : board.getNextBoards(color)) {
            Board nextBoard = board.apply(nextMove);
            BestMove nextBestMove = alphaBeta(nextBoard, depth - 1, -beta, -alpha, color.getOtherColor(), timeToStop);
            int val = -nextBestMove.getValue();
            if (val > bestMove.getValue()) {
                bestMove.setValue(val);
                bestMove.setMove(nextMove);
                alpha = Math.max(alpha, val);
            }
            nextBoard.undo(nextMove);
            if (alpha >= beta) {
                break;
            }
        }

        if (isTranspositionTableUsed()) {
            // Store the best move into the transposition table.
            transpositionTable.put(board.hashCode(), new TranspositionTableEntry(bestMove, originalAlpha, beta, depth));
        }

        return bestMove;
    }

    private boolean isNullHeuristicOn() {
        return true;
    }

    private boolean isTranspositionTableUsed() {
        return true;
    }
}
