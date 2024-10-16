package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * This interface represents a controller for the Solo Red game.
 */
public interface RedGameController {

  /**
   * Starts and plays the Solo Red game using the provided model and deck.
   *
   * @param <C> the type of cards being used in the game, must extend Card
   * @param model the game model to be controlled
   * @param deck the deck of cards used to play the game
   * @param shuffle a boolean indicating whether to shuffle the deck before starting
   * @param numPalettes the number of palettes to be used in the game
   * @param handSize the size of each player's hand
   * @throws IllegalArgumentException if the model or deck is null
   */
  <C extends Card> void playGame(RedGameModel<C> model, List<C> deck,
                                 boolean shuffle, int numPalettes, int handSize);

  /**
   * Transmits a message to be displayed to the user.
   *
   * @param message the message to be transmitted
   * @throws IllegalStateException if transmission fails
   */
  void transmit(String message);

}
