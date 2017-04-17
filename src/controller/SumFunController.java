package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.*;
import view.BoardView;
import view.HighScoreBoard;

public class SumFunController {
	
	private Scoring score;
	private TileQueue tileQ;
	private ObservableTile[][] tiles;
	private BoardView board;
	private HighScoreBoard highScoreBoard;
	private MoveCounter mc;
	private FileHandler fileHandler;
	private TimedGamemode gamemode;
	private boolean timed;
	
	public SumFunController(){}
	
	public SumFunController(Scoring score, TileQueue tileQ, 
			ObservableTile[][] tiles, MoveCounter mc, BoardView board, HighScoreBoard highScoreBoard){
		this.score = score;
		this.tileQ = tileQ;
		this.tiles = tiles;
		this.board = board; 
		this.mc = mc;
		
		timed = false;
		fileHandler = new FileHandler();
		
		gamemode = TimedGamemode.getGamemode();
		this.highScoreBoard = highScoreBoard;
		
		board.addTileButtonHandler(new TileButtonHandler());
		board.addRefreshButtonHandler(new RefreshButtonHandler());
		board.addRadioButtonListener(new RadioButtonListener());
		board.addMenuItemListener(new MenuItemListener());
	}

	private class TileButtonHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int row; 
			int column;
			Tile tile = (Tile) e.getSource();
			row = tile.getRow();
			column = tile.getColumn();
					
			if(!tile.isOccupied()){
				placeTile(tiles[row][column], tileQ.getNextValue());
				checkGameOver(); 
			}
		}	
	}
	
	private class RefreshButtonHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			tileQ.reset();
			tileQ.setRefreshIsEnabled(false);
		}
	}
	
	private class MenuItemListener implements ActionListener {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("New")){
				if(timed == true) {
					resetGame(1);
				} else {
					resetGame(0);
				}
			} else {
				System.exit(0);
			}
		}
	}
	
	private class RadioButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Timed")) {
				timed = true;
			} else {
				timed = false;
			}
		}
	}
	

	/**
	 * Checks the sum of the next number with all the values of each of the neighboring tiles
	 * and compares that sum to see if sum modular 10 equals the number (qValue) in the queue.
	 * @param tile The origin tile whose neighbors are to be checked.
	 * @return Boolean saying if the move was successful.
	 */
	public boolean checkNeighbors(ObservableTile tile, int qValue){
		int row = 0;
		int column = 0;
		int sum = 0;
		row = tile.getRow();
		column = tile.getColumn();

		//Add the numbers from all surrounding tiles
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(i != 0 || j != 0) {
					sum += tiles[row+i][column+j].getNumber();
				}
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
		int row = 0;
		int column = 0;
		int count = 0; 
		int newScore;
		row = tile.getRow();
		column = tile.getColumn();
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(!(i == 0 && j == 0) && tiles[row+i][column+j].isOccupied()) {
					count++;
				}
				tiles[row + i][column + j].setOccupied(false);
				tiles[row+i][column+j].setNumber(0);
			}
		}
		mc.setTileCount(mc.getTileCount() - count);
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
		if(checkNeighbors(tile, qValue)) {
			resetNeighbors(tile);
		} else { 
			tile.setOccupied(true);
			tile.setNumber(qValue);
			mc.setTileCount(mc.getTileCount() + 1);
		 }
	}
	
	
	private void resetGame(int version){
		for(int i = 1; i < 10; i++){
			for(int j = 1; j < 10; j++){
				tiles[i][j].resetTile();
			}
		}
		tileQ.reset();
		mc.setTileCount(49);
		score.setScore(0);
		board.switchGameModeView(version);
		highScoreBoard.setVisible(false);
		
		if (version == 1) {
			gamemode.setTime(300);
		} else {
			mc.setMoveCount(50);
		}
	}
	
	private void checkGameOver(){
		int optionNumber = 10;
		
		tileQ.dequeue();
		
		if(mc.getTileCount() >= 81){
			gameOver("Game Over! All tiles are occupied! New Game?");
		} else if(mc.getTileCount() <= 0){
			
			if (timed) {
				gamemode.stopTimer();
			}
			if (fileHandler.isHighScore(score.getScore(), timed)) {
				String name = JOptionPane.showInputDialog(null, "Congratulations! New High Score!  Please enter your name");
				if (timed) {
					fileHandler.addScore(name, gamemode.getTime(), score.getScore(), timed);
				} else {
					fileHandler.addScore(name, Integer.toString(mc.getMoveCount()) , score.getScore(), timed);
				}
				if (timed) {
					highScoreBoard.generateView("Timed");
				} else {
					highScoreBoard.generateView("Untimed");
				}
				highScoreBoard.setVisible(true);
			}
			
			Object[] o = {"Yes!", "No, I want to quit the game."};
			optionNumber = JOptionPane.showOptionDialog(null, "Congratulations! You win! New Game?",
					"Sum Fun", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, o, o[1]);
			
		}
		
		if(timed == false){
			mc.decrementCount();
			
			if(mc.getMoveCount() <= 0){
				gameOver("Game Over! You ran out of moves! New Game?");
			}
		}
		
		reset(optionNumber);
		
	}
	
	public void gameOver(String message) {
		int optionNumber;
		
		Object[] o = {"Yes!", "No, I want to quit the game."};
		optionNumber = JOptionPane.showOptionDialog(null, "Game Over! You ran out of moves! New Game?",
				"Sum Fun", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null, o, o[1]);
		
		reset(optionNumber);
	}
	
	public void reset(int optionNumber) {
		if(optionNumber == JOptionPane.YES_OPTION){
			if(timed == true){
				resetGame(1);
			} else{
				resetGame(0);
			}
		} else if(optionNumber == JOptionPane.NO_OPTION) {
			System.exit(0);	
		}
	}
	
}