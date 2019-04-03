import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Horloge {
    
    private int temps;
    private int score;
    
    
    public Horloge(int tempsDonne, int scoreDonne){
        temps=tempsDonne;
        score=scoreDonne;
    }
    
    public void dessine(Graphics g){
            g.setFont(new Font("Latin", Font.PLAIN, 35));
            String s1 = Integer.toString(temps)+" s";
            g.drawString(s1,100,100);
            String s2 = "SCORE : "+Integer.toString(score);
            g.drawString(s2,300,300);
    }
    
    public void setTemps(int valeur){
        this.temps = valeur;
    }
    
    public void setScore(int valeur){
        this.score = score;
    }
    
}
    

