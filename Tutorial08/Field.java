
public class Field {
	
	private int value;//Record the number in specific location of Sudoku grid
	private final boolean initial;// False if the value is changable true if not
	
	public Field() {
		this.value =0;
		this.initial =false;
	}
	public Field(int value, boolean initial) {
		this.value=value;
		this.initial = initial;
	}
	public void setValue(int value ) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public boolean getInitial() {
		return initial;
	}
	
}
