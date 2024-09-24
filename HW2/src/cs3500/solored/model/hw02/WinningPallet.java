package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class helps you figure out which pallet is winning in the game.
 * It uses different rules based on the color of the canvas card,
 * and if there’s a tie, it uses the highest card (Red rule) to break the tie.
 */
public class WinningPallet {

  /**
   * Enum representing the colors of the cards and their importance.
   * R (Red) is the most important (value 4), followed by O (Orange), B (Blue),
   * I (Indigo), and V (Violet), which is the least important (value 0).
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
   * This method checks which pallet is currently winning based on the canvas card’s color.
   * It runs the specific rule for the canvas color, and if two or more pallets are tied,
   * it breaks the tie by picking the pallet with the highest card (Red rule).
   *
   * @param pallets a list of pallets (each being a list of cards)
   * @param canvas the canvas card which decides which rule to use
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
   * Finds the pallet with the highest card.
   * If there’s a tie for the card number, the color’s importance
   * (R > O > B > I > V) breaks the tie.
   *
   * @param pallets the list of pallets to look through
   * @return the index of the pallet with the highest card
   */
  private static int highestCard(List<List<CardModel>> pallets) {
    int highestPalletRowIndex = -1;
    CardModel highestCard = null;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);
      for (CardModel card : pallet) {
        if (highestCard == null
                || card.getNumber() > highestCard.getNumber()
                || (card.getNumber() == highestCard.getNumber()
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
   * Finds the pallet with the most repeated card numbers.
   * If two or more pallets tie, it uses the highest card (Red rule) to break the tie.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the most repeated numbers
   */
  private static int mostSingleNumbers(List<List<CardModel>> pallets) {
    List<Integer> tiedPallets = new ArrayList<>();
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
        tiedPallets.clear();
        tiedPallets.add(i);
      } else if (repeatingCount == maxRepeatingCount) {
        tiedPallets.add(i);
      }
    }

    if (tiedPallets.size() > 1) {
      return breakTieUsingHighestCard(pallets, tiedPallets);
    }

    return palletWithMostRepeats;
  }

  /**
   * Finds the pallet with the most different colors of cards.
   * If two or more pallets tie, it uses the highest card (Red rule) to break the tie.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the most different colors
   */
  private static int mostDifferentColors(List<List<CardModel>> pallets) {
    List<Integer> tiedPallets = new ArrayList<>();
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
        tiedPallets.clear();
        tiedPallets.add(i);
      } else if (seenColors.size() == maxDifferentColors) {
        tiedPallets.add(i);
      }
    }

    if (tiedPallets.size() > 1) {
      return breakTieUsingHighestCard(pallets, tiedPallets);
    }

    return palletWithMostDifferentColors;
  }

  /**
   * Finds the pallet with the longest consecutive run of numbers.
   * If there’s a tie in run length, the highest number in the run breaks the tie.
   * If there’s still a tie, it uses the highest card (Red rule) to break the tie.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the longest run, or -1 if no valid run exists
   */
  private static int longestRun(List<List<CardModel>> pallets) {
    List<Integer> tiedPallets = new ArrayList<>();
    int palletWithLongestRun = -1;
    int maxRunLength = 0;
    int highestRunCardValue = 0;

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
      int highestCardInThisRun = numbers.get(0);

      for (int j = 1; j < numbers.size(); j++) {
        if (numbers.get(j) == numbers.get(j - 1) + 1) {
          currentRunLength++;
          highestCardInThisRun = numbers.get(j);
        } else {
          currentRunLength = 1;
          highestCardInThisRun = numbers.get(j);
        }
        longestRunInThisPallet = Math.max(longestRunInThisPallet, currentRunLength);
      }

      if (longestRunInThisPallet > maxRunLength
              || (longestRunInThisPallet == maxRunLength
              && highestCardInThisRun > highestRunCardValue)) {
        maxRunLength = longestRunInThisPallet;
        highestRunCardValue = highestCardInThisRun;
        palletWithLongestRun = i;
        tiedPallets.clear();
        tiedPallets.add(i);
      } else if (longestRunInThisPallet == maxRunLength
              && highestCardInThisRun == highestRunCardValue) {
        tiedPallets.add(i);
      }
    }

    if (tiedPallets.size() > 1) {
      return breakTieUsingHighestCard(pallets, tiedPallets);
    }

    return maxRunLength > 1 ? palletWithLongestRun : -1;
  }

  /**
   * Finds the pallet with the most cards that have numbers below four.
   * If there’s a tie, it uses the highest card (Red rule) to break the tie.
   *
   * @param pallets the list of pallets to evaluate
   * @return the index of the pallet with the most cards below four, or -1 if none exist
   */
  private static int cardsBelowFour(List<List<CardModel>> pallets) {
    List<Integer> tiedPallets = new ArrayList<>();
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
        tiedPallets.clear();
        tiedPallets.add(i);
      } else if (countBelowFour == maxCardsBelowFour) {
        tiedPallets.add(i);
      }
    }

    if (tiedPallets.size() > 1) {
      return breakTieUsingHighestCard(pallets, tiedPallets);
    }

    return palletWithMostCardsBelowFour;
  }

  /**
   * Uses the highest card (Red rule) to break a tie between pallets.
   *
   * @param pallets the list of pallets
   * @param tiedPallets the indices of the tied pallets
   * @return the index of the winning pallet after using the highest card rule
   */
  private static int breakTieUsingHighestCard(List<List<CardModel>> pallets,
                                              List<Integer> tiedPallets) {
    List<List<CardModel>> tiedPalletsList = new ArrayList<>();
    for (int index : tiedPallets) {
      if (!pallets.get(index).isEmpty()) {
        tiedPalletsList.add(pallets.get(index));
      }
    }

    int highestIndexInTied = highestCard(tiedPalletsList);
    int highestIndex = tiedPallets.get(highestIndexInTied);
    List<CardModel> tiedPalletWithHighestCard = pallets.get(highestIndex);

    for (int index : tiedPallets) {
      List<CardModel> pallet = pallets.get(index);
      if (!pallet.isEmpty() && pallet.get(0).getNumber()
              == tiedPalletWithHighestCard.get(0).getNumber()) {
        return index;
      }
    }

    return highestIndex;
  }

}
