/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.common.board;

import game.common.Location;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.turnResult.DetailedMoveResult;
import game.common.turnResult.ITurnResult;

import java.util.Collection;

import common.StrategyException;

/** A BoardManager is responsible for all additions and removals
 *  of pieces during a Strategy game. For most versions, the work 
 *  of getPieceAt is also deffered to the board manager from the
 *  controller.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public interface IBoardManager {

	/** Adds the given pieces+locations to the configuration
	 * 
	 * @param config the given pieces+locations to add to the configuration
	 */
	void addToConfiguration(Collection<PieceLocationDescriptor> config);

	/* (non-Javadoc)
	 * @see game.GameController#getPieceAt(game.common.Location)
	 */
	Piece getPieceAt(Location location);

	/** Update the field with a Detailed Move Result. Losers will be removed
	 *  and winners will be moved.
	 *  
	 * @param theDMove the full move details
	
	 * @return can remove an updated detailed move result if only the flag remains */
	DetailedMoveResult updateField(DetailedMoveResult theDMove);

	/** Assemble a list of all the pieces in the movement path. This implementation
	 *  is for straight lines only
	 * 
	 * @param from the location to start at
	 * @param to   the location to get to
	
	 * @return a list of the pieces in the path, cannot contain null */
	Collection<Piece> getPiecesInPath(Location from, Location to); 
	
	/** Adds a piece to the board. In certain implementations, piece removal
	 *  is also handled. EX: GO
	 * 
	 * @param piece to add
	 * @param loc   at location
	
	
	 * @return ITurnResult
	 * @throws StrategyException thrown upon error */
	ITurnResult placePiece(Piece piece, Location loc) throws StrategyException;
}
