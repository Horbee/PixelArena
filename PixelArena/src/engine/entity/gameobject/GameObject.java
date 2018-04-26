package engine.entity.gameobject;

import engine.entity.Entity;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class GameObject extends Entity {

	public static GameObject oszlop = new BigSimpleObject(Sprite.oszlop3);
	public static GameObject oszloptort = new SimpleObject(Sprite.oszlop3_tort);
	public static GameObject tabla = new SimpleObject(Sprite.tabla);
	public static GameObject tabla_questmark = new SimpleObject(Sprite.tabla_questmark);

	public static final int COl_CATAGO_OSZLOP = 0xffaba9ab;
	public static final int COl_CATAGO_OSZLOPTORT = 0xff8c898c;
	public static final int COl_CATAGO_TABLA = 0xffe2e412;
	public static final int COl_CATAGO_TABLA_QUESTMARK = 0xffcdce33;

	public Sprite currentSprite;
	public int currlife;
	public boolean floatup;
	private double yy;

	protected int width, height;
	
	public GameObject() {
	}

	public GameObject(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void damage(int dmg) {

	}

	public void update() {

	}

	public void render(int posX, int posY, Screen screen) {

	}

	public void render(Screen screen) {
		screen.renderObject(this, (int) posX, (int) (posY + yy));
		// Debug.drawRect(screen, posX, posY, 32, 32, true);
	}

	public boolean solid() {
		return false;
	}

	public void floating(int time) {
		if (time % 30 == 0) {
			floatup = !floatup;
		}
		if (floatup) {
			yy -= 0.2;
		} else {
			yy += 0.2;
		}
	}

}
