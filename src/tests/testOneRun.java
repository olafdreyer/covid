package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import calc.OneRun;
import helper.Droplet;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;
import helper.WellsResultSet;

class testOneRun {

	@Test
	void test() {
		
		GlobalProperties gp = new GlobalProperties()
				.withHumidity(0.90)
				.withPressure(GlobalStaticProperties.p_gas_standard)
				.withTemperature(293.15);
		
		Droplet droplet = new Droplet()
				.withHeight(2.0) // 2m initial height, not needed here
				.withInitialRadius(20e-6)
				.withTemperature(gp.getTemperature())
				.withRMin(2.0e-6);
		
		OneRun or = new OneRun()
				.withGlobalProperties(gp)
				.withDroplet(droplet)
				.withR0(droplet.getInitialRadius());
		
		or.calc();

		WellsResultSet wrs = or.getResult();
		System.out.format(Locale.US, "%10.9f,%10.9f,%10.9f,%10.9f,%10.9f\n", wrs.getR0(), wrs.getTime(), wrs.getRadius(), wrs.getSpeed(), wrs.getPosition() );			
		
	} // end test

} // end class
