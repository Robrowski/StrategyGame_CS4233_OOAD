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

import game.common.DetailedMoveResult;
import game.common.Location;
import game.common.Piece;
import game.common.PieceLocationDescriptor;

import java.util.Collection;
import java.util.Iterator;

import common.StrategyException;
import common.StrategyRuntimeException;

/** Abstract class to make basic implementations of IBoardManager
 * 
 * 
 * @author Dabrowski
 *
 */
public class AbstractBoardManager implements IBoardManager {

	
	
	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#addAll(java.util.Collection)
	 */
	@Override
	public void addToConfiguration(Collection<PieceLocationDescriptor> config) {
		final Iterator<PieceLocationDescriptor> allIter = config.iterator();
		while (allIter.hasNext()){
			PieceLocationDescriptor next = allIter.next();
			try {
				this.placePiece(next.getPiece(), next.getLocation()  );
			} catch (StrategyException e) {
				e.printStackTrace();
				throw new StrategyRuntimeException("Unable to place pieces in configuration");
			}
		}
	}
	


	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location location) {
		throw new StrategyRuntimeException("NOT IMPLEMENTED!");	
	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#updateField(game.common.DetailedMoveResult)
	 */
	@Override
	public DetailedMoveResult updateField(DetailedMoveResult theDMove) {
		throw new StrategyRuntimeException("NOT IMPLEMENTED!");	
	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPiecesInPath(game.common.Location, game.common.Location)
	 */
	@Override
	public Collection<Piece> getPiecesInPath(Location from, Location to) {
		throw new StrategyRuntimeException("NOT IMPLEMENTED!");	
	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#placePiece(game.common.Piece, game.common.Location)
	 */
	@Override
	public void placePiece(Piece piece, Location loc) throws StrategyException {
		throw new StrategyException("NOT IMPLEMENTED!");		
	}
}