package engine.util;

public class Vector2i {
	
	private int x, y;
	
	public Vector2i() {
		x = 0;
		y = 0;
	}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2i(Vector2i vector) {
		this.x = vector.getX();
		this.y = vector.getY();
	}
	
	public boolean equals(Object object) {
		if (!(object instanceof Vector2i)) return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == this.getX() && vec.getY() == this.getY()) return true;
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
