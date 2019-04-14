import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.*;

public class MainFrame extends JFrame {

  private final int WINDOW_WIDTH = 800;
  private final int WINDOW_HEIGHT = 600;

  // les deux JPanel qui se succèdent dans la fenêtre
  GamePanel game;
  Menu menu;

  // Ce JPanel contiendra le CardLayout permettant de changer entre le menu et la fenêtre de jeu
  JPanel mainPanel;

  // utilisées dans le CardLayout
  final String GAME = "GAME";
  final String MENU = "MENU";

  private String currentCard = null;

  public MainFrame(Ballon b, Panier p) {

    // définition de la fenêtre
    this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    this.setResizable(false);
    this.setTitle("Panier-Ballon");
    this.setVisible(true);
    this.setLayout(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setIconImage((new ImageIcon("icon.png")).getImage());

    // Définition du CardLayout
    mainPanel = new JPanel(new CardLayout());
    mainPanel.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);

    // Définition de la zone de jeu
    game = new GamePanel(b,p,this);
    mainPanel.add(game, GAME);

    // Définition du menu
    menu = new Menu(this);
    mainPanel.add(menu, MENU);

    this.add(mainPanel);

    // affichage du menu au départ
    ((CardLayout)mainPanel.getLayout()).show(mainPanel, MENU);
    currentCard = MENU;

  }

  // cette méthode permet de changer de "carte" entre celle contenant le menu et celle contenant la zone de jeu.
  public String switchCards() {

    if(currentCard.equals(MENU)) {
      ((CardLayout)mainPanel.getLayout()).show(mainPanel, GAME);
      currentCard = GAME;
      game.startTimer();
    }
    else if(currentCard.equals(GAME)) {
      ((CardLayout)mainPanel.getLayout()).show(mainPanel, MENU);
      currentCard = MENU;
    }

    return currentCard;
  }
}
