package calc;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer;
import org.apache.commons.math3.random.MersenneTwister;

import helper.Droplet;
import helper.GlobalProperties;

public class CalcRM {

	private GlobalProperties gp;
	private Droplet droplet;
	private double rM;
		
	public CalcRM() {
		 gp = new GlobalProperties();
		 droplet = new Droplet();
		 rM = 0.0;
	} // end empty constructor

	public CalcRM calc() {
		doCalc();
		return this;
	} // end calc
	
	public void doCalc() {
		
		// define the function to be minimized
		final MultivariateFunction fitnessFunction = new CalcTimes( gp, droplet );

		// define the optimizer
	    final CMAESOptimizer optimizer = new CMAESOptimizer(2000, 0, true, 10, 0, new MersenneTwister(), false, null);
	    	    	    
	    final double[] lower = new double [] {droplet.getRMin()};
	    final double[] upper = new double [] {droplet.getInitialRadius()*100.0}; // 
	    final double[] sigma = new double [] {droplet.getRMin()*10.0};
	    
	    final double[] result = optimizer.optimize(new MaxEval(10000),
	                                               new ObjectiveFunction(fitnessFunction),
	                                               GoalType.MAXIMIZE,
	                                               new CMAESOptimizer.PopulationSize(6), // 4 + 3*ln(n), could be lower for N=1
	                                               new CMAESOptimizer.Sigma(sigma),
	                                               new InitialGuess(new double [] {droplet.getInitialRadius()} ),
	                                               new SimpleBounds(lower, upper)).getPoint();
	    rM = result[0];
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
	
	
} // end class
