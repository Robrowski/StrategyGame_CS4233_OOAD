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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import game.common.Coordinate;
import game.common.GameVersion;
import game.common.Location;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.battle.BattleResult;
import game.common.turnResult.MoveResultStatus;

import org.junit.Test;

import common.PlayerColor;

/** This is for testing the methods given in the original classes made by Prof. Pollice 
 * 
 * 
 * @author Dabrowski
 *
 */
public class CommonMiscTest {
	
	// Setup some reusable data
	static private final Location l1 = new Location2D(1,2);
	static private final Location l2 = new Location2D(1,0);
	static private final Location l3 = new Location2D(2,1);

	static private final Piece redFlag     = new Piece(PieceType.FLAG , PlayerColor.RED);
	static private final Piece blueFlag    = new Piece(PieceType.FLAG , PlayerColor.BLUE);
	static private final Piece redBomb     = new Piece(PieceType.BOMB , PlayerColor.RED);
	static private final Piece blueMarshal = new Piece(PieceType.MARSHAL , PlayerColor.BLUE);

	@Test
	public void test_hashCode(){
		// These outputs aren't very useful by design, but that is how the prof. made them
		assertSame(  ( 1 + 2 + 1)*(1+1)*(2+1), l1.hashCode());
		assertSame(  ( 1 + 0 + 1)*(1+1)*(0+1), l2.hashCode());
		assertSame(  ( 2 + 1 + 1)*(2+1)*(1+1), l3.hashCode());

		assertNotSame(blueFlag.hashCode(), redFlag.hashCode());
		assertNotSame(blueFlag.hashCode(), blueMarshal.hashCode());
		assertNotSame(blueFlag.hashCode(), redBomb.hashCode());

		assertNotSame(new PieceLocationDescriptor(redFlag, l1).hashCode(), new PieceLocationDescriptor( redFlag, l2).hashCode());
		assertNotSame(new PieceLocationDescriptor(redFlag, l1).hashCode(), new PieceLocationDescriptor( blueFlag, l2).hashCode());
		assertNotSame(new PieceLocationDescriptor(blueMarshal, l3).hashCode(), new PieceLocationDescriptor(redBomb, l1 ).hashCode());
	}

	@Test
	public void test_toString(){
		assertTrue(redFlag.toString().contains("RED") && redFlag.toString().contains("Flag") );
		assertTrue(blueFlag.toString().contains("BLUE") && blueFlag.toString().contains("Flag") );
		assertTrue(blueMarshal.toString().contains("BLUE") && blueMarshal.toString().contains("Marshal") );
	}

	@Test
	public void equalsMethodReturnsFalseWhenUnrecognizedObjectsAreUsed(){
		assertFalse(l1.equals(new Object()));
		assertFalse(redFlag.equals(new Object()));
		assertFalse(new PieceLocationDescriptor(blueMarshal, l3).equals(new Object()));
	}
	
	@Test // This is simply for test coverage and is only using methods provided by java enums
	public void testingEnums(){
		MoveResultStatus.values();
		MoveResultStatus.valueOf(MoveResultStatus.BLUE_WINS.toString());
		GameVersion.values();
		GameVersion.valueOf(GameVersion.ALPHA.toString());
		PlayerColor.values();
		PlayerColor.valueOf(PlayerColor.BLUE.toString());
		PieceType.values();
		PieceType.valueOf("BOMB");
		Coordinate.values();
		Coordinate.valueOf(Coordinate.X_COORDINATE.toString());
		BattleResult.values();
		BattleResult.valueOf(BattleResult.DEFENDERWINS.toString());
	}

}
