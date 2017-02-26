package dkeep.logic;
import java.util.Random;


public class Suspicious extends Guarda {
	Random rd = new Random();
	boolean dir = true;
	public Suspicious(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}
	
	public void move(char direction, Board b) {
		int i = rd.nextInt(100);
		if (i < 10) {
		dir = !dir;
		}
		compGuarda.move(dir, this, b);
	}
}
