package engine.graphics;

public class Enviroment {

	public static int GreyScale(int col) {
		int r = (col & 0xff0000) >> 16;
		int g = (col & 0xff00) >> 8;
		int b = (col & 0xff);

		r = r - 50;
		g = g - 50;
		b = b - 50;
		int nr = (r + g + b) / 3;
		int ng = (r + g + b) / 3;
		int nb = (r + g + b) / 3;

		return nr << 16 | ng << 8 | nb;
	}
	
	public static int CurseColor(int col) {
		int r = (col & 0xff0000) >> 16;
		int g = (col & 0xff00) >> 8;
		int b = (col & 0xff);

		r = r - 50;
		g = g - 50;
		b = b - 50;
		int nr = (r + g + b) / 2;
		int ng = (r + g + b) / 3;
		int nb = (r + g + b) / 3;

		return nr << 16 | ng << 8 | nb;
	}

	
	public static int makeTransparent(int col, int value) {
		int a = (col & 0xff000000) >> 24;
		int r = (col & 0xff0000) >> 16;
		int g = (col & 0xff00) >> 8;
		int b = (col & 0xff);

		a += value;
		if (a < 0) a = 0;
		if (a > 255) a = 255;

		return a << 24 | r << 16 | g << 8 | b;
	}

	public static int makeDarker(int col, double value) {
		double r = (col & 0xff0000) >> 16;
		double g = (col & 0xff00) >> 8;
		double b = (col & 0xff);

		r = r / value;
		g = g / value;
		b = b / value;

		return (int) r << 16 | (int) g << 8 | (int) b;
	}
	


	public static int makeBrighter(int col, double value) {
		double r = (col & 0xff0000) >> 16;
		double g = (col & 0xff00) >> 8;
		double b = (col & 0xff);

		r = r * value;
		g = g * value;
		b = b * value;

		if (r > 255) r = 255;
		if (g > 255) g = 255;
		if (b > 255) b = 255;
		if (r < 0) r = 0;
		if (g < 0) g = 0;
		if (b < 0) b = 0;
		
		return (int) r << 16 | (int) g << 8 | (int) b;
	}
	
	

	public static int changeBirghtness(int col, int amount) {
		int r = (col & 0xff0000) >> 16;
		int g = (col & 0xff00) >> 8;
		int b = (col & 0xff);
		// if (amount > ) {

		// }

		r += amount;
		g += amount;
		b += amount;

		if (r < 0) r = 0;
		if (g < 0) g = 0;
		if (b < 0) b = 0;
		if (r > 255) r = 255;
		if (g > 255) g = 255;
		if (b > 255) b = 255;

		return r << 16 | g << 8 | b;
	}

}
