package model;

import java.util.Random;
import javax.swing.JButton;

public class Tile extends JButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private int number;
	private int row;
	private int column;
	private boolean isOccupied;
	private boolean flash;
	private boolean reset;
	
	// Constructor
	public Tile(int row, int column) {
		this.row = row;
		this.column = column;
		this.isOccupied = false;
		this.flash = false;
		populateGrid();
	}
	
	//Populate only a 7X7 grid of tiles with numbers
	public void populateGrid(){
		this.setReset(true);
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
	public void setFlash(boolean flash){
		this.flash = flash;
	}
	public boolean isFlash(){
		return this.flash;
	}
	
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
		this.setEnabled(!isOccupied);
		this.isOccupied = isOccupied;
	}
	// endregion

	/**
	 * @return the reset
	 */
	public boolean isReset() {
		return reset;
	}

	/**
	 * @param reset the reset to set
	 */
	public void setReset(boolean reset) {
		this.reset = reset;
	}
}
