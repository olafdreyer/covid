package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.Droplet;
import helper.GlobalStaticProperties;
import helper.HeightRadiusReached;
import helper.HeightReached;
import helper.RadiusReached;

class testEventHandler {

	@Test
	void test() {
		
		Droplet droplet = new Droplet()
				.withHeight(2.0) // 2m initial height, not needed here
				.withInitialRadius(200e-6)
				.withTemperature(GlobalStaticProperties.T0 + 33.0)
				.withRMin(2.0e-6);
		
		RadiusReached rr = new RadiusReached()
				.withRmin(droplet.getRMin());
		HeightRadiusReached hrr = new HeightRadiusReached()
				.withRMin(droplet.getRMin())
				.withDroplet(droplet);
		HeightReached hr = new HeightReached()
				.withDroplet(droplet);

		// rr
		assertEquals(true, rr.g(1.0, new double [] {1e-6,0.1,1.2}) > 0.0);
		assertEquals(true, rr.g(1.0, new double [] {1e-10,0.1,1.2}) > 0.0);
		assertEquals(false, rr.g(1.0, new double [] {3e-6,0.1,1.2}) > 0.0);
		assertEquals(1.0e-6, rr.g(1.0, new double [] {1e-6,0.1,1.2}), 1e-10);
		assertEquals(1.9999e-6, rr.g(1.0, new double [] {1e-10,0.1,1.2}) , 1e-10);
		assertEquals(-1e-6, rr.g(1.0, new double [] {3e-6,0.1,1.2}),1e-10);
		
		// hrr
		assertEquals(false, hrr.g(1.0, new double [] {3e-6,0.1,1.2}) > 0.0); // r ok, x ok
		assertEquals(true, hrr.g(1.0, new double [] {1e-6,0.1,1.0}) > 0.0); // r false, x ok
		assertEquals(true, hrr.g(1.0, new double [] {4e-6,0.1,3.0}) > 0.0); // r ok, x false
		assertEquals(true, hrr.g(1.0, new double [] {1.2e-6,0.1,2.4}) > 0.0); // r false, x false

	}

} // end class
