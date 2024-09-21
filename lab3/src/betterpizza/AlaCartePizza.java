package betterpizza;

import java.util.Map;

import pizza.Crust;
import pizza.Size;
import pizza.ToppingName;
import pizza.ToppingPortion;

/**
 * Represents a customizable ala carte pizza where you can select crust, size, and toppings.
 */
public class AlaCartePizza implements ObservablePizza {
  private Crust crust;
  private Size size;
  private Map<ToppingName, ToppingPortion> toppings;

  /**
   * Creates an AlaCartePizza with a specified crust, size, and set of toppings.
   *
   * @param crust the crust type of the pizza
   * @param size the size of the pizza
   * @param toppings the toppings and their portions for the pizza
   */
  public AlaCartePizza(Crust crust, Size size, Map<ToppingName, ToppingPortion> toppings) {
    this.crust = crust;
    this.size = size;
    this.toppings = toppings;
  }

  /**
   * Checks if the pizza has a certain topping and returns the portion if it exists.
   *
   * @param name the name of the topping to check
   * @return the portion of the topping if it exists, null otherwise
   */
  @Override
  public ToppingPortion hasTopping(ToppingName name) {
    return this.toppings.getOrDefault(name, null);
  }

  /**
   * Calculates the total cost of the pizza based on the size and selected toppings.
   *
   * @return the total cost of the pizza
   */
  @Override
  public double cost() {
    double cost = 0.0;
    for (Map.Entry<ToppingName, ToppingPortion> item : this.toppings.entrySet()) {
      cost += item.getKey().getCost() * item.getValue().getCostMultiplier();
    }
    return cost + this.size.getBaseCost();
  }

  /**
   * Builder class to create an AlaCartePizza.
   */
  public static class AlaCartePizzaBuilder extends PizzaBuilder<AlaCartePizzaBuilder> {

    /**
     * Returns the builder instance for method chaining.
     *
     * @return the builder instance
     */
    @Override
    protected AlaCartePizzaBuilder returnBuilder() {
      return this;
    }

    /**
     * Builds and returns an AlaCartePizza instance.
     *
     * @return a new AlaCartePizza
     * @throws IllegalArgumentException if the size, crust, or toppings are not set
     */
    public AlaCartePizza build() {
      if (size == null) {
        throw new IllegalArgumentException("size cannot be null");
      }
      if (crust == null) {
        throw new IllegalArgumentException("crust cannot be null");
      }
      if (toppings == null) {
        throw new IllegalArgumentException("toppings cannot be null");
      }
      return new AlaCartePizza(crust, size, toppings);
    }
  }
}
