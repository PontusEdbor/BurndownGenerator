import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.LinkedList;

public class Burndown{
    private static double labs = 26.0;
    private final static int baseThickness = 5;
    private final static int padding = 15;
    private final static int width = 1000;
    private final static int height = 400;
    
    public static void main (String[] args) throws Exception{
	BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = bi.createGraphics();
	LinkedList<GraphPoint> dotList = new LinkedList<GraphPoint>();
	dotList.add(new GraphPoint(padding,padding));
	WebReader wr = new WebReader("https://pontusedbor.github.io/");

	//String goals = wr.getLine();
	String goals = "02040444003200004020321040";
	double stepLength = ((width-padding-padding)/labs);
	int completed = 0;
	double xPos = padding;
	double yPos = padding;
	double steps = 0;
	for (char c : goals.toCharArray()){
	    completed +=Character.getNumericValue(c);
	    yPos = (completed/48.0)*(height-padding-padding)+padding;
	    xPos += stepLength;
	    dotList.add(new GraphPoint((int)xPos,(int)yPos));
	}
	
	setupBase(g2d);
	drawDots(g2d, dotList);
	try{
	    File outputfile = new File("Burndown.png"); 
	    ImageIO.write(bi, "png", outputfile);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    public static void setupBase (Graphics2D g2d){
	g2d.setColor(Color.white);
	g2d.fillRect(0,0,width,height);
	
	g2d.setColor(Color.black);
	g2d.setStroke(new BasicStroke(baseThickness));
	g2d.drawLine(padding,padding,padding,height-padding);
	g2d.drawLine(padding,height-padding,width-padding,height-padding);

	g2d.setStroke(new BasicStroke(1));
	g2d.drawLine(padding,padding,width-padding,height-padding);
	
    }
    public static void drawDots(Graphics2D g2d,LinkedList<GraphPoint> gpl){
	int size = 10;
	g2d.setStroke(new BasicStroke(3));
	g2d.setColor(Color.blue);
	GraphPoint prev = null;
	for (GraphPoint gp: gpl){
	    g2d.fillOval(gp.getX()-(size/2),gp.getY()-(size/2),size,size);
	    if (prev != null){
		g2d.drawLine(gp.getX(),gp.getY(),prev.getX(),prev.getY());
	    }
	    prev = gp;
	}
    }
}
