package model;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JButton;

public class Tile extends JButton{

	private int number, row, column;
	private boolean isOccupied;
	
	// Constructor
	public Tile(int row, int column) {
		Random rand = new Random();
		this.row = row;
		this.column = column;
		this.isOccupied = false;

		//Populate only a 7X7 grid of tiles with numbers
		if (row > 1 && row < 9 && column > 1 && column < 9){
			this.setNumber(rand.nextInt(10));
			this.setOccupied(true);
		}
	}
		
	
	// region: Getter and Setter methods.
	public int getNumber() {
		return number;
	}
	public int getRow() {
		return row;
	}
	public int getColumn(){
		return column;
	}
	public void setNumber(int number){
		this.number = number;
	}
	
	public boolean isOccupied(){
		return isOccupied;
	}
	
	public void setOccupied(boolean isOccupied){
		this.isOccupied = isOccupied;
	}
	// endregion
}
