package com.vranec.minimax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ArtificialIntelligence {
    private Cache<Board, TranspositionTableEntry> transpositionTable = CacheBuilder.newBuilder().maximumSize(1000000)
            .recordStats().build();

    public BestMove getBestMove(Board board, int depth, Color color) {
        BestMove alphaBeta = null;
        for (int i = 1; i <= depth; i++) {
            alphaBeta = alphaBeta(board, i, -Integer.MAX_VALUE, Integer.MAX_VALUE, color);
        }
        System.out.println(transpositionTable.stats());
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
        List<Board> possibleMoves = new ArrayList<Board>();
        for (Board nextBoard : board.getNextBoards(color)) {
            possibleMoves.add(nextBoard);
        }
        Collections.sort(possibleMoves, new Comparator<Board>() {
            public int compare(Board o1, Board o2) {
                Integer o1value = Integer.MIN_VALUE, o2value = Integer.MIN_VALUE;
                TranspositionTableEntry o1tt = transpositionTable.getIfPresent(o1);
                if (o1tt != null) {
                    o1value = o1tt.getBestMove().getValue();
                }
                TranspositionTableEntry o2tt = transpositionTable.getIfPresent(o2);
                if (o2tt != null) {
                    o2value = o2tt.getBestMove().getValue();
                }

                return Integer.compare(o1value, o2value);
            }
        });
        for (Board nextBoard : board.getNextBoards(color)) {
            BestMove nextBestMove = alphaBeta(nextBoard, depth - 1, -beta, -alpha, color.getOtherColor());
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

        // Store the best move into the transposition table.
        transpositionTable.put(board, new TranspositionTableEntry(bestMove, originalAlpha, beta, depth));

        return bestMove;
    }
}
