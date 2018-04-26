package engine.entity.projectile;

import engine.Game;
import engine.entity.Spawner;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class EnemyProjectile extends Projectile {

	public EnemyProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 200;
		speed = 4.5;
		sprite = Sprite.proj_fire;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (!drawDmg) move();
		else {
			time++;
			posY--;
			if (time % 45 == 0) remove();
		}
	}

	public void render(Screen screen) {
		if (!drawDmg) screen.renderProjectile((int) posX, (int) posY, this);
		else Game.font.render((int) posX, (int) posY, "-" + Integer.toString(dmgDone), 0xffff0000, true, screen);
	}

	protected void move() {
		posX += nx;
		posY += ny;

		if (level.playerCollision((int) posX, (int) posY)) {
			level.getPlayer().health--;
			drawDmg = true;
			setDmgDone(1);
			//remove();
		}

		if (level.gameobjectCollision(this, (int) posX, (int) posY)) {
			new Spawner((int) posX, (int) posY, Spawner.Type.FIREPARTICLE, 1, level);
			remove();
		}

		if (level.tileCollision((int) (posX + nx), (int) (posY + ny), 14, 15, 15)) {
			new Spawner((int) posX, (int) posY, Spawner.Type.FIREPARTICLE, 1, level);
			remove();
		}

		if (distance() > range) remove();
	}
}
