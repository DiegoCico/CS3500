package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.Cell;
import cs3500.threetrios.game.GameGrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

/**
 * cs3500.threetrios.GameGridTest class for testing purposes.
 */
public class GameGridTest {

  private GameGrid gameGrid;

  @Before
  public void setUp() {
    gameGrid = new GameGrid(3, 3);
  }

  @Test
  public void testConstructor_withValidDimensions() {
    GameGrid grid = new GameGrid(4, 5);
    assertEquals(4, grid.getRows());
    assertEquals(5, grid.getCols());
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructor_withInvalidDimensions() {
    new GameGrid(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_withNullCells() {
    new GameGrid(3, 3, null);
  }

  @Test
  public void testConstructor_withGridCopy() {
    GameGrid originalGrid = new GameGrid(2, 2);
    GameGrid copyGrid = new GameGrid(originalGrid);
    assertEquals(originalGrid.getRows(), copyGrid.getRows());
    assertEquals(originalGrid.getCols(), copyGrid.getCols());
    assertNotSame(originalGrid.getCells(), copyGrid.getCells());
  }

  @Test
  public void testInitializeGrid() {
    gameGrid.initializeGrid();
    assertEquals(Cell.CellType.CARD_CELL, gameGrid.getCellType(0, 0));
  }

  @Test
  public void testGetCols() {
    assertEquals(3, gameGrid.getCols());
  }

  @Test
  public void testGetRows() {
    assertEquals(3, gameGrid.getRows());
  }

  @Test
  public void testGetNumCardsCells() {
    assertEquals(9, gameGrid.getNumCardsCells());
  }

  @Test
  public void testPlaceCard_onValidCell() {
    Card card = new CardModel("TestCard", 1, 2, 3, 4, COLOR.RED);
    gameGrid.placeCard(0, 0, card);
    assertEquals(card, gameGrid.getCard(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCard_invalidPosition() {
    gameGrid.getCard(-1, 0);
  }

  @Test
  public void testIsEmpty_onEmptyCell() {
    assertTrue(gameGrid.isEmpty(0, 0));
  }

  @Test
  public void testIsEmpty_onOccupiedCell() {
    Card card = new CardModel("TestCard", 1, 1, 1, 1, COLOR.RED);
    gameGrid.placeCard(0, 0, card);
    assertFalse(gameGrid.isEmpty(0, 0));
  }

  @Test(expected = IllegalStateException.class)
  public void testGetCellType_invalidPosition() {
    gameGrid.getCellType(-1, -1);
  }

  @Test
  public void testValidPosition_withValidPositions() {
    assertTrue(gameGrid.validPosition(0, 0));
    assertTrue(gameGrid.validPosition(2, 2));
  }

  @Test
  public void testValidPosition_withInvalidPositions() {
    assertFalse(gameGrid.validPosition(-1, 0));
    assertFalse(gameGrid.validPosition(3, 3));
  }

  @Test
  public void testGetCells_deepCopy() {
    Cell[][] cells = gameGrid.getCells();
    assertNotNull(cells);
    assertNotSame(gameGrid.getCells(), cells);
  }

  @Test
  public void testConstructorWithSmallDimensions() {
    GameGrid grid = new GameGrid(1, 1);
    assertEquals(1, grid.getRows());
    assertEquals(1, grid.getCols());
    assertTrue(grid.isEmpty(0, 0));
  }

  @Test
  public void testConstructor_withLargeDimensions() {
    GameGrid grid = new GameGrid(100, 100);
    assertEquals(100, grid.getRows());
    assertEquals(100, grid.getCols());
    assertTrue(grid.isEmpty(0, 0));
  }


  @Test
  public void testReinitializeGrid() {
    Card card = new CardModel("CardBeforeReset", 2, 2, 2, 2, COLOR.RED);
    gameGrid.placeCard(0, 0, card);
    assertEquals(card, gameGrid.getCard(0, 0));

    gameGrid.initializeGrid();
    assertTrue(gameGrid.isEmpty(0, 0));
  }

}
