import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {

  private final int WINDOW_WIDTH = 800;
  private final int WINDOW_HEIGHT = 600;

  GamePanel game;
  Menu menu;

  JPanel mainPanel;

  final String GAME = "GAME";
  final String MENU = "MENU";

  private String currentCard = null;

  public MainFrame(Ballon b, Panier p) {

    this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    this.setResizable(false);
    this.setTitle("Panier-Ballon");
    this.setVisible(true);

    this.setLayout(null);

    mainPanel = new JPanel(new CardLayout());
    mainPanel.setBounds(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);


    game = new GamePanel(b,p,this);
    mainPanel.add(game, GAME);

    menu = new Menu(this);
    mainPanel.add(menu, MENU);

    this.add(mainPanel);

    ((CardLayout)mainPanel.getLayout()).show(mainPanel, MENU);
    currentCard = MENU;

  }

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
