package solver;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

import notifications.NotificationSystem;
import puzzle.Connection;
import puzzle.Piece;
import boards.Board;
import boards.Chunk;

/**
 * 
 */

/** Solves a puzzle
 * 
 * v 1.0 tries to solve without rotating
 * v 2.0 tries rotating?
 * 
 * @author rpdabrowski
 *
 */
public class Solver  {

	Board b;
	Chunk current_chunk;
	PriorityQueue<Chunk> failures = new PriorityQueue<Chunk>(50);	
	public static final String id = "Solver";
	public  final HashMap<String,Queue<Piece>> s_pieces = new HashMap<String, Queue<Piece>>();


	/** Basic constructor to initialize data fields
	 * 
	 * @param b
	 */
	public Solver(Board b){
		// TODO :: DO this with a "toString" method that describes the connectors
		this.b = b;
		current_chunk = new Chunk(b);
	}


	/** Take the board and pieces and do some pre-sorting
	 * 
	 * @param b
	 * @param pieces 
	 */
	public Solver(Board b, Piece[] pieces){
		this(b);
		for (Piece p : pieces)	sortPiece(p);
	}

	/** Initializes the solver given a collection of pieces
	 * 
	 * @param b
	 * @param pieces
	 */
	public Solver(Board b, Collection<Piece> pieces){
		this(b);
		for (Piece p : pieces)	sortPiece(p);
	}

	/** Sorts and stores a given piece into an appropriate buckets
	 * 
	 * @param p
	 */
	protected void sortPiece(Piece p){
		Queue<Piece> q = this.s_pieces.get(p.toString());

		if (q == null){
			q = new LinkedList<Piece>();
			this.s_pieces.put(p.toString(), q);
		}

		q.add(p);

	}


	/** Solves a puzzle...
	 *  
	 */
	public void solve(){
		// 0. Initialize

		// 1. Place the four corners
		// int[] x_corner = {0,0,current_chunk.width-1, current_chunk.width-1};
		// int[] y_corner = {0,current_chunk.height-1,0,current_chunk.height-1};
		// for (int i = 0; i < 4; i++){
		// Piece px = getPieceToFit(x_corner[i], y_corner[i]);
		// current_chunk.placeAt(px, x_corner[i], y_corner[i]);
		// NotificationSystem.notifyPut(px, x_corner[i], y_corner[i]);
		// }

		// 2. Try to solve the edges. Retry until a valid edge is finished

		// boolean done = false;
		// int attempts = 0;
		// while (!done){
		// try {
		// attempts++;
		//
		// for (int x = 1; x < current_chunk.width - 1; x++ ){
		// Piece p = getPieceToFit(x, 0);
		//
		// current_chunk.placeAt(p, x, 0);
		// NotificationSystem.notifyPut(p, x, 0);
		// }
		// done = true;
		// } catch (Exception e){
		// System.out.println("fail");
		// // Remove the row
		// for (int x = 1; x < current_chunk.width - 1; x++){
		// Piece rp = current_chunk.remove(x, 0); // Take the piece off and put
		// it back...
		// if (rp != null) this.sortPiece(rp);
		// }
		//
		// }
		// }
		// System.out.println("attempts: " + attempts);
		// done = false;
		// while (!done){
		// // First Column
		// try {
		// for (int y = 1; y < current_chunk.height - 1; y++ ){
		// Piece p = getPieceToFit(0, y);
		// current_chunk.placeAt(p, 0, y);
		// NotificationSystem.notifyPut(p, 0, y);
		// }
		// done = true;
		//
		// } catch (Exception e){
		// System.out.println("fail");
		// // Remove the column
		// for (int y = 1; y < current_chunk.height - 1; y++) {
		// Piece rp = current_chunk.remove(0, y); // Take the pieces
		// // off
		// NotificationSystem.notifyRemove(0, y);
		// if (rp != null) this.sortPiece(rp);
		// }
		// }
		// }
		// System.out.println("attempts: " + attempts);

		// 3. Place row by row (until corner)

		// Place side columns first
		while (!attemptColumn(0))
			removeColumn(0);
		while (!attemptColumn(current_chunk.width - 1))
			removeColumn(current_chunk.width - 1);

		for (int y = 0; y < current_chunk.height; y++) {
			boolean done = false;
			while (!done) {
				try {
					for (int x = 1; x < current_chunk.width - 1; x++) {
						Piece p = this.getPieceToFit(x, y);
						if (p == null) 
							throw new PlacementException( x, y);

						current_chunk.placeAt(p, x, y);
						NotificationSystem.notifyPut(p, x, y);
					}
					done = true;
				} catch (PlacementException e) {
					for (int x = 1; x < current_chunk.width - 1; x++) {
						Piece rp = current_chunk.remove(x, y);
						NotificationSystem.notifyRemove(x, y);
						if (rp != null)
							this.sortPiece(rp);
					}

				}
			}

		}


		// 4. Have we failed? return a few points and try again
		//		Collections.shuffle((List<Piece>) this.inners);
		NotificationSystem.setStatus(id,"stuff");

	}

	/**
	 * Attempt to place pieces in the column
	 * 
	 * @param column
	 * @return
	 */
	boolean attemptColumn(int column) {
		for (int y = 0; y < current_chunk.height; y++) {
			Piece p = this.getPieceToFit(column, y);
			current_chunk.placeAt(p, column, y);
			NotificationSystem.notifyPut(p, column, y);
			if (p == null)
				return false;
		}

		return true;
	}

	/**
	 * Remove the pieces in the column
	 * 
	 * @param column
	 */
	void removeColumn(int column) {
		for (int y = 0; y < current_chunk.height; y++) {
			Piece rp = current_chunk.remove(column, y);
			NotificationSystem.notifyRemove(column, y);
			if (rp != null)
				this.sortPiece(rp);
		}
	}


	/**
	 * Attempt to place a line given the starts and ends. Return false on
	 * failure.
	 * 
	 * @param start_x
	 * @param start_y
	 * @param end_x
	 * @param end_y
	 * @return
	 */
	boolean attemptLine(int start_x, int start_y, int end_x, int end_y) {

		for (int i = 0; i < Math.max(end_y - start_y, end_x - start_x); i++) {
			int x = 1;
			int y = 1;

			Piece p = this.getPieceToFit(x, y);
			if (p == null)
				return false;
		}

		return true;
	}

	class PlacementException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5437667448840094447L;

		int x, y;
		public PlacementException(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

	}

	// Search orders
	int[] x_search = {0, -1, 0, 1};
	int[] y_search = {-1, 0, 1, 0};

	/** Gets a piece that fits in the given position. Neighbors that are 
	 *  null specify "random". Considers all 4 sides.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected Piece getPieceToFit(int x, int y){
		Connection[] conns = new Connection[4];

		for (int i = 0; i < conns.length; i++ ){
			try {
				Piece p = this.current_chunk.getPiece(x + x_search[i], y + y_search[i]);
				if (p == null){
					conns[i] = null;
				} else {
					switch (i){
					case 0:
						conns[i] = p.top();
						break;
					case 1:
						conns[i] = p.right();
						break;
					case 2:
						conns[i] = p.bottom();
						break;
					case 3:
						conns[i] = p.left();
						break;
					}
				}
			} catch (ArrayIndexOutOfBoundsException aioobe){ 
				conns[i] = Connection.FLAT;				
			}
		}
		return getPieceToFit(conns[0],conns[1],conns[2],conns[3]);
	}



	/** For the given connections, finds an available piece that matches them. nulls 
	 * represent cases where it doesn't matter
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	protected Piece getPieceToFit(Connection a, Connection b,Connection c,Connection d){	
		// Build up a regular expression that represents the "a b c d" with nulls = ****
		String regex = Connection.makeCRegex(a) + " " 
				+ Connection.makeCRegex(b) + " " 
				+ Connection.makeCRegex(c) + " " 
				+ Connection.makeCRegex(d) + " ";

		String matched_s = "";
		LinkedList<String> keys = new LinkedList<String>(this.s_pieces.keySet());
		Collections.shuffle(keys); // Provides randomness in puzzle piece placement
		// Iterate through the keys, pick the first that matches
		for (String s: keys){
			if (s.matches(regex)){
				///////////////////////////
				// TODO FIX THIS HACK
				// If it has a flat to fill a ".*", then its wrong
				StringTokenizer regex_tok = new StringTokenizer(regex);
				StringTokenizer     s_tok = new StringTokenizer(s);
				boolean bad = false;
				while (regex_tok.hasMoreTokens() && s_tok.hasMoreTokens()){
					String r = regex_tok.nextToken();
					String m = s_tok.nextToken();
					if (r.equals(".*") && m.equals("FLAT")){
						bad = true;
						break;
					}
				}
				if (bad){
					continue;
				}
				/////////////////////////////////


				matched_s = s;
				break;
			} 
		}

		return getPieceFromSorted(matched_s); // didn't find it...
	}

	/** Gets a piece out of the hash map and removes the key if the queue is empty
	 * 
	 * @param key
	 * @return
	 */
	protected Piece getPieceFromSorted(String key){
		if (!this.s_pieces.containsKey(key)) return null;		
		Piece p = this.s_pieces.get(key).poll();
		if (this.s_pieces.get(key).size() == 0){
			this.s_pieces.remove(key); // remove if empty
			// This might be the cause of a simultaneous access exception
		}

		return p;
	}

}
