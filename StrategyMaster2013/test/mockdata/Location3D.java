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
public class Location3D implements Location {

	@SuppressWarnings("unused")
	private int z;
	private int x;
	private int y;

	/**
	 * @param i
	 * @param j
	 * @param k
	 */
	public Location3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	@Override
	public int getCoordinate(Coordinate coordinate)
	{
		return coordinate == Coordinate.X_COORDINATE ? x: y;
	}

	@Override
	public int distanceTo(Location otherLocation)
	{
		final int otherX =
				otherLocation.getCoordinate(Coordinate.X_COORDINATE);
		final int otherY =
				otherLocation.getCoordinate(Coordinate.Y_COORDINATE);
		if (x != otherX && y != otherY) {
			throw new StrategyRuntimeException(
					"Coordinates are not on same row or column");
		}
		return x == otherX ?
				Math.abs(y - otherY)
				: Math.abs(x - otherX);
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) return true;
		if (!(other instanceof Location3D)) return false;
		final Location3D that = (Location3D)other;
		return (x == that.x &&
				y == that.y);
	}

	@Override
	public int hashCode()
	{
		return (x + y + 1) * (x + 1) * (y + 1);
	}

	@Override
	public String toString()
	{
		return "(" + x + ',' + y + ')';
	}


}
