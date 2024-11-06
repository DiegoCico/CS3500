package cs3500.threetrios.GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.ReadOnlyGameModel;
import cs3500.threetrios.player.Player;

public class ThreeTriosViewImpl extends JFrame implements ThreeTriosGameView {

  private final ReadOnlyGameModel model;
  private final GridPanel gridPanel;
  private final JPanel redPlayerPanel;
  private final JPanel bluePlayerPanel;
  private Features features;

  public ThreeTriosViewImpl(ReadOnlyGameModel model) {
    this.model = model;
    setTitle("Three Trios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    gridPanel = new GridPanel(model);
    gridPanel.addMouseListener(new BoardClickListener());
    add(gridPanel, BorderLayout.CENTER);

    redPlayerPanel = createPlayerPanel(COLOR.RED);
    add(redPlayerPanel, BorderLayout.WEST);

    bluePlayerPanel = createPlayerPanel(COLOR.BLUE);
    add(bluePlayerPanel, BorderLayout.EAST);

    setPreferredSize(new Dimension(800, 600));
    pack();
    setVisible(true);
  }

  /**
   * Creates a panel for a player with the specified color and title.
   *
   * @param player The player color, either "Red" or "Blue".
   * @return The initialized player panel.
   */
  private JPanel createPlayerPanel(COLOR playerColor) {
    JPanel playerPanel = new JPanel();
    playerPanel.setPreferredSize(new Dimension(100, 600));
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

    Color backgroundColor = playerColor == COLOR.RED ? Color.PINK : Color.CYAN;
    playerPanel.setBackground(backgroundColor);

    String playerName = playerColor == COLOR.RED ? "Red" : "Blue";
    JLabel playerLabel = new JLabel(playerName + " Player");
    playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    playerPanel.add(playerLabel);

    Player player = playerColor == COLOR.RED ? model.getPlayers()[0] : model.getPlayers()[1];

    for (Card card : player.getHand()) {
      JLabel cardLabel = new JLabel(card.toString());
      cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      playerPanel.add(cardLabel);
    }

    return playerPanel;
  }


  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  @Override
  public void refresh() {
    gridPanel.repaint();
    revalidate();
    repaint();
  }

  @Override
  public void displayGameOverMessage() {
    String message = model.getWinner().equals("Tie") ?
            "It's a tie!" : model.getWinner() + " wins!";
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }

  public void displayCurrentPlayer(String player) {
    setTitle("Three Trios Game - Current Player " + player);
  }

  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private class BoardClickListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
      int cellSize = gridPanel.getWidth() / model.getGridSize();
      int row = e.getY() / cellSize;
      int col = e.getX() / cellSize;
      features.handleCellClick(row, col);
    }
  }
}
