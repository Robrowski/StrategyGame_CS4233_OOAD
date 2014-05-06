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

import interceptor.StrategyMessageInterceptor;
import interceptor.StrategyPreprocessorInterceptor;
import message.Sendable;
import server.StrategyServer;
import strategy.game.StrategyGameController;


/** An interceptor for intercepting messages and logging them.
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class MessageLoggingInterceptor implements StrategyMessageInterceptor, StrategyPreprocessorInterceptor {


	/* (non-Javadoc)
	 * @see server.interceptor.StrategyInterceptor#getServiceName()
	 */
	@Override
	public String getServiceName() {
		return "LOGGER";
	}

	/** Adds the given message to the log
	 * 
	 * @param message the message to add
	 * @param a reference to the current game
	 */
	public Sendable handleMessage(Sendable message, StrategyGameController game) {
			
		// Add it to the log
		StrategyServer.getInstance().addToLog(message);
		
		// Say the message was a success
		message.setMessageStatus(true);
		
		// Proceed
		return message;
	}

	
	
}
