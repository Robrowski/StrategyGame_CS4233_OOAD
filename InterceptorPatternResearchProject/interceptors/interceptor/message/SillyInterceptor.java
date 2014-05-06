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

import game.GameController;
import interceptor.StrategyMessageInterceptor;
import interceptor.StrategyPreprocessorInterceptor;
import message.Sendable;


/** A silly interceptor simply for the purposes of demonstrating
 *  that any sevice may be provided
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class SillyInterceptor implements StrategyMessageInterceptor, StrategyPreprocessorInterceptor {


	/* (non-Javadoc)
	 * @see server.interceptor.StrategyInterceptor#getServiceName()
	 */
	@Override
	public String getServiceName() {
		return "SILLY";
	}

	/** Does Silly Things
	 * 
	 * @param message the message to add
	 * @param a reference to the current game
	 */
	public Sendable handleMessage(Sendable message, GameController game) {
		System.out.println("I'm a silly interceptor... I was dynamically loaded from");
		System.out.println("   MessageProcessingInterceptors.txt   :D  ");
		System.out.println("   Make sure you look at the 'interceptors' folder  ");

		
		// Have to tell people I was successful
		message.setMessageStatus(true);
		return message;
	}

	
	
}
