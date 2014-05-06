/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package interceptor.message;

import common.StrategyException;
import game.GameController;
import game.common.MoveResult;
import interceptor.StrategyMessageInterceptor;
import message.Sendable;
import message.StrategyMessageType;
import message.implementations.MoveMessage;


/** An interceptor specifically made to handle Move messages
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class MoveMessageInterceptor implements StrategyMessageInterceptor {

	/* (non-Javadoc)
	 * @see interceptor.StrategyInterceptor#getServiceName()
	 */
	@Override
	public String getServiceName() {
		return StrategyMessageType.MOVE.toString();
	}

	/** Attempts to make a move - the origin of the message is 
	 * not checked - thus red may make valid blue moves etc.. meh
	 * 
	 * @param message the message containing move information
	 * @param game a reference to the current game
	 * @return the result of the move
	 */
	public Sendable handleMessage(Sendable message, GameController game) {
		// If this is called, we know what type the message was
		final MoveMessage theMessage = (MoveMessage) message;
		
		// can't do anything without a game
		if (game != null){
			try {
				// Try to move.. could throw an exception
				final MoveResult theResult = game.move(theMessage.getPieceType(),
						theMessage.getFrom() ,
						theMessage.getTo());
				
				// If here, all is well
				theMessage.setMoveResult(theResult);
				theMessage.setMessageStatus(true);
				
			// If there was a fault, get the message and package it all
			} catch (StrategyException se) {
				theMessage.setFaultReason(se.getMessage());
			}
		}
		
		return theMessage;	
	}

}
