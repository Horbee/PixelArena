package engine.entity.gameobject;

public class SolidArea extends GameObject {

	public SolidArea(int posX, int posY, int width, int height) {
		super(posX, posY);
		this.width = width;
		this.height = height;

	}

	public boolean solid() {
		return true;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
