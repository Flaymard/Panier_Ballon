import javax.swing.*;
import java.awt.Color;
import java.util.LinkedList; 
import java.awt.event.*;
import java.awt.Graphics;

public class TestTrajectoire extends JFrame implements ActionListener {
    
    int x0=0;
    int y0=940;
    int x;
    int y;
    Timer monChrono;
    int deltaT;
    int temps; 
    Ballon b;
    
    
    public TestTrajectoire(){
        
    this.setSize(1000,1000);
    this.setTitle("IHM - Courbe GRAPHISME");
    deltaT= 10; 
    monChrono = new Timer(deltaT,this); 
    temps = 0; 
    x=x0;
    y=y0;
    b = new Ballon(20);
    monChrono.start();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    }
    
    public void paint(Graphics g){ 
        g.setColor(Color.black); 
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.orange); 
        g.fillOval(x,y,30,30);
    }
    
     //monChrono.stop();

    public void actionPerformed (ActionEvent e) { 
        if (e.getSource() == monChrono) {
            temps = temps + deltaT;
            this.setTitle("Temps  " + temps+ "  ms");
            double[] t = b.calculCoord((7*Math.PI)/180,(double)(temps),x0,y0);
            x=(int)(t[0]);
            y=(int)(t[1]);
            repaint (); 
        }
    }
    
    
    }

