package com.r2ha.blackjack.domain;

import com.r2ha.blackjack.adapter.in.console.ConsoleHand;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Game {

    private static PrintStream consoleOut = System.out;
    private static Scanner scanner;

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        displayWelcomeScreen();
        waitForEnterFromUser();

        playGame();

        resetScreen();
    }

    private static void resetScreen() {
        consoleOut.println(ansi().reset());
    }

    private static void playGame() {
        Game game = new Game();
        game.initialDeal();
        game.play();
    }

    private static void waitForEnterFromUser() {
        consoleOut.println(ansi()
                                   .cursor(3, 1)
                                   .fgBrightBlack().a("Hit [ENTER] to start..."));
        scanner.nextLine();
    }

    private static void displayWelcomeScreen() {
        AnsiConsole.systemInstall();
        consoleOut.println(ansi()
                                   .bgBright(Ansi.Color.WHITE)
                                   .eraseScreen()
                                   .cursor(1, 1)
                                   .fgGreen().a("Welcome to")
                                   .fgRed().a(" JitterTed's")
                                   .fgBlack().a(" Blackjack game"));
    }

    public static void directOutputTo(PrintStream outputStream) {
        consoleOut = outputStream;
    }

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    public void play() {
        playerTurn();

        dealerTurn();

        displayFinalGameState();

        determineOutcome();
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    private void determineOutcome() {
        if (playerHand.isBusted()) {
            consoleOut.println("You Busted, so you lose.  💸");
        } else if (dealerHand.isBusted()) {
            consoleOut.println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (playerHand.beats(dealerHand)) {
            consoleOut.println("You beat the Dealer! 💵");
        } else if (playerHand.pushes(dealerHand)) {
            consoleOut.println("Push: Nobody wins, we'll call it even.");
        } else {
            consoleOut.println("You lost to the Dealer. 💸");
        }
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    private void playerTurn() {
        // get Player's decision: hit until they stand, then they're done (or they go bust)

        while (!playerHand.isBusted()) {
            displayGameState();
            String playerChoice = inputFromPlayer().toLowerCase();
            if (playerChoice.startsWith("s")) {
                break;
            }
            if (playerChoice.startsWith("h")) {
                playerHand.drawFrom(deck);
                if (playerHand.isBusted()) {
                    return;
                }
            } else {
                consoleOut.println("You need to [H]it or [S]tand");
            }
        }
    }

    private String inputFromPlayer() {
        consoleOut.println("[H]it or [S]tand?");
        return scanner.nextLine();
    }

    private void displayGameState() {
        consoleOut.print(ansi().eraseScreen().cursor(1, 1));
        consoleOut.println("Dealer has: ");
        consoleOut.println(ConsoleHand.displayFaceUpCard(dealerHand));

        // the second card is the hole card, which is hidden, or "face down"
        displayBackOfCard();

        consoleOut.println();
        consoleOut.println("Player has: ");
        consoleOut.println(ConsoleHand.cardsAsString(playerHand));
        consoleOut.println(" (" + playerHand.value() + ")");
    }

    private void displayBackOfCard() {
        consoleOut.print(
                ansi()
                        .cursorUp(7)
                        .cursorRight(12)
                        .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("│░ J I T ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E R ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T E D ░│").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("└─────────┘"));
    }

    private void displayFinalGameState() {
        consoleOut.print(ansi().eraseScreen().cursor(1, 1));
        consoleOut.println("Dealer has: ");
        consoleOut.println(ConsoleHand.cardsAsString(dealerHand));
        consoleOut.println(" (" + dealerHand.value() + ")");

        consoleOut.println();
        consoleOut.println("Player has: ");
        consoleOut.println(ConsoleHand.cardsAsString(playerHand));
        consoleOut.println(" (" + playerHand.value() + ")");
    }

}
