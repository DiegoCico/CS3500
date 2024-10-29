package cs3500.simon.controller;

import cs3500.simon.model.Simon;
import cs3500.simon.model.ColorGuess;
import cs3500.simon.view.SimonView;
import cs3500.simon.view.ViewFeatures;

/**
 * Controller class that manages interactions between the Simon game model and view.
 */
public class Controller implements ViewFeatures {
  private final Simon model;
  private final SimonView view;

  /**
   * Constructs a Controller with the specified model and view.
   * Sets this controller as a listener for view events.
   *
   * @param model the Simon game model
   * @param view the view to display the game
   */
  public Controller(Simon model, SimonView view) {
    this.model = model;
    this.view = view;
    this.view.addFeatureListener(this);
  }

  /**
   * Starts the game by displaying the view.
   */
  public void goController() {
    this.view.display(true);
  }

  /**
   * Handles a color selection event from the view.
   * Checks if the selected color matches the next color in the sequence.
   * If successful, advances the view; otherwise, shows an error.
   *
   * @param s the selected color
   */
  @Override
  public void selectedColor(ColorGuess s) {
    boolean success = this.model.enterNextColor(s);
    if (success) {
      this.view.advance();
    } else {
      this.view.error();
    }
  }

  /**
   * Quits the game by exiting the application.
   */
  @Override
  public void quit() {
    System.exit(0);
  }
}
