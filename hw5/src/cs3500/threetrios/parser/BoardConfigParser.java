package cs3500.threetrios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardConfigParser {

  /**
   * Parses a combined configuration file to create a Grid and a list of Cards.
   *
   * @param filePath the path to the board configuration file
   * @return a GameModel initialized with parsed Grid and Cards
   * @throws FileNotFoundException if the file cannot be found
   */
  public static GameModel parseBoardConfig(String filePath) throws FileNotFoundException {
    File file = new File(filePath);
    Scanner scanner = new Scanner(file);

    Grid grid = null;
    List<Card> cards = new ArrayList<>();
    int rows = 0, cols = 0;
    boolean parsingGridLayout = false;
    boolean parsingCardList = false;
    int currentRow = 0;

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine().trim();

      // Ignore comments and empty lines
      if (line.startsWith("#") || line.isEmpty()) continue;

      // Detect sections
      if (line.equals("GRID_LAYOUT")) {
        parsingGridLayout = true;
        continue;
      } else if (line.equals("CARD_LIST")) {
        parsingGridLayout = false;
        parsingCardList = true;
        continue;
      }

      if (line.startsWith("GRID_SIZE")) {
        String[] size = line.split(" ");
        rows = Integer.parseInt(size[1]);
        cols = Integer.parseInt(size[2]);
        grid = new GameGrid(rows, cols);
        continue;
      }

      // Parse grid layout
      if (parsingGridLayout && currentRow < rows) {
        for (int col = 0; col < line.length(); col++) {
          char cellType = line.charAt(col);
          if (cellType == 'C') {
            grid.addCardCell(currentRow, col);
          } else if (cellType == 'X') {
            grid.addHole(currentRow, col);
          }
        }
        currentRow++;
      }

      // Parse card list
      if (parsingCardList) {
        String[] cardData = line.split(" ");
        if (cardData.length != 5) {
          System.err.println("Invalid card data format: " + line);
          continue;
        }
        try {
          String name = cardData[0];
          int north = parseValue(cardData[1]);
          int south = parseValue(cardData[2]);
          int east = parseValue(cardData[3]);
          int west = parseValue(cardData[4]);

          Card card = new CardModel(name, north, south, east, west, null);
          cards.add(card);
        } catch (IllegalArgumentException e) {
          System.err.println("Error parsing card: " + line + " - " + e.getMessage());
        }
      }
    }

    scanner.close();

    if (grid == null) {
      throw new IllegalStateException("Invalid grid configuration in board config file.");
    }

    return new GameModel(grid, cards);
  }

  /**
   * Converts a string value to an integer, treating "A" as 10.
   *
   * @param value the string to parse
   * @return the integer value
   */
  private static int parseValue(String value) {
    return value.equals("A") ? 10 : Integer.parseInt(value);
  }
}
