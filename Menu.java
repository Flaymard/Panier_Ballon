import javax.swing.*;
import java.awt.Color;
import java.util.LinkedList;
import java.awt.event.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Menu extends JPanel implements ActionListener {

    public JPanel p;
    public JButton b;
    public JLabel l;

    public JButton b1;
    public JButton b2;
    public JButton b3;
    public JButton b4;

    private MainFrame frame;

    public Menu (MainFrame frame) {

      this.frame = frame;

        this.setBounds((frame.getWidth()-this.getWidth())/2,(frame.getHeight()-this.getHeight())/2,400,400);
        this.setLayout(null);
        this.setLocation((frame.getWidth()-this.getWidth())/2,(frame.getHeight()-this.getHeight())/2);
        this.setBackground(Color.black);

        // bouton pour démarrer le jeu. Si aucun bouton de fond n'a été selectionné, alors le fond de la zone de jeu sera noire.
        b= new JButton ("Jouer au jeu");
        b.setBounds(50,50,250,30);
        b.setBackground(Color.black);
        b.setForeground(Color.white);
        b.addActionListener(this);

        // tous les boutons pour le choix du fond
        b1= new JButton ("Joue avec Florian");
        b1.setBounds(50,100,250,30);
        b1.setBackground(Color.black);
        b1.setForeground(Color.white);
        b1.addActionListener(this);

        b2= new JButton ("Joue avec Juliette");
        b2.setBounds(50,150,250,30);
        b2.setBackground(Color.black);
        b2.setForeground(Color.white);
        b2.addActionListener(this);

        b3= new JButton ("Joue avec Gaetan");
        b3.setBounds(50,200,250,30);
        b3.setBackground(Color.black);
        b3.setForeground(Color.white);
        b3.addActionListener(this);

        b4= new JButton ("Joue avec Lea");
        b4.setBounds(50,250,250,30);
        b4.setBackground(Color.black);
        b4.setForeground(Color.white);
        b4.addActionListener(this);

        // fond
        JLabel image = new JLabel (new ImageIcon ("photofond.jpg"));
        image.setBounds(0,0,getWidth(),getHeight());

        JPanel cont_im = new JPanel ();
        cont_im.setBounds(0,0,400,400);
        this.add(image);

        // implémentation du label
        //l =  new JLabel();
        //l.setBounds(100,10,250,30);
        //l.setText("Viens jouer a Panier Ballon");


        this.add(cont_im);
        // conteneur main
        JPanel cont_bis = new JPanel ();
        cont_bis.setLayout(null);
        cont_bis.setBounds(0,0,400,400);
        cont_bis.add(b);
        cont_bis.add(b1);
        cont_bis.add(b2);
        cont_bis.add(b3);
        cont_bis.add(b4);
        //cont_bis.add(l);

        this.add(cont_bis);
    }


    public void actionPerformed (ActionEvent e){
        if (e.getSource()==b){
            System.out.println(frame.switchCards());
        }

        // Chaque bouton est associé à une image de fond. Si on appuie sur un bouton, ce bouton devient vert pour signaler son activation,
        // et les autres deviennent rouge. On peut choisir un bouton puis un autre.

        if (e.getSource()==b1){
            try {
                BufferedImage im = ImageIO.read(new File("photoflo.jpg"));
                frame.game.setFond(im);
                b1.setBackground(Color.green);
                b2.setBackground(Color.blue);
                b3.setBackground(Color.blue);
                b4.setBackground(Color.blue);
            }
            catch(Exception i){}
            //System.out.println(frame.switchCards());
        }
        if (e.getSource()==b2){
            try {
                BufferedImage im = ImageIO.read(new File("photojuju.jpg"));
                frame.game.setFond(im);
                b2.setBackground(Color.green);
                b1.setBackground(Color.blue);
                b3.setBackground(Color.blue);
                b4.setBackground(Color.blue);
                }
            catch(Exception i){}
            //System.out.println(frame.switchCards());
        }
        if (e.getSource()==b3){
            try {
                BufferedImage im = ImageIO.read(new File("photogaga.jpg"));
                frame.game.setFond(im);
                b3.setBackground(Color.green);
                b1.setBackground(Color.blue);
                b2.setBackground(Color.blue);
                b4.setBackground(Color.blue);
                }
            catch(Exception i){}
            //System.out.println(frame.switchCards());
        }
        if (e.getSource()==b4){
            try {
                BufferedImage im = ImageIO.read(new File("photolele.jpg"));
                frame.game.setFond(im);
                b4.setBackground(Color.green);
                b1.setBackground(Color.blue);
                b2.setBackground(Color.blue);
                b3.setBackground(Color.blue);
                }
            catch(Exception i){}
            //System.out.println(frame.switchCards());
        }
    }


}
