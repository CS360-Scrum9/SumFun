package controller;

import model.ObservableTile;

import org.junit.Test;
import static org.junit.Assert.*;


public class SumFunControllerTest {

	private ObservableTile[][] mockTileGrid;
	private SumFunController sfc = SumFunController.getController();
	
	@Test
	public void testCheckNeighbors() {
		
		//create a mock 4x4 grid of tiles with
		//the inner 2x2 tiles occupied with numbers
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 6; j++){
				mockTileGrid[i][j] = new ObservableTile(i,j,6);
			}
		}
		sfc.createMockboard(mockTileGrid);
		// Black-box Testing
		// assertEquals("Error", false, sfc.checkNeighbors(tiles, row, column, queueValue));
	}

	@Test
	public void testResetNeighbors() {
		// Black-box Testing
		fail("Not yet implemented");
	}

}
