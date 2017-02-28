package dkeep.logic;

import java.util.Vector;

public class Hero extends Entidade {

	char state = ' ';

	public Hero(int x, int y, char tag) {
		super(x, y, tag);
	}

	public boolean print(String level, char current, Board b) {
		if (level == "level1" && current != ' ') {
			switch (current) {
			case 'k':
				b.openDoors();
				System.out.print(tag);
				break;
			case 's':
				System.out.print(tag);
				return true;
			}
		} else if (level == "level2" && current != ' ') {
			switch (current) {
			case 'k':
				tag = 'K';
				state = 'K';
				System.out.print(tag);
				break;
			case 's':
				System.out.print(tag);
				return true;
			}
		} else if(b.get(x, y)==tag|b.get(x, y)==' '){
			System.out.print(tag);
		}

		return false;
	}

	public void move(char input, Board b) {
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
			this.x++;
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
			this.x--;
			break;
		default:
			System.out.println("default input");
		}
	}

	public boolean checkSurround(char enemy, Vector<Entidade> map) {
		for (Entidade temp : map) {
			if (temp.tag == enemy) {
				if ((temp.x == x + 1 && temp.y == y) | (temp.x == x - 1 && temp.y == y)
						| (temp.x == x && temp.y == y + 1) | (temp.x == x && temp.y == y - 1)
						| (temp.x == x && temp.y == y))
					return true;
			} else
				return false;
		}
		return false;
	}
	
	public boolean checkSurround(Board b, char enemy) {
		if (b.get(x + 1, y) == enemy || b.get(x - 1, y) == enemy || b.get(x, y + 1) == enemy
				|| b.get(x, y - 1) == enemy|| current==enemy) {
			return true;
		} else
			return false;
	}
}
