package controller;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Tile;

public class SumFunControllerTest {

	private Tile[][] mockTileGrid;
	
	@Test
	public void testCheckNeighbors() {
		
		//create a mock 4x4 grid of tiles with
		//the inner 2x2 tiles occupied with numbers
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				mockTileGrid[i][j] = new Tile(i,j,6);
			}
		}
		// Black-box Testing
		// assertEquals("Error", true, );
	}

	@Test
	public void testResetNeighbors() {
		// Black-box Testing
		fail("Not yet implemented");
	}

}
