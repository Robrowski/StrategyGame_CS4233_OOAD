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

import game.common.GameVersion;
import game.common.Location2D;
import game.common.PieceType;


import message.Sendable;
import message.StrategyMessageType;
import message.implementations.CommandMessage;
import message.implementations.MoveMessage;
import network.BasicNetwork;

/** This class is only meant to do some very basic things, like
 * scripted message patterns mainly for testing the network
 * and the server.
 * 
 * by the principles of TDD, the client is here to FAKE IT!
 * 
 * @author Dabrowski 
 * @version $Revision: 1.0 $
 */
public class BasicClient {

	/** a reference to the network */
	BasicNetwork network = 	BasicNetwork.getInstance();

	
	/** play a basic game of Alpha Strategy */
	public void playAlphaGame(){
		network.sendToServer(new CommandMessage(StrategyMessageType.DEFAULT_INITIALIZE, GameVersion.ALPHA));
		network.sendToServer(new CommandMessage(StrategyMessageType.START, GameVersion.ALPHA));
		network.sendToServer(new MoveMessage(PieceType.MARSHAL, new Location2D(0, 0), new Location2D(0, 1)));
	}
	
	/** Sends the given message to the server
	 * 
	 * @param aMessage to send
	 * @return result
	 */
	public Sendable sendAMessage(Sendable aMessage){
		return network.sendToServer(aMessage);
	}
	
	
}

