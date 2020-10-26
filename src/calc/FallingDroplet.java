package calc;

import helper.Droplet;
import helper.GlobalProperties;

public class FallingDroplet {

	private Droplet droplet;
	private GlobalProperties gp;
	
	public FallingDroplet() {
		this.droplet = new Droplet();
		this.gp = new GlobalProperties();
	} // end construtor

	public Droplet getDroplet() {
		return droplet;
	}

	public void setDroplet(Droplet droplet) {
		this.droplet = droplet;
	}

	public FallingDroplet withDroplet(Droplet droplet) {
		this.droplet = droplet;
		return this;
	}

	public GlobalProperties getGp() {
		return gp;
	}

	public void setGp(GlobalProperties gp) {
		this.gp = gp;
	}
	
	public FallingDroplet withGp(GlobalProperties gp) {
		this.gp = gp;
		return this;
	}
	
} // end class
