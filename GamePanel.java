import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

  // la MainFrame dans laquelle est contenue le GamePanel
  MainFrame frame;

  // objet Ballon et variable booléenne pour détecter si le joueur clique sur le ballon ou en dehors
  Ballon ball = null;
  boolean onBall;

  // détermine si il faut dessiner une trajectoire ou non selon la situation
  boolean drawTraj;

  boolean panierMarque = false;

  // objet Panier
  Panier panier = null;

  // l'objet Horloge qui affiche le temps imparti au joueur ainsi que son score
  Horloge horloge;

  // time : temps pour le déplacement du panier --- tempsJeu : décompte du temps imparti, initialisé à 60 secondes
  double time = 0;
  final int tempsJeu_init = 35;
  int tempsJeu = tempsJeu_init;
  int deltaT = 4;
  int deltaT_panier = 25;

  // tous les Timer dont on se sert pour les différents objets
  Timer timerBall;
  Timer timerHorloge;
  Timer timerPanier;

  // contient le score du joueur
  int score = 0;

  // jeu fini
  boolean gameFinished = false;

  // pour l'interaction avec le ballon
  double argument = 0.0;
  double module = 0.0;

  public GamePanel(Ballon b, Panier p, MainFrame frame) {

    // initialisation du JPanel
    this.setSize(800,600);
    this.setLocation(0,0);
    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    // initialisation des Timer
    timerBall = new Timer(deltaT, this);
    timerPanier = new Timer(deltaT_panier, this);
    timerHorloge = new Timer(1000, this);

    // initialisation de l'horloge de jeu
    horloge = new Horloge(tempsJeu, score);

    // initialisation des éléments du jeu
    this.ball = b;
    this.panier = p;
    this.frame = frame;

  }

  public void paint(Graphics g) {

    // dessin du fond noir
    g.setColor(Color.black);
    g.fillRect(0,0, 800, 600);

    // si le jeu est fini, on ne dessine rien d'autre
    if(gameFinished) {
      g.setColor(Color.red);
      g.setFont(new Font("Latin", Font.PLAIN, 35));
      g.drawString("FIN DE LA PARTIE",this.getWidth()/2 - 100,this.getHeight()/2);
      try {
        Thread.sleep(2000);
      } catch(Exception e) {
        
      }
      frame.switchCards();
    }

    // dessin du ballon en fonction de ses coordonnées
    g.setColor(Color.orange);
    ball.setCoords(time);
    ball.drawBall(g);

    // dessin du panier
    panier.dessine(g);

    // dessin de l'horloge (affichage temps et score)
    g.setColor(Color.white);
    horloge.dessine(g);

    // si il faut dessiner une trajectoire (déterminé dans mouseDragged)
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
      timerBall.start();
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

    if(onBall) {
      drawTraj = true;
    }

    argument = Math.atan(((double) (cercleY-sourisY))/(cercleX-sourisX));
    module = Math.sqrt(Math.pow(cercleY - sourisY, 2) + Math.pow(cercleX - sourisX, 2));
    repaint();

  }

  public void mouseMoved(MouseEvent e) {}

  public void actionPerformed(ActionEvent e){

    if(e.getSource() == timerBall) {
      time += deltaT;
      testContact();
      repaint();

    }
    if(e.getSource() == timerHorloge) {
      //this.panier.deplace();
      if(tempsJeu == 0) {
        timerHorloge.stop();
        this.stopGame();
      }
      tempsJeu -= 1;
      if(!timerPanier.isRunning() && tempsJeu <= 30) {
        timerPanier.start();
      }

      horloge.setTemps(tempsJeu);
      repaint();

    }
    if(e.getSource() == timerPanier) {
      this.panier.deplace();
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

        timerBall.stop();
        time = 0;
        ball.setCoordsInitiales((int)(Math.random()*(300-10)+ 10),(int)(Math.random()*(500-300)+300));
        hors_x = false;
        hors_y = false;
        panierMarque = false;
      }
  }

  public void startTimer() {
    gameFinished = false;
    tempsJeu = tempsJeu_init;
    time = 0;
    timerHorloge.start();
  }

  public void stopGame() {
    gameFinished = true;
    timerBall.stop();
    timerPanier.stop();
    timerHorloge.stop();
  }

}
