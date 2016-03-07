package com.styxsailors.sidescroller.utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.styxsailors.sidescroller.handler.CommandHandler;
import com.styxsailors.sidescroller.handler.InputHandler;
import com.styxsailors.sidescroller.main.Game;

public class InGameConsole {
	
	private ArrayList<String> messages = new ArrayList<String>();
	private int previousLine = 10;
	private InputHandler input;
	private boolean enabled = true;
	private Global global;
	private int x, y;
	
	public InGameConsole(Global global){
		this.global = global;
		this.input = global.input;
	}
	
	public void update(){
		x = global.camX;
		y = global.camY;
		if(input.console.down){
			input.releseKey(input.console);
			enabled = !enabled;
		}
		if(enabled){
			add("//DEBUG CONSOLE//");
			add("//Press H for insert a command//");
		}
		messages.clear();
	}
	
	public void render(Graphics g){
		if(enabled){
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(1,1,1, 0.3f));
			g2d.fillRect(x,y,Game.WIDTH * Game.SCALE / 3 * 2, Game.HEIGHT * Game.SCALE);
			g2d.setColor(Color.white);
			previousLine = 10;
			if(messages.size() > 0)
			g2d.drawString(messages.get(0),x,y + previousLine);
			g2d.scale(1, 1);
			for(int i = 1; i < messages.size(); i++){
				previousLine += 10;
				g2d.drawString(messages.get(i), x,y + previousLine);
				
			}
			
		}
	}
	
	public void add(String message){
		messages.add(message);
	}

}
