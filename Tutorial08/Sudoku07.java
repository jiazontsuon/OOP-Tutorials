import java.util.Objects;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class Sudoku07 {
	
	public static String askForPath() {
		
		String path = JOptionPane.showInputDialog("Provide path to Sudoku game file.");
		
		Objects.requireNonNull(path);
		
		Path fileName = Paths.get(path);
		if (Files.exists(fileName)) {
			return path;
		}else {
			return null;
		}
		
	}
		

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameGrid a = new RGameGrid(Sudoku07.askForPath());
		

	}

}
