
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class GameModelTest {

  private GameModel game;
  private Grid grid;

  @Before
  public void setUp() {
    grid = new GameGrid(3, 3);
    game = new GameModel(grid);
  }

  @Test
  public void testConstructor_withValidGrid() {
    assertNotNull(game.getGrid());
    assertEquals(2, game.players.length);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor_withNullGrid() {
    new GameModel(null);
  }

  @Test
  public void testSwitchTurns() {
    assertEquals(0, game.turn);
    game.switchTurns();
    assertEquals(1, game.turn);
    game.switchTurns();
    assertEquals(0, game.turn);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSwitchTurns_invalidTurn() {
    game.turn = 2;
    game.switchTurns();
  }

  @Test
  public void testGetCurrentPlayer() {
    Player currentPlayer = game.getCurrentPlayer();
    assertNotNull(currentPlayer);
    assertEquals("RED", currentPlayer.getName());
  }

  @Test (expected = IllegalStateException.class)
  public void testPlaceCard_onEmptyCell() {
    Card card = game.getCurrentPlayer().getHand().get(0);
    game.placeCard(1, 1, card);
    assertEquals(card, grid.getCard(1, 1));
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCard_onOccupiedCell() {
    Card card = game.getCurrentPlayer().getHand().get(0);
    game.placeCard(1, 1, card);
    Card anotherCard = new CardModel("NewCard", 1, 1, 1, 1, COLOR.RED);
    game.placeCard(1, 1, anotherCard);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCard_wrongTurnColor() {
    Card redCard = game.players[0].getHand().get(0);
    game.placeCard(0, 0, redCard);
    game.placeCard(0, 1, redCard);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceCard_inInvalidRowCol() {
    Card card = game.getCurrentPlayer().getHand().get(0);
    game.placeCard(-1, 0, card);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBattleCards_adjacentCardsWithDifferentColors() {
    Card redCard = new CardModel("Red", 5, 1, 3, 2, COLOR.RED);
    Card blueCard = new CardModel("Blue", 2, 3, 1, 4, COLOR.BLUE);
    grid.placeCard(1, 1, redCard);
    grid.placeCard(0, 1, blueCard);
    game.battleCards(1, 1);
    assertEquals(COLOR.RED, grid.getCard(0, 1).getColor());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testComboBattle_triggerComboBattle() {
    Card redCard = new CardModel("Red", 5, 1, 3, 2, COLOR.RED);
    Card blueCard = new CardModel("Blue", 2, 3, 1, 4, COLOR.BLUE);
    Card anotherBlueCard = new CardModel("Blue", 1, 2, 3, 5, COLOR.BLUE);
    grid.placeCard(1, 1, redCard);
    grid.placeCard(0, 1, blueCard);
    grid.placeCard(1, 0, anotherBlueCard);
    game.battleCards(1, 1);
    assertEquals(COLOR.RED, grid.getCard(0, 1).getColor());
    assertEquals(COLOR.RED, grid.getCard(1, 0).getColor());
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

}
