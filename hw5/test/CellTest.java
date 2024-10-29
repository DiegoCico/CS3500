import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

  private Cell cardCell;
  private Cell holeCell;
  private Card testCard;

  @Before
  public void setUp() {
    cardCell = new Cell(Cell.CellType.CARD_CELL);
    holeCell = new Cell(Cell.CellType.HOLE);
    testCard = new CardModel("TestCard", 1, 2, 3, 4, COLOR.RED);
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
}
