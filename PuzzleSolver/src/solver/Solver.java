package solver;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

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
		for (String s: this.s_pieces.keySet()){
			if (s.startsWith("FLAT FLAT ")){
				Piece p = getPieceFromSorted(s);
								
				current_chunk.placeAt(p, 0, 0);
				NotificationSystem.notifyAll(p, 0, 0);
				break;
			}
		}
		
		
		// Not needed....
//		// 1.b rotate until set as bottom left
//		while (corner1.left() == Connection.FLAT && corner1.bottom() == Connection.FLAT){
//			corner1.rotate();
//		}
		

		// 2. Place bottom row (until corner)
		Piece px = getPieceToFit(Connection.FLAT , null, null , current_chunk.getPiece(0, 0).right() );

		
		
		// 3. Start placing pieces


		// 4. Have we failed? return a few points and try again
//		Collections.shuffle((List<Piece>) this.inners);
		NotificationSystem.setStatus(id,"stuff");

		///// A WAY TO ITERATE THROUGH AND CONSIDER SIDES....
		//		// Generates the puzzle solution row by row
		//		for (int x = 0; x < width; x++){
		//			for (int y = 0; y < height; y++){ 
		//				Connection[] conns = new Connection[4];
		//				
		//				// Handle each edge
		//				if (y == 0) conns[0] = Connection.FLAT; //Bottom
		//				else conns[0] =  b.getPiece(x, y-1).top();
		//				if (x == 0) conns[1] = Connection.FLAT; // Left
		//				else conns[1] =  b.getPiece(x-1, y).right();
		//				
		//				if (y == height - 1) conns[2] = Connection.FLAT;
		//				else conns[2] = Connection.random(colors);
		//				if (x == width - 1) conns[3] = Connection.FLAT;
		//				else conns[3] = Connection.random(colors);
		//
		//				Piece p = new Piece(conns);
		//				b.placeAt(p, x, y);
		//				NotificationSystem.notifyAll(p, x, y);
		//			}
		//		}

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
