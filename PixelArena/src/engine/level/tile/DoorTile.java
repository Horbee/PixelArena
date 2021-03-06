package engine.level.tile;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class DoorTile extends Tile {

	public DoorTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * 32, y * 32, this);
	}
	
	public boolean solid() {
		return true;
	}
}
