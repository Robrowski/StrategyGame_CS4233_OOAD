/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.beta;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/**
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class BetaStrategyGameController implements StrategyGameController {

	/** The number of players */
	private static final int NUM_PLAYERS = 2;
	/** The size of the field */
	private static final int SIZE_FIELD = 6;
	/** The number of moves allowed in a game - one move per player */
	private static final int NUM_MOVES = 12;

	/** The number of pieces per team at initialization */
	private static final int NUM_TOTAL_PIECES_PER_TEAM = 12;
	/** The number of flags per team at initialization */
	private static final int NUM_FLAGS_PER_TEAM = 1;
	/** The number of marshals per team at initialization */
	private static final int NUM_MARSHALS_PER_TEAM = 1;
	/** The number of colonels per team at initialization */
	private static final int NUM_COLONELS_PER_TEAM = 2;
	/** The number of captains per team at initialization */
	private static final int NUM_CAPTAINS_PER_TEAM = 2;
	/** The number of lieutenants per team at initialization */
	private static final int NUM_LIEUTENANTS_PER_TEAM = 3;
	/** The number of sergeants per team at initialization */
	private static final int NUM_SERGEANTS_PER_TEAM = 3;

	/** Boolean flag for whether the game has started or not */
	private boolean gameStarted;
	/** Boolean flag for whether the game is over or not */
	private boolean gameOver;
	/** Configuration for all the pieces */
	final private Collection<PieceLocationDescriptor>  fieldConfiguration;
	/** The current turn = the next move is expected by */
	PlayerColor currentTurn = PlayerColor.RED;
	/** The number of moves left */
	private int movesLeft = NUM_MOVES;

	/** Constructor for BetaStrategyGameController. Takes two configurations, checks their 
	 *  validity (throwing an exception if either is invalid) and stores them internally,
	 *  as well as setting up a couple other basic variables involved with a game.
	 * @param redConfiguration Configuration for the red side
	 * @param blueConfiguration  Configuration for the blue side

	 * @throws StrategyException Thrown if an invalid configuration is detected */
	public BetaStrategyGameController(
			Collection<PieceLocationDescriptor>  redConfiguration,
			Collection<PieceLocationDescriptor>  blueConfiguration)throws StrategyException {
		int invalidities = 0; // Tally of invalidities

		// Check for the correct number of pieces in total
		if (blueConfiguration.size() != NUM_TOTAL_PIECES_PER_TEAM ) invalidities++;
		if (redConfiguration.size()  != NUM_TOTAL_PIECES_PER_TEAM ) invalidities++;

		// Check that the correct number for each piece is represented
		int player=0;
		final Map<Location,PieceLocationDescriptor> tempLocationChecker = new HashMap<Location,PieceLocationDescriptor>();
		for (Collection<PieceLocationDescriptor> config = redConfiguration; player < NUM_PLAYERS; config = blueConfiguration){ 
			// Keep track of the number of pieces
			int numFlags = 0, numMarshals =0, numCaptains =0, numColonels =0, numLieutenants =0, numSergeants =0;		

			Iterator<PieceLocationDescriptor> pieces = config.iterator();
			while (pieces.hasNext()){
				PieceLocationDescriptor next = pieces.next();
				// Check that all pieces of data do not have null

				PieceType typeOfPice = next.getPiece().getType();
				// Count each occurrence of the pieces
				if (typeOfPice.equals(PieceType.FLAG))	numFlags++;
				else if (typeOfPice.equals(PieceType.MARSHAL))  numMarshals++;
				else if (typeOfPice.equals(PieceType.COLONEL))  numColonels++;
				else if (typeOfPice.equals(PieceType.CAPTAIN))  numCaptains++;
				else if (typeOfPice.equals(PieceType.SERGEANT)) numSergeants++;
				else if (typeOfPice.equals(PieceType.LIEUTENANT)) numLieutenants++;
				else 	invalidities++;// not a valid piece	

				//Check to see if the next location has been used
				if (tempLocationChecker.containsKey(next.getLocation())){
					invalidities++; //Invalid to reuse locations
				} else {
					tempLocationChecker.put(next.getLocation(), next);
				} 

				// Check that locations are not in neutral zone
				int currentYCoord = next.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
				int currentXCoord = next.getLocation().getCoordinate(Coordinate.X_COORDINATE);

				// Check that the coordinates are not in negative regions
				if (currentYCoord < 0 || currentXCoord < 0) invalidities++;

				// Check that the coordinates are not out of bounds in positive regions
				if (currentYCoord >= SIZE_FIELD || currentXCoord >= SIZE_FIELD )invalidities++;

				// Check that pieces are not in enemy or neutral zones
				if (next.getPiece().getOwner() == PlayerColor.BLUE){
					// can't have blue out of its zone
					if (currentYCoord < 4) invalidities++;
				} else { // else the player is red
					// can't have red out of its zone
					if (currentYCoord > 1) invalidities++;
				}
			}

			if (numFlags != NUM_FLAGS_PER_TEAM) invalidities++;
			if (numMarshals != NUM_MARSHALS_PER_TEAM) invalidities++;
			if (numColonels != NUM_COLONELS_PER_TEAM) invalidities++;
			if (numSergeants != NUM_SERGEANTS_PER_TEAM) invalidities++;
			if (numLieutenants != NUM_LIEUTENANTS_PER_TEAM) invalidities++;
			if (numCaptains != NUM_CAPTAINS_PER_TEAM) invalidities++;
			player++; 
		}

		//	System.out.println("Number of invalidities: "+invalidities);  // TODO: remove print statements


		/* IF there are any invalidities, throw exception
		   The design was done this way, so that all errors that didn't include having 
		   null data would be accumulated. Null data causes an error to be thrown
		   before any of it can be read. This facilitates testing certain cases
		   easier because for one configuration property to be wrong usually
		   includes another property to be wrong. 
		   EX 2 flags causes the following violations -
		   	 > 12 pieces (if nothing else is removed)
		   	 	- location double use
		   	 	- OR placed illegally on the opponents side or neutral zone
		   	 If something IS removed for the flag to take its location, then 
		   the violation is that a different piece + flag have invalid quantities.


			Additional design consideration - append messages onto a null string, then
			throw error if the string != null.
		 */
		if (invalidities > 0){
			throw new StrategyException("One or more of the configurations had " 
					+ invalidities 
					+" cases of invalidity.");
		}

		gameStarted = false; // Default
		gameOver = false;    // Default
		fieldConfiguration = new LinkedList<PieceLocationDescriptor>();
		fieldConfiguration.addAll(redConfiguration);   
		fieldConfiguration.addAll(blueConfiguration);
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException {
		if (gameStarted) throw new StrategyException("A game is already started!");
		gameStarted = true;
		gameOver = false;
		movesLeft = NUM_MOVES;

	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#move(strategy.game.common.PieceType, strategy.game.common.Location, strategy.game.common.Location)
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		if (gameOver) {
			throw new StrategyException("The game is over, you cannot make a move");
		}
		if (!gameStarted) {
			throw new StrategyException("You must start the game!");
		}
		// A tally of the problems- in the form of an error message string
		String invalidities = "The following invalidities occured: ";

		// Check that the move is in bounds  
		final int toX = to.getCoordinate(Coordinate.X_COORDINATE);
		final int toY = to.getCoordinate(Coordinate.Y_COORDINATE);
		final int fromX = from.getCoordinate(Coordinate.X_COORDINATE);
		final int fromY = from.getCoordinate(Coordinate.Y_COORDINATE);


		if (toX >= SIZE_FIELD || toX < 0) invalidities+= "toX out of bounds, ";
		if (toY >= SIZE_FIELD || toY < 0) invalidities+= "toY out of bounds, ";
		if (Math.abs( toX-fromX) != 1 && Math.abs( toY-fromY) == 0 )invalidities+= "toX is non adjacent, ";
		else if (Math.abs( toX-fromX) == 0 && Math.abs( toY-fromY) != 1 )invalidities+= "toY is non adjacent, ";
		else if (Math.abs( toX-fromX) == 1 && Math.abs( toY-fromY) == 1 )invalidities+= "can't move diagonal, ";
		if (Math.abs( toX-fromX) > 1 && Math.abs( toY-fromY) > 1 )invalidities+= "that move isn't even close to adjacent, ";

		// Check if there is a piece at the given "from". If there is not, an exception should
		// be thrown. This catches the times that a location off of the board is input
		// into the "from" as well as when legitimate spaces don't have a piece.
		final Piece atFrom = this.getPieceAt(from);
		if (atFrom == null) invalidities+= "no piece found at 'from', "; // can't move nothing
		else {
			if (atFrom.getOwner() != currentTurn) invalidities+= atFrom.getOwner().toString() + " went out of turn, ";
			if (atFrom.getType() == PieceType.FLAG) invalidities+= "cannot move flag, "; // can't move flag
			if (atFrom.getType() != piece) invalidities+= "wrong piece at 'from'";  // can't move the wrong piece
			if (this.getPieceAt(to) != null && this.getPieceAt(to).getOwner() == currentTurn) invalidities+= "you cannot attack your own pieces, ";
			// if it gets here, it is valid
		}

		// Check if any messages have been added to the error message
		if (!invalidities.equals("The following invalidities occured: ")){
			throw new StrategyException(invalidities);
		}

		// Variables for use
		PieceLocationDescriptor result =null;
		MoveResult theMove = null;

		// Check for battle
		final Piece atTo = this.getPieceAt(to);
		if (atTo != null ){ // BATTLE MODE
			final int atkPower = BetaRules.getPower(atFrom.getType());
			final int defPower = BetaRules.getPower(atTo.getType());

			// If attacker wins
			if (atkPower > defPower){

				fieldConfiguration.remove(new PieceLocationDescriptor(atFrom, from));// Must move attacker
				fieldConfiguration.remove(new PieceLocationDescriptor(atTo,to));   // removing the loser
				result = new PieceLocationDescriptor(atFrom, to);
				fieldConfiguration.add(result); // save its new location
				theMove =  new MoveResult(MoveResultStatus.OK, result );

				if (defPower == 1){ // the flag has been attacked
					final MoveResultStatus winner = (currentTurn == PlayerColor.BLUE) ?  MoveResultStatus.BLUE_WINS
							: MoveResultStatus.RED_WINS;
					gameOver = true;
					return new MoveResult(winner, null);
				}
			} else if  (atkPower < defPower){
				fieldConfiguration.remove(new PieceLocationDescriptor(atFrom,from));   // remove the loser
				fieldConfiguration.remove(new PieceLocationDescriptor(atTo,to));       // remove the old winner position

				result = new PieceLocationDescriptor(atTo, from);
				fieldConfiguration.add(result); // move the winner to the attackers position
				theMove =  new MoveResult(MoveResultStatus.OK, result );
			} else { // it was a tie
				fieldConfiguration.remove(new PieceLocationDescriptor(atFrom,from));   // remove the loser
				fieldConfiguration.remove(new PieceLocationDescriptor(atTo,to));       // remove the loser
				theMove = new MoveResult(MoveResultStatus.OK, null);
			}
		}

		// stays null if there was no battle
		if (theMove == null){
			// Save non-battle move
			fieldConfiguration.remove(new PieceLocationDescriptor(atFrom, from));
			result = new PieceLocationDescriptor(atFrom, to);
			fieldConfiguration.add(result);		
			theMove = new MoveResult(MoveResultStatus.OK, result );
		}	
		// Save states
		currentTurn = (atFrom.getOwner() == PlayerColor.RED) ? PlayerColor.BLUE
				: PlayerColor.RED;
		movesLeft--;
		if (movesLeft == 0) {
			gameOver = true;
			return new MoveResult(MoveResultStatus.DRAW, theMove.getBattleWinner());
		}
		return theMove;
	}

	/* (non-Javadoc)
	 * @see strategy.game.StrategyGameController#getPieceAt(strategy.game.common.Location)
	 */
	@Override
	public Piece getPieceAt(Location location) {
		// configuration that contains them ALL
		final Iterator<PieceLocationDescriptor> allIter = fieldConfiguration.iterator();

		// Check each entry in the configuration for the given location
		while (allIter.hasNext()){
			PieceLocationDescriptor theNext = allIter.next();
			Location aLocation = theNext.getLocation();

			// Do a check and save the piece for returning if this is the right location
			if (aLocation.equals(location))	return theNext.getPiece();			
		}
		return null; // else
	}
}
