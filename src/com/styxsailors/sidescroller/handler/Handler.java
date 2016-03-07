package com.styxsailors.sidescroller.handler;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.styxsailors.sidescroller.mobs.BasicEntity;
import com.styxsailors.sidescroller.mobs.Enemy;
import com.styxsailors.sidescroller.mobs.GameObj;
import com.styxsailors.sidescroller.mobs.Player;
import com.styxsailors.sidescroller.objects.Wall;
import com.styxsailors.sidescroller.utility.Global;
import com.styxsailors.sidescroller.utility.InGameConsole;

public class Handler {
	
	ArrayList<BasicEntity> levelEntities = new ArrayList<BasicEntity>();
	Player player;
	InGameConsole console;
	Global global;
	InputHandler input;
	public String levelName;
	ArrayList<GameObj> gameObjects;
	
	public Handler(InGameConsole console, Global global, InputHandler input){
		this.console = console;
		this.global = global;
		this.input = input;
		
	}
	
	public Handler(Global global) {
		this.global = global;
		init();
	}

	public void init(){
		
		loadObjects();
		
	}
	
	public void loadObjects(){
		System.out.println("Carico Obj");
		player = new Player(64,60,32,32,global);
		gameObjects = new ArrayList<GameObj>();
		for(int i = 0; i < 640; i+=32){
			gameObjects.add(new Wall(i,450,32,32));
		}
		for (int j = 1; j < 5 ; j ++){
			for(int i = 0; i < 20; i++){
				if(i == 0 || i == 19)
					gameObjects.add(new Wall(i * 32,450 - j*32,32,32));
			}
		}
		gameObjects.add(new Enemy(96,60,32,32));
		gameObjects.add(player);
		System.out.println("Setto global");
		for(int i = 0; i < gameObjects.size(); i ++)
			gameObjects.get(i).global = global;
		global.allEntities = gameObjects;
		
	}
	
	public void tick(){
		if(global.input.restart.down){
			player.x = player.initialX;
			player.y = player.initialY;
		}
		updateEntities();
	}
	
	public void updateEntities(){
		for(int i = 0; i < gameObjects.size();i++){
			GameObj gameObj = gameObjects.get(i);
			//Controllo se devo rimuovere l'entità
			if(gameObj.remove){
				gameObjects.remove(i);
				global.allEntities.remove(i);
				continue;
			}
			gameObj.active = isInsideViewPort(gameObj);
			if(gameObj.active){
				gameObj.update();
			}
		}
	}
	
	public void render(Graphics2D g){
		for(int i = 0; i < gameObjects.size(); i++){
			GameObj obj = gameObjects.get(i);
			if(obj.active){
				obj.render(g);
			}
		}
		
	}
	
	public boolean isInsideViewPort(GameObj object){
		if(object.isNullObject)
			return true;
		else
			return (object.x > global.camX - 128 && object.x + object.width < global.camX + 128 + global.GWIDTH * global.GSCALE );
	}

	
}
