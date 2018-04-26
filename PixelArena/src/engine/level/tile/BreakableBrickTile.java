package engine.level.tile;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class BreakableBrickTile extends Tile {
	
	public int life = 60;
	
	public BreakableBrickTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		if (life > 0){
			screen.renderTile(x * 32, y * 32, this);
		}
	}
	
	public boolean solid() {
		return true;
	}
	
	public boolean breakable(){
		return true;
	}
}
