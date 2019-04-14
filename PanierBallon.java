import javax.swing.*;
import java.awt.*;

public class PanierBallon {

  static MainFrame window; // on la déclare en static pour pouvoir accéder à ses méthodes et constantes depuis d'autres objets
  Ballon b;
  Panier p;

  public static void main(String[] args) {

    // création du panier et du ballon
    Ballon b = new Ballon(100,450);
    Panier p = new Panier();
    window = new MainFrame(b,p); // création du MainFrame
    //window.setPanier(p);
    //window.setBallon(b);

  }

}
