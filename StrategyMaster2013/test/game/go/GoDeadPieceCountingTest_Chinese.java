/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.go;

import java.util.Collection;

import org.junit.Test;

import game.common.PieceLocationDescriptor;
import common.PlayerColor;
import common.StrategyException;
import static org.junit.Assert.assertTrue;



/** Tests to specifically test how "dead pieces" are recognized
 * 
 * @author Dabrowski
 *
 */
public class GoDeadPieceCountingTest_Chinese extends GoTestSuite {

	/** Run a dead piece test. Assume that all black pieces are dead and will be removed.
	 * 
	 * @param blk black configuration
	 * @param wht white configuration
	 * @throws StrategyException
	 */
	void runDeadPieceTest(int[][] blk, int[][] wht) throws StrategyException{
		addBlackAndWhitePieces(blk,wht);
		
		Collection<PieceLocationDescriptor> expectedDead = generateConfiguration(PlayerColor.BLACK, blk);
		Collection<PieceLocationDescriptor> deadFound = ((GoScoreKeeperTestDouble)  gameDoubleScore).findDeadPieces();
		
		assertTrue(expectedDead.containsAll(deadFound) && deadFound.containsAll(expectedDead));
	}
	
	
	
	@Test
	public void noCapturesOnEmptyBoard() throws StrategyException {
		int[][] nullList = {   };
		runDeadPieceTest(nullList, nullList);
	}
	
	
	
	
	
}
