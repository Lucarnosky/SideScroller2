package com.styxsailors.sidescroller.menus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.styxsailors.sidescroller.handler.InputHandler;
import com.styxsailors.sidescroller.level.LevelEditor;
import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.utility.Global;

public class MainMenu extends Menu{
	
	ArrayList <String> options = new ArrayList<String>();
	InputHandler input;
	Game game;
	private int select = 0;
	
	public MainMenu(InputHandler input,Game game,Global global) {
		super(global);
		this.input = input;
		this.game = game;
	}
	
	public void init(){
		instantiateOptions();
	}
	
	public void tick(){
		
		if(input.down.down){
			if(select  < options.size() - 1)
				select ++;
			input.releseKey(input.down);
		}
		if(input.up.down )
			if(select > 0)
				select --;
			input.releseKey(input.up);
			
		if(input.attack.down){
			switch(select){
				case 0:
					game.setMenu(null);
					//game.StartGame(null);
					break;
				case 1:
					//game.setEditor(new LevelEditor(null, game));
					game.setMenu(null);
					break;
				case 2:
					String levelName = JOptionPane.showInputDialog("Level name");
					//game.setEditor(new LevelEditor(levelName,game));
					game.setMenu(null);
					break;
				case 3:
					String levelName1 = JOptionPane.showInputDialog("Level name");
					game.setMenu(null);
					//game.StartGame(levelName1);
					break;
				case 4:
					System.exit(1);
					break;
			}
		}
	}
	
	public void render(Graphics2D g){
		
		g.setColor(Color.white);
		for(int i = 0; i < options.size(); i++){
			if(select == i)
				g.drawString(">" +options.get(i) + "<", Game.WIDTH * Game.SCALE /2 - options.get(i).length()/2, (i + 1) * 50 );
			else
				g.drawString(options.get(i), Game.WIDTH * Game.SCALE /2 - options.get(i).length()/2, (i + 1) * 50 );
		}
	}
	
	public void instantiateOptions(){
		options.add("New Game");
		options.add("Level New Editor");
		options.add("Edit Existing Level");
		options.add("Test a level");
		options.add("Exit");
	}

}
