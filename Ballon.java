import java.awt.*;
import java.awt.event.*;

public class Ballon {

  int init_x;
  int init_y;
  int x;
  int y;
  final int diameter = 50;

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

  public void setCoords(double t, double theta, double v_0) {
    double g =0.01;
    this.x =(int) (v_0*Math.cos(theta)*t+init_x);
    this.y =(int) (g*(t*t)/2-v_0*Math.sin(theta)*t+init_y);

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
