package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
import org.junit.jupiter.api.Test;

import helper.DiffEquations;
import helper.Droplet;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;
import helper.HeightRadiusReached;
import helper.HeightReached;
import helper.RadiusReached;

class testDiffEquationsI {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties()
				.withHumidity(0.99)
				.withPressure(GlobalStaticProperties.p_gas_standard)
				.withTemperature(GlobalStaticProperties.T0 + 18.0);
		
		Droplet droplet = new Droplet()
				.withHeight(2.0) // 2m initial height, not needed here
				.withInitialRadius(100e-6)
				//.withTemperature(GlobalStaticProperties.T0 + 33.0)
				.withTemperature(gp.getTemperature())
				.withRMin(2.0e-6);
		
		// FirstOrderIntegrator integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
		FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(1e-5);
		RadiusReached rr = new RadiusReached()
				.withRmin(droplet.getRMin());
		HeightRadiusReached hrr = new HeightRadiusReached()
				.withRMin(droplet.getRMin())
				.withDroplet(droplet);
		HeightReached hr = new HeightReached()
				.withDroplet(droplet);
		integrator.addEventHandler(hrr, 0.1, 0.1*droplet.getRMin(), 1000);
		
		DiffEquations de = new DiffEquations()
				.withDroplet(droplet)
				.withGp(gp);
		
		// 0: r, 1: v, 2: x
		double [] y = new double [] {0.0, 0.0, 0.0 };
		double [] y_init = new double [] {droplet.getInitialRadius(), 0.0, 0.0};
		double T = 40.0;			
		
		// integrate
		integrator.integrate(de, 0.0, y_init, T, y);
		System.out.format(Locale.US, "%10.9f,%10.9f,%10.9f,%10.9f,%10.9f\n", droplet.getInitialRadius(), hrr.getTH(), y[0], y[1], y[2] );			
	} // end test

} // end class
