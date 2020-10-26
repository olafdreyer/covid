package helper;

/*
 * This class provides the means to calculate
 * the dynamic viscosity. The units are 
 * 
 * [T] = K
 * 
 * [eta] = Kg/(ms)
 * 
 * The interdependence of evaporation and settling for airborne freely falling droplets
 * Jaakko Kukkonen, Vesala Markku, Markku Kulmala
 * Journal of Aerosol Science 20(7):749-763
 * 
 * Appendix C
 * 
 */

public class DynamicViscosity {
	
	private GlobalProperties gp;
	
	public DynamicViscosity() {
		this.gp = new GlobalProperties();
	} // end constructor
	
	public void setGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
	} // end setGlobalProperties

	public DynamicViscosity withGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		return this;
	} // end withGlobalPrpperties
	
	public double eta_water() {
		double T = gp.getTemperature();
		return 1.07E-5 * Math.pow( T / GlobalStaticProperties.T0, 0.5 );
	} // end eta_water
	
	public double eta_air() {
		double T = gp.getTemperature();
		return 1.72E-5 * Math.pow( T / GlobalStaticProperties.T0, 0.74 );
	} // end eta_air
	
	public double phi_water_air() {
		double ew = eta_water();
		double ea = eta_air();
		double mw = GlobalStaticProperties.molar_mass_water_vapor;
		double ma = GlobalStaticProperties.molar_mass_dry_air;
		
		double numerator = Math.pow(1.0 + Math.sqrt(ew/ea)*Math.pow(ma/mw, 0.25),2.0);
		double denominator = Math.sqrt(8.0*(1 + mw/ma));
		
		return numerator / denominator;
	} // end phi_water_air
	
	public double phi_air_water() {
		double ea = eta_air();
		double ew = eta_water();
		double ma = GlobalStaticProperties.molar_mass_dry_air;
		double mw = GlobalStaticProperties.molar_mass_water_vapor;
		
		double numerator = Math.pow(1.0 + Math.sqrt(ea/ew)*Math.pow(mw/ma, 0.25),2.0);
		double denominator = Math.sqrt(8.0*(1 + ma/mw));
		
		return numerator / denominator;
	} // end phi_air_water
	
	public double eta() {
		// the mole fractions
		double xw = gp.X_water();
		double xa = gp.X_air();
		
		double s1 = xw == 0.0 ? 0.0 : eta_water()/(1.0 + phi_water_air()*xa/xw);
		double s2 = xa == 0.0 ? 0.0 : eta_air()/(1.0 + phi_air_water()*xw/xa);
				
		return s1 + s2;
	} // end eta
	
} // end class
