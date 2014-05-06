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

import game.common.Coordinate;
import game.common.Location;
import common.StrategyRuntimeException;

/**
 * @author Dabrowski
 *
 */
public class Location1D implements Location {

	private int x;
	
	/**
	 * @param i
	 */
	public Location1D(int x) {
		this.x = x;
	}

	/* (non-Javadoc)
	 * @see game.common.Location#getCoordinate(game.common.Coordinate)
	 */
	@Override
	public int getCoordinate(Coordinate coordinate) {
		if (coordinate.equals(Coordinate.X_COORDINATE)){
			return x;
		}
		throw new StrategyRuntimeException("That coordinate is not used here");
	}

	/* (non-Javadoc)
	 * @see game.common.Location#distanceTo(game.common.Location)
	 */
	@Override
	public int distanceTo(Location otherLocation) {
		// TODO Auto-generated method stub
		return 0;
	}

}
