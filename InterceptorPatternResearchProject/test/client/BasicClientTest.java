/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package client;

import static org.junit.Assert.assertTrue;
import message.implementations.GenericMessage;

import org.junit.*;

import server.StrategyServerTestDouble;

/** The basic client for this version of the server is 
 * not actually supposed to implement anything. 
 * 
 * 
 * @author Dabrowski
 *
 */
public class BasicClientTest {

	@Before
	public void setup(){
		StrategyServerTestDouble.resetSingleton();
	}
	
	
	@Test
	public void testPlayAlphaGame(){
		new BasicClient().playAlphaGame();
	}
	
	@Test 
	public void testSillyInterceptor(){
		assertTrue( new BasicClient().sendAMessage(new GenericMessage("SILLY", null)).getMessageStatus());
	}
}
