package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: REVIEW THE CODE MAKE SURE EVERYTHING WORKS
public class WinningPallet {
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

    // Getter to retrieve the value
    public int getValue() {
      return value;
    }
  }

  public int checkWinningPallet(List<List<CardModel>> pallets, CardModel canvas) {
    return switch (COLOR.valueOf(canvas.getColor())) {
      case R -> highestCard(pallets);
      case O -> mostSingleNumbers(pallets);
      case B -> mostDifferentColors(pallets);
      case I -> longestRun(pallets);
      case V -> cardsBelowFour(pallets);
      default -> throw new IllegalArgumentException("Invalid color: " + canvas.getColor());
    };
  }

  private int highestCard(List<List<CardModel>> pallets) {
    int highestPalletRowIndex = -1;
    CardModel highestCard = null;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);
      for (int j = 0; j < pallet.size(); j++) {
        CardModel card = pallet.get(j);
        if (highestCard == null) {
          highestCard = card;
          highestPalletRowIndex = i;
        } else if (card.getNumber() > highestCard.getNumber()) {
          highestCard = card;
          highestPalletRowIndex = i;
        } else if (card.getNumber() == highestCard.getNumber()) {
          if (COLOR.valueOf(card.getColor()).getValue() > COLOR.valueOf(highestCard.getColor()).getValue()) {
            highestCard = card;
            highestPalletRowIndex = i;
          }
        }
      }
    }
    return highestPalletRowIndex;
  }

  // TODO : CHECK OVER THIS CODE
  private int mostSingleNumbers(List<List<CardModel>> pallets) {
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

  // TODO CHECK OVER THIS CODE
  private int mostDifferentColors(List<List<CardModel>> pallets) {
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

  // TODO CHECK OVER THIS CODE
  private int longestRun(List<List<CardModel>> pallets) {
    int palletWithLongestRun = -1;
    int maxRunLength = 0;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);

      if (pallet.isEmpty()) {
        continue; // Skip empty pallets
      }

      // Extract the numbers and sort them
      List<Integer> numbers = new ArrayList<>();
      for (CardModel card : pallet) {
        numbers.add(card.getNumber());
      }
      Collections.sort(numbers);

      // Find the longest run of consecutive numbers
      int currentRunLength = 1;
      int longestRunInThisPallet = 1;

      for (int j = 1; j < numbers.size(); j++) {
        if (numbers.get(j) == numbers.get(j - 1) + 1) {
          currentRunLength++;
        } else {
          currentRunLength = 1; // Reset if sequence breaks
        }
        longestRunInThisPallet = Math.max(longestRunInThisPallet, currentRunLength);
      }

      // Update if this pallet has the longest run
      if (longestRunInThisPallet > maxRunLength) {
        maxRunLength = longestRunInThisPallet;
        palletWithLongestRun = i;
      }
    }

    // Return the index of the pallet with the longest run, or -1 if no run found
    return maxRunLength > 1 ? palletWithLongestRun : -1;
  }

  // TODO CHECK OVER THIS CODE
  private int cardsBelowFour(List<List<CardModel>> pallets) {
    int palletWithMostCardsBelowFour = -1;
    int maxCardsBelowFour = 0;

    for (int i = 0; i < pallets.size(); i++) {
      List<CardModel> pallet = pallets.get(i);
      int countBelowFour = 0;

      // Count the number of cards below 4 in the current pallet
      for (CardModel card : pallet) {
        if (card.getNumber() < 4) {
          countBelowFour++;
        }
      }

      // Update the pallet with the most cards below 4
      if (countBelowFour > maxCardsBelowFour) {
        maxCardsBelowFour = countBelowFour;
        palletWithMostCardsBelowFour = i;
      }
    }

    return maxCardsBelowFour > 0 ? palletWithMostCardsBelowFour : -1;
  }






}
