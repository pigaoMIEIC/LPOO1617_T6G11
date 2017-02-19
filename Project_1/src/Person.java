
public class Person {

	char tag;
	int x;
	int y;
	char current = ' ';
	char state;

	Person(char tag, int x, int y) {
		this.tag = tag;
		this.x = x;
		this.y = y;
		state = ' ';
	}

	public char state() {
		return this.state;
	}

	public char setState(char state) {
		return this.state = state;
	}

	public boolean checkSurround(Board b, char enemy) {
		if (b.get(x + 1, y) == enemy || b.get(x - 1, y) == enemy || b.get(x, y + 1) == enemy
				|| b.get(x, y - 1) == enemy) {
			System.out.println("You got caught! :(");
			return true;
		} else
			return false;
	}

	public void reset(int x, int y,char tag,Board b) {
		this.x = x;
		this.y = y;
		current = ' ';
		this.tag=tag;
		state = ' ';
		b.set(x, y, tag);
	}

	public boolean move(Board b, char input) {
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

		if (current == 'k') {
			state = 'K';
		}
		if (current == 's') {
			b.print();
			System.out.println("Parabens passou o nivel!!");
			return true;
		}
		return false;

	}
}