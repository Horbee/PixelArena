package engine;

import engine.entity.Entity;
import engine.graphics.Screen;

public class Camera {

	private double posX, posY;
	
	public Camera(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void update(Entity player, Screen screen) {
		//posX++;
//		posX = -player.getPosX() + Game.width /2 ;
		double velX = ((player.getPosX() + screen.width /2) - posX - screen.width) * 0.02;
		double velY = ((player.getPosY() + screen.height /2) - posY - screen.height) * 0.02;
		//System.out.println("VelX: " + velX + " VelY: " + velY);
		posX += velX; //tweening algorythm
		posY += velY; //tweening algorythm

	}
	
	public void update(double x, double y, Screen screen) {
		posX += ((x + screen.width /2) - posX - screen.width) * 0.02; //tweening algorythm
		posY += ((y + screen.height /2) - posY - screen.height) * 0.02; //tweening algorythm
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

}
