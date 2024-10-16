package cs3500.solored.controller;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class handles the controller for the Solo Red card game.
 * It deals with receiving input, processing it, and outputting the result.
 */
public class SoloRedTextController implements RedGameController {

  private final Appendable ap;
  private SoloRedGameTextView view;
  private final Scanner scan;
  private boolean gameOver;

  /**
   * Creates a controller for Solo Red, with given input and output.
   *
   * @param rd the readable source for user input
   * @param ap the appendable destination for displaying the game output
   * @throws IllegalArgumentException if rd or ap are null
   */
  public SoloRedTextController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable, Appendable cannot be null.");
    }
    scan = new Scanner(rd);
    this.ap = ap;
    this.gameOver = false;
  }

  /**
   * Starts and plays the Solo Red game based on the provided model and deck.
   *
   * @param model the game model to control
   * @param deck the deck of cards to play the game with
   * @param shuffle if the deck should be shuffled
   * @param numPalettes the number of palettes for the game
   * @param handSize the size of each player's hand
   * @throws IllegalArgumentException if the model or deck is null
   */
  @Override
  public <C extends Card> void playGame(RedGameModel<C> model,
                                        List<C> deck, boolean shuffle,
                                        int numPalettes, int handSize) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model or deck cannot be null.");
    }
    if (numPalettes < 1 || handSize < 1) {
      throw new IllegalArgumentException("Number of palettes and hand size" +
              " must be greater than 0.");
    }

    boolean paletteWork = false;

    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
      this.view = new SoloRedGameTextView(model, ap);
      this.view.render();
      transmit("\nNumber of cards in deck: " + model.numOfCardsInDeck());

      while (!model.isGameOver() && scan.hasNext()) {
        String userInput = scan.next();

        if (!userInput.equalsIgnoreCase("canvas") && paletteWork) {
          model.drawForHand();
        } else {
          paletteWork = false;
        }

        switch (userInput.toLowerCase()) {
          case "q":
            gameQuit(model);
            return;

          case "canvas":
            List<Integer> canvasChoice = validNumber(0, model);
            if (!canvasChoice.isEmpty()) {
              updateCanvas(canvasChoice.get(0) - 1, model);
            }
            break;

          case "palette":
            List<Integer> paletteChoice = validNumber(1, model);
            if (paletteChoice.size() >= 2) {
              updatePallete(paletteChoice.get(0) - 1, paletteChoice.get(1) - 1, model);
              paletteWork = true;
            }
            break;

          default:
            if (userInput.isEmpty()) {
              transmit("Invalid command. Try again.");
            } else {
              transmit("Invalid command. Try again. " + userInput);
            }
            break;
        }

        if (!checkGameStatus(model) || gameOver) {
          return;
        }

        this.view.render();
        transmit("\nNumber of cards in deck: " + model.numOfCardsInDeck());
      }

      throw new IllegalStateException("Game ended with no ending");

    } catch (IOException e) {
      throw new IllegalStateException("Error transmitting output.", e);
    } catch (IllegalStateException | IllegalArgumentException e) {
      throw new IllegalStateException("Error from starGame.", e);
    }
  }


  /**
   * Validates the user's input as a number and
   * processes it for either canvas or palette mode.
   *
   * @param mode 0 for canvas move, 1 for palette move
   * @param model the game model being used
   * @return a list of valid numbers based on the user input
   */
  private List<Integer> validNumber(int mode, RedGameModel<?> model) throws IOException {
    List<Integer> numbers = new ArrayList<>();

    while (scan.hasNext() && !gameOver) {
      String input = scan.next();

      if (input.equalsIgnoreCase("q")) {
        gameQuit(model);
        return List.of();
      }

      try {
        int num = Integer.parseInt(input);

        if (num > 0) {
          numbers.add(num);

          if (mode == 0) {
            return List.of(num);
          }

          if (mode != 0 && numbers.size() == 2) {
            return numbers;
          }
        }
      } catch (NumberFormatException ignored) {

      }
    }

    return Collections.emptyList();
  }


  /**
   * Checks if the game is over, transmitting the win/loss to the output.
   *
   * @param model the game model to check
   * @return true if the game continues, false if it's over
   * @throws IOException if transmitting the game state fails
   */
  private boolean checkGameStatus(RedGameModel<?> model) throws IOException {
    if (model.isGameOver()) {
      String resultMessage = model.isGameWon() ? "Game won." : "Game lost.";
      transmit(resultMessage);
      this.view.render();
      transmit("\nNumber of cards in deck: " + model.numOfCardsInDeck());
      return false;
    }
    return true;
  }

  /**
   * Quits the game, showing the current game state and ending the session.
   *
   * @param model the game model being used
   * @throws IOException if transmitting the game state fails
   */
  private void gameQuit(RedGameModel<?> model) throws IOException {
    transmit("Game quit!");
    transmit("State of game when quit:");
    this.view.render();
    transmit("\nNumber of cards in deck: " + model.numOfCardsInDeck());
    this.gameOver = true;
  }

  /**
   * Updates the canvas with the user's move.
   *
   * @param userInput the canvas index to update
   * @param model the game model being used
   */
  private void updateCanvas(Integer userInput, RedGameModel<?> model) {
    try {
      model.playToCanvas(userInput);
    } catch (IllegalArgumentException | NoSuchElementException | IndexOutOfBoundsException e) {
      transmit("Invalid move. Try again. Invalid canvas index.");
    } catch (IllegalStateException e) {
      transmit("Invalid move. Try again. Invalid canvas has been already played.");
    }
  }

  /**
   * Updates the palette with the user's move.
   *
   * @param paletteIdx the palette index to update
   * @param handIdx the hand index to play from
   * @param model the game model being used
   */
  private void updatePallete(Integer paletteIdx, Integer handIdx, RedGameModel<?> model) {
    try {
      model.playToPalette(paletteIdx, handIdx);
    } catch (IllegalArgumentException | NoSuchElementException e) {
      transmit("Invalid move. Try again. Invalid palette index.");
    } catch (IllegalStateException | IndexOutOfBoundsException e) {
      transmit("Invalid move. Try again. Palette is currently winning.");
    }
  }

  /**
   * Sends a message to the output.
   *
   * @param message the message to display
   */
  @Override
  public void transmit(String message) {
    try {
      ap.append(message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to transmit message", e);
    }
  }
}
