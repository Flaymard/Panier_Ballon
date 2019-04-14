import java.awt.*;
import java.awt.event.*;

public class Ballon {

  int init_x;
  int init_y;
  int x;
  int y;
  public final int diameter = 50;

  //CONDITIONS INITIALES
  private double v_0;
  private double theta;

  public Ballon(int init_x, int init_y) {

    this.init_x = init_x;
    this.init_y = init_y;

    this.x = this.init_x;
    this.y = this.init_y;

  }

  public void drawBall(Graphics g) {

    g.setColor(new Color(255,128,0));

    g.fillOval(this.x, this.y, this.diameter, this.diameter);
  }


  // Les trois méthodes suivantes permettent de paramétrer les conditions initiales de déplacement ainsi que de faire évoluer la position du ballon en fonction du temps (équations paraboliques paramétrées)
  public void setConditionsInitiales(double v_0, double theta) {

    this.v_0 = v_0;
    this.theta = theta;

  }

  public void setCoordsInitiales(int initx , int inity) {

    init_x = initx;
    init_y = inity;
  }

  public void setCoords(double t) {
    double g = 0.01;
    this.x =(int) (v_0*Math.cos(theta)*t+init_x);
    this.y =(int) (g*(t*t)/2-v_0*Math.sin(theta)*t+init_y);

  }

  // Toutes les méthodes ci-dessous permettent d'obtenir des informations sur le déplacement et la position du ballon, ainsi que sa position à time = 0

  public double getVitesseX(double t) {
    double vx;
    vx= (v_0*Math.cos(theta));
    return vx;
  }

  public double getVitesseY(double t) {
    double vy;
    double g = 0.01;
    vy = g*t-v_0*Math.sin(theta);
    return vy;
  }

  public int getInitX() {
    return init_x;
  }

  public int getInitY() {
    return init_y;
  }

  public int getCenterX() {
    return this.x + (int) (diameter/2.0);
  }

  public int getCenterY() {
      return this.y + (int) (diameter/2.0);
  }

  public void reset() {
    this.x = init_x;
    this.y = init_y;
  }

}
