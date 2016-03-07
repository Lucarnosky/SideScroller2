package com.styxsailors.sidescroller.windowMenu.buttons;

import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import com.styxsailors.sidescroller.gui.GuiButton;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;

public class ChangeHeightButton extends GuiButton{
	
	public ChangeHeightButton(int x, int y, int width, int height,Global global, MouseHandler mouse, String message) {
		super(x, y, width, height,global, mouse, "Change Height");
	}
	
	public void tick(){
		super.tick(x, y);
	}
	
	public void render(Graphics2D g){
		super.render(g);
	}
	
	protected void buttonEffects(){
		int height = Integer.parseInt(JOptionPane.showInputDialog("Grid Height in Tile"))* 32;
		global.editor.setHeight(height);
	}


}
