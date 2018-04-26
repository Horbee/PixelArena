package engine.graphics;

public class Light {

	public int x, y, width, height;
	public boolean fixed;
	public double flickering = 0;
	public double intensity;
	private double radius = 50;
	private boolean flicker;

	public Light(int x, int y, int radius, int intensity, boolean fixed) {
		this.x = x;
		this.y = y;
		this.width = radius;
		this.height = radius;
		this.radius = radius;
		this.intensity = intensity;
		this.fixed = fixed;
	}

	public void update() {
		if (flickering <= 0 || flickering >= 5) {
			flicker = !flicker;
		}
		if (flicker) flickering += 0.5;
		else flickering -= 0.5;
	}

	public void render(int x, int y, Screen screen) {
		// Debug.drawRect(screen, x, y, width, height, fixed);
		screen.renderLight((int) (x - flickering), (int) (y - flickering), (int) (radius + flickering), this);
	}

}
