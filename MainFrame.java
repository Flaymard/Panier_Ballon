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
  Timer timerbis;

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
    timerbis = new Timer(deltaT, this);
    timerbis.start();
    timerbis = new Timer(100, this);
    timerbis.start();

  }

  public void paint(Graphics g) {

    g.setColor(Color.black);
    g.fillRect(0,0, 800, 600);

    ball.setCoords(time);
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
      ball.setConditionsInitiales((module/170)*4, argument);
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
      testContact();
      repaint();

    }
    if(e.getSource() == timerbis) {
      //this.panier.deplace();
      repaint();

    }

  }

  public void testContact() {
    double nbPas = 1000;
    double theta = 0.0;
    for(int i=0; i<nbPas; i++){
      theta = (2*Math.PI)*i/nbPas;
      boolean sur_xa = (ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) >= panier.r1[0]-1) && ((ball.getCenterX() + (ball.diameter/2)*Math.cos(theta))<= panier.r1[0]+1);
      boolean sur_ya = panier.r1[1]-1<=(ball.getCenterY() + (ball.diameter/2)*Math.sin(theta)) && (ball.y + (ball.diameter/2)*Math.sin(theta))<= panier.r1[1] + 1 + panier.r1[3];
      boolean sur_xp = (ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) >= panier.r2[0]-1) && ((ball.getCenterX() + (ball.diameter/2)*Math.cos(theta))<= panier.r2[0]+1);
      boolean sur_yp = panier.r2[1]-1<=(ball.getCenterY() + (ball.diameter/2)*Math.sin(theta)) && (ball.y + (ball.diameter/2)*Math.sin(theta))<= panier.r2[1]  + panier.r2[3];
      boolean dans_x = (ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) >= panier.r1[0]+1) && ((ball.getCenterX() + (ball.diameter/2)*Math.cos(theta))<= panier.r1[0]+panier.r1[2]);
      boolean dans_y =panier.r1[1]<=(ball.getCenterY() + (ball.diameter/2)*Math.sin(theta)) && (ball.y + (ball.diameter/2)*Math.sin(theta))<= panier.r1[1] + panier.r1[3];
      if(sur_xa && sur_ya && time > 60) {
        System.out.println("contact");
        ball.setCoordsInitiales(ball.x, ball.y);
        double new_v0 = Math.sqrt(ball.getVitesseX(time) * ball.getVitesseX(time) + ball.getVitesseY(time) * ball.getVitesseY(time));
        double new_theta = Math.atan(ball.getVitesseY(time)/ball.getVitesseX(time));
        ball.setConditionsInitiales(new_v0, Math.PI-new_theta);
        time = 0;
      }else if(sur_xp && sur_yp && time > 60){
        ball.setCoordsInitiales(ball.x,ball.y);
        double new_v0 = Math.sqrt(ball.getVitesseX(time) * ball.getVitesseX(time) + ball.getVitesseY(time) * ball.getVitesseY(time));
        double new_theta = Math.atan(ball.getVitesseY(time)/ball.getVitesseX(time));
        ball.setConditionsInitiales(new_v0, Math.PI-new_theta);
        time = 0;
      }else if(dans_x && dans_y){
        System.out.println("gechldcqhls"); //problème pcq ca s'affiche même si ca passe pas dans le ballon, je pense que c'est une histoire des theta mais je sais pas comment faire

      }
    }
  }

}
