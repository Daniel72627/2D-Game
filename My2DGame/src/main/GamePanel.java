package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	public final int originalTileSize = 16;
	public final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 60;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this, keyH);
	TileManager tileM = new TileManager(this);
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 0.0166666 SECONDS BETWEEN FRAMESS \FOR 60
		double nextDrawTime = System.nanoTime() + drawInterval;
		while(gameThread != null) {
		
			
			
			
			
			update();
			
			repaint();
			
			
			
			
			try {
				
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime = nextDrawTime + drawInterval; 
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void update() {
	
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		player.draw(g2);
		
		
		g2.dispose();
		
		
	}
	

}
