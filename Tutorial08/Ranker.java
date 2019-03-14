import java.util.HashMap;
import java.util.Objects;


public class Ranker {

	public static float rankSudoku(GameGrid grid) {
		int numberOfFreeField = grid.getFreeField();
		//new Solver();
		int numberOfSolutions = Solver.findAllSolutions(grid).size();
		if (numberOfSolutions == 0) {
			return Float.MAX_VALUE;
			//throw new RuntimeException("The sudoku has no solution.");
		}
		return (numberOfSolutions + (1- (float)numberOfFreeField/ 81));

	}

	public static void main(String[] args) {
		String dir = args[0];
		HashMap<String, GameGrid> hashMap = new IOUtils().loadFromFolder(dir);

		float LowestRank = Float.MAX_VALUE;
		String fileName = null;

		for (String key : hashMap.keySet()) {

			if (rankSudoku(hashMap.get(key)) < LowestRank) {
				LowestRank = rankSudoku(hashMap.get(key));
				fileName = key;

			}

		}

		System.out.println("File Name: " + fileName + " Rank: " + LowestRank);

	}
}
