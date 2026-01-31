package com.r2ha.blackjack.domain;

import com.r2ha.blackjack.adapter.in.console.ConsoleCard;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fusesource.jansi.Ansi.ansi;

class CardTest {

    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private static final Rank DUMMY_RANK = Rank.TEN;

    @Test
    void withNumberCardHasNumericValueOfTheNumber() {
        Card card = new Card(DUMMY_SUIT, Rank.SEVEN);

        assertThat(card.rankValue())
                .isEqualTo(7);
    }

    @Test
    void withValueOfQueenHasNumericValueOf10() {
        Card card = new Card(DUMMY_SUIT, Rank.QUEEN);

        assertThat(card.rankValue())
                .isEqualTo(10);
    }

    @Test
    void withAceHasNumericValueOf1() {
        Card card = new Card(DUMMY_SUIT, Rank.ACE);

        assertThat(card.rankValue())
                .isEqualTo(1);
    }

    @Test
    void suitOfHeartsOrDiamondsIsDisplayedInRed() {
        // given a card with Hearts or Diamonds
        Card heartsCard = new Card(Suit.HEARTS, DUMMY_RANK);
        Card diamondsCard = new Card(Suit.DIAMONDS, DUMMY_RANK);

        // when we ask for its display representation
        String ansiRedString = ansi().fgRed().toString();

        // then we expect a red color ansi sequence
        assertThat(ConsoleCard.display(heartsCard))
                .contains(ansiRedString);
        assertThat(ConsoleCard.display(diamondsCard))
                .contains(ansiRedString);
    }

}