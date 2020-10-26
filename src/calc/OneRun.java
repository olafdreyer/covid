package calc;

import java.util.Locale;

import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;

import helper.DiffEquations;
import helper.Droplet;
import helper.GlobalProperties;
import helper.HeightRadiusReached;
import helper.WellsResultSet;

public class OneRun {
	
	private GlobalProperties gp;
	private Droplet droplet;
	private double r0;
	
	private WellsResultSet result;

	public OneRun() {
	} // end empty construtor
	
	public OneRun(GlobalProperties glob, Droplet d) {
		this.gp = glob;
		this.droplet = d;
		this.r0 = this.droplet.getInitialRadius();
	} // end construtor
	
	public void calc() {
		
		FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(1e-6);
		HeightRadiusReached hrr = new HeightRadiusReached()
				.withRMin(this.droplet.getRMin())
				.withDroplet(this.droplet);
		
		integrator.addEventHandler(hrr, 1e-3, 0.1*droplet.getRMin(), 1000);

		DiffEquations de = new DiffEquations()
				.withDroplet(droplet)
				.withGp(gp);
		
		// 0: r, 1: v, 2: x
		double [] y = new double [] {0.0, 0.0, 0.0 };
		double T = 40.0; // should stop before			
		
		double [] y_init = new double [] {droplet.getInitialRadius(), 0.0, 0.0};
		integrator.integrate(de, 0.0, y_init, T, y);
		
		result = new WellsResultSet()
				.withR0(r0)
				.withTime(hrr.getTH())
				.withRadius(y[0])
				.withSpeed(y[1])
				.withPosition(y[2]);

	} // end calc
	
	@Override
	public String toString() {
		return String.format(Locale.US, "%10.9f,%10.9f,%10.9f,%10.9f,%10.9f", result.getR0(), result.getTime(), result.getRadius(), result.getSpeed(), result.getPosition() );			
	} // end toString
	

	public GlobalProperties getGp() {
		return gp;
	}

	public void setGp(GlobalProperties gp) {
		this.gp = gp;
	}

	public OneRun withGlobalProperties(GlobalProperties gp) {
		this.gp = gp;
		return this;
	}

	public Droplet getDroplet() {
		return droplet;
	}

	public OneRun withDroplet(Droplet droplet) {
		this.droplet = droplet;
		this.r0 = droplet.getInitialRadius();
		return this;
	}

	public double getR0() {
		return r0;
	}

	public OneRun withR0(double r0) {
		this.r0 = r0;
		return this;
	}

	public WellsResultSet getResult() {
		return result;
	}	
	
} // end class
