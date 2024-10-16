package cs3500.solored;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the SoloRedTextController class.
 * This class includes various test cases to
 * verify the behavior of the controller
 * under different inputs and scenarios,
 * ensuring proper game flow and error handling.
 */
public class SoloRedTextControllerTest {

  private RedGameModel<CardModel> model;
  private StringWriter output;
  private SoloRedTextController controller;

  /**
   * Sets up a new model and output stream before each test.
   * This ensures that each test runs in a clean state
   * with a fresh game model.
   */
  @Before
  public void setup() {
    model = new SoloRedGameModel();
    output = new StringWriter();
  }

  /**
   * Tests the controller's constructor with null arguments.
   */
  @Test
  public void testConstructorNullArguments() {
    assertThrows(IllegalArgumentException.class, () ->
            new SoloRedTextController(null, output));
    assertThrows(IllegalArgumentException.class, () ->
            new SoloRedTextController(new StringReader(""), null));
  }

  /**
   * Tests the playGame method with null model or deck.
   * Verifies that it throws an IllegalArgumentException when
   * these parameters are null.
   */
  @Test
  public void testPlayGameNullModelOrDeck() {
    controller = new SoloRedTextController(new StringReader(""), output);
    List<CardModel> deck = model.getAllCards();

    assertThrows(IllegalArgumentException.class, () ->
            controller.playGame(null, deck, false, 2, 7));
    assertThrows(IllegalArgumentException.class, () ->
            controller.playGame(model, null, false, 2, 7));
  }

  /**
   * Tests the playGame method with invalid values for
   * numPalettes or handSize.
   * Verifies that it throws an IllegalArgumentException when
   * the values are non-positive.
   */
  @Test
  public void testPlayGameInvalidNumPalettesOrHandSize() {
    controller = new SoloRedTextController(new StringReader(""), output);
    List<CardModel> deck = model.getAllCards();

    assertThrows(IllegalArgumentException.class, () ->
            controller.playGame(model, deck, false, 0, 7));
    assertThrows(IllegalArgumentException.class, () ->
            controller.playGame(model, deck, false, 2, 0));
  }

  /**
   * Tests that the game quits when the user
   * enters the 'q' command.
   * Verifies that the output contains the quit
   * message and the game state at the time of quitting.
   */
  @Test
  public void testPlayGameQuitCommand() {
    StringReader input = new StringReader("q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Game quit!"));
    assertTrue(output.toString().contains("State of game when quit:"));
  }

  /**
   * Tests that an invalid command is handled gracefully.
   */
  @Test
  public void testPlayGameInvalidCommand() {
    StringReader input = new StringReader("invalid_command q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Invalid command. Try again."));
    assertTrue(output.toString().contains("Game quit!"));
  }

  /**
   * Tests a valid game start scenario where the user inputs 'q' to quit immediately.
   */
  @Test
  public void testPlayGameValidStart() {
    StringReader input = new StringReader("q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Number of cards in deck:"));
    assertTrue(output.toString().contains("Game quit!"));
  }

  /**
   * Tests a scenario where the user makes a valid move to the canvas.
   */
  @Test
  public void testPlayGameValidMoveToCanvas() {
    StringReader input = new StringReader("canvas 1 q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Canvas: R"));
    assertTrue(output.toString().contains("Canvas: R"));
    assertTrue(output.toString().contains("Game quit!"));
  }

  /**
   * Tests a scenario where the user inputs an invalid canvas index.
   * Verifies that the output contains a message indicating an invalid move.
   */
  @Test
  public void testPlayGameInvalidCanvasIndex() {
    StringReader input = new StringReader("canvas 99 q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Invalid move. Try again. Invalid canvas index."));
    assertTrue(output.toString().contains("Game quit!"));
  }

  /**
   * Tests a scenario where the user inputs an invalid palette move.
   * Verifies that the output contains a message indicating an invalid move.
   */
  @Test
  public void testPlayGameInvalidPaletteMove() {
    StringReader input = new StringReader("palette 3 99 q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Invalid move. Try again. Invalid palette index."));
    assertTrue(output.toString().contains("Game quit!"));
  }

  /**
   * Tests a scenario where the user makes multiple valid commands before quitting.
   */
  @Test
  public void testPlayGameMultipleValidCommands() {
    StringReader input = new StringReader("palette 1 2 canvas 1 q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Canvas: R"));
    assertTrue(output.toString().contains("Canvas: R"));
    assertTrue(output.toString().contains("Game quit!"));
  }

  /**
   * Tests a scenario where the input is empty, leading to an error.
   */
  @Test
  public void testPlayGameEmptyInput() {
    StringReader input = new StringReader("");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    assertThrows(IllegalStateException.class, () ->
            controller.playGame(model, deck, false, 2, 7));
  }

  /**
   * Tests the playGame method with the shuffle parameter set to false.
   */
  @Test
  public void testPlayGameNoShuffle() {
    StringReader input = new StringReader("q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, false, 2, 7);

    assertTrue(output.toString().contains("Number of cards in deck:"));
    assertTrue(output.toString().contains("Game quit!"));
  }

  /**
   * Tests the playGame method with the shuffle parameter set to true.
   */
  @Test
  public void testPlayGameWithShuffle() {
    StringReader input = new StringReader("q");
    controller = new SoloRedTextController(input, output);
    List<CardModel> deck = model.getAllCards();

    controller.playGame(model, deck, true, 2, 7);

    assertTrue(output.toString().contains("Number of cards in deck:"));
    assertTrue(output.toString().contains("Game quit!"));
  }
}
