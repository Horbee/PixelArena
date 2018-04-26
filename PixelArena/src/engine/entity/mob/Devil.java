package engine.entity.mob;

import engine.Game;
import engine.entity.Spawner;
import engine.entity.gameobject.Portal;
import engine.entity.projectile.CurseProjectile;
import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.SpriteSheet;

public class Devil extends Mob {

	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.devil_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.devil_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.devil_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.devil_down, 32, 32, 3);

	private boolean walking = false;
	private int anim = 0;
	private int num_portals = 0;

	int time = 0;

	public Devil(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		health = 500 * Game.difficulty;
		speed = 2.0;
		animSprite = down;
	}

	public void update() {
		time++;

		if (!isDead) {

			if (walking) animSprite.update();
			else animSprite.setFrame(1);

			if (time % 60 == 0) {
				if (getPlayerDistance() < 100) {
					// todo: curse image, firerate
					curseSkill();
				}
			}

			spawnEnemy();
			shootPlayer(100);
			moveRandomly(time);

			if (anim < 7500) anim++;
			else anim = 0;

			if (ya < 0) {
				dir = 0;
				animSprite = up;
			} else if (ya > 0) {
				dir = 2;
				animSprite = down;
			}
			if (xa < 0) {
				dir = 3;
				animSprite = left;
			} else if (xa > 0) {
				dir = 1;
				animSprite = right;
			}

			if (xa != 0 || ya != 0) {
				move(xa, ya);
				walking = true;
			} else {
				walking = false;
				// animSprite = idle;
			}
			

			if (health < 0) {
				isDead = true;
			}
		}

		if (isDead) {
			new Spawner((int) posX + 16, (int) posY + 16, Spawner.Type.EMERALDPARTICLE, 50, level);
			remove();
		}

		sprite = animSprite.getSprite();
	}

	private void spawnEnemy() {
		if (time % 600 == 0) {
			if (level.entities.size() < 4 && num_portals < 4) {
				// todo: spawning anim
				level.add(new Portal((int) posX, (int) posY, 1));
				num_portals++;
			}
		}
		// Check every 1 min if we need more enemy
		if (time % 3600 == 0) {
			if (level.entities.size() < 4) {
				num_portals = 0;
			}
		}
	}

	private void curseSkill() {
		double dx = level.getPlayer().getPosX() - posX;
		double dy = level.getPlayer().getPosY() - posY;
		double dir = Math.atan2(dy, dx);
		level.add(new CurseProjectile(posX, posY + 10, dir));
	}

	public void render(Screen screen) {
		screen.renderCharacter((int) posX, (int) posY, this);
	}

}
