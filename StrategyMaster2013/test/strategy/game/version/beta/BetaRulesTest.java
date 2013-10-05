/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.beta;

import static org.junit.Assert.*;

import org.junit.Test;

import strategy.game.common.PieceType;

/**
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class BetaRulesTest {

	/**
	 * Method test_BetaRules_and_getPower.
	 */
	@Test
	public void test_BetaRules_and_getPower(){
		assertNotNull( new BetaRules());
		assertSame(0, BetaRules.getPower(PieceType.BOMB));
		assertSame(0, BetaRules.getPower(PieceType.BOMB)); // works as static too

	}
}
