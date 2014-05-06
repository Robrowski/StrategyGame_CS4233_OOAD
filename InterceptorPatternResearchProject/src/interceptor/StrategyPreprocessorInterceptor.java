/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package interceptor;

import message.Sendable;
import strategy.game.StrategyGameController;


/** This class is to be implemented by all classes
 *  claiming to be an interceptor. It is extremely
 *  important that  lasses that are brought in 
 *  out-of-band implement this interface.
 * 
 *  NOTE* classes implementing this interface should not
 *  have constructors that take arguments
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $

 */
public interface StrategyPreprocessorInterceptor extends StrategyInterceptor {

	/** Handle a message/do what ever the message says to do
	 * 
	 * The following parameters = context object
	 * @param message the message saying what to do
	 * @param game a reference to the current game, if needed
	 * @return a message to send back
	 */
	Sendable handleMessage(Sendable message, StrategyGameController game);

	
}
