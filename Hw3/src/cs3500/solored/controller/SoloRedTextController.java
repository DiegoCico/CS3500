package cs3500.solored.controller;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The controller for the Solo Red game, handling input and output for the game.
 */
public class SoloRedTextController implements RedGameController {

  private final Readable rd;
  private final Appendable ap;
  private final SoloRedGameTextView view;

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
    this.rd = rd;
    this.ap = ap;
    this.view = new SoloRedGameTextView(new SoloRedGameModel());
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize) {
    if (model == null || deck == null) {
      throw new IllegalArgumentException("Model or deck cannot be null.");
    }

    Scanner scanner = new Scanner(this.rd);
    String userInput = scanner.nextLine();

    String[] commands = userInput.split("\\s+");

    try {
      model.startGame(deck, shuffle, numPalettes, handSize);

      int i = 0;
      while (!model.isGameOver() && i < commands.length) {
        this.view.render();
        transmit("Number of cards in deck: " + model.numOfCardsInDeck());

        String command = commands[i];
        if (command.equalsIgnoreCase("q")) {
          transmit("Game quit!");
          transmit("State of game when quit:");
          this.view.render();
          transmit("Remaining cards in deck: " + model.numOfCardsInDeck());
          return;
        }

        processMove(command, model);
        i++;
      }

      this.view.render();
      transmit("Number of cards in deck: " + model.numOfCardsInDeck());
      transmit(model.isGameWon() ? "Game won." : "Game lost.");

    } catch (IOException e) {
      throw new IllegalStateException("Error transmitting output.", e);
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

  private <C extends Card> void processMove(String userInput, RedGameModel<C> model) throws IOException {
    String[] inputs = userInput.split("\\s+");

    if (inputs.length == 3 && inputs[0].equalsIgnoreCase("palette")) {
      try {
        int paletteIndex = Integer.parseInt(inputs[1]) - 1;
        int cardIndex = Integer.parseInt(inputs[2]) - 1;
        model.playToPalette(paletteIndex, cardIndex);
      } catch (IllegalArgumentException e) {
        transmit("Invalid move. Try again.");
      }
    } else if (inputs.length == 2 && inputs[0].equalsIgnoreCase("canvas")) {
      try {
        int cardIndex = Integer.parseInt(inputs[1]) - 1;
        model.playToCanvas(cardIndex);
      } catch (IllegalArgumentException e) {
        transmit("Invalid move. Try again.");
      }
    } else {
      transmit("Invalid command. Try again.");
    }
  }
}
