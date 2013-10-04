/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package mockdata;

import strategy.common.StrategyRuntimeException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;

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
	 * @see strategy.game.common.Location#getCoordinate(strategy.game.common.Coordinate)
	 */
	@Override
	public int getCoordinate(Coordinate coordinate) {
		if (coordinate.equals(Coordinate.X_COORDINATE)){
			return x;
		}
		throw new StrategyRuntimeException("That coordinate is not used here");
	}

	/* (non-Javadoc)
	 * @see strategy.game.common.Location#distanceTo(strategy.game.common.Location)
	 */
	@Override
	public int distanceTo(Location otherLocation) {
		// TODO Auto-generated method stub
		return 0;
	}

}
