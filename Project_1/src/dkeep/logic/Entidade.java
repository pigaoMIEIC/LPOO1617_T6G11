package dkeep.logic;

public abstract class Entidade {
	int x,y;
	char tag=' ';
	char current=' ';
	
	public Entidade(int x, int y){
		this.x = x;
		this.y = y;
	}

	public abstract boolean print(String level,char current,Board b);
	
	public abstract void move(char direction, Board b);
	
}
