package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import model.*;

public class BoardView extends JFrame implements Observer{
	
	
	// private JPanel pnlMain;
	private JPanel pnlGrid;
	private JPanel pnlQueue;
	private JPanel pnlNorth;
	// private JPanel pnlWest;
	
	private JButton queueRefresh;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newGameItem;
	private JMenuItem exitItem;
	private JRadioButtonMenuItem timedItem;
	private JRadioButtonMenuItem untimedItem;
	
	private ButtonGroup buttonGroup;
	
	private JLabel[] queueTiles;
	private JLabel queueTitle;
	private JLabel scoreLabel;
	private JLabel lblCounter;
	private JLabel lblMoveCounter;
	private JLabel lblTimer;
	private JLabel lblCountdown;
	
	private Scoring score;
	private TileQueue tileQ;
	private ObservableTile[][] tiles;
	private Tile[][] tileButtons;
	private MoveCounter mc;
	private TimedGamemode timedMode;
	
	
	
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
		lblMoveCounter = new JLabel("" + mc.getMoveCount(), SwingConstants.CENTER);
		
		lblTimer = new JLabel("Time Left:  ", SwingConstants.CENTER);
		lblCountdown = new JLabel("5:00", SwingConstants.CENTER);
		
		// pnlMain = new JPanel();
		pnlGrid = new JPanel();
		pnlQueue = new JPanel();
		pnlNorth = new JPanel();
		// pnlWest = new JPanel();
		
		// gl = new GridLayout(1,1);
		// pnlWest.setLayout(gl);
		
		pnlGrid.setLayout(new GridLayout(9, 9));
		pnlNorth.setLayout(new GridLayout(1, 3));
		pnlQueue.setLayout(new GridLayout(7, 1));
		
		tileButtons = new Tile[11][11];
		
		//Adds tiles to the grid
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				tileButtons[i][j] = tiles[i][j].getTile();
				tiles[i][j].addObserver(this);
				if(tiles[i][j].isOccupied()) {
					tileButtons[i][j].setText("" + tiles[i][j].getNumber());
				}
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
		queueRefresh = new JButton("<html>Refresh<br>Queue</html>");
		queueTiles = new JLabel[5];
		queueTitle = new JLabel("Queue");
		pnlQueue.add(queueTitle);
		
		for(int i = 4; i >= 0; i--){
			queueTiles[i] = new JLabel("", SwingConstants.CENTER);
			if(i==4){
				queueTiles[i].setFont(new Font(queueTiles[i].getFont().getFontName(), Font.BOLD, 36));
			} else {
				queueTiles[i].setFont(new Font(queueTiles[i].getFont().getFontName(), Font.PLAIN, 12));
			}
			pnlQueue.add(queueTiles[i]);
		}
		
		pnlQueue.add(queueRefresh);
		queueTiles[4].setOpaque(true);
		updateQueue();
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		newGameItem = new JMenuItem("New Game");
		newGameItem.setMnemonic(KeyEvent.VK_N);
		newGameItem.setActionCommand("New");
		
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
	    exitItem.setActionCommand("Exit");
	    
		untimedItem = new JRadioButtonMenuItem("Untimed Game");
		untimedItem.setMnemonic(KeyEvent.VK_U);
	    untimedItem.setActionCommand("Untimed");
	    
		timedItem = new JRadioButtonMenuItem("Timed Game");
		timedItem.setMnemonic(KeyEvent.VK_T);
	    timedItem.setActionCommand("Timed");
	    
	    buttonGroup = new ButtonGroup();
	    buttonGroup.add(untimedItem);
	    buttonGroup.add(timedItem);
	    untimedItem.setSelected(true);
	    
	    fileMenu.add(newGameItem);
	    fileMenu.addSeparator();
	    fileMenu.add(timedItem);
	    fileMenu.add(untimedItem);
	    fileMenu.addSeparator();
	    fileMenu.add(exitItem);
	    
	    menuBar.add(fileMenu);
	    
	    
	    
	    
		
		//Add the panels to the frame
		this.add(pnlGrid, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlQueue, BorderLayout.EAST);
		this.setJMenuBar(menuBar);
		// this.add(pnlWest, BorderLayout.WEST);
	}
	
	public void switchGameModeView(int version){
		if(version == 1){
			this.remove(pnlNorth);
			pnlNorth = new JPanel();
			pnlNorth.setLayout(new GridLayout(1, 3));
			pnlNorth.add(lblTimer);
			pnlNorth.add(lblCountdown);
			pnlNorth.add(scoreLabel);
			this.add(pnlNorth, BorderLayout.NORTH);
			timedMode = TimedGamemode.getGamemode();
			timedMode.startTime(lblCountdown);
		} else{
			this.remove(pnlNorth);
			pnlNorth = new JPanel();
			pnlNorth.setLayout(new GridLayout(1, 3));
			pnlNorth.add(lblCounter);
			pnlNorth.add(lblMoveCounter);
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
	}
	
	private void updateTile(Observable arg0){
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
	}
	
	private void updateQueue(){	
		int[] currentQ = tileQ.getCurrentQueue();
		for(int i = 4; i >= 0; i--) {
			queueTiles[i].setText("" + currentQ[i]);
		}
		
		if(tileQ.refreshIsEnabled()){
			queueRefresh.setEnabled(true);
		} else {
			queueRefresh.setEnabled(false);
		}
	}
	
	private void updateMoveCounter(){
		lblMoveCounter.setText("" + mc.getMoveCount());
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
	
	public void addMenuItemListener(ActionListener al){
		newGameItem.addActionListener(al);
		exitItem.addActionListener(al);
	}
	
	public void addRadioButtonListener(ActionListener al){
		untimedItem.addActionListener(al);
		timedItem.addActionListener(al);
	}

}
