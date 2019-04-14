import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements MouseListener, ActionListener, MouseMotionListener {

  // la MainFrame dans laquelle est contenue le GamePanel (cette variable permet d'interagir avec elle, pour pouvoir retourner au menu principal à la fin de la partie)
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

  // l'image de fond du jeu
  BufferedImage fond=null;

  // time : temps pour le déplacement du panier --- tempsJeu : décompte du temps imparti, initialisé à 60 secondes
  double time = 0;
  final int tempsJeu_init = 60;
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

    // dessin du fond défini dans setFond(BufferedImage im)
    g.drawImage(fond,0,0,800,600,null);

    // si le jeu est fini, on ne redessine rien d'autre
    if(gameFinished) {
      g.setColor(Color.white);
      g.setFont(new Font("Latin", Font.PLAIN, 35));
      String s = "FIN DE LA PARTIE";
      g.drawString(s,this.getWidth()/2 - 100,this.getHeight()/2);
      try {
        Thread.sleep(2000);
      } catch(Exception e) {

      }
      frame.switchCards(); // retour au menu principal
    }

    // dessin du ballon en fonction de ses coordonnées
    g.setColor(Color.orange);
    ball.setCoords(time);
    ball.drawBall(g);

    // dessin du panier
    panier.dessine(g);

    // dessin de l'horloge (affichage temps et score)
    horloge.dessine(g);

    // si il faut dessiner une trajectoire (déterminé dans mouseDragged)
    if(drawTraj) {
      Trajectoire traj = new Trajectoire((module/170)*4, argument, ball.getCenterX(), ball.getCenterY());
      traj.drawTrajectoire(g);
    }

  }

  // Utilisée pour afficher l'image im en fond du jeu
  public void setFond(BufferedImage im){
      this.fond = im;
  }

  public void mousePressed(MouseEvent e) {

    onBall = false;
    // coordonnées du clic dans le repère basé sur le centre du ballon
    int clic_circleX = e.getX() - ball.getCenterX();
    int clic_circleY = e.getY() - ball.getCenterY();

    // détermine si l'appui souris a été réalisé sur la balle (si le module du vecteur déterminé juste avant est inférieur ou égal au rayon du cercle)
    if(Math.sqrt(clic_circleX*clic_circleX + clic_circleY*clic_circleY) <= ball.diameter/2.0) {
      onBall = true;

    }

  }

  public void mouseReleased(MouseEvent e) {

    if(onBall) {

      drawTraj = false; // on désactive le tracé de trajectoire

      int cercleX = ball.getCenterX();
      int cercleY = ball.getCenterY();

      int sourisX = e.getX();
      int sourisY = e.getY();

      // on détermine l'argument et le module du vecteur reliant la souris au ballon pour en déduire des conditions initiales (vitesse et angle au départ) pour le ballon
      // le coefficient 4/170 appliqué au module a été déterminé par essais et erreurs. On a cherché le coefficient qui donnait une vitesse maximale raisonnable au ballon.
      argument = -1*Math.atan(((double) (cercleY-sourisY))/(cercleX-sourisX));
      module = Math.sqrt(Math.pow(cercleY - sourisY, 2) + Math.pow(cercleX - sourisX, 2));
      ball.setConditionsInitiales((module/170)*4, argument);

      // on démarre le timer, le ballon commence à bouger.
      time = 0;
      timerBall.start();

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

    // si la souris a été appuyée sur le ballon, on commence à tracer la trajectoire (cf méthode paint())
    if(onBall) {
      drawTraj = true;
    }

    argument = Math.atan(((double) (cercleY-sourisY))/(cercleX-sourisX));
    module = Math.sqrt(Math.pow(cercleY - sourisY, 2) + Math.pow(cercleX - sourisX, 2));
    repaint();

  }

  public void mouseMoved(MouseEvent e) {}

  public void actionPerformed(ActionEvent e){

    // à chaque avancée de la balle, on augmente son compteur et on regarde si elle touche un élément du panier
    if(e.getSource() == timerBall) {
      time += deltaT;
      testContact();
      repaint();

    }

    // ce timer gère le déroulement de la partie
    if(e.getSource() == timerHorloge) {

      tempsJeu -= 1;
      //dans ce cas, la partie se termine
      if(tempsJeu == 0) {
        timerHorloge.stop();
        horloge.setTemps(0);
        this.stopGame();
      }

      // activation du déplacement du panier si le temps est inférieur à 30 secondes
      if(!timerPanier.isRunning() && tempsJeu <= 30) {
        timerPanier.start();
      }

      horloge.setTemps(tempsJeu);
      repaint();

    }

    // déplacement du panier à chaque signal du timer
    if(e.getSource() == timerPanier) {
      this.panier.deplace();
      repaint();
    }

  }


  // Cette méthode permet de déterminer si le ballon rencontre un obstacle (avant de l'anneau ou planche)
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

    // balle sort du cadre
    boolean hors_x = ((ball.x + 50) >= 800 || ball.x<=0);
    boolean hors_y = (/*(ball.y + 50) <=0* ||*/ ball.y +50 >=600 );

    // On teste chaque point autour du ballon pour voir si il touche un élément du panier
    for(int i=0; i<nbPas; i++){
      theta = (2*Math.PI)*i/nbPas;

      // contact avec l'anneau
      sur_xa = (ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) >= panier.r1[0]-7) && ((ball.getCenterX() + (ball.diameter/2)*Math.cos(theta))<= panier.r1[0]+0) && ((ball.getCenterX() - (ball.diameter/2)) < panier.r1[0]);
      sur_ya = panier.r1[1]-7<=(ball.getCenterY() + (ball.diameter/2)*Math.sin(theta)) && (ball.getCenterY() + (ball.diameter/2)*Math.sin(theta))<= panier.r1[1] + 7 + panier.r1[3];
      if(sur_xa && sur_ya) break; // on sort de la boucle for si la condition est remplie (pas la peine de tester les autres points)

      // contact avec la planche
      sur_xp = (ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) >= panier.r2[0]-0) && ((ball.getCenterX() + (ball.diameter/2)*Math.cos(theta))<= panier.r2[0]+1);
      sur_yp = panier.r2[1]-1<=(ball.getCenterY() + (ball.diameter/2)*Math.sin(theta)) && (ball.getCenterY() + (ball.diameter/2)*Math.sin(theta))<= panier.r2[1]  + panier.r2[3];
      if(sur_xp && sur_yp) break; // même chose ici


    }

      if(sur_xa && sur_ya && time > 60) {
        // System.out.println("contact " + ( ball.getCenterX() + (ball.diameter/2)*Math.cos(theta) ) + " " + (ball.getCenterY() + (ball.diameter/2)*Math.sin(theta))); DEBUG
        ball.setCoordsInitiales(ball.x, ball.y); // le ballon commence un nouveau mouvement à partir de son emplacement actuel
        double new_v0 = Math.sqrt(ball.getVitesseX(time) * ball.getVitesseX(time) + ball.getVitesseY(time) * ball.getVitesseY(time)); // la norme de sa vitesse initiale est égale à celle de sa vitesse actuelle
        double new_theta = -Math.atan(ball.getVitesseY(time)/ball.getVitesseX(time)); // le ballon repart dans une direction symétrique (ex : arrivant d'en haut à gauche, il repart en bas à gauche)
        ball.setConditionsInitiales(new_v0, Math.PI-new_theta);
        time = 0; // on remet le temps à 0 pour le nouveau mouvement
      }

      // même déroulement ici
      else if(sur_xp && sur_yp && time > 60){
        ball.setCoordsInitiales(ball.x,ball.y);
        double new_v0 = Math.sqrt(ball.getVitesseX(time) * ball.getVitesseX(time) + ball.getVitesseY(time) * ball.getVitesseY(time));
        double new_theta = -Math.atan(ball.getVitesseY(time)/ball.getVitesseX(time));
        ball.setConditionsInitiales(new_v0, Math.PI-new_theta);
        time = 0;
      }

      // ici, si le panier est marqué, on fait partir le ballon verticalement vers le bas.
      else if(dans_x && dans_y && !panierMarque){
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
      }

      // Si le ballon sort du cadre, on remet le ballon à un emplacement aléatoire en bas à gauche de la zone de jeu.
      else if(hors_x || hors_y){
        //System.out.println("ballon sort"); DEBUG

        timerBall.stop();
        time = 0;
        ball.setCoordsInitiales((int)(Math.random()*(300-10)+ 10),(int)(Math.random()*(500-300)+300));
        hors_x = false;
        hors_y = false;
        panierMarque = false;
      }
  }

  // cette méthode est appelée par le MainFrame quand le joueur sélectionne un fond
  public void startTimer() {
    gameFinished = false;
    tempsJeu = tempsJeu_init;
    time = 0;
    timerHorloge.start();
  }

  // Cette méthode gère la fin du jeu
  public void stopGame() {
    timerBall.stop();
    timerPanier.stop();
    timerHorloge.stop();
    gameFinished = true;
    repaint();
    tempsJeu = 0;
  }

}
