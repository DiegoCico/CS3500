package cs3500.solored.hw2;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.view.hw02.SimpleRedSevenView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

/**
 * Test suite for SimpleRedSevenView.
 * It checks the view's output when various game actions are performed.
 */
public class SimpleRedSevenViewTest {

  private SoloRedGameModel model;
  private SimpleRedSevenView view;
  private List<CardModel> deck;

  /**
   * Sets up a mock deck and initializes the model and view for testing.
   * This method is run before each test.
   */
  @Before
  public void setUp() {
    model = new SoloRedGameModel();
    deck = Arrays.asList(
            new CardModel("R", 1),
            new CardModel("O", 2),
            new CardModel("B", 3),
            new CardModel("I", 4),
            new CardModel("V", 5),
            new CardModel("R", 6),
            new CardModel("O", 7),
            new CardModel("B", 1),
            new CardModel("I", 2),
            new CardModel("V", 3),
            new CardModel("R", 4),
            new CardModel("O", 5)
    );
    model.startGame(deck, false, 3, 5);
    view = new SimpleRedSevenView(model);
  }

  /**
   * Tests the initial state of the game to ensure the view outputs the correct format.
   */
  @Test
  public void testToStringInitialGame() {
    String expected = "Canvas: R\n"
            + "P1: R6\n"
            + "> P2: O7\n"
            + "P3: B1\n"
            + "Hand: R1 O2 B3 I4 V5";
    assertEquals("Initial state wrong.", expected, view.toString().trim());
  }

  /**
   * Tests the view's output after playing a card to a palette.
   */
  @Test
  public void testToStringAfterPlayToPalette() {
    model.playToPalette(2, 0);

    String expected = "Canvas: R\n"
            + "P1: R6\n"
            + "> P2: O7\n"
            + "P3: B1 R1\n"
            + "Hand: O2 B3 I4 V5";
    assertEquals("Play to palette state wrong.", expected, view.toString());
  }

  /**
   * Tests the view's output after playing a card to the canvas.
   */
  @Test
  public void testToStringAfterPlayToCanvas() {
    model.playToCanvas(1);

    String expected = "Canvas: O\n"
            + "P1: R6\n"
            + "P2: O7\n"
            + "P3: B1\n"
            + "Hand: R1 B3 I4 V5";
    assertEquals("Play to canvas state wrong.", expected, view.toString());
  }

  /**
   * Tests the view's output after a win condition is met.
   */
  @Test
  public void testToStringAfterWinCondition() {
    model.playToPalette(2, 0);
    model.playToCanvas(1);

    String expected = "Canvas: B\n"
            + "P1: R6\n"
            + "P2: O7\n"
            + "> P3: B1 R1\n"
            + "Hand: O2 I4 V5";
    assertEquals("Winning state wrong.", expected, view.toString());
  }

  /**
   * Tests the view's output when the hand becomes empty after playing all cards.
   */
  @Test
  public void testToStringEmptyHand() {
    model.playToPalette(0, 0);
    model.playToPalette(0, 0);
    model.playToPalette(0, 0);
    model.playToPalette(0, 0);
    model.playToPalette(0, 0);

    String expected = "Canvas: R\n"
            + "P1: R6 R1 O2 B3 I4 V5\n"
            + "> P2: O7\n"
            + "P3: B1\n"
            + "Hand: ";
    assertEquals("Empty hand state wrong.", expected, view.toString());
  }
}
