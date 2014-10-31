package solver;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import MockObjects.MockSolver;
import boards.Board;
import puzzle.Connection;
import puzzle.Piece;

public class SolverTest {

	Solver s;
	Collection<Piece> random_pieces;
	int width = 6, height = 6, colors = 2;
	
	@Before
	public void setup(){
		// TODO this is a random puzzle.... probably ok?
		this.random_pieces = PuzzleGenerator.generateRandomPuzzle(width, height, colors, 0);
		this.s = new MockSolver(new Board(width, height), random_pieces);
	}
	
	
	@Test
	public void test_getPieceToFit_exact_regex(){
		// For each piece, try to find a piece that matches exactly :D
		for (Piece p: random_pieces){
			Piece found = s.getPieceToFit(p.bottom(), p.left(), p.top(), p.right());
			assertTrue(found.isEquivalent(p));
			assertEquals(found.toString(), p.toString());
		}
	}
	
	@Test
	public void test_getPieceToFit_doesnt_exist(){
		assertNull(s.getPieceToFit(Connection.CYAN, Connection.CYAN, Connection.CYAN, Connection.CYAN));
	}
	
	@Test
	public void test_getPieceToFit_by_regex(){
		// Some of these aren't actually guaranteed to exist in a given puzzle... so special Pieces were made
		Connection[] a = { Connection.FLAT, Connection.RED, Connection.BLUE};
		
		Collection<Piece> pieces = new LinkedList<Piece>();
		for (Connection ac: a)
			for (Connection bc: a)
				for (Connection cc: a)
					for (Connection dc: a)
						pieces.add(new Piece(ac, bc, cc, dc));

		Solver new_solver; 
		Connection[] a2 = {null, Connection.FLAT, Connection.RED, Connection.BLUE};
		for (Connection ac: a2)
			for (Connection bc: a2)
				for (Connection cc: a2)
					for (Connection dc: a2){
						// New solver because getPieceToFit removes....
						new_solver = new MockSolver(new Board(pieces.size(), 1), pieces);
						Piece p = new Piece(ac, bc, cc, dc);
						assertNotNull( new_solver.getPieceToFit(p.bottom(), p.left(), p.top(), p.right()));
					}
	}
	
	
	
}
