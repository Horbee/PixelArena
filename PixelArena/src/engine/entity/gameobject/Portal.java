package engine.entity.gameobject;

import java.util.ArrayList;
import java.util.List;

import engine.entity.mob.Devil;
import engine.entity.mob.MrBone;
import engine.entity.mob.Orc;
import engine.entity.mob.player.Boss;
import engine.entity.particles.PortalParticle;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class Portal extends GameObject {

	private List<PortalParticle> particles = new ArrayList<PortalParticle>();
	private int time = 0;
	private int type; //0: ORC; 1: Mr.Bone, 2: Devil, 3: Boss
	
	public Portal(int posX, int posY, int type) {
		super(posX, posY);
		this.type = type;
		currentSprite = Sprite.portal;
	}

	public void update() {
		time++;
		//System.out.println(particles.size());
		particles.add(new PortalParticle((int) posX + 15, (int) posY + rnd.nextInt(30), 60));

		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
			else particles.get(i).update();
		}
		if (time % 180 == 0) {
			if(type == 0) level.add(new Orc((int)posX, (int)posY));
			if(type == 1) level.add(new MrBone(posX, posY));
			if(type == 2) level.add(new Devil((int)posX, (int)posY));
			if(type == 3) level.add(new Boss((int)posX, (int)posY));
			remove();
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(Sprite.portal, (int) posX, (int) posY, true);
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
	}

}
