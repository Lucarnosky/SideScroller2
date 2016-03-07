package com.styxsailors.sidescroller.windowMenu.buttons;

import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import com.styxsailors.sidescroller.gui.GuiButton;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;

public class SaveButton extends GuiButton{

	public SaveButton(int x, int y, int width, int height,Global global, MouseHandler mouse, String message) {
		super(x, y, width, height,global, mouse, "Save");
	}
	
	public void tick(){
		super.tick(x, y);
	}
	
	public void render(Graphics2D g){
		super.render(g);
	}
	
	protected void buttonEffects(){
		String levelName = JOptionPane.showInputDialog("Current Level Name");
		global.editor.exportLevel(levelName.toLowerCase());
	}

}
