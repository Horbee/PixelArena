package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import engine.UI.GameUI;
import engine.UI.UIManager;
import engine.audio.Sound;
import engine.entity.mob.player.Mage;
import engine.entity.mob.player.Player;
import engine.entity.mob.player.PlayerManager;
import engine.graphics.MyFont;
import engine.graphics.Screen;
import engine.input.Keyboard;
import engine.input.Mouse;
import engine.level.Level;
import engine.level.TestLevel;
import engine.level.TmxLevel;
import engine.menu.Menu;
import engine.util.Util;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static Game game;
	public static int time = 0;
	public static int difficulty = 1;

	public static String title = "PixelArena";
	public static final int width = 480;
	public static final int height = width / 16 * 9;
	private static final int scale = 2;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	private Thread thread;
	private JFrame frame;
	private Screen screen;
	private static Keyboard key;
	private Mouse mouse;

	public static Level level;
	public static TestLevel testLevel;
	public static TmxLevel nexus;
	private Camera cam;
	public static MyFont font;
	public static GameUI gUI;
	public static UIManager uiManager;
	public static boolean AUDIO = true;

	public static Point mousePosToScreen;
	public static Point mousePosToGame;

	private static Menu menu;
	public static Sound[] sounds = new Sound[5];
	private boolean running = false;

	public double fadeValue = 40;

	public static void main(String[] args) {
		game = new Game();

		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.init();

		game.start();
	}

	public Game() {
		MyFont.loadAll();

		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);

		screen = new Screen(width, height);
		frame = new JFrame();
		cam = new Camera(0, 0);
		key = new Keyboard();
		mouse = new Mouse();
		font = new MyFont();
		addKeyListener(getKey());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		mousePosToScreen = new Point(0, 0);
		mousePosToGame = new Point(0, 0);

	}

	public void init() {
		menu = new Menu();

		sounds[0] = new Sound("src/data/audio/click.wav");
		sounds[1] = new Sound("src/data/audio/open.wav");
		sounds[2] = new Sound("src/data/audio/hover.wav");
		sounds[3] = new Sound("src/data/audio/shoot.wav");
		sounds[4] = new Sound("src/data/audio/powerup.wav");

		uiManager = new UIManager();
		PlayerManager.setPlayer(new Mage(2 * 32, 2 * 32, Game.getKey()));

		testLevel = new TestLevel();
		nexus = new TmxLevel("/data/level/nexusmini.xml");

		level = testLevel;

		gUI = new GameUI();

	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta_ups = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta_ups += (now - lastTime) / ns;
			lastTime = now;
			while (delta_ups >= 1) {
				update(); // LOGIC: 60 times/sec
				updates++;
				delta_ups--;
			}
			render(); // GRAPHIC
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(title + " | " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}

		}

	}

	float y = 5;

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		screen.clear();

		// RENDERING GAME

		if (State.getState() == State.MENU) {
			if (State.fadeOut) {
				fadeValue = Util.lerp(fadeValue, 5, 0.005);
				screen.setTileColorDarker(fadeValue);
				if (fadeValue < 6) {
					State.fadeOut = false;
				}
			}

			y += 0.05f;
			if (y > 350) {
				State.fadeOut = true;
				fadeValue = 40;
				y = -100;
			}

			level.render(300, y, screen);
		} else {
			level.render(PlayerManager.getPlayer().getPosX() - width / 2, PlayerManager.getPlayer().getPosY() - height / 2, screen);
		}
		if (!(State.getState() == State.MENU))
			gUI.render(screen);
		for (int i = 0; i < screen.top_layer.size(); i++) {
			screen.top_layer.get(i).render(screen);
		}

		// RENDERING MENU
		if (State.getState() == State.MENU) {
			menu.render(screen);
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		uiManager.render(g);

		for (int i = 0; i < screen.text_buffer.size(); i++) {
			screen.text_buffer.get(i).render(g);
		}

		if ((State.getState() == State.GAME)) {
			// g.setColor(Color.WHITE);
			// g.drawString(mousePosToScreen.toString(), 20, 80);
			// g.drawString("playerX: " + level.player.posX + " playerY: " +
			// level.player.posY, 20, 100);

			// GAME DIMENSIONS CURSOR POSITION
			g.setColor(Color.ORANGE);
			// g.drawString("MouseX: " + mousePosToGame.x + " MouseY: " +
			// mousePosToGame.y, 20, 140);
			// GAME DIMENSIONS COLLISION RECTS
			// int newPRX = ((level.tCh.collRect.x + level.player.posX) / scale)
			// + 16;
			// int newPRY = ((level.tCh.collRect.y + level.player.posY) / scale)
			// + 16;
			// g.drawString("newPRX: " + newPRX + " newPRY: " + newPRY, 20,
			// 140);
			// g.setColor(Color.ORANGE);
			// g.drawString(netText, 20, 200);
		}

		screen.top_layer.clear();
		screen.text_buffer.clear();
		g.dispose();
		bs.show();

	}

	private void update() {
		if (time > 7200)
			time = 0;
		else
			time++;
		// Mouse.getMouseRect().setBounds(Mouse.getMouseX(), Mouse.getMouseY(),
		// 10, 10);

		if ((State.getState() == State.GAME)) {
			mousePosToScreen = mousePositionToScreen();
			mousePosToGame = mousePositionToGame();
			level.update();
			gUI.update();
			uiManager.update();
			cam.update(PlayerManager.getPlayer(), screen);
			// chc.update();
		}

		if (State.getState() == State.MENU) {
			menu.update();
		}

	}

	public Point mousePositionToScreen() {
		Point mp = new Point(0, 0);
		if (this.getMousePosition() != null) {
			mp = this.getMousePosition();
		}
		// System.out.println("x: " + mp.x + " y: " + mp.y);
		return mp;
	}

	public Point mousePositionToGame() {
		// GAME DIMENSIONS CURSOR POSITION
		int newMX = ((Mouse.getMouseX() / scale + (int) level.getPlayer().getPosX()) - (width / 2));
		int newMY = ((Mouse.getMouseY() / scale + (int) level.getPlayer().getPosY()) - (height / 2));
		Point mousePosToGame = new Point(newMX, newMY);
		return mousePosToGame;
	}

	public static Keyboard getKey() {
		return key;
	}

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	public static int getScale() {
		return scale;
	}

	public Screen getScreen() {
		return screen;
	}

	public static void changeLevel(int index) {
		level = index == 0 ? testLevel : nexus;
		Game.level.add(PlayerManager.getPlayer());
		Game.level.spawnPlayer();
	}

}
