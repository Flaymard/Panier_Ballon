import java.awt.Graphics;
import java.awt.Color;

public class Trajectoire {

  double module, argument;
  int [] coordX;
  int [] coordY;
  final int nbPoints = 30;

  public Trajectoire(double v_0, double argument, int init_x, int init_y){
    coordX = new int [nbPoints];
    coordY = new int [nbPoints];
    int deltaT = 5;
    double g = 0.01;

    for(int i=0;i<nbPoints;i++) {
      coordX[i]= (int) (v_0*Math.cos(-argument)*i*deltaT+init_x);
      coordY[i]= (int) (g*(i*deltaT*i*deltaT)/2-v_0*Math.sin(-argument)*i*deltaT+init_y);
    }
  }

  public void drawTrajectoire(Graphics g) {

    g.setColor(new Color(0,76,153));

    for(int i=0;i<nbPoints;i++){
      g.fillOval(coordX[i] -3,coordY[i] -3,6,6);
    }

  }


}
