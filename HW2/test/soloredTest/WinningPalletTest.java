package soloredTest;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.WinningPallet;

/**
 * Test suite for the WinningPallet class.
 */
public class WinningPalletTest {

  @Test
  public void testCheckWinningPalletRed() {
    CardModel canvas = new CardModel("R", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 5)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 1 should have the highest card (R7).", 1, winningIndex);
  }

  @Test
  public void testCheckWinningPalletOrange() {
    // Orange color: most repeated numbers
    CardModel canvas = new CardModel("O", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 3)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 2), new CardModel("O", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 1 should have the most repeated numbers.", 0, winningIndex);
  }

  @Test
  public void testCheckWinningPalletBlue() {
    // Blue color: most different colors
    CardModel canvas = new CardModel("B", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 5)),
            Arrays.asList(new CardModel("R", 7), new CardModel("R", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("I", 6), new CardModel("B", 1))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 2 should have the most different colors.", 2, winningIndex);
  }

  @Test
  public void testCheckWinningPalletIndigo() {
    // Indigo color: longest run
    CardModel canvas = new CardModel("I", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 4), new CardModel("R", 5)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 5), new CardModel("I", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 0 should have the longest run.", 0, winningIndex);
  }

  @Test
  public void testCheckWinningPalletViolet() {
    // Violet color: most cards below four
    CardModel canvas = new CardModel("V", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 2), new CardModel("R", 1)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 1), new CardModel("I", 3))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 0 should have the most cards below four.", 0, winningIndex);
  }

  @Test
  public void testHighestCardTieBreaker() {
    // Tie on number, use color priority
    CardModel canvas = new CardModel("R", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 5)),
            Arrays.asList(new CardModel("O", 5)),
            Arrays.asList(new CardModel("V", 5))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 0 should win due to higher color priority (B > O > V).", 1, winningIndex);
  }

  @Test
  public void testMostSingleNumbersTie() {
    // Tie on most repeated numbers
    CardModel canvas = new CardModel("O", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 3)),
            Arrays.asList(new CardModel("R", 2), new CardModel("I", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 0 should win as it appears first with the most repeats.", 0, winningIndex);
  }

  @Test
  public void testMostDifferentColorsTie() {
    // Tie on most different colors
    CardModel canvas = new CardModel("B", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 5), new CardModel("I", 2)),
            Arrays.asList(new CardModel("R", 7), new CardModel("V", 2), new CardModel("I", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("Palette 0 should win as it appears first with the most different colors.", 0, winningIndex);
  }

  @Test
  public void testLongestRunNoRun() {
    // No pallet has a run longer than 1
    CardModel canvas = new CardModel("I", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3), new CardModel("O", 5)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 2)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("No valid run exists, should return -1.", -1, winningIndex);
  }

  @Test
  public void testCardsBelowFourNoCards() {
    // No cards below four in any pallet
    CardModel canvas = new CardModel("V", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 4), new CardModel("O", 5)),
            Arrays.asList(new CardModel("R", 7), new CardModel("I", 4)),
            Arrays.asList(new CardModel("V", 4), new CardModel("B", 6))
    );
    int winningIndex = WinningPallet.checkWinningPallet(pallets, canvas);
    assertEquals("No cards below four, should return -1.", -1, winningIndex);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckWinningPalletInvalidColor() {
    CardModel canvas = new CardModel("X", 0);
    List<List<CardModel>> pallets = Arrays.asList(
            Arrays.asList(new CardModel("B", 3)),
            Arrays.asList(new CardModel("O", 5))
    );
    WinningPallet.checkWinningPallet(pallets, canvas);
  }
}
