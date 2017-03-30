package model;

import view.BoardView;;

public class TileBehavior {
	/**
	 * Create a grid of tiles, 11X11 so that all surrounding tiles can always be 
	 * checked without generating an out of bounds exception
	 */
	protected static Tile tiles[][] = new Tile[11][11];
	
	public static void addTile(int row, int column, BoardView.ButtonHandler b){
		tiles[row][column] = new Tile(row, column);
		if(row > 0 && row < 10 && column > 0 && column < 10 && b != null){
			tiles[row][column].addActionListener(b);
		}
	}
	
	public static Tile getTile(int row, int column){
		return tiles[row][column];
	}
	
	public static boolean placeTile(Tile tile, int qValue, Scoring score){
		if(!hasNeighbors(tile)){
			return false;
		}else if(checkNeighbors(tile, qValue)){
			score.setScore(tiles, tile);
			resetNeighbors(tile);
		} else {
			tile.setNumber(qValue);
			tile.setText("" + qValue);
		}
		return true;
	}
	
	protected static void resetNeighbors(Tile tile){
		int row, column;
		row = tile.getRow();
		column = tile.getColumn();
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				tiles[row+i][column+j].setText("");
				tiles[row+i][column+j].setNumber(0);
			}
		}
		
	}
	
	protected static boolean checkNeighbors(Tile tile, int n){
		int row, column, number = n;
		row = tile.getRow();
		column = tile.getColumn();

		//Add the numbers from all surrounding tiles
		number += tiles[row + 1][column].getNumber();
		number += tiles[row + 1][column + 1].getNumber();
		number += tiles[row + 1][column - 1].getNumber();
		number += tiles[row][column + 1].getNumber();
		number += tiles[row][column - 1].getNumber();
		number += tiles[row - 1][column].getNumber();
		number += tiles[row - 1][column + 1].getNumber();
		number += tiles[row - 1][column - 1].getNumber();
		return number%10 == n;
	}
	
	protected static boolean hasNeighbors(Tile tile){
		int row, column;
		row = tile.getRow();
		column = tile.getColumn();
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(tiles[row+i][column+j].getNumber() > 0)
					return true;
			}
		}
		return false;
	}
}
