package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class implements the RedGameModel interface for the card game Solo Red.
 * It models the state of the game, including the deck,
 * hand, palettes, and the canvas.
 * It allows for game actions like starting the game, playing cards,
 * and checking the current game state.
 */
public class SoloRedGameModel implements RedGameModel<CardModel> {
  private List<CardModel> deck;
  private List<CardModel> hand;
  private List<List<CardModel>> palettes;
  private boolean gameOver;
  private CardModel canvas;
  private int handsize;
  private boolean gameStarted;
  private boolean canvasPlayedThisTurn;
  private Random rand;

  /**
   * Default constructor for the SoloRedGameModel.
   * Initializes the model with a new Random object for randomness.
   * This constructor prepares the game model,
   * but the game does not start until startGame() is called.
   */
  public SoloRedGameModel() {
    this(new Random());
  }

  /**
   * Constructor for the SoloRedGameModel that allows for a custom Random object.
   * This constructor can be used to inject
   * a specific Random instance for testing or custom behavior.
   *
   *    @param rand the Random object used for shuffling and randomness
   *    @throws IllegalArgumentException if rand is null
   */
  public SoloRedGameModel(Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException("Random object cannot be null.");
    }
    this.rand = rand;
    this.deck = new ArrayList<>();
    this.hand = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.handsize = 0;
    this.canvas = null;
    this.gameStarted = false;
    this.canvasPlayedThisTurn = false;
    this.gameOver = false;
  }

  /**
   * Play the given card from the hand to the losing palette chosen.
   * The card is removed from the hand and placed at the far right
   * end of the palette.
   *
   * @param paletteIdx    a 0-index number representing which palette to play to
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if paletteIdx < 2 or more than the number of palettes
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *                                  or greater/equal to the number of cards in hand
   * @throws IllegalStateException    if the palette referred to by paletteIdx is winning
   */
  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    if (!gameStarted || isGameOver()) {
      throw new IllegalStateException("Game is over or game has not started.");
    }
    if (paletteIdx < 0 || paletteIdx >= palettes.size()) {
      throw new IllegalArgumentException("Invalid move. Try again. palette index: " + paletteIdx);
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Invalid move. Try again. " +
              "Invalid card index in hand: " + cardIdxInHand);
    }
    int winningIndex = winningPaletteIndex();
    if (winningIndex == paletteIdx) {
      throw new IllegalStateException("Invalid move. Try again. Cannot play to a winning palette.");
    }

    CardModel cardToPlay = hand.remove(cardIdxInHand);
    palettes.get(paletteIdx).add(cardToPlay);

    if (winningPaletteIndex() != paletteIdx) {
      gameOver = true;
    }
  }


  /**
   * Play the given card from the hand to the canvas.
   * This changes the rules of the game for all palettes.
   * The method can only be called once per turn.
   *
   * @param cardIdxInHand a 0-index number representing the card to play from the hand
   * @throws IllegalStateException    if the game has not started or the game is over
   * @throws IllegalArgumentException if paletteIdx < 0 or more than the number of palettes
   * @throws IllegalArgumentException if cardIdxInHand < 0
   *                                  or greater/equal to the number of cards in hand
   * @throws IllegalStateException    if this method was already called once in a given turn
   */
  @Override
  public void playToCanvas(int cardIdxInHand) {
    if (!gameStarted || isGameOver()) {
      throw new IllegalStateException("Game is over or game has not started.");
    }
    if (canvasPlayedThisTurn) {
      throw new IllegalStateException("Invalid move. Try again." +
              " Canvas has already been played this turn.");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("Invalid move. Try again." +
              " Invalid card index in hand: " + cardIdxInHand);
    }
    if (hand.size() <= 1) {
      throw new IllegalStateException("Invalid move. Try again. Can't play with only 1 card.");
    }

    CardModel cardToPlay = hand.remove(cardIdxInHand);
    canvas = cardToPlay;
    canvasPlayedThisTurn = true;
  }


  /**
   * Draws cards from the deck until the hand is full (has 7 cards)
   * OR until the deck is empty, whichever occurs first. Newly drawn cards
   * are added to the end of the hand (far-right conventionally).
   * SIDE-EFFECT: Allows the player to play to the canvas again.
   *
   * @throws IllegalStateException if the game has not started or the game is over
   */
  @Override
  public void drawForHand() {
    if (!gameStarted || isGameOver()) {
      throw new IllegalStateException("Game has not started or the game is over.");
    }

    while (hand.size() < handsize && !deck.isEmpty()) {
      hand.add(deck.remove(0));
    }

    canvasPlayedThisTurn = false;
  }

  /**
   * Starts the game with the given options. The deck given is used
   * to set up the palettes and hand. Modifying the deck given to this method
   * will not modify the game state in any way.
   *
   * @param deck        the cards used to set up and play the game
   * @param shuffle     whether the deck should be shuffled prior to setting up the game
   * @param numPalettes number of palettes in the game
   * @param handSize    the maximum number of cards allowed in the hand
   * @throws IllegalStateException    if the game has started or the game is over
   * @throws IllegalArgumentException if numPalettes or handSize <= 0
   * @throws IllegalArgumentException if deck's size is not large enough to setup the game
   * @throws IllegalArgumentException if deck has non-unique cards or null cards
   */
  @Override
  public void startGame(List<CardModel> deck, boolean shuffle, int numPalettes, int handSize) {
    if (gameStarted) {
      throw new IllegalStateException("Game has already been started or has already ended.");
    }
    if (numPalettes < 2) {
      throw new IllegalArgumentException("Number of palettes must be at least 2.");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("Hand size must be greater than 0.");
    }
    if (deck == null || deck.size() < numPalettes + handSize) {
      throw new IllegalArgumentException("Not enough cards to start the game.");
    }

    for (int i = 0; i < deck.size(); i++) {
      for (int j = i + 1; j < deck.size(); j++) {
        if (deck.get(i).equals(deck.get(j))) {
          throw new IllegalArgumentException("Deck contains duplicate cards.");
        }
      }
    }

    this.deck = new ArrayList<>(deck);
    if (shuffle) {
      Collections.shuffle(this.deck, rand);
    }

    palettes = new ArrayList<>();
    for (int i = 0; i < numPalettes; i++) {
      List<CardModel> palette = new ArrayList<>();
      palette.add(this.deck.remove(0));
      this.palettes.add(palette);
    }

    this.handsize = handSize;
    for (int i = 0; i < handSize; i++) {
      hand.add(this.deck.remove(0));
    }

    canvas = new CardModel("R", 0);
    gameStarted = true;
  }

  /**
   * Returns the number of cards remaining in the deck used in the game.
   *
   * @return the number of cards in the deck
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numOfCardsInDeck() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return this.deck.size();
  }

  /**
   * Returns the number of palettes in the running game.
   *
   * @return the number of palettes in the game
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int numPalettes() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return palettes.size();
  }

  /**
   * Returns the index of the winning palette in the game.
   *
   * @return the 0-based index of the winning palette
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public int winningPaletteIndex() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return WinningPallet.checkWinningPallet(palettes, canvas);
  }

  /**
   * Returns if the game is over as specified by the implementation.
   *
   * @return true if the game has ended and false otherwise
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    if (gameOver) {
      return true;
    }
    return hand.isEmpty() && deck.isEmpty();
  }

  /**
   * Returns if the game is won by the player as specified by the implementation.
   *
   * @return true if the game has been won or false if the game has not
   * @throws IllegalStateException if the game has not started or the game is not over
   */
  @Override
  public boolean isGameWon() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }

    return hand.isEmpty() && deck.isEmpty();
  }

  /**
   * Returns a copy of the hand in the game. This means modifying the returned list
   * or the cards in the list has no effect on the game.
   *
   * <p>Returns a new list containing the cards in the player's hand in the same order
   * as in the current state of the game.
   *
   * @return a new list containing the cards in the player's hand in the same order
   *     as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   */
  @Override
  public List<CardModel> getHand() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    return List.copyOf(hand);
  }

  /**
   * Returns a copy of the palette.
   *
   * <p>Returns a new list containing the cards in the specified palette in the same order
   * as in the current state of the game.
   *
   * @param paletteNum 0-based index of a particular palette
   * @return a new list containing the cards in the specified palette in the same order
   *     as in the current state of the game.
   * @throws IllegalStateException if the game has not started
   * @throws IllegalArgumentException if paletteIdx is less than 0 or
   *     greater than the number of palettes
   */
  @Override
  public List<CardModel> getPalette(int paletteNum) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not been started");
    }
    if (paletteNum < 0 || paletteNum >= palettes.size()) {
      throw new IllegalArgumentException("Invalid move. Try again. " +
              "palette number: " + paletteNum);
    }
    return List.copyOf(palettes.get(paletteNum));
  }

  /**
   * Return the top card of the canvas.
   * Modifying this card has no effect on the game.
   *
   * @return the top card of the canvas
   * @throws IllegalStateException if the game has not started or the game is over
   */
  @Override
  public CardModel getCanvas() {
    if (!gameStarted || isGameOver()) {
      throw new IllegalStateException("Game has not been started or Game is already ended.");
    }
    return new CardModel(canvas.getColor(), canvas.getNumber());
  }

  /**
   * Get a NEW list of all cards that can be used to play the game.
   * Editing this list should have no effect on the game itself.
   * Repeated calls to this method should produce a list of cards in the same order.
   * Modifying the cards in this list should have no effect on any returned list
   * or the game itself.
   *
   *    @return a new list of all possible cards that can be used for the game
   */
  @Override
  public List<CardModel> getAllCards() {
    List<CardModel> allCards = new ArrayList<>();
    String[] colors = {"R", "O", "B", "I", "V"};
    for (String color : colors) {
      for (int num = 1; num <= 7; num++) {
        allCards.add(new CardModel(color, num));
      }
    }
    return allCards;
  }
}
