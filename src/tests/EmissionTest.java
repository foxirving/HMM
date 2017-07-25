package tests;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import src.Metadata;

public class EmissionTest {
	
	
	
	@Before 
	public void setUp(){
		Metadata.INSTANCE.setFileName("test.txt");
		Metadata.INSTANCE.setStringToDecode("I fish for fish");
		Metadata.INSTANCE.setSmoothing(0.0);
		Metadata.INSTANCE.setMyStartProbability(1.0);
	}

	@Test
	public void testEmissionTableZeroNoSmoothing() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEmissionTableZeroWithSmoothing() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEmissionTableNonZeroNoSmoothing() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEmissionTableNonZeroWithSmoothing() {
		fail("Not yet implemented");
	}
	
	
	
}
