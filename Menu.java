import javax.swing.*;
import java.awt.Color;
import java.util.LinkedList;
import java.awt.event.*;

public class Menu extends JPanel implements ActionListener {

    public JPanel p;
    public JButton b;
    public JLabel l;
    public JLabel imagelele;
    public JLabel imagefloflo;
    public JLabel imagegaga;
    public JLabel imagejuju;

    public JButton b1;
    public JButton b2;
    public JButton b3;
    public JButton b4;

    private MainFrame frame;

    public Menu (MainFrame frame) {

      this.frame = frame;

        this.setSize(400,400);
        this.setLayout(null);

        // implémentation du bouton
        b= new JButton ("viens jouer a Panier Ballon");
        b.setBounds(50,50,250,30);
        b.setBackground(Color.black);
        b.setForeground(Color.white);
        b.addActionListener(this);

        // tous les boutons pour le choix du fond
        b1= new JButton ("Joue avec Flo");
        b1.setBounds(50,100,250,30);
        b1.setBackground(Color.black);
        b1.setForeground(Color.white);
        b1.addActionListener(this);

        b2= new JButton ("Joue avec Juju");
        b2.setBounds(50,150,250,30);
        b2.setBackground(Color.black);
        b2.setForeground(Color.white);
        b2.addActionListener(this);

        b3= new JButton ("Joue avec Gaga");
        b3.setBounds(50,200,250,30);
        b3.setBackground(Color.black);
        b3.setForeground(Color.white);
        b3.addActionListener(this);

        b4= new JButton ("Joue avec Lele");
        b4.setBounds(50,250,250,30);
        b4.setBackground(Color.black);
        b4.setForeground(Color.white);
        b4.addActionListener(this);

        // fond
        JLabel image = new JLabel (new ImageIcon ("photofond.jpg"));
        image.setBounds(0,0,getWidth(),getHeight());

        JPanel cont_im = new JPanel ();
        cont_im.setBounds(0,0,400,400);
        cont_im.add(image);

        // implémentation du label
        //l =  new JLabel();
        //l.setBounds(100,10,250,30);
        //l.setText("Viens jouer a Panier Ballon");


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

        this.add(cont_im);
        this.add(cont_bis);

        imagelele = new JLabel (new ImageIcon ("photolele.jpg"));
        imagefloflo = new JLabel (new ImageIcon ("photoflo.jpg"));
        imagegaga = new JLabel (new ImageIcon ("photogaga.jpg"));
        imagejuju = new JLabel (new ImageIcon ("photojuju.jpg"));
    }


    public void actionPerformed (ActionEvent e){
        if (e.getSource()==b){
            System.out.println("Let's GOOOOO");
            System.out.println(frame.switchCards());
        }
        if (e.getSource()==b1){
            fond f = new fond (imagefloflo);
        }
        if (e.getSource()==b2){
            fond f = new fond (imagejuju);
        }
        if (e.getSource()==b3){
            fond f = new fond (imagegaga);
        }
        if (e.getSource()==b4){
            fond f = new fond (imagelele);
        }
    }


}
