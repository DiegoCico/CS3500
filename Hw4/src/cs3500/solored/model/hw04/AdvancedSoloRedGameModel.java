package cs3500.solored.model.hw04;

import java.util.List;
import java.util.Random;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * An advanced version of the Solo Red game, with additional rules.
 * When a player plays a card to their palette, they automatically
 * draw another card to their hand.
 */
public class AdvancedSoloRedGameModel extends SoloRedGameModel {

  /**
   * Default constructor that initializes the game without a specific
   * random seed.
   */
  public AdvancedSoloRedGameModel() {
    super();
  }

  /**
   * Constructor that allows for a seeded random number generator,
   * useful for testing.
   *
   * @param rand the random number generator to be used
   */
  public AdvancedSoloRedGameModel(Random rand) {
    super(rand);
  }

  /**
   * Plays a card from the player's hand to their palette, then draws a
   * new card to their hand.
   *
   * @param paletteIdx the index of the player's palette
   * @param cardIdxInHand the index of the card in the player's hand
   */
  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    super.playToPalette(paletteIdx, cardIdxInHand);
  }

  /**
   * Plays a card from the player's hand to the canvas.
   *
   * @param cardIdxInHand the index of the card in the player's hand
   */
  @Override
  public void playToCanvas(int cardIdxInHand) {
    super.playToCanvas(cardIdxInHand);
  }

  /**
   * Draws a card for the player’s hand.
   */
  @Override
  public void drawForHand() {
    super.drawForHand();
  }

  /**
   * Starts the game with the provided deck, shuffle option, number of
   * palettes, and hand size.
   *
   * @param deck the deck of cards to use
   * @param shuffle whether to shuffle the deck
   * @param numPalettes the number of palettes in the game
   * @param handSize the number of cards in each player's hand
   */
  @Override
  public void startGame(List deck, boolean shuffle, int numPalettes, int handSize) {
    super.startGame(deck, shuffle, numPalettes, handSize);
  }

  /**
   * Returns the number of cards left in the deck.
   *
   * @return the number of cards in the deck
   */
  @Override
  public int numOfCardsInDeck() {
    return super.numOfCardsInDeck();
  }

  /**
   * Returns the number of palettes in the game.
   *
   * @return the number of palettes
   */
  @Override
  public int numPalettes() {
    return super.numPalettes();
  }

  /**
   * Returns the index of the palette that is currently winning the game.
   *
   * @return the index of the winning palette
   */
  @Override
  public int winningPaletteIndex() {
    return super.winningPaletteIndex();
  }

  /**
   * Checks if the game is over.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return super.isGameOver();
  }

  /**
   * Checks if the game has been won by a player.
   *
   * @return true if the game has been won, false otherwise
   */
  @Override
  public boolean isGameWon() {
    return super.isGameWon();
  }

  /**
   * Gets the player's hand of cards.
   *
   * @return the list of cards in the player's hand
   */
  @Override
  public List getHand() {
    return super.getHand();
  }

  /**
   * Gets the cards in a specific palette.
   *
   * @param paletteNum the index of the palette
   * @return the list of cards in the palette
   */
  @Override
  public List getPalette(int paletteNum) {
    return super.getPalette(paletteNum);
  }

  /**
   * Gets the card that is currently on the canvas.
   *
   * @return the card on the canvas
   */
  @Override
  public CardModel getCanvas() {
    return super.getCanvas();
  }

  /**
   * Gets all the cards involved in the game (deck, hands,
   * palettes, and canvas).
   *
   * @return the list of all cards in the game
   */
  @Override
  public List getAllCards() {
    return super.getAllCards();
  }
}
