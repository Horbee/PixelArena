package engine.level.tile;

import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;

public class AnimatedTile extends Tile {

	private AnimatedSprite animSprite;

	public AnimatedTile(AnimatedSprite animSprite) {
		super(animSprite);
		this.animSprite = animSprite;
	}

	public void update() {
		animSprite.update();
	}

	public void render(int x, int y, Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderTile(x * 32, y * 32, this);
	}
	
	public boolean solid() {
		return true;
	}
}
