package cs3500.threetrios.GUI;

import javax.swing.*;
import java.awt.*;

import cs3500.threetrios.game.ReadOnlyGameModel;

public class GridPanel extends JPanel {
  private final ReadOnlyGameModel model;

  public GridPanel(ReadOnlyGameModel model) {
    this.model = model;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawGrid(g);
    drawCards(g);
    drawGameStatus(g);
  }

  private void drawGrid(Graphics g) {
    int size = Math.min(getWidth(), getHeight()) / model.getGridSize(); // Calculate cell size properly
    for (int i = 1; i < model.getGridSize(); i++) {
      g.setColor(Color.GRAY);
      g.drawLine(0, i * size, getWidth(), i * size);
      g.drawLine(i * size, 0, i * size, getHeight());
    }
  }

  private void drawCards(Graphics g) {
    int size = Math.min(getWidth(), getHeight()) / model.getGridSize();
    for (int row = 0; row < model.getGridSize(); row++) {
      for (int col = 0; col < model.getGridSize(); col++) {
        String card = model.getCardAt(row, col);
        if (card != null) {
          if (card.equals("Red")) {
            g.setColor(new Color(255, 182, 193)); // Light pink for Red player
          } else {
            g.setColor(new Color(173, 216, 230)); // Light blue for Blue player
          }
          g.fillRect(col * size, row * size, size, size);
          g.setColor(Color.BLACK);
          g.drawRect(col * size, row * size, size, size);
          g.drawString(card, col * size + size / 2 - 5, row * size + size / 2 + 5);
        }
      }
    }
  }


  private void drawGameStatus(Graphics g) {
    g.setColor(Color.BLACK);
    if (model.isGameOver()) {
      String status = model.getWinner().equals("Tie") ?
              "It' a tie" : model.getWinner() + " wins!";
      g.drawString(status, getWidth() / 2, getHeight() / 2);
    } else {
      g.drawString("Current player: " + model.getCurrentPlayer().getName(),
              getWidth() / 2, getHeight() / 2);
    }
  }

}
