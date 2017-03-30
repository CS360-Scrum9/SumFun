package model;
import java.util.NoSuchElementException;

public class Queue {
	private int manyItems;
	private int front;
	private int rear;
	
	private int[] tileQueue;
	
	public Queue(int arraySize) {
		tileQueue = new int[arraySize];
	}

	private int nextIndex(int i) {
		return (i + 1) % tileQueue.length;
	}
	
	public void ensureCapacity(int minimumCapacity) {
	      int biggerArray[ ];
	      int n1, n2;
	      if ( tileQueue.length >= minimumCapacity ) // No change needed
	         return;
	      else if ( manyItems == 0 )   // Just increase the capacity ignoring array elements
	         tileQueue = new int[ minimumCapacity ];
	      else if ( front <= rear ) {                            // Create larger array and shift the data
	         biggerArray = new int[ minimumCapacity ];
	         System.arraycopy(tileQueue, front, biggerArray, front, manyItems);
	         tileQueue = biggerArray;
	      } else {                                // Create a bigger array with more attention to copying 
	         biggerArray = new int[ minimumCapacity ];
	         n1 = tileQueue.length - front;
	         n2 = rear + 1;
	         System.arraycopy(tileQueue, front, biggerArray, 0, n1);
	         System.arraycopy(tileQueue, 0, biggerArray, n1, n2);
	         front = 0;
	         rear = manyItems-1;  
	         tileQueue = biggerArray;
	      }
	}
	
	public void enqueue(int item) {
		if (manyItems == tileQueue.length) 
			ensureCapacity(manyItems * 2 + 1);
		if (manyItems == 0) {
			front = 0;
			rear = 0;
		} else {
			rear = nextIndex(rear);
		}
		
		tileQueue[rear] = item;
		manyItems++;
	}
	
	public int dequeue() {
		int answer;
		
		if (manyItems == 0) 
			throw new NoSuchElementException("Queue underflow");
		answer = tileQueue[front];
		front = nextIndex(front);
		manyItems--;
		return answer;
	}
	
	public boolean isEmpty() {
		if (manyItems == 0) {
			return true;
		}
		return false;
	}
	
	
}
