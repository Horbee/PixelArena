package engine.entity.projectile;

import engine.Game;
import engine.entity.Spawner;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class StunProjectile extends Projectile {
	public StunProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 300;
		speed = 3;
		sprite = Sprite.proj_stun;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		time++;

		if (!drawDmg) {
			move();
			sprite = Sprite.rotate(Sprite.proj_stun, 16, (time / 5), 7, 7);
		} else {
			posY -= 0.5;
			if (time % 60 == 0) remove();
		}
	}

	public void render(Screen screen) {
		if (!drawDmg) screen.renderProjectile((int) posX, (int) posY, this);
		else Game.font.render((int) posX, (int) posY, "STUNNED!", 0xffff0000, true, screen);
	}

	protected void move() {
		posX += nx;
		posY += ny;

		if (level.playerCollision((int) posX, (int) posY)) {
			level.getPlayer().stunned = true;
			drawDmg = true;
		}

		if (level.gameobjectCollision(this, (int) posX, (int) posY)) {
			new Spawner((int) posX, (int) posY, Spawner.Type.STONEPARTICLE, 5, level);
			remove();
		}

		if (level.tileCollision((int) (posX + nx), (int) (posY + ny), 14, 15, 15)) {
			new Spawner((int) posX, (int) posY, Spawner.Type.STONEPARTICLE, 5, level);
			remove();
		}

		if (distance() > range) remove();
	}
}
