import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;

public class Panier{

	int []xp = {700,780,765,715};
	int []yp = {165,165,235,235};
	int [] r1 = {700,150,81,15};
	int []r2 = {770,80,10,70};
	int []l1 = {726,165, 705,188};
	int []l2= {752,165, 710,211};
	int []l3= { 780,165, 715,235};
	int []l4 = {780,165, 715,235};

	public Panier () {

	}

	public void dessine(Graphics g){
		g.setColor(Color.red);
		g.fillRect(r1[0],r1[1],r1[2],r1[3]);
		g.setColor(Color.white);
		g.fillRect(r2[0],r2[1],r2[2],r2[3]);
		g.drawPolygon(xp, yp, 4);
		g.drawLine(l1[0],l1[1], l1[2],l1[3]);
		g.drawLine(l2[0],l2[1],l2[2],l2[3]);
		g.drawLine(l3[0],l3[1],l3[2],l3[3]);
		g.drawLine(l4[0], l4[1], l4[2], l4[3]);

	}

	/*public void deplace(){

		if (){
			sens = -1;
		}
		if (){
			sens = 1;
		}
	}*/
}
