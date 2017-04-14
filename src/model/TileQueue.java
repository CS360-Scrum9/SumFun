package model;

import java.util.Observable;
import java.util.Random;

public class TileQueue extends Observable{
	
	private Queue tileQueue;
	private int queueSize = 65, currentQueueSize = 10;
	private int[] currentQueue;
	
	public TileQueue(){
		reset();
	}
	
	public void reset(){
		
		tileQueue = new Queue(queueSize);
		currentQueue = new int[currentQueueSize];
		Random rand = new Random();
		
		for(int i = 0; i < queueSize; i++)
			tileQueue.enqueue(rand.nextInt(10));
	
		
		for(int i = 4; i >= 0; i--)
			currentQueue[i]=tileQueue.dequeue();
		
		setChanged();
		notifyObservers();
	}
	
	public void dequeue(){
		for(int i = 4; i > 0; i--)
			currentQueue[i] = currentQueue[i-1];
		
		currentQueue[0] = tileQueue.dequeue();
		setChanged();
		notifyObservers();
	}

	public int[] getCurrentQueue(){
		return currentQueue;
	}
	
	public int getNextValue(){
		return currentQueue[4];
	}
	
}
