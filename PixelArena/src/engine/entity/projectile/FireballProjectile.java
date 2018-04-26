package engine.entity.projectile;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class FireballProjectile extends Projectile {

	public FireballProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 200;
		speed = 4.5;
		sprite = Sprite.rotate(Sprite.proj_fireball, 16, dir, 7, 7);
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

		if (level.entitiyCollision(this, (int) posX, (int) posY)) {
			remove();
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

	public int getDamage() {
		return 30;
	}
}
