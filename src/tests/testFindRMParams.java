package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.beust.jcommander.JCommander;

import Executable.FindRMParams;
import Executable.WellsParams;

class testFindRMParams {
	
	static FindRMParams params = new FindRMParams();

	@Test
	void test() {
		
		String args [] = new String [] {"-humidity", "0.45", "-startradius", "10e-6", };
		
		// parse the command line
		JCommander.newBuilder()
		  .addObject(params)
		  .build()
		  .parse(args);

		assertEquals(0.45, params.getHumidity(), 1e-7);
		assertEquals(10e-6, params.getR_start(), 1e-7);
		assertEquals(291.15, params.getTemperature(), 1e-7);
		assertEquals(101325, params.getPressure(), 1e-7);
		assertEquals(2.0, params.getHeight(), 1e-7);

		args = new String [] {"-humidity", "0.98", "-startradius", "55e-6", "-temperature", "265.0", "-height", "1.75", "-pressure", "120004.0" };
		
		// parse the command line
		JCommander.newBuilder()
		  .addObject(params)
		  .build()
		  .parse(args);

		assertEquals(0.98, params.getHumidity(), 1e-7);
		assertEquals(55e-6, params.getR_start(), 1e-7);
		assertEquals(265.0, params.getTemperature(), 1e-7);
		assertEquals(120004.0, params.getPressure(), 1e-7);
		assertEquals(1.75, params.getHeight(), 1e-7);

		
	} // end test

} // end testWellsParams
