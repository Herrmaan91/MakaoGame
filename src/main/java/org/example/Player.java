package org.example;

import java.util.ArrayList;

public class Player {
    ArrayList cards;

    int id;

    static int globId = 1;

    Player(ArrayList cards) {
        this.cards = cards;
        this.id = globId;
        globId++;
    }

    @Override
    public String toString() {
        return "Player{" +
                "cards=" + cards +
                ", id=" + id +
                '}';
    }
}

