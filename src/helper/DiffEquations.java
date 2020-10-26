package helper;

import java.util.Locale;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;

import calc.FallConstants;

public class DiffEquations implements FirstOrderDifferentialEquations {

	private GlobalProperties gp;
	private Droplet droplet;
	private FallConstants fc;
	private Reynolds reynolds;
	private Schmidt schmidt;
	private double Sc;
	private double rootSc;

	// the constants for the equations
	private double B;
	private double g;
	private double st;
	
	public DiffEquations() {
		this.gp = new GlobalProperties();
		this.droplet = new Droplet();
		init();
	} // end DiffEquations
	
	/*
	 * There are three dimensions: r, v, x
	 * y[0] = r, y[1] = v, y[2] = x
	 * 
	 */
	
	@Override
	public int getDimension() {		
		return 3;
	} // end getDimension
	
	@Override
	public void computeDerivatives(double t, double[] y, double[] yDot)
			throws MaxCountExceededException, DimensionMismatchException {
		
		double r = y[0];
		double v = y[1];
				
		// System.out.format(Locale.US, "%10.9f,%10.9f,%10.9f\n", reynolds.nr(v, r),r,v );			
		
		yDot[0] = r<droplet.getRMin() ? 0 : -this.B/r*(1.0 + 0.276 * Math.sqrt(reynolds.nr(v, r)) * rootSc );
		yDot[1] = this.g - st*v/Math.pow(r, 2.0);
		yDot[2] = v;
		
	} // end computeDerivatives
	
	private void init() {
		this.fc = new FallConstants()
				.withDroplet(droplet)
				.withGlobalProperties(gp);
		// set the constants
		this.B = fc.getB();
		this.g = fc.getG();
		this.st = fc.getStokes()/(4.0/3.0*Math.PI*gp.density_water(droplet.getTemperature()));
		// Reynolds and Schmidt
		this.reynolds = new Reynolds()
				.withGlobalProperties(gp);
		this.schmidt = new Schmidt()
				.withGlobalProperties(gp);
		this.Sc = schmidt.nr();
		this.rootSc = Math.pow(Sc,1.0/3.0);
	} // end init;

	public void setGp(GlobalProperties gp) {
		this.gp = gp;
		init();
	}

	public DiffEquations withGp(GlobalProperties gp) {
		this.gp = gp;
		init();
		return this;
	}

	public void setDroplet(Droplet droplet) {
		this.droplet = droplet;
		init();
	}
	
	public DiffEquations withDroplet(Droplet droplet) {
		this.droplet = droplet;
		init();
		return this;
	}
	
	

} // end class
