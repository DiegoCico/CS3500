package cs3500.threetrios.gui;

import java.awt.*;

import javax.swing.JPanel;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.card.Card;
import cs3500.threetrios.game.ReadOnlyGameModel;
import cs3500.threetrios.player.Player;

/**
 * Implementation of the player-specific view for Player Two (BLUE).
 */
public class PlayerTwoViewImpl extends AbstractPlayerView {

  public PlayerTwoViewImpl(ReadOnlyGameModel model) {
    super(model, COLOR.BLUE);
    GridPanel gridPanel = new GridPanel(model);
    add(gridPanel, BorderLayout.CENTER);
  }



  @Override
  protected void refreshPlayerPanel() {
    Player player = model.getPlayers()[1];
    playerPanel.removeAll();

    for (Card card : player.getHand()) {
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
