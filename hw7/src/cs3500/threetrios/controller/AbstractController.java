package cs3500.threetrios.controller;

import cs3500.threetrios.gui.AbstractPlayerView;
import cs3500.threetrios.gui.Features;
import cs3500.threetrios.game.Game;

/**
 * Represents an abstract controller for the Three Trios game.
 * This class provides a base for specific controller implementations such as
 * human and AI controllers. It defines common fields and methods for managing
 * interactions between the model, view, and controller features.
 */
public abstract class AbstractController implements Features {
  protected final Game model;
  protected final AbstractPlayerView view;

  /**
   * Constructs an AbstractController with the specified model and view.
   *
   * @param model the game model
   * @param view  the game view
   */
  public AbstractController(Game model, AbstractPlayerView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Abstract method for making a move.
   */
  public abstract void makeMove();

  /**
   * Handle a cell click.
   *
   * @param row the row of the clicked cell
   * @param col the column of the clicked cell
   */
  public abstract void handleCellClick(int row, int col);

  /**
   * Handle card selection.
   *
   * @param cardIndex the index of the selected card
   */
  public abstract void handleCardSelection(int cardIndex);


  /**
   * Handles the AI's move when it is the AI's turn.
   * Processes the logic for the AI to select and place a card.
   */
  public abstract void handleAIMove();

}
