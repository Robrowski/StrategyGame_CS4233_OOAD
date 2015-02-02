import java.util.Collection;
import java.util.Collections;
import java.util.List;

import notifications.FileLogger;
import notifications.NotificationSystem;
import puzzle.Piece;
import solver.PuzzleGenerator;
import solver.Solver;
import visualizer.VisualizationFrame;
import boards.Board;


public class Simulation {
	private static VisualizationFrame x, y;
	private static Solver s;
	private static FileLogger notificationLogger = new FileLogger(
			"notification");
	
	public static void main(String args[]) throws InterruptedException {
		int width = 20;
		int height = 12;
		int colors = 2;
//		x = new VisualizationFrame(width, height, "generator");

//		NotificationSystem.register(x.getPuzzlePanel());
		Collection<Piece> pieces = PuzzleGenerator.generateRandomPuzzle(width, height, colors, 0);
		Collections.shuffle((List<Piece>) pieces); /// Can cast because we know it is a linked list
		 
		y = new VisualizationFrame(width, height, "solver");
		NotificationSystem.register(y.getPuzzlePanel());
		NotificationSystem.register(notificationLogger);

		s = new Solver(new Board(width, height), pieces);

		
		// Print some stats about the solvers records 
//		int i = 0;
//		for (String astring: s.s_pieces.keySet()){
//			i++;
//			System.out.println(astring + ": " + s.s_pieces.get(astring).size());
//		}
//		System.out.println("Total: " + i);
				
		NotificationSystem.viz_delay = 10;
		s.solve();
		Thread.sleep(2000);

		y.flush();
	}
}
