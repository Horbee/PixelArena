package engine.entity.particles;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class StoneParticle extends Particle {

	public StoneParticle(int posX, int posY, int life) {
		super(posX, posY, life);
		sprite = Sprite.particle_normal;

		this.xa = rnd.nextGaussian();
		this.ya = rnd.nextGaussian();
		this.zz = rnd.nextFloat() + 2.0;
	}
	
	public StoneParticle(int posX, int posY, int life, int color) {
		super(posX, posY, life);
		sprite = new Sprite(3, color);

		this.xa = rnd.nextGaussian();
		this.ya = rnd.nextGaussian();
		this.zz = rnd.nextFloat() + 2.0;
	}
	
	public void update() {
		super.update();
		
		za -= 0.1;
		if (zz < 0) {
			zz = 0;
			za *= -0.6;
			xa *= 0.4;
			ya *= 0.4;
		}
		
		move(xx + xa, (yy+ya) + (zz+za));
	}
	
	private void move(double x, double y) {
		if (Collision(x, y)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		xx += xa;
		yy += ya;
		zz += za;
	}
	
	public boolean Collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 32) / 32;
			double yt = (y - c / 2 * 32) / 32;
			int ix = (int)Math.ceil(xt);
			int iy = (int)Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt); 
			if (c / 2 == 0) iy = (int) Math.floor(yt); 
			if (level.getTile(ix, iy).solid()) solid = true; 
		}
		return solid;
	}
	
	public void render(Screen screen) {
		screen.renderSprite(sprite, (int) xx, (int)yy - (int)zz, true);
	}
}
