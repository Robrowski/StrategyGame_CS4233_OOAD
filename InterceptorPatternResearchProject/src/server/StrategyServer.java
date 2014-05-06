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


import game.GameController;

import java.util.Collection;
import java.util.LinkedList;

import common.StrategyException;
import message.Sendable;
import server.message.MessageProcessingDispatcher;


/** A basic server to take in messages from the client over the 
 *  network, translate those to moves and try to use those 
 *  moves in a game of Strategy. This is implemented as a
 *  singleton.
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class StrategyServer {

	
	/** The dispatcher for handling the processing of messages */
	final private MessageProcessingDispatcher processMessageDispatcher = MessageProcessingDispatcher.getInstance();
	/** An instance of the StrategyServer         */
	protected final static StrategyServer instance = new StrategyServer();
	/** The current game */
	protected static GameController game = null;
	/** A log of messages */
	protected static Collection<Sendable> log = new LinkedList<Sendable>();
	
	/**
	 * Default private constructor to ensure this is a singleton.
	 * 
	 * This has been made protected for the purposes of testing!
	 */
	protected StrategyServer() {
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static StrategyServer getInstance()
	{
		return instance;
	}
	
	/** Receives a message, processes it and performs actions if necessary
	 *  and returns how it went. A message may be sent back before the end
	 *  of this method. 
	 * 
	 * THIS IS THE MAIN INTERCEPTION POINT
	 * 
	 * 
	 * @param message the message to receive
	 * @return how the receiving went.
	 */
	public Sendable receiveMessage(Sendable message){
		// Have a default status ready
		message.setMessageStatus(false);
		return processMessageDispatcher.handleMessageProcessing(message, game);
	}

	/** Add the given message to the log
	 * 
	 * @param aMessage to add
	 */
	public void addToLog(Sendable aMessage){
		this.getLog().add(aMessage);
	}

	/** Gets the log
	 * @return the log
	 */
	public Collection<Sendable> getLog(){
		return log;
	}

	/** Set the current game to the given game
	 * 
	 * @param game2 game to set to
	 * @throws StrategyException if game was already set
	 */
	public static void setGame(GameController game2) throws StrategyException {
		// Check to see if the game was started already
		if (game == null){
			game = game2;
		} else {// Oops it was already set
			throw new StrategyException("The game has already been intialized");
		}
		
	}

}