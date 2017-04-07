import controller.SumFunController;
import model.MoveCounter;
import model.ObservableTile;
import model.Scoring;
import model.TileQueue;
import view.BoardView;

import controller.SumFunController;
import model.*;
import view.BoardView;

public class Driver {
	/**
	 * Used to start the game.
	 * @param args Any arguments passed when calling the program.
	 */
	public static void main(String[] args) {
		
		ObservableTile[][] tiles = new ObservableTile[11][11];
		for(int i = 0; i < 11; i++){
			for(int j = 0; j < 11; j++){
				tiles[i][j] = new ObservableTile(i,j);
			}
		}
		TileQueue tileQ = new TileQueue();
		Scoring score = new Scoring();
		MoveCounter mc = new MoveCounter();
		BoardView board = new BoardView(score,tileQ,tiles,mc);
		SumFunController control = new SumFunController(score,tileQ,tiles,mc,board);
		board.setVisible(true);
	}
}
