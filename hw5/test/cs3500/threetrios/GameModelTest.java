package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.Cell;
import cs3500.threetrios.game.Game;
import cs3500.threetrios.game.GameGrid;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.player.Player;
import cs3500.threetrios.player.PlayerModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * cs3500.threetrios.GameModelTest class meant for testing.
 */
public class GameModelTest {

  private GameModel game;
  private Grid grid;

  @Before
  public void setUp() {
    grid = new GameGrid(3, 3);
    game = new GameModel(grid);

    Card initialCard = new CardModel("InitialCard", 1, 1, 1, 1, COLOR.RED);
    game.getPlayers()[0].addCard(initialCard);
    Card initialCard2 = new CardModel("InitialCard", 1, 1, 1, 1, COLOR.BLUE);
    game.getPlayers()[1].addCard(initialCard2);
  }

  @Test
  public void testConstructor_withValidGrid() {
    assertNotNull(game.getGrid());
    assertEquals(2, game.getPlayers().length);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_withNullGrid() throws FileNotFoundException {
    new GameModel(null);
  }

  @Test
  public void testSwitchTurns() {
    assertEquals(0, game.getTurn());
    game.switchTurns();
    assertEquals(1, game.getTurn());
    game.switchTurns();
    assertEquals(0, game.getTurn());
  }

  @Test
  public void testGetCurrentPlayer() {
    Player currentPlayer = game.getCurrentPlayer();
    assertNotNull(currentPlayer);
    assertEquals("Player Red", currentPlayer.getName());
  }

  @Test
  public void testPlaceCard_onEmptyCell() {
    Card card = new CardModel("CardTest", 1, 1, 1,1, COLOR.RED);
    game.placeCard(1,1, card);
    assertNotNull("Card should not be null after placement", game.getGrid().getCard(1, 1));
    assertEquals("Placed card should match expected card", card, game.getGrid().getCard(1, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCard_onOccupiedCell() {
    Card card = game.getCurrentPlayer().getHand().get(0);
    game.placeCard(1, 1, card);
    game.switchTurns();
    Card anotherCard = new CardModel("NewCard", 1, 1, 1, 1, COLOR.RED);
    game.placeCard(1, 1, anotherCard);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCard_wrongTurnColor() {
    Card redCard = game.getPlayers()[0].getHand().get(0);
    game.placeCard(0, 0, redCard);
    game.switchTurns();
    game.placeCard(0, 1, redCard);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCard_inInvalidRowCol() {
    Card card = game.getCurrentPlayer().getHand().get(0);
    game.placeCard(-1, 0, card);
  }

  @Test
  public void testComboBattle_triggerComboBattle() {
    Card redCard = new CardModel("RED1", 5, 1, 3, 2, COLOR.RED);
    Card blueCard = new CardModel("BLUE3", 2, 3, 1, 4, COLOR.BLUE);
    Card anotherRedCard = new CardModel("RED2", 1, 2, 3, 5, COLOR.RED);
    List<Card> redCards = List.of(redCard, anotherRedCard);
    List<Card> blueCards = List.of(blueCard);
    Player redPlayer = new PlayerModel("RED1", COLOR.RED, redCards);
    Player bluePlayer = new PlayerModel("BLUE1", COLOR.BLUE, blueCards);
    Player[] players = new Player[]{redPlayer, bluePlayer};
    Game gameBattle = new GameModel(grid, players);

    gameBattle.placeCard(1, 1, redCard);
    assertNotNull(gameBattle.getGrid().getCard(1, 1));
    assertEquals(COLOR.RED, gameBattle.getGrid().getCard(1, 1).getColor());


    gameBattle.switchTurns();
    gameBattle.placeCard(0, 1, blueCard);
    assertNotNull(gameBattle.getGrid().getCard(0, 1));
    assertEquals(COLOR.BLUE, gameBattle.getGrid().getCard(0, 1).getColor());


    gameBattle.switchTurns();
    gameBattle.placeCard(1, 0, anotherRedCard);
    assertNotNull(gameBattle.getGrid().getCard(1, 0));
    assertEquals(COLOR.RED, gameBattle.getGrid().getCard(1, 0).getColor());
  }

  @Test
  public void testBattleCards() {
    GameGrid grid = new GameGrid(3, 3);
    GameModel gameModel = new GameModel(grid);

    CardModel middleCard = new CardModel("MiddleCard", 5, 5, 5, 5, COLOR.RED);
    CardModel leftCard = new CardModel("LeftCard", 1, 1, 1, 1, COLOR.BLUE);
    CardModel rightCard = new CardModel("RightCard", 1, 1, 1, 1, COLOR.BLUE);


    grid.placeCard(1, 1, middleCard);
    grid.placeCard(1, 0, leftCard);
    grid.placeCard(1, 2, rightCard);


    gameModel.battleCards(1, 1);

    assertEquals("Left card should be RED", COLOR.RED, grid.getCard(1, 0).getColor());
    assertEquals("Middle card should remain RED", COLOR.RED, grid.getCard(1, 1).getColor());
    assertEquals("Right card should be RED", COLOR.RED, grid.getCard(1, 2).getColor());
  }


  @Test
  public void testGetGrid_returnsCopy() {
    Grid gridCopy = game.getGrid();
    assertNotSame(grid, gridCopy);
    assertEquals(grid.getCard(1, 1), gridCopy.getCard(1, 1));
  }

  @Test
  public void testGetCards_correctNumberOfCards() {
    List<Card> cards = game.getCards();
    int expectedNumCards = (grid.getNumCardsCells() + 1) / 2;
    assertEquals(expectedNumCards, cards.size());
  }

  @Test(expected = IllegalStateException.class)
  public void testConstructor_withZeroOrNegativeDimensions() {
    new GameGrid(0, 3);
    new GameGrid(3, 0);
    new GameGrid(-3, -3);
  }

  @Test
  public void testPlaceCardInHoleCellThrowsException() {
    Cell[][] cells = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        cells[i][j] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
    cells[1][1] = new Cell(Cell.CellType.HOLE);
    GameGrid grid = new GameGrid(3, 3, cells);

    GameModel game = new GameModel(grid);
    CardModel card = new CardModel("TestCard", 1, 2, 3, 4, COLOR.RED);

    assertThrows(IllegalArgumentException.class, () -> game.placeCard(1, 1, card));
  }

  @Test
  public void testPlaceCardInAnotherHoleCellThrowsException() {
    Cell[][] cells = new Cell[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        cells[i][j] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
    cells[2][2] = new Cell(Cell.CellType.HOLE);
    GameGrid grid = new GameGrid(4, 4, cells);

    GameModel game = new GameModel(grid);
    CardModel card = new CardModel("TestCard2", 5, 6, 7, 8, COLOR.BLUE);

    assertThrows(IllegalStateException.class, () -> game.placeCard(2, 2, card));
  }

  @Test
  public void testRedWins() {
    Cell[][] cells = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        cells[i][j] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
    GameGrid grid = new GameGrid(3, 3, cells);

    GameModel game = new GameModel(grid);
    grid.placeCard(0, 0, new CardModel("RedCard1", 5, 3, 6, 7, COLOR.RED));
    grid.placeCard(0, 1, new CardModel("RedCard2", 3, 5, 7, 8, COLOR.RED));
    grid.placeCard(0, 2, new CardModel("BlueCard1", 6, 2, 4, 3, COLOR.BLUE));

    assertEquals("Red Wins", game.checkWinCondition());
  }

  @Test
  public void testBlueWins() {
    Cell[][] cells = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        cells[i][j] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
    GameGrid grid = new GameGrid(3, 3, cells);

    GameModel game = new GameModel(grid);
    grid.placeCard(0, 0, new CardModel("BlueCard1", 5, 3, 6, 7, COLOR.BLUE));
    grid.placeCard(0, 1, new CardModel("BlueCard2", 3, 5, 7, 8, COLOR.BLUE));
    grid.placeCard(0, 2, new CardModel("RedCard1", 6, 2, 4, 3, COLOR.RED));

    assertEquals("Blue Wins", game.checkWinCondition());
  }

  @Test
  public void testTie() {
    Cell[][] cells = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        cells[i][j] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
    GameGrid grid = new GameGrid(3, 3, cells);

    GameModel game = new GameModel(grid);
    grid.placeCard(0, 0, new CardModel("RedCard1", 5, 3, 6, 7, COLOR.RED));
    grid.placeCard(0, 1, new CardModel("BlueCard1", 3, 5, 7, 8, COLOR.BLUE));

    assertEquals("Tie", game.checkWinCondition());
  }

  @Test
  public void testGameOverTrue() {
    Cell[][] cells = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        cells[i][j] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
    GameGrid grid = new GameGrid(3, 3, cells);

    GameModel game = new GameModel(grid);
    grid.placeCard(0, 0, new CardModel("RedCard1", 5, 3, 6, 7, COLOR.RED));
    grid.placeCard(0, 1, new CardModel("BlueCard1", 3, 5, 7, 8, COLOR.BLUE));
    grid.placeCard(0, 2, new CardModel("RedCard2", 6, 2, 4, 3, COLOR.RED));
    grid.placeCard(1, 0, new CardModel("BlueCard2", 7, 8, 5, 9, COLOR.BLUE));
    grid.placeCard(1, 1, new CardModel("RedCard3", 2, 3, 4, 5, COLOR.RED));
    grid.placeCard(1, 2, new CardModel("BlueCard3", 4, 6, 5, 3, COLOR.BLUE));
    grid.placeCard(2, 0, new CardModel("RedCard4", 8, 7, 6, 5, COLOR.RED));
    grid.placeCard(2, 1, new CardModel("BlueCard4", 5, 4, 3, 2, COLOR.BLUE));
    grid.placeCard(2, 2, new CardModel("RedCard5", 9, 1, 8, 7, COLOR.RED));

    assertTrue(game.isGameOver());
  }

  @Test
  public void testGameOverFalse() {
    Cell[][] cells = new Cell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        cells[i][j] = new Cell(Cell.CellType.CARD_CELL);
      }
    }
    GameGrid grid = new GameGrid(3, 3, cells);

    GameModel game = new GameModel(grid);
    grid.placeCard(0, 0, new CardModel("RedCard1", 5, 3, 6, 7, COLOR.RED));
    grid.placeCard(0, 1, new CardModel("BlueCard1", 3, 5, 7, 8, COLOR.BLUE));
    grid.placeCard(0, 2, new CardModel("RedCard2", 6, 2, 4, 3, COLOR.RED));

    assertFalse(game.isGameOver());
  }
}
