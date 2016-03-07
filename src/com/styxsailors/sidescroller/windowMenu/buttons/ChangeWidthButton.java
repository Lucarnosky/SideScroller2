package com.styxsailors.sidescroller.windowMenu.buttons;

import java.awt.Graphics2D;

import javax.swing.JOptionPane;

import com.styxsailors.sidescroller.gui.GuiButton;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;

public class ChangeWidthButton extends GuiButton{
	
	public ChangeWidthButton(int x, int y, int width, int height,Global global, MouseHandler mouse, String message) {
		super(x, y, width, height,global, mouse, "Change Width");
	}
	
	public void tick(){
		super.tick(x, y);
	}
	
	public void render(Graphics2D g){
		super.render(g);
	}
	
	protected void buttonEffects(){
		int width = Integer.parseInt(JOptionPane.showInputDialog("Tile Width"))* 32;
		global.editor.setWidth(width);
	}

}
