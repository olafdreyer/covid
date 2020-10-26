package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.DynamicViscosity;
import helper.GlobalProperties;

class testDynamicViscosity {

	@Test
	void test() {
		
		// test for eta_water
		double [] Ts = new double [] {250, 260, 270, 280, 290, 300};
		double [] res = new double [] {0.0000102365, 0.0000104393, 0.0000106381, 0.0000108333, 0.0000110251, 0.0000112136};
		
		DynamicViscosity dv = new DynamicViscosity();
		GlobalProperties gp = new GlobalProperties();
		
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			dv.setGlobalProperties(gp);
			double eta_w = dv.eta_water();
			assertEquals(res[i],eta_w,1e-6);
		} // end for
		
		// test for eta_air
		res = new double [] {0.0000161089, 0.0000165833, 0.000017053, 0.0000175182, 0.000017979, 0.0000184358};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			dv.setGlobalProperties(gp);
			double eta_a = dv.eta_air();
			assertEquals(res[i],eta_a,1e-6);
		} // end for
		
		// test for phi_water_air
		res = new double [] {0.999668, 0.995232, 0.990993, 0.986934, 0.983043, 0.979306};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			dv.setGlobalProperties(gp);
			double phiwa = dv.phi_water_air();
			assertEquals(res[i],phiwa,1e-6);
		} // end for
		
		// test for phi_air_water
		res = new double [] {0.978472, 0.983343, 0.988064, 0.992644, 0.997092, 1.001416413	};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			dv.setGlobalProperties(gp);
			double phiaw = dv.phi_air_water();
			assertEquals(res[i],phiaw,1e-6);
		} // end for
		
		// eta
		// humidity = 0.0
		gp.setHumidity(0.0);
		res = new double [] {0.0000161089, 0.0000165833, 0.000017053, 0.0000175182, 0.000017979, 0.0000184358};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			dv.setGlobalProperties(gp);
			double eta = dv.eta();
			assertEquals(res[i],eta,1e-8);
		} // end for
		
		// test against
		// https://www.engineeringtoolbox.com/air-absolute-kinematic-viscosity-d_601.html?vA=280&units=K#
		gp.setHumidity(0.0);
		gp.setTemperature(290.0);
		assertEquals(17.97E-6,dv.eta(),1e-8);

		// humidity = 0.5
		gp.setHumidity(0.5);
		res = new double [] {0.0000161064, 0.000016577, 0.0000170384, 0.0000174869, 0.0000179157, 0.000018314};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			dv.setGlobalProperties(gp);
			double eta = dv.eta();
			assertEquals(res[i],eta,1e-8);
		} // end for

		// humidity = 0.9
		gp.setHumidity(0.9);
		res = new double [] {0.0000161043, 0.0000165719, 0.0000170268, 0.0000174619, 0.0000178651, 0.0000182165};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			dv.setGlobalProperties(gp);
			double eta = dv.eta();
			assertEquals(res[i],eta,1e-8);
		} // end for
	}

} // end class
