import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	private Tile tiles;
	
	public Main() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,500);
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
		
		//Adds tiles to the grid
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Tile newTile = Tile.getTile();
				newTile.addActionListener(bl);
				pnlGrid.add(newTile);
			}
		}
		
		pnlNorth.add(lblCounter);
		pnlNorth.add(lblMoveCounter);
/*		
		pnlQueue.add(lblTile1);
		pnlQueue.add(lblTile2);
		pnlQueue.add(lblTile3);
		pnlQueue.add(lblTile4);
		pnlQueue.add(lblTile5);*/
		
		this.add(pnlGrid, BorderLayout.CENTER);
		this.add(pnlNorth, BorderLayout.NORTH);
	}
	
	public class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Tile tile = (Tile) e.getSource();
			System.out.println(tile.getNumber());
			
			//Lowers the move counter;
			int movesLeft = Integer.parseInt(lblMoveCounter.getText());
			lblMoveCounter.setText(Integer.toString(movesLeft - 1));
			
		}
		
	}
}
