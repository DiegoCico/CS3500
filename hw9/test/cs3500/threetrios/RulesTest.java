package cs3500.threetrios;

import org.junit.Test;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.game.GameGrid;
import cs3500.threetrios.game.Grid;
import cs3500.threetrios.level1.FallenAceBattleRuleImpl;
import cs3500.threetrios.level1.ReverseBattleRuleImpl;
import cs3500.threetrios.level2.PlusBattleRuleImpl;
import cs3500.threetrios.level2.SameBattleRuleImpl;
import cs3500.threetrios.level3.Level3BattleRuleImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for extra credit.
 */
public class RulesTest {
  @Test
  public void testReverseRuleShouldFlip() {
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 2, 1, 6, COLOR.BLUE);

    ReverseBattleRuleImpl reverseRule = new ReverseBattleRuleImpl();
    boolean shouldFlip = reverseRule.shouldFlip(attacker, defender, 1);

    assertTrue(shouldFlip);
  }

  @Test
  public void testReverseRuleShouldNotFlip() {
    Card attacker = new CardModel("Attacker", 5, 4, 3, 7, COLOR.RED);
    Card defender = new CardModel("Defender", 3, 2, 1, 6, COLOR.BLUE);

    ReverseBattleRuleImpl reverseRule = new ReverseBattleRuleImpl();
    boolean shouldFlip = reverseRule.shouldFlip(attacker, defender, 3);

    assertFalse(shouldFlip);
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
  public void testFallenAceRuleShouldNotFlip() {
    Card attacker = new CardModel("Attacker", 5, 4, 3, 2, COLOR.RED);
    Card defender = new CardModel("Defender", 9, 5, 1, 6, COLOR.BLUE);

    FallenAceBattleRuleImpl fallenAceRule = new FallenAceBattleRuleImpl();
    boolean shouldFlip = fallenAceRule.shouldFlip(attacker, defender, 0);

    assertFalse(shouldFlip);
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

}
