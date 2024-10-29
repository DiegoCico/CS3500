import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridButton extends JButton {
  private int row;
  private int col;
  private Grid grid;
  private GameController gameController;

  public GridButton(Grid grid,int row, int col, GameController gameController) {
    this.row = row;
    this.col = col;
    this.grid = grid;
    this.gameController = gameController;

    if(grid.getCellType(row, col) == Cell.CellType.HOLE) {
      setBackground(Color.BLACK);
      setEnabled(false);
    } else {
      //hehe blackpink
      setBackground(Color.PINK);
    }
      addActionListener(new ActionListener() {
        //wtf is this syntax
        @Override
        public void actionPerformed(ActionEvent e) {
          handleButtonClicked();
        }
      });
  }

  private void handleButtonClicked() {
    gameController.handleMove(row, col);

  }

  public void updateButton() {
    if (grid.isEmpty(row, col)) {
      Card card = grid.getCard(row, col);
      setText(card.toString());
      setEnabled(false);
    }
  }

}
