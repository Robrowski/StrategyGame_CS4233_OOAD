/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package network;

import message.Sendable;


/** This is a very basic network implementation that will be used
 *  to simulate the connection between a client and a server. It 
 *  is here to act as the network without all the fancy network
 *  features like what http has. In this network, Strings are 
 *  passed around as messages.
 * 
 *  It is designed so that it can be referenced from either the server or 
 *  the client.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class BasicNetwork {
	
	/** The factory that provides StrategyGames   */
	private final static BasicNetwork theNetwork = new BasicNetwork();

	/**
	 * Default private constructor to ensure this is a singleton.
	 * 
	 */
	private BasicNetwork()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static BasicNetwork getInstance()
	{
		return theNetwork;
	}
	
	
	
	/** Takes a message and sends it to the server
	 * 
	 * @param message the message to send
	
	 * @return the status of the delivery */
	public Sendable sendToServer(Sendable message) {
		// The test double is used for testing purposes - same functionality
		return server.StrategyServer.getInstance().receiveMessage(message);
	}
	
}

