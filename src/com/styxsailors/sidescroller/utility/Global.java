package com.styxsailors.sidescroller.utility;

import java.util.ArrayList;

import com.styxsailors.sidescroller.handler.InputHandler;
import com.styxsailors.sidescroller.level.LevelEditor;
import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.mobs.GameObj;

public class Global {
	
	// Generals
	public int GRAVITY = 5;
	public boolean isPaused = false;
	public boolean cmdRestart = false;
	public double scale = 1;
	public String firstLevel="testlevel";
	public int GWIDTH,GHEIGHT,GSCALE;
	public ArrayList<GameObj> allEntities;
	public InGameConsole console;
	public InputHandler input;
	public enum GameState{
		PLAY,PAUSE
	}
	public GameState gameState;
	
	// Console
	public boolean hitboxOn = false;
	public boolean showPlayerBounds = false; 
	public boolean printCamBounds = false;
	
	// Player
	public int playerSpeed = 3;
	public int playerJump = GRAVITY * 2;
	public int playerJumpHeight = 128;
	public int tileToJump = 128;
	public int initialPlayerX = 0;
	public int initialPlayerY = -64;
	
	// Camera
	public boolean cameraEnabled = false;
	public int initialCamX = (-initialPlayerX + Game.WIDTH / 3)* Game.SCALE , initialCamY = (-initialPlayerY + Game.HEIGHT / 3)* Game.SCALE ;
	public int camX = initialCamX, camY = initialCamY;
	
	// Editor
	public LevelEditor editor;
	public boolean showEditorSupportWindow = true;
	
}
