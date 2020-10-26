package helper;

public class Schmidt {

	private GlobalProperties gp;
	private double eta;
	private double rho;
	private double D;

	public Schmidt() {
		this.gp = new GlobalProperties();
		init();
	} // end empty constructor

	public Schmidt( GlobalProperties glob ) {
		this.gp = glob;
		init();
	} // end constructor
	
	public void setGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		init();
	} // end setGlobalPrperties

	public Schmidt withGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		init();
		return this;
	} // end withGlobalProperties

	private void init() {
		DynamicViscosity dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		eta = dv.eta();
		rho = gp.density_humid_air();
		D = gp.binary_diffusion_water();
	} // end setEta
		
	public double getEta() {
		return this.eta;
	} // end getEta

	public double nr() {
		return eta/rho/D;
	} // end nr
	
} // end class
