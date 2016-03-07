package com.styxsailors.sidescroller.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;

public class GuiButton extends GuiItem{

	MouseHandler mouse;
	boolean selected = false;
	protected String message = "";
	
	public GuiButton(int x, int y, int width, int height,Global global,MouseHandler mouse, String message) {
		super(x, y, width, height, global);
		this.mouse = mouse;
		this.message = message;
	}

	public void tick(int x, int y){
		super.tick(x, y);
		if( mouse.x > x && mouse.x < x + width && mouse.y > y - 12 && mouse.y < y - 12 + height){
			if(mouse.left.down){
				mouse.releaseButton(mouse.left);
				buttonEffects();
			}
			
		}
	}
	
	public void render(Graphics2D g){
		
		g.setColor(Color.green);
		g.drawRect(x, y-12, width, height);
		if(selected){
			g.setColor(Color.cyan);
			g.fillRect(x, y - 12, width, height);
			g.setColor(Color.orange);
			g.drawString(message, x, y);
		}else{
			g.setColor(Color.white);
			g.fillRect(x, y - 12, width, height);
			g.setColor(Color.black);
			g.drawString(message, x, y);
		}
	}
	
	protected void buttonEffects(){
		System.out.println("Should save");
	}

}
