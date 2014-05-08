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

import common.StrategyException;
import common.StrategyRuntimeException;

/** Implementation of a Go board of variable size
 * 
 * 
 * @author Dabrowski
 *
 */
public class GoBoard extends AbstractBoardManager {

	/** Square, odd number sides for board size */
	private int boardSize; 

	/** theBoard[x][y]  = piece  */
	protected final Piece[][] theBoard; 

	/** Creates a Go board. Throws exception for invalid input
	 * @param sideLength length of one side of a square Go board
	 * @throws StrategyException for invalid input
	 */
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


		/******************* Check for captures   ***********************************/
		// Pieces that are confirmed as captured go in this list
		LinkedList<PieceLocationDescriptor> capturePieces = new LinkedList<PieceLocationDescriptor>();
		// All non-null pieces that are visited go here
		LinkedList<PieceLocationDescriptor> nodesVisited  = new LinkedList<PieceLocationDescriptor>();

		//////////////////////   Find captures based on the piece just placed.
		Iterator<PieceLocationDescriptor> fourPossibleCaptureGroups = get4Adjacent( l).iterator();
		while (fourPossibleCaptureGroups.hasNext()){
			// The start of the current cluster search
			PieceLocationDescriptor groupSeed = fourPossibleCaptureGroups.next();
			
			// If the group seed is null or allied or visited, ignore the cluster
			if (groupSeed.getPiece() == null || groupSeed.getPiece().getOwner() == piece.getOwner()  || nodesVisited.contains(groupSeed)){
				continue;
			}
			
			// BFS along group, searching for "enemies" of piece only. If a null is found, terminate search
			// If search ends and no "null" is found, the group is to be removed because it is surrounded
			// by enemies/walls
			boolean nullFound = false;
			Collection<PieceLocationDescriptor> enemiesFound = new LinkedList<PieceLocationDescriptor>();
			
			// Use the linked list as a search queue
			// **This search lets nodes get visited up to 4 times.. not efficient...
			LinkedList<PieceLocationDescriptor> toSearch = new LinkedList<PieceLocationDescriptor>();
			toSearch.add(groupSeed);
			
			// Do the search
			while (!toSearch.isEmpty()){
				// The next search
				PieceLocationDescriptor aPiece = toSearch.pop();
				
				// If null is found, end the search
				if (aPiece.getPiece() == null ){
					nullFound = true;
					break;
				}
				
				// Ignore if visited 
				if (nodesVisited.contains(aPiece) ){ 
					continue;
				}
				nodesVisited.add(aPiece);
				
				// ignore if allied
				if (aPiece.getPiece().getOwner() == piece.getOwner()){
					continue;
				}
				
				// Search the next 4 for more enemies/ replenish the search
				enemiesFound.add(aPiece);
				toSearch.addAll(get4Adjacent(aPiece.getLocation()));				
			}
			
			// If null wasn't found, add all found enemies
			if (!nullFound && toSearch.isEmpty()){
				capturePieces.addAll(enemiesFound);
			}	
		}

		// Remove the pieces
		Iterator<PieceLocationDescriptor> toRemove = capturePieces.iterator();
		while (toRemove.hasNext()){
			this.removePiece(toRemove.next().getLocation());
		}

		// Return!
		return new MoveResult(MoveResultStatus.OK, new PieceLocationDescriptor(piece,l), capturePieces );
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
		int x = l.getCoordinate(Coordinate.X_COORDINATE);
		int y = l.getCoordinate(Coordinate.Y_COORDINATE);

		// Make sure its on the board
		if (x >= boardSize || x < 0 || y < 0 || y >= boardSize){
			throw new StrategyRuntimeException("Query was off the board");
		}
		return theBoard[x][y];
	}

	/** Get the 4 adjacent pieces, along with their locations
	 * 
	 * @param l
	 * @return
	 */
	private Collection<PieceLocationDescriptor> get4Adjacent(Location l){
		Collection<PieceLocationDescriptor> fourAdj = new LinkedList<PieceLocationDescriptor>();
		int x = l.getCoordinate(Coordinate.X_COORDINATE);
		int y = l.getCoordinate(Coordinate.Y_COORDINATE);

		// The change in x/y to get to 4adj
		int [] dx = { 0, 0, -1, 1};
		int [] dy = { -1, 1, 0 ,0 };

		// Find the four adjacent pieces
		for (int i = 0; i < 4; i++ ){
			Location adjLoc = new Location2D(x + dx[i], y + dy[i] );

			// Add the piece if it is on the board
			try {
				Piece adjPiece = getPieceAt(adjLoc);
				fourAdj.add(new PieceLocationDescriptor( adjPiece, adjLoc ) );
			} catch (StrategyRuntimeException sre){
				// don't add... 
			}
		}

		return fourAdj;
	}
}
