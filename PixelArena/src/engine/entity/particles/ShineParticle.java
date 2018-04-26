package engine.entity.particles;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class ShineParticle extends Particle {

	public ShineParticle(int posX, int posY, int life) {
		super(posX + rnd.nextInt(32), posY + 10, life);
		sprite = Sprite.particle_shine;
		//if (rnd.nextInt(10) > 5) sprite = Sprite.particle1;
		//else sprite = Sprite.particle2;

		this.xa = rnd.nextGaussian() * 0.1;
		this.ya = rnd.nextFloat() * - 2 ;
	}
	
	public void update() {
		super.update();
		xx += xa;
		yy += ya * 0.5;
	}
		
	public void render(Screen screen) {
		screen.renderSprite(sprite, (int) xx, (int)yy, true);
	}
	
}
