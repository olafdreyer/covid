package helper;

import org.apache.commons.math3.ode.events.EventHandler;

public class HeightReached implements EventHandler {

	private Droplet droplet;
	
	private double tH;
	private double [] yH;
	
	public HeightReached() {
		this.droplet = new Droplet();
	} // end constructor
	
	public HeightReached(Droplet d) {
		this.droplet = d;
	} // end constructor
	
	@Override
	public void init(double t0, double[] y0, double t) {
	} // nothing to do here

	@Override
	public double g(double t, double[] y) {
		return y[2] - droplet.getHeight();
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

	public HeightReached withDroplet(Droplet droplet) {
		this.droplet = droplet;
		return this;
	}

	public double getTH() {
		return tH;
	}

	public double[] getyH() {
		return yH;
	}

}
