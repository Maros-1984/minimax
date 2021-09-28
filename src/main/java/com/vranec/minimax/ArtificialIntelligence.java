package com.vranec.minimax;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtificialIntelligence<MoveType extends Move> {
    private final Cache<Object, TranspositionTableEntry<MoveType>> transpositionTable =
            Caffeine.newBuilder().maximumSize(1000000)
            .recordStats().build();

    public BestMove<MoveType> getBestMoveIterativeDeepening(Board<MoveType> board, int depth, Color color) {
        return getBestMoveTimedIterativeDeepeningTimed(board, depth, color, Long.MAX_VALUE);
    }

    public BestMove<MoveType> getBestMoveTimedIterativeDeepeningTimed(Board<MoveType> board, int depth, Color color, long timeToStop) {
        List<BestMove<MoveType>> bestMoves = new ArrayList<>();
        BestMove<MoveType> lastKnownBestMove = null;
        for (MoveType move : board.getNextBoards(color)) {
            bestMoves.add(new BestMove<>(move));
        }

        int currentDepth = 0;
        for (; currentDepth < depth; currentDepth++) {
            for (BestMove<MoveType> move : bestMoves) {
                Board<MoveType> nextBoard = board.apply(move.getMove());
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
        System.out.println("Last known best move value: " + lastKnownBestMove.getValue());

        return lastKnownBestMove;
    }

    public BestMove<MoveType> getBestMove(Board<MoveType> board, int depth, Color color, long timeToStop) {
        return alphaBeta(board, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE, color, timeToStop);
    }

    /**
     * 
     * @param board
     *            Current board state.
     * @param depth
     *            Depth to search in.
     * @param color
     *            Who is on the move.
     */
    public BestMove<MoveType> alphaBeta(Board<MoveType> board, int depth, int alpha, int beta, Color color, long timeToStop) {
        int originalAlpha = alpha;

        if (board.isTranspositionTableUsed()) {
            // Check the transposition table.
            TranspositionTableEntry<MoveType> ttEntry = transpositionTable.getIfPresent(board.getTranspositionTableKey());
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
            int boardValue = board.getBoardValue(color);
            if(boardValue == Integer.MIN_VALUE) {
                throw new IllegalStateException("Please use -Integer.MAX_VALUE for losing condition instead of" +
                        " Integer.MIN_VALUE.");
            }
            return new BestMove<>(boardValue);
        }

        if (board.isNullHeuristicOn() && depth >= 3) {
            int value = -alphaBeta(board, depth - 1 - 2, -beta, -beta + 1, color.getOtherColor(), timeToStop).getValue();
            if (value >= beta) {
                return new BestMove<>(value);
            }
        }

        BestMove<MoveType> bestMove = new BestMove<>(-Integer.MAX_VALUE);
        for (MoveType nextMove : board.getNextBoards(color)) {
            Board<MoveType> nextBoard = board.apply(nextMove);
            BestMove<MoveType> nextBestMove = alphaBeta(nextBoard, depth - 1, -beta, -alpha, color.getOtherColor(), timeToStop);
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

        if (board.isTranspositionTableUsed()) {
            // Store the best move into the transposition table.
            transpositionTable.put(board.getTranspositionTableKey(), new TranspositionTableEntry<>(bestMove, originalAlpha, beta, depth));
        }

        return bestMove;
    }
}
