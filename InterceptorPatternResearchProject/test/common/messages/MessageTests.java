/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package common.messages;

import game.common.GameVersion;
import message.MessageRuntimeException;
import message.StrategyMessageType;
import message.implementations.CommandMessage;

import org.junit.Test;



/**
 * @author Dabrowski
 *
 */
public class MessageTests {

	
	
	@Test(expected=MessageRuntimeException.class)
	public void limitedMessageTypesForCommandMessage1(){
		new CommandMessage(null, GameVersion.ALPHA);
	}
	
	@Test(expected=MessageRuntimeException.class)
	public void limitedMessageTypesForCommandMessage2(){
		new CommandMessage(StrategyMessageType.MOVE, GameVersion.ALPHA);
	}
	
	@Test(expected=MessageRuntimeException.class)
	public void limitedMessageTypesForCommandMessage3(){
		new CommandMessage(StrategyMessageType.QUERY, GameVersion.ALPHA);
	}
	
	@Test(expected=MessageRuntimeException.class)
	public void limitedMessageTypesForCommandMessage4(){
		new CommandMessage(StrategyMessageType.RESULT, GameVersion.ALPHA);
	}
}
