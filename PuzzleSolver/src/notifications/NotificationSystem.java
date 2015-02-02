package notifications;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import puzzle.Piece;


/**
 * @author rpdabrowski
 *
 */
public class NotificationSystem {
	
	/** How many milliseconds delay during simulation */
	public static int viz_delay = 0;

	/** Notify all observers of an added piece
	 * 
	 * @param p
	 * @param x
	 * @param y
	 */
	public static void notifyPut(Piece p, int x, int y){
		for (IPuzzleObserver ipo: ipo_list){
			ipo.notifyPlacement(p, x, y);
		}	
		delay();
	}


	public static void notifyRemove(int x, int y){
		for (IPuzzleObserver ipo: ipo_list){
			ipo.notifyRemove( x, y);
		}	
		delay();
	}
	
	/** If there is a requested delay.. sleep */
	private static void delay() {
		if (viz_delay > 0) {
			try {
				Thread.sleep(viz_delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/** List of observers	 */
	static protected final Collection<IPuzzleObserver> ipo_list = new LinkedList<IPuzzleObserver>();

	/** Register an observer to be notified when pieces are added
	 * 
	 * @param iso
	 */
	static public void register(IPuzzleObserver iso) {
		ipo_list.add(iso);		
	}
	
	/** Storage for statuses by string name */
	static protected final HashMap<String, String> statuses = new HashMap<String, String>();
	
	/** Sets the status for the given id
	 * 
	 * @param id
	 * @param status
	 */
	public static void setStatus(String id, String status){
		statuses.put(id, status);
	}
	
	/** Gets the status for the given id
	 * 
	 * @param id
	 * @return
	 */
	public static String getStatus(String id){
		return statuses.getOrDefault(id, "Status not set...");
	}
}
