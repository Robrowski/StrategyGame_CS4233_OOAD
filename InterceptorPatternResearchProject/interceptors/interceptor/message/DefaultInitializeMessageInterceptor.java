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
import message.implementations.CommandMessage;
import server.SampleConfigurations;
import server.StrategyServer;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.GameVersion;


/** This interceptor handles initialization of games
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class DefaultInitializeMessageInterceptor implements StrategyMessageInterceptor {

	/* (non-Javadoc)
	 * @see interceptor.StrategyInterceptor#getServiceName()
	 */
	@Override
	public String getServiceName() {
		return StrategyMessageType.DEFAULT_INITIALIZE.toString();
	}

	/** Try to initialize a game
	 * 
	 * @param message the message containing information
	 * @param game a reference to the current game
	 * @return the result of the move
	 */
	public Sendable handleMessage(Sendable message, StrategyGameController game) {
		// The version of the message
		final GameVersion version = ((CommandMessage) message).getVersion();

		/** The factory that provides StrategyGames   */
		final StrategyGameFactory theFactory = StrategyGameFactory.getInstance();


		final SampleConfigurations sample = SampleConfigurations.getInstance();

		// Catch exceptions thrown by the controller
		try {
			// Initialize a game
			switch (version){
			case ALPHA:
				game = theFactory.makeAlphaStrategyGame();
				message.setMessageStatus(true);
				break;
			case BETA:
				game = theFactory.makeBetaStrategyGame(sample.getValidBetaRedConfig(), 
						sample.getValidBetaBlueConfig());
				message.setMessageStatus(true);
				break;
			case GAMMA:
				game = theFactory.makeGammaStrategyGame(sample.getValidBetaRedConfig(), 
						sample.getValidBetaBlueConfig());
				message.setMessageStatus(true);
				break;
			case DELTA:
			default:
				break;
			}
		// This is here for when the client sets the configuration, some day
		} catch (StrategyException se){ 
			// Send off the message from the exception
			message.setFaultReason(se.getMessage());
		}
		
		// Since a game was made, try to set it
		try {
			StrategyServer.setGame(game);
		} catch (StrategyException se) {
			// If a game was already made, tell the message
			message.setMessageStatus(false);
			message.setFaultReason(se.getMessage());
		}
		
		// return the message to send back
		return message;
	}

}
