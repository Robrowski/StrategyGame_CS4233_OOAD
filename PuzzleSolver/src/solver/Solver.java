package solver;
import java.util.Collection;
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
		
		// 1. Place a corner
		// 1.a pick a bottom left corner (starts with FLAT FLAT * * 
		Piece px = getPieceToFit(Connection.FLAT , Connection.FLAT, null , null );
		current_chunk.placeAt(px, 0,0);
		NotificationSystem.notifyAll(px, 0, 0);
		
		
		// Not needed....
//		// 1.b rotate until set as bottom left
//		while (corner1.left() == Connection.FLAT && corner1.bottom() == Connection.FLAT){
//			corner1.rotate();
//		}
		
		// TODO: Solve all edges, then spiral in... 
		try {
			for (int x = 1; x < current_chunk.width - 1; x++ ){
				Piece p = getPieceToFit(Connection.FLAT , current_chunk.getPiece(x - 1 , 0).right(), null , null );
				current_chunk.placeAt(p, x, 0);
				NotificationSystem.notifyAll(p, x, 0);
			}
			for (int y = 1; y < current_chunk.height - 1; y++ ){
				Piece p = getPieceToFit(current_chunk.getPiece(0 , y -1).top(), Connection.FLAT  , null , null );
				current_chunk.placeAt(p, 0, y);
				NotificationSystem.notifyAll(p, 0, y);
			}
		} catch (Exception e){
			
		}
		
		// 2. Place bottom row (until corner)
		for (int y = 1; y < current_chunk.height -1; y++){
			for (int x = 1; x < current_chunk.width - 1; x++ ){
				Piece p = this.getPieceToFit(x, y);
//				Piece p = getPieceToFit(current_chunk.getPiece(x , y-1).top() , current_chunk.getPiece(x - 1 , y).right(), null , null );
				current_chunk.placeAt(p, x, y);
				NotificationSystem.notifyAll(p, x, y);
			}
		}
		


		// 4. Have we failed? return a few points and try again
//		Collections.shuffle((List<Piece>) this.inners);
		NotificationSystem.setStatus(id,"stuff");

	}
	
	
	// Search orders
	int[] x_search = {0, -1, 0, 1};
	int[] y_search = {-1, 0, 1, 0};
	
	/** Gets a piece that fits in the given position. Neighbors that are 
	 *  null specify "random"
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
		// Iterate through the keys, pick the first that matches
		for (String s: this.s_pieces.keySet()){
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
