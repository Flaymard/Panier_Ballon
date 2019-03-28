import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Horloge {
    
    public int temps;
    
    public Horloge(int tempsDonne){
        temps=tempsDonne;
    }
    
    public void dessine(Graphics g){
        g.setFont(new Font("Latin", Font.PLAIN, 35));
        String s = Integer.toString(temps)+" s";
        g.drawString(s,100,100);
    }
}
