package dkeep.logic;

public class Hero extends Entidade {
	char tag;
	
	public Hero(int x, int y,char tag) {
		super(x, y);
		this.tag = tag;
	}
	
	public void movimento(char input){
		switch (input) {
		case 's':
			if (b.get(x, y + 1) == 'x') {
				break;
			}
			if (b.get(x, y + 1) == 'i') {
				if (state == 'K')
					b.openDoors();
				break;
			}
			current = b.refresh(x, y + 1, input, tag, current);
			y++;
			break;
		case 'w':
			if (b.get(x, y - 1) == 'x') {
				break;
			}
			if (b.get(x, y - 1) == 'i') {
				if (state == 'K')
					b.openDoors();
				break;
			}
			current = b.refresh(x, y - 1, input, tag, current);
			y--;
			break;
		case 'd':
			if (b.get(x + 1, y) == 'x') {
				break;
			}
			if (b.get(x + 1, y) == 'i') {
				if (state == 'K')
					b.openDoors();
				break;
			}
			current = b.refresh(x + 1, y, input, tag, current);
			x++;
			break;
		case 'a':
			if (b.get(x - 1, y) == 'x') {
				break;
			}
			if (b.get(x - 1, y) == 'i') {
				if (state == 'K')
					b.openDoors();
				break;
			}
			current = b.refresh(x - 1, y, input, tag, current);
			x--;
			break;
		default:
			System.out.println("default input");
		}
	}

}
