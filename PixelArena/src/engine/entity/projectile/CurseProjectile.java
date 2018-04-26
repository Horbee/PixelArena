package engine.entity.projectile;

import engine.Game;
import engine.entity.Spawner;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class CurseProjectile extends Projectile {

	private boolean cursing = false;
	private int time = 0;

	public CurseProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 250;
		speed = 4.5;
		sprite = Sprite.rotate(Sprite.proj_curse, 16, dir, 7, 7);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (cursing) {
			time++;
			if (time % 60 == 0) level.getPlayer().health -= 15;
			if (time % 120 == 0) {
				level.getPlayer().cursed = false;
				remove();
			}
			
		} else move();
	}

	public void render(Screen screen) {
		if (!cursing) screen.renderProjectile((int) posX, (int) posY, this);
		else Game.font.render((int) posX, (int) posY, "CURSED!", 0xff5c0f5d, true, screen);
		//todo: if cursing: animation
	}

	protected void move() {
		posX += nx;
		posY += ny;

		if (level.playerCollision((int) posX, (int) posY)) {
			// doing curse
			cursing = true;
			level.getPlayer().cursed = true;
			new Spawner((int) posX, (int) posY, Spawner.Type.CURSEPARTICLE, 1, level);
			// level.getPlayer().health--;
			// remove();
		}

		if (level.gameobjectCollision(this, (int) posX, (int) posY)) {
			// new Spawner((int) posX, (int) posY, Spawner.Type.FIREPARTICLE, 1, level);
			remove();
		}

		if (level.tileCollision((int) (posX + nx), (int) (posY + ny), 14, 15, 15)) {
			new Spawner((int) posX, (int) posY, Spawner.Type.CURSEPARTICLE, 1, level);
			remove();
		}

		if (distance() > range) remove();
	}
}
