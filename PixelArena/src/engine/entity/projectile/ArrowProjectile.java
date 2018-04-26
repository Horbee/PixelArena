package engine.entity.projectile;

import engine.graphics.Screen;
import engine.graphics.Sprite;

public class ArrowProjectile extends Projectile{	
	
	public ArrowProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 300;
		speed = 5;
		sprite = Sprite.rotate(Sprite.proj_arrow, 32, dir, 16, 7);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	

	public void update() {
		move();
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int)posX, (int)posY, this);
		//Debug.drawRect(screen, (int)(posX), (int) (posY + 15), 32, 5, true);
	}
	
	protected void move() {
		posX += nx;
		posY += ny;
		//for (int i = 0; i < level.gameobjects.size(); i++) {
		//	GameObject temp = level.gameobjects.get(i);
		//	if (rect.intersects(temp.collRect)) {
				//takeDamage(temp);
				//new Spawner((int)posX, (int)posY, Spawner.Type.FIREPARTICLE, 1, level);
				//new Spawner((int)posX, (int)posY, Spawner.Type.STONEPARTICLE, 10, level);
			//	remove();
		//	}	
		//}
		if (level.tileCollision((int)(posX + nx), (int)(posY + ny), 14, 15, 15)) {
			//new Spawner((int)posX, (int)posY, Spawner.Type.FIREPARTICLE, 1, level);
			remove();
		}
		
		if (distance() > range) remove();
	}
}
