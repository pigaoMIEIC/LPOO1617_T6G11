package dkeep.logic;
import java.util.Random;


public class Suspicious extends Guarda {
	Random rd = new Random();
	boolean dir = true;
	public Suspicious(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}
	
	public void move() {
		int i = rd.nextInt(10);
		if (i < 3) {
		dir = !dir;
		}
		compGuarda.move(dir, this);
	}
}
