package helper;

public class Prandl {

	private GlobalProperties gp;
	private double c_gas;
	private double eta;
	private double K;
	
	public Prandl() {
		this.gp = new GlobalProperties();
		init();
	} // end empty constructor

	public Prandl( GlobalProperties glob ) {
		this.gp = glob;
		init();
	} // end constructor
	
	public void setGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		init();
	} // end setGlobalPrperties

	public Prandl withGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		init();
		return this;
	} // end withGlobalProperties
	
	private void init() {
		DynamicViscosity dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		this.eta = dv.eta();
		this.c_gas = gp.specific_heat_water_air();
		ThermalConductivity tc = new ThermalConductivity()
				.withGlobalProperties(gp);
		this.K = tc.K_air_water();
	} // end init

	public double nr() {
		return c_gas*eta/K;
	} // end nr

	public double getC_gas() {
		return c_gas;
	}

	public double getEta() {
		return eta;
	}

	public double getK() {
		return K;
	}
	 
} // end class
