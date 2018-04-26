package engine.UI;

import java.awt.Rectangle;

import engine.Game;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.input.Mouse;

public class Button {

	public int x, y, width, height;
	public Rectangle rect;
	public boolean clickedOn = false;
	public boolean on = false;
	public boolean isMouseOn = false;

	private int scale;
	private Sprite spriteON;
	private Sprite spriteOFF;
	private Sprite spriteMoved;
	private Sprite sprite;
	private boolean hotKeyPressed = false;

	public Button(int x, int y, Sprite spriteON, Sprite spriteOFF, Sprite spriteMoved) {
		this.x = x;
		this.y = y;
		this.scale = Game.getScale();
		this.sprite = spriteON;
		this.spriteON = spriteON;
		this.spriteOFF = spriteOFF;
		if (spriteMoved == null) {
			this.spriteMoved = spriteOFF;
		} else {
			this.spriteMoved = spriteMoved;
		}
		this.width = sprite.width * scale;
		this.height = sprite.height * scale;
		
		rect = new Rectangle(this.x, this.y, this.width, this.height);
	}

	public void setHotkey(boolean hotKeyPressed) {
		this.hotKeyPressed = hotKeyPressed;
	}

	public void clickedOn() {
		clickedOn = true;
	}

	private boolean movedOn() {
		if ((Mouse.getMouseX()) > this.x && (Mouse.getMouseX()) < (this.x + this.width) && (Mouse.getMouseY()) > this.y && (Mouse.getMouseY()) < (this.y + this.height)) {
			return true;
		}
		return false;
	}

	public void update() {
		if (on) {
			sprite = spriteON;
		} else if (!on) {
			sprite = spriteOFF;
		}

		if (hotKeyPressed) function();
		if (movedOn()) {
			if (Mouse.mouseClicked(Mouse.LEFT_BUTTON)) {
				function();
			}
			if (!on) sprite = spriteMoved;
			isMouseOn = true;
		}else{
			if (isMouseOn) isMouseOn = false;			
		}

	}
	
	private void function() {
		//Game.audio.sfx_click.Play();
		on = !on;
	}


	public void render(Screen screen) {
		screen.renderSprite(sprite, x / scale, y / scale, false, false);
	}

}
