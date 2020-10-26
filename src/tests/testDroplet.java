package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.Droplet;
import helper.GlobalStaticProperties;

class testDroplet {

	@Test
	void test() {
		
		Droplet droplet = new Droplet();
		
		assertEquals(GlobalStaticProperties.T0 + 15.0, droplet.getTemperature());
		assertEquals(70e-6, droplet.getInitialRadius(),1e-10);
		assertEquals(2.0, droplet.getHeight(),1e-10);
		
		double [] testNrs = { 234.06, -34.2, 573.34, 1.34e10 };
		
		for( int i=0; i<testNrs.length; i++) {
			double nr = testNrs[i];
			
			droplet.setHeight(nr);
			droplet.setInitialRadius(nr);
			droplet.setTemperature(nr);
			
			Droplet d = new Droplet()
					.withHeight(nr)
					.withInitialRadius(nr)
					.withTemperature(nr);
			
			assertEquals(nr, droplet.getTemperature());
			assertEquals(nr, droplet.getInitialRadius(),1e-10);
			assertEquals(nr, droplet.getHeight(),1e-10);

			assertEquals(nr, d.getTemperature());
			assertEquals(nr, d.getInitialRadius(),1e-10);
			assertEquals(nr, d.getHeight(),1e-10);

		} // end for
		
	} // end test

} // end class
