import java.util.Random;

import javax.swing.JButton;
// Testing a change to upload to Github.
public class Tile extends JButton {

	private int number;
	
	private static int MAXTILES = 81;
	private static int nTiles = 0;
	
	private Tile() {
		Random rand = new Random();

		if (nTiles > 9 && nTiles % 9 != 0 && !getSumOfDigits() && nTiles < 72) {
			number = rand.nextInt(10);
			this.setText(Integer.toString(number));
		}
	}
	
	public static Tile getTile() {
		
		Tile tile = null;
		
		if (nTiles < MAXTILES) {
			
			tile = new Tile();
			nTiles++;
		}
		
		return tile;
	
	}
	
	public int getNumber() {
		return number;
	}
	
	private boolean getSumOfDigits() {
		int first = nTiles % 10;
		int second = (nTiles / 10);
		if (first + second == 8) {
			return true;
		}
		return false;
	}
	
	
}
