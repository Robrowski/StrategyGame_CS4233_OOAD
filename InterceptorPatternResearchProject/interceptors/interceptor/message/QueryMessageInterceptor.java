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
import message.Sendable;
import message.StrategyMessageType;
import message.implementations.QueryMessage;
import strategy.game.StrategyGameController;
import strategy.game.common.Piece;


/** An interceptor specifically made to handle board Queries
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class QueryMessageInterceptor implements StrategyMessageInterceptor {

	/* (non-Javadoc)
	 * @see interceptor.StrategyInterceptor#getServiceName()
	 */
	@Override
	public String getServiceName() {
		return StrategyMessageType.QUERY.toString();
	}

	/** Query the board for the requested information
	 *  and send a message back to the client
	 * 
	 * @param message contains the information about the query
	 * @return the result
	 */
	public Sendable handleMessage(Sendable message, StrategyGameController game) {
		// If this is called, we know what type the message was
		final QueryMessage theMessage = (QueryMessage) message;
		
		// If the game exists (is initialized and thus has a board)
		if (game != null){
			final Piece thePiece = game.getPieceAt(theMessage.getAt());
			theMessage.setAtLocation(thePiece);
			theMessage.setMessageStatus(true);
		}
		
		return theMessage;
	}

}
