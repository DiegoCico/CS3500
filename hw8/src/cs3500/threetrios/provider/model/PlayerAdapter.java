package cs3500.threetrios.provider.model;

import java.util.ArrayList;

import cs3500.threetrios.card.COLOR;
import cs3500.threetrios.player.PlayerModel;

/**
 * An adapter class that adapts the `cs3500.threetrios.card.PlayerModel`
 * model and the provider's `Player` enum.
 */
public class PlayerAdapter {

  /**
   * Converts PlayerModel to the provider's Player.
   *
   * @param player the player to convert
   * @return the corresponding provider
   * @throws IllegalArgumentException if the player name is unknown
   */
  public static Player toProviderPlayer(PlayerModel player) {
    try {
      return cs3500.threetrios.provider.model.Player.valueOf(player.getName().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Unknown player name: " + player.getName());
    }
  }


  /**
   * Converts the provider's Player to a PlayerModel.
   *
   * @param providerPlayer the provider player to convert
   * @return the corresponding playerModel
   */
  public static PlayerModel toPlayerModel(cs3500.threetrios.provider.model.Player providerPlayer) {
    COLOR color = providerPlayer == Player.A ? COLOR.RED : COLOR.BLUE;
    return new PlayerModel(providerPlayer.name(), color, new ArrayList<>());
  }
}

