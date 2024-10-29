package cs3500.tictactoe;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


/**
 * This is the panel, uses the mouse variables.
 */
public class TTTPanel extends JPanel {

  private TicTacToeController features;

    /**
     * This is the constructor for the class.
     */
  public TTTPanel() {
    this.addMouseListener(new TTTClickListener());
  }

  /**
   * This is a clicker button.
   * @param features incoorporates the TicTacToeController
   */
  public void addClickListener(TicTacToeController features) {
    this.features = features;
    this.addMouseListener(new TTTClickListener());
  }

  private class TTTClickListener implements MouseListener {

    /**
     * when mouse is clicked.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
      System.err.println(e.getX() + " " + e.getY());
    }

    /**
     * when mouse is unreleased.
     *
     * @param e the event to be processed.
     */
    @Override
    public void mousePressed(MouseEvent e) {
      // nothing here.
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
      // nothing here.
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
      // nothing here.
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
      // nothing here.
    }
  }
}
