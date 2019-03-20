import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;

public class Panier{
    
    public int sens=-1;
    public int variable=0;
	public int []xp = {700,780,765,715};
	public int []yp = {165+variable,165+variable,235+variable,235+variable};
	public int [] r1 = {700,150,81,15};
	public int []r2 = {770,80,10,70};
	public int []l1 = {726,165, 705,188};
	public int []l2= {752,165, 710,211};
	public int []l3= { 780,165, 715,235};
	public int []l4 = {780,165, 715,235};
	
	public Panier () {
		
	}
	
	public void dessine(Graphics g){
		g.setColor(Color.red);
		g.fillRect(r1[0],r1[1]+variable,r1[2],r1[3]);
		g.setColor(Color.white);
		g.fillRect(r2[0],r2[1]+variable,r2[2],r2[3]);
		g.drawPolygon(xp, yp, 4);
		g.drawLine(l1[0],l1[1]+variable, l1[2],l1[3]+variable);
		g.drawLine(l2[0],l2[1]+variable,l2[2],l2[3]+variable);
		g.drawLine(l3[0],l3[1]+variable,l3[2],l3[3]+variable);
		g.drawLine(l4[0], l4[1]+variable, l4[2], l4[3]+variable);
		
	}
	
	public void deplace(){
		this.variable= sens*5;
        if (this.r1[1]>=450 && this.r1[1]<=150){
            this.sens=-this.sens;
        }
	}
}
