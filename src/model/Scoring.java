package model;

public class Scoring {
	protected int playerScore;
	
	public Scoring(){
		playerScore = 0;
	}
	
	protected void setScore(Tile tiles[][], Tile tile){
		int row, column, count = 0;
		row = tile.getRow();
		column = tile.getColumn();
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(tiles[row+i][column+j].getNumber() > 0)
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
