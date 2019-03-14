import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class SudokuFieldAction implements  java.awt.event.ActionListener{
	
	private final int row;
	private final int column;
	private final GameGrid gameGrid;

	public SudokuFieldAction(int row, int column, GameGrid gameGrid) {
		this.row = row;
		this.column = column;
		this.gameGrid = gameGrid;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton button;
		if (arg0.getSource() instanceof JButton) {
			button= (JButton) arg0.getSource(); 
		}else {
			return;
		}
		String number = JOptionPane.showInputDialog("Please provide the number you want to put in this field.");
		int num = Integer.valueOf(number);
		
		if (num <0||num >9||number.equals(null)) {
			JOptionPane.showMessageDialog(null, "Invalid input "+ number);
			
		}else if (num ==0) {
			gameGrid.clearField(row, column);
			button.setText(null);
			
		}
		else if (gameGrid.setField(row, column, num)) {
			button.setText(number);
		}else {
			JOptionPane.showMessageDialog(null, "Your input is invalid here");
		}
		
		if (gameGrid.getFreeField() ==0) {
			JOptionPane.showMessageDialog(null, "Congratulations!!!!!!!!!!!!!!!!!");
		}
		
		
		
		
	
		
	}

}
