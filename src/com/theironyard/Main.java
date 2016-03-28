package com.theironyard;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    static HashSet<Card> createDeck() {
        HashSet<Card> deck = new HashSet<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card c = new Card(suit, rank) ;
                deck.add(c);
            }
        }
        return deck;
    }

    static HashSet<HashSet<Card>> createHands(HashSet<Card> deck) {
        HashSet<HashSet<Card>> hands = new HashSet<>();
        for (Card c1 : deck) {
            HashSet<Card> deck2 =(HashSet<Card>) deck.clone();
            deck2.remove(c1);
            for (Card c2 : deck2) {
                HashSet<Card> deck3 = (HashSet<Card>) deck2.clone();
                deck3.remove(c2);
                for (Card c3 : deck3) {
                    HashSet<Card> deck4 = (HashSet<Card>) deck3.clone();
                    deck4.remove(c3);
                    for (Card c4 : deck4) {
                        HashSet<Card> hand = new HashSet<>();
                        hand.add(c1);
                        hand.add(c2);
                        hand.add(c3);
                        hand.add(c4);
                        hands.add(hand);
                    }
                }
            }
        }
        return hands;
    }

    static HashMap<Card.Rank, Integer> rankValues() {
        HashMap<Card.Rank, Integer> rankValues = new HashMap<>();
        int value = 0;
        for (Card.Rank rank : Card.Rank.values()){
            rankValues.put(rank, value);
            value ++;
        }
        return rankValues;
    }

    static boolean isStraightFlush(HashSet<Card> hand){
        if (isFlush(hand) && isStraight(hand)){
            return true;
        }
        return false;
    }

    static boolean isRoyalFlush(HashSet<Card> hand){
        if (isFlush(hand) && isStraight(hand)){
            ArrayList<Integer> royalFlush =
                    hand.stream()
                    .map(card -> {
                        return card.rank.ordinal();
                    })
                    .collect(Collectors.toCollection(ArrayList<Integer>::new));
            Integer count = 0;
        for (Integer rank: royalFlush) {
            count += rank;
        }
            if (count == 33){
                return true;
            }
        }
        return false;
    }

    static boolean isTwoPair(HashSet<Card> hand){
        ArrayList<Integer> ranks =
                hand.stream()
                .map(card -> {
                    return card.rank.ordinal();
                })
                .collect(Collectors.toCollection(ArrayList<Integer>::new));
        if (Collections.frequency(ranks, ranks.get(0)) == 2) {
            if ((ranks.get(0) != ranks.get(1)) && (Collections.frequency(ranks, ranks.get(1)) == 2)){
                return true;
            }else if ((ranks.get(0) != ranks.get(2)) && (Collections.frequency(ranks, ranks.get(2)) == 2)){
                return true;
            }
        }
        return false;
    }

    static boolean isStraight(HashSet<Card> hand) {
        HashSet<Integer> rankSet =
                hand.stream()
                .map(card -> {
                        return card.rank.ordinal();
                })
                .collect(Collectors.toCollection(HashSet<Integer>::new));
        ArrayList<Integer> ranks = new ArrayList<>(rankSet);
        Collections.sort(ranks);
        if (ranks.size()==4){
            if ((ranks.get(0) != 0) && (ranks.get(3) - ranks.get(0) == 3)){
                return true;
            }else if ((ranks.get(0) == 0) && ((ranks.get(3) - ranks.get(0) == 3))){
                return true;
            }else if (ranks.get(0) == 0) {
                Integer count = 0;
                for (Integer rank : ranks) {
                    count += rank;
                }
                if (count == 33){
                    return true;
                }
            }
        }
        return false;
    }

    static boolean isThreeOfAKind(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks =
                hand.stream()
                .map(card -> {
                    return card.rank;
                })
                .collect(Collectors.toCollection(HashSet<Card.Rank>::new));
        if (ranks.size() == 2 && !isTwoPair(hand)){
            return true;
        }
        return false;
    }

    static boolean isFourOfAKind(HashSet<Card> hand) {
        HashSet<Card.Rank> ranks =
                hand.stream()
                .map(card -> {
                    return card.rank;
                })
                .collect(Collectors.toCollection(HashSet<Card.Rank>::new));
        if (ranks.size() == 1){
            return true;
        }
        return false;
    }

    static boolean isFlush(HashSet<Card> hand) {
        HashSet<Card.Suit> suits =
                hand.stream()
                .map(card -> {
                        return card.suit;
                })
                .collect(Collectors.toCollection(HashSet<Card.Suit>::new));
        return suits.size() == 1;
    }

    public static void main(String[] args) {
//        long beginTime = System.currentTimeMillis();
        HashSet<Card> deck = createDeck();
//        Card c1 = new Card(Card.Suit.SPADES, Card.Rank.ACE); //Must override equals because Java does not recognize equality of the two cards
//        Card c2 = new Card(Card.Suit.SPADES, Card.Rank.ACE);
//        HashSet<Card> cards = new HashSet<>();
//        cards.add(c1);
//        cards.add(c2);
        HashSet<HashSet<Card>> hands = createHands(deck);
//        hands = hands.stream()
//                .filter(Main::isFlush)
//                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
//        long endTime = System.currentTimeMillis();
        hands = hands.stream()
                .filter(Main::isFlush)
                .collect(Collectors.toCollection(HashSet<HashSet<Card>>::new));
        System.out.println(hands.size());

    }
}
