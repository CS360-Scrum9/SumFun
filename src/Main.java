import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame {
	
	private JLabel lblCounter;
	private JLabel lblMoveCounter;
	
	private JPanel pnlMain;
	private JPanel pnlGrid;
	private JPanel pnlQueue;
	private JPanel pnlNorth;
	
	private JLabel lblTile1;
	private JLabel lblTile2;
	private JLabel lblTile3;
	private JLabel lblTile4;
	private JLabel lblTile5;
	
	private GridLayout gl;
	
	private Tile tiles[][];
	
	private Queue tileQueue;
	
	private int queueSize = 60;
	
	public Main() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,600);
		setLayout(new BorderLayout());
		
		lblCounter = new JLabel("Moves Left:  ");
		lblMoveCounter = new JLabel("50");
		
		pnlMain = new JPanel();
		pnlGrid = new JPanel();
		pnlQueue = new JPanel();
		pnlNorth = new JPanel();
		
		gl = new GridLayout(9, 9);
		pnlGrid.setLayout(gl);
		
		gl = new GridLayout(1, 2);
		pnlNorth.setLayout(gl);
		
		gl = new GridLayout(5, 1);
		pnlQueue.setLayout(gl);
		
		ButtonHandler bl = new ButtonHandler();
		
		//Create a grid of tiles, 11X11 so that all surrounding 
		//tiles can always be checked without generating an out 
		//of bounds exception
		tiles = new Tile[11][11];
		
		//Adds tiles to the grid
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				tiles[i][j] = new Tile(i,j);
				if(i > 0 && i < 10 && j > 0 && j < 10){
					tiles[i][j].addActionListener(bl);
					pnlGrid.add(tiles[i][j]);
				}
			}
		}
		
		pnlNorth.add(lblCounter);
		pnlNorth.add(lblMoveCounter);
		
		//Create a queue instant for the queue tiles
		tileQueue = new Queue(queueSize);
		
		//Populate the queue with random numbers
		Random rand = new Random();
		for(int i = 0; i < queueSize; i++)
			tileQueue.enqueue(rand.nextInt(10));
		
		//Dequeue numbers from the queue to populate the queue tiles
		lblTile1 = new JLabel(Integer.toString(tileQueue.dequeue()));
		lblTile2 = new JLabel(Integer.toString(tileQueue.dequeue()));
		lblTile3 = new JLabel(Integer.toString(tileQueue.dequeue()));
		lblTile4 = new JLabel(Integer.toString(tileQueue.dequeue()));
		lblTile5 = new JLabel(Integer.toString(tileQueue.dequeue()));

		//Add the queue tiles to the queue panel
		pnlQueue.add(lblTile1);
		pnlQueue.add(lblTile2);
		pnlQueue.add(lblTile3);
		pnlQueue.add(lblTile4);
		pnlQueue.add(lblTile5);
		
		//Add the panels to the frame
		this.add(pnlGrid, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlQueue, BorderLayout.EAST);
	}
	
	public class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Tile tile = (Tile) e.getSource();
			
			//Only allow moves into unoccupied tiles of the game board
			if(tile.getText()== ""){
				
				int row, column, number = 0;
				row = tile.getRow();
				column = tile.getColumn();
		
				//Add the numbers from all surrounding tiles
				number += tiles[row + 1][column].getNumber();
				number += tiles[row + 1][column + 1].getNumber();
				number += tiles[row + 1][column - 1].getNumber();
				number += tiles[row][column + 1].getNumber();
				number += tiles[row][column - 1].getNumber();
				number += tiles[row - 1][column].getNumber();
				number += tiles[row - 1][column + 1].getNumber();
				number += tiles[row - 1][column - 1].getNumber();	
			
				if(number % 10 == 0){
					
					//Set all surrounding tile's text to blank
					tiles[row + 1][column].setText("");
					tiles[row + 1][column + 1].setText("");
					tiles[row + 1][column - 1].setText("");
					tiles[row][column + 1].setText("");
					tiles[row][column - 1].setText("");
					tiles[row - 1][column].setText("");
					tiles[row - 1][column + 1].setText("");
					tiles[row - 1][column - 1].setText("");
				
					//Set all surrounding tile's numbers to zero
					tiles[row + 1][column].setNumber(0);
					tiles[row + 1][column + 1].setNumber(0);
					tiles[row + 1][column - 1].setNumber(0);
					tiles[row][column + 1].setNumber(0);
					tiles[row][column - 1].setNumber(0);
					tiles[row - 1][column].setNumber(0);
					tiles[row - 1][column + 1].setNumber(0);
					tiles[row - 1][column - 1].setNumber(0);
				}	
				
				else{
					
					//Update the clicked tile with the number from the queue
					tile.setNumber(Integer.parseInt(lblTile5.getText()));
					tile.setText(lblTile5.getText());
				}
				
				//Update the queue tiles
				lblTile5.setText(lblTile4.getText());
				lblTile4.setText(lblTile3.getText());
				lblTile3.setText(lblTile2.getText());
				lblTile2.setText(lblTile1.getText());
				lblTile1.setText(Integer.toString(tileQueue.dequeue()));
			
				//Lowers the move counter
				int movesLeft = Integer.parseInt(lblMoveCounter.getText());
				lblMoveCounter.setText(Integer.toString(movesLeft - 1));
			}
		}	
	}
}
