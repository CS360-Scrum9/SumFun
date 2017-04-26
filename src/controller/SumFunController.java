package controller;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.FileHandler;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.MoveCounter;
import model.ObservableTile;
import model.Scoring;
import model.Tile;
import model.TileQueue;
import model.TimedGamemode;

import view.BoardView;
import view.HighScoreBoard;

public class SumFunController {
	
	private static SumFunController sfc = new SumFunController();
	
	public static SumFunController getController(){
		return sfc;
	}
	
	private Scoring score;
	private TileQueue tileQ;
	private ObservableTile[][] tiles;
	private BoardView board;
	private HighScoreBoard highScoreBoard;
	private MoveCounter mc;
	private FileHandler fileHandler;
	private TimedGamemode gamemode;
	private boolean timed;
	private boolean canclick;
	private boolean clearTilesUsed;
	private boolean clearTiles;
	private int neighborCount;
	private int hintCount;
	
	private SumFunController(){}
	
	public void instantiateSumFunController(Scoring score, TileQueue tileQ, 
			ObservableTile[][] tiles, MoveCounter mc, BoardView board, 
			HighScoreBoard highScoreBoard, TimedGamemode gamemode) throws SecurityException, IOException{
		this.score = score;
		this.tileQ = tileQ;
		this.tiles = tiles;
		this.board = board; 
		this.mc = mc;
		
		hintCount = 3;
		clearTilesUsed = false;
		clearTiles = false;
		this.canclick = true;
		this.timed = false;
		this.fileHandler = new FileHandler();
		
		this.gamemode = gamemode;
		this.highScoreBoard = highScoreBoard;
		
		this.board.addTileButtonHandler(new TileButtonHandler());
		this.board.addRefreshButtonHandler(new RefreshButtonHandler());
		this.board.addHintButtonHandler(new HintButtonHandler());
		this.board.addRemoveButtonHandler(new RemoveButtonHandler());
		this.board.addNewTimedGameButtonHandler(new NewTimedGameButtonHandler());
		this.board.addNewUntimedGameButtonHandler(new NewUntimedGameButtonHandler());
	}

	private class TileButtonHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(canclick){
				int row; 
				int column;
				Tile tile = (Tile) e.getSource();
				row = tile.getRow();
				column = tile.getColumn();
				 					
				if(!tile.isOccupied()){
				 	placeTile(tiles[row][column], tileQ.getNextValue());
				}else if(clearTilesUsed == false){
					clearAllTilesWithNumber(tile.getNumber());
					toggleTiles();
				}
			}
		}	
	}
	
	private class RefreshButtonHandler implements ActionListener {
		
		private JButton button;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			button = (JButton) e.getSource();
			button.setEnabled(false);
			
			Timer randomizer = new Timer(10, new ActionListener(){
			private int count = 0;
		    private int maxCount = 50;
				 		    
				public void actionPerformed(ActionEvent e){
					if (count >= maxCount) {
						((Timer) e.getSource()).stop();
					} else {
						tileQ.reset();
						count++;
					}
				}
			});
			randomizer.start();
		}
	}
	
	private class HintButtonHandler implements ActionListener {
		
		private JButton button;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(hintCount > 0){
				useHint();
				button = (JButton) e.getSource();
				hintCount--;
				button.setText("Hint (" + hintCount + ")");
				if(hintCount < 1){
					button.setEnabled(false);
				}

			}
		}
	}
	
	private class RemoveButtonHandler implements ActionListener {
		
		private JButton button;
		
		@Override
		public void actionPerformed(ActionEvent e){
			toggleTiles();
			button = (JButton) e.getSource();
			button.setEnabled(false); 
		}
	}
	
	private class NewTimedGameButtonHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e){
			timed = true;
			resetGame(1); 
		}
	}
	
	
	private class NewUntimedGameButtonHandler implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e){
			timed = false;
			resetGame(0); 
		}
	}
	
	/**
	 * Checks the sum of the next number with all the values of each of the neighboring tiles
	 * and compares that sum to see if sum modular 10 equals the number (qValue) in the queue.
	 * @param row The row of the tile in question. 1 <= row <= 9
	 * @param column The column of the tile in question. 1 <= column <= 9
	 * @param queueValue The value of the number being placed or that is in the tile in question. 0 <= queueValue <= 9
	 * @return
	 */
	public boolean checkNeighbors(ObservableTile[][] tiles, int row, int column, int queueValue){
		int sum = 0;
		neighborCount = 0;

		//Add the numbers from all surrounding tiles
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				if(!(i == 0 && j == 0) && tiles[row+i][column+j].isOccupied()) {
					neighborCount++;
					sum += tiles[row+i][column+j].getNumber();
				}
			}
		}
		return sum%10 == queueValue;
	}
	
	/**
	 * Resets the values of all neighbor tiles. Only call if checkNeighbors() is true.
	 * Also sets the score from resetting neighbors if three or more neighbors are 
	 * reset.
	 * @param row The row of the tile in question. 1 <= row <= 9
	 * @param column The column of the tile in question. 1 <= column <= 9
	 */
	public void resetNeighbors(int row, int column){
		int newScore;
		for(int i = -1; i < 2; i++){
			for(int j = -1; j < 2; j++){
				tiles[row + i][column + j].setOccupied(false);
				tiles[row+i][column+j].setNumber(0);
			}
		}
		mc.setTileCount(mc.getTileCount() - neighborCount);
		if(neighborCount >= 3){
			newScore = score.getScore() + neighborCount * 10;
			score.setScore(newScore);
		}
		checkGameOver();
	}
	
	/**
	 * Called when a player attempts to place a tile on the board. 
	 * Checks neighbor values and either removes the tiles, or adds the new one.
	 * @param tile Where the player is trying to place the tile.
	 * @param qValue The value of the next tile in queue to be placed.
	 */
	public void placeTile(ObservableTile tile, int queueValue){
		canclick = false;
		if(checkNeighbors(this.tiles, tile.getRow(), tile.getColumn(), queueValue)) {
			tile.setOccupied(true);
			tile.setNumber(queueValue);
			Timer greenFlash = new Timer(200, new ActionListener(){
			 	private int count = 0;
			 	private int maxCount = 4;
			 	private boolean on = false;
			 	private int row = tile.getRow();
			 	private int col = tile.getColumn();
			 
			 	public void actionPerformed(ActionEvent e) {
			 		if (count >= maxCount) {
			 			for(int i = -1; i < 2; i++){
			 			    for(int j = -1; j < 2; j++){
			 			    	if(tiles[row+i][col+j].isOccupied()) {
			 			    		tiles[row+i][col+j].getTile().setBackground(null);
			 			        }
			 			    }
			 			}
			 			((Timer) e.getSource()).stop();
			 			resetNeighbors(tile.getRow(), tile.getColumn());
			 			canclick = true;
			        } else {
			 	    	for(int i = -1; i < 2; i++){
			    			for(int j = -1; j < 2; j++){
			 			    	if(tiles[row+i][col+j].isOccupied()) {
			 						tiles[row+i][col+j].getTile().setBackground( on ? Color.GREEN : null);
			    				}
			    			}
			    		}
 			            on = !on;
			            count++;
           	        }
			 	}
			 });
			 greenFlash.start();
		} else { 
			tile.setOccupied(true);
			tile.setNumber(queueValue);
			Timer redFlash = new Timer(200, new ActionListener(){
				 private int count = 0;
				 private int maxCount = 4;
				 private boolean on = false;
				 
				 public void actionPerformed(ActionEvent e) {
				 	if (count >= maxCount) {
				 		tile.getTile().setBackground(null);
				 		((Timer) e.getSource()).stop();
				 		canclick = true;
				    } else {
				 		tile.getTile().setBackground( on ? Color.RED : null);
				 		on = !on;
				 		count++;
				 	}
				 }
			});
			redFlash.start();
			mc.setTileCount(mc.getTileCount() + 1);
			checkGameOver();
		 }
	}
	
	
	private void resetGame(int version){
		for(int i = 1; i < 10; i++){
			for(int j = 1; j < 10; j++){
				tiles[i][j].resetTile();
			}
		}
		hintCount = 3;
		tileQ.reset();
		mc.setTileCount(49);
		score.setScore(0);
		board.switchGameModeView(version);
		highScoreBoard.setVisible(false);
		clearTilesUsed = false;
		gamemode.setTime(300);
		mc.setMoveCount(50);	
	}
	
	private void checkGameOver(){
		int optionNumber = 10;
		
		if(clearTiles == false){
			tileQ.dequeue();
		}
		
		if(mc.getTileCount() >= 81 && clearTilesUsed == true){
			gameOver("Game Over! All tiles are occupied! New Game?", JOptionPane.ERROR_MESSAGE);
		} else if(mc.getTileCount() <= 0){
			if (timed) {
				gamemode.stopTimer();
			}
			/*if (( fileHandler).isHighScore(score.getScore(), timed)) {
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
			}*/
			
			gameOver("Congratulations! You win! New Game?", JOptionPane.PLAIN_MESSAGE);
			
		}
		
		if(timed == false){
			if(clearTiles == false){
				mc.decrementCount();
			}
			
			if(mc.getMoveCount() <= 0){
				gameOver("Game Over! You ran out of moves! New Game?", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		reset(optionNumber);
		clearTiles = false;
		
	}
	
	public void gameOver(String message, int icon) {
		int optionNumber;
		
		Object[] o = {"Yes!", "No, I want to quit the game."};
		optionNumber = JOptionPane.showOptionDialog(null, message,
				"Sum Fun", JOptionPane.YES_NO_OPTION, icon, null, o, o[1]);
		
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
	
	private void clearAllTilesWithNumber(int n){
		int count = 0;
		for(int i = 1; i < 10; i++){
			for(int j = 1; j < 10; j++){
				if(tiles[i][j].getNumber() == n){
					count++;
					tiles[i][j].setOccupied(false);
					tiles[i][j].setNumber(0);
				}
			}
		}
		
		mc.setTileCount(mc.getTileCount() - count);
		clearTilesUsed = true;
		clearTiles = true;
		checkGameOver();
		
	}
	
	private void useHint(){
		neighborCount = 0;
		int maxCount = 0;
		int row = 0;
		int column = 0;
		for(int i = 1; i < 10; i++){
			for(int j = 1; j < 10; j++){
				if(!tiles[i][j].isOccupied() && checkNeighbors(this.tiles,
						tiles[i][j].getRow(), tiles[i][j].getColumn(),
						tileQ.getNextValue())){
					if(neighborCount > maxCount){
						maxCount = neighborCount;
						row = i;
						column = j;
					}
				}
			}
		}
		
		if(maxCount > 0){
			tiles[row][column].startFlash();
			tiles[row][column].stopFlash();
		}
	}
	
	private void toggleTiles(){
		for(ObservableTile[] g : tiles){
			for(ObservableTile t : g){
				t.toggleEnable(clearTilesUsed);
			}
		}
	}
}
