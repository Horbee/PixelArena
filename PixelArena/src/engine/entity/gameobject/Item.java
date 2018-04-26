package engine.entity.gameobject;

import engine.entity.projectile.ArrowProjectile;
import engine.entity.projectile.FireProjectile;
import engine.entity.projectile.GreenProjectile;
import engine.entity.projectile.Projectile;
import engine.graphics.Sprite;

public class Item{

	public enum Type{
		GREENPROJECTILE, FIREPROJECTILE, ARROWPROJECTILE
	}
	
	public String name;
	public Sprite sprite;
	private int min_dmg = 0;
	private int max_dmg = 0;
	private int hp = 0;
	private int mana = 0;
	private int FIRE_RATE;
	private Type ptype;
	
	public Item(int spriteID) {
		this.sprite = ItemManager.item[spriteID];
	}
	
	public Item(String name, int spriteID, int min_dmg, int max_dmg, int FR, int hp, int mana, Type ptype) {
		this.name = name;
		this.min_dmg = min_dmg;
		this.max_dmg = max_dmg;
		this.FIRE_RATE = FR;
		this.hp = hp;
		this.mana = mana;
		this.sprite = ItemManager.item[spriteID];
		this.ptype = ptype;
	}
	
	public Projectile getProjectile(double posX, double posY, double dir){
		if (ptype == Type.GREENPROJECTILE) return new GreenProjectile(posX, posY, dir);
		if (ptype == Type.FIREPROJECTILE) return new FireProjectile(posX, posY, dir);
		if (ptype == Type.ARROWPROJECTILE) return new ArrowProjectile(posX, posY, dir);
		else return null;
	}

	public void use() {

	}
	
	public int getMinDmg() {
		return min_dmg;
	}
	
	public int getMaxDmg() {
		return max_dmg;
	}

	public int getFireRate() {
		return FIRE_RATE;
	}
	
	public int getHp() {
		return hp;
	}

	public int getMana() {
		return mana;
	}

	public int getFIRE_RATE() {
		return FIRE_RATE;
	}

}
