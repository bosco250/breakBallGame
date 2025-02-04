import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
	private boolean play=false;
	private int score=0;
	private int totalBreaks=21;
	private int delay=5;
	private Timer timer;
	private int playerX=310;
	
	private int ballposX=120;
	private int ballposY=350;
	private int ballXdir=-1;
	private int ballYdir=-2;
	private MapGenerator map;
	
	public GamePlay() {
		map=new MapGenerator(7,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer=new Timer(delay, this);
		timer.start();
		
	}
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1,692,592);
		
		//drawing map
		map.draw((Graphics2D)g);
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Score: "+score, 570,30);
		
		
		//border
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//at the end of game
		if(ballposY>570) {
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Game Over!!  Score: "+score,190,300);
			
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("Press Enter TO Restart", 190, 450);
		}
		
		
		g.dispose();
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir=-ballYdir;
			}
			
		 A:for(int i=0;i<map.map.length;i++) {
				for (int j=0;j<map.map.length;j++) {
					if (map.map[i][j]>0) {
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0,i, j);
							totalBreaks--;
							score+=5;
							if(ballposX+19<=brickRect.x||ballposY+1>=brickRect.x+brickWidth) {
								ballXdir=-ballXdir;
							}else {
								ballYdir=-ballYdir;
							}
							break A;
						}
								
					}
					
				}
			}
			
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0) {
				ballXdir=-ballXdir;
			}
			if(ballposY<0) {
				ballYdir=-ballYdir;
			}
			if(ballposX>670) {
				ballXdir=-ballXdir;
			}
			
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			if(!play) {
				play=true;
				ballposX=120;
				ballposY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=310;
				score=0;
				totalBreaks=21;
				map=new MapGenerator(7,7);
				repaint();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(playerX>=600) {
				playerX=600;
			}else {
				moveRight();
			}
			
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			if(playerX<10) {
				playerX=10;
			}else {
				moveLeft();
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

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
