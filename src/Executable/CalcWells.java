package Executable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.beust.jcommander.JCommander;

import calc.OneRun;
import helper.Droplet;
import helper.GlobalProperties;

public class CalcWells {

	static WellsParams params = new WellsParams();
	
	static List<OneRun> runLst;
	
	public static void main(String[] args) {
		
		// parse the command line
		JCommander.newBuilder()
		  .addObject(params)
		  .build()
		  .parse(args);
				
		double rStart = params.getR_start();
		double rEnd = params.getR_end();
		double deltaR = params.getDeltaR();
		
		int N_calc = (int)Math.ceil((rEnd - rStart) / deltaR)+1;
		
		// make a list of OneRuns
		runLst = new ArrayList<>();
		for( int i=0; i<N_calc; i++ ) {
			GlobalProperties gp = new GlobalProperties()
					.withHumidity(params.getHumidity())
					.withPressure(params.getPressure())
					.withTemperature(params.getTemperature());			
			double radius = rStart + deltaR*i;
			Droplet d = new Droplet()
					.withHeight(params.getHeight())
					.withInitialRadius(radius);
			OneRun or = new OneRun()
					.withDroplet(d)
					.withGlobalProperties(gp)
					.withR0(radius);
			runLst.add(or);
		} // end for
		
		// System.out.format(Locale.US, "Created %d calculation objects.\n", N_calc);
		
		// do the calculations
		runLst.stream()
		.parallel()
		.forEach(o->o.calc());
		
		// print the results
		runLst.stream()
		.forEach(o-> System.out.println(o));
		
	} // end main

} // end class
