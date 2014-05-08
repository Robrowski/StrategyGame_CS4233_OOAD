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

import game.common.Coordinate;
import game.common.Location;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.pieceStats.IPieceMoves;
import game.common.turnResult.DetailedMoveResult;
import game.common.turnResult.MoveResultStatus;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import common.PlayerColor;
import common.StrategyException;
import common.StrategyRuntimeException;


/** This is the board manager responsible for keeping track of a
 *  Gamma game of Strategy. It can add and remove pieces as well 
 *  as tell you what pieces are located at a particular location.
 * 
 * @author Dabrowski
 * @version 1.0
 */
public class MapBoardManager extends AbstractBoardManager {

	/** The underlying implementation of the field configuration */
	protected final Map<String, PieceLocationDescriptor> fieldConfiguration;
	/** a tool for figuring out the movement capabilities of pieces */
	private final IPieceMoves pieceMovementCapabilities;

	/** Basic constructor that just initializes variables and take in a map to
	 *  use to store data. It is requested that a map be passed in to allow 
	 *  for variability in speed/efficiency of the map, as well as scalability 
	 *  and size. Currently, a map with contents may also be passed in, although
	 *  this is advised against and the Manager will not be responsible for
	 *  the consequences.
	 * 
	 * @param emptyMap a map to use  
 	 * @param pieceMoves a tool for figuring out the movement capabilities of pieces
	 */
	public MapBoardManager(Map<String, PieceLocationDescriptor> emptyMap, IPieceMoves pieceMoves ){
		fieldConfiguration = emptyMap;
		pieceMovementCapabilities = pieceMoves;

	}



	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location location) {
		// Attempt to get the PieceLocationDescriptor
		final PieceLocationDescriptor pld = fieldConfiguration.get(location.toString());
		if (pld == null) return null;
		return pld.getPiece();
	}


	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#updateField(game.common.DetailedMoveResult)
	 */
	@Override
	public DetailedMoveResult updateField(DetailedMoveResult theDMove) {
		// Remove the piece that moved
		this.remove(theDMove.getPieceThatMoved());

		// Remove the loser if there was one
		if (theDMove.getLoser() != null){
			this.remove(theDMove.getLoser());
		}
		// Add the winner back in (if there was one)
		if (theDMove.getBattleWinner() != null){
			try {
				this.placePiece(theDMove.getBattleWinner().getPiece(),theDMove.getBattleWinner().getLocation() );
			} catch (StrategyException e) {
				e.printStackTrace();
			}
		}
		
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
					theDMove.getBattleWinner(), 
					theDMove.getPieceThatMoved(), 
					theDMove.getLoser());
		} else if (numReds == 0) {
			return new DetailedMoveResult(MoveResultStatus.BLUE_WINS, 
					theDMove.getBattleWinner(), 
					theDMove.getPieceThatMoved(), 
					theDMove.getLoser());
		} else if (numBlues == 0){
			return new DetailedMoveResult(MoveResultStatus.RED_WINS, 
					theDMove.getBattleWinner(), 
					theDMove.getPieceThatMoved(), 
					theDMove.getLoser());
		}

		
		return theDMove;
	}


	/** Remove the piece at the location given
	 * 
	 * @param aLoc the location of the piece to remove
	 */
	protected void remove(Location aLoc) {
		fieldConfiguration.remove(aLoc.toString());
	}

	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#placePiece(game.common.Piece, game.common.Location)
	 */
	@Override
	public void placePiece(Piece piece, Location loc) throws StrategyException {
		fieldConfiguration.put(loc.toString(), new PieceLocationDescriptor( piece, loc));	
	}



	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPiecesInPath(game.common.Location, game.common.Location)
	 */
	@Override
	public Collection<Piece> getPiecesInPath(Location from, Location to) {
		// Have a list Ready
		final Collection<Piece> piecesInPath = new LinkedList<Piece>();
		int direction, staticAxis;
		Coordinate axisMovedOn; 

		// Check to see if the move is a straight path
		try {
			if (2 > from.distanceTo(to)){
				return piecesInPath; // because we don't care about the path in this case
			}
		} catch(StrategyRuntimeException sre){
			return piecesInPath; // invalid path
		}

		// Figure out what axis to move on - if the X is the same, the move is on the Y axis, else X axis
		if (from.getCoordinate(Coordinate.X_COORDINATE) == to.getCoordinate(Coordinate.X_COORDINATE) ){
			axisMovedOn = Coordinate.Y_COORDINATE; // the coordinate that changes
			staticAxis =  from.getCoordinate(Coordinate.X_COORDINATE);
		} else {
			axisMovedOn = Coordinate.X_COORDINATE; // the coordinate that changes
			staticAxis =  from.getCoordinate(Coordinate.Y_COORDINATE);
		}

		// Figure out direction, positive or negative
		if (from.getCoordinate(axisMovedOn) < to.getCoordinate(axisMovedOn)){
			direction = 1;
		} else {
			direction = -1;
		}

		// Get the pieces
		for (int i = from.getCoordinate(axisMovedOn); i != to.getCoordinate(axisMovedOn) + direction; i += direction){
			Piece toAdd;
			if (axisMovedOn == Coordinate.X_COORDINATE){
				toAdd = this.getPieceAt(new Location2D(i, staticAxis));
			} else { // other axis
				toAdd = this.getPieceAt(new Location2D(staticAxis ,i));
			}

			// Only add if not null
			if (toAdd != null){
				piecesInPath.add(toAdd);
			}	
		}

		return piecesInPath;
	}
}
