package engine.entity.particles;

import engine.entity.Entity;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class Particle extends Entity {
	
	protected Sprite sprite;
	
	protected int life;
	protected int time = 0;
	
	protected double xx, yy, zz, xa, ya, za;
	
	public Particle(int posX, int posY, int life) {
		this.posX = posX;
		this.posY = posY;
		this.xx = posX;
		this.yy = posY;
		this.life = life + rnd.nextInt(20);
	
	}
	
	
	public void update() {
		time++;
		if (time > 7500) time = 0;
		if (time >= life) {
			remove();
		}
		
	}
	
	
	public void render(Screen screen) {
		screen.renderSprite(sprite, (int) xx, (int)yy - (int)zz, true);
	}
	
}
