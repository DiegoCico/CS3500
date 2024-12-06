package cs3500.threetrios.provider.model;

import cs3500.threetrios.card.CardModel;
import cs3500.threetrios.player.PlayerModel;

import java.awt.Color;
import java.util.Map;

/**
 * An adapter class that adapts the `cs3500.threetrios.card.Card`
 * model and the provider's `Slot` interface.
 * It deals with the interaction between cards and the board in the ThreeTrios game.
 */
public class CardAdapter implements Slot {
  private final cs3500.threetrios.card.Card myCard;
  private PlayerModel myPlayer;

  /**
   * Constructs a CardAdapter with the given card and player.
   *
   * @param myCard   the card to adapt
   * @param myPlayer the player owning the card
   * @throws IllegalArgumentException if the card is null
   */
  public CardAdapter(cs3500.threetrios.card.Card myCard, PlayerModel myPlayer) {
    if (myCard == null) {
      throw new IllegalArgumentException("Card cannot be null");
    }
    this.myCard = myCard;
    this.myPlayer = myPlayer;
  }


  /**
   * Checks if a card can be played on this slot.
   *
   * @return true if no player owns the card, false otherwise
   */
  @Override
  public boolean canPlayCard() {
    return myPlayer == null;
  }

  /**
   * Switches owner of the slot to the specified player.
   *
   * @param player the player to assign ownership to
   * @throws IllegalArgumentException if the player is null
   */
  @Override
  public void switchPlayer(cs3500.threetrios.provider.model.Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    this.myPlayer = PlayerAdapter.toPlayerModel(player);
  }


  /**
   * Determines the outcome of a battle between this slot and another.
   *
   * @param other     the opposing slot
   * @param direction the direction of the battle
   * @return true if this slot wins the battle, false otherwise
   * @throws IllegalArgumentException if the other slot or direction is null
   */
  @Override
  public boolean battle(Slot other, Direction direction) {
    if (other == null || direction == null) {
      throw new IllegalArgumentException("Other slot or direction cannot be null");
    }

    AttackValue thisAttack = getAttackValue(direction);
    AttackValue otherDefense = other.getDirectionalValues()
            .get(direction.getOppositeDirection());

    return thisAttack.compareToValue(otherDefense) > 0;
  }


  /**
   * Adds to the count of cards owned by players.
   *
   * @param curCount a map of players to their respective counts
   */
  @Override
  public void addToPlayerCount(Map<Player, Integer> curCount) {
    if (myPlayer != null) {
      Player adaptedPlayer =
              PlayerAdapter.toProviderPlayer(myPlayer);

      curCount.put(adaptedPlayer, curCount.getOrDefault(adaptedPlayer, 0) + 1);
    }
  }


  /**
   * Compares  attack val of a card in given direction to another attack value.
   *
   * @param dirAttackComingFrom the direction of the attack
   * @param other               the opposing attack value
   * @return if its positive, negative or the same
   * @throws IllegalArgumentException if the direction or attack value is null
   */
  @Override
  public int compareAttackValueTo(Direction dirAttackComingFrom, AttackValue other) {
    if (dirAttackComingFrom == null || other == null) {
      throw new IllegalArgumentException("Direction and AttackValue cannot be null");
    }
    AttackValue thisValue = getAttackValue(dirAttackComingFrom);
    return thisValue.compareToValue(other);
  }


  /**
   * Gives a textual rep of this card for board display purposes.
   *
   * @return a string representation of the card
   */
  @Override
  public String boardPrint() {
    return myCard.toString();
  }

  /**
   * Creates a deep copy of this slot.
   *
   * @return a new CardAdapter with the same card and player
   */
  public Slot copySlot() {
    return new CardAdapter(new CardModel(myCard.getName(), myCard.getNorth(),
            myCard.getSouth(),
            myCard.getEast(),
            myCard.getWest(),
            myCard.getColor()), myPlayer);
  }

  /**
   * Gets the player who owns this slot.
   *
   * @return the owning player, or null if unowned
   */
  @Override
  public Player getSlotOwner() {
    return PlayerAdapter.toProviderPlayer(myPlayer);
  }



  /**
   * Gets the color associated with this slot.
   *
   * @return the color of the card in the slot
   */
  @Override
  public Color getSlotColor() {
    switch (myCard.getColor()) {
      case RED:
        return Color.RED;
      case BLUE:
        return Color.BLUE;
      default:
        throw new IllegalArgumentException("Unsupported color");
    }
  }


  /**
   * Gets the directional attack values for this card.
   *
   * @return a map of directions to their corresponding attack values
   */
  @Override
  public Map<Direction, AttackValue> getDirectionalValues() {
    return Map.of(
            Direction.UP, mapToAttackValue(myCard.getNorth()),
            Direction.DOWN, mapToAttackValue(myCard.getSouth()),
            Direction.LEFT, mapToAttackValue(myCard.getWest()),
            Direction.RIGHT, mapToAttackValue(myCard.getEast())
    );
  }

  /**
   * Maps a num attack value to an `AttackValue` enum.
   *
   * @param value the num attack value
   * @return the corresponding AttackValue
   * @throws IllegalArgumentException if the value is invalid
   */
  private AttackValue mapToAttackValue(int value) {
    switch (value) {
      case 1: return AttackValue.ONE;
      case 2: return AttackValue.TWO;
      case 3: return AttackValue.THREE;
      case 4: return AttackValue.FOUR;
      case 5: return AttackValue.FIVE;
      case 6: return AttackValue.SIX;
      case 7: return AttackValue.SEVEN;
      case 8: return AttackValue.EIGHT;
      case 9: return AttackValue.NINE;
      case 10: return AttackValue.A;
      default:
        throw new IllegalArgumentException("Invalid attack value: " + value);
    }
  }

  /**
   * Helper method to get the attack value for a given direction.
   */
  private AttackValue getAttackValue(Direction direction) {
    switch (direction) {
      case UP:
        return mapToAttackValue(myCard.getNorth());
      case DOWN:
        return mapToAttackValue(myCard.getSouth());
      case LEFT:
        return mapToAttackValue(myCard.getWest());
      case RIGHT:
        return mapToAttackValue(myCard.getEast());
      default:
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }
  }
}

