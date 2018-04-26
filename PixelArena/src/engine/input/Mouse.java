package engine.input;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import engine.Game;
import engine.State;
import engine.UI.CharacterUI;
import engine.UI.GameUI;

public class Mouse extends MouseAdapter {

	private static List<Integer> clicked = new ArrayList<Integer>();
	private static List<Integer> released = new ArrayList<Integer>();
	private static boolean[] mouseButtons = new boolean[4];

	private static int x = -1;
	private static int y = -1;
	private static int button = -1;
	public static final int LEFT_BUTTON = 1;
	public static final int RIGHT_BUTTON = 3;

	public static int MdX = -1;
	public static int MdY = -1;
	public static boolean dragged = false;

	private static Rectangle mouseRect;

	private static double scale = 1.0;

	public Mouse(double scale) {
		Mouse.scale = scale;
	}

	public Mouse() {
		mouseRect = new Rectangle();
	}

	public static int getMouseX() {
		return x;
	}

	public static int getMouseY() {
		return y;
	}

	public static int getButton() {
		return button;
	}

	public static Rectangle getMouseRect() {
		return mouseRect;
	}

	public static boolean isMouseOnObject() {

		for (int i = 0; i < GameUI.buttons.size(); i++) {
			if (GameUI.buttons.get(i).isMouseOn == true) {
				return true;
			}
		}
		
		CharacterUI c = Game.level.getPlayer().charUI;
		
		if (c.on) {
			if (Mouse.getMouseX() / 2 > c.getPosX() && Mouse.getMouseX() / 2 < c.getPosX() + 96 && Mouse.getMouseY() / 2 > c.getPosY() && Mouse.getMouseY() / 2 < c.getPosY() + 160) {
				return true;
			}			
		}
		
		return false;
	}

	public static boolean mouseClicked(int mouseB) {
		if (!mouseButtons[mouseB]) return false;
		if (clicked.contains(new Integer(mouseB))) return false;
		clicked.add(new Integer(mouseB));
		return true;
	}

	public static boolean mouseReleased(int mouseB) {
		
		if (mouseButtons[mouseB]) return false;
		if (released.contains(new Integer(mouseB))) return false;
		released.add(new Integer(mouseB));
		return true;
	}
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public static boolean mousePressed(int mouseB) {
		return mouseButtons[mouseB];
	}

	public void mousePressed(MouseEvent e) {
		button = e.getButton();
		mouseButtons[e.getButton()] = true;

	}

	public void mouseReleased(MouseEvent e) {
		dragged = false;
		button = MouseEvent.NOBUTTON;

		mouseButtons[e.getButton()] = false;
		if (clicked.contains(new Integer(e.getButton()))) clicked.remove(new Integer(e.getButton()));
		if (released.contains(new Integer(e.getButton()))) released.remove(new Integer(e.getButton()));

	}

	public void mouseDragged(MouseEvent e) {
		if (State.getState() == State.GAME) {
			x = (int) (e.getX() / scale / scale);
			y = (int) (e.getY() / scale / scale);
		}
		dragged = true;
		MdX = (int) (e.getX() / scale / scale);
		MdY = (int) (e.getY() / scale / scale);
	}

	public void mouseMoved(MouseEvent e) {
		x = (int) (e.getX() / scale / scale);
		y = (int) (e.getY() / scale / scale);
	}

}
