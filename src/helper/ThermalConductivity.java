package helper;

public class ThermalConductivity {

	private GlobalProperties gp;
		
	public ThermalConductivity() {
		this.gp = new GlobalProperties();
	} // end constructor
	
	public void setGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
	} // end setGlobalProperties

	public ThermalConductivity withGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		return this;
	} // end withGlobalPrpperties

	public double K_air_water() {
		double sum1 = gp.X_water() == 0.0 ? 0.0 : K_water() / ( 1.0 + A_water_air()*gp.X_air()/gp.X_water() );
		double sum2 = gp.X_air() == 0.0 ? 0.0 : K_air() / ( 1.0 + A_air_water()*gp.X_water()/gp.X_air() );
		return sum1 + sum2;
	} // end K_air_water
	
	public double A_water_air() {
		double sroot = Math.sqrt(n_water_n_air()*Math.pow(GlobalStaticProperties.molar_mass_dry_air/GlobalStaticProperties.molar_mass_water_vapor,0.75)*(1.0 + S_water()/gp.getTemperature())/(1+0 + S_air()/gp.getTemperature()));
		return 0.25*Math.pow(1.0 + sroot,2.0)*(1.0 + S_water_air()/gp.getTemperature())/(1.0 + S_water()/gp.getTemperature());
	} // end A_water_air
	
	public double A_air_water() {
		double sroot = Math.sqrt(n_air_n_water()*Math.pow(GlobalStaticProperties.molar_mass_water_vapor/GlobalStaticProperties.molar_mass_dry_air,0.75)*(1.0 + S_air()/gp.getTemperature())/(1.0 + S_water()/gp.getTemperature()));
		return 0.25*Math.pow(1.0 + sroot,2.0)*(1.0 + S_air_water()/gp.getTemperature())/(1.0 + S_air()/gp.getTemperature());
	} // end A_air_water
	
	public double n_water_n_air() {
		double numerator = K_water()*GlobalStaticProperties.molar_mass_water_vapor *(GlobalStaticProperties.specific_heat_air*GlobalStaticProperties.molar_mass_dry_air + 5.0/4.0 * GlobalStaticProperties.R_universal_gas_constant);
		double denominator = K_air()*GlobalStaticProperties.molar_mass_dry_air*(GlobalStaticProperties.specific_heat_water*GlobalStaticProperties.molar_mass_water_vapor + 5.0/4.0*GlobalStaticProperties.R_universal_gas_constant);
		return numerator / denominator;
	} // end n_water_n_air
	
	public double n_air_n_water() {
		double numerator = K_air()*GlobalStaticProperties.molar_mass_dry_air*(GlobalStaticProperties.specific_heat_water*GlobalStaticProperties.molar_mass_water_vapor + 5.0/4.0*GlobalStaticProperties.R_universal_gas_constant);
		double denominator = K_water()*GlobalStaticProperties.molar_mass_water_vapor *(GlobalStaticProperties.specific_heat_air*GlobalStaticProperties.molar_mass_dry_air + 5.0/4.0 * GlobalStaticProperties.R_universal_gas_constant);
		return numerator / denominator;
	} // end n_air_n_water
	
	public double K_air() {
		double K0 = 3.44e-3;
		double K1 = 7.52e-5;
		return K0 + gp.getTemperature()*K1;
	} // end K_air
	
	public double K_water() {
		double K0 = -6.72e-3;
		double K1 = 7.49e-5;
		return K0 + gp.getTemperature()*K1;
	} // end K_air
	

	public double S_air() {
		double T_b_air = 80;
		return 1.5*T_b_air;
	} // end S_air
	
	public double S_water() {
		double T_b_water = 373.2;
		return 1.5*T_b_water;
	} // end S_water
	
	// this is formula (C8) in Kukkonen
	// Choosing the strongly polar case because the water
	public double S_water_air() {
		return 0.733*Math.sqrt(S_air()*S_water());
	} // end S_water_air
	
	public double S_air_water() {
		return 0.733*Math.sqrt(S_air()*S_water());
	} // end S_water_air
	
} // end class
