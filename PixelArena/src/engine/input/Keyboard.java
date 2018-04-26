package engine.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Keyboard extends KeyAdapter {

	public boolean up, down, left, right;

	private static List<Integer> pressed = new ArrayList<Integer>();
	private static boolean[] keys = new boolean[65536];

	// String inputtext = "";

	public Keyboard() {
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// inputtext += e.getKeyChar();
		// System.out.println(inputtext);

		if (key == KeyEvent.VK_ESCAPE) System.exit(1);

		if (key == KeyEvent.VK_A) left = true;
		if (key == KeyEvent.VK_D) right = true;
		if (key == KeyEvent.VK_W) up = true;
		if (key == KeyEvent.VK_S) down = true;

		keys[e.getKeyCode()] = true;

	}

	public static boolean keyPressed(int key) {
		return keys[key];
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
		if (key == KeyEvent.VK_A) left = false;
		if (key == KeyEvent.VK_D) right = false;
		if (key == KeyEvent.VK_W) up = false;
		if (key == KeyEvent.VK_S) down = false;

		keys[e.getKeyCode()] = false;

		if (pressed.contains(new Integer(e.getKeyCode()))) pressed.remove(new Integer(e.getKeyCode()));

	}

	public static boolean keyTyped(int key) {
		if (!keys[key]) return false;
		if (pressed.contains(new Integer(key))) return false;

		pressed.add(new Integer(key));

		return true;
	}

}
