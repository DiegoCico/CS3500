package cs3500.solored.view.hw02;

import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.SoloRedModel;
import cs3500.solored.view.hw02.RedSevenView;
import cs3500.solored.view.hw02.SimpleRedSevenView;

import java.util.List;
import java.util.Scanner;

/**
 * Main class to run the RedSeven game and display the game state to the user.
 */
public class Main {

  public static void main(String[] args) {
    // Create a new SoloRedModel
    SoloRedModel model = new SoloRedModel();


    List<CardModel> deck = model.getAllCards();
    boolean shuffle = true;
    int numPalettes = 4;
    int handSize = 7;

    model.startGame(deck, shuffle, numPalettes, handSize);

    RedSevenView view = new SimpleRedSevenView(model);

    // Display the current state of the game
    System.out.println(view.toString());

    // Example of interacting with the game via console (for testing)
    Scanner scanner = new Scanner(System.in);
    while (!model.isGameOver()) {
      System.out.println("Enter '1' to draw cards, '2' to play to palette, '3' to play to canvas:");
      int choice = scanner.nextInt();
      switch (choice) {
        case 1:
          model.drawForHand();
          break;
        case 2:
          System.out.println("Enter the palette index and card index in hand:");
          int paletteIdx = scanner.nextInt();
          int cardIdx = scanner.nextInt();
          model.playToPalette(paletteIdx, cardIdx);
          break;
        case 3:
          System.out.println("Enter the card index in hand to play to the canvas:");
          int canvasCardIdx = scanner.nextInt();
          model.playToCanvas(canvasCardIdx);
          break;
        default:
          System.out.println("Invalid choice. Try again.");
      }
      System.out.println(view.toString());
    }

    System.out.println("Game over!");
  }
}
