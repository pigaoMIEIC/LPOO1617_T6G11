package dkeep.logic;

import java.util.Random;

public class Drunken extends Guarda {
	Random rd = new Random();
	boolean dir = true;
	public Drunken(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}
	
	public void move(char direction, Board b) {
		int i = rd.nextInt(10);
		if (i < 3) {
			tag = 'g';
			return;
		} else {
			if (tag == 'g') {
				tag = 'G';
				i = rd.nextInt(10);
				if (i < 3) {
					dir = !dir;
				}
			}
		}
		compGuarda.move(dir, this, b);
	}
}
