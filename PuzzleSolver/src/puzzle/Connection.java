package puzzle;

import java.awt.Color;
import java.util.regex.Pattern;


/** Enums to indicate what type a particular connector is
 * 
 * @author rpdabrowski
 *
 */
public enum Connection {

	RED,BLUE,GREEN,PURPLE,CYAN,FLAT;
	
	/** Returns whether the pieces can connect or not. Always false if a 
	 *  flat is involved
	 * 
	 * @param o
	 * @return
	 */
	public boolean canConnect(Connection o){
		if (this == Connection.FLAT || o == Connection.FLAT)
			return false;
		return this == o;
	}
	
	/** Gives a random connection from the first "i" 
	 * 
	 * @param i The size of the sample set to pick from
	 * @return
	 */
	public static Connection random(int i){
		if (i <= 1) return Connection.RED;
		
		switch ((int) Math.round(Math.random()*(i-1 ))){
		case 0:
			return Connection.RED;
		case 1:
			return Connection.BLUE;
		case 2:
			return Connection.GREEN;
		case 3:
			return Connection.CYAN;
		case 4:
			return Connection.PURPLE;
		default:
			return Connection.FLAT;
		}
	}
	
	
	/** Returns the color for a given puzzle piece
	 * 
	 * @return
	 */
	public Color color(){
		switch(this){
		case BLUE:
			return Color.BLUE;
		case GREEN:
			return Color.GREEN;
		case RED:
			return Color.RED;
		case CYAN:
			return Color.CYAN;
		case PURPLE:
			return Color.MAGENTA;
		case FLAT:
		default:
			return Color.BLACK;
		}
	}
	
	/** Makes a regular expression of the connection, or * if null is given
	 * 
	 * @param a
	 * @return
	 */
	public static String makeCRegex(Connection a){
		if (a == null){
			return ".*"; // 3 or 4 characters... or more
		}
		return a.toString();
	}
	
	
}
