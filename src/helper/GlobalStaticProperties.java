package helper;

public class GlobalStaticProperties {

	public static double p_gas_standard = 101325; // the gas pressure [Pa]
	public static double T0 = 273.15; // 0 deg. Celsius in Kelvin;
	
	public static double R_universal_gas_constant = 8.314; // [J/(K·mol)]	
	
	public static double molar_mass_dry_air = 0.0289654; // [Kg/mol]
	public static double molar_mass_water_vapor = 0.018016; // [Kg/mol]
	
	public static double specific_heat_water = 1860; // [J/kg/K]
	public static double specific_heat_air = 1005; // [J/kg/K]
	
	public static double gravitational_constant = 9.80665; // [m/s^2] 

	public static double density_water = 997.0; // [kg/m^3] 
	
	public static double Stefan_Boltzmann_constant = 5.670374419e-8; 

	// saturation pressure of water vapor
	public static double p_water( double T ) { // this will be used, good fit with the Wikipedia formula
		return Math.exp( 77.34 - (7235/T) - 8.2 * Math.log(T) + 0.005711 * T);
	} // end p_water

	// the Wikipedia formula, not used
	// https://en.wikipedia.org/wiki/Density_of_air#cite_note-wahiduddin_01-15
	public static double p_water2( double T ) { 
		return 610.78 * Math.exp( Math.log(10) * (7.5 * (T - 273.15) / (T - 273.15 + 237.3) ));
	} // end p_water

} // end class
