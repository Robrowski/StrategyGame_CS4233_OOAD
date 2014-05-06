/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common;

/** This extension of MoveResult provides some extra information about
 *  the result of a move so that field configurations may be updated
 *  properly and in a consistent manner.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class DetailedMoveResult extends MoveResult{

	/** This is a descriptor of a piece to remove from the field configuration*/
	private final Location pieceThatMoved;
	/** If there was a battle, this is the loser */
	private final Location loser;

	
	/** DetailedMoveResult takes same items as MoveResult, but also expects a 
	 *  PieceLocationDescriptor of the piece that moved, as well as one for 
	 *  the loser of the battle (if there was one).
	 * 
	 * @param status          The status of the game
	 * @param battleWinner    The winner of the battle
	 * @param pieceThatMoved  The piece that moved
	 * @param loser           The loser to remove if there was one
	 */
	public DetailedMoveResult(MoveResultStatus status,
			PieceLocationDescriptor battleWinner,
			Location pieceThatMoved,
			Location loser) {
		super(status, battleWinner);
		this.pieceThatMoved = (pieceThatMoved);
		this.loser = (loser);
	}


	/**
	 * @return the pieceThatMoved
	 */
	public Location getPieceThatMoved() {
		return pieceThatMoved;
	}

	/**
	 * @return the loser
	 */
	public Location getLoser() {
		return loser;
	} 

	/** Determines if two DetailedMoveResults are equal in terms of their locations
	 * 
	 * @param aMove the move to compare to	
	
	 * @return true if equivalent */
	public boolean isEqual(DetailedMoveResult aMove){
		
		return pieceThatMoved.distanceTo(aMove.getPieceThatMoved()) == 0 
				&& this.getBattleWinner().getLocation().distanceTo(aMove.getBattleWinner().getLocation()) == 0;
	}


	/** Figure out if two moves are opposite - used by move repetition rule
	 * @param aMove the move to compare to
	
	 * @return boolean for whether or not the two are opposite */
	public boolean isOpposite(DetailedMoveResult aMove) {
		return pieceThatMoved.distanceTo(aMove.getBattleWinner().getLocation()) == 0 
				&& this.getBattleWinner().getLocation().distanceTo(aMove.getPieceThatMoved()) == 0;
	}

}
