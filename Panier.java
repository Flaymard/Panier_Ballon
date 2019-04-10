import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;

public class Panier{

    public int sens=1;
    public int variable=0;
    public boolean descend=true;
	public int []xpi = {670,780,760,690};  // coordonnées initiales x des points du polygone du filet
    public int []xp= {670,780,760,690};
	public int []ypi = {165,165,280,280};  // coordonnées initiales y des points du polygone du filet
    public int []yp={165,165,280,280};
	public int [] r1i = {670,150,111,15}; // coordonées initiales rectangle rouge ou arceau
    public int []r1={670,150,111,15};  
	public int []r2i = {770,80,10,70};  // coordonnées initiales rectangle blanc ou planche
    public int []r2={770,80,10,70};
	public int []l1i ={726,165,705,188};
    public int []l1={726,165,705,188};
	public int []l2i= {752,165,710,211};
    public int []l2={752,165,710,211};
	public int []l3i={780,165,715,235};
    public int []l3={780,165,715,235};
	public int []l4i ={780,165,715,235};
    public int []l4={780,165,715,235};

	public Panier () {

	}

	public void dessine(Graphics g){
		g.setColor(Color.red);

    r1[1]= r1[1]+ variable;
    r2[1] = r2[1]+variable;
    l1[1] = l1[1]+variable;
    l1[3] =l1[3]+variable;
    l2[1] =l2[1]+variable;
    l2[3] = l2[3]+variable;
    l3[1]= l3[1]+variable;
    l3[3]= l3[3]+variable;
    l4[1] = l4[1]+variable;
    l4[3]= l4[3]+variable;

		g.fillRect(r1[0],r1[1],r1[2],r1[3]);
		g.setColor(Color.white);
		g.fillRect(r2[0],(r2[1]),r2[2],r2[3]);

    for(int i=0;i<yp.length;i++){
        yp[i]=yp[i]+variable;
    }

    g.drawPolygon(xp, yp, 4);
		g.drawLine(l1[0],l1[1], l1[2],l1[3]);
		g.drawLine(l2[0],l2[1],l2[2],l2[3]);
		g.drawLine(l3[0],l3[1],l3[2],l3[3]);
		g.drawLine(l4[0],l4[1],l4[2],l4[3]);

	}

	public void deplace(){
		this.variable= sens*5;
        if (this.r1[1]>450){
            descend=false;
        }
        if(this.r1[1]<150){
            descend=true;
        }
        if(descend==false){
            sens=-1;
        } else {
            sens=1;
        }
        System.out.println(r1[1]);
	}
}
