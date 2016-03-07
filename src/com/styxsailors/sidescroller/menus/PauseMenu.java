package com.styxsailors.sidescroller.menus;

import java.awt.Color;
import java.awt.Graphics2D;

import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.utility.Global;

public class PauseMenu extends Menu{

	public PauseMenu(Global global) {
		super(global);
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics2D g){
		g.setColor(new Color(1,1,1, 0.3f));
		g.fillRect(0,0,Game.WIDTH * Game.SCALE / 3 * 2, Game.HEIGHT * Game.SCALE);
		g.setColor(Color.white);
		//g.drawString("Pause",x,y + previousLine);
	}

}
