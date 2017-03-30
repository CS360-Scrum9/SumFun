package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.*;

public class BoardView extends JFrame {
	
	private JLabel lblCounter;
	private JLabel lblMoveCounter;
	
	private JPanel pnlMain;
	private JPanel pnlGrid;
	private JPanel pnlQueue;
	private JPanel pnlNorth;
	// private JPanel pnlWest;
	
	private JLabel qTile1;
	private JLabel qTile2;
	private JLabel qTile3;
	private JLabel qTile4;
	private JLabel qTile5;
	private JLabel qTitle;
	
	private GridLayout gl;
	
	private Queue tileQueue;
	
	private int queueSize = 60;
	private int movecount = 50;
	
	private Scoring score;
	private JLabel scoreLabel;
	
	public BoardView() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700,700);
		setLayout(new BorderLayout());
		
		lblCounter = new JLabel("Moves Left:  ", SwingConstants.CENTER);
		lblMoveCounter = new JLabel("" + movecount, SwingConstants.CENTER);
		
		pnlMain = new JPanel();
		pnlGrid = new JPanel();
		pnlQueue = new JPanel();
		pnlNorth = new JPanel();
		// pnlWest = new JPanel();
		
		// gl = new GridLayout(1,1);
		// pnlWest.setLayout(gl);
		
		gl = new GridLayout(9, 9);
		pnlGrid.setLayout(gl);
		
		gl = new GridLayout(1, 3);
		pnlNorth.setLayout(gl);
		
		gl = new GridLayout(6, 1);
		pnlQueue.setLayout(gl);
		
		ButtonHandler bl = new ButtonHandler();
		
		//Adds tiles to the grid
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				TileBehavior.addTile(i, j, bl);
				if(i > 0 && i < 10 && j > 0 && j < 10){
					pnlGrid.add(TileBehavior.getTile(i, j));
				}
			}
		}

		score = new Scoring();
		scoreLabel = new JLabel(score.toString(), SwingConstants.CENTER);
		
		pnlNorth.add(lblCounter);
		pnlNorth.add(lblMoveCounter);
		pnlNorth.add(scoreLabel);
		// pnlWest.add(scoreLabel);
		
		//Create a queue instant for the queue tiles
		tileQueue = new Queue(queueSize);
		
		//Populate the queue with random numbers
		Random rand = new Random();
		for(int i = 0; i < queueSize; i++)
			tileQueue.enqueue(rand.nextInt(10));
		
		//Dequeue numbers from the queue to populate the queue tiles
		qTile1 = new JLabel(Integer.toString(tileQueue.dequeue()), SwingConstants.CENTER);
		qTile2 = new JLabel(Integer.toString(tileQueue.dequeue()), SwingConstants.CENTER);
		qTile3 = new JLabel(Integer.toString(tileQueue.dequeue()), SwingConstants.CENTER);
		qTile4 = new JLabel(Integer.toString(tileQueue.dequeue()), SwingConstants.CENTER);
		qTile5 = new JLabel(Integer.toString(tileQueue.dequeue()), SwingConstants.CENTER);
		qTitle = new JLabel("\u2193 Queue \u2193");
		qTile5.setOpaque(true);
		qTile5.setBackground(Color.GREEN);

		//Add the queue tiles to the queue panel
		pnlQueue.add(qTitle);
		pnlQueue.add(qTile5);
		pnlQueue.add(qTile4);
		pnlQueue.add(qTile3);
		pnlQueue.add(qTile2);
		pnlQueue.add(qTile1);
		
		//Add the panels to the frame
		this.add(pnlGrid, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlQueue, BorderLayout.EAST);
		// this.add(pnlWest, BorderLayout.WEST);
	}
	
	public class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Tile tile = (Tile) e.getSource();
			
			if(TileBehavior.placeTile(tile, Integer.parseInt(qTile5.getText()), score)){
				// Adjust the queue
				qTile5.setText(qTile4.getText());
				qTile4.setText(qTile3.getText());
				qTile3.setText(qTile2.getText());
				qTile2.setText(qTile1.getText());
				qTile1 = new JLabel(Integer.toString(tileQueue.dequeue()), SwingConstants.CENTER);
				// Lowers the move counter
				lblMoveCounter.setText("" + movecount--);
				// Update Score
				scoreLabel.setText(score.toString());
			}
		}	
	}
}
