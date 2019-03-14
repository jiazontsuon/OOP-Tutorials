import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JButton;
import java.awt.GridLayout;

public class SudokuFrame extends JFrame {
	private final GameGrid game;

	public SudokuFrame(String fileName) {
		super.setSize(600, 600);
		super.setTitle(fileName);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Objects.requireNonNull(fileName);
		game = new RGameGrid(fileName);

		GridLayout layOut = new GridLayout(9, 9);
		super.setLayout(layOut);
		createButtonGrid();
	}

	private void createButtonGrid() {
		for (int row = 0; row < game.GRID_DIM; row++) {
			for (int column = 0; column < game.GRID_DIM; column++) {
				
				JButton botton = new JButton();
				botton.addActionListener(new SudokuFieldAction(row,column,game));
				
				int valueOfField = game.gotField(row, column);

				if (game.isInitial(row, column)) {

					// JButton botton = new JButton();
					botton.setText(String.valueOf(valueOfField));
					botton.setEnabled(false);

				} else {
					if (valueOfField != 0) {
						botton.setText(String.valueOf(valueOfField));
					}

				}
				this.add(botton);
			}
		}
	}

	public static void main(String[] args) {
		SudokuFrame gameFrame = new SudokuFrame(Sudoku07.askForPath());
		gameFrame.setVisible(true);

	}
}
