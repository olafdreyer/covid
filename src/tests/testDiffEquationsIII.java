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
import helper.RadiusReached;
import helper.VelocityReached;

class testDiffEquationsIII {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties()
				.withHumidity(0.1)
				.withPressure(GlobalStaticProperties.p_gas_standard)
				.withTemperature(GlobalStaticProperties.T0 + 18.0);
		
		Droplet droplet = new Droplet()
				.withHeight(2.0) // 2m initial height, not needed here
				.withInitialRadius(180e-6)
				.withTemperature(GlobalStaticProperties.T0 + 33.0)
				.withRMin(2.0e-6); // this terminates the evolution
		
		FirstOrderIntegrator dp853 = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
		RadiusReached rr = new RadiusReached(droplet.getRMin());
		/* VelocityReached vr = new VelocityReached()
		 		.withVmin(0.05); */
		dp853.addEventHandler(rr, 0.1, 0.1*droplet.getRMin(), 1000);
						
		// 0: r, 1: v, 2: x
		double [] y = new double [] {0.0, 0.0, 0.0 };

		for( int i=0; i<20; i++) {
			double r0 = (60.0 - 1.5*i)*1e-6;
			droplet.setInitialRadius(r0);
			DiffEquations de = new DiffEquations()
					.withDroplet(droplet)
					.withGp(gp);
			double [] y_init = new double [] {droplet.getInitialRadius(), 0.0, 0.0};
			dp853.integrate(de, 0.0, y_init, 40.0, y); // should stop
			System.out.format(Locale.US, "%10.9f,%10.9f\n", droplet.getInitialRadius(), rr.getTH() );	
		} // end for		
		
	} // end test

} // end class