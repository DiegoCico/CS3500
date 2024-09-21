package cs3500.solored.hw2.filesystem;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.WinningPallet;

/**
 * Test suite for the WinningPallet class, including edge cases and exception handling.
 * It verifies the logic of determining the winning pallet based on various conditions and inputs.
 */
public class WinningPalletTest {

  /**
   * Test the "Red" rule where the pallet with the highest card wins.
   */
  @Test
  public void testCheckWinningPalletRed() {
    CardModel canvas = new CardModel("R", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 5)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Pallet 1 should have the highest card (R7).", 1, winningIndex);
  }

  /**
   * Test the behavior when the pallet list is empty.
   * It should throw an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyPalletList() {
    CardModel canvas = new CardModel("R", 0);
    List<List<CardModel>> pallets = new ArrayList<>();
    WinningPallet.checkWinningPallet(pallets, canvas);
  }

  /**
   * Test the behavior when a null pallet is present in the pallet list.
   * It should throw a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testNullPalletInList() {
    CardModel canvas = new CardModel("O", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            null,
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 3))
    );
    WinningPallet.checkWinningPallet(pallets, canvas);
  }

  /**
   * Test the behavior when a null card is present in a pallet.
   * It should throw a NullPointerException.
   */
  @Test(expected = NullPointerException.class)
  public void testNullCardInPallet() {
    CardModel canvas = new CardModel("B", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), null),
            Arrays.asList(new CardModel("R", 7))
    );
    WinningPallet.checkWinningPallet(pallets, canvas);
  }

  /**
   * Test the behavior when a card has a negative number.
   * It should throw an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCardNumber() {
    CardModel canvas = new CardModel("V", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", -3)),
            Arrays.asList(new CardModel("R", 7))
    );
    WinningPallet.checkWinningPallet(pallets, canvas);
  }

  /**
   * Test the behavior when a pallet contains a single card.
   */
  @Test
  public void testSingleCardInPallet() {
    CardModel canvas = new CardModel("R", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3)),
            Arrays.asList(new CardModel("R", 7))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Pallet 1 should win due to the highest card (R7).", 1, winningIndex);
  }

  /**
   * Test the behavior when the canvas card has an invalid color.
   * It should throw an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidColorOnCard() {
    CardModel canvas = new CardModel("Z", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3)),
            Arrays.asList(new CardModel("R", 7))
    );
    WinningPallet.checkWinningPallet(pallets, canvas);
  }

  /**
   * Test the case where all cards are identical.
   * The first pallet should win.
   */
  @Test
  public void testAllIdenticalCards() {
    CardModel canvas = new CardModel("R", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 5), new CardModel("B", 5)),
            Arrays.asList(new CardModel("B", 5), new CardModel("B", 5))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("The first pallet should win due to identical cards.", 0, winningIndex);
  }

  /**
   * Test the behavior when the canvas card color is invalid.
   * It should throw an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCheckWinningPalletInvalidColor() {
    CardModel canvas = new CardModel("X", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3)),
            Arrays.asList(new CardModel("O", 5))
    );
    WinningPallet.checkWinningPallet(pallets, canvas);
  }

  /**
   * Test the "Orange" rule where the pallet with the most repeated numbers wins.
   */
  @Test
  public void testCheckWinningPalletOrange() {
    CardModel canvas = new CardModel("O", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 3)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 2),
                    new CardModel("O", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Pallet 1 should have the most repeated numbers.", 0, winningIndex);
  }

  /**
   * Test the "Blue" rule where the pallet with the most different colors wins.
   */
  @Test
  public void testCheckWinningPalletBlue() {
    CardModel canvas = new CardModel("B", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 5)),
            Arrays.asList(new CardModel("R", 7), new CardModel("R", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("I", 6),
                    new CardModel("B", 1))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Pallet 2 should have the most different colors.", 2, winningIndex);
  }
}
