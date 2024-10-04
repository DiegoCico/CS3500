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
 * The controller for the Solo Red game, handling input and output for the game.
 */
public class SoloRedTextController implements RedGameController {

  private final Appendable ap;
  private SoloRedGameTextView view;
  private final Scanner scan;
  private boolean gameOver;

  /**
   * Constructs a controller that operates on the given Readable and Appendable.
   * @param rd the readable to provide input
   * @param ap the appendable to display output
   * @throws IllegalArgumentException if rd or ap are null
   */
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable, Appendable cannot be null.");
    }
    scan = new Scanner(rd);
    this.ap = ap;
    this.gameOver = false;
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model or deck cannot be null.");
    }

    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
      this.view = new SoloRedGameTextView(model);

      while (!model.isGameOver() && scan.hasNext()) {
        String userInput = scan.next();

        if (userInput.equalsIgnoreCase("q")) {
          gameQuit(model);
        } else if (userInput.equalsIgnoreCase("canvas")) {
          try {
            List<Integer> choice = validNumber(0, model);
            updateCanvas(choice.get(0), model);
          } catch (IndexOutOfBoundsException ignore) {

          }
        } else if (userInput.equalsIgnoreCase("palette")) {
          try {
            List<Integer> choice = validNumber(1, model);
            updatePallete(choice.get(0), choice.get(1), model);
          } catch (IndexOutOfBoundsException ignore) {

          }
        } else {
          transmit("Invalid command. Try again. " + userInput);
        }

        if (!checkGameStatus(model) || gameOver) {
          return;
        } else {
          transmit(this.view.toString());
          transmit("Number of cards in deck: " + model.numOfCardsInDeck());
        }
      }

    } catch (IOException e) {
      throw new IllegalStateException("Error transmitting output", e);
    }
  }

  private List<Integer> validNumber(int mode, RedGameModel<?> model) {
    if (mode == 0) {
      while (scan.hasNext()) {
        String input = scan.next();
        if (input.equalsIgnoreCase("q")) {
          try {
            gameQuit(model);
            return List.of();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        try {
          int num = Integer.parseInt(input);

          if (num > 0 && num <= model.getHand().size()) {
            return List.of(num);
          }
        } catch (NumberFormatException ignored) {

        }
      }
    } else {
      List<Integer> lastTwoInts = new ArrayList<>();

      while (scan.hasNext()) {
        String input = scan.next();

        if (input.equalsIgnoreCase("q")) {
          try {
            gameQuit(model);
            return List.of();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }

        try {
          int num = Integer.parseInt(input);

          if (num > 0 && num <= model.getHand().size()) {
            lastTwoInts.add(num);

            if (lastTwoInts.size() == 2) {
              return lastTwoInts;
            }
          }
        } catch (NumberFormatException ignored) {

        }
      }

    }

    return Collections.emptyList();
  }



  private boolean checkGameStatus(RedGameModel<?> model) throws IOException {
    if (model.isGameOver()) {
      if (model.isGameWon()) {
        transmit("Game won.");
        transmit(this.view.toString());
        transmit("Number of cards in deck: " + model.numOfCardsInDeck());
        return false;
      } else {
        transmit("Game lost.");
        transmit(this.view.toString());
        transmit("Number of cards in deck: " + model.numOfCardsInDeck());
        return false;
      }
    }
    return true;
  }

  private void gameQuit(RedGameModel<?> model) throws IOException {
    transmit("Game quit!");
    transmit("State of game when quit:");
    transmit(this.view.toString());
    transmit("Number of cards in deck: " + model.numOfCardsInDeck());
    this.gameOver = true;
  }

  private void updateCanvas(Integer userInput, RedGameModel<?> model) {
    try {
      model.playToCanvas(userInput);
      model.drawForHand();
    } catch (IllegalArgumentException | NoSuchElementException | IndexOutOfBoundsException e) {
      transmit("Invalid move. Try again. Invalid canvas index.");
    } catch (IllegalStateException e) {
      transmit("Invalid move. Try again. Invalid canvas has been already played.");
    }
  }

  private void updatePallete(Integer palleteIdx, Integer handIdx, RedGameModel<?> model) {
    try {
      model.playToPalette(palleteIdx, handIdx);
      model.drawForHand();
    } catch (IllegalArgumentException | NoSuchElementException  e) {
      transmit("Invalid move. Try again. Invalid palette index.");
    } catch (IllegalStateException | IndexOutOfBoundsException e) {
      transmit("Invalid move. Try again. Palette is currently winning.");
    }
  }


  @Override
  public void transmit(String message) {
    try {
      ap.append(message).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Failed to transmit message", e);
    }
  }


}
