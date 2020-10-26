package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import calc.FallConstants;
import helper.Droplet;
import helper.DynamicViscosity;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;

class testFallConstants {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties(); // atm. pressure, T0+20, phi=0
		Droplet droplet = new Droplet(); // r0 = 70e-6 m
		
		FallConstants fc = new FallConstants()
				.withGlobalProperties(gp)
				.withDroplet(droplet);
		
		// test G
		double rho_g = GlobalStaticProperties.molar_mass_dry_air * gp.getPressure() / GlobalStaticProperties.R_universal_gas_constant / gp.getTemperature();
		double rho_w = gp.density_water(droplet.getTemperature());
		double g = GlobalStaticProperties.gravitational_constant*( 1.0 - rho_g/rho_w);		
		
		assertEquals(g, fc.getG(), 1e-6);

		// change humidity & temperature
		gp.setHumidity(0.75);
		gp.setTemperature(310.0);
		fc.setGlobalProperties(gp);
		
		rho_g = gp.density_humid_air();
		rho_w = gp.density_water(droplet.getTemperature());
		g = GlobalStaticProperties.gravitational_constant*( 1.0 - rho_g/rho_w);		
		
		assertEquals(g, fc.getG(), 1e-6);
		
		// change humidity & temperature & droplet temperature
		gp.setHumidity(0.23);
		gp.setTemperature(300.0);
		droplet.setTemperature(200.0);
		
		fc.setGlobalProperties(gp);
		fc.setDroplet(droplet);
		
		rho_g = gp.density_humid_air();
		rho_w = gp.density_water(droplet.getTemperature());
		g = GlobalStaticProperties.gravitational_constant*( 1.0 - rho_g/rho_w);		
		
		assertEquals(g, fc.getG(), 1e-12);
		
		// test stokes and eta 
		gp = new GlobalProperties();
		DynamicViscosity dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		fc.setGlobalProperties(gp);
		double e = dv.eta();
		
		assertEquals(6.0*Math.PI*e, fc.getStokes(), 1e-8);

		gp = new GlobalProperties()
				.withTemperature(250.0)
				.withHumidity(0.14)
				.withPressure(GlobalStaticProperties.p_gas_standard+50000.0);
		fc.setGlobalProperties(gp);
		dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		e = dv.eta();
		
		assertEquals(6.0*Math.PI*e, fc.getStokes(), 1e-8);
		
		// test v_limit
		gp = new GlobalProperties();
		fc.setGlobalProperties(gp);
		droplet = new Droplet();
		fc.setDroplet(droplet);
		
		double gbar = fc.getG();
		double r0 = droplet.getInitialRadius();
		double st = fc.getStokes();
		double m0 = 4.0/3.0*Math.PI*Math.pow(r0, 3.0)*gp.density_water(droplet.getTemperature());
		
		assertEquals(m0*gbar/st/r0, fc.getV_limit_0(), 1e-8);		
		
		// test CCorr
		gp = new GlobalProperties();
		fc.setGlobalProperties(gp);
		droplet = new Droplet()
				.withTemperature(gp.getTemperature());
		fc.setDroplet(droplet);
		assertEquals(1.0, fc.getCCorr(), 1e-6);

		droplet = new Droplet()
				.withTemperature(gp.getTemperature()+0.1);
		fc.setDroplet(droplet);
		assertEquals(1.000153495, fc.getCCorr(), 1e-6);
		
		droplet = new Droplet()
				.withTemperature(gp.getTemperature()+0.01001);
		fc.setDroplet(droplet);
		assertEquals(1.000015366, fc.getCCorr(), 1e-6);
		
		droplet = new Droplet()
				.withTemperature(gp.getTemperature()+0.009);
		fc.setDroplet(droplet);
		assertEquals(1.000013815, fc.getCCorr(), 1e-6);
		
		fc = new FallConstants();
		assertEquals(0.9923005413, fc.getCCorr(), 1e-6);		
		
		
		// test B
		gp = new GlobalProperties();
		fc.setGlobalProperties(gp);
		droplet = new Droplet();
		fc.setDroplet(droplet);
		
		double M = GlobalStaticProperties.molar_mass_water_vapor;
		double D = gp.binary_diffusion_water();
		double C = 0.9923005413;
		double p_inf = gp.getPressure();
		rho_w = gp.density_water(droplet.getTemperature());
		double R_gas = GlobalStaticProperties.R_universal_gas_constant;
		double T_inf = gp.getTemperature();
		
		double p_va = GlobalStaticProperties.p_water(droplet.getTemperature());
		double p_vinf = gp.getHumidity()*GlobalStaticProperties.p_water(gp.getTemperature());
		double logCorr = Math.log((1 - p_va/p_inf)/(1 - p_vinf/p_inf));
		
		double Btest = - C*M*D*p_inf/(rho_w*R_gas*T_inf)*logCorr;
		
		assertEquals(Btest, fc.getB(),1e-16);
		assertEquals(3.1100805605e-10, fc.getB(),1e-18);
		
	} // end test

} // end class
