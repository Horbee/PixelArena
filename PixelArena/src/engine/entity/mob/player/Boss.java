package engine.entity.mob.player;

import java.awt.Color;
import java.util.List;

import engine.Game;
import engine.entity.Spawner;
import engine.entity.gameobject.Cave;
import engine.entity.gameobject.Chest;
import engine.entity.gameobject.MinorHealth;
import engine.entity.gameobject.Portal;
import engine.entity.mob.Mob;
import engine.entity.projectile.FireBombProjectile;
import engine.entity.projectile.StunProjectile;
import engine.graphics.AnimatedSprite;
import engine.graphics.MyFont;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;
import engine.level.Level;
import engine.level.Node;
import engine.util.Util;
import engine.util.Vector2i;

public class Boss extends Mob {

	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.boss_up, 64, 64, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.boss_left, 64, 64, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.boss_right, 64, 64, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.boss_down, 64, 64, 3);

	private boolean walking = false;
	private int num_portals;
	private int firetime = 60;
	private List<Node> path = null;

	// private int anim = 0;

	int time = 0;

	public Boss(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		health = 1000 * Game.difficulty;
		animSprite = down;
	}

	public void update() {
		time++;

		if (!isDead) {

			if (walking) animSprite.update();
			else animSprite.setFrame(1);

			// 4 phases: shoots at the player, area damage, 20% hp spawns enemy, stun
			// more damage and less fire rate
			if (Util.getDistance(posX, posY, level.getPlayer().getPosX(), level.getPlayer().getPosY()) < 200) {
				if (time % 120 == 0) {
					if (rnd.nextInt(100) < 60) {
						areaDamage();
					} else {
						stun();
					}
					firetime = 60;
				}

				if (time % 3 == 0) {
					Vector2i start = new Vector2i((int) posX >> 5, (int) posY >> 5);
					Vector2i goal = new Vector2i((int) (level.getPlayer().getPosX() + 16) >> 5, (int) (level.getPlayer().getPosY() + 16) >> 5);
					path = level.findPath(start, goal);
				}

			} else {
				path = null;
			}

			if (path != null) {
				xa = 0;
				ya = 0;
				if (path.size() > 0) {
					if (path.get(path.size() - 1).tile.getX() << 5 < posX) xa--;
					if (path.get(path.size() - 1).tile.getX() << 5 > posX) xa++;
					if (path.get(path.size() - 1).tile.getY() << 5 < posY) ya--;
					if (path.get(path.size() - 1).tile.getY() << 5 > posY) ya++;
				}
			} else {
				moveRandomly(time);
			}

			if (firetime < 0) {
				shootPlayer(200);
			} else firetime--;

			if (health < 200) {
				spawnEnemy();
			}

			/*if (anim < 7500) anim++;
			else anim = 0;*/

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
			// if we beat him, the difficulty will increase
			Game.difficulty++; 

			if (rnd.nextInt(3) == 1) level.add(new Chest((int) posX + 20, (int) posY + 20).addItem(new MinorHealth()));// .addRandomItem());
			new Spawner((int) posX + 16, (int) posY + 16, Spawner.Type.EMERALDPARTICLE, 20, level);
			remove();
		}

		sprite = animSprite.getSprite();
	}

	private void areaDamage() {
		// he shoots 5 bomb at the player that does damage within a certain area
		double dx = level.getPlayer().getPosX() - posX;
		double dy = level.getPlayer().getPosY() - posY;
		double dir = Math.atan2(dy, dx);
		level.add(new FireBombProjectile(posX, posY, dir - 0.3));
	}

	private void spawnEnemy() {
		// if hp 20% less, he spawns a portal: 3
		if (time % 600 == 0) {
			if (health < 200 && num_portals < 3) {
				level.add(new Portal((int) posX, (int) posY, 1));
				num_portals++;
			}
		}
	}

	private void stun() {
		for (int i = 0; i < 12; i++) {
			level.add(new StunProjectile(posX + 25, posY + 32, Math.toRadians(i * 30)));
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(new Sprite(100, 20, 0xff3b383b), Game.width / 2 - 50, 0, false);
		double hp = health * 93 / 1000;
		screen.renderHUD(new Sprite(93, 15, 0xff00ff00), (int) hp, Game.width / 2 - 46, 3, false);
		screen.drawText("BOSS " + health, Game.getWindowWidth() / 2 - 35, 25, MyFont.diabloFontMini, Color.ORANGE);
		screen.renderCharacter((int) posX, (int) posY, this);
	}
}
