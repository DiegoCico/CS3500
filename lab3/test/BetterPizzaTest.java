import org.junit.Test;
import betterpizza.AlaCartePizza;
import betterpizza.CheesePizza;
import betterpizza.ObservablePizza;
import betterpizza.VeggiePizza;
import pizza.Crust;
import pizza.Size;
import pizza.ToppingName;
import pizza.ToppingPortion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Additional test cases for the BetterPizza classes.
 */
public class BetterPizzaTest {

  @Test
  public void testAlaCartePizzaNoToppings() {
    ObservablePizza alacarte = new AlaCartePizza.AlaCartePizzaBuilder()
            .crust(Crust.Thin)
            .size(Size.Small)
            .build();

    assertEquals("Base cost only", alacarte.cost(), Size.Small.getBaseCost(), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAlaCartePizzaNoCrust() {
    new AlaCartePizza.AlaCartePizzaBuilder()
            .size(Size.Large)
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAlaCartePizzaNoSize() {
    new AlaCartePizza.AlaCartePizzaBuilder()
            .crust(Crust.Classic)
            .build();
  }

  @Test
  public void testAlaCartePizzaSingleTopping() {
    ObservablePizza alacarte = new AlaCartePizza.AlaCartePizzaBuilder()
            .crust(Crust.Stuffed)
            .size(Size.Medium)
            .addTopping(ToppingName.Cheese, ToppingPortion.Full)
            .build();

    double expectedCost = Size.Medium.getBaseCost() +
            ToppingName.Cheese.getCost() *
                    ToppingPortion.Full.getCostMultiplier();
    assertEquals("Base + 1 topping", expectedCost, alacarte.cost(), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheesePizzaNoCrust() {
    new CheesePizza.CheesePizzaBuilder()
            .size(Size.Medium)
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheesePizzaNoSize() {
    new CheesePizza.CheesePizzaBuilder()
            .crust(Crust.Thin)
            .build();
  }

  @Test
  public void testCheesePizzaBasicCost() {
    CheesePizza cheesePizza = new CheesePizza.CheesePizzaBuilder()
            .crust(Crust.Classic)
            .size(Size.Large)
            .build();

    assertEquals("Base cost only", Size.Large.getBaseCost(), cheesePizza.cost(), 0.001);
  }

  @Test
  public void testVeggiePizzaBasicCost() {
    VeggiePizza veggiePizza = new VeggiePizza.VeggiePizzaBuilder()
            .crust(Crust.Stuffed)
            .size(Size.Small)
            .build();

    assertEquals("Base cost only", Size.Small.getBaseCost(), veggiePizza.cost(), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVeggiePizzaNoCrust() {
    new VeggiePizza.VeggiePizzaBuilder()
            .size(Size.Medium)
            .build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVeggiePizzaNoSize() {
    new VeggiePizza.VeggiePizzaBuilder()
            .crust(Crust.Classic)
            .build();
  }

  @Test
  public void testAlaCartePizzaMultipleToppings() {
    ObservablePizza alacarte = new AlaCartePizza.AlaCartePizzaBuilder()
            .crust(Crust.Classic)
            .size(Size.Large)
            .addTopping(ToppingName.Cheese, ToppingPortion.Full)
            .addTopping(ToppingName.Onion, ToppingPortion.Full)
            .addTopping(ToppingName.GreenPepper, ToppingPortion.LeftHalf)
            .build();

    double expectedCost = Size.Large.getBaseCost()
            + ToppingName.Cheese.getCost() * ToppingPortion.Full.getCostMultiplier()
            + ToppingName.Onion.getCost() * ToppingPortion.Full.getCostMultiplier()
            + ToppingName.GreenPepper.getCost() * ToppingPortion.LeftHalf.getCostMultiplier();

    assertEquals("Base + multiple toppings", expectedCost, alacarte.cost(), 0.001);
  }

  @Test
  public void testAlaCartePizzaHasTopping() {
    ObservablePizza alacarte = new AlaCartePizza.AlaCartePizzaBuilder()
            .crust(Crust.Classic)
            .size(Size.Medium)
            .addTopping(ToppingName.Jalapeno, ToppingPortion.Full)
            .build();

    assertNotNull("Should have Jalapeno", alacarte.hasTopping(ToppingName.Jalapeno));
  }

  @Test
  public void testVeggiePizzaHasNoTopping() {
    VeggiePizza veggiePizza = new VeggiePizza.VeggiePizzaBuilder()
            .crust(Crust.Thin)
            .size(Size.Medium)
            .build();

    assertNull("No Onion topping", veggiePizza.hasTopping(ToppingName.Onion));
  }
}
