package cs3500.threetrios.GUI;

import java.awt.*;
import javax.swing.*;

import cs3500.threetrios.game.ReadOnlyGameModel;

public class GameGUI extends JFrame {
  private final ThreeTriosViewImpl boardPanel;
  private final JLabel statusLabel;

  public GameGUI(ReadOnlyGameModel model) {
    setTitle("Three Trios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(800, 600));

    boardPanel = new ThreeTriosViewImpl(model);
    add(boardPanel, BorderLayout.CENTER);

    statusLabel = new JLabel("Current player: " + model.getCurrentPlayer());
    add(statusLabel, BorderLayout.SOUTH);

    pack();
    setVisible(true);
  }

  /**
   * Refresh the grid display.
   */
  public void refresh() {
    boardPanel.refresh();
  }

  /**
   * Display a message when the game is over.
   *
   * @param message The game over message
   */
  public void showGameOverMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Update the status label to reflect the current player.
   *
   * @param player The name of the current player
   */
  public void updateCurrentPlayer(String player) {
    statusLabel.setText("Current player: " + player);
  }

  /**
   * Show an error message, e.g., when an invalid move is attempted.
   *
   * @param message The error message to show
   */
  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Invalid Move", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Get the board panel to add mouse listeners.
   *
   * @return The board panel
   */
  public ThreeTriosViewImpl getBoardPanel() {
    return boardPanel;
  }
}
