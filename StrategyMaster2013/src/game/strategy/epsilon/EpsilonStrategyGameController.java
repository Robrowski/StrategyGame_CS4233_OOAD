/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.strategy.epsilon;

import game.AbstractStrategyGameController;
import game.VersionRules;
import game.common.Location;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.turnResult.ITurnResult;
import game.common.turnResult.MoveResult;
import game.common.turnResult.MoveResultStatus;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import common.PlayerColor;
import common.StrategyException;
import common.StrategyRuntimeException;
import common.observer.GameObservable;
import common.observer.GameObserver;

/** The Epsilon game engine for the Epsilon version of Strategy. This class handles
 *  validating piece configurations, validating and recording moves and also
 *  locating pieces.
 *  
 *  The observer pattern is also implemented here
 * 
 * @author Dabrowski
 * @version $Revision: 6.9 $
 */
public class EpsilonStrategyGameController extends AbstractStrategyGameController implements GameObservable {

	/** The objects observer this observable */
	private final Collection<GameObserver> observers = new HashSet<GameObserver>();
	/** red's configuration */
	private Collection<PieceLocationDescriptor> redConfiguration = null;
	/** Blue's configuration */
	private Collection<PieceLocationDescriptor> blueConfiguration = null;
	/** Number of RedFlags remaining */
	private int redFlags = 2;
	/** Number of blueFlags remaining */
	private int blueFlags = 2;
	
	
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
			Collection<GameObserver> observers) throws StrategyException {
		super(redConfiguration, blueConfiguration, theRules);
	
		this.redConfiguration = redConfiguration;   // for the observers only
		this.blueConfiguration = blueConfiguration; // for the observers only
		
		if (observers != null){
			this.observers.addAll(observers); // Automatic registration
		} 
	}

	
	/* (non-Javadoc)
	 * @see game.GameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException {
		super.startGame();
		notifyGameStart();
	}


	/** Notifies the observers that the game has started  */
	private void notifyGameStart() {
		final Iterator<GameObserver> obsIter = observers.iterator();
		while (obsIter.hasNext()){
			obsIter.next().gameStart(redConfiguration, blueConfiguration);		
		}
	}

	/* (non-Javadoc)
	 * @see game.GameController#move(game.common.PieceType, game.common.Location, game.common.Location)
	 */
	@Override
	public ITurnResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		// Catch resignations
		if (piece == null && from == null && to == null){
			if (gameOver){
				throw new StrategyException("Why did you resign AFTER your opponent?!?!?");
			}
			final MoveResultStatus winner = (currentTurn == PlayerColor.RED ) ? MoveResultStatus.BLUE_WINS : MoveResultStatus.RED_WINS;
			final MoveResult result = new MoveResult(winner, null);
			notifyMove(piece, from, to, result, null);
			gameOver = true;
			return result;
		}
				
		// Try to call the normal way
		try {
			final Piece atTo = this.getPieceAt(to);
			ITurnResult theMove = super.move(piece, from, to);
			
			// Catch the case where 1 flag is capture but the OTHER remains
			if (atTo != null && atTo.getType() == PieceType.FLAG){
				// IF RED flag
				if (atTo.getOwner() == PlayerColor.RED){
					redFlags--; //decrement the count

					// IF a flag still remains, the status is FLAG_CAPTURED
					if (redFlags == 1){
						gameOver = false;
						theMove = new MoveResult(MoveResultStatus.FLAG_CAPTURED, theMove.getBattleWinner());
					}
				// ELSE BLUE flag
				} else {
					blueFlags--; //decrement the count
					
					// IF a flag still remains, the status is FLAG_CAPTURED
					if (blueFlags == 1){
						gameOver = false;
						theMove = new MoveResult(MoveResultStatus.FLAG_CAPTURED, theMove.getBattleWinner());
					}
				}
			}
			
			// Notify and finish
			notifyMove(piece, from, to, theMove, null);
			return theMove;
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
			ITurnResult result, StrategyException fault) {
		final Iterator<GameObserver> obsIter = observers.iterator();
		while (obsIter.hasNext()){
			obsIter.next().moveHappened(piece, from, to, result, fault);
		}		
	}

	/** Verify the move distance, locations, and pieces involved.
	 * 
	 * This is to be overridden when very special cases arise where both the pieces at the
	 * start and end and the path must all be checked at once
	 * 
	 * @param piece piece being moved
	 * @param from  location to start at
	 * @param to    location to move to
	 * @param atFrom piece at the start
	 * @param atTo   piece at the end
	 * @throws StrategyException
	 */
	protected void verifyMove(PieceType piece, Location from, Location to,
			final Piece atFrom, final Piece atTo) throws StrategyException {
		
		// Main way of figuring out which special case to deal with
		int moveDistance = 0;
		try{
			moveDistance = from.distanceTo(to);
		}catch(StrategyRuntimeException sre){
			sre.getMessage();
		}
		
		// Extremely special First_Lieutenant case
		if (piece == PieceType.FIRST_LIEUTENANT 
				&& moveDistance == 2 // First Lieutenants actually have a normal move to, so this is super special
				&& atTo != null
				&& (atTo.getType() != PieceType.CHOKE_POINT || atTo.getOwner() != currentTurn)){
			
			// Check that the lietenant is attacking and NOT jumping
			final Collection<Piece> path = fieldConfiguration.getPiecesInPath(from, to);
			if (path.size() == 2 && atTo != null){ // exactly 2 items and the second is not null indicates distance 2 attack
				return;
			}
			throw new StrategyException("The FirstLieutentant attack path was invalid");
		}

		// The original version
		super.verifyMove(piece, from, to, atFrom, atTo);
	}
	
	
	/* (non-Javadoc)
	 * @see game.common.GameObservable#register(game.common.GameObserver)
	 */
	@Override
	public void register(GameObserver observer) {
		observers.add(observer);
	}

	/* (non-Javadoc)
	 * @see game.common.GameObservable#unregister(game.common.GameObserver)
	 */
	@Override
	public void unregister(GameObserver observer) {
		if (!observers.remove(observer)){
			throw new StrategyRuntimeException("That observer was never registered - USE THE SAME INSTANCE");
		}
	}


}
