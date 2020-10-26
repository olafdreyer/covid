package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.DynamicViscosity;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;
import helper.Reynolds;

class testReynolds {

	@Test
	void test() {
		
		Reynolds Re = new Reynolds();
		
		double eta = Re.getEta();
		GlobalProperties gp = new GlobalProperties();
		DynamicViscosity dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		
		assertEquals(dv.eta(), Re.getEta());
		
		gp.setTemperature(GlobalStaticProperties.T0 + 30.0);
		dv.setGlobalProperties(gp);
		Re.setGlobalProperties(gp);
		assertEquals(dv.eta(), Re.getEta());
		
		Re = new Reynolds()
				.withGlobalProperties(gp);
		assertEquals(dv.eta(), Re.getEta());		
		
		gp.setTemperature(GlobalStaticProperties.T0 - 130.0);
		gp.setPressure(80000.0);
		dv.setGlobalProperties(gp);
		Re.setGlobalProperties(gp);
		assertEquals(dv.eta(), Re.getEta());
		
		Re = new Reynolds()
				.withGlobalProperties(gp);
		assertEquals(dv.eta(), Re.getEta());
		
		// test the number
		gp = new GlobalProperties();
		Re = new Reynolds()
				.withGlobalProperties(gp);		
		assertEquals(1.32889E6, Re.nr(10.0, 1.0), 10);

		gp.setHumidity(0.76);
		dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		Re.setGlobalProperties(gp);
		assertEquals(1.196247957, gp.density_humid_air(), 1e-6);
		assertEquals(0.0000180045, dv.eta(),1e-7);
		assertEquals(10.0*2*gp.density_humid_air()/dv.eta(), Re.nr(10.0, 1.0),1e-6);
		
		assertEquals(1.32883449E6, Re.nr(10.0, 1.0), 10);
		
		gp.setTemperature(250.0);
		Re.setGlobalProperties(gp);
		assertEquals(438266.3073, Re.nr(25, 0.1), 0.1);
		
		// test c_drag
		gp = new GlobalProperties()
				.withHumidity(0.1);
		Re.setGlobalProperties(gp);
		assertEquals(1.32888e6, Re.nr(10, 1), 10);
		assertEquals(0.424, Re.C_drag(10.0, 1.0), 1e-4);
		assertEquals(13.2888, Re.nr(1e-4, 1), 1e-4);
		assertEquals(3.49478, Re.C_drag(1e-4, 1.0), 1e-4);
		assertEquals(332.22, Re.nr(1e-3, 2.5), 1e-2);
		assertEquals(0.649785, Re.C_drag(1e-3, 2.5), 1e-4);
		
	} // end test

} // end class
