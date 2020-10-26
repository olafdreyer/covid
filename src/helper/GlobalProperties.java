package helper;

/*
 * This class contains quantities that are global
 * like the temperature and pressure at infinity.
 */

public class GlobalProperties {

	private double pressure;
	private double temperature;
	private double humidity;
	
	public GlobalProperties() {
		pressure = GlobalStaticProperties.p_gas_standard;
		temperature = GlobalStaticProperties.T0 + 20.0;
		humidity = 0;
	} // end constructor
	
	// Density of humid air
	// https://en.wikipedia.org/wiki/Density_of_air#cite_note-wahiduddin_01-15
	public double density_humid_air() {
		double p_v = humidity*GlobalStaticProperties.p_water(temperature);
		double p_d = pressure - p_v;
		return (p_d*GlobalStaticProperties.molar_mass_dry_air + p_v*GlobalStaticProperties.molar_mass_water_vapor) / GlobalStaticProperties.R_universal_gas_constant / temperature;
	} // end rho_humid_air
	
	// binary diffusion coefficient
	public double binary_diffusion_water() {
		return  0.22e-4*Math.pow(temperature/GlobalStaticProperties.T0, 1.67)*GlobalStaticProperties.p_gas_standard/pressure;
	} // end binary_diffusion_water
	
	// the mole fractions
	public double X_water() {
		return humidity*GlobalStaticProperties.p_water(temperature) / pressure;
	} // end X_water

	public double X_air() {
		return (pressure - humidity*GlobalStaticProperties.p_water(temperature)) / pressure;
	} // end X_air
	
	public double specific_heat_water_air() {
		return X_water()*GlobalStaticProperties.specific_heat_water + X_air()*GlobalStaticProperties.specific_heat_air;
	} // end specific_heat_water_air()
	
	public double density_water() {
		double rho = 1050.0 - temperature*0.1763;
		return rho;
	} // end density_water
	
	public double density_water(double T) {
		double rho = 1050.0 - T*0.1763;
		return rho;
	} // end density_water
	
	public double specific_latent_heat() {
		return 3.15e6 - temperature*2.36e3;
	} // end latent_heat
	
	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public GlobalProperties withPressure(double pressure) {
		this.pressure = pressure;
		return this;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public GlobalProperties withTemperature(double temperature) {
		this.temperature = temperature;
		return this;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	
	public GlobalProperties withHumidity(double humidity) {
		this.humidity = humidity;
		return this;
	}
	
	
	
} // end class
