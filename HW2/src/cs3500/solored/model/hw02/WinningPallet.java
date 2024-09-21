package cs3500.solored.filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a winning pallet selector for a card game.
 * Depending on the color of a "canvas" card, it evaluates different conditions
 * across a list of card pallets and returns the index of the winning pallet.
 */
public class WinningPallet {

  /**
   * Represents card colors and their priority values.
   * Colors are represented as R (4), O (3), B (2), I (1), V (0).
   * The numbers are used to represent which color is worth more.
   */
  enum COLOR {
    R(4),
    O(3),
    B(2),
    I(1),
    V(0);

    private final int value;

    COLOR(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  /**
   * Determines the winning pallet based on the color of the canvas card.
   * It uses different rules depending on the canvas card's color.
   *
   * @param pallets a list of pallets (each containing a list of cards)
   * @param canvas the canvas card that determines the rule to apply
   * @return the index of the winning pallet
   * @throws IllegalArgumentException if the pallet list is null, empty, or contains invalid cards
   * @throws NullPointerException if a pallet or card is null
   */
  public static int checkWinningPallet(List<List<CardModel>> pallets, CardModel canvas) {
    if (pallets == null || pallets.isEmpty()) {
      throw new IllegalArgumentException("Pallet list cannot be null or empty.");
    }
    if (canvas == null) {
      throw new IllegalArgumentException("Canvas card cannot be null.");
    }

    for (List<CardModel> pallet : pallets) {
      if (pallet == null) {
        throw new IllegalArgumentException("A pallet in the list is null.");
      }
      for (CardModel card : pallet) {
        if (card == null) {
          throw new IllegalArgumentException("A card in a pallet is null.");
        }
        if (card.getNumber() < 0) {
          throw new IllegalArgumentException("Card number cannot be negative: " + card.getNumber());
        }
      }
    }

    try {
      COLOR color = COLOR.valueOf(canvas.getColor());
      switch (color) {
        case R:
          return highestCard(pallets);
        case O:
          return mostSingleNumbers(pallets);
        case B:
          return mostDifferentColors(pallets);
        case I:
          return longestRun(pallets);
        case V:
          return cardsBelowFour(pallets);
        default:
          throw new IllegalArgumentException("Invalid color: " + canvas.getColor());
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid color: " + canvas.getColor(), e);
    }

  }

  /**
   * Determines which pallet contains the highest card.
   * In case of ties in card number, the card with the higher color value wins.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the highest card
   */
  private static int highestCard(List<List<CardModel>> pallets) {
    int highestPalletRowIndex = -1;
    CardModel highestCard = null;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);
      for (CardModel card : pallet) {
        if (highestCard == null
                || card.getNumber() > highestCard.getNumber() ||
                (card.getNumber() == highestCard.getNumber()
                        && COLOR.valueOf(card.getColor()).getValue()
                        > COLOR.valueOf(highestCard.getColor()).getValue())) {
          highestCard = card;
          highestPalletRowIndex = i;
        }
      }
    }
    return highestPalletRowIndex;
  }

  /**
   * Determines which pallet has the most repeated card numbers.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the most repeated numbers
   */
  private static int mostSingleNumbers(List<List<CardModel>> pallets) {
    int palletWithMostRepeats = -1;
    int maxRepeatingCount = 0;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);
      int repeatingCount = 0;

      for (int j = 0; j < pallet.size(); j++) {
        CardModel card = pallet.get(j);
        int cardNumber = card.getNumber();
        boolean foundRepeat = false;

        for (int k = 0; k < pallet.size(); k++) {
          if (j != k && cardNumber == pallet.get(k).getNumber()) {
            foundRepeat = true;
            break;
          }
        }

        if (foundRepeat) {
          repeatingCount++;
        }
      }

      if (repeatingCount > maxRepeatingCount) {
        maxRepeatingCount = repeatingCount;
        palletWithMostRepeats = i;
      }
    }

    return palletWithMostRepeats;
  }

  /**
   * Determines which pallet has the most different colors.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the most different colors
   */
  private static int mostDifferentColors(List<List<CardModel>> pallets) {
    int palletWithMostDifferentColors = -1;
    int maxDifferentColors = 0;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);
      List<String> seenColors = new ArrayList<>();

      for (CardModel card : pallet) {
        String color = card.getColor();
        if (!seenColors.contains(color)) {
          seenColors.add(color);
        }
      }

      if (seenColors.size() > maxDifferentColors) {
        maxDifferentColors = seenColors.size();
        palletWithMostDifferentColors = i;
      }
    }

    return palletWithMostDifferentColors;
  }

  /**
   * Determines which pallet has the longest consecutive run of card numbers.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the longest run, or -1 if no valid run exists
   */
  private static int longestRun(List<List<CardModel>> pallets) {
    int palletWithLongestRun = -1;
    int maxRunLength = 0;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);

      if (pallet.isEmpty()) {
        continue;
      }

      List<Integer> numbers = new ArrayList<>();
      for (CardModel card : pallet) {
        numbers.add(card.getNumber());
      }
      Collections.sort(numbers);

      int currentRunLength = 1;
      int longestRunInThisPallet = 1;

      for (int j = 1; j < numbers.size(); j++) {
        if (numbers.get(j) == numbers.get(j - 1) + 1) {
          currentRunLength++;
        } else {
          currentRunLength = 1;
        }
        longestRunInThisPallet = Math.max(longestRunInThisPallet, currentRunLength);
      }

      if (longestRunInThisPallet > maxRunLength) {
        maxRunLength = longestRunInThisPallet;
        palletWithLongestRun = i;
      }
    }

    return maxRunLength > 1 ? palletWithLongestRun : -1;
  }

  /**
   * Determines which pallet has the most cards with numbers below four.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the most cards below four, or -1 if none exist
   */
  private static int cardsBelowFour(List<List<CardModel>> pallets) {
    int palletWithMostCardsBelowFour = -1;
    int maxCardsBelowFour = 0;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);
      int countBelowFour = 0;

      for (CardModel card : pallet) {
        if (card.getNumber() < 4) {
          countBelowFour++;
        }
      }

      if (countBelowFour > maxCardsBelowFour) {
        maxCardsBelowFour = countBelowFour;
        palletWithMostCardsBelowFour = i;
      }
    }

    return maxCardsBelowFour > 0 ? palletWithMostCardsBelowFour : -1;
  }
}
