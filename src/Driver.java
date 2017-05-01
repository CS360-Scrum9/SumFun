import controller.SumFunController;

import java.io.IOException;

import model.FileHandler;
import model.MoveCounter;
import model.ObservableTile;
import model.Scoring;
import model.TileQueue;
import model.TimedGamemode;
import view.BoardView;
import view.HighScoreBoard;

public class Driver {
	
	private static final int SIZE = 11;
	/**
	 * Used to start the game.
	 * @param args Any arguments passed when calling the program.
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		FileHandler fh = new FileHandler();
		fh.checkFiles();
		HighScoreBoard highScores = new HighScoreBoard("Untimed");
		ObservableTile[][] tiles = new ObservableTile[11][11];
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				tiles[i][j] = new ObservableTile(i,j,SIZE);
			}
		}
		SumFunController control = SumFunController.getController();
		TimedGamemode gamemode = TimedGamemode.getGamemode();
		gamemode.setController(control);
		TileQueue tileQ = TileQueue.getInstance();
		Scoring score = Scoring.getInstance();
		MoveCounter mc = MoveCounter.getInstance();
		BoardView board = new BoardView(score,tileQ,tiles,mc,gamemode);
		control.instantiateSumFunController(score,tileQ,tiles,mc,board,highScores,gamemode);
		board.setVisible(true);
	}
}
