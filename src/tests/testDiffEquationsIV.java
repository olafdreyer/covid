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

class testDiffEquationsIV {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties()
				.withHumidity(0.9)
				.withPressure(GlobalStaticProperties.p_gas_standard)
				.withTemperature(GlobalStaticProperties.T0 + 18.0);
		
		Droplet droplet = new Droplet()
				.withHeight(1.7) // 2m initial height, not needed here
				.withInitialRadius(70e-6)
				.withTemperature(GlobalStaticProperties.T0 + 33.0)
				.withRMin(2.0e-6);
				
		FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(1e-6);
		// RadiusReached rr = new RadiusReached()
		HeightRadiusReached hrr = new HeightRadiusReached()
				.withRMin(droplet.getRMin())
				.withDroplet(droplet);
		
		integrator.addEventHandler(hrr, 1e-3, 0.1*droplet.getRMin(), 1000);

		DiffEquations de = new DiffEquations()
				.withDroplet(droplet)
				.withGp(gp);
		
		// 0: r, 1: v, 2: x
		double [] y = new double [] {0.0, 0.0, 0.0 };
		double T = 40.0;			
		
		// integrate
		for( int i=0; i<10; i++) {
			double r0 = (20.0 + 12.0*i)*1e-6;
			droplet.setInitialRadius(r0);
			double [] y_init = new double [] {droplet.getInitialRadius(), 0.0, 0.0};
			integrator.integrate(de, 0.0, y_init, T, y);
			System.out.format(Locale.US, "%10.9f,%10.9f,%10.9f,%10.9f,%10.9f\n", r0, hrr.getTH(), y[0], y[1], y[2] );			
		}
	} // end test

} // end class
