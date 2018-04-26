package engine.entity.mob.player;

import engine.Game;
import engine.UI.CharacterUI;
import engine.entity.gameobject.Item;
import engine.entity.mob.Mob;
import engine.entity.projectile.Projectile;
import engine.graphics.Screen;
import engine.input.Keyboard;

public class Player extends Mob {

	public CharacterUI charUI;
	public boolean cursed = false;
	public boolean stunned = false;

	protected int stun_timer = 120;
	protected Keyboard input;
	protected Projectile p;
	protected boolean walking = false;
	protected int anim = 0;
	protected int timer = 0;
	public int dmgModifier = 0;

	public Player(Keyboard input) {
		this.input = input;
	}

	public Player(int posX, int posY, Keyboard input) {
		this.posX = posX;
		this.posY = posY;
		this.input = input;
		charUI = new CharacterUI(Game.width - 100, Game.height - 165, this);
	}

	public void update() {

	}

	public void render(Screen screen) {
	}

	protected void clear() {
		for (int i = 0; i < level.projectiles.size(); i++) {
			if (level.projectiles.get(i).isRemoved()) level.projectiles.remove(i);
		}
	}

	public void shoot(double x, double y, double dir) {
		p = charUI.slot_weapon.getItem().getProjectile(x, y, dir);
		level.add(p);
	}

	public Item getWeapon() {
		return charUI.slot_weapon.getItem();
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public void setWalking(boolean walking) {
		this.walking = walking;
	}
	
	public void setInput(Keyboard input) {
		this.input = input;
	}

}
