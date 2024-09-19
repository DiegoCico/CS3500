//package cs3500.solored.view.hw02;
//
//import cs3500.solored.model.hw02.Card;
//import cs3500.solored.model.hw02.CardModel;
//import cs3500.solored.model.hw02.RedGameModel;
//import cs3500.solored.model.hw02.SoloRedModel;
//
//public class Main {
//  private final SoloRedModel<CardModel> model;
//
//  public Main(RedGameModel<CardModel> model) {
//    this.model = model;
//  }
//
//  @Override
//  public String toString() {
//    StringBuilder sb = new StringBuilder();
//    sb.append("Canvas: ").append(model.getCanvas()).append("\n");
//    for (int i = 0; i < model.numPalettes(); i++) {
//      sb.append("P").append(i + 1).append(": ");
//      sb.append(String.join(" ", model.getPalette(i))).append("\n");
//    }
//    sb.append("Hand: ").append(String.join(" ", model.getHand())).append("\n");
//    return sb.toString().trim();
//  }
//}
