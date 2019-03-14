import java.util.ArrayList;
import java.util.Objects;

/**
 * Functionality to solve a given Sudoku game. 
 */
public class Solver {

    /** 
     * Solve the given Sudoku game using backtracking.
     *
     * @param game The game to be solved.
     * @return true if a solution was found, false otherwise.
     * 
     * 
     */
	private static boolean backtrack(GameGrid copiedGrid, int row, int column) {
		if (row == 8 && column == 9) {
			
			return true;

		}
		if (column == 9) {
			row += 1;
			column = 0;
		}

		if (copiedGrid.isInitial(row, column)) {
			boolean a = backtrack(copiedGrid, row, column + 1);
			if (a) {
				return true;
			}
		} else {
			for (int i = 0; i < copiedGrid.GRID_DIM; i++) {
				if (copiedGrid.setField(row, column, i + 1)) {
					
					boolean b = backtrack(copiedGrid, row, column + 1);
					if (b) {
						return true;
					} else {
						copiedGrid.clearField(row, column);
						
					}

				}
			}

		}
		return false;
	}

	
	// Use backtrack function to fill the gaps in Grid, starting from row 0 and column 0
	public static boolean solve(GameGrid copiedGrid) {
		return backtrack(copiedGrid, 0, 0);

	}
    public static ArrayList<GameGrid> findAllSolutions(GameGrid game) {
       Objects.requireNonNull(game);
       ArrayList<GameGrid> solutions = new ArrayList<>();

       // start at top left
       int column = 0;
       int row = 0;

       // if true, we move backwards through the grid, forward otherwise
       boolean goBack = false;

       // while not iterated through all possible combinations yet
       while(!(column == GameGrid.GRID_DIM - 1 && row == -1)) {
           
           // try values on current field
           if(!game.isInitial(row,column)) {
               goBack = false; // go forward
               if(!tryIncrease(game,column,row)) {
                   game.clearField(row,column);
                   goBack = true; // track back
               }

           } 
           
           // move through grid
           if(goBack) { // backwards
               column--;
               if(column < 0) { // move up one row
                   column = GameGrid.GRID_DIM - 1;
                   row--;
               }
           } else { // forward
              column++;
              if(column >= GameGrid.GRID_DIM) { // move down one row
                  column = 0;
                  row++;
              }
           }

           // we reached the end, hence found a valid solution
           if (column == 0 && row == GameGrid.GRID_DIM) {
               solutions.add(SudokuPlay.copyGameGrid(game));
           	   goBack = true;
           	   column = GameGrid.GRID_DIM-1;
           	   row--;
      }
       }
      // we tried all possible combinations without reaching the end, the solutions in the Arraylist are saturated
      return solutions;
    
    }

    /**
     * A helper method trying to increase the value of a specified field 
     * in the given game based on Sudoku rules.
     *
     * @param game The game of which the specified value is to be increased
     * @param column x coordinate between 0 and 8
     * @param row y coordinate between 0 and 8
     * @return true if the value could be increased, false otherwise.
     *
     */
    private static boolean tryIncrease(GameGrid game, int column, int row) {
        int val = game.gotField(row,column);

        boolean success = false;
        for(int i = val + 1; i <= GameGrid.MAX_VAL; i++) {
            if(game.setField(row,column,i)) {
                success = true;
                break;
            }
        }

        return success;
    }
}
