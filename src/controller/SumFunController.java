package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.*;
import view.BoardView;

public class SumFunController {
	
	private Scoring score;
	private TileQueue tileQ;
	private ObservableTile[][] tiles;
	private BoardView board;
	private MoveCounter mc;
	
	public SumFunController(){}
	
	public SumFunController(Scoring score, TileQueue tileQ, 
			ObservableTile[][] tiles, MoveCounter mc, BoardView board){
		this.score = score;
		this.tileQ = tileQ;
		this.tiles = tiles;
		this.board = board; 
		this.mc = mc;
		
		board.addButtonHandler(new ButtonHandler());
	}

	// Should we have a different listener for the tiles?
	public class ButtonHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().matches("<html>Refresh<br>Queue</html>")){
				JButton qRefresh = (JButton) e.getSource();
				qRefresh.setEnabled(false);
				tileQ.reset();
			} else {
				int row, column;
				Tile tile = (Tile) e.getSource();
				row = tile.getRow();
				column = tile.getColumn();
				SumFunController control = new SumFunController();
				// System.out.println("nextValue(): " + tileQ.getNextValue());
			
				if(!tile.isOccupied()){
					placeTile(tiles[row][column], tileQ.getNextValue());
					tileQ.dequeue();
					mc.decrementCount();
			}
			}
			
		
		}	
	}
	

	/**
	 * Checks the sum of the next number with all the values of each of the neighboring tiles
	 * and compares that sum to see if sum modular 10 equals the number (n) in the queue.
	 * @param tile The origin tile whose neighbors are to be checked.
	 * @return Boolean saying if the move was successful.
	 */
	public boolean checkNeighbors(ObservableTile tile, int qValue){
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
		return sum%10 == qValue;
	}
	
	/**
	 * Resets the values of all neighbor tiles. Only call if checkNeighbors() is true.
	 * Also sets the score from resetting neighbors if three or more neighbors are 
	 * reset.
	 * @param tile The tile whose neighbors will be reset.
	 */
	public void resetNeighbors(ObservableTile tile){
		int row, column, count = 0, newScore;
		row = tile.getRow();
		column = tile.getColumn();
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(!(i == 0 && j == 0) && tiles[row+i][column+j].isOccupied())
					count++;
				tiles[row + i][column + j].setOccupied(false);
				tiles[row+i][column+j].setNumber(0);
			}
		}
		if(count >= 3){
			newScore = score.getScore() + count * 10;
			score.setScore(newScore);
		}
	}
	
	/**
	 * Called when a player attempts to place a tile on the board. 
	 * Checks neighbor values and either removes the tiles, or adds the new one.
	 * @param tile Where the player is trying to place the tile.
	 * @param qValue The value of the next tile in queue to be placed.
	 */
	public void placeTile(ObservableTile tile, int qValue){
		if(checkNeighbors(tile, qValue))
			resetNeighbors(tile);
		 else{ 
			tile.setOccupied(true);
			tile.setNumber(qValue);
	
		 }
	}
}