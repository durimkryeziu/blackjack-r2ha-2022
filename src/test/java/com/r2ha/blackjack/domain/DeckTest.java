package com.r2ha.blackjack.domain;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class DeckTest {

    @Test
    void fullDeckHas52Cards() {
        Deck deck = new Deck();

        assertThat(deck.size())
                .isEqualTo(52);
    }

    @Test
    void drawCardFromDeckReducesDeckSizeByOne() {
        Deck deck = new Deck();

        deck.draw();

        assertThat(deck.size())
                .isEqualTo(51);
    }

    @Test
    void drawCardFromDeckReturnsValidCard() {
        Deck deck = new Deck();

        Card card = deck.draw();

        assertThat(card)
                .isNotNull();

        assertThat(card.rankValue())
                .isGreaterThan(0);
    }

    @Test
    void drawAllCardsResultsInSetOf52UniqueCards() {
        Deck deck = new Deck();

        Set<Card> drawnCards = new HashSet<>();
        for (int i = 1; i <= 52; i++) {
            drawnCards.add(deck.draw());
        }

        assertThat(drawnCards)
                .hasSize(52);
    }

}