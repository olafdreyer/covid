package helper;

import java.util.Locale;

import org.apache.commons.math3.ode.events.EventHandler;
import org.apache.commons.math3.ode.events.EventHandler.Action;

public class VelocityReached implements EventHandler {
	
	private double tH;
	private double [] yH;
	
	private double v_min;
	
	public VelocityReached() {
		this.v_min = 0.01;
	} // end constructor
	
	public VelocityReached(double vm) {
		this.v_min = vm;
	} // end constructor
	
	@Override
	public void init(double t0, double[] y0, double t) {
	} // nothing to do here

	@Override
	public double g(double t, double[] y) {
		// System.out.format(Locale.US, "%10.9f,%10.9f,%10.9f,%10.9f\n", t,y[0],y[1],y[2] );	
		return this.v_min - y[1];
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

	public void setVmin(double v_min) {
		this.v_min = v_min;
	}

	public VelocityReached withVmin(double v_min) {
		this.v_min = v_min;
		return this;
	}

} // end class
