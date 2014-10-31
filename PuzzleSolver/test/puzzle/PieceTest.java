package puzzle;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import puzzle.Connection;
import puzzle.Piece;

/**
 * 
 */

/**
 * @author rpdabrowski
 *
 */
public class PieceTest {
	Piece corner, corner2, edge, edge2, center, bad, bad2;
	
	@Before
	public void setUp(){
		this.corner  = new Piece(Connection.FLAT, Connection.FLAT, Connection.RED, Connection.RED);
		this.corner2 = new Piece(Connection.FLAT, Connection.BLUE,  Connection.BLUE, Connection.FLAT);
		this.edge    = new Piece(Connection.FLAT, Connection.BLUE, Connection.RED, Connection.BLUE);
		this.edge2    = new Piece(Connection.RED, Connection.BLUE, Connection.RED, Connection.BLUE);

		this.center  = new Piece(Connection.BLUE, Connection.BLUE,  Connection.BLUE, Connection.BLUE);
		this.bad     = new Piece(Connection.FLAT, Connection.RED,  Connection.FLAT, Connection.BLUE);
		this.bad2    = new Piece(Connection.BLUE, Connection.FLAT,  Connection.RED, Connection.FLAT);

	}
	
	@Test
	public void testIsCorner(){
		//TODO: rotate the edges through to check
		assertTrue( this.corner.isCorner());
		assertTrue( this.corner2.isCorner());
		assertFalse(this.edge.isCorner());
		assertFalse(this.center.isCorner());		
		assertFalse(this.bad.isCorner());
		assertFalse(this.bad2.isCorner());
	}

	@Test
	public void testIsEdge(){
		//TODO: rotate the edges through to check
		assertFalse( this.corner.isEdge());
		assertFalse( this.corner2.isEdge());
		assertTrue(this.edge.isEdge());
		assertFalse(this.center.isEdge());	
	}
	
	@Test
	public void testIsInner(){
		//TODO: rotate the edges through to check
		assertFalse( this.corner.isInner());
		assertFalse( this.corner2.isInner());
		assertFalse(this.edge.isInner());
		assertTrue(this.center.isInner());		
	}
	
	@Test
	public void testCanConnect(){
		assertTrue( this.corner.canConnectRotated(this.corner));
		assertFalse( this.corner.canConnectRotated(this.corner2));
		assertTrue( this.corner.canConnectRotated(this.edge));
		assertFalse( this.corner.canConnectRotated(this.center));
	}
	
	
	@Test
	public void testEquivalent(){
		assertTrue(this.corner.isEquivalent(this.corner));
		assertTrue(this.corner.isEquivalent( new Piece(Connection.FLAT, Connection.RED, Connection.RED,Connection.FLAT)));
		assertTrue(this.corner.isEquivalent( new Piece(Connection.RED, Connection.RED, Connection.FLAT,Connection.FLAT)));
		assertTrue(this.corner.isEquivalent( new Piece(Connection.RED, Connection.FLAT, Connection.FLAT,Connection.RED)));
		assertFalse(this.corner.isEquivalent(this.corner2));
		assertFalse(this.corner.isEquivalent(this.edge));
		assertFalse(this.corner.isEquivalent(this.center));
		assertFalse(this.corner.isEquivalent(this.bad));
	}
	

	
	@Test
	public void testConnect(){
		assertTrue(this.corner.canConnect( this.edge2, 2) );
	}
	
	
	@Test
	public void testID(){
		assertNotEquals(this.corner.id, this.corner2.id); 
		assertNotEquals(this.corner2.id, this.edge.id);
		assertNotEquals(this.edge.id, this.center.id);
		assertNotEquals(this.center.id, this.bad.id);
	}
	
	
	
}
