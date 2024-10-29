import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
  private GridButton[][] button;
  private Grid grid;
  private GameController gameController;

  public GridPanel(Grid grid, GameController gameController) {
    this.grid = grid;
    this.gameController = gameController;

    setLayout(new GridLayout(grid.getRows(), grid.getCols()));

    button = new GridButton[grid.getRows()][grid.getCols()];

    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        GridButton buttons = new GridButton(grid, row, col, gameController);
        button[row][col] = buttons;
        add(buttons);
      }
    }
  }


  public void updateGrid() {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getCols(); col++) {
        button[row][col].updateButton();
      }
    }
  }
}
