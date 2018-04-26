package engine;

public class State {

	public static final int GAME = 0x0;
	public static final int MENU = 0x2;
	private static int state = MENU;

	public static boolean fadeOut = false;

	public static void setState(int state) {
		State.state = state;
	}

	public static int getState() {
		return state;
	}
	
}
