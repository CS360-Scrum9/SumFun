import controller.SumFunController;

import java.io.IOException;

import model.MoveCounter;
import model.ObservableTile;
import model.Scoring;
import model.TileQueue;
import view.BoardView;
import view.HighScoreBoard;

public class Driver {
	/**
	 * Used to start the game.
	 * @param args Any arguments passed when calling the program.
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		HighScoreBoard highScores = new HighScoreBoard("Untimed");
		ObservableTile[][] tiles = new ObservableTile[11][11];
		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 11; j++){
				tiles[i][j] = new ObservableTile(i,j);
			}
		}
		TileQueue tileQ = TileQueue.getInstance();
		Scoring score = Scoring.getInstance();
		MoveCounter mc = MoveCounter.getInstance();
		BoardView board = new BoardView(score,tileQ,tiles,mc);
		SumFunController control = SumFunController.getController();
		control.instantiateSumFunController(score,tileQ,tiles,mc,board,highScores);
		board.setVisible(true);
	}
}
