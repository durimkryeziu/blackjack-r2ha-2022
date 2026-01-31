package com.r2ha.blackjack.adapter.in.console;

import com.r2ha.blackjack.domain.Card;
import com.r2ha.blackjack.domain.Rank;
import com.r2ha.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CardDisplayTest {

    @Test
    void displayTenAsString() {
        Card card = new Card(Suit.HEARTS, Rank.TEN);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[31m┌─────────┐[1B[11D│10       │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│       10│[1B[11D└─────────┘");
    }

    @Test
    void displayNonTenAsString() {
        Card card = new Card(Suit.DIAMONDS, Rank.SEVEN);

        assertThat(ConsoleCard.display(card))
                .isEqualTo("[31m┌─────────┐[1B[11D│7        │[1B[11D│         │[1B[11D│    ♦    │[1B[11D│         │[1B[11D│        7│[1B[11D└─────────┘");
    }
}
