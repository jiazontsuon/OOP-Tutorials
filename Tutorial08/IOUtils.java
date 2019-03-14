import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;

import java.io.UncheckedIOException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class IOUtils {

    /**
     * This function loads a Sudoku game grid from a file.
     *
     * @param gridFileName the path to a Sudoku grid data file
     * @return a two-dimensional integer array holding the data from the specified file
     *
     */
	public static HashMap loadFromFolder(String dir) {
		 HashMap<String,GameGrid> hashMap = new HashMap<>();
		 File directory = new File(dir);
		 
		 if (directory.exists() && directory.isDirectory()) {
			 String[] listOfFile = directory.list();
			 
			 for (String name : listOfFile) {
				 String absolutePath = dir +"\\"+ name;
				 if (name.substring(name.length()-3).equals(".sd")&&name.substring(0,1).equals("x")) {
					 hashMap.put(name , SudokuPlay.copyGameGrid(new XGameGrid(absolutePath)));
				 }else if (name.substring(name.length()-3).equals(".sd")) {
					 hashMap.put(name , SudokuPlay.copyGameGrid(new RGameGrid(absolutePath)));
				 }
			 }
			 
		 }
		 else {
			 return hashMap; 
		 }
		 return hashMap;
		 
	}
    public static int[][] loadFromFile(String gridFileName) {
        Objects.requireNonNull(gridFileName);

        Path fileName = Paths.get(gridFileName);

        if (!Files.exists(fileName))
            throw new IllegalArgumentException("Given file does not exist: " + fileName);

        int[][] grid = new int[GameGrid.GRID_DIM][GameGrid.GRID_DIM];
        
        try {     
        	Scanner in = new Scanner(fileName);         
	
	        for(int row = 0; row < GameGrid.GRID_DIM; row++) {
	            for(int column = 0; column < GameGrid.GRID_DIM; column++) {
	                if(!in.hasNextInt())
	                    throw new RuntimeException("Given Sudoku file has invalid format: " + fileName);
	
	                int value = in.nextInt();
	                if (value < 0 || value > 9)
	                    throw new RuntimeException("Given Sudoku file has invalid "
	                               + "entry at: " + column + "x" + row);
	               
	                grid[row][column] = value;
	            }
	        }
	        
	        in.close();
        
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return grid;
    }

}
