import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.ArrayList;

public class SudokuPlay {

	//private static final int grid_size = 9;
	static int x_cordOfClashNum;
	static int y_cordOfClashNum;
	// static StringBuilder error = new StringBuilder("The Your input contradicts
	// with: ");
	//static ArrayList<String> clash = new ArrayList<>();

	/**
	 * From OOP Tutorial Name: Cen Yu Print a game menu message to the console.
	 */
	public static void printMenu() {
		System.out.print("\n" + "1. Set field\n" + "2. Clear field\n" + 
				"3. Print game\n" + "4. Solve\n" +"5. Find All Solutions\n"+ "6. RankSudoku\n"+"7. Exit\n\n");
	}
	public static void printMenu1() {
		System.out.print("\nSelect game mode:\n"+"1. Regular Sudoku\n2. X Sudoku\r3. Exit\n\n\nSelect an action [1-3]:\n" );
	}

	/**
	 * Read a single integer value from the console and return it. This function
	 * blocks the program's execution until a user entered a value into the command
	 * line and confirmed by pressing the Enter key.
	 * 
	 * @return The user's input as integer or -1 if the user's input was invalid.
	 */
	public static int parseInput() {
		Scanner in = new Scanner(System.in);
		try {
			return in.nextInt();
		} catch (InputMismatchException missE) {
			in.next(); // discard invalid input
			return -1;
		}
	}
	
	public static String parseString() {
		Scanner in = new Scanner(System.in);
		while(true) {
			try {
		    	return in.next();
		    }catch (InputMismatchException missE) {
		    	in.hasNext();
		    	System.out.println("The input has to be String");		    	
		    }
		}
	    
	}

	/**
	 * Display a dialog requesting a single integer which is returned upon
	 * completion.
	 *
	 * The dialog is repeated in an endless loop if the given input is not an
	 * integer or not within min and max bounds.
	 *
	 * @param msg: a name for the requested data.
	 * @param min: minium accepted integer.
	 * @param max: maximum accepted integer.
	 * @return The user's input as integer.
	 */

	public static int requestInt(String msg, int min, int max) {
		Objects.requireNonNull(msg);
	
		while (true) {
			System.out.print("Please provide " + msg + ": ");
			int input = parseInput();
			if (input >= min && input <= max)
				return input;
			else {
				System.out.println("Invalid input. Must be between " + min + " and " + max);
			}
		}
	}

	public static GameGrid copyGameGrid(GameGrid grid) {
		if (grid instanceof XGameGrid) {
			return new XGameGrid(grid);
		}else {
			return new RGameGrid(grid);
		}
	}

	public static void main(String[] args) {
		
		GameGrid game;
		// TODO implement the game loop here
		
		while (true) {
			printMenu1();
			int command1 = parseInput();
			if (command1==1) {
				System.out.println("Please enter the name of regular Sudoku file you want to play(eg.sudoku1.sd).");
				String input = parseString();
				try {
					game = new RGameGrid(input);
					if (!Solver.solve(copyGameGrid(game))) {
						System.out.println("It is not a regular sudoku file or is unsolvable. ");
						continue;
					}
					}
					catch (IllegalArgumentException IlleArg) {
						System.out.println("The file cannot be found.");
						continue;
					}
			}else if (command1==2) {
				System.out.println("Please enter the name of X Sudoku file you want to play(eg.xsudoku1.sd).");
				String input = parseString();
				try {
					game = new XGameGrid(input);
					if (!Solver.solve(copyGameGrid(game))) {
						System.out.println("It is not an X sudoku file or is unsolvable. ");
						continue;
					}
					}
					catch (IllegalArgumentException IlleArg) {
						System.out.println("The file cannot be found.");
						continue;
					}
								
			}
				else if (command1==3){
				break;
			}
				else {
			
				System.out.println("Please select an action [1-3]!");				
				continue;
			}
		
		while (true) {
			
			printMenu();
			int command = parseInput();
			if (command == 1) {
				int x_cord = requestInt("X-Coordinate", 1, 9);
				int y_cord = requestInt("Y-Coordinate", 1, 9);
				int inputValue = requestInt("the value for the corresponding coordinates", 1, 9);
				if (game.isValid(y_cord - 1, x_cord - 1, inputValue)) {
					game.setField(y_cord - 1, x_cord - 1, inputValue);
					System.out.print(game.toString());
				} else {
					game.printClash();
					System.out.print(game.toString());
				}
			} else if (command == 2) {
				int x_cord = requestInt("X-Coordinate", 1, 9);
				int y_cord = requestInt("Y-Coordinate", 1, 9);
				game.setField(y_cord - 1, x_cord - 1, 0);
				System.out.print(game.toString());
			} else if (command == 3) {
				System.out.print(game);
			}else if (command ==4) {
				GameGrid copiedGrid = copyGameGrid(game);				
				
				if (Solver.solve(copiedGrid)) {
					System.out.print("Possible Solution:\n"+ copiedGrid.toString());
				}else {
					System.out.println("This Sudoku has no solution. ");
				}
			}
			else if (command ==5) {
				Solver a = new Solver();
				ArrayList<GameGrid> b = a.findAllSolutions(game);
				if ( b.size()==0) {
					System.out.println("No solutions found.");
				}else {
					for (int i =0;i<b.size();i++) {
						
						System.out.print("Solution "+ (i+1)+ ":\n" +b.get(i));
					}
				}
				
			}else if (command == 6) {
				
				System.out.println("Rank Value:  "+ Ranker.rankSudoku(game));
			}
			else if (command == 7) {
				System.out.println("Thank you for playing this game");
				break;
			} 
			else {
				System.out.println("Your input should be between 1 and 7 !");
			}
		}
		
	}

	}
}
