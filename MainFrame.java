import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements MouseListener, ActionListener, MouseMotionListener {

  Ballon ball = null;
  boolean onBall;

  boolean drawTraj;

  Panier panier = null;

  double time = 0;
  int deltaT = 15;
  Timer timer;

  double argument = 0.0;
  double module = 0.0;

  public MainFrame() {

    this.setSize(800,600);
    this.setResizable(false);
    this.setTitle("Panier-Ballon");
    this.setVisible(true);

    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    timer = new Timer(deltaT, this);

  }

  public void paint(Graphics g) {

    g.setColor(Color.black);
    g.fillRect(0,0, 800, 600);

    ball.setCoords(time, argument, (module/170)*4);
    ball.drawBall(g);
    panier.dessine(g);

    if(drawTraj) {
      Trajectoire traj = new Trajectoire((module/170)*4, argument, ball.getCenterX(), ball.getCenterY());
      traj.drawTrajectoire(g);
    }

  }

  public void setBallon(Ballon b) {

    this.ball = b;
    System.out.println("ball initialized");

  }

  public void setPanier(Panier p) {
    this.panier = p;
  }

  public void mousePressed(MouseEvent e) {

    int circleX = e.getX() - ball.getCenterX();
    int circleY = e.getY() - ball.getCenterY();
    onBall = false;

    if(Math.sqrt(circleX*circleX + circleY*circleY) <= ball.diameter/2.0) {
      onBall = true;

      //timer.start();
    }

  }

  public void mouseReleased(MouseEvent e) {

    if(onBall) {

      drawTraj = false;

      int cercleX = ball.getCenterX();
      int cercleY = ball.getCenterY();

      int sourisX = e.getX();
      int sourisY = e.getY();

      argument = -1*Math.atan(((double) (cercleY-sourisY))/(cercleX-sourisX));
      module = Math.sqrt(Math.pow(cercleY - sourisY, 2) + Math.pow(cercleX - sourisX, 2));
      timer.start();
      time = 0;
      repaint();

    }

  }

  public void mouseExited(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}

  public void mouseDragged(MouseEvent e) {

    int cercleX = ball.getCenterX();
    int cercleY = ball.getCenterY();

    int sourisX = e.getX();
    int sourisY = e.getY();

    drawTraj = true;

    argument = Math.atan(((double) (cercleY-sourisY))/(cercleX-sourisX));
    module = Math.sqrt(Math.pow(cercleY - sourisY, 2) + Math.pow(cercleX - sourisX, 2));
    repaint();

  }

  public void mouseMoved(MouseEvent e) {}

  public void actionPerformed(ActionEvent e){

    if(e.getSource() == timer) {
      time += deltaT;
      repaint();

    }

  }

}
