package engine.entity.particles;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class PowerupParticle extends Particle {

	public PowerupParticle(int posX, int posY, int life, int color) {
		super(posX + rnd.nextInt(32), posY + 10, life);
		sprite = new Sprite(3, color);
		//if (rnd.nextInt(10) > 5) sprite = Sprite.particle1;
		//else sprite = Sprite.particle2;

		this.xa = rnd.nextGaussian() * 0.3;
		this.ya = rnd.nextFloat() * - 3 ;
	}
	
	public void update() {
		super.update();
		xx += xa;
		yy += ya * 0.8;
	}
		
	public void render(Screen screen) {
			screen.renderSprite(sprite, (int) xx, (int)yy, true);

	}
	
}
