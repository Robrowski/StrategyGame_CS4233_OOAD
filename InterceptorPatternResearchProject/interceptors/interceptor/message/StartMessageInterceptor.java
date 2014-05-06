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
import interceptor.StrategyMessageInterceptor;
import message.Sendable;
import message.StrategyMessageType;


/** An interceptor specifically made to handle START command messages
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class StartMessageInterceptor implements StrategyMessageInterceptor {

	/* (non-Javadoc)
	 * @see interceptor.StrategyInterceptor#getServiceName()
	 */
	@Override
	public String getServiceName() {
		return StrategyMessageType.START.toString();
	}

	/** Start a game
	 * 
	 * @param message the message containing information
	 * @param game a referen`ce to the current game
	 * @return the result of the move
	 */
	public Sendable handleMessage(Sendable message, GameController game) {
		// Try to start the game
		if (game != null){
			try {
				game.startGame();
				message.setMessageStatus(true);
			} catch (StrategyException se) {
				// Send off the message from the exception
				message.setFaultReason(se.getMessage());
			}
		}
		return message;
	}

}
