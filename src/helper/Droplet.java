package helper;

public class Droplet {

	private double temperature;
	private double initial_radius;
	private double height;
	private double r_min;
	
	public Droplet() {
		this.temperature = GlobalStaticProperties.T0 + 15.0;
		this.initial_radius = 70e-6; // average size of air droplet in breath
		this.height = 2; // 2 m
		this.r_min = 2.0e-6;
	} // end constructor

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public Droplet withTemperature(double temperature) {
		this.temperature = temperature;
		return this;
	}

	public double getInitialRadius() {
		return initial_radius;
	}

	public void setInitialRadius(double initial_radius) {
		this.initial_radius = initial_radius;
	}

	public Droplet withInitialRadius(double initial_radius) {
		this.initial_radius = initial_radius;
		return this;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public Droplet withHeight(double height) {
		this.height = height;
		return this;
	}

	public void setRMin(double r_min) {
		this.r_min = r_min;
	}
	
	public double getRMin() {
		return this.r_min;
	}
	
	public Droplet withRMin(double r_min) {
		this.r_min = r_min;
		return this;
	}
	
	
	
} // end class
