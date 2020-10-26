package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
import org.junit.jupiter.api.Test;

import helper.DiffEquations;
import helper.Droplet;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;
import helper.HeightReached;

class testDiffEquationsII {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties()
				.withHumidity(0.9)
				.withPressure(GlobalStaticProperties.p_gas_standard)
				.withTemperature(GlobalStaticProperties.T0 + 18.0);
		
		Droplet droplet = new Droplet()
				.withHeight(2.0) // 2m initial height, not needed here
				.withInitialRadius(160e-6)
				.withTemperature(GlobalStaticProperties.T0 + 33.0)
				.withRMin(1e-6);
		
		FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 1.0, 1.0e-10, 1.0e-10);
		HeightReached hr = new HeightReached()
				.withDroplet(droplet); // this sets the height
		dp853.addEventHandler(hr, 0.1, 1e-2, 1000);
		
				
		// 0: r, 1: v, 2: x
		double [] y = new double [] {0.0, 0.0, 0.0 };

		for( int i=0; i<25; i++) {
			double r0 = (120.0 - 2.0*i)*1e-6;
			droplet.setInitialRadius(r0);
			DiffEquations de = new DiffEquations()
					.withDroplet(droplet)
					.withGp(gp);
			double [] y_init = new double [] {droplet.getInitialRadius(), 0.0, 0.0};
			dp853.integrate(de, 0.0, y_init, 40.0, y); // should stop
			System.out.format(Locale.US, "%10.9f,%10.9f,%10.9f\n", droplet.getInitialRadius(), hr.getTH(), hr.getyH()[0] );	
		} // end for		
		
	} // end test

} // end class