import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Life{
	private Image image; //backward, left, right; 	
	private AffineTransform tx;
	
	//attributes of the classs
	int width, height;				//collision detection 
	int x;
	int y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 2;		//change to scale image
	double scaleHeight = 2; 		//change to scale image

	public Life() {
		image 	= getImage("/imgs/"+"heart.png"); //load the image for Tree
		//backward 	= getImage("/imgs/"+"backward.png"); //load the image for Tree
		//left 		= getImage("/imgs/"+"left.png"); //load the image for Tree
		//right 		= getImage("/imgs/"+"right.png"); //load the image for Tree

		//width and height or hit box
		width = 20;
		height = 20;
		
		//used for placement 
		x = 0;
		y = 0;
		
		//if your movement will not be hopping base
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	

	//second constructor - allow setting x and y during construction
	public Life(int x, int y) {
		//call the default constructor for all the normal stuff
		this();//invokes original constructor
		
		//do the specific task for this constructor
		this.x = x;
		this.y = y;
	}
	
	
	
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
	
	public void setX(int x) {
		this.x = x;
	}
	
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		//update x and y if using vx,, vy variables
		
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		g2.drawImage(image, tx, null);
		//draw hit box based on x, y , width, height
		//for collison detection
		if(Frame.debugging) {
			g.setColor(Color.blue);
			g.drawRect(x, y, width, height);
		}

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Life.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}