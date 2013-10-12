/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.epsilon;

import java.util.Collection;
import java.util.Iterator;

import strategy.common.StrategyException;
import strategy.game.common.DetailedMoveResult;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObservable;
import strategy.game.common.StrategyGameObserver;
import strategy.game.version.AbstractStrategyGameController;
import strategy.game.version.VersionRules;

/** The Epsilon game engine for the Epsilon version of Strategy. This class handles
 *  validating piece configurations, validating and recording moves and also
 *  locating pieces.
 *  
 *  The observer pattern is also implemented here
 * 
 * @author Dabrowski
 * @version $Revision: 6.9 $
 */
public class EpsilonStrategyGameController extends AbstractStrategyGameController implements StrategyGameObservable {

	/** The objects observer this observable */
	private final Collection<StrategyGameObserver> observers;
	/** red's configuration */
	private final Collection<PieceLocationDescriptor> redConfiguration;
	/** Blue's configuration */
	private final Collection<PieceLocationDescriptor> blueConfiguration;
	
	
	/** Constructor for AbstractStrategyGameController. Takes two configurations, checks their 
	 *  validity (throwing an exception if either is invalid) and stores them internally,
	 *  as well as setting up a couple other basic variables involved with a game.
	 * @param redConfiguration Configuration for the red side
	 * @param blueConfiguration  Configuration for the blue side
	 * @param theRules a validator for checking the player configurations
	 * @param observers observers that will watch this controller
	 * @throws StrategyException Thrown if an invalid configuration is detected */
	public EpsilonStrategyGameController(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration,
			VersionRules theRules, 
			Collection<StrategyGameObserver> observers) throws StrategyException {
		super(redConfiguration, blueConfiguration, theRules);
	
		this.redConfiguration = redConfiguration;   // for the observers only
		this.blueConfiguration = blueConfiguration; // for the observers only
		
		if (observers != null){
			this.observers = observers; // Automatic registration
		}
	}

	
	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException {
		super.startGame();
		notifyGameStart();
	}


	/** Notifies the observers that the game has started  */
	private void notifyGameStart() {
		final Iterator<StrategyGameObserver> obsIter = observers.iterator();
		while (obsIter.hasNext()){
			obsIter.next().gameStart(redConfiguration, blueConfiguration);		
		}
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#move(strategy.game.common.PieceType, strategy.game.common.Location, strategy.game.common.Location)
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		// Try to call the normal way
		try {
			final DetailedMoveResult theDMove = (DetailedMoveResult) super.move(piece, from, to);
			notifyMove(piece, from, to, theDMove, null);
			return theDMove;
		} catch(StrategyException se){
			// notify observer of exception, then rethrow
			notifyMove(piece, from, to, null, se);			
			throw se;
		}		
	}
	

	/** Notify the observers about the move
	 *
	 * @param piece piece attempting to move
	 * @param from  where the piece is
	 * @param to   where the piece is going
	 * @param result  result if there is one
	 * @param fault      fault if there is one
	 */
	private void notifyMove(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault) {
		final Iterator<StrategyGameObserver> obsIter = observers.iterator();
		while (obsIter.hasNext()){
			obsIter.next().moveHappened(piece, from, to, result, fault);
		}		
	}

	/* (non-Javadoc)
	 * @see strategy.game.common.StrategyGameObservable#register(strategy.game.common.StrategyGameObserver)
	 */
	@Override
	public void register(StrategyGameObserver observer) {
		observers.add(observer);
	}

	/* (non-Javadoc)
	 * @see strategy.game.common.StrategyGameObservable#unregister(strategy.game.common.StrategyGameObserver)
	 */
	@Override
	public void unregister(StrategyGameObserver observer) {
		observers.remove(observer);
	}





}
