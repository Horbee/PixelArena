package engine.entity.gameobject;

import engine.entity.particles.StoneParticle;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class Block extends GameObject {

	public Block(int posX, int posY) {
		super(posX, posY);
		currlife = 100;
		currentSprite = Sprite.block;
	}

	public void damage(int dmg) {
		currlife -= dmg;
	}

	public void update() {
		if (currlife < 50) currentSprite = Sprite.block_cracked;
		if (currlife <= 0) {
			for (int i = 0; i < 10; i++) {
				level.add(new StoneParticle((int) posX, (int) posY, 100, 0x000000));				
			}
			remove();
		}
	}

	public void render(Screen screen) {
		screen.renderObject(this, (int) posX, (int) posY);
	}

	public boolean solid() {
		return true;
	}

}
