import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements MouseListener, ActionListener, MouseMotionListener {

  Ballon ball = null;
  boolean onBall;

  boolean drawTraj;
  boolean panierMarque = false;

  Panier panier = null;

  Horloge horloge;

  double time = 0;
  int tempsJeu = 0;
  int deltaT = 4;
  Timer timer;
  Timer timerbis;

  int score = 0;

  double argument = 0.0;
  double module = 0.0;

  public MainFrame() {

    this.setSize(800,600);
    this.setResizable(false);
    this.setTitle("Panier-Ballon");
    this.setVisible(true);

    ImageIcon icon = new ImageIcon("icon.png");
    this.setIconImage(icon.getImage());

    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    timer = new Timer(deltaT, this);
    timerbis = new Timer(deltaT, this);
    timerbis.start();
    timerbis = new Timer(1000, this);
    timerbis.start();

    horloge = new Horloge(tempsJeu, score);

  }

  public void paint(Graphics g) {

    g.setColor(Color.black);
    g.fillRect(0,0, 800, 600);
    g.setColor(Color.orange);
    ball.setCoords(time);
    ball.drawBall(g);

    panier.dessine(g);

    g.setColor(Color.white);
    horloge.dessine(g);

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
      tempsJeu += 1000;
      horloge.setTemps(tempsJeu/1000);
      repaint();

    }

  }

  public void testContact() {
    double nbPas = 1000;
    double theta = 0.0;
    boolean sur_xa = false;
    boolean sur_ya = false;
    boolean sur_xp = false;
    boolean sur_yp = false;

    //System.out.println(panier.r1[0]);
    // balle rentre dans le panier

    boolean dans_x = (ball.getCenterX() >= panier.r1[0]+1) && (ball.getCenterX()<= panier.r1[0]+panier.r1[2] - 1);
    boolean dans_y = (panier.r1[1]<=ball.getCenterY()) && (ball.getCenterY() <= panier.r1[1] + panier.r1[3]);

    // balle sort du catch cadre
    boolean hors_x = ((ball.x + 50) >= 800 || ball.x<=0);
    boolean hors_y = (/*(ball.y + 50) <=0* ||*/ ball.y +50 >=600 );


    for(int i=0; i<nbPas; i++){
      theta = (2*Math.PI)*i/nbPas;

      // contact avec l'anneau
      sur_xa = (ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) >= panier.r1[0]-7) && ((ball.getCenterX() + (ball.diameter/2)*Math.cos(theta))<= panier.r1[0]+0) && ((ball.getCenterX() - (ball.diameter/2)) < panier.r1[0]);
      sur_ya = panier.r1[1]-7<=(ball.getCenterY() + (ball.diameter/2)*Math.sin(theta)) && (ball.getCenterY() + (ball.diameter/2)*Math.sin(theta))<= panier.r1[1] + 7 + panier.r1[3];
      if(sur_xa && sur_ya) break;

      // contact avec la planche
      sur_xp = (ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) >= panier.r2[0]-0) && ((ball.getCenterX() + (ball.diameter/2)*Math.cos(theta))<= panier.r2[0]+1);
      sur_yp = panier.r2[1]-1<=(ball.getCenterY() + (ball.diameter/2)*Math.sin(theta)) && (ball.getCenterY() + (ball.diameter/2)*Math.sin(theta))<= panier.r2[1]  + panier.r2[3];
      if(sur_xp && sur_yp) break;


    }

      if(sur_xa && sur_ya && time > 60) {
        // System.out.println("contact " + ( ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) ) + " " + (ball.getCenterY() + (ball.diameter/2)*Math.sin(theta))); DEBUG
        ball.setCoordsInitiales(ball.x, ball.y);
        double new_v0 = Math.sqrt(ball.getVitesseX(time) * ball.getVitesseX(time) + ball.getVitesseY(time) * ball.getVitesseY(time));
        double new_theta = -Math.atan(ball.getVitesseY(time)/ball.getVitesseX(time));
        ball.setConditionsInitiales(new_v0, Math.PI-new_theta);
        time = 0;
      }

      else if(sur_xp && sur_yp && time > 60){
        ball.setCoordsInitiales(ball.x,ball.y);
        double new_v0 = Math.sqrt(ball.getVitesseX(time) * ball.getVitesseX(time) + ball.getVitesseY(time) * ball.getVitesseY(time));
        double new_theta = -Math.atan(ball.getVitesseY(time)/ball.getVitesseX(time));
        ball.setConditionsInitiales(new_v0, Math.PI-new_theta);
        time = 0;
      }else if(dans_x && dans_y && !panierMarque){
        // System.out.println("Panier"); DEBUG
        score = score + 1;
        horloge.setScore(score);
        dans_x = false;
        dans_y = false;
        ball.setCoordsInitiales(ball.x,ball.y);
        double new_v0 = Math.sqrt(ball.getVitesseX(time) * ball.getVitesseX(time) + ball.getVitesseY(time) * ball.getVitesseY(time));
        ball.setConditionsInitiales(new_v0, -Math.PI/2);
        time = 0;
        panierMarque = true;
      }else if(hors_x || hors_y){
        //System.out.println("ballon sort"); DEBUG

        timer.stop();
        time = 0;
        ball.setCoordsInitiales((int)(Math.random()*(300-10)+ 10),(int)(Math.random()*(500-300)+300));
        hors_x = false;
        hors_y = false;
        panierMarque = false;
      }
  }

}
