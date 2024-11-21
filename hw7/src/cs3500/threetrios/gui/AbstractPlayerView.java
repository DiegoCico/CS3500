package cs3500.threetrios.gui;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.game.ReadOnlyGameModel;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract base class for player-specific views in the Three Trios game.
 * Implements shared behavior for the ThreeTriosGameView interface.
 */
public abstract class AbstractPlayerView extends JFrame implements ThreeTriosGameView {

  protected final ReadOnlyGameModel model;
  protected final COLOR playerColor;
  protected JPanel playerPanel;
  protected JPanel gridPanel;
  protected Features features;

  /**
   * Constructs a player-specific view.
   *
   * @param model       the shared game model
   * @param playerColor the color representing the player (RED or BLUE)
   */
  public AbstractPlayerView(ReadOnlyGameModel model, COLOR playerColor) {
    this.model = model;
    this.playerColor = playerColor;

    setTitle("Three Trios Game - " + (playerColor == COLOR.RED ? "Player One" : "Player Two"));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(800, 600));

    initializeComponents();
    pack();
    setVisible(true);
  }

  /**
   * Initializes the layout and components for the view.
   */
  protected void initializeComponents() {
    playerPanel = new JPanel();
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
    playerPanel.setBackground(playerColor == COLOR.RED ? Color.PINK : Color.CYAN);
    playerPanel.setPreferredSize(new Dimension(200, getHeight()));

    // Place RED on the left (WEST) and BLUE on the right (EAST)
    if (playerColor == COLOR.RED) {
      add(playerPanel, BorderLayout.WEST);
    } else {
      add(playerPanel, BorderLayout.EAST);
    }

    // Grid panel for the main game in the center
    gridPanel = new JPanel(new GridLayout(model.getGridSize(), model.getGridSize()));
    gridPanel.setPreferredSize(new Dimension(600, 600)); // Center grid
    add(gridPanel, BorderLayout.CENTER);
  }

  /**
   * Sets the features object to allow interaction with the controllers.
   *
   * @param features the Features object that provides the control actions.
   */
  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  /**
   * Refreshes the view to reflect the current game state.
   */
  @Override
  public void refresh() {
    refreshPlayerPanel();
    gridPanel.repaint();
  }

  /**
   * Refreshes the player's hand and updates the UI.
   */
  protected abstract void refreshPlayerPanel();

  /**
   * Displays a message indicating the game is over.
   */
  @Override
  public void displayGameOverMessage() {
    String winnerMessage = model.getWinner();
    String message = winnerMessage.equals("Tie") ? "It's a tie!" : winnerMessage + " wins!";
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Displays the current player's turn in the view title.
   *
   * @param player a String representing the current player's name or details.
   */
  @Override
  public void displayCurrentPlayer(String player) {
    setTitle("Three Trios Game - Current Player: " + player);
  }

  /**
   * Displays an error message in the view.
   *
   * @param message a String representing the error message to display.
   */
  @Override
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
