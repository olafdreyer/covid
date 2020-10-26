package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.GlobalProperties;
import helper.GlobalStaticProperties;

class testGlobalProperties {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties();
		
		assertEquals(GlobalStaticProperties.p_gas_standard, gp.getPressure(), 1e-6);
		assertEquals(GlobalStaticProperties.T0 + 20.0, gp.getTemperature(), 1e-6);
		assertEquals(0.0, gp.getHumidity(), 1e-6);
		
		double [] testNrs = { 234.06, -34.2, 573.34, 1.34e10 };
		
		for( int i=0; i<testNrs.length; i++) {
			double nr=testNrs[i];
			gp.setPressure(nr);
			gp.setTemperature(nr);
			gp.setHumidity(nr);
			
			assertEquals(nr, gp.getPressure(), 1e-6);
			assertEquals(nr, gp.getTemperature(), 1e-6);
			assertEquals(nr, gp.getHumidity(), 1e-6);
			
		} // end for
		
		testNrs = new double [] { 345.98, -12586.0, 34.66, -234.98, 1.56e12 };
		
		for( int i=0; i<testNrs.length; i++) {
			double nr=testNrs[i];
			gp = new GlobalProperties()
					.withPressure(nr)
					.withTemperature(nr)
					.withHumidity(nr);
			
			assertEquals(nr, gp.getPressure(), 1e-6);
			assertEquals(nr, gp.getTemperature(), 1e-6);
			assertEquals(nr, gp.getHumidity(), 1e-6);
			
		} // end for
		
		// density of air
		gp = new GlobalProperties();
		assertEquals(1.204193349, gp.density_humid_air(),1e-8);
		gp.setHumidity(.28);
		assertEquals(1.201266099, gp.density_humid_air(),1e-8);
		gp.setTemperature(200.0);
		assertEquals(1.76504581, gp.density_humid_air(),1e-8);
		gp.setHumidity(.56);
		assertEquals(1.76504522, gp.density_humid_air(),1e-8);
		
		// binary diffusion coefficient
		gp = new GlobalProperties()
				.withTemperature(GlobalStaticProperties.T0);
		assertEquals(.22e-4, gp.binary_diffusion_water(),1.0e-10);
		
		gp.setPressure(2.0*GlobalStaticProperties.p_gas_standard);
		assertEquals(0.5*.22e-4, gp.binary_diffusion_water(),1.0e-10);
		
		gp.setPressure(0.1*GlobalStaticProperties.p_gas_standard);
		assertEquals(10.0*.22e-4, gp.binary_diffusion_water(),1.0e-10);
		
		gp = new GlobalProperties()
				.withTemperature(GlobalStaticProperties.T0+25.5)
				.withPressure(GlobalStaticProperties.p_gas_standard+25000);
		assertEquals(0.0000204824, gp.binary_diffusion_water(),1.0e-10);
		
		gp = new GlobalProperties()
				.withTemperature(GlobalStaticProperties.T0-100.5)
				.withPressure(GlobalStaticProperties.p_gas_standard-50000);
		assertEquals(0.0000201878, gp.binary_diffusion_water(),1.0e-10);
		
		// test the mole fractions
		gp = new GlobalProperties();
		assertEquals(0.0, gp.X_water());
		assertEquals(1.0, gp.X_air());
		
		gp.setHumidity(0.75);
		gp.setTemperature(200);
		assertEquals(2.369639013E-6, gp.X_water(), 1e-10);
		assertEquals(0.9999976304, gp.X_air(), 1e-10);
		
		gp.setHumidity(0.95);
		gp.setTemperature(320);
		assertEquals(0.09832579145, gp.X_water(), 1e-10);
		assertEquals(0.9016742086, gp.X_air(), 1e-10);
		
		// test specific heat of mixture
		gp = new GlobalProperties();
		assertEquals(GlobalStaticProperties.specific_heat_air, gp.specific_heat_water_air());
		
		gp.setHumidity(0.85);
		gp.setTemperature(230);
		assertEquals(1005.097029, gp.specific_heat_water_air(),1e-6);
		
		gp.setHumidity(0.15);
		gp.setTemperature(320);
		assertEquals(1018.273982, gp.specific_heat_water_air(),1e-6);
		
		gp.setHumidity(0.456);
		gp.setTemperature(298.0);
		gp.setPressure(GlobalStaticProperties.p_gas_standard+40000.0);
		assertEquals(1013.621072, gp.specific_heat_water_air(),1e-6);
		
		// test density of water
		// values from appendix C in Kukkonen et al. 
		gp = new GlobalProperties();
		double [] Ts = {280.0, 290.0, 300.0, 310.0, 320.0 };
		for( int i=0; i<Ts.length; i++ ) {
			gp.setTemperature(Ts[i]);
			assertEquals(gp.density_water(), gp.density_water(Ts[i]), 1e-6);
			assertEquals(1050.0, gp.density_water(0.0),1e-6);
		} // end for
		// test slope
		assertEquals(-0.1763, (gp.density_water(200.0) - gp.density_water(100.0))/100.0, 1e-6 );
		
	} // end test

} // end class
