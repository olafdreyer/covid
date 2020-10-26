package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.DynamicViscosity;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;
import helper.Prandl;
import helper.ThermalConductivity;

class testPrandl {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties();
		
		Prandl pr = new Prandl()
				.withGlobalProperties(gp);
		
		// basic tests
		assertEquals(pr.getC_gas(), GlobalStaticProperties.specific_heat_air);
		double eta = new DynamicViscosity().eta();
		assertEquals(pr.getEta(), new DynamicViscosity().eta());
		double K = new ThermalConductivity().K_air_water();
		assertEquals(pr.getK(), K);
		assertEquals(GlobalStaticProperties.specific_heat_air*eta/K, pr.nr(), 1e-9);
		assertEquals(gp.specific_heat_water_air()*eta/K, pr.nr(), 1e-9);
		
		double [] Ts = new double [] {250, 260, 270, 280, 290, 300};
		double [] dv = new double [] {0.0000161064, 0.000016577, 0.0000170384, 0.0000174869, 0.0000179157, 0.000018314};
		double [] tc = new double [] {0.0222379,0.0229875,0.0237351,0.0244793,0.0252181,0.0259483};

		gp.setHumidity(0.5);
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			pr.setGlobalProperties(gp);
			assertEquals(gp.specific_heat_water_air()*dv[i]/tc[i], pr.nr(), 1e-5);
		} // end for
		
		dv = new double [] {0.0000161043, 0.0000165719, 0.0000170268, 0.0000174619, 0.0000178651, 0.0000182165};
		tc = new double [] {0.0222362, 0.0229839, 0.0237278, 0.0244654, 0.0251923, 0.0259003};
		gp.setHumidity(0.9);
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			pr.setGlobalProperties(gp);
			assertEquals(gp.specific_heat_water_air()*dv[i]/tc[i], pr.nr(), 1e-5);
		} // end for 		
		
		// from https://en.wikipedia.org/wiki/Prandtl_number
		gp = new GlobalProperties();
		pr.setGlobalProperties(gp);
		assertEquals(0.71, pr.nr(), 1e-2);
		
	} // end test

} // end class
