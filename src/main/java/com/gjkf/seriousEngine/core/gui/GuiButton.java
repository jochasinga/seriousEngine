/**
 * Created by Davide Cossu (gjkf), 6/10/2016
 */
package com.gjkf.seriousEngine.core.gui;

import com.gjkf.seriousEngine.core.controls.MouseInput;
import com.gjkf.seriousEngine.core.render.Renderer;
import org.lwjgl.stb.STBEasyFont;

/**
 * 	Class representing a button.
 * 	Pass the callback function as a {@code Lambda} expression.
 *
 * 	@see MouseListener#mouseClicked()
 *	@see MouseInput#initMouseButton()
 */

public class GuiButton extends GuiWidget{

	private String text;
	private float scale;

	public GuiButton(int x, int y, String text, float scale, MouseListener listener){
		super(x, y, (int)(STBEasyFont.stb_easy_font_width(text)*scale), (int)(12*scale), listener);
		this.text = text;
		this.scale = scale;
	}

	@Override
	public void draw(){
		super.draw();
		//Renderer.drawRect(x, y, width, height, new Color3f(1f, 0f, 1f));
		if(pointInside(MouseInput.getMouseX(), MouseInput.getMouseY())){
			Renderer.drawLine(x-15, y-5, x-5, y+height/2, Colors.WHITE.color);
			Renderer.drawLine(x-15, y+height+5, x-5, y+height/2, Colors.WHITE.color);
			Renderer.drawLine(x+width+15, y-5, x+width+6, y+height/2, Colors.WHITE.color);
			Renderer.drawLine(x+width+15, y+height+5, x+width+6, y+height/2, Colors.WHITE.color);
		}
		Renderer.renderFont(x, y+height/4, Colors.WHITE.color, this.scale, this.text);
	}

	@Override
	public void update(){
		super.update();
	}

	@Override
	public void mouseClicked(){
		super.mouseClicked();
	}
}