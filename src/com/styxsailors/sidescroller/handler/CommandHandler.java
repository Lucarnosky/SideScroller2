package com.styxsailors.sidescroller.handler;

import java.util.ArrayList;

import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.utility.Global;
import com.styxsailors.sidescroller.utility.InGameConsole;

public class CommandHandler {
	
	ArrayList<String> commandsResult = new ArrayList<String>();
	private Global global;
	private InGameConsole console;
	
	public CommandHandler(Global global, Game game, InGameConsole console){
		this.global = global;
		this.console = console;
	}
	
	public void tick(){
		for(int i = 0; i < commandsResult.size(); i++)
			console.add(commandsResult.get(i).toString());
	}
	
	public void addCommand(String command){
		String[] cmd = command.split(" ");
		switch(cmd[0]){
		case("set"):
			switch(cmd[1]){
			case("camx"):
				if(cmd[2] != null)
					global.camX = Integer.parseInt(cmd[2]);
				break;
			case("camy"):
				if(cmd[2] != null)
					global.camY = Integer.parseInt(cmd[2]);
				break;
			case("gravity"):
			if(cmd[2] != null){
				global.GRAVITY = Integer.parseInt(cmd[2]);
				global.playerJump = global.GRAVITY * 2 ;
			}
			break;
			case("player"):
				switch(cmd[2]){
				case("speed"):
					if(cmd[3] != null){
						if(commandsResult.contains("Player Speed: " + global.playerSpeed)){
							commandsResult.remove("Player Speed: " + global.playerSpeed);
							global.playerSpeed = Integer.parseInt(cmd[3]);
							commandsResult.add("Player Speed: " + global.playerSpeed);
						}else{
							global.playerSpeed = Integer.parseInt(cmd[3]);
						}
					}
				break;
				case("jump"):
					if(commandsResult.contains("Player Jump: " + global.playerJump)){
						commandsResult.remove("Player Jump: " + global.playerJump);
						global.playerJump = Integer.parseInt(cmd[3]);
						commandsResult.add("Player Speed: " + global.playerJump);
					}else{
						global.playerJump = Integer.parseInt(cmd[3]);
					}
				}
				break;
				case("jumpheight"):
					if(commandsResult.contains("Player Jump: " + global.playerJump)){
						commandsResult.remove("Player Jump: " + global.playerJump);
						global.playerJumpHeight = Integer.parseInt(cmd[3]);
						commandsResult.add("Player Speed: " + global.playerJump);
					}else{
						global.playerJumpHeight = Integer.parseInt(cmd[3]);
					}
				break;
			case("scale"):
				if(cmd[2] != null)
					global.scale = Integer.parseInt(cmd[2]);
				break;
			}//END SET SWITCH
			break;
		case("show"):
			switch(cmd[1]){
				case("cam"):
					global.printCamBounds = !global.printCamBounds;
				break;
				case("editor"):
					switch(cmd[2]){
					case("support"):
						global.showEditorSupportWindow = !global.showEditorSupportWindow;
						break;
					}
				break;
				case("player"):
					// INSIDE PLAYER OPTIONS
					switch(cmd[2]){
					case("bounds"):
						global.showPlayerBounds = !global.showPlayerBounds;
						break;
					case("speed"):
						if(commandsResult.contains("Player Speed: " + global.playerSpeed))
							commandsResult.remove("Player Speed: " + global.playerSpeed);
						else
							commandsResult.add("Player Speed: "+global.playerSpeed);
						break;
					case("jump"):
						if(commandsResult.contains("Player Jump: " + global.playerJump))
							commandsResult.remove("Player Jump: " + global.playerJump);
						else
							commandsResult.add("Player Jump: "+global.playerJump);
						break;
					default:
						break;
					} // FINISH PLAYER OPTIONS
					break;
				case("hitbox"):
					global.hitboxOn = !global.hitboxOn;
					if(global.hitboxOn)
						commandsResult.add("Hitboxes On");
					else
						commandsResult.remove("Hitboxes On");
					break;
			}//FINISH SHOW OPTIONS
			break;
		case("restart"):
			global.cmdRestart = true;
			break;
		default:
			break;
		}// END MAIN SWITCH
	}
}
