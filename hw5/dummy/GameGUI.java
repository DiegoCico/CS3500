import java.awt.*;

import javax.swing.*;

public class GameGUI {
  private JFrame frame;
  private GridPanel panel;
  private Grid grid;
  private GameController gameController;

  public GameGUI(Grid grid, GameController gameController) {
    this.grid = grid;
    this.gameController = gameController;

    initializeFrame();
  }

  private void initializeFrame() {
    frame = new JFrame("Three Trios cs3500.threetrios.Game.Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,400);
    frame.setLayout(new BorderLayout());

    panel = new GridPanel(grid, gameController);
    frame.add(panel, BorderLayout.CENTER);
    frame.setVisible(true);
  }

  public void updateGrid() {
    panel.updateGrid();
  }

  public void showMessage(String message) {
    JOptionPane.showMessageDialog(frame, message);
  }

  public static void launchGame(Grid grid, GameController gameController) {
    SwingUtilities.invokeLater(() -> new GameGUI(grid, gameController));
  }
}
