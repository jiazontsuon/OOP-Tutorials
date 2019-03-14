import java.util.ArrayList;
import java.util.Objects;

public abstract class GameGrid {
	public static final int GRID_DIM = 9;
	public static final int SUBGRID_DIM = GRID_DIM / 3;
	public static final int MAX_VAL = 9;
	public static final int MIN_VAL = 1;
	public static final int EMPTY_VAL = 0;
	public Field[][] Grid;
	
	/**an arraylist that stores the coordinates of value(s) on sudoku that  
	clashes with the value that is going to be set.*/
	static ArrayList<String> clash = new ArrayList<>();
	
	
	public GameGrid(int[][] grid) {
		Objects.requireNonNull(grid);
		Grid = initialiseGrid(grid);
	}

	public GameGrid(String path) {
		Objects.requireNonNull(path);

		new IOUtils();
		this.Grid = initialiseGrid(IOUtils.loadFromFile(path));
	}

	public GameGrid(GameGrid grid) {
		Field[][] newGrid = new Field[GRID_DIM][GRID_DIM];
		for (int i = 0; i < GRID_DIM; i++) {
			for (int j = 0; j < GRID_DIM; j++) {
				newGrid[i][j] = new Field(grid.Grid[i][j].getValue(),grid.Grid[i][j].getInitial());			
			}
		}
		this.Grid = newGrid;

	}

	/**
	 * row and cloumn numbers both use 0_based counting. eg. row 0 means the first
	 * row.
	 */

	private Field[][] initialiseGrid(int[][] grid) {
		Field[][] newGrid = new Field[GRID_DIM][GRID_DIM];
		for (int i = 0; i < GRID_DIM; i++) {
			for (int j = 0; j < GRID_DIM; j++) {
				if (grid[i][j]==0) {
					newGrid[i][j] = new Field();
				}else {
					newGrid[i][j] = new Field(grid[i][j], true);
				}
				
			}
		}
		return newGrid;
	}

	public boolean isInitial(int row, int column) {
		if (row < 0 || row > 8 || column < 0 || column > 8) {
			throw new IllegalArgumentException("Given dimensions invalid: " + row + "x" + column);
		}
		if (Grid[row][column].equals(null)) {
			return false;
		} else {
			return Grid[row][column].getInitial();
		}
	}

	public int gotField(int row, int column) {
		if (row < 0 || row > 8 || column < 0 || column > 8) {
			throw new IllegalArgumentException("Given dimensions invalid: " + column + "x" + row);
		}

		return Grid[row][column].getValue();

	}

	public boolean setField(int row, int column, int value) {
		if (row < 0 || row > 8 || column < 0 || column > 8) {
			throw new IllegalArgumentException("The input is invalid!");
		}
		if (value < MIN_VAL || value > MAX_VAL)
			throw new IllegalArgumentException("Given value invalid: " + value);

		if (isValid(row, column, value) && !isInitial(row, column)) {
			this.Grid[row][column].setValue(value);
			return true;
		}

		return false;
	}

	public void clearField(int row, int column) {
		if (row < 0 || row > 8 || column < 0 || column > 8) {
			throw new IllegalArgumentException("The input is invalid!");
		} else {
			this.Grid[row][column].setValue(EMPTY_VAL);
		}
	}

	public static String clashReport(int x, int y) {
		return ("(" + x + "," + y + ")");

	}

	private boolean checkRow(int row, int column, int value) {

		for (int j = 0; j < GRID_DIM; j++) {

			if (Grid[row][j].getValue() == value) {
				clash.add(clashReport(j + 1, row + 1));

				return true;
			}
		}
		return false;

	}

	private boolean checkColumn(int row, int column, int value) {

		for (int j = 0; j < GRID_DIM; j++) {
			if (Grid[j][column].getValue() == value) {
				clash.add(clashReport(column + 1, j + 1));
				return true;
			}
		}
		return false;

	}

	private boolean checkSubGrid(int row, int column, int value) {
		int verticleBlock = row / 3;
		int horizontalBlock = column / 3;
		int correspondingVPosition = verticleBlock * 3;
		int correspondingHPosition = horizontalBlock * 3;

		for (int i = correspondingVPosition; i < correspondingVPosition + 3; i++) {
			for (int j = correspondingHPosition; j < correspondingHPosition + 3; j++) {
				if (Grid[i][j].getValue() == value) {
					if (clash.contains(clashReport(j + 1, i + 1))) {
						return true;
					} else {
						clash.add(clashReport(j + 1, i + 1));
						return true;
					}

				}
			}
		}
		return false;

	}

	public boolean isValid(int row, int column, int value) {
		boolean rowTest = checkRow(row, column, value);
		boolean columnTest = checkColumn(row, column, value);
		boolean subGridTest = checkSubGrid(row, column, value);

		return !(rowTest || columnTest || subGridTest);
	}

	public void printClash() {
		System.out.println("\nYour value is not valid! The cordinates of the clashing point(s): " + clash);
		clash.clear();
	}

	public String toString() {
		String str = "-------------------\n";

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (j == 0) {
					str += ("| " + Grid[i][j].getValue());
				} else if ((j + 1) % 3 == 0) {
					str += (Grid[i][j].getValue() + " | ");
				} else {
					str += (Grid[i][j].getValue());
				}
			}
			if ((i + 1) % 3 == 0) {
				str += ("\n-------------------\n");

			} else {
				str += ("\n");
			}

		}
		return str;

	}
	
	public int getFreeField() {
		int counter =0;
		for(int row =0;row<GRID_DIM;row++) {
			for (int column =0;column< GRID_DIM;column++) {
				if (!this.isInitial(row, column)) {
					counter++;
				}
			}
		}
		return counter;
	}

}
