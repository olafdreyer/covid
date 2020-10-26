package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.GlobalStaticProperties;

class testGlobalStaticProperties {

	@Test
	void test() {
		
		assertEquals(273.15, GlobalStaticProperties.T0, 1e-5);
		assertEquals(101325, GlobalStaticProperties.p_gas_standard, 1e-5);
		
		assertEquals(18.02E-3, GlobalStaticProperties.molar_mass_water_vapor, 1e-2);
		assertEquals(28.96E-3, GlobalStaticProperties.molar_mass_dry_air, 1e-2);
		
		assertEquals(1860,  GlobalStaticProperties.specific_heat_water, 1e-6);
		assertEquals(1005, GlobalStaticProperties.specific_heat_air, 1e-6);
		
		assertEquals(9.80665, GlobalStaticProperties.gravitational_constant, 1e-6);
		assertEquals(997.0, GlobalStaticProperties.density_water, 1e-6);
		

		double [] Ts = new double [] {100.0, 150.0, 200.0, 250.0, 300.0};
		double [] ps = new double [] {1.03551E-14, 0.0000147555, 0.320138, 94.8001, 3517.89};		
		for( int i = 0; i<Ts.length; i++ ) {
			assertEquals( Math.abs((ps[i] - GlobalStaticProperties.p_water(Ts[i]))/ps[i]), 1e-5, 1e-5 );			
		} // end for
		
	} // end test

} // end class
