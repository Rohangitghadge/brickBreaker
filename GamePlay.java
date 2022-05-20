package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener,ActionListener{
	private boolean play=false;
	private int totalBricks=21;
	private Timer timer;
	private int delay=10;
	private int playerX=310;//paddle position
	private int ballPosX=120;
	private int ballPosY=350;
	private int ballXdir=-1;
	private int ballYdir=-2;
	private int score=0;
	private Mapgenerator map;
	public GamePlay() {
		map=new Mapgenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		//set the timer
		timer=new Timer(delay,this);
		timer.start();
		
	}
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);//(x,y,width,height)
		
		//draw the bricks;
		map.draw((Graphics2D)g);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score,590,30);
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(681,0,3,592);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX,500,100,8);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX,ballPosY,20,20);
		//win game then restart again condition
		if(totalBricks<=0) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("You Won :",260,300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to Restart:",230,350);
		}
		//loss game restart again condition
		if(ballPosY>570) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over :",190,300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press ENTER to Restart:",230,350);
		}
		g.dispose();
		
		
		
	}
	@Override
	//this method will automatically call 
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		//code for ball
		if(play) {
			//create rectangle around ball and paddle
			
			//rectangle around ball
			Rectangle ballRect=new Rectangle(ballPosX,ballPosY,20,20);
			
			//rectangle around paddle
			Rectangle paddleRect=new Rectangle(playerX,500,100,8);
			
			//check for intersection
			if(ballRect.intersects(paddleRect)) {
				ballYdir=-ballYdir;//then ball go in opposite direction
			}
			for(int i=0;i<map.map.length;i++) { //1st map-variable
				//2nd one->matrix;
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickWidth+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						//rectangle around bricks
						Rectangle brickRect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
						
						if(ballRect.intersects(brickRect)) {
							map.setBricksValue(0,i,j);
							totalBricks--;
							score+=5;
							
							if(ballPosX+19<=brickRect.x || ballPosX+1>=brickRect.x+brickRect.width) {
								ballXdir=-ballXdir;
							}
							else {
								ballYdir=-ballYdir;
							}
						}
					}
				}
			}
			ballPosX+=ballXdir;
			ballPosY+=ballYdir;
			if(ballPosX<0) {
				ballXdir=-ballXdir;
			}
			if(ballPosY<0) {
				ballYdir=-ballYdir;
			}
			if(ballPosX>670) {
				ballXdir=-ballXdir;
			}
		}
		//to re-Draw the paddle
		//because it calls once 
		//we need to call it again 
		//means re-Draw it again
		 repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		 if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			 if(playerX>=600) {
				 playerX=600;
			 }
			 else {
				  moveRight();
			 }
		 }
		 if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			 if(playerX<=10) {
				 playerX=10;
			 }
			 else {
				  moveLeft();
			 }
		 }
		 if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			 if(!play) {
				 play=true;
				 ballPosX=120;
				 ballPosY=350;
				 ballXdir=-1;
				 ballYdir=-2;
				 playerX=310;
				 score=0;
				 totalBricks=21;
				 map=new Mapgenerator(3,7);
				 
				 repaint();
			 }
		 }
		
	}
	public void moveRight() {
		play=true;
		playerX+=20;
	}
	public void moveLeft() {
		play=true;	 
		playerX-=20;
	}

	 
	
}
