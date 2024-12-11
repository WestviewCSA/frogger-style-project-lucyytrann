import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class WoodScrolling1{
	private Image forward; //backward, left, right; 	
	private AffineTransform tx;
	
	//attributes of the class
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;				//collision detection 
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 1;		//change to scale image
	double scaleHeight = 1; 		//change to scale image

	public WoodScrolling1() {
		forward 	= getImage("/imgs/"+"wood1.png"); //load the image for Tree
		//backward 	= getImage("/imgs/"+"backward.png"); //load the image for Tree
		//left 		= getImage("/imgs/"+"left.png"); //load the image for Tree
		//right 		= getImage("/imgs/"+"right.png"); //load the image for Tree

		//width and height or hit box
		width = 150;
		height = 50;
		
		//used for placement
		x = -width; //off screen for now
		y = 300;
		
		//if your movement will not be hopping base
		vx = 4;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public boolean collided(Monkey character) {
		
		Rectangle main = new Rectangle(character.getX(), character.getY(), 
									   character.getWidth(), character.getHeight());
		Rectangle thisObject = new Rectangle(x, y, width, height);
		
		//user built-in method to check collision
		return main.intersects(thisObject);
	} 
	
	//public boolean collision(Monkey charac) {
	//	int leg = charac.getX(); 
		
		
	//	return false; 
	//}

	//second constructor - allow setting x and y during construction
	public WoodScrolling1(int x, int y) {
		//call the default constructor for all the normal stuff
		this();//invokes original constructor
		
		//do the specific task for this constructor
		this.x = x;
		this.y = y;
	}
	
	//setter and getter 
	public int getX() {
		return x; 
	}
	
	public int getY() {
		return y; 
	}
	
	public int getWidth() {
		return width; 
	}
	
	public int getHeight() {
		return height; 
	}
	
	public int getVx() {
		return vx; 
	}
	
	public void setVx(int vx) {
		this.vx = vx; 
	}
	
	public int getVy() {
		return vy; 
	}
	
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		//update x and y if using vx,, vy variables
		
		x+=vx;
		y+=vy;	
		
		//for infinite scrolling - teleport to the other side 
		//once it leave the other side
		if (x > 750) {
			x = -150;
		}
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
		//draw hit box based on x, y , width, height
		//for collison detection
		if(Frame.debugging) {
			g.setColor(Color.blue);
			g.drawRect(x, y, width, height);
		}

	}
	
	public void stop() {
		vx = 0;
		vy = 0; 
	} 
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = WoodScrolling1.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
