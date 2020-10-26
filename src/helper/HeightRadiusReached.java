package helper;

import java.util.Locale;

import org.apache.commons.math3.ode.events.EventHandler;

public class HeightRadiusReached implements EventHandler {

	private Droplet droplet;
	private double r_min;
	
	private double tH;
	private double [] yH;
	
	public HeightRadiusReached() {
		this.droplet = new Droplet();
		this.r_min = 1.0e-6;
	} // end constructor
	
	public HeightRadiusReached(Droplet d, double rm) {
		this.droplet = d;
		this.r_min = rm;
	} // end constructor
	
	@Override
	public void init(double t0, double[] y0, double t) {
	} // nothing to do here

	@Override
	public double g(double t, double[] y) {
		double g = Math.max(y[2] - droplet.getHeight(), r_min - y[0]);
		// System.out.format(Locale.US,  "%5.4f\n", g);
		return g;
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

	public void setDroplet(Droplet droplet) {
		this.droplet = droplet;
	}

	public HeightRadiusReached withDroplet(Droplet droplet) {
		this.droplet = droplet;
		return this;
	}

	public double getTH() {
		return tH;
	}

	public double[] getyH() {
		return yH;
	}

	public void setRMin(double rm) {
		this.r_min = rm;
	}

	public HeightRadiusReached withRMin(double rm) {
		this.r_min = rm;
		return this;
	}

}
