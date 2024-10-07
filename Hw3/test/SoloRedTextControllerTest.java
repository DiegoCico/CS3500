import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw02.CardModel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.util.List;

public class SoloRedTextControllerTest {

  private Appendable output;
  private Readable input;
  private RedGameModel<CardModel> model;
  private SoloRedTextController controller;
  private List<CardModel> deck;

  @Before
  public void setUp() {
    output = new StringBuilder();
    input = new StringReader(""); // default no input
    model = new SoloRedGameModel();
    deck = model.getAllCards(); // gets a valid deck
  }

  // Test when readable is null, expect IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullReadable() {
    new SoloRedTextController(null, output);
  }

  // Test when appendable is null, expect IllegalArgumentException
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullAppendable() {
    new SoloRedTextController(input, null);
  }

  // Test starting the game with a null model
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameWithNullModel() {
    controller = new SoloRedTextController(input, output);
    controller.playGame(null, deck, false, 2, 7);
  }

  // Test starting the game with a null deck
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameWithNullDeck() {
    controller = new SoloRedTextController(input, output);
    controller.playGame(model, null, false, 2, 7);
  }

  // Test exception when game is played with insufficient palettes
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameWithInvalidPalettes() {
    controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, false, 1, 7); // less than 2 palettes
  }

  // Test exception when game is played with invalid hand size
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameWithInvalidHandSize() {
    controller = new SoloRedTextController(input, output);
    controller.playGame(model, deck, false, 2, 0); // hand size less than 1
  }

  // Test quitting game at start
  @Test
  public void testGameQuitAtStart() {
    input = new StringReader("q");
    controller = new SoloRedTextController(input, output);

    controller.playGame(model, deck, false, 2, 7);

    String expectedOutput = "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n";

    assertEquals(expectedOutput, output.toString());
  }

  // Test invalid canvas input
  @Test
  public void testInvalidCanvasInput() {
    input = new StringReader("canvas 999 q");
    controller = new SoloRedTextController(input, output);

    controller.playGame(model, deck, false, 2, 7);

    String expectedOutput = "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n"
            + "Invalid move. Try again. Invalid canvas index.\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n";

    assertEquals(expectedOutput, output.toString());
  }

  // Test invalid palette input
  @Test
  public void testInvalidPaletteInput() {
    input = new StringReader("palette adwad 2 dwada 2 palette 1 1 q");
    controller = new SoloRedTextController(input, output);

    controller.playGame(model, deck, false, 2, 7);

    String expectedOutput = "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n"
            + "Invalid move. Try again. Invalid palette index.\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n";

    assertEquals(expectedOutput, output.toString());
  }

  // Test starting game with shuffle enabled
  @Test
  public void testGameStartWithShuffle() {
    input = new StringReader("q");
    controller = new SoloRedTextController(input, output);

    controller.playGame(model, deck, true, 2, 7); // shuffle enabled

    String expectedOutput = "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: " // shuffled hand, so we don't check exact cards
            + "Number of cards in deck: " + (deck.size() - 9) + "\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: "
            + "Number of cards in deck: " + (deck.size() - 9) + "\n";

    assertEquals(expectedOutput, output.toString().substring(0, 100)); // only checking initial part due to shuffle
  }

  // Test playing a valid move to the canvas
  @Test
  public void testValidMoveToCanvas() {
    input = new StringReader("canvas 1 q");
    controller = new SoloRedTextController(input, output);

    controller.playGame(model, deck, false, 2, 7); // no shuffle

    String expectedOutput = "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n"
            + "Canvas: R2\n" // after playing to canvas
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R2\n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n";

    assertEquals(expectedOutput, output.toString());
  }

  // Test for invalid card selection from hand
  @Test
  public void testInvalidHandCardSelection() {
    input = new StringReader("canvas 999 q"); // invalid hand card
    controller = new SoloRedTextController(input, output);

    controller.playGame(model, deck, false, 2, 7);

    String expectedOutput = "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n"
            + "Invalid move. Try again. Invalid canvas index.\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: \n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: " + (deck.size() - 9) + "\n";

    assertEquals(expectedOutput, output.toString());
  }

  // Test for game over scenario
  @Test
  public void testGameOver() {
    // Modify the game to make it almost over (deck and hand empty)
    input = new StringReader("canvas 1 q");
    controller = new SoloRedTextController(input, output);

    controller.playGame(model, deck.subList(0, 9), false, 2, 7); // short deck to force game over

    String expectedOutput = "Canvas: R\n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R2 R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: 0\n"
            + "Game quit!\n"
            + "State of game when quit:\n"
            + "Canvas: R2\n"
            + "P1: R1\n"
            + "P2: O1\n"
            + "Hand: R3 R4 R5 R6 R7 R8\n"
            + "Number of cards in deck: 0\n"; // after the game ends due to empty deck

    assertEquals(expectedOutput, output.toString());
  }
}
