package model;
// import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e, sameE, diffE;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Sensor open at door");   // (1)
		d = Calendar.getInstance().getTime();   // (2)

		sameE = new Event("Sensor open at door");
		diffE = new Event("Sensor NOT open at door");
		
	}
	
	@Test
	public void testEvent() {
		assertEquals("Sensor open at door", e.getDescription());
		assertEquals(d.toString(), e.getDate().toString());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
	}


	@Test
	public void testEquals() {
		assertFalse(e.equals(null));
		assertFalse(e.equals("Other Class"));
		assertTrue(e.equals(e));
		assertTrue(e.equals(sameE));
	}

	@Test
	public void testHashCode() {
		assertEquals(e.hashCode(), sameE.hashCode());
		assertNotEquals(e.hashCode(), diffE.hashCode());
	}
}