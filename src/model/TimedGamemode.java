package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class TimedGamemode extends Gamemode {

	private static TimedGamemode gamemode;
	private JLabel lblTimer;
	private int timeLeft;
	Timer timer;
	
	private TimedGamemode() {
		super();
		timeLeft = 300;
		timer = new Timer(1000, new UpdateTime());
	}

	public static TimedGamemode getGamemode() {
		 if (gamemode == null) {
			 gamemode = new TimedGamemode();
		 }
		 return gamemode;
	}
	
	public void startTime(JLabel lblTimer) {
		this.lblTimer = lblTimer;
		timer.start();
	}
	
	private String convertToString() {
		int minutes = timeLeft / 60;
		int seconds = timeLeft % 60;
		return minutes + ":" + seconds;
	}
	
	private class UpdateTime implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			lblTimer.setText(convertToString());
			timeLeft--;
			
		}
		
	}
	
}
