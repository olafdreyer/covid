package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import helper.GlobalProperties;
import helper.GlobalStaticProperties;
import helper.ThermalConductivity;

class testThermalConductivity {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties();
		
		ThermalConductivity tc = new ThermalConductivity()
				.withGlobalProperties(gp);
		
		assertEquals(120.0, tc.S_air());
		assertEquals(559.8, tc.S_water());
		assertEquals(tc.S_water_air(), tc.S_air_water());
		assertEquals(189.9813829, tc.S_air_water(),1e-6);
		assertEquals(189.9813829, tc.S_water_air(),1e-6);
		
		// the Ks
		double [] Ts = {250,260,270,280,290,300};
		gp = new GlobalProperties();
		double [] kair = {0.02224, 0.022992, 0.023744, 0.024496, 0.025248, 0.026};
		double [] kwater = {0.012005, 0.012754, 0.013503, 0.014252, 0.015001, 0.01575};
		
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			tc.setGlobalProperties(gp);
			assertEquals(kair[i], tc.K_air(),1e-8);
			assertEquals(kwater[i], tc.K_water(),1e-8);
		} // end for

		double [] nwna = {0.302097, 0.310448, 0.31827, 0.325611, 0.332516, 0.339021};
		double [] nanw = {3.31019, 3.22115, 3.14199, 3.07114, 3.00738, 2.94967};
		// test the n_water / n_air
		assertEquals(tc.n_air_n_water(), 1/tc.n_water_n_air(), 1e-10);
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			tc.setGlobalProperties(gp);
			assertEquals(nwna[i], tc.n_water_n_air(),1e-5);
			assertEquals(nanw[i], tc.n_air_n_water(),1e-5);
		} // end for
		
		// As
		gp = new GlobalProperties();
		double [] Awa = {0.528008,0.536822,0.545108,0.552919,0.560299,0.567289};
		double [] Aaw = {1.22413,1.21109,1.19956,1.18931,1.18016,1.17196};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			tc.setGlobalProperties(gp);
			assertEquals(Awa[i], tc.A_water_air(),1e-5);
			assertEquals(Aaw[i], tc.A_air_water(),1e-5);
		} // end for
		
		// K
		gp = new GlobalProperties();
		tc.setGlobalProperties(gp);
		assertEquals(tc.K_air_water(), tc.K_air()); // zero humidity
		
		// data from https://en.wikipedia.org/wiki/List_of_thermal_conductivities		
		double [] T_wiki = {250, 300, 350};
		double [] Ks = {0.02226, 0.02614, 0.02970};
		for( int i=0; i<T_wiki.length; i++ ) {
			gp.setTemperature(T_wiki[i]);
			tc.setGlobalProperties(gp);
			assertEquals(Ks[i], tc.K_air_water(),2.0e-4);
		} // end for
		
		gp.setHumidity(0.75);
		double [] Kaw = {0.0222368,0.0229853,0.0237306,0.0244707,0.0252022,0.025919};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			tc.setGlobalProperties(gp);
			assertEquals(Kaw[i], tc.K_air_water(),1e-5);
		} // end for
		
		gp.setHumidity(0.95);
		gp.setPressure(GlobalStaticProperties.p_gas_standard + 25000.0);
		Kaw = new double [] {0.022236,0.0229835,0.0237269,0.0244636,0.0251889,0.0258939};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			tc.setGlobalProperties(gp);
			assertEquals(Kaw[i], tc.K_air_water(),1e-4);
		} // end for
		
		gp.setHumidity(0.25);
		gp.setPressure(GlobalStaticProperties.p_gas_standard - 35000.0);
		Kaw = new double [] {0.0222389,0.0229898,0.0237396,0.0244877,0.0252334,0.0259753};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			tc.setGlobalProperties(gp);
			assertEquals(Kaw[i], tc.K_air_water(),1e-4);
		} // end for
		
		gp = new GlobalProperties();
		gp.setHumidity(0.5);
		Kaw = new double [] {0.0222379,0.0229875,0.0237351,0.0244793,0.0252181,0.0259483};
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			tc.setGlobalProperties(gp);
			assertEquals(Kaw[i], tc.K_air_water(),1e-4);
		} // end for
		
	} // end test

} // end class
