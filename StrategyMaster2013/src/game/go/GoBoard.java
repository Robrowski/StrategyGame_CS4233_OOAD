/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game.go;

import game.common.Coordinate;
import game.common.Location;
import game.common.Location2D;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.board.AbstractBoardManager;
import game.common.turnResult.ITurnResult;
import game.common.turnResult.MoveResult;
import game.common.turnResult.MoveResultStatus;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import common.PlayerColor;
import common.StrategyException;
import common.StrategyRuntimeException;

/** Implementation of a Go board of variable size
 * 
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class GoBoard extends AbstractBoardManager {

	/** Square, odd number sides for board size */
	private final int boardSize; 

	/** theBoard[x][y]  = piece  */
	protected final Piece[][] theBoard; 

	/** Creates a Go board. Throws exception for invalid input
	 * @param sideLength length of one side of a square Go board
	 * @throws StrategyException for invalid input */
	public GoBoard(int sideLength) throws StrategyException {
		// Check that the board is bigger than 5x5 and is odd length
		if (sideLength < 5 || sideLength % 2 == 0){
			throw new StrategyException("Invalid board size");
		}

		boardSize = sideLength;

		// Initialize the board
		theBoard = new Piece[boardSize][boardSize];		
		for (int x = 0; x < boardSize; x++ ){
			for (int y = 0; y < boardSize; y++){
				theBoard[x][y] = null;
			}
		}
	}


	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#placePiece(game.common.Piece, game.common.Location)
	 */
	@Override
	public ITurnResult placePiece(Piece piece, Location l) throws StrategyException {
		// Make sure the space is empty, then place
		if (getPieceAt(l) == null){
			theBoard[l.getCoordinate(Coordinate.X_COORDINATE)][l.getCoordinate(Coordinate.Y_COORDINATE)] = piece;
		} else {
			throw new StrategyException("Cannot place pieces on top of other pieces.");
		}

		// Find Captures		
		Collection<PieceLocationDescriptor> enemyCaptured = this.findCaptures(l, piece.getOwner().opposite());
		Collection<PieceLocationDescriptor> allyCaptured = this.findCaptures(l, piece.getOwner() );

		// Check for suicides 
		if (enemyCaptured.isEmpty() && !allyCaptured.isEmpty()){
			this.removePiece(l); // take the piece back out
			throw new StrategyException("Can't suicide bub.");
		}

		// Remove the pieces
		final Iterator<PieceLocationDescriptor> toRemove = enemyCaptured.iterator();
		while (toRemove.hasNext()){
			PieceLocationDescriptor n = toRemove.next();
			if (n.getPiece().getOwner() == piece.getOwner()){
				System.err.println("SHit");
			}
			this.removePiece(n.getLocation());
		}


		// Return!
		return new MoveResult(MoveResultStatus.OK, new PieceLocationDescriptor(piece,l), enemyCaptured );
	}

	/** Finds captures of the given color around the given location. Used for finding 
	 *  captures as well as detecting suicide moves
	 * 
	 * @param l the location
	 * @param searchingAlong the color of the placed piece
	 * @return a collection of PieceLocationDescriptors that show captured pieces (pieces without liberties)
	 */
	public Collection<PieceLocationDescriptor> findCaptures(Location l, PlayerColor searchingAlong){

		/******************* Check for captures   ***********************************/
		// Pieces that are confirmed as captured go in these lists
		Collection<PieceLocationDescriptor> captured = new LinkedList<PieceLocationDescriptor>();

		// All non-null pieces that are visited go here
		List<PieceLocationDescriptor> nodesVisited  = new LinkedList<PieceLocationDescriptor>();

		//////////////////////   Find captures around the piece just placed. Search the piece placed as well
		Collection<PieceLocationDescriptor>  groupSeeds = get4Adjacent( l);
		groupSeeds.add(new PieceLocationDescriptor(getPieceAt(l), l  ));
		final Iterator<PieceLocationDescriptor> fourPossibleCaptureGroups = groupSeeds.iterator();
		
		// Conduct the search
		while (fourPossibleCaptureGroups.hasNext()){
			// The start of the current cluster search
			PieceLocationDescriptor groupSeed = fourPossibleCaptureGroups.next();

			// If the group seed is null or allied or visited, ignore the cluster
			if (groupSeed.getPiece() == null || groupSeed.getPiece().getOwner() != searchingAlong  || nodesVisited.contains(groupSeed)){
				continue;
			}

			// BFS along group, searching for "enemies" of piece only. If a null is found, terminate search
			// If search ends and no "null" is found, the group is to be removed because it is surrounded
			// by enemies/walls
			boolean nullFound = false;
			Collection<PieceLocationDescriptor> potentialCaptures = new LinkedList<PieceLocationDescriptor>();

			// Use the linked list as a search queue
			// **This search lets nodes get visited up to 4 times.. not efficient...
			LinkedList<PieceLocationDescriptor> toSearch = new LinkedList<PieceLocationDescriptor>();
			toSearch.add(groupSeed);

			// Do the search
			while (!toSearch.isEmpty()){
				// The next search
				PieceLocationDescriptor aPiece = toSearch.pop();

				// Record if a null is found, end the search
				nullFound |= (aPiece.getPiece() == null);
				
				/****** This algorithm is not optimized. Theoretically, it could 
				 *  end as soon as a null is found in a cluster, however the cluster
				 *  may be connected to a node adjacent to the starting point. If that
				 *  is the case, ending early will leave the current cluster in the
				 *  nodes visited, possibly disallowing the the other starting points
				 *  for the search to find nulls, thus incorrectly finding captures. 
				 */				
				
				// Ignore if visited 
				if (nodesVisited.contains(aPiece) ){ 
					continue;
				}
				nodesVisited.add(aPiece);

				// ignore if not part of intended search
				if (nullFound || aPiece.getPiece().getOwner() != searchingAlong){
					continue;
				}
				
				// Search the next 4 for more enemies/replenish the search queue
				potentialCaptures.add(aPiece);
				toSearch.addAll(get4Adjacent(aPiece.getLocation()));				
			}
			
			// If null wasn't found, add all found enemies to captured list because there is no liberty
			if (!nullFound && toSearch.isEmpty() && potentialCaptures.size() > 0){
				captured.addAll(potentialCaptures);
			}
		}

		return captured;
	}



	/** Removes the piece on the board at the given location
	 * 
	 * @param l location of item to set to null
	 */
	private void removePiece(Location l){
		theBoard[l.getCoordinate(Coordinate.X_COORDINATE)][l.getCoordinate(Coordinate.Y_COORDINATE)] = null;
	}


	/* (non-Javadoc)
	 * @see game.common.board.IBoardManager#getPieceAt(game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location l) {
		final int x = l.getCoordinate(Coordinate.X_COORDINATE);
		final int y = l.getCoordinate(Coordinate.Y_COORDINATE);

		// Make sure its on the board
		if (x >= boardSize || x < 0 || y < 0 || y >= boardSize){
			throw new StrategyRuntimeException("Query was off the board");
		}
		return theBoard[x][y];
	}

	/** Get the 4 adjacent pieces, along with their locations
	 * 
	 * @param l
	 * @return could be null
	 */
	public Collection<PieceLocationDescriptor> get4Adjacent(Location l){
		final Collection<PieceLocationDescriptor> fourAdj = new LinkedList<PieceLocationDescriptor>();
		final int x = l.getCoordinate(Coordinate.X_COORDINATE);
		final int y = l.getCoordinate(Coordinate.Y_COORDINATE);

		// The change in x/y to get to 4adj
		final int [] dx = { 0, 0, -1, 1};
		final int [] dy = { -1, 1, 0 ,0 };

		// Find the four adjacent pieces
		for (int i = 0; i < 4; i++ ){
			Location adjLoc = new Location2D(x + dx[i], y + dy[i] );

			// Add the piece if it is on the board
			try {
				Piece adjPiece = getPieceAt(adjLoc);
				fourAdj.add(new PieceLocationDescriptor( adjPiece, adjLoc ) );
			} catch (StrategyRuntimeException sre){
				continue; 
			}
		}

		return fourAdj;
	}
}
