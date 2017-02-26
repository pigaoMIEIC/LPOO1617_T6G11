package dkeep.logic;

public class Rookie extends Guarda {

	public Rookie(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}

	public void move(char direction, Board b) {
		compGuarda.move(true,this,b);
	}

}
