package com.r2ha.blackjack.adapter.in.console;

import com.r2ha.blackjack.domain.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class GameDisplayTest {

    private final InputStream originalSystemIn = System.in;

    private void provideInput(String input) {
        byte[] inputBytes = input.getBytes();
        ByteArrayInputStream testIn = new ByteArrayInputStream(inputBytes);
        System.setIn(testIn); // reading from System.in will consume our input
    }

    @Test
    void gamePlays() {
        provideInput("\nS\n"); // simulates typing: Enter, S, Enter
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Game.directOutputTo(printStream);
        // Starts the game with an empty String array for the arguments.
        Game.main(new String[0]);

        List<String> cleanedOutput = outputStream.toString()
                                                 .replaceAll("\u001B\\[[\\d;]*[^\\d;]", "\n")
                                                 .lines()
                                                 .map(String::strip)
                                                 .toList();

        assertThat(cleanedOutput)
                .contains("Welcome to",
                          "JitterTed's",
                          "Blackjack game",
                          "Hit [ENTER] to start...",
                          "Dealer has:",
                          "Player has:",
                          "[H]it or [S]tand?");
    }

    @AfterEach
    void restoreSystemInput() {
        System.setIn(originalSystemIn);
    }
}
