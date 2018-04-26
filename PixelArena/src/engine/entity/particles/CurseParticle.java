package engine.entity.particles;

import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.SpriteSheet;

public class CurseParticle extends Particle{
	
	private AnimatedSprite anim_sprite = new AnimatedSprite(SpriteSheet.curse_projectile_anim, 16, 16, 3);

	public CurseParticle(int posX, int posY, int life) {
		super(posX, posY, life);
	}

	public void update() {
		super.update();
		if (anim_sprite.oncePlayed) {
			remove();
		} else {
			anim_sprite.playOnce();
		}
	}


	public void render(Screen screen) {
		screen.renderSprite(anim_sprite.getSprite(), (int) posX, (int) posY, true);
	}

}
