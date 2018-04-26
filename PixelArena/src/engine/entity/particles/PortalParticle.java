package engine.entity.particles;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class PortalParticle extends Particle {

	public PortalParticle(int posX, int posY, int life) {
		super(posX, posY, life);
		sprite = new Sprite(2, 0xffffff);
		this.xa = 2 * rnd.nextDouble() - 1;
		//this.ya = 2 * rnd.nextDouble() - 1;
	}

	public void update() {
		super.update();
		xx += xa;
		//yy += ya * 0.5;
	}

	public void render(Screen screen) {
		screen.renderSprite(sprite, (int) xx, (int) yy, true);
	}
}
