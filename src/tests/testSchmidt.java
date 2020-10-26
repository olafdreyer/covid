package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import helper.DynamicViscosity;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;
import helper.Schmidt;

class testSchmidt {

	@Test
	void test() {
		
		Schmidt Sc = new Schmidt();
		
		double eta = Sc.getEta();
		GlobalProperties gp = new GlobalProperties();
		DynamicViscosity dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		
		assertEquals(dv.eta(), Sc.getEta());
		
		gp.setTemperature(GlobalStaticProperties.T0 + 30.0);
		dv.setGlobalProperties(gp);
		Sc.setGlobalProperties(gp);
		assertEquals(dv.eta(), Sc.getEta());
		
		Sc = new Schmidt()
				.withGlobalProperties(gp);
		assertEquals(dv.eta(), Sc.getEta());		
		
		gp.setTemperature(GlobalStaticProperties.T0 - 130.0);
		gp.setPressure(80000.0);
		dv.setGlobalProperties(gp);
		Sc.setGlobalProperties(gp);
		assertEquals(dv.eta(), Sc.getEta());
		
		Sc = new Schmidt()
				.withGlobalProperties(gp);
		assertEquals(dv.eta(), Sc.getEta());
		
		// test the number
		gp = new GlobalProperties();
		dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		 assertEquals(dv.eta()/gp.binary_diffusion_water()/gp.density_humid_air(), Sc.withGlobalProperties(gp).nr(),1e-9);
		 
		 gp.setHumidity(0.72);
		 gp.setTemperature(GlobalStaticProperties.T0-110.5);
		 gp.setPressure(GlobalStaticProperties.p_gas_standard-50000.0);
		 dv.setGlobalProperties(gp);
		 assertEquals(0.00001171978285, dv.eta(), 1e-10);		 
		 assertEquals(0.00001827320035, gp.binary_diffusion_water(), 1e-10);
		 assertEquals(1.09937131163, gp.density_humid_air(), 1e-10);
		 assertEquals(0.00001171978285/0.00001827320035/1.09937131163, Sc.withGlobalProperties(gp).nr(),1e-9);
		 
		 gp = new GlobalProperties();
		 assertEquals(0.7, Sc.withGlobalProperties(gp).nr(),1e-1);
		 
	} // end test
} // end class
