package betterpizza;

import pizza.Crust;
import pizza.Size;
import pizza.ToppingName;
import pizza.ToppingPortion;

/**
 * Represents a veggie pizza with predefined toppings.
 */
public class VeggiePizza implements ObservablePizza {
  private Crust crust;
  private Size size;

  /**
   * Creates a VeggiePizza with the specified size and crust.
   *
   * @param size the size of the pizza
   * @param crust the crust type of the pizza
   */
  public VeggiePizza(Size size, Crust crust) {
    this.crust = crust;
    this.size = size;
  }

  /**
   * Veggie pizza has predefined toppings, so this always returns null.
   *
   * @param name the name of the topping (not applicable for veggie pizza)
   * @return null as no extra toppings are added
   */
  @Override
  public ToppingPortion hasTopping(ToppingName name) {
    return null;
  }

  /**
   * Calculates the cost of the veggie pizza based on its size.
   *
   * @return the cost of the pizza
   */
  @Override
  public double cost() {
    return this.size.getBaseCost();
  }

  /**
   * Builder class to create a VeggiePizza.
   */
  public static class VeggiePizzaBuilder extends PizzaBuilder<VeggiePizzaBuilder> {

    /**
     * Returns the builder instance for method chaining.
     *
     * @return the builder instance
     */
    @Override
    protected VeggiePizzaBuilder returnBuilder() {
      return this;
    }

    /**
     * Builds and returns a VeggiePizza instance.
     *
     * @return a new VeggiePizza
     * @throws IllegalArgumentException if the size or crust is not set
     */
    public VeggiePizza build() {
      if (size == null) {
        throw new IllegalArgumentException("size cannot be null");
      }
      if (crust == null) {
        throw new IllegalArgumentException("crust cannot be null");
      }
      return new VeggiePizza(size, crust);
    }
  }
}
