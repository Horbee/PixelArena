package engine.entity.projectile;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class FrostProjectile extends Projectile {

	private boolean friendly = false;

	public FrostProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 200;
		speed = 4.5;
		sprite = Sprite.rotate(Sprite.proj_frost, 16, dir, 7, 7);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		move();
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) posX, (int) posY, this);
	}

	protected void move() {
		posX += nx;
		posY += ny;

		if (!friendly) {
			if (level.playerCollision((int) posX, (int) posY)) {
				level.getPlayer().health--;
				level.getPlayer().speed = 0.5;
				remove();
			}
		} else {
			if (level.entitiyCollision(this, (int) posX, (int) posY)) {
				// remove();
				drawDmg = true;
			}
		}

		if (level.gameobjectCollision(this, (int) posX, (int) posY)) {
			// new Spawner((int) posX, (int) posY, Spawner.Type.FIREPARTICLE, 1, level);
			remove();
		}

		if (level.tileCollision((int) (posX + nx), (int) (posY + ny), 14, 15, 15)) {
			// new Spawner((int) posX, (int) posY, Spawner.Type.FIREPARTICLE, 1, level);
			remove();
		}

		if (distance() > range) remove();
	}
	
	
	public void setFriendly(boolean friendly) {
		this.friendly = friendly;
	}
}
