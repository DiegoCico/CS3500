package betterpizza;

import pizza.Crust;
import pizza.Size;
import pizza.ToppingName;
import pizza.ToppingPortion;

/**
 * Represents a basic cheese pizza with no additional toppings.
 */
public class CheesePizza implements ObservablePizza {
  private Crust crust;
  private Size size;

  /**
   * Creates a CheesePizza with the specified crust and size.
   *
   * @param crust the crust type of the pizza
   * @param size the size of the pizza
   */
  public CheesePizza(Crust crust, Size size) {
    this.crust = crust;
    this.size = size;
  }

  /**
   * Cheese pizza has no additional toppings, so this always returns null.
   *
   * @param name the name of the topping (not applicable for cheese pizza)
   * @return null as there are no additional toppings
   */
  @Override
  public ToppingPortion hasTopping(ToppingName name) {
    return null;
  }

  /**
   * Calculates the cost of the cheese pizza based on its size.
   *
   * @return the cost of the pizza
   */
  @Override
  public double cost() {
    return this.size.getBaseCost();
  }

  /**
   * Builder class to create a CheesePizza.
   */
  public static class CheesePizzaBuilder extends PizzaBuilder<CheesePizzaBuilder> {

    /**
     * Returns the builder instance for method chaining.
     *
     * @return the builder instance
     */
    @Override
    protected CheesePizzaBuilder returnBuilder() {
      return this;
    }

    /**
     * Builds and returns a CheesePizza instance.
     *
     * @return a new CheesePizza
     * @throws IllegalArgumentException if the size or crust is not set
     */
    public CheesePizza build() {
      if (size == null) {
        throw new IllegalArgumentException("size cannot be null");
      }
      if (crust == null) {
        throw new IllegalArgumentException("crust cannot be null");
      }
      return new CheesePizza(crust, size);
    }
  }
}
