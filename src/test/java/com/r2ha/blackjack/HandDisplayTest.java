package com.r2ha.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HandDisplayTest {

    @Test
    void displayFaceUpCardReturnsAsCorrectString() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.JACK));
        Hand hand = new Hand(cards);

        assertThat(ConsoleHand.displayFaceUpCard(hand))
                .isEqualTo("[31m┌─────────┐[1B[11D│A        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        A│[1B[11D└─────────┘");
    }

    @Test
    void cardsAsStringTranslatesCardsToString() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.JACK));
        Hand hand = new Hand(cards);

        assertThat(ConsoleHand.cardsAsString(hand))
                .isEqualTo("[31m┌─────────┐[1B[11D│A        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        A│[1B[11D└─────────┘[6A[1C[30m┌─────────┐[1B[11D│J        │[1B[11D│         │[1B[11D│    ♣    │[1B[11D│         │[1B[11D│        J│[1B[11D└─────────┘");
    }
}
