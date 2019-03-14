
public class XGameGrid extends GameGrid {

	public XGameGrid(GameGrid grid) {
		super(grid);
		// TODO Auto-generated constructor stub
	}

	public XGameGrid(String grid) {
		super(grid);
	}

	public XGameGrid(int[][] grid) {
		super(grid);
	}

	public boolean isDiagonal(int row, int column) {
		return (column == row || column + row == GRID_DIM - 1);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "";
		for (int row = 0; row < GRID_DIM; row++) {
			for (int column = 0; column < GRID_DIM; column++) {
				if (column == 0 && !isDiagonal(row, column)) {
					str += " ";
				}
				if ((column + 1) % 3 == 0 && isDiagonal(row, column)) {
					str = str + "[" + super.gotField(row, column) + "]" + "  ";

				} else if (isDiagonal(row, column)) {
					str = str + "[" + super.gotField(row, column) + "] ";
				} else if (isDiagonal(row, column + 1) && (column + 1) % 3 == 0) {
					str = str + super.gotField(row, column) + "  ";
				} else if (isDiagonal(row, column + 1)) {
					str = str + super.gotField(row, column) + " ";
				} else if ((column + 1) % 3 == 0) {
					str = str + super.gotField(row, column) + "   ";
				} else {
					str = str + super.gotField(row, column) + "  ";
				}

			}
			str += "\n";
			if ((row + 1) % 3 == 0) {
				str += "\n";
			}
		}
		return str;
	}

	private boolean checkDiagonal(int row, int column, int value) {
		// check if the the point to be set is on descending diagonal
		if (row == column) {
			// search through the values on descending diagonal, if identical return false;
			for (int i = 0; i < GRID_DIM; i++) {
				if (Grid[i][i].getValue() == value) {
					return false;
				}
			}
		}
		// search through the values on ascending diagonal, if identical return false;
		else if (row +column==GRID_DIM-1) {
			for (int i =0;i<GRID_DIM;i++) {
				if (Grid[i][GRID_DIM-1-i].getValue()==value) {
					return false;
				}
			}
		}
		else {
			return true;
		}
		return true;
	}

	@Override
	public boolean isValid(int row, int column, int value) {
		// TODO Auto-generated method stub
		return (super.isValid(row, column, value) && checkDiagonal(row, column, value));
	}

	public static void main(String[] args) {

		GameGrid game = new XGameGrid(args[0]);
		System.out.print(game);

	}

}
