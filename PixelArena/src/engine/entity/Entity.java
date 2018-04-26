package engine.entity;

import java.awt.event.KeyEvent;
import java.util.Random;

import engine.Game;
import engine.graphics.Screen;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.level.Level;

public class Entity {

	protected double posX, posY;
	protected boolean removed = false;
	protected Level level;

	protected final static Random rnd = new Random();
	public boolean isDead = false;

	public Entity() {

	}

	public void init(Level level) {
		this.level = level;
	}

	public void render(Screen screen) {

	}

	public void update() {

	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public boolean isSelected() {
		int dx = (int) ((level.getPlayer().getPosX() - this.posX));
		int dy = (int) ((level.getPlayer().getPosY() - this.posY));
		double dist = Math.sqrt((dx * dx) + (dy * dy));

		if (dist < 50) {
			if (Keyboard.keyTyped(KeyEvent.VK_E)) {
				return true;
			}
			if (Game.mousePosToGame.x > this.posX && Game.mousePosToGame.y > this.posY && Game.mousePosToGame.x < this.posX + 32 && Game.mousePosToGame.y < this.posY + 32) {
				if (Mouse.mouseClicked(Mouse.RIGHT_BUTTON) && dist < 50) {
					return true;
				}
			}
		}
		return false;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public void setPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

}
