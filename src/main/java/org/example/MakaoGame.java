package org.example;

import java.util.ArrayList;
import java.util.Random;

public class MakaoGame {

    public static void main(String[] args) {

        //TWORZĘ LISTĘ WSZYSTKICH KART
        ArrayList<Card> cardsArray = createCards();

        //ROZDAJĘ KARTY UŻYTKOWNIKOM
        ArrayList[] cardsArrays = Dealer.dealCards(cardsArray);
        Player p1 = new Player(cardsArrays[0]);
        Player p2 = new Player(cardsArrays[1]);
        ArrayList restCards = cardsArrays[2];

        MakaoGame.game(p1, p2, restCards);

    }

    static void game(Player p1, Player p2, ArrayList restCards) {
        Random r = new Random();
        Player currentPlayer = p1;
        Card playedCard = MakaoGame.getFirstCardTOSTartGame(restCards);

        while (p1.cards.size() != 0 && p2.cards.size() != 0) {

            //dobieranie kart
            while (checkIhHand(playedCard, currentPlayer) == null) {
                Card newCard = (Card) restCards.get(MakaoGame.randomIndex(restCards));
                MakaoGame.takeCard(newCard, currentPlayer, restCards);
            }
            //gracz rzuca kartę
            playedCard = checkIhHand(playedCard, currentPlayer);

            //sprawdzenie co to za karta, czy trzeba wykonac niestandardowe czynnosci?
            if (playedCard.rank == Rank.FOUR) {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
                //sprawdzamy do przeciwnik ma tez czwórkę
                Player gamer = currentPlayer == p1 ? p2 : p1;
                while (checkIhHandByRank(playedCard, gamer) != null) {
                    //jestesmy tutaj jesli ma
                    playedCard = checkIhHandByRank(playedCard, gamer);
                    MakaoGame.giveCard(playedCard, gamer, restCards);
                    //przechodzimy do rywala rywala :)
                    gamer = gamer == p1 ? p2 : p1;
                    if (checkIhHandByRank(playedCard, gamer) == null) {
                        currentPlayer = gamer;
                    }
                }
                if (currentPlayer.cards.size() == 0) continue;
                //gracz który pozbył się wszystkich 4 lub ich nawet nie miał to currentPlayer i musi rzucić kolejną kartę
                playedCard = (Card) currentPlayer.cards.get(MakaoGame.randomIndex(currentPlayer.cards));
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
            } else if (playedCard.rank == Rank.TWO) {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
                //sprawdzamy do przeciwnik ma tez dwójkę
                Player gamer = currentPlayer == p1 ? p2 : p1;
                int count = 1;
                if (checkIhHandByRank(playedCard, gamer) != null) {
                    while (checkIhHandByRank(playedCard, gamer) != null) {
                        count++;
                        //jestesmy tutaj jesli ma
                        playedCard = checkIhHandByRank(playedCard, gamer);
                        MakaoGame.giveCard(playedCard, gamer, restCards);
                        //przechodzimy do rywala rywala :)
                        gamer = gamer == p1 ? p2 : p1;
                        if (checkIhHandByRank(playedCard, gamer) == null) {
                            // gamer pobiera kart w ilosci 2 x count z restCards
                            int cardAmount = 2 * count;
                            int k = 0;
                            if (restCards.size() < cardAmount) break;
                            while (k < cardAmount) {
                                Card randomCard = (Card) restCards.get(MakaoGame.randomIndex(restCards));
                                takeCard(randomCard, gamer, restCards);
                                k++;
                            }
                            currentPlayer = gamer;
                        }
                    }
                } else {
                    int k = 0;
                    while (k < 2) {
                        Card randomCard = (Card) restCards.get(MakaoGame.randomIndex(restCards));
                        takeCard(randomCard, gamer, restCards);
                        k++;
                    }
                    currentPlayer = gamer;
                }
            } else if (playedCard.rank == Rank.THREE) {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
                //sprawdzamy do przeciwnik ma tez trójkę
                Player gamer = currentPlayer == p1 ? p2 : p1;
                int count = 1;
                if (checkIhHandByRank(playedCard, gamer) != null) {
                    while (checkIhHandByRank(playedCard, gamer) != null) {
                        count++;
                        //jestesmy tutaj jesli ma
                        playedCard = checkIhHandByRank(playedCard, gamer);
                        MakaoGame.giveCard(playedCard, gamer, restCards);
                        //przechodzimy do rywala rywala :)
                        gamer = gamer == p1 ? p2 : p1;
                        if (checkIhHandByRank(playedCard, gamer) == null) {
                            // gamer pobiera kart w ilosci 2 x count z restCards
                            int cardAmount = 3 * count;
                            int k = 0;
                            while (k < cardAmount) {
                                if (restCards.size() < cardAmount) break;
                                Card randomCard = (Card) restCards.get(MakaoGame.randomIndex(restCards));
                                takeCard(randomCard, gamer, restCards);
                                k++;
                            }
                            currentPlayer = gamer;
                        }
                    }
                } else {
                    int k = 0;
                    while (k < 3) {
                        Card randomCard = (Card) restCards.get(MakaoGame.randomIndex(restCards));
                        takeCard(randomCard, gamer, restCards);
                        k++;
                    }
                    currentPlayer = gamer;
                }
            } else if (playedCard.rank == Rank.QUEEN) {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
                if (currentPlayer.cards.size() != 0) {
                    if (currentPlayer.cards.size() < 1) continue;
                    Card randomCard = (Card) currentPlayer.cards.get(MakaoGame.randomIndex(currentPlayer.cards));
                    MakaoGame.giveCard(randomCard, currentPlayer, restCards);
                }
            } else if (playedCard.rank == Rank.KING && (playedCard.getSuit() == CardSuite.HEART || playedCard.getSuit() == CardSuite.SPADE)) {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
                //sprawdzamy do przeciwnik ma tez króla
                Player gamer = currentPlayer == p1 ? p2 : p1;
                if (checkIhHandForKing(playedCard, gamer) != null) {
                    //jestesmy tutaj jesli ma
                    playedCard = checkIhHandForKing(playedCard, gamer);
                    MakaoGame.giveCard(playedCard, gamer, restCards);
                    //przechodzimy do rywala rywala :)
                    gamer = gamer == p1 ? p2 : p1;
                    // gamer pobiera kart w ilosci 10 z restCards
                    int cardAmount = 10;
                    int k = 0;
                    while (k < cardAmount) {
                        Card randomCard = (Card) restCards.get(MakaoGame.randomIndex(restCards));
                        takeCard(randomCard, gamer, restCards);
                        k++;
                    }
                    currentPlayer = gamer;

                } else {
                    int k = 0;
                    while (k < 5) {
                        Card randomCard = (Card) restCards.get(MakaoGame.randomIndex(restCards));
                        takeCard(randomCard, gamer, restCards);
                        k++;
                    }
                    currentPlayer = gamer;
                }
            } else if (playedCard.rank == Rank.ACE) {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
                //sprawdzamy czy przeciwnik ma tez asa
                Player gamer = currentPlayer == p1 ? p2 : p1;
                while (checkIhHandByRank(playedCard, gamer) != null) {
                    //jestesmy tutaj jesli ma
                    playedCard = checkIhHandByRank(playedCard, gamer);
                    MakaoGame.giveCard(playedCard, gamer, restCards);
                    //przechodzimy do rywala rywala :)
                    gamer = gamer == p1 ? p2 : p1;
                    if (checkIhHandByRank(playedCard, gamer) == null) {
                        currentPlayer = gamer;
                    }
                }
                Random random = new Random();
                int randomIndex = random.nextInt(4);
                CardSuite[] tablica = CardSuite.values();
                CardSuite wybranyKolor = tablica[randomIndex];
                playedCard = new Card(wybranyKolor, null);
            } else if (playedCard.rank == Rank.JACK) {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
                //sprawdzamy czy przeciwnik ma tez asa
                Player gamer = currentPlayer == p1 ? p2 : p1;
                while (checkIhHandByRank(playedCard, gamer) != null) {
                    //jestesmy tutaj jesli ma
                    playedCard = checkIhHandByRank(playedCard, gamer);
                    MakaoGame.giveCard(playedCard, gamer, restCards);
                    //przechodzimy do rywala rywala :)
                    gamer = gamer == p1 ? p2 : p1;
                    if (checkIhHandByRank(playedCard, gamer) == null) {
                        currentPlayer = gamer;
                    }
                }
                Random random = new Random();
                int randomIndex = random.nextInt(4);
                Rank[] tablica = Rank.values();
                Rank wybranyRanking = tablica[randomIndex];
                playedCard = new Card(null, wybranyRanking);
            } else {
                MakaoGame.giveCard(playedCard, currentPlayer, restCards);
            }
            currentPlayer = currentPlayer == p2 ? p1 : p2;

            System.out.println(p1);
            System.out.println(p2);
        }

        Player winner = p1.cards.size() == 0 ? p1 : p2;
        System.out.println("Wygrał gracz " + winner.id);
    }

    static Card getFirstCardTOSTartGame(ArrayList restCards) {
        Random r = new Random();
        Card drawedCard = (Card) restCards.get(r.nextInt(restCards.size()));
        while ((drawedCard.rank == Rank.TWO) || (drawedCard.rank == Rank.THREE) || (drawedCard.rank == Rank.FOUR) || (drawedCard.rank == Rank.JACK) || (drawedCard.rank == Rank.QUEEN) || (drawedCard.rank == Rank.KING && drawedCard.getSuit() == CardSuite.HEART) || (drawedCard.rank == Rank.KING && drawedCard.getSuit() == CardSuite.SPADE) || (drawedCard.rank == Rank.ACE)) {
            drawedCard = (Card) restCards.get(r.nextInt(restCards.size()));
        }
        return drawedCard;
    }

    static int randomIndex(ArrayList list) {
        Random random = new Random();
        if (list.size() > 0) return random.nextInt(list.size());
        return 0;
    }

    static Card checkIhHand(Card card, Player p) {
        for (Object c : p.cards) {
            Card playerCard = (Card) c;
            if (playerCard.rank == card.rank) return playerCard;
            if (playerCard.rank == Rank.QUEEN) return playerCard;
        }
        for (Object c : p.cards) {
            Card playerCard = (Card) c;
            if (playerCard.getSuit() == card.getSuit()) return playerCard;
        }
        return null;
    }

    static Card checkIhHandByRank(Card card, Player p) {
        for (Object c : p.cards) {
            Card playerCard = (Card) c;
            if (playerCard.rank == card.rank) return playerCard;
        }
        return null;
    }

    static Card checkIhHandForKing(Card card, Player p) {
        for (Object c : p.cards) {
            Card playerCard = (Card) c;
            if ((playerCard.rank == card.rank && playerCard.getSuit() == CardSuite.HEART) || (playerCard.rank == card.rank && playerCard.getSuit() == CardSuite.SPADE))
                return playerCard;
        }
        return null;
    }

    static void takeCard(Card card, Player player, ArrayList restCards) {
        restCards.remove(card);
        player.cards.add(card);
    }

    static void giveCard(Card card, Player player, ArrayList restCards) {
        restCards.add(card);
        player.cards.remove(card);
    }

    static ArrayList<Card> createCards() {
        ArrayList<Card> cardsArray = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            for (CardSuite suit : CardSuite.values()) {
                cardsArray.add(new Card(suit, rank));
            }
        }
        return cardsArray;
    }
}