import javax.swing.*;
import java.awt.*;

public class PanierBallon {

  static MainFrame window;
  Ballon b;
  Panier p;

  public static void main(String[] args) {

    Ballon b = new Ballon(100,450);
    Panier p = new Panier();
    window = new MainFrame();
    window.setPanier(p);
    window.setBallon(b);

  }

}
