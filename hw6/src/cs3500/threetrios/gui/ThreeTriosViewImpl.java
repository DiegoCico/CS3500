package cs3500.threetrios.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.ReadOnlyGameModel;
import cs3500.threetrios.player.Player;

/**
 * Implementation of the Three Trios game view using Java Swing.
 * Responsible for rendering the game grid, player hands,
 * and handling user input events.
 */
public class ThreeTriosViewImpl extends JFrame implements ThreeTriosGameView {

  private final ReadOnlyGameModel model;
  private final GridPanel gridPanel;
  private final JPanel redPlayerPanel;
  private final JPanel bluePlayerPanel;
  private Features features;
  private int selectedCardIndex = -1;

  /**
   * Constructs a ThreeTriosViewImpl with the specified game model.
   *
   * @param model the game model
   */
  public ThreeTriosViewImpl(ReadOnlyGameModel model) {
    this.model = model;
    setTitle("Three Trios Game");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(0, 0));

    gridPanel = new GridPanel(model);
    gridPanel.addMouseListener(new BoardClickListener());
    add(gridPanel, BorderLayout.CENTER);

    redPlayerPanel = createPlayerPanel(COLOR.RED);
    bluePlayerPanel = createPlayerPanel(COLOR.BLUE);

    redPlayerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    bluePlayerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

    add(redPlayerPanel, BorderLayout.WEST);
    add(bluePlayerPanel, BorderLayout.EAST);

    setPreferredSize(new Dimension(800, 600));
    pack();
    setVisible(true);
  }

  /**
   * Creates a panel displaying the player's hand of cards.
   *
   * @param playerColor the color of the player
   * @return JPanel representing the player's hand
   */
  private JPanel createPlayerPanel(COLOR playerColor) {
    JPanel playerPanel = new JPanel();
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
    playerPanel.setPreferredSize(new Dimension(80, getHeight()));

    Color backgroundColor = playerColor == COLOR.RED ? Color.PINK : Color.CYAN;
    playerPanel.setBackground(backgroundColor);

    JLabel playerLabel = new JLabel(playerColor == COLOR.RED ? "Red Player" : "Blue Player");
    playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    playerPanel.add(playerLabel);

    updatePlayerPanel(playerColor, playerPanel);

    return playerPanel;
  }

  /**
   * Returns the display value of a card side, converting 10 to "A".
   *
   * @param value the side value of the card
   * @return "A" if the value is 10, otherwise the value as a string
   */
  private String getDisplayValue(int value) {
    return value == 10 ? "A" : String.valueOf(value);
  }

  /**
   * Gets the color corresponding to the card's color type.
   *
   * @param color the card color
   * @return Color object for the card panel background
   */
  private Color getCardColor(COLOR color) {
    switch (color) {
      case RED:
        return Color.PINK;
      case BLUE:
        return Color.CYAN;
      default:
        return Color.WHITE;
    }
  }

  /**
   * Setting the features in the view.
   * @param features the Features object that provides the control actions.
   */
  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  @Override
  public void refresh() {
    updatePlayerPanel(COLOR.RED, redPlayerPanel);
    updatePlayerPanel(COLOR.BLUE, bluePlayerPanel);
    gridPanel.repaint();
    revalidate();
    repaint();
  }


  /**
   * Updates the player panel with the latest cards and highlights the selected card
   * for the current player only.
   *
   * @param playerColor the color of the player
   * @param playerPanel the panel representing the player's hand
   */
  private void updatePlayerPanel(COLOR playerColor, JPanel playerPanel) {
    playerPanel.removeAll();

    JLabel playerLabel = new JLabel(playerColor == COLOR.RED ? "Red Player" : "Blue Player");
    playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    playerPanel.add(playerLabel);

    Player player = playerColor == COLOR.RED ? model.getPlayers()[0] : model.getPlayers()[1];
    int cardIndex = 0;
    boolean isCurrentPlayer = model.getCurrentPlayer().getColor() == playerColor;

    for (Card card : player.getHand()) {
      JPanel cardPanel = new JPanel(new BorderLayout());
      cardPanel.setPreferredSize(new Dimension(60, 60));
      cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

      if (isCurrentPlayer && cardIndex == selectedCardIndex) {
        cardPanel.setBackground(Color.GRAY);
      } else {
        cardPanel.setBackground(getCardColor(card.getColor()));
      }

      cardPanel.add(new JLabel(getDisplayValue(card.getNorth()),
              SwingConstants.CENTER), BorderLayout.NORTH);
      cardPanel.add(new JLabel(getDisplayValue(card.getSouth()),
              SwingConstants.CENTER), BorderLayout.SOUTH);
      cardPanel.add(new JLabel(getDisplayValue(card.getEast()),
              SwingConstants.CENTER), BorderLayout.EAST);
      cardPanel.add(new JLabel(getDisplayValue(card.getWest()),
              SwingConstants.CENTER), BorderLayout.WEST);
      cardPanel.add(new JLabel(card.getColor().toString(),
              SwingConstants.CENTER), BorderLayout.CENTER);

      cardPanel.addMouseListener(new DeckClickListener(cardIndex, isCurrentPlayer));
      playerPanel.add(cardPanel);

      cardIndex++;
    }

    playerPanel.revalidate();
    playerPanel.repaint();
  }

  /**
   * Displays the game over message.
   */
  @Override
  public void displayGameOverMessage() {
    String winnerMessage = model.getWinner();
    String message = winnerMessage.equals("Tie") ? "It's a tie!" : winnerMessage + " wins!";
    JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }

  public void setSelectedCardIndex(int index) {
    this.selectedCardIndex = index;
  }

  /**
   * Displays the current player in the window title.
   *
   * @param player the name of the current player
   */
  public void displayCurrentPlayer(String player) {
    setTitle("Three Trios Game - Current Player " + player);
  }

  /**
   * Displays an error message dialog.
   *
   * @param message the error message to display
   */
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Mouse adapter for handling board cell clicks.
   */
  private class BoardClickListener extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
      int cellSize = gridPanel.getWidth() / model.getGridSize();
      int row = e.getY() / cellSize;
      int col = e.getX() / cellSize;
      features.handleCellClick(row, col);
    }
  }

  /**
   * Mouse adapter for handling card selection in the player's hand.
   */
  private class DeckClickListener extends MouseAdapter {
    private final int cardIndex;
    private final boolean isCurrentPlayer;

    /**
     * Constructs a DeckClickListener for a card at a specific index.
     *
     * @param cardIndex the index of the card in the player's hand
     * @param isCurrentPlayer if true, the player can select the card
     */
    public DeckClickListener(int cardIndex, boolean isCurrentPlayer) {
      this.cardIndex = cardIndex;
      this.isCurrentPlayer = isCurrentPlayer;
    }

    /**
     * detects if the mouse is clicked.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      if (isCurrentPlayer) {
        selectedCardIndex = this.cardIndex;
        features.handleCardSelection(cardIndex);
        refresh();
      }
    }
  }
}