package com.vranec.minimax;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtificialIntelligenceTest {

    @Test
    void getBestMove_givenWinningMove_returnsIt() {
        BestMove<TestMove> bestMove = new ArtificialIntelligence<TestMove>().getBestMoveTimedIterativeDeepeningTimed(
                new TestBoard(3), Integer.MAX_VALUE, Color.COMPUTER, Long.MAX_VALUE);

        assertEquals(3, bestMove.getMove().getValue());
    }

    @Test
    void getBestMove_givenSimpleBestMove_returnsIt() {
        BestMove<TestMove> bestMove = new ArtificialIntelligence<TestMove>().getBestMoveTimedIterativeDeepeningTimed(
                new TestBoard(5), Integer.MAX_VALUE, Color.COMPUTER, Long.MAX_VALUE);

        assertEquals(1, bestMove.getMove().getValue());
    }

    @Test
    void getBestMove_givenFarBestMove_returnsIt() {
        BestMove<TestMove> bestMove = new ArtificialIntelligence<TestMove>().getBestMoveIterativeDeepening(
                new TestBoard(17), Integer.MAX_VALUE, Color.COMPUTER);

        assertEquals(1, bestMove.getMove().getValue());
        assertEquals(Integer.MAX_VALUE, bestMove.getValue());
    }

    @Test
    void getBestMove_givenTooShallowSearch_doesntReturnIt() {
        BestMove<TestMove> bestMove = new ArtificialIntelligence<TestMove>().getBestMoveTimedIterativeDeepeningTimed(
                new TestBoard(20), 8, Color.COMPUTER, Long.MAX_VALUE);

        assertEquals(97, bestMove.getValue());
    }
}