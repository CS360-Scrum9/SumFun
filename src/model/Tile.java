package model;

import java.util.Random;
import javax.swing.JButton;

public class Tile extends JButton {

	private int number, row, column;
	
	
	public Tile(int row, int column) {
		Random rand = new Random();
		this.row = row;
		this.column = column;

		//Populate only a 7X7 grid of tiles with numbers
		if (row > 1 && row < 9 && column > 1 && column < 9) {
			number = rand.nextInt(10);
			this.setText(Integer.toString(number));
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
	// endregion
	
	/*public static Tile getTile() {
		
		Tile tile = null;
		
		if (nTiles < MAXTILES) {
			
			tile = new Tile();
			nTiles++;
		}
		
		return tile;
	
	}
	*/
	/*
	private boolean getSumOfDigits() {
		int first = nTiles % 10;
		int second = (nTiles / 10);
		if (first + second == 8) {
			return true;
		}
		return false;
	}
	*/
	
}
