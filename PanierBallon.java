import javax.swing.*;
import java.awt.*;

public class PanierBallon {

  static MainFrame window;
  Ballon b;
  Panier p;

  public static void main(String[] args) {

    window = new MainFrame();
    Ballon b = new Ballon(100,450);
    Panier p = new Panier();
    window.setBallon(b);
    window.setPanier(p);

  }

}
