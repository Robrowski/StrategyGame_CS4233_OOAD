/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common;

import static org.junit.Assert.*;
import game.common.Location;
import game.common.Location2D;
import game.common.PieceLocationDescriptor;
import game.common.turnResult.DetailedMoveResult;

import org.junit.Test;

/** Tests for my DetailedMoveResult class
 * 
 * 
 * @author Dabrowski
 *
 */
public class DetailedMoveResultTest {

	Location L1 = new Location2D(0, 5);
	Location L2 = new Location2D(1, 5);
	Location L3 = new Location2D(2, 5);
	
	final DetailedMoveResult dmr1 = new DetailedMoveResult(null, new PieceLocationDescriptor(null, L1), L2, null);
	final DetailedMoveResult dmr2 = new DetailedMoveResult(null, new PieceLocationDescriptor(null, L2), L1, null);
	final DetailedMoveResult dmr3 = new DetailedMoveResult(null, new PieceLocationDescriptor(null, L1), L2, null);
	final DetailedMoveResult dmr5 = new DetailedMoveResult(null, new PieceLocationDescriptor(null, L2), L3, null);
	final DetailedMoveResult dmr6 = new DetailedMoveResult(null, new PieceLocationDescriptor(null, L3), L2, null);
		
	@Test
	public void testIsEqual(){
		assertTrue(dmr1.isEqual(dmr3));
		assertFalse(dmr1.isEqual(dmr2));
		assertFalse(dmr6.isEqual(dmr3));

	}
	
	@Test
	public void testIsOpposite(){
		assertTrue(dmr1.isOpposite(dmr2));
		assertFalse(dmr1.isOpposite(dmr3));
		assertFalse(dmr1.isOpposite(dmr5));

	}
	
	
	
	
	
	
}
