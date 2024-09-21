package betterpizza;

import java.util.HashMap;
import java.util.Map;

import pizza.Crust;
import pizza.Size;
import pizza.ToppingName;
import pizza.ToppingPortion;

/**
 * Abstract class representing a general pizza builder.
 *
 * @param <T> the specific type of PizzaBuilder being used
 */
public abstract class PizzaBuilder<T extends PizzaBuilder<T>> {
  protected Size size;
  protected Crust crust;
  protected Map<ToppingName, ToppingPortion> toppings = new HashMap<>();

  /**
   * Sets the size of the pizza.
   *
   * @param size the size of the pizza
   * @return the builder instance
   */
  public T size(Size size) {
    this.size = size;
    return returnBuilder();
  }

  /**
   * Sets the crust of the pizza.
   *
   * @param crust the crust of the pizza
   * @return the builder instance
   */
  public T crust(Crust crust) {
    this.crust = crust;
    return returnBuilder();
  }

  /**
   * Adds a topping to the pizza.
   *
   * @param name the name of the topping
   * @param portion the portion of the topping
   * @return the builder instance
   */
  public T addTopping(ToppingName name, ToppingPortion portion) {
    this.toppings.put(name, portion);
    return returnBuilder();
  }

  /**
   * Abstract method that returns the specific builder instance for chaining.
   *
   * @return the builder instance
   */
  protected abstract T returnBuilder();
}
