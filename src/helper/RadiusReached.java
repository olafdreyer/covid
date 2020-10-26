package helper;

import java.util.Locale;

import org.apache.commons.math3.ode.events.EventHandler;
import org.apache.commons.math3.ode.events.EventHandler.Action;

public class RadiusReached implements EventHandler {
	
	private double tH;
	private double [] yH;
	
	private double r_min;
	
	public RadiusReached() {
		this.r_min = 5e-6;
	} // end constructor
	
	public RadiusReached(double rm) {
		this.r_min = rm;
	} // end constructor
	
	@Override
	public void init(double t0, double[] y0, double t) {
	} // nothing to do here

	@Override
	public double g(double t, double[] y) {
		// System.out.format(Locale.US, "%10.9f,%10.9f\n", y[0], t );	
		return this.r_min - y[0] ;
	}

	@Override
	public Action eventOccurred(double t, double[] y, boolean increasing) {
		this.tH = t;
		this.yH = y;
		return EventHandler.Action.STOP;
	}

	@Override
	public void resetState(double t, double[] y) {
	}

	public double getTH() {
		return tH;
	}

	public double[] getyH() {
		return yH;
	}

	public void setR_min(double r_min) {
		this.r_min = r_min;
	}

	public RadiusReached withRmin(double r_min) {
		this.r_min = r_min;
		return this;
	}

} // end class
