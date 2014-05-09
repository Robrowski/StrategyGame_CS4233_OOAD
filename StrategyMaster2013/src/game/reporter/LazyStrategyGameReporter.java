/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.reporter;

import game.common.Location;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.turnResult.ITurnResult;

import java.util.Collection;
import java.util.Iterator;

import common.StrategyException;
import common.StrategyRuntimeException;
import common.observer.GameObserver;

/** This is a lazy reporter that simply prints what ever it is given
 *  whenever it is notified about events. 
 * 
 * @author Dabrowski
 * @version $Revision: 6.9 $
 */
public class LazyStrategyGameReporter implements GameObserver {

	/* (non-Javadoc)
	 * @see game.common.GameObserver#gameStart(java.util.Collection, java.util.Collection)
	 */
	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {
		System.out.println("A new game has started!");
		printConfig(redConfiguration);
		printConfig(blueConfiguration);
		System.out.println(" ");
	}

	/** Prints the given configuration
	 * @param config the configuration to print
	 */
	private void printConfig(
			Collection<PieceLocationDescriptor> config) {
		final Iterator<PieceLocationDescriptor> configIter =   config.iterator();
		while (configIter.hasNext()){
			PieceLocationDescriptor next = configIter.next();
			System.out.println(next.toString());
		}
	}

	/* (non-Javadoc)
	 * @see game.common.GameObserver#moveHappened(game.common.PieceType, game.common.Location, game.common.Location, game.common.MoveResult, common.StrategyException)
	 */
	@Override
	public void moveHappened(PieceType piece, Location from, Location to,
			ITurnResult result, StrategyException fault) {
		// Catch resignation
		if (piece == null && from == null && to == null){
			System.out.println("Dishonorable resignation: " + result.getStatus().toString());
			System.out.println(" ");
			return;
		}

		System.out.println("A move was attempted: ");
		System.out.println("Piece: " + piece.toString());
		System.out.println("From:  " + from.toString());		
		System.out.println("To:    " + to.toString());

		if (result != null){
			System.out.print("Result:");
			if (result.getBattleWinner() != null){
				if (result.getBattleWinner().getPiece() != null){
					System.out.print(result.getBattleWinner().getPiece().toString() + " won ");
				}
				if (result.getBattleWinner().getLocation() != null){
					System.out.print(" at " + result.getBattleWinner().getLocation().toString());	
				}
			} else {
				System.out.print("the pieces killed eachother lol");
			}
			System.out.println( ", Game Status: " + result.getStatus().toString());
		}
		if (fault != null)	System.out.println("Fault :" + fault.getMessage());
		System.out.println(" ");
	}

	/* (non-Javadoc)
	 * @see common.observer.GameObserver#placeHappened(game.common.turnResult.ITurnResult, common.StrategyException)
	 */
	@Override
	public void notifyPlacement(ITurnResult result, StrategyException fault) {
		throw new StrategyRuntimeException("Not implemented");
	}
}
