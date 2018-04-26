package engine.entity.gameobject;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class SimpleObject extends GameObject {

	public SimpleObject(Sprite sprite) {
		currentSprite = sprite;
	}

	public void update() {

	}

	public void render(int posX, int posY, Screen screen) {
		screen.renderObject(this, posX * 32, posY * 32);
	}

}
