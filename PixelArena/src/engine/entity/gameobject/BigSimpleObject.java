package engine.entity.gameobject;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class BigSimpleObject extends GameObject {

	public BigSimpleObject(Sprite sprite) {
		currentSprite = sprite;
	}
	
	public BigSimpleObject(Sprite sprite, int posX, int posY) {
		currentSprite = sprite;
		this.posX = posX;
		this.posY = posY;
	}


	public void update() {

	}

	public void render(int posX, int posY, Screen screen) {
		screen.renderObject(this, posX * 32, posY * 32 - 32);
	}
	
	public void render(Screen screen) {
		screen.renderObject(this, (int) posX, (int) posY);
	}

}
