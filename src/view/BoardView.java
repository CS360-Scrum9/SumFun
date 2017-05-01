package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import model.MoveCounter;
import model.ObservableTile;
import model.Scoring;
import model.Tile;
import model.TileQueue;
import model.TimedGamemode;

public class BoardView extends JFrame implements Observer{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlGrid;
	private JPanel pnlQueue;
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	
	private JButton queueRefresh;
	private JButton hintButton;
	private JButton removeButton;
	private JButton timedGameButton;
	private JButton untimedGameButton;
	
	private JLabel[] queueTiles;
	private JLabel queueTitle;
	private JLabel scoreLabel;
	private JLabel lblCounter;
	private JLabel lblTimer;
	
	private Scoring score;
	private TileQueue tileQ;
	private ObservableTile[][] tiles;
	private Tile[][] tileButtons;
	private MoveCounter mc;
	private Timer time;
	private TimedGamemode gamemode;
	private boolean stopTime;
	private int hintCount;
	
	public BoardView(Scoring score, TileQueue tileQ, ObservableTile[][] tiles
			, MoveCounter mc, TimedGamemode gamemode) {
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
		this.gamemode = gamemode;
		
		ImageIcon icon = new ImageIcon("src/controller/img/icon.png");
		this.setIconImage(icon.getImage());
		this.setTitle("SumFun with Numbers! Learning Modularity");
		
		lblCounter = new JLabel("Moves Left: " + mc.getMoveCount(), SwingConstants.CENTER);
		lblTimer = new JLabel("Time Left: 5:00", SwingConstants.CENTER);
		
		pnlGrid = new JPanel();
		pnlQueue = new JPanel();
		pnlNorth = new JPanel();
		pnlSouth = new JPanel();
		
		pnlGrid.setLayout(new GridLayout(9, 9));
		pnlNorth.setLayout(new GridLayout(1, 2));
		pnlQueue.setLayout(new GridLayout(7, 1));
		pnlSouth.setLayout(new GridLayout(2, 2));
		
		tileButtons = new Tile[11][11];
		
		pnlGrid.setBackground(Color.ORANGE);
		pnlQueue.setBackground(Color.ORANGE);
		pnlNorth.setBackground(Color.ORANGE);
		pnlSouth.setBackground(Color.ORANGE);
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				tileButtons[i][j] = this.tiles[i][j].getTile();
				this.tiles[i][j].addObserver(this);
				if(this.tiles[i][j].isOccupied()) {
					tileButtons[i][j].setText("" + this.tiles[i][j].getNumber());
				}
				if(i > 0 && i < 10 && j > 0 && j < 10){
					tileButtons[i][j].setFont(new Font(tileButtons[i][j].getFont().getFontName(), Font.BOLD, 36));
					tileButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));;
					pnlGrid.add(tileButtons[i][j]);
				}
			}
		}

		scoreLabel = new JLabel(score.toString(), SwingConstants.CENTER);

		lblCounter.setFont(new Font(lblCounter.getFont().getFontName(), Font.ITALIC, 24));
		scoreLabel.setFont(new Font(scoreLabel.getFont().getFontName(), Font.ITALIC, 24));
		
		pnlNorth.add(lblCounter);
		pnlNorth.add(scoreLabel);
		
		queueRefresh = new JButton("<html>Refresh<br>Queue</html>");
		queueTiles = new JLabel[5];
		queueTitle = new JLabel("Queue", SwingConstants.CENTER);
		queueTitle.setFont(new Font(queueTitle.getFont().getFontName(), Font.ITALIC, 24));
		queueRefresh.setFont(new Font(queueTitle.getFont().getFontName(), Font.CENTER_BASELINE, 18));
		pnlQueue.add(queueTitle);
		
		for(int i = 4; i >= 0; i--){
			queueTiles[i] = new JLabel("", SwingConstants.CENTER);
			if(i==4){
				queueTiles[i].setFont(new Font(queueTiles[i].getFont().getFontName(), Font.BOLD, 36));
				queueTiles[i].setBackground(Color.ORANGE);
				queueTiles[i].setForeground(Color.DARK_GRAY);
			} else {
				queueTiles[i].setFont(new Font(queueTiles[i].getFont().getFontName(), Font.PLAIN, 20));
				queueTiles[i].setForeground(Color.GRAY);
			}
			pnlQueue.add(queueTiles[i]);
		}
		
		pnlQueue.add(queueRefresh);
		queueTiles[4].setOpaque(true);
		updateQueue();
		
		hintCount = 3;
		hintButton = new JButton("Hint (" + hintCount + ")");
		removeButton = new JButton("Remove a Number");
		timedGameButton = new JButton("New Timed Game");
		untimedGameButton = new JButton("New Untimed Game");
		
		hintButton.setFont(new Font(untimedGameButton.getFont().getFontName(), Font.PLAIN, 18));
		removeButton.setFont(new Font(untimedGameButton.getFont().getFontName(), Font.PLAIN, 18));
		timedGameButton.setFont(new Font(untimedGameButton.getFont().getFontName(), Font.PLAIN, 18));
		untimedGameButton.setFont(new Font(untimedGameButton.getFont().getFontName(), Font.PLAIN, 18));
		
		pnlSouth.add(hintButton);
		pnlSouth.add(removeButton);
		pnlSouth.add(untimedGameButton);
		pnlSouth.add(timedGameButton);
	    
		this.add(pnlGrid, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlQueue, BorderLayout.EAST);
		this.add(pnlSouth, BorderLayout.SOUTH);
	}
	
	public void switchGameModeView(int version){
		if(version == 1){
			this.remove(pnlNorth);
			pnlNorth = new JPanel();
			pnlNorth.setLayout(new GridLayout(1, 2));
			pnlNorth.add(lblTimer);
			pnlNorth.add(scoreLabel);
			this.add(pnlNorth, BorderLayout.NORTH);
			gamemode = TimedGamemode.getGamemode();
			gamemode.startTime(lblTimer);
		} else{
			this.remove(pnlNorth);
			pnlNorth = new JPanel();
			pnlNorth.setLayout(new GridLayout(1, 2));
			pnlNorth.add(lblCounter);
			pnlNorth.add(scoreLabel);
			this.add(pnlNorth, BorderLayout.NORTH);
		}
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 == this.score) {
			updateScore();
		} else if(arg0 == this.tileQ) { 
			updateQueue();
		} else if(arg0 == this.mc) { 
			updateMoveCounter();
		} else { 
			updateTile(arg0);
		}
	}
	
	private void updateScore(){
		scoreLabel.setText(score.toString());
		if(score.getScore() == 0){
			hintButton.setText("Hint (" + 3 + ")");
			hintButton.setEnabled(true);
			removeButton.setEnabled(true);
			queueRefresh.setEnabled(true);
		}
	}
	
	private void updateTile(Observable arg0){
		
		stopTime = true;
		int row;
		int column;
		ObservableTile tile = (ObservableTile) arg0;
		row = tile.getRow();
		column = tile.getColumn();
		if(tile.isOccupied()){
			tileButtons[row][column].setText("" + tile.getNumber());
		} else{
			tileButtons[row][column].setText("");
		}
		
		if(tile.doFlash()){
			hintCount--;
			time = new Timer(400, new FlashListener(tileButtons[row][column], Color.YELLOW));
			stopTime = false;
			time.start();
		}
	}
	
	private void updateQueue(){	
		int[] currentQ = tileQ.getCurrentQueue();
		for(int i = 4; i >= 0; i--) {
			queueTiles[i].setText("" + currentQ[i]);
		}
	}
	
	private void updateMoveCounter(){
		lblCounter.setText("Moves Left: " + mc.getMoveCount());
	}
	
	private class FlashListener implements ActionListener {
	    JButton button;
        Color color;
        Color originalColor;
		
		public FlashListener( JButton button, Color color ) {
	            this.button = button;
	            this.color = color;
	            this.originalColor = button.getBackground();
		}
		public void actionPerformed(ActionEvent e) {
			 if(stopTime){
				  button.setBackground( originalColor );
     	       	((Timer) e.getSource()).stop(); 
			 }else{
				 if( button.getBackground().equals( originalColor ) ) {
					 button.setBackground( color);
	              
	               
				 }else {
					 button.setBackground( originalColor );
	               }
			 }
		 }
	}
	
	public void addTileButtonHandler(ActionListener al){
		for(int i = 1; i < 10; i++){
			for(int j = 1; j < 10; j++) {
				tileButtons[i][j].addActionListener(al);
			}
		}
	}
	
	public void addRefreshButtonHandler(ActionListener al){
		queueRefresh.addActionListener(al);
	}

	public void addHintButtonHandler(ActionListener al){
		hintButton.addActionListener(al);
	}
	
	public void addRemoveButtonHandler(ActionListener al){
		removeButton.addActionListener(al);
	}
	
	public void addNewTimedGameButtonHandler(ActionListener al){
		timedGameButton.addActionListener(al);
	}
	
	public void addNewUntimedGameButtonHandler(ActionListener al){
		untimedGameButton.addActionListener(al);
	}
}
