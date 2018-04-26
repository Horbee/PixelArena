package engine.level.tile;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class VoidTile extends Tile {

	
	
	public VoidTile(Sprite sprite) {
		super(sprite);
		solid = true;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * 32, y * 32, this);
	}
}
