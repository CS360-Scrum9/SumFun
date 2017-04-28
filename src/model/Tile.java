package model;

import java.awt.Color;
import java.util.Random;

import javax.swing.JButton;

public class Tile extends JButton{

	private static final long serialVersionUID = 4L;
	private int number;
	private int row;
	private int column;
	private int gridSize;
	private boolean isOccupied;
	private boolean flash;
	
	// Constructor
	public Tile(int row, int column, int size) {
		this.row = row;
		this.column = column;
		this.isOccupied = false;
		this.flash = false;
		this.setGridSize(size);
		this.populateGrid(size);
	}
	
	//Populate only a 7X7 grid of tiles with numbers
	public void populateGrid(int size){
		Random rand = new Random();
		
		if (row > 1 && row < size - 2 && column > 1 && column < size - 2){
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
		if(isOccupied){
			this.setBackground(Color.CYAN);
		} else {
			this.setBackground(Color.MAGENTA);
		}
		this.isOccupied = isOccupied;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
}
