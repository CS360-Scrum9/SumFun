package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HighScoreBoard extends JFrame{
	private JPanel pnlMain;
	private JPanel pnlCenter;
	private JPanel pnlTop;
	private JPanel pnlBottom;
	
	private JLabel lblTitle;
	private JLabel[] lblName;
	private JLabel[] lblMoves;
	private JLabel[] lblTimeLeft;
	private JLabel[] lblScore;
	
	private final String TIMED_FILE = "TimedHighScores.txt";
	private final String UNTIMED_FILE = "UntimedHighScores.txt";
		
	//The version argument should either be 0 or 1.  This will determine
	//which view needs to be shown.
	public HighScoreBoard(int version) {
		pnlMain = new JPanel();
		pnlCenter = new JPanel();
		pnlTop = new JPanel();
		pnlBottom = new JPanel();
		
		lblTitle = new JLabel("Highscores");
		lblName = new JLabel[10];
		lblMoves = new JLabel[10];
		lblTimeLeft = new JLabel[10];
		lblScore = new JLabel[10];
		
		BorderLayout bl = new BorderLayout();
		pnlMain.setLayout(bl);
		
		GridLayout gl = new GridLayout(10,3);
		pnlCenter.setLayout(gl);
		
		if (version == 1) {
			for (int i = 0; i < 10; i++) {
				lblName[i] = new JLabel();
				lblTimeLeft[i] = new JLabel();
				lblScore[i] = new JLabel();
				pnlCenter.add(lblName[i]);
				pnlCenter.add(lblTimeLeft[i]);
				pnlCenter.add(lblScore[i]);
			}
			generateTimedView();

		} else {
			for (int i = 0; i < 10; i++) {
				lblName[i] = new JLabel();
				lblMoves[i] = new JLabel();
				lblScore[i] = new JLabel();
				pnlCenter.add(lblName[i]);
				pnlCenter.add(lblMoves[i]);
				pnlCenter.add(lblScore[i]);
			}
			generateUntimedView();
		}
		
		pnlTop.add(lblTitle, SwingConstants.CENTER);
		pnlMain.add(pnlCenter);
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlMain);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private String[][] getScores(String fileName) {
		File file = new File(fileName);
		Scanner input = null;
		
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException ex) {
			System.out.println(ex);
		}
		
		String[][] scores = new String[10][];
		int counter = 0;
		
		while (input.hasNext()) {
			String line = input.nextLine();
			String[] info = line.split(",");
			scores[counter] = info;
			counter++;
		}
		
		return scores;
	}
	
	private void generateTimedView() {
		String[][] scores = getScores(TIMED_FILE);
		
		int i = 0;
		
		while (scores[i] != null) {
			lblName[i].setText(scores[i][0]);
			lblTimeLeft[i].setText(scores[i][1]);
			lblScore[i].setText(scores[i][2]);
			i++;
		}
	}
	
	private void generateUntimedView() {
		String[][] scores = getScores(UNTIMED_FILE);
		
		int i = 0;
		
		while (scores[i] != null) {
			lblName[i].setText(scores[i][0]);
			lblMoves[i].setText(scores[i][1]);
			lblScore[i].setText(scores[i][2]);
			i++;
		}
	}
}
