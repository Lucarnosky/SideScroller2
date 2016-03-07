package com.styxsailors.sidescroller.main;

import java.awt.Color;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;

import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.image.BufferStrategy;

import com.styxsailors.sidescroller.handler.Handler;
import com.styxsailors.sidescroller.handler.InputHandler;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.level.LevelEditor;
import com.styxsailors.sidescroller.menus.Menu;
import com.styxsailors.sidescroller.utility.Global;
import com.styxsailors.sidescroller.utility.Global.GameState;
import com.styxsailors.sidescroller.menus.MainMenu;
import com.styxsailors.sidescroller.utility.InGameConsole;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "Side Scroller";
	public static final int HEIGHT = 120;
	public static final int WIDTH = 160;
	public static final int SCALE = 1;
	private InputHandler input = new InputHandler(this);
	private boolean running = false;
	private Handler handler;
	private Global global = new Global();
	private Menu menu ;
	private LevelEditor editor;
	private MouseHandler mouse = new MouseHandler(this);
	static JFrame frame;
	private Font defaultFont = new Font("TimesRoman", Font.PLAIN, 15);
	private String pauseString = "GAME PAUSED";
	
	public void setMenu(Menu menu) {
		this.menu = menu;
		if(this.menu != null)
			this.menu.init();
	}

	public void start() {
		global.cameraEnabled = true;
		running = true;
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}
	
	private void init() {
		global = new Global();
		global.input = new InputHandler(this);
		handler = new Handler(global);
		global.gameState = GameState.PLAY;
		global.GWIDTH = WIDTH;
		global.GHEIGHT = HEIGHT;
		global.GSCALE = SCALE;
		global.console = new InGameConsole(global);
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		init();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}
	

	public void tick() {
		//Fase di aggiornamento
		global.console.update();
		handler.tick();
		
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.scale(global.scale, global.scale);
		g.setColor(Color.blue);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(defaultFont);
		g.translate(-global.camX, -global.camY);
		
		
		///AGGIORNARE QUI
		handler.render(g);
		
		global.console.render(g);
		///FINE AGGIORNAMENTO
		
		
		
		if(!hasFocus() || global.gameState == GameState.PAUSE){
			g.setColor(new Color(1,1,1, 0.3f));
			g.fillRect(-global.camX,-global.camY, WIDTH * SCALE + 10 , HEIGHT * SCALE + 10);
			g.setColor(Color.white);
			g.drawString(pauseString, -global.camX + WIDTH * SCALE / 2 - pauseString.length() / 2, - global.camY + HEIGHT * SCALE / 2);
		}
		
		g.dispose();
		
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		//frame.setLocationRelativeTo(null);

		game.start();
		frame.setVisible(true);
	}
	
	public InputHandler getInput(){
		return input;
	}
	
	public Global getGlobals(){
		return global;
	}
	
}