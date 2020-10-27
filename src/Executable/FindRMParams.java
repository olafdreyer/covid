package Executable;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

import helper.GlobalStaticProperties;

public class FindRMParams {
	
	@Parameter
	private List<String> parameters = new ArrayList<>();
	
	@Parameter(names = { "-humidity" }, description = "The humidity (between 0 und 1).", required = true)
	private Double humidity=0.0;
	
	@Parameter(names = { "-temperature" }, description = "The air temperature.", required = false )
	private Double temperature=GlobalStaticProperties.T0 + 18.0;

	@Parameter(names = { "-startradius" }, description = "The radius to start the calculation.", required = true )
	private Double r_start=10.0e-6;
	
	@Parameter(names = { "-height" }, description = "The height of the drop.", required = false )
	private Double height=2.0;

	@Parameter(names = { "-pressure" }, description = "The pressure of the air.", required = false )
	private Double pressure=GlobalStaticProperties.p_gas_standard;
	
	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getR_start() {
		return r_start;
	}

	public void setR_start(Double r_start) {
		this.r_start = r_start;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getPressure() {
		return pressure;
	}

	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
	
} // end class
