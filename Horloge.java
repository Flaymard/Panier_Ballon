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


    // affichage du temps et du score en haut de la fenêtre de jeu
    public void dessine(Graphics g){

            g.setColor(Color.green);
            g.setFont(new Font("Latin", Font.PLAIN, 35));
            String s1 = Integer.toString(temps)+" s";
            g.drawString(s1,100,150);
            String s2 = "SCORE : "+Integer.toString(score);
            g.drawString(s2,300,150);
    }

    public void setTemps(int valeur){
        this.temps = valeur;
    }

    public void setScore(int valeur){
        this.score = valeur;
    }

}
