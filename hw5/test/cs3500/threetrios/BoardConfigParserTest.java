package cs3500.threetrios;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.game.Cell;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.player.Player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * testing playing with the config.
 */
public class BoardConfigParserTest {
  private GameModel game;
  private Grid grid;

  @Before
  public void setUp() throws FileNotFoundException {
    game = new GameModel();
    grid = game.getGrid();
  }

  @Test
  public void testGridInitialization() {
    assertNotNull("Grid should not be null", grid);
    assertEquals("Grid should have 3 rows", 3, grid.getRows());
    assertEquals("Grid should have 3 columns", 3, grid.getCols());
  }

  @Test
  public void testGridLayout() {
    assertEquals("Cell (0,0) should be a CARD_CELL",
            Cell.CellType.CARD_CELL, grid.getCellType(0, 0));
    assertEquals("Cell (0,1) should be a HOLE",
            Cell.CellType.HOLE, grid.getCellType(0, 1));
    assertEquals("Cell (0,2) should be a HOLE",
            Cell.CellType.HOLE, grid.getCellType(0, 2));

    assertEquals("Cell (1,0) should be a HOLE",
            Cell.CellType.HOLE, grid.getCellType(1, 0));
    assertEquals("Cell (1,1) should be a CARD_CELL",
            Cell.CellType.CARD_CELL, grid.getCellType(1, 1));
    assertEquals("Cell (1,2) should be a HOLE",
            Cell.CellType.HOLE, grid.getCellType(1, 2));

    assertEquals("Cell (2,0) should be a HOLE",
            Cell.CellType.HOLE, grid.getCellType(2, 0));
    assertEquals("Cell (2,1) should be a HOLE",
            Cell.CellType.HOLE, grid.getCellType(2, 1));
    assertEquals("Cell (2,2) should be a CARD_CELL",
            Cell.CellType.CARD_CELL, grid.getCellType(2, 2));
  }

  @Test
  public void testPlayerCardAttributes() {
    Player[] players = game.getPlayers();
    assertNotNull("Players array should not be null", players);

    for (Player player : players) {
      List<Card> playerHand = player.getHand();
      for (Card card : playerHand) {
        int north = card.getNorth();
        int south = card.getSouth();
        int east = card.getEast();
        int west = card.getWest();

        assertTrue("North value should be between 1 and 10",
                north >= 1 && north <= 10);
        assertTrue("South value should be between 1 and 10",
                south >= 1 && south <= 10);
        assertTrue("East value should be between 1 and 10",
                east >= 1 && east <= 10);
        assertTrue("West value should be between 1 and 10",
                west >= 1 && west <= 10);
      }
    }
  }

  @Test
  public void testPlayerInitialization() {
    Player[] players = game.getPlayers();
    assertNotNull("Players array should not be null", players);
    assertEquals("There should be exactly 2 players", 2, players.length);

    Player redPlayer = players[0];
    Player bluePlayer = players[1];

    int expected = players[0].getHand().size() + players[1].getHand().size();

    assertEquals("Red player's color should be RED", COLOR.RED,
            redPlayer.getColor());
    assertEquals("Blue player's color should be BLUE", COLOR.BLUE,
            bluePlayer.getColor());
    assertEquals("Red player should have half the deck size",
            expected / 2, redPlayer.getHand().size());
    assertEquals("Blue player should have half the deck size",
            expected / 2, bluePlayer.getHand().size());
  }

  @Test
  public void testCardColorAssignment() {
    Player redPlayer = game.getPlayers()[0];
    Player bluePlayer = game.getPlayers()[1];

    for (Card card : redPlayer.getHand()) {
      assertEquals("All cards in red player's hand should be RED",
              COLOR.RED, card.getColor());
    }

    for (Card card : bluePlayer.getHand()) {
      assertEquals("All cards in blue player's hand should be BLUE",
              COLOR.BLUE, card.getColor());
    }
  }

  @Test
  public void testPlaceCard() {
    Player currentPlayer = game.getCurrentPlayer();
    Card card = currentPlayer.getCard(1);

    assertTrue("Cell (0,0) should initially be empty",
            game.getGrid().isEmpty(0, 0));
    game.placeCard(0, 0, card);
    assertFalse("Cell (0,0) should no longer be empty after placing a card",
            game.getGrid().isEmpty(0, 0));
    assertEquals("Placed card should match the card object",
            card, game.getGrid().getCard(0, 0));
  }
}
