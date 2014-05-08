/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package game;

import game.common.Location;
import game.common.Piece;
import game.common.PieceLocationDescriptor;
import game.common.PieceType;
import game.common.battle.IBattleEngine;
import game.common.board.IBoardManager;
import game.common.history.IMoveHistory;
import game.common.turnResult.DetailedMoveResult;
import game.common.turnResult.ITurnResult;
import game.common.turnResult.MoveResultStatus;
import game.common.validation.move.IMoveDistanceValidator;
import game.common.validation.move.IMoveSpecialCaseValidator;

import java.util.Collection;

import common.PlayerColor;
import common.StrategyException;

/** This class holds all the code that is common to all StrategyGameControllers
 *  as I plan to write them. All the code needed to validate configurations
 *  and moves is here. The key to keeping this code concise is the use of
 *  the VersionRules.class. This class holds all the worker objects that a
 *  controller relies on to get its work done. Those worker objects may also
 *  be version specific, thus the controller doesn't have to change to
 *  be useful to new versions of Strategy.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public abstract class AbstractStrategyGameController implements
GameController {

	/** Boolean flag for whether the game has started or not */
	protected boolean gameStarted;
	/** Boolean flag for whether the game is over or not */
	protected boolean gameOver;
	/** The current turn the next move is expected by */
	protected PlayerColor currentTurn;
	
	// The following are worker objects given by VersionRules
	/** Configuration for all the pieces */
	protected IBoardManager fieldConfiguration;
	/** The validator to use to make sure moves are OK */
	protected IMoveSpecialCaseValidator moveSpecialCaseValidator;
	/** The validator to use when validating movement distances */
	protected IMoveDistanceValidator moveDistanceValidator;
	/** The history of all the moves */
	protected IMoveHistory moveHistory;
	/** The battle engine */
	protected IBattleEngine battleEngine;

	/** Constructor for AbstractStrategyGameController. Takes two configurations, checks their 
	 *  validity (throwing an exception if either is invalid) and stores them internally,
	 *  as well as setting up a couple other basic variables involved with a game.
	 * @param redConfiguration Configuration for the red side
	 * @param blueConfiguration  Configuration for the blue side
	 * @param theRules a validator for checking the player configurations
	 * @throws StrategyException Thrown if an invalid configuration is detected */
	protected AbstractStrategyGameController(
			Collection<PieceLocationDescriptor>  redConfiguration,
			Collection<PieceLocationDescriptor>  blueConfiguration,
			VersionRules theRules)throws StrategyException {

		// Checks the configurations - throws an exception if invalidities are found
		theRules.getConfigurationValidator().validatePlayerConfigurations(redConfiguration, blueConfiguration);

		// Setup default fields
		gameStarted = false; // Default
		gameOver = false;    // Default
		currentTurn = theRules.getPlayer1(); // set by version

		// Save the move validators and worker objects
		moveSpecialCaseValidator = theRules.getMoveSpecialCaseValidator();
		moveDistanceValidator = theRules.getMoveDistanceValidator();
		moveHistory = theRules.getMoveHistory();
		battleEngine = theRules.getBattleEngine();	
		fieldConfiguration = theRules.getBoard();

		// Save the configurations
		fieldConfiguration.addToConfiguration(redConfiguration);   
		fieldConfiguration.addToConfiguration(blueConfiguration);
		fieldConfiguration.addToConfiguration(theRules.additionalFieldItems());
	}

	/* (non-Javadoc)
	 * @see game.GameController#placePiece(game.common.Piece, game.common.Location)
	 */
	@Override
	public ITurnResult placePiece(Piece piece, Location at) throws StrategyException {
		throw new StrategyException("HAHA not implemented.");	
	}

	/* (non-Javadoc)
	 * @see game.GameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException {
		if (gameStarted) throw new StrategyException("A game is already started!");
		gameStarted = true;
		gameOver = false;
	}


	/* (non-Javadoc)
	 * @see game.GameController#getPieceAt(game.common.Location)
	 */
	public Piece getPieceAt(Location location) {
		return fieldConfiguration.getPieceAt(location);		
	}

	/* (non-Javadoc)
	 * @see game.GameController#move(game.common.PieceType, game.common.Location, game.common.Location)
	 */
	@Override
	public ITurnResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		// Check the basic stuff first
		if (gameOver) {
			throw new StrategyException("The game is over, you cannot make a move");
		}
		if (!gameStarted) {
			throw new StrategyException("You must start the game!");
		}

		// Check if there is a piece at the given "from". If there is not, an exception should
		// be thrown. This catches the times that a location off of the board is input
		// into the "from" as well as when legitimate spaces don't have a piece.
		final Piece atFrom = this.getPieceAt(from);
		final Piece atTo = this.getPieceAt(to);
		
		// Verify the move distance, locations, and pieces involved
		verifyMove(piece, from, to, atFrom, atTo);

		// Do the battle since the move is valid
		DetailedMoveResult theDMove = battleEngine.doBattle(atFrom, from, atTo, to); // Have to start somewhere
	
		// Record the move - exceptions thrown here indicate the move repetition rule was violated
		try {
			moveHistory.recordMove(theDMove);
		} catch (StrategyException se){
			// Other player wins by default
			final MoveResultStatus winner = (currentTurn == PlayerColor.RED) ? MoveResultStatus.BLUE_WINS: MoveResultStatus.RED_WINS;
			
			theDMove = new DetailedMoveResult(winner, 
					theDMove.getBattleWinner(), 
					theDMove.getPieceThatMoved(), 
					theDMove.getLoser());
		}
		// Update the field after the move
		theDMove = fieldConfiguration.updateField(theDMove);
		
		
		// Update the turn states
		currentTurn = (atFrom.getOwner() == PlayerColor.RED) ? PlayerColor.BLUE
						: PlayerColor.RED;
		
		// Check if the game ended...
		if (theDMove.getStatus() != MoveResultStatus.OK) gameOver = true;
				
		return theDMove;
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
		// A tally of the problems- in the form of an error message string
		String invalidities = "The following invalidities occured: ";
		
		// Verify the start location and destination
		invalidities += moveSpecialCaseValidator.verifyDestination(currentTurn,     atTo);
		invalidities += moveSpecialCaseValidator.verifyStartLocation(currentTurn, atFrom, piece);
		
		// Verify the move path
		invalidities += moveSpecialCaseValidator.verifyMovePath(fieldConfiguration.getPiecesInPath(from, to));
		
		// If a piece was found, verify that it can go the distance
		if (atFrom != null){
			invalidities += moveDistanceValidator.moveDistanceIsValid(from, to, atFrom);	
		}
		
		// Check if any messages have been added to the error message, throw if necessary
		if (!invalidities.equals("The following invalidities occured: ")){
			throw new StrategyException(invalidities);
		}
	}

}
