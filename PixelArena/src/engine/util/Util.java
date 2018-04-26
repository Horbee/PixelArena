package engine.util;

import java.util.Comparator;
import java.util.List;
import engine.State;
import engine.level.Node;


public class Util {
	

	public static int clamp(int var, int min, int max) {
		if (var < min) return min;
		if (var > max) return max;
		return var;
	}

	public static int min(int var, int min){
		if(var < min) return min;
		return var;
	}
	
	public static int max(int var, int max){
		if(var > max) return max;
		return var;
	}
	
	public static double getDistance(Vector2i start, Vector2i goal) {
		double dx = start.getX() - goal.getX();
		double dy = start.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public static double getDistance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public static double lerp(double a, double b, double f) {
		return (a * (1.0f - f)) + (b * f);
	}
	
	public static Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public static int myabs(double value) {
		if (value > 0) return 1;
		return -1;
	}

	public static boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}

}
