package betterpizza;

import pizza.ToppingName;
import pizza.ToppingPortion;

/**
 * An interface representing a pizza that can be observed
 * for its properties such as cost and toppings.
 */
public interface ObservablePizza {

  /**
   * Retrieves the cost of this pizza.
   *
   * @return the cost of this pizza in monetary format (MM.CC), where MM represents the dollars
   *         and CC represents the cents.
   */
  double cost();

  /**
   * Checks whether the specified topping is present on this pizza. If the topping is present,
   * returns the portion of the topping.
   *
   * @param name the name of the topping to check for
   * @return the portion of the specified topping on this pizza, or {@code null} if the topping
   *         is not present on this pizza
   */
  ToppingPortion hasTopping(ToppingName name);

}
