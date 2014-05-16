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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import common.PlayerColor;
import common.StrategyException;

/** Tests that verify that the final score calculations are accurate 
 * 
 * 
 * @author Dabrowski
 *
 */
public class GoFinalScoreTest extends GoTestSuite {
	/** Runs a final configuration test, given the black and white configurations as well as expected scores
	 * 
	 *  It is expected that configurations are valid
	 *   
	 * @param blk configuration
	 * @param wht configuration
	 * @param expectedBlkScore   black score
	 * @param expectedWhtScore   white score
	 * @throws StrategyException thrown upon error
	 */
	void runFinalConfigurationTest( int [][]  blk, int [][] wht, int expectedBlkScore, int expectedWhtScore) throws StrategyException{
		addBlackAndWhitePieces(blk, wht);
		
		gameDouble.setGameOver();
		gameDoubleScore.CalculateFinalScore();
		
		assertEquals(gameDoubleScore.getPlayerScore(PlayerColor.BLACK), expectedBlkScore);
		assertEquals(gameDoubleScore.getPlayerScore(PlayerColor.WHITE), expectedWhtScore);
	}
	
	
	
	//***********************  Calculate Final Scores **************************/
	// This is a template for testing end game scores
	
	@Test
	public void finalConfig1()throws StrategyException{
		int [][] blackFinalConfig1 = { { 0, 3},{ 2, 3},{ 3, 3}   };
		int [][] whiteFinalConfig1 = { {0 ,1 },{1 ,0 },{0 ,4 },{1 ,2 },{1 ,3 },{1 ,5 },{2 ,2 },{2 ,4 },{3 ,2 },{3 ,4 },{4 ,2 },{4 ,4 },{ 5, 3}};
		
		
		// Set up the configuration
		runFinalConfigurationTest(blackFinalConfig1,whiteFinalConfig1,0,0);
	}
}
