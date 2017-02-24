package dkeep.logic;

public class Rookie extends Guarda {

	public Rookie(int x, int y, char tag, char[] mov) {
		super(x, y, tag, mov);
	}
	
	public void move(){
		compGuarda.move(true,this);
	}

}
