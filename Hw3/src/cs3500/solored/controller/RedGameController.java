package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.CardModel;
import cs3500.solored.model.hw02.RedGameModel;

public interface RedGameController {

  <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize);

  void transmit(String message);

}
