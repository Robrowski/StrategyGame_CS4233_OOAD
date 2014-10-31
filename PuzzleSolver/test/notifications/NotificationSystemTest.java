/**
 * 
 */
package notifications;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import puzzle.Piece;
import MockObjects.MockObserver;
import boards.Board;

/** Tests for the notification system
 * 
 * @author rpdabrowski
 *
 */
public class NotificationSystemTest {
	Board b = new Board(10,10);
	
	@Before
	public void setup(){
		new NotificationSystem();
	}


	@Test
	public void testNotifyAll(){
		NotificationSystem.register(new MockObserver(this.b));
		for (int x = 0; x < b.width; x++){
			for (int y = 0; y < b.height; y++){
				NotificationSystem.notifyAll(new Piece(null,null,null,null), x, y);
			}
		}
				
	}
	
	@Test 
	public void testStatuses(){
		NotificationSystem.setStatus("test", "THIS IS A STATUS");
		assertEquals("THIS IS A STATUS", NotificationSystem.getStatus("test"));
		NotificationSystem.setStatus("test", "NEW STATUS");
		assertEquals("NEW STATUS", NotificationSystem.getStatus("test"));
		assertNotEquals("THIS IS A STATUS", NotificationSystem.getStatus("test"));
		
		assertEquals("Status not set...", NotificationSystem.getStatus("random id"));
	}
	
	
}
