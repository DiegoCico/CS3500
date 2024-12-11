package cs3500.threetrios;

import org.junit.Test;

import java.util.List;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.GameGrid;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.game.MockGameModel;
import cs3500.threetrios.level0.Hint0Impl;
import cs3500.threetrios.level1.FallenAceBattleRuleImpl;
import cs3500.threetrios.level1.ReverseBattleRuleImpl;
import cs3500.threetrios.level2.PlusBattleRuleImpl;
import cs3500.threetrios.level2.SameBattleRuleImpl;
import cs3500.threetrios.level3.Level3BattleRuleImpl;
import cs3500.threetrios.player.Player;
import cs3500.threetrios.player.PlayerModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for extra credit.
 */
public class RulesTest {
  @Test
  public void testReverseRuleShouldNotFlip() {
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 2, 1, 6, COLOR.BLUE);

    ReverseBattleRuleImpl reverseRule = new ReverseBattleRuleImpl();
    boolean shouldFlip = reverseRule.shouldFlip(attacker, defender, 1);

    assertFalse(shouldFlip);
  }

  @Test
  public void testReverseRuleShouldFlip() {
    Card attacker = new CardModel("Attacker", 5, 4, 3, 7, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 2, 1, 6, COLOR.BLUE);

    ReverseBattleRuleImpl reverseRule = new ReverseBattleRuleImpl();
    boolean shouldFlip = reverseRule.shouldFlip(attacker, defender, 3);

    assertTrue(shouldFlip);
  }

  @Test
  public void testFallenAceRuleShouldFlip() {

    Card attacker = new CardModel("Attacker", 1, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 10, 5, 1, 6, COLOR.BLUE);

    FallenAceBattleRuleImpl fallenAceRule = new FallenAceBattleRuleImpl();
    boolean shouldFlip = fallenAceRule.shouldFlip(attacker, defender, 3);

    assertTrue(shouldFlip);
  }

  @Test
  public void testFallenAceShouldNotFlipAttackerNot1() {
    Grid grid = new GameGrid(3, 3);

    Card attacker = new CardModel("Attacker", 4, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 10, 5, 1, 6, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, defender);

    FallenAceBattleRuleImpl fallenAceRule = new FallenAceBattleRuleImpl();

    boolean shouldFlip = fallenAceRule.shouldFlip(attacker, defender, 0);

    assertFalse(shouldFlip);
  }


  @Test
  public void testFallenAceShouldNotFlipDefenderNot10() {
    Grid grid = new GameGrid(3, 3);

    Card attacker = new CardModel("Attacker", 1, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 9, 5, 1, 6, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, defender);

    FallenAceBattleRuleImpl fallenAceRule = new FallenAceBattleRuleImpl();

    boolean shouldFlip = fallenAceRule.shouldFlip(attacker, defender, 0);

    assertFalse(shouldFlip);
  }


  @Test
  public void testFallenAceRuleShould() {
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 9, 5, 1, 6, COLOR.BLUE);

    FallenAceBattleRuleImpl fallenAceRule = new FallenAceBattleRuleImpl();
    boolean shouldFlip = fallenAceRule.shouldFlip(attacker, defender, 0);

    assertTrue(shouldFlip);
  }

  @Test
  public void testSameRuleShouldFlip() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card adjacent1 = new CardModel("Adjacent1", 3, 5, 1, 4, COLOR.BLUE);
    Card adjacent2 = new CardModel("Adjacent2", 1, 3, 5, 2, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, adjacent1);
    grid.placeCard(2, 1, adjacent2);

    SameBattleRuleImpl sameRule = new SameBattleRuleImpl(grid);
    boolean shouldFlip = sameRule.shouldFlip(attacker, adjacent1, 0);

    assertTrue(shouldFlip);
  }

  @Test
  public void testSameRuleShouldNotFlip() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card adjacent1 = new CardModel("Adjacent1", 3, 5, 1, 4, COLOR.BLUE);
    Card adjacent2 = new CardModel("Adjacent2", 6, 3, 8, 2, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, adjacent1);
    grid.placeCard(2, 1, adjacent2);

    SameBattleRuleImpl sameRule = new SameBattleRuleImpl(grid);
    boolean shouldFlip = sameRule.shouldFlip(attacker, adjacent1, 1);

    assertFalse(shouldFlip);
  }

  @Test
  public void testPlusRuleShouldFlip() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 3, 5, 3, 2, COLOR.RED);
    Card adjacent1 = new CardModel("Adjacent1", 3, 5, 5, 3, COLOR.BLUE);
    Card adjacent2 = new CardModel("Adjacent2", 3, 5, 5, 3, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, adjacent1);
    grid.placeCard(2, 1, adjacent2);

    PlusBattleRuleImpl plusRule = new PlusBattleRuleImpl(grid);
    boolean shouldFlip = plusRule.shouldFlip(attacker, adjacent1, 0);

    assertTrue(shouldFlip);
  }


  @Test
  public void testPlusRuleShouldNotFlip() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card adjacent1 = new CardModel("Adjacent1", 3, 5, 2, 3, COLOR.BLUE);
    Card adjacent2 = new CardModel("Adjacent2", 4, 2, 6, 1, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, adjacent1);
    grid.placeCard(2, 1, adjacent2);

    PlusBattleRuleImpl plusRule = new PlusBattleRuleImpl(grid);
    boolean shouldFlip = plusRule.shouldFlip(attacker, adjacent1, 0);

    assertFalse(shouldFlip);
  }

  @Test
  public void testNoRuleShouldFlip() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card adjacent1 = new CardModel("Adjacent1", 3, 5, 2, 3, COLOR.BLUE);
    Card adjacent2 = new CardModel("Adjacent2", 6, 3, 8, 2, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, adjacent1);
    grid.placeCard(2, 1, adjacent2);

    Level3BattleRuleImpl level3Rule = new Level3BattleRuleImpl(grid, false, false, false, false);
    boolean shouldFlip = level3Rule.shouldFlip(attacker, adjacent1, 0);

    assertFalse(shouldFlip);
  }

  @Test
  public void testReverseFallenAceSame() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 1, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 10, 1, 5, 6, COLOR.BLUE);
    Card adjacent = new CardModel("Adjacent", 4, 2, 1, 3, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, defender);
    grid.placeCard(2, 1, adjacent);

    Level3BattleRuleImpl rule = new Level3BattleRuleImpl(grid, true, true, true, false);

    boolean shouldFlip = rule.shouldFlip(attacker, defender, 0);

    assertTrue(shouldFlip);
  }


  @Test
  public void testFallenAcePlus() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 1, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 9, 5, 1, 6, COLOR.BLUE);
    Card adjacent1 = new CardModel("Adjacent1", 3, 5, 2, 3, COLOR.BLUE);
    Card adjacent2 = new CardModel("Adjacent2", 4, 2, 5, 1, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, defender);
    grid.placeCard(2, 1, adjacent1);
    grid.placeCard(1, 0, adjacent2);

    Level3BattleRuleImpl rule = new Level3BattleRuleImpl(grid, false, true, false, true);

    boolean shouldFlip = rule.shouldFlip(attacker, defender, 3);

    assertTrue(shouldFlip);
  }

  @Test
  public void testShouldThrowExceptionIfAttackerNotOnGrid() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 5, 4, 6, COLOR.BLUE);
    PlusBattleRuleImpl rule = new PlusBattleRuleImpl(grid);

    assertThrows(IllegalArgumentException.class, () -> {
      rule.shouldFlip(attacker, defender, 0);
    });
  }


  @Test
  public void testSameRuleAlone() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card adjacent1 = new CardModel("Adjacent1", 3, 5, 1, 4, COLOR.BLUE);
    Card adjacent2 = new CardModel("Adjacent2", 6, 3, 8, 2, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, adjacent1);
    grid.placeCard(2, 1, adjacent2);

    Level3BattleRuleImpl rule = new Level3BattleRuleImpl(grid, false, false, true, false);

    boolean shouldFlip = rule.shouldFlip(attacker, adjacent1, 0);

    assertTrue("Should flip due to Same Rule", shouldFlip);
  }

  @Test
  public void testReverseRuleWithEqualAttackValues() {
    Card attacker = new CardModel("Attacker", 3, 3, 3, 3, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 3, 3, 3, COLOR.BLUE);

    ReverseBattleRuleImpl reverseRule = new ReverseBattleRuleImpl();

    boolean shouldFlip = reverseRule.shouldFlip(attacker, defender, 0);

    assertFalse(shouldFlip);
  }

  @Test
  public void testFallenAceInvalidDirection() {
    Card attacker = new CardModel("Attacker", 1, 2, 3, 4, COLOR.RED);
    Card defender = new CardModel("Defender", 5, 10, 5, 6, COLOR.BLUE);

    FallenAceBattleRuleImpl fallenAceRule = new FallenAceBattleRuleImpl();
    boolean shouldFlip = fallenAceRule.shouldFlip(attacker, defender, 1);

    assertFalse(shouldFlip);
  }

  @Test
  public void testPlaceCardOutOfBounds() {
    Grid grid = new GameGrid(3, 3);
    Card card = new CardModel("TestCard", 5, 5, 5, 5, COLOR.RED);

    assertThrows(ArrayIndexOutOfBoundsException.class, () -> grid.placeCard(-1, 0, card));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> grid.placeCard(3, 3, card));
  }

  @Test
  public void testCalculateHintWithValidFlips() {
    Grid grid = new GameGrid(3, 3);

    Card redCard = new CardModel("RedCard", 5, 5, 5, 5, COLOR.RED);
    Player redPlayer = new PlayerModel("RedPlayer", COLOR.RED, List.of(redCard));

    MockGameModel model = new MockGameModel(redPlayer, grid, new Player[]{redPlayer});

    grid.placeCard(0, 1, new CardModel("BlueCard1", 2, 3, 4, 1, COLOR.BLUE));
    grid.placeCard(1, 0, new CardModel("BlueCard2", 4, 3, 2, 1, COLOR.BLUE));

    Hint0Impl hint = new Hint0Impl(model);

    int hintResult = hint.calculateHint(1, 1, redCard);

    assertEquals(2, hintResult);
  }

  @Test
  public void testCalculateHintWithNoFlips() {
    Grid grid = new GameGrid(3, 3);

    Card redCard = new CardModel("RedCard", 5, 4, 3, 2, COLOR.RED);
    Player redPlayer = new PlayerModel("RedPlayer", COLOR.RED, List.of(redCard));

    MockGameModel model = new MockGameModel(redPlayer, grid, new Player[]{redPlayer});

    Hint0Impl hint = new Hint0Impl(model);

    int hintResult = hint.calculateHint(1, 1, redCard);

    assertEquals(0, hintResult);
  }

  @Test
  public void testLevel3MultipleRulesFlipping() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 1, 5, 10, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 10, 2, 1, 3, COLOR.BLUE);
    Card adjacent1 = new CardModel("Adjacent1", 2, 5, 3, 4, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, defender);
    grid.placeCard(2, 1, adjacent1);

    Level3BattleRuleImpl rule = new Level3BattleRuleImpl(grid, true, true, true, true);

    boolean shouldFlip = rule.shouldFlip(attacker, defender, 2);

    assertTrue(shouldFlip);
  }

  @Test
  public void testBattleRuleInvalidDirection() {
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 5, 4, 6, COLOR.BLUE);

    FallenAceBattleRuleImpl rule = new FallenAceBattleRuleImpl();

    assertThrows(IllegalArgumentException.class, () -> rule.shouldFlip(attacker, defender, 4));
  }

  @Test
  public void testLevel3NoRules() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 2, 1, 6, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, defender);

    Level3BattleRuleImpl rule = new Level3BattleRuleImpl(grid, false, false, false, false);

    boolean shouldFlip = rule.shouldFlip(attacker, defender, 0);

    assertFalse(shouldFlip);
  }


  @Test
  public void testReverseRuleWithOneDifference() {
    Card defender = new CardModel("Attacker", 4, 3, 5, 2, COLOR.BLUE);
    Card attacker = new CardModel("Defender", 5, 4, 4, 6, COLOR.RED);

    ReverseBattleRuleImpl reverseRule = new ReverseBattleRuleImpl();

    boolean shouldFlip = reverseRule.shouldFlip(attacker, defender, 1);
    assertTrue(shouldFlip);
  }

  @Test
  public void testFallenAceRuleInvalidEdgeCase() {
    Card attacker = new CardModel("Attacker", 1, 2, 3, 4, COLOR.RED);
    Card defender = new CardModel("Defender", 2, 5, 2, 6, COLOR.BLUE);

    FallenAceBattleRuleImpl rule = new FallenAceBattleRuleImpl();
    boolean shouldFlip = rule.shouldFlip(attacker, defender, 1);

    assertFalse(shouldFlip);
  }




  @Test
  public void testHintWithFullGrid() {
    Grid grid = new GameGrid(3, 3);
    Card redCard = new CardModel("RedCard", 5, 5, 5, 5, COLOR.RED);
    Player redPlayer = new PlayerModel("RedPlayer", COLOR.RED, List.of(redCard));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        grid.placeCard(i, j, new CardModel("Filler", 2, 2, 2, 2, COLOR.BLUE));
      }
    }

    MockGameModel model = new MockGameModel(redPlayer, grid, new Player[]{redPlayer});
    Hint0Impl hint = new Hint0Impl(model);

    int hintResult = hint.calculateHint(0, 0, redCard);
    assertEquals(0, hintResult);
  }
  @Test
  public void testHintAtGridEdge() {
    Grid grid = new GameGrid(3, 3);
    Card redCard = new CardModel("RedCard", 5, 5, 5, 5, COLOR.RED);
    grid.placeCard(0, 1, new CardModel("BlueCard", 3, 3, 3, 3, COLOR.BLUE));

    Player redPlayer = new PlayerModel("RedPlayer", COLOR.RED, List.of(redCard));
    MockGameModel model = new MockGameModel(redPlayer, grid, new Player[]{redPlayer});

    Hint0Impl hint = new Hint0Impl(model);

    int hintResult = hint.calculateHint(0, 0, redCard);
    assertTrue(hintResult > 0);
  }

  @Test
  public void testLevel3NoFlipAllRulesEnabled() {
    Grid grid = new GameGrid(3, 3);
    Card attacker = new CardModel("Attacker", 1, 2, 3, 4, COLOR.RED);
    Card defender = new CardModel("Defender", 5, 5, 5, 5, COLOR.BLUE);

    grid.placeCard(1, 1, attacker);
    grid.placeCard(0, 1, defender);

    Level3BattleRuleImpl level3Rule = new Level3BattleRuleImpl(grid, true, true, true, true);

    boolean shouldFlip = level3Rule.shouldFlip(attacker, defender, 0);

    assertFalse(shouldFlip);
  }

  @Test
  public void testReverseRuleShouldNotFlipMinimalValue() {
    Card attacker = new CardModel("Attacker", 1, 2, 3, 4, COLOR.RED);
    Card defender = new CardModel("Defender", 5, 5, 5, 5, COLOR.BLUE);

    ReverseBattleRuleImpl reverseRule = new ReverseBattleRuleImpl();

    boolean shouldFlip = reverseRule.shouldFlip(attacker, defender, 0);

    assertFalse(shouldFlip);
  }



  @Test
  public void testPlaceNullCard() {
    Grid grid = new GameGrid(3, 3);

    assertThrows(IllegalArgumentException.class, () -> grid.placeCard(1, 1, null));
  }




}
