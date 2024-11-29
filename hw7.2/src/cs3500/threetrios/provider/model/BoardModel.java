package cs3500.threetrios.provider.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.threetrios.provider.controller.ModelFeatures;

/**
 * Represents a mutable board for the ThreeTrios game.
 */
public class BoardModel implements Board {
  private final Slot[][] grid;
  private final int width;
  private final int height;
  private final List<Player> players;
  private final Map<Player, List<Card>> hands;
  private final Map<Player, Integer> scores;
  private int currentPlayerIndex;
  private boolean gameStarted;
  private boolean gameOver;

  private final List<ModelFeatures> listeners;

  /**
   * Constructs a BoardModel with the given width, height, players, and their initial hands.
   *
   * @param width  the width of the grid
   * @param height the height of the grid
   * @param players the list of players in the game
   * @param hands  the initial hands for each player
   */
  public BoardModel(int width, int height, List<Player> players, Map<Player, List<Card>> hands) {
    if (width <= 0 || height <= 0 || players == null || hands == null) {
      throw new IllegalArgumentException("Invalid board dimensions or player data");
    }

    this.width = width;
    this.height = height;
    this.grid = new Slot[width][height];
    this.players = new ArrayList<>(players);
    this.hands = new HashMap<>(hands);
    this.scores = new HashMap<>();
    for (Player player : players) {
      scores.put(player, 0);
    }
    this.currentPlayerIndex = 0;
    this.gameStarted = false;
    this.gameOver = false;
    this.listeners = new ArrayList<>();
  }

  @Override
  public void playToBoard(int handIndex, int x, int y) {
    if (!gameStarted || gameOver) {
      throw new IllegalStateException("Game has not started or is already over");
    }

    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Coordinates out of bounds");
    }

    Player currentPlayer = players.get(currentPlayerIndex);
    List<Card> hand = hands.get(currentPlayer);

    if (handIndex < 0 || handIndex >= hand.size()) {
      throw new IllegalArgumentException("Invalid hand index");
    }

    Slot targetSlot = grid[x][y];
    if (targetSlot != null && !targetSlot.canPlayCard()) {
      throw new IllegalArgumentException("Cannot play card to this slot");
    }

    Card playedCard = hand.remove(handIndex);
    grid[x][y] = playedCard;

    int flips = possibleCardsFlipped(playedCard, x, y);
    scores.put(currentPlayer, scores.get(currentPlayer) + flips + 1);

    endTurn();
  }

  private void endTurn() {
    if (isGameOver()) {
      gameOver = true;
    } else {
      currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
  }

  @Override
  public void startGame() {
    if (gameStarted || gameOver || players.size() < 2) {
      throw new IllegalStateException("Game cannot be started");
    }
    gameStarted = true;
  }

  @Override
  public void addListener(ModelFeatures features) {
    if (features == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    if (gameStarted || listeners.size() >= players.size()) {
      throw new IllegalStateException("Cannot add listener");
    }
    listeners.add(features);
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      return false;
    }
    if (players.size() < 2) {
      return false;
    }

    return gameOver;
  }

  @Override
  public Player gameWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException("Game is not over");
    }

    Player winner = null;
    int maxScore = -1;

    for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
      if (entry.getValue() > maxScore) {
        maxScore = entry.getValue();
        winner = entry.getKey();
      }
    }

    return winner;
  }

  @Override
  public Slot[][] getGrid() {
    return grid.clone();
  }

  @Override
  public int gameWidth() {
    return width;
  }

  @Override
  public int gameHeight() {
    return height;
  }

  @Override
  public Slot getCoord(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Coordinates out of bounds");
    }
    return grid[x][y];
  }

  @Override
  public Player getCellOwner(int x, int y) {
    Slot slot = getCoord(x, y);
    return slot != null ? slot.getSlotOwner() : null;
  }

  @Override
  public boolean isMoveLegal(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height && (grid[x][y] == null || grid[x][y].canPlayCard());
  }

  @Override
  public int score(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    return scores.getOrDefault(player, 0);
  }

  @Override
  public int possibleCardsFlipped(Card card, int x, int y) {
    if (card == null || x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Invalid card or coordinates");
    }
      int flips = 0;

    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        if (dx == 0 && dy == 0) continue;
        int nx = x + dx;
        int ny = y + dy;
        if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
          Slot neighbor = grid[nx][ny];
          if (neighbor != null && neighbor.getSlotOwner() != card.getSlotOwner()) {
            flips++;
          }
        }
      }
    }

    return flips;
  }

  @Override
  public Player curPlayer() {
    return players.get(currentPlayerIndex);
  }

  @Override
  public List<Card> getHand(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    return hands.getOrDefault(player, List.of());
  }
}
