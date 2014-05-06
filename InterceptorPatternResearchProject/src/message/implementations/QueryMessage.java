/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package message.implementations;

import message.AbstractMessage;
import message.StrategyMessageType;
import strategy.game.common.Location;
import strategy.game.common.Piece;

/** A message used to hold the information to 
 *  request a query at a location. It can
 *  also hold the result of the query when
 *  sent back from the server
 * 
 *  This implementation includes a security risk
 *  in that any player may request the piece at
 *  any location and thus figure out what the 
 *  other player has for pieces. Future implementations
 *  will check for that.
 * 
 * @author Dabrowski
 *
 * @version $Revision: 1.0 $
 */
public class QueryMessage extends AbstractMessage {

	/** The location to query */
	private final Location at;
	/** Whatever is at the location */
	private Piece atLocation = null;
	
	/** Basic constructor to hold a location to query
	 * 
	 * @param at  the location the piece is at
	 */
	public QueryMessage(Location at){
		type = StrategyMessageType.QUERY.toString();
		this.at = at;
	}

	/** Get the location to query
	 * 
	 * @return the location to query
	 */
	public Location getAt(){
		return at;
	}
	
	/** Get what was at the location
	 * @return whatever was at the location
	 */
	public Piece getAtLocation(){
		return atLocation;
	}
	
	/** set what was at the location
	 * @param whatever was at the location
	 */
	public void setAtLocation(Piece atLocation){
		this.atLocation = atLocation;
	}
}
