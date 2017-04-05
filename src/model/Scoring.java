package model;

import java.util.Observable;

public class Scoring extends Observable{
	protected int playerScore;
	private TileBehavior tb = TileBehavior.getTileBehavior();
	
	public Scoring(){
		playerScore = 0;
	}
	/**
	 * Changes the score if the player placed a tile the removed three or more tiles.
	 * @param tile The origin tile the the player placed. 
	 */
	protected void setScore(Tile tile){
		int row, column, count = 0;
		row = tile.getRow();
		column = tile.getColumn();
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(!(i == 0 && j == 0) && tb.tiles[row+i][column+j].getText() != "")
					count++;
			}
		}
		if(count >= 3)
			playerScore+= count * 10;
	}
	@Override
	public String toString(){
		return " Score: " + playerScore + " ";
	}
}
