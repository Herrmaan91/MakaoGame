package org.example;

public enum Rank {
    TWO(2, ""),
    THREE(3, ""),
    FOUR(4, ""),
    FIVE(5, ""),
    SIX(6, ""),
    SEVEN(7, ""),
    EIGHT(8, ""),
    NINE(9, ""),
    TEN(10, ""),
    JACK(2, "Walet"),
    QUEEN(3, "Dama"),
    KING(4, "Król"),
    ACE(11, "As");

    private int rank;
    private String plRank;

    Rank(int rank, String plRank) {
        this.rank = rank;
        this.plRank = plRank;
    }
    public int getRank() {
        return rank;
    }
}
