package engine.entity.gameobject;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class Candle extends GameObject{

	public Candle(int x, int y) {
		this.posX = x;
		this.posY = y;
		currentSprite = Sprite.candle_cata;
	}
	
	public void update() {
	}
	
	public void render(Screen screen) {
		screen.renderObject(this, (int) posX, (int) posY);
	}
	
}
