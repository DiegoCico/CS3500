package cs3500.threetrios.gui;

import java.awt.*;

import javax.swing.JPanel;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.ReadOnlyGameModel;
import cs3500.threetrios.player.Player;

/**
 * Implementation of the player-specific view for Player One (RED).
 */
public class PlayerOneViewImpl extends AbstractPlayerView {

  public PlayerOneViewImpl(ReadOnlyGameModel model) {
    super(model, COLOR.RED);
    GridPanel gridPanel = new GridPanel(model);
    add(gridPanel, BorderLayout.CENTER);
  }

  @Override
  protected void refreshPlayerPanel() {
    System.out.println("Refreshing player panel for: " + playerColor);
    Player player = model.getPlayers()[playerColor == COLOR.RED ? 0 : 1];
    playerPanel.removeAll();

    for (Card card : player.getHand()) {
      System.out.println("Adding card: " + card);
      JPanel cardPanel = new JPanel();
      cardPanel.setPreferredSize(new java.awt.Dimension(60, 60));
      cardPanel.setBackground(playerColor == COLOR.RED ? Color.PINK : Color.CYAN);
      cardPanel.add(new javax.swing.JLabel(card.getColor().toString()));
      playerPanel.add(cardPanel);
    }

    playerPanel.revalidate();
    playerPanel.repaint();
  }

}
