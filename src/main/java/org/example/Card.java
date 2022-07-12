package org.example;

public class Card {
    private CardSuite suit;
    Rank rank;


    Card(CardSuite suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    int getCardRank() {
        return rank.getRank();
    }

    public CardSuite getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }
}

