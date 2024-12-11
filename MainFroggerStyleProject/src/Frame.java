import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	public static boolean debugging = true;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	int score = 0;
	boolean gameOver = false; 
	
	Font myFont = new Font("Courier", Font.BOLD, 27);
	Font myFont1 = new Font("Courier", Font.BOLD, 25);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
	SimpleAudioPlayer spiderMusic = new SimpleAudioPlayer("spider.wav", false);
	SimpleAudioPlayer coinMusic = new SimpleAudioPlayer("coin.wav", false);
	SimpleAudioPlayer waterMusic = new SimpleAudioPlayer("river.wav", false);
	//SimpleAudioPlayer movingMusic = new SimpleAudioPlayer("monkey.wav", false);
	//Music soundBang = new Music("bang.wav", false);
	//Music soundHaha = new Music("haha.wav", false);
	Monkey monkey = new Monkey(341,682);
	BananaCoin coin = new BananaCoin(100,100);
	Background background = new Background();
	WinScreen winScreen = new WinScreen();
	DeathScreen deathScreen = new DeathScreen();
	//a row of MonkeyScorlling objects
	//MonkeyScrolling[] row1 = new MonkeyScrolling[10];
	
	//Spider spider = new Spider();
	//a row of MonkeyScorlling objects
	
	ArrayList<BananaCoin> coin1List = new ArrayList<BananaCoin>(); 
	ArrayList<BananaCoin> coin2List = new ArrayList<BananaCoin>(); 
	ArrayList<BananaCoin> coin3List = new ArrayList<BananaCoin>(); 
	ArrayList<BananaCoin> coin4List = new ArrayList<BananaCoin>();
	ArrayList<BananaCoin> coin5List = new ArrayList<BananaCoin>();
	ArrayList<Life> lives = new ArrayList<Life>();
	SpiderScrolling[] row1 = new SpiderScrolling[2];
	SpiderScrolling[] row2 = new SpiderScrolling[3];
	SpiderScrolling2[] row3 = new SpiderScrolling2[4];
	WoodScrolling1[] wood1 = new WoodScrolling1[3]; 
	WoodScrolling2[] wood2 = new WoodScrolling2[3]; 
	
	//BananaCoin[] coins = new BananaCoin[4];
	
	
		
	
	//frame width/height
	static int width = 750; //745
	static int height = 775;	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		//paint the objects that you have
		background.paint(g);
		//coin.paint(g);	
		//backgroundMusic.play();
		
		//ADD TEXT
		g.setFont(myFont);
		g.setColor(new Color(255,235,59));
		g.drawString(""+score, 717, 53);
		
		
		
		//have the row1 objects paint on the screen!
		//for each obj in row1
		/*
		 * for(MonkeyScrolling obj : row1) {
		 * obj.paint(g);
		 * }
		 */
		for(SpiderScrolling obj : row1) {
			obj.paint(g);
		}
		
		for(SpiderScrolling obj : row2) {
			obj.paint(g);
		}
		
		for(SpiderScrolling2 obj : row3) {
			obj.paint(g);
		}
		
		for(WoodScrolling1 obj : wood1) {
			obj.paint(g);
		}
		
		for(WoodScrolling2 obj : wood2) {
			obj.paint(g);
		}
		
		for(BananaCoin obj: coin1List) {
			obj.paint(g);
		}
		
		for(BananaCoin obj: coin2List) {
			obj.paint(g);
		}
		
		for(BananaCoin obj: coin3List) {
			obj.paint(g);
		}
		
		for(BananaCoin obj: coin4List) {
			obj.paint(g);
		}
		
		for(BananaCoin obj: coin5List) {
			obj.paint(g);
		}
		
		for(Life obj: lives) {
			obj.paint(g);
		}
		
		collidedSpider();
		collidedWood();
		collidedBananaCoin();
		insideFrame();	
		
		monkey.paint(g); // at last
		
		if (score == 10) {
			monkey.setX(341);
			monkey.setY(682); 
			winScreen.paint(g);
		}
		
		if (gameOver) {
			monkey.setX(341);
			monkey.setY(682); 
			deathScreen.paint(g);
		}
		
	}
	
	
	public void resetMonkey() {
		 monkey.setX(341);
		 monkey.setY(682); 
        
		 if (lives.size() > 1){
			 lives.remove(0);
		 }
		 else {
			 gameOver = true; 
		 }
		 
	}
	
	public void insideFrame() {
		if(monkey.getY()>=700) {
	        monkey.setY(682);
		}
		
		if (monkey.getX()<=0) {
			monkey.setX(0);
		}
		
		if(monkey.getY()<=0) {
			monkey.setY(0);
		}
		
		if (monkey.getX() >= 683) {
			monkey.setX(682);
		}
	}
	
	public void resetGame() {
		gameOver = false; 
		resetBananaCoin();
		 initializeBananaCoin();
		 initializeLives();
		 score = 0;
		 monkey.setX(341);
		 monkey.setY(682);
	}
	
	public void collidedSpider() {
		for(SpiderScrolling obj : row1) {
			if (obj.collided(monkey)) {
				System.out.println("ouch"); //check
				spiderMusic = new SimpleAudioPlayer("spider.wav", false);
				spiderMusic.play();
				resetMonkey();
				JOptionPane.showMessageDialog(null, "Your monkey got hit by the spider :(");
			}
		}

		for(SpiderScrolling obj : row2) {
			if (obj.collided(monkey)) {
				spiderMusic = new SimpleAudioPlayer("spider.wav", false);
				spiderMusic.play();
				resetMonkey();
				JOptionPane.showMessageDialog(null, "Your monkey got hit by the spider :(");
			}
		}
		
		for(SpiderScrolling2 obj : row3) {
			if (obj.collided(monkey)) {
				spiderMusic = new SimpleAudioPlayer("spider.wav", false);
				spiderMusic.play();
				resetMonkey();
				JOptionPane.showMessageDialog(null, "Your monkey got hit by the spider :(");
			}
		}
		
	}
	
	public void collidedWood() {
		boolean riding = false; 
		for(WoodScrolling1 obj : wood1) {
			if(obj.collided(monkey)) {
				monkey.setVx(obj.getVx()); 
				riding = true; 
				break;
			}
		}
		
		for(WoodScrolling2 obj : wood2) {
			if(obj.collided(monkey)) {
				monkey.setVx(obj.getVx()); 
				riding = true; 
				break;
			}
		}
		
		if((monkey.getY() > 315 && monkey.getY() < 465)) {
			if (!riding || monkey.getX()<=0) {
				resetMonkey();
				waterMusic = new SimpleAudioPlayer("river.wav", false);
				waterMusic.play();
				JOptionPane.showMessageDialog(null,"Your monkey has drowned :(");
			}
		}
		else if (!riding) {
			monkey.setVx(0); 
		}
	}
	
	public void collidedBananaCoin() {
		for (int i = coin1List.size() - 1; i >= 0; i--) {
		    BananaCoin coin = coin1List.get(i);
		    if (coin.collided(monkey)) {
		    	coinMusic = new SimpleAudioPlayer("coin.wav", false);
		    	coinMusic.play();
		        coin1List.remove(i);
		        score++;
		    }
		}
		
		for (int i = coin2List.size() - 1; i >= 0; i--) {
		    BananaCoin coin = coin2List.get(i);
		    if (coin.collided(monkey)) {
		    	coinMusic = new SimpleAudioPlayer("coin.wav", false);
		    	coinMusic.play();
		        coin2List.remove(i);
		        score++;
		    }
		}
		
		for (int i = coin3List.size() - 1; i >= 0; i--) {
		    BananaCoin coin = coin3List.get(i);
		    if (coin.collided(monkey)) {
		    	coinMusic = new SimpleAudioPlayer("coin.wav", false);
		    	coinMusic.play();
		        coin3List.remove(i);
		        score++;
		    }
		}
		
		for (int i = coin4List.size() - 1; i >= 0; i--) {
		    BananaCoin coin = coin4List.get(i);
		    if (coin.collided(monkey)) {
		    	coinMusic = new SimpleAudioPlayer("coin.wav", false);
		    	coinMusic.play();
		        coin4List.remove(i);
		        score++;
		    }
		}
		
		for (int i = coin5List.size() - 1; i >= 0; i--) {
		    BananaCoin coin = coin5List.get(i);
		    if (coin.collided(monkey)) {
		    	coinMusic = new SimpleAudioPlayer("coin.wav", false);
		    	coinMusic.play();
		        coin5List.remove(i);
		        score++;
		    }
		}
	}
	
	/*
	 * Setup any 1D array here! - create the objects that go in them
	 * for (int i = 0; i < row1.length; i++) {
		row1[i] = new MonkeyScrolling(i*150, 100);
		}
	 */
	//traverse the array
	
	public void initializeLives() {
		for (int i = 0; i < 3; i++) {
			//to make it at the top right corner
			int x = Frame.width - (i + 1) * 40 - 15; 
		    this.lives.add(new Life(x, 8));
		}
	}
	
	public void initializeSpider() {
		for (int i = 0; i < row1.length; i++) {
			row1[i] = new SpiderScrolling(i*370, 565);
		}
		
		
		 for (int i = row2.length-1; i >= 0; i--) {
			row2[i] = new SpiderScrolling(i*250, 485);
		}
		 
		 for (int i = row3.length-1; i >= 0; i--) {
			row3[i] = new SpiderScrolling2(i*200, 180);
		}
	}
	
	public void initializeWood() {
		for (int i = 0; i < wood1.length; i++) {
				wood1[i] = new WoodScrolling1(i*300, 400);
		}
		
		for (int i = wood2.length-1; i >= 0; i--) {
			wood2[i] = new WoodScrolling2(i*300, 325);
		}
	}
	
	public void initializeBananaCoin() {
		//FIRST ROW 
		coin1List.add(new BananaCoin((int)(Math.random() * 680 + 30),555));
		
		//SECOND ROW 
		coin1List.add(new BananaCoin((int)(Math.random() * 680 + 30),475));
		
		//THIRD ROW
		for (int i = 0; i < 2; i++) {
			this.coin3List.add(new BananaCoin((int)(Math.random() * 680 + 30), 245));
			//NOT OVERLAP
			for (int j = 0; j < i; j++) {
		        while (Math.abs(coin3List.get(i).getX() - coin3List.get(j).getX()) <= 120) {
		        	coin3List.set(i,new BananaCoin((int)(Math.random() * 680 + 30), 245));
		        }
		    }
		}
		
		//FOURTH ROW 
		for (int i = 0; i < 2; i++) {
			this.coin4List.add(new BananaCoin((int)(Math.random() * 680 + 30), 165));
			//NOT OVERLAP
			for (int j = 0; j < i; j++) {
		        while (Math.abs(coin4List.get(i).getX() - coin4List.get(j).getX()) <= 120) {
		        	coin4List.set(i,new BananaCoin((int)(Math.random() * 680 + 30), 165));
		        }
		    }
		}
		
		//FIFTH ROW
		for (int i = 0; i < 3; i++) {
			this.coin5List.add(new BananaCoin((int)(Math.random() * 680 + 30), 82));
			//NOT OVERLAP
			for (int j = 0; j < i; j++) {
		        while (Math.abs(coin5List.get(i).getX() - coin5List.get(j).getX()) <= 90) {
		        	coin5List.set(i,new BananaCoin((int)(Math.random() * 680 + 30), 82));
		        }
		    }
		}
		
		//SIXTH ROW
		coin1List.add(new BananaCoin((int)(Math.random() * 500 + 30),23));
		
		/* THIS IS FOR ARRAY
		 * 
		 *	for (int i = 0; i < coins.length; i++) {
		 *coins[i] = new BananaCoin((int)(Math.random() * 700 + 30), 100);
		    	
		    //make sure that the next coin and previous coin is far away from each other 
		    for (int j = 0; j < i; j++) {
		        while (Math.abs(coins[i].getX() - coins[j].getX()) <= 90) {
		            coins[i] = new BananaCoin((int)(Math.random() * 700 + 30), 100);
		        }
		    }
		}
		 */
	}
	
	public void resetBananaCoin() {
		for (int i = coin1List.size() - 1; i >= 0; i--) {
		        coin1List.remove(i);
		}
		
		for (int i = coin2List.size() - 1; i >= 0; i--) {
	        coin2List.remove(i);
		}
		
		for (int i = coin3List.size() - 1; i >= 0; i--) {
	        coin3List.remove(i);
		}
		
		for (int i = coin4List.size() - 1; i >= 0; i--) {
	        coin4List.remove(i);
		}
		
		for (int i = coin5List.size() - 1; i >= 0; i--) {
	        coin5List.remove(i);
		}
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
		f.addMouseListener(this);
		f.addKeyListener(this);
	
		//backgroundMusic.play();
		
		initializeLives();
		initializeSpider();
		initializeWood();
		initializeBananaCoin(); 		
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0); 
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent m) {
		
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() == 87) {
			monkey.move(0);
			//movingMusic = new SimpleAudioPlayer("monkey.wav", false);
			//movingMusic.play();
			
			//when it is on the water area 
			//hop with larger distance 
			if (monkey.getY()>250 && monkey.getY()<495-30) {
				monkey.move(4);
			}
		}
		
		else if (arg0.getKeyCode() == 83) {
			monkey.move(1);
			//movingMusic = new SimpleAudioPlayer("monkey.wav", false);
			//ovingMusic.play();
			
			//when the monkey is on the water area 
			//hop with larger distance 
			if (monkey.getY()>250 && monkey.getY()<495-30) {
				monkey.move(5);
			}
		}
		
		else if (arg0.getKeyCode() == 65) {
			monkey.move(2);
			//movingMusic = new SimpleAudioPlayer("monkey.wav", false);
			//movingMusic.play();
		}
		
		//move right
		else if (arg0.getKeyCode() == 68) {
			monkey.move(3);
			//movingMusic = new SimpleAudioPlayer("monkey.wav", false);
			//movingMusic.play();
			System.out.println("Monkey x is: " + monkey.getX() + " Monkey y is: " +monkey.getY());
			}
		
		//Reset game
		else if (arg0.getKeyCode() == 32) {
			if (score == 10) {
				resetGame();
			}
			if (gameOver) {
				resetGame();
			}
		}
		else if (arg0.getKeyCode() == 82) {
			resetGame();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
