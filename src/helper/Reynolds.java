package helper;

public class Reynolds {
	
	private GlobalProperties gp;
	private double eta;
	private double rho;
	
	public Reynolds() {
		this.gp = new GlobalProperties();
		init();
	} // end empty constructor

	public Reynolds( GlobalProperties glob ) {
		this.gp = glob;
		init();
	} // end constructor
	
	public void setGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		init();
	} // end setGlobalPrperties

	public Reynolds withGlobalProperties( GlobalProperties glob ) {
		this.gp = glob;
		init();
		return this;
	} // end withGlobalProperties

	private void init() {
		DynamicViscosity dv = new DynamicViscosity()
				.withGlobalProperties(gp);
		this.eta = dv.eta();
		this.rho = this.gp.density_humid_air();
	} // end init
	
	public double getEta() {
		return this.eta;
	} // end getEta
	
	public double nr( double v, double r ) {
		return v*2.0*r*rho/eta;
	} // end nr
	
	public double C_drag( double v, double r) {
		double re = nr(v,r);
		double c = (re > 1000.0) ? 0.424 : 24.0/re*(1 + 1/6.0*Math.pow(re, 2.0/3.0));
		return c;
	} // end C_drag
	
} // end class
