package org.example;

public enum CardSuite {

    CLUB("ŻOŁĄDŹ"),
    SPADE("PIK"),
    HEART("CZERWO"),
    DIAMOND("DZWONEK");

    private String plSuit;

    CardSuite(String plSuit) {
        this.plSuit = plSuit;
    }

}
