package cs3500.simon;

import cs3500.simon.controller.Controller;
import cs3500.simon.model.Simon;
import cs3500.simon.model.SimonSays;
import cs3500.simon.view.SimonView;
import cs3500.simon.view.SimpleSimonView;

/**
 * Entry point for the Simon game application.
 * Initializes the game model, view, and controller, and starts the game.
 */
public class SimonGame {

  /**
   * Main method to run the Simon game.
   * Configures the game model, view, and controller,
   * then starts the game loop.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    Simon model = new SimonSays.Builder().build(); // Feel free to customize this as desired
    SimonView view = new SimpleSimonView(model);
    Controller controller = new Controller(model, view);
    controller.go();
  }
}
