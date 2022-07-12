package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Dealer {

    static ArrayList[] dealCards(ArrayList<Card> cardsArray){
        Random r = new Random();
        ArrayList[] cardsArrays = new ArrayList[3];
        cardsArrays[0] = new ArrayList<Card>();
        cardsArrays[1] = new ArrayList<Card>();
        cardsArrays[2] = new ArrayList<Card>();
        while(cardsArray.size() != 0) {
            int index = r.nextInt(cardsArray.size());
            Card selectedCard = cardsArray.get(index);
            cardsArray.remove(index);

            if(cardsArrays[0].size() < 5) {
                cardsArrays[0].add(selectedCard);
                continue;
            }
            if(cardsArrays[1].size() < 5) {
                cardsArrays[1].add(selectedCard);
                continue;
            }
            cardsArrays[2].add(selectedCard);

        }
        return cardsArrays;
    }
}