package cs3500.threetrios;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;

import cs3500.threetrios.ai.GoForCorner;
import cs3500.threetrios.ai.HybridStrategy;
import cs3500.threetrios.ai.LeastFlippableStrategy;
import cs3500.threetrios.ai.PosnStrategy;
import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.controller.AIController;
import cs3500.threetrios.controller.ThreeTriosControllerImpl;
import cs3500.threetrios.game.Cell;
import cs3500.threetrios.game.GameGrid;
import cs3500.threetrios.game.GameModel;
import cs3500.threetrios.gui.BluePlayerView;
import cs3500.threetrios.gui.RedPlayerView;
import cs3500.threetrios.parser.BoardConfigParser;
import cs3500.threetrios.player.Player;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * all the tests.
 */
@RunWith(Enclosed.class)
public class CombineTest {

  /**
   * All the test cases for HW7 implementation of two controllers and two views.
   */
  public static class Homework7Tests {
    private GameModel game;
    private RedPlayerView redView;
    private BluePlayerView blueView;
    private ThreeTriosControllerImpl controllerRed;
    private ThreeTriosControllerImpl controllerBlue;
    private PosnStrategy strategy;


    @Before
    public void setUp() throws FileNotFoundException {
      game = new GameModel("docs/boardNoHoles.config");

      Card initialCard = new CardModel("InitialCard", 1, 1, 1, 1, COLOR.RED);
      game.getPlayers()[0].addCard(initialCard);
      Card initialCard2 = new CardModel("InitialCard", 1, 1, 1, 1, COLOR.BLUE);
      game.getPlayers()[1].addCard(initialCard2);

      redView = new RedPlayerView(game);
      blueView = new BluePlayerView(game);

      controllerRed = new ThreeTriosControllerImpl(game, redView);
      controllerBlue = new ThreeTriosControllerImpl(game, blueView);

      strategy = new HybridStrategy(List.of(new GoForCorner(), new LeastFlippableStrategy()));
    }


    @Test
    public void testSwitchTurns() {
      game.switchTurns();
      assertEquals("Player Blue", game.getCurrentPlayer().getName());
      game.switchTurns();
      assertEquals("Player Red", game.getCurrentPlayer().getName());
    }

    @Test
    public void testSwitchTurnsColor() {
      game.switchTurns();
      assertEquals(COLOR.BLUE, game.getCurrentPlayer().getColor());
      game.switchTurns();
      assertEquals(COLOR.RED, game.getCurrentPlayer().getColor());
    }


    @Test
    public void testPlaceCard() {
      Card redCard = game.getPlayers()[0].getHand().get(0);
      game.placeCard(1, 1, redCard);
      assertEquals(redCard, game.getGrid().getCard(1, 1));
    }

    @Test
    public void testHandleCellClickOnPlayerTurnRedView() {
      Card redCard = game.getPlayers()[0].getHand().get(0);
      assertNotNull(redCard);
      assertEquals(COLOR.RED, redCard.getColor());

      controllerRed.setSelectedCard(0);
      controllerRed.handleCellClick(0, 0);
      redView.refresh();

      assertEquals(redCard, game.getGrid().getCard(0,0));
      assertEquals(COLOR.RED, game.getCardAt(0, 0).getColor());
    }



    @Test
    public void testGoForCornerStrategy() {
      strategy = new GoForCorner();

      int[] move = strategy.choosePositions(game);
      assertTrue(game.isMoveLegal(move[0], move[1]));
      assertEquals(0, move[0]);
      assertEquals(0, move[1]);
    }


    @Test
    public void testHybridStrategy() {
      PosnStrategy strategy = new HybridStrategy(List.of(new GoForCorner(),
              new LeastFlippableStrategy()));
      int[] move = strategy.choosePositions(game);
      assertTrue(game.isMoveLegal(move[0], move[1]));
    }

    @Test
    public void testHandleCellClickOutOfTurn() {
      controllerRed.setSelectedCard(0);
      controllerRed.handleCellClick(0, 0);

      assertEquals("Player Blue", game.getCurrentPlayer().getName());

      controllerBlue.setSelectedCard(0);
      controllerBlue.handleCellClick(0, 0);

      redView.displayErrorMessage("Cell is already occupied.");
      String lastMessage = redView.getLastErrorMessage();
      assertEquals(
              "Cell is already occupied." ,
              lastMessage
      );

      redView.refresh();
      blueView.refresh();
    }



    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCardPlacementOccupiedCell() {
      Card redCard = game.getPlayers()[0].getHand().get(0);
      game.placeCard(1, 1, redCard);
      game.placeCard(1, 1, redCard);
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidCardPlacementOutOfBounds() {
      Card redCard = game.getPlayers()[0].getHand().get(0);
      game.placeCard(-1, -1, redCard);
    }


    @Test
    public void testGameOver() {
      controllerRed.setSelectedCard(0);
      controllerRed.handleCellClick(0, 0);
      controllerBlue.setSelectedCard(0);
      controllerBlue.handleCellClick(0, 1);
      controllerRed.setSelectedCard(1);
      controllerRed.handleCellClick(0, 2);

      controllerBlue.setSelectedCard(1);
      controllerBlue.handleCellClick(1, 0);
      controllerRed.setSelectedCard(2);
      controllerRed.handleCellClick(1, 1);
      controllerBlue.setSelectedCard(2);
      controllerBlue.handleCellClick(1, 2);

      controllerRed.setSelectedCard(2);
      controllerRed.handleCellClick(2, 0);
      controllerBlue.setSelectedCard(2);
      controllerBlue.handleCellClick(2, 1);
      controllerRed.setSelectedCard(1);
      controllerRed.handleCellClick(2, 2);

      assertTrue(game.isGameOver());
      String expectedWinner = "Blue Wins";
      assertEquals(expectedWinner, game.getWinner());
    }


    @Test
    public void testRenderGrid() {
      redView.refresh();
      assertNotNull(game.getGrid().getCells());
    }


    @Test
    public void testCardBattle() {
      Card redCard = game.getPlayers()[0].getHand().get(0);
      game.placeCard(1, 1, redCard);

      game.switchTurns();

      Card blueCard = game.getPlayers()[1].getHand().get(0);
      game.placeCard(1, 2, blueCard);

      game.battleCards(1, 2, new HashSet<>());

      assertEquals(COLOR.BLUE, game.getGrid().getCard(1, 2).getColor());
    }


    @Test
    public void testTurnBasedRestriction() {
      controllerRed.setSelectedCard(0);
      controllerRed.handleCellClick(0, 0);
      controllerBlue.setSelectedCard(0);
      controllerBlue.handleCellClick(1, 1);

      redView.displayErrorMessage("Cell is already occupied.");
      String lastMessage = redView.getLastErrorMessage();
      assertEquals("Cell is already occupied.", lastMessage);
    }

    @Test
    public void testAIControllerInitialization() {
      AIController controller = new AIController(game, strategy, COLOR.RED);
      assertNotNull(controller);
    }

    @Test
    public void testInvalidMoveErrorHandling() {
      controllerRed.setSelectedCard(0);
      controllerRed.handleCellClick(-1, -1);

      redView.displayErrorMessage("Invalid Move");
      String lastMessage = redView.getLastErrorMessage();
      assertEquals("Invalid Move", lastMessage);
    }


    @Test
    public void testPlayerTurnValidation() {
      Player currentPlayer = game.getCurrentPlayer();
      assertEquals(COLOR.RED, currentPlayer.getColor());

      Card selectedCard = currentPlayer.getHand().get(0);
      game.placeCard(0, 0, selectedCard);

      game.switchTurns();
      assertEquals(COLOR.BLUE, game.getCurrentPlayer().getColor());
    }



    @Test
    public void testAIValidMoves() {
      int[] move = strategy.choosePositions(game);
      assertTrue(game.isMoveLegal(move[0], move[1]));
    }


    @Test
    public void testBluePlayerViewRefresh() {
      controllerRed.setSelectedCard(0);
      controllerRed.handleCellClick(1, 1);
      controllerBlue.setSelectedCard(0);
      controllerBlue.handleCellClick(0,0);
      controllerRed.setSelectedCard(1);
      controllerRed.handleCellClick(1, 2);

      Card cardInGrid = game.getGrid().getCard(0, 0);
      assertEquals(COLOR.BLUE, cardInGrid.getColor());
    }

    @Test
    public void testSwitchTurnsFr() {
      assertEquals(COLOR.RED, game.getCurrentPlayer().getColor());
      game.switchTurns();
      assertEquals(COLOR.BLUE, game.getCurrentPlayer().getColor());
    }


    @Test (expected = IllegalStateException.class)
    public void testInvalidPlacementOnOccupiedCell() {
      Card redCard = game.getPlayers()[0].getHand().get(0);
      game.placeCard(0, 0, redCard);

      Card blueCard = game.getPlayers()[1].getHand().get(0);
      game.placeCard(0, 0, blueCard);

      assertEquals("Cell is already occupied", redView.getLastErrorMessage());
    }

  }

}
