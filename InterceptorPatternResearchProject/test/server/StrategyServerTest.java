/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


import game.common.GameVersion;
import game.common.Location;
import game.common.Location2D;
import game.common.PieceType;
import message.StrategyMessageType;
import message.implementations.CommandMessage;
import message.implementations.GenericMessage;
import message.implementations.MoveMessage;
import message.implementations.QueryMessage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



/** This handles unit tests for my network
 * 
 * @author Dabrowski
 *
 */
public class StrategyServerTest {

	// Alpha details
	private final Location redMarshalLocation = new Location2D(0, 0);
	private final Location blueFlagLocation = new Location2D(0, 1);
	private final MoveMessage validAlphaMove = new MoveMessage(PieceType.MARSHAL, redMarshalLocation, blueFlagLocation);

	
	// Some locations
	private final Location L00 = new Location2D(0,0);
	private final Location L01 = new Location2D(0,1);
	private final Location L10 = new Location2D(1,0);
	private final Location L11 = new Location2D(1, 1);
	
	// The server
	private static StrategyServer theServer = StrategyServerTestDouble.getInstance();

	
	@BeforeClass
	public static void setupBeforeClass(){
		StrategyServer.getInstance(); 		// Called for code coverage
		SampleConfigurations.getInstance();	// Called for code coverage
	}
	
	@Before 
	public void setup() {
		StrategyServerTestDouble.resetSingleton();
	}
	
	
	// testing with CommandMessage
	@Test
	public void cannotStartGameBeforeInitialize(){
		assertFalse(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.ALPHA)).getMessageStatus());
		assertFalse(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.BETA)).getMessageStatus());
		assertFalse(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.GAMMA)).getMessageStatus());
	}
	
	@Test
	public void cannotMoveGameBeforeInitializeANDStart_ALPHA(){
		// Before initialize
		assertFalse(theServer.receiveMessage(validAlphaMove).getMessageStatus());
		
		// Before start
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.ALPHA)).getMessageStatus());
		assertFalse(theServer.receiveMessage(validAlphaMove).getMessageStatus());

		// After start
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.ALPHA)).getMessageStatus());
	}
	
	@Test
	public void cannotDoubleInitialize(){
		assertTrue( theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.GAMMA)).getMessageStatus());
		assertFalse(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.GAMMA)).getMessageStatus());
	}
	
	@Test
	public void initializeAndStartGamma(){
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.GAMMA)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.GAMMA)).getMessageStatus());
	}
	
	@Test
	public void initializeAndStartBETA(){
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.BETA)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.BETA)).getMessageStatus());
	}
	
	@Test
	public void cannotIntializeDelta(){
		assertFalse(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.DELTA)).getMessageStatus());
	}
	
	@Test
	public void cannotStartAStartedGameBeta_AND_GameVersionDoesntMatterWhenMessageTypeIsStart(){
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.BETA)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START,  GameVersion.ALPHA)).getMessageStatus());
		assertFalse(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.BETA)).getMessageStatus());
	}
	
	
	// testing with QueryMessage
	@Test 
	public void cannotQueryBeforeGameStartsInAPLHA(){
		assertFalse(theServer.receiveMessage(new QueryMessage(L00)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.ALPHA)).getMessageStatus());
	}
	
	@Test
	public void canQueryAlphaSuccessfully(){
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.ALPHA)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, null)).getMessageStatus());		
		
		assertTrue(theServer.receiveMessage(new QueryMessage(L00)).getMessageStatus());
		assertSame(((QueryMessage) theServer.receiveMessage(new QueryMessage(L00))).getAtLocation().getType(), PieceType.MARSHAL);

		assertTrue(theServer.receiveMessage(new QueryMessage(L01)).getMessageStatus());
		assertSame(((QueryMessage) theServer.receiveMessage(new QueryMessage(L01))).getAtLocation().getType(), PieceType.FLAG);
		
		assertTrue(theServer.receiveMessage(new QueryMessage(L10)).getMessageStatus());
		assertSame(((QueryMessage) theServer.receiveMessage(new QueryMessage(L10))).getAtLocation().getType(), PieceType.FLAG);
		
		assertTrue(theServer.receiveMessage(new QueryMessage(L11)).getMessageStatus());
		assertSame(((QueryMessage) theServer.receiveMessage(new QueryMessage(L11))).getAtLocation().getType()   , PieceType.MARSHAL);
	}
	
	
	// test MessageType.RESULT
	@Test
	public void serverCantUseResults(){
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.ALPHA)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.ALPHA)).getMessageStatus());
			
		final MoveMessage theResult = (MoveMessage) theServer.receiveMessage(validAlphaMove);
		assertTrue(theResult.getMessageStatus());
		assertFalse(theServer.receiveMessage(theResult).getMessageStatus());
	}
	
	// Test making moves!!
	@Test
	public void testValidAlphaaMove(){
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.ALPHA)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.ALPHA)).getMessageStatus());
	
		final MoveMessage theResult = (MoveMessage) theServer.receiveMessage(validAlphaMove);
		
		assertTrue(theResult.getMessageStatus());
		assertSame(theResult.getFaultReason(),"");
		assertNotNull(theResult.getMoveResult());
	}
	
	// Test new interceptor!
	@Test 
	public void testNewInterceptor(){
		GenericMessage theResult = (GenericMessage) theServer.receiveMessage(new GenericMessage("LOGGER", null));
		assertTrue(theResult.getMessageStatus());
		assertNull(theResult.getArguments());
		assertSame(theServer.getLog().size(), 2); // the message should actually be logged twice because this interceptor is in both lists
	}

		
	// Test that the interceptor is working
	@Test
	public void testLog(){
		// Fill log with two items
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.ALPHA)).getMessageStatus());
		assertTrue(theServer.receiveMessage(new CommandMessage(StrategyMessageType.START, GameVersion.ALPHA)).getMessageStatus());
		assertSame(theServer.getLog().size(), 2);
	}
	
	
}
