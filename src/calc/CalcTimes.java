package calc;

import java.util.Locale;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;

import helper.Droplet;
import helper.GlobalProperties;

public class CalcTimes implements UnivariateFunction {

	private OneRun or;

	public CalcTimes( GlobalProperties glob, Droplet d ) {
		this.or = new OneRun(glob, d);
	} // end CalcTimes
	
	@Override
	public double value(double point) {
		or.getDroplet().setInitialRadius(point);
		or.calc();
		System.out.format(Locale.US, "%10.9f\n", point );
		return or.getResult().getTime(); // remember: need to maximize
	} // end value

} // end class
