package model;

import java.util.Observable;

import view.BoardView;;

public class TileBehavior extends Observable{
	/**
	 * Make into a singleton class.
	 */
	private static TileBehavior tb = new TileBehavior();
	private TileBehavior(){}
	/**
	 * Get the object reference to the TileBehavior class.
	 * @return the singleton TileBehavior object.
	 */
	public static TileBehavior getTileBehavior(){
		return tb;
	}
	/**
	 * Create a grid of tiles, 11X11 so that all surrounding tiles can always be 
	 * checked without generating an out of bounds exception
	 */
	protected Tile tiles[][] = new Tile[11][11];
	/**
	 * Adds a tile to the array.
	 * @param row Row where the tile is on the board.
	 * @param column Column where the tile is on the board.
	 * @param b Listener attached to the tile to initiate action when clicked.
	 */
	public void addTile(int row, int column, BoardView.ButtonHandler b){
		tiles[row][column] = new Tile(row, column);
		if(row > 0 && row < 10 && column > 0 && column < 10 && b != null){
			tiles[row][column].addActionListener(b);
		}
	}
	/**
	 * Get the tile at the given position.
	 * @param row Row of the tile on the board.
	 * @param column Column of the tile on the board.
	 * @return The tile and the location.
	 */
	public Tile getTile(int row, int column){
		return tiles[row][column];
	}
	/**
	 * Called when a player attempts to place a tile on the board. 
	 * If the tile already has a value, or has no neighbors, no action is taken.
	 * Then checks neighbor values and either removes the tiles, or adds the new one.
	 * @param tile Where the player is trying to place the tile.
	 * @param qValue The value of the next tile in queue to be placed.
	 * @param score Scoring object being used to keep the player's score.
	 * @return Boolean value saying if an action was taken.
	 */
	public void placeTile(Tile tile, int qValue, Scoring score){
		if(checkNeighbors(tile, qValue)){
			score.setScore(tile);
			resetNeighbors(tile);
		} else {
			tile.setNumber(qValue);
			tile.setText("" + qValue);
		}
	}
	/**
	 * Checks the sum of the next number with all the values of each of the neighboring tiles
	 * and compares that sum to see if sum modular 10 equals the number (n) in the queue.
	 * @param tile The origin tile whose neighbors are to be checked.
	 * @param n The value of the next tile in the queue.
	 * @return Boolean saying if the move was successful.
	 */
	protected boolean checkNeighbors(Tile tile, int n){
		int row, column, sum = 0;
		row = tile.getRow();
		column = tile.getColumn();

		//Add the numbers from all surrounding tiles
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(i != 0 || j != 0)
					sum += tiles[row+i][column+j].getNumber();
			}
		}
		return sum > 0 && sum%10 == n;
	}
	/**
	 * Resets the values of all neighbor tiles. Only call if checkNeighbors() is true.
	 * @param tile The origin tile whose neighbors will be reset.
	 */
	protected void resetNeighbors(Tile tile){
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
}
