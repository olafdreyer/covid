package calc;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.univariate.BrentOptimizer;
import org.apache.commons.math3.optim.univariate.SearchInterval;
import org.apache.commons.math3.optim.univariate.UnivariateObjectiveFunction;

import helper.Droplet;
import helper.GlobalProperties;

public class CalcRM {

	private GlobalProperties gp;
	private Droplet droplet;
	private SearchInterval si;
	private double rM;
		
	public CalcRM() {
		 gp = new GlobalProperties();
		 droplet = new Droplet();
		 si = new SearchInterval(20e-6, 200e-6, 70e-6);
		 rM = 0.0;
	} // end empty constructor

	public CalcRM calc() {
		doCalc();
		return this;
	} // end calc
	
	public void doCalc() {
		
		// define the function to be minimized
		final UnivariateFunction fitnessFunction = new CalcTimes( gp, droplet );

		// use the Brent optimizer
		final UnivariateObjectiveFunction uof = new UnivariateObjectiveFunction(fitnessFunction);
		
		final MaxEval BRENT_MAX_EVAL = new MaxEval(1000);
	    final double RELATIVE_TOLERANCE = 1e-8;
	    final double ABSOLUTE_TOLERANCE = 1e-8;
	    final BrentOptimizer optimizer = new BrentOptimizer(RELATIVE_TOLERANCE, ABSOLUTE_TOLERANCE);
	    
	    this.rM = optimizer.optimize(uof, GoalType.MAXIMIZE, si, BRENT_MAX_EVAL).getPoint();
		
	} // end doCalc 
	
	public GlobalProperties getGlobalProperties() {
		return gp;
	}

	public void setGlobalProperties(GlobalProperties gp) {
		this.gp = gp;
	}

	public CalcRM withGlobalProperties(GlobalProperties gp) {
		this.gp = gp;
		return this;
	}

	public Droplet getDroplet() {
		return droplet;
	}

	public void setDroplet(Droplet droplet) {
		this.droplet = droplet;
	}
	
	public CalcRM withDroplet(Droplet droplet) {
		this.droplet = droplet;
		return this;
	}
	
	public double getRM() {
		return rM;
	}

	public void setInterval(SearchInterval si) {
		this.si = si;
	}
	
	public CalcRM withInterval(SearchInterval si) {
		this.si = si;
		return this;
	}
	
	
} // end class
