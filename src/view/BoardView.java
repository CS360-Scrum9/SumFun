package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
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
	private Timer time;
	private boolean stopTime;
	private int hintCount;
	
	
	
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
		
	
		pnlGrid = new JPanel();
		pnlQueue = new JPanel();
		pnlNorth = new JPanel();
		pnlSouth = new JPanel();
		
		pnlGrid.setLayout(new GridLayout(9, 9));
		pnlNorth.setLayout(new GridLayout(1, 3));
		pnlQueue.setLayout(new GridLayout(7, 1));
		pnlSouth.setLayout(new GridLayout(1, 4));
		
		tileButtons = new Tile[11][11];
		
		//Adds tiles to the grid
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				tileButtons[i][j] = this.tiles[i][j].getTile();
				this.tiles[i][j].addObserver(this);
				if(this.tiles[i][j].isOccupied()) {
					tileButtons[i][j].setText("" + this.tiles[i][j].getNumber());
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
		
		hintCount = 3;
		hintButton = new JButton("Hint (" + hintCount + ")");
		pnlSouth.add(hintButton);
		
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
	    
	    
	    
	    
		
		
		this.add(pnlGrid, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlQueue, BorderLayout.EAST);
		this.add(pnlSouth, BorderLayout.SOUTH);
		this.setJMenuBar(menuBar);

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
		
		if(tile.isReset() && hintCount < 3){
			hintCount = 3;
			hintButton.setText("Hint (" + hintCount + ")");
			hintButton.setEnabled(true);
		}
		
		if(tile.doFlash()){
			time = new Timer(400, new FlashListener(tileButtons[row][column], Color.YELLOW));
			stopTime = false;
			time.start();
			hintCount--;
			hintButton.setText("Hint (" + hintCount + ")");
			if(hintCount < 1){
				hintButton.setEnabled(false);
			}
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
	
	public void addMenuItemListener(ActionListener al){
		newGameItem.addActionListener(al);
		exitItem.addActionListener(al);
	}
	
	public void addRadioButtonListener(ActionListener al){
		untimedItem.addActionListener(al);
		timedItem.addActionListener(al);
	}

	public void addHintButtonHandler(ActionListener al){
		hintButton.addActionListener(al);
	}
}
