package com.vranec.minimax;

import java.util.Objects;

public class TranspositionTableKey {
    private final Color playerOnTurn;
    private final Object key;

    public TranspositionTableKey(Color playerOnTurn, Object key) {
        this.playerOnTurn = playerOnTurn;
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranspositionTableKey that = (TranspositionTableKey) o;
        return playerOnTurn == that.playerOnTurn && key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerOnTurn, key);
    }
}
