/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.delta;

import java.util.Iterator;
import java.util.Map;

import strategy.common.PlayerColor;
import strategy.game.common.DetailedMoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.board.MapBoardManager;
import strategy.game.common.pieceStats.IPieceMoves;

/** A Delta Specific board manager that modifies the field updating
 *  from the standard to incorporate the fact that bombs cannot 
 *  move. This fact introduces more conditions that could end
 *  the game early.
 * 
 * 
 * @author Dabrowski
 * @version $Revision: 1.0 $
 */
public class DeltaBoardManager extends MapBoardManager {

	
	/** a tool for figuring out the movement capabilities of pieces */
	private IPieceMoves pieceMovementCapabilities;

	/** A basic constructor to work the same as MapBoardManager
	 * 
	 * @param emptyMap an empty map
	 * @param deltaPieceMoves a tool for figuring out the movement capabilities of pieces
	 */
	public DeltaBoardManager(Map<String, PieceLocationDescriptor> emptyMap, DeltaPieceMoves deltaPieceMoves) {
		super(emptyMap);
		pieceMovementCapabilities = deltaPieceMoves;
	}



	/* (non-Javadoc)
	 * @see strategy.game.common.board.IBoardManager#updateField(strategy.game.common.DetailedMoveResult)
	 */
	@Override
	public DetailedMoveResult updateField(DetailedMoveResult theDMove) {
		// Evaluate the result the old way
		DetailedMoveResult oldResult = super.updateField(theDMove);
				
		// Have to check field for bombs and flags
		// Actually only checking for instances of movable pieces
		final Iterator<PieceLocationDescriptor> iter = fieldConfiguration.values().iterator();
		int numReds =0, numBlues=0; // if 1, then only flag remains
		while (iter.hasNext()){
			Piece next = iter.next().getPiece();
			// If not a choke point
			if (next.getOwner() != null){
				// If movable, count it
				if (next.getOwner() == PlayerColor.BLUE) numBlues+= pieceMovementCapabilities.getMovementCapability(next.getType());
				else numReds+= pieceMovementCapabilities.getMovementCapability(next.getType());
			}
		}

		// Evaluate the counts
		if (numReds == 0 && numBlues == 0 ){
			return new DetailedMoveResult(MoveResultStatus.DRAW, 
					oldResult.getBattleWinner(), 
					oldResult.getPieceThatMoved(), 
					oldResult.getLoser());
		} else if (numReds == 0) {
			return new DetailedMoveResult(MoveResultStatus.BLUE_WINS, 
					oldResult.getBattleWinner(), 
					oldResult.getPieceThatMoved(), 
					oldResult.getLoser());
		} else if (numBlues == 0){
			return new DetailedMoveResult(MoveResultStatus.RED_WINS, 
					oldResult.getBattleWinner(), 
					oldResult.getPieceThatMoved(), 
					oldResult.getLoser());
		}

		// Else the old result is good enough
		return oldResult;
	}
}
