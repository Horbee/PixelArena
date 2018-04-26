package engine.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate;
	private int lenght = -1;
	private int time = 0;
	public boolean oncePlayed = false;
	public boolean oncePlayedReverse = false;

	public AnimatedSprite(SpriteSheet sheet, int width, int height, int lenght, int rate) {
		super(sheet, width, height);
		this.lenght = lenght;
		this.rate = rate;
		sprite = sheet.getSprites()[0];
		if (lenght > sheet.getSprites().length) System.err.println("lenght of animation is too long!");
	}

	public AnimatedSprite(SpriteSheet sheet, int width, int height, int lenght) {
		super(sheet, width, height);
		this.lenght = lenght;
		this.rate = 5;
		sprite = sheet.getSprites()[0];
		if (lenght > sheet.getSprites().length) System.err.println("lenght of animation is too long!");
	}

	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= lenght - 1) {
				oncePlayed = true;
				frame = 0;
			} else frame++;
			sprite = sheet.getSprites()[frame];
		}
		// System.out.println(sprite + ", Frame: " + frame);
	}

	public void playOnce() {
		time++;
		if (time % rate == 0) {
			if (frame >= lenght - 1) {
				oncePlayed = true;
				oncePlayedReverse = false;
			} else frame++;
			sprite = sheet.getSprites()[frame];
			//if (oncePlayed) frame = 0;
		}
		// System.out.println(sprite + ", Frame: " + frame);
	}

	public void playReverseOnce() {
		time++;
		if (time % rate == 0) {
			if (frame >= lenght - 1) {
				oncePlayedReverse = true;
				oncePlayed = false;
			} else frame++;
			sprite = sheet.getSprites()[4 - frame];
			if (oncePlayedReverse) frame = 0;
		}
		// System.out.println(sprite + ", Frame: " + frame);
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int frames) {
		rate = frames;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void resetFrame() {
		frame = 0;
		sprite = sheet.getSprites()[0];
	}

	public void setFrame(int index) {
		if (index > sheet.getSprites().length - 1) return;
		sprite = sheet.getSprites()[index];
	}
}
