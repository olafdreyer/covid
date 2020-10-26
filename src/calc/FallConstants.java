package calc;

import java.util.Locale;

import helper.Droplet;
import helper.DynamicViscosity;
import helper.GlobalProperties;
import helper.GlobalStaticProperties;

public class FallConstants {

	GlobalProperties gp;
	Droplet droplet;

	private double mu = 1.9;
	
	private double B;
	private double C_corr;
	private double g;
	private double eta;
	private double stokes;
	private double v_limit_0;
	
	public FallConstants() {
		gp = new GlobalProperties();
		droplet = new Droplet();
		g = GlobalStaticProperties.gravitational_constant;
		init();
	} // end constructor
	
	private void init() {
		setModifiedGravitationalConstant();
		setCCorr();
		setB();
		setEta();
		setStokesFactor();
		setInitialVLimit();
	} // end init
	
	private void setCCorr() {
		double Tinf = gp.getTemperature();
		double Ta = droplet.getTemperature();
		
		// see corrFactorC.nb
		if (Math.abs(Tinf-Ta) < 1.0e-2) {
			// System.out.format(Locale.US, "%10.9f,%10.9f,%10.9f\n", Tinf, Ta, Tinf-Ta );
			this.C_corr = 1.0 + (this.mu-1.0)*(Ta-Tinf)/2.0/Tinf+(3.0 - 4.0*this.mu + this.mu*this.mu)*(Ta-Tinf)*(Ta-Tinf)/12.0/Tinf/Tinf;
		} else {
			this.C_corr = (Tinf - Ta) / Math.pow(Tinf, this.mu-1.0) * (2 - this.mu) / (Math.pow(Tinf, 2.0-this.mu) - Math.pow(Ta, 2.0-this.mu));			
		} // end if
	} // end SetCCorr
	
	private void setInitialVLimit() {
		this.v_limit_0 = 2.0 * Math.pow(droplet.getInitialRadius(), 2.0) * (gp.density_water(droplet.getTemperature())-gp.density_humid_air()) *GlobalStaticProperties.gravitational_constant / (9.0*eta);
	} // end setVLimit
	
	private void setStokesFactor() {
		this.stokes = 6*Math.PI*eta;
	} // end setStokesFactor
	
	private void setEta() {
		DynamicViscosity dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		this.eta = dv.eta();
	} // end setEta;
	
	private void setB() {
		// the fraction
		double Mv = GlobalStaticProperties.molar_mass_water_vapor;
		double D = gp.binary_diffusion_water();
		double B0 = - C_corr*Mv*D*gp.getPressure()/GlobalStaticProperties.R_universal_gas_constant/gp.getTemperature()/gp.density_water(droplet.getTemperature());
		// the logarithm
		double dpa = gp.getPressure() - GlobalStaticProperties.p_water(gp.getTemperature()); 
		double dpinf = gp.getPressure() - gp.getHumidity()*GlobalStaticProperties.p_water(gp.getTemperature()); 
		this.B = B0*Math.log(dpa/dpinf);
	} // end setB;
	
	private void setModifiedGravitationalConstant() {
		this.g = GlobalStaticProperties.gravitational_constant*(1-gp.density_humid_air()/gp.density_water(droplet.getTemperature()));	
	} // end setModifiedGravitationalConstant

	public double getB() {
		return B;
	}

	public double getG() {
		return g;
	}

	public double getStokes() {
		return stokes;
	}

	public void setGlobalProperties(GlobalProperties gp) {
		this.gp = gp;
		init();
	}

	public FallConstants withGlobalProperties(GlobalProperties gp) {
		this.gp = gp;
		init();
		return this;
	}

	public void setDroplet(Droplet droplet) {
		this.droplet = droplet;
		init();
	}

	public FallConstants withDroplet(Droplet droplet) {
		this.droplet = droplet;
		init();
		return this;
	}

	public double getV_limit_0() {
		return v_limit_0;
	}
	
	public double getCCorr() {
		return this.C_corr;
	}

	
	
	
	
} // end class
