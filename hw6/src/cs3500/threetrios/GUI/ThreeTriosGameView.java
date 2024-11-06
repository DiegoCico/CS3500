package cs3500.threetrios.GUI;

public interface ThreeTriosGameView {
  void setFeatures(Features features);
  void refresh();
  void displayGameOverMessage();
  void displayCurrentPlayer(String player);
  void displayErrorMessage(String message);
}
