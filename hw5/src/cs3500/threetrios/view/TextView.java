import cs3500.threetrios.Game.Cell;
import cs3500.threetrios.Game.Grid;

public class TextView {
  private final Grid grid;
  public TextView(Grid grid) {
    this.grid = grid;
  }

  public String render() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < grid.getRows(); row++){
      for (int col = 0; col < grid.getCols(); col++){
        if (grid.getCellType(row, col) == Cell.CellType.HOLE) {
          sb.append("X ");
        } else if (grid.isEmpty(row, col)) {
          sb.append("_ ");
        } else {
          sb.append(grid.getCard(row, col).toString().charAt(0) + " ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

}
