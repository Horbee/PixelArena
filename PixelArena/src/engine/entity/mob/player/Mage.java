package engine.entity.mob.player;

import java.awt.Color;
import java.awt.event.KeyEvent;
import engine.Game;
import engine.UI.SkillUI;
import engine.entity.Spawner;
import engine.entity.projectile.FireballProjectile;
import engine.entity.projectile.FrostProjectile;
import engine.graphics.AnimatedSprite;
import engine.graphics.MyFont;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.util.Util;

public class Mage extends Player {

	public AnimatedSprite up = new AnimatedSprite(SpriteSheet.mage_up, 32, 32, 3);
	public AnimatedSprite left = new AnimatedSprite(SpriteSheet.mage_left, 32, 32, 3);
	public AnimatedSprite right = new AnimatedSprite(SpriteSheet.mage_right, 32, 32, 3);
	public AnimatedSprite down = new AnimatedSprite(SpriteSheet.mage_down, 32, 32, 3);
	// public AnimatedSprite idle = new AnimatedSprite(SpriteSheet.player_idle, 32, 32, 5);

	public SkillUI fireball_ui, slow_ui;

	public Mage(int posX, int posY, Keyboard input) {
		super(posX, posY, input);
		health = 60;
		base_health = 100;
		max_health = base_health;
		mana = 100;
		base_mana = 200;
		max_mana = base_mana;
		speed = 2;
		defense = 0;

		animSprite = down;
		fireRate = 5;
		dir = 2;

		
		fireball_ui = new SkillUI(40, 32, Sprite.scale(Sprite.skill_fireball, 0.5));
		fireball_ui.setLabel("FIREBALL \nactivate by pressing \n<space> button.");
		charUI.skill_uis.add(fireball_ui);

		slow_ui = new SkillUI(60, 32, Sprite.scale(Sprite.skill_slow, 0.5));
		slow_ui.setLabel("SLOWBALL \nslows enemy by 20% \nactivate by pressing \n<1> button.");
		charUI.skill_uis.add(slow_ui);
	}

	public void update() {
		// super.update();
		timer++;
		
		if (!isDead) {

			if (fireRate > 0) fireRate--;

			if (!stunned) {
				if (walking) {
					animSprite.update();
					// } else if (dir == 2) {
					// animSprite.update();
				} else {
					animSprite.setFrame(1);
				}

				double xa = 0, ya = 0;

				if (anim < 7500) anim++;
				else anim = 0;

				if (input.up) {
					ya -= speed;
					dir = 0;
					animSprite = up;
				} else if (input.down) {
					ya += speed;
					dir = 2;
					animSprite = down;
				}
				if (input.left) {
					xa -= speed;
					dir = 3;
					animSprite = left;
				} else if (input.right) {
					xa += speed;
					dir = 1;
					animSprite = right;
				}

				if (dir == 0) animSprite = up;
				else if (dir == 2) animSprite = down;
				else if (dir == 3) animSprite = left;
				else if (dir == 1) animSprite = right;

				if (xa != 0 || ya != 0) {
					move(xa, ya);
					walking = true;
				} else {
					walking = false;
					// if (dir == 2) animSprite = idle;
				}
			}

			// SHOOTING MECHANISM
			updateShooting();
			clear();
			if (Keyboard.keyTyped(KeyEvent.VK_SPACE)) fireballSkill();
			if (Keyboard.keyTyped(KeyEvent.VK_1)) slowBall();

			if (health <= 0) {
				isDead = true;
				new Spawner((int) posX, (int) posY, Spawner.Type.EMERALDPARTICLE, 100, level);
			}
			
			// SETTING THE HEALTH AND MANA, DOING REGEN
			if ((health != max_health) && (timer % hp_regen_time == 0)) health++;
			if ((mana != max_mana) && (timer % mana_regen_time == 0)) mana++;
			health = Util.clamp(health, 0, max_health);
			mana = Util.clamp(mana, 0, max_mana);

			// RECHARGE SPEED
			if (speed < 2) {
				if (timer % 60 == 0) speed += 0.5;
			}

			// IF STUNNED
			if (stunned) {
				stun_timer--;
				if (stun_timer < 0) {
					stunned = false;
					stun_timer = 120;
				}
			}

			if (charUI.slot_weapon.getItem() != null) {
				dmg = Integer.toString(getWeapon().getMinDmg() + dmgModifier) + "-" + Integer.toString(getWeapon().getMaxDmg() + dmgModifier);
				max_health = base_health + getWeapon().getHp();
				max_mana = base_mana + getWeapon().getMana();
			} else {
				max_mana = base_mana;
				max_health = base_health;
				dmg = "0";
			}
		}

		if (isDead) {
			// to do
		}
	}

	public void render(Screen screen) {
		if (!isDead) {
			sprite = animSprite.getSprite();
			screen.renderCharacter((int) posX, (int) posY, this);
		} else {
			screen.drawText("You are dead", Game.getWindowWidth() / 2 - 100, Game.getWindowHeight() / 2 - 20, MyFont.diabloFont, Color.RED, false);
		}
	}

	private void fireballSkill() {
		if (mana > 30) {
			for (int i = 0; i < 12; i++) {
				level.add(new FireballProjectile(posX + 14, posY + 25, Math.toRadians(i * 30)));
			}
			mana -= 30;
		}
	}

	private void slowBall() {
		double dx = Mouse.getMouseX() - Game.getWindowWidth() / 2;
		double dy = Mouse.getMouseY() - Game.getWindowHeight() / 2;
		double dir = Math.atan2(dy, dx);
		FrostProjectile p = new FrostProjectile(posX, posY + 10, dir);
		p.setFriendly(true);
		level.add(p);

	}

	protected void updateShooting() {
		if (charUI.slot_weapon.hasItem) {
			if (Mouse.getButton() == 1 && fireRate <= 0 && Mouse.isMouseOnObject() == false) {

				double dx = Mouse.getMouseX() - Game.getWindowWidth() / 2;
				double dy = Mouse.getMouseY() - Game.getWindowHeight() / 2;
				double dir = Math.atan2(dy, dx);
				// if (mana > 10) {
				shoot(posX, posY, dir);
				Game.sounds[3].play();
				// mana -= 10;
				// }
				// fireRate = charUI.slot_weapon.getItem().getProjectile(anim,
				// anim, dir)
				fireRate = charUI.slot_weapon.getItem().getFireRate();
			}
		}
	}
}
