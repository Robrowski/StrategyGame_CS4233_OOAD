package boards;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import boards.Board;


public class BoardTest {
	Board board;
	
	@Before
	public void setup(){
		this.board = new Board(10,10);
	}
	
	
	@Test
	public void testGetPiece(){
		assertNull(this.board.getPiece(4, 4)); // uninitialized
	}
	
	
}
