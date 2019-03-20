public class Ballon {
	
    // Vo est la vitesse initiale 
    double vo;  
    // g est la gravité 
    double g = 100;
    
	public Ballon (double vIni) {
		this.vo=vIni;
	}
    
    // méthode tir avec équations paraboliques
    public double[] calculCoord(double a, double temps, int x, int y){       // a est l'angle alpha et temps est une variable associée à un timer
        double[] t = new double[2];
        t[1]=-this.g*Math.pow(temps/1000,2)/2-this.vo*Math.sin(a)*(temps/1000)+y;
        t[0]=this.vo*Math.cos(a)*(temps/1000)+x;
        return t;
    }

}

