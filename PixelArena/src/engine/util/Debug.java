package engine.util;

import engine.graphics.Screen;

public class Debug {
	
	public Debug() {
	}
	
	public static void drawRect(Screen screen, int posX, int posY, int width, int height, int color, boolean fixed) {
		screen.drawRect(posX, posY, width, height, color, fixed);
	}
	
	public static void drawRect(Screen screen, int posX, int posY, int width, int height,boolean fixed) {
		screen.drawRect(posX, posY, width, height, 0xff0000, fixed);
	}
}
