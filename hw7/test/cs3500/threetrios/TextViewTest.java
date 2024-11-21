package cs3500.threetrios;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.Cell;
import cs3500.threetrios.game.GameGrid;
import cs3500.threetrios.view.TextView;

/**
 * Class to test the view of the game.
 */
public class TextViewTest {
  private TextView textView;
  private GameGrid gameGrid;

  @Before
  public void setUp() {
    gameGrid = new GameGrid(3, 3);
    textView = new TextView(gameGrid);
  }

  @Test
  public void testRenderEmptyView() {
    for (int row = 0; row < gameGrid.getRows(); row++) {
      for (int col = 0; col < gameGrid.getCols(); col++) {
        gameGrid.setCellType(row, col, Cell.CellType.CARD_CELL);
      }
    }
    String expected = "_ _ _ \n_ _ _ \n_ _ _ \n";
    Assert.assertEquals("Render should display a grid with empty cells",
            expected, textView.render());
  }

  @Test
  public void testRenderGridWithHoleAndCard() {
    gameGrid.setCellType(0, 0, Cell.CellType.HOLE);
    gameGrid.setCellType(0, 1, Cell.CellType.CARD_CELL);
    gameGrid.setCellType(1, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(1, 0, new CardModel("Dragon", 4,
            5, 6, 7, COLOR.RED));
    gameGrid.setCellType(1, 1, Cell.CellType.CARD_CELL);

    textView = new TextView(gameGrid);
    String expected = "X _ _ \nD _ _ \n_ _ _ \n";
    String actual = textView.render();

    assertEquals("Render should display a grid with one hole," +
            " one card, and empty cells", expected, textView.render());
  }

  @Test
  public void testRenderGridWithMultipleCards() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.setCellType(0, 1, Cell.CellType.CARD_CELL);
    gameGrid.setCellType(1, 0, Cell.CellType.CARD_CELL);
    gameGrid.setCellType(1, 1, Cell.CellType.CARD_CELL);

    gameGrid.placeCard(0, 0, new CardModel("Knight", 2, 3,
            4, 5, COLOR.BLUE));
    gameGrid.placeCard(0, 1, new CardModel("Dragon", 6, 7,
            8, 9, COLOR.RED));
    gameGrid.placeCard(1, 1, new CardModel("Hero", 1, 2,
            3, 4, COLOR.BLUE));

    textView = new TextView(gameGrid);

    String expected = "K D _ \n_ H _ \n_ _ _ \n";
    assertEquals("Render should display a grid with " +
            "multiple cards correctly", expected, textView.render());
  }


  @Test
  public void testRenderWithBorderHoles() {
    for (int row = 0; row < gameGrid.getRows(); row++) {
      for (int col = 0; col < gameGrid.getCols(); col++) {
        if (row == 0 || row == gameGrid.getRows() - 1 || col == 0
                || col == gameGrid.getCols() - 1) {
          gameGrid.setCellType(row, col, Cell.CellType.HOLE);
        } else {
          gameGrid.setCellType(row, col, Cell.CellType.CARD_CELL);
        }
      }
    }
    String expected = "X X X \nX _ X \nX X X \n";
    Assert.assertEquals(expected, textView.render());
  }

  @Test
  public void testRenderGridWithDiagonalHoles() {
    for (int row = 0; row < gameGrid.getRows(); row++) {
      for (int col = 0; col < gameGrid.getCols(); col++) {
        if (row == col) {
          gameGrid.setCellType(row, col, Cell.CellType.HOLE);
        } else {
          gameGrid.setCellType(row, col, Cell.CellType.CARD_CELL);
        }
      }
    }
    String expected = "X _ _ \n_ X _ \n_ _ X \n";
    assertEquals("Render should display a grid with" +
            "diagonal holes", expected, textView.render());
  }

  @Test
  public void testRenderGridWithMixedCellsAndCards() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 0, new CardModel("Dragon", 4,
            5, 6, 7, COLOR.RED));

    gameGrid.setCellType(0, 1, Cell.CellType.CARD_CELL);
    gameGrid.setCellType(0, 2, Cell.CellType.HOLE);

    gameGrid.setCellType(1, 0, Cell.CellType.HOLE);
    gameGrid.setCellType(1, 1, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(1, 1, new CardModel("Knight", 2,
            3, 4, 5, COLOR.BLUE));

    gameGrid.setCellType(1, 2, Cell.CellType.CARD_CELL);
    gameGrid.setCellType(2, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(2, 0, new CardModel("Hero", 1,
            2, 3, 4, COLOR.RED));

    gameGrid.setCellType(2, 1, Cell.CellType.CARD_CELL);
    gameGrid.setCellType(2, 2, Cell.CellType.CARD_CELL);

    String expected = "D _ X \nX K _ \nH _ _ \n";
    assertEquals("Render should display a grid with mixed cells," +
            " holes, and cards", expected, textView.render());
  }

  @Test
  public void testRenderCompletelyFilledGridWithCards() {
    for (int row = 0; row < gameGrid.getRows(); row++) {
      for (int col = 0; col < gameGrid.getCols(); col++) {
        gameGrid.setCellType(row, col, Cell.CellType.CARD_CELL);
        gameGrid.placeCard(row, col, new CardModel("Hero", 1,
                2, 3, 4, COLOR.RED));
      }
    }
    String expected = "H H H \nH H H \nH H H \n";
    assertEquals("Render should display a completely filled" +
            " grid with cards", expected, textView.render());
  }

  @Test
  public void testRenderEmptyRowAndColumn() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 0, new CardModel("Dragon", 4, 5,
            6, 7, COLOR.RED));
    gameGrid.setCellType(0, 2, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 2, new CardModel("Knight", 2, 3,
            4, 5, COLOR.BLUE));

    gameGrid.setCellType(2, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(2, 0, new CardModel("Hero", 1, 2,
            3, 4, COLOR.RED));
    gameGrid.setCellType(2, 2, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(2, 2, new CardModel("Hero", 1, 2,
            3, 4, COLOR.BLUE));

    String expected = "D _ K \n_ _ _ \nH _ H \n";
    assertEquals("Render should display a grid with an empty row and column",
            expected, textView.render());
  }

  @Test
  public void testRenderGridWithDifferentColoredCards() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 0, new CardModel("Dragon", 4, 5,
            6, 7, COLOR.RED));

    gameGrid.setCellType(0, 1, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 1, new CardModel("Knight", 2, 3,
            4, 5, COLOR.BLUE));

    gameGrid.setCellType(0, 2, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 2, new CardModel("Hero", 1, 2,
            3, 4, COLOR.RED));

    gameGrid.setCellType(1, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(1, 0, new CardModel("Mage", 3, 4,
            5, 6, COLOR.BLUE));

    gameGrid.setCellType(1, 1, Cell.CellType.HOLE);
    gameGrid.setCellType(1, 2, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(1, 2, new CardModel("Warrior", 7, 8,
            9, 1, COLOR.RED));

    gameGrid.setCellType(2, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(2, 0, new CardModel("Rogue", 4, 5,
            6, 7, COLOR.BLUE));

    gameGrid.setCellType(2, 1, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(2, 1, new CardModel("Archer", 5, 6,
            7, 8, COLOR.RED));

    gameGrid.setCellType(2, 2, Cell.CellType.HOLE);

    String expected = "D K H \nM X W \nR A X \n";
    assertEquals("Render should display a grid with different" +
            " colored cards and holes", expected, textView.render());
  }

  @Test
  public void testRenderSingleCellGrid() {
    GameGrid singleCellGrid = new GameGrid(1, 1);
    TextView singleCellView = new TextView(singleCellGrid);

    singleCellGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    String expectedEmpty = "_ \n";
    assertEquals("Render should display a single empty cell",
            expectedEmpty, singleCellView.render());

    singleCellGrid.setCellType(0, 0, Cell.CellType.HOLE);
    String expectedHole = "X \n";
    assertEquals("Render should display a single hole", expectedHole, singleCellView.render());

    singleCellGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    singleCellGrid.placeCard(0, 0, new CardModel("Hero", 1, 2, 3, 4, COLOR.RED));
    String expectedCard = "H \n";
    assertEquals("Render should display a single cell with a card",
            expectedCard, singleCellView.render());
  }

  @Test
  public void testRenderLargeGrid() {
    GameGrid largeGrid = new GameGrid(5, 5);
    TextView largeGridView = new TextView(largeGrid);

    for (int row = 0; row < largeGrid.getRows(); row++) {
      for (int col = 0; col < largeGrid.getCols(); col++) {
        largeGrid.setCellType(row, col, Cell.CellType.CARD_CELL);
      }
    }

    StringBuilder expected = new StringBuilder();
    for (int i = 0; i < 5; i++) {
      expected.append("_ _ _ _ _ \n");
    }
    assertEquals("Render should display a large empty grid",
            expected.toString(), largeGridView.render());
  }

  @Test
  public void testRenderAfterMultipleChanges() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 0, new CardModel("Knight", 2, 3,
            4, 5, COLOR.BLUE));
    String expectedAfterFirstChange = "K _ _ \n_ _ _ \n_ _ _ \n";
    assertEquals("Render after first card placement",
            expectedAfterFirstChange, textView.render());

    gameGrid.setCellType(2, 2, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(2, 2, new CardModel("Hero", 1, 2,
            3, 4, COLOR.RED));
    String expectedAfterSecondChange = "K _ _ \n_ _ _ \n_ _ H \n";
    assertEquals("Render after placing second card",
            expectedAfterSecondChange, textView.render());

    gameGrid.setCellType(0, 0, Cell.CellType.HOLE);
    String expectedAfterThirdChange = "X _ _ \n_ _ _ \n_ _ H \n";
    assertEquals("Render after changing first cell to hole",
            expectedAfterThirdChange, textView.render());
  }

  @Test
  public void testRenderZeroByZero() {
    GameGrid grid = new GameGrid(0, 0);
    TextView textView = new TextView(grid);
    String expectedEmpty = "";
    assertEquals("Render should display a zero by zero",
            expectedEmpty, textView.render());
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderAfterCardUpdate() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 0, new CardModel("Dragon", 4, 5,
            6, 7, COLOR.RED));
    String expectedWithFirstCard = "D _ _ \n_ _ _ \n_ _ _ \n";
    assertEquals("Render should display grid with initial card",
            expectedWithFirstCard, textView.render());

    gameGrid.placeCard(0, 0, new CardModel("Knight", 2,
            3, 4, 5, COLOR.BLUE));
    String expectedWithUpdatedCard = "K _ _ \n_ _ _ \n_ _ _ \n";
    assertEquals("Render should display grid with updated card",
            expectedWithUpdatedCard, textView.render());
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testRenderWithInvalidRowPlacement() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(3, 0, new CardModel("Dragon",
            4, 5, 6, 7, COLOR.RED));
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testRenderWithInvalidColumnPlacement() {
    gameGrid.setCellType(0, 0, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(0, 3, new CardModel("Dragon",
            4, 5, 6, 7, COLOR.RED));
  }

  @Test(expected = NullPointerException.class)
  public void testRenderWithInvalidHolePlacement() {
    gameGrid.setCellType(1, 1, Cell.CellType.HOLE);
    gameGrid.placeCard(1, 1, new CardModel("Dragon",
            4, 5, 6, 7, COLOR.RED));
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderWithDuplicateCardPlacement() {
    gameGrid.setCellType(1, 1, Cell.CellType.CARD_CELL);
    gameGrid.placeCard(1, 1, new CardModel("Knight",
            2, 3, 4, 5, COLOR.BLUE));
    gameGrid.placeCard(1, 1, new CardModel("Dragon",
            4, 5, 6, 7, COLOR.RED));
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testRenderWithNegativeRowPlacement() {
    gameGrid.placeCard(-1, 0, new CardModel("Dragon",
            4, 5, 6, 7, COLOR.RED));
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testRenderWithNegativeColumnPlacement() {
    gameGrid.placeCard(0, -1, new CardModel("Dragon",
            4, 5, 6, 7, COLOR.RED));
  }

  @Test
  public void testRenderGridFilledWithHoles() {
    for (int row = 0; row < gameGrid.getRows(); row++) {
      for (int col = 0; col < gameGrid.getCols(); col++) {
        gameGrid.setCellType(row, col, Cell.CellType.HOLE);
      }
    }
    String expected = "X X X \nX X X \nX X X \n";
    assertEquals("Render should display a grid completely " +
            "filled with holes", expected, textView.render());
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderWithZeroGridDimensions() {
    new GameGrid(-3, -3);
  }


}