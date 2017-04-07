package model;

import java.util.Observable;

public class MoveCounter extends Observable{

	private int moveCount;
	
	public MoveCounter(){
		moveCount = 50;
	}
	
	public int getCount(){
		return moveCount;
	}
	
	public void setCount(int moveCount){
		this.moveCount = moveCount;
	}
	
	public void decrementCount(){
		moveCount = moveCount - 1;
		setChanged();
		notifyObservers();
	}
}
