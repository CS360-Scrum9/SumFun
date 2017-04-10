package view;

import java.awt.GridLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartingScreen extends JFrame {
	private JPanel pnlMain;

	private JPanel pnlTop;
	private JPanel pnlBottom;

	private JLabel lblQuestion;

	private JButton btnTimed;
	private JButton btnUntimed;

	public StartingScreen() {
		pnlMain = new JPanel();
		pnlTop = new JPanel();
		pnlBottom = new JPanel();

		lblQuestion = new JLabel("Which gamemode would you like to play?");

		btnTimed = new JButton("Timed");
		btnUntimed = new JButton("Untimed");

		GridLayout gl = new GridLayout(2, 1);
		pnlMain.setLayout(gl);

		gl = new GridLayout(1, 2);
		pnlBottom.setLayout(gl);

		pnlTop.add(lblQuestion, Alignment.CENTER);

		pnlBottom.add(btnTimed);
		pnlBottom.add(btnUntimed);

		pnlMain.add(pnlTop);
		pnlMain.add(pnlBottom);

		add(pnlMain);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
