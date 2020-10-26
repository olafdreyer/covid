package helper;

import java.util.Locale;

public class WellsResultSet {

	private double r_init;
	private double time;
	private double radius;
	private double speed;
	private double position;
	
	public WellsResultSet() {
	} // empty conbstructor
	
	public WellsResultSet(double r0, double t, double r, double v, double x) {
		this.r_init = r0;
		this.time = t;
		this.radius = r;
		this.speed = v;
		this.position = x;
	} // end conbstructor
	
	public String toString() {
		return String.format(Locale.US, "%10.9f,%10.9f,%10.9f,%10.9f,%10.9f\n", r_init, time, radius, speed, position );			
	} // end toString

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public WellsResultSet withTime(double time) {
		this.time = time;
		return this;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public WellsResultSet withRadius(double radius) {
		this.radius = radius;
		return this;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public WellsResultSet withSpeed(double speed) {
		this.speed = speed;
		return this;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public WellsResultSet withPosition(double position) {
		this.position = position;
		return this;
	}

	public double getR0() {
		return r_init;
	}

	public void setR0(double r0) {
		this.r_init = r0;
	}
	
	public WellsResultSet withR0(double r0) {
		this.r_init = r0;
		return this;
	}
	
	
	
	
} // end class
