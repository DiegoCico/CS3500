package cs3500.tictactoe;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents a console-based controller for a Tic-Tac-Toe game.
 * It handles user input via a {@link Readable} and outputs game state and results
 * via an {@link Appendable}. The controller manages game progression by taking
 * user input, validating moves, and communicating the current game state.
 */
public class TicTacToeConsoleController implements TicTacToeController {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructs a {@code TicTacToeConsoleController} with the given input and output sources.
   *
   * @param in  the {@link Readable} source for user input
   * @param out the {@link Appendable} source for output
   * @throws IllegalArgumentException if the input or output is null
   */
  public TicTacToeConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  /**
   * Plays a Tic-Tac-Toe game using the provided model.
   * Continuously prompts the user for input, updates the game state, and displays the
   * game board until the game is over.
   *
   * @throws IllegalStateException if appending output fails
   */
  @Override
  public void playGame(TicTacToe m) {
    try {
      while (!m.isGameOver()) {
        this.out.append(m.toString() + "\n");
        this.out.append("Enter a move for " + m.getTurn().toString() + "\n");

        int row = scan.nextInt();
        int col = scan.nextInt();
        m.move(row - 1, col - 1);
      }

      transmitGameOverMessage(m);

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private int getNextInt() {
    if (scan.hasNextInt()) {
      return scan.nextInt();
    }
    String input = scan.next();
    if (input.equalsIgnoreCase("q")) {
      throw new QuitException();
    }
    throw new RuntimeException("Got garbage");
  }

  /**
   * Exception class representing the action
   * of quitting the game by the user.
   */
  class QuitException extends RuntimeException {

  }

  private void transmit(String message) throws IOException {
    this.out.append(message + "\n");
  }

  private void transmitGameOverMessage(TicTacToe m) throws IOException {
    transmit(m.toString() + "\n");
    if (m.getWinner() != null) {
      transmit("Game is over! " + m.getWinner().toString() + " wins.");
    } else {
      transmit("Game is over! Tie game.");
    }
  }
}
