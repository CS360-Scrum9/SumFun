package model;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JButton;

public class Tile extends JButton{

	private int number;
	private int row;
	private int column;
	private boolean isOccupied;
	
	// Constructor
	public Tile(int row, int column) {
		this.row = row;
		this.column = column;
		this.isOccupied = false;
		populateGrid();
	}
	
	//Populate only a 7X7 grid of tiles with numbers
	public void populateGrid(){
		Random rand = new Random();
		if (row > 1 && row < 9 && column > 1 && column < 9){
			setOccupied(true);
			setNumber(rand.nextInt(10));
		} else {
			setOccupied(false);
			setNumber(0);
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
		if(isOccupied){
			this.setEnabled(false);
		} else {
			this.setEnabled(true);
		}
		this.isOccupied = isOccupied;
	}
	// endregion
}
