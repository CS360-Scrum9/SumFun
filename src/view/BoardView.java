package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.*;

public class BoardView extends JFrame implements Observer{
	
	
	// private JPanel pnlMain;
	private JPanel pnlGrid;
	private JPanel pnlQueue;
	private JPanel pnlNorth;
	// private JPanel pnlWest;
	
	private JLabel[] qTiles;
	private JLabel qTitle;
	private JLabel scoreLabel;
	private JLabel lblCounter;
	private JLabel lblMoveCounter;
	
	private int moveCount = 50;
	
	private Scoring score;
	private TileQueue tileQ;
	private ObservableTile[][] tiles;
	private Tile[][] tileButtons;
	private MoveCounter mc;
	
	
	
	public BoardView(Scoring score, TileQueue tileQ, ObservableTile[][] tiles, MoveCounter mc) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700,700);
		setLayout(new BorderLayout());
		
		this.score = score;
		score.addObserver(this);
		this.tileQ = tileQ;
		tileQ.addObserver(this);
		this.tiles = tiles;
		this.mc = mc;
		mc.addObserver(this);
		
		lblCounter = new JLabel("Moves Left:  ", SwingConstants.CENTER);
		lblMoveCounter = new JLabel("" + mc.getCount(), SwingConstants.CENTER);
		
		// pnlMain = new JPanel();
		pnlGrid = new JPanel();
		pnlQueue = new JPanel();
		pnlNorth = new JPanel();
		// pnlWest = new JPanel();
		
		// gl = new GridLayout(1,1);
		// pnlWest.setLayout(gl);
		
		pnlGrid.setLayout(new GridLayout(9, 9));
		pnlNorth.setLayout(new GridLayout(1, 3));
		pnlQueue.setLayout(new GridLayout(6, 1));
		
		tileButtons = new Tile[11][11];
		
		//Adds tiles to the grid
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				tileButtons[i][j] = tiles[i][j].getTile();
				tiles[i][j].addObserver(this);
				if(tiles[i][j].isOccupied())
					tileButtons[i][j].setText("" + tiles[i][j].getNumber());
				if(i > 0 && i < 10 && j > 0 && j < 10){
					pnlGrid.add(tileButtons[i][j]);
				}
			}
		}

		scoreLabel = new JLabel(score.toString(), SwingConstants.CENTER);
		
		pnlNorth.add(lblCounter);
		pnlNorth.add(lblMoveCounter);
		pnlNorth.add(scoreLabel);
		// pnlWest.add(scoreLabel);
		
		//Add the queue tiles to the queue panel
		qTiles = new JLabel[5];
		qTitle = new JLabel("\u2193 Queue \u2193");
		pnlQueue.add(qTitle);
		
		for(int i = 4; i >= 0; i--){
			qTiles[i] = new JLabel();
			pnlQueue.add(qTiles[i]);
		}
		
		qTiles[4].setOpaque(true);
		qTiles[4].setBackground(Color.GREEN);
		updateQueue();
	
		//Add the panels to the frame
		this.add(pnlGrid, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlQueue, BorderLayout.EAST);
		// this.add(pnlWest, BorderLayout.WEST);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 == this.score)
			updateScore();
		else if(arg0 == this.tileQ)
			updateQueue();
		else if(arg0 == this.mc)
			updateMoveCounter();
		else
			updateTile(arg0);
		
	}
	
	public void updateScore(){
		scoreLabel.setText(score.toString());
	}
	
	public void updateTile(Observable arg0){
		int row, column;
		ObservableTile tile = (ObservableTile) arg0;
		row = tile.getRow();
		column = tile.getColumn();
		
		if(tile.isOccupied()){
			tileButtons[row][column].setText("" + tile.getNumber());
		}
		else{
			tileButtons[row][column].setText("");
		}
	}
	
	public void updateQueue(){	
		int[] currentQ = tileQ.getCurrentQueue();
		for(int i = 4; i >= 0; i--)
			qTiles[i].setText("" + currentQ[i]);
	}
	
	public void updateMoveCounter(){
		lblMoveCounter.setText("" + mc.getCount());
	}
	
	public void addButtonHandler(ActionListener bh){
		for(int i = 1; i < 10; i++){
			for(int j = 1; j < 10; j++)
				tileButtons[i][j].addActionListener(bh);
		}
	}
}
