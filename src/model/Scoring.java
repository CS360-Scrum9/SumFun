package model;

import java.util.Observable;

	public class Scoring extends Observable{
		protected int playerScore;
		
		public Scoring(){
			playerScore = 0;
		}
		
		public int getScore(){
			return playerScore;
		}
		
		public void setScore(int playerScore){
			this.playerScore = playerScore;
			setChanged();
			notifyObservers();
		}
		
		@Override
		public String toString(){
			return " Score: " + playerScore + " ";
		}
	}