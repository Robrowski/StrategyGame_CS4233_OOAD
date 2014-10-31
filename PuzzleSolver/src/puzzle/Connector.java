package puzzle;
/**
 * 
 */

/**
 * @author rpdabrowski
 *
 */
public class Connector {
	Connection c; // What can be connected to
	// Data
	
	public Connector(Connection c){
		this.c = c;
	}
	
	public String toString(){
		return c.toString();
	}
	
	public void print(){
		System.out.print( " " + this.c + " " );	
	}
	
	public boolean equals(Connector other){
		return this.c == other.c;
	}
}
