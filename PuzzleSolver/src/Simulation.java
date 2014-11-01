import java.util.Collection;
import java.util.Collections;
import java.util.List;

import notifications.NotificationSystem;
import puzzle.Piece;
import solver.PuzzleGenerator;
import solver.Solver;
import visualizer.VisualizationFrame;
import boards.Board;


public class Simulation {
	private static VisualizationFrame x, y;
	private static Solver s;
	
	public static void main(String args[]) {
		int width = 20;
		int height = 12;
		int colors = 2;
//		x = new VisualizationFrame(width, height, "generator");

//		NotificationSystem.register(x.getPuzzlePanel());
		Collection<Piece> pieces = PuzzleGenerator.generateRandomPuzzle(width, height, colors, 0);
		Collections.shuffle((List<Piece>) pieces); /// Can cast because we know it is a linked list
		 
		y = new VisualizationFrame(width, height, "solver");
		NotificationSystem.register(y.getPuzzlePanel());

		s = new Solver(new Board(width, height), pieces);

		
		// Print some stats about the solvers records 
//		int i = 0;
//		for (String astring: s.s_pieces.keySet()){
//			i++;
//			System.out.println(astring + ": " + s.s_pieces.get(astring).size());
//		}
//		System.out.println("Total: " + i);
				
		
		s.solve();
		
		
		

	}
}
