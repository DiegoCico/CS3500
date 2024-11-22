package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.Cell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * A cs3500.threetrios.CellTest class for testing purposes.
 */
public class CellTest {

  private Cell cardCell;
  private Cell holeCell;
  private Card testCard;

  @Before
  public void setUp() {
    cardCell = new Cell(Cell.CellType.CARD_CELL);
    holeCell = new Cell(Cell.CellType.HOLE);
    testCard = new CardModel("TestCard",
            1, 2, 3, 4, COLOR.RED);
  }

  @Test
  public void testConstructor_withValidCardCellType() {
    Cell cell = new Cell(Cell.CellType.CARD_CELL);
    assertEquals(Cell.CellType.CARD_CELL, cell.getType());
    assertTrue(cell.isEmpty());
  }

  @Test
  public void testConstructor_withValidHoleType() {
    Cell cell = new Cell(Cell.CellType.HOLE);
    assertEquals(Cell.CellType.HOLE, cell.getType());
    assertTrue(cell.isEmpty());
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructor_withNullCardOrType() {
    new Cell(null, null);
  }

  @Test
  public void testConstructor_withCardAndType() {
    Cell cell = new Cell(testCard, Cell.CellType.CARD_CELL);
    assertEquals(Cell.CellType.CARD_CELL, cell.getType());
    assertEquals(testCard, cell.getCard());
  }

  @Test
  public void testIsCardCell_withCardCellType() {
    assertTrue(cardCell.isCardCell());
  }

  @Test
  public void testIsCardCell_withHoleType() {
    assertFalse(holeCell.isCardCell());
  }

  @Test
  public void testPlaceCard_onCardCell() {
    cardCell.placeCard(testCard);
    assertEquals(testCard, cardCell.getCard());
    assertFalse(cardCell.isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceCard_withNullCard() {
    cardCell.placeCard(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCard_onHoleCell() {
    holeCell.placeCard(testCard);
  }

  @Test
  public void testGetCard_onEmptyCell() {
    assertNull(cardCell.getCard());
  }

  @Test
  public void testGetCard_afterPlacingCard() {
    cardCell.placeCard(testCard);
    assertEquals(testCard, cardCell.getCard());
  }

  @Test
  public void testIsEmpty_onEmptyCell() {
    assertTrue(cardCell.isEmpty());
  }

  @Test
  public void testIsEmpty_afterPlacingCard() {
    cardCell.placeCard(testCard);
    assertFalse(cardCell.isEmpty());
  }

  @Test
  public void testGetType_returnsCorrectType() {
    assertEquals(Cell.CellType.CARD_CELL, cardCell.getType());
    assertEquals(Cell.CellType.HOLE, holeCell.getType());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCardOnOccupiedCell() {
    cardCell.placeCard(testCard);
    Card anotherCard = new CardModel("TestCard",
            1, 2, 3, 4, COLOR.RED);
    cardCell.placeCard(anotherCard);
  }

  @Test
  public void testGetCardOnHoleCell() {
    assertNull(holeCell.getCard());
  }

  @Test
  public void testRemoveCardFromEmptyCardCell() {
    assertNull(cardCell.getCard());
    assertTrue(cardCell.isEmpty());
  }

  @Test
  public void testPlaceCardDoesNotChangeCellType() {
    cardCell.placeCard(testCard);
    assertEquals(Cell.CellType.CARD_CELL, cardCell.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceNullCardInCardCell() {
    cardCell.placeCard(null);
  }

  @Test
  public void testToStringWithCardInCell() {
    cardCell.placeCard(testCard);
    assertEquals("TestCard: 1 2 3 4 RED", cardCell.getCard().toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructorWithNullType() {
    new Cell(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructorWithNullCardAndType() {
    new Cell(null, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructorWithNullCard() {
    new Cell(null, Cell.CellType.CARD_CELL);
  }

  @Test
  public void testIsCardCellReturnsFalseForHoleType() {
    Cell holeCell = new Cell(Cell.CellType.HOLE);
    assertFalse("Expected hole cell to return false for isCardCell",
            holeCell.isCardCell());
  }

  @Test
  public void testGetTypeAfterPlacingCard() {
    cardCell.placeCard(testCard);
    assertEquals(Cell.CellType.CARD_CELL, cardCell.getType());
  }

  @Test
  public void testTypeIsImmutableAfterPlacingCard() {
    Cell cell = new Cell(Cell.CellType.CARD_CELL);
    cell.placeCard(testCard);
    assertEquals(Cell.CellType.CARD_CELL, cell.getType());
  }

  @Test (expected = IllegalStateException.class)
  public void testEmptyToString() {
    Card card = new CardModel("", -1, -1, -1, -1, COLOR.RED);
  }

  @Test
  public void testMultiplePlaceCardCallsOnSameCardCell() {
    cardCell.placeCard(testCard);
    assertEquals(testCard, cardCell.getCard());

    Card anotherCard = new CardModel("AnotherCard", 5, 6, 7, 8, COLOR.BLUE);

    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      cardCell.placeCard(anotherCard);
    });

    assertEquals("This cell already contains a card", exception.getMessage());
  }



}
