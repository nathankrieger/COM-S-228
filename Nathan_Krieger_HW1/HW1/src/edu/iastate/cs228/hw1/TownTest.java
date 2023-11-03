package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author Nathan Krieger
 *
 */
class TownTest {

	@Test
	void testLengthWidthInit() {

		Town test = new Town(5, 6);

		assertEquals(test.getLength(), 5);
		assertEquals(test.getWidth(), 6);

	}

	@Test
	void randomInitTest() {

		Town testTown = new Town(4, 4);

		testTown.randomInit(10);

		String expectedResult = "O\tR\tO\tR\n" + "E\tE\tC\tO\n" + "E\tS\tO\tS\n" + "E\tO\tR\tR";

		String result = testTown.toString();

		assertEquals(result, expectedResult);

	}

	@Test
	void filePathTest() throws Exception {

		Town testTown = new Town("testFile.txt"); 

		String expectedResult = "O\tR\tO\tR\n" + "E\tE\tC\tO\n" + "E\tS\tO\tS\n" + "E\tO\tR\tR";

		String result = testTown.toString();

		assertEquals(expectedResult, result);
		assertEquals(testTown.getLength(), 4);
		assertEquals(testTown.getWidth(), 4);

	}

}
