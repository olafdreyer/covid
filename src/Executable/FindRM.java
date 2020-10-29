package Executable;

import java.util.Locale;

import org.apache.commons.math3.optim.univariate.SearchInterval;

import com.beust.jcommander.JCommander;

import calc.CalcRM;
import helper.Droplet;
import helper.GlobalProperties;

public class FindRM {

	static FindRMParams params = new FindRMParams();
	
	static CalcRM crm = new CalcRM();

	public static void main(String[] args) {
		
		// parse the command line
		JCommander.newBuilder()
		  .addObject(params)
		  .build()
		  .parse(args);
				
		double rStart = params.getR_start();

		GlobalProperties gp = new GlobalProperties()
				.withHumidity(params.getHumidity())
				.withPressure(params.getPressure())
				.withTemperature(params.getTemperature());			

		Droplet d = new Droplet()
				.withHeight(params.getHeight())
				.withInitialRadius(params.getR_guess());
		
		final SearchInterval searchInterval = new SearchInterval(params.getR_start(), params.getR_end(), params.getR_guess()); 
		
		crm.setGlobalProperties(gp);
		crm.setDroplet(d);
		crm.setInterval(searchInterval);
		
		crm.calc();
		
		System.out.format(Locale.US, "\n r_M = %10.9f\n", crm.getRM() );

	} // end main

	
	
} //end class
