/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.version.gamma;

import static org.junit.Assert.*;
import game.common.PieceType;
import game.version.gamma.GammaPieceStats;

import org.junit.Test;

/** Simple tests to finish coverage of the GammaPieceStats class
 * 
 * @author Dabrowski
 *
 */
public class GammaPieceStatsTest {
	@Test
	public void testGetPower_unknownType(){
		assertEquals(new GammaPieceStats().getPower(PieceType.BOMB), 0);
	}
	
	@Test
	public void testGetMovementCapability_unknownType(){
		assertEquals(new GammaPieceStats().getMovementCapability(PieceType.BOMB), 0);
	}
}
