
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.threetrios.COLOR;
import cs3500.threetrios.Card;
import cs3500.threetrios.CardModel;
import cs3500.threetrios.Game;
import cs3500.threetrios.GameGrid;
import cs3500.threetrios.GameModel;
import cs3500.threetrios.Grid;
import cs3500.threetrios.Player;
import cs3500.threetrios.PlayerModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * GameModelTest class meant for testing.
 */
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
    assertEquals("cs3500.threetrios.Player Red", currentPlayer.getName());
  }

  @Test (expected = AssertionError.class)
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
    game.placeCard(1, 1, redCard);
    game.switchTurns();
    game.placeCard(0, 1, blueCard);
    game.battleCards(1, 1);
    assertEquals(COLOR.RED, grid.getCard(0, 1).getColor());
  }

  //FIX
  @Test
  public void testComboBattle_triggerComboBattle() {
    Card redCard = new CardModel("RED1", 5, 1, 3, 2, COLOR.RED);
    Card blueCard = new CardModel("BLUE3", 2, 3, 1, 4, COLOR.BLUE);
    Card anotherRedCard = new CardModel("RED2", 1, 2, 3, 5, COLOR.RED);
    List<Card> redCards = List.of(redCard, anotherRedCard);
    List<Card> blueCards = List.of(blueCard);
    Player redPlayer = new PlayerModel("RED1", COLOR.RED, redCards);
    Player bluePlayer = new PlayerModel("BLUE1", COLOR.BLUE, blueCards);
    Player[] players = new Player[] {redPlayer, bluePlayer};
    Game gameBattle = new GameModel(grid, players);
    gameBattle.placeCard(1, 1, gameBattle.getCurrentPlayer().getCard(1));
    assertNotNull("cs3500.threetrios.Card should have been placed at (1, 1)", grid.getCard(1, 1));
    gameBattle.switchTurns();
    gameBattle.placeCard(0, 1, gameBattle.getCurrentPlayer().getCard(1));
    assertNotNull("cs3500.threetrios.Card should have been placed at (0, 1)", grid.getCard(0, 1));
    gameBattle.switchTurns();
    gameBattle.placeCard(1, 0, gameBattle.getCurrentPlayer().getCard(1));
    assertNotNull("cs3500.threetrios.Card should have been placed at (1, 0)", grid.getCard(1, 0));
    assertEquals(COLOR.RED, grid.getCard(0, 1).getColor());
    assertEquals(COLOR.RED, grid.getCard(1, 0).getColor());
  }

  //FIX
  @Test
  public void testComboBattle_triggerComboBattleMultiSide() {
    Card redCard = new CardModel("RED1", 5, 1, 3, 2, COLOR.RED);
    Card blueCard = new CardModel("BLUE3", 2, 3, 1, 4, COLOR.BLUE);
    Card anotherRedCard = new CardModel("RED2", 1, 2, 3, 5, COLOR.RED);
    Card anotherBlueCard = new CardModel("BLUE3", 10, 10, 10, 10, COLOR.BLUE);
    List<Card> redCards = List.of(redCard, anotherRedCard);
    List<Card> blueCards = List.of(blueCard, anotherBlueCard);
    Player redPlayer = new PlayerModel("RED1", COLOR.RED, redCards);
    Player bluePlayer = new PlayerModel("BLUE1", COLOR.BLUE, blueCards);
    Player[] players = new Player[] {redPlayer, bluePlayer};
    Grid gridMulti = new GameGrid(5, 5);
    Game gameBattle = new GameModel(gridMulti, players);
    gameBattle.placeCard(1, 1, gameBattle.getCurrentPlayer().getCard(1));
    assertNotNull("cs3500.threetrios.Card should have been placed at (1, 1)", gridMulti.getCard(1, 1));
    assertEquals(COLOR.RED, gridMulti.getCard(1, 1).getColor());

    gameBattle.switchTurns();
    gameBattle.placeCard(2, 1, gameBattle.getCurrentPlayer().getCard(1));
    assertNotNull("cs3500.threetrios.Card should have been placed at (2, 1)", gridMulti.getCard(2, 1));
    assertEquals(COLOR.BLUE, gridMulti.getCard(2, 1).getColor());

    gameBattle.switchTurns();
    gameBattle.placeCard(4, 1, gameBattle.getCurrentPlayer().getCard(1));
    assertNotNull("cs3500.threetrios.Card should have been placed at (4, 1)", gridMulti.getCard(4, 1));
    assertEquals(COLOR.RED, gridMulti.getCard(4, 1).getColor());

    gameBattle.switchTurns();
    gameBattle.placeCard(1, 2, gameBattle.getCurrentPlayer().getCard(0));
    assertNotNull("cs3500.threetrios.Card should have been placed at (1, 2)", gridMulti.getCard(1, 2));
    assertEquals(COLOR.BLUE, gridMulti.getCard(1, 2).getColor());

    assertEquals(COLOR.BLUE, gridMulti.getCard(1, 1).getColor());
    assertEquals(COLOR.BLUE, gridMulti.getCard(2, 1).getColor());

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

}
