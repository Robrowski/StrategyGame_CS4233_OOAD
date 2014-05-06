/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package message.implementations;

import message.AbstractMessage;
import message.MessageRuntimeException;
import message.StrategyMessageType;
import strategy.game.common.GameVersion;

/** A message that only contains a MessageType. This 
 *  message is used to request a command such as
 *  starting a game or initializing a game.
 *   
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class CommandMessage extends AbstractMessage{

	/** the version of the game to start/initialize */
	private final GameVersion version;
	
	
	/** Basic constructor to take a message type and save it
	 * 
	 * @param command the command to be taken, cannot accept
	 *  MessageTypes other than START or INITIALIZE_GAME
	 * @param version the version of the game to initialize
	 */
	public CommandMessage(StrategyMessageType command, GameVersion version){
		// Check that the message type is acceptable
		if (command != StrategyMessageType.START 
				&& command != StrategyMessageType.DEFAULT_INITIALIZE){
			throw new MessageRuntimeException("You can't use that here");
		}
		type = command.toString();
		this.version = version;
	}


	/**
	 * @return the version
	 */
	public GameVersion getVersion() {
		return version;
	}

	
	
}
